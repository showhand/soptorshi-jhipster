package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {AcademicInformationMapper.class, TrainingInformationMapper.class, ExperienceInformationMapper.class})
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {

    @Mapping(source = "academicInformation.id", target = "academicInformationId")
    @Mapping(source = "trainingInformation.id", target = "trainingInformationId")
    @Mapping(source = "experienceInformation.id", target = "experienceInformationId")
    AttachmentDTO toDto(Attachment attachment);

    @Mapping(source = "academicInformationId", target = "academicInformation")
    @Mapping(source = "trainingInformationId", target = "trainingInformation")
    @Mapping(source = "experienceInformationId", target = "experienceInformation")
    Attachment toEntity(AttachmentDTO attachmentDTO);

    default Attachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setId(id);
        return attachment;
    }
}
