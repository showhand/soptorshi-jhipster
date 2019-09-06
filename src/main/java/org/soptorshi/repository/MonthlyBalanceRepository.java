package org.soptorshi.repository;

import org.soptorshi.domain.MonthlyBalance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MonthlyBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyBalanceRepository extends JpaRepository<MonthlyBalance, Long>, JpaSpecificationExecutor<MonthlyBalance> {

}
