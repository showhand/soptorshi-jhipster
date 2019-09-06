package org.soptorshi.web.rest;
import org.soptorshi.service.ChequeRegisterService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ChequeRegisterDTO;
import org.soptorshi.service.dto.ChequeRegisterCriteria;
import org.soptorshi.service.ChequeRegisterQueryService;
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
 * REST controller for managing ChequeRegister.
 */
@RestController
@RequestMapping("/api")
public class ChequeRegisterResource {

    private final Logger log = LoggerFactory.getLogger(ChequeRegisterResource.class);

    private static final String ENTITY_NAME = "chequeRegister";

    private final ChequeRegisterService chequeRegisterService;

    private final ChequeRegisterQueryService chequeRegisterQueryService;

    public ChequeRegisterResource(ChequeRegisterService chequeRegisterService, ChequeRegisterQueryService chequeRegisterQueryService) {
        this.chequeRegisterService = chequeRegisterService;
        this.chequeRegisterQueryService = chequeRegisterQueryService;
    }

    /**
     * POST  /cheque-registers : Create a new chequeRegister.
     *
     * @param chequeRegisterDTO the chequeRegisterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chequeRegisterDTO, or with status 400 (Bad Request) if the chequeRegister has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cheque-registers")
    public ResponseEntity<ChequeRegisterDTO> createChequeRegister(@RequestBody ChequeRegisterDTO chequeRegisterDTO) throws URISyntaxException {
        log.debug("REST request to save ChequeRegister : {}", chequeRegisterDTO);
        if (chequeRegisterDTO.getId() != null) {
            throw new BadRequestAlertException("A new chequeRegister cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChequeRegisterDTO result = chequeRegisterService.save(chequeRegisterDTO);
        return ResponseEntity.created(new URI("/api/cheque-registers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cheque-registers : Updates an existing chequeRegister.
     *
     * @param chequeRegisterDTO the chequeRegisterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chequeRegisterDTO,
     * or with status 400 (Bad Request) if the chequeRegisterDTO is not valid,
     * or with status 500 (Internal Server Error) if the chequeRegisterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cheque-registers")
    public ResponseEntity<ChequeRegisterDTO> updateChequeRegister(@RequestBody ChequeRegisterDTO chequeRegisterDTO) throws URISyntaxException {
        log.debug("REST request to update ChequeRegister : {}", chequeRegisterDTO);
        if (chequeRegisterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChequeRegisterDTO result = chequeRegisterService.save(chequeRegisterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chequeRegisterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cheque-registers : get all the chequeRegisters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of chequeRegisters in body
     */
    @GetMapping("/cheque-registers")
    public ResponseEntity<List<ChequeRegisterDTO>> getAllChequeRegisters(ChequeRegisterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ChequeRegisters by criteria: {}", criteria);
        Page<ChequeRegisterDTO> page = chequeRegisterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cheque-registers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cheque-registers/count : count all the chequeRegisters.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cheque-registers/count")
    public ResponseEntity<Long> countChequeRegisters(ChequeRegisterCriteria criteria) {
        log.debug("REST request to count ChequeRegisters by criteria: {}", criteria);
        return ResponseEntity.ok().body(chequeRegisterQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cheque-registers/:id : get the "id" chequeRegister.
     *
     * @param id the id of the chequeRegisterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chequeRegisterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cheque-registers/{id}")
    public ResponseEntity<ChequeRegisterDTO> getChequeRegister(@PathVariable Long id) {
        log.debug("REST request to get ChequeRegister : {}", id);
        Optional<ChequeRegisterDTO> chequeRegisterDTO = chequeRegisterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chequeRegisterDTO);
    }

    /**
     * DELETE  /cheque-registers/:id : delete the "id" chequeRegister.
     *
     * @param id the id of the chequeRegisterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cheque-registers/{id}")
    public ResponseEntity<Void> deleteChequeRegister(@PathVariable Long id) {
        log.debug("REST request to delete ChequeRegister : {}", id);
        chequeRegisterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cheque-registers?query=:query : search for the chequeRegister corresponding
     * to the query.
     *
     * @param query the query of the chequeRegister search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cheque-registers")
    public ResponseEntity<List<ChequeRegisterDTO>> searchChequeRegisters(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChequeRegisters for query {}", query);
        Page<ChequeRegisterDTO> page = chequeRegisterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cheque-registers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
