package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.FamilyInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FamilyInformation and its DTO FamilyInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface FamilyInformationMapper extends EntityMapper<FamilyInformationDTO, FamilyInformation> {

    @Mapping(source = "employee.id", target = "employeeId")
    FamilyInformationDTO toDto(FamilyInformation familyInformation);

    @Mapping(source = "employeeId", target = "employee")
    FamilyInformation toEntity(FamilyInformationDTO familyInformationDTO);

    default FamilyInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        FamilyInformation familyInformation = new FamilyInformation();
        familyInformation.setId(id);
        return familyInformation;
    }
}
