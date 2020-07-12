package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderDetailsQueryService;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.extended.SupplyOrderDetailsExtendedService;
import org.soptorshi.web.rest.SupplyOrderDetailsResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyOrderDetails.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyOrderDetailsExtendedResource extends SupplyOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsExtendedResource.class);

    private static final String ENTITY_NAME = "supplyOrderDetails";

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    public SupplyOrderDetailsExtendedResource(SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService, SupplyOrderDetailsQueryService supplyOrderDetailsQueryService) {
        super(supplyOrderDetailsExtendedService, supplyOrderDetailsQueryService);
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
    }

    @PostMapping("/supply-order-details")
    public ResponseEntity<SupplyOrderDetailsDTO> createSupplyOrderDetails(@Valid @RequestBody SupplyOrderDetailsDTO supplyOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyOrderDetails : {}", supplyOrderDetailsDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyOrderDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyOrderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplyOrderDetailsExtendedService.isValidSupplyOrderStatus(supplyOrderDetailsDTO)) {
            throw new BadRequestAlertException("Invalid Request", ENTITY_NAME, "invalidaccess");
        }
        SupplyOrderDetailsDTO result = supplyOrderDetailsExtendedService.save(supplyOrderDetailsDTO);
        return ResponseEntity.created(new URI("/api/supply-order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-order-details")
    public ResponseEntity<SupplyOrderDetailsDTO> updateSupplyOrderDetails(@Valid @RequestBody SupplyOrderDetailsDTO supplyOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyOrderDetails : {}", supplyOrderDetailsDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyOrderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplyOrderDetailsExtendedService.isValidSupplyOrderStatus(supplyOrderDetailsDTO)) {
            throw new BadRequestAlertException("Invalid Request", ENTITY_NAME, "invalidaccess");
        }
        SupplyOrderDetailsDTO result = supplyOrderDetailsExtendedService.save(supplyOrderDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyOrderDetailsDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-order-details/{id}")
    public ResponseEntity<Void> deleteSupplyOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete SupplyOrderDetails : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
