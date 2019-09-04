package org.soptorshi.service.impl;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelParser;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AttendanceRepository;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.AttendanceExcelUploadService;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.repository.AttendanceExcelUploadRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.service.AttendanceService;
import org.soptorshi.service.EmployeeService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AttendanceExcelUpload.
 */
@Service
@Transactional
public class AttendanceExcelUploadServiceImpl implements AttendanceExcelUploadService {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadServiceImpl.class);

    private final AttendanceExcelUploadRepository attendanceExcelUploadRepository;

    private final AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    private final AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceService attendanceService;

    private final EmployeeRepository employeeRepository;

    public AttendanceExcelUploadServiceImpl(AttendanceExcelUploadRepository attendanceExcelUploadRepository, AttendanceExcelUploadMapper attendanceExcelUploadMapper, AttendanceExcelUploadSearchRepository attendanceExcelUploadSearchRepository, AttendanceMapper attendanceMapper, AttendanceService attendanceService,
                                            EmployeeRepository employeeRepository) {
        this.attendanceExcelUploadRepository = attendanceExcelUploadRepository;
        this.attendanceExcelUploadMapper = attendanceExcelUploadMapper;
        this.attendanceExcelUploadSearchRepository = attendanceExcelUploadSearchRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceService = attendanceService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a attendanceExcelUpload.
     *
     * @param attendanceExcelUploadDTO the entity to save
     * @return the persisted entity
     */
    @Override
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
            attendanceService.deleteByAttendanceExcelUpload(attendanceExcelUpload);
            log.debug("Saving new data AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
            parseExcelValueToAttendanceObjectAndSave(attendanceExcelParsers, attendanceExcelUpload);
        }
        return result;
    }

    /**
     * Get all the attendanceExcelUploads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceExcelUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceExcelUploads");
        return attendanceExcelUploadRepository.findAll(pageable)
            .map(attendanceExcelUploadMapper::toDto);
    }


    /**
     * Get one attendanceExcelUpload by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttendanceExcelUploadDTO> findOne(Long id) {
        log.debug("Request to get AttendanceExcelUpload : {}", id);
        return attendanceExcelUploadRepository.findById(id)
            .map(attendanceExcelUploadMapper::toDto);
    }

    /**
     * Delete the attendanceExcelUpload by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttendanceExcelUpload : {}", id);
        attendanceExcelUploadRepository.deleteById(id);
        attendanceExcelUploadSearchRepository.deleteById(id);
    }

    /**
     * Search for the attendanceExcelUpload corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceExcelUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AttendanceExcelUploads for query {}", query);
        return attendanceExcelUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(attendanceExcelUploadMapper::toDto);
    }

    private List<AttendanceExcelParser> parseExcel(byte[] bytes) {
        try {
            XLSReader xlsReader = null;
            xlsReader = ReaderBuilder.buildFromXML(new File("D:/Projects/soptorshi-jhipster/src/main/resources/templates/jxls/attendance-reader.xml"));
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
            Optional<Employee> employee = employeeRepository.findByEmployeeId(attendanceExcelParser.getEmployeeId());
            if(employee.isPresent()) {
                if (!attendanceExcelParser.getAttendanceDate().isEmpty()) {
                    Attendance attendance = new Attendance();
                    attendance.setEmployeeId(attendanceExcelParser.getEmployeeId());
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
            attendanceService.save(attendanceDTO);
        }
    }
}
