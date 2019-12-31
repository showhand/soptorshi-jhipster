package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.StockOutItem;
import org.soptorshi.service.dto.StockOutItemDTO;

/**
 * Mapper for the entity StockOutItem and its DTO StockOutItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, StockInItemMapper.class})
public interface StockOutItemMapper extends EntityMapper<StockOutItemDTO, StockOutItem> {

    @Mapping(source = "productCategories.id", target = "productCategoriesId")
    @Mapping(source = "productCategories.name", target = "productCategoriesName")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    @Mapping(source = "stockInItems.id", target = "stockInItemsId")
    StockOutItemDTO toDto(StockOutItem stockOutItem);

    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
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
