package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.Manager;
import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.domain.enumeration.WeekendStatus;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

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

    private final HolidayTypeExtendedService holidayTypeExtendedService;

    private final HolidayExtendedService holidayExtendedService;

    private final WeekendExtendedService weekendExtendedService;

    public LeaveApplicationExtendedService(LeaveApplicationRepository leaveApplicationRepository, LeaveApplicationMapper leaveApplicationMapper, LeaveApplicationSearchRepository leaveApplicationSearchRepository,
                                           LeaveBalanceService leaveBalanceService, EmployeeRepository employeeRepository,
                                           ManagerRepository managerRepository,
                                           HolidayTypeExtendedService holidayTypeExtendedService,
                                           HolidayExtendedService holidayExtendedService,
                                           WeekendExtendedService weekendExtendedService) {
        super(leaveApplicationRepository, leaveApplicationMapper, leaveApplicationSearchRepository);
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
        this.leaveApplicationSearchRepository = leaveApplicationSearchRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
        this.holidayTypeExtendedService = holidayTypeExtendedService;
        this.holidayExtendedService = holidayExtendedService;
        this.weekendExtendedService = weekendExtendedService;
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
        Employee employee = employeeRepository.getOne(leaveApplicationDTO.getEmployeesId());

        Optional<String> loggedInUserId = SecurityUtils.getCurrentUserLogin();
        if (loggedInUserId.isPresent()) {
            Optional<Employee> loggedInEmployee = employeeRepository.findByEmployeeId(loggedInUserId.get());
            if (loggedInEmployee.isPresent()) {
                Optional<Manager> manager = managerRepository.getByParentEmployeeIdAndEmployee(employee.getId(), loggedInEmployee.get());
                if (manager.isPresent()) {
                    if (!isValid(leaveApplicationDTO)) {
                        throw new BadRequestAlertException("application not valid!!", "leaveApplication", "idnull");
                    } else {
                        log.debug("Request to save LeaveApplication : {}", leaveApplicationDTO);
                        LeaveApplication leaveApplication = leaveApplicationMapper.toEntity(leaveApplicationDTO);
                        leaveApplication = leaveApplicationRepository.save(leaveApplication);
                        LeaveApplicationDTO result = leaveApplicationMapper.toDto(leaveApplication);
                        leaveApplicationSearchRepository.save(leaveApplication);
                        return result;
                    }
                } else {
                    if (loggedInEmployee.get().equals(employee)) {
                        if (!isValid(leaveApplicationDTO)) {
                            throw new BadRequestAlertException("application not valid!!", "leaveApplication", "idnull");
                        } else {
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

    private boolean isValid(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Validating LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getStatus().equals(LeaveStatus.REJECTED)) {
            throw new BadRequestAlertException("application already rejected!!", "leaveApplication", "idnull");
        } else {
            Employee employee = employeeRepository.getOne(leaveApplicationDTO.getEmployeesId());
            LeaveBalanceDTO leaveBalance = leaveBalanceService
                .calculateLeaveBalance(employee.getEmployeeId(), leaveApplicationDTO.getFromDate().getYear(),
                    leaveApplicationDTO.getLeaveTypesId());
            return leaveApplicationDTO.getNumberOfDays() <= leaveBalance.getRemainingDays();
        }
    }

    public Integer calcDiff(LocalDate fromDate, LocalDate toDate) {
        if(toDate.compareTo(fromDate) >= 0) {
            long daysBetween = DAYS.between(fromDate, toDate) + 1;
            ArrayList<LocalDate> localDates = new ArrayList<LocalDate>();
            LocalDate temp = fromDate;
            while (temp.compareTo(toDate) <= 0) {
                localDates.add(temp);
                temp = temp.plusDays(1);
            }
            Optional<Weekend> weekend = weekendExtendedService.getWeekendByStatus(WeekendStatus.ACTIVE);
            ArrayList<LocalDate> matchedDates = new ArrayList<>();
            if(weekend.isPresent()) {
                for (LocalDate localDate : localDates) {
                    DayOfWeek dayOfWeek = localDate.getDayOfWeek();
                    String day = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).trim().toUpperCase();

                    if (weekend.get().getDay1() != null && day.equalsIgnoreCase(weekend.get().getDay1().toString().trim().toUpperCase())) {
                        daysBetween = daysBetween - 1;
                        matchedDates.add(localDate);
                    }
                    if (weekend.get().getDay2() != null && day.equalsIgnoreCase(weekend.get().getDay2().toString().trim().toUpperCase())) {
                        daysBetween = daysBetween - 1;
                        matchedDates.add(localDate);
                    }
                    if (weekend.get().getDay3() != null && day.equalsIgnoreCase(weekend.get().getDay3().toString().trim().toUpperCase())) {
                        daysBetween = daysBetween - 1;
                        matchedDates.add(localDate);
                    }
                }
            }

            for(LocalDate localDate: matchedDates) {
                localDates.remove(localDate);
            }
            ArrayList<LocalDate> holidays = holidayExtendedService.getAllHolidayDates(fromDate.getYear());
            for (LocalDate holiday : holidays) {
                for (LocalDate localDate : localDates) {
                    if (localDate.compareTo(holiday) == 0) {
                        daysBetween = daysBetween - 1;
                    }
                }
            }
            return (int) daysBetween;
        }
        return -1;
    }
}
