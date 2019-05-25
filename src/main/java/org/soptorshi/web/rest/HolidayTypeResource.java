package org.soptorshi.web.rest;
import org.soptorshi.service.HolidayTypeService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.dto.HolidayTypeCriteria;
import org.soptorshi.service.HolidayTypeQueryService;
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
 * REST controller for managing HolidayType.
 */
@RestController
@RequestMapping("/api")
public class HolidayTypeResource {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeResource.class);

    private static final String ENTITY_NAME = "holidayType";

    private final HolidayTypeService holidayTypeService;

    private final HolidayTypeQueryService holidayTypeQueryService;

    public HolidayTypeResource(HolidayTypeService holidayTypeService, HolidayTypeQueryService holidayTypeQueryService) {
        this.holidayTypeService = holidayTypeService;
        this.holidayTypeQueryService = holidayTypeQueryService;
    }

    /**
     * POST  /holiday-types : Create a new holidayType.
     *
     * @param holidayTypeDTO the holidayTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new holidayTypeDTO, or with status 400 (Bad Request) if the holidayType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/holiday-types")
    public ResponseEntity<HolidayTypeDTO> createHolidayType(@Valid @RequestBody HolidayTypeDTO holidayTypeDTO) throws URISyntaxException {
        log.debug("REST request to save HolidayType : {}", holidayTypeDTO);
        if (holidayTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new holidayType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayTypeDTO result = holidayTypeService.save(holidayTypeDTO);
        return ResponseEntity.created(new URI("/api/holiday-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /holiday-types : Updates an existing holidayType.
     *
     * @param holidayTypeDTO the holidayTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated holidayTypeDTO,
     * or with status 400 (Bad Request) if the holidayTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the holidayTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/holiday-types")
    public ResponseEntity<HolidayTypeDTO> updateHolidayType(@Valid @RequestBody HolidayTypeDTO holidayTypeDTO) throws URISyntaxException {
        log.debug("REST request to update HolidayType : {}", holidayTypeDTO);
        if (holidayTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HolidayTypeDTO result = holidayTypeService.save(holidayTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, holidayTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /holiday-types : get all the holidayTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of holidayTypes in body
     */
    @GetMapping("/holiday-types")
    public ResponseEntity<List<HolidayTypeDTO>> getAllHolidayTypes(HolidayTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get HolidayTypes by criteria: {}", criteria);
        Page<HolidayTypeDTO> page = holidayTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/holiday-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /holiday-types/count : count all the holidayTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/holiday-types/count")
    public ResponseEntity<Long> countHolidayTypes(HolidayTypeCriteria criteria) {
        log.debug("REST request to count HolidayTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidayTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /holiday-types/:id : get the "id" holidayType.
     *
     * @param id the id of the holidayTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the holidayTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/holiday-types/{id}")
    public ResponseEntity<HolidayTypeDTO> getHolidayType(@PathVariable Long id) {
        log.debug("REST request to get HolidayType : {}", id);
        Optional<HolidayTypeDTO> holidayTypeDTO = holidayTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidayTypeDTO);
    }

    /**
     * DELETE  /holiday-types/:id : delete the "id" holidayType.
     *
     * @param id the id of the holidayTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/holiday-types/{id}")
    public ResponseEntity<Void> deleteHolidayType(@PathVariable Long id) {
        log.debug("REST request to delete HolidayType : {}", id);
        holidayTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/holiday-types?query=:query : search for the holidayType corresponding
     * to the query.
     *
     * @param query the query of the holidayType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/holiday-types")
    public ResponseEntity<List<HolidayTypeDTO>> searchHolidayTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HolidayTypes for query {}", query);
        Page<HolidayTypeDTO> page = holidayTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/holiday-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
