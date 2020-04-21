package org.soptorshi.repository;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrderVoucherRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderVoucherRelationRepository extends JpaRepository<PurchaseOrderVoucherRelation, Long>, JpaSpecificationExecutor<PurchaseOrderVoucherRelation> {

}
