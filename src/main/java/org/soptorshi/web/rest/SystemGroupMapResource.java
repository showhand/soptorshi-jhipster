package org.soptorshi.web.rest;
import org.soptorshi.service.SystemGroupMapService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.dto.SystemGroupMapCriteria;
import org.soptorshi.service.SystemGroupMapQueryService;
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
 * REST controller for managing SystemGroupMap.
 */
@RestController
@RequestMapping("/api")
public class SystemGroupMapResource {

    private final Logger log = LoggerFactory.getLogger(SystemGroupMapResource.class);

    private static final String ENTITY_NAME = "systemGroupMap";

    private final SystemGroupMapService systemGroupMapService;

    private final SystemGroupMapQueryService systemGroupMapQueryService;

    public SystemGroupMapResource(SystemGroupMapService systemGroupMapService, SystemGroupMapQueryService systemGroupMapQueryService) {
        this.systemGroupMapService = systemGroupMapService;
        this.systemGroupMapQueryService = systemGroupMapQueryService;
    }

    /**
     * POST  /system-group-maps : Create a new systemGroupMap.
     *
     * @param systemGroupMapDTO the systemGroupMapDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemGroupMapDTO, or with status 400 (Bad Request) if the systemGroupMap has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-group-maps")
    public ResponseEntity<SystemGroupMapDTO> createSystemGroupMap(@RequestBody SystemGroupMapDTO systemGroupMapDTO) throws URISyntaxException {
        log.debug("REST request to save SystemGroupMap : {}", systemGroupMapDTO);
        if (systemGroupMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemGroupMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemGroupMapDTO result = systemGroupMapService.save(systemGroupMapDTO);
        return ResponseEntity.created(new URI("/api/system-group-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-group-maps : Updates an existing systemGroupMap.
     *
     * @param systemGroupMapDTO the systemGroupMapDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemGroupMapDTO,
     * or with status 400 (Bad Request) if the systemGroupMapDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemGroupMapDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-group-maps")
    public ResponseEntity<SystemGroupMapDTO> updateSystemGroupMap(@RequestBody SystemGroupMapDTO systemGroupMapDTO) throws URISyntaxException {
        log.debug("REST request to update SystemGroupMap : {}", systemGroupMapDTO);
        if (systemGroupMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemGroupMapDTO result = systemGroupMapService.save(systemGroupMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemGroupMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-group-maps : get all the systemGroupMaps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of systemGroupMaps in body
     */
    @GetMapping("/system-group-maps")
    public ResponseEntity<List<SystemGroupMapDTO>> getAllSystemGroupMaps(SystemGroupMapCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SystemGroupMaps by criteria: {}", criteria);
        Page<SystemGroupMapDTO> page = systemGroupMapQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-group-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /system-group-maps/count : count all the systemGroupMaps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/system-group-maps/count")
    public ResponseEntity<Long> countSystemGroupMaps(SystemGroupMapCriteria criteria) {
        log.debug("REST request to count SystemGroupMaps by criteria: {}", criteria);
        return ResponseEntity.ok().body(systemGroupMapQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /system-group-maps/:id : get the "id" systemGroupMap.
     *
     * @param id the id of the systemGroupMapDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemGroupMapDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-group-maps/{id}")
    public ResponseEntity<SystemGroupMapDTO> getSystemGroupMap(@PathVariable Long id) {
        log.debug("REST request to get SystemGroupMap : {}", id);
        Optional<SystemGroupMapDTO> systemGroupMapDTO = systemGroupMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemGroupMapDTO);
    }

    /**
     * DELETE  /system-group-maps/:id : delete the "id" systemGroupMap.
     *
     * @param id the id of the systemGroupMapDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-group-maps/{id}")
    public ResponseEntity<Void> deleteSystemGroupMap(@PathVariable Long id) {
        log.debug("REST request to delete SystemGroupMap : {}", id);
        systemGroupMapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/system-group-maps?query=:query : search for the systemGroupMap corresponding
     * to the query.
     *
     * @param query the query of the systemGroupMap search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/system-group-maps")
    public ResponseEntity<List<SystemGroupMapDTO>> searchSystemGroupMaps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SystemGroupMaps for query {}", query);
        Page<SystemGroupMapDTO> page = systemGroupMapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/system-group-maps");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
