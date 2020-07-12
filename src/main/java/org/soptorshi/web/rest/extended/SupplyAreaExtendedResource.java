package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaQueryService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.extended.SupplyAreaExtendedService;
import org.soptorshi.web.rest.SupplyAreaResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyArea.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyAreaExtendedResource extends SupplyAreaResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaExtendedResource.class);

    private static final String ENTITY_NAME = "supplyArea";

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    public SupplyAreaExtendedResource(SupplyAreaExtendedService supplyAreaExtendedService, SupplyAreaQueryService supplyAreaQueryService) {
        super(supplyAreaExtendedService, supplyAreaQueryService);
        this.supplyAreaExtendedService = supplyAreaExtendedService;
    }

    @PostMapping("/supply-areas")
    public ResponseEntity<SupplyAreaDTO> createSupplyArea(@Valid @RequestBody SupplyAreaDTO supplyAreaDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyArea : {}", supplyAreaDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplyAreaExtendedService.isValidInput(supplyAreaDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaDTO result = supplyAreaExtendedService.save(supplyAreaDTO);
        return ResponseEntity.created(new URI("/api/supply-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-areas")
    public ResponseEntity<SupplyAreaDTO> updateSupplyArea(@Valid @RequestBody SupplyAreaDTO supplyAreaDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyArea : {}", supplyAreaDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplyAreaExtendedService.isValidInput(supplyAreaDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaDTO result = supplyAreaExtendedService.save(supplyAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-areas/{id}")
    public ResponseEntity<Void> deleteSupplyArea(@PathVariable Long id) {
        log.debug("REST request to delete SupplyArea : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
