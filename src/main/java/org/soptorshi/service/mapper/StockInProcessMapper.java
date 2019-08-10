package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockInProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockInProcess and its DTO StockInProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemCategoryMapper.class, ItemSubCategoryMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, ManufacturerMapper.class})
public interface StockInProcessMapper extends EntityMapper<StockInProcessDTO, StockInProcess> {

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
    StockInProcessDTO toDto(StockInProcess stockInProcess);

    @Mapping(source = "itemCategoriesId", target = "itemCategories")
    @Mapping(source = "itemSubCategoriesId", target = "itemSubCategories")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    @Mapping(source = "manufacturersId", target = "manufacturers")
    StockInProcess toEntity(StockInProcessDTO stockInProcessDTO);

    default StockInProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockInProcess stockInProcess = new StockInProcess();
        stockInProcess.setId(id);
        return stockInProcess;
    }
}
