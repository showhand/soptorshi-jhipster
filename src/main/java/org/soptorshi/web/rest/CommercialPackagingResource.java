package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPackagingQueryService;
import org.soptorshi.service.CommercialPackagingService;
import org.soptorshi.service.dto.CommercialPackagingCriteria;
import org.soptorshi.service.dto.CommercialPackagingDTO;
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
 * REST controller for managing CommercialPackaging.
 */
@RestController
@RequestMapping("/api")
public class CommercialPackagingResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingResource.class);

    private static final String ENTITY_NAME = "commercialPackaging";

    private final CommercialPackagingService commercialPackagingService;

    private final CommercialPackagingQueryService commercialPackagingQueryService;

    public CommercialPackagingResource(CommercialPackagingService commercialPackagingService, CommercialPackagingQueryService commercialPackagingQueryService) {
        this.commercialPackagingService = commercialPackagingService;
        this.commercialPackagingQueryService = commercialPackagingQueryService;
    }

    /**
     * POST  /commercial-packagings : Create a new commercialPackaging.
     *
     * @param commercialPackagingDTO the commercialPackagingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPackagingDTO, or with status 400 (Bad Request) if the commercialPackaging has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-packagings")
    public ResponseEntity<CommercialPackagingDTO> createCommercialPackaging(@Valid @RequestBody CommercialPackagingDTO commercialPackagingDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPackaging : {}", commercialPackagingDTO);
        if (commercialPackagingDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPackaging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPackagingDTO result = commercialPackagingService.save(commercialPackagingDTO);
        return ResponseEntity.created(new URI("/api/commercial-packagings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-packagings : Updates an existing commercialPackaging.
     *
     * @param commercialPackagingDTO the commercialPackagingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPackagingDTO,
     * or with status 400 (Bad Request) if the commercialPackagingDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPackagingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-packagings")
    public ResponseEntity<CommercialPackagingDTO> updateCommercialPackaging(@Valid @RequestBody CommercialPackagingDTO commercialPackagingDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPackaging : {}", commercialPackagingDTO);
        if (commercialPackagingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPackagingDTO result = commercialPackagingService.save(commercialPackagingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPackagingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-packagings : get all the commercialPackagings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPackagings in body
     */
    @GetMapping("/commercial-packagings")
    public ResponseEntity<List<CommercialPackagingDTO>> getAllCommercialPackagings(CommercialPackagingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPackagings by criteria: {}", criteria);
        Page<CommercialPackagingDTO> page = commercialPackagingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-packagings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-packagings/count : count all the commercialPackagings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-packagings/count")
    public ResponseEntity<Long> countCommercialPackagings(CommercialPackagingCriteria criteria) {
        log.debug("REST request to count CommercialPackagings by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPackagingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-packagings/:id : get the "id" commercialPackaging.
     *
     * @param id the id of the commercialPackagingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPackagingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-packagings/{id}")
    public ResponseEntity<CommercialPackagingDTO> getCommercialPackaging(@PathVariable Long id) {
        log.debug("REST request to get CommercialPackaging : {}", id);
        Optional<CommercialPackagingDTO> commercialPackagingDTO = commercialPackagingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPackagingDTO);
    }

    /**
     * DELETE  /commercial-packagings/:id : delete the "id" commercialPackaging.
     *
     * @param id the id of the commercialPackagingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-packagings/{id}")
    public ResponseEntity<Void> deleteCommercialPackaging(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPackaging : {}", id);
        commercialPackagingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-packagings?query=:query : search for the commercialPackaging corresponding
     * to the query.
     *
     * @param query the query of the commercialPackaging search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-packagings")
    public ResponseEntity<List<CommercialPackagingDTO>> searchCommercialPackagings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPackagings for query {}", query);
        Page<CommercialPackagingDTO> page = commercialPackagingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-packagings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
