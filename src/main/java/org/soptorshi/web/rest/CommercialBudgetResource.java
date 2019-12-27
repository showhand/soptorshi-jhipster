package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialBudgetQueryService;
import org.soptorshi.service.CommercialBudgetService;
import org.soptorshi.service.dto.CommercialBudgetCriteria;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommercialBudget.
 */
@RestController
@RequestMapping("/api")
public class CommercialBudgetResource {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetResource.class);

    private static final String ENTITY_NAME = "commercialBudget";

    private final CommercialBudgetService commercialBudgetService;

    private final CommercialBudgetQueryService commercialBudgetQueryService;

    public CommercialBudgetResource(CommercialBudgetService commercialBudgetService, CommercialBudgetQueryService commercialBudgetQueryService) {
        this.commercialBudgetService = commercialBudgetService;
        this.commercialBudgetQueryService = commercialBudgetQueryService;
    }

    /**
     * POST  /commercial-budgets : Create a new commercialBudget.
     *
     * @param commercialBudgetDTO the commercialBudgetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialBudgetDTO, or with status 400 (Bad Request) if the commercialBudget has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-budgets")
    public ResponseEntity<CommercialBudgetDTO> createCommercialBudget(@Valid @RequestBody CommercialBudgetDTO commercialBudgetDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialBudget : {}", commercialBudgetDTO);
        if (commercialBudgetDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialBudget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialBudgetDTO result = commercialBudgetService.save(commercialBudgetDTO);
        return ResponseEntity.created(new URI("/api/commercial-budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-budgets : Updates an existing commercialBudget.
     *
     * @param commercialBudgetDTO the commercialBudgetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialBudgetDTO,
     * or with status 400 (Bad Request) if the commercialBudgetDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialBudgetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-budgets")
    public ResponseEntity<CommercialBudgetDTO> updateCommercialBudget(@Valid @RequestBody CommercialBudgetDTO commercialBudgetDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialBudget : {}", commercialBudgetDTO);
        if (commercialBudgetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialBudgetDTO result = commercialBudgetService.save(commercialBudgetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialBudgetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-budgets : get all the commercialBudgets.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialBudgets in body
     */
    @GetMapping("/commercial-budgets")
    public ResponseEntity<List<CommercialBudgetDTO>> getAllCommercialBudgets(CommercialBudgetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialBudgets by criteria: {}", criteria);
        Page<CommercialBudgetDTO> page = commercialBudgetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-budgets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-budgets/count : count all the commercialBudgets.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-budgets/count")
    public ResponseEntity<Long> countCommercialBudgets(CommercialBudgetCriteria criteria) {
        log.debug("REST request to count CommercialBudgets by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialBudgetQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-budgets/:id : get the "id" commercialBudget.
     *
     * @param id the id of the commercialBudgetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialBudgetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-budgets/{id}")
    public ResponseEntity<CommercialBudgetDTO> getCommercialBudget(@PathVariable Long id) {
        log.debug("REST request to get CommercialBudget : {}", id);
        Optional<CommercialBudgetDTO> commercialBudgetDTO = commercialBudgetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialBudgetDTO);
    }

    /**
     * DELETE  /commercial-budgets/:id : delete the "id" commercialBudget.
     *
     * @param id the id of the commercialBudgetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-budgets/{id}")
    public ResponseEntity<Void> deleteCommercialBudget(@PathVariable Long id) {
        log.debug("REST request to delete CommercialBudget : {}", id);
        commercialBudgetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-budgets?query=:query : search for the commercialBudget corresponding
     * to the query.
     *
     * @param query the query of the commercialBudget search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-budgets")
    public ResponseEntity<List<CommercialBudgetDTO>> searchCommercialBudgets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialBudgets for query {}", query);
        Page<CommercialBudgetDTO> page = commercialBudgetService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-budgets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
