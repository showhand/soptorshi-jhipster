package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderQueryService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.extended.SupplyAccumulateOrderReportService;
import org.soptorshi.service.extended.SupplyOrderExtendedService;
import org.soptorshi.web.rest.SupplyOrderResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing SupplyOrder.
 */
@RestController
@RequestMapping("/api/extended")
public class SupplyOrderExtendedResource extends SupplyOrderResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedResource.class);

    private static final String ENTITY_NAME = "supplyOrder";

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyAccumulateOrderReportService supplyAccumulateOrderReportService;

    public SupplyOrderExtendedResource(SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderQueryService supplyOrderQueryService,
                                       SupplyAccumulateOrderReportService supplyAccumulateOrderReportService) {
        super(supplyOrderExtendedService, supplyOrderQueryService);
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyAccumulateOrderReportService = supplyAccumulateOrderReportService;
    }

    @PostMapping("/supply-orders")
    public ResponseEntity<SupplyOrderDTO> createSupplyOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyOrder : {}", supplyOrderDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!supplyOrderExtendedService.isValidInput(supplyOrderDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        supplyOrderDTO.setStatus(SupplyOrderStatus.ORDER_RECEIVED);
        SupplyOrderDTO result = supplyOrderExtendedService.save(supplyOrderDTO);
        return ResponseEntity.created(new URI("/api/supply-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-orders")
    public ResponseEntity<SupplyOrderDTO> updateSupplyOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyOrder : {}", supplyOrderDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        if (supplyOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!supplyOrderExtendedService.isValidInput(supplyOrderDTO)) {
            throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
        }
        if(!supplyOrderExtendedService.isValidStatus(supplyOrderDTO)) {
            throw new BadRequestAlertException("Invalid Request", ENTITY_NAME, "invalidaccess");
        }
        SupplyOrderDTO result = supplyOrderExtendedService.save(supplyOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyOrderDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/supply-orders/bulk")
    public ResponseEntity<List<SupplyOrderDTO>> updateBulkSupplyOrder(@Valid @RequestBody List<SupplyOrderDTO> supplyOrderDTOs) throws URISyntaxException {
        log.debug("REST request to update bulk SupplyOrder : {}", supplyOrderDTOs);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER))
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");

        for (SupplyOrderDTO supplyOrderDTO : supplyOrderDTOs) {
            if (supplyOrderDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
        }
        for (SupplyOrderDTO supplyOrderDTO : supplyOrderDTOs) {
            if (!supplyOrderExtendedService.isValidInput(supplyOrderDTO)) {
                throw new BadRequestAlertException("Invalid Input", ENTITY_NAME, "invalidaccess");
            }
            if (!supplyOrderExtendedService.isValidStatus(supplyOrderDTO)) {
                throw new BadRequestAlertException("Invalid Request", ENTITY_NAME, "invalidaccess");
            }
        }

        List<SupplyOrderDTO> results = new ArrayList<>();

        for (SupplyOrderDTO supplyOrderDTO : supplyOrderDTOs) {
            SupplyOrderDTO result = supplyOrderExtendedService.save(supplyOrderDTO);
            results.add(result);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyOrderDTOs.stream()
                .map(SupplyOrderDTO::getId).collect(Collectors.toList()).toString()))
            .body(results);
    }

    @DeleteMapping("/supply-orders/{id}")
    public ResponseEntity<Void> deleteSupplyOrder(@PathVariable Long id) {
        log.debug("REST request to delete SupplyOrder : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }

    @GetMapping(value = "/supply-orders/download/referenceNo/{refNo}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadAccumulatedOrders(@PathVariable String refNo) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = null;
        /*ByteArrayInputStream byteArrayInputStream = supplyAccumulateOrderReportService.downloadAccumulatedOrders(refNo);*/
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/supply-orders");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
