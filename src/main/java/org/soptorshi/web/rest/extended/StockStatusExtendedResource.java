package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockStatusQueryService;
import org.soptorshi.service.extended.StockStatusExtendedService;
import org.soptorshi.web.rest.StockStatusResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing StockStatus.
 */
@RestController
@RequestMapping("/api/extended")
public class StockStatusExtendedResource extends StockStatusResource {

    private final Logger log = LoggerFactory.getLogger(StockStatusExtendedResource.class);

    public StockStatusExtendedResource(StockStatusExtendedService stockStatusExtendedService, StockStatusQueryService stockStatusQueryService) {
       super(stockStatusExtendedService, stockStatusQueryService);
    }
}
