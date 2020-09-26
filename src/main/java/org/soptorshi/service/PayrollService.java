package org.soptorshi.service;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.extended.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
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

    private final LoanService loanService;
    private final FineService fineService;
    private final AdvanceService advanceService;
    private final SalaryExtendedService salaryService;
    private final EmployeeService employeeService;
    private final MonthlySalaryExtendedService monthlySalaryService;
    private final SpecialAllowanceTimeLineExtendedService specialAllowanceTimeLineService;
    private final DesignationWiseAllowanceService designationWiseAllowanceService;
    private final ProvidentFundService providentFundService;
    private final BillService billService;
    private final TaxService taxService;
    private final EmployeeRepository employeeRepository;
    private final HolidayExtendedService holidayExtendedService;
    private final WeekendExtendedService weekendExtendedService;
    private final LeaveApplicationExtendedService leaveApplicationExtendedService;
    private final UserService userService;


    public PayrollService(LoanService loanService, FineService fineService, AdvanceService advanceService, SalaryExtendedService salaryService, EmployeeService employeeService, MonthlySalaryExtendedService monthlySalaryService, SpecialAllowanceTimeLineExtendedService specialAllowanceTimeLineService, DesignationWiseAllowanceService designationWiseAllowanceService, ProvidentFundService providentFundService, BillService billService, TaxService taxService, EmployeeRepository employeeRepository, HolidayExtendedService holidayExtendedService, WeekendExtendedService weekendExtendedService, LeaveApplicationExtendedService leaveApplicationExtendedService, UserService userService) {
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
        this.holidayExtendedService = holidayExtendedService;
        this.weekendExtendedService = weekendExtendedService;
        this.leaveApplicationExtendedService = leaveApplicationExtendedService;
        this.userService = userService;
    }

    @Transactional
    public void generatePayroll(Long officeId, Integer year, MonthType monthType, Long employeeId){
        monthlySalaryService.delete(year, monthType, officeId, employeeId);
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

//            Long totalDayInAMonth = totalDays(monthType, year);
            //for test purpose
            Long totalDayInAMonth = 26L;
            // todo remove the bellow code fragments for calculation.
/*            List<LocalDate> holidayDates = holidayExtendedService.getAllHolidayDates(monthType, year);
            List<LocalDate> weekendDates = weekendExtendedService.getAllWeekendDates(monthType, year);
            List<LocalDate> withoutPayLeaveDates = leaveApplicationExtendedService.getWithoutPayLeaveDates(employeeId, monthType, year);
            List<LocalDate> withPayLeaveDates = leaveApplicationExtendedService.getWithPayLeaveDates(employeeId, monthType, year);
            Long totalPresent = totalDayInAMonth - (holidayDates.size() + weekendDates.size() + withoutPayLeaveDates.size() + withPayLeaveDates.size());*/
            // for test purpose
            Long totalPresent = 26L;
            monthlySalary.setAbsent( Integer.parseInt ((totalDayInAMonth-totalPresent)+""));
            BigDecimal perDaySalary = monthlySalary.getGross().divide(BigDecimal.valueOf(totalDayInAMonth), RoundingMode.CEILING);
            BigDecimal grossSalaryBasedOnPresense = perDaySalary.multiply(BigDecimal.valueOf(totalPresent));
            BigDecimal totalPayableSalary = grossSalaryBasedOnPresense.compareTo(monthlySalary.getGross())<0? grossSalaryBasedOnPresense: monthlySalary.getGross();
            monthlySalary.setGross(totalPayableSalary);
            monthlySalary = assignTax(monthlySalary);

            BigDecimal totalPayable = new BigDecimal(0);
            totalPayable = totalPayable
                .add(monthlySalary.getGross())
                .add(ObjectUtils.defaultIfNull( monthlySalary.getOverTimeAllowance(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getFestivalAllowance(), BigDecimal.ZERO));
            BigDecimal totalDeduction = new BigDecimal(0);

            totalDeduction = totalDeduction
                .add(ObjectUtils.defaultIfNull(monthlySalary.getAdvanceHO(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getFine(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getTax(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getProvidentFund(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getLoanAmount(), BigDecimal.ZERO));


            monthlySalary.setPayable(totalPayable.subtract(totalDeduction));
            monthlySalary.setGross(monthlySalary.getPayable());

            monthlySalaries.add(monthlySalary);
        }

        fineService.saveAll(updatableFines);
        loanService.saveAll(updatableLoans);
        advanceService.saveAll(updatableAdvances);
        monthlySalaryService.saveAll(monthlySalaries);
    }

    private Long totalDays(MonthType monthType, int year) {
        YearMonth yearMonth = YearMonth.of(year, monthType.ordinal() + 1);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate lastOfMonth = yearMonth.atEndOfMonth();

        return Duration.between(firstOfMonth, lastOfMonth).toDays();
    }

    @Transactional
    public void generatePayroll(Long officeId, Long designationId, Integer year, MonthType monthType){
        monthlySalaryService.delete(year, monthType, officeId);
        updatableFines = new ArrayList<>();
        updatableAdvances = new ArrayList<>();
        updatableLoans = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        if(designationId!= 9999)
            employees = employeeService.get(officeId, designationId, EmployeeStatus.ACTIVE);
        else
            employees = employeeService.get(officeId, EmployeeStatus.ACTIVE);
        List<MonthlySalary> monthlySalaries = new ArrayList<>();
        specialAllowanceTimeLineMap = specialAllowanceTimeLineService.get(year, monthType).stream()
            .collect(Collectors.toMap(s->s.getReligion(), s->s));

        for(Employee employee: employees){
            MonthlySalary monthlySalary = new MonthlySalary();
            monthlySalary.setEmployee(employee);
            monthlySalary.setYear(year);
            monthlySalary.setMonth(monthType);
            monthlySalary.setFine(calculateFine(employee, year, monthType));
            monthlySalary.setAdvanceFactory(calculateAdvance(employee, year, monthType));
            monthlySalary.setLoanAmount(calculateLoan(employee, year, monthType));
            calculateBasicAndGross(employee, year, monthType, monthlySalary);
            monthlySalary = calculateProvidentFund(monthlySalary);
            monthlySalary = calculateBill(monthlySalary);
            monthlySalary = assignAllowances(monthlySalary);

//            Long totalDayInAMonth = totalDays(monthType, year);
            //for test purpose
            Long totalDayInAMonth = 26L;
            // todo remove the bellow code fragments for calculation.
/*            List<LocalDate> holidayDates = holidayExtendedService.getAllHolidayDates(monthType, year);
            List<LocalDate> weekendDates = weekendExtendedService.getAllWeekendDates(monthType, year);
            List<LocalDate> withoutPayLeaveDates = leaveApplicationExtendedService.getWithoutPayLeaveDates(employeeId, monthType, year);
            List<LocalDate> withPayLeaveDates = leaveApplicationExtendedService.getWithPayLeaveDates(employeeId, monthType, year);
            Long totalPresent = totalDayInAMonth - (holidayDates.size() + weekendDates.size() + withoutPayLeaveDates.size() + withPayLeaveDates.size());*/
            // for test purpose
            Long totalPresent = 26L;
            monthlySalary.setAbsent( Integer.parseInt ((totalDayInAMonth-totalPresent)+""));
            BigDecimal totalPayableGross = monthlySalary.getGross().divide(BigDecimal.valueOf(totalDayInAMonth), RoundingMode.CEILING);
            BigDecimal perDaySalary =  totalPayableGross.compareTo(monthlySalary.getGross())<0? totalPayableGross: monthlySalary.getGross();
            BigDecimal grossSalaryBasedOnPresense = perDaySalary.multiply(BigDecimal.valueOf(totalPresent));
            monthlySalary.setGross(grossSalaryBasedOnPresense.compareTo(monthlySalary.getGross())<0? grossSalaryBasedOnPresense: monthlySalary.getGross());

            BigDecimal totalPayable = new BigDecimal(0);
            totalPayable = totalPayable
                .add(monthlySalary.getGross())
                .add(ObjectUtils.defaultIfNull( monthlySalary.getOverTimeAllowance(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull( monthlySalary.getBillPayable(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getFestivalAllowance(), BigDecimal.ZERO));
            BigDecimal totalDeduction = new BigDecimal(0);
            monthlySalary = assignTax(monthlySalary);
            totalDeduction = totalDeduction
                .add(ObjectUtils.defaultIfNull(monthlySalary.getAdvanceFactory(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getFine(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getTax(), BigDecimal.ZERO))
                .add(ObjectUtils.defaultIfNull(monthlySalary.getLoanAmount(), BigDecimal.ZERO));

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
        if(tax==null)
            return monthlySalary;
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
            monthlySalary.setProvidentFund(monthlySalary.getBasic().divide(BigDecimal.valueOf(providentFund.getRate()/100)));
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
            fine.setLeft(fine.getAmount().subtract(fineAmount));
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
            BigDecimal totalLeft = advance.getLeft().subtract(advanceAmount);
            advance.setLeft(advance.getAmount().subtract(advanceAmount));
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
            BigDecimal totalLeft = loan.getLeft().subtract(loanAmount);
            loan.setLeft(loan.getAmount().subtract(loanAmount));
            if(loan.getLeft().equals(new BigDecimal(0)) || loan.getLeft().compareTo(BigDecimal.ZERO)==-1){
                loan.setPaymentStatus(PaymentStatus.PAID);
            }
            totalLoan = totalLoan.add(loanAmount);
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


    public List<MonthlySalaryDTO> approveAll(Long officeId, Long designationId, Integer year, MonthType monthType, SalaryApprovalType salaryApprovalType) {
        List<MonthlySalaryDTO> existingSalaries = monthlySalaryService.get(officeId, designationId, year, monthType);
        List<MonthlySalaryDTO> updatedSalaries = new ArrayList<>();

        for(MonthlySalaryDTO s: existingSalaries){
            MonthlySalaryStatus monthlySalaryStatus = null;
            if(s.getStatus()==null && SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.EMPLOYEE_MANAGEMENT) )
                monthlySalaryStatus = salaryApprovalType.equals(SalaryApprovalType.APPROVE)? MonthlySalaryStatus.APPROVED_BY_MANAGER: null;
            else if(s.getStatus().equals(MonthlySalaryStatus.APPROVED_BY_MANAGER) && SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ACCOUNTS))
                monthlySalaryStatus = salaryApprovalType.equals(SalaryApprovalType.APPROVE)?  MonthlySalaryStatus.APPROVED_BY_ACCOUNTS: MonthlySalaryStatus.MODIFICATION_REQUEST_BY_ACCOUNTS;
            else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.CFO) && s.getStatus().equals(MonthlySalaryStatus.APPROVED_BY_ACCOUNTS))
                monthlySalaryStatus = salaryApprovalType.equals(SalaryApprovalType.APPROVE)? MonthlySalaryStatus.APPROVED_BY_CFO: MonthlySalaryStatus.MODIFICATION_REQUEST_BY_CFO;
            else
                monthlySalaryStatus = salaryApprovalType.equals(SalaryApprovalType.APPROVE)? MonthlySalaryStatus.APPROVED_BY_MD: MonthlySalaryStatus.MODIFICATION_REQUEST_BY_MD;

            s.setStatus(monthlySalaryStatus);
        }
        updatedSalaries = monthlySalaryService.updateAll(existingSalaries);
        return updatedSalaries;
    }


}
