package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.RequisitionMessagesQueryService;
import org.soptorshi.service.RequisitionMessagesService;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.extended.RequisitionMessagesExtendedService;
import org.soptorshi.web.rest.RequisitionMessagesResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class RequisitionMessagesExtendedResource {
    private final Logger log = LoggerFactory.getLogger(RequisitionMessagesExtendedResource.class);

    private static final String ENTITY_NAME = "requisitionMessages";

    private final RequisitionMessagesExtendedService requisitionMessagesService;

    private final RequisitionMessagesQueryService requisitionMessagesQueryService;

    public RequisitionMessagesExtendedResource(RequisitionMessagesExtendedService requisitionMessagesService, RequisitionMessagesQueryService requisitionMessagesQueryService) {
        this.requisitionMessagesService = requisitionMessagesService;
        this.requisitionMessagesQueryService = requisitionMessagesQueryService;
    }

    @PostMapping("/requisition-messages")
    public ResponseEntity<RequisitionMessagesDTO> createRequisitionMessages(@RequestBody RequisitionMessagesDTO requisitionMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save RequisitionMessages : {}", requisitionMessagesDTO);
        if (requisitionMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitionMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionMessagesDTO result = requisitionMessagesService.save(requisitionMessagesDTO);
        return ResponseEntity.created(new URI("/api/requisition-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/requisition-messages")
    public ResponseEntity<RequisitionMessagesDTO> updateRequisitionMessages(@RequestBody RequisitionMessagesDTO requisitionMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update RequisitionMessages : {}", requisitionMessagesDTO);
        if (requisitionMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionMessagesDTO result = requisitionMessagesService.save(requisitionMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionMessagesDTO.getId().toString()))
            .body(result);
    }

}
