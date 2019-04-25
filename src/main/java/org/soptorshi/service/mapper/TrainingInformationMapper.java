package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.TrainingInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrainingInformation and its DTO TrainingInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TrainingInformationMapper extends EntityMapper<TrainingInformationDTO, TrainingInformation> {

    @Mapping(source = "employee.id", target = "employeeId")
    TrainingInformationDTO toDto(TrainingInformation trainingInformation);

    @Mapping(source = "employeeId", target = "employee")
    TrainingInformation toEntity(TrainingInformationDTO trainingInformationDTO);

    default TrainingInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrainingInformation trainingInformation = new TrainingInformation();
        trainingInformation.setId(id);
        return trainingInformation;
    }
}
