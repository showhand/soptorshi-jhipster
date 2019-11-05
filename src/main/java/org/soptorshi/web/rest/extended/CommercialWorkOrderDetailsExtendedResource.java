package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialWorkOrderDetailsQueryService;
import org.soptorshi.service.CommercialWorkOrderDetailsService;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsCriteria;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsDTO;
import org.soptorshi.web.rest.CommercialWorkOrderDetailsResource;
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
 * REST controller for managing CommercialWorkOrderDetails.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialWorkOrderDetailsExtendedResource extends CommercialWorkOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderDetailsExtendedResource.class);

    private static final String ENTITY_NAME = "commercialWorkOrderDetails";

    private final CommercialWorkOrderDetailsService commercialWorkOrderDetailsService;

    private final CommercialWorkOrderDetailsQueryService commercialWorkOrderDetailsQueryService;

    public CommercialWorkOrderDetailsExtendedResource(CommercialWorkOrderDetailsService commercialWorkOrderDetailsService, CommercialWorkOrderDetailsQueryService commercialWorkOrderDetailsQueryService) {
        super(commercialWorkOrderDetailsService, commercialWorkOrderDetailsQueryService);
        this.commercialWorkOrderDetailsService = commercialWorkOrderDetailsService;
        this.commercialWorkOrderDetailsQueryService = commercialWorkOrderDetailsQueryService;
    }

    /**
     * POST  /commercial-work-order-details : Create a new commercialWorkOrderDetails.
     *
     * @param commercialWorkOrderDetailsDTO the commercialWorkOrderDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialWorkOrderDetailsDTO, or with status 400 (Bad Request) if the commercialWorkOrderDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-work-order-details")
    public ResponseEntity<CommercialWorkOrderDetailsDTO> createCommercialWorkOrderDetails(@Valid @RequestBody CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialWorkOrderDetails : {}", commercialWorkOrderDetailsDTO);
        if (commercialWorkOrderDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialWorkOrderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialWorkOrderDetailsDTO result = commercialWorkOrderDetailsService.save(commercialWorkOrderDetailsDTO);
        return ResponseEntity.created(new URI("/api/commercial-work-order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-work-order-details : Updates an existing commercialWorkOrderDetails.
     *
     * @param commercialWorkOrderDetailsDTO the commercialWorkOrderDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialWorkOrderDetailsDTO,
     * or with status 400 (Bad Request) if the commercialWorkOrderDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialWorkOrderDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-work-order-details")
    public ResponseEntity<CommercialWorkOrderDetailsDTO> updateCommercialWorkOrderDetails(@Valid @RequestBody CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialWorkOrderDetails : {}", commercialWorkOrderDetailsDTO);
        if (commercialWorkOrderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialWorkOrderDetailsDTO result = commercialWorkOrderDetailsService.save(commercialWorkOrderDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialWorkOrderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-work-order-details : get all the commercialWorkOrderDetails.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialWorkOrderDetails in body
     */
    @GetMapping("/commercial-work-order-details")
    public ResponseEntity<List<CommercialWorkOrderDetailsDTO>> getAllCommercialWorkOrderDetails(CommercialWorkOrderDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialWorkOrderDetails by criteria: {}", criteria);
        Page<CommercialWorkOrderDetailsDTO> page = commercialWorkOrderDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-work-order-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-work-order-details/count : count all the commercialWorkOrderDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-work-order-details/count")
    public ResponseEntity<Long> countCommercialWorkOrderDetails(CommercialWorkOrderDetailsCriteria criteria) {
        log.debug("REST request to count CommercialWorkOrderDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialWorkOrderDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-work-order-details/:id : get the "id" commercialWorkOrderDetails.
     *
     * @param id the id of the commercialWorkOrderDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialWorkOrderDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-work-order-details/{id}")
    public ResponseEntity<CommercialWorkOrderDetailsDTO> getCommercialWorkOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get CommercialWorkOrderDetails : {}", id);
        Optional<CommercialWorkOrderDetailsDTO> commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialWorkOrderDetailsDTO);
    }

    /**
     * DELETE  /commercial-work-order-details/:id : delete the "id" commercialWorkOrderDetails.
     *
     * @param id the id of the commercialWorkOrderDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-work-order-details/{id}")
    public ResponseEntity<Void> deleteCommercialWorkOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete CommercialWorkOrderDetails : {}", id);
        commercialWorkOrderDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-work-order-details?query=:query : search for the commercialWorkOrderDetails corresponding
     * to the query.
     *
     * @param query the query of the commercialWorkOrderDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-work-order-details")
    public ResponseEntity<List<CommercialWorkOrderDetailsDTO>> searchCommercialWorkOrderDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialWorkOrderDetails for query {}", query);
        Page<CommercialWorkOrderDetailsDTO> page = commercialWorkOrderDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-work-order-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
