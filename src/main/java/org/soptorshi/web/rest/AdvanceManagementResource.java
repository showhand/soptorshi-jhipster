package org.soptorshi.web.rest;
import org.soptorshi.domain.AdvanceManagement;
import org.soptorshi.repository.AdvanceManagementRepository;
import org.soptorshi.repository.search.AdvanceManagementSearchRepository;
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
 * REST controller for managing AdvanceManagement.
 */
@RestController
@RequestMapping("/api")
public class AdvanceManagementResource {

    private final Logger log = LoggerFactory.getLogger(AdvanceManagementResource.class);

    private static final String ENTITY_NAME = "advanceManagement";

    private final AdvanceManagementRepository advanceManagementRepository;

    private final AdvanceManagementSearchRepository advanceManagementSearchRepository;

    public AdvanceManagementResource(AdvanceManagementRepository advanceManagementRepository, AdvanceManagementSearchRepository advanceManagementSearchRepository) {
        this.advanceManagementRepository = advanceManagementRepository;
        this.advanceManagementSearchRepository = advanceManagementSearchRepository;
    }

    /**
     * POST  /advance-managements : Create a new advanceManagement.
     *
     * @param advanceManagement the advanceManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advanceManagement, or with status 400 (Bad Request) if the advanceManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/advance-managements")
    public ResponseEntity<AdvanceManagement> createAdvanceManagement(@RequestBody AdvanceManagement advanceManagement) throws URISyntaxException {
        log.debug("REST request to save AdvanceManagement : {}", advanceManagement);
        if (advanceManagement.getId() != null) {
            throw new BadRequestAlertException("A new advanceManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvanceManagement result = advanceManagementRepository.save(advanceManagement);
        advanceManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/advance-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /advance-managements : Updates an existing advanceManagement.
     *
     * @param advanceManagement the advanceManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advanceManagement,
     * or with status 400 (Bad Request) if the advanceManagement is not valid,
     * or with status 500 (Internal Server Error) if the advanceManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/advance-managements")
    public ResponseEntity<AdvanceManagement> updateAdvanceManagement(@RequestBody AdvanceManagement advanceManagement) throws URISyntaxException {
        log.debug("REST request to update AdvanceManagement : {}", advanceManagement);
        if (advanceManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvanceManagement result = advanceManagementRepository.save(advanceManagement);
        advanceManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, advanceManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /advance-managements : get all the advanceManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of advanceManagements in body
     */
    @GetMapping("/advance-managements")
    public List<AdvanceManagement> getAllAdvanceManagements() {
        log.debug("REST request to get all AdvanceManagements");
        return advanceManagementRepository.findAll();
    }

    /**
     * GET  /advance-managements/:id : get the "id" advanceManagement.
     *
     * @param id the id of the advanceManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advanceManagement, or with status 404 (Not Found)
     */
    @GetMapping("/advance-managements/{id}")
    public ResponseEntity<AdvanceManagement> getAdvanceManagement(@PathVariable Long id) {
        log.debug("REST request to get AdvanceManagement : {}", id);
        Optional<AdvanceManagement> advanceManagement = advanceManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(advanceManagement);
    }

    /**
     * DELETE  /advance-managements/:id : delete the "id" advanceManagement.
     *
     * @param id the id of the advanceManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/advance-managements/{id}")
    public ResponseEntity<Void> deleteAdvanceManagement(@PathVariable Long id) {
        log.debug("REST request to delete AdvanceManagement : {}", id);
        advanceManagementRepository.deleteById(id);
        advanceManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/advance-managements?query=:query : search for the advanceManagement corresponding
     * to the query.
     *
     * @param query the query of the advanceManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/advance-managements")
    public List<AdvanceManagement> searchAdvanceManagements(@RequestParam String query) {
        log.debug("REST request to search AdvanceManagements for query {}", query);
        return StreamSupport
            .stream(advanceManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
