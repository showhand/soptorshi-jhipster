package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.InventorySubLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InventorySubLocation and its DTO InventorySubLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {InventoryLocationMapper.class})
public interface InventorySubLocationMapper extends EntityMapper<InventorySubLocationDTO, InventorySubLocation> {

    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    InventorySubLocationDTO toDto(InventorySubLocation inventorySubLocation);

    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
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
