package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.FineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fine and its DTO FineDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface FineMapper extends EntityMapper<FineDTO, Fine> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    FineDTO toDto(Fine fine);

    @Mapping(source = "employeeId", target = "employee")
    Fine toEntity(FineDTO fineDTO);

    default Fine fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fine fine = new Fine();
        fine.setId(id);
        return fine;
    }
}
