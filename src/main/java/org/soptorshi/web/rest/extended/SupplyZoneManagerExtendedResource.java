package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.extended.SupplyZoneManagerExtendedService;
import org.soptorshi.web.rest.SupplyZoneManagerResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyZoneManager.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyZoneManagerExtendedResource extends SupplyZoneManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerExtendedResource.class);

    private static final String ENTITY_NAME = "supplyZoneManager";

    public SupplyZoneManagerExtendedResource(SupplyZoneManagerExtendedService supplyZoneManagerExtendedService) {
        super(supplyZoneManagerExtendedService);
    }
}
