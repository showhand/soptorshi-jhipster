package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;

/**
 * Mapper for the entity SupplyAreaManager and its DTO SupplyAreaManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class})
public interface SupplyAreaManagerMapper extends EntityMapper<SupplyAreaManagerDTO, SupplyAreaManager> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    SupplyAreaManagerDTO toDto(SupplyAreaManager supplyAreaManager);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    SupplyAreaManager toEntity(SupplyAreaManagerDTO supplyAreaManagerDTO);

    default SupplyAreaManager fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyAreaManager supplyAreaManager = new SupplyAreaManager();
        supplyAreaManager.setId(id);
        return supplyAreaManager;
    }
}
