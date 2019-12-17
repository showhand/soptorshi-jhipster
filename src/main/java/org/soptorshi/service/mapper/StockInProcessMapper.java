package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.StockInProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockInProcess and its DTO StockInProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, VendorMapper.class})
public interface StockInProcessMapper extends EntityMapper<StockInProcessDTO, StockInProcess> {

    @Mapping(source = "productCategories.id", target = "productCategoriesId")
    @Mapping(source = "productCategories.name", target = "productCategoriesName")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    @Mapping(source = "inventoryLocations.id", target = "inventoryLocationsId")
    @Mapping(source = "inventoryLocations.name", target = "inventoryLocationsName")
    @Mapping(source = "inventorySubLocations.id", target = "inventorySubLocationsId")
    @Mapping(source = "inventorySubLocations.name", target = "inventorySubLocationsName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.companyName", target = "vendorCompanyName")
    StockInProcessDTO toDto(StockInProcess stockInProcess);

    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    @Mapping(source = "vendorId", target = "vendor")
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
