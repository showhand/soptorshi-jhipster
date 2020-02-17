package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ProductionQueryService;
import org.soptorshi.service.extended.ProductionExtendedService;
import org.soptorshi.web.rest.ProductionResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * DELETE  /productions/:id : delete the "id" production.
     *
     * @param id the id of the productionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productions/{id}")
    public ResponseEntity<Void> deleteProduction(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
