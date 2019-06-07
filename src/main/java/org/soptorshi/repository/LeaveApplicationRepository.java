package org.soptorshi.repository;

import org.soptorshi.domain.LeaveApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {

}
