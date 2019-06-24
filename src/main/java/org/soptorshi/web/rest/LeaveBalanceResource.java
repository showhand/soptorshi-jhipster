package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.LeaveBalanceService;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LeaveBalanceResource {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceResource.class);

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceResource(LeaveBalanceService leaveBalanceService){
        this.leaveBalanceService = leaveBalanceService;
    }

    @GetMapping("/leave-balance")
    public ResponseEntity<List<LeaveBalanceDTO>> getAllLeaveBalance(String employeeId) {
        log.debug("REST request to get LeaveBalance by employeeId: {}", employeeId);
        return ResponseEntity.ok().body(leaveBalanceService.calculateLeaveBalance(employeeId, 2018));
    }

    @GetMapping("/leave-balance/{employeeId}")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalance(@PathVariable String employeeId) {
        log.debug("REST request to get LeaveBalance : {}", employeeId);
        return ResponseEntity.ok().body(leaveBalanceService.calculateLeaveBalance(employeeId, 2019));
    }
}
