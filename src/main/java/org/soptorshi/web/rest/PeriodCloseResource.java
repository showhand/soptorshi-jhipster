package org.soptorshi.web.rest;
import org.soptorshi.service.PeriodCloseService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.dto.PeriodCloseCriteria;
import org.soptorshi.service.PeriodCloseQueryService;
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
 * REST controller for managing PeriodClose.
 */
@RestController
@RequestMapping("/api")
public class PeriodCloseResource {

    private final Logger log = LoggerFactory.getLogger(PeriodCloseResource.class);

    private static final String ENTITY_NAME = "periodClose";

    private final PeriodCloseService periodCloseService;

    private final PeriodCloseQueryService periodCloseQueryService;

    public PeriodCloseResource(PeriodCloseService periodCloseService, PeriodCloseQueryService periodCloseQueryService) {
        this.periodCloseService = periodCloseService;
        this.periodCloseQueryService = periodCloseQueryService;
    }

    /**
     * POST  /period-closes : Create a new periodClose.
     *
     * @param periodCloseDTO the periodCloseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodCloseDTO, or with status 400 (Bad Request) if the periodClose has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/period-closes")
    public ResponseEntity<PeriodCloseDTO> createPeriodClose(@RequestBody PeriodCloseDTO periodCloseDTO) throws URISyntaxException {
        log.debug("REST request to save PeriodClose : {}", periodCloseDTO);
        if (periodCloseDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodClose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodCloseDTO result = periodCloseService.save(periodCloseDTO);
        return ResponseEntity.created(new URI("/api/period-closes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /period-closes : Updates an existing periodClose.
     *
     * @param periodCloseDTO the periodCloseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodCloseDTO,
     * or with status 400 (Bad Request) if the periodCloseDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodCloseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/period-closes")
    public ResponseEntity<PeriodCloseDTO> updatePeriodClose(@RequestBody PeriodCloseDTO periodCloseDTO) throws URISyntaxException {
        log.debug("REST request to update PeriodClose : {}", periodCloseDTO);
        if (periodCloseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodCloseDTO result = periodCloseService.save(periodCloseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodCloseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /period-closes : get all the periodCloses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of periodCloses in body
     */
    @GetMapping("/period-closes")
    public ResponseEntity<List<PeriodCloseDTO>> getAllPeriodCloses(PeriodCloseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PeriodCloses by criteria: {}", criteria);
        Page<PeriodCloseDTO> page = periodCloseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/period-closes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /period-closes/count : count all the periodCloses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/period-closes/count")
    public ResponseEntity<Long> countPeriodCloses(PeriodCloseCriteria criteria) {
        log.debug("REST request to count PeriodCloses by criteria: {}", criteria);
        return ResponseEntity.ok().body(periodCloseQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /period-closes/:id : get the "id" periodClose.
     *
     * @param id the id of the periodCloseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodCloseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/period-closes/{id}")
    public ResponseEntity<PeriodCloseDTO> getPeriodClose(@PathVariable Long id) {
        log.debug("REST request to get PeriodClose : {}", id);
        Optional<PeriodCloseDTO> periodCloseDTO = periodCloseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodCloseDTO);
    }

    /**
     * DELETE  /period-closes/:id : delete the "id" periodClose.
     *
     * @param id the id of the periodCloseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/period-closes/{id}")
    public ResponseEntity<Void> deletePeriodClose(@PathVariable Long id) {
        log.debug("REST request to delete PeriodClose : {}", id);
        periodCloseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/period-closes?query=:query : search for the periodClose corresponding
     * to the query.
     *
     * @param query the query of the periodClose search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/period-closes")
    public ResponseEntity<List<PeriodCloseDTO>> searchPeriodCloses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PeriodCloses for query {}", query);
        Page<PeriodCloseDTO> page = periodCloseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/period-closes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
