package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialProformaInvoiceQueryService;
import org.soptorshi.service.CommercialProformaInvoiceService;
import org.soptorshi.service.dto.CommercialProformaInvoiceCriteria;
import org.soptorshi.service.dto.CommercialProformaInvoiceDTO;
import org.soptorshi.web.rest.CommercialProformaInvoiceResource;
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
 * REST controller for managing CommercialProformaInvoice.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialProformaInvoiceExtendedResource extends CommercialProformaInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(CommercialProformaInvoiceExtendedResource.class);

    private static final String ENTITY_NAME = "commercialProformaInvoice";

    private final CommercialProformaInvoiceService commercialProformaInvoiceService;

    private final CommercialProformaInvoiceQueryService commercialProformaInvoiceQueryService;

    public CommercialProformaInvoiceExtendedResource(CommercialProformaInvoiceService commercialProformaInvoiceService, CommercialProformaInvoiceQueryService commercialProformaInvoiceQueryService) {
        super(commercialProformaInvoiceService, commercialProformaInvoiceQueryService);
        this.commercialProformaInvoiceService = commercialProformaInvoiceService;
        this.commercialProformaInvoiceQueryService = commercialProformaInvoiceQueryService;
    }

    /**
     * POST  /commercial-proforma-invoices : Create a new commercialProformaInvoice.
     *
     * @param commercialProformaInvoiceDTO the commercialProformaInvoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialProformaInvoiceDTO, or with status 400 (Bad Request) if the commercialProformaInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-proforma-invoices")
    public ResponseEntity<CommercialProformaInvoiceDTO> createCommercialProformaInvoice(@Valid @RequestBody CommercialProformaInvoiceDTO commercialProformaInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialProformaInvoice : {}", commercialProformaInvoiceDTO);
        if (commercialProformaInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialProformaInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialProformaInvoiceDTO result = commercialProformaInvoiceService.save(commercialProformaInvoiceDTO);
        return ResponseEntity.created(new URI("/api/commercial-proforma-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commercial-proforma-invoices : Updates an existing commercialProformaInvoice.
     *
     * @param commercialProformaInvoiceDTO the commercialProformaInvoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialProformaInvoiceDTO,
     * or with status 400 (Bad Request) if the commercialProformaInvoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialProformaInvoiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-proforma-invoices")
    public ResponseEntity<CommercialProformaInvoiceDTO> updateCommercialProformaInvoice(@Valid @RequestBody CommercialProformaInvoiceDTO commercialProformaInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialProformaInvoice : {}", commercialProformaInvoiceDTO);
        if (commercialProformaInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialProformaInvoiceDTO result = commercialProformaInvoiceService.save(commercialProformaInvoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialProformaInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commercial-proforma-invoices : get all the commercialProformaInvoices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of commercialProformaInvoices in body
     */
    @GetMapping("/commercial-proforma-invoices")
    public ResponseEntity<List<CommercialProformaInvoiceDTO>> getAllCommercialProformaInvoices(CommercialProformaInvoiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommercialProformaInvoices by criteria: {}", criteria);
        Page<CommercialProformaInvoiceDTO> page = commercialProformaInvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commercial-proforma-invoices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /commercial-proforma-invoices/count : count all the commercialProformaInvoices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/commercial-proforma-invoices/count")
    public ResponseEntity<Long> countCommercialProformaInvoices(CommercialProformaInvoiceCriteria criteria) {
        log.debug("REST request to count CommercialProformaInvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(commercialProformaInvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /commercial-proforma-invoices/:id : get the "id" commercialProformaInvoice.
     *
     * @param id the id of the commercialProformaInvoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commercialProformaInvoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commercial-proforma-invoices/{id}")
    public ResponseEntity<CommercialProformaInvoiceDTO> getCommercialProformaInvoice(@PathVariable Long id) {
        log.debug("REST request to get CommercialProformaInvoice : {}", id);
        Optional<CommercialProformaInvoiceDTO> commercialProformaInvoiceDTO = commercialProformaInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercialProformaInvoiceDTO);
    }

    /**
     * DELETE  /commercial-proforma-invoices/:id : delete the "id" commercialProformaInvoice.
     *
     * @param id the id of the commercialProformaInvoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-proforma-invoices/{id}")
    public ResponseEntity<Void> deleteCommercialProformaInvoice(@PathVariable Long id) {
        log.debug("REST request to delete CommercialProformaInvoice : {}", id);
        commercialProformaInvoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commercial-proforma-invoices?query=:query : search for the commercialProformaInvoice corresponding
     * to the query.
     *
     * @param query the query of the commercialProformaInvoice search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commercial-proforma-invoices")
    public ResponseEntity<List<CommercialProformaInvoiceDTO>> searchCommercialProformaInvoices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CommercialProformaInvoices for query {}", query);
        Page<CommercialProformaInvoiceDTO> page = commercialProformaInvoiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commercial-proforma-invoices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
