package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.service.MonthlySalaryQueryService;
import org.soptorshi.service.MonthlySalaryService;
import org.soptorshi.service.extended.MonthlySalaryExtendedService;
import org.soptorshi.web.rest.MonthlySalaryResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extended")
public class MonthlySalaryExtendedResource {
    private final Logger log = LoggerFactory.getLogger(MonthlySalaryExtendedResource.class);

    private static final String ENTITY_NAME = "monthlySalary";

    private final MonthlySalaryExtendedService monthlySalaryService;

    private final MonthlySalaryQueryService monthlySalaryQueryService;

    public MonthlySalaryExtendedResource(MonthlySalaryExtendedService monthlySalaryService, MonthlySalaryQueryService monthlySalaryQueryService) {
        this.monthlySalaryService = monthlySalaryService;
        this.monthlySalaryQueryService = monthlySalaryQueryService;
    }


    @GetMapping("/monthly-salaries/{officeId}/{year}/{monthType}")
    public ResponseEntity<Void> createSalaryVouchers(
        @PathVariable("officeId") Long officeId,
        @PathVariable("year") int year,
        @PathVariable("monthType") String monthType
    ){
        monthlySalaryService.createVouchers(officeId, year, MonthType.valueOf(monthType));
        return ResponseEntity.ok()
            .body(null);
    }

}
