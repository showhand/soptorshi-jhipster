package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DesignationWiseAllowanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DesignationWiseAllowance and its DTO DesignationWiseAllowanceDTO.
 */
@Mapper(componentModel = "spring", uses = {DesignationMapper.class})
public interface DesignationWiseAllowanceMapper extends EntityMapper<DesignationWiseAllowanceDTO, DesignationWiseAllowance> {

    @Mapping(source = "designation.id", target = "designationId")
    DesignationWiseAllowanceDTO toDto(DesignationWiseAllowance designationWiseAllowance);

    @Mapping(source = "designationId", target = "designation")
    DesignationWiseAllowance toEntity(DesignationWiseAllowanceDTO designationWiseAllowanceDTO);

    default DesignationWiseAllowance fromId(Long id) {
        if (id == null) {
            return null;
        }
        DesignationWiseAllowance designationWiseAllowance = new DesignationWiseAllowance();
        designationWiseAllowance.setId(id);
        return designationWiseAllowance;
    }
}
