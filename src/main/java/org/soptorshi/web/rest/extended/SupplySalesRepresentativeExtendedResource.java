package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplySalesRepresentativeQueryService;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.extended.SupplySalesRepresentativeExtendedService;
import org.soptorshi.web.rest.SupplySalesRepresentativeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplySalesRepresentative.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplySalesRepresentativeExtendedResource extends SupplySalesRepresentativeResource {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeExtendedResource.class);

    private static final String ENTITY_NAME = "supplySalesRepresentative";

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    public SupplySalesRepresentativeExtendedResource(SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService, SupplySalesRepresentativeQueryService supplySalesRepresentativeQueryService) {
        super(supplySalesRepresentativeExtendedService, supplySalesRepresentativeQueryService);
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
    }

    @PostMapping("/supply-sales-representatives")
    public ResponseEntity<SupplySalesRepresentativeDTO> createSupplySalesRepresentative(@Valid @RequestBody SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to save SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplySalesRepresentativeDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplySalesRepresentative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplySalesRepresentativeExtendedService.isValidInput(supplySalesRepresentativeDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeExtendedService.save(supplySalesRepresentativeDTO);
        return ResponseEntity.created(new URI("/api/supply-sales-representatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-sales-representatives")
    public ResponseEntity<SupplySalesRepresentativeDTO> updateSupplySalesRepresentative(@Valid @RequestBody SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) throws URISyntaxException {
        log.debug("REST request to update SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplySalesRepresentativeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplySalesRepresentativeExtendedService.isValidInput(supplySalesRepresentativeDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeExtendedService.save(supplySalesRepresentativeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplySalesRepresentativeDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-sales-representatives/{id}")
    public ResponseEntity<Void> deleteSupplySalesRepresentative(@PathVariable Long id) {
        log.debug("REST request to delete SupplySalesRepresentative : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
