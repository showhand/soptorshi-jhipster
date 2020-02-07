package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.PaymentVoucherQueryService;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.extended.PaymentVoucherExtendedService;
import org.soptorshi.web.rest.PaymentVoucherResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/extended")
public class PaymentVoucherExtendedResource {
    private final Logger log = LoggerFactory.getLogger(PaymentVoucherExtendedResource.class);

    private static final String ENTITY_NAME = "paymentVoucher";

    private final PaymentVoucherExtendedService paymentVoucherService;

    private final PaymentVoucherQueryService paymentVoucherQueryService;

    public PaymentVoucherExtendedResource(PaymentVoucherExtendedService paymentVoucherService, PaymentVoucherQueryService paymentVoucherQueryService) {
        this.paymentVoucherService = paymentVoucherService;
        this.paymentVoucherQueryService = paymentVoucherQueryService;
    }

    /**
     * POST  /payment-vouchers : Create a new paymentVoucher.
     *
     * @param paymentVoucherDTO the paymentVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentVoucherDTO, or with status 400 (Bad Request) if the paymentVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-vouchers")
    public ResponseEntity<PaymentVoucherDTO> createPaymentVoucher(@RequestBody PaymentVoucherDTO paymentVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentVoucher : {}", paymentVoucherDTO);
        if (paymentVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentVoucherDTO result = paymentVoucherService.save(paymentVoucherDTO);
        return ResponseEntity.created(new URI("/api/payment-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-vouchers : Updates an existing paymentVoucher.
     *
     * @param paymentVoucherDTO the paymentVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentVoucherDTO,
     * or with status 400 (Bad Request) if the paymentVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-vouchers")
    public ResponseEntity<PaymentVoucherDTO> updatePaymentVoucher(@RequestBody PaymentVoucherDTO paymentVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentVoucher : {}", paymentVoucherDTO);
        if (paymentVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentVoucherDTO result = paymentVoucherService.save(paymentVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentVoucherDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/payment-vouchers/voucherNo/{voucherNo}")
    public ResponseEntity<PaymentVoucherDTO> getJournalVoucher(@PathVariable String voucherNo) {
        log.debug("REST request to get JournalVoucher by voucher no: {}", voucherNo);
        Optional<PaymentVoucherDTO> paymentVoucherDTO = paymentVoucherService.findByVoucherNo(voucherNo);
        return ResponseUtil.wrapOrNotFound(paymentVoucherDTO);
    }

}
