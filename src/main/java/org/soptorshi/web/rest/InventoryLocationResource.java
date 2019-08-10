package org.soptorshi.web.rest;
import org.soptorshi.service.InventoryLocationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.InventoryLocationDTO;
import org.soptorshi.service.dto.InventoryLocationCriteria;
import org.soptorshi.service.InventoryLocationQueryService;
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
 * REST controller for managing InventoryLocation.
 */
@RestController
@RequestMapping("/api")
public class InventoryLocationResource {

    private final Logger log = LoggerFactory.getLogger(InventoryLocationResource.class);

    private static final String ENTITY_NAME = "inventoryLocation";

    private final InventoryLocationService inventoryLocationService;

    private final InventoryLocationQueryService inventoryLocationQueryService;

    public InventoryLocationResource(InventoryLocationService inventoryLocationService, InventoryLocationQueryService inventoryLocationQueryService) {
        this.inventoryLocationService = inventoryLocationService;
        this.inventoryLocationQueryService = inventoryLocationQueryService;
    }

    /**
     * POST  /inventory-locations : Create a new inventoryLocation.
     *
     * @param inventoryLocationDTO the inventoryLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryLocationDTO, or with status 400 (Bad Request) if the inventoryLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventory-locations")
    public ResponseEntity<InventoryLocationDTO> createInventoryLocation(@Valid @RequestBody InventoryLocationDTO inventoryLocationDTO) throws URISyntaxException {
        log.debug("REST request to save InventoryLocation : {}", inventoryLocationDTO);
        if (inventoryLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryLocationDTO result = inventoryLocationService.save(inventoryLocationDTO);
        return ResponseEntity.created(new URI("/api/inventory-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventory-locations : Updates an existing inventoryLocation.
     *
     * @param inventoryLocationDTO the inventoryLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventoryLocationDTO,
     * or with status 400 (Bad Request) if the inventoryLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the inventoryLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventory-locations")
    public ResponseEntity<InventoryLocationDTO> updateInventoryLocation(@Valid @RequestBody InventoryLocationDTO inventoryLocationDTO) throws URISyntaxException {
        log.debug("REST request to update InventoryLocation : {}", inventoryLocationDTO);
        if (inventoryLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryLocationDTO result = inventoryLocationService.save(inventoryLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inventoryLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventory-locations : get all the inventoryLocations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inventoryLocations in body
     */
    @GetMapping("/inventory-locations")
    public ResponseEntity<List<InventoryLocationDTO>> getAllInventoryLocations(InventoryLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InventoryLocations by criteria: {}", criteria);
        Page<InventoryLocationDTO> page = inventoryLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inventory-locations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /inventory-locations/count : count all the inventoryLocations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/inventory-locations/count")
    public ResponseEntity<Long> countInventoryLocations(InventoryLocationCriteria criteria) {
        log.debug("REST request to count InventoryLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryLocationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /inventory-locations/:id : get the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inventory-locations/{id}")
    public ResponseEntity<InventoryLocationDTO> getInventoryLocation(@PathVariable Long id) {
        log.debug("REST request to get InventoryLocation : {}", id);
        Optional<InventoryLocationDTO> inventoryLocationDTO = inventoryLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryLocationDTO);
    }

    /**
     * DELETE  /inventory-locations/:id : delete the "id" inventoryLocation.
     *
     * @param id the id of the inventoryLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventory-locations/{id}")
    public ResponseEntity<Void> deleteInventoryLocation(@PathVariable Long id) {
        log.debug("REST request to delete InventoryLocation : {}", id);
        inventoryLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inventory-locations?query=:query : search for the inventoryLocation corresponding
     * to the query.
     *
     * @param query the query of the inventoryLocation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/inventory-locations")
    public ResponseEntity<List<InventoryLocationDTO>> searchInventoryLocations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InventoryLocations for query {}", query);
        Page<InventoryLocationDTO> page = inventoryLocationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/inventory-locations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
