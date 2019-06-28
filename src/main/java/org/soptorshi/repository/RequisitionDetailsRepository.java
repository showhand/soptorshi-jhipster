package org.soptorshi.repository;

import org.soptorshi.domain.RequisitionDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RequisitionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitionDetailsRepository extends JpaRepository<RequisitionDetails, Long>, JpaSpecificationExecutor<RequisitionDetails> {

}
