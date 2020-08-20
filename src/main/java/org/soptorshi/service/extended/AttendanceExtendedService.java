package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.extended.AttendanceExtendedRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AttendanceService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AttendanceExtendedService extends AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceExtendedService.class);

    private final AttendanceExtendedRepository attendanceExtendedRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceSearchRepository attendanceSearchRepository;

    private final EmployeeRepository employeeRepository;

    public AttendanceExtendedService(AttendanceExtendedRepository attendanceExtendedRepository, AttendanceMapper attendanceMapper, AttendanceSearchRepository attendanceSearchRepository,
                                     EmployeeRepository employeeRepository) {
        super(attendanceExtendedRepository, attendanceMapper, attendanceSearchRepository);
        this.attendanceExtendedRepository = attendanceExtendedRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceSearchRepository = attendanceSearchRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */

    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(attendanceDTO.getId() == null) {
            attendanceDTO.setCreatedBy(currentUser);
            attendanceDTO.setCreatedOn(currentDateTime);
        }
        else {
            attendanceDTO.setUpdatedBy(currentUser);
            attendanceDTO.setUpdatedOn(currentDateTime);
        }

        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceExtendedRepository.save(attendance);
        AttendanceDTO result = attendanceMapper.toDto(attendance);
        attendanceSearchRepository.save(attendance);
        return result;
    }

    public List<Attendance> getAttendances(LocalDate from, LocalDate to, Employee employee) {
        return attendanceExtendedRepository.getAllByAttendanceDateGreaterThanEqualAndAttendanceDateIsLessThanEqualAndEmployeeEqualsOrderByAttendanceDateDesc(from, to, employee);
    }

    public List<Attendance> getAttendances(LocalDate from, LocalDate to) {
        return attendanceExtendedRepository.getAllByAttendanceDateGreaterThanEqualAndAttendanceDateIsLessThanEqualOrderByAttendanceDateDesc(from, to);
    }

    public List<Attendance> getAttendances(Employee employee) {
        return attendanceExtendedRepository.getAllByEmployeeEqualsOrderByAttendanceDateDesc(employee);
    }
}
