package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyZoneQueryService;
import org.soptorshi.service.extended.SupplyZoneExtendedService;
import org.soptorshi.web.rest.SupplyZoneResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyZone.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyZoneExtendedResource extends SupplyZoneResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneExtendedResource.class);

    private static final String ENTITY_NAME = "supplyZone";

    public SupplyZoneExtendedResource(SupplyZoneExtendedService supplyZoneExtendedService, SupplyZoneQueryService supplyZoneQueryService) {
        super(supplyZoneExtendedService, supplyZoneQueryService);
    }
}
