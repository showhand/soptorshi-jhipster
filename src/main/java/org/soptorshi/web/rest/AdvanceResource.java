package org.soptorshi.web.rest;
import org.soptorshi.service.AdvanceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.AdvanceDTO;
import org.soptorshi.service.dto.AdvanceCriteria;
import org.soptorshi.service.AdvanceQueryService;
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
 * REST controller for managing Advance.
 */
@RestController
@RequestMapping("/api")
public class AdvanceResource {

    private final Logger log = LoggerFactory.getLogger(AdvanceResource.class);

    private static final String ENTITY_NAME = "advance";

    private final AdvanceService advanceService;

    private final AdvanceQueryService advanceQueryService;

    public AdvanceResource(AdvanceService advanceService, AdvanceQueryService advanceQueryService) {
        this.advanceService = advanceService;
        this.advanceQueryService = advanceQueryService;
    }

    /**
     * POST  /advances : Create a new advance.
     *
     * @param advanceDTO the advanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advanceDTO, or with status 400 (Bad Request) if the advance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/advances")
    public ResponseEntity<AdvanceDTO> createAdvance(@RequestBody AdvanceDTO advanceDTO) throws URISyntaxException {
        log.debug("REST request to save Advance : {}", advanceDTO);
        if (advanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new advance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvanceDTO result = advanceService.save(advanceDTO);
        return ResponseEntity.created(new URI("/api/advances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /advances : Updates an existing advance.
     *
     * @param advanceDTO the advanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advanceDTO,
     * or with status 400 (Bad Request) if the advanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the advanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/advances")
    public ResponseEntity<AdvanceDTO> updateAdvance(@RequestBody AdvanceDTO advanceDTO) throws URISyntaxException {
        log.debug("REST request to update Advance : {}", advanceDTO);
        if (advanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdvanceDTO result = advanceService.save(advanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, advanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /advances : get all the advances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of advances in body
     */
    @GetMapping("/advances")
    public ResponseEntity<List<AdvanceDTO>> getAllAdvances(AdvanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Advances by criteria: {}", criteria);
        Page<AdvanceDTO> page = advanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/advances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /advances/count : count all the advances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/advances/count")
    public ResponseEntity<Long> countAdvances(AdvanceCriteria criteria) {
        log.debug("REST request to count Advances by criteria: {}", criteria);
        return ResponseEntity.ok().body(advanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /advances/:id : get the "id" advance.
     *
     * @param id the id of the advanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/advances/{id}")
    public ResponseEntity<AdvanceDTO> getAdvance(@PathVariable Long id) {
        log.debug("REST request to get Advance : {}", id);
        Optional<AdvanceDTO> advanceDTO = advanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advanceDTO);
    }

    /**
     * DELETE  /advances/:id : delete the "id" advance.
     *
     * @param id the id of the advanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/advances/{id}")
    public ResponseEntity<Void> deleteAdvance(@PathVariable Long id) {
        log.debug("REST request to delete Advance : {}", id);
        advanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/advances?query=:query : search for the advance corresponding
     * to the query.
     *
     * @param query the query of the advance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/advances")
    public ResponseEntity<List<AdvanceDTO>> searchAdvances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Advances for query {}", query);
        Page<AdvanceDTO> page = advanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/advances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
