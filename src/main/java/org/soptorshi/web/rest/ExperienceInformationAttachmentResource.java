package org.soptorshi.web.rest;
import org.soptorshi.service.ExperienceInformationAttachmentService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.service.dto.ExperienceInformationAttachmentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ExperienceInformationAttachment.
 */
@RestController
@RequestMapping("/api")
public class ExperienceInformationAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationAttachmentResource.class);

    private static final String ENTITY_NAME = "experienceInformationAttachment";

    private final ExperienceInformationAttachmentService experienceInformationAttachmentService;

    public ExperienceInformationAttachmentResource(ExperienceInformationAttachmentService experienceInformationAttachmentService) {
        this.experienceInformationAttachmentService = experienceInformationAttachmentService;
    }

    /**
     * POST  /experience-information-attachments : Create a new experienceInformationAttachment.
     *
     * @param experienceInformationAttachmentDTO the experienceInformationAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experienceInformationAttachmentDTO, or with status 400 (Bad Request) if the experienceInformationAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experience-information-attachments")
    public ResponseEntity<ExperienceInformationAttachmentDTO> createExperienceInformationAttachment(@Valid @RequestBody ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save ExperienceInformationAttachment : {}", experienceInformationAttachmentDTO);
        if (experienceInformationAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new experienceInformationAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceInformationAttachmentDTO result = experienceInformationAttachmentService.save(experienceInformationAttachmentDTO);
        return ResponseEntity.created(new URI("/api/experience-information-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experience-information-attachments : Updates an existing experienceInformationAttachment.
     *
     * @param experienceInformationAttachmentDTO the experienceInformationAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experienceInformationAttachmentDTO,
     * or with status 400 (Bad Request) if the experienceInformationAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the experienceInformationAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experience-information-attachments")
    public ResponseEntity<ExperienceInformationAttachmentDTO> updateExperienceInformationAttachment(@Valid @RequestBody ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update ExperienceInformationAttachment : {}", experienceInformationAttachmentDTO);
        if (experienceInformationAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperienceInformationAttachmentDTO result = experienceInformationAttachmentService.save(experienceInformationAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experienceInformationAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experience-information-attachments : get all the experienceInformationAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experienceInformationAttachments in body
     */
    @GetMapping("/experience-information-attachments")
    public List<ExperienceInformationAttachmentDTO> getAllExperienceInformationAttachments() {
        log.debug("REST request to get all ExperienceInformationAttachments");
        return experienceInformationAttachmentService.findAll();
    }

    /**
     * GET  /experience-information-attachments/:id : get the "id" experienceInformationAttachment.
     *
     * @param id the id of the experienceInformationAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experienceInformationAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/experience-information-attachments/{id}")
    public ResponseEntity<ExperienceInformationAttachmentDTO> getExperienceInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to get ExperienceInformationAttachment : {}", id);
        Optional<ExperienceInformationAttachmentDTO> experienceInformationAttachmentDTO = experienceInformationAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experienceInformationAttachmentDTO);
    }

    /**
     * DELETE  /experience-information-attachments/:id : delete the "id" experienceInformationAttachment.
     *
     * @param id the id of the experienceInformationAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experience-information-attachments/{id}")
    public ResponseEntity<Void> deleteExperienceInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to delete ExperienceInformationAttachment : {}", id);
        experienceInformationAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/experience-information-attachments?query=:query : search for the experienceInformationAttachment corresponding
     * to the query.
     *
     * @param query the query of the experienceInformationAttachment search
     * @return the result of the search
     */
    @GetMapping("/_search/experience-information-attachments")
    public List<ExperienceInformationAttachmentDTO> searchExperienceInformationAttachments(@RequestParam String query) {
        log.debug("REST request to search ExperienceInformationAttachments for query {}", query);
        return experienceInformationAttachmentService.search(query);
    }

}
