package org.soptorshi.service.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Salary;
import org.soptorshi.domain.enumeration.SalaryStatus;
import org.soptorshi.repository.SalaryRepository;
import org.soptorshi.repository.extended.SalaryExtendedRepository;
import org.soptorshi.repository.search.SalarySearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SalaryService;
import org.soptorshi.service.dto.SalaryDTO;
import org.soptorshi.service.mapper.SalaryMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class SalaryExtendedService extends SalaryService {
    private SalaryExtendedRepository salaryExtendedRepository;

    public SalaryExtendedService(SalaryRepository salaryRepository, SalaryMapper salaryMapper, SalarySearchRepository salarySearchRepository, SalaryExtendedRepository salaryExtendedRepository) {
        super(salaryRepository, salaryMapper, salarySearchRepository);
        this.salaryExtendedRepository = salaryExtendedRepository;
    }

    @Override
    public SalaryDTO save(SalaryDTO salaryDTO) {
        salaryDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryDTO.setModifiedOn(LocalDate.now());
        return super.save(salaryDTO);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Salary get(Employee employee, SalaryStatus salaryStatus){
        return salaryExtendedRepository.getByEmployeeAndSalaryStatus(employee, salaryStatus);
    }
}
