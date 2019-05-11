package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, OfficeMapper.class, DesignationMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "designation.id", target = "designationId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "designationId", target = "designation")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
