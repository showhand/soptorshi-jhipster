package org.soptorshi.web.rest;
import org.soptorshi.domain.LoanManagement;
import org.soptorshi.repository.LoanManagementRepository;
import org.soptorshi.repository.search.LoanManagementSearchRepository;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LoanManagement.
 */
@RestController
@RequestMapping("/api")
public class LoanManagementResource {

    private final Logger log = LoggerFactory.getLogger(LoanManagementResource.class);

    private static final String ENTITY_NAME = "loanManagement";

    private final LoanManagementRepository loanManagementRepository;

    private final LoanManagementSearchRepository loanManagementSearchRepository;

    public LoanManagementResource(LoanManagementRepository loanManagementRepository, LoanManagementSearchRepository loanManagementSearchRepository) {
        this.loanManagementRepository = loanManagementRepository;
        this.loanManagementSearchRepository = loanManagementSearchRepository;
    }

    /**
     * POST  /loan-managements : Create a new loanManagement.
     *
     * @param loanManagement the loanManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanManagement, or with status 400 (Bad Request) if the loanManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loan-managements")
    public ResponseEntity<LoanManagement> createLoanManagement(@RequestBody LoanManagement loanManagement) throws URISyntaxException {
        log.debug("REST request to save LoanManagement : {}", loanManagement);
        if (loanManagement.getId() != null) {
            throw new BadRequestAlertException("A new loanManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanManagement result = loanManagementRepository.save(loanManagement);
        loanManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/loan-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loan-managements : Updates an existing loanManagement.
     *
     * @param loanManagement the loanManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanManagement,
     * or with status 400 (Bad Request) if the loanManagement is not valid,
     * or with status 500 (Internal Server Error) if the loanManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loan-managements")
    public ResponseEntity<LoanManagement> updateLoanManagement(@RequestBody LoanManagement loanManagement) throws URISyntaxException {
        log.debug("REST request to update LoanManagement : {}", loanManagement);
        if (loanManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanManagement result = loanManagementRepository.save(loanManagement);
        loanManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loanManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loan-managements : get all the loanManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loanManagements in body
     */
    @GetMapping("/loan-managements")
    public List<LoanManagement> getAllLoanManagements() {
        log.debug("REST request to get all LoanManagements");
        return loanManagementRepository.findAll();
    }

    /**
     * GET  /loan-managements/:id : get the "id" loanManagement.
     *
     * @param id the id of the loanManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanManagement, or with status 404 (Not Found)
     */
    @GetMapping("/loan-managements/{id}")
    public ResponseEntity<LoanManagement> getLoanManagement(@PathVariable Long id) {
        log.debug("REST request to get LoanManagement : {}", id);
        Optional<LoanManagement> loanManagement = loanManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loanManagement);
    }

    /**
     * DELETE  /loan-managements/:id : delete the "id" loanManagement.
     *
     * @param id the id of the loanManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loan-managements/{id}")
    public ResponseEntity<Void> deleteLoanManagement(@PathVariable Long id) {
        log.debug("REST request to delete LoanManagement : {}", id);
        loanManagementRepository.deleteById(id);
        loanManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/loan-managements?query=:query : search for the loanManagement corresponding
     * to the query.
     *
     * @param query the query of the loanManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/loan-managements")
    public List<LoanManagement> searchLoanManagements(@RequestParam String query) {
        log.debug("REST request to search LoanManagements for query {}", query);
        return StreamSupport
            .stream(loanManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
