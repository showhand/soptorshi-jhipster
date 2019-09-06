package org.soptorshi.web.rest;
import org.soptorshi.service.DebtorLedgerService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DebtorLedgerDTO;
import org.soptorshi.service.dto.DebtorLedgerCriteria;
import org.soptorshi.service.DebtorLedgerQueryService;
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
 * REST controller for managing DebtorLedger.
 */
@RestController
@RequestMapping("/api")
public class DebtorLedgerResource {

    private final Logger log = LoggerFactory.getLogger(DebtorLedgerResource.class);

    private static final String ENTITY_NAME = "debtorLedger";

    private final DebtorLedgerService debtorLedgerService;

    private final DebtorLedgerQueryService debtorLedgerQueryService;

    public DebtorLedgerResource(DebtorLedgerService debtorLedgerService, DebtorLedgerQueryService debtorLedgerQueryService) {
        this.debtorLedgerService = debtorLedgerService;
        this.debtorLedgerQueryService = debtorLedgerQueryService;
    }

    /**
     * POST  /debtor-ledgers : Create a new debtorLedger.
     *
     * @param debtorLedgerDTO the debtorLedgerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new debtorLedgerDTO, or with status 400 (Bad Request) if the debtorLedger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/debtor-ledgers")
    public ResponseEntity<DebtorLedgerDTO> createDebtorLedger(@RequestBody DebtorLedgerDTO debtorLedgerDTO) throws URISyntaxException {
        log.debug("REST request to save DebtorLedger : {}", debtorLedgerDTO);
        if (debtorLedgerDTO.getId() != null) {
            throw new BadRequestAlertException("A new debtorLedger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DebtorLedgerDTO result = debtorLedgerService.save(debtorLedgerDTO);
        return ResponseEntity.created(new URI("/api/debtor-ledgers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /debtor-ledgers : Updates an existing debtorLedger.
     *
     * @param debtorLedgerDTO the debtorLedgerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated debtorLedgerDTO,
     * or with status 400 (Bad Request) if the debtorLedgerDTO is not valid,
     * or with status 500 (Internal Server Error) if the debtorLedgerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/debtor-ledgers")
    public ResponseEntity<DebtorLedgerDTO> updateDebtorLedger(@RequestBody DebtorLedgerDTO debtorLedgerDTO) throws URISyntaxException {
        log.debug("REST request to update DebtorLedger : {}", debtorLedgerDTO);
        if (debtorLedgerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DebtorLedgerDTO result = debtorLedgerService.save(debtorLedgerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, debtorLedgerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /debtor-ledgers : get all the debtorLedgers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of debtorLedgers in body
     */
    @GetMapping("/debtor-ledgers")
    public ResponseEntity<List<DebtorLedgerDTO>> getAllDebtorLedgers(DebtorLedgerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DebtorLedgers by criteria: {}", criteria);
        Page<DebtorLedgerDTO> page = debtorLedgerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/debtor-ledgers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /debtor-ledgers/count : count all the debtorLedgers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/debtor-ledgers/count")
    public ResponseEntity<Long> countDebtorLedgers(DebtorLedgerCriteria criteria) {
        log.debug("REST request to count DebtorLedgers by criteria: {}", criteria);
        return ResponseEntity.ok().body(debtorLedgerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /debtor-ledgers/:id : get the "id" debtorLedger.
     *
     * @param id the id of the debtorLedgerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the debtorLedgerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/debtor-ledgers/{id}")
    public ResponseEntity<DebtorLedgerDTO> getDebtorLedger(@PathVariable Long id) {
        log.debug("REST request to get DebtorLedger : {}", id);
        Optional<DebtorLedgerDTO> debtorLedgerDTO = debtorLedgerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(debtorLedgerDTO);
    }

    /**
     * DELETE  /debtor-ledgers/:id : delete the "id" debtorLedger.
     *
     * @param id the id of the debtorLedgerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/debtor-ledgers/{id}")
    public ResponseEntity<Void> deleteDebtorLedger(@PathVariable Long id) {
        log.debug("REST request to delete DebtorLedger : {}", id);
        debtorLedgerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/debtor-ledgers?query=:query : search for the debtorLedger corresponding
     * to the query.
     *
     * @param query the query of the debtorLedger search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/debtor-ledgers")
    public ResponseEntity<List<DebtorLedgerDTO>> searchDebtorLedgers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DebtorLedgers for query {}", query);
        Page<DebtorLedgerDTO> page = debtorLedgerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/debtor-ledgers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
