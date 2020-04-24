package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.service.extended.HolidayExtendedService;
import org.soptorshi.service.extended.LeaveApplicationExtendedService;
import org.soptorshi.service.extended.WeekendExtendedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * REST controller for managing HolidayType.
 */
@RestController
@RequestMapping("/api/extended")
public class PayrollSupportResource {

    private final Logger log = LoggerFactory.getLogger(PayrollSupportResource.class);

    private final HolidayExtendedService holidayExtendedService;

    private final WeekendExtendedService weekendExtendedService;

    private final LeaveApplicationExtendedService leaveApplicationExtendedService;

    public PayrollSupportResource(HolidayExtendedService holidayExtendedService,
                                  WeekendExtendedService weekendExtendedService, LeaveApplicationExtendedService leaveApplicationExtendedService) {
        this.holidayExtendedService = holidayExtendedService;
        this.weekendExtendedService = weekendExtendedService;
        this.leaveApplicationExtendedService = leaveApplicationExtendedService;
    }

    @GetMapping("/payroll-support/totalWorkDays/month/{month}/year/{year}")
    public ResponseEntity<Long> getTotalWorkingDays(@PathVariable MonthType month, @PathVariable int year) {
        log.debug("REST request to get total working days");
        return ResponseEntity.ok().body(new Long(26));
       /* Long totalDayInAMonth = totalDays(month, year);
        List<LocalDate> holidayDates = holidayExtendedService.getAllHolidayDates(month, year);
        List<LocalDate> weekendDates = weekendExtendedService.getAllWeekendDates(month, year);
        Long totalWorkday = totalDayInAMonth - (holidayDates.size() + weekendDates.size());

        return ResponseEntity.ok().body(totalWorkday);*/
    }

    @GetMapping("/payroll-support/totalPresent/employeeId/{employeeId}/month/{month}/year/{year}")
    public ResponseEntity<Long> getTotalPresent(@PathVariable Long employeeId, @PathVariable MonthType month, @PathVariable int year) {
        log.debug("REST request to get total working days");

        return ResponseEntity.ok().body(new Long(28));

        /*Long totalDayInAMonth = totalDays(month, year);
        List<LocalDate> holidayDates = holidayExtendedService.getAllHolidayDates(month, year);
        List<LocalDate> weekendDates = weekendExtendedService.getAllWeekendDates(month, year);
        List<LocalDate> withoutPayLeaveDates = leaveApplicationExtendedService.getWithoutPayLeaveDates(employeeId, month, year);
        List<LocalDate> withPayLeaveDates = leaveApplicationExtendedService.getWithPayLeaveDates(employeeId, month, year);
        Long totalPresent = totalDayInAMonth - (holidayDates.size() + weekendDates.size() + withoutPayLeaveDates.size() + withPayLeaveDates.size());

        return ResponseEntity.ok().body(totalPresent);*/
    }

    @GetMapping("/payroll-support/totalPresentWithoutPay/employeeId/{employeeId}/month/{month}/year/{year}")
    public ResponseEntity<Long> getTotalPresentWithoutPay(@PathVariable Long employeeId, @PathVariable MonthType month, @PathVariable int year) {
        log.debug("REST request to get total working days");

        return ResponseEntity.ok().body(new Long(26));


        /*Long totalDayInAMonth = totalDays(month, year);
        List<LocalDate> holidayDates = holidayExtendedService.getAllHolidayDates(month, year);
        List<LocalDate> weekendDates = weekendExtendedService.getAllWeekendDates(month, year);
        List<LocalDate> withPayLeaveDates = leaveApplicationExtendedService.getWithPayLeaveDates(employeeId, month, year);
        Long totalPresent = totalDayInAMonth - (holidayDates.size() + weekendDates.size() + withPayLeaveDates.size());

        return ResponseEntity.ok().body(totalPresent);*/
    }

    private Long totalDays(MonthType monthType, int year) {
        YearMonth yearMonth = YearMonth.of(year, monthType.ordinal() + 1);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate lastOfMonth = yearMonth.atEndOfMonth();

        return Duration.between(firstOfMonth, lastOfMonth).toDays();
    }
}
