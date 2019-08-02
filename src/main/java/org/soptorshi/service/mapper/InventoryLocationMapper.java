package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.InventoryLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InventoryLocation and its DTO InventoryLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryLocationMapper extends EntityMapper<InventoryLocationDTO, InventoryLocation> {



    default InventoryLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventoryLocation inventoryLocation = new InventoryLocation();
        inventoryLocation.setId(id);
        return inventoryLocation;
    }
}
