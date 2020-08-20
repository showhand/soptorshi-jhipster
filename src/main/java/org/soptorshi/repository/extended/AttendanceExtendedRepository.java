package org.soptorshi.repository.extended;

import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AttendanceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceExtendedRepository extends AttendanceRepository {

    List<Attendance> getAllByAttendanceDateGreaterThanEqualAndAttendanceDateIsLessThanEqualAndEmployeeEqualsOrderByAttendanceDateDesc(LocalDate from, LocalDate to, Employee employee);

    List<Attendance> getAllByAttendanceDateGreaterThanEqualAndAttendanceDateIsLessThanEqualOrderByAttendanceDateDesc(LocalDate from, LocalDate to);

    List<Attendance> getAllByEmployeeEqualsOrderByAttendanceDateDesc(Employee employee);
}
