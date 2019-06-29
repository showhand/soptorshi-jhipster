package org.soptorshi.service;

import org.soptorshi.service.dto.LeaveBalanceDTO;

import java.util.List;
import java.util.Map;

public interface LeaveBalanceService {

    Map<String, List<LeaveBalanceDTO>> calculateLeaveBalance(int year);

    List<LeaveBalanceDTO> calculateLeaveBalance(String employeeId, int year);

    LeaveBalanceDTO calculateLeaveBalance(String employeeId, int year, Long leaveType);
}
