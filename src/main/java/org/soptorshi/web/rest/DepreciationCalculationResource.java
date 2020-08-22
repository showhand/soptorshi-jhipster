package org.soptorshi.web.rest;
import org.soptorshi.service.DepreciationCalculationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.service.dto.DepreciationCalculationCriteria;
import org.soptorshi.service.DepreciationCalculationQueryService;
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
 * REST controller for managing DepreciationCalculation.
 */
@RestController
@RequestMapping("/api")
public class DepreciationCalculationResource {

    private final Logger log = LoggerFactory.getLogger(DepreciationCalculationResource.class);

    private static final String ENTITY_NAME = "depreciationCalculation";

    private final DepreciationCalculationService depreciationCalculationService;

    private final DepreciationCalculationQueryService depreciationCalculationQueryService;

    public DepreciationCalculationResource(DepreciationCalculationService depreciationCalculationService, DepreciationCalculationQueryService depreciationCalculationQueryService) {
        this.depreciationCalculationService = depreciationCalculationService;
        this.depreciationCalculationQueryService = depreciationCalculationQueryService;
    }

    /**
     * POST  /depreciation-calculations : Create a new depreciationCalculation.
     *
     * @param depreciationCalculationDTO the depreciationCalculationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depreciationCalculationDTO, or with status 400 (Bad Request) if the depreciationCalculation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depreciation-calculations")
    public ResponseEntity<DepreciationCalculationDTO> createDepreciationCalculation(@RequestBody DepreciationCalculationDTO depreciationCalculationDTO) throws URISyntaxException {
        log.debug("REST request to save DepreciationCalculation : {}", depreciationCalculationDTO);
        if (depreciationCalculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new depreciationCalculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepreciationCalculationDTO result = depreciationCalculationService.save(depreciationCalculationDTO);
        return ResponseEntity.created(new URI("/api/depreciation-calculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depreciation-calculations : Updates an existing depreciationCalculation.
     *
     * @param depreciationCalculationDTO the depreciationCalculationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depreciationCalculationDTO,
     * or with status 400 (Bad Request) if the depreciationCalculationDTO is not valid,
     * or with status 500 (Internal Server Error) if the depreciationCalculationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depreciation-calculations")
    public ResponseEntity<DepreciationCalculationDTO> updateDepreciationCalculation(@RequestBody DepreciationCalculationDTO depreciationCalculationDTO) throws URISyntaxException {
        log.debug("REST request to update DepreciationCalculation : {}", depreciationCalculationDTO);
        if (depreciationCalculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepreciationCalculationDTO result = depreciationCalculationService.save(depreciationCalculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depreciationCalculationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depreciation-calculations : get all the depreciationCalculations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of depreciationCalculations in body
     */
    @GetMapping("/depreciation-calculations")
    public ResponseEntity<List<DepreciationCalculationDTO>> getAllDepreciationCalculations(DepreciationCalculationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DepreciationCalculations by criteria: {}", criteria);
        Page<DepreciationCalculationDTO> page = depreciationCalculationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/depreciation-calculations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /depreciation-calculations/count : count all the depreciationCalculations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/depreciation-calculations/count")
    public ResponseEntity<Long> countDepreciationCalculations(DepreciationCalculationCriteria criteria) {
        log.debug("REST request to count DepreciationCalculations by criteria: {}", criteria);
        return ResponseEntity.ok().body(depreciationCalculationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /depreciation-calculations/:id : get the "id" depreciationCalculation.
     *
     * @param id the id of the depreciationCalculationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depreciationCalculationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/depreciation-calculations/{id}")
    public ResponseEntity<DepreciationCalculationDTO> getDepreciationCalculation(@PathVariable Long id) {
        log.debug("REST request to get DepreciationCalculation : {}", id);
        Optional<DepreciationCalculationDTO> depreciationCalculationDTO = depreciationCalculationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depreciationCalculationDTO);
    }

    /**
     * DELETE  /depreciation-calculations/:id : delete the "id" depreciationCalculation.
     *
     * @param id the id of the depreciationCalculationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depreciation-calculations/{id}")
    public ResponseEntity<Void> deleteDepreciationCalculation(@PathVariable Long id) {
        log.debug("REST request to delete DepreciationCalculation : {}", id);
        depreciationCalculationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/depreciation-calculations?query=:query : search for the depreciationCalculation corresponding
     * to the query.
     *
     * @param query the query of the depreciationCalculation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/depreciation-calculations")
    public ResponseEntity<List<DepreciationCalculationDTO>> searchDepreciationCalculations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DepreciationCalculations for query {}", query);
        Page<DepreciationCalculationDTO> page = depreciationCalculationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/depreciation-calculations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
