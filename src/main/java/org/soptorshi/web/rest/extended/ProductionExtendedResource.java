package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ProductionQueryService;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.extended.ProductionExtendedService;
import org.soptorshi.web.rest.ProductionResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Production.
 */
@RestController
@RequestMapping("/api/extended")
public class ProductionExtendedResource extends ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionExtendedResource.class);

    private static final String ENTITY_NAME = "production";

    private final ProductionExtendedService productionExtendedService;

    public ProductionExtendedResource(ProductionExtendedService productionExtendedService, ProductionQueryService productionQueryService) {
        super(productionExtendedService, productionQueryService);
        this.productionExtendedService = productionExtendedService;
    }

    @PostMapping("/productions")
    public ResponseEntity<ProductionDTO> createProduction(@Valid @RequestBody ProductionDTO productionDTO) throws URISyntaxException {
        log.debug("REST request to save Production : {}", productionDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PRODUCTION_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PRODUCTION_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (productionDTO.getId() != null) {
            throw new BadRequestAlertException("A new production cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductionDTO result = productionExtendedService.save(productionDTO);
        return ResponseEntity.created(new URI("/api/productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/productions")
    public ResponseEntity<ProductionDTO> updateProduction(@Valid @RequestBody ProductionDTO productionDTO) throws URISyntaxException {
        log.debug("REST request to update Production : {}", productionDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PRODUCTION_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PRODUCTION_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (productionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductionDTO result = productionExtendedService .save(productionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productionDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/productions/{id}")
    public ResponseEntity<Void> deleteProduction(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PRODUCTION_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        productionExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
