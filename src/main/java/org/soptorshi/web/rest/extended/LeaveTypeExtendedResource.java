package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveTypeQueryService;
import org.soptorshi.service.extended.LeaveTypeExtendedService;
import org.soptorshi.web.rest.LeaveTypeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing LeaveType.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveTypeExtendedResource extends LeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeExtendedResource.class);

    private static final String ENTITY_NAME = "leaveType";

    public LeaveTypeExtendedResource(LeaveTypeExtendedService leaveTypeExtendedService, LeaveTypeQueryService leaveTypeQueryService) {
        super(leaveTypeExtendedService, leaveTypeQueryService);
    }

    /**
     * DELETE  /leave-types/:id : delete the "id" leaveType.
     *
     * @param id the id of the leaveTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-types/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete LeaveType : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }

}
