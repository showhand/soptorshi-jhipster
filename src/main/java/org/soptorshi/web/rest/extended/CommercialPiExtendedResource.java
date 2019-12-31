package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPiQueryService;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.extended.CommercialPiExtendedService;
import org.soptorshi.web.rest.CommercialPiResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialPi.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPiExtendedResource extends CommercialPiResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPiExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPi";

    public CommercialPiExtendedResource(CommercialPiExtendedService commercialPiService, CommercialPiQueryService commercialPiQueryService) {
        super(commercialPiService, commercialPiQueryService);
    }

    /**
     * POST  /commercial-pis : Create a new commercialPi.
     *
     * @param commercialPiDTO the commercialPiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPiDTO, or with status 400 (Bad Request) if the commercialPi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-pis")
    public ResponseEntity<CommercialPiDTO> createCommercialPi(@Valid @RequestBody CommercialPiDTO commercialPiDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPi : {}", commercialPiDTO);
        throw new BadRequestAlertException("Post operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /commercial-pis/:id : delete the "id" commercialPi.
     *
     * @param id the id of the commercialPiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-pis/{id}")
    public ResponseEntity<Void> deleteCommercialPi(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPi : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
