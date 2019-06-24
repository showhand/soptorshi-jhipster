package org.soptorshi.service.impl;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.soptorshi.repository.LeaveTypeRepository;
import org.soptorshi.service.LeaveBalanceService;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
@Transactional
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    protected LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    protected LeaveTypeRepository leaveTypeRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    public List<LeaveBalanceDTO> calculateLeaveBalance(String employeeId, int queryYear) {

        Optional<Employee> employee = employeeRepository.findByEmployeeId("701001");

        if (employee.isPresent()) {

            LocalDate localDate = LocalDate.now();
            LocalDate joiningDate = employee.get().getJoiningDate();

            LocalDate firstDayOfTheJoiningYearYear = joiningDate.with(firstDayOfYear()); // 2015-01-01
            LocalDate lastDayOfTheJoiningYearYear = joiningDate.with(lastDayOfYear()); // 2015-12-31

            LocalDate firstDayOfTheCurrentYearYear = joiningDate.with(firstDayOfYear());
            LocalDate lastDayOfTheCurrentYearYear = joiningDate.with(lastDayOfYear());

            int currentYear = localDate.getYear(); // 2019
            int joiningYear = joiningDate.getYear(); // 2017

            int lengthOfTheCurrentYear = localDate.lengthOfYear(); // 365 or 366
            int lengthOfTheJoiningYear = localDate.lengthOfYear(); // 365 or 366

            int currentDayOfYear = localDate.getDayOfYear(); // 177
            int joiningDayOfYear = joiningDate.getDayOfYear(); // 155

            int remainingDaysOfTheJoiningYear = lengthOfTheJoiningYear - joiningDayOfYear;

            List<LeaveType> leaveTypes = getAllLeaveType();
            List<LeaveBalanceDTO> leaveBalanceDtos = new ArrayList<>();


            for (LeaveType leaveType : leaveTypes) {
                if (leaveType.getMaximumNumberOfDays() != null) {
                    LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
                    leaveBalanceDto.setEmployeeId(employeeId);
                    if (queryYear == joiningYear) {
                        leaveBalanceDto.setTotalLeaveApplicableDays((remainingDaysOfTheJoiningYear * leaveType.getMaximumNumberOfDays()) / lengthOfTheJoiningYear);
                        List<LeaveApplication> leaveApplications = getAppliedLeave("701001", leaveType, firstDayOfTheJoiningYearYear, lastDayOfTheJoiningYearYear);
                        leaveBalanceDto.setRemainingDays(
                            ((remainingDaysOfTheJoiningYear * leaveType.getMaximumNumberOfDays()) / lengthOfTheJoiningYear) - leaveApplications.size());
                    } else if (queryYear > joiningYear) {
                        leaveBalanceDto.setTotalLeaveApplicableDays(leaveType.getMaximumNumberOfDays());
                        List<LeaveApplication> leaveApplications = getAppliedLeave("701001", leaveType, firstDayOfTheCurrentYearYear, lastDayOfTheCurrentYearYear);
                        leaveBalanceDto.setRemainingDays(
                            leaveType.getMaximumNumberOfDays() - leaveApplications.size());
                    } else {
                        leaveBalanceDto.setTotalLeaveApplicableDays(0);
                        leaveBalanceDto.setRemainingDays(0);
                    }
                    leaveBalanceDto.setLeaveTypeId(leaveType.getId());
                    leaveBalanceDto.setLeaveTypeName(leaveType.getName());
                    leaveBalanceDtos.add(leaveBalanceDto);
                }
            }
            return leaveBalanceDtos;
        }
        return null;
    }

    private List<LeaveType> getAllLeaveType() {
        return leaveTypeRepository.findAll();
    }

    private List<LeaveApplication> getAppliedLeave(final String employeeId, final LeaveType leaveType, final LocalDate fromDate, final LocalDate toDate) {
        return leaveApplicationRepository.
            findByEmployeeIdAndLeaveTypesAndStatusAndFromDateGreaterThanAndToDateLessThan(employeeId, leaveType, LeaveStatus.ACCEPTED, fromDate, toDate);
    }
}
