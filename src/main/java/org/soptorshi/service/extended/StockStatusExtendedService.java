package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.extended.StockStatusExtendedRepository;
import org.soptorshi.repository.search.StockStatusSearchRepository;
import org.soptorshi.service.StockStatusService;
import org.soptorshi.service.mapper.StockStatusMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockStatusExtendedService extends StockStatusService {

    private final Logger log = LoggerFactory.getLogger(StockStatusExtendedService.class);

    private final StockStatusExtendedRepository stockStatusExtendedRepository;

    public StockStatusExtendedService(StockStatusExtendedRepository stockStatusExtendedRepository, StockStatusMapper stockStatusMapper, StockStatusSearchRepository stockStatusSearchRepository) {
        super(stockStatusExtendedRepository, stockStatusMapper, stockStatusSearchRepository);
        this.stockStatusExtendedRepository = stockStatusExtendedRepository;
    }

    public StockStatus getOne(ProductCategory productCategory, Product product, InventoryLocation inventoryLocation, InventorySubLocation inventorySubLocation,
                              String containerTrackingId) {
        return stockStatusExtendedRepository.getByProductCategoriesAndProductsAndInventoryLocationsAndInventorySubLocationsAndContainerTrackingId(
            productCategory, product, inventoryLocation, inventorySubLocation, containerTrackingId
        );
    }
}

