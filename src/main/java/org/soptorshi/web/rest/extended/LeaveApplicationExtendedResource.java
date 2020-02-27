package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveApplicationQueryService;
import org.soptorshi.service.extended.LeaveApplicationExtendedService;
import org.soptorshi.web.rest.LeaveApplicationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for managing LeaveApplication.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveApplicationExtendedResource extends LeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationExtendedResource.class);

    private static final String ENTITY_NAME = "leaveApplication";

    private LeaveApplicationExtendedService leaveApplicationExtendedService;

    public LeaveApplicationExtendedResource(LeaveApplicationExtendedService leaveApplicationExtendedService, LeaveApplicationQueryService leaveApplicationQueryService) {
        super(leaveApplicationExtendedService, leaveApplicationQueryService);
        this.leaveApplicationExtendedService = leaveApplicationExtendedService;
    }

    @GetMapping("/leave-applications/calculateDiff/fromDate/{fromDate}/toDate/{toDate}")
    public ResponseEntity<Integer> calculateDifference(@PathVariable String fromDate, @PathVariable String toDate) {
        log.debug("REST request to calculate differences between two dates");
        return ResponseEntity.ok().body(leaveApplicationExtendedService.calcDiff(LocalDate.parse(fromDate), LocalDate.parse(toDate)));
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
