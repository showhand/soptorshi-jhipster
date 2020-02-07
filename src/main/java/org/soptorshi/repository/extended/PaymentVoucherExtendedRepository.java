package org.soptorshi.repository.extended;

import org.soptorshi.domain.PaymentVoucher;
import org.soptorshi.repository.PaymentVoucherRepository;

import java.util.Optional;

public interface PaymentVoucherExtendedRepository extends PaymentVoucherRepository {
    Optional<PaymentVoucher> getByVoucherNo(String voucherNo);
}
