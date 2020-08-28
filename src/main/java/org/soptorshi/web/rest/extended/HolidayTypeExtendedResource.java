package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.HolidayTypeQueryService;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.extended.HolidayTypeExtendedService;
import org.soptorshi.web.rest.HolidayTypeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing HolidayType.
 */
@RestController
@RequestMapping("/api/extended")
public class HolidayTypeExtendedResource extends HolidayTypeResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeExtendedResource.class);

    private static final String ENTITY_NAME = "holidayType";

    private final HolidayTypeExtendedService holidayTypeExtendedService;

    public HolidayTypeExtendedResource(HolidayTypeExtendedService holidayTypeExtendedService, HolidayTypeQueryService holidayTypeQueryService) {
        super(holidayTypeExtendedService, holidayTypeQueryService);
        this.holidayTypeExtendedService = holidayTypeExtendedService;
    }

    @PostMapping("/holiday-types")
    public ResponseEntity<HolidayTypeDTO> createHolidayType(@Valid @RequestBody HolidayTypeDTO holidayTypeDTO) throws URISyntaxException {
        log.debug("REST request to save HolidayType : {}", holidayTypeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (holidayTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new holidayType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayTypeDTO result = holidayTypeExtendedService.save(holidayTypeDTO);
        return ResponseEntity.created(new URI("/api/holiday-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/holiday-types")
    public ResponseEntity<HolidayTypeDTO> updateHolidayType(@Valid @RequestBody HolidayTypeDTO holidayTypeDTO) throws URISyntaxException {
        log.debug("REST request to update HolidayType : {}", holidayTypeDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (holidayTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayTypeDTO result = holidayTypeExtendedService.save(holidayTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, holidayTypeDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/holiday-types/{id}")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable Long id) {
        log.debug("REST request to delete HolidayType : {}", id);
        if(!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN)) {
            throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
        }
        holidayTypeExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
