package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.Manager;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.soptorshi.repository.ManagerRepository;
import org.soptorshi.repository.search.LeaveApplicationSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.LeaveApplicationService;
import org.soptorshi.service.dto.LeaveApplicationDTO;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.soptorshi.service.mapper.LeaveApplicationMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LeaveApplicationExtendedService extends LeaveApplicationService {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationExtendedService.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveApplicationMapper leaveApplicationMapper;

    private final LeaveApplicationSearchRepository leaveApplicationSearchRepository;

    private final LeaveBalanceService leaveBalanceService;

    private final EmployeeRepository employeeRepository;

    private final ManagerRepository managerRepository;

    public LeaveApplicationExtendedService(LeaveApplicationRepository leaveApplicationRepository, LeaveApplicationMapper leaveApplicationMapper, LeaveApplicationSearchRepository leaveApplicationSearchRepository,
                                           LeaveBalanceService leaveBalanceService, EmployeeRepository employeeRepository,
                                           ManagerRepository managerRepository) {
        super(leaveApplicationRepository, leaveApplicationMapper, leaveApplicationSearchRepository);
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
        this.leaveApplicationSearchRepository = leaveApplicationSearchRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }

    /**
     * Save a leaveApplication.
     *
     * @param leaveApplicationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LeaveApplicationDTO save(LeaveApplicationDTO leaveApplicationDTO) {
        // need to check whether employee is the manager of the applicant
        Optional<Employee> employee = employeeRepository.findByEmployeeId(leaveApplicationDTO.getEmployeeId());
        if (employee.isPresent()) {
            Optional<String> loggedInUserId = SecurityUtils.getCurrentUserLogin();
            if(loggedInUserId.isPresent()) {
                Optional<Employee> loggedInEmployee = employeeRepository.findByEmployeeId(loggedInUserId.get());
                if (loggedInEmployee.isPresent()) {
                    Optional<Manager> manager = managerRepository.getByParentEmployeeIdAndEmployee(employee.get().getId(), loggedInEmployee.get());
                    if(manager.isPresent()) {
                        if (!isValid(leaveApplicationDTO)) {
                            throw new BadRequestAlertException("application not valid!!", "leaveApplication", "idnull");
                        }
                        else {
                            log.debug("Request to save LeaveApplication : {}", leaveApplicationDTO);
                            LeaveApplication leaveApplication = leaveApplicationMapper.toEntity(leaveApplicationDTO);
                            leaveApplication = leaveApplicationRepository.save(leaveApplication);
                            LeaveApplicationDTO result = leaveApplicationMapper.toDto(leaveApplication);
                            leaveApplicationSearchRepository.save(leaveApplication);
                            return result;
                        }
                    }
                    else{
                        if(loggedInEmployee.get().equals(employee.get())){
                            if (!isValid(leaveApplicationDTO)) {
                                throw new BadRequestAlertException("application not valid!!", "leaveApplication", "idnull");
                            }
                            else {
                                log.debug("Request to save LeaveApplication : {}", leaveApplicationDTO);
                                LeaveApplication leaveApplication = leaveApplicationMapper.toEntity(leaveApplicationDTO);
                                leaveApplication = leaveApplicationRepository.save(leaveApplication);
                                LeaveApplicationDTO result = leaveApplicationMapper.toDto(leaveApplication);
                                leaveApplicationSearchRepository.save(leaveApplication);
                                return result;
                            }
                        }
                    }
                }
                throw new BadRequestAlertException("error while getting logged in user's employee details!!", "leaveApplication", "idnull");
            }
            throw new BadRequestAlertException("error while getting logged in user!!", "leaveApplication", "idnull");
        }
        throw new BadRequestAlertException("no employee found!!", "leaveApplication", "idnull");
    }

    private boolean isValid(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Validating LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getStatus().equals(LeaveStatus.REJECTED)) {
            throw new BadRequestAlertException("application already rejected!!", "leaveApplication", "idnull");
        }
        else {
            LeaveBalanceDTO leaveBalance = leaveBalanceService
                .calculateLeaveBalance(leaveApplicationDTO.getEmployeeId(), leaveApplicationDTO.getFromDate().getYear(),
                    leaveApplicationDTO.getLeaveTypesId());
            return leaveApplicationDTO.getNumberOfDays() <= leaveBalance.getRemainingDays();
        }
    }
}
