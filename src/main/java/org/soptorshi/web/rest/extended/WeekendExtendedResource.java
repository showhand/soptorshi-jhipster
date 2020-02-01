package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.WeekendQueryService;
import org.soptorshi.service.extended.WeekendExtendedService;
import org.soptorshi.web.rest.WeekendResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Weekend.
 */
@RestController
@RequestMapping("/api/extended")
public class WeekendExtendedResource extends WeekendResource {

    private final Logger log = LoggerFactory.getLogger(WeekendExtendedResource.class);

    private static final String ENTITY_NAME = "weekend";

    public WeekendExtendedResource(WeekendExtendedService weekendExtendedService, WeekendQueryService weekendQueryService) {
        super(weekendExtendedService, weekendQueryService);
    }

    /**
     * DELETE  /weekends/:id : delete the "id" weekend.
     *
     * @param id the id of the weekendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weekends/{id}")
    public ResponseEntity<Void> deleteWeekend(@PathVariable Long id) {
        log.debug("REST request to delete Weekend : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
