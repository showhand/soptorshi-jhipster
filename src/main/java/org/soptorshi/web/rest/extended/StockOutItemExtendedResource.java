package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockOutItemQueryService;
import org.soptorshi.service.extended.StockOutItemExtendedService;
import org.soptorshi.web.rest.StockOutItemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing StockOutItem.
 */
@RestController
@RequestMapping("/api/extended")
public class StockOutItemExtendedResource extends StockOutItemResource {

    private final Logger log = LoggerFactory.getLogger(StockOutItemExtendedResource.class);

    public StockOutItemExtendedResource(StockOutItemExtendedService stockOutItemExtendedService, StockOutItemQueryService stockOutItemQueryService) {
        super(stockOutItemExtendedService, stockOutItemQueryService);
    }
}
