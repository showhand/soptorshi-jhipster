package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ProductionQueryService;
import org.soptorshi.service.extended.ProductionExtendedService;
import org.soptorshi.web.rest.ProductionResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Production.
 */
@RestController
@RequestMapping("/api/extended")
public class ProductionExtendedResource extends ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionExtendedResource.class);

    private static final String ENTITY_NAME = "production";

    public ProductionExtendedResource(ProductionExtendedService productionService, ProductionQueryService productionQueryService) {
        super(productionService, productionQueryService);
    }
}
