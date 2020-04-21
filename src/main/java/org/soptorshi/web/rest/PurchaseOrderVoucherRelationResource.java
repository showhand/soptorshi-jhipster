package org.soptorshi.web.rest;
import org.soptorshi.service.PurchaseOrderVoucherRelationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationCriteria;
import org.soptorshi.service.PurchaseOrderVoucherRelationQueryService;
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
 * REST controller for managing PurchaseOrderVoucherRelation.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderVoucherRelationResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderVoucherRelationResource.class);

    private static final String ENTITY_NAME = "purchaseOrderVoucherRelation";

    private final PurchaseOrderVoucherRelationService purchaseOrderVoucherRelationService;

    private final PurchaseOrderVoucherRelationQueryService purchaseOrderVoucherRelationQueryService;

    public PurchaseOrderVoucherRelationResource(PurchaseOrderVoucherRelationService purchaseOrderVoucherRelationService, PurchaseOrderVoucherRelationQueryService purchaseOrderVoucherRelationQueryService) {
        this.purchaseOrderVoucherRelationService = purchaseOrderVoucherRelationService;
        this.purchaseOrderVoucherRelationQueryService = purchaseOrderVoucherRelationQueryService;
    }

    /**
     * POST  /purchase-order-voucher-relations : Create a new purchaseOrderVoucherRelation.
     *
     * @param purchaseOrderVoucherRelationDTO the purchaseOrderVoucherRelationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderVoucherRelationDTO, or with status 400 (Bad Request) if the purchaseOrderVoucherRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-order-voucher-relations")
    public ResponseEntity<PurchaseOrderVoucherRelationDTO> createPurchaseOrderVoucherRelation(@RequestBody PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderVoucherRelation : {}", purchaseOrderVoucherRelationDTO);
        if (purchaseOrderVoucherRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrderVoucherRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrderVoucherRelationDTO result = purchaseOrderVoucherRelationService.save(purchaseOrderVoucherRelationDTO);
        return ResponseEntity.created(new URI("/api/purchase-order-voucher-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-order-voucher-relations : Updates an existing purchaseOrderVoucherRelation.
     *
     * @param purchaseOrderVoucherRelationDTO the purchaseOrderVoucherRelationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderVoucherRelationDTO,
     * or with status 400 (Bad Request) if the purchaseOrderVoucherRelationDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderVoucherRelationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-order-voucher-relations")
    public ResponseEntity<PurchaseOrderVoucherRelationDTO> updatePurchaseOrderVoucherRelation(@RequestBody PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderVoucherRelation : {}", purchaseOrderVoucherRelationDTO);
        if (purchaseOrderVoucherRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrderVoucherRelationDTO result = purchaseOrderVoucherRelationService.save(purchaseOrderVoucherRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrderVoucherRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-order-voucher-relations : get all the purchaseOrderVoucherRelations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrderVoucherRelations in body
     */
    @GetMapping("/purchase-order-voucher-relations")
    public ResponseEntity<List<PurchaseOrderVoucherRelationDTO>> getAllPurchaseOrderVoucherRelations(PurchaseOrderVoucherRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PurchaseOrderVoucherRelations by criteria: {}", criteria);
        Page<PurchaseOrderVoucherRelationDTO> page = purchaseOrderVoucherRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-order-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /purchase-order-voucher-relations/count : count all the purchaseOrderVoucherRelations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/purchase-order-voucher-relations/count")
    public ResponseEntity<Long> countPurchaseOrderVoucherRelations(PurchaseOrderVoucherRelationCriteria criteria) {
        log.debug("REST request to count PurchaseOrderVoucherRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseOrderVoucherRelationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /purchase-order-voucher-relations/:id : get the "id" purchaseOrderVoucherRelation.
     *
     * @param id the id of the purchaseOrderVoucherRelationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderVoucherRelationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-order-voucher-relations/{id}")
    public ResponseEntity<PurchaseOrderVoucherRelationDTO> getPurchaseOrderVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderVoucherRelation : {}", id);
        Optional<PurchaseOrderVoucherRelationDTO> purchaseOrderVoucherRelationDTO = purchaseOrderVoucherRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrderVoucherRelationDTO);
    }

    /**
     * DELETE  /purchase-order-voucher-relations/:id : delete the "id" purchaseOrderVoucherRelation.
     *
     * @param id the id of the purchaseOrderVoucherRelationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-order-voucher-relations/{id}")
    public ResponseEntity<Void> deletePurchaseOrderVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderVoucherRelation : {}", id);
        purchaseOrderVoucherRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchase-order-voucher-relations?query=:query : search for the purchaseOrderVoucherRelation corresponding
     * to the query.
     *
     * @param query the query of the purchaseOrderVoucherRelation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/purchase-order-voucher-relations")
    public ResponseEntity<List<PurchaseOrderVoucherRelationDTO>> searchPurchaseOrderVoucherRelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PurchaseOrderVoucherRelations for query {}", query);
        Page<PurchaseOrderVoucherRelationDTO> page = purchaseOrderVoucherRelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/purchase-order-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
