package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.HolidayQueryService;
import org.soptorshi.service.extended.HolidayExtendedService;
import org.soptorshi.web.rest.HolidayResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Holiday.
 */
@RestController
@RequestMapping("/api/extended")
public class HolidayExtendedResource extends HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayExtendedResource.class);

    private static final String ENTITY_NAME = "holiday";

    public HolidayExtendedResource(HolidayExtendedService holidayExtendedService, HolidayQueryService holidayQueryService) {
        super(holidayExtendedService, holidayQueryService);
    }

    /**
     * DELETE  /holidays/:id : delete the "id" holiday.
     *
     * @param id the id of the holidayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
