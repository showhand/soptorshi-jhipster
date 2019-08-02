package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockOutItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockOutItem and its DTO StockOutItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class, ItemSubCategoryMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, StockInItemMapper.class})
public interface StockOutItemMapper extends EntityMapper<StockOutItemDTO, StockOutItem> {

    @Mapping(source = "itemCategories.id", target = "itemCategoriesId")
    @Mapping(source = "itemCategories.name", target = "itemCategoriesName")
    @Mapping(source = "itemSubCategories.id", target = "itemSubCategoriesId")
    @Mapping(source = "itemSubCategories.name", target = "itemSubCategoriesName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    @Mapping(source = "stockInItems.id", target = "stockInItemsId")
    StockOutItemDTO toDto(StockOutItem stockOutItem);

    @Mapping(source = "itemCategoriesId", target = "itemCategories")
    @Mapping(source = "itemSubCategoriesId", target = "itemSubCategories")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    @Mapping(source = "stockInItemsId", target = "stockInItems")
    StockOutItem toEntity(StockOutItemDTO stockOutItemDTO);

    default StockOutItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockOutItem stockOutItem = new StockOutItem();
        stockOutItem.setId(id);
        return stockOutItem;
    }
}
