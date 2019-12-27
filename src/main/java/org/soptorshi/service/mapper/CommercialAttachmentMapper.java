package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.service.dto.CommercialAttachmentDTO;

/**
 * Mapper for the entity CommercialAttachment and its DTO CommercialAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPoMapper.class})
public interface CommercialAttachmentMapper extends EntityMapper<CommercialAttachmentDTO, CommercialAttachment> {

    @Mapping(source = "commercialPo.id", target = "commercialPoId")
    @Mapping(source = "commercialPo.purchaseOrderNo", target = "commercialPoPurchaseOrderNo")
    CommercialAttachmentDTO toDto(CommercialAttachment commercialAttachment);

    @Mapping(source = "commercialPoId", target = "commercialPo")
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
