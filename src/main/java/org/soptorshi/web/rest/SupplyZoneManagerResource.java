package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyZoneManagerQueryService;
import org.soptorshi.service.SupplyZoneManagerService;
import org.soptorshi.service.dto.SupplyZoneManagerCriteria;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
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
 * REST controller for managing SupplyZoneManager.
 */
@RestController
@RequestMapping("/api")
public class SupplyZoneManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerResource.class);

    private static final String ENTITY_NAME = "supplyZoneManager";

    private final SupplyZoneManagerService supplyZoneManagerService;

    private final SupplyZoneManagerQueryService supplyZoneManagerQueryService;

    public SupplyZoneManagerResource(SupplyZoneManagerService supplyZoneManagerService, SupplyZoneManagerQueryService supplyZoneManagerQueryService) {
        this.supplyZoneManagerService = supplyZoneManagerService;
        this.supplyZoneManagerQueryService = supplyZoneManagerQueryService;
    }

    /**
     * POST  /supply-zone-managers : Create a new supplyZoneManager.
     *
     * @param supplyZoneManagerDTO the supplyZoneManagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyZoneManagerDTO, or with status 400 (Bad Request) if the supplyZoneManager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-zone-managers")
    public ResponseEntity<SupplyZoneManagerDTO> createSupplyZoneManager(@Valid @RequestBody SupplyZoneManagerDTO supplyZoneManagerDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZoneManager : {}", supplyZoneManagerDTO);
        if (supplyZoneManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZoneManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneManagerDTO result = supplyZoneManagerService.save(supplyZoneManagerDTO);
        return ResponseEntity.created(new URI("/api/supply-zone-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-zone-managers : Updates an existing supplyZoneManager.
     *
     * @param supplyZoneManagerDTO the supplyZoneManagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyZoneManagerDTO,
     * or with status 400 (Bad Request) if the supplyZoneManagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyZoneManagerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-zone-managers")
    public ResponseEntity<SupplyZoneManagerDTO> updateSupplyZoneManager(@Valid @RequestBody SupplyZoneManagerDTO supplyZoneManagerDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZoneManager : {}", supplyZoneManagerDTO);
        if (supplyZoneManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneManagerDTO result = supplyZoneManagerService.save(supplyZoneManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-zone-managers : get all the supplyZoneManagers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyZoneManagers in body
     */
    @GetMapping("/supply-zone-managers")
    public ResponseEntity<List<SupplyZoneManagerDTO>> getAllSupplyZoneManagers(SupplyZoneManagerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyZoneManagers by criteria: {}", criteria);
        Page<SupplyZoneManagerDTO> page = supplyZoneManagerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-zone-managers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-zone-managers/count : count all the supplyZoneManagers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-zone-managers/count")
    public ResponseEntity<Long> countSupplyZoneManagers(SupplyZoneManagerCriteria criteria) {
        log.debug("REST request to count SupplyZoneManagers by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyZoneManagerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-zone-managers/:id : get the "id" supplyZoneManager.
     *
     * @param id the id of the supplyZoneManagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyZoneManagerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-zone-managers/{id}")
    public ResponseEntity<SupplyZoneManagerDTO> getSupplyZoneManager(@PathVariable Long id) {
        log.debug("REST request to get SupplyZoneManager : {}", id);
        Optional<SupplyZoneManagerDTO> supplyZoneManagerDTO = supplyZoneManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyZoneManagerDTO);
    }

    /**
     * DELETE  /supply-zone-managers/:id : delete the "id" supplyZoneManager.
     *
     * @param id the id of the supplyZoneManagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-zone-managers/{id}")
    public ResponseEntity<Void> deleteSupplyZoneManager(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZoneManager : {}", id);
        supplyZoneManagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-zone-managers?query=:query : search for the supplyZoneManager corresponding
     * to the query.
     *
     * @param query the query of the supplyZoneManager search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-zone-managers")
    public ResponseEntity<List<SupplyZoneManagerDTO>> searchSupplyZoneManagers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyZoneManagers for query {}", query);
        Page<SupplyZoneManagerDTO> page = supplyZoneManagerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-zone-managers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
