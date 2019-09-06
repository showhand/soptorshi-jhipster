package org.soptorshi.web.rest;
import org.soptorshi.service.SystemAccountMapService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.dto.SystemAccountMapCriteria;
import org.soptorshi.service.SystemAccountMapQueryService;
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
 * REST controller for managing SystemAccountMap.
 */
@RestController
@RequestMapping("/api")
public class SystemAccountMapResource {

    private final Logger log = LoggerFactory.getLogger(SystemAccountMapResource.class);

    private static final String ENTITY_NAME = "systemAccountMap";

    private final SystemAccountMapService systemAccountMapService;

    private final SystemAccountMapQueryService systemAccountMapQueryService;

    public SystemAccountMapResource(SystemAccountMapService systemAccountMapService, SystemAccountMapQueryService systemAccountMapQueryService) {
        this.systemAccountMapService = systemAccountMapService;
        this.systemAccountMapQueryService = systemAccountMapQueryService;
    }

    /**
     * POST  /system-account-maps : Create a new systemAccountMap.
     *
     * @param systemAccountMapDTO the systemAccountMapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemAccountMapDTO, or with status 400 (Bad Request) if the systemAccountMap has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-account-maps")
    public ResponseEntity<SystemAccountMapDTO> createSystemAccountMap(@RequestBody SystemAccountMapDTO systemAccountMapDTO) throws URISyntaxException {
        log.debug("REST request to save SystemAccountMap : {}", systemAccountMapDTO);
        if (systemAccountMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemAccountMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemAccountMapDTO result = systemAccountMapService.save(systemAccountMapDTO);
        return ResponseEntity.created(new URI("/api/system-account-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-account-maps : Updates an existing systemAccountMap.
     *
     * @param systemAccountMapDTO the systemAccountMapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemAccountMapDTO,
     * or with status 400 (Bad Request) if the systemAccountMapDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemAccountMapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-account-maps")
    public ResponseEntity<SystemAccountMapDTO> updateSystemAccountMap(@RequestBody SystemAccountMapDTO systemAccountMapDTO) throws URISyntaxException {
        log.debug("REST request to update SystemAccountMap : {}", systemAccountMapDTO);
        if (systemAccountMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemAccountMapDTO result = systemAccountMapService.save(systemAccountMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemAccountMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-account-maps : get all the systemAccountMaps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of systemAccountMaps in body
     */
    @GetMapping("/system-account-maps")
    public ResponseEntity<List<SystemAccountMapDTO>> getAllSystemAccountMaps(SystemAccountMapCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemAccountMaps by criteria: {}", criteria);
        Page<SystemAccountMapDTO> page = systemAccountMapQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-account-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /system-account-maps/count : count all the systemAccountMaps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/system-account-maps/count")
    public ResponseEntity<Long> countSystemAccountMaps(SystemAccountMapCriteria criteria) {
        log.debug("REST request to count SystemAccountMaps by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemAccountMapQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /system-account-maps/:id : get the "id" systemAccountMap.
     *
     * @param id the id of the systemAccountMapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemAccountMapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-account-maps/{id}")
    public ResponseEntity<SystemAccountMapDTO> getSystemAccountMap(@PathVariable Long id) {
        log.debug("REST request to get SystemAccountMap : {}", id);
        Optional<SystemAccountMapDTO> systemAccountMapDTO = systemAccountMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemAccountMapDTO);
    }

    /**
     * DELETE  /system-account-maps/:id : delete the "id" systemAccountMap.
     *
     * @param id the id of the systemAccountMapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-account-maps/{id}")
    public ResponseEntity<Void> deleteSystemAccountMap(@PathVariable Long id) {
        log.debug("REST request to delete SystemAccountMap : {}", id);
        systemAccountMapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/system-account-maps?query=:query : search for the systemAccountMap corresponding
     * to the query.
     *
     * @param query the query of the systemAccountMap search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/system-account-maps")
    public ResponseEntity<List<SystemAccountMapDTO>> searchSystemAccountMaps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SystemAccountMaps for query {}", query);
        Page<SystemAccountMapDTO> page = systemAccountMapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/system-account-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
