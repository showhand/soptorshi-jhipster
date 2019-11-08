package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyZoneQueryService;
import org.soptorshi.service.SupplyZoneService;
import org.soptorshi.service.dto.SupplyZoneCriteria;
import org.soptorshi.service.dto.SupplyZoneDTO;
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
 * REST controller for managing SupplyZone.
 */
@RestController
@RequestMapping("/api")
public class SupplyZoneResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneResource.class);

    private static final String ENTITY_NAME = "supplyZone";

    private final SupplyZoneService supplyZoneService;

    private final SupplyZoneQueryService supplyZoneQueryService;

    public SupplyZoneResource(SupplyZoneService supplyZoneService, SupplyZoneQueryService supplyZoneQueryService) {
        this.supplyZoneService = supplyZoneService;
        this.supplyZoneQueryService = supplyZoneQueryService;
    }

    /**
     * POST  /supply-zones : Create a new supplyZone.
     *
     * @param supplyZoneDTO the supplyZoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyZoneDTO, or with status 400 (Bad Request) if the supplyZone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-zones")
    public ResponseEntity<SupplyZoneDTO> createSupplyZone(@Valid @RequestBody SupplyZoneDTO supplyZoneDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZone : {}", supplyZoneDTO);
        if (supplyZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneDTO result = supplyZoneService.save(supplyZoneDTO);
        return ResponseEntity.created(new URI("/api/supply-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-zones : Updates an existing supplyZone.
     *
     * @param supplyZoneDTO the supplyZoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyZoneDTO,
     * or with status 400 (Bad Request) if the supplyZoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyZoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-zones")
    public ResponseEntity<SupplyZoneDTO> updateSupplyZone(@Valid @RequestBody SupplyZoneDTO supplyZoneDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZone : {}", supplyZoneDTO);
        if (supplyZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneDTO result = supplyZoneService.save(supplyZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-zones : get all the supplyZones.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyZones in body
     */
    @GetMapping("/supply-zones")
    public ResponseEntity<List<SupplyZoneDTO>> getAllSupplyZones(SupplyZoneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyZones by criteria: {}", criteria);
        Page<SupplyZoneDTO> page = supplyZoneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-zones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-zones/count : count all the supplyZones.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-zones/count")
    public ResponseEntity<Long> countSupplyZones(SupplyZoneCriteria criteria) {
        log.debug("REST request to count SupplyZones by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyZoneQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-zones/:id : get the "id" supplyZone.
     *
     * @param id the id of the supplyZoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyZoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-zones/{id}")
    public ResponseEntity<SupplyZoneDTO> getSupplyZone(@PathVariable Long id) {
        log.debug("REST request to get SupplyZone : {}", id);
        Optional<SupplyZoneDTO> supplyZoneDTO = supplyZoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyZoneDTO);
    }

    /**
     * DELETE  /supply-zones/:id : delete the "id" supplyZone.
     *
     * @param id the id of the supplyZoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-zones/{id}")
    public ResponseEntity<Void> deleteSupplyZone(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZone : {}", id);
        supplyZoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-zones?query=:query : search for the supplyZone corresponding
     * to the query.
     *
     * @param query the query of the supplyZone search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-zones")
    public ResponseEntity<List<SupplyZoneDTO>> searchSupplyZones(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyZones for query {}", query);
        Page<SupplyZoneDTO> page = supplyZoneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-zones");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
