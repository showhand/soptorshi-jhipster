package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.service.dto.CommercialAttachmentDTO;

/**
 * Mapper for the entity CommercialAttachment and its DTO CommercialAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPiMapper.class})
public interface CommercialAttachmentMapper extends EntityMapper<CommercialAttachmentDTO, CommercialAttachment> {

    @Mapping(source = "commercialPi.id", target = "commercialPiId")
    @Mapping(source = "commercialPi.proformaNo", target = "commercialPiProformaNo")
    CommercialAttachmentDTO toDto(CommercialAttachment commercialAttachment);

    @Mapping(source = "commercialPiId", target = "commercialPi")
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
