package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.MonthlySalaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MonthlySalary and its DTO MonthlySalaryDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface MonthlySalaryMapper extends EntityMapper<MonthlySalaryDTO, MonthlySalary> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    MonthlySalaryDTO toDto(MonthlySalary monthlySalary);

    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "employeeId", target = "employee")
    MonthlySalary toEntity(MonthlySalaryDTO monthlySalaryDTO);

    default MonthlySalary fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.setId(id);
        return monthlySalary;
    }
}
