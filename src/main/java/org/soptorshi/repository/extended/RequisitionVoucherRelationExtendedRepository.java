package org.soptorshi.repository.extended;

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.repository.RequisitionVoucherRelationRepository;

public interface RequisitionVoucherRelationExtendedRepository extends RequisitionVoucherRelationRepository {
    boolean existsByVoucherNo(String voucherNo);

    RequisitionVoucherRelation findByVoucherNo(String voucherNo);
}
