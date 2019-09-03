package org.soptorshi.web.rest;
import org.soptorshi.service.QuotationDetailsService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.dto.QuotationDetailsCriteria;
import org.soptorshi.service.QuotationDetailsQueryService;
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
 * REST controller for managing QuotationDetails.
 */
@RestController
@RequestMapping("/api")
public class QuotationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(QuotationDetailsResource.class);

    private static final String ENTITY_NAME = "quotationDetails";

    private final QuotationDetailsService quotationDetailsService;

    private final QuotationDetailsQueryService quotationDetailsQueryService;

    public QuotationDetailsResource(QuotationDetailsService quotationDetailsService, QuotationDetailsQueryService quotationDetailsQueryService) {
        this.quotationDetailsService = quotationDetailsService;
        this.quotationDetailsQueryService = quotationDetailsQueryService;
    }

    /**
     * POST  /quotation-details : Create a new quotationDetails.
     *
     * @param quotationDetailsDTO the quotationDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotationDetailsDTO, or with status 400 (Bad Request) if the quotationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quotation-details")
    public ResponseEntity<QuotationDetailsDTO> createQuotationDetails(@RequestBody QuotationDetailsDTO quotationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save QuotationDetails : {}", quotationDetailsDTO);
        if (quotationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new quotationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationDetailsDTO result = quotationDetailsService.save(quotationDetailsDTO);
        return ResponseEntity.created(new URI("/api/quotation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotation-details : Updates an existing quotationDetails.
     *
     * @param quotationDetailsDTO the quotationDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotationDetailsDTO,
     * or with status 400 (Bad Request) if the quotationDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the quotationDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quotation-details")
    public ResponseEntity<QuotationDetailsDTO> updateQuotationDetails(@RequestBody QuotationDetailsDTO quotationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update QuotationDetails : {}", quotationDetailsDTO);
        if (quotationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuotationDetailsDTO result = quotationDetailsService.save(quotationDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotation-details : get all the quotationDetails.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of quotationDetails in body
     */
    @GetMapping("/quotation-details")
    public ResponseEntity<List<QuotationDetailsDTO>> getAllQuotationDetails(QuotationDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuotationDetails by criteria: {}", criteria);
        Page<QuotationDetailsDTO> page = quotationDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quotation-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /quotation-details/count : count all the quotationDetails.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/quotation-details/count")
    public ResponseEntity<Long> countQuotationDetails(QuotationDetailsCriteria criteria) {
        log.debug("REST request to count QuotationDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(quotationDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /quotation-details/:id : get the "id" quotationDetails.
     *
     * @param id the id of the quotationDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quotationDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/quotation-details/{id}")
    public ResponseEntity<QuotationDetailsDTO> getQuotationDetails(@PathVariable Long id) {
        log.debug("REST request to get QuotationDetails : {}", id);
        Optional<QuotationDetailsDTO> quotationDetailsDTO = quotationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quotationDetailsDTO);
    }

    /**
     * DELETE  /quotation-details/:id : delete the "id" quotationDetails.
     *
     * @param id the id of the quotationDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quotation-details/{id}")
    public ResponseEntity<Void> deleteQuotationDetails(@PathVariable Long id) {
        log.debug("REST request to delete QuotationDetails : {}", id);
        quotationDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/quotation-details?query=:query : search for the quotationDetails corresponding
     * to the query.
     *
     * @param query the query of the quotationDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/quotation-details")
    public ResponseEntity<List<QuotationDetailsDTO>> searchQuotationDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of QuotationDetails for query {}", query);
        Page<QuotationDetailsDTO> page = quotationDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/quotation-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
