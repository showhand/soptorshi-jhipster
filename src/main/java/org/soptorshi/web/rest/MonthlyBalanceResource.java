package org.soptorshi.web.rest;
import org.soptorshi.service.MonthlyBalanceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.MonthlyBalanceDTO;
import org.soptorshi.service.dto.MonthlyBalanceCriteria;
import org.soptorshi.service.MonthlyBalanceQueryService;
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
 * REST controller for managing MonthlyBalance.
 */
@RestController
@RequestMapping("/api")
public class MonthlyBalanceResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyBalanceResource.class);

    private static final String ENTITY_NAME = "monthlyBalance";

    private final MonthlyBalanceService monthlyBalanceService;

    private final MonthlyBalanceQueryService monthlyBalanceQueryService;

    public MonthlyBalanceResource(MonthlyBalanceService monthlyBalanceService, MonthlyBalanceQueryService monthlyBalanceQueryService) {
        this.monthlyBalanceService = monthlyBalanceService;
        this.monthlyBalanceQueryService = monthlyBalanceQueryService;
    }

    /**
     * POST  /monthly-balances : Create a new monthlyBalance.
     *
     * @param monthlyBalanceDTO the monthlyBalanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlyBalanceDTO, or with status 400 (Bad Request) if the monthlyBalance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monthly-balances")
    public ResponseEntity<MonthlyBalanceDTO> createMonthlyBalance(@RequestBody MonthlyBalanceDTO monthlyBalanceDTO) throws URISyntaxException {
        log.debug("REST request to save MonthlyBalance : {}", monthlyBalanceDTO);
        if (monthlyBalanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyBalanceDTO result = monthlyBalanceService.save(monthlyBalanceDTO);
        return ResponseEntity.created(new URI("/api/monthly-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthly-balances : Updates an existing monthlyBalance.
     *
     * @param monthlyBalanceDTO the monthlyBalanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlyBalanceDTO,
     * or with status 400 (Bad Request) if the monthlyBalanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the monthlyBalanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monthly-balances")
    public ResponseEntity<MonthlyBalanceDTO> updateMonthlyBalance(@RequestBody MonthlyBalanceDTO monthlyBalanceDTO) throws URISyntaxException {
        log.debug("REST request to update MonthlyBalance : {}", monthlyBalanceDTO);
        if (monthlyBalanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlyBalanceDTO result = monthlyBalanceService.save(monthlyBalanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monthlyBalanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthly-balances : get all the monthlyBalances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of monthlyBalances in body
     */
    @GetMapping("/monthly-balances")
    public ResponseEntity<List<MonthlyBalanceDTO>> getAllMonthlyBalances(MonthlyBalanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MonthlyBalances by criteria: {}", criteria);
        Page<MonthlyBalanceDTO> page = monthlyBalanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/monthly-balances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /monthly-balances/count : count all the monthlyBalances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/monthly-balances/count")
    public ResponseEntity<Long> countMonthlyBalances(MonthlyBalanceCriteria criteria) {
        log.debug("REST request to count MonthlyBalances by criteria: {}", criteria);
        return ResponseEntity.ok().body(monthlyBalanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /monthly-balances/:id : get the "id" monthlyBalance.
     *
     * @param id the id of the monthlyBalanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlyBalanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monthly-balances/{id}")
    public ResponseEntity<MonthlyBalanceDTO> getMonthlyBalance(@PathVariable Long id) {
        log.debug("REST request to get MonthlyBalance : {}", id);
        Optional<MonthlyBalanceDTO> monthlyBalanceDTO = monthlyBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyBalanceDTO);
    }

    /**
     * DELETE  /monthly-balances/:id : delete the "id" monthlyBalance.
     *
     * @param id the id of the monthlyBalanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monthly-balances/{id}")
    public ResponseEntity<Void> deleteMonthlyBalance(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyBalance : {}", id);
        monthlyBalanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/monthly-balances?query=:query : search for the monthlyBalance corresponding
     * to the query.
     *
     * @param query the query of the monthlyBalance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/monthly-balances")
    public ResponseEntity<List<MonthlyBalanceDTO>> searchMonthlyBalances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MonthlyBalances for query {}", query);
        Page<MonthlyBalanceDTO> page = monthlyBalanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/monthly-balances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
