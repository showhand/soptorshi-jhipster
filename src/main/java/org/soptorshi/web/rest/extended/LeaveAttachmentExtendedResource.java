package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveAttachmentQueryService;
import org.soptorshi.service.LeaveAttachmentService;
import org.soptorshi.web.rest.LeaveAttachmentResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing LeaveAttachment.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveAttachmentExtendedResource extends LeaveAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentExtendedResource.class);

    private static final String ENTITY_NAME = "leaveAttachment";

    public LeaveAttachmentExtendedResource(LeaveAttachmentService leaveAttachmentService, LeaveAttachmentQueryService leaveAttachmentQueryService) {
        super(leaveAttachmentService, leaveAttachmentQueryService);
    }

    /**
     * DELETE  /leave-attachments/:id : delete the "id" leaveAttachment.
     *
     * @param id the id of the leaveAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-attachments/{id}")
    public ResponseEntity<Void> deleteLeaveAttachment(@PathVariable Long id) {
        log.debug("REST request to delete LeaveAttachment : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
