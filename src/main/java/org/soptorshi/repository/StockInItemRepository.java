package org.soptorshi.repository;

import org.soptorshi.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockInItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockInItemRepository extends JpaRepository<StockInItem, Long>, JpaSpecificationExecutor<StockInItem> {
    StockInItem getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ItemCategory itemCategory, ItemSubCategory itemSubCategory, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);
}
