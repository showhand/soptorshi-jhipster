package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.JournalVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JournalVoucher and its DTO JournalVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JournalVoucherMapper extends EntityMapper<JournalVoucherDTO, JournalVoucher> {



    default JournalVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        JournalVoucher journalVoucher = new JournalVoucher();
        journalVoucher.setId(id);
        return journalVoucher;
    }
}
