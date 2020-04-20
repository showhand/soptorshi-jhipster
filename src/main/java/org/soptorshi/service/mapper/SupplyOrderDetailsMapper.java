package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;

/**
 * Mapper for the entity SupplyOrderDetails and its DTO SupplyOrderDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyOrderMapper.class, ProductCategoryMapper.class, ProductMapper.class})
public interface SupplyOrderDetailsMapper extends EntityMapper<SupplyOrderDetailsDTO, SupplyOrderDetails> {

    @Mapping(source = "supplyOrder.id", target = "supplyOrderId")
    @Mapping(source = "supplyOrder.orderNo", target = "supplyOrderOrderNo")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    SupplyOrderDetailsDTO toDto(SupplyOrderDetails supplyOrderDetails);

    @Mapping(source = "supplyOrderId", target = "supplyOrder")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productId", target = "product")
    SupplyOrderDetails toEntity(SupplyOrderDetailsDTO supplyOrderDetailsDTO);

    default SupplyOrderDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyOrderDetails supplyOrderDetails = new SupplyOrderDetails();
        supplyOrderDetails.setId(id);
        return supplyOrderDetails;
    }
}
