package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.QuotationQueryService;
import org.soptorshi.service.QuotationService;
import org.soptorshi.service.extended.QuotationDetailsExtendedService;
import org.soptorshi.web.rest.QuotationResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extended")
public class QuotationExtendedResource extends QuotationResource {

    private final Logger log = LoggerFactory.getLogger(QuotationExtendedResource.class);


    public QuotationExtendedResource(QuotationService quotationService, QuotationQueryService quotationQueryService) {
        super(quotationService, quotationQueryService);
    }


}
