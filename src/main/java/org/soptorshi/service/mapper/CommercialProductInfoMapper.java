package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.service.dto.CommercialProductInfoDTO;

/**
 * Mapper for the entity CommercialProductInfo and its DTO CommercialProductInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialBudgetMapper.class, ProductCategoryMapper.class, ProductMapper.class})
public interface CommercialProductInfoMapper extends EntityMapper<CommercialProductInfoDTO, CommercialProductInfo> {

    @Mapping(source = "commercialBudget.id", target = "commercialBudgetId")
    @Mapping(source = "commercialBudget.budgetNo", target = "commercialBudgetBudgetNo")
    @Mapping(source = "productCategories.id", target = "productCategoriesId")
    @Mapping(source = "productCategories.name", target = "productCategoriesName")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    CommercialProductInfoDTO toDto(CommercialProductInfo commercialProductInfo);

    @Mapping(source = "commercialBudgetId", target = "commercialBudget")
    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
    CommercialProductInfo toEntity(CommercialProductInfoDTO commercialProductInfoDTO);

    default CommercialProductInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialProductInfo commercialProductInfo = new CommercialProductInfo();
        commercialProductInfo.setId(id);
        return commercialProductInfo;
    }
}
