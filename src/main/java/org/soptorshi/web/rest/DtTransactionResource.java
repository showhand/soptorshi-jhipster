package org.soptorshi.web.rest;
import org.soptorshi.service.DtTransactionService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.DtTransactionQueryService;
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
 * REST controller for managing DtTransaction.
 */
@RestController
@RequestMapping("/api")
public class DtTransactionResource {

    private final Logger log = LoggerFactory.getLogger(DtTransactionResource.class);

    private static final String ENTITY_NAME = "dtTransaction";

    private final DtTransactionService dtTransactionService;

    private final DtTransactionQueryService dtTransactionQueryService;

    public DtTransactionResource(DtTransactionService dtTransactionService, DtTransactionQueryService dtTransactionQueryService) {
        this.dtTransactionService = dtTransactionService;
        this.dtTransactionQueryService = dtTransactionQueryService;
    }

    /**
     * POST  /dt-transactions : Create a new dtTransaction.
     *
     * @param dtTransactionDTO the dtTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dtTransactionDTO, or with status 400 (Bad Request) if the dtTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dt-transactions")
    public ResponseEntity<DtTransactionDTO> createDtTransaction(@RequestBody DtTransactionDTO dtTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save DtTransaction : {}", dtTransactionDTO);
        if (dtTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dtTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DtTransactionDTO result = dtTransactionService.save(dtTransactionDTO);
        return ResponseEntity.created(new URI("/api/dt-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dt-transactions : Updates an existing dtTransaction.
     *
     * @param dtTransactionDTO the dtTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dtTransactionDTO,
     * or with status 400 (Bad Request) if the dtTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the dtTransactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dt-transactions")
    public ResponseEntity<DtTransactionDTO> updateDtTransaction(@RequestBody DtTransactionDTO dtTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update DtTransaction : {}", dtTransactionDTO);
        if (dtTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DtTransactionDTO result = dtTransactionService.save(dtTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dtTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dt-transactions : get all the dtTransactions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dtTransactions in body
     */
    @GetMapping("/dt-transactions")
    public ResponseEntity<List<DtTransactionDTO>> getAllDtTransactions(DtTransactionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DtTransactions by criteria: {}", criteria);
        Page<DtTransactionDTO> page = dtTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dt-transactions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /dt-transactions/count : count all the dtTransactions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/dt-transactions/count")
    public ResponseEntity<Long> countDtTransactions(DtTransactionCriteria criteria) {
        log.debug("REST request to count DtTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(dtTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /dt-transactions/:id : get the "id" dtTransaction.
     *
     * @param id the id of the dtTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dtTransactionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dt-transactions/{id}")
    public ResponseEntity<DtTransactionDTO> getDtTransaction(@PathVariable Long id) {
        log.debug("REST request to get DtTransaction : {}", id);
        Optional<DtTransactionDTO> dtTransactionDTO = dtTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dtTransactionDTO);
    }

    /**
     * DELETE  /dt-transactions/:id : delete the "id" dtTransaction.
     *
     * @param id the id of the dtTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dt-transactions/{id}")
    public ResponseEntity<Void> deleteDtTransaction(@PathVariable Long id) {
        log.debug("REST request to delete DtTransaction : {}", id);
        dtTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dt-transactions?query=:query : search for the dtTransaction corresponding
     * to the query.
     *
     * @param query the query of the dtTransaction search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dt-transactions")
    public ResponseEntity<List<DtTransactionDTO>> searchDtTransactions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DtTransactions for query {}", query);
        Page<DtTransactionDTO> page = dtTransactionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dt-transactions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
