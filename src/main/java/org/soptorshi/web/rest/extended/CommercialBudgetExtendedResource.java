package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.CommercialBudgetQueryService;
import org.soptorshi.service.extended.CommercialBudgetExtendedService;
import org.soptorshi.web.rest.CommercialBudgetResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing CommercialBudget.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialBudgetExtendedResource extends CommercialBudgetResource {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetExtendedResource.class);

    private static final String ENTITY_NAME = "commercialBudget";

    public CommercialBudgetExtendedResource(CommercialBudgetExtendedService commercialBudgetService, CommercialBudgetQueryService commercialBudgetQueryService) {
        super(commercialBudgetService, commercialBudgetQueryService);
    }

    /**
     * DELETE  /commercial-budgets/:id : delete the "id" commercialBudget.
     *
     * @param id the id of the commercialBudgetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commercial-budgets/{id}")
    public ResponseEntity<Void> deleteCommercialBudget(@PathVariable Long id) {
        log.debug("REST request to delete CommercialBudget : {}", id);
        throw new BadRequestAlertException("Delete operation is not allowed", ENTITY_NAME, "idnull");
    }
}
