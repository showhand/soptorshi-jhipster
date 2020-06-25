package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.service.dto.SupplyAreaDTO;

/**
 * Mapper for the entity SupplyArea and its DTO SupplyAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class})
public interface SupplyAreaMapper extends EntityMapper<SupplyAreaDTO, SupplyArea> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    SupplyAreaDTO toDto(SupplyArea supplyArea);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    SupplyArea toEntity(SupplyAreaDTO supplyAreaDTO);

    default SupplyArea fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyArea supplyArea = new SupplyArea();
        supplyArea.setId(id);
        return supplyArea;
    }
}
