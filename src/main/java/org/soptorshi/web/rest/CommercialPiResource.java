package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPiQueryService;
import org.soptorshi.service.CommercialPiService;
import org.soptorshi.service.dto.CommercialPiCriteria;
import org.soptorshi.service.dto.CommercialPiDTO;
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
 * REST controller for managing CommercialPi.
 */
@RestController
@RequestMapping("/api")
public class CommercialPiResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPiResource.class);

    private static final String ENTITY_NAME = "commercialPi";

    private final CommercialPiService commercialPiService;

    private final CommercialPiQueryService commercialPiQueryService;

    public CommercialPiResource(CommercialPiService commercialPiService, CommercialPiQueryService commercialPiQueryService) {
        this.commercialPiService = commercialPiService;
        this.commercialPiQueryService = commercialPiQueryService;
    }

    /**
     * POST  /commercial-pis : Create a new commercialPi.
     *
     * @param commercialPiDTO the commercialPiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPiDTO, or with status 400 (Bad Request) if the commercialPi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-pis")
    public ResponseEntity<CommercialPiDTO> createCommercialPi(@Valid @RequestBody CommercialPiDTO commercialPiDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPi : {}", commercialPiDTO);
        if (commercialPiDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPiDTO result = commercialPiService.save(commercialPiDTO);
        return ResponseEntity.created(new URI("/api/commercial-pis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-pis : Updates an existing commercialPi.
     *
     * @param commercialPiDTO the commercialPiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPiDTO,
     * or with status 400 (Bad Request) if the commercialPiDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-pis")
    public ResponseEntity<CommercialPiDTO> updateCommercialPi(@Valid @RequestBody CommercialPiDTO commercialPiDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPi : {}", commercialPiDTO);
        if (commercialPiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPiDTO result = commercialPiService.save(commercialPiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-pis : get all the commercialPis.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPis in body
     */
    @GetMapping("/commercial-pis")
    public ResponseEntity<List<CommercialPiDTO>> getAllCommercialPis(CommercialPiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPis by criteria: {}", criteria);
        Page<CommercialPiDTO> page = commercialPiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-pis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-pis/count : count all the commercialPis.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-pis/count")
    public ResponseEntity<Long> countCommercialPis(CommercialPiCriteria criteria) {
        log.debug("REST request to count CommercialPis by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPiQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-pis/:id : get the "id" commercialPi.
     *
     * @param id the id of the commercialPiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-pis/{id}")
    public ResponseEntity<CommercialPiDTO> getCommercialPi(@PathVariable Long id) {
        log.debug("REST request to get CommercialPi : {}", id);
        Optional<CommercialPiDTO> commercialPiDTO = commercialPiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPiDTO);
    }

    /**
     * DELETE  /commercial-pis/:id : delete the "id" commercialPi.
     *
     * @param id the id of the commercialPiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-pis/{id}")
    public ResponseEntity<Void> deleteCommercialPi(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPi : {}", id);
        commercialPiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-pis?query=:query : search for the commercialPi corresponding
     * to the query.
     *
     * @param query the query of the commercialPi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-pis")
    public ResponseEntity<List<CommercialPiDTO>> searchCommercialPis(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPis for query {}", query);
        Page<CommercialPiDTO> page = commercialPiService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-pis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
