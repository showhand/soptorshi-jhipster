package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyChallanQueryService;
import org.soptorshi.service.extended.SupplyChallanExtendedService;
import org.soptorshi.web.rest.SupplyChallanResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

/**
 * REST controller for managing SupplyChallan.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyChallanExtendedResource extends SupplyChallanResource {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanExtendedResource.class);

    private static final String ENTITY_NAME = "supplyChallan";

    private final SupplyChallanExtendedService supplyChallanService;

    private final SupplyChallanQueryService supplyChallanQueryService;

    public SupplyChallanExtendedResource(SupplyChallanExtendedService supplyChallanService, SupplyChallanQueryService supplyChallanQueryService) {
        super(supplyChallanService, supplyChallanQueryService);
        this.supplyChallanService = supplyChallanService;
        this.supplyChallanQueryService = supplyChallanQueryService;
    }

    @GetMapping(value = "/supply-challans/download/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadChallan(@PathVariable Long id) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/supply-challans");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
