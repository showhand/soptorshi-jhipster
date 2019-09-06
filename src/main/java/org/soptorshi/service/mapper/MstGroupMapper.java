package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.MstGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MstGroup and its DTO MstGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MstGroupMapper extends EntityMapper<MstGroupDTO, MstGroup> {



    default MstGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        MstGroup mstGroup = new MstGroup();
        mstGroup.setId(id);
        return mstGroup;
    }
}
