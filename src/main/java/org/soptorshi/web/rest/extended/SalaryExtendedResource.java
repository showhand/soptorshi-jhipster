package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.service.PayrollService;
import org.soptorshi.service.SalaryQueryService;
import org.soptorshi.service.SalaryService;
import org.soptorshi.service.dto.SalaryDTO;
import org.soptorshi.service.extended.SalaryExtendedService;
import org.soptorshi.web.rest.SalaryResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class SalaryExtendedResource {
    private final Logger log = LoggerFactory.getLogger(SalaryResource.class);

    private static final String ENTITY_NAME = "salary";

    private final SalaryExtendedService salaryService;

    private final SalaryQueryService salaryQueryService;

    private final PayrollService payrollService;


    public SalaryExtendedResource(SalaryExtendedService salaryService, SalaryQueryService salaryQueryService, PayrollService payrollService) {
        this.salaryService = salaryService;
        this.salaryQueryService = salaryQueryService;
        this.payrollService = payrollService;
    }


    /**
     * POST  /salaries : Create a new salary.
     *
     * @param salaryDTO the salaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryDTO, or with status 400 (Bad Request) if the salary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salaries")
    public ResponseEntity<SalaryDTO> createSalary(@Valid @RequestBody SalaryDTO salaryDTO) throws URISyntaxException {
        log.debug("REST request to save Salary : {}", salaryDTO);
        if (salaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new salary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryDTO result = salaryService.save(salaryDTO);
        return ResponseEntity.created(new URI("/api/salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salaries : Updates an existing salary.
     *
     * @param salaryDTO the salaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryDTO,
     * or with status 400 (Bad Request) if the salaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salaries")
    public ResponseEntity<SalaryDTO> updateSalary(@Valid @RequestBody SalaryDTO salaryDTO) throws URISyntaxException {
        log.debug("REST request to update Salary : {}", salaryDTO);
        if (salaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryDTO result = salaryService.save(salaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryDTO.getId().toString()))
            .body(result);
    }



    @GetMapping("/salaries/generatePayRoll-all/{officeId}/{designationId}/{year}/{monthType}")
    public ResponseEntity<Void> generatePayroll(@PathVariable("officeId") Long officeId,@PathVariable("designationId") Long designationId,@PathVariable("year") Integer year,@PathVariable("monthType") MonthType monthType){
        payrollService.generatePayroll(officeId, designationId, year, monthType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/salaries/generatePayRoll-employee/{officeId}/{year}/{monthType}/{employeeId}")
    public ResponseEntity<Void> generatePayroll(@PathVariable("officeId") Long officeId,@PathVariable("year") Integer year,@PathVariable("monthType") MonthType monthType, @PathVariable("employeeId") Long employeeId){
        payrollService.generatePayroll(officeId, year, monthType, employeeId);
        return ResponseEntity.ok().build();
    }
}
