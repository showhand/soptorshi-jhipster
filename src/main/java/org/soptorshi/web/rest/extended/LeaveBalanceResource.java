package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.soptorshi.service.extended.LeaveBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeaveBalanceResource {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceResource.class);

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceResource(LeaveBalanceService leaveBalanceService){
        this.leaveBalanceService = leaveBalanceService;
    }

    @GetMapping("/leave-balance/year/{queryYear}")
    public ResponseEntity<Map<String, List<LeaveBalanceDTO>>> getAllLeaveBalance(@PathVariable  int queryYear) {
        log.debug("REST request to get all LeaveBalance by query year : {}", queryYear);
        return ResponseEntity.ok().body(leaveBalanceService.calculateLeaveBalance(queryYear));
    }

    @GetMapping("/leave-balance/employee/{employeeId}/year/{queryYear}")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalance(@PathVariable String employeeId, @PathVariable int queryYear) {
        log.debug("REST request to get LeaveBalance : {} and query year: {}", employeeId, queryYear);
        return ResponseEntity.ok().body(leaveBalanceService.calculateLeaveBalance(employeeId, queryYear));
    }

    @GetMapping("/leave-balance/employee/{employeeId}/year/{queryYear}/leave-type/{leaveType}")
    public ResponseEntity<LeaveBalanceDTO> getLeaveBalance(@PathVariable String employeeId, @PathVariable int queryYear, @PathVariable Long leaveType) {
        log.debug("REST request to get LeaveBalance: {} and query year: {} and leave type: {}", employeeId, queryYear, leaveType);
        return ResponseEntity.ok().body(leaveBalanceService.calculateLeaveBalance(employeeId, queryYear, leaveType));
    }
}
