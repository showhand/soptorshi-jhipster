package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.QuotationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Quotation and its DTO QuotationDTO.
 */
@Mapper(componentModel = "spring", uses = {RequisitionMapper.class, VendorMapper.class})
public interface QuotationMapper extends EntityMapper<QuotationDTO, Quotation> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.companyName", target = "vendorCompanyName")
    QuotationDTO toDto(Quotation quotation);

    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "vendorId", target = "vendor")
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
