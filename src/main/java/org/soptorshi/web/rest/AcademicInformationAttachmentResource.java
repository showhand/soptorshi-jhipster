package org.soptorshi.web.rest;
import org.soptorshi.service.AcademicInformationAttachmentService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.service.dto.AcademicInformationAttachmentDTO;
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
 * REST controller for managing AcademicInformationAttachment.
 */
@RestController
@RequestMapping("/api")
public class AcademicInformationAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationAttachmentResource.class);

    private static final String ENTITY_NAME = "academicInformationAttachment";

    private final AcademicInformationAttachmentService academicInformationAttachmentService;

    public AcademicInformationAttachmentResource(AcademicInformationAttachmentService academicInformationAttachmentService) {
        this.academicInformationAttachmentService = academicInformationAttachmentService;
    }

    /**
     * POST  /academic-information-attachments : Create a new academicInformationAttachment.
     *
     * @param academicInformationAttachmentDTO the academicInformationAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new academicInformationAttachmentDTO, or with status 400 (Bad Request) if the academicInformationAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/academic-information-attachments")
    public ResponseEntity<AcademicInformationAttachmentDTO> createAcademicInformationAttachment(@Valid @RequestBody AcademicInformationAttachmentDTO academicInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicInformationAttachment : {}", academicInformationAttachmentDTO);
        if (academicInformationAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicInformationAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicInformationAttachmentDTO result = academicInformationAttachmentService.save(academicInformationAttachmentDTO);
        return ResponseEntity.created(new URI("/api/academic-information-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /academic-information-attachments : Updates an existing academicInformationAttachment.
     *
     * @param academicInformationAttachmentDTO the academicInformationAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated academicInformationAttachmentDTO,
     * or with status 400 (Bad Request) if the academicInformationAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the academicInformationAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/academic-information-attachments")
    public ResponseEntity<AcademicInformationAttachmentDTO> updateAcademicInformationAttachment(@Valid @RequestBody AcademicInformationAttachmentDTO academicInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update AcademicInformationAttachment : {}", academicInformationAttachmentDTO);
        if (academicInformationAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicInformationAttachmentDTO result = academicInformationAttachmentService.save(academicInformationAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, academicInformationAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /academic-information-attachments : get all the academicInformationAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of academicInformationAttachments in body
     */
    @GetMapping("/academic-information-attachments")
    public List<AcademicInformationAttachmentDTO> getAllAcademicInformationAttachments() {
        log.debug("REST request to get all AcademicInformationAttachments");
        return academicInformationAttachmentService.findAll();
    }

    /**
     * GET  /academic-information-attachments/:id : get the "id" academicInformationAttachment.
     *
     * @param id the id of the academicInformationAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the academicInformationAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/academic-information-attachments/{id}")
    public ResponseEntity<AcademicInformationAttachmentDTO> getAcademicInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to get AcademicInformationAttachment : {}", id);
        Optional<AcademicInformationAttachmentDTO> academicInformationAttachmentDTO = academicInformationAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicInformationAttachmentDTO);
    }

    /**
     * DELETE  /academic-information-attachments/:id : delete the "id" academicInformationAttachment.
     *
     * @param id the id of the academicInformationAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/academic-information-attachments/{id}")
    public ResponseEntity<Void> deleteAcademicInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to delete AcademicInformationAttachment : {}", id);
        academicInformationAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/academic-information-attachments?query=:query : search for the academicInformationAttachment corresponding
     * to the query.
     *
     * @param query the query of the academicInformationAttachment search
     * @return the result of the search
     */
    @GetMapping("/_search/academic-information-attachments")
    public List<AcademicInformationAttachmentDTO> searchAcademicInformationAttachments(@RequestParam String query) {
        log.debug("REST request to search AcademicInformationAttachments for query {}", query);
        return academicInformationAttachmentService.search(query);
    }

}
