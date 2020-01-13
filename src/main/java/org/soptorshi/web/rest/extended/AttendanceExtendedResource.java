package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.extended.AttendanceExtendedService;
import org.soptorshi.web.rest.AttendanceResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/attendances/distinct")
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        log.debug("REST request to get distinct Attendances:");
        List<AttendanceDTO> attendanceDTOS = attendanceExtendedService.getAllByDistinctAttendanceDate();
        return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(attendanceDTOS);
    }
}

