package org.soptorshi.service.extended;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.SalaryVoucherRelation;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.repository.OfficeRepository;
import org.soptorshi.repository.extended.MonthlySalaryExtendedRepository;
import org.soptorshi.repository.extended.SalaryExtendedRepository;
import org.soptorshi.repository.search.MonthlySalarySearchRepository;
import org.soptorshi.service.MonthlySalaryService;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.mapper.MonthlySalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonthlySalaryExtendedService extends MonthlySalaryService {
    private MonthlySalaryExtendedRepository monthlySalaryExtendedRepository;
    private OfficeRepository officeRepository;
    private JournalVoucherExtendedService journalVoucherExtendedService;
    private PaymentVoucherExtendedService paymentVoucherExtendedService;
    private SalaryExtendedRepository salaryExtendedRepository;


    public MonthlySalaryExtendedService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryMapper monthlySalaryMapper, MonthlySalarySearchRepository monthlySalarySearchRepository, MonthlySalaryExtendedRepository monthlySalaryExtendedRepository, OfficeRepository officeRepository, JournalVoucherExtendedService journalVoucherExtendedService, PaymentVoucherExtendedService paymentVoucherExtendedService, SalaryExtendedRepository salaryExtendedRepository) {
        super(monthlySalaryRepository, monthlySalaryMapper, monthlySalarySearchRepository);
        this.monthlySalaryExtendedRepository = monthlySalaryExtendedRepository;
        this.officeRepository = officeRepository;
        this.journalVoucherExtendedService = journalVoucherExtendedService;
        this.paymentVoucherExtendedService = paymentVoucherExtendedService;
        this.salaryExtendedRepository = salaryExtendedRepository;
    }

    public void saveAll(List<MonthlySalary> monthlySalaryList){
        monthlySalaryExtendedRepository.saveAll(monthlySalaryList);
    }

    @Override
    public MonthlySalaryDTO save(MonthlySalaryDTO monthlySalaryDTO) {
        return super.save(monthlySalaryDTO);
    }

    public void delete(Integer year, MonthType monthType, Long officeId, Long designationId){
        monthlySalaryExtendedRepository.deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_Id(year, monthType, officeId, designationId);
    }

    public void delete(Integer year, MonthType monthType, Long officeId, Long designationId, Long employeeId){
        monthlySalaryExtendedRepository.deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_idAndStatusIsNot(year, monthType, officeId, employeeId, MonthlySalaryStatus.APPROVED_BY_MD);
    }

    public void createVouchers(final Long officeId, final int year, final MonthType monthType){
        List<MonthlySalary> monthlySalaries = new ArrayList<>();

        monthlySalaryExtendedRepository.getByEmployee_Office_IdAndYearAndMonth(officeId, year, monthType)
            .stream()
            .forEach(m->{
                if(m.getStatus()!=null && m.getStatus().equals(MonthlySalaryStatus.APPROVED_BY_MD) && (m.isVoucherGenerated()==null || m.isVoucherGenerated()!=true)){
                    monthlySalaries.add(m);
                }
            });

        SalaryVoucherRelation salaryVoucherRelation = new SalaryVoucherRelation();
        salaryVoucherRelation.setOffice(officeRepository.getOne(officeId));
        salaryVoucherRelation.setYear(year);
        salaryVoucherRelation.setMonth(monthType);

        if(monthlySalaries.size()>0){
            journalVoucherExtendedService.createPayrollJournalEntry(monthlySalaries, salaryVoucherRelation);
            SalaryVoucherRelation salaryVoucherRelationForPayment = new SalaryVoucherRelation();
            salaryVoucherRelationForPayment.setOffice(salaryVoucherRelation.getOffice());
            salaryVoucherRelationForPayment.setYear(salaryVoucherRelation.getYear());
            salaryVoucherRelationForPayment.setMonth(monthType);
            paymentVoucherExtendedService.createPayrollPaymentVoucherEntry(monthlySalaries, salaryVoucherRelationForPayment);
        }

        monthlySalaries.forEach(m->{
            m.setVoucherGenerated(true);
        });

        monthlySalaryExtendedRepository.saveAll(monthlySalaries);

    }

}
