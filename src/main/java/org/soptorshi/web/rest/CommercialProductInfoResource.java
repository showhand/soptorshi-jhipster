package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialProductInfoQueryService;
import org.soptorshi.service.CommercialProductInfoService;
import org.soptorshi.service.dto.CommercialProductInfoCriteria;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
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
 * REST controller for managing CommercialProductInfo.
 */
@RestController
@RequestMapping("/api")
public class CommercialProductInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoResource.class);

    private static final String ENTITY_NAME = "commercialProductInfo";

    private final CommercialProductInfoService commercialProductInfoService;

    private final CommercialProductInfoQueryService commercialProductInfoQueryService;

    public CommercialProductInfoResource(CommercialProductInfoService commercialProductInfoService, CommercialProductInfoQueryService commercialProductInfoQueryService) {
        this.commercialProductInfoService = commercialProductInfoService;
        this.commercialProductInfoQueryService = commercialProductInfoQueryService;
    }

    /**
     * POST  /commercial-product-infos : Create a new commercialProductInfo.
     *
     * @param commercialProductInfoDTO the commercialProductInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialProductInfoDTO, or with status 400 (Bad Request) if the commercialProductInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-product-infos")
    public ResponseEntity<CommercialProductInfoDTO> createCommercialProductInfo(@Valid @RequestBody CommercialProductInfoDTO commercialProductInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialProductInfo : {}", commercialProductInfoDTO);
        if (commercialProductInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialProductInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialProductInfoDTO result = commercialProductInfoService.save(commercialProductInfoDTO);
        return ResponseEntity.created(new URI("/api/commercial-product-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-product-infos : Updates an existing commercialProductInfo.
     *
     * @param commercialProductInfoDTO the commercialProductInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialProductInfoDTO,
     * or with status 400 (Bad Request) if the commercialProductInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialProductInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-product-infos")
    public ResponseEntity<CommercialProductInfoDTO> updateCommercialProductInfo(@Valid @RequestBody CommercialProductInfoDTO commercialProductInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialProductInfo : {}", commercialProductInfoDTO);
        if (commercialProductInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialProductInfoDTO result = commercialProductInfoService.save(commercialProductInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialProductInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-product-infos : get all the commercialProductInfos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialProductInfos in body
     */
    @GetMapping("/commercial-product-infos")
    public ResponseEntity<List<CommercialProductInfoDTO>> getAllCommercialProductInfos(CommercialProductInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialProductInfos by criteria: {}", criteria);
        Page<CommercialProductInfoDTO> page = commercialProductInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-product-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-product-infos/count : count all the commercialProductInfos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-product-infos/count")
    public ResponseEntity<Long> countCommercialProductInfos(CommercialProductInfoCriteria criteria) {
        log.debug("REST request to count CommercialProductInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialProductInfoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-product-infos/:id : get the "id" commercialProductInfo.
     *
     * @param id the id of the commercialProductInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialProductInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-product-infos/{id}")
    public ResponseEntity<CommercialProductInfoDTO> getCommercialProductInfo(@PathVariable Long id) {
        log.debug("REST request to get CommercialProductInfo : {}", id);
        Optional<CommercialProductInfoDTO> commercialProductInfoDTO = commercialProductInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialProductInfoDTO);
    }

    /**
     * DELETE  /commercial-product-infos/:id : delete the "id" commercialProductInfo.
     *
     * @param id the id of the commercialProductInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-product-infos/{id}")
    public ResponseEntity<Void> deleteCommercialProductInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialProductInfo : {}", id);
        commercialProductInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-product-infos?query=:query : search for the commercialProductInfo corresponding
     * to the query.
     *
     * @param query the query of the commercialProductInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-product-infos")
    public ResponseEntity<List<CommercialProductInfoDTO>> searchCommercialProductInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialProductInfos for query {}", query);
        Page<CommercialProductInfoDTO> page = commercialProductInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-product-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
