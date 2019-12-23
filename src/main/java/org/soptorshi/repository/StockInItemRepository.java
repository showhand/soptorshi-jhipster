package org.soptorshi.repository;

import org.soptorshi.domain.StockInItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockInItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockInItemRepository extends JpaRepository<StockInItem, Long>, JpaSpecificationExecutor<StockInItem> {

}
