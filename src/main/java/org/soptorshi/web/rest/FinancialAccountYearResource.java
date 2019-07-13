package org.soptorshi.web.rest;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.dto.FinancialAccountYearCriteria;
import org.soptorshi.service.FinancialAccountYearQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FinancialAccountYear.
 */
@RestController
@RequestMapping("/api")
public class FinancialAccountYearResource {

    private final Logger log = LoggerFactory.getLogger(FinancialAccountYearResource.class);

    private static final String ENTITY_NAME = "financialAccountYear";

    private final FinancialAccountYearService financialAccountYearService;

    private final FinancialAccountYearQueryService financialAccountYearQueryService;

    public FinancialAccountYearResource(FinancialAccountYearService financialAccountYearService, FinancialAccountYearQueryService financialAccountYearQueryService) {
        this.financialAccountYearService = financialAccountYearService;
        this.financialAccountYearQueryService = financialAccountYearQueryService;
    }

    /**
     * POST  /financial-account-years : Create a new financialAccountYear.
     *
     * @param financialAccountYearDTO the financialAccountYearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialAccountYearDTO, or with status 400 (Bad Request) if the financialAccountYear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-account-years")
    public ResponseEntity<FinancialAccountYearDTO> createFinancialAccountYear(@Valid @RequestBody FinancialAccountYearDTO financialAccountYearDTO) throws URISyntaxException {
        log.debug("REST request to save FinancialAccountYear : {}", financialAccountYearDTO);
        if (financialAccountYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new financialAccountYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialAccountYearDTO result = financialAccountYearService.save(financialAccountYearDTO);
        return ResponseEntity.created(new URI("/api/financial-account-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-account-years : Updates an existing financialAccountYear.
     *
     * @param financialAccountYearDTO the financialAccountYearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialAccountYearDTO,
     * or with status 400 (Bad Request) if the financialAccountYearDTO is not valid,
     * or with status 500 (Internal Server Error) if the financialAccountYearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-account-years")
    public ResponseEntity<FinancialAccountYearDTO> updateFinancialAccountYear(@Valid @RequestBody FinancialAccountYearDTO financialAccountYearDTO) throws URISyntaxException {
        log.debug("REST request to update FinancialAccountYear : {}", financialAccountYearDTO);
        if (financialAccountYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinancialAccountYearDTO result = financialAccountYearService.save(financialAccountYearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialAccountYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /financial-account-years : get all the financialAccountYears.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of financialAccountYears in body
     */
    @GetMapping("/financial-account-years")
    public ResponseEntity<List<FinancialAccountYearDTO>> getAllFinancialAccountYears(FinancialAccountYearCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FinancialAccountYears by criteria: {}", criteria);
        Page<FinancialAccountYearDTO> page = financialAccountYearQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/financial-account-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /financial-account-years/count : count all the financialAccountYears.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/financial-account-years/count")
    public ResponseEntity<Long> countFinancialAccountYears(FinancialAccountYearCriteria criteria) {
        log.debug("REST request to count FinancialAccountYears by criteria: {}", criteria);
        return ResponseEntity.ok().body(financialAccountYearQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /financial-account-years/:id : get the "id" financialAccountYear.
     *
     * @param id the id of the financialAccountYearDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financialAccountYearDTO, or with status 404 (Not Found)
     */
    @GetMapping("/financial-account-years/{id}")
    public ResponseEntity<FinancialAccountYearDTO> getFinancialAccountYear(@PathVariable Long id) {
        log.debug("REST request to get FinancialAccountYear : {}", id);
        Optional<FinancialAccountYearDTO> financialAccountYearDTO = financialAccountYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(financialAccountYearDTO);
    }

    /**
     * DELETE  /financial-account-years/:id : delete the "id" financialAccountYear.
     *
     * @param id the id of the financialAccountYearDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/financial-account-years/{id}")
    public ResponseEntity<Void> deleteFinancialAccountYear(@PathVariable Long id) {
        log.debug("REST request to delete FinancialAccountYear : {}", id);
        financialAccountYearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/financial-account-years?query=:query : search for the financialAccountYear corresponding
     * to the query.
     *
     * @param query the query of the financialAccountYear search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/financial-account-years")
    public ResponseEntity<List<FinancialAccountYearDTO>> searchFinancialAccountYears(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FinancialAccountYears for query {}", query);
        Page<FinancialAccountYearDTO> page = financialAccountYearService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/financial-account-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
