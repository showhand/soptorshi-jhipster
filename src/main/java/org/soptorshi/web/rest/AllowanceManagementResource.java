package org.soptorshi.web.rest;
import org.soptorshi.domain.AllowanceManagement;
import org.soptorshi.repository.AllowanceManagementRepository;
import org.soptorshi.repository.search.AllowanceManagementSearchRepository;
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
 * REST controller for managing AllowanceManagement.
 */
@RestController
@RequestMapping("/api")
public class AllowanceManagementResource {

    private final Logger log = LoggerFactory.getLogger(AllowanceManagementResource.class);

    private static final String ENTITY_NAME = "allowanceManagement";

    private final AllowanceManagementRepository allowanceManagementRepository;

    private final AllowanceManagementSearchRepository allowanceManagementSearchRepository;

    public AllowanceManagementResource(AllowanceManagementRepository allowanceManagementRepository, AllowanceManagementSearchRepository allowanceManagementSearchRepository) {
        this.allowanceManagementRepository = allowanceManagementRepository;
        this.allowanceManagementSearchRepository = allowanceManagementSearchRepository;
    }

    /**
     * POST  /allowance-managements : Create a new allowanceManagement.
     *
     * @param allowanceManagement the allowanceManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allowanceManagement, or with status 400 (Bad Request) if the allowanceManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allowance-managements")
    public ResponseEntity<AllowanceManagement> createAllowanceManagement(@RequestBody AllowanceManagement allowanceManagement) throws URISyntaxException {
        log.debug("REST request to save AllowanceManagement : {}", allowanceManagement);
        if (allowanceManagement.getId() != null) {
            throw new BadRequestAlertException("A new allowanceManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllowanceManagement result = allowanceManagementRepository.save(allowanceManagement);
        allowanceManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/allowance-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allowance-managements : Updates an existing allowanceManagement.
     *
     * @param allowanceManagement the allowanceManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allowanceManagement,
     * or with status 400 (Bad Request) if the allowanceManagement is not valid,
     * or with status 500 (Internal Server Error) if the allowanceManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allowance-managements")
    public ResponseEntity<AllowanceManagement> updateAllowanceManagement(@RequestBody AllowanceManagement allowanceManagement) throws URISyntaxException {
        log.debug("REST request to update AllowanceManagement : {}", allowanceManagement);
        if (allowanceManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllowanceManagement result = allowanceManagementRepository.save(allowanceManagement);
        allowanceManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allowanceManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allowance-managements : get all the allowanceManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of allowanceManagements in body
     */
    @GetMapping("/allowance-managements")
    public List<AllowanceManagement> getAllAllowanceManagements() {
        log.debug("REST request to get all AllowanceManagements");
        return allowanceManagementRepository.findAll();
    }

    /**
     * GET  /allowance-managements/:id : get the "id" allowanceManagement.
     *
     * @param id the id of the allowanceManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allowanceManagement, or with status 404 (Not Found)
     */
    @GetMapping("/allowance-managements/{id}")
    public ResponseEntity<AllowanceManagement> getAllowanceManagement(@PathVariable Long id) {
        log.debug("REST request to get AllowanceManagement : {}", id);
        Optional<AllowanceManagement> allowanceManagement = allowanceManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(allowanceManagement);
    }

    /**
     * DELETE  /allowance-managements/:id : delete the "id" allowanceManagement.
     *
     * @param id the id of the allowanceManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allowance-managements/{id}")
    public ResponseEntity<Void> deleteAllowanceManagement(@PathVariable Long id) {
        log.debug("REST request to delete AllowanceManagement : {}", id);
        allowanceManagementRepository.deleteById(id);
        allowanceManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/allowance-managements?query=:query : search for the allowanceManagement corresponding
     * to the query.
     *
     * @param query the query of the allowanceManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/allowance-managements")
    public List<AllowanceManagement> searchAllowanceManagements(@RequestParam String query) {
        log.debug("REST request to search AllowanceManagements for query {}", query);
        return StreamSupport
            .stream(allowanceManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
