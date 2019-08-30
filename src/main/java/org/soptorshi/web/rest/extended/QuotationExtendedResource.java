package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.QuotationQueryService;
import org.soptorshi.service.QuotationService;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.extended.QuotationDetailsExtendedService;
import org.soptorshi.service.extended.QuotationExtendedService;
import org.soptorshi.web.rest.QuotationResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class QuotationExtendedResource  {

    private final Logger log = LoggerFactory.getLogger(QuotationExtendedResource.class);

    private static final String ENTITY_NAME = "quotation";

    private final QuotationExtendedService quotationExtendedService;

    private final QuotationQueryService quotationQueryService;

    public QuotationExtendedResource(QuotationExtendedService quotationExtendedService, QuotationQueryService quotationQueryService) {
        this.quotationExtendedService = quotationExtendedService;
        this.quotationQueryService = quotationQueryService;
    }


    @PutMapping("/quotations")
    public ResponseEntity<QuotationDTO> updateQuotation(@Valid @RequestBody QuotationDTO quotationDTO) throws URISyntaxException {
        log.debug("REST request to update Quotation : {}", quotationDTO);
        if (quotationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuotationDTO result = quotationExtendedService.save(quotationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationDTO.getId().toString()))
            .body(result);
    }
}
