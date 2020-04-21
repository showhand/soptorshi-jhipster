package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrderVoucherRelation and its DTO PurchaseOrderVoucherRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {VoucherMapper.class, PurchaseOrderMapper.class})
public interface PurchaseOrderVoucherRelationMapper extends EntityMapper<PurchaseOrderVoucherRelationDTO, PurchaseOrderVoucherRelation> {

    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "voucher.name", target = "voucherName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "purchaseOrder.purchaseOrderNo", target = "purchaseOrderPurchaseOrderNo")
    PurchaseOrderVoucherRelationDTO toDto(PurchaseOrderVoucherRelation purchaseOrderVoucherRelation);

    @Mapping(source = "voucherId", target = "voucher")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderVoucherRelation toEntity(PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO);

    default PurchaseOrderVoucherRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderVoucherRelation purchaseOrderVoucherRelation = new PurchaseOrderVoucherRelation();
        purchaseOrderVoucherRelation.setId(id);
        return purchaseOrderVoucherRelation;
    }
}
