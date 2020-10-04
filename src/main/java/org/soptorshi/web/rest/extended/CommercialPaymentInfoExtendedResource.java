package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPaymentInfoQueryService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.extended.CommercialPaymentInfoExtendedService;
import org.soptorshi.web.rest.CommercialPaymentInfoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialPaymentInfo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPaymentInfoExtendedResource extends CommercialPaymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPaymentInfo";

    private final CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService;

    public CommercialPaymentInfoExtendedResource(CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService, CommercialPaymentInfoQueryService commercialPaymentInfoQueryService) {
        super(commercialPaymentInfoExtendedService, commercialPaymentInfoQueryService);
        this.commercialPaymentInfoExtendedService = commercialPaymentInfoExtendedService;
    }

    @PostMapping("/commercial-payment-infos")
    public ResponseEntity<CommercialPaymentInfoDTO> createCommercialPaymentInfo(@Valid @RequestBody CommercialPaymentInfoDTO commercialPaymentInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPaymentInfo : {}", commercialPaymentInfoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPaymentInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPaymentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPaymentInfoDTO result = commercialPaymentInfoExtendedService.save(commercialPaymentInfoDTO);
        return ResponseEntity.created(new URI("/api/commercial-payment-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/commercial-payment-infos")
    public ResponseEntity<CommercialPaymentInfoDTO> updateCommercialPaymentInfo(@Valid @RequestBody CommercialPaymentInfoDTO commercialPaymentInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPaymentInfo : {}", commercialPaymentInfoDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialPaymentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPaymentInfoDTO result = commercialPaymentInfoExtendedService.save(commercialPaymentInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPaymentInfoDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-payment-infos/{id}")
    public ResponseEntity<Void> deleteCommercialPaymentInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPaymentInfo : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialPaymentInfoExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
