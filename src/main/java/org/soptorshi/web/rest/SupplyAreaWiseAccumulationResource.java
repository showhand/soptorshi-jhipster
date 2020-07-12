package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyAreaWiseAccumulationQueryService;
import org.soptorshi.service.SupplyAreaWiseAccumulationService;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationCriteria;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
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
 * REST controller for managing SupplyAreaWiseAccumulation.
 */
@RestController
@RequestMapping("/api")
public class SupplyAreaWiseAccumulationResource {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaWiseAccumulationResource.class);

    private static final String ENTITY_NAME = "supplyAreaWiseAccumulation";

    private final SupplyAreaWiseAccumulationService supplyAreaWiseAccumulationService;

    private final SupplyAreaWiseAccumulationQueryService supplyAreaWiseAccumulationQueryService;

    public SupplyAreaWiseAccumulationResource(SupplyAreaWiseAccumulationService supplyAreaWiseAccumulationService, SupplyAreaWiseAccumulationQueryService supplyAreaWiseAccumulationQueryService) {
        this.supplyAreaWiseAccumulationService = supplyAreaWiseAccumulationService;
        this.supplyAreaWiseAccumulationQueryService = supplyAreaWiseAccumulationQueryService;
    }

    /**
     * POST  /supply-area-wise-accumulations : Create a new supplyAreaWiseAccumulation.
     *
     * @param supplyAreaWiseAccumulationDTO the supplyAreaWiseAccumulationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyAreaWiseAccumulationDTO, or with status 400 (Bad Request) if the supplyAreaWiseAccumulation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-area-wise-accumulations")
    public ResponseEntity<SupplyAreaWiseAccumulationDTO> createSupplyAreaWiseAccumulation(@Valid @RequestBody SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTO);
        if (supplyAreaWiseAccumulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyAreaWiseAccumulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationService.save(supplyAreaWiseAccumulationDTO);
        return ResponseEntity.created(new URI("/api/supply-area-wise-accumulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-area-wise-accumulations : Updates an existing supplyAreaWiseAccumulation.
     *
     * @param supplyAreaWiseAccumulationDTO the supplyAreaWiseAccumulationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyAreaWiseAccumulationDTO,
     * or with status 400 (Bad Request) if the supplyAreaWiseAccumulationDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyAreaWiseAccumulationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-area-wise-accumulations")
    public ResponseEntity<SupplyAreaWiseAccumulationDTO> updateSupplyAreaWiseAccumulation(@Valid @RequestBody SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTO);
        if (supplyAreaWiseAccumulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationService.save(supplyAreaWiseAccumulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyAreaWiseAccumulationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-area-wise-accumulations : get all the supplyAreaWiseAccumulations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyAreaWiseAccumulations in body
     */
    @GetMapping("/supply-area-wise-accumulations")
    public ResponseEntity<List<SupplyAreaWiseAccumulationDTO>> getAllSupplyAreaWiseAccumulations(SupplyAreaWiseAccumulationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyAreaWiseAccumulations by criteria: {}", criteria);
        Page<SupplyAreaWiseAccumulationDTO> page = supplyAreaWiseAccumulationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-area-wise-accumulations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-area-wise-accumulations/count : count all the supplyAreaWiseAccumulations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-area-wise-accumulations/count")
    public ResponseEntity<Long> countSupplyAreaWiseAccumulations(SupplyAreaWiseAccumulationCriteria criteria) {
        log.debug("REST request to count SupplyAreaWiseAccumulations by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyAreaWiseAccumulationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-area-wise-accumulations/:id : get the "id" supplyAreaWiseAccumulation.
     *
     * @param id the id of the supplyAreaWiseAccumulationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyAreaWiseAccumulationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-area-wise-accumulations/{id}")
    public ResponseEntity<SupplyAreaWiseAccumulationDTO> getSupplyAreaWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to get SupplyAreaWiseAccumulation : {}", id);
        Optional<SupplyAreaWiseAccumulationDTO> supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyAreaWiseAccumulationDTO);
    }

    /**
     * DELETE  /supply-area-wise-accumulations/:id : delete the "id" supplyAreaWiseAccumulation.
     *
     * @param id the id of the supplyAreaWiseAccumulationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-area-wise-accumulations/{id}")
    public ResponseEntity<Void> deleteSupplyAreaWiseAccumulation(@PathVariable Long id) {
        log.debug("REST request to delete SupplyAreaWiseAccumulation : {}", id);
        supplyAreaWiseAccumulationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-area-wise-accumulations?query=:query : search for the supplyAreaWiseAccumulation corresponding
     * to the query.
     *
     * @param query the query of the supplyAreaWiseAccumulation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-area-wise-accumulations")
    public ResponseEntity<List<SupplyAreaWiseAccumulationDTO>> searchSupplyAreaWiseAccumulations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyAreaWiseAccumulations for query {}", query);
        Page<SupplyAreaWiseAccumulationDTO> page = supplyAreaWiseAccumulationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-area-wise-accumulations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
