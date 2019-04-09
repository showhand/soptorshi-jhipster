package org.soptorshi.web.rest;
import org.soptorshi.service.ExperienceInformationService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ExperienceInformationDTO;
import org.soptorshi.service.dto.ExperienceInformationCriteria;
import org.soptorshi.service.ExperienceInformationQueryService;
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
 * REST controller for managing ExperienceInformation.
 */
@RestController
@RequestMapping("/api")
public class ExperienceInformationResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationResource.class);

    private static final String ENTITY_NAME = "experienceInformation";

    private final ExperienceInformationService experienceInformationService;

    private final ExperienceInformationQueryService experienceInformationQueryService;

    public ExperienceInformationResource(ExperienceInformationService experienceInformationService, ExperienceInformationQueryService experienceInformationQueryService) {
        this.experienceInformationService = experienceInformationService;
        this.experienceInformationQueryService = experienceInformationQueryService;
    }

    /**
     * POST  /experience-informations : Create a new experienceInformation.
     *
     * @param experienceInformationDTO the experienceInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experienceInformationDTO, or with status 400 (Bad Request) if the experienceInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experience-informations")
    public ResponseEntity<ExperienceInformationDTO> createExperienceInformation(@RequestBody ExperienceInformationDTO experienceInformationDTO) throws URISyntaxException {
        log.debug("REST request to save ExperienceInformation : {}", experienceInformationDTO);
        if (experienceInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new experienceInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceInformationDTO result = experienceInformationService.save(experienceInformationDTO);
        return ResponseEntity.created(new URI("/api/experience-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experience-informations : Updates an existing experienceInformation.
     *
     * @param experienceInformationDTO the experienceInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experienceInformationDTO,
     * or with status 400 (Bad Request) if the experienceInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the experienceInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experience-informations")
    public ResponseEntity<ExperienceInformationDTO> updateExperienceInformation(@RequestBody ExperienceInformationDTO experienceInformationDTO) throws URISyntaxException {
        log.debug("REST request to update ExperienceInformation : {}", experienceInformationDTO);
        if (experienceInformationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperienceInformationDTO result = experienceInformationService.save(experienceInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experienceInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experience-informations : get all the experienceInformations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of experienceInformations in body
     */
    @GetMapping("/experience-informations")
    public ResponseEntity<List<ExperienceInformationDTO>> getAllExperienceInformations(ExperienceInformationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExperienceInformations by criteria: {}", criteria);
        Page<ExperienceInformationDTO> page = experienceInformationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experience-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /experience-informations/count : count all the experienceInformations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/experience-informations/count")
    public ResponseEntity<Long> countExperienceInformations(ExperienceInformationCriteria criteria) {
        log.debug("REST request to count ExperienceInformations by criteria: {}", criteria);
        return ResponseEntity.ok().body(experienceInformationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /experience-informations/:id : get the "id" experienceInformation.
     *
     * @param id the id of the experienceInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experienceInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/experience-informations/{id}")
    public ResponseEntity<ExperienceInformationDTO> getExperienceInformation(@PathVariable Long id) {
        log.debug("REST request to get ExperienceInformation : {}", id);
        Optional<ExperienceInformationDTO> experienceInformationDTO = experienceInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experienceInformationDTO);
    }

    /**
     * DELETE  /experience-informations/:id : delete the "id" experienceInformation.
     *
     * @param id the id of the experienceInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experience-informations/{id}")
    public ResponseEntity<Void> deleteExperienceInformation(@PathVariable Long id) {
        log.debug("REST request to delete ExperienceInformation : {}", id);
        experienceInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/experience-informations?query=:query : search for the experienceInformation corresponding
     * to the query.
     *
     * @param query the query of the experienceInformation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/experience-informations")
    public ResponseEntity<List<ExperienceInformationDTO>> searchExperienceInformations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ExperienceInformations for query {}", query);
        Page<ExperienceInformationDTO> page = experienceInformationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/experience-informations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
