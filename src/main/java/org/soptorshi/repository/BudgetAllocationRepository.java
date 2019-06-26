package org.soptorshi.repository;

import org.soptorshi.domain.BudgetAllocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BudgetAllocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long>, JpaSpecificationExecutor<BudgetAllocation> {

}
