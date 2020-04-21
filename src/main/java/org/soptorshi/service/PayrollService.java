package org.soptorshi.service;

import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.dto.EmployeeDTO;
import org.soptorshi.service.extended.MonthlySalaryExtendedService;
import org.soptorshi.service.extended.SalaryExtendedService;
import org.soptorshi.service.extended.SpecialAllowanceTimeLineExtendedService;
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
    private Map<Religion, SpecialAllowanceTimeLine> specialAllowanceTimeLineMap;

    private LoanService loanService;
    private FineService fineService;
    private AdvanceService advanceService;
    private SalaryExtendedService salaryService;
    private EmployeeService employeeService;
    private MonthlySalaryExtendedService monthlySalaryService;
    private SpecialAllowanceTimeLineExtendedService specialAllowanceTimeLineService;
    private DesignationWiseAllowanceService designationWiseAllowanceService;
    private ProvidentFundService providentFundService;
    private BillService billService;
    private TaxService taxService;
    private EmployeeRepository employeeRepository;


    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryExtendedService salaryService, EmployeeService employeeService, MonthlySalaryExtendedService monthlySalaryService, SpecialAllowanceTimeLineExtendedService specialAllowanceTimeLineService, DesignationWiseAllowanceService designationWiseAllowanceService, ProvidentFundService providentFundService, BillService billService, TaxService taxService, EmployeeRepository employeeRepository) {
        this.loanService = loanService;
        this.fineService = fineService;
        this.advanceService = advanceService;
        this.salaryService = salaryService;
        this.employeeService = employeeService;
        this.monthlySalaryService = monthlySalaryService;
        this.specialAllowanceTimeLineService = specialAllowanceTimeLineService;
        this.designationWiseAllowanceService = designationWiseAllowanceService;
        this.providentFundService = providentFundService;
        this.billService = billService;
        this.taxService = taxService;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void generatePayroll(Long officeId, Long designationId, Integer year, MonthType monthType, Long employeeId){
        monthlySalaryService.delete(year, monthType, officeId, designationId, employeeId);
        updatableFines = new ArrayList<>();
        updatableAdvances = new ArrayList<>();
        updatableLoans = new ArrayList<>();

        List<Employee> employees = new ArrayList<>();
        employees.add(employeeRepository.getOne(employeeId));
        List<MonthlySalary> monthlySalaries = new ArrayList<>();
        specialAllowanceTimeLineMap = specialAllowanceTimeLineService.get(year, monthType).stream()
            .collect(Collectors.toMap(s->s.getReligion(), s->s));

        for(Employee employee: employees){
            MonthlySalary monthlySalary = new MonthlySalary();
            monthlySalary.setEmployee(employee);
            monthlySalary.setYear(year);
            monthlySalary.setMonth(monthType);
            monthlySalary.setFine(calculateFine(employee, year, monthType));
            monthlySalary.setAdvanceHO(calculateAdvance(employee, year, monthType));
            monthlySalary.setLoanAmount(calculateLoan(employee, year, monthType));
            calculateBasicAndGross(employee, year, monthType, monthlySalary);
            monthlySalary = calculateProvidentFund(monthlySalary);
            monthlySalary = calculateBill(monthlySalary);
            monthlySalary = assignAllowances(monthlySalary);

            BigDecimal totalPayable = new BigDecimal(0);
            totalPayable = totalPayable
                .add(monthlySalary.getGross())
                .add(monthlySalary.getFestivalAllowance()==null? BigDecimal.ZERO: monthlySalary.getFestivalAllowance());
            BigDecimal totalDeduction = new BigDecimal(0);
            totalDeduction = totalDeduction
                .add(monthlySalary.getAdvanceFactory()==null? BigDecimal.ZERO: monthlySalary.getAdvanceFactory())
                .add(monthlySalary.getFine()==null? BigDecimal.ZERO: monthlySalary.getFine())
                .add(monthlySalary.getLoanAmount()==null? BigDecimal.ZERO: monthlySalary.getLoanAmount());

            monthlySalary.setPayable(totalPayable.subtract(totalDeduction));

            monthlySalaries.add(monthlySalary);
        }

        fineService.saveAll(updatableFines);
        loanService.saveAll(updatableLoans);
        advanceService.saveAll(updatableAdvances);
        monthlySalaryService.saveAll(monthlySalaries);
    }

    @Transactional
    public void generatePayroll(Long officeId, Long designationId, Integer year, MonthType monthType){
        monthlySalaryService.delete(year, monthType, officeId, designationId);
        updatableFines = new ArrayList<>();
        updatableAdvances = new ArrayList<>();
        updatableLoans = new ArrayList<>();

        List<Employee> employees = employeeService.get(officeId, designationId, EmployeeStatus.ACTIVE);
        List<MonthlySalary> monthlySalaries = new ArrayList<>();
        specialAllowanceTimeLineMap = specialAllowanceTimeLineService.get(year, monthType).stream()
            .collect(Collectors.toMap(s->s.getReligion(), s->s));

        for(Employee employee: employees){
            MonthlySalary monthlySalary = new MonthlySalary();
            monthlySalary.setEmployee(employee);
            monthlySalary.setYear(year);
            monthlySalary.setMonth(monthType);
            monthlySalary.setFine(calculateFine(employee, year, monthType));
            monthlySalary.setAdvanceHO(calculateAdvance(employee, year, monthType));
            monthlySalary.setLoanAmount(calculateLoan(employee, year, monthType));
            calculateBasicAndGross(employee, year, monthType, monthlySalary);
            monthlySalary = calculateProvidentFund(monthlySalary);
            monthlySalary = calculateBill(monthlySalary);
            monthlySalary = assignAllowances(monthlySalary);

            BigDecimal totalPayable = new BigDecimal(0);
            totalPayable = totalPayable
                .add(monthlySalary.getGross())
                .add(monthlySalary.getFestivalAllowance()==null? BigDecimal.ZERO: monthlySalary.getFestivalAllowance());
            BigDecimal totalDeduction = new BigDecimal(0);
            totalDeduction = totalDeduction
                .add(monthlySalary.getAdvanceFactory()==null? BigDecimal.ZERO: monthlySalary.getAdvanceFactory())
                .add(monthlySalary.getFine()==null? BigDecimal.ZERO: monthlySalary.getFine())
                .add(monthlySalary.getLoanAmount()==null? BigDecimal.ZERO: monthlySalary.getLoanAmount());

            monthlySalary.setPayable(totalPayable.subtract(totalDeduction));

            monthlySalaries.add(monthlySalary);
        }

        fineService.saveAll(updatableFines);
        loanService.saveAll(updatableLoans);
        advanceService.saveAll(updatableAdvances);
        monthlySalaryService.saveAll(monthlySalaries);
    }

    private MonthlySalary assignAllowances(MonthlySalary monthlySalary){

        List<DesignationWiseAllowance> designationWiseAllowances = designationWiseAllowanceService.get(monthlySalary.getEmployee().getDesignation().getId());
        monthlySalary.setOtherAllowance(new BigDecimal(0));
        for(DesignationWiseAllowance designationWiseAllowance: designationWiseAllowances){
            if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.HOUSE_RENT) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setHouseRent(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.OVERTIME_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.SPECIFIC)){
                monthlySalary.setOverTimeAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.FOOD_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setFoodAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.DRIVER_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setDriverAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.FUEL_LUB_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setFuelLubAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.TRAVEL_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setTravelAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.MOBILE_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setMobileAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.ARREAR_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY)){
                monthlySalary.setArrearAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.MEDICAL_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY))
                monthlySalary.setMedicalAllowance(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100))));
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.FESTIVAL_BONUS) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.SPECIFIC)){
                if(specialAllowanceTimeLineMap.containsKey(monthlySalary.getEmployee().getReligion())){
                    monthlySalary.setFestivalAllowance( monthlySalary.getFestivalAllowance()==null? BigDecimal.ZERO: monthlySalary.getFestivalAllowance());
                    if(!monthlySalary.getEmployee().getReligion().equals(Religion.ISLAM)){
                        monthlySalary.setFestivalAllowance(monthlySalary.getFestivalAllowance().add(monthlySalary.getGross().multiply(designationWiseAllowance.getAmount().divide(BigDecimal.valueOf(100)))));
                        monthlySalary.setFestivalAllowance(monthlySalary.getFestivalAllowance().multiply(BigDecimal.ONE.add(BigDecimal.ONE)));
                    }else{
                        monthlySalary.setFestivalAllowance(monthlySalary.getFestivalAllowance().add(monthlySalary.getGross().multiply(designationWiseAllowance.getAmount().divide(BigDecimal.valueOf(100)))));
                    }
                }else{
                    monthlySalary.setFestivalAllowance(BigDecimal.ZERO);
                }
            }
            else if(designationWiseAllowance.getAllowanceType().equals(AllowanceType.OTHER_ALLOWANCE) && designationWiseAllowance.getAllowanceCategory().equals(AllowanceCategory.MONTHLY))
                monthlySalary.setOtherAllowance(monthlySalary.getOtherAllowance().add(monthlySalary.getGross().multiply (designationWiseAllowance.getAmount().divide(new BigDecimal(100)))));
        }

        return monthlySalary;
    }

    private MonthlySalary assignTax(MonthlySalary monthlySalary){
        Tax tax = taxService.find(monthlySalary.getEmployee().getId(), TaxStatus.ACTIVE);
        monthlySalary.setTax(tax.getAmount());
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
                                .divide (allowanceMap.get(specialAllowanceTimeLine.getAllowanceType()).getAmount()
                                    .divide(new BigDecimal(100)))));
            }
        }
        return monthlySalary;
    }


    private MonthlySalary calculateProvidentFund(MonthlySalary monthlySalary){
        ProvidentFund providentFund = providentFundService.get(monthlySalary.getEmployee(), ProvidentFundStatus.ACTIVE);
        if(providentFund!=null){
            monthlySalary.setProvidentFund(monthlySalary.getGross().divide(BigDecimal.valueOf(providentFund.getRate()/100)));
        }
        return monthlySalary;
    }


    private MonthlySalary calculateBill(MonthlySalary monthlySalary){
        List<Bill> bills = billService.get(monthlySalary.getEmployee().getId());
        monthlySalary.setBillPayable(BigDecimal.ZERO);
        monthlySalary.setBillReceivable(BigDecimal.ZERO);

        for(Bill bill: bills){
            if(bill.getBillCategory().equals(BillCategory.PAYABLE)){
                monthlySalary.setBillPayable(monthlySalary.getBillPayable().add(bill.getAmount()));
            }else{
                monthlySalary.setBillReceivable(monthlySalary.getBillReceivable().add(bill.getAmount()));
            }
        }

        return monthlySalary;
    }
    private BigDecimal calculateFine(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalFine = new BigDecimal(0);
        List<Fine> fines = fineService.get(employee.getId(), PaymentStatus.NOT_PAID);
        for(Fine fine: fines){
            BigDecimal fineAmount = fine.getAmount().multiply(BigDecimal.valueOf(fine.getMonthlyPayable()/100));
            BigDecimal totalLeft = fine.getLeft().add(fineAmount);
            fine.setLeft(fine.getAmount().subtract(totalLeft));
            if(fine.getLeft().equals(new BigDecimal(0)) || fine.getLeft().compareTo(BigDecimal.ZERO)==-1)
                fine.setPaymentStatus(PaymentStatus.PAID);
            totalFine = totalFine.add(fineAmount);
        }

        updatableFines.addAll(fines);
        return totalFine;
    }

    private BigDecimal calculateAdvance(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalAdvance = new BigDecimal(0);
        List<Advance> advances = advanceService.get(employee, PaymentStatus.NOT_PAID);
        for(Advance advance: advances){
            BigDecimal advanceAmount = advance.getAmount().multiply(BigDecimal.valueOf(advance.getMonthlyPayable()/100));
            BigDecimal totalLeft = advance.getLeft().add(advanceAmount);
            advance.setLeft(advance.getAmount().subtract(totalLeft));
            if(advance.getLeft().equals(new BigDecimal(0)) || advance.getLeft().compareTo(BigDecimal.ZERO)==-1)
                advance.setPaymentStatus(PaymentStatus.PAID);
            totalAdvance = totalAdvance.add(advanceAmount);
        }
        updatableAdvances.addAll(advances);
        return totalAdvance;
    }

    private BigDecimal calculateLoan(Employee employee, Integer year, MonthType monthType){
        BigDecimal totalLoan = new BigDecimal(0);
        List<Loan> loans = loanService.get(employee, PaymentStatus.NOT_PAID);
        for(Loan loan: loans){
            BigDecimal loanAmount = loan.getAmount().multiply(BigDecimal.valueOf(loan.getMonthlyPayable()/100));
            BigDecimal totalLeft = loan.getLeft().add(loanAmount);
            loan.setLeft(loan.getAmount().subtract(totalLeft));
            if(loan.getLeft().equals(new BigDecimal(0)) || loan.getLeft().compareTo(BigDecimal.ZERO)==-1){
                loan.setPaymentStatus(PaymentStatus.PAID);
            }
            totalLoan = totalLoan.add(totalLeft);
        }
        return totalLoan;
    }

    private void calculateBasicAndGross(Employee employee, Integer year, MonthType monthType, MonthlySalary monthlySalary){
        BigDecimal totalSalary = new BigDecimal(0);
        Salary activeSalary = salaryService.get(employee, SalaryStatus.ACTIVE);
        if(activeSalary!=null){
            monthlySalary.setBasic(activeSalary.getGross().multiply( activeSalary.getBasic().divide(BigDecimal.valueOf(100))));
            monthlySalary.setGross(activeSalary.getGross());
        }else{
            monthlySalary.setBasic(BigDecimal.ZERO);
            monthlySalary.setGross(BigDecimal.ZERO);
        }
    }
}
