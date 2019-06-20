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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    protected LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    protected LeaveTypeRepository leaveTypeRepository;

    public List<LeaveBalanceDTO> calculateLeaveBalance(String employeeId) {
        List<LeaveType> leaveTypes = getAllLeaveType();
        List<LeaveBalanceDTO> leaveBalanceDtos = new ArrayList<>();

        for (LeaveType leaveType : leaveTypes) {
            List<LeaveApplication> leaveApplications = getAppliedLeave(employeeId, leaveType);
            int totalLeave = leaveApplications.isEmpty() ? 0 : leaveApplications.size();
            LeaveBalanceDTO leaveBalanceDto = new LeaveBalanceDTO();
            leaveBalanceDto.setEmployeeId(employeeId);
            leaveBalanceDto.setRemainingDays(totalLeave);
            leaveBalanceDto.setLeaveTypeId(leaveType.getId());
            leaveBalanceDto.setLeaveTypeName(leaveType.getName());
            leaveBalanceDtos.add(leaveBalanceDto);
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
