package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.service.dto.CommercialAttachmentDTO;

/**
 * Mapper for the entity CommercialAttachment and its DTO CommercialAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class, CommercialPoStatusMapper.class})
public interface CommercialAttachmentMapper extends EntityMapper<CommercialAttachmentDTO, CommercialAttachment> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    @Mapping(source = "commercialPoStatus.id", target = "commercialPoStatusId")
    @Mapping(source = "commercialPoStatus.status", target = "commercialPoStatusStatus")
    CommercialAttachmentDTO toDto(CommercialAttachment commercialAttachment);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    @Mapping(source = "commercialPoStatusId", target = "commercialPoStatus")
    CommercialAttachment toEntity(CommercialAttachmentDTO commercialAttachmentDTO);

    default CommercialAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialAttachment commercialAttachment = new CommercialAttachment();
        commercialAttachment.setId(id);
        return commercialAttachment;
    }
}
