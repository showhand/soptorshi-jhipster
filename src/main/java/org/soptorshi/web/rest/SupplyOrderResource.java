package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SupplyOrderQueryService;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.SupplyOrderCriteria;
import org.soptorshi.service.dto.SupplyOrderDTO;
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
 * REST controller for managing SupplyOrder.
 */
@RestController
@RequestMapping("/api")
public class SupplyOrderResource {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderResource.class);

    private static final String ENTITY_NAME = "supplyOrder";

    private final SupplyOrderService supplyOrderService;

    private final SupplyOrderQueryService supplyOrderQueryService;

    public SupplyOrderResource(SupplyOrderService supplyOrderService, SupplyOrderQueryService supplyOrderQueryService) {
        this.supplyOrderService = supplyOrderService;
        this.supplyOrderQueryService = supplyOrderQueryService;
    }

    /**
     * POST  /supply-orders : Create a new supplyOrder.
     *
     * @param supplyOrderDTO the supplyOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supplyOrderDTO, or with status 400 (Bad Request) if the supplyOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supply-orders")
    public ResponseEntity<SupplyOrderDTO> createSupplyOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO) throws URISyntaxException {
        log.debug("REST request to save SupplyOrder : {}", supplyOrderDTO);
        if (supplyOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyOrderDTO result = supplyOrderService.save(supplyOrderDTO);
        return ResponseEntity.created(new URI("/api/supply-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supply-orders : Updates an existing supplyOrder.
     *
     * @param supplyOrderDTO the supplyOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supplyOrderDTO,
     * or with status 400 (Bad Request) if the supplyOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the supplyOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supply-orders")
    public ResponseEntity<SupplyOrderDTO> updateSupplyOrder(@Valid @RequestBody SupplyOrderDTO supplyOrderDTO) throws URISyntaxException {
        log.debug("REST request to update SupplyOrder : {}", supplyOrderDTO);
        if (supplyOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplyOrderDTO result = supplyOrderService.save(supplyOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supplyOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supply-orders : get all the supplyOrders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of supplyOrders in body
     */
    @GetMapping("/supply-orders")
    public ResponseEntity<List<SupplyOrderDTO>> getAllSupplyOrders(SupplyOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplyOrders by criteria: {}", criteria);
        Page<SupplyOrderDTO> page = supplyOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supply-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /supply-orders/count : count all the supplyOrders.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/supply-orders/count")
    public ResponseEntity<Long> countSupplyOrders(SupplyOrderCriteria criteria) {
        log.debug("REST request to count SupplyOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplyOrderQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /supply-orders/:id : get the "id" supplyOrder.
     *
     * @param id the id of the supplyOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supplyOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/supply-orders/{id}")
    public ResponseEntity<SupplyOrderDTO> getSupplyOrder(@PathVariable Long id) {
        log.debug("REST request to get SupplyOrder : {}", id);
        Optional<SupplyOrderDTO> supplyOrderDTO = supplyOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyOrderDTO);
    }

    /**
     * DELETE  /supply-orders/:id : delete the "id" supplyOrder.
     *
     * @param id the id of the supplyOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supply-orders/{id}")
    public ResponseEntity<Void> deleteSupplyOrder(@PathVariable Long id) {
        log.debug("REST request to delete SupplyOrder : {}", id);
        supplyOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/supply-orders?query=:query : search for the supplyOrder corresponding
     * to the query.
     *
     * @param query the query of the supplyOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/supply-orders")
    public ResponseEntity<List<SupplyOrderDTO>> searchSupplyOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SupplyOrders for query {}", query);
        Page<SupplyOrderDTO> page = supplyOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/supply-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
