package org.soptorshi.repository;

import org.soptorshi.domain.FinancialAccountYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FinancialAccountYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialAccountYearRepository extends JpaRepository<FinancialAccountYear, Long>, JpaSpecificationExecutor<FinancialAccountYear> {

}
