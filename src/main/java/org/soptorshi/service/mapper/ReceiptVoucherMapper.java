package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ReceiptVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReceiptVoucher and its DTO ReceiptVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReceiptVoucherMapper extends EntityMapper<ReceiptVoucherDTO, ReceiptVoucher> {



    default ReceiptVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceiptVoucher receiptVoucher = new ReceiptVoucher();
        receiptVoucher.setId(id);
        return receiptVoucher;
    }
}
