package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.RequisitionQueryService;
import org.soptorshi.service.RequisitionService;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.extended.RequisitionExtendedService;
import org.soptorshi.web.rest.RequisitionResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class RequisitionExtendedResource {
    private final Logger log = LoggerFactory.getLogger(RequisitionResource.class);

    private static final String ENTITY_NAME = "requisition";

    private final RequisitionExtendedService requisitionService;

    private final RequisitionQueryService requisitionQueryService;

    public RequisitionExtendedResource(RequisitionExtendedService requisitionService, RequisitionQueryService requisitionQueryService) {
        this.requisitionService = requisitionService;
        this.requisitionQueryService = requisitionQueryService;
    }

    @PostMapping("/requisitions")
    public ResponseEntity<RequisitionDTO> createRequisition(@RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to save Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitionDTO result = requisitionService.save(requisitionDTO);
        return ResponseEntity.created(new URI("/api/requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/requisitions")
    public ResponseEntity<RequisitionDTO> updateRequisition(@RequestBody RequisitionDTO requisitionDTO) throws URISyntaxException {
        log.debug("REST request to update Requisition : {}", requisitionDTO);
        if (requisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitionDTO result = requisitionService.save(requisitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionDTO.getId().toString()))
            .body(result);
    }
}
