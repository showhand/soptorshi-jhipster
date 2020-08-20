package org.soptorshi.service.extended;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelParser;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.extended.AttendanceExcelUploadExtendedRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AttendanceExcelUploadService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class AttendanceExcelUploadExtendedService extends AttendanceExcelUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadExtendedService.class);

    private final AttendanceExcelUploadExtendedRepository attendanceExcelUploadExtendedRepository;

    private final AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceExtendedService attendanceExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final ResourceLoader resourceLoader;

    public AttendanceExcelUploadExtendedService(AttendanceExcelUploadExtendedRepository attendanceExcelUploadExtendedRepository, AttendanceExcelUploadMapper attendanceExcelUploadMapper, AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository, AttendanceMapper attendanceMapper,
                                                AttendanceExtendedService attendanceExtendedService,
                                                EmployeeExtendedRepository employeeExtendedRepository, ResourceLoader resourceLoader) {
        super(attendanceExcelUploadExtendedRepository, attendanceExcelUploadMapper, attendanceExcelUploadSearchRepository);
        this.attendanceExcelUploadExtendedRepository = attendanceExcelUploadExtendedRepository;
        this.attendanceExcelUploadMapper = attendanceExcelUploadMapper;
        this.attendanceMapper = attendanceMapper;
        this.attendanceExtendedService = attendanceExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.resourceLoader = resourceLoader;
    }

    @Transactional
    public AttendanceExcelUploadDTO save(AttendanceExcelUploadDTO attendanceExcelUploadDTO) {
        log.debug("Request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (attendanceExcelUploadDTO.getId() == null) {
            attendanceExcelUploadDTO.setCreatedBy(currentUser);
            attendanceExcelUploadDTO.setCreatedOn(currentDateTime);
        } else {
            attendanceExcelUploadDTO.setUpdatedBy(currentUser);
            attendanceExcelUploadDTO.setUpdatedOn(currentDateTime);
        }

        AttendanceExcelUpload attendanceExcelUpload = attendanceExcelUploadMapper.toEntity(attendanceExcelUploadDTO);
        AttendanceExcelUploadDTO result = null;
        /*attendanceExcelUploadSearchRepository.save(attendanceExcelUpload);*/

        log.debug("Parsing excel before processing request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        List<AttendanceExcelParser> attendanceExcelParsers = parseExcel(attendanceExcelUploadDTO.getFile());
        if (attendanceExcelParsers == null) {
            throw new BadRequestAlertException("Parsing operation failed", "attendanceExcelUpload", "idnull");
        }

        attendanceExcelUpload = attendanceExcelUploadExtendedRepository.save(attendanceExcelUpload);
        result = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);
        parseExcelValueToAttendanceObjectAndSave(attendanceExcelParsers, attendanceExcelUpload);

        return result;
    }

    private List<AttendanceExcelParser> parseExcel(byte[] bytes) {
        try {
            XLSReader xlsReader = null;
            xlsReader = ReaderBuilder.buildFromXML(resourceLoader.getResource("classpath:/templates/jxls/attendance-reader.xml").getFile());
            List<AttendanceExcelParser> result = new ArrayList<>();
            Map<String, Object> beans = new HashMap<>();
            beans.put("attendances", result);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(bytes));
            xlsReader.read(inputStream, beans);
            return result.size() > 0 ? result : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseExcelValueToAttendanceObjectAndSave(List<AttendanceExcelParser> attendanceExcelParsers, AttendanceExcelUpload attendanceExcelUpload) {
        final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        List<Attendance> attendances = new ArrayList<>();
        for (AttendanceExcelParser attendanceExcelParser : attendanceExcelParsers) {
            Optional<Employee> employee = employeeExtendedRepository.findByEmployeeId(attendanceExcelParser.getEmployeeId());
            if (employee.isPresent()) {
                if (!attendanceExcelParser.getAttendanceDate().isEmpty()) {
                    Attendance attendance = new Attendance();
                    attendance.setEmployee(employee.get());
                    attendance.setAttendanceDate(LocalDate.parse(attendanceExcelParser.getAttendanceDate()));

                    String[] inOut = attendanceExcelParser.getInOutTime().split(" ");
                    if (inOut.length > 0) {
                        attendance.setInTime(Instant.from(formatter.parse(attendanceExcelParser.getAttendanceDate() + " " + inOut[0])));
                    }
                    if (inOut.length > 1) {
                        attendance.setOutTime(Instant.from(formatter.parse(attendanceExcelParser.getAttendanceDate() + " " + inOut[inOut.length - 1])));
                    }

                    if (attendance.getInTime() != null && attendance.getOutTime() != null) {
                        Duration between = Duration.between(attendance.getOutTime(), attendance.getInTime());
                        attendance.setDuration(between.toString());
                    }
                    attendance.setAttendanceExcelUpload(attendanceExcelUpload);
                    attendances.add(attendance);
                }
            }
        }

        for(Attendance attendance: attendances) {
            LocalDate currentDate = LocalDate.now();
            if(attendance.getAttendanceDate().compareTo(currentDate) > 0) {
                throw new BadRequestAlertException("Please check the dates", "attendanceExcelUpload", "idexists");
            }
            LocalDate ld1 = null;
            LocalDate ld2 = null;
            if(attendance.getInTime() != null) {
                ld1 = LocalDateTime.ofInstant(attendance.getInTime(), ZoneId.systemDefault()).toLocalDate();
            }
            if(attendance.getOutTime() != null) {
                ld2 = LocalDateTime.ofInstant(attendance.getOutTime(), ZoneId.systemDefault()).toLocalDate();
            }
            if(ld1 != null && ld2 != null) {
                if (!(ld1.isEqual(attendance.getAttendanceDate()) && ld2.isEqual(attendance.getAttendanceDate()))) {
                    throw new BadRequestAlertException("Please check the attendance date with in and out dates", "attendanceExcelUpload", "idexists");
                }
            }
            else if(ld1 != null) {
                if (!(ld1.isEqual(attendance.getAttendanceDate()))) {
                    throw new BadRequestAlertException("Please check the attendance date with in and out dates", "attendanceExcelUpload", "idexists");
                }
            }
            else if(ld2 != null) {
                if (!(ld2.isEqual(attendance.getAttendanceDate()))) {
                    throw new BadRequestAlertException("Please check the attendance date with in and out dates", "attendanceExcelUpload", "idexists");
                }
            }
            Instant currentTime = Instant.now();
            if(attendance.getInTime() != null) {
                if (!(attendance.getInTime().isBefore(currentTime))) {
                    throw new BadRequestAlertException("Please check the in and out time. It should be less than current time", "attendanceExcelUpload", "idexists");
                }
            }
            if(attendance.getOutTime() != null) {
                if (!(attendance.getOutTime().isBefore(currentTime))) {
                    throw new BadRequestAlertException("Please check the in and out time. It should be less than current time", "attendanceExcelUpload", "idexists");
                }
            }
        }

        for (Attendance attendance : attendances) {
            AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
            attendanceExtendedService.save(attendanceDTO);
        }
    }
}
