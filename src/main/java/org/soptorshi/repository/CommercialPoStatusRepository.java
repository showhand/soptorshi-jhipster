package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPoStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPoStatusRepository extends JpaRepository<CommercialPoStatus, Long>, JpaSpecificationExecutor<CommercialPoStatus> {

    CommercialPoStatus findTopByCommercialPurchaseOrderOrderByCreateOnDesc(CommercialPurchaseOrder commercialPurchaseOrder);
}
