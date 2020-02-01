package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.soptorshi.domain.Weekend;
import org.soptorshi.service.dto.WeekendDTO;

/**
 * Mapper for the entity Weekend and its DTO WeekendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WeekendMapper extends EntityMapper<WeekendDTO, Weekend> {



    default Weekend fromId(Long id) {
        if (id == null) {
            return null;
        }
        Weekend weekend = new Weekend();
        weekend.setId(id);
        return weekend;
    }
}
