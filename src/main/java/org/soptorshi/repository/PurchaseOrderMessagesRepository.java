package org.soptorshi.repository;

import org.soptorshi.domain.PurchaseOrderMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrderMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderMessagesRepository extends JpaRepository<PurchaseOrderMessages, Long>, JpaSpecificationExecutor<PurchaseOrderMessages> {

}
