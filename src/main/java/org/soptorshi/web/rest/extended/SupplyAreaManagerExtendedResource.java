package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaManagerQueryService;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.extended.SupplyAreaManagerExtendedService;
import org.soptorshi.web.rest.SupplyAreaManagerResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyAreaManager.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyAreaManagerExtendedResource extends SupplyAreaManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerExtendedResource.class);

    private static final String ENTITY_NAME = "supplyAreaManager";

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    public SupplyAreaManagerExtendedResource(SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplyAreaManagerQueryService supplyAreaManagerQueryService) {
        super(supplyAreaManagerExtendedService, supplyAreaManagerQueryService);
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
    }

    @PostMapping("/supply-area-managers")
    public ResponseEntity<SupplyAreaManagerDTO> createSupplyAreaManager(@Valid @RequestBody SupplyAreaManagerDTO supplyAreaManagerDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyAreaManager : {}", supplyAreaManagerDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyAreaManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplyAreaManagerExtendedService.isValidInput(supplyAreaManagerDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaManagerDTO result = supplyAreaManagerExtendedService.save(supplyAreaManagerDTO);
        return ResponseEntity.created(new URI("/api/supply-area-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-area-managers")
    public ResponseEntity<SupplyAreaManagerDTO> updateSupplyAreaManager(@Valid @RequestBody SupplyAreaManagerDTO supplyAreaManagerDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyAreaManager : {}", supplyAreaManagerDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplyAreaManagerExtendedService.isValidInput(supplyAreaManagerDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaManagerDTO result = supplyAreaManagerExtendedService.save(supplyAreaManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaManagerDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-area-managers/{id}")
    public ResponseEntity<Void> deleteSupplyAreaManager(@PathVariable Long id) {
        log.debug("REST request to delete SupplyAreaManager : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
