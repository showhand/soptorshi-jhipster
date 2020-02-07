package org.soptorshi.web.rest.extended;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ContraVoucherQueryService;
import org.soptorshi.service.ContraVoucherService;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.extended.ContraVoucherExtendedService;
import org.soptorshi.web.rest.ContraVoucherResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/extended")
public class ContraVoucherExtendedResource {
    private final Logger log = LoggerFactory.getLogger(ContraVoucherExtendedResource.class);

    private static final String ENTITY_NAME = "contraVoucher";

    private final ContraVoucherExtendedService contraVoucherService;

    private final ContraVoucherQueryService contraVoucherQueryService;

    public ContraVoucherExtendedResource(ContraVoucherExtendedService contraVoucherService, ContraVoucherQueryService contraVoucherQueryService) {
        this.contraVoucherService = contraVoucherService;
        this.contraVoucherQueryService = contraVoucherQueryService;
    }

    /**
     * POST  /contra-vouchers : Create a new contraVoucher.
     *
     * @param contraVoucherDTO the contraVoucherDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contraVoucherDTO, or with status 400 (Bad Request) if the contraVoucher has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contra-vouchers")
    public ResponseEntity<ContraVoucherDTO> createContraVoucher(@RequestBody ContraVoucherDTO contraVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save ContraVoucher : {}", contraVoucherDTO);
        if (contraVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new contraVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContraVoucherDTO result = contraVoucherService.save(contraVoucherDTO);
        return ResponseEntity.created(new URI("/api/contra-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contra-vouchers : Updates an existing contraVoucher.
     *
     * @param contraVoucherDTO the contraVoucherDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contraVoucherDTO,
     * or with status 400 (Bad Request) if the contraVoucherDTO is not valid,
     * or with status 500 (Internal Server Error) if the contraVoucherDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contra-vouchers")
    public ResponseEntity<ContraVoucherDTO> updateContraVoucher(@RequestBody ContraVoucherDTO contraVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update ContraVoucher : {}", contraVoucherDTO);
        if (contraVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContraVoucherDTO result = contraVoucherService.save(contraVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contraVoucherDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/contra-vouchers/voucherNo/{voucherNo}")
    public ResponseEntity<ContraVoucherDTO> getReceiptVoucher(@PathVariable String voucherNo) {
        log.debug("REST request to get ContraVoucher by voucher no: {}", voucherNo);
        Optional<ContraVoucherDTO> contraVoucherDTO = contraVoucherService.findByVoucherNo(voucherNo);
        return ResponseUtil.wrapOrNotFound(contraVoucherDTO);
    }

}
