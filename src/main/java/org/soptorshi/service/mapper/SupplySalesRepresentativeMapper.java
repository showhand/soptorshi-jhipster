package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplySalesRepresentative;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;

/**
 * Mapper for the entity SupplySalesRepresentative and its DTO SupplySalesRepresentativeDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, SupplyZoneManagerMapper.class, SupplyAreaManagerMapper.class})
public interface SupplySalesRepresentativeMapper extends EntityMapper<SupplySalesRepresentativeDTO, SupplySalesRepresentative> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    SupplySalesRepresentativeDTO toDto(SupplySalesRepresentative supplySalesRepresentative);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    SupplySalesRepresentative toEntity(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO);

    default SupplySalesRepresentative fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplySalesRepresentative supplySalesRepresentative = new SupplySalesRepresentative();
        supplySalesRepresentative.setId(id);
        return supplySalesRepresentative;
    }
}
