package org.soptorshi.repository.extended;

import org.soptorshi.domain.ReceiptVoucher;
import org.soptorshi.repository.ReceiptVoucherRepository;

import java.util.Optional;

public interface ReceiptVoucherExtendedRepository extends ReceiptVoucherRepository {
    Optional<ReceiptVoucher> findByVoucherNo(String voucherNo);
}
