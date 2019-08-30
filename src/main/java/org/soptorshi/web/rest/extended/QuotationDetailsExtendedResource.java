package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.extended.QuotationDetailsExtendedRepository;
import org.soptorshi.service.QuotationDetailsQueryService;
import org.soptorshi.service.QuotationDetailsService;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.extended.QuotationDetailsExtendedService;
import org.soptorshi.web.rest.QuotationDetailsResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api/extended")
public class QuotationDetailsExtendedResource{
    private final Logger log = LoggerFactory.getLogger(QuotationDetailsExtendedRepository.class);
    private static final String ENTITY_NAME = "quotationDetails";

    private final QuotationDetailsService quotationDetailsService;

    private final QuotationDetailsQueryService quotationDetailsQueryService;
    private QuotationDetailsExtendedService quotationDetailsExtendedService;
    public QuotationDetailsExtendedResource(QuotationDetailsService quotationDetailsService,
                                            QuotationDetailsQueryService quotationDetailsQueryService,
                                            QuotationDetailsExtendedService quotationDetailsExtendedService) {
        this.quotationDetailsService = quotationDetailsService;
        this.quotationDetailsQueryService = quotationDetailsQueryService;
        this.quotationDetailsExtendedService = quotationDetailsExtendedService;
    }


    /**
     * POST  /quotation-details : Create a new quotationDetails.
     *
     * @param quotationDetailsDTO the quotationDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotationDetailsDTO, or with status 400 (Bad Request) if the quotationDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quotation-details")
    public ResponseEntity<QuotationDetailsDTO> createQuotationDetailsExtended(@RequestBody QuotationDetailsDTO quotationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save QuotationDetails : {}", quotationDetailsDTO);
        if (quotationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new quotationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotationDetailsDTO result = quotationDetailsExtendedService.save(quotationDetailsDTO);
        return ResponseEntity.created(new URI("/api/quotation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotation-details : Updates an existing quotationDetails.
     *
     * @param quotationDetailsDTO the quotationDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotationDetailsDTO,
     * or with status 400 (Bad Request) if the quotationDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the quotationDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quotation-details")
    public ResponseEntity<QuotationDetailsDTO> updateQuotationDetailsExtended(@RequestBody QuotationDetailsDTO quotationDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update QuotationDetails : {}", quotationDetailsDTO);
        if (quotationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuotationDetailsDTO result = quotationDetailsExtendedService.save(quotationDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotationDetailsDTO.getId().toString()))
            .body(result);
    }
}
