package org.soptorshi.web.rest;
import org.soptorshi.service.CreditorLedgerService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.CreditorLedgerDTO;
import org.soptorshi.service.dto.CreditorLedgerCriteria;
import org.soptorshi.service.CreditorLedgerQueryService;
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
 * REST controller for managing CreditorLedger.
 */
@RestController
@RequestMapping("/api")
public class CreditorLedgerResource {

    private final Logger log = LoggerFactory.getLogger(CreditorLedgerResource.class);

    private static final String ENTITY_NAME = "creditorLedger";

    private final CreditorLedgerService creditorLedgerService;

    private final CreditorLedgerQueryService creditorLedgerQueryService;

    public CreditorLedgerResource(CreditorLedgerService creditorLedgerService, CreditorLedgerQueryService creditorLedgerQueryService) {
        this.creditorLedgerService = creditorLedgerService;
        this.creditorLedgerQueryService = creditorLedgerQueryService;
    }

    /**
     * POST  /creditor-ledgers : Create a new creditorLedger.
     *
     * @param creditorLedgerDTO the creditorLedgerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditorLedgerDTO, or with status 400 (Bad Request) if the creditorLedger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/creditor-ledgers")
    public ResponseEntity<CreditorLedgerDTO> createCreditorLedger(@RequestBody CreditorLedgerDTO creditorLedgerDTO) throws URISyntaxException {
        log.debug("REST request to save CreditorLedger : {}", creditorLedgerDTO);
        if (creditorLedgerDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditorLedger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditorLedgerDTO result = creditorLedgerService.save(creditorLedgerDTO);
        return ResponseEntity.created(new URI("/api/creditor-ledgers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /creditor-ledgers : Updates an existing creditorLedger.
     *
     * @param creditorLedgerDTO the creditorLedgerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditorLedgerDTO,
     * or with status 400 (Bad Request) if the creditorLedgerDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditorLedgerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/creditor-ledgers")
    public ResponseEntity<CreditorLedgerDTO> updateCreditorLedger(@RequestBody CreditorLedgerDTO creditorLedgerDTO) throws URISyntaxException {
        log.debug("REST request to update CreditorLedger : {}", creditorLedgerDTO);
        if (creditorLedgerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditorLedgerDTO result = creditorLedgerService.save(creditorLedgerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditorLedgerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /creditor-ledgers : get all the creditorLedgers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of creditorLedgers in body
     */
    @GetMapping("/creditor-ledgers")
    public ResponseEntity<List<CreditorLedgerDTO>> getAllCreditorLedgers(CreditorLedgerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CreditorLedgers by criteria: {}", criteria);
        Page<CreditorLedgerDTO> page = creditorLedgerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/creditor-ledgers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /creditor-ledgers/count : count all the creditorLedgers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/creditor-ledgers/count")
    public ResponseEntity<Long> countCreditorLedgers(CreditorLedgerCriteria criteria) {
        log.debug("REST request to count CreditorLedgers by criteria: {}", criteria);
        return ResponseEntity.ok().body(creditorLedgerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /creditor-ledgers/:id : get the "id" creditorLedger.
     *
     * @param id the id of the creditorLedgerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditorLedgerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/creditor-ledgers/{id}")
    public ResponseEntity<CreditorLedgerDTO> getCreditorLedger(@PathVariable Long id) {
        log.debug("REST request to get CreditorLedger : {}", id);
        Optional<CreditorLedgerDTO> creditorLedgerDTO = creditorLedgerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditorLedgerDTO);
    }

    /**
     * DELETE  /creditor-ledgers/:id : delete the "id" creditorLedger.
     *
     * @param id the id of the creditorLedgerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/creditor-ledgers/{id}")
    public ResponseEntity<Void> deleteCreditorLedger(@PathVariable Long id) {
        log.debug("REST request to delete CreditorLedger : {}", id);
        creditorLedgerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/creditor-ledgers?query=:query : search for the creditorLedger corresponding
     * to the query.
     *
     * @param query the query of the creditorLedger search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/creditor-ledgers")
    public ResponseEntity<List<CreditorLedgerDTO>> searchCreditorLedgers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CreditorLedgers for query {}", query);
        Page<CreditorLedgerDTO> page = creditorLedgerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/creditor-ledgers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
