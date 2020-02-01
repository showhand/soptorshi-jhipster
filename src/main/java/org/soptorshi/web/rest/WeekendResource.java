package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.WeekendQueryService;
import org.soptorshi.service.WeekendService;
import org.soptorshi.service.dto.WeekendCriteria;
import org.soptorshi.service.dto.WeekendDTO;
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
 * REST controller for managing Weekend.
 */
@RestController
@RequestMapping("/api")
public class WeekendResource {

    private final Logger log = LoggerFactory.getLogger(WeekendResource.class);

    private static final String ENTITY_NAME = "weekend";

    private final WeekendService weekendService;

    private final WeekendQueryService weekendQueryService;

    public WeekendResource(WeekendService weekendService, WeekendQueryService weekendQueryService) {
        this.weekendService = weekendService;
        this.weekendQueryService = weekendQueryService;
    }

    /**
     * POST  /weekends : Create a new weekend.
     *
     * @param weekendDTO the weekendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new weekendDTO, or with status 400 (Bad Request) if the weekend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weekends")
    public ResponseEntity<WeekendDTO> createWeekend(@Valid @RequestBody WeekendDTO weekendDTO) throws URISyntaxException {
        log.debug("REST request to save Weekend : {}", weekendDTO);
        if (weekendDTO.getId() != null) {
            throw new BadRequestAlertException("A new weekend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeekendDTO result = weekendService.save(weekendDTO);
        return ResponseEntity.created(new URI("/api/weekends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weekends : Updates an existing weekend.
     *
     * @param weekendDTO the weekendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated weekendDTO,
     * or with status 400 (Bad Request) if the weekendDTO is not valid,
     * or with status 500 (Internal Server Error) if the weekendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weekends")
    public ResponseEntity<WeekendDTO> updateWeekend(@Valid @RequestBody WeekendDTO weekendDTO) throws URISyntaxException {
        log.debug("REST request to update Weekend : {}", weekendDTO);
        if (weekendDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeekendDTO result = weekendService.save(weekendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, weekendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weekends : get all the weekends.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of weekends in body
     */
    @GetMapping("/weekends")
    public ResponseEntity<List<WeekendDTO>> getAllWeekends(WeekendCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Weekends by criteria: {}", criteria);
        Page<WeekendDTO> page = weekendQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/weekends");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /weekends/count : count all the weekends.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/weekends/count")
    public ResponseEntity<Long> countWeekends(WeekendCriteria criteria) {
        log.debug("REST request to count Weekends by criteria: {}", criteria);
        return ResponseEntity.ok().body(weekendQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /weekends/:id : get the "id" weekend.
     *
     * @param id the id of the weekendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the weekendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/weekends/{id}")
    public ResponseEntity<WeekendDTO> getWeekend(@PathVariable Long id) {
        log.debug("REST request to get Weekend : {}", id);
        Optional<WeekendDTO> weekendDTO = weekendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weekendDTO);
    }

    /**
     * DELETE  /weekends/:id : delete the "id" weekend.
     *
     * @param id the id of the weekendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weekends/{id}")
    public ResponseEntity<Void> deleteWeekend(@PathVariable Long id) {
        log.debug("REST request to delete Weekend : {}", id);
        weekendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/weekends?query=:query : search for the weekend corresponding
     * to the query.
     *
     * @param query the query of the weekend search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/weekends")
    public ResponseEntity<List<WeekendDTO>> searchWeekends(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Weekends for query {}", query);
        Page<WeekendDTO> page = weekendService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/weekends");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
