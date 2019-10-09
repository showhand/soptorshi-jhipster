package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.VoucherNumberControlQueryService;
import org.soptorshi.service.VoucherNumberControlService;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.extended.VoucherNumberControlExtendedService;
import org.soptorshi.web.rest.VoucherNumberControlResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class VoucherNumberControlExtendedResource {
    private final Logger log = LoggerFactory.getLogger(VoucherNumberControlExtendedResource.class);

    private static final String ENTITY_NAME = "voucherNumberControl";

    private final VoucherNumberControlExtendedService voucherNumberControlService;

    private final VoucherNumberControlQueryService voucherNumberControlQueryService;

    public VoucherNumberControlExtendedResource(VoucherNumberControlExtendedService voucherNumberControlService, VoucherNumberControlQueryService voucherNumberControlQueryService) {
        this.voucherNumberControlService = voucherNumberControlService;
        this.voucherNumberControlQueryService = voucherNumberControlQueryService;
    }

    @PostMapping("/voucher-number-controls")
    public ResponseEntity<VoucherNumberControlDTO> createVoucherNumberControl(@RequestBody VoucherNumberControlDTO voucherNumberControlDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherNumberControl : {}", voucherNumberControlDTO);
        if (voucherNumberControlDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherNumberControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherNumberControlDTO result = voucherNumberControlService.save(voucherNumberControlDTO);
        return ResponseEntity.created(new URI("/api/voucher-number-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/voucher-number-controls")
    public ResponseEntity<VoucherNumberControlDTO> updateVoucherNumberControl(@RequestBody VoucherNumberControlDTO voucherNumberControlDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherNumberControl : {}", voucherNumberControlDTO);
        if (voucherNumberControlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoucherNumberControlDTO result = voucherNumberControlService.save(voucherNumberControlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherNumberControlDTO.getId().toString()))
            .body(result);
    }

}
