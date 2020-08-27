package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.WeekendQueryService;
import org.soptorshi.service.dto.WeekendDTO;
import org.soptorshi.service.extended.WeekendExtendedService;
import org.soptorshi.web.rest.WeekendResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Weekend.
 */
@RestController
@RequestMapping("/api/extended")
public class WeekendExtendedResource extends WeekendResource {

    private final Logger log = LoggerFactory.getLogger(WeekendExtendedResource.class);

    private static final String ENTITY_NAME = "weekend";

    private final WeekendExtendedService weekendExtendedService;

    public WeekendExtendedResource(WeekendExtendedService weekendExtendedService, WeekendQueryService weekendQueryService) {
        super(weekendExtendedService, weekendQueryService);
        this.weekendExtendedService = weekendExtendedService;
    }

    @PostMapping("/weekends")
    public ResponseEntity<WeekendDTO> createWeekend(@Valid @RequestBody WeekendDTO weekendDTO) throws URISyntaxException {
        log.debug("REST request to save Weekend : {}", weekendDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.WEEKEND_ADMIN) &&
        !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.WEEKEND_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (weekendDTO.getId() != null) {
            throw new BadRequestAlertException("A new weekend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeekendDTO result = weekendExtendedService.save(weekendDTO);
        return ResponseEntity.created(new URI("/api/weekends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/weekends")
    public ResponseEntity<WeekendDTO> updateWeekend(@Valid @RequestBody WeekendDTO weekendDTO) throws URISyntaxException {
        log.debug("REST request to update Weekend : {}", weekendDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.WEEKEND_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.WEEKEND_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (weekendDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeekendDTO result = weekendExtendedService.save(weekendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weekendDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/weekends/{id}")
    public ResponseEntity<Void> deleteWeekend(@PathVariable Long id) {
        log.debug("REST request to delete Weekend : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.WEEKEND_ADMIN)) {
            throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
        }
        weekendExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
