package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.HolidayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Holiday and its DTO HolidayDTO.
 */
@Mapper(componentModel = "spring", uses = {HolidayTypeMapper.class})
public interface HolidayMapper extends EntityMapper<HolidayDTO, Holiday> {

    @Mapping(source = "holidayType.id", target = "holidayTypeId")
    @Mapping(source = "holidayType.name", target = "holidayTypeName")
    HolidayDTO toDto(Holiday holiday);

    @Mapping(source = "holidayTypeId", target = "holidayType")
    Holiday toEntity(HolidayDTO holidayDTO);

    default Holiday fromId(Long id) {
        if (id == null) {
            return null;
        }
        Holiday holiday = new Holiday();
        holiday.setId(id);
        return holiday;
    }
}
