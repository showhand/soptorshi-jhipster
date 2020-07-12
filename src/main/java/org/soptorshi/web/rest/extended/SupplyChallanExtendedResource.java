package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyChallanQueryService;
import org.soptorshi.service.dto.SupplyChallanDTO;
import org.soptorshi.service.extended.SupplyChallanExtendedService;
import org.soptorshi.web.rest.SupplyChallanResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing SupplyChallan.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyChallanExtendedResource extends SupplyChallanResource {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanExtendedResource.class);

    private static final String ENTITY_NAME = "supplyChallan";

    private final SupplyChallanExtendedService supplyChallanService;

    private final SupplyChallanQueryService supplyChallanQueryService;

    public SupplyChallanExtendedResource(SupplyChallanExtendedService supplyChallanService, SupplyChallanQueryService supplyChallanQueryService) {
        super(supplyChallanService, supplyChallanQueryService);
        this.supplyChallanService = supplyChallanService;
        this.supplyChallanQueryService = supplyChallanQueryService;
    }

    @PostMapping("/supply-challans")
    public ResponseEntity<SupplyChallanDTO> createSupplyChallan(@Valid @RequestBody SupplyChallanDTO supplyChallanDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyChallan : {}", supplyChallanDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyChallanDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyChallan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!supplyChallanService.isValidInput(supplyChallanDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyChallanDTO result = supplyChallanService.save(supplyChallanDTO);
        return ResponseEntity.created(new URI("/api/supply-challans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-challans")
    public ResponseEntity<SupplyChallanDTO> updateSupplyChallan(@Valid @RequestBody SupplyChallanDTO supplyChallanDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyChallan : {}", supplyChallanDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyChallanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!supplyChallanService.isValidInput(supplyChallanDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        SupplyChallanDTO result = supplyChallanService.save(supplyChallanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyChallanDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/supply-challans/{id}")
    public ResponseEntity<Void> deleteSupplyChallan(@PathVariable Long id) {
        log.debug("REST request to delete SupplyChallan : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }

    @GetMapping(value = "/supply-challans/download/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadChallan(@PathVariable Long id) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = supplyChallanService.downloadChallan(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/supply-challans");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
