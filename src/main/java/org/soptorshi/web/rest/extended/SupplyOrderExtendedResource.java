package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyOrderQueryService;
import org.soptorshi.service.extended.SupplyOrderExtendedService;
import org.soptorshi.web.rest.SupplyOrderResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyOrder.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyOrderExtendedResource extends SupplyOrderResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedResource.class);

    private static final String ENTITY_NAME = "supplyOrder";

    public SupplyOrderExtendedResource(SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderQueryService supplyOrderQueryService) {
        super(supplyOrderExtendedService, supplyOrderQueryService);
    }
}
