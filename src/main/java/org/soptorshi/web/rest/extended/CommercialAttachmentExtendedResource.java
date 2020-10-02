package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialAttachmentQueryService;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
import org.soptorshi.service.extended.CommercialAttachmentExtendedService;
import org.soptorshi.web.rest.CommercialAttachmentResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialAttachment.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialAttachmentExtendedResource extends CommercialAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentExtendedResource.class);

    private static final String ENTITY_NAME = "commercialAttachment";

    private final CommercialAttachmentExtendedService commercialAttachmentExtendedService;

    public CommercialAttachmentExtendedResource(CommercialAttachmentExtendedService commercialAttachmentExtendedService, CommercialAttachmentQueryService commercialAttachmentQueryService) {
       super(commercialAttachmentExtendedService, commercialAttachmentQueryService);
       this.commercialAttachmentExtendedService = commercialAttachmentExtendedService;
    }

    @PostMapping("/commercial-attachments")
    public ResponseEntity<CommercialAttachmentDTO> createCommercialAttachment(@Valid @RequestBody CommercialAttachmentDTO commercialAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialAttachment : {}", commercialAttachmentDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialAttachmentDTO result = commercialAttachmentExtendedService.save(commercialAttachmentDTO);
        return ResponseEntity.created(new URI("/api/commercial-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/commercial-attachments")
    public ResponseEntity<CommercialAttachmentDTO> updateCommercialAttachment(@Valid @RequestBody CommercialAttachmentDTO commercialAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialAttachment : {}", commercialAttachmentDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialAttachmentDTO result = commercialAttachmentExtendedService.save(commercialAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialAttachmentDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-attachments/{id}")
    public ResponseEntity<Void> deleteCommercialAttachment(@PathVariable Long id) {
        log.debug("REST request to delete CommercialAttachment : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialAttachmentExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
