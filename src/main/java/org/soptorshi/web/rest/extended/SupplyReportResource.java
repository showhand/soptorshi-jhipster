package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.extended.SupplyReportService;
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
public class SupplyReportResource {

    private final Logger log = LoggerFactory.getLogger(SupplyReportResource.class);

    private final SupplyReportService supplyReportService;

    public SupplyReportResource(SupplyReportService supplyReportService) {
        this.supplyReportService = supplyReportService;
    }

    @GetMapping(value = "/supply-report/download/from/{fromDate}/to/{toDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadReport(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = supplyReportService.downloadReport(fromDate, toDate);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/supply-report");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
