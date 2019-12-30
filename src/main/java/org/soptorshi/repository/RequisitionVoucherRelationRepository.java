package org.soptorshi.repository;

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RequisitionVoucherRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitionVoucherRelationRepository extends JpaRepository<RequisitionVoucherRelation, Long>, JpaSpecificationExecutor<RequisitionVoucherRelation> {

}
