package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyMoneyCollection;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;

/**
 * Mapper for the entity SupplyMoneyCollection and its DTO SupplyMoneyCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyZoneManagerMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class, SupplyShopMapper.class, SupplyOrderMapper.class})
public interface SupplyMoneyCollectionMapper extends EntityMapper<SupplyMoneyCollectionDTO, SupplyMoneyCollection> {

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
    SupplyMoneyCollectionDTO toDto(SupplyMoneyCollection supplyMoneyCollection);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
    @Mapping(source = "supplyShopId", target = "supplyShop")
    @Mapping(source = "supplyOrderId", target = "supplyOrder")
    SupplyMoneyCollection toEntity(SupplyMoneyCollectionDTO supplyMoneyCollectionDTO);

    default SupplyMoneyCollection fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyMoneyCollection supplyMoneyCollection = new SupplyMoneyCollection();
        supplyMoneyCollection.setId(id);
        return supplyMoneyCollection;
    }
}
