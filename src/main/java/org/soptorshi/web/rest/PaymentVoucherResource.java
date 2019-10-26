package org.soptorshi.web.rest;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.dto.PaymentVoucherCriteria;
import org.soptorshi.service.PaymentVoucherQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PaymentVoucher.
 */
@RestController
@RequestMapping("/api")
public class PaymentVoucherResource {

    private final Logger log = LoggerFactory.getLogger(PaymentVoucherResource.class);

    private static final String ENTITY_NAME = "paymentVoucher";

    private final PaymentVoucherService paymentVoucherService;

    private final PaymentVoucherQueryService paymentVoucherQueryService;

    public PaymentVoucherResource(PaymentVoucherService paymentVoucherService, PaymentVoucherQueryService paymentVoucherQueryService) {
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

    /**
     * GET  /payment-vouchers : get all the paymentVouchers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of paymentVouchers in body
     */
    @GetMapping("/payment-vouchers")
    public ResponseEntity<List<PaymentVoucherDTO>> getAllPaymentVouchers(PaymentVoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentVouchers by criteria: {}", criteria);
        Page<PaymentVoucherDTO> page = paymentVoucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /payment-vouchers/count : count all the paymentVouchers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/payment-vouchers/count")
    public ResponseEntity<Long> countPaymentVouchers(PaymentVoucherCriteria criteria) {
        log.debug("REST request to count PaymentVouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentVoucherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /payment-vouchers/:id : get the "id" paymentVoucher.
     *
     * @param id the id of the paymentVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-vouchers/{id}")
    public ResponseEntity<PaymentVoucherDTO> getPaymentVoucher(@PathVariable Long id) {
        log.debug("REST request to get PaymentVoucher : {}", id);
        Optional<PaymentVoucherDTO> paymentVoucherDTO = paymentVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentVoucherDTO);
    }

    /**
     * DELETE  /payment-vouchers/:id : delete the "id" paymentVoucher.
     *
     * @param id the id of the paymentVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-vouchers/{id}")
    public ResponseEntity<Void> deletePaymentVoucher(@PathVariable Long id) {
        log.debug("REST request to delete PaymentVoucher : {}", id);
        paymentVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/payment-vouchers?query=:query : search for the paymentVoucher corresponding
     * to the query.
     *
     * @param query the query of the paymentVoucher search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/payment-vouchers")
    public ResponseEntity<List<PaymentVoucherDTO>> searchPaymentVouchers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PaymentVouchers for query {}", query);
        Page<PaymentVoucherDTO> page = paymentVoucherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/payment-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
