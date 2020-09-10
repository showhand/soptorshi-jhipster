package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.LeaveReportService;
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
public class LeaveReportResource {

    private final Logger log = LoggerFactory.getLogger(LeaveReportResource.class);

    private final LeaveReportService leaveReportService;

    public LeaveReportResource(LeaveReportService leaveReportService) {
        this.leaveReportService = leaveReportService;
    }

    @GetMapping(value = "/leave-applications/history/report/fromDate/{fromDate}/toDate/{toDate}/employeeId/{employeeId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllLeaveApplications(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable String employeeId) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "LeaveApplications", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = leaveReportService.getAllLeaveApplications(fromDate, toDate, employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-applications/history/report/fromDate/" + fromDate + "/toDate/" + toDate + "/employeeId/" + employeeId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/leave-applications/history/report/fromDate/{fromDate}/toDate/{toDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllLeaveApplications(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", "LeaveApplications", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = leaveReportService.getAllLeaveApplications(fromDate, toDate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-applications/history/report/fromDate/" + fromDate + "/toDate/" + toDate);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/leave-applications/balance/report/year/{year}/employeeId/{employeeId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllLeaveBalance(@PathVariable int year, @PathVariable String employeeId) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "LeaveApplications", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = leaveReportService.getAllLeaveBalance(year, employeeId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-applications/balance/report/year/" + year + "/employeeId/" + employeeId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/leave-applications/balance/report/year/{year}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllLeaveBalance(@PathVariable int year) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.LEAVE_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", "LeaveApplications", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = leaveReportService.getAllLeaveBalance(year);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-applications/balance/report/year/" + year);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
