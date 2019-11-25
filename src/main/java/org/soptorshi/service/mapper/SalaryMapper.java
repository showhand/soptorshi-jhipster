package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SalaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Salary and its DTO SalaryDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface SalaryMapper extends EntityMapper<SalaryDTO, Salary> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    SalaryDTO toDto(Salary salary);

    @Mapping(source = "employeeId", target = "employee")
    Salary toEntity(SalaryDTO salaryDTO);

    default Salary fromId(Long id) {
        if (id == null) {
            return null;
        }
        Salary salary = new Salary();
        salary.setId(id);
        return salary;
    }
}
