package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Office;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;

@Service
@Transactional
public class PayrollService {

    private final Logger log = LoggerFactory.getLogger(PayrollService.class);

    private LoanService loanService;
    private FineService fineService;
    private AdvanceService advanceService;
    private SalaryService salaryService;
    private LoanQueryService loanQueryService;
    private FineQueryService fineQueryService;
    private AdvanceQueryService advanceQueryService;
    private SalaryQueryService salaryQueryService;
    private MonthlySalaryService monthlySalaryService;


    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryService salaryService, LoanQueryService loanQueryService, FineQueryService fineQueryService, AdvanceQueryService advanceQueryService, SalaryQueryService salaryQueryService, MonthlySalaryService monthlySalaryService) {
        this.loanService = loanService;
        this.fineService = fineService;
        this.advanceService = advanceService;
        this.salaryService = salaryService;
        this.loanQueryService = loanQueryService;
        this.fineQueryService = fineQueryService;
        this.advanceQueryService = advanceQueryService;
        this.salaryQueryService = salaryQueryService;
        this.monthlySalaryService = monthlySalaryService;
    }

    public void generatePayroll(Office office, Designation designation, Month month){
        
    }
}
