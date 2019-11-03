package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;

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
