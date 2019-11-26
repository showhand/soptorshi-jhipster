package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SalaryMessagesQueryService;
import org.soptorshi.service.SalaryMessagesService;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.extended.SalaryMessagesExtendedService;
import org.soptorshi.web.rest.SalaryMessagesResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class SalaryMessagesExtendedResource {
    private final Logger log = LoggerFactory.getLogger(SalaryMessagesExtendedResource.class);

    private static final String ENTITY_NAME = "salaryMessages";

    private final SalaryMessagesExtendedService salaryMessagesService;

    private final SalaryMessagesQueryService salaryMessagesQueryService;

    public SalaryMessagesExtendedResource(SalaryMessagesExtendedService salaryMessagesService, SalaryMessagesQueryService salaryMessagesQueryService) {
        this.salaryMessagesService = salaryMessagesService;
        this.salaryMessagesQueryService = salaryMessagesQueryService;
    }

    /**
     * POST  /salary-messages : Create a new salaryMessages.
     *
     * @param salaryMessagesDTO the salaryMessagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryMessagesDTO, or with status 400 (Bad Request) if the salaryMessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salary-messages")
    public ResponseEntity<SalaryMessagesDTO> createSalaryMessages(@RequestBody SalaryMessagesDTO salaryMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save SalaryMessages : {}", salaryMessagesDTO);
        if (salaryMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new salaryMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryMessagesDTO result = salaryMessagesService.save(salaryMessagesDTO);
        return ResponseEntity.created(new URI("/api/salary-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salary-messages : Updates an existing salaryMessages.
     *
     * @param salaryMessagesDTO the salaryMessagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryMessagesDTO,
     * or with status 400 (Bad Request) if the salaryMessagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryMessagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salary-messages")
    public ResponseEntity<SalaryMessagesDTO> updateSalaryMessages(@RequestBody SalaryMessagesDTO salaryMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update SalaryMessages : {}", salaryMessagesDTO);
        if (salaryMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryMessagesDTO result = salaryMessagesService.save(salaryMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryMessagesDTO.getId().toString()))
            .body(result);
    }
}
