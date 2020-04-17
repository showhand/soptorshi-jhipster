package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyAreaManagerQueryService;
import org.soptorshi.service.SupplyAreaManagerService;
import org.soptorshi.service.dto.SupplyAreaManagerCriteria;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
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
 * REST controller for managing SupplyAreaManager.
 */
@RestController
@RequestMapping("/api")
public class SupplyAreaManagerResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerResource.class);

    private static final String ENTITY_NAME = "supplyAreaManager";

    private final SupplyAreaManagerService supplyAreaManagerService;

    private final SupplyAreaManagerQueryService supplyAreaManagerQueryService;

    public SupplyAreaManagerResource(SupplyAreaManagerService supplyAreaManagerService, SupplyAreaManagerQueryService supplyAreaManagerQueryService) {
        this.supplyAreaManagerService = supplyAreaManagerService;
        this.supplyAreaManagerQueryService = supplyAreaManagerQueryService;
    }

    /**
     * POST  /supply-area-managers : Create a new supplyAreaManager.
     *
     * @param supplyAreaManagerDTO the supplyAreaManagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyAreaManagerDTO, or with status 400 (Bad Request) if the supplyAreaManager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-area-managers")
    public ResponseEntity<SupplyAreaManagerDTO> createSupplyAreaManager(@Valid @RequestBody SupplyAreaManagerDTO supplyAreaManagerDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyAreaManager : {}", supplyAreaManagerDTO);
        if (supplyAreaManagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyAreaManager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyAreaManagerDTO result = supplyAreaManagerService.save(supplyAreaManagerDTO);
        return ResponseEntity.created(new URI("/api/supply-area-managers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-area-managers : Updates an existing supplyAreaManager.
     *
     * @param supplyAreaManagerDTO the supplyAreaManagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyAreaManagerDTO,
     * or with status 400 (Bad Request) if the supplyAreaManagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyAreaManagerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-area-managers")
    public ResponseEntity<SupplyAreaManagerDTO> updateSupplyAreaManager(@Valid @RequestBody SupplyAreaManagerDTO supplyAreaManagerDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyAreaManager : {}", supplyAreaManagerDTO);
        if (supplyAreaManagerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyAreaManagerDTO result = supplyAreaManagerService.save(supplyAreaManagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaManagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-area-managers : get all the supplyAreaManagers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyAreaManagers in body
     */
    @GetMapping("/supply-area-managers")
    public ResponseEntity<List<SupplyAreaManagerDTO>> getAllSupplyAreaManagers(SupplyAreaManagerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyAreaManagers by criteria: {}", criteria);
        Page<SupplyAreaManagerDTO> page = supplyAreaManagerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-area-managers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-area-managers/count : count all the supplyAreaManagers.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-area-managers/count")
    public ResponseEntity<Long> countSupplyAreaManagers(SupplyAreaManagerCriteria criteria) {
        log.debug("REST request to count SupplyAreaManagers by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyAreaManagerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-area-managers/:id : get the "id" supplyAreaManager.
     *
     * @param id the id of the supplyAreaManagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyAreaManagerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-area-managers/{id}")
    public ResponseEntity<SupplyAreaManagerDTO> getSupplyAreaManager(@PathVariable Long id) {
        log.debug("REST request to get SupplyAreaManager : {}", id);
        Optional<SupplyAreaManagerDTO> supplyAreaManagerDTO = supplyAreaManagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyAreaManagerDTO);
    }

    /**
     * DELETE  /supply-area-managers/:id : delete the "id" supplyAreaManager.
     *
     * @param id the id of the supplyAreaManagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-area-managers/{id}")
    public ResponseEntity<Void> deleteSupplyAreaManager(@PathVariable Long id) {
        log.debug("REST request to delete SupplyAreaManager : {}", id);
        supplyAreaManagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-area-managers?query=:query : search for the supplyAreaManager corresponding
     * to the query.
     *
     * @param query the query of the supplyAreaManager search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-area-managers")
    public ResponseEntity<List<SupplyAreaManagerDTO>> searchSupplyAreaManagers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyAreaManagers for query {}", query);
        Page<SupplyAreaManagerDTO> page = supplyAreaManagerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-area-managers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
