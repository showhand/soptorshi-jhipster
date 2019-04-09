package org.soptorshi.web.rest;
import org.soptorshi.service.AcademicInformationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.AcademicInformationDTO;
import org.soptorshi.service.dto.AcademicInformationCriteria;
import org.soptorshi.service.AcademicInformationQueryService;
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
 * REST controller for managing AcademicInformation.
 */
@RestController
@RequestMapping("/api")
public class AcademicInformationResource {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationResource.class);

    private static final String ENTITY_NAME = "academicInformation";

    private final AcademicInformationService academicInformationService;

    private final AcademicInformationQueryService academicInformationQueryService;

    public AcademicInformationResource(AcademicInformationService academicInformationService, AcademicInformationQueryService academicInformationQueryService) {
        this.academicInformationService = academicInformationService;
        this.academicInformationQueryService = academicInformationQueryService;
    }

    /**
     * POST  /academic-informations : Create a new academicInformation.
     *
     * @param academicInformationDTO the academicInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new academicInformationDTO, or with status 400 (Bad Request) if the academicInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/academic-informations")
    public ResponseEntity<AcademicInformationDTO> createAcademicInformation(@RequestBody AcademicInformationDTO academicInformationDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicInformation : {}", academicInformationDTO);
        if (academicInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicInformationDTO result = academicInformationService.save(academicInformationDTO);
        return ResponseEntity.created(new URI("/api/academic-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /academic-informations : Updates an existing academicInformation.
     *
     * @param academicInformationDTO the academicInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated academicInformationDTO,
     * or with status 400 (Bad Request) if the academicInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the academicInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/academic-informations")
    public ResponseEntity<AcademicInformationDTO> updateAcademicInformation(@RequestBody AcademicInformationDTO academicInformationDTO) throws URISyntaxException {
        log.debug("REST request to update AcademicInformation : {}", academicInformationDTO);
        if (academicInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicInformationDTO result = academicInformationService.save(academicInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, academicInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /academic-informations : get all the academicInformations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of academicInformations in body
     */
    @GetMapping("/academic-informations")
    public ResponseEntity<List<AcademicInformationDTO>> getAllAcademicInformations(AcademicInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AcademicInformations by criteria: {}", criteria);
        Page<AcademicInformationDTO> page = academicInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/academic-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /academic-informations/count : count all the academicInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/academic-informations/count")
    public ResponseEntity<Long> countAcademicInformations(AcademicInformationCriteria criteria) {
        log.debug("REST request to count AcademicInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(academicInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /academic-informations/:id : get the "id" academicInformation.
     *
     * @param id the id of the academicInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the academicInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/academic-informations/{id}")
    public ResponseEntity<AcademicInformationDTO> getAcademicInformation(@PathVariable Long id) {
        log.debug("REST request to get AcademicInformation : {}", id);
        Optional<AcademicInformationDTO> academicInformationDTO = academicInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicInformationDTO);
    }

    /**
     * DELETE  /academic-informations/:id : delete the "id" academicInformation.
     *
     * @param id the id of the academicInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/academic-informations/{id}")
    public ResponseEntity<Void> deleteAcademicInformation(@PathVariable Long id) {
        log.debug("REST request to delete AcademicInformation : {}", id);
        academicInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/academic-informations?query=:query : search for the academicInformation corresponding
     * to the query.
     *
     * @param query the query of the academicInformation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/academic-informations")
    public ResponseEntity<List<AcademicInformationDTO>> searchAcademicInformations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AcademicInformations for query {}", query);
        Page<AcademicInformationDTO> page = academicInformationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/academic-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
