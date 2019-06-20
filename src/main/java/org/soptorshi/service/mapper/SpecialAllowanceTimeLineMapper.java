package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SpecialAllowanceTimeLine and its DTO SpecialAllowanceTimeLineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialAllowanceTimeLineMapper extends EntityMapper<SpecialAllowanceTimeLineDTO, SpecialAllowanceTimeLine> {



    default SpecialAllowanceTimeLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        SpecialAllowanceTimeLine specialAllowanceTimeLine = new SpecialAllowanceTimeLine();
        specialAllowanceTimeLine.setId(id);
        return specialAllowanceTimeLine;
    }
}
