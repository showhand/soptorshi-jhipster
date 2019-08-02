package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.InventorySubLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InventorySubLocation and its DTO InventorySubLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {InventoryLocationMapper.class})
public interface InventorySubLocationMapper extends EntityMapper<InventorySubLocationDTO, InventorySubLocation> {

    @Mapping(source = "inventoryLocation.id", target = "inventoryLocationId")
    @Mapping(source = "inventoryLocation.name", target = "inventoryLocationName")
    InventorySubLocationDTO toDto(InventorySubLocation inventorySubLocation);

    @Mapping(source = "inventoryLocationId", target = "inventoryLocation")
    InventorySubLocation toEntity(InventorySubLocationDTO inventorySubLocationDTO);

    default InventorySubLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        InventorySubLocation inventorySubLocation = new InventorySubLocation();
        inventorySubLocation.setId(id);
        return inventorySubLocation;
    }
}
