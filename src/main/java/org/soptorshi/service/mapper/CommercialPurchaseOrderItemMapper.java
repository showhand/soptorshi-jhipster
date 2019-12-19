package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialPurchaseOrderItem and its DTO CommercialPurchaseOrderItemDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialPurchaseOrderItemMapper extends EntityMapper<CommercialPurchaseOrderItemDTO, CommercialPurchaseOrderItem> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialPurchaseOrderItemDTO toDto(CommercialPurchaseOrderItem commercialPurchaseOrderItem);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialPurchaseOrderItem toEntity(CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO);

    default CommercialPurchaseOrderItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPurchaseOrderItem commercialPurchaseOrderItem = new CommercialPurchaseOrderItem();
        commercialPurchaseOrderItem.setId(id);
        return commercialPurchaseOrderItem;
    }
}
