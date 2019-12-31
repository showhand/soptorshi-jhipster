package org.soptorshi.repository;

import org.soptorshi.domain.Requisition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Requisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequisitionRepository extends JpaRepository<Requisition, Long>, JpaSpecificationExecutor<Requisition> {

}
