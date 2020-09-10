package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.LeaveTypeQueryService;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.extended.LeaveTypeExtendedService;
import org.soptorshi.web.rest.LeaveTypeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing LeaveType.
 */
@RestController
@RequestMapping("/api/extended")
public class LeaveTypeExtendedResource extends LeaveTypeResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeExtendedResource.class);

    private static final String ENTITY_NAME = "leaveType";

    private final LeaveTypeExtendedService leaveTypeExtendedService;

    public LeaveTypeExtendedResource(LeaveTypeExtendedService leaveTypeExtendedService, LeaveTypeQueryService leaveTypeQueryService) {
        super(leaveTypeExtendedService, leaveTypeQueryService);
        this.leaveTypeExtendedService = leaveTypeExtendedService;
    }

    @PostMapping("/leave-types")
    public ResponseEntity<LeaveTypeDTO> createLeaveType(@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveType : {}", leaveTypeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveTypeDTO result = leaveTypeExtendedService.save(leaveTypeDTO);
        return ResponseEntity.created(new URI("/api/leave-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/leave-types")
    public ResponseEntity<LeaveTypeDTO> updateLeaveType(@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveType : {}", leaveTypeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (leaveTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveTypeDTO result = leaveTypeExtendedService.save(leaveTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveTypeDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/leave-types/{id}")
    public ResponseEntity<Void> deleteLeaveType(@PathVariable Long id) {
        log.debug("REST request to delete LeaveType : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        leaveTypeExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
