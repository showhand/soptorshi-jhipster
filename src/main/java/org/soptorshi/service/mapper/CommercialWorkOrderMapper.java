package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialWorkOrder and its DTO CommercialWorkOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialWorkOrderMapper extends EntityMapper<CommercialWorkOrderDTO, CommercialWorkOrder> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialWorkOrderDTO toDto(CommercialWorkOrder commercialWorkOrder);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialWorkOrder toEntity(CommercialWorkOrderDTO commercialWorkOrderDTO);

    default CommercialWorkOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialWorkOrder commercialWorkOrder = new CommercialWorkOrder();
        commercialWorkOrder.setId(id);
        return commercialWorkOrder;
    }
}
