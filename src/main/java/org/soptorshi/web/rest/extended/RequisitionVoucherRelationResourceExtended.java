package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.RequisitionVoucherRelationQueryService;
import org.soptorshi.service.RequisitionVoucherRelationService;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
import org.soptorshi.web.rest.RequisitionVoucherRelationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/extended")
public class RequisitionVoucherRelationResourceExtended {
    private final Logger log = LoggerFactory.getLogger(RequisitionVoucherRelationResourceExtended.class);

    private static final String ENTITY_NAME = "requisitionVoucherRelation";

    private final RequisitionVoucherRelationService requisitionVoucherRelationService;

    private final RequisitionVoucherRelationQueryService requisitionVoucherRelationQueryService;

    public RequisitionVoucherRelationResourceExtended(RequisitionVoucherRelationService requisitionVoucherRelationService, RequisitionVoucherRelationQueryService requisitionVoucherRelationQueryService) {
        this.requisitionVoucherRelationService = requisitionVoucherRelationService;
        this.requisitionVoucherRelationQueryService = requisitionVoucherRelationQueryService;
    }

    @PostMapping("/requisition-voucher-relations")
    public ResponseEntity<RequisitionVoucherRelationDTO> createRequisitionVoucherRelation(@RequestBody RequisitionVoucherRelationDTO requisitionVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to save RequisitionVoucherRelation : {}", requisitionVoucherRelationDTO);
        if (requisitionVoucherRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitionVoucherRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requisitionVoucherRelationDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        requisitionVoucherRelationDTO.setModifiedOn(LocalDate.now());
        RequisitionVoucherRelationDTO result = requisitionVoucherRelationService.save(requisitionVoucherRelationDTO);
        return ResponseEntity.created(new URI("/api/requisition-voucher-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/requisition-voucher-relations")
    public ResponseEntity<RequisitionVoucherRelationDTO> updateRequisitionVoucherRelation(@RequestBody RequisitionVoucherRelationDTO requisitionVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to update RequisitionVoucherRelation : {}", requisitionVoucherRelationDTO);
        if (requisitionVoucherRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        requisitionVoucherRelationDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        requisitionVoucherRelationDTO.setModifiedOn(LocalDate.now());
        RequisitionVoucherRelationDTO result = requisitionVoucherRelationService.save(requisitionVoucherRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisitionVoucherRelationDTO.getId().toString()))
            .body(result);
    }
}
