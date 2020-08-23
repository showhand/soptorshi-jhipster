package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ChartOfAccountsExcelReportService;
import org.soptorshi.service.ChartsOfAccountReportService;
import org.soptorshi.service.MstAccountQueryService;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.extended.CashFlowService;
import org.soptorshi.service.extended.MstAccountExtendedService;
import org.soptorshi.service.extended.ProfitLossService;
import org.soptorshi.web.rest.MstAccountResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/extended")
public class MstAccountExtendedResource {
    private final Logger log = LoggerFactory.getLogger(MstAccountExtendedResource.class);

    private static final String ENTITY_NAME = "mstAccount";

    private final MstAccountExtendedService mstAccountService;

    private final MstAccountQueryService mstAccountQueryService;

    private final ChartsOfAccountReportService chartsOfAccountReportService;

    private final ChartOfAccountsExcelReportService chartOfAccountsExcelReportService;
    private final ProfitLossService profitLossService;
    private final CashFlowService cashFlowService;

    public MstAccountExtendedResource(MstAccountExtendedService mstAccountService, MstAccountQueryService mstAccountQueryService, ChartsOfAccountReportService chartsOfAccountReportService, ChartOfAccountsExcelReportService chartOfAccountsExcelReportService, ProfitLossService profitLossService, CashFlowService cashFlowService) {
        this.mstAccountService = mstAccountService;
        this.mstAccountQueryService = mstAccountQueryService;
        this.chartsOfAccountReportService = chartsOfAccountReportService;
        this.chartOfAccountsExcelReportService = chartOfAccountsExcelReportService;
        this.profitLossService = profitLossService;
        this.cashFlowService = cashFlowService;
    }

    /**
     * POST  /mst-accounts : Create a new mstAccount.
     *
     * @param mstAccountDTO the mstAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mstAccountDTO, or with status 400 (Bad Request) if the mstAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mst-accounts")
    public ResponseEntity<MstAccountDTO> createMstAccount(@RequestBody MstAccountDTO mstAccountDTO) throws URISyntaxException {
        log.debug("REST request to save MstAccount : {}", mstAccountDTO);
        if (mstAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new mstAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MstAccountDTO result = mstAccountService.save(mstAccountDTO);
        return ResponseEntity.created(new URI("/api/mst-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mst-accounts : Updates an existing mstAccount.
     *
     * @param mstAccountDTO the mstAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mstAccountDTO,
     * or with status 400 (Bad Request) if the mstAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the mstAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mst-accounts")
    public ResponseEntity<MstAccountDTO> updateMstAccount(@RequestBody MstAccountDTO mstAccountDTO) throws URISyntaxException {
        log.debug("REST request to update MstAccount : {}", mstAccountDTO);
        if (mstAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MstAccountDTO result = mstAccountService.save(mstAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mstAccountDTO.getId().toString()))
            .body(result);
    }

    @GetMapping(value = "/mst-accounts/charts-of-account", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateChartsOfAccounts() throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = chartsOfAccountReportService.createChartsOrAccountReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/extended/charts-of-account");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
    @GetMapping(value = "/mst-accounts/charts-of-account/excel", produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> generateChartsOfAccountsExcelFormat() throws Exception, DocumentException {
        ByteArrayInputStream byteArrayInputStream = chartOfAccountsExcelReportService.createChartsOrAccountReport();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/extended/charts-of-account");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(byteArrayInputStream));
    }


    @GetMapping(value = "/mst-accounts/profit-and-loss/excel/{fromDate}/{toDate}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> generateProfitAndLossExcelFormat(@PathVariable("fromDate")String fromDate, @PathVariable("toDate") String toDate) throws Exception, DocumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ByteArrayInputStream byteArrayInputStream = profitLossService.createReport(LocalDate.parse(fromDate, dateTimeFormatter), LocalDate.parse(toDate,dateTimeFormatter));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/extended/charts-of-account");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(byteArrayInputStream));
    }


    @GetMapping(value = "/mst-accounts/cash-flow/excel/{fromDate}/{toDate}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> generateCashFlowExcelFormat(@PathVariable("fromDate")String fromDate, @PathVariable("toDate") String toDate) throws Exception, DocumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ByteArrayInputStream byteArrayInputStream = cashFlowService.createReport(LocalDate.parse(fromDate, dateTimeFormatter), LocalDate.parse(toDate,dateTimeFormatter));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/extended/charts-of-account");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(byteArrayInputStream));
    }
}

