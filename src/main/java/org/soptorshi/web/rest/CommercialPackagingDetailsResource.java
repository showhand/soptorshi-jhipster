package org.soptorshi.web.rest;
import org.soptorshi.service.CommercialPackagingDetailsService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.CommercialPackagingDetailsDTO;
import org.soptorshi.service.dto.CommercialPackagingDetailsCriteria;
import org.soptorshi.service.CommercialPackagingDetailsQueryService;
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
 * REST controller for managing CommercialPackagingDetails.
 */
@RestController
@RequestMapping("/api")
public class CommercialPackagingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingDetailsResource.class);

    private static final String ENTITY_NAME = "commercialPackagingDetails";

    private final CommercialPackagingDetailsService commercialPackagingDetailsService;

    private final CommercialPackagingDetailsQueryService commercialPackagingDetailsQueryService;

    public CommercialPackagingDetailsResource(CommercialPackagingDetailsService commercialPackagingDetailsService, CommercialPackagingDetailsQueryService commercialPackagingDetailsQueryService) {
        this.commercialPackagingDetailsService = commercialPackagingDetailsService;
        this.commercialPackagingDetailsQueryService = commercialPackagingDetailsQueryService;
    }

    /**
     * POST  /commercial-packaging-details : Create a new commercialPackagingDetails.
     *
     * @param commercialPackagingDetailsDTO the commercialPackagingDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPackagingDetailsDTO, or with status 400 (Bad Request) if the commercialPackagingDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-packaging-details")
    public ResponseEntity<CommercialPackagingDetailsDTO> createCommercialPackagingDetails(@RequestBody CommercialPackagingDetailsDTO commercialPackagingDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPackagingDetails : {}", commercialPackagingDetailsDTO);
        if (commercialPackagingDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPackagingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPackagingDetailsDTO result = commercialPackagingDetailsService.save(commercialPackagingDetailsDTO);
        return ResponseEntity.created(new URI("/api/commercial-packaging-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-packaging-details : Updates an existing commercialPackagingDetails.
     *
     * @param commercialPackagingDetailsDTO the commercialPackagingDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPackagingDetailsDTO,
     * or with status 400 (Bad Request) if the commercialPackagingDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPackagingDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-packaging-details")
    public ResponseEntity<CommercialPackagingDetailsDTO> updateCommercialPackagingDetails(@RequestBody CommercialPackagingDetailsDTO commercialPackagingDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPackagingDetails : {}", commercialPackagingDetailsDTO);
        if (commercialPackagingDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPackagingDetailsDTO result = commercialPackagingDetailsService.save(commercialPackagingDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPackagingDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-packaging-details : get all the commercialPackagingDetails.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPackagingDetails in body
     */
    @GetMapping("/commercial-packaging-details")
    public ResponseEntity<List<CommercialPackagingDetailsDTO>> getAllCommercialPackagingDetails(CommercialPackagingDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPackagingDetails by criteria: {}", criteria);
        Page<CommercialPackagingDetailsDTO> page = commercialPackagingDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-packaging-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-packaging-details/count : count all the commercialPackagingDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-packaging-details/count")
    public ResponseEntity<Long> countCommercialPackagingDetails(CommercialPackagingDetailsCriteria criteria) {
        log.debug("REST request to count CommercialPackagingDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPackagingDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-packaging-details/:id : get the "id" commercialPackagingDetails.
     *
     * @param id the id of the commercialPackagingDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPackagingDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-packaging-details/{id}")
    public ResponseEntity<CommercialPackagingDetailsDTO> getCommercialPackagingDetails(@PathVariable Long id) {
        log.debug("REST request to get CommercialPackagingDetails : {}", id);
        Optional<CommercialPackagingDetailsDTO> commercialPackagingDetailsDTO = commercialPackagingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPackagingDetailsDTO);
    }

    /**
     * DELETE  /commercial-packaging-details/:id : delete the "id" commercialPackagingDetails.
     *
     * @param id the id of the commercialPackagingDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-packaging-details/{id}")
    public ResponseEntity<Void> deleteCommercialPackagingDetails(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPackagingDetails : {}", id);
        commercialPackagingDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-packaging-details?query=:query : search for the commercialPackagingDetails corresponding
     * to the query.
     *
     * @param query the query of the commercialPackagingDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-packaging-details")
    public ResponseEntity<List<CommercialPackagingDetailsDTO>> searchCommercialPackagingDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPackagingDetails for query {}", query);
        Page<CommercialPackagingDetailsDTO> page = commercialPackagingDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-packaging-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
