package org.soptorshi.service.impl;

import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.enumeration.LeaveStatus;
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

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
@Transactional
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    protected LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    protected LeaveTypeRepository leaveTypeRepository;

    public List<LeaveBalanceDTO> calculateLeaveBalance(String employeeId) {
        LocalDate localDate = LocalDate.now();
        int currentYear = localDate.getYear();
        int lengthOftheYear = localDate.lengthOfYear();
        LocalDate remaingDAysofTheYear = localDate.withYear(currentYear);
        LocalDate firstDay = localDate.with(firstDayOfYear()); // 2015-01-01
        LocalDate lastDay = localDate.with(lastDayOfYear());

        System.out.println("Current year ====================== " + currentYear + " Lenght if the year ========= " + lengthOftheYear);

        List<LeaveType> leaveTypes = getAllLeaveType();
        List<LeaveBalanceDTO> leaveBalanceDtos = new ArrayList<>();

        for (LeaveType leaveType : leaveTypes) {
            if(leaveType.getMaximumNumberOfDays() != null) {
                List<LeaveApplication> leaveApplications = getAppliedLeave(employeeId, leaveType);
                LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
                leaveBalanceDto.setEmployeeId(employeeId);
                leaveBalanceDto.setRemainingDays(leaveApplications.size() == 0 ? leaveType.getMaximumNumberOfDays() :
                    leaveType.getMaximumNumberOfDays() - leaveApplications.size());
                leaveBalanceDto.setLeaveTypeId(leaveType.getId());
                leaveBalanceDto.setLeaveTypeName(leaveType.getName());
                leaveBalanceDtos.add(leaveBalanceDto);
            }
        }
        return leaveBalanceDtos;
    }

    private List<LeaveType> getAllLeaveType() {
        return leaveTypeRepository.findAll();
    }

    private List<LeaveApplication> getAppliedLeave(final String employeeId, final LeaveType leaveType) {
        return leaveApplicationRepository.
            findByEmployeeIdAndLeaveTypesAndStatus(employeeId, leaveType, LeaveStatus.ACCEPTED);
    }
}
