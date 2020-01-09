package org.soptorshi.service.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.RequisitionService;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.soptorshi.web.rest.errors.CustomParameterizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@Transactional
public class RequisitionExtendedService extends RequisitionService {
    EmployeeExtendedRepository employeeExtendedRepository;

    public RequisitionExtendedService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper, RequisitionSearchRepository requisitionSearchRepository, EmployeeExtendedRepository employeeExtendedRepository) {
        super(requisitionRepository, requisitionMapper, requisitionSearchRepository);
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    @Override
    public RequisitionDTO save(RequisitionDTO requisitionDTO) {
        Employee employee = employeeExtendedRepository.findByEmployeeId(SecurityUtils.getCurrentUserLogin().get()).get();
        requisitionDTO.setDepartmentId(employee.getDepartment().getId());
        requisitionDTO.setOfficeId(employee.getOffice().getId());
        requisitionDTO.setEmployeeId(employee.getId());
        return super.save(requisitionDTO);
    }
}
