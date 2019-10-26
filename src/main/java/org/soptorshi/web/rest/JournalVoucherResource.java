package org.soptorshi.web.rest;
import org.soptorshi.service.JournalVoucherService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.dto.JournalVoucherCriteria;
import org.soptorshi.service.JournalVoucherQueryService;
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
 * REST controller for managing JournalVoucher.
 */
@RestController
@RequestMapping("/api")
public class JournalVoucherResource {

    private final Logger log = LoggerFactory.getLogger(JournalVoucherResource.class);

    private static final String ENTITY_NAME = "journalVoucher";

    private final JournalVoucherService journalVoucherService;

    private final JournalVoucherQueryService journalVoucherQueryService;

    public JournalVoucherResource(JournalVoucherService journalVoucherService, JournalVoucherQueryService journalVoucherQueryService) {
        this.journalVoucherService = journalVoucherService;
        this.journalVoucherQueryService = journalVoucherQueryService;
    }

    /**
     * POST  /journal-vouchers : Create a new journalVoucher.
     *
     * @param journalVoucherDTO the journalVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journalVoucherDTO, or with status 400 (Bad Request) if the journalVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journal-vouchers")
    public ResponseEntity<JournalVoucherDTO> createJournalVoucher(@RequestBody JournalVoucherDTO journalVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save JournalVoucher : {}", journalVoucherDTO);
        if (journalVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new journalVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalVoucherDTO result = journalVoucherService.save(journalVoucherDTO);
        return ResponseEntity.created(new URI("/api/journal-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journal-vouchers : Updates an existing journalVoucher.
     *
     * @param journalVoucherDTO the journalVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journalVoucherDTO,
     * or with status 400 (Bad Request) if the journalVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the journalVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journal-vouchers")
    public ResponseEntity<JournalVoucherDTO> updateJournalVoucher(@RequestBody JournalVoucherDTO journalVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update JournalVoucher : {}", journalVoucherDTO);
        if (journalVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalVoucherDTO result = journalVoucherService.save(journalVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalVoucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journal-vouchers : get all the journalVouchers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of journalVouchers in body
     */
    @GetMapping("/journal-vouchers")
    public ResponseEntity<List<JournalVoucherDTO>> getAllJournalVouchers(JournalVoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JournalVouchers by criteria: {}", criteria);
        Page<JournalVoucherDTO> page = journalVoucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/journal-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /journal-vouchers/count : count all the journalVouchers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/journal-vouchers/count")
    public ResponseEntity<Long> countJournalVouchers(JournalVoucherCriteria criteria) {
        log.debug("REST request to count JournalVouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(journalVoucherQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /journal-vouchers/:id : get the "id" journalVoucher.
     *
     * @param id the id of the journalVoucherDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journalVoucherDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journal-vouchers/{id}")
    public ResponseEntity<JournalVoucherDTO> getJournalVoucher(@PathVariable Long id) {
        log.debug("REST request to get JournalVoucher : {}", id);
        Optional<JournalVoucherDTO> journalVoucherDTO = journalVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journalVoucherDTO);
    }

    /**
     * DELETE  /journal-vouchers/:id : delete the "id" journalVoucher.
     *
     * @param id the id of the journalVoucherDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journal-vouchers/{id}")
    public ResponseEntity<Void> deleteJournalVoucher(@PathVariable Long id) {
        log.debug("REST request to delete JournalVoucher : {}", id);
        journalVoucherService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/journal-vouchers?query=:query : search for the journalVoucher corresponding
     * to the query.
     *
     * @param query the query of the journalVoucher search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/journal-vouchers")
    public ResponseEntity<List<JournalVoucherDTO>> searchJournalVouchers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JournalVouchers for query {}", query);
        Page<JournalVoucherDTO> page = journalVoucherService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/journal-vouchers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
