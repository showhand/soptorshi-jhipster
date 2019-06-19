package org.soptorshi.service;

import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PayrollService {

    private final Logger log = LoggerFactory.getLogger(PayrollService.class);

    private LoanService loanService;
    private FineService fineService;
    private AdvanceService advanceService;
    private SalaryService salaryService;
    private EmployeeService employeeService;
    private MonthlySalaryService monthlySalaryService;


    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryService salaryService, EmployeeService employeeService, MonthlySalaryService monthlySalaryService) {
        this.loanService = loanService;
        this.fineService = fineService;
        this.advanceService = advanceService;
        this.salaryService = salaryService;
        this.employeeService = employeeService;
        this.monthlySalaryService = monthlySalaryService;
    }

    public void generatePayroll(Long officeId, Long designationId, Integer year, MonthType monthType){

        monthlySalaryService.delete(year, monthType, officeId, designationId);
        List<Employee> employees = employeeService.get(officeId, designationId, EmployeeStatus.ACTIVE);
        List<MonthlySalary> monthlySalaries = new ArrayList<>();

        for(Employee employee: employees){
            MonthlySalary monthlySalary = new MonthlySalary();
            monthlySalary.setEmployee(employee);
            monthlySalary.setYear(year);
            monthlySalary.setMonth(monthType);
            monthlySalary.setFine(calculateFine(employee, year, monthType));
            monthlySalary.setAdvanceHO(calculateAdvance(employee, year, monthType));
            monthlySalary.setLoanAmount(calculateLoan(employee, year, monthType));
            monthlySalary.setBasic(calculateSalary(employee, year, monthType));

        }
    }

    private MonthlySalary assignAllowances(MonthlySalary monthlySalary){

        return monthlySalary;
    }

    private BigDecimal calculateFine(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalFine = new BigDecimal(0);

        return totalFine;
    }

    private BigDecimal calculateAdvance(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalAdvance = new BigDecimal(0);

        return totalAdvance;
    }

    private BigDecimal calculateLoan(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalLoan = new BigDecimal(0);

        return totalLoan;
    }

    private BigDecimal calculateSalary(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalSalary = new BigDecimal(0);

        return totalSalary;
    }
}
