package org.soptorshi.web.rest;
import org.soptorshi.service.ProvidentFundService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ProvidentFundDTO;
import org.soptorshi.service.dto.ProvidentFundCriteria;
import org.soptorshi.service.ProvidentFundQueryService;
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
 * REST controller for managing ProvidentFund.
 */
@RestController
@RequestMapping("/api")
public class ProvidentFundResource {

    private final Logger log = LoggerFactory.getLogger(ProvidentFundResource.class);

    private static final String ENTITY_NAME = "providentFund";

    private final ProvidentFundService providentFundService;

    private final ProvidentFundQueryService providentFundQueryService;

    public ProvidentFundResource(ProvidentFundService providentFundService, ProvidentFundQueryService providentFundQueryService) {
        this.providentFundService = providentFundService;
        this.providentFundQueryService = providentFundQueryService;
    }

    /**
     * POST  /provident-funds : Create a new providentFund.
     *
     * @param providentFundDTO the providentFundDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providentFundDTO, or with status 400 (Bad Request) if the providentFund has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provident-funds")
    public ResponseEntity<ProvidentFundDTO> createProvidentFund(@Valid @RequestBody ProvidentFundDTO providentFundDTO) throws URISyntaxException {
        log.debug("REST request to save ProvidentFund : {}", providentFundDTO);
        if (providentFundDTO.getId() != null) {
            throw new BadRequestAlertException("A new providentFund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvidentFundDTO result = providentFundService.save(providentFundDTO);
        return ResponseEntity.created(new URI("/api/provident-funds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provident-funds : Updates an existing providentFund.
     *
     * @param providentFundDTO the providentFundDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providentFundDTO,
     * or with status 400 (Bad Request) if the providentFundDTO is not valid,
     * or with status 500 (Internal Server Error) if the providentFundDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provident-funds")
    public ResponseEntity<ProvidentFundDTO> updateProvidentFund(@Valid @RequestBody ProvidentFundDTO providentFundDTO) throws URISyntaxException {
        log.debug("REST request to update ProvidentFund : {}", providentFundDTO);
        if (providentFundDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvidentFundDTO result = providentFundService.save(providentFundDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providentFundDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provident-funds : get all the providentFunds.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of providentFunds in body
     */
    @GetMapping("/provident-funds")
    public ResponseEntity<List<ProvidentFundDTO>> getAllProvidentFunds(ProvidentFundCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProvidentFunds by criteria: {}", criteria);
        Page<ProvidentFundDTO> page = providentFundQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provident-funds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /provident-funds/count : count all the providentFunds.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/provident-funds/count")
    public ResponseEntity<Long> countProvidentFunds(ProvidentFundCriteria criteria) {
        log.debug("REST request to count ProvidentFunds by criteria: {}", criteria);
        return ResponseEntity.ok().body(providentFundQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /provident-funds/:id : get the "id" providentFund.
     *
     * @param id the id of the providentFundDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providentFundDTO, or with status 404 (Not Found)
     */
    @GetMapping("/provident-funds/{id}")
    public ResponseEntity<ProvidentFundDTO> getProvidentFund(@PathVariable Long id) {
        log.debug("REST request to get ProvidentFund : {}", id);
        Optional<ProvidentFundDTO> providentFundDTO = providentFundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providentFundDTO);
    }

    /**
     * DELETE  /provident-funds/:id : delete the "id" providentFund.
     *
     * @param id the id of the providentFundDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provident-funds/{id}")
    public ResponseEntity<Void> deleteProvidentFund(@PathVariable Long id) {
        log.debug("REST request to delete ProvidentFund : {}", id);
        providentFundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provident-funds?query=:query : search for the providentFund corresponding
     * to the query.
     *
     * @param query the query of the providentFund search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/provident-funds")
    public ResponseEntity<List<ProvidentFundDTO>> searchProvidentFunds(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProvidentFunds for query {}", query);
        Page<ProvidentFundDTO> page = providentFundService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/provident-funds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
