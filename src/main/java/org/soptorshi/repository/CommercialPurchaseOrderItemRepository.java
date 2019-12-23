package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPurchaseOrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPurchaseOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPurchaseOrderItemRepository extends JpaRepository<CommercialPurchaseOrderItem, Long>, JpaSpecificationExecutor<CommercialPurchaseOrderItem> {

}
