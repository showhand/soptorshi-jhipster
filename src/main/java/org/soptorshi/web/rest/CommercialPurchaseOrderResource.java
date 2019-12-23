package org.soptorshi.web.rest;
import org.soptorshi.service.CommercialPurchaseOrderService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;
import org.soptorshi.service.dto.CommercialPurchaseOrderCriteria;
import org.soptorshi.service.CommercialPurchaseOrderQueryService;
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
 * REST controller for managing CommercialPurchaseOrder.
 */
@RestController
@RequestMapping("/api")
public class CommercialPurchaseOrderResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderResource.class);

    private static final String ENTITY_NAME = "commercialPurchaseOrder";

    private final CommercialPurchaseOrderService commercialPurchaseOrderService;

    private final CommercialPurchaseOrderQueryService commercialPurchaseOrderQueryService;

    public CommercialPurchaseOrderResource(CommercialPurchaseOrderService commercialPurchaseOrderService, CommercialPurchaseOrderQueryService commercialPurchaseOrderQueryService) {
        this.commercialPurchaseOrderService = commercialPurchaseOrderService;
        this.commercialPurchaseOrderQueryService = commercialPurchaseOrderQueryService;
    }

    /**
     * POST  /commercial-purchase-orders : Create a new commercialPurchaseOrder.
     *
     * @param commercialPurchaseOrderDTO the commercialPurchaseOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPurchaseOrderDTO, or with status 400 (Bad Request) if the commercialPurchaseOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-purchase-orders")
    public ResponseEntity<CommercialPurchaseOrderDTO> createCommercialPurchaseOrder(@Valid @RequestBody CommercialPurchaseOrderDTO commercialPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPurchaseOrder : {}", commercialPurchaseOrderDTO);
        if (commercialPurchaseOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPurchaseOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPurchaseOrderDTO result = commercialPurchaseOrderService.save(commercialPurchaseOrderDTO);
        return ResponseEntity.created(new URI("/api/commercial-purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-purchase-orders : Updates an existing commercialPurchaseOrder.
     *
     * @param commercialPurchaseOrderDTO the commercialPurchaseOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPurchaseOrderDTO,
     * or with status 400 (Bad Request) if the commercialPurchaseOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPurchaseOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-purchase-orders")
    public ResponseEntity<CommercialPurchaseOrderDTO> updateCommercialPurchaseOrder(@Valid @RequestBody CommercialPurchaseOrderDTO commercialPurchaseOrderDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPurchaseOrder : {}", commercialPurchaseOrderDTO);
        if (commercialPurchaseOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPurchaseOrderDTO result = commercialPurchaseOrderService.save(commercialPurchaseOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPurchaseOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-purchase-orders : get all the commercialPurchaseOrders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPurchaseOrders in body
     */
    @GetMapping("/commercial-purchase-orders")
    public ResponseEntity<List<CommercialPurchaseOrderDTO>> getAllCommercialPurchaseOrders(CommercialPurchaseOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPurchaseOrders by criteria: {}", criteria);
        Page<CommercialPurchaseOrderDTO> page = commercialPurchaseOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-purchase-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-purchase-orders/count : count all the commercialPurchaseOrders.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-purchase-orders/count")
    public ResponseEntity<Long> countCommercialPurchaseOrders(CommercialPurchaseOrderCriteria criteria) {
        log.debug("REST request to count CommercialPurchaseOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPurchaseOrderQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-purchase-orders/:id : get the "id" commercialPurchaseOrder.
     *
     * @param id the id of the commercialPurchaseOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPurchaseOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-purchase-orders/{id}")
    public ResponseEntity<CommercialPurchaseOrderDTO> getCommercialPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to get CommercialPurchaseOrder : {}", id);
        Optional<CommercialPurchaseOrderDTO> commercialPurchaseOrderDTO = commercialPurchaseOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPurchaseOrderDTO);
    }

    /**
     * DELETE  /commercial-purchase-orders/:id : delete the "id" commercialPurchaseOrder.
     *
     * @param id the id of the commercialPurchaseOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-purchase-orders/{id}")
    public ResponseEntity<Void> deleteCommercialPurchaseOrder(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPurchaseOrder : {}", id);
        commercialPurchaseOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-purchase-orders?query=:query : search for the commercialPurchaseOrder corresponding
     * to the query.
     *
     * @param query the query of the commercialPurchaseOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-purchase-orders")
    public ResponseEntity<List<CommercialPurchaseOrderDTO>> searchCommercialPurchaseOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPurchaseOrders for query {}", query);
        Page<CommercialPurchaseOrderDTO> page = commercialPurchaseOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-purchase-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
