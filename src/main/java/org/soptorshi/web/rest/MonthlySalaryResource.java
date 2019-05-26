package org.soptorshi.web.rest;
import org.soptorshi.service.MonthlySalaryService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.dto.MonthlySalaryCriteria;
import org.soptorshi.service.MonthlySalaryQueryService;
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
 * REST controller for managing MonthlySalary.
 */
@RestController
@RequestMapping("/api")
public class MonthlySalaryResource {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryResource.class);

    private static final String ENTITY_NAME = "monthlySalary";

    private final MonthlySalaryService monthlySalaryService;

    private final MonthlySalaryQueryService monthlySalaryQueryService;

    public MonthlySalaryResource(MonthlySalaryService monthlySalaryService, MonthlySalaryQueryService monthlySalaryQueryService) {
        this.monthlySalaryService = monthlySalaryService;
        this.monthlySalaryQueryService = monthlySalaryQueryService;
    }

    /**
     * POST  /monthly-salaries : Create a new monthlySalary.
     *
     * @param monthlySalaryDTO the monthlySalaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monthlySalaryDTO, or with status 400 (Bad Request) if the monthlySalary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monthly-salaries")
    public ResponseEntity<MonthlySalaryDTO> createMonthlySalary(@Valid @RequestBody MonthlySalaryDTO monthlySalaryDTO) throws URISyntaxException {
        log.debug("REST request to save MonthlySalary : {}", monthlySalaryDTO);
        if (monthlySalaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlySalary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlySalaryDTO result = monthlySalaryService.save(monthlySalaryDTO);
        return ResponseEntity.created(new URI("/api/monthly-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monthly-salaries : Updates an existing monthlySalary.
     *
     * @param monthlySalaryDTO the monthlySalaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monthlySalaryDTO,
     * or with status 400 (Bad Request) if the monthlySalaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the monthlySalaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monthly-salaries")
    public ResponseEntity<MonthlySalaryDTO> updateMonthlySalary(@Valid @RequestBody MonthlySalaryDTO monthlySalaryDTO) throws URISyntaxException {
        log.debug("REST request to update MonthlySalary : {}", monthlySalaryDTO);
        if (monthlySalaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MonthlySalaryDTO result = monthlySalaryService.save(monthlySalaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monthlySalaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monthly-salaries : get all the monthlySalaries.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of monthlySalaries in body
     */
    @GetMapping("/monthly-salaries")
    public ResponseEntity<List<MonthlySalaryDTO>> getAllMonthlySalaries(MonthlySalaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MonthlySalaries by criteria: {}", criteria);
        Page<MonthlySalaryDTO> page = monthlySalaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/monthly-salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /monthly-salaries/count : count all the monthlySalaries.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/monthly-salaries/count")
    public ResponseEntity<Long> countMonthlySalaries(MonthlySalaryCriteria criteria) {
        log.debug("REST request to count MonthlySalaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(monthlySalaryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /monthly-salaries/:id : get the "id" monthlySalary.
     *
     * @param id the id of the monthlySalaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monthlySalaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/monthly-salaries/{id}")
    public ResponseEntity<MonthlySalaryDTO> getMonthlySalary(@PathVariable Long id) {
        log.debug("REST request to get MonthlySalary : {}", id);
        Optional<MonthlySalaryDTO> monthlySalaryDTO = monthlySalaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlySalaryDTO);
    }

    /**
     * DELETE  /monthly-salaries/:id : delete the "id" monthlySalary.
     *
     * @param id the id of the monthlySalaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monthly-salaries/{id}")
    public ResponseEntity<Void> deleteMonthlySalary(@PathVariable Long id) {
        log.debug("REST request to delete MonthlySalary : {}", id);
        monthlySalaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/monthly-salaries?query=:query : search for the monthlySalary corresponding
     * to the query.
     *
     * @param query the query of the monthlySalary search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/monthly-salaries")
    public ResponseEntity<List<MonthlySalaryDTO>> searchMonthlySalaries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MonthlySalaries for query {}", query);
        Page<MonthlySalaryDTO> page = monthlySalaryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/monthly-salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
