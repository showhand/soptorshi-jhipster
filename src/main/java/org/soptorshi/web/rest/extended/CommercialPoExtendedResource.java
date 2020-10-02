package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPoQueryService;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.extended.CommercialPoExtendedService;
import org.soptorshi.web.rest.CommercialPoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialPo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPoExtendedResource extends CommercialPoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPo";

    private final CommercialPoExtendedService commercialPoExtendedService;

    public CommercialPoExtendedResource(CommercialPoExtendedService commercialPoExtendedService, CommercialPoQueryService commercialPoQueryService) {
        super(commercialPoExtendedService, commercialPoQueryService);
        this.commercialPoExtendedService = commercialPoExtendedService;
    }

    @PostMapping("/commercial-pos")
    public ResponseEntity<CommercialPoDTO> createCommercialPo(@Valid @RequestBody CommercialPoDTO commercialPoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPo : {}", commercialPoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPoDTO result = commercialPoExtendedService.save(commercialPoDTO);
        return ResponseEntity.created(new URI("/api/commercial-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/commercial-pos")
    public ResponseEntity<CommercialPoDTO> updateCommercialPo(@Valid @RequestBody CommercialPoDTO commercialPoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPo : {}", commercialPoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPoDTO result = commercialPoExtendedService.save(commercialPoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPoDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-pos/{id}")
    public ResponseEntity<Void> deleteCommercialPo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPo : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialPoExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
