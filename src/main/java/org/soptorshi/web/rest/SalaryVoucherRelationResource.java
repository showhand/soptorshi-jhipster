package org.soptorshi.web.rest;
import org.soptorshi.service.SalaryVoucherRelationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.dto.SalaryVoucherRelationCriteria;
import org.soptorshi.service.SalaryVoucherRelationQueryService;
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
 * REST controller for managing SalaryVoucherRelation.
 */
@RestController
@RequestMapping("/api")
public class SalaryVoucherRelationResource {

    private final Logger log = LoggerFactory.getLogger(SalaryVoucherRelationResource.class);

    private static final String ENTITY_NAME = "salaryVoucherRelation";

    private final SalaryVoucherRelationService salaryVoucherRelationService;

    private final SalaryVoucherRelationQueryService salaryVoucherRelationQueryService;

    public SalaryVoucherRelationResource(SalaryVoucherRelationService salaryVoucherRelationService, SalaryVoucherRelationQueryService salaryVoucherRelationQueryService) {
        this.salaryVoucherRelationService = salaryVoucherRelationService;
        this.salaryVoucherRelationQueryService = salaryVoucherRelationQueryService;
    }

    /**
     * POST  /salary-voucher-relations : Create a new salaryVoucherRelation.
     *
     * @param salaryVoucherRelationDTO the salaryVoucherRelationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryVoucherRelationDTO, or with status 400 (Bad Request) if the salaryVoucherRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salary-voucher-relations")
    public ResponseEntity<SalaryVoucherRelationDTO> createSalaryVoucherRelation(@RequestBody SalaryVoucherRelationDTO salaryVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to save SalaryVoucherRelation : {}", salaryVoucherRelationDTO);
        if (salaryVoucherRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new salaryVoucherRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryVoucherRelationDTO result = salaryVoucherRelationService.save(salaryVoucherRelationDTO);
        return ResponseEntity.created(new URI("/api/salary-voucher-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salary-voucher-relations : Updates an existing salaryVoucherRelation.
     *
     * @param salaryVoucherRelationDTO the salaryVoucherRelationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryVoucherRelationDTO,
     * or with status 400 (Bad Request) if the salaryVoucherRelationDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryVoucherRelationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salary-voucher-relations")
    public ResponseEntity<SalaryVoucherRelationDTO> updateSalaryVoucherRelation(@RequestBody SalaryVoucherRelationDTO salaryVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to update SalaryVoucherRelation : {}", salaryVoucherRelationDTO);
        if (salaryVoucherRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryVoucherRelationDTO result = salaryVoucherRelationService.save(salaryVoucherRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryVoucherRelationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salary-voucher-relations : get all the salaryVoucherRelations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of salaryVoucherRelations in body
     */
    @GetMapping("/salary-voucher-relations")
    public ResponseEntity<List<SalaryVoucherRelationDTO>> getAllSalaryVoucherRelations(SalaryVoucherRelationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SalaryVoucherRelations by criteria: {}", criteria);
        Page<SalaryVoucherRelationDTO> page = salaryVoucherRelationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salary-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /salary-voucher-relations/count : count all the salaryVoucherRelations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/salary-voucher-relations/count")
    public ResponseEntity<Long> countSalaryVoucherRelations(SalaryVoucherRelationCriteria criteria) {
        log.debug("REST request to count SalaryVoucherRelations by criteria: {}", criteria);
        return ResponseEntity.ok().body(salaryVoucherRelationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /salary-voucher-relations/:id : get the "id" salaryVoucherRelation.
     *
     * @param id the id of the salaryVoucherRelationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salaryVoucherRelationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salary-voucher-relations/{id}")
    public ResponseEntity<SalaryVoucherRelationDTO> getSalaryVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to get SalaryVoucherRelation : {}", id);
        Optional<SalaryVoucherRelationDTO> salaryVoucherRelationDTO = salaryVoucherRelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaryVoucherRelationDTO);
    }

    /**
     * DELETE  /salary-voucher-relations/:id : delete the "id" salaryVoucherRelation.
     *
     * @param id the id of the salaryVoucherRelationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salary-voucher-relations/{id}")
    public ResponseEntity<Void> deleteSalaryVoucherRelation(@PathVariable Long id) {
        log.debug("REST request to delete SalaryVoucherRelation : {}", id);
        salaryVoucherRelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/salary-voucher-relations?query=:query : search for the salaryVoucherRelation corresponding
     * to the query.
     *
     * @param query the query of the salaryVoucherRelation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/salary-voucher-relations")
    public ResponseEntity<List<SalaryVoucherRelationDTO>> searchSalaryVoucherRelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SalaryVoucherRelations for query {}", query);
        Page<SalaryVoucherRelationDTO> page = salaryVoucherRelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/salary-voucher-relations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
