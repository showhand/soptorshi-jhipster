package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.RequisitionDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RequisitionDetails and its DTO RequisitionDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, RequisitionMapper.class, ProductMapper.class})
public interface RequisitionDetailsMapper extends EntityMapper<RequisitionDetailsDTO, RequisitionDetails> {

    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    RequisitionDetailsDTO toDto(RequisitionDetails requisitionDetails);

    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "productId", target = "product")
    RequisitionDetails toEntity(RequisitionDetailsDTO requisitionDetailsDTO);

    default RequisitionDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequisitionDetails requisitionDetails = new RequisitionDetails();
        requisitionDetails.setId(id);
        return requisitionDetails;
    }
}
