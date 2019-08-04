package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockStatus and its DTO StockStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class, ItemSubCategoryMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class})
public interface StockStatusMapper extends EntityMapper<StockStatusDTO, StockStatus> {

    @Mapping(source = "itemCategories.id", target = "itemCategoriesId")
    @Mapping(source = "itemCategories.name", target = "itemCategoriesName")
    @Mapping(source = "itemSubCategories.id", target = "itemSubCategoriesId")
    @Mapping(source = "itemSubCategories.name", target = "itemSubCategoriesName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    StockStatusDTO toDto(StockStatus stockStatus);

    @Mapping(source = "itemCategoriesId", target = "itemCategories")
    @Mapping(source = "itemSubCategoriesId", target = "itemSubCategories")
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
