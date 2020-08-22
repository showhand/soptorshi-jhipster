package org.soptorshi.mediator;

import org.soptorshi.service.extended.JournalVoucherExtendedService;
import org.springframework.stereotype.Service;

@Service
public class VoucherTransactionService {
    private final JournalVoucherExtendedService journalVoucherExtendedService;


    public VoucherTransactionService(JournalVoucherExtendedService journalVoucherExtendedService) {
        this.journalVoucherExtendedService = journalVoucherExtendedService;
    }
}
