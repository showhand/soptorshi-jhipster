package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceExcelUploadQueryService;
import org.soptorshi.service.extended.AttendanceExcelUploadExtendedService;
import org.soptorshi.web.rest.AttendanceExcelUploadResource;
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
}
