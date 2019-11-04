package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPaymentInfoQueryService;
import org.soptorshi.service.CommercialPaymentInfoService;
import org.soptorshi.service.dto.CommercialPaymentInfoCriteria;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
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
 * REST controller for managing CommercialPaymentInfo.
 */
@RestController
@RequestMapping("/api")
public class CommercialPaymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoResource.class);

    private static final String ENTITY_NAME = "commercialPaymentInfo";

    private final CommercialPaymentInfoService commercialPaymentInfoService;

    private final CommercialPaymentInfoQueryService commercialPaymentInfoQueryService;

    public CommercialPaymentInfoResource(CommercialPaymentInfoService commercialPaymentInfoService, CommercialPaymentInfoQueryService commercialPaymentInfoQueryService) {
        this.commercialPaymentInfoService = commercialPaymentInfoService;
        this.commercialPaymentInfoQueryService = commercialPaymentInfoQueryService;
    }

    /**
     * POST  /commercial-payment-infos : Create a new commercialPaymentInfo.
     *
     * @param commercialPaymentInfoDTO the commercialPaymentInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPaymentInfoDTO, or with status 400 (Bad Request) if the commercialPaymentInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-payment-infos")
    public ResponseEntity<CommercialPaymentInfoDTO> createCommercialPaymentInfo(@Valid @RequestBody CommercialPaymentInfoDTO commercialPaymentInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPaymentInfo : {}", commercialPaymentInfoDTO);
        if (commercialPaymentInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPaymentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPaymentInfoDTO result = commercialPaymentInfoService.save(commercialPaymentInfoDTO);
        return ResponseEntity.created(new URI("/api/commercial-payment-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-payment-infos : Updates an existing commercialPaymentInfo.
     *
     * @param commercialPaymentInfoDTO the commercialPaymentInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPaymentInfoDTO,
     * or with status 400 (Bad Request) if the commercialPaymentInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPaymentInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-payment-infos")
    public ResponseEntity<CommercialPaymentInfoDTO> updateCommercialPaymentInfo(@Valid @RequestBody CommercialPaymentInfoDTO commercialPaymentInfoDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPaymentInfo : {}", commercialPaymentInfoDTO);
        if (commercialPaymentInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPaymentInfoDTO result = commercialPaymentInfoService.save(commercialPaymentInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPaymentInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-payment-infos : get all the commercialPaymentInfos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPaymentInfos in body
     */
    @GetMapping("/commercial-payment-infos")
    public ResponseEntity<List<CommercialPaymentInfoDTO>> getAllCommercialPaymentInfos(CommercialPaymentInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPaymentInfos by criteria: {}", criteria);
        Page<CommercialPaymentInfoDTO> page = commercialPaymentInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-payment-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-payment-infos/count : count all the commercialPaymentInfos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-payment-infos/count")
    public ResponseEntity<Long> countCommercialPaymentInfos(CommercialPaymentInfoCriteria criteria) {
        log.debug("REST request to count CommercialPaymentInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPaymentInfoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-payment-infos/:id : get the "id" commercialPaymentInfo.
     *
     * @param id the id of the commercialPaymentInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPaymentInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-payment-infos/{id}")
    public ResponseEntity<CommercialPaymentInfoDTO> getCommercialPaymentInfo(@PathVariable Long id) {
        log.debug("REST request to get CommercialPaymentInfo : {}", id);
        Optional<CommercialPaymentInfoDTO> commercialPaymentInfoDTO = commercialPaymentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPaymentInfoDTO);
    }

    /**
     * DELETE  /commercial-payment-infos/:id : delete the "id" commercialPaymentInfo.
     *
     * @param id the id of the commercialPaymentInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-payment-infos/{id}")
    public ResponseEntity<Void> deleteCommercialPaymentInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPaymentInfo : {}", id);
        commercialPaymentInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-payment-infos?query=:query : search for the commercialPaymentInfo corresponding
     * to the query.
     *
     * @param query the query of the commercialPaymentInfo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-payment-infos")
    public ResponseEntity<List<CommercialPaymentInfoDTO>> searchCommercialPaymentInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPaymentInfos for query {}", query);
        Page<CommercialPaymentInfoDTO> page = commercialPaymentInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-payment-infos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
