package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.DepreciationCalculationExtendedService;
import org.soptorshi.service.DepreciationCalculationQueryService;
import org.soptorshi.service.DepreciationCalculationService;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.web.rest.DepreciationCalculationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class DepreciationCalculationExtendedResource extends DepreciationCalculationResource {
    private final Logger log = LoggerFactory.getLogger(DepreciationCalculationResource.class);

    private static final String ENTITY_NAME = "depreciationCalculation";

    private final DepreciationCalculationExtendedService depreciationCalculationService;

    private final DepreciationCalculationQueryService depreciationCalculationQueryService;

    public DepreciationCalculationExtendedResource(DepreciationCalculationService depreciationCalculationService, DepreciationCalculationQueryService depreciationCalculationQueryService, DepreciationCalculationExtendedService depreciationCalculationService1, DepreciationCalculationQueryService depreciationCalculationQueryService1) {
        super(depreciationCalculationService, depreciationCalculationQueryService);
        this.depreciationCalculationService = depreciationCalculationService1;
        this.depreciationCalculationQueryService = depreciationCalculationQueryService1;
    }

    @PostMapping("/depreciation-calculations")
    public ResponseEntity<DepreciationCalculationDTO> createDepreciationCalculation(@RequestBody DepreciationCalculationDTO depreciationCalculationDTO) throws URISyntaxException {
        log.debug("REST request to save DepreciationCalculation : {}", depreciationCalculationDTO);
        if (depreciationCalculationDTO.getId() != null) {
            throw new BadRequestAlertException("A new depreciationCalculation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepreciationCalculationDTO result = depreciationCalculationService.save(depreciationCalculationDTO);
        return ResponseEntity.created(new URI("/api/depreciation-calculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depreciation-calculations : Updates an existing depreciationCalculation.
     *
     * @param depreciationCalculationDTO the depreciationCalculationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depreciationCalculationDTO,
     * or with status 400 (Bad Request) if the depreciationCalculationDTO is not valid,
     * or with status 500 (Internal Server Error) if the depreciationCalculationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depreciation-calculations")
    public ResponseEntity<DepreciationCalculationDTO> updateDepreciationCalculation(@RequestBody DepreciationCalculationDTO depreciationCalculationDTO) throws URISyntaxException {
        log.debug("REST request to update DepreciationCalculation : {}", depreciationCalculationDTO);
        if (depreciationCalculationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepreciationCalculationDTO result = depreciationCalculationService.save(depreciationCalculationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depreciationCalculationDTO.getId().toString()))
            .body(result);
    }
}
