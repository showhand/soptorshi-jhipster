package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.HolidayQueryService;
import org.soptorshi.service.dto.HolidayDTO;
import org.soptorshi.service.extended.HolidayExtendedService;
import org.soptorshi.web.rest.HolidayResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Holiday.
 */
@RestController
@RequestMapping("/api/extended")
public class HolidayExtendedResource extends HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayExtendedResource.class);

    private static final String ENTITY_NAME = "holiday";

    private final HolidayExtendedService holidayExtendedService;

    public HolidayExtendedResource(HolidayExtendedService holidayExtendedService, HolidayQueryService holidayQueryService) {
        super(holidayExtendedService, holidayQueryService);
        this.holidayExtendedService = holidayExtendedService;
    }

    @PostMapping("/holidays")
    public ResponseEntity<HolidayDTO> createHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holidayDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (holidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayDTO result = holidayExtendedService.save(holidayDTO);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/holidays")
    public ResponseEntity<HolidayDTO> updateHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holidayDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (holidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayDTO result = holidayExtendedService.save(holidayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, holidayDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN)) {
            throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
        }
        holidayExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
