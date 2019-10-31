package org.soptorshi.service.extended;

import org.soptorshi.repository.ReceiptVoucherRepository;
import org.soptorshi.repository.search.ReceiptVoucherSearchRepository;
import org.soptorshi.service.ReceiptVoucherService;
import org.soptorshi.service.mapper.ReceiptVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReceiptVoucherExtendedService extends ReceiptVoucherService {
    public ReceiptVoucherExtendedService(ReceiptVoucherRepository receiptVoucherRepository, ReceiptVoucherMapper receiptVoucherMapper, ReceiptVoucherSearchRepository receiptVoucherSearchRepository) {
        super(receiptVoucherRepository, receiptVoucherMapper, receiptVoucherSearchRepository);
    }
}
