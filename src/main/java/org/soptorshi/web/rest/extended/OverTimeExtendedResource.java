package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.OverTimeQueryService;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.extended.OverTimeExtendedService;
import org.soptorshi.web.rest.OverTimeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * REST controller for managing OverTime.
 */
@RestController
@RequestMapping("/api/extended")
public class OverTimeExtendedResource extends OverTimeResource {

    private final Logger log = LoggerFactory.getLogger(OverTimeExtendedResource.class);

    private static final String ENTITY_NAME = "overTime";

    private final OverTimeExtendedService overTimeExtendedService;

    private final OverTimeQueryService overTimeQueryService;

    public OverTimeExtendedResource(OverTimeExtendedService overTimeExtendedService, OverTimeQueryService overTimeQueryService) {
        super(overTimeExtendedService, overTimeQueryService);
        this.overTimeExtendedService = overTimeExtendedService;
        this.overTimeQueryService = overTimeQueryService;
    }

    @PostMapping("/over-times")
    public ResponseEntity<OverTimeDTO> createOverTime(@Valid @RequestBody OverTimeDTO overTimeDTO) throws URISyntaxException {
        log.debug("REST request to save OverTime : {}", overTimeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OVERTIME_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OVERTIME_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (overTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new overTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate currentDate = LocalDate.now();
        if(overTimeDTO.getOverTimeDate().compareTo(currentDate) > 0) {
            throw new BadRequestAlertException("Please check the over time date", ENTITY_NAME, "idexists");
        }
        LocalDate ld1 = LocalDateTime.ofInstant(overTimeDTO.getFromTime(), ZoneId.systemDefault()).toLocalDate();
        LocalDate ld2 = LocalDateTime.ofInstant(overTimeDTO.getToTime(), ZoneId.systemDefault()).toLocalDate();
        if(!(ld1.isEqual(overTimeDTO.getOverTimeDate()) && ld2.isEqual(overTimeDTO.getOverTimeDate()))) {
            throw new BadRequestAlertException("Please check the over time date with from and to dates", ENTITY_NAME, "idexists");
        }
        if(!overTimeDTO.getFromTime().isBefore(overTimeDTO.getToTime())) {
            throw new BadRequestAlertException("Please check the from and to dates", ENTITY_NAME, "idexists");
        }
        OverTimeDTO result = overTimeExtendedService.save(overTimeDTO);
        return ResponseEntity.created(new URI("/api/over-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/over-times")
    public ResponseEntity<OverTimeDTO> updateOverTime(@Valid @RequestBody OverTimeDTO overTimeDTO) throws URISyntaxException {
        log.debug("REST request to update OverTime : {}", overTimeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OVERTIME_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OVERTIME_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (overTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalDate currentDate = LocalDate.now();
        if(overTimeDTO.getOverTimeDate().compareTo(currentDate) > 0) {
            throw new BadRequestAlertException("Please check the over time date", ENTITY_NAME, "idexists");
        }
        LocalDate ld1 = LocalDateTime.ofInstant(overTimeDTO.getFromTime(), ZoneId.systemDefault()).toLocalDate();
        LocalDate ld2 = LocalDateTime.ofInstant(overTimeDTO.getToTime(), ZoneId.systemDefault()).toLocalDate();
        if(!(ld1.isEqual(overTimeDTO.getOverTimeDate()) && ld2.isEqual(overTimeDTO.getOverTimeDate()))) {
            throw new BadRequestAlertException("Please check the over time date with from and to dates", ENTITY_NAME, "idexists");
        }
        if(!overTimeDTO.getFromTime().isBefore(overTimeDTO.getToTime())) {
            throw new BadRequestAlertException("Please check the from and to time", ENTITY_NAME, "idexists");
        }
        OverTimeDTO result = overTimeExtendedService.save(overTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, overTimeDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/over-times/{id}")
    public ResponseEntity<Void> deleteOverTime(@PathVariable Long id) {
        log.debug("REST request to delete OverTime : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OVERTIME_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        overTimeExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
