package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.HolidayTypeQueryService;
import org.soptorshi.service.extended.HolidayTypeExtendedService;
import org.soptorshi.web.rest.HolidayTypeResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing HolidayType.
 */
@RestController
@RequestMapping("/api/extended")
public class HolidayTypeExtendedResource extends HolidayTypeResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeExtendedResource.class);

    private static final String ENTITY_NAME = "holidayType";

    public HolidayTypeExtendedResource(HolidayTypeExtendedService holidayTypeExtendedService, HolidayTypeQueryService holidayTypeQueryService) {
        super(holidayTypeExtendedService, holidayTypeQueryService);
    }

    /**
     * DELETE  /holiday-types/:id : delete the "id" holidayType.
     *
     * @param id the id of the holidayTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/holiday-types/{id}")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable Long id) {
        log.debug("REST request to delete HolidayType : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
