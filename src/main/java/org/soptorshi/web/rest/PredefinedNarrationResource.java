package org.soptorshi.web.rest;
import org.soptorshi.service.PredefinedNarrationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.dto.PredefinedNarrationCriteria;
import org.soptorshi.service.PredefinedNarrationQueryService;
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
 * REST controller for managing PredefinedNarration.
 */
@RestController
@RequestMapping("/api")
public class PredefinedNarrationResource {

    private final Logger log = LoggerFactory.getLogger(PredefinedNarrationResource.class);

    private static final String ENTITY_NAME = "predefinedNarration";

    private final PredefinedNarrationService predefinedNarrationService;

    private final PredefinedNarrationQueryService predefinedNarrationQueryService;

    public PredefinedNarrationResource(PredefinedNarrationService predefinedNarrationService, PredefinedNarrationQueryService predefinedNarrationQueryService) {
        this.predefinedNarrationService = predefinedNarrationService;
        this.predefinedNarrationQueryService = predefinedNarrationQueryService;
    }

    /**
     * POST  /predefined-narrations : Create a new predefinedNarration.
     *
     * @param predefinedNarrationDTO the predefinedNarrationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new predefinedNarrationDTO, or with status 400 (Bad Request) if the predefinedNarration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/predefined-narrations")
    public ResponseEntity<PredefinedNarrationDTO> createPredefinedNarration(@RequestBody PredefinedNarrationDTO predefinedNarrationDTO) throws URISyntaxException {
        log.debug("REST request to save PredefinedNarration : {}", predefinedNarrationDTO);
        if (predefinedNarrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new predefinedNarration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PredefinedNarrationDTO result = predefinedNarrationService.save(predefinedNarrationDTO);
        return ResponseEntity.created(new URI("/api/predefined-narrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /predefined-narrations : Updates an existing predefinedNarration.
     *
     * @param predefinedNarrationDTO the predefinedNarrationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated predefinedNarrationDTO,
     * or with status 400 (Bad Request) if the predefinedNarrationDTO is not valid,
     * or with status 500 (Internal Server Error) if the predefinedNarrationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/predefined-narrations")
    public ResponseEntity<PredefinedNarrationDTO> updatePredefinedNarration(@RequestBody PredefinedNarrationDTO predefinedNarrationDTO) throws URISyntaxException {
        log.debug("REST request to update PredefinedNarration : {}", predefinedNarrationDTO);
        if (predefinedNarrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PredefinedNarrationDTO result = predefinedNarrationService.save(predefinedNarrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, predefinedNarrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /predefined-narrations : get all the predefinedNarrations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of predefinedNarrations in body
     */
    @GetMapping("/predefined-narrations")
    public ResponseEntity<List<PredefinedNarrationDTO>> getAllPredefinedNarrations(PredefinedNarrationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PredefinedNarrations by criteria: {}", criteria);
        Page<PredefinedNarrationDTO> page = predefinedNarrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/predefined-narrations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /predefined-narrations/count : count all the predefinedNarrations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/predefined-narrations/count")
    public ResponseEntity<Long> countPredefinedNarrations(PredefinedNarrationCriteria criteria) {
        log.debug("REST request to count PredefinedNarrations by criteria: {}", criteria);
        return ResponseEntity.ok().body(predefinedNarrationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /predefined-narrations/:id : get the "id" predefinedNarration.
     *
     * @param id the id of the predefinedNarrationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the predefinedNarrationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/predefined-narrations/{id}")
    public ResponseEntity<PredefinedNarrationDTO> getPredefinedNarration(@PathVariable Long id) {
        log.debug("REST request to get PredefinedNarration : {}", id);
        Optional<PredefinedNarrationDTO> predefinedNarrationDTO = predefinedNarrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(predefinedNarrationDTO);
    }

    /**
     * DELETE  /predefined-narrations/:id : delete the "id" predefinedNarration.
     *
     * @param id the id of the predefinedNarrationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/predefined-narrations/{id}")
    public ResponseEntity<Void> deletePredefinedNarration(@PathVariable Long id) {
        log.debug("REST request to delete PredefinedNarration : {}", id);
        predefinedNarrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/predefined-narrations?query=:query : search for the predefinedNarration corresponding
     * to the query.
     *
     * @param query the query of the predefinedNarration search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/predefined-narrations")
    public ResponseEntity<List<PredefinedNarrationDTO>> searchPredefinedNarrations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PredefinedNarrations for query {}", query);
        Page<PredefinedNarrationDTO> page = predefinedNarrationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/predefined-narrations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
