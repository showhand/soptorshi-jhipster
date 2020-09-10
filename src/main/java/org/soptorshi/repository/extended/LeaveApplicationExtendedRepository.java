package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveApplicationExtendedRepository extends LeaveApplicationRepository {

    List<LeaveApplication> findByEmployeesAndLeaveTypesAndStatusAndFromDateGreaterThanAndToDateLessThan(Employee employee, LeaveType leaveTypes, LeaveStatus status, LocalDate fromDate, LocalDate toDate);

    List<LeaveApplication> getByEmployees(Employee employee);

    List<LeaveApplication> getAllByFromDateGreaterThanEqualAndToDateLessThanEqualAndEmployeesEquals(LocalDate from, LocalDate to, Employee employee);

    List<LeaveApplication> getAllByFromDateGreaterThanEqualAndToDateLessThanEqual(LocalDate from, LocalDate to);
}
