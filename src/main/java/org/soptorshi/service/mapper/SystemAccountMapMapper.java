package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SystemAccountMapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemAccountMap and its DTO SystemAccountMapDTO.
 */
@Mapper(componentModel = "spring", uses = {MstAccountMapper.class})
public interface SystemAccountMapMapper extends EntityMapper<SystemAccountMapDTO, SystemAccountMap> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    SystemAccountMapDTO toDto(SystemAccountMap systemAccountMap);

    @Mapping(source = "accountId", target = "account")
    SystemAccountMap toEntity(SystemAccountMapDTO systemAccountMapDTO);

    default SystemAccountMap fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemAccountMap systemAccountMap = new SystemAccountMap();
        systemAccountMap.setId(id);
        return systemAccountMap;
    }
}
