package org.soptorshi.repository.extended;

import org.soptorshi.domain.*;
import org.soptorshi.repository.StockInItemRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInItemExtendedRepository extends StockInItemRepository {

    StockInItem getByProductCategoriesAndProductsAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
        ProductCategory productCategory, Product product, InventoryLocation inventoryLocation, InventorySubLocation
        inventorySubLocation, String containerTrackingId);

    boolean existsByStockInProcesses(StockInProcess stockInProcess);
}
