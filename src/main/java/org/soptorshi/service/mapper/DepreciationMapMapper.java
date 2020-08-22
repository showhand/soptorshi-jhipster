package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DepreciationMapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepreciationMap and its DTO DepreciationMapDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepreciationMapMapper extends EntityMapper<DepreciationMapDTO, DepreciationMap> {



    default DepreciationMap fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepreciationMap depreciationMap = new DepreciationMap();
        depreciationMap.setId(id);
        return depreciationMap;
    }
}
