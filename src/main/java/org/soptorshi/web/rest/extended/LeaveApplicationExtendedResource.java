package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveApplicationQueryService;
import org.soptorshi.service.extended.LeaveApplicationExtendedService;
import org.soptorshi.web.rest.LeaveApplicationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing LeaveApplication.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveApplicationExtendedResource extends LeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationExtendedResource.class);

    private static final String ENTITY_NAME = "leaveApplication";

    public LeaveApplicationExtendedResource(LeaveApplicationExtendedService leaveApplicationExtendedService, LeaveApplicationQueryService leaveApplicationQueryService) {
        super(leaveApplicationExtendedService, leaveApplicationQueryService);
    }

    /**
     * DELETE  /leave-applications/:id : delete the "id" leaveApplication.
     *
     * @param id the id of the leaveApplicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-applications/{id}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApplication : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
