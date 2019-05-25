package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.HolidayTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HolidayType and its DTO HolidayTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HolidayTypeMapper extends EntityMapper<HolidayTypeDTO, HolidayType> {



    default HolidayType fromId(Long id) {
        if (id == null) {
            return null;
        }
        HolidayType holidayType = new HolidayType();
        holidayType.setId(id);
        return holidayType;
    }
}
