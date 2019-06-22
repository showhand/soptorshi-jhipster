package org.soptorshi.web.rest;
import org.soptorshi.service.PurchaseCommitteeService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.PurchaseCommitteeDTO;
import org.soptorshi.service.dto.PurchaseCommitteeCriteria;
import org.soptorshi.service.PurchaseCommitteeQueryService;
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
 * REST controller for managing PurchaseCommittee.
 */
@RestController
@RequestMapping("/api")
public class PurchaseCommitteeResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseCommitteeResource.class);

    private static final String ENTITY_NAME = "purchaseCommittee";

    private final PurchaseCommitteeService purchaseCommitteeService;

    private final PurchaseCommitteeQueryService purchaseCommitteeQueryService;

    public PurchaseCommitteeResource(PurchaseCommitteeService purchaseCommitteeService, PurchaseCommitteeQueryService purchaseCommitteeQueryService) {
        this.purchaseCommitteeService = purchaseCommitteeService;
        this.purchaseCommitteeQueryService = purchaseCommitteeQueryService;
    }

    /**
     * POST  /purchase-committees : Create a new purchaseCommittee.
     *
     * @param purchaseCommitteeDTO the purchaseCommitteeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseCommitteeDTO, or with status 400 (Bad Request) if the purchaseCommittee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-committees")
    public ResponseEntity<PurchaseCommitteeDTO> createPurchaseCommittee(@RequestBody PurchaseCommitteeDTO purchaseCommitteeDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseCommittee : {}", purchaseCommitteeDTO);
        if (purchaseCommitteeDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseCommittee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseCommitteeDTO result = purchaseCommitteeService.save(purchaseCommitteeDTO);
        return ResponseEntity.created(new URI("/api/purchase-committees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-committees : Updates an existing purchaseCommittee.
     *
     * @param purchaseCommitteeDTO the purchaseCommitteeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseCommitteeDTO,
     * or with status 400 (Bad Request) if the purchaseCommitteeDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseCommitteeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-committees")
    public ResponseEntity<PurchaseCommitteeDTO> updatePurchaseCommittee(@RequestBody PurchaseCommitteeDTO purchaseCommitteeDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseCommittee : {}", purchaseCommitteeDTO);
        if (purchaseCommitteeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseCommitteeDTO result = purchaseCommitteeService.save(purchaseCommitteeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseCommitteeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-committees : get all the purchaseCommittees.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseCommittees in body
     */
    @GetMapping("/purchase-committees")
    public ResponseEntity<List<PurchaseCommitteeDTO>> getAllPurchaseCommittees(PurchaseCommitteeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PurchaseCommittees by criteria: {}", criteria);
        Page<PurchaseCommitteeDTO> page = purchaseCommitteeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-committees");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /purchase-committees/count : count all the purchaseCommittees.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/purchase-committees/count")
    public ResponseEntity<Long> countPurchaseCommittees(PurchaseCommitteeCriteria criteria) {
        log.debug("REST request to count PurchaseCommittees by criteria: {}", criteria);
        return ResponseEntity.ok().body(purchaseCommitteeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /purchase-committees/:id : get the "id" purchaseCommittee.
     *
     * @param id the id of the purchaseCommitteeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseCommitteeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-committees/{id}")
    public ResponseEntity<PurchaseCommitteeDTO> getPurchaseCommittee(@PathVariable Long id) {
        log.debug("REST request to get PurchaseCommittee : {}", id);
        Optional<PurchaseCommitteeDTO> purchaseCommitteeDTO = purchaseCommitteeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseCommitteeDTO);
    }

    /**
     * DELETE  /purchase-committees/:id : delete the "id" purchaseCommittee.
     *
     * @param id the id of the purchaseCommitteeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-committees/{id}")
    public ResponseEntity<Void> deletePurchaseCommittee(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseCommittee : {}", id);
        purchaseCommitteeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchase-committees?query=:query : search for the purchaseCommittee corresponding
     * to the query.
     *
     * @param query the query of the purchaseCommittee search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/purchase-committees")
    public ResponseEntity<List<PurchaseCommitteeDTO>> searchPurchaseCommittees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PurchaseCommittees for query {}", query);
        Page<PurchaseCommitteeDTO> page = purchaseCommitteeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/purchase-committees");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
