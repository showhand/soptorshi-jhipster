package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PaymentVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentVoucher and its DTO PaymentVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentVoucherMapper extends EntityMapper<PaymentVoucherDTO, PaymentVoucher> {



    default PaymentVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentVoucher paymentVoucher = new PaymentVoucher();
        paymentVoucher.setId(id);
        return paymentVoucher;
    }
}
