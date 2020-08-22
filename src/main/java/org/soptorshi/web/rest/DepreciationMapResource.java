package org.soptorshi.web.rest;
import org.soptorshi.service.DepreciationMapService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DepreciationMapDTO;
import org.soptorshi.service.dto.DepreciationMapCriteria;
import org.soptorshi.service.DepreciationMapQueryService;
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
 * REST controller for managing DepreciationMap.
 */
@RestController
@RequestMapping("/api")
public class DepreciationMapResource {

    private final Logger log = LoggerFactory.getLogger(DepreciationMapResource.class);

    private static final String ENTITY_NAME = "depreciationMap";

    private final DepreciationMapService depreciationMapService;

    private final DepreciationMapQueryService depreciationMapQueryService;

    public DepreciationMapResource(DepreciationMapService depreciationMapService, DepreciationMapQueryService depreciationMapQueryService) {
        this.depreciationMapService = depreciationMapService;
        this.depreciationMapQueryService = depreciationMapQueryService;
    }

    /**
     * POST  /depreciation-maps : Create a new depreciationMap.
     *
     * @param depreciationMapDTO the depreciationMapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depreciationMapDTO, or with status 400 (Bad Request) if the depreciationMap has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depreciation-maps")
    public ResponseEntity<DepreciationMapDTO> createDepreciationMap(@RequestBody DepreciationMapDTO depreciationMapDTO) throws URISyntaxException {
        log.debug("REST request to save DepreciationMap : {}", depreciationMapDTO);
        if (depreciationMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new depreciationMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepreciationMapDTO result = depreciationMapService.save(depreciationMapDTO);
        return ResponseEntity.created(new URI("/api/depreciation-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depreciation-maps : Updates an existing depreciationMap.
     *
     * @param depreciationMapDTO the depreciationMapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depreciationMapDTO,
     * or with status 400 (Bad Request) if the depreciationMapDTO is not valid,
     * or with status 500 (Internal Server Error) if the depreciationMapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depreciation-maps")
    public ResponseEntity<DepreciationMapDTO> updateDepreciationMap(@RequestBody DepreciationMapDTO depreciationMapDTO) throws URISyntaxException {
        log.debug("REST request to update DepreciationMap : {}", depreciationMapDTO);
        if (depreciationMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepreciationMapDTO result = depreciationMapService.save(depreciationMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depreciationMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depreciation-maps : get all the depreciationMaps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of depreciationMaps in body
     */
    @GetMapping("/depreciation-maps")
    public ResponseEntity<List<DepreciationMapDTO>> getAllDepreciationMaps(DepreciationMapCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DepreciationMaps by criteria: {}", criteria);
        Page<DepreciationMapDTO> page = depreciationMapQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/depreciation-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /depreciation-maps/count : count all the depreciationMaps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/depreciation-maps/count")
    public ResponseEntity<Long> countDepreciationMaps(DepreciationMapCriteria criteria) {
        log.debug("REST request to count DepreciationMaps by criteria: {}", criteria);
        return ResponseEntity.ok().body(depreciationMapQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /depreciation-maps/:id : get the "id" depreciationMap.
     *
     * @param id the id of the depreciationMapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depreciationMapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/depreciation-maps/{id}")
    public ResponseEntity<DepreciationMapDTO> getDepreciationMap(@PathVariable Long id) {
        log.debug("REST request to get DepreciationMap : {}", id);
        Optional<DepreciationMapDTO> depreciationMapDTO = depreciationMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depreciationMapDTO);
    }

    /**
     * DELETE  /depreciation-maps/:id : delete the "id" depreciationMap.
     *
     * @param id the id of the depreciationMapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depreciation-maps/{id}")
    public ResponseEntity<Void> deleteDepreciationMap(@PathVariable Long id) {
        log.debug("REST request to delete DepreciationMap : {}", id);
        depreciationMapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/depreciation-maps?query=:query : search for the depreciationMap corresponding
     * to the query.
     *
     * @param query the query of the depreciationMap search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/depreciation-maps")
    public ResponseEntity<List<DepreciationMapDTO>> searchDepreciationMaps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DepreciationMaps for query {}", query);
        Page<DepreciationMapDTO> page = depreciationMapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/depreciation-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
