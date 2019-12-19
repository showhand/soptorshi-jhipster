package org.soptorshi.repository;

import org.soptorshi.domain.CommercialWorkOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialWorkOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialWorkOrderRepository extends JpaRepository<CommercialWorkOrder, Long>, JpaSpecificationExecutor<CommercialWorkOrder> {

}
