package org.soptorshi.repository;

import org.soptorshi.domain.RequisitionMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RequisitionMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitionMessagesRepository extends JpaRepository<RequisitionMessages, Long>, JpaSpecificationExecutor<RequisitionMessages> {

}
