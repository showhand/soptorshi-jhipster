package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.PeriodCloseQueryService;
import org.soptorshi.service.PeriodCloseService;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.extended.PeriodCloseExtendedService;
import org.soptorshi.web.rest.PeriodCloseResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class PeriodCloseExtendedResource {
    private final Logger log = LoggerFactory.getLogger(PeriodCloseExtendedResource.class);

    private static final String ENTITY_NAME = "periodClose";

    private final PeriodCloseExtendedService periodCloseService;

    private final PeriodCloseQueryService periodCloseQueryService;

    public PeriodCloseExtendedResource(PeriodCloseExtendedService periodCloseService, PeriodCloseQueryService periodCloseQueryService) {
        this.periodCloseService = periodCloseService;
        this.periodCloseQueryService = periodCloseQueryService;
    }

    /**
     * POST  /period-closes : Create a new periodClose.
     *
     * @param periodCloseDTO the periodCloseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodCloseDTO, or with status 400 (Bad Request) if the periodClose has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/period-closes")
    public ResponseEntity<PeriodCloseDTO> createPeriodClose(@RequestBody PeriodCloseDTO periodCloseDTO) throws URISyntaxException {
        log.debug("REST request to save PeriodClose : {}", periodCloseDTO);
        if (periodCloseDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodClose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodCloseDTO result = periodCloseService.save(periodCloseDTO);
        return ResponseEntity.created(new URI("/api/period-closes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /period-closes : Updates an existing periodClose.
     *
     * @param periodCloseDTO the periodCloseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodCloseDTO,
     * or with status 400 (Bad Request) if the periodCloseDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodCloseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/period-closes")
    public ResponseEntity<PeriodCloseDTO> updatePeriodClose(@RequestBody PeriodCloseDTO periodCloseDTO) throws URISyntaxException {
        log.debug("REST request to update PeriodClose : {}", periodCloseDTO);
        if (periodCloseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodCloseDTO result = periodCloseService.save(periodCloseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodCloseDTO.getId().toString()))
            .body(result);
    }
}
