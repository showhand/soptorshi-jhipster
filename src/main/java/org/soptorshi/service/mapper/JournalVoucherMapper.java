package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.JournalVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JournalVoucher and its DTO JournalVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface JournalVoucherMapper extends EntityMapper<JournalVoucherDTO, JournalVoucher> {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.notation", target = "currencyNotation")
    JournalVoucherDTO toDto(JournalVoucher journalVoucher);

    @Mapping(source = "currencyId", target = "currency")
    JournalVoucher toEntity(JournalVoucherDTO journalVoucherDTO);

    default JournalVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        JournalVoucher journalVoucher = new JournalVoucher();
        journalVoucher.setId(id);
        return journalVoucher;
    }
}
