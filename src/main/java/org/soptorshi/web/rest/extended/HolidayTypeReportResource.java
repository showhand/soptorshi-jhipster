package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.extended.HolidayTypeReportService;
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
public class HolidayTypeReportResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeReportResource.class);

    private final HolidayTypeReportService holidayTypeReportService;

    public HolidayTypeReportResource(HolidayTypeReportService holidayTypeReportService) {
        this.holidayTypeReportService = holidayTypeReportService;
    }

    @GetMapping(value = "/holiday-types/report/all", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllHolidayTypes() throws Exception, DocumentException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.HOLIDAY_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", "HolidayTypes", "invalidaccess");
        }
        ByteArrayInputStream byteArrayInputStream = holidayTypeReportService.getAllHolidayTypes();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/holiday-types/report/all");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
