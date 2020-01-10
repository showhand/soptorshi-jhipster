package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.extended.AttendanceExtendedRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.AttendanceService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Employee> employee = employeeRepository.findByEmployeeId(attendanceDTO.getEmployeeId());
        if(employee.isPresent()) {
            Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
            attendance = attendanceExtendedRepository.save(attendance);
            AttendanceDTO result = attendanceMapper.toDto(attendance);
            attendanceSearchRepository.save(attendance);
            return result;
        }
        return null;
    }


    public List<AttendanceDTO> getAllByDistinctAttendanceDate() {
        log.debug("Request to get all Distinct Attendances Date");
        LocalDate localDate = LocalDate.now();
        List<Attendance> attendances = attendanceExtendedRepository.getDistinctByAttendanceDateLessThanEqual(localDate);
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for(Attendance attendance: attendances) {
            AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
            attendanceDTOS.add(attendanceDTO);
        }
        return attendanceDTOS;
    }

    public void deleteByAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload) {
        log.debug("Request to delete Attendance : {}", attendanceExcelUpload);
        List<Attendance> attendances = attendanceExtendedRepository.getByAttendanceExcelUpload(attendanceExcelUpload);
        for(Attendance attendance: attendances) {
            attendanceExtendedRepository.deleteById(attendance.getId());
            attendanceSearchRepository.deleteById(attendance.getId());
        }
    }
}
