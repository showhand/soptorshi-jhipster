package org.soptorshi.web.rest;
import org.soptorshi.service.VoucherService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.VoucherDTO;
import org.soptorshi.service.dto.VoucherCriteria;
import org.soptorshi.service.VoucherQueryService;
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
 * REST controller for managing Voucher.
 */
@RestController
@RequestMapping("/api")
public class VoucherResource {

    private final Logger log = LoggerFactory.getLogger(VoucherResource.class);

    private static final String ENTITY_NAME = "voucher";

    private final VoucherService voucherService;

    private final VoucherQueryService voucherQueryService;

    public VoucherResource(VoucherService voucherService, VoucherQueryService voucherQueryService) {
        this.voucherService = voucherService;
        this.voucherQueryService = voucherQueryService;
    }

    /**
     * POST  /vouchers : Create a new voucher.
     *
     * @param voucherDTO the voucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherDTO, or with status 400 (Bad Request) if the voucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vouchers")
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) throws URISyntaxException {
        log.debug("REST request to save Voucher : {}", voucherDTO);
        if (voucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherDTO result = voucherService.save(voucherDTO);
        return ResponseEntity.created(new URI("/api/vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vouchers : Updates an existing voucher.
     *
     * @param voucherDTO the voucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherDTO,
     * or with status 400 (Bad Request) if the voucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vouchers")
    public ResponseEntity<VoucherDTO> updateVoucher(@RequestBody VoucherDTO voucherDTO) throws URISyntaxException {
        log.debug("REST request to update Voucher : {}", voucherDTO);
        if (voucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoucherDTO result = voucherService.save(voucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vouchers : get all the vouchers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vouchers in body
     */
    @GetMapping("/vouchers")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers(VoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vouchers by criteria: {}", criteria);
        Page<VoucherDTO> page = voucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /vouchers/count : count all the vouchers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/vouchers/count")
    public ResponseEntity<Long> countVouchers(VoucherCriteria criteria) {
        log.debug("REST request to count Vouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /vouchers/:id : get the "id" voucher.
     *
     * @param id the id of the voucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vouchers/{id}")
    public ResponseEntity<VoucherDTO> getVoucher(@PathVariable Long id) {
        log.debug("REST request to get Voucher : {}", id);
        Optional<VoucherDTO> voucherDTO = voucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherDTO);
    }

    /**
     * DELETE  /vouchers/:id : delete the "id" voucher.
     *
     * @param id the id of the voucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        log.debug("REST request to delete Voucher : {}", id);
        voucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vouchers?query=:query : search for the voucher corresponding
     * to the query.
     *
     * @param query the query of the voucher search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vouchers")
    public ResponseEntity<List<VoucherDTO>> searchVouchers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Vouchers for query {}", query);
        Page<VoucherDTO> page = voucherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
