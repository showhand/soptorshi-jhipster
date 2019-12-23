package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockStatus and its DTO StockStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {StockInItemMapper.class, ProductCategoryMapper.class, ProductMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class})
public interface StockStatusMapper extends EntityMapper<StockStatusDTO, StockStatus> {

    @Mapping(source = "stockInItem.id", target = "stockInItemId")
    @Mapping(source = "productCategories.id", target = "productCategoriesId")
    @Mapping(source = "productCategories.name", target = "productCategoriesName")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    StockStatusDTO toDto(StockStatus stockStatus);

    @Mapping(source = "stockInItemId", target = "stockInItem")
    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    StockStatus toEntity(StockStatusDTO stockStatusDTO);

    default StockStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockStatus stockStatus = new StockStatus();
        stockStatus.setId(id);
        return stockStatus;
    }
}
