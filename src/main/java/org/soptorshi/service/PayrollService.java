package org.soptorshi.service;

import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PayrollService {

    private final Logger log = LoggerFactory.getLogger(PayrollService.class);

    private List<Fine> updatableFines;
    private List<Advance> updatableAdvances;
    private List<Loan> updatableLoans;

    private LoanService loanService;
    private FineService fineService;
    private AdvanceService advanceService;
    private SalaryService salaryService;
    private EmployeeService employeeService;
    private MonthlySalaryService monthlySalaryService;
    private SpecialAllowanceTimeLineService specialAllowanceTimeLineService;
    private DesignationWiseAllowanceService designationWiseAllowanceService;
    private ProvidentFundService providentFundService;

    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryService salaryService, EmployeeService employeeService, MonthlySalaryService monthlySalaryService, SpecialAllowanceTimeLineService specialAllowanceTimeLineService, DesignationWiseAllowanceService designationWiseAllowanceService, ProvidentFundService providentFundService) {
        this.loanService = loanService;
        this.fineService = fineService;
        this.advanceService = advanceService;
        this.salaryService = salaryService;
        this.employeeService = employeeService;
        this.monthlySalaryService = monthlySalaryService;
        this.specialAllowanceTimeLineService = specialAllowanceTimeLineService;
        this.designationWiseAllowanceService = designationWiseAllowanceService;
        this.providentFundService = providentFundService;
    }

    public void generatePayroll(Long officeId, Long designationId, Integer year, MonthType monthType){
        monthlySalaryService.delete(year, monthType, officeId, designationId);
        updatableFines = new ArrayList<>();
        updatableAdvances = new ArrayList<>();
        updatableLoans = new ArrayList<>();

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
            monthlySalary = calculateProvidentFund(monthlySalary);
            monthlySalary = assignAllowances(monthlySalary);
        }
    }

    private MonthlySalary assignAllowances(MonthlySalary monthlySalary){

        List<DesignationWiseAllowance> designationWiseAllowances = designationWiseAllowanceService.get(monthlySalary.getEmployee().getDesignation().getId());
        monthlySalary.setOtherAllowance(new BigDecimal(0));
        for(DesignationWiseAllowance designationWiseAllowance: designationWiseAllowances){
            if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.HOUSE_RENT) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY))
                monthlySalary.setHouseRent(monthlySalary.getBasic().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.MEDICAL_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY))
                monthlySalary.setHouseRent(monthlySalary.getBasic().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.OTHER_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY))
                monthlySalary.setOtherAllowance(monthlySalary.getOtherAllowance().add(monthlySalary.getBasic().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100)))));
        }

        monthlySalary = calculateSpecialAndOtherAllowances(monthlySalary, designationWiseAllowances);
        return monthlySalary;
    }

    private MonthlySalary calculateSpecialAndOtherAllowances(MonthlySalary monthlySalary, List<DesignationWiseAllowance> designationWiseAllowances){
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLines = specialAllowanceTimeLineService.get(monthlySalary.getYear(), monthlySalary.getMonth());
        Map<AllowanceType, DesignationWiseAllowance> allowanceMap = designationWiseAllowances
            .stream()
            .collect(Collectors.toMap(a->a.getAllowanceType(), a->a));

        for(SpecialAllowanceTimeLine specialAllowanceTimeLine: specialAllowanceTimeLines){
            if(allowanceMap.containsKey(specialAllowanceTimeLine.getAllowanceType())){
                monthlySalary
                    .setOtherAllowance(
                        monthlySalary.getOtherAllowance()
                            .add(monthlySalary.getBasic()
                                .multiply (allowanceMap.get(specialAllowanceTimeLine.getAllowanceType()).getAmount()
                                    .divide(new BigDecimal(100)))));
            }
        }
        return monthlySalary;
    }


    private MonthlySalary calculateProvidentFund(MonthlySalary monthlySalary){
        ProvidentFund providentFund = providentFundService.get(monthlySalary.getEmployee(), ProvidentFundStatus.ACTIVE);
        if(providentFund!=null){
            monthlySalary.setProvidentFund(monthlySalary.getBasic().multiply(BigDecimal.valueOf(providentFund.getRate()/100)));
        }
        return monthlySalary;
    }

    private BigDecimal calculateFine(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalFine = new BigDecimal(0);
        List<Fine> fines = fineService.get(employee.getId(), PaymentStatus.NOT_PAID);
        for(Fine fine: fines){
            BigDecimal fineAmount = fine.getAmount().multiply(BigDecimal.valueOf(fine.getMonthlyPayable()/100));
            fine.setLeft(totalFine.subtract(fineAmount));
            if(fine.getLeft().equals(new BigDecimal(0)))
                fine.setPaymentStatus(PaymentStatus.PAID);
            totalFine = totalFine.add(fineAmount);
        }

        updatableFines.addAll(fines);
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
