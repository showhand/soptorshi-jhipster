package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.extended.AttendanceExtendedService;
import org.soptorshi.web.rest.AttendanceResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/extended")
public class AttendanceExtendedResource extends AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceExtendedResource.class);

    private final AttendanceExtendedService attendanceExtendedService;

    private static  final String ENTITY_NAME = "attendance";

    public AttendanceExtendedResource(AttendanceExtendedService attendanceExtendedService, AttendanceQueryService attendanceQueryService) {
        super(attendanceExtendedService, attendanceQueryService);
        this.attendanceExtendedService = attendanceExtendedService;
    }

    @GetMapping("/attendances/dates")
    public ResponseEntity<List<LocalDate>> getAttendanceDates() {
        log.debug("REST request to get distinct Attendances:");
        List<LocalDate> attendanceDTOS = attendanceExtendedService.getAllDistinctAttendanceDate();
        return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(attendanceDTOS);
    }

    /**
     * DELETE  /attendances/:id : delete the "id" attendance.
     *
     * @param id the id of the attendanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendances/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}

