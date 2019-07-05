package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.BudgetAllocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BudgetAllocation and its DTO BudgetAllocationDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class, DepartmentMapper.class, FinancialAccountYearMapper.class})
public interface BudgetAllocationMapper extends EntityMapper<BudgetAllocationDTO, BudgetAllocation> {

    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "office.name", target = "officeName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    BudgetAllocationDTO toDto(BudgetAllocation budgetAllocation);

    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    BudgetAllocation toEntity(BudgetAllocationDTO budgetAllocationDTO);

    default BudgetAllocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        BudgetAllocation budgetAllocation = new BudgetAllocation();
        budgetAllocation.setId(id);
        return budgetAllocation;
    }
}
