package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyShop;
import org.soptorshi.service.dto.SupplyShopDTO;

/**
 * Mapper for the entity SupplyShop and its DTO SupplyShopDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplySalesRepresentativeMapper.class, SupplyAreaManagerMapper.class})
public interface SupplyShopMapper extends EntityMapper<SupplyShopDTO, SupplyShop> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.salesRepresentativeName", target = "supplySalesRepresentativeSalesRepresentativeName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    SupplyShopDTO toDto(SupplyShop supplyShop);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
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
