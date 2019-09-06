package org.soptorshi.web.rest;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.dto.MstAccountCriteria;
import org.soptorshi.service.MstAccountQueryService;
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
 * REST controller for managing MstAccount.
 */
@RestController
@RequestMapping("/api")
public class MstAccountResource {

    private final Logger log = LoggerFactory.getLogger(MstAccountResource.class);

    private static final String ENTITY_NAME = "mstAccount";

    private final MstAccountService mstAccountService;

    private final MstAccountQueryService mstAccountQueryService;

    public MstAccountResource(MstAccountService mstAccountService, MstAccountQueryService mstAccountQueryService) {
        this.mstAccountService = mstAccountService;
        this.mstAccountQueryService = mstAccountQueryService;
    }

    /**
     * POST  /mst-accounts : Create a new mstAccount.
     *
     * @param mstAccountDTO the mstAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mstAccountDTO, or with status 400 (Bad Request) if the mstAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mst-accounts")
    public ResponseEntity<MstAccountDTO> createMstAccount(@RequestBody MstAccountDTO mstAccountDTO) throws URISyntaxException {
        log.debug("REST request to save MstAccount : {}", mstAccountDTO);
        if (mstAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new mstAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MstAccountDTO result = mstAccountService.save(mstAccountDTO);
        return ResponseEntity.created(new URI("/api/mst-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mst-accounts : Updates an existing mstAccount.
     *
     * @param mstAccountDTO the mstAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mstAccountDTO,
     * or with status 400 (Bad Request) if the mstAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the mstAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mst-accounts")
    public ResponseEntity<MstAccountDTO> updateMstAccount(@RequestBody MstAccountDTO mstAccountDTO) throws URISyntaxException {
        log.debug("REST request to update MstAccount : {}", mstAccountDTO);
        if (mstAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MstAccountDTO result = mstAccountService.save(mstAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mstAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mst-accounts : get all the mstAccounts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mstAccounts in body
     */
    @GetMapping("/mst-accounts")
    public ResponseEntity<List<MstAccountDTO>> getAllMstAccounts(MstAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MstAccounts by criteria: {}", criteria);
        Page<MstAccountDTO> page = mstAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mst-accounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /mst-accounts/count : count all the mstAccounts.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/mst-accounts/count")
    public ResponseEntity<Long> countMstAccounts(MstAccountCriteria criteria) {
        log.debug("REST request to count MstAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(mstAccountQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /mst-accounts/:id : get the "id" mstAccount.
     *
     * @param id the id of the mstAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mstAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mst-accounts/{id}")
    public ResponseEntity<MstAccountDTO> getMstAccount(@PathVariable Long id) {
        log.debug("REST request to get MstAccount : {}", id);
        Optional<MstAccountDTO> mstAccountDTO = mstAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mstAccountDTO);
    }

    /**
     * DELETE  /mst-accounts/:id : delete the "id" mstAccount.
     *
     * @param id the id of the mstAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mst-accounts/{id}")
    public ResponseEntity<Void> deleteMstAccount(@PathVariable Long id) {
        log.debug("REST request to delete MstAccount : {}", id);
        mstAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mst-accounts?query=:query : search for the mstAccount corresponding
     * to the query.
     *
     * @param query the query of the mstAccount search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mst-accounts")
    public ResponseEntity<List<MstAccountDTO>> searchMstAccounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MstAccounts for query {}", query);
        Page<MstAccountDTO> page = mstAccountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mst-accounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
