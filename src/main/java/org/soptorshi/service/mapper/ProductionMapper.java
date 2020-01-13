package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.Production;
import org.soptorshi.service.dto.ProductionDTO;

/**
 * Mapper for the entity Production and its DTO ProductionDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductMapper.class, RequisitionMapper.class})
public interface ProductionMapper extends EntityMapper<ProductionDTO, Production> {

    @Mapping(source = "productCategories.id", target = "productCategoriesId")
    @Mapping(source = "productCategories.name", target = "productCategoriesName")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    @Mapping(source = "requisitions.id", target = "requisitionsId")
    @Mapping(source = "requisitions.requisitionNo", target = "requisitionsRequisitionNo")
    ProductionDTO toDto(Production production);

    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
    @Mapping(source = "requisitionsId", target = "requisitions")
    Production toEntity(ProductionDTO productionDTO);

    default Production fromId(Long id) {
        if (id == null) {
            return null;
        }
        Production production = new Production();
        production.setId(id);
        return production;
    }
}
