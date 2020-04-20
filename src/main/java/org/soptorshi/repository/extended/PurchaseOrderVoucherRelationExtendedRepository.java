package org.soptorshi.repository.extended;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;

public interface PurchaseOrderVoucherRelationExtendedRepository extends PurchaseOrderVoucherRelationRepository {
    Boolean existsByVoucherNo(String voucherNo);

    PurchaseOrderVoucherRelation findByVoucherNo(String voucherNo);
}
