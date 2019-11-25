package org.soptorshi.web.rest;
import org.soptorshi.service.SalaryMessagesService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.dto.SalaryMessagesCriteria;
import org.soptorshi.service.SalaryMessagesQueryService;
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
 * REST controller for managing SalaryMessages.
 */
@RestController
@RequestMapping("/api")
public class SalaryMessagesResource {

    private final Logger log = LoggerFactory.getLogger(SalaryMessagesResource.class);

    private static final String ENTITY_NAME = "salaryMessages";

    private final SalaryMessagesService salaryMessagesService;

    private final SalaryMessagesQueryService salaryMessagesQueryService;

    public SalaryMessagesResource(SalaryMessagesService salaryMessagesService, SalaryMessagesQueryService salaryMessagesQueryService) {
        this.salaryMessagesService = salaryMessagesService;
        this.salaryMessagesQueryService = salaryMessagesQueryService;
    }

    /**
     * POST  /salary-messages : Create a new salaryMessages.
     *
     * @param salaryMessagesDTO the salaryMessagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryMessagesDTO, or with status 400 (Bad Request) if the salaryMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salary-messages")
    public ResponseEntity<SalaryMessagesDTO> createSalaryMessages(@RequestBody SalaryMessagesDTO salaryMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save SalaryMessages : {}", salaryMessagesDTO);
        if (salaryMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new salaryMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryMessagesDTO result = salaryMessagesService.save(salaryMessagesDTO);
        return ResponseEntity.created(new URI("/api/salary-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salary-messages : Updates an existing salaryMessages.
     *
     * @param salaryMessagesDTO the salaryMessagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryMessagesDTO,
     * or with status 400 (Bad Request) if the salaryMessagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryMessagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salary-messages")
    public ResponseEntity<SalaryMessagesDTO> updateSalaryMessages(@RequestBody SalaryMessagesDTO salaryMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update SalaryMessages : {}", salaryMessagesDTO);
        if (salaryMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryMessagesDTO result = salaryMessagesService.save(salaryMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salary-messages : get all the salaryMessages.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of salaryMessages in body
     */
    @GetMapping("/salary-messages")
    public ResponseEntity<List<SalaryMessagesDTO>> getAllSalaryMessages(SalaryMessagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SalaryMessages by criteria: {}", criteria);
        Page<SalaryMessagesDTO> page = salaryMessagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salary-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /salary-messages/count : count all the salaryMessages.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/salary-messages/count")
    public ResponseEntity<Long> countSalaryMessages(SalaryMessagesCriteria criteria) {
        log.debug("REST request to count SalaryMessages by criteria: {}", criteria);
        return ResponseEntity.ok().body(salaryMessagesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /salary-messages/:id : get the "id" salaryMessages.
     *
     * @param id the id of the salaryMessagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salaryMessagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salary-messages/{id}")
    public ResponseEntity<SalaryMessagesDTO> getSalaryMessages(@PathVariable Long id) {
        log.debug("REST request to get SalaryMessages : {}", id);
        Optional<SalaryMessagesDTO> salaryMessagesDTO = salaryMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaryMessagesDTO);
    }

    /**
     * DELETE  /salary-messages/:id : delete the "id" salaryMessages.
     *
     * @param id the id of the salaryMessagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salary-messages/{id}")
    public ResponseEntity<Void> deleteSalaryMessages(@PathVariable Long id) {
        log.debug("REST request to delete SalaryMessages : {}", id);
        salaryMessagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/salary-messages?query=:query : search for the salaryMessages corresponding
     * to the query.
     *
     * @param query the query of the salaryMessages search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/salary-messages")
    public ResponseEntity<List<SalaryMessagesDTO>> searchSalaryMessages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SalaryMessages for query {}", query);
        Page<SalaryMessagesDTO> page = salaryMessagesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/salary-messages");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
