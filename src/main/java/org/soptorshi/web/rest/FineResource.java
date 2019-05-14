package org.soptorshi.web.rest;
import org.soptorshi.service.FineService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.FineDTO;
import org.soptorshi.service.dto.FineCriteria;
import org.soptorshi.service.FineQueryService;
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
 * REST controller for managing Fine.
 */
@RestController
@RequestMapping("/api")
public class FineResource {

    private final Logger log = LoggerFactory.getLogger(FineResource.class);

    private static final String ENTITY_NAME = "fine";

    private final FineService fineService;

    private final FineQueryService fineQueryService;

    public FineResource(FineService fineService, FineQueryService fineQueryService) {
        this.fineService = fineService;
        this.fineQueryService = fineQueryService;
    }

    /**
     * POST  /fines : Create a new fine.
     *
     * @param fineDTO the fineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fineDTO, or with status 400 (Bad Request) if the fine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fines")
    public ResponseEntity<FineDTO> createFine(@Valid @RequestBody FineDTO fineDTO) throws URISyntaxException {
        log.debug("REST request to save Fine : {}", fineDTO);
        if (fineDTO.getId() != null) {
            throw new BadRequestAlertException("A new fine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FineDTO result = fineService.save(fineDTO);
        return ResponseEntity.created(new URI("/api/fines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fines : Updates an existing fine.
     *
     * @param fineDTO the fineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fineDTO,
     * or with status 400 (Bad Request) if the fineDTO is not valid,
     * or with status 500 (Internal Server Error) if the fineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fines")
    public ResponseEntity<FineDTO> updateFine(@Valid @RequestBody FineDTO fineDTO) throws URISyntaxException {
        log.debug("REST request to update Fine : {}", fineDTO);
        if (fineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FineDTO result = fineService.save(fineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fines : get all the fines.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of fines in body
     */
    @GetMapping("/fines")
    public ResponseEntity<List<FineDTO>> getAllFines(FineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fines by criteria: {}", criteria);
        Page<FineDTO> page = fineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /fines/count : count all the fines.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/fines/count")
    public ResponseEntity<Long> countFines(FineCriteria criteria) {
        log.debug("REST request to count Fines by criteria: {}", criteria);
        return ResponseEntity.ok().body(fineQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /fines/:id : get the "id" fine.
     *
     * @param id the id of the fineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fines/{id}")
    public ResponseEntity<FineDTO> getFine(@PathVariable Long id) {
        log.debug("REST request to get Fine : {}", id);
        Optional<FineDTO> fineDTO = fineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fineDTO);
    }

    /**
     * DELETE  /fines/:id : delete the "id" fine.
     *
     * @param id the id of the fineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fines/{id}")
    public ResponseEntity<Void> deleteFine(@PathVariable Long id) {
        log.debug("REST request to delete Fine : {}", id);
        fineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fines?query=:query : search for the fine corresponding
     * to the query.
     *
     * @param query the query of the fine search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fines")
    public ResponseEntity<List<FineDTO>> searchFines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fines for query {}", query);
        Page<FineDTO> page = fineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
