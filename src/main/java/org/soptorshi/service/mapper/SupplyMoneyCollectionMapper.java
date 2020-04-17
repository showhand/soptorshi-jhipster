package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyMoneyCollection;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;

/**
 * Mapper for the entity SupplyMoneyCollection and its DTO SupplyMoneyCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class})
public interface SupplyMoneyCollectionMapper extends EntityMapper<SupplyMoneyCollectionDTO, SupplyMoneyCollection> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.salesRepresentativeName", target = "supplySalesRepresentativeSalesRepresentativeName")
    SupplyMoneyCollectionDTO toDto(SupplyMoneyCollection supplyMoneyCollection);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
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
