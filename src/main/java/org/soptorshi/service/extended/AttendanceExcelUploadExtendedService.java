package org.soptorshi.service.extended;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelParser;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AttendanceExcelUploadRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.service.AttendanceExcelUploadService;
import org.soptorshi.service.EmployeeService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class AttendanceExcelUploadExtendedService extends AttendanceExcelUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadExtendedService.class);

    private final AttendanceExcelUploadRepository attendanceExcelUploadRepository;

    private final AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    private final AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceExtendedService attendanceExtendedService;

    private final EmployeeService employeeService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    public AttendanceExcelUploadExtendedService(AttendanceExcelUploadRepository attendanceExcelUploadRepository, AttendanceExcelUploadMapper attendanceExcelUploadMapper, AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository, AttendanceMapper attendanceMapper,
                                                AttendanceExtendedService attendanceExtendedService, EmployeeService employeeService,
                                                EmployeeExtendedRepository employeeExtendedRepository) {
        super(attendanceExcelUploadRepository, attendanceExcelUploadMapper, attendanceExcelUploadSearchRepository);
        this.attendanceExcelUploadRepository = attendanceExcelUploadRepository;
        this.attendanceExcelUploadMapper = attendanceExcelUploadMapper;
        this.attendanceExcelUploadSearchRepository = attendanceExcelUploadSearchRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceExtendedService = attendanceExtendedService;
        this.employeeService = employeeService;
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    /**
     * Save a attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the entity to save
     * @return the persisted entity
     */

    public AttendanceExcelUploadDTO save(AttendanceExcelUploadDTO attendanceExcelUploadDTO) {
        log.debug("Request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        AttendanceExcelUpload attendanceExcelUpload = attendanceExcelUploadMapper.toEntity(attendanceExcelUploadDTO);
        attendanceExcelUpload = attendanceExcelUploadRepository.save(attendanceExcelUpload);
        AttendanceExcelUploadDTO result = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);
        attendanceExcelUploadSearchRepository.save(attendanceExcelUpload);

        log.debug("Parsing excel before processing request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        List<AttendanceExcelParser> attendanceExcelParsers = parseExcel(attendanceExcelUploadDTO.getFile());
        if (attendanceExcelParsers == null) return null;
        else {
            log.debug("Deleting previous data AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
            attendanceExtendedService.deleteByAttendanceExcelUpload(attendanceExcelUpload);
            log.debug("Saving new data AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
            parseExcelValueToAttendanceObjectAndSave(attendanceExcelParsers, attendanceExcelUpload);
        }
        return result;
    }

    private List<AttendanceExcelParser> parseExcel(byte[] bytes) {
        try {
            XLSReader xlsReader = null;
            xlsReader = ReaderBuilder.buildFromXML(new File("/home/soptorshi/Documents/attendance-reader.xml"));
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
            if(employee.isPresent()) {
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
                    attendance.setAttendanceExcelUpload(attendanceExcelUpload);
                    attendances.add(attendance);
                }
            }
        }

        for(Attendance attendance: attendances){
            AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
            attendanceExtendedService.save(attendanceDTO);
        }
    }
}
