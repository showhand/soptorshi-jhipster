package org.soptorshi.web.rest;
import org.soptorshi.service.CommercialInvoiceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.CommercialInvoiceDTO;
import org.soptorshi.service.dto.CommercialInvoiceCriteria;
import org.soptorshi.service.CommercialInvoiceQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CommercialInvoice.
 */
@RestController
@RequestMapping("/api")
public class CommercialInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(CommercialInvoiceResource.class);

    private static final String ENTITY_NAME = "commercialInvoice";

    private final CommercialInvoiceService commercialInvoiceService;

    private final CommercialInvoiceQueryService commercialInvoiceQueryService;

    public CommercialInvoiceResource(CommercialInvoiceService commercialInvoiceService, CommercialInvoiceQueryService commercialInvoiceQueryService) {
        this.commercialInvoiceService = commercialInvoiceService;
        this.commercialInvoiceQueryService = commercialInvoiceQueryService;
    }

    /**
     * POST  /commercial-invoices : Create a new commercialInvoice.
     *
     * @param commercialInvoiceDTO the commercialInvoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialInvoiceDTO, or with status 400 (Bad Request) if the commercialInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-invoices")
    public ResponseEntity<CommercialInvoiceDTO> createCommercialInvoice(@Valid @RequestBody CommercialInvoiceDTO commercialInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialInvoice : {}", commercialInvoiceDTO);
        if (commercialInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialInvoiceDTO result = commercialInvoiceService.save(commercialInvoiceDTO);
        return ResponseEntity.created(new URI("/api/commercial-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-invoices : Updates an existing commercialInvoice.
     *
     * @param commercialInvoiceDTO the commercialInvoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialInvoiceDTO,
     * or with status 400 (Bad Request) if the commercialInvoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialInvoiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-invoices")
    public ResponseEntity<CommercialInvoiceDTO> updateCommercialInvoice(@Valid @RequestBody CommercialInvoiceDTO commercialInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialInvoice : {}", commercialInvoiceDTO);
        if (commercialInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialInvoiceDTO result = commercialInvoiceService.save(commercialInvoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-invoices : get all the commercialInvoices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialInvoices in body
     */
    @GetMapping("/commercial-invoices")
    public ResponseEntity<List<CommercialInvoiceDTO>> getAllCommercialInvoices(CommercialInvoiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialInvoices by criteria: {}", criteria);
        Page<CommercialInvoiceDTO> page = commercialInvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-invoices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-invoices/count : count all the commercialInvoices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-invoices/count")
    public ResponseEntity<Long> countCommercialInvoices(CommercialInvoiceCriteria criteria) {
        log.debug("REST request to count CommercialInvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialInvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-invoices/:id : get the "id" commercialInvoice.
     *
     * @param id the id of the commercialInvoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialInvoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-invoices/{id}")
    public ResponseEntity<CommercialInvoiceDTO> getCommercialInvoice(@PathVariable Long id) {
        log.debug("REST request to get CommercialInvoice : {}", id);
        Optional<CommercialInvoiceDTO> commercialInvoiceDTO = commercialInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialInvoiceDTO);
    }

    /**
     * DELETE  /commercial-invoices/:id : delete the "id" commercialInvoice.
     *
     * @param id the id of the commercialInvoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-invoices/{id}")
    public ResponseEntity<Void> deleteCommercialInvoice(@PathVariable Long id) {
        log.debug("REST request to delete CommercialInvoice : {}", id);
        commercialInvoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-invoices?query=:query : search for the commercialInvoice corresponding
     * to the query.
     *
     * @param query the query of the commercialInvoice search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-invoices")
    public ResponseEntity<List<CommercialInvoiceDTO>> searchCommercialInvoices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialInvoices for query {}", query);
        Page<CommercialInvoiceDTO> page = commercialInvoiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-invoices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
