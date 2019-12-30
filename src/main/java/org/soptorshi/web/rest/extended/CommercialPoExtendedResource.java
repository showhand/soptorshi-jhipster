package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialPoQueryService;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.extended.CommercialPoExtendedService;
import org.soptorshi.web.rest.CommercialPoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialPo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPoExtendedResource extends CommercialPoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPo";

    public CommercialPoExtendedResource(CommercialPoExtendedService commercialPoService, CommercialPoQueryService commercialPoQueryService) {
        super(commercialPoService, commercialPoQueryService);
    }

    /**
     * POST  /commercial-pos : Create a new commercialPo.
     *
     * @param commercialPoDTO the commercialPoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commercialPoDTO, or with status 400 (Bad Request) if the commercialPo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commercial-pos")
    public ResponseEntity<CommercialPoDTO> createCommercialPo(@Valid @RequestBody CommercialPoDTO commercialPoDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialPo : {}", commercialPoDTO);
        throw new BadRequestAlertException("Post operation is not allowed", ENTITY_NAME, "idnull");
    }

    /**
     * DELETE  /commercial-pos/:id : delete the "id" commercialPo.
     *
     * @param id the id of the commercialPoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-pos/{id}")
    public ResponseEntity<Void> deleteCommercialPo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPo : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
