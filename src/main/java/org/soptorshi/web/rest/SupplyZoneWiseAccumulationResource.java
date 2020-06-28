package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyZoneWiseAccumulationQueryService;
import org.soptorshi.service.SupplyZoneWiseAccumulationService;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationCriteria;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
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
 * REST controller for managing SupplyZoneWiseAccumulation.
 */
@RestController
@RequestMapping("/api")
public class SupplyZoneWiseAccumulationResource {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneWiseAccumulationResource.class);

    private static final String ENTITY_NAME = "supplyZoneWiseAccumulation";

    private final SupplyZoneWiseAccumulationService supplyZoneWiseAccumulationService;

    private final SupplyZoneWiseAccumulationQueryService supplyZoneWiseAccumulationQueryService;

    public SupplyZoneWiseAccumulationResource(SupplyZoneWiseAccumulationService supplyZoneWiseAccumulationService, SupplyZoneWiseAccumulationQueryService supplyZoneWiseAccumulationQueryService) {
        this.supplyZoneWiseAccumulationService = supplyZoneWiseAccumulationService;
        this.supplyZoneWiseAccumulationQueryService = supplyZoneWiseAccumulationQueryService;
    }

    /**
     * POST  /supply-zone-wise-accumulations : Create a new supplyZoneWiseAccumulation.
     *
     * @param supplyZoneWiseAccumulationDTO the supplyZoneWiseAccumulationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyZoneWiseAccumulationDTO, or with status 400 (Bad Request) if the supplyZoneWiseAccumulation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-zone-wise-accumulations")
    public ResponseEntity<SupplyZoneWiseAccumulationDTO> createSupplyZoneWiseAccumulation(@Valid @RequestBody SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        if (supplyZoneWiseAccumulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyZoneWiseAccumulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationService.save(supplyZoneWiseAccumulationDTO);
        return ResponseEntity.created(new URI("/api/supply-zone-wise-accumulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-zone-wise-accumulations : Updates an existing supplyZoneWiseAccumulation.
     *
     * @param supplyZoneWiseAccumulationDTO the supplyZoneWiseAccumulationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyZoneWiseAccumulationDTO,
     * or with status 400 (Bad Request) if the supplyZoneWiseAccumulationDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyZoneWiseAccumulationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-zone-wise-accumulations")
    public ResponseEntity<SupplyZoneWiseAccumulationDTO> updateSupplyZoneWiseAccumulation(@Valid @RequestBody SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        if (supplyZoneWiseAccumulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationService.save(supplyZoneWiseAccumulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyZoneWiseAccumulationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-zone-wise-accumulations : get all the supplyZoneWiseAccumulations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyZoneWiseAccumulations in body
     */
    @GetMapping("/supply-zone-wise-accumulations")
    public ResponseEntity<List<SupplyZoneWiseAccumulationDTO>> getAllSupplyZoneWiseAccumulations(SupplyZoneWiseAccumulationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyZoneWiseAccumulations by criteria: {}", criteria);
        Page<SupplyZoneWiseAccumulationDTO> page = supplyZoneWiseAccumulationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-zone-wise-accumulations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-zone-wise-accumulations/count : count all the supplyZoneWiseAccumulations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-zone-wise-accumulations/count")
    public ResponseEntity<Long> countSupplyZoneWiseAccumulations(SupplyZoneWiseAccumulationCriteria criteria) {
        log.debug("REST request to count SupplyZoneWiseAccumulations by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyZoneWiseAccumulationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-zone-wise-accumulations/:id : get the "id" supplyZoneWiseAccumulation.
     *
     * @param id the id of the supplyZoneWiseAccumulationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyZoneWiseAccumulationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-zone-wise-accumulations/{id}")
    public ResponseEntity<SupplyZoneWiseAccumulationDTO> getSupplyZoneWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to get SupplyZoneWiseAccumulation : {}", id);
        Optional<SupplyZoneWiseAccumulationDTO> supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyZoneWiseAccumulationDTO);
    }

    /**
     * DELETE  /supply-zone-wise-accumulations/:id : delete the "id" supplyZoneWiseAccumulation.
     *
     * @param id the id of the supplyZoneWiseAccumulationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-zone-wise-accumulations/{id}")
    public ResponseEntity<Void> deleteSupplyZoneWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to delete SupplyZoneWiseAccumulation : {}", id);
        supplyZoneWiseAccumulationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-zone-wise-accumulations?query=:query : search for the supplyZoneWiseAccumulation corresponding
     * to the query.
     *
     * @param query the query of the supplyZoneWiseAccumulation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-zone-wise-accumulations")
    public ResponseEntity<List<SupplyZoneWiseAccumulationDTO>> searchSupplyZoneWiseAccumulations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyZoneWiseAccumulations for query {}", query);
        Page<SupplyZoneWiseAccumulationDTO> page = supplyZoneWiseAccumulationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-zone-wise-accumulations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
