package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.StockInProcessQueryService;
import org.soptorshi.service.extended.StockInProcessExtendedService;
import org.soptorshi.web.rest.StockInProcessResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing StockInProcess.
 */
@RestController
@RequestMapping("/api/extended")
public class StockInProcessExtendedResource extends StockInProcessResource {

    private final Logger log = LoggerFactory.getLogger(StockInProcessExtendedResource.class);

    public StockInProcessExtendedResource(StockInProcessExtendedService stockInProcessExtendedService, StockInProcessQueryService stockInProcessQueryService) {
        super(stockInProcessExtendedService, stockInProcessQueryService);
    }
}
