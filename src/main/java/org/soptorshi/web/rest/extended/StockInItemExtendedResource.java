package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInItemQueryService;
import org.soptorshi.service.extended.StockInItemExtendedService;
import org.soptorshi.web.rest.StockInItemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing StockInItem.
 */
@RestController
@RequestMapping("/api/extended")
public class StockInItemExtendedResource extends StockInItemResource {

    private final Logger log = LoggerFactory.getLogger(StockInItemExtendedResource.class);

    public StockInItemExtendedResource(StockInItemExtendedService stockInItemExtendedService, StockInItemQueryService stockInItemQueryService) {
        super(stockInItemExtendedService, stockInItemQueryService);
    }
}
