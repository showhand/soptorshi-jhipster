package org.soptorshi.repository;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.ProvidentFund;
import org.soptorshi.domain.enumeration.ProvidentFundStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProvidentFund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvidentFundRepository extends JpaRepository<ProvidentFund, Long>, JpaSpecificationExecutor<ProvidentFund> {
    ProvidentFund getByEmployeeAndStatus(Employee employee, ProvidentFundStatus providentFundStatus);
}
