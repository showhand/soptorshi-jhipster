package org.soptorshi.web.rest;
import org.soptorshi.domain.FineAdvanceLoanManagement;
import org.soptorshi.repository.FineAdvanceLoanManagementRepository;
import org.soptorshi.repository.search.FineAdvanceLoanManagementSearchRepository;
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
 * REST controller for managing FineAdvanceLoanManagement.
 */
@RestController
@RequestMapping("/api")
public class FineAdvanceLoanManagementResource {

    private final Logger log = LoggerFactory.getLogger(FineAdvanceLoanManagementResource.class);

    private static final String ENTITY_NAME = "fineAdvanceLoanManagement";

    private final FineAdvanceLoanManagementRepository fineAdvanceLoanManagementRepository;

    private final FineAdvanceLoanManagementSearchRepository fineAdvanceLoanManagementSearchRepository;

    public FineAdvanceLoanManagementResource(FineAdvanceLoanManagementRepository fineAdvanceLoanManagementRepository, FineAdvanceLoanManagementSearchRepository fineAdvanceLoanManagementSearchRepository) {
        this.fineAdvanceLoanManagementRepository = fineAdvanceLoanManagementRepository;
        this.fineAdvanceLoanManagementSearchRepository = fineAdvanceLoanManagementSearchRepository;
    }

    /**
     * POST  /fine-advance-loan-managements : Create a new fineAdvanceLoanManagement.
     *
     * @param fineAdvanceLoanManagement the fineAdvanceLoanManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fineAdvanceLoanManagement, or with status 400 (Bad Request) if the fineAdvanceLoanManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fine-advance-loan-managements")
    public ResponseEntity<FineAdvanceLoanManagement> createFineAdvanceLoanManagement(@RequestBody FineAdvanceLoanManagement fineAdvanceLoanManagement) throws URISyntaxException {
        log.debug("REST request to save FineAdvanceLoanManagement : {}", fineAdvanceLoanManagement);
        if (fineAdvanceLoanManagement.getId() != null) {
            throw new BadRequestAlertException("A new fineAdvanceLoanManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FineAdvanceLoanManagement result = fineAdvanceLoanManagementRepository.save(fineAdvanceLoanManagement);
        fineAdvanceLoanManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fine-advance-loan-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fine-advance-loan-managements : Updates an existing fineAdvanceLoanManagement.
     *
     * @param fineAdvanceLoanManagement the fineAdvanceLoanManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fineAdvanceLoanManagement,
     * or with status 400 (Bad Request) if the fineAdvanceLoanManagement is not valid,
     * or with status 500 (Internal Server Error) if the fineAdvanceLoanManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fine-advance-loan-managements")
    public ResponseEntity<FineAdvanceLoanManagement> updateFineAdvanceLoanManagement(@RequestBody FineAdvanceLoanManagement fineAdvanceLoanManagement) throws URISyntaxException {
        log.debug("REST request to update FineAdvanceLoanManagement : {}", fineAdvanceLoanManagement);
        if (fineAdvanceLoanManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FineAdvanceLoanManagement result = fineAdvanceLoanManagementRepository.save(fineAdvanceLoanManagement);
        fineAdvanceLoanManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fineAdvanceLoanManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fine-advance-loan-managements : get all the fineAdvanceLoanManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fineAdvanceLoanManagements in body
     */
    @GetMapping("/fine-advance-loan-managements")
    public List<FineAdvanceLoanManagement> getAllFineAdvanceLoanManagements() {
        log.debug("REST request to get all FineAdvanceLoanManagements");
        return fineAdvanceLoanManagementRepository.findAll();
    }

    /**
     * GET  /fine-advance-loan-managements/:id : get the "id" fineAdvanceLoanManagement.
     *
     * @param id the id of the fineAdvanceLoanManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fineAdvanceLoanManagement, or with status 404 (Not Found)
     */
    @GetMapping("/fine-advance-loan-managements/{id}")
    public ResponseEntity<FineAdvanceLoanManagement> getFineAdvanceLoanManagement(@PathVariable Long id) {
        log.debug("REST request to get FineAdvanceLoanManagement : {}", id);
        Optional<FineAdvanceLoanManagement> fineAdvanceLoanManagement = fineAdvanceLoanManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fineAdvanceLoanManagement);
    }

    /**
     * DELETE  /fine-advance-loan-managements/:id : delete the "id" fineAdvanceLoanManagement.
     *
     * @param id the id of the fineAdvanceLoanManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fine-advance-loan-managements/{id}")
    public ResponseEntity<Void> deleteFineAdvanceLoanManagement(@PathVariable Long id) {
        log.debug("REST request to delete FineAdvanceLoanManagement : {}", id);
        fineAdvanceLoanManagementRepository.deleteById(id);
        fineAdvanceLoanManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fine-advance-loan-managements?query=:query : search for the fineAdvanceLoanManagement corresponding
     * to the query.
     *
     * @param query the query of the fineAdvanceLoanManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/fine-advance-loan-managements")
    public List<FineAdvanceLoanManagement> searchFineAdvanceLoanManagements(@RequestParam String query) {
        log.debug("REST request to search FineAdvanceLoanManagements for query {}", query);
        return StreamSupport
            .stream(fineAdvanceLoanManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
