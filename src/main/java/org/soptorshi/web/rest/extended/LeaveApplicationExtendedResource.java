package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.LeaveApplicationQueryService;
import org.soptorshi.service.dto.LeaveApplicationDTO;
import org.soptorshi.service.extended.LeaveApplicationExtendedService;
import org.soptorshi.web.rest.LeaveApplicationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> createLeaveApplication(@Valid @RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveApplication : {}", leaveApplicationDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveApplicationDTO result = leaveApplicationExtendedService.save(leaveApplicationDTO);
        return ResponseEntity.created(new URI("/api/leave-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> updateLeaveApplication(@Valid @RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveApplication : {}", leaveApplicationDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveApplicationDTO result = leaveApplicationExtendedService.save(leaveApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveApplicationDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/leave-applications/calculateDiff/fromDate/{fromDate}/toDate/{toDate}")
    public ResponseEntity<Integer> calculateDifference(@PathVariable String fromDate, @PathVariable String toDate) {
        log.debug("REST request to calculate differences between two dates");
        return ResponseEntity.ok().body(leaveApplicationExtendedService.calcDiff(LocalDate.parse(fromDate), LocalDate.parse(toDate)));
    }

    @DeleteMapping("/leave-applications/{id}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApplication : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        leaveApplicationExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
