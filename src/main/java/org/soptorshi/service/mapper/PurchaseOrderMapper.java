package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PurchaseOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrder and its DTO PurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {RequisitionMapper.class, QuotationMapper.class})
public interface PurchaseOrderMapper extends EntityMapper<PurchaseOrderDTO, PurchaseOrder> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    @Mapping(source = "quotation.id", target = "quotationId")
    @Mapping(source = "quotation.quotationNo", target = "quotationQuotationNo")
    PurchaseOrderDTO toDto(PurchaseOrder purchaseOrder);

    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "quotationId", target = "quotation")
    PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDTO);

    default PurchaseOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
    }
}
