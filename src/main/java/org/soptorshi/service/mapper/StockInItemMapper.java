package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockInItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockInItem and its DTO StockInItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class, ItemSubCategoryMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, ManufacturerMapper.class, StockInProcessMapper.class})
public interface StockInItemMapper extends EntityMapper<StockInItemDTO, StockInItem> {

    @Mapping(source = "itemCategories.id", target = "itemCategoriesId")
    @Mapping(source = "itemCategories.name", target = "itemCategoriesName")
    @Mapping(source = "itemSubCategories.id", target = "itemSubCategoriesId")
    @Mapping(source = "itemSubCategories.name", target = "itemSubCategoriesName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    @Mapping(source = "manufacturers.id", target = "manufacturersId")
    @Mapping(source = "manufacturers.name", target = "manufacturersName")
    @Mapping(source = "stockInProcesses.id", target = "stockInProcessesId")
    StockInItemDTO toDto(StockInItem stockInItem);

    @Mapping(source = "itemCategoriesId", target = "itemCategories")
    @Mapping(source = "itemSubCategoriesId", target = "itemSubCategories")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    @Mapping(source = "manufacturersId", target = "manufacturers")
    @Mapping(source = "stockInProcessesId", target = "stockInProcesses")
    StockInItem toEntity(StockInItemDTO stockInItemDTO);

    default StockInItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockInItem stockInItem = new StockInItem();
        stockInItem.setId(id);
        return stockInItem;
    }
}
