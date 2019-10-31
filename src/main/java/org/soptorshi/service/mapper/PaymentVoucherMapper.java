package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PaymentVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentVoucher and its DTO PaymentVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {MstAccountMapper.class})
public interface PaymentVoucherMapper extends EntityMapper<PaymentVoucherDTO, PaymentVoucher> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    PaymentVoucherDTO toDto(PaymentVoucher paymentVoucher);

    @Mapping(source = "accountId", target = "account")
    PaymentVoucher toEntity(PaymentVoucherDTO paymentVoucherDTO);

    default PaymentVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentVoucher paymentVoucher = new PaymentVoucher();
        paymentVoucher.setId(id);
        return paymentVoucher;
    }
}
