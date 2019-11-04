package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPoStatusQueryService;
import org.soptorshi.service.CommercialPoStatusService;
import org.soptorshi.service.dto.CommercialPoStatusCriteria;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
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
 * REST controller for managing CommercialPoStatus.
 */
@RestController
@RequestMapping("/api")
public class CommercialPoStatusResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPoStatusResource.class);

    private static final String ENTITY_NAME = "commercialPoStatus";

    private final CommercialPoStatusService commercialPoStatusService;

    private final CommercialPoStatusQueryService commercialPoStatusQueryService;

    public CommercialPoStatusResource(CommercialPoStatusService commercialPoStatusService, CommercialPoStatusQueryService commercialPoStatusQueryService) {
        this.commercialPoStatusService = commercialPoStatusService;
        this.commercialPoStatusQueryService = commercialPoStatusQueryService;
    }

    /**
     * POST  /commercial-po-statuses : Create a new commercialPoStatus.
     *
     * @param commercialPoStatusDTO the commercialPoStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPoStatusDTO, or with status 400 (Bad Request) if the commercialPoStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-po-statuses")
    public ResponseEntity<CommercialPoStatusDTO> createCommercialPoStatus(@Valid @RequestBody CommercialPoStatusDTO commercialPoStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPoStatus : {}", commercialPoStatusDTO);
        if (commercialPoStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialPoStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialPoStatusDTO result = commercialPoStatusService.save(commercialPoStatusDTO);
        return ResponseEntity.created(new URI("/api/commercial-po-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-po-statuses : Updates an existing commercialPoStatus.
     *
     * @param commercialPoStatusDTO the commercialPoStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialPoStatusDTO,
     * or with status 400 (Bad Request) if the commercialPoStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialPoStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-po-statuses")
    public ResponseEntity<CommercialPoStatusDTO> updateCommercialPoStatus(@Valid @RequestBody CommercialPoStatusDTO commercialPoStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialPoStatus : {}", commercialPoStatusDTO);
        if (commercialPoStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialPoStatusDTO result = commercialPoStatusService.save(commercialPoStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialPoStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-po-statuses : get all the commercialPoStatuses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialPoStatuses in body
     */
    @GetMapping("/commercial-po-statuses")
    public ResponseEntity<List<CommercialPoStatusDTO>> getAllCommercialPoStatuses(CommercialPoStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialPoStatuses by criteria: {}", criteria);
        Page<CommercialPoStatusDTO> page = commercialPoStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-po-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-po-statuses/count : count all the commercialPoStatuses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-po-statuses/count")
    public ResponseEntity<Long> countCommercialPoStatuses(CommercialPoStatusCriteria criteria) {
        log.debug("REST request to count CommercialPoStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialPoStatusQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-po-statuses/:id : get the "id" commercialPoStatus.
     *
     * @param id the id of the commercialPoStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialPoStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-po-statuses/{id}")
    public ResponseEntity<CommercialPoStatusDTO> getCommercialPoStatus(@PathVariable Long id) {
        log.debug("REST request to get CommercialPoStatus : {}", id);
        Optional<CommercialPoStatusDTO> commercialPoStatusDTO = commercialPoStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialPoStatusDTO);
    }

    /**
     * DELETE  /commercial-po-statuses/:id : delete the "id" commercialPoStatus.
     *
     * @param id the id of the commercialPoStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-po-statuses/{id}")
    public ResponseEntity<Void> deleteCommercialPoStatus(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPoStatus : {}", id);
        commercialPoStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-po-statuses?query=:query : search for the commercialPoStatus corresponding
     * to the query.
     *
     * @param query the query of the commercialPoStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-po-statuses")
    public ResponseEntity<List<CommercialPoStatusDTO>> searchCommercialPoStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialPoStatuses for query {}", query);
        Page<CommercialPoStatusDTO> page = commercialPoStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-po-statuses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
