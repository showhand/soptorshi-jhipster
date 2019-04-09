package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AcademicInformationAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AcademicInformationAttachment and its DTO AcademicInformationAttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AcademicInformationAttachmentMapper extends EntityMapper<AcademicInformationAttachmentDTO, AcademicInformationAttachment> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeId", target = "employeeEmployeeId")
    AcademicInformationAttachmentDTO toDto(AcademicInformationAttachment academicInformationAttachment);

    @Mapping(source = "employeeId", target = "employee")
    AcademicInformationAttachment toEntity(AcademicInformationAttachmentDTO academicInformationAttachmentDTO);

    default AcademicInformationAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicInformationAttachment academicInformationAttachment = new AcademicInformationAttachment();
        academicInformationAttachment.setId(id);
        return academicInformationAttachment;
    }
}
