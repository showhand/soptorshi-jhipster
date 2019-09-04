package org.soptorshi.repository.extended;

import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.domain.TermsAndConditions;
import org.soptorshi.repository.TermsAndConditionsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsAndConditionsExtendedRepository extends TermsAndConditionsRepository {
    List<TermsAndConditions> getByPurchaseOrder(PurchaseOrder purchaseOrder);
}
