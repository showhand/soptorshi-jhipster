package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AttendanceExcelUploadQueryService;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.extended.AttendanceExcelUploadExtendedService;
import org.soptorshi.web.rest.AttendanceExcelUploadResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing AttendanceExcelUpload.
 */
@RestController
@RequestMapping("/api/extended")
public class AttendanceExcelUploadExtendedResource extends AttendanceExcelUploadResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadExtendedResource.class);

    private static final String ENTITY_NAME = "attendanceExcelUpload";

    private final AttendanceExcelUploadExtendedService attendanceExcelUploadExtendedService;

    public AttendanceExcelUploadExtendedResource(AttendanceExcelUploadExtendedService attendanceExcelUploadExtendedService, AttendanceExcelUploadQueryService attendanceExcelUploadQueryService) {
        super(attendanceExcelUploadExtendedService, attendanceExcelUploadQueryService);
        this.attendanceExcelUploadExtendedService = attendanceExcelUploadExtendedService;
    }

    @PostMapping("/attendance-excel-uploads")
    public ResponseEntity<AttendanceExcelUploadDTO> createAttendanceExcelUpload(@Valid @RequestBody AttendanceExcelUploadDTO attendanceExcelUploadDTO) throws URISyntaxException {
        log.debug("REST request to save AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (attendanceExcelUploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendanceExcelUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceExcelUploadDTO result = attendanceExcelUploadExtendedService.save(attendanceExcelUploadDTO);
        return ResponseEntity.created(new URI("/api/attendance-excel-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/attendance-excel-uploads")
    public ResponseEntity<AttendanceExcelUploadDTO> updateAttendanceExcelUpload(@Valid @RequestBody AttendanceExcelUploadDTO attendanceExcelUploadDTO) throws URISyntaxException {
        log.debug("REST request to update AttendanceExcelUpload : {}", attendanceExcelUploadDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        AttendanceExcelUploadDTO result = attendanceExcelUploadExtendedService.save(attendanceExcelUploadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceExcelUploadDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/attendance-excel-uploads/{id}")
    public ResponseEntity<Void> deleteAttendanceExcelUpload(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceExcelUpload : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        attendanceExcelUploadExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
