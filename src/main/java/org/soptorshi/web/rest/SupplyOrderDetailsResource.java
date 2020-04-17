package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyOrderDetailsQueryService;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.dto.SupplyOrderDetailsCriteria;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
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
 * REST controller for managing SupplyOrderDetails.
 */
@RestController
@RequestMapping("/api")
public class SupplyOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsResource.class);

    private static final String ENTITY_NAME = "supplyOrderDetails";

    private final SupplyOrderDetailsService supplyOrderDetailsService;

    private final SupplyOrderDetailsQueryService supplyOrderDetailsQueryService;

    public SupplyOrderDetailsResource(SupplyOrderDetailsService supplyOrderDetailsService, SupplyOrderDetailsQueryService supplyOrderDetailsQueryService) {
        this.supplyOrderDetailsService = supplyOrderDetailsService;
        this.supplyOrderDetailsQueryService = supplyOrderDetailsQueryService;
    }

    /**
     * POST  /supply-order-details : Create a new supplyOrderDetails.
     *
     * @param supplyOrderDetailsDTO the supplyOrderDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyOrderDetailsDTO, or with status 400 (Bad Request) if the supplyOrderDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-order-details")
    public ResponseEntity<SupplyOrderDetailsDTO> createSupplyOrderDetails(@Valid @RequestBody SupplyOrderDetailsDTO supplyOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyOrderDetails : {}", supplyOrderDetailsDTO);
        if (supplyOrderDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyOrderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyOrderDetailsDTO result = supplyOrderDetailsService.save(supplyOrderDetailsDTO);
        return ResponseEntity.created(new URI("/api/supply-order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-order-details : Updates an existing supplyOrderDetails.
     *
     * @param supplyOrderDetailsDTO the supplyOrderDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyOrderDetailsDTO,
     * or with status 400 (Bad Request) if the supplyOrderDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyOrderDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-order-details")
    public ResponseEntity<SupplyOrderDetailsDTO> updateSupplyOrderDetails(@Valid @RequestBody SupplyOrderDetailsDTO supplyOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyOrderDetails : {}", supplyOrderDetailsDTO);
        if (supplyOrderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyOrderDetailsDTO result = supplyOrderDetailsService.save(supplyOrderDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyOrderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-order-details : get all the supplyOrderDetails.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyOrderDetails in body
     */
    @GetMapping("/supply-order-details")
    public ResponseEntity<List<SupplyOrderDetailsDTO>> getAllSupplyOrderDetails(SupplyOrderDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyOrderDetails by criteria: {}", criteria);
        Page<SupplyOrderDetailsDTO> page = supplyOrderDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-order-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-order-details/count : count all the supplyOrderDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-order-details/count")
    public ResponseEntity<Long> countSupplyOrderDetails(SupplyOrderDetailsCriteria criteria) {
        log.debug("REST request to count SupplyOrderDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyOrderDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-order-details/:id : get the "id" supplyOrderDetails.
     *
     * @param id the id of the supplyOrderDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyOrderDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-order-details/{id}")
    public ResponseEntity<SupplyOrderDetailsDTO> getSupplyOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get SupplyOrderDetails : {}", id);
        Optional<SupplyOrderDetailsDTO> supplyOrderDetailsDTO = supplyOrderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyOrderDetailsDTO);
    }

    /**
     * DELETE  /supply-order-details/:id : delete the "id" supplyOrderDetails.
     *
     * @param id the id of the supplyOrderDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-order-details/{id}")
    public ResponseEntity<Void> deleteSupplyOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete SupplyOrderDetails : {}", id);
        supplyOrderDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-order-details?query=:query : search for the supplyOrderDetails corresponding
     * to the query.
     *
     * @param query the query of the supplyOrderDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-order-details")
    public ResponseEntity<List<SupplyOrderDetailsDTO>> searchSupplyOrderDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyOrderDetails for query {}", query);
        Page<SupplyOrderDetailsDTO> page = supplyOrderDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-order-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
