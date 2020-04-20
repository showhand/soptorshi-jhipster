package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyShopQueryService;
import org.soptorshi.service.extended.SupplyShopExtendedService;
import org.soptorshi.web.rest.SupplyShopResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplyShop.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyShopExtendedResource extends SupplyShopResource {

    private final Logger log = LoggerFactory.getLogger(SupplyShopExtendedResource.class);

    private static final String ENTITY_NAME = "supplyShop";

    public SupplyShopExtendedResource(SupplyShopExtendedService supplyShopExtendedService, SupplyShopQueryService supplyShopQueryService) {
        super(supplyShopExtendedService, supplyShopQueryService);
    }

}
