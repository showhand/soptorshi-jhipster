package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ContraVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContraVoucher and its DTO ContraVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ContraVoucherMapper extends EntityMapper<ContraVoucherDTO, ContraVoucher> {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.notation", target = "currencyNotation")
    ContraVoucherDTO toDto(ContraVoucher contraVoucher);

    @Mapping(source = "currencyId", target = "currency")
    ContraVoucher toEntity(ContraVoucherDTO contraVoucherDTO);

    default ContraVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContraVoucher contraVoucher = new ContraVoucher();
        contraVoucher.setId(id);
        return contraVoucher;
    }
}
