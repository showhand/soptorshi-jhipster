package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ItemSubCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemSubCategory and its DTO ItemSubCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class})
public interface ItemSubCategoryMapper extends EntityMapper<ItemSubCategoryDTO, ItemSubCategory> {

    @Mapping(source = "itemCategories.id", target = "itemCategoriesId")
    @Mapping(source = "itemCategories.name", target = "itemCategoriesName")
    ItemSubCategoryDTO toDto(ItemSubCategory itemSubCategory);

    @Mapping(source = "itemCategoriesId", target = "itemCategories")
    ItemSubCategory toEntity(ItemSubCategoryDTO itemSubCategoryDTO);

    default ItemSubCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemSubCategory itemSubCategory = new ItemSubCategory();
        itemSubCategory.setId(id);
        return itemSubCategory;
    }
}
