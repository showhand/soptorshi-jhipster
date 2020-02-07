package org.soptorshi.repository.extended;

import org.soptorshi.domain.ContraVoucher;
import org.soptorshi.repository.ContraVoucherRepository;

import java.util.Optional;

public interface ContraVoucherExtendedRepository extends ContraVoucherRepository {
    Optional<ContraVoucher> findByVoucherNo(String voucherNo);
}
