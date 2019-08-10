package org.soptorshi.repository;

import org.soptorshi.domain.StockInProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockInProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockInProcessRepository extends JpaRepository<StockInProcess, Long>, JpaSpecificationExecutor<StockInProcess> {

}
