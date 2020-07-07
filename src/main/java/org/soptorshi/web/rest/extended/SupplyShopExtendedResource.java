package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyShopQueryService;
import org.soptorshi.service.dto.SupplyShopDTO;
import org.soptorshi.service.extended.SupplyShopExtendedService;
import org.soptorshi.web.rest.SupplyShopResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyShop.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyShopExtendedResource extends SupplyShopResource {

    private final Logger log = LoggerFactory.getLogger(SupplyShopExtendedResource.class);

    private static final String ENTITY_NAME = "supplyShop";

    private final SupplyShopExtendedService supplyShopExtendedService;

    public SupplyShopExtendedResource(SupplyShopExtendedService supplyShopExtendedService, SupplyShopQueryService supplyShopQueryService) {
        super(supplyShopExtendedService, supplyShopQueryService);
        this.supplyShopExtendedService = supplyShopExtendedService;
    }

    @PostMapping("/supply-shops")
    public ResponseEntity<SupplyShopDTO> createSupplyShop(@Valid @RequestBody SupplyShopDTO supplyShopDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyShop : {}", supplyShopDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyShopDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyShop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplyShopExtendedService.isValidInput(supplyShopDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyShopDTO result = supplyShopExtendedService.save(supplyShopDTO);
        return ResponseEntity.created(new URI("/api/supply-shops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-shops")
    public ResponseEntity<SupplyShopDTO> updateSupplyShop(@Valid @RequestBody SupplyShopDTO supplyShopDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyShop : {}", supplyShopDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyShopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplyShopExtendedService.isValidInput(supplyShopDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyShopDTO result = supplyShopExtendedService.save(supplyShopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyShopDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-shops/{id}")
    public ResponseEntity<Void> deleteSupplyShop(@PathVariable Long id) {
        log.debug("REST request to delete SupplyShop : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }

}
