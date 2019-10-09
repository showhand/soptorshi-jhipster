package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.SystemGroupMapQueryService;
import org.soptorshi.service.SystemGroupMapService;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.extended.SystemGroupMapExtendedService;
import org.soptorshi.web.rest.SystemGroupMapResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class SystemGroupMapExtendedResource {
    private final Logger log = LoggerFactory.getLogger(SystemGroupMapExtendedResource.class);

    private static final String ENTITY_NAME = "systemGroupMap";

    private final SystemGroupMapExtendedService systemGroupMapService;

    private final SystemGroupMapQueryService systemGroupMapQueryService;

    public SystemGroupMapExtendedResource(SystemGroupMapExtendedService systemGroupMapService, SystemGroupMapQueryService systemGroupMapQueryService) {
        this.systemGroupMapService = systemGroupMapService;
        this.systemGroupMapQueryService = systemGroupMapQueryService;
    }

    @PostMapping("/system-group-maps")
    public ResponseEntity<SystemGroupMapDTO> createSystemGroupMap(@RequestBody SystemGroupMapDTO systemGroupMapDTO) throws URISyntaxException {
        log.debug("REST request to save SystemGroupMap : {}", systemGroupMapDTO);
        if (systemGroupMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemGroupMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemGroupMapDTO result = systemGroupMapService.save(systemGroupMapDTO);
        return ResponseEntity.created(new URI("/api/system-group-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/system-group-maps")
    public ResponseEntity<SystemGroupMapDTO> updateSystemGroupMap(@RequestBody SystemGroupMapDTO systemGroupMapDTO) throws URISyntaxException {
        log.debug("REST request to update SystemGroupMap : {}", systemGroupMapDTO);
        if (systemGroupMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemGroupMapDTO result = systemGroupMapService.save(systemGroupMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemGroupMapDTO.getId().toString()))
            .body(result);
    }
}
