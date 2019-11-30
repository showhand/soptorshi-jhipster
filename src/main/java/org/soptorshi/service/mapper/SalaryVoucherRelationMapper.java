package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalaryVoucherRelation and its DTO SalaryVoucherRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {OfficeMapper.class})
public interface SalaryVoucherRelationMapper extends EntityMapper<SalaryVoucherRelationDTO, SalaryVoucherRelation> {

    @Mapping(source = "office.id", target = "officeId")
    @Mapping(source = "office.name", target = "officeName")
    SalaryVoucherRelationDTO toDto(SalaryVoucherRelation salaryVoucherRelation);

    @Mapping(source = "officeId", target = "office")
    SalaryVoucherRelation toEntity(SalaryVoucherRelationDTO salaryVoucherRelationDTO);

    default SalaryVoucherRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalaryVoucherRelation salaryVoucherRelation = new SalaryVoucherRelation();
        salaryVoucherRelation.setId(id);
        return salaryVoucherRelation;
    }
}
