package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.QuotationDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuotationDetails and its DTO QuotationDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {QuotationMapper.class, RequisitionDetailsMapper.class, ProductMapper.class})
public interface QuotationDetailsMapper extends EntityMapper<QuotationDetailsDTO, QuotationDetails> {

    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quotation.quotationNo", target = "quotationQuotationNo")
    @Mapping(source = "requisitionDetails.id", target = "requisitionDetailsId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    QuotationDetailsDTO toDto(QuotationDetails quotationDetails);

    @Mapping(source = "quotationId", target = "quotation")
    @Mapping(source = "requisitionDetailsId", target = "requisitionDetails")
    @Mapping(source = "productId", target = "product")
    QuotationDetails toEntity(QuotationDetailsDTO quotationDetailsDTO);

    default QuotationDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotationDetails quotationDetails = new QuotationDetails();
        quotationDetails.setId(id);
        return quotationDetails;
    }
}
