package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.OverTimeQueryService;
import org.soptorshi.service.OverTimeService;
import org.soptorshi.service.dto.OverTimeCriteria;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OverTime.
 */
@RestController
@RequestMapping("/api")
public class OverTimeResource {

    private final Logger log = LoggerFactory.getLogger(OverTimeResource.class);

    private static final String ENTITY_NAME = "overTime";

    private final OverTimeService overTimeService;

    private final OverTimeQueryService overTimeQueryService;

    public OverTimeResource(OverTimeService overTimeService, OverTimeQueryService overTimeQueryService) {
        this.overTimeService = overTimeService;
        this.overTimeQueryService = overTimeQueryService;
    }

    /**
     * POST  /over-times : Create a new overTime.
     *
     * @param overTimeDTO the overTimeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new overTimeDTO, or with status 400 (Bad Request) if the overTime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/over-times")
    public ResponseEntity<OverTimeDTO> createOverTime(@Valid @RequestBody OverTimeDTO overTimeDTO) throws URISyntaxException {
        log.debug("REST request to save OverTime : {}", overTimeDTO);
        if (overTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new overTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OverTimeDTO result = overTimeService.save(overTimeDTO);
        return ResponseEntity.created(new URI("/api/over-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /over-times : Updates an existing overTime.
     *
     * @param overTimeDTO the overTimeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated overTimeDTO,
     * or with status 400 (Bad Request) if the overTimeDTO is not valid,
     * or with status 500 (Internal Server Error) if the overTimeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/over-times")
    public ResponseEntity<OverTimeDTO> updateOverTime(@Valid @RequestBody OverTimeDTO overTimeDTO) throws URISyntaxException {
        log.debug("REST request to update OverTime : {}", overTimeDTO);
        if (overTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OverTimeDTO result = overTimeService.save(overTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, overTimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /over-times : get all the overTimes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of overTimes in body
     */
    @GetMapping("/over-times")
    public ResponseEntity<List<OverTimeDTO>> getAllOverTimes(OverTimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OverTimes by criteria: {}", criteria);
        Page<OverTimeDTO> page = overTimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/over-times");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /over-times/count : count all the overTimes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/over-times/count")
    public ResponseEntity<Long> countOverTimes(OverTimeCriteria criteria) {
        log.debug("REST request to count OverTimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(overTimeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /over-times/:id : get the "id" overTime.
     *
     * @param id the id of the overTimeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the overTimeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/over-times/{id}")
    public ResponseEntity<OverTimeDTO> getOverTime(@PathVariable Long id) {
        log.debug("REST request to get OverTime : {}", id);
        Optional<OverTimeDTO> overTimeDTO = overTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overTimeDTO);
    }

    /**
     * DELETE  /over-times/:id : delete the "id" overTime.
     *
     * @param id the id of the overTimeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/over-times/{id}")
    public ResponseEntity<Void> deleteOverTime(@PathVariable Long id) {
        log.debug("REST request to delete OverTime : {}", id);
        overTimeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/over-times?query=:query : search for the overTime corresponding
     * to the query.
     *
     * @param query the query of the overTime search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/over-times")
    public ResponseEntity<List<OverTimeDTO>> searchOverTimes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OverTimes for query {}", query);
        Page<OverTimeDTO> page = overTimeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/over-times");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
