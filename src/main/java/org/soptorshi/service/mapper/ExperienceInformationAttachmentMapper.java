package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ExperienceInformationAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExperienceInformationAttachment and its DTO ExperienceInformationAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ExperienceInformationAttachmentMapper extends EntityMapper<ExperienceInformationAttachmentDTO, ExperienceInformationAttachment> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeId", target = "employeeEmployeeId")
    ExperienceInformationAttachmentDTO toDto(ExperienceInformationAttachment experienceInformationAttachment);

    @Mapping(source = "employeeId", target = "employee")
    ExperienceInformationAttachment toEntity(ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO);

    default ExperienceInformationAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExperienceInformationAttachment experienceInformationAttachment = new ExperienceInformationAttachment();
        experienceInformationAttachment.setId(id);
        return experienceInformationAttachment;
    }
}
