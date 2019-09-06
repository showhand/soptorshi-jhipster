package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PredefinedNarrationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PredefinedNarration and its DTO PredefinedNarrationDTO.
 */
@Mapper(componentModel = "spring", uses = {VoucherMapper.class})
public interface PredefinedNarrationMapper extends EntityMapper<PredefinedNarrationDTO, PredefinedNarration> {

    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "voucher.name", target = "voucherName")
    PredefinedNarrationDTO toDto(PredefinedNarration predefinedNarration);

    @Mapping(source = "voucherId", target = "voucher")
    PredefinedNarration toEntity(PredefinedNarrationDTO predefinedNarrationDTO);

    default PredefinedNarration fromId(Long id) {
        if (id == null) {
            return null;
        }
        PredefinedNarration predefinedNarration = new PredefinedNarration();
        predefinedNarration.setId(id);
        return predefinedNarration;
    }
}
