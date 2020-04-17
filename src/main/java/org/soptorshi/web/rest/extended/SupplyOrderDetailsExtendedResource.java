package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyOrderDetailsQueryService;
import org.soptorshi.service.extended.SupplyOrderDetailsExtendedService;
import org.soptorshi.web.rest.SupplyOrderDetailsResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyOrderDetails.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyOrderDetailsExtendedResource extends SupplyOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsExtendedResource.class);

    private static final String ENTITY_NAME = "supplyOrderDetails";

    public SupplyOrderDetailsExtendedResource(SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService, SupplyOrderDetailsQueryService supplyOrderDetailsQueryService) {
        super(supplyOrderDetailsExtendedService, supplyOrderDetailsQueryService);
    }
}
