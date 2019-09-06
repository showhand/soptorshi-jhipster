package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SystemGroupMapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemGroupMap and its DTO SystemGroupMapDTO.
 */
@Mapper(componentModel = "spring", uses = {MstGroupMapper.class})
public interface SystemGroupMapMapper extends EntityMapper<SystemGroupMapDTO, SystemGroupMap> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    SystemGroupMapDTO toDto(SystemGroupMap systemGroupMap);

    @Mapping(source = "groupId", target = "group")
    SystemGroupMap toEntity(SystemGroupMapDTO systemGroupMapDTO);

    default SystemGroupMap fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemGroupMap systemGroupMap = new SystemGroupMap();
        systemGroupMap.setId(id);
        return systemGroupMap;
    }
}
