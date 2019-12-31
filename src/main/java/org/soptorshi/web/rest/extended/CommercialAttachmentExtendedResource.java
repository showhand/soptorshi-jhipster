package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialAttachmentQueryService;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
import org.soptorshi.service.extended.CommercialAttachmentExtendedService;
import org.soptorshi.web.rest.CommercialAttachmentResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialAttachment.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialAttachmentExtendedResource extends CommercialAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentExtendedResource.class);

    private static final String ENTITY_NAME = "commercialAttachment";

    public CommercialAttachmentExtendedResource(CommercialAttachmentExtendedService commercialAttachmentService, CommercialAttachmentQueryService commercialAttachmentQueryService) {
       super(commercialAttachmentService, commercialAttachmentQueryService);
    }

    /**
     * PUT  /commercial-attachments : Updates an existing commercialAttachment.
     *
     * @param commercialAttachmentDTO the commercialAttachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commercialAttachmentDTO,
     * or with status 400 (Bad Request) if the commercialAttachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the commercialAttachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commercial-attachments")
    public ResponseEntity<CommercialAttachmentDTO> updateCommercialAttachment(@RequestBody CommercialAttachmentDTO commercialAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialAttachment : {}", commercialAttachmentDTO);
        throw new BadRequestAlertException("Update operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /commercial-attachments/:id : delete the "id" commercialAttachment.
     *
     * @param id the id of the commercialAttachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-attachments/{id}")
    public ResponseEntity<Void> deleteCommercialAttachment(@PathVariable Long id) {
        log.debug("REST request to delete CommercialAttachment : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
