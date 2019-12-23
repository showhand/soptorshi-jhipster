package org.soptorshi.web.rest;
import org.soptorshi.domain.FineManagement;
import org.soptorshi.repository.FineManagementRepository;
import org.soptorshi.repository.search.FineManagementSearchRepository;
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
 * REST controller for managing FineManagement.
 */
@RestController
@RequestMapping("/api")
public class FineManagementResource {

    private final Logger log = LoggerFactory.getLogger(FineManagementResource.class);

    private static final String ENTITY_NAME = "fineManagement";

    private final FineManagementRepository fineManagementRepository;

    private final FineManagementSearchRepository fineManagementSearchRepository;

    public FineManagementResource(FineManagementRepository fineManagementRepository, FineManagementSearchRepository fineManagementSearchRepository) {
        this.fineManagementRepository = fineManagementRepository;
        this.fineManagementSearchRepository = fineManagementSearchRepository;
    }

    /**
     * POST  /fine-managements : Create a new fineManagement.
     *
     * @param fineManagement the fineManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fineManagement, or with status 400 (Bad Request) if the fineManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fine-managements")
    public ResponseEntity<FineManagement> createFineManagement(@RequestBody FineManagement fineManagement) throws URISyntaxException {
        log.debug("REST request to save FineManagement : {}", fineManagement);
        if (fineManagement.getId() != null) {
            throw new BadRequestAlertException("A new fineManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FineManagement result = fineManagementRepository.save(fineManagement);
        fineManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fine-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fine-managements : Updates an existing fineManagement.
     *
     * @param fineManagement the fineManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fineManagement,
     * or with status 400 (Bad Request) if the fineManagement is not valid,
     * or with status 500 (Internal Server Error) if the fineManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fine-managements")
    public ResponseEntity<FineManagement> updateFineManagement(@RequestBody FineManagement fineManagement) throws URISyntaxException {
        log.debug("REST request to update FineManagement : {}", fineManagement);
        if (fineManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FineManagement result = fineManagementRepository.save(fineManagement);
        fineManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fineManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fine-managements : get all the fineManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fineManagements in body
     */
    @GetMapping("/fine-managements")
    public List<FineManagement> getAllFineManagements() {
        log.debug("REST request to get all FineManagements");
        return fineManagementRepository.findAll();
    }

    /**
     * GET  /fine-managements/:id : get the "id" fineManagement.
     *
     * @param id the id of the fineManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fineManagement, or with status 404 (Not Found)
     */
    @GetMapping("/fine-managements/{id}")
    public ResponseEntity<FineManagement> getFineManagement(@PathVariable Long id) {
        log.debug("REST request to get FineManagement : {}", id);
        Optional<FineManagement> fineManagement = fineManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fineManagement);
    }

    /**
     * DELETE  /fine-managements/:id : delete the "id" fineManagement.
     *
     * @param id the id of the fineManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fine-managements/{id}")
    public ResponseEntity<Void> deleteFineManagement(@PathVariable Long id) {
        log.debug("REST request to delete FineManagement : {}", id);
        fineManagementRepository.deleteById(id);
        fineManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fine-managements?query=:query : search for the fineManagement corresponding
     * to the query.
     *
     * @param query the query of the fineManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/fine-managements")
    public List<FineManagement> searchFineManagements(@RequestParam String query) {
        log.debug("REST request to search FineManagements for query {}", query);
        return StreamSupport
            .stream(fineManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
