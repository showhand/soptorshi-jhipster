package org.soptorshi.web.rest;
import org.soptorshi.domain.ProvidentManagement;
import org.soptorshi.repository.ProvidentManagementRepository;
import org.soptorshi.repository.search.ProvidentManagementSearchRepository;
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
 * REST controller for managing ProvidentManagement.
 */
@RestController
@RequestMapping("/api")
public class ProvidentManagementResource {

    private final Logger log = LoggerFactory.getLogger(ProvidentManagementResource.class);

    private static final String ENTITY_NAME = "providentManagement";

    private final ProvidentManagementRepository providentManagementRepository;

    private final ProvidentManagementSearchRepository providentManagementSearchRepository;

    public ProvidentManagementResource(ProvidentManagementRepository providentManagementRepository, ProvidentManagementSearchRepository providentManagementSearchRepository) {
        this.providentManagementRepository = providentManagementRepository;
        this.providentManagementSearchRepository = providentManagementSearchRepository;
    }

    /**
     * POST  /provident-managements : Create a new providentManagement.
     *
     * @param providentManagement the providentManagement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providentManagement, or with status 400 (Bad Request) if the providentManagement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provident-managements")
    public ResponseEntity<ProvidentManagement> createProvidentManagement(@RequestBody ProvidentManagement providentManagement) throws URISyntaxException {
        log.debug("REST request to save ProvidentManagement : {}", providentManagement);
        if (providentManagement.getId() != null) {
            throw new BadRequestAlertException("A new providentManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvidentManagement result = providentManagementRepository.save(providentManagement);
        providentManagementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/provident-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provident-managements : Updates an existing providentManagement.
     *
     * @param providentManagement the providentManagement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providentManagement,
     * or with status 400 (Bad Request) if the providentManagement is not valid,
     * or with status 500 (Internal Server Error) if the providentManagement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provident-managements")
    public ResponseEntity<ProvidentManagement> updateProvidentManagement(@RequestBody ProvidentManagement providentManagement) throws URISyntaxException {
        log.debug("REST request to update ProvidentManagement : {}", providentManagement);
        if (providentManagement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvidentManagement result = providentManagementRepository.save(providentManagement);
        providentManagementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providentManagement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provident-managements : get all the providentManagements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providentManagements in body
     */
    @GetMapping("/provident-managements")
    public List<ProvidentManagement> getAllProvidentManagements() {
        log.debug("REST request to get all ProvidentManagements");
        return providentManagementRepository.findAll();
    }

    /**
     * GET  /provident-managements/:id : get the "id" providentManagement.
     *
     * @param id the id of the providentManagement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providentManagement, or with status 404 (Not Found)
     */
    @GetMapping("/provident-managements/{id}")
    public ResponseEntity<ProvidentManagement> getProvidentManagement(@PathVariable Long id) {
        log.debug("REST request to get ProvidentManagement : {}", id);
        Optional<ProvidentManagement> providentManagement = providentManagementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(providentManagement);
    }

    /**
     * DELETE  /provident-managements/:id : delete the "id" providentManagement.
     *
     * @param id the id of the providentManagement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provident-managements/{id}")
    public ResponseEntity<Void> deleteProvidentManagement(@PathVariable Long id) {
        log.debug("REST request to delete ProvidentManagement : {}", id);
        providentManagementRepository.deleteById(id);
        providentManagementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provident-managements?query=:query : search for the providentManagement corresponding
     * to the query.
     *
     * @param query the query of the providentManagement search
     * @return the result of the search
     */
    @GetMapping("/_search/provident-managements")
    public List<ProvidentManagement> searchProvidentManagements(@RequestParam String query) {
        log.debug("REST request to search ProvidentManagements for query {}", query);
        return StreamSupport
            .stream(providentManagementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
