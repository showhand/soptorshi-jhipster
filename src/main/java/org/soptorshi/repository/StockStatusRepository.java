package org.soptorshi.repository;

import org.soptorshi.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockStatusRepository extends JpaRepository<StockStatus, Long>, JpaSpecificationExecutor<StockStatus> {

    StockStatus getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ItemCategory itemCategory, ItemSubCategory itemSubCategory, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);
}
