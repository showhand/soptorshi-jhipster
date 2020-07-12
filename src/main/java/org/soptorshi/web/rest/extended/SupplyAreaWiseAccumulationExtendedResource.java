package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaWiseAccumulationQueryService;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.extended.SupplyAreaWiseAccumulationExtendedService;
import org.soptorshi.web.rest.SupplyAreaWiseAccumulationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing SupplyAreaWiseAccumulation.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyAreaWiseAccumulationExtendedResource extends SupplyAreaWiseAccumulationResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaWiseAccumulationExtendedResource.class);

    private static final String ENTITY_NAME = "supplyAreaWiseAccumulation";

    private final SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService;

    private final SupplyAreaWiseAccumulationQueryService supplyAreaWiseAccumulationQueryService;

    public SupplyAreaWiseAccumulationExtendedResource(SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService, SupplyAreaWiseAccumulationQueryService supplyAreaWiseAccumulationQueryService) {
        super(supplyAreaWiseAccumulationExtendedService, supplyAreaWiseAccumulationQueryService);
        this.supplyAreaWiseAccumulationExtendedService = supplyAreaWiseAccumulationExtendedService;
        this.supplyAreaWiseAccumulationQueryService = supplyAreaWiseAccumulationQueryService;
    }


    @PostMapping("/supply-area-wise-accumulations")
    public ResponseEntity<SupplyAreaWiseAccumulationDTO> createSupplyAreaWiseAccumulation(@Valid @RequestBody SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaWiseAccumulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyAreaWiseAccumulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(!supplyAreaWiseAccumulationExtendedService.isValidInput(supplyAreaWiseAccumulationDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationExtendedService.save(supplyAreaWiseAccumulationDTO);
        return ResponseEntity.created(new URI("/api/supply-area-wise-accumulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-area-wise-accumulations")
    public ResponseEntity<SupplyAreaWiseAccumulationDTO> updateSupplyAreaWiseAccumulation(@Valid @RequestBody SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTO);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyAreaWiseAccumulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if(!supplyAreaWiseAccumulationExtendedService.isValidInput(supplyAreaWiseAccumulationDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        if(!supplyAreaWiseAccumulationExtendedService.isValidStatus(supplyAreaWiseAccumulationDTO)) {
            throw new BadRequestAlertException("Invalid Request", ENTITY_NAME, "invalidaccess");
        }
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationExtendedService.save(supplyAreaWiseAccumulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaWiseAccumulationDTO.getId().toString()))
            .body(result);
    }

    @PostMapping("/supply-area-wise-accumulations/bulk")
    public ResponseEntity<List<SupplyAreaWiseAccumulationDTO>> postBulkSupplyAreaWiseAccumulation(@Valid @RequestBody List<SupplyAreaWiseAccumulationDTO> supplyAreaWiseAccumulationDTOs) throws URISyntaxException {
        log.debug("REST request to post bulk SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTOs);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            if (supplyAreaWiseAccumulationDTO.getId() != null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
        }
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            if (!supplyAreaWiseAccumulationExtendedService.isValidInput(supplyAreaWiseAccumulationDTO)) {
                throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
            }
        }
        List<SupplyAreaWiseAccumulationDTO> results = new ArrayList<>();
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationExtendedService.save(supplyAreaWiseAccumulationDTO);
            results.add(result);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaWiseAccumulationDTOs.stream()
                .map(SupplyAreaWiseAccumulationDTO::getId).collect(Collectors.toList()).toString()))
            .body(results);
    }

    @PutMapping("/supply-area-wise-accumulations/bulk")
    public ResponseEntity<List<SupplyAreaWiseAccumulationDTO>> updateBulkSupplyAreaWiseAccumulation(@Valid @RequestBody List<SupplyAreaWiseAccumulationDTO> supplyAreaWiseAccumulationDTOs) throws URISyntaxException {
        log.debug("REST request to update bulk SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTOs);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            if (supplyAreaWiseAccumulationDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
        }
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            if (!supplyAreaWiseAccumulationExtendedService.isValidInput(supplyAreaWiseAccumulationDTO)) {
                throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
            }
        }
        List<SupplyAreaWiseAccumulationDTO> results = new ArrayList<>();
        for(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO: supplyAreaWiseAccumulationDTOs) {
            SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationExtendedService.save(supplyAreaWiseAccumulationDTO);
            results.add(result);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaWiseAccumulationDTOs.stream()
                .map(SupplyAreaWiseAccumulationDTO::getId).collect(Collectors.toList()).toString()))
            .body(results);
    }

    @DeleteMapping("/supply-area-wise-accumulations/{id}")
    public ResponseEntity<Void> deleteSupplyAreaWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to delete SupplyAreaWiseAccumulation : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
