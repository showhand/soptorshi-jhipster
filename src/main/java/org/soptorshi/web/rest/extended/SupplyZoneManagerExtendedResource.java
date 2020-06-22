package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneManagerQueryService;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.extended.SupplyZoneManagerExtendedService;
import org.soptorshi.web.rest.SupplyZoneManagerResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyZoneManager.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyZoneManagerExtendedResource extends SupplyZoneManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerExtendedResource.class);

    private static final String ENTITY_NAME = "supplyZoneManager";

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    public SupplyZoneManagerExtendedResource(SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyZoneManagerQueryService supplyZoneManagerQueryService) {
        super(supplyZoneManagerExtendedService, supplyZoneManagerQueryService);
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
    }

    @PostMapping("/supply-zone-managers")
    public ResponseEntity<SupplyZoneManagerDTO> createSupplyZoneManager(@Valid @RequestBody SupplyZoneManagerDTO supplyZoneManagerDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZoneManager : {}", supplyZoneManagerDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZoneManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneManagerDTO result = supplyZoneManagerExtendedService.save(supplyZoneManagerDTO);
        return ResponseEntity.created(new URI("/api/supply-zone-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-zone-managers")
    public ResponseEntity<SupplyZoneManagerDTO> updateSupplyZoneManager(@Valid @RequestBody SupplyZoneManagerDTO supplyZoneManagerDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZoneManager : {}", supplyZoneManagerDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyZoneManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneManagerDTO result = supplyZoneManagerExtendedService.save(supplyZoneManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneManagerDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-zone-managers/{id}")
    public ResponseEntity<Void> deleteSupplyZoneManager(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZoneManager : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
