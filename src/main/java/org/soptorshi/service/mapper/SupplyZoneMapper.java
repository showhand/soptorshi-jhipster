package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.service.dto.SupplyZoneDTO;

/**
 * Mapper for the entity SupplyZone and its DTO SupplyZoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplyZoneMapper extends EntityMapper<SupplyZoneDTO, SupplyZone> {



    default SupplyZone fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyZone supplyZone = new SupplyZone();
        supplyZone.setId(id);
        return supplyZone;
    }
}
