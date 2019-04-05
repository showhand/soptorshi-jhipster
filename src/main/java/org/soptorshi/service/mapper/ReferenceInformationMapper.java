package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ReferenceInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReferenceInformation and its DTO ReferenceInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ReferenceInformationMapper extends EntityMapper<ReferenceInformationDTO, ReferenceInformation> {

    @Mapping(source = "employee.id", target = "employeeId")
    ReferenceInformationDTO toDto(ReferenceInformation referenceInformation);

    @Mapping(source = "employeeId", target = "employee")
    ReferenceInformation toEntity(ReferenceInformationDTO referenceInformationDTO);

    default ReferenceInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferenceInformation referenceInformation = new ReferenceInformation();
        referenceInformation.setId(id);
        return referenceInformation;
    }
}
