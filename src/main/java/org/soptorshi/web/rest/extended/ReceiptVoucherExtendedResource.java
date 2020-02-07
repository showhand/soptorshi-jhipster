package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ReceiptVoucherQueryService;
import org.soptorshi.service.ReceiptVoucherService;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.extended.ReceiptVoucherExtendedService;
import org.soptorshi.web.rest.ReceiptVoucherResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/extended")
public class ReceiptVoucherExtendedResource {
    private final Logger log = LoggerFactory.getLogger(ReceiptVoucherExtendedResource.class);

    private static final String ENTITY_NAME = "receiptVoucher";

    private final ReceiptVoucherExtendedService receiptVoucherService;

    private final ReceiptVoucherQueryService receiptVoucherQueryService;

    public ReceiptVoucherExtendedResource(ReceiptVoucherExtendedService receiptVoucherService, ReceiptVoucherQueryService receiptVoucherQueryService) {
        this.receiptVoucherService = receiptVoucherService;
        this.receiptVoucherQueryService = receiptVoucherQueryService;
    }

    /**
     * POST  /receipt-vouchers : Create a new receiptVoucher.
     *
     * @param receiptVoucherDTO the receiptVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptVoucherDTO, or with status 400 (Bad Request) if the receiptVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/receipt-vouchers")
    public ResponseEntity<ReceiptVoucherDTO> createReceiptVoucher(@RequestBody ReceiptVoucherDTO receiptVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save ReceiptVoucher : {}", receiptVoucherDTO);
        if (receiptVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new receiptVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReceiptVoucherDTO result = receiptVoucherService.save(receiptVoucherDTO);
        return ResponseEntity.created(new URI("/api/receipt-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipt-vouchers : Updates an existing receiptVoucher.
     *
     * @param receiptVoucherDTO the receiptVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receiptVoucherDTO,
     * or with status 400 (Bad Request) if the receiptVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the receiptVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/receipt-vouchers")
    public ResponseEntity<ReceiptVoucherDTO> updateReceiptVoucher(@RequestBody ReceiptVoucherDTO receiptVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update ReceiptVoucher : {}", receiptVoucherDTO);
        if (receiptVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReceiptVoucherDTO result = receiptVoucherService.save(receiptVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receiptVoucherDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/receipt-vouchers/voucherNo/{voucherNo}")
    public ResponseEntity<ReceiptVoucherDTO> getReceiptVoucher(@PathVariable String voucherNo) {
        log.debug("REST request to get JournalVoucher by voucher no: {}", voucherNo);
        Optional<ReceiptVoucherDTO> paymentVoucherDTO = receiptVoucherService.findByVoucherNo(voucherNo);
        return ResponseUtil.wrapOrNotFound(paymentVoucherDTO);
    }

}
