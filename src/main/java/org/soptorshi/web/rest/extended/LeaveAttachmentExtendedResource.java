package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.LeaveAttachmentQueryService;
import org.soptorshi.service.dto.LeaveAttachmentDTO;
import org.soptorshi.service.extended.LeaveAttachmentExtendedService;
import org.soptorshi.web.rest.LeaveAttachmentResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing LeaveAttachment.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveAttachmentExtendedResource extends LeaveAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentExtendedResource.class);

    private static final String ENTITY_NAME = "leaveAttachment";

    private final LeaveAttachmentExtendedService leaveAttachmentExtendedService;

    public LeaveAttachmentExtendedResource(LeaveAttachmentExtendedService leaveAttachmentExtendedService, LeaveAttachmentQueryService leaveAttachmentQueryService) {
        super(leaveAttachmentExtendedService, leaveAttachmentQueryService);
        this.leaveAttachmentExtendedService = leaveAttachmentExtendedService;
    }

    @PostMapping("/leave-attachments")
    public ResponseEntity<LeaveAttachmentDTO> createLeaveAttachment(@Valid @RequestBody LeaveAttachmentDTO leaveAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveAttachment : {}", leaveAttachmentDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveAttachmentDTO result = leaveAttachmentExtendedService.save(leaveAttachmentDTO);
        return ResponseEntity.created(new URI("/api/leave-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/leave-attachments")
    public ResponseEntity<LeaveAttachmentDTO> updateLeaveAttachment(@Valid @RequestBody LeaveAttachmentDTO leaveAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveAttachment : {}", leaveAttachmentDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveAttachmentDTO result = leaveAttachmentExtendedService.save(leaveAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveAttachmentDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/leave-attachments/{id}")
    public ResponseEntity<Void> deleteLeaveAttachment(@PathVariable Long id) {
        log.debug("REST request to delete LeaveAttachment : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        leaveAttachmentExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
