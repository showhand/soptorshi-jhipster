package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.HolidayQueryService;
import org.soptorshi.service.HolidayService;
import org.soptorshi.service.dto.HolidayCriteria;
import org.soptorshi.service.dto.HolidayDTO;
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
 * REST controller for managing Holiday.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private static final String ENTITY_NAME = "holiday";

    private final HolidayService holidayService;

    private final HolidayQueryService holidayQueryService;

    public HolidayResource(HolidayService holidayService, HolidayQueryService holidayQueryService) {
        this.holidayService = holidayService;
        this.holidayQueryService = holidayQueryService;
    }

    /**
     * POST  /holidays : Create a new holiday.
     *
     * @param holidayDTO the holidayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new holidayDTO, or with status 400 (Bad Request) if the holiday has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/holidays")
    public ResponseEntity<HolidayDTO> createHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holidayDTO);
        if (holidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayDTO result = holidayService.save(holidayDTO);
        return ResponseEntity.created(new URI("/api/holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /holidays : Updates an existing holiday.
     *
     * @param holidayDTO the holidayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated holidayDTO,
     * or with status 400 (Bad Request) if the holidayDTO is not valid,
     * or with status 500 (Internal Server Error) if the holidayDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/holidays")
    public ResponseEntity<HolidayDTO> updateHoliday(@Valid @RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}", holidayDTO);
        if (holidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayDTO result = holidayService.save(holidayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, holidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /holidays : get all the holidays.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of holidays in body
     */
    @GetMapping("/holidays")
    public ResponseEntity<List<HolidayDTO>> getAllHolidays(HolidayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Holidays by criteria: {}", criteria);
        Page<HolidayDTO> page = holidayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/holidays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /holidays/count : count all the holidays.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/holidays/count")
    public ResponseEntity<Long> countHolidays(HolidayCriteria criteria) {
        log.debug("REST request to count Holidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidayQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /holidays/:id : get the "id" holiday.
     *
     * @param id the id of the holidayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the holidayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/holidays/{id}")
    public ResponseEntity<HolidayDTO> getHoliday(@PathVariable Long id) {
        log.debug("REST request to get Holiday : {}", id);
        Optional<HolidayDTO> holidayDTO = holidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidayDTO);
    }

    /**
     * DELETE  /holidays/:id : delete the "id" holiday.
     *
     * @param id the id of the holidayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/holidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/holidays?query=:query : search for the holiday corresponding
     * to the query.
     *
     * @param query the query of the holiday search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/holidays")
    public ResponseEntity<List<HolidayDTO>> searchHolidays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Holidays for query {}", query);
        Page<HolidayDTO> page = holidayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/holidays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
