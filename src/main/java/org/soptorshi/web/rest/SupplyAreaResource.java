package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyAreaQueryService;
import org.soptorshi.service.SupplyAreaService;
import org.soptorshi.service.dto.SupplyAreaCriteria;
import org.soptorshi.service.dto.SupplyAreaDTO;
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
 * REST controller for managing SupplyArea.
 */
@RestController
@RequestMapping("/api")
public class SupplyAreaResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaResource.class);

    private static final String ENTITY_NAME = "supplyArea";

    private final SupplyAreaService supplyAreaService;

    private final SupplyAreaQueryService supplyAreaQueryService;

    public SupplyAreaResource(SupplyAreaService supplyAreaService, SupplyAreaQueryService supplyAreaQueryService) {
        this.supplyAreaService = supplyAreaService;
        this.supplyAreaQueryService = supplyAreaQueryService;
    }

    /**
     * POST  /supply-areas : Create a new supplyArea.
     *
     * @param supplyAreaDTO the supplyAreaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyAreaDTO, or with status 400 (Bad Request) if the supplyArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-areas")
    public ResponseEntity<SupplyAreaDTO> createSupplyArea(@Valid @RequestBody SupplyAreaDTO supplyAreaDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyArea : {}", supplyAreaDTO);
        if (supplyAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyAreaDTO result = supplyAreaService.save(supplyAreaDTO);
        return ResponseEntity.created(new URI("/api/supply-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-areas : Updates an existing supplyArea.
     *
     * @param supplyAreaDTO the supplyAreaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyAreaDTO,
     * or with status 400 (Bad Request) if the supplyAreaDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyAreaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-areas")
    public ResponseEntity<SupplyAreaDTO> updateSupplyArea(@Valid @RequestBody SupplyAreaDTO supplyAreaDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyArea : {}", supplyAreaDTO);
        if (supplyAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyAreaDTO result = supplyAreaService.save(supplyAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-areas : get all the supplyAreas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyAreas in body
     */
    @GetMapping("/supply-areas")
    public ResponseEntity<List<SupplyAreaDTO>> getAllSupplyAreas(SupplyAreaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyAreas by criteria: {}", criteria);
        Page<SupplyAreaDTO> page = supplyAreaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-areas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-areas/count : count all the supplyAreas.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-areas/count")
    public ResponseEntity<Long> countSupplyAreas(SupplyAreaCriteria criteria) {
        log.debug("REST request to count SupplyAreas by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyAreaQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-areas/:id : get the "id" supplyArea.
     *
     * @param id the id of the supplyAreaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyAreaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-areas/{id}")
    public ResponseEntity<SupplyAreaDTO> getSupplyArea(@PathVariable Long id) {
        log.debug("REST request to get SupplyArea : {}", id);
        Optional<SupplyAreaDTO> supplyAreaDTO = supplyAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyAreaDTO);
    }

    /**
     * DELETE  /supply-areas/:id : delete the "id" supplyArea.
     *
     * @param id the id of the supplyAreaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-areas/{id}")
    public ResponseEntity<Void> deleteSupplyArea(@PathVariable Long id) {
        log.debug("REST request to delete SupplyArea : {}", id);
        supplyAreaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-areas?query=:query : search for the supplyArea corresponding
     * to the query.
     *
     * @param query the query of the supplyArea search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-areas")
    public ResponseEntity<List<SupplyAreaDTO>> searchSupplyAreas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyAreas for query {}", query);
        Page<SupplyAreaDTO> page = supplyAreaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-areas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
