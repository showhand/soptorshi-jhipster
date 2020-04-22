package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.OverTime;
import org.soptorshi.service.dto.OverTimeDTO;

/**
 * Mapper for the entity OverTime and its DTO OverTimeDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface OverTimeMapper extends EntityMapper<OverTimeDTO, OverTime> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    OverTimeDTO toDto(OverTime overTime);

    @Mapping(source = "employeeId", target = "employee")
    OverTime toEntity(OverTimeDTO overTimeDTO);

    default OverTime fromId(Long id) {
        if (id == null) {
            return null;
        }
        OverTime overTime = new OverTime();
        overTime.setId(id);
        return overTime;
    }
}
