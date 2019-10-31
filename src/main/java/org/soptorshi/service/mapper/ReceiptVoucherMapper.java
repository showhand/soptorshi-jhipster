package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ReceiptVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReceiptVoucher and its DTO ReceiptVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {MstAccountMapper.class})
public interface ReceiptVoucherMapper extends EntityMapper<ReceiptVoucherDTO, ReceiptVoucher> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    ReceiptVoucherDTO toDto(ReceiptVoucher receiptVoucher);

    @Mapping(source = "accountId", target = "account")
    ReceiptVoucher toEntity(ReceiptVoucherDTO receiptVoucherDTO);

    default ReceiptVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceiptVoucher receiptVoucher = new ReceiptVoucher();
        receiptVoucher.setId(id);
        return receiptVoucher;
    }
}
