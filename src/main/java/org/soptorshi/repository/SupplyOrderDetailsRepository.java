package org.soptorshi.repository;

import org.soptorshi.domain.SupplyOrderDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyOrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyOrderDetailsRepository extends JpaRepository<SupplyOrderDetails, Long>, JpaSpecificationExecutor<SupplyOrderDetails> {

}
