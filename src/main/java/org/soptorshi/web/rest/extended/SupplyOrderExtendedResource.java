package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.service.SupplyOrderQueryService;
import org.soptorshi.service.extended.SupplyOrderExtendedService;
import org.soptorshi.web.rest.SupplyOrderResource;
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
import java.util.List;

/**
 * REST controller for managing SupplyOrder.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyOrderExtendedResource extends SupplyOrderResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedResource.class);

    private static final String ENTITY_NAME = "supplyOrder";

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    public SupplyOrderExtendedResource(SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderQueryService supplyOrderQueryService) {
        super(supplyOrderExtendedService, supplyOrderQueryService);
        this.supplyOrderExtendedService = supplyOrderExtendedService;
    }

    @GetMapping("/supply-orders/dates")
    public ResponseEntity<List<LocalDate>> getSupplyOrderDates() {
        log.debug("REST request to get distinct Supply Order Dates:");
        List<LocalDate> supplyOrderDates = supplyOrderExtendedService.getAllDistinctSupplyOrderDate();
        return ResponseEntity.ok().headers(HttpHeaders.EMPTY).body(supplyOrderDates);
    }

    @GetMapping("/supply-orders/referenceNo/{refNo}/fromDate/{fromDate}/toDate/{toDate}/status/{status}")
    public ResponseEntity<Long> saveAndGetSupplyOrders(@PathVariable String refNo, @PathVariable LocalDate fromDate, @PathVariable LocalDate toDate, @PathVariable SupplyOrderStatus status) {
        log.debug("REST request to save and get values");
        return ResponseEntity.ok().body(supplyOrderExtendedService.updateReferenceNoAfterFilterByDate(refNo, fromDate, toDate, status));
    }

    @GetMapping(value = "/supply-orders/download/referenceNo/{refNo}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadAccumulatedOrders(@PathVariable String refNo) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = supplyOrderExtendedService.downloadAccumulatedOrders(refNo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/supply-orders");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
