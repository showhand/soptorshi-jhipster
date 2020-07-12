package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyChallan;
import org.soptorshi.service.dto.SupplyChallanDTO;

/**
 * Mapper for the entity SupplyChallan and its DTO SupplyChallanDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyZoneManagerMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class, SupplyShopMapper.class, SupplyOrderMapper.class})
public interface SupplyChallanMapper extends EntityMapper<SupplyChallanDTO, SupplyChallan> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.name", target = "supplySalesRepresentativeName")
    @Mapping(source = "supplyShop.id", target = "supplyShopId")
    @Mapping(source = "supplyShop.name", target = "supplyShopName")
    @Mapping(source = "supplyOrder.id", target = "supplyOrderId")
    @Mapping(source = "supplyOrder.orderNo", target = "supplyOrderOrderNo")
    SupplyChallanDTO toDto(SupplyChallan supplyChallan);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
    @Mapping(source = "supplyShopId", target = "supplyShop")
    @Mapping(source = "supplyOrderId", target = "supplyOrder")
    SupplyChallan toEntity(SupplyChallanDTO supplyChallanDTO);

    default SupplyChallan fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyChallan supplyChallan = new SupplyChallan();
        supplyChallan.setId(id);
        return supplyChallan;
    }
}
