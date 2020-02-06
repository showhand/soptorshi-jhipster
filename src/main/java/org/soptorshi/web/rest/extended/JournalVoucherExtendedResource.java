package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.JournalVoucherQueryService;
import org.soptorshi.service.JournalVoucherService;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.extended.JournalVoucherExtendedService;
import org.soptorshi.web.rest.JournalVoucherResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/extended")
public class JournalVoucherExtendedResource {
    private final Logger log = LoggerFactory.getLogger(JournalVoucherExtendedResource.class);

    private static final String ENTITY_NAME = "journalVoucher";

    private final JournalVoucherExtendedService journalVoucherService;

    private final JournalVoucherQueryService journalVoucherQueryService;

    public JournalVoucherExtendedResource(JournalVoucherExtendedService journalVoucherService, JournalVoucherQueryService journalVoucherQueryService) {
        this.journalVoucherService = journalVoucherService;
        this.journalVoucherQueryService = journalVoucherQueryService;
    }

    @PostMapping("/journal-vouchers")
    public ResponseEntity<JournalVoucherDTO> createJournalVoucher(@RequestBody JournalVoucherDTO journalVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save JournalVoucher : {}", journalVoucherDTO);
        if (journalVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new journalVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JournalVoucherDTO result = journalVoucherService.save(journalVoucherDTO);
        return ResponseEntity.created(new URI("/api/journal-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/journal-vouchers")
    public ResponseEntity<JournalVoucherDTO> updateJournalVoucher(@RequestBody JournalVoucherDTO journalVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update JournalVoucher : {}", journalVoucherDTO);
        if (journalVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JournalVoucherDTO result = journalVoucherService.save(journalVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journalVoucherDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/journal-vouchers/voucherNo/{voucherNo}")
    public ResponseEntity<JournalVoucherDTO> getJournalVoucher(@PathVariable String voucherNo) {
        log.debug("REST request to get JournalVoucher by voucher no: {}", voucherNo);
        Optional<JournalVoucherDTO> journalVoucherDTO = journalVoucherService.getByVoucherNo(voucherNo);
        return ResponseUtil.wrapOrNotFound(journalVoucherDTO);
    }

}
