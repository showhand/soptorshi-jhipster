package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.PredefinedNarrationQueryService;
import org.soptorshi.service.PredefinedNarrationService;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.extended.PredefinedNarrationExtendedService;
import org.soptorshi.web.rest.PredefinedNarrationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class PredefinedNarrationExtendedResource {
    private final Logger log = LoggerFactory.getLogger(PredefinedNarrationExtendedResource.class);

    private static final String ENTITY_NAME = "predefinedNarration";

    private final PredefinedNarrationExtendedService predefinedNarrationService;

    private final PredefinedNarrationQueryService predefinedNarrationQueryService;

    public PredefinedNarrationExtendedResource(PredefinedNarrationExtendedService predefinedNarrationService, PredefinedNarrationQueryService predefinedNarrationQueryService) {
        this.predefinedNarrationService = predefinedNarrationService;
        this.predefinedNarrationQueryService = predefinedNarrationQueryService;
    }


    /**
     * POST  /predefined-narrations : Create a new predefinedNarration.
     *
     * @param predefinedNarrationDTO the predefinedNarrationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new predefinedNarrationDTO, or with status 400 (Bad Request) if the predefinedNarration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/predefined-narrations")
    public ResponseEntity<PredefinedNarrationDTO> createPredefinedNarration(@RequestBody PredefinedNarrationDTO predefinedNarrationDTO) throws URISyntaxException {
        log.debug("REST request to save PredefinedNarration : {}", predefinedNarrationDTO);
        if (predefinedNarrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new predefinedNarration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PredefinedNarrationDTO result = predefinedNarrationService.save(predefinedNarrationDTO);
        return ResponseEntity.created(new URI("/api/predefined-narrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /predefined-narrations : Updates an existing predefinedNarration.
     *
     * @param predefinedNarrationDTO the predefinedNarrationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated predefinedNarrationDTO,
     * or with status 400 (Bad Request) if the predefinedNarrationDTO is not valid,
     * or with status 500 (Internal Server Error) if the predefinedNarrationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/predefined-narrations")
    public ResponseEntity<PredefinedNarrationDTO> updatePredefinedNarration(@RequestBody PredefinedNarrationDTO predefinedNarrationDTO) throws URISyntaxException {
        log.debug("REST request to update PredefinedNarration : {}", predefinedNarrationDTO);
        if (predefinedNarrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PredefinedNarrationDTO result = predefinedNarrationService.save(predefinedNarrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, predefinedNarrationDTO.getId().toString()))
            .body(result);
    }

}
