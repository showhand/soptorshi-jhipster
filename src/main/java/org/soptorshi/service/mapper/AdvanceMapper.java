package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AdvanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Advance and its DTO AdvanceDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AdvanceMapper extends EntityMapper<AdvanceDTO, Advance> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    AdvanceDTO toDto(Advance advance);

    @Mapping(source = "employeeId", target = "employee")
    Advance toEntity(AdvanceDTO advanceDTO);

    default Advance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Advance advance = new Advance();
        advance.setId(id);
        return advance;
    }
}
