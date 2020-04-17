package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.service.dto.SupplyOrderDTO;

/**
 * Mapper for the entity SupplyOrder and its DTO SupplyOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, SupplySalesRepresentativeMapper.class})
public interface SupplyOrderMapper extends EntityMapper<SupplyOrderDTO, SupplyOrder> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "supplySalesRepresentative.id", target = "supplySalesRepresentativeId")
    @Mapping(source = "supplySalesRepresentative.salesRepresentativeName", target = "supplySalesRepresentativeSalesRepresentativeName")
    SupplyOrderDTO toDto(SupplyOrder supplyOrder);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "supplySalesRepresentativeId", target = "supplySalesRepresentative")
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
