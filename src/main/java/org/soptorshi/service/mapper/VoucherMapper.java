package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.VoucherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Voucher and its DTO VoucherDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VoucherMapper extends EntityMapper<VoucherDTO, Voucher> {



    default Voucher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Voucher voucher = new Voucher();
        voucher.setId(id);
        return voucher;
    }
}
