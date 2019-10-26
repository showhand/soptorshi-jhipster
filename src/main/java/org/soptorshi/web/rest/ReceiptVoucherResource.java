package org.soptorshi.web.rest;
import org.soptorshi.service.ReceiptVoucherService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.dto.ReceiptVoucherCriteria;
import org.soptorshi.service.ReceiptVoucherQueryService;
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
 * REST controller for managing ReceiptVoucher.
 */
@RestController
@RequestMapping("/api")
public class ReceiptVoucherResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptVoucherResource.class);

    private static final String ENTITY_NAME = "receiptVoucher";

    private final ReceiptVoucherService receiptVoucherService;

    private final ReceiptVoucherQueryService receiptVoucherQueryService;

    public ReceiptVoucherResource(ReceiptVoucherService receiptVoucherService, ReceiptVoucherQueryService receiptVoucherQueryService) {
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

    /**
     * GET  /receipt-vouchers : get all the receiptVouchers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of receiptVouchers in body
     */
    @GetMapping("/receipt-vouchers")
    public ResponseEntity<List<ReceiptVoucherDTO>> getAllReceiptVouchers(ReceiptVoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReceiptVouchers by criteria: {}", criteria);
        Page<ReceiptVoucherDTO> page = receiptVoucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipt-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /receipt-vouchers/count : count all the receiptVouchers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/receipt-vouchers/count")
    public ResponseEntity<Long> countReceiptVouchers(ReceiptVoucherCriteria criteria) {
        log.debug("REST request to count ReceiptVouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(receiptVoucherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /receipt-vouchers/:id : get the "id" receiptVoucher.
     *
     * @param id the id of the receiptVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiptVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/receipt-vouchers/{id}")
    public ResponseEntity<ReceiptVoucherDTO> getReceiptVoucher(@PathVariable Long id) {
        log.debug("REST request to get ReceiptVoucher : {}", id);
        Optional<ReceiptVoucherDTO> receiptVoucherDTO = receiptVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receiptVoucherDTO);
    }

    /**
     * DELETE  /receipt-vouchers/:id : delete the "id" receiptVoucher.
     *
     * @param id the id of the receiptVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/receipt-vouchers/{id}")
    public ResponseEntity<Void> deleteReceiptVoucher(@PathVariable Long id) {
        log.debug("REST request to delete ReceiptVoucher : {}", id);
        receiptVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/receipt-vouchers?query=:query : search for the receiptVoucher corresponding
     * to the query.
     *
     * @param query the query of the receiptVoucher search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/receipt-vouchers")
    public ResponseEntity<List<ReceiptVoucherDTO>> searchReceiptVouchers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ReceiptVouchers for query {}", query);
        Page<ReceiptVoucherDTO> page = receiptVoucherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/receipt-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
