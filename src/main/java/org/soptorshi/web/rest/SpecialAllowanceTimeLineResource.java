package org.soptorshi.web.rest;
import org.soptorshi.service.SpecialAllowanceTimeLineService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineDTO;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineCriteria;
import org.soptorshi.service.SpecialAllowanceTimeLineQueryService;
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
 * REST controller for managing SpecialAllowanceTimeLine.
 */
@RestController
@RequestMapping("/api")
public class SpecialAllowanceTimeLineResource {

    private final Logger log = LoggerFactory.getLogger(SpecialAllowanceTimeLineResource.class);

    private static final String ENTITY_NAME = "specialAllowanceTimeLine";

    private final SpecialAllowanceTimeLineService specialAllowanceTimeLineService;

    private final SpecialAllowanceTimeLineQueryService specialAllowanceTimeLineQueryService;

    public SpecialAllowanceTimeLineResource(SpecialAllowanceTimeLineService specialAllowanceTimeLineService, SpecialAllowanceTimeLineQueryService specialAllowanceTimeLineQueryService) {
        this.specialAllowanceTimeLineService = specialAllowanceTimeLineService;
        this.specialAllowanceTimeLineQueryService = specialAllowanceTimeLineQueryService;
    }

    /**
     * POST  /special-allowance-time-lines : Create a new specialAllowanceTimeLine.
     *
     * @param specialAllowanceTimeLineDTO the specialAllowanceTimeLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialAllowanceTimeLineDTO, or with status 400 (Bad Request) if the specialAllowanceTimeLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/special-allowance-time-lines")
    public ResponseEntity<SpecialAllowanceTimeLineDTO> createSpecialAllowanceTimeLine(@RequestBody SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO) throws URISyntaxException {
        log.debug("REST request to save SpecialAllowanceTimeLine : {}", specialAllowanceTimeLineDTO);
        if (specialAllowanceTimeLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialAllowanceTimeLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialAllowanceTimeLineDTO result = specialAllowanceTimeLineService.save(specialAllowanceTimeLineDTO);
        return ResponseEntity.created(new URI("/api/special-allowance-time-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /special-allowance-time-lines : Updates an existing specialAllowanceTimeLine.
     *
     * @param specialAllowanceTimeLineDTO the specialAllowanceTimeLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialAllowanceTimeLineDTO,
     * or with status 400 (Bad Request) if the specialAllowanceTimeLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the specialAllowanceTimeLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/special-allowance-time-lines")
    public ResponseEntity<SpecialAllowanceTimeLineDTO> updateSpecialAllowanceTimeLine(@RequestBody SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO) throws URISyntaxException {
        log.debug("REST request to update SpecialAllowanceTimeLine : {}", specialAllowanceTimeLineDTO);
        if (specialAllowanceTimeLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecialAllowanceTimeLineDTO result = specialAllowanceTimeLineService.save(specialAllowanceTimeLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialAllowanceTimeLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /special-allowance-time-lines : get all the specialAllowanceTimeLines.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of specialAllowanceTimeLines in body
     */
    @GetMapping("/special-allowance-time-lines")
    public ResponseEntity<List<SpecialAllowanceTimeLineDTO>> getAllSpecialAllowanceTimeLines(SpecialAllowanceTimeLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SpecialAllowanceTimeLines by criteria: {}", criteria);
        Page<SpecialAllowanceTimeLineDTO> page = specialAllowanceTimeLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/special-allowance-time-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /special-allowance-time-lines/count : count all the specialAllowanceTimeLines.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/special-allowance-time-lines/count")
    public ResponseEntity<Long> countSpecialAllowanceTimeLines(SpecialAllowanceTimeLineCriteria criteria) {
        log.debug("REST request to count SpecialAllowanceTimeLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(specialAllowanceTimeLineQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /special-allowance-time-lines/:id : get the "id" specialAllowanceTimeLine.
     *
     * @param id the id of the specialAllowanceTimeLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialAllowanceTimeLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/special-allowance-time-lines/{id}")
    public ResponseEntity<SpecialAllowanceTimeLineDTO> getSpecialAllowanceTimeLine(@PathVariable Long id) {
        log.debug("REST request to get SpecialAllowanceTimeLine : {}", id);
        Optional<SpecialAllowanceTimeLineDTO> specialAllowanceTimeLineDTO = specialAllowanceTimeLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialAllowanceTimeLineDTO);
    }

    /**
     * DELETE  /special-allowance-time-lines/:id : delete the "id" specialAllowanceTimeLine.
     *
     * @param id the id of the specialAllowanceTimeLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/special-allowance-time-lines/{id}")
    public ResponseEntity<Void> deleteSpecialAllowanceTimeLine(@PathVariable Long id) {
        log.debug("REST request to delete SpecialAllowanceTimeLine : {}", id);
        specialAllowanceTimeLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/special-allowance-time-lines?query=:query : search for the specialAllowanceTimeLine corresponding
     * to the query.
     *
     * @param query the query of the specialAllowanceTimeLine search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/special-allowance-time-lines")
    public ResponseEntity<List<SpecialAllowanceTimeLineDTO>> searchSpecialAllowanceTimeLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SpecialAllowanceTimeLines for query {}", query);
        Page<SpecialAllowanceTimeLineDTO> page = specialAllowanceTimeLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/special-allowance-time-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
