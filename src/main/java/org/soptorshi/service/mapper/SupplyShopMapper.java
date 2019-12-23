package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SupplyShopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SupplyShop and its DTO SupplyShopDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class})
public interface SupplyShopMapper extends EntityMapper<SupplyShopDTO, SupplyShop> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplyAreaManager.managerName", target = "supplyAreaManagerManagerName")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.salesRepresentativeName", target = "supplySalesRepresentativeSalesRepresentativeName")
    SupplyShopDTO toDto(SupplyShop supplyShop);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
    SupplyShop toEntity(SupplyShopDTO supplyShopDTO);

    default SupplyShop fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyShop supplyShop = new SupplyShop();
        supplyShop.setId(id);
        return supplyShop;
    }
}
