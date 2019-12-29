package org.soptorshi.service.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.repository.RequisitionMessagesRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.RequisitionMessagesSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.RequisitionMessagesService;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.mapper.RequisitionMessagesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class RequisitionMessagesExtendedService extends RequisitionMessagesService {
    EmployeeExtendedRepository employeeExtendedRepository;

    public RequisitionMessagesExtendedService(RequisitionMessagesRepository requisitionMessagesRepository, RequisitionMessagesMapper requisitionMessagesMapper, RequisitionMessagesSearchRepository requisitionMessagesSearchRepository, EmployeeExtendedRepository employeeExtendedRepository) {
        super(requisitionMessagesRepository, requisitionMessagesMapper, requisitionMessagesSearchRepository);
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    @Override
    public RequisitionMessagesDTO save(RequisitionMessagesDTO requisitionMessagesDTO) {
        Employee loggedEmployee = employeeExtendedRepository.findByEmployeeId(SecurityUtils.getCurrentUserLogin().get()).get();
        requisitionMessagesDTO.setCommenterId(loggedEmployee.getId());
        requisitionMessagesDTO.setCommentedOn(LocalDate.now());
        return super.save(requisitionMessagesDTO);
    }
}
