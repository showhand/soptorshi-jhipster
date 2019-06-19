package org.soptorshi.service;

import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Fine;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;

@Service
@Transactional
public class PayrollService {

    private final Logger log = LoggerFactory.getLogger(PayrollService.class);

    private LoanService loanService;
    private FineService fineService;
    private AdvanceService advanceService;
    private SalaryService salaryService;
    private EmployeeQueryService employeeQueryService;

    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryService salaryService, EmployeeQueryService employeeQueryService) {
        this.loanService = loanService;
        this.fineService = fineService;
        this.advanceService = advanceService;
        this.salaryService = salaryService;
        this.employeeQueryService = employeeQueryService;
    }

    public void generatePayroll(Long officeId, Long designationId, Month month){

    }
}
