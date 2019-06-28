package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.QuotationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Quotation and its DTO QuotationDTO.
 */
@Mapper(componentModel = "spring", uses = {RequisitionMapper.class})
public interface QuotationMapper extends EntityMapper<QuotationDTO, Quotation> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    QuotationDTO toDto(Quotation quotation);

    @Mapping(source = "requisitionId", target = "requisition")
    Quotation toEntity(QuotationDTO quotationDTO);

    default Quotation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quotation quotation = new Quotation();
        quotation.setId(id);
        return quotation;
    }
}
