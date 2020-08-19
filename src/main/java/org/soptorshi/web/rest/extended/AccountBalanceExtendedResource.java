package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import net.sf.cglib.core.Local;
import org.soptorshi.domain.enumeration.BalanceSheetFetchType;
import org.soptorshi.service.BalanceSheetExcelReportService;
import org.soptorshi.service.BalanceSheetReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class AccountBalanceExtendedResource {
    private final BalanceSheetReportService balanceSheetReportService;
    private final BalanceSheetExcelReportService balanceSheetExcelReportService;

    public AccountBalanceExtendedResource(BalanceSheetReportService balanceSheetReportService, BalanceSheetExcelReportService balanceSheetExcelReportService) {
        this.balanceSheetReportService = balanceSheetReportService;
        this.balanceSheetExcelReportService = balanceSheetExcelReportService;
    }

    @GetMapping(value="/extended/account-balances/balance-sheet/{fetchType}/{asOnDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateBalanceSheetPdbReport(
        @PathVariable("fetchType")String balanceSheetFetchType,
        @PathVariable("asOnDate") String asOnDate
    ) throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = balanceSheetReportService.createBalanceSheetReport(balanceSheetFetchType.equals("0")?BalanceSheetFetchType.SUMMARIZED: BalanceSheetFetchType.DETAILED, LocalDate.parse(asOnDate));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/account-balances");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value="/extended/account-balances/balance-sheet/{asOnDate}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> generateBalanceSheetExcelReport(
        @PathVariable("asOnDate") String asOnDate
    ) throws Exception, DocumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ByteArrayInputStream byteArrayInputStream = balanceSheetExcelReportService.createBalanceSheetReport(LocalDate.parse(asOnDate,dateTimeFormatter));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/account-balances");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
