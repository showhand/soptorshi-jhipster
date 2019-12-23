package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPurchaseOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPurchaseOrderRepository extends JpaRepository<CommercialPurchaseOrder, Long>, JpaSpecificationExecutor<CommercialPurchaseOrder> {

}
