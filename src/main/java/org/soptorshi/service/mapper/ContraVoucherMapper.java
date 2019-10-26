package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ContraVoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContraVoucher and its DTO ContraVoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContraVoucherMapper extends EntityMapper<ContraVoucherDTO, ContraVoucher> {



    default ContraVoucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContraVoucher contraVoucher = new ContraVoucher();
        contraVoucher.setId(id);
        return contraVoucher;
    }
}
