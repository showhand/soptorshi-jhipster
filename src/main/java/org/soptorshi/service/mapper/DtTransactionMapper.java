package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DtTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DtTransaction and its DTO DtTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {MstAccountMapper.class, VoucherMapper.class, CurrencyMapper.class})
public interface DtTransactionMapper extends EntityMapper<DtTransactionDTO, DtTransaction> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "voucher.name", target = "voucherName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.notation", target = "currencyNotation")
    DtTransactionDTO toDto(DtTransaction dtTransaction);

    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "voucherId", target = "voucher")
    @Mapping(source = "currencyId", target = "currency")
    DtTransaction toEntity(DtTransactionDTO dtTransactionDTO);

    default DtTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        DtTransaction dtTransaction = new DtTransaction();
        dtTransaction.setId(id);
        return dtTransaction;
    }
}
