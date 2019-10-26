package org.soptorshi.web.rest;
import org.soptorshi.service.ContraVoucherService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.dto.ContraVoucherCriteria;
import org.soptorshi.service.ContraVoucherQueryService;
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
 * REST controller for managing ContraVoucher.
 */
@RestController
@RequestMapping("/api")
public class ContraVoucherResource {

    private final Logger log = LoggerFactory.getLogger(ContraVoucherResource.class);

    private static final String ENTITY_NAME = "contraVoucher";

    private final ContraVoucherService contraVoucherService;

    private final ContraVoucherQueryService contraVoucherQueryService;

    public ContraVoucherResource(ContraVoucherService contraVoucherService, ContraVoucherQueryService contraVoucherQueryService) {
        this.contraVoucherService = contraVoucherService;
        this.contraVoucherQueryService = contraVoucherQueryService;
    }

    /**
     * POST  /contra-vouchers : Create a new contraVoucher.
     *
     * @param contraVoucherDTO the contraVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contraVoucherDTO, or with status 400 (Bad Request) if the contraVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contra-vouchers")
    public ResponseEntity<ContraVoucherDTO> createContraVoucher(@RequestBody ContraVoucherDTO contraVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save ContraVoucher : {}", contraVoucherDTO);
        if (contraVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new contraVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContraVoucherDTO result = contraVoucherService.save(contraVoucherDTO);
        return ResponseEntity.created(new URI("/api/contra-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contra-vouchers : Updates an existing contraVoucher.
     *
     * @param contraVoucherDTO the contraVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contraVoucherDTO,
     * or with status 400 (Bad Request) if the contraVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the contraVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contra-vouchers")
    public ResponseEntity<ContraVoucherDTO> updateContraVoucher(@RequestBody ContraVoucherDTO contraVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update ContraVoucher : {}", contraVoucherDTO);
        if (contraVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContraVoucherDTO result = contraVoucherService.save(contraVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contraVoucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contra-vouchers : get all the contraVouchers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of contraVouchers in body
     */
    @GetMapping("/contra-vouchers")
    public ResponseEntity<List<ContraVoucherDTO>> getAllContraVouchers(ContraVoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContraVouchers by criteria: {}", criteria);
        Page<ContraVoucherDTO> page = contraVoucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contra-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /contra-vouchers/count : count all the contraVouchers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/contra-vouchers/count")
    public ResponseEntity<Long> countContraVouchers(ContraVoucherCriteria criteria) {
        log.debug("REST request to count ContraVouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(contraVoucherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /contra-vouchers/:id : get the "id" contraVoucher.
     *
     * @param id the id of the contraVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contraVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contra-vouchers/{id}")
    public ResponseEntity<ContraVoucherDTO> getContraVoucher(@PathVariable Long id) {
        log.debug("REST request to get ContraVoucher : {}", id);
        Optional<ContraVoucherDTO> contraVoucherDTO = contraVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contraVoucherDTO);
    }

    /**
     * DELETE  /contra-vouchers/:id : delete the "id" contraVoucher.
     *
     * @param id the id of the contraVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contra-vouchers/{id}")
    public ResponseEntity<Void> deleteContraVoucher(@PathVariable Long id) {
        log.debug("REST request to delete ContraVoucher : {}", id);
        contraVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contra-vouchers?query=:query : search for the contraVoucher corresponding
     * to the query.
     *
     * @param query the query of the contraVoucher search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contra-vouchers")
    public ResponseEntity<List<ContraVoucherDTO>> searchContraVouchers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContraVouchers for query {}", query);
        Page<ContraVoucherDTO> page = contraVoucherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contra-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
