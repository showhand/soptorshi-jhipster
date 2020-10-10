package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPiQueryService;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.extended.CommercialPiExtendedService;
import org.soptorshi.web.rest.CommercialPiResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialPi.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPiExtendedResource extends CommercialPiResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPiExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPi";

    private final CommercialPiExtendedService commercialPiExtendedService;

    public CommercialPiExtendedResource(CommercialPiExtendedService commercialPiExtendedService, CommercialPiQueryService commercialPiQueryService) {
        super(commercialPiExtendedService, commercialPiQueryService);
        this.commercialPiExtendedService = commercialPiExtendedService;
    }

    @PostMapping("/commercial-pis")
    public ResponseEntity<CommercialPiDTO> createCommercialPi(@Valid @RequestBody CommercialPiDTO commercialPiDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPi : {}", commercialPiDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPiDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPiDTO result = commercialPiExtendedService.save(commercialPiDTO);
        return ResponseEntity.created(new URI("/api/commercial-pis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/commercial-pis")
    public ResponseEntity<CommercialPiDTO> updateCommercialPi(@Valid @RequestBody CommercialPiDTO commercialPiDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPi : {}", commercialPiDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPiDTO result = commercialPiExtendedService.save(commercialPiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPiDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-pis/{id}")
    public ResponseEntity<Void> deleteCommercialPi(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPi : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialPiExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
