package org.soptorshi.repository.extended;

import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialPoStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialPoStatusExtendedRepository extends CommercialPoStatusRepository {

    CommercialPoStatus findTopByCommercialPurchaseOrderOrderByCreateOnDesc(CommercialPurchaseOrder commercialPurchaseOrder);
}
