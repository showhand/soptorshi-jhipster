package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialPoStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialPoStatus and its DTO CommercialPoStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialPoStatusMapper extends EntityMapper<CommercialPoStatusDTO, CommercialPoStatus> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialPoStatusDTO toDto(CommercialPoStatus commercialPoStatus);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialPoStatus toEntity(CommercialPoStatusDTO commercialPoStatusDTO);

    default CommercialPoStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPoStatus commercialPoStatus = new CommercialPoStatus();
        commercialPoStatus.setId(id);
        return commercialPoStatus;
    }
}
