package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPoQueryService;
import org.soptorshi.service.CommercialPoService;
import org.soptorshi.service.dto.CommercialPoCriteria;
import org.soptorshi.service.dto.CommercialPoDTO;
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
 * REST controller for managing CommercialPo.
 */
@RestController
@RequestMapping("/api")
public class CommercialPoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPoResource.class);

    private static final String ENTITY_NAME = "commercialPo";

    private final CommercialPoService commercialPoService;

    private final CommercialPoQueryService commercialPoQueryService;

    public CommercialPoResource(CommercialPoService commercialPoService, CommercialPoQueryService commercialPoQueryService) {
        this.commercialPoService = commercialPoService;
        this.commercialPoQueryService = commercialPoQueryService;
    }

    /**
     * POST  /commercial-pos : Create a new commercialPo.
     *
     * @param commercialPoDTO the commercialPoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPoDTO, or with status 400 (Bad Request) if the commercialPo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-pos")
    public ResponseEntity<CommercialPoDTO> createCommercialPo(@Valid @RequestBody CommercialPoDTO commercialPoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPo : {}", commercialPoDTO);
        if (commercialPoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPoDTO result = commercialPoService.save(commercialPoDTO);
        return ResponseEntity.created(new URI("/api/commercial-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-pos : Updates an existing commercialPo.
     *
     * @param commercialPoDTO the commercialPoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPoDTO,
     * or with status 400 (Bad Request) if the commercialPoDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-pos")
    public ResponseEntity<CommercialPoDTO> updateCommercialPo(@Valid @RequestBody CommercialPoDTO commercialPoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPo : {}", commercialPoDTO);
        if (commercialPoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPoDTO result = commercialPoService.save(commercialPoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-pos : get all the commercialPos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPos in body
     */
    @GetMapping("/commercial-pos")
    public ResponseEntity<List<CommercialPoDTO>> getAllCommercialPos(CommercialPoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPos by criteria: {}", criteria);
        Page<CommercialPoDTO> page = commercialPoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-pos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-pos/count : count all the commercialPos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-pos/count")
    public ResponseEntity<Long> countCommercialPos(CommercialPoCriteria criteria) {
        log.debug("REST request to count CommercialPos by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-pos/:id : get the "id" commercialPo.
     *
     * @param id the id of the commercialPoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-pos/{id}")
    public ResponseEntity<CommercialPoDTO> getCommercialPo(@PathVariable Long id) {
        log.debug("REST request to get CommercialPo : {}", id);
        Optional<CommercialPoDTO> commercialPoDTO = commercialPoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPoDTO);
    }

    /**
     * DELETE  /commercial-pos/:id : delete the "id" commercialPo.
     *
     * @param id the id of the commercialPoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-pos/{id}")
    public ResponseEntity<Void> deleteCommercialPo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPo : {}", id);
        commercialPoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-pos?query=:query : search for the commercialPo corresponding
     * to the query.
     *
     * @param query the query of the commercialPo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-pos")
    public ResponseEntity<List<CommercialPoDTO>> searchCommercialPos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPos for query {}", query);
        Page<CommercialPoDTO> page = commercialPoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-pos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
