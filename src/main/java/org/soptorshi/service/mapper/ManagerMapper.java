package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ManagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Manager and its DTO ManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface ManagerMapper extends EntityMapper<ManagerDTO, Manager> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    ManagerDTO toDto(Manager manager);

    @Mapping(source = "employeeId", target = "employee")
    Manager toEntity(ManagerDTO managerDTO);

    default Manager fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manager manager = new Manager();
        manager.setId(id);
        return manager;
    }
}
