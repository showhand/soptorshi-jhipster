package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneQueryService;
import org.soptorshi.service.dto.SupplyZoneDTO;
import org.soptorshi.service.extended.SupplyZoneExtendedService;
import org.soptorshi.web.rest.SupplyZoneResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyZone.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyZoneExtendedResource extends SupplyZoneResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneExtendedResource.class);

    private static final String ENTITY_NAME = "supplyZone";

    private final SupplyZoneExtendedService supplyZoneExtendedService;

    public SupplyZoneExtendedResource(SupplyZoneExtendedService supplyZoneExtendedService, SupplyZoneQueryService supplyZoneQueryService) {
        super(supplyZoneExtendedService, supplyZoneQueryService);
        this.supplyZoneExtendedService = supplyZoneExtendedService;
    }

    @PostMapping("/supply-zones")
    public ResponseEntity<SupplyZoneDTO> createSupplyZone(@Valid @RequestBody SupplyZoneDTO supplyZoneDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZone : {}", supplyZoneDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneDTO result = supplyZoneExtendedService.save(supplyZoneDTO);
        return ResponseEntity.created(new URI("/api/supply-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-zones")
    public ResponseEntity<SupplyZoneDTO> updateSupplyZone(@Valid @RequestBody SupplyZoneDTO supplyZoneDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZone : {}", supplyZoneDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneDTO result = supplyZoneExtendedService.save(supplyZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-zones/{id}")
    public ResponseEntity<Void> deleteSupplyZone(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZone : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
