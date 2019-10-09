package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.FinancialAccountYearQueryService;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.extended.FinancialAccountYearExtendedService;
import org.soptorshi.web.rest.FinancialAccountYearResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.SoptorshiHeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class FinancialAccountYearExtendedResource {
    private final Logger log = LoggerFactory.getLogger(FinancialAccountYearExtendedResource.class);

    private static final String ENTITY_NAME = "financialAccountYear";

    private final FinancialAccountYearExtendedService financialAccountYearExtendedService;

    private final FinancialAccountYearQueryService financialAccountYearQueryService;

    public FinancialAccountYearExtendedResource(FinancialAccountYearExtendedService financialAccountYearExtendedService, FinancialAccountYearQueryService financialAccountYearQueryService) {
        this.financialAccountYearExtendedService = financialAccountYearExtendedService;
        this.financialAccountYearQueryService = financialAccountYearQueryService;
    }

    /**
     * POST  /financial-account-years : Create a new financialAccountYear.
     *
     * @param financialAccountYearDTO the financialAccountYearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialAccountYearDTO, or with status 400 (Bad Request) if the financialAccountYear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-account-years")
    public ResponseEntity<FinancialAccountYearDTO> createFinancialAccountYear(@Valid @RequestBody FinancialAccountYearDTO financialAccountYearDTO) throws URISyntaxException {
        log.debug("REST request to save FinancialAccountYear in extended : {}", financialAccountYearDTO);
        if (financialAccountYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new financialAccountYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialAccountYearDTO result = financialAccountYearExtendedService.save(financialAccountYearDTO);
        return ResponseEntity.created(new URI("/api/financial-account-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-account-years : Updates an existing financialAccountYear.
     *
     * @param financialAccountYearDTO the financialAccountYearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialAccountYearDTO,
     * or with status 400 (Bad Request) if the financialAccountYearDTO is not valid,
     * or with status 500 (Internal Server Error) if the financialAccountYearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-account-years")
    public ResponseEntity<FinancialAccountYearDTO> updateFinancialAccountYear(@Valid @RequestBody FinancialAccountYearDTO financialAccountYearDTO) throws URISyntaxException {
        log.debug("REST request to update FinancialAccountYear in extended : {}", financialAccountYearDTO);
        if (financialAccountYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FinancialAccountYearDTO result = financialAccountYearExtendedService.save(financialAccountYearDTO);
        return ResponseEntity.ok()
            .headers(SoptorshiHeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialAccountYearDTO.getId().toString()))
            .body(result);
    }
}
