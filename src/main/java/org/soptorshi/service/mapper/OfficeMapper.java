package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.OfficeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Office and its DTO OfficeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OfficeMapper extends EntityMapper<OfficeDTO, Office> {



    default Office fromId(Long id) {
        if (id == null) {
            return null;
        }
        Office office = new Office();
        office.setId(id);
        return office;
    }
}
