package org.soptorshi.web.rest.extended;


import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.PurchaseOrderQueryService;
import org.soptorshi.service.PurchaseOrderReportService;
import org.soptorshi.service.PurchaseOrderService;
import org.soptorshi.web.rest.PurchaseOrderResource;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.Document;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api")
public class PurchaseOrderReportResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderReportResource.class);

    private static final String ENTITY_NAME = "purchaseOrder";

    private final PurchaseOrderService purchaseOrderService;

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    private final PurchaseOrderReportService purchaseOrderReportService;

    public PurchaseOrderReportResource(PurchaseOrderService purchaseOrderService, PurchaseOrderQueryService purchaseOrderQueryService, PurchaseOrderReportService purchaseOrderReportService) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
        this.purchaseOrderReportService = purchaseOrderReportService;
    }

    @RequestMapping(value = "/purchase-order-report/{purchaseOrderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePurchaseOrderReport(@PathVariable("purchaseOrderId") Long purchaseOrderId) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = purchaseOrderReportService.createPurchaseOrderReport(purchaseOrderId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/purchase-orders");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

}
