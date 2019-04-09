package org.soptorshi.web.rest;
import org.soptorshi.service.TrainingInformationAttachmentService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.service.dto.TrainingInformationAttachmentDTO;
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
 * REST controller for managing TrainingInformationAttachment.
 */
@RestController
@RequestMapping("/api")
public class TrainingInformationAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationAttachmentResource.class);

    private static final String ENTITY_NAME = "trainingInformationAttachment";

    private final TrainingInformationAttachmentService trainingInformationAttachmentService;

    public TrainingInformationAttachmentResource(TrainingInformationAttachmentService trainingInformationAttachmentService) {
        this.trainingInformationAttachmentService = trainingInformationAttachmentService;
    }

    /**
     * POST  /training-information-attachments : Create a new trainingInformationAttachment.
     *
     * @param trainingInformationAttachmentDTO the trainingInformationAttachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainingInformationAttachmentDTO, or with status 400 (Bad Request) if the trainingInformationAttachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/training-information-attachments")
    public ResponseEntity<TrainingInformationAttachmentDTO> createTrainingInformationAttachment(@Valid @RequestBody TrainingInformationAttachmentDTO trainingInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save TrainingInformationAttachment : {}", trainingInformationAttachmentDTO);
        if (trainingInformationAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingInformationAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingInformationAttachmentDTO result = trainingInformationAttachmentService.save(trainingInformationAttachmentDTO);
        return ResponseEntity.created(new URI("/api/training-information-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /training-information-attachments : Updates an existing trainingInformationAttachment.
     *
     * @param trainingInformationAttachmentDTO the trainingInformationAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainingInformationAttachmentDTO,
     * or with status 400 (Bad Request) if the trainingInformationAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the trainingInformationAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/training-information-attachments")
    public ResponseEntity<TrainingInformationAttachmentDTO> updateTrainingInformationAttachment(@Valid @RequestBody TrainingInformationAttachmentDTO trainingInformationAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update TrainingInformationAttachment : {}", trainingInformationAttachmentDTO);
        if (trainingInformationAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingInformationAttachmentDTO result = trainingInformationAttachmentService.save(trainingInformationAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainingInformationAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /training-information-attachments : get all the trainingInformationAttachments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trainingInformationAttachments in body
     */
    @GetMapping("/training-information-attachments")
    public List<TrainingInformationAttachmentDTO> getAllTrainingInformationAttachments() {
        log.debug("REST request to get all TrainingInformationAttachments");
        return trainingInformationAttachmentService.findAll();
    }

    /**
     * GET  /training-information-attachments/:id : get the "id" trainingInformationAttachment.
     *
     * @param id the id of the trainingInformationAttachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainingInformationAttachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/training-information-attachments/{id}")
    public ResponseEntity<TrainingInformationAttachmentDTO> getTrainingInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to get TrainingInformationAttachment : {}", id);
        Optional<TrainingInformationAttachmentDTO> trainingInformationAttachmentDTO = trainingInformationAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingInformationAttachmentDTO);
    }

    /**
     * DELETE  /training-information-attachments/:id : delete the "id" trainingInformationAttachment.
     *
     * @param id the id of the trainingInformationAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/training-information-attachments/{id}")
    public ResponseEntity<Void> deleteTrainingInformationAttachment(@PathVariable Long id) {
        log.debug("REST request to delete TrainingInformationAttachment : {}", id);
        trainingInformationAttachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/training-information-attachments?query=:query : search for the trainingInformationAttachment corresponding
     * to the query.
     *
     * @param query the query of the trainingInformationAttachment search
     * @return the result of the search
     */
    @GetMapping("/_search/training-information-attachments")
    public List<TrainingInformationAttachmentDTO> searchTrainingInformationAttachments(@RequestParam String query) {
        log.debug("REST request to search TrainingInformationAttachments for query {}", query);
        return trainingInformationAttachmentService.search(query);
    }

}
