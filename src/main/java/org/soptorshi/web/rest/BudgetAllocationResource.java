package org.soptorshi.web.rest;
import org.soptorshi.service.BudgetAllocationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.BudgetAllocationDTO;
import org.soptorshi.service.dto.BudgetAllocationCriteria;
import org.soptorshi.service.BudgetAllocationQueryService;
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
 * REST controller for managing BudgetAllocation.
 */
@RestController
@RequestMapping("/api")
public class BudgetAllocationResource {

    private final Logger log = LoggerFactory.getLogger(BudgetAllocationResource.class);

    private static final String ENTITY_NAME = "budgetAllocation";

    private final BudgetAllocationService budgetAllocationService;

    private final BudgetAllocationQueryService budgetAllocationQueryService;

    public BudgetAllocationResource(BudgetAllocationService budgetAllocationService, BudgetAllocationQueryService budgetAllocationQueryService) {
        this.budgetAllocationService = budgetAllocationService;
        this.budgetAllocationQueryService = budgetAllocationQueryService;
    }

    /**
     * POST  /budget-allocations : Create a new budgetAllocation.
     *
     * @param budgetAllocationDTO the budgetAllocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new budgetAllocationDTO, or with status 400 (Bad Request) if the budgetAllocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/budget-allocations")
    public ResponseEntity<BudgetAllocationDTO> createBudgetAllocation(@RequestBody BudgetAllocationDTO budgetAllocationDTO) throws URISyntaxException {
        log.debug("REST request to save BudgetAllocation : {}", budgetAllocationDTO);
        if (budgetAllocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new budgetAllocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BudgetAllocationDTO result = budgetAllocationService.save(budgetAllocationDTO);
        return ResponseEntity.created(new URI("/api/budget-allocations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /budget-allocations : Updates an existing budgetAllocation.
     *
     * @param budgetAllocationDTO the budgetAllocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated budgetAllocationDTO,
     * or with status 400 (Bad Request) if the budgetAllocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the budgetAllocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/budget-allocations")
    public ResponseEntity<BudgetAllocationDTO> updateBudgetAllocation(@RequestBody BudgetAllocationDTO budgetAllocationDTO) throws URISyntaxException {
        log.debug("REST request to update BudgetAllocation : {}", budgetAllocationDTO);
        if (budgetAllocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BudgetAllocationDTO result = budgetAllocationService.save(budgetAllocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, budgetAllocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /budget-allocations : get all the budgetAllocations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of budgetAllocations in body
     */
    @GetMapping("/budget-allocations")
    public ResponseEntity<List<BudgetAllocationDTO>> getAllBudgetAllocations(BudgetAllocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BudgetAllocations by criteria: {}", criteria);
        Page<BudgetAllocationDTO> page = budgetAllocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/budget-allocations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /budget-allocations/count : count all the budgetAllocations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/budget-allocations/count")
    public ResponseEntity<Long> countBudgetAllocations(BudgetAllocationCriteria criteria) {
        log.debug("REST request to count BudgetAllocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(budgetAllocationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /budget-allocations/:id : get the "id" budgetAllocation.
     *
     * @param id the id of the budgetAllocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the budgetAllocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/budget-allocations/{id}")
    public ResponseEntity<BudgetAllocationDTO> getBudgetAllocation(@PathVariable Long id) {
        log.debug("REST request to get BudgetAllocation : {}", id);
        Optional<BudgetAllocationDTO> budgetAllocationDTO = budgetAllocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(budgetAllocationDTO);
    }

    /**
     * DELETE  /budget-allocations/:id : delete the "id" budgetAllocation.
     *
     * @param id the id of the budgetAllocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/budget-allocations/{id}")
    public ResponseEntity<Void> deleteBudgetAllocation(@PathVariable Long id) {
        log.debug("REST request to delete BudgetAllocation : {}", id);
        budgetAllocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/budget-allocations?query=:query : search for the budgetAllocation corresponding
     * to the query.
     *
     * @param query the query of the budgetAllocation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/budget-allocations")
    public ResponseEntity<List<BudgetAllocationDTO>> searchBudgetAllocations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BudgetAllocations for query {}", query);
        Page<BudgetAllocationDTO> page = budgetAllocationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/budget-allocations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
