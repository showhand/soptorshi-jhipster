package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.extended.AttendanceExtendedService;
import org.soptorshi.web.rest.AttendanceResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extended")
public class AttendanceExtendedResource extends AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceExtendedResource.class);

    private static  final String ENTITY_NAME = "attendance";

    public AttendanceExtendedResource(AttendanceExtendedService attendanceExtendedService, AttendanceQueryService attendanceQueryService) {
        super(attendanceExtendedService, attendanceQueryService);
    }
}

