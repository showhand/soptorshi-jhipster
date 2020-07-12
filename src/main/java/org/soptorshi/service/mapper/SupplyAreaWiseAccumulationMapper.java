package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;

/**
 * Mapper for the entity SupplyAreaWiseAccumulation and its DTO SupplyAreaWiseAccumulationDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyZoneManagerMapper.class, SupplyAreaMapper.class, SupplyAreaManagerMapper.class, ProductCategoryMapper.class, ProductMapper.class})
public interface SupplyAreaWiseAccumulationMapper extends EntityMapper<SupplyAreaWiseAccumulationDTO, SupplyAreaWiseAccumulation> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "supplyAreaManager.id", target = "supplyAreaManagerId")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    SupplyAreaWiseAccumulationDTO toDto(SupplyAreaWiseAccumulation supplyAreaWiseAccumulation);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "supplyAreaManagerId", target = "supplyAreaManager")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productId", target = "product")
    SupplyAreaWiseAccumulation toEntity(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO);

    default SupplyAreaWiseAccumulation fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation = new SupplyAreaWiseAccumulation();
        supplyAreaWiseAccumulation.setId(id);
        return supplyAreaWiseAccumulation;
    }
}
