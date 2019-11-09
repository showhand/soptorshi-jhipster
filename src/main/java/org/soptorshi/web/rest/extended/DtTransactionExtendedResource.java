package org.soptorshi.web.rest.extended;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.enumeration.GeneralLedgerFetchType;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.DtTransactionService;
import org.soptorshi.service.GeneralLedgerReportService;
import org.soptorshi.service.VoucherReport;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.extended.DtTransactionExtendedService;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.DtTransactionResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/extended")
public class DtTransactionExtendedResource {
    private final Logger log = LoggerFactory.getLogger(DtTransactionExtendedResource.class);

    private static final String ENTITY_NAME = "dtTransaction";

    private final DtTransactionExtendedService dtTransactionService;

    private final DtTransactionQueryService dtTransactionQueryService;

    private final VoucherReport voucherReport;

    private final GeneralLedgerReportService generalLedgerReportService;

    public DtTransactionExtendedResource(DtTransactionExtendedService dtTransactionService, DtTransactionQueryService dtTransactionQueryService, VoucherReport voucherReport, GeneralLedgerReportService generalLedgerReportService) {
        this.dtTransactionService = dtTransactionService;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.voucherReport = voucherReport;
        this.generalLedgerReportService = generalLedgerReportService;
    }

    /**
     * POST  /dt-transactions : Create a new dtTransaction.
     *
     * @param dtTransactionDTO the dtTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dtTransactionDTO, or with status 400 (Bad Request) if the dtTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dt-transactions")
    public ResponseEntity<DtTransactionDTO> createDtTransaction(@RequestBody DtTransactionDTO dtTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save DtTransaction : {}", dtTransactionDTO);
        if (dtTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dtTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DtTransactionDTO result = dtTransactionService.save(dtTransactionDTO);
        return ResponseEntity.created(new URI("/api/dt-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dt-transactions : Updates an existing dtTransaction.
     *
     * @param dtTransactionDTO the dtTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dtTransactionDTO,
     * or with status 400 (Bad Request) if the dtTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the dtTransactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dt-transactions")
    public ResponseEntity<DtTransactionDTO> updateDtTransaction(@RequestBody DtTransactionDTO dtTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update DtTransaction : {}", dtTransactionDTO);
        if (dtTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DtTransactionDTO result = dtTransactionService.save(dtTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dtTransactionDTO.getId().toString()))
            .body(result);
    }
    @GetMapping(value="/dt-transactions/voucher-report/{voucherName}/{voucherNo}/{voucherDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateVoucherReport(
        @PathVariable("voucherName") String voucherName,
        @PathVariable("voucherNo") String voucherNo,
        @PathVariable("voucherDate")String voucherdate
        ) throws Exception, DocumentException{
        ByteArrayInputStream byteArrayInputStream = voucherReport.createVoucherReport(voucherName, voucherNo, LocalDate.parse(voucherdate) );
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/dt-transactions");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value="/dt-transactions/general-ledger-report/{generalLedgerFetchType}/{accountId}/{fromDate}/{toDate}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateGeneralLedgerReport(
        @PathVariable("generalLedgerFetchType") String generalLedgerFetchType,
        @PathVariable("accountId") Long accountId,
        @PathVariable("fromDate")String fromDate,
        @PathVariable("toDate") String toDate
    ) throws Exception, DocumentException{
        ByteArrayInputStream byteArrayInputStream = generalLedgerReportService.createGeneralLedger(GeneralLedgerFetchType.valueOf(generalLedgerFetchType),
            accountId.equals(9999)?null: accountId, LocalDate.parse(fromDate), LocalDate.parse(toDate) );
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/api/dt-transactions");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
