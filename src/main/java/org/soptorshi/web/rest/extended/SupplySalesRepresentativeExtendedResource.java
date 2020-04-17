package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplySalesRepresentativeQueryService;
import org.soptorshi.service.extended.SupplySalesRepresentativeExtendedService;
import org.soptorshi.web.rest.SupplySalesRepresentativeResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing SupplySalesRepresentative.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplySalesRepresentativeExtendedResource extends SupplySalesRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeExtendedResource.class);

    private static final String ENTITY_NAME = "supplySalesRepresentative";

    public SupplySalesRepresentativeExtendedResource(SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService, SupplySalesRepresentativeQueryService supplySalesRepresentativeQueryService) {
        super(supplySalesRepresentativeExtendedService, supplySalesRepresentativeQueryService);
    }

}
