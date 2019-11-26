package org.soptorshi.service.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.repository.SalaryMessagesRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.SalaryMessagesSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SalaryMessagesService;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.mapper.SalaryMessagesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SalaryMessagesExtendedService extends SalaryMessagesService {

    EmployeeExtendedRepository employeeExtendedRepository;

    public SalaryMessagesExtendedService(SalaryMessagesRepository salaryMessagesRepository, SalaryMessagesMapper salaryMessagesMapper, SalaryMessagesSearchRepository salaryMessagesSearchRepository, EmployeeExtendedRepository employeeExtendedRepository) {
        super(salaryMessagesRepository, salaryMessagesMapper, salaryMessagesSearchRepository);
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    @Override
    public SalaryMessagesDTO save(SalaryMessagesDTO salaryMessagesDTO) {
        Employee loggedEmployee = employeeExtendedRepository.findByEmployeeId(SecurityUtils.getCurrentUserLogin().get()).get();
        salaryMessagesDTO.setCommenterId(loggedEmployee.getId());
        salaryMessagesDTO.setCommentedOn(LocalDate.now());
        return super.save(salaryMessagesDTO);
    }
}
