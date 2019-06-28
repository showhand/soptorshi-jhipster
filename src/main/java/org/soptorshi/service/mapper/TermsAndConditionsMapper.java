package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.TermsAndConditionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TermsAndConditions and its DTO TermsAndConditionsDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkOrderMapper.class})
public interface TermsAndConditionsMapper extends EntityMapper<TermsAndConditionsDTO, TermsAndConditions> {

    @Mapping(source = "workOrder.id", target = "workOrderId")
    TermsAndConditionsDTO toDto(TermsAndConditions termsAndConditions);

    @Mapping(source = "workOrderId", target = "workOrder")
    TermsAndConditions toEntity(TermsAndConditionsDTO termsAndConditionsDTO);

    default TermsAndConditions fromId(Long id) {
        if (id == null) {
            return null;
        }
        TermsAndConditions termsAndConditions = new TermsAndConditions();
        termsAndConditions.setId(id);
        return termsAndConditions;
    }
}
