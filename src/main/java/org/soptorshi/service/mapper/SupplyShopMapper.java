package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyShop;
import org.soptorshi.service.dto.SupplyShopDTO;

/**
 * Mapper for the entity SupplyShop and its DTO SupplyShopDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplyZoneManagerMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class})
public interface SupplyShopMapper extends EntityMapper<SupplyShopDTO, SupplyShop> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.name", target = "supplySalesRepresentativeName")
    SupplyShopDTO toDto(SupplyShop supplyShop);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
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
