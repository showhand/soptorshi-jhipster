package org.soptorshi.repository;

import org.soptorshi.domain.PurchaseCommittee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseCommittee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseCommitteeRepository extends JpaRepository<PurchaseCommittee, Long>, JpaSpecificationExecutor<PurchaseCommittee> {

}
