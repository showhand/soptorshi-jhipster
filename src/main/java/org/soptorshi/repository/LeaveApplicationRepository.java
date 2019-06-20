package org.soptorshi.repository;

import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the LeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {

    List<LeaveApplication> findByEmployeeIdAndLeaveTypesAndStatus(String employeeId, LeaveType leaveTypes,LeaveStatus status);
}
