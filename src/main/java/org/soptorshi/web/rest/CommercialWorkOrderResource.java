package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialWorkOrderQueryService;
import org.soptorshi.service.CommercialWorkOrderService;
import org.soptorshi.service.dto.CommercialWorkOrderCriteria;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;
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
 * REST controller for managing CommercialWorkOrder.
 */
@RestController
@RequestMapping("/api")
public class CommercialWorkOrderResource {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderResource.class);

    private static final String ENTITY_NAME = "commercialWorkOrder";

    private final CommercialWorkOrderService commercialWorkOrderService;

    private final CommercialWorkOrderQueryService commercialWorkOrderQueryService;

    public CommercialWorkOrderResource(CommercialWorkOrderService commercialWorkOrderService, CommercialWorkOrderQueryService commercialWorkOrderQueryService) {
        this.commercialWorkOrderService = commercialWorkOrderService;
        this.commercialWorkOrderQueryService = commercialWorkOrderQueryService;
    }

    /**
     * POST  /commercial-work-orders : Create a new commercialWorkOrder.
     *
     * @param commercialWorkOrderDTO the commercialWorkOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialWorkOrderDTO, or with status 400 (Bad Request) if the commercialWorkOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-work-orders")
    public ResponseEntity<CommercialWorkOrderDTO> createCommercialWorkOrder(@Valid @RequestBody CommercialWorkOrderDTO commercialWorkOrderDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialWorkOrder : {}", commercialWorkOrderDTO);
        if (commercialWorkOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialWorkOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialWorkOrderDTO result = commercialWorkOrderService.save(commercialWorkOrderDTO);
        return ResponseEntity.created(new URI("/api/commercial-work-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-work-orders : Updates an existing commercialWorkOrder.
     *
     * @param commercialWorkOrderDTO the commercialWorkOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialWorkOrderDTO,
     * or with status 400 (Bad Request) if the commercialWorkOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialWorkOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-work-orders")
    public ResponseEntity<CommercialWorkOrderDTO> updateCommercialWorkOrder(@Valid @RequestBody CommercialWorkOrderDTO commercialWorkOrderDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialWorkOrder : {}", commercialWorkOrderDTO);
        if (commercialWorkOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialWorkOrderDTO result = commercialWorkOrderService.save(commercialWorkOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialWorkOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-work-orders : get all the commercialWorkOrders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialWorkOrders in body
     */
    @GetMapping("/commercial-work-orders")
    public ResponseEntity<List<CommercialWorkOrderDTO>> getAllCommercialWorkOrders(CommercialWorkOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialWorkOrders by criteria: {}", criteria);
        Page<CommercialWorkOrderDTO> page = commercialWorkOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-work-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-work-orders/count : count all the commercialWorkOrders.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-work-orders/count")
    public ResponseEntity<Long> countCommercialWorkOrders(CommercialWorkOrderCriteria criteria) {
        log.debug("REST request to count CommercialWorkOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialWorkOrderQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-work-orders/:id : get the "id" commercialWorkOrder.
     *
     * @param id the id of the commercialWorkOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialWorkOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-work-orders/{id}")
    public ResponseEntity<CommercialWorkOrderDTO> getCommercialWorkOrder(@PathVariable Long id) {
        log.debug("REST request to get CommercialWorkOrder : {}", id);
        Optional<CommercialWorkOrderDTO> commercialWorkOrderDTO = commercialWorkOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialWorkOrderDTO);
    }

    /**
     * DELETE  /commercial-work-orders/:id : delete the "id" commercialWorkOrder.
     *
     * @param id the id of the commercialWorkOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-work-orders/{id}")
    public ResponseEntity<Void> deleteCommercialWorkOrder(@PathVariable Long id) {
        log.debug("REST request to delete CommercialWorkOrder : {}", id);
        commercialWorkOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-work-orders?query=:query : search for the commercialWorkOrder corresponding
     * to the query.
     *
     * @param query the query of the commercialWorkOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-work-orders")
    public ResponseEntity<List<CommercialWorkOrderDTO>> searchCommercialWorkOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialWorkOrders for query {}", query);
        Page<CommercialWorkOrderDTO> page = commercialWorkOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-work-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
