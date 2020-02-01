package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceExcelUploadQueryService;
import org.soptorshi.service.extended.AttendanceExcelUploadExtendedService;
import org.soptorshi.web.rest.AttendanceExcelUploadResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing AttendanceExcelUpload.
 */
@RestController
@RequestMapping("/api/extended")
public class AttendanceExcelUploadExtendedResource extends AttendanceExcelUploadResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceExcelUploadExtendedResource.class);

    private static final String ENTITY_NAME = "attendanceExcelUpload";

    public AttendanceExcelUploadExtendedResource(AttendanceExcelUploadExtendedService attendanceExcelUploadExtendedService, AttendanceExcelUploadQueryService attendanceExcelUploadQueryService) {
        super(attendanceExcelUploadExtendedService, attendanceExcelUploadQueryService);
    }

    /**
     * DELETE  /attendance-excel-uploads/:id : delete the "id" attendanceExcelUpload.
     *
     * @param id the id of the attendanceExcelUploadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendance-excel-uploads/{id}")
    public ResponseEntity<Void> deleteAttendanceExcelUpload(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceExcelUpload : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
