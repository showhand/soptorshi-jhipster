package org.soptorshi.repository;

import org.soptorshi.domain.StockOutItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockOutItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockOutItemRepository extends JpaRepository<StockOutItem, Long>, JpaSpecificationExecutor<StockOutItem> {

}
