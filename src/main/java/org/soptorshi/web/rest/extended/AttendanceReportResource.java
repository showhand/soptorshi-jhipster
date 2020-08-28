package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.AttendanceReportService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/extended")
public class AttendanceReportResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceReportResource.class);

    private final AttendanceReportService attendanceReportService;

    public AttendanceReportResource(AttendanceReportService attendanceReportService) {
        this.attendanceReportService = attendanceReportService;
    }

    @GetMapping(value = "/attendances/report/fromDate/{fromDate}/toDate/{toDate}/employeeId/{employeeId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendances(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String employeeId) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "Attendances", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = attendanceReportService.getAttendances(fromDate, toDate, employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendances/report/fromDate/" + fromDate + "/toDate/" + toDate + "/employeeId/" + employeeId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendances/report/fromDate/{fromDate}/toDate/{toDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendances(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "Attendances", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = attendanceReportService.getAttendances(fromDate, toDate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendances/report/fromDate/" + fromDate + "/toDate/" + toDate);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendances/report/employeeId/{employeeId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendances(@PathVariable String employeeId) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ATTENDANCE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "Attendances", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = attendanceReportService.getAttendances(employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendances/report/employeeId/" + employeeId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendances/report/fromDate/{fromDate}/toDate/{toDate}/loggedInUser", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendancesByLoggedInUser(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) throws Exception, DocumentException {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : null;
        if(currentUser != null) {
            ByteArrayInputStream byteArrayInputStream = attendanceReportService.getAttendances(fromDate, toDate, currentUser);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "/attendances/report/fromDate/" + fromDate + "/toDate/" + toDate + "/loggedInUser");
            return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
        }
        return null;
    }
}
