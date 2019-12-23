package org.soptorshi.web.rest;
import org.soptorshi.service.InventorySubLocationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.InventorySubLocationDTO;
import org.soptorshi.service.dto.InventorySubLocationCriteria;
import org.soptorshi.service.InventorySubLocationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InventorySubLocation.
 */
@RestController
@RequestMapping("/api")
public class InventorySubLocationResource {

    private final Logger log = LoggerFactory.getLogger(InventorySubLocationResource.class);

    private static final String ENTITY_NAME = "inventorySubLocation";

    private final InventorySubLocationService inventorySubLocationService;

    private final InventorySubLocationQueryService inventorySubLocationQueryService;

    public InventorySubLocationResource(InventorySubLocationService inventorySubLocationService, InventorySubLocationQueryService inventorySubLocationQueryService) {
        this.inventorySubLocationService = inventorySubLocationService;
        this.inventorySubLocationQueryService = inventorySubLocationQueryService;
    }

    /**
     * POST  /inventory-sub-locations : Create a new inventorySubLocation.
     *
     * @param inventorySubLocationDTO the inventorySubLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventorySubLocationDTO, or with status 400 (Bad Request) if the inventorySubLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventory-sub-locations")
    public ResponseEntity<InventorySubLocationDTO> createInventorySubLocation(@Valid @RequestBody InventorySubLocationDTO inventorySubLocationDTO) throws URISyntaxException {
        log.debug("REST request to save InventorySubLocation : {}", inventorySubLocationDTO);
        if (inventorySubLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventorySubLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventorySubLocationDTO result = inventorySubLocationService.save(inventorySubLocationDTO);
        return ResponseEntity.created(new URI("/api/inventory-sub-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventory-sub-locations : Updates an existing inventorySubLocation.
     *
     * @param inventorySubLocationDTO the inventorySubLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventorySubLocationDTO,
     * or with status 400 (Bad Request) if the inventorySubLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the inventorySubLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventory-sub-locations")
    public ResponseEntity<InventorySubLocationDTO> updateInventorySubLocation(@Valid @RequestBody InventorySubLocationDTO inventorySubLocationDTO) throws URISyntaxException {
        log.debug("REST request to update InventorySubLocation : {}", inventorySubLocationDTO);
        if (inventorySubLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventorySubLocationDTO result = inventorySubLocationService.save(inventorySubLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventorySubLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventory-sub-locations : get all the inventorySubLocations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inventorySubLocations in body
     */
    @GetMapping("/inventory-sub-locations")
    public ResponseEntity<List<InventorySubLocationDTO>> getAllInventorySubLocations(InventorySubLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InventorySubLocations by criteria: {}", criteria);
        Page<InventorySubLocationDTO> page = inventorySubLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventory-sub-locations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /inventory-sub-locations/count : count all the inventorySubLocations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/inventory-sub-locations/count")
    public ResponseEntity<Long> countInventorySubLocations(InventorySubLocationCriteria criteria) {
        log.debug("REST request to count InventorySubLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventorySubLocationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /inventory-sub-locations/:id : get the "id" inventorySubLocation.
     *
     * @param id the id of the inventorySubLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventorySubLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inventory-sub-locations/{id}")
    public ResponseEntity<InventorySubLocationDTO> getInventorySubLocation(@PathVariable Long id) {
        log.debug("REST request to get InventorySubLocation : {}", id);
        Optional<InventorySubLocationDTO> inventorySubLocationDTO = inventorySubLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventorySubLocationDTO);
    }

    /**
     * DELETE  /inventory-sub-locations/:id : delete the "id" inventorySubLocation.
     *
     * @param id the id of the inventorySubLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-sub-locations/{id}")
    public ResponseEntity<Void> deleteInventorySubLocation(@PathVariable Long id) {
        log.debug("REST request to delete InventorySubLocation : {}", id);
        inventorySubLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inventory-sub-locations?query=:query : search for the inventorySubLocation corresponding
     * to the query.
     *
     * @param query the query of the inventorySubLocation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/inventory-sub-locations")
    public ResponseEntity<List<InventorySubLocationDTO>> searchInventorySubLocations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InventorySubLocations for query {}", query);
        Page<InventorySubLocationDTO> page = inventorySubLocationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/inventory-sub-locations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
