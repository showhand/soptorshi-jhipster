package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.TrainingInformationAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrainingInformationAttachment and its DTO TrainingInformationAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TrainingInformationAttachmentMapper extends EntityMapper<TrainingInformationAttachmentDTO, TrainingInformationAttachment> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeId", target = "employeeEmployeeId")
    TrainingInformationAttachmentDTO toDto(TrainingInformationAttachment trainingInformationAttachment);

    @Mapping(source = "employeeId", target = "employee")
    TrainingInformationAttachment toEntity(TrainingInformationAttachmentDTO trainingInformationAttachmentDTO);

    default TrainingInformationAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrainingInformationAttachment trainingInformationAttachment = new TrainingInformationAttachment();
        trainingInformationAttachment.setId(id);
        return trainingInformationAttachment;
    }
}
