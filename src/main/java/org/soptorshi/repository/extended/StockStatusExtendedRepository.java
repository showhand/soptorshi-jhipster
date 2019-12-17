package org.soptorshi.repository.extended;

import org.soptorshi.domain.*;
import org.soptorshi.repository.StockStatusRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockStatusExtendedRepository extends StockStatusRepository {

    StockStatus getByProductCategoriesAndProductsAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ProductCategory productCategory, Product product, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);
}
