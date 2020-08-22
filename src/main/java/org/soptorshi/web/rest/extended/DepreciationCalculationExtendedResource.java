package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.DepreciationCalculationExtendedService;
import org.soptorshi.service.DepreciationCalculationQueryService;
import org.soptorshi.service.DepreciationCalculationService;
import org.soptorshi.web.rest.DepreciationCalculationResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extended")
public class DepreciationCalculationExtendedResource extends DepreciationCalculationResource {
    private final Logger log = LoggerFactory.getLogger(DepreciationCalculationResource.class);

    private static final String ENTITY_NAME = "depreciationCalculation";

    private final DepreciationCalculationExtendedService depreciationCalculationService;

    private final DepreciationCalculationQueryService depreciationCalculationQueryService;

    public DepreciationCalculationExtendedResource(DepreciationCalculationService depreciationCalculationService, DepreciationCalculationQueryService depreciationCalculationQueryService, DepreciationCalculationExtendedService depreciationCalculationService1, DepreciationCalculationQueryService depreciationCalculationQueryService1) {
        super(depreciationCalculationService, depreciationCalculationQueryService);
        this.depreciationCalculationService = depreciationCalculationService1;
        this.depreciationCalculationQueryService = depreciationCalculationQueryService1;
    }
}
