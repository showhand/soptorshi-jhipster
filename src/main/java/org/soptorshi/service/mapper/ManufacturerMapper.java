package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ManufacturerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Manufacturer and its DTO ManufacturerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ManufacturerMapper extends EntityMapper<ManufacturerDTO, Manufacturer> {



    default Manufacturer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        return manufacturer;
    }
}
