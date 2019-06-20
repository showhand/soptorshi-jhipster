package org.soptorshi.service;

import org.soptorshi.service.dto.LeaveBalanceDTO;

import java.util.List;

public interface LeaveBalanceService {

    List<LeaveBalanceDTO> calculateLeaveBalance(String employeeId);
}
