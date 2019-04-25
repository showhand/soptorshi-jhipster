package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AcademicInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AcademicInformation and its DTO AcademicInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AcademicInformationMapper extends EntityMapper<AcademicInformationDTO, AcademicInformation> {

    @Mapping(source = "employee.id", target = "employeeId")
    AcademicInformationDTO toDto(AcademicInformation academicInformation);

    @Mapping(source = "employeeId", target = "employee")
    AcademicInformation toEntity(AcademicInformationDTO academicInformationDTO);

    default AcademicInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicInformation academicInformation = new AcademicInformation();
        academicInformation.setId(id);
        return academicInformation;
    }
}
