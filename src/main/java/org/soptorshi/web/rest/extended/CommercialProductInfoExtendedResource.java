package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialProductInfoQueryService;
import org.soptorshi.service.extended.CommercialProductInfoExtendedService;
import org.soptorshi.web.rest.CommercialProductInfoResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CommercialProductInfo.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialProductInfoExtendedResource extends CommercialProductInfoResource {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoExtendedResource.class);

    private static final String ENTITY_NAME = "commercialProductInfo";

    public CommercialProductInfoExtendedResource(CommercialProductInfoExtendedService commercialProductInfoService, CommercialProductInfoQueryService commercialProductInfoQueryService) {
        super(commercialProductInfoService, commercialProductInfoQueryService);
    }

    /**
     * DELETE  /commercial-product-infos/:id : delete the "id" commercialProductInfo.
     *
     * @param id the id of the commercialProductInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-product-infos/{id}")
    public ResponseEntity<Void> deleteCommercialProductInfo(@PathVariable Long id) {
        log.debug("REST request to delete CommercialProductInfo : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
