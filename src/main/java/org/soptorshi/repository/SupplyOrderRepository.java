package org.soptorshi.repository;

import org.soptorshi.domain.SupplyOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long>, JpaSpecificationExecutor<SupplyOrder> {

}
