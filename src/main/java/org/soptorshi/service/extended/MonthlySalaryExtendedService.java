package org.soptorshi.service.extended;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.repository.extended.MonthlySalaryExtendedRepository;
import org.soptorshi.repository.search.MonthlySalarySearchRepository;
import org.soptorshi.service.MonthlySalaryService;
import org.soptorshi.service.mapper.MonthlySalaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonthlySalaryExtendedService extends MonthlySalaryService {
    private MonthlySalaryExtendedRepository monthlySalaryExtendedRepository;

    public MonthlySalaryExtendedService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryMapper monthlySalaryMapper, MonthlySalarySearchRepository monthlySalarySearchRepository, MonthlySalaryExtendedRepository monthlySalaryExtendedRepository) {
        super(monthlySalaryRepository, monthlySalaryMapper, monthlySalarySearchRepository);
        this.monthlySalaryExtendedRepository = monthlySalaryExtendedRepository;
    }

    public void saveAll(List<MonthlySalary> monthlySalaryList){
        monthlySalaryExtendedRepository.saveAll(monthlySalaryList);
    }

    public void delete(Integer year, MonthType monthType, Long officeId, Long designationId){
        monthlySalaryExtendedRepository.deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_Id(year, monthType, officeId, designationId);
    }

    public void delete(Integer year, MonthType monthType, Long officeId, Long designationId, Long employeeId){
        monthlySalaryExtendedRepository.deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_IdAndEmployee_id(year, monthType, officeId, designationId, employeeId);
    }

    public void createVouchers(final Long officeId, final int year, final MonthType monthType){
        List<MonthlySalary> monthlySalaries = monthlySalaryExtendedRepository.getByEmployee_Office_IdAndYearAndMonth(officeId, year, monthType)
            .stream()
            .filter(m-> m.getStatus().equals(MonthlySalaryStatus.APPROVED_BY_MD) && m.isVoucherGenerated()!=true)
            .collect(Collectors.toList());

    }

}
