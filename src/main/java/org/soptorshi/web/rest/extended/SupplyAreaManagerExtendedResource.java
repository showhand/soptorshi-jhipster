package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyAreaManagerQueryService;
import org.soptorshi.service.extended.SupplyAreaManagerExtendedService;
import org.soptorshi.web.rest.SupplyAreaManagerResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyAreaManager.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyAreaManagerExtendedResource extends SupplyAreaManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerExtendedResource.class);

    private static final String ENTITY_NAME = "supplyAreaManager";

    public SupplyAreaManagerExtendedResource(SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplyAreaManagerQueryService supplyAreaManagerQueryService) {
        super(supplyAreaManagerExtendedService, supplyAreaManagerQueryService);
    }
}
