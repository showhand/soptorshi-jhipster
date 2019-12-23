package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialPaymentInfo and its DTO CommercialPaymentInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialPaymentInfoMapper extends EntityMapper<CommercialPaymentInfoDTO, CommercialPaymentInfo> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialPaymentInfoDTO toDto(CommercialPaymentInfo commercialPaymentInfo);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialPaymentInfo toEntity(CommercialPaymentInfoDTO commercialPaymentInfoDTO);

    default CommercialPaymentInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPaymentInfo commercialPaymentInfo = new CommercialPaymentInfo();
        commercialPaymentInfo.setId(id);
        return commercialPaymentInfo;
    }
}
