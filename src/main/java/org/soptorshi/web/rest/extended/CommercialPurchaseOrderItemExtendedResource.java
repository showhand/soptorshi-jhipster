package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPurchaseOrderItemQueryService;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemCriteria;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemDTO;
import org.soptorshi.service.extended.CommercialPurchaseOrderItemExtendedService;
import org.soptorshi.web.rest.CommercialPurchaseOrderItemResource;
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
 * REST controller for managing CommercialPurchaseOrderItem.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPurchaseOrderItemExtendedResource extends CommercialPurchaseOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderItemExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPurchaseOrderItem";

    private final CommercialPurchaseOrderItemExtendedService commercialPurchaseOrderItemService;

    private final CommercialPurchaseOrderItemQueryService commercialPurchaseOrderItemQueryService;

    public CommercialPurchaseOrderItemExtendedResource(CommercialPurchaseOrderItemExtendedService commercialPurchaseOrderItemService, CommercialPurchaseOrderItemQueryService commercialPurchaseOrderItemQueryService) {
        super(commercialPurchaseOrderItemService, commercialPurchaseOrderItemQueryService);
        this.commercialPurchaseOrderItemService = commercialPurchaseOrderItemService;
        this.commercialPurchaseOrderItemQueryService = commercialPurchaseOrderItemQueryService;
    }

    /**
     * POST  /commercial-purchase-order-items : Create a new commercialPurchaseOrderItem.
     *
     * @param commercialPurchaseOrderItemDTO the commercialPurchaseOrderItemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPurchaseOrderItemDTO, or with status 400 (Bad Request) if the commercialPurchaseOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-purchase-order-items")
    public ResponseEntity<CommercialPurchaseOrderItemDTO> createCommercialPurchaseOrderItem(@Valid @RequestBody CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPurchaseOrderItem : {}", commercialPurchaseOrderItemDTO);
        if (commercialPurchaseOrderItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPurchaseOrderItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPurchaseOrderItemDTO result = commercialPurchaseOrderItemService.save(commercialPurchaseOrderItemDTO);
        return ResponseEntity.created(new URI("/api/commercial-purchase-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-purchase-order-items : Updates an existing commercialPurchaseOrderItem.
     *
     * @param commercialPurchaseOrderItemDTO the commercialPurchaseOrderItemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPurchaseOrderItemDTO,
     * or with status 400 (Bad Request) if the commercialPurchaseOrderItemDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPurchaseOrderItemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-purchase-order-items")
    public ResponseEntity<CommercialPurchaseOrderItemDTO> updateCommercialPurchaseOrderItem(@Valid @RequestBody CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPurchaseOrderItem : {}", commercialPurchaseOrderItemDTO);
        if (commercialPurchaseOrderItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPurchaseOrderItemDTO result = commercialPurchaseOrderItemService.save(commercialPurchaseOrderItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPurchaseOrderItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-purchase-order-items : get all the commercialPurchaseOrderItems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPurchaseOrderItems in body
     */
    @GetMapping("/commercial-purchase-order-items")
    public ResponseEntity<List<CommercialPurchaseOrderItemDTO>> getAllCommercialPurchaseOrderItems(CommercialPurchaseOrderItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPurchaseOrderItems by criteria: {}", criteria);
        Page<CommercialPurchaseOrderItemDTO> page = commercialPurchaseOrderItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-purchase-order-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-purchase-order-items/count : count all the commercialPurchaseOrderItems.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-purchase-order-items/count")
    public ResponseEntity<Long> countCommercialPurchaseOrderItems(CommercialPurchaseOrderItemCriteria criteria) {
        log.debug("REST request to count CommercialPurchaseOrderItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPurchaseOrderItemQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-purchase-order-items/:id : get the "id" commercialPurchaseOrderItem.
     *
     * @param id the id of the commercialPurchaseOrderItemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPurchaseOrderItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-purchase-order-items/{id}")
    public ResponseEntity<CommercialPurchaseOrderItemDTO> getCommercialPurchaseOrderItem(@PathVariable Long id) {
        log.debug("REST request to get CommercialPurchaseOrderItem : {}", id);
        Optional<CommercialPurchaseOrderItemDTO> commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPurchaseOrderItemDTO);
    }

    /**
     * DELETE  /commercial-purchase-order-items/:id : delete the "id" commercialPurchaseOrderItem.
     *
     * @param id the id of the commercialPurchaseOrderItemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-purchase-order-items/{id}")
    public ResponseEntity<Void> deleteCommercialPurchaseOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPurchaseOrderItem : {}", id);
        commercialPurchaseOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-purchase-order-items?query=:query : search for the commercialPurchaseOrderItem corresponding
     * to the query.
     *
     * @param query the query of the commercialPurchaseOrderItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-purchase-order-items")
    public ResponseEntity<List<CommercialPurchaseOrderItemDTO>> searchCommercialPurchaseOrderItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPurchaseOrderItems for query {}", query);
        Page<CommercialPurchaseOrderItemDTO> page = commercialPurchaseOrderItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-purchase-order-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
