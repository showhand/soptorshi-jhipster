package org.soptorshi.repository.extended;

import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveApplicationExtendedRepository extends LeaveApplicationRepository {

    List<LeaveApplication> findByEmployeeIdAndLeaveTypesAndStatusAndFromDateGreaterThanAndToDateLessThan(String employeeId, LeaveType leaveTypes, LeaveStatus status, LocalDate fromDate, LocalDate toDate);
}
