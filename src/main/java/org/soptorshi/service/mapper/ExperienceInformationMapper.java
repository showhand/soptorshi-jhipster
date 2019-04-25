package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ExperienceInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExperienceInformation and its DTO ExperienceInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ExperienceInformationMapper extends EntityMapper<ExperienceInformationDTO, ExperienceInformation> {

    @Mapping(source = "employee.id", target = "employeeId")
    ExperienceInformationDTO toDto(ExperienceInformation experienceInformation);

    @Mapping(source = "employeeId", target = "employee")
    ExperienceInformation toEntity(ExperienceInformationDTO experienceInformationDTO);

    default ExperienceInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExperienceInformation experienceInformation = new ExperienceInformation();
        experienceInformation.setId(id);
        return experienceInformation;
    }
}
