package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.PurchaseOrderQueryService;
import org.soptorshi.service.PurchaseOrderService;
import org.soptorshi.service.dto.PurchaseOrderDTO;
import org.soptorshi.service.extended.PurchaseOrderExtendedService;
import org.soptorshi.web.rest.PurchaseOrderResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class PurchaseOrderExtendedResource {
    private final Logger log = LoggerFactory.getLogger(PurchaseOrderExtendedResource.class);

    private static final String ENTITY_NAME = "purchaseOrder";

    private final PurchaseOrderExtendedService purchaseOrderService;

    private final PurchaseOrderQueryService purchaseOrderQueryService;

    public PurchaseOrderExtendedResource(PurchaseOrderExtendedService purchaseOrderService, PurchaseOrderQueryService purchaseOrderQueryService) {
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderQueryService = purchaseOrderQueryService;
    }


    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrderDTO result = purchaseOrderService.save(purchaseOrderDTO);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrder : {}", purchaseOrderDTO);
        if (purchaseOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrderDTO result = purchaseOrderService.save(purchaseOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrderDTO.getId().toString()))
            .body(result);
    }
}
