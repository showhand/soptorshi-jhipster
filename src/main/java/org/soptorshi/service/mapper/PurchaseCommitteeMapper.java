package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PurchaseCommitteeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseCommittee and its DTO PurchaseCommitteeDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface PurchaseCommitteeMapper extends EntityMapper<PurchaseCommitteeDTO, PurchaseCommittee> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    PurchaseCommitteeDTO toDto(PurchaseCommittee purchaseCommittee);

    @Mapping(source = "employeeId", target = "employee")
    PurchaseCommittee toEntity(PurchaseCommitteeDTO purchaseCommitteeDTO);

    default PurchaseCommittee fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseCommittee purchaseCommittee = new PurchaseCommittee();
        purchaseCommittee.setId(id);
        return purchaseCommittee;
    }
}
