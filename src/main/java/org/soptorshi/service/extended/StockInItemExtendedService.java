package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.extended.StockInItemExtendedRepository;
import org.soptorshi.repository.search.StockInItemSearchRepository;
import org.soptorshi.service.StockInItemService;
import org.soptorshi.service.mapper.StockInItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing StockInItem.
 */
@Service
@Transactional
public class StockInItemExtendedService extends StockInItemService {

    private final Logger log = LoggerFactory.getLogger(StockInItemExtendedService.class);

    private final StockInItemExtendedRepository stockInItemExtendedRepository;

    public StockInItemExtendedService(StockInItemExtendedRepository stockInItemExtendedRepository, StockInItemMapper stockInItemMapper, StockInItemSearchRepository stockInItemSearchRepository) {
        super(stockInItemExtendedRepository, stockInItemMapper, stockInItemSearchRepository);
        this.stockInItemExtendedRepository = stockInItemExtendedRepository;
    }

    public boolean exists(final StockInProcess stockInProcess) {
        return stockInItemExtendedRepository.existsByStockInProcesses(stockInProcess);
    }

    public StockInItem getOne(ProductCategory productCategory, Product product, InventoryLocation inventoryLocation, InventorySubLocation inventorySubLocation,
                              String containerTrackingId) {
        return stockInItemExtendedRepository.getByProductCategoriesAndProductsAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
            productCategory, product, inventoryLocation, inventorySubLocation, containerTrackingId
        );
    }
}
