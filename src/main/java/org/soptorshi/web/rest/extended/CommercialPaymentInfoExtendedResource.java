package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.extended.CommercialPaymentInfoExtendedService;
import org.soptorshi.web.rest.CommercialPaymentInfoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CommercialPaymentInfo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialPaymentInfoExtendedResource extends CommercialPaymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialPaymentInfo";

    public CommercialPaymentInfoExtendedResource(CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService) {
        super(commercialPaymentInfoExtendedService);
    }

    /**
     * DELETE  /commercial-payment-infos/:id : delete the "id" commercialPaymentInfo.
     *
     * @param id the id of the commercialPaymentInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-payment-infos/{id}")
    public ResponseEntity<Void> deleteCommercialPaymentInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialPaymentInfo : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
