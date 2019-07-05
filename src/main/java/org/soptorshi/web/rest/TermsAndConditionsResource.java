package org.soptorshi.web.rest;
import org.soptorshi.service.TermsAndConditionsService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.TermsAndConditionsDTO;
import org.soptorshi.service.dto.TermsAndConditionsCriteria;
import org.soptorshi.service.TermsAndConditionsQueryService;
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
 * REST controller for managing TermsAndConditions.
 */
@RestController
@RequestMapping("/api")
public class TermsAndConditionsResource {

    private final Logger log = LoggerFactory.getLogger(TermsAndConditionsResource.class);

    private static final String ENTITY_NAME = "termsAndConditions";

    private final TermsAndConditionsService termsAndConditionsService;

    private final TermsAndConditionsQueryService termsAndConditionsQueryService;

    public TermsAndConditionsResource(TermsAndConditionsService termsAndConditionsService, TermsAndConditionsQueryService termsAndConditionsQueryService) {
        this.termsAndConditionsService = termsAndConditionsService;
        this.termsAndConditionsQueryService = termsAndConditionsQueryService;
    }

    /**
     * POST  /terms-and-conditions : Create a new termsAndConditions.
     *
     * @param termsAndConditionsDTO the termsAndConditionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new termsAndConditionsDTO, or with status 400 (Bad Request) if the termsAndConditions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terms-and-conditions")
    public ResponseEntity<TermsAndConditionsDTO> createTermsAndConditions(@RequestBody TermsAndConditionsDTO termsAndConditionsDTO) throws URISyntaxException {
        log.debug("REST request to save TermsAndConditions : {}", termsAndConditionsDTO);
        if (termsAndConditionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new termsAndConditions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TermsAndConditionsDTO result = termsAndConditionsService.save(termsAndConditionsDTO);
        return ResponseEntity.created(new URI("/api/terms-and-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terms-and-conditions : Updates an existing termsAndConditions.
     *
     * @param termsAndConditionsDTO the termsAndConditionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated termsAndConditionsDTO,
     * or with status 400 (Bad Request) if the termsAndConditionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the termsAndConditionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terms-and-conditions")
    public ResponseEntity<TermsAndConditionsDTO> updateTermsAndConditions(@RequestBody TermsAndConditionsDTO termsAndConditionsDTO) throws URISyntaxException {
        log.debug("REST request to update TermsAndConditions : {}", termsAndConditionsDTO);
        if (termsAndConditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TermsAndConditionsDTO result = termsAndConditionsService.save(termsAndConditionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, termsAndConditionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terms-and-conditions : get all the termsAndConditions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of termsAndConditions in body
     */
    @GetMapping("/terms-and-conditions")
    public ResponseEntity<List<TermsAndConditionsDTO>> getAllTermsAndConditions(TermsAndConditionsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TermsAndConditions by criteria: {}", criteria);
        Page<TermsAndConditionsDTO> page = termsAndConditionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terms-and-conditions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /terms-and-conditions/count : count all the termsAndConditions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/terms-and-conditions/count")
    public ResponseEntity<Long> countTermsAndConditions(TermsAndConditionsCriteria criteria) {
        log.debug("REST request to count TermsAndConditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(termsAndConditionsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /terms-and-conditions/:id : get the "id" termsAndConditions.
     *
     * @param id the id of the termsAndConditionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the termsAndConditionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/terms-and-conditions/{id}")
    public ResponseEntity<TermsAndConditionsDTO> getTermsAndConditions(@PathVariable Long id) {
        log.debug("REST request to get TermsAndConditions : {}", id);
        Optional<TermsAndConditionsDTO> termsAndConditionsDTO = termsAndConditionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termsAndConditionsDTO);
    }

    /**
     * DELETE  /terms-and-conditions/:id : delete the "id" termsAndConditions.
     *
     * @param id the id of the termsAndConditionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terms-and-conditions/{id}")
    public ResponseEntity<Void> deleteTermsAndConditions(@PathVariable Long id) {
        log.debug("REST request to delete TermsAndConditions : {}", id);
        termsAndConditionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/terms-and-conditions?query=:query : search for the termsAndConditions corresponding
     * to the query.
     *
     * @param query the query of the termsAndConditions search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/terms-and-conditions")
    public ResponseEntity<List<TermsAndConditionsDTO>> searchTermsAndConditions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TermsAndConditions for query {}", query);
        Page<TermsAndConditionsDTO> page = termsAndConditionsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/terms-and-conditions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
