package org.soptorshi.service.extended;

import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.mapper.PaymentVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PaymentVoucherExtendedService extends PaymentVoucherService {

    public PaymentVoucherExtendedService(PaymentVoucherRepository paymentVoucherRepository, PaymentVoucherMapper paymentVoucherMapper, PaymentVoucherSearchRepository paymentVoucherSearchRepository) {
        super(paymentVoucherRepository, paymentVoucherMapper, paymentVoucherSearchRepository);
    }

    @Override
    public PaymentVoucherDTO save(PaymentVoucherDTO paymentVoucherDTO) {
        paymentVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        paymentVoucherDTO.setModifiedOn(LocalDate.now());
        return super.save(paymentVoucherDTO);
    }

}
