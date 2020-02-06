package org.soptorshi.repository.extended;

import org.soptorshi.domain.JournalVoucher;
import org.soptorshi.repository.JournalVoucherRepository;

import java.util.Optional;

public interface JournalVoucherExtendedRepository extends JournalVoucherRepository {
    Optional<JournalVoucher> getByVoucherNo(String voucherNo);
}
