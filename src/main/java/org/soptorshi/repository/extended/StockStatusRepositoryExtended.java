package org.soptorshi.repository.extended;

import org.soptorshi.domain.*;
import org.soptorshi.repository.StockStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockStatusRepositoryExtended extends StockStatusRepository {

    StockStatus getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ItemCategory itemCategory, ItemSubCategory itemSubCategory, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);
}
