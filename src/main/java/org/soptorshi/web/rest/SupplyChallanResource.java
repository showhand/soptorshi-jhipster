package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyChallanQueryService;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.dto.SupplyChallanCriteria;
import org.soptorshi.service.dto.SupplyChallanDTO;
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
 * REST controller for managing SupplyChallan.
 */
@RestController
@RequestMapping("/api")
public class SupplyChallanResource {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanResource.class);

    private static final String ENTITY_NAME = "supplyChallan";

    private final SupplyChallanService supplyChallanService;

    private final SupplyChallanQueryService supplyChallanQueryService;

    public SupplyChallanResource(SupplyChallanService supplyChallanService, SupplyChallanQueryService supplyChallanQueryService) {
        this.supplyChallanService = supplyChallanService;
        this.supplyChallanQueryService = supplyChallanQueryService;
    }

    /**
     * POST  /supply-challans : Create a new supplyChallan.
     *
     * @param supplyChallanDTO the supplyChallanDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyChallanDTO, or with status 400 (Bad Request) if the supplyChallan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-challans")
    public ResponseEntity<SupplyChallanDTO> createSupplyChallan(@Valid @RequestBody SupplyChallanDTO supplyChallanDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyChallan : {}", supplyChallanDTO);
        if (supplyChallanDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyChallan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyChallanDTO result = supplyChallanService.save(supplyChallanDTO);
        return ResponseEntity.created(new URI("/api/supply-challans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-challans : Updates an existing supplyChallan.
     *
     * @param supplyChallanDTO the supplyChallanDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyChallanDTO,
     * or with status 400 (Bad Request) if the supplyChallanDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyChallanDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-challans")
    public ResponseEntity<SupplyChallanDTO> updateSupplyChallan(@Valid @RequestBody SupplyChallanDTO supplyChallanDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyChallan : {}", supplyChallanDTO);
        if (supplyChallanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyChallanDTO result = supplyChallanService.save(supplyChallanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyChallanDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-challans : get all the supplyChallans.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyChallans in body
     */
    @GetMapping("/supply-challans")
    public ResponseEntity<List<SupplyChallanDTO>> getAllSupplyChallans(SupplyChallanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyChallans by criteria: {}", criteria);
        Page<SupplyChallanDTO> page = supplyChallanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-challans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-challans/count : count all the supplyChallans.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-challans/count")
    public ResponseEntity<Long> countSupplyChallans(SupplyChallanCriteria criteria) {
        log.debug("REST request to count SupplyChallans by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyChallanQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-challans/:id : get the "id" supplyChallan.
     *
     * @param id the id of the supplyChallanDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyChallanDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-challans/{id}")
    public ResponseEntity<SupplyChallanDTO> getSupplyChallan(@PathVariable Long id) {
        log.debug("REST request to get SupplyChallan : {}", id);
        Optional<SupplyChallanDTO> supplyChallanDTO = supplyChallanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyChallanDTO);
    }

    /**
     * DELETE  /supply-challans/:id : delete the "id" supplyChallan.
     *
     * @param id the id of the supplyChallanDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-challans/{id}")
    public ResponseEntity<Void> deleteSupplyChallan(@PathVariable Long id) {
        log.debug("REST request to delete SupplyChallan : {}", id);
        supplyChallanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-challans?query=:query : search for the supplyChallan corresponding
     * to the query.
     *
     * @param query the query of the supplyChallan search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-challans")
    public ResponseEntity<List<SupplyChallanDTO>> searchSupplyChallans(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyChallans for query {}", query);
        Page<SupplyChallanDTO> page = supplyChallanService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-challans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
