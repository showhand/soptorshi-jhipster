package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.HolidayReportService;
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

@RestController
@RequestMapping("/api/extended")
public class HolidayReportResource {

    private final Logger log = LoggerFactory.getLogger(HolidayReportResource.class);

    private final HolidayReportService holidayReportService;

    public HolidayReportResource(HolidayReportService holidayReportService) {
        this.holidayReportService = holidayReportService;
    }

    @GetMapping(value = "/holidays/report/all", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllHolidays() throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "Holidays", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = holidayReportService.getAllHolidays();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/holidays/report/all");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/holidays/report/year/{year}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getHolidays(@PathVariable int year) throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "Holidays", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = holidayReportService.getHolidays(year);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/holidays/report/year/" + year);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
