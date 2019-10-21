package org.soptorshi.repository.extended;

import org.soptorshi.domain.*;
import org.soptorshi.repository.StockInItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInItemRepositoryExtended  extends StockInItemRepository {

    StockInItem getByItemCategoriesAndItemSubCategoriesAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ItemCategory itemCategory, ItemSubCategory itemSubCategory, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);
}
