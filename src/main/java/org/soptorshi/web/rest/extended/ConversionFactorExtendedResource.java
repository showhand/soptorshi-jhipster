package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ConversionFactorQueryService;
import org.soptorshi.service.ConversionFactorService;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.extended.ConversionFactorExtendedService;
import org.soptorshi.web.rest.ConversionFactorResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class ConversionFactorExtendedResource {
    private final Logger log = LoggerFactory.getLogger(ConversionFactorResource.class);

    private static final String ENTITY_NAME = "conversionFactor";

    private final ConversionFactorExtendedService conversionFactorService;

    private final ConversionFactorQueryService conversionFactorQueryService;

    public ConversionFactorExtendedResource(ConversionFactorExtendedService conversionFactorService, ConversionFactorQueryService conversionFactorQueryService) {
        this.conversionFactorService = conversionFactorService;
        this.conversionFactorQueryService = conversionFactorQueryService;
    }

    @PostMapping("/conversion-factors")
    public ResponseEntity<ConversionFactorDTO> createConversionFactor(@RequestBody ConversionFactorDTO conversionFactorDTO) throws URISyntaxException {
        log.debug("REST request to save ConversionFactor : {}", conversionFactorDTO);
        if (conversionFactorDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversionFactor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversionFactorDTO result = conversionFactorService.save(conversionFactorDTO);
        return ResponseEntity.created(new URI("/api/extended/conversion-factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversion-factors : Updates an existing conversionFactor.
     *
     * @param conversionFactorDTO the conversionFactorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversionFactorDTO,
     * or with status 400 (Bad Request) if the conversionFactorDTO is not valid,
     * or with status 500 (Internal Server Error) if the conversionFactorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversion-factors")
    public ResponseEntity<ConversionFactorDTO> updateConversionFactor(@RequestBody ConversionFactorDTO conversionFactorDTO) throws URISyntaxException {
        log.debug("REST request to update ConversionFactor : {}", conversionFactorDTO);
        if (conversionFactorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConversionFactorDTO result = conversionFactorService.save(conversionFactorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conversionFactorDTO.getId().toString()))
            .body(result);
    }
}
