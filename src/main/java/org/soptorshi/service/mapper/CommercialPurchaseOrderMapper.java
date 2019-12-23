package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialPurchaseOrder and its DTO CommercialPurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommercialPurchaseOrderMapper extends EntityMapper<CommercialPurchaseOrderDTO, CommercialPurchaseOrder> {



    default CommercialPurchaseOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPurchaseOrder commercialPurchaseOrder = new CommercialPurchaseOrder();
        commercialPurchaseOrder.setId(id);
        return commercialPurchaseOrder;
    }
}
