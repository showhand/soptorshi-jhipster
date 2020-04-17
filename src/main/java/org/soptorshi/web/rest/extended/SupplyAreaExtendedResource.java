package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyAreaQueryService;
import org.soptorshi.service.extended.SupplyAreaExtendedService;
import org.soptorshi.web.rest.SupplyAreaResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyArea.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyAreaExtendedResource extends SupplyAreaResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaExtendedResource.class);

    private static final String ENTITY_NAME = "supplyArea";

    public SupplyAreaExtendedResource(SupplyAreaExtendedService supplyAreaExtendedService, SupplyAreaQueryService supplyAreaQueryService) {
        super(supplyAreaExtendedService, supplyAreaQueryService);
    }
}
