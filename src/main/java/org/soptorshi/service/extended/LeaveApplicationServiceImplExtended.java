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
import org.soptorshi.service.dto.LeaveApplicationDTO;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.soptorshi.service.impl.LeaveApplicationServiceImpl;
import org.soptorshi.service.mapper.LeaveApplicationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
@Transactional
public class LeaveApplicationServiceImplExtended extends LeaveApplicationServiceImpl {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationServiceImplExtended.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveApplicationMapper leaveApplicationMapper;

    private final LeaveApplicationSearchRepository leaveApplicationSearchRepository;

    private final LeaveBalanceServiceImpl leaveBalanceServiceImpl;

    private final EmployeeRepository employeeRepository;

    private final ManagerRepository managerRepository;

    public LeaveApplicationServiceImplExtended(LeaveApplicationRepository leaveApplicationRepository, LeaveApplicationMapper leaveApplicationMapper, LeaveApplicationSearchRepository leaveApplicationSearchRepository,
                                               LeaveBalanceServiceImpl leaveBalanceServiceImpl, EmployeeRepository employeeRepository,
                                       ManagerRepository managerRepository) {
        super(leaveApplicationRepository, leaveApplicationMapper, leaveApplicationSearchRepository, leaveBalanceServiceImpl, employeeRepository, managerRepository);
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
        this.leaveApplicationSearchRepository = leaveApplicationSearchRepository;
        this.leaveBalanceServiceImpl = leaveBalanceServiceImpl;
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
                        if (!isValid(leaveApplicationDTO)) return null;
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
                            if (!isValid(leaveApplicationDTO)) return null;
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
            }
        }
        return null;
    }

    private boolean isValid(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Validating LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getStatus().equals(LeaveStatus.REJECTED)) return true;
        LeaveBalanceDTO leaveBalance = leaveBalanceServiceImpl
            .calculateLeaveBalance(leaveApplicationDTO.getEmployeeId(), leaveApplicationDTO.getFromDate().getYear(),
                leaveApplicationDTO.getLeaveTypesId());
        return leaveApplicationDTO.getNumberOfDays() <= leaveBalance.getRemainingDays();
    }

    /**
     * Get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApplications");
        return leaveApplicationRepository.findAll(pageable)
            .map(leaveApplicationMapper::toDto);
    }


    /**
     * Get one leaveApplication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LeaveApplicationDTO> findOne(Long id) {
        log.debug("Request to get LeaveApplication : {}", id);
        return leaveApplicationRepository.findById(id)
            .map(leaveApplicationMapper::toDto);
    }

    /**
     * Delete the leaveApplication by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeaveApplication : {}", id);
        leaveApplicationRepository.deleteById(id);
        leaveApplicationSearchRepository.deleteById(id);
    }

    /**
     * Search for the leaveApplication corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeaveApplications for query {}", query);
        return leaveApplicationSearchRepository.search(queryStringQuery(query), pageable)
            .map(leaveApplicationMapper::toDto);
    }
}
