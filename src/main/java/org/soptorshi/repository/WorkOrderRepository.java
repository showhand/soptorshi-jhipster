package org.soptorshi.repository;

import org.soptorshi.domain.WorkOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WorkOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, JpaSpecificationExecutor<WorkOrder> {

}
