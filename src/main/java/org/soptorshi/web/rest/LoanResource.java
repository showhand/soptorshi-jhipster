package org.soptorshi.web.rest;
import org.soptorshi.service.LoanService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.LoanDTO;
import org.soptorshi.service.dto.LoanCriteria;
import org.soptorshi.service.LoanQueryService;
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
 * REST controller for managing Loan.
 */
@RestController
@RequestMapping("/api")
public class LoanResource {

    private final Logger log = LoggerFactory.getLogger(LoanResource.class);

    private static final String ENTITY_NAME = "loan";

    private final LoanService loanService;

    private final LoanQueryService loanQueryService;

    public LoanResource(LoanService loanService, LoanQueryService loanQueryService) {
        this.loanService = loanService;
        this.loanQueryService = loanQueryService;
    }

    /**
     * POST  /loans : Create a new loan.
     *
     * @param loanDTO the loanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanDTO, or with status 400 (Bad Request) if the loan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loans")
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO) throws URISyntaxException {
        log.debug("REST request to save Loan : {}", loanDTO);
        if (loanDTO.getId() != null) {
            throw new BadRequestAlertException("A new loan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDTO result = loanService.save(loanDTO);
        return ResponseEntity.created(new URI("/api/loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loans : Updates an existing loan.
     *
     * @param loanDTO the loanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanDTO,
     * or with status 400 (Bad Request) if the loanDTO is not valid,
     * or with status 500 (Internal Server Error) if the loanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loans")
    public ResponseEntity<LoanDTO> updateLoan(@Valid @RequestBody LoanDTO loanDTO) throws URISyntaxException {
        log.debug("REST request to update Loan : {}", loanDTO);
        if (loanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanDTO result = loanService.save(loanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loans : get all the loans.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of loans in body
     */
    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getAllLoans(LoanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Loans by criteria: {}", criteria);
        Page<LoanDTO> page = loanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /loans/count : count all the loans.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/loans/count")
    public ResponseEntity<Long> countLoans(LoanCriteria criteria) {
        log.debug("REST request to count Loans by criteria: {}", criteria);
        return ResponseEntity.ok().body(loanQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /loans/:id : get the "id" loan.
     *
     * @param id the id of the loanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long id) {
        log.debug("REST request to get Loan : {}", id);
        Optional<LoanDTO> loanDTO = loanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDTO);
    }

    /**
     * DELETE  /loans/:id : delete the "id" loan.
     *
     * @param id the id of the loanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        log.debug("REST request to delete Loan : {}", id);
        loanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/loans?query=:query : search for the loan corresponding
     * to the query.
     *
     * @param query the query of the loan search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/loans")
    public ResponseEntity<List<LoanDTO>> searchLoans(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Loans for query {}", query);
        Page<LoanDTO> page = loanService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/loans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
