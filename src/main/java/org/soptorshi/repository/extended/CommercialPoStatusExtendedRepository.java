package org.soptorshi.repository.extended;

import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.enumeration.CommercialStatus;
import org.soptorshi.repository.CommercialPoStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialPoStatusExtendedRepository extends CommercialPoStatusRepository {

    boolean existsByCommercialPurchaseOrderAndStatus(CommercialPurchaseOrder commercialPurchaseOrder, CommercialStatus status);
}
