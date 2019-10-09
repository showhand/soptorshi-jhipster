package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SystemAccountMapQueryService;
import org.soptorshi.service.SystemAccountMapService;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.extended.SystemAccountMapExtendedService;
import org.soptorshi.web.rest.SystemAccountMapResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class SystemAccountMapExtendedResource {
    private final Logger log = LoggerFactory.getLogger(SystemAccountMapExtendedResource.class);

    private static final String ENTITY_NAME = "systemAccountMap";

    private final SystemAccountMapExtendedService systemAccountMapService;

    private final SystemAccountMapQueryService systemAccountMapQueryService;

    public SystemAccountMapExtendedResource(SystemAccountMapExtendedService systemAccountMapService, SystemAccountMapQueryService systemAccountMapQueryService) {
        this.systemAccountMapService = systemAccountMapService;
        this.systemAccountMapQueryService = systemAccountMapQueryService;
    }

    @PostMapping("/system-account-maps")
    public ResponseEntity<SystemAccountMapDTO> createSystemAccountMap(@RequestBody SystemAccountMapDTO systemAccountMapDTO) throws URISyntaxException {
        log.debug("REST request to save SystemAccountMap : {}", systemAccountMapDTO);
        if (systemAccountMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemAccountMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemAccountMapDTO result = systemAccountMapService.save(systemAccountMapDTO);
        return ResponseEntity.created(new URI("/api/system-account-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/system-account-maps")
    public ResponseEntity<SystemAccountMapDTO> updateSystemAccountMap(@RequestBody SystemAccountMapDTO systemAccountMapDTO) throws URISyntaxException {
        log.debug("REST request to update SystemAccountMap : {}", systemAccountMapDTO);
        if (systemAccountMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemAccountMapDTO result = systemAccountMapService.save(systemAccountMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemAccountMapDTO.getId().toString()))
            .body(result);
    }

}
