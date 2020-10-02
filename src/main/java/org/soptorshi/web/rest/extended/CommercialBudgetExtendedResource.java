package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialBudgetQueryService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.extended.CommercialBudgetExtendedService;
import org.soptorshi.web.rest.CommercialBudgetResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CommercialBudget.
 */
@RestController
@RequestMapping("/api/extended")
public class CommercialBudgetExtendedResource extends CommercialBudgetResource {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetExtendedResource.class);

    private static final String ENTITY_NAME = "commercialBudget";

    private final CommercialBudgetExtendedService commercialBudgetExtendedService;

    public CommercialBudgetExtendedResource(CommercialBudgetExtendedService commercialBudgetExtendedService, CommercialBudgetQueryService commercialBudgetQueryService) {
        super(commercialBudgetExtendedService, commercialBudgetQueryService);
        this.commercialBudgetExtendedService = commercialBudgetExtendedService;
    }

    @PostMapping("/commercial-budgets")
    public ResponseEntity<CommercialBudgetDTO> createCommercialBudget(@Valid @RequestBody CommercialBudgetDTO commercialBudgetDTO) throws URISyntaxException {
        log.debug("REST request to save CommercialBudget : {}", commercialBudgetDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialBudgetDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercialBudget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercialBudgetDTO result = commercialBudgetExtendedService.save(commercialBudgetDTO);
        return ResponseEntity.created(new URI("/api/commercial-budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/commercial-budgets")
    public ResponseEntity<CommercialBudgetDTO> updateCommercialBudget(@Valid @RequestBody CommercialBudgetDTO commercialBudgetDTO) throws URISyntaxException {
        log.debug("REST request to update CommercialBudget : {}", commercialBudgetDTO);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_MANAGER)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        if (commercialBudgetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercialBudgetDTO result = commercialBudgetExtendedService.save(commercialBudgetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commercialBudgetDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/commercial-budgets/{id}")
    public ResponseEntity<Void> deleteCommercialBudget(@PathVariable Long id) {
        log.debug("REST request to delete CommercialBudget : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.COMMERCIAL_ADMIN)) {
            throw new BadRequestAlertException("Access Denied", ENTITY_NAME, "invalidaccess");
        }
        commercialBudgetExtendedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
