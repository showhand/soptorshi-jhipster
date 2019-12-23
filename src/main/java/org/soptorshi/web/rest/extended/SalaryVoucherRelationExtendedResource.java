package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SalaryVoucherRelationQueryService;
import org.soptorshi.service.SalaryVoucherRelationService;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.extended.SalaryVoucherRelationExtendedService;
import org.soptorshi.web.rest.SalaryVoucherRelationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class SalaryVoucherRelationExtendedResource {
    private final Logger log = LoggerFactory.getLogger(SalaryVoucherRelationExtendedResource.class);

    private static final String ENTITY_NAME = "salaryVoucherRelation";

    private final SalaryVoucherRelationExtendedService salaryVoucherRelationService;

    private final SalaryVoucherRelationQueryService salaryVoucherRelationQueryService;

    public SalaryVoucherRelationExtendedResource(SalaryVoucherRelationExtendedService salaryVoucherRelationService, SalaryVoucherRelationQueryService salaryVoucherRelationQueryService) {
        this.salaryVoucherRelationService = salaryVoucherRelationService;
        this.salaryVoucherRelationQueryService = salaryVoucherRelationQueryService;
    }

    /**
     * POST  /salary-voucher-relations : Create a new salaryVoucherRelation.
     *
     * @param salaryVoucherRelationDTO the salaryVoucherRelationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryVoucherRelationDTO, or with status 400 (Bad Request) if the salaryVoucherRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salary-voucher-relations")
    public ResponseEntity<SalaryVoucherRelationDTO> createSalaryVoucherRelation(@RequestBody SalaryVoucherRelationDTO salaryVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to save SalaryVoucherRelation : {}", salaryVoucherRelationDTO);
        if (salaryVoucherRelationDTO.getId() != null) {
            throw new BadRequestAlertException("A new salaryVoucherRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryVoucherRelationDTO result = salaryVoucherRelationService.save(salaryVoucherRelationDTO);
        return ResponseEntity.created(new URI("/api/salary-voucher-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salary-voucher-relations : Updates an existing salaryVoucherRelation.
     *
     * @param salaryVoucherRelationDTO the salaryVoucherRelationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryVoucherRelationDTO,
     * or with status 400 (Bad Request) if the salaryVoucherRelationDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryVoucherRelationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salary-voucher-relations")
    public ResponseEntity<SalaryVoucherRelationDTO> updateSalaryVoucherRelation(@RequestBody SalaryVoucherRelationDTO salaryVoucherRelationDTO) throws URISyntaxException {
        log.debug("REST request to update SalaryVoucherRelation : {}", salaryVoucherRelationDTO);
        if (salaryVoucherRelationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryVoucherRelationDTO result = salaryVoucherRelationService.save(salaryVoucherRelationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryVoucherRelationDTO.getId().toString()))
            .body(result);
    }

}
