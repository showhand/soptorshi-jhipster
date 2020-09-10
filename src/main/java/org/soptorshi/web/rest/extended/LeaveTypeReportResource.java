package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.LeaveTypeReportService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/extended")
public class LeaveTypeReportResource {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeReportResource.class);

    private final LeaveTypeReportService leaveTypeReportService;

    public LeaveTypeReportResource(LeaveTypeReportService leaveTypeReportService) {
        this.leaveTypeReportService = leaveTypeReportService;
    }

    @GetMapping(value = "/leave-types/report/all", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllHolidays() throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "leaveType", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = leaveTypeReportService.getAllLeaveTypes();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-types/report/all");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
