package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialProductInfoQueryService;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.extended.CommercialProductInfoExtendedService;
import org.soptorshi.web.rest.CommercialProductInfoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialProductInfo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialProductInfoExtendedResource extends CommercialProductInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialProductInfo";

    private final CommercialProductInfoExtendedService commercialProductInfoExtendedService;

    public CommercialProductInfoExtendedResource(CommercialProductInfoExtendedService commercialProductInfoExtendedService, CommercialProductInfoQueryService commercialProductInfoQueryService) {
        super(commercialProductInfoExtendedService, commercialProductInfoQueryService);
        this.commercialProductInfoExtendedService = commercialProductInfoExtendedService;
    }

    @PostMapping("/commercial-product-infos")
    public ResponseEntity<CommercialProductInfoDTO> createCommercialProductInfo(@Valid @RequestBody CommercialProductInfoDTO commercialProductInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialProductInfo : {}", commercialProductInfoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialProductInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialProductInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialProductInfoDTO result = commercialProductInfoExtendedService.save(commercialProductInfoDTO);
        return ResponseEntity.created(new URI("/api/commercial-product-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/commercial-product-infos")
    public ResponseEntity<CommercialProductInfoDTO> updateCommercialProductInfo(@Valid @RequestBody CommercialProductInfoDTO commercialProductInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialProductInfo : {}", commercialProductInfoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialProductInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialProductInfoDTO result = commercialProductInfoExtendedService.save(commercialProductInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialProductInfoDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-product-infos/{id}")
    public ResponseEntity<Void> deleteCommercialProductInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialProductInfo : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialProductInfoExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
