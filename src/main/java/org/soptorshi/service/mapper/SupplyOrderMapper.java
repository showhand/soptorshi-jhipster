package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.service.dto.SupplyOrderDTO;

/**
 * Mapper for the entity SupplyOrder and its DTO SupplyOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyZoneManagerMapper.class, SupplyAreaMapper.class, SupplySalesRepresentativeMapper.class, SupplyAreaManagerMapper.class, SupplyShopMapper.class})
public interface SupplyOrderMapper extends EntityMapper<SupplyOrderDTO, SupplyOrder> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.name", target = "supplySalesRepresentativeName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplyShop.id", target = "supplyShopId")
    @Mapping(source = "supplyShop.name", target = "supplyShopName")
    SupplyOrderDTO toDto(SupplyOrder supplyOrder);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplyShopId", target = "supplyShop")
    SupplyOrder toEntity(SupplyOrderDTO supplyOrderDTO);

    default SupplyOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyOrder supplyOrder = new SupplyOrder();
        supplyOrder.setId(id);
        return supplyOrder;
    }
}
