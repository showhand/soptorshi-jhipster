package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.StockInItem;
import org.soptorshi.service.dto.StockInItemDTO;

/**
 * Mapper for the entity StockInItem and its DTO StockInItemDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductCategoryMapper.class, ProductMapper.class, InventoryLocationMapper.class, InventorySubLocationMapper.class, VendorMapper.class, StockInProcessMapper.class, RequisitionMapper.class})
public interface StockInItemMapper extends EntityMapper<StockInItemDTO, StockInItem> {

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
    @Mapping(source = "stockInProcesses.id", target = "stockInProcessesId")
    @Mapping(source = "requisitions.id", target = "requisitionsId")
    @Mapping(source = "requisitions.requisitionNo", target = "requisitionsRequisitionNo")
    StockInItemDTO toDto(StockInItem stockInItem);

    @Mapping(source = "productCategoriesId", target = "productCategories")
    @Mapping(source = "productsId", target = "products")
    @Mapping(source = "inventoryLocationsId", target = "inventoryLocations")
    @Mapping(source = "inventorySubLocationsId", target = "inventorySubLocations")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "stockInProcessesId", target = "stockInProcesses")
    @Mapping(source = "requisitionsId", target = "requisitions")
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
