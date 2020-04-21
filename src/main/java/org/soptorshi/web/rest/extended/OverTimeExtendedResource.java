package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.OverTimeQueryService;
import org.soptorshi.service.extended.OverTimeExtendedService;
import org.soptorshi.web.rest.OverTimeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/over-times/dates")
    public ResponseEntity<List<LocalDate>> getAttendanceDates() {
        log.debug("REST request to get distinct Attendances:");
        List<LocalDate> overTimeDTOS = overTimeExtendedService.getAllDistinctOverTimeDate();
        return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(overTimeDTOS);
    }

    /**
     * DELETE  /over-times/:id : delete the "id" overTime.
     *
     * @param id the id of the overTimeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/over-times/{id}")
    public ResponseEntity<Void> deleteOverTime(@PathVariable Long id) {
        log.debug("REST request to delete OverTime : {}", id);
        throw new BadRequestAlertException("Delete operation not allowed", ENTITY_NAME, "idnull");
    }
}
