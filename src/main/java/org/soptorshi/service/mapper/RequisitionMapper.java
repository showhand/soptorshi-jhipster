package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.RequisitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Requisition and its DTO RequisitionDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, OfficeMapper.class, ProductCategoryMapper.class, DepartmentMapper.class})
public interface RequisitionMapper extends EntityMapper<RequisitionDTO, Requisition> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "office.name", target = "officeName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    RequisitionDTO toDto(Requisition requisition);

    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "officeId", target = "office")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "departmentId", target = "department")
    Requisition toEntity(RequisitionDTO requisitionDTO);

    default Requisition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Requisition requisition = new Requisition();
        requisition.setId(id);
        return requisition;
    }
}
