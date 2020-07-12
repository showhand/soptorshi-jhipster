package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyZoneWiseAccumulation;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;

/**
 * Mapper for the entity SupplyZoneWiseAccumulation and its DTO SupplyZoneWiseAccumulationDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyZoneManagerMapper.class, ProductCategoryMapper.class, ProductMapper.class})
public interface SupplyZoneWiseAccumulationMapper extends EntityMapper<SupplyZoneWiseAccumulationDTO, SupplyZoneWiseAccumulation> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    SupplyZoneWiseAccumulationDTO toDto(SupplyZoneWiseAccumulation supplyZoneWiseAccumulation);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productId", target = "product")
    SupplyZoneWiseAccumulation toEntity(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO);

    default SupplyZoneWiseAccumulation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation = new SupplyZoneWiseAccumulation();
        supplyZoneWiseAccumulation.setId(id);
        return supplyZoneWiseAccumulation;
    }
}
