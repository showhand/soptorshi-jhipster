package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.extended.AttendanceExtendedService;
import org.soptorshi.web.rest.AttendanceResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    @PostMapping("/attendances")
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendanceDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (attendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate currentDate = LocalDate.now();
        if(attendanceDTO.getAttendanceDate().compareTo(currentDate) > 0) {
            throw new BadRequestAlertException("Please check the attendance date", ENTITY_NAME, "idexists");
        }
        LocalDate ld1 = LocalDateTime.ofInstant(attendanceDTO.getInTime(), ZoneId.systemDefault()).toLocalDate();
        LocalDate ld2 = LocalDateTime.ofInstant(attendanceDTO.getOutTime(), ZoneId.systemDefault()).toLocalDate();
        if(!(ld1.isEqual(attendanceDTO.getAttendanceDate()) && ld2.isEqual(attendanceDTO.getAttendanceDate()))) {
            throw new BadRequestAlertException("Please check the attendance date with in and out dates", ENTITY_NAME, "idexists");
        }
        Instant currentTime = Instant.now();
        if(!(attendanceDTO.getInTime().isBefore(currentTime) && attendanceDTO.getOutTime().isBefore(currentTime))) {
            throw new BadRequestAlertException("Please check the in and out time. It should be less than current time", ENTITY_NAME, "idexists");
        }
        if(!attendanceDTO.getInTime().isBefore(attendanceDTO.getOutTime())) {
            throw new BadRequestAlertException("Please check the in and out time. It should be less than out time", ENTITY_NAME, "idexists");
        }
        AttendanceDTO result = attendanceExtendedService.save(attendanceDTO);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/attendances")
    public ResponseEntity<AttendanceDTO> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendanceDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (attendanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalDate currentDate = LocalDate.now();
        if(attendanceDTO.getAttendanceDate().compareTo(currentDate) > 0) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "idexists");
        }
        LocalDate ld1 = LocalDateTime.ofInstant(attendanceDTO.getInTime(), ZoneId.systemDefault()).toLocalDate();
        LocalDate ld2 = LocalDateTime.ofInstant(attendanceDTO.getOutTime(), ZoneId.systemDefault()).toLocalDate();
        if(!(ld1.isEqual(attendanceDTO.getAttendanceDate()) && ld2.isEqual(attendanceDTO.getAttendanceDate()))) {
            throw new BadRequestAlertException("Please check the attendance date with in and out dates", ENTITY_NAME, "idexists");
        }
        Instant currentTime = Instant.now();
        if(!(attendanceDTO.getInTime().isBefore(currentTime) && attendanceDTO.getOutTime().isBefore(currentTime))) {
            throw new BadRequestAlertException("Please check the in and out time. It should be less than current time", ENTITY_NAME, "idexists");
        }
        if(!attendanceDTO.getInTime().isBefore(attendanceDTO.getOutTime())) {
            throw new BadRequestAlertException("Please check the in and out time. It should be less than out time", ENTITY_NAME, "idexists");
        }
        AttendanceDTO result = attendanceExtendedService.save(attendanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/attendances/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        attendanceExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

