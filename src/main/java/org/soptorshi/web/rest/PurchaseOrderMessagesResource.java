package org.soptorshi.web.rest;
import org.soptorshi.service.PurchaseOrderMessagesService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PurchaseOrderMessagesDTO;
import org.soptorshi.service.dto.PurchaseOrderMessagesCriteria;
import org.soptorshi.service.PurchaseOrderMessagesQueryService;
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
 * REST controller for managing PurchaseOrderMessages.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrderMessagesResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderMessagesResource.class);

    private static final String ENTITY_NAME = "purchaseOrderMessages";

    private final PurchaseOrderMessagesService purchaseOrderMessagesService;

    private final PurchaseOrderMessagesQueryService purchaseOrderMessagesQueryService;

    public PurchaseOrderMessagesResource(PurchaseOrderMessagesService purchaseOrderMessagesService, PurchaseOrderMessagesQueryService purchaseOrderMessagesQueryService) {
        this.purchaseOrderMessagesService = purchaseOrderMessagesService;
        this.purchaseOrderMessagesQueryService = purchaseOrderMessagesQueryService;
    }

    /**
     * POST  /purchase-order-messages : Create a new purchaseOrderMessages.
     *
     * @param purchaseOrderMessagesDTO the purchaseOrderMessagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseOrderMessagesDTO, or with status 400 (Bad Request) if the purchaseOrderMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-order-messages")
    public ResponseEntity<PurchaseOrderMessagesDTO> createPurchaseOrderMessages(@RequestBody PurchaseOrderMessagesDTO purchaseOrderMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrderMessages : {}", purchaseOrderMessagesDTO);
        if (purchaseOrderMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrderMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrderMessagesDTO result = purchaseOrderMessagesService.save(purchaseOrderMessagesDTO);
        return ResponseEntity.created(new URI("/api/purchase-order-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-order-messages : Updates an existing purchaseOrderMessages.
     *
     * @param purchaseOrderMessagesDTO the purchaseOrderMessagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseOrderMessagesDTO,
     * or with status 400 (Bad Request) if the purchaseOrderMessagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseOrderMessagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-order-messages")
    public ResponseEntity<PurchaseOrderMessagesDTO> updatePurchaseOrderMessages(@RequestBody PurchaseOrderMessagesDTO purchaseOrderMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrderMessages : {}", purchaseOrderMessagesDTO);
        if (purchaseOrderMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrderMessagesDTO result = purchaseOrderMessagesService.save(purchaseOrderMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseOrderMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-order-messages : get all the purchaseOrderMessages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseOrderMessages in body
     */
    @GetMapping("/purchase-order-messages")
    public ResponseEntity<List<PurchaseOrderMessagesDTO>> getAllPurchaseOrderMessages(PurchaseOrderMessagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PurchaseOrderMessages by criteria: {}", criteria);
        Page<PurchaseOrderMessagesDTO> page = purchaseOrderMessagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-order-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /purchase-order-messages/count : count all the purchaseOrderMessages.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/purchase-order-messages/count")
    public ResponseEntity<Long> countPurchaseOrderMessages(PurchaseOrderMessagesCriteria criteria) {
        log.debug("REST request to count PurchaseOrderMessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseOrderMessagesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /purchase-order-messages/:id : get the "id" purchaseOrderMessages.
     *
     * @param id the id of the purchaseOrderMessagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseOrderMessagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-order-messages/{id}")
    public ResponseEntity<PurchaseOrderMessagesDTO> getPurchaseOrderMessages(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrderMessages : {}", id);
        Optional<PurchaseOrderMessagesDTO> purchaseOrderMessagesDTO = purchaseOrderMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrderMessagesDTO);
    }

    /**
     * DELETE  /purchase-order-messages/:id : delete the "id" purchaseOrderMessages.
     *
     * @param id the id of the purchaseOrderMessagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-order-messages/{id}")
    public ResponseEntity<Void> deletePurchaseOrderMessages(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrderMessages : {}", id);
        purchaseOrderMessagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchase-order-messages?query=:query : search for the purchaseOrderMessages corresponding
     * to the query.
     *
     * @param query the query of the purchaseOrderMessages search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/purchase-order-messages")
    public ResponseEntity<List<PurchaseOrderMessagesDTO>> searchPurchaseOrderMessages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PurchaseOrderMessages for query {}", query);
        Page<PurchaseOrderMessagesDTO> page = purchaseOrderMessagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/purchase-order-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
