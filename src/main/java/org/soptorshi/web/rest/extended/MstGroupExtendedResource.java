package org.soptorshi.web.rest.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.MstGroupQueryService;
import org.soptorshi.service.MstGroupService;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.extended.MstGroupExtendedService;
import org.soptorshi.web.rest.MstGroupResource;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/extended")
public class MstGroupExtendedResource {

    private final Logger log = LoggerFactory.getLogger(MstGroupExtendedResource.class);

    private static final String ENTITY_NAME = "mstGroup";

    private final MstGroupExtendedService mstGroupService;

    private final MstGroupQueryService mstGroupQueryService;

    public MstGroupExtendedResource(MstGroupExtendedService mstGroupService, MstGroupQueryService mstGroupQueryService) {
        this.mstGroupService = mstGroupService;
        this.mstGroupQueryService = mstGroupQueryService;
    }

    /**
     * POST  /mst-groups : Create a new mstGroup.
     *
     * @param mstGroupDTO the mstGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mstGroupDTO, or with status 400 (Bad Request) if the mstGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mst-groups")
    public ResponseEntity<MstGroupDTO> createMstGroup(@RequestBody MstGroupDTO mstGroupDTO) throws URISyntaxException {
        log.debug("REST request to save MstGroup : {}", mstGroupDTO);
        if (mstGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new mstGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MstGroupDTO result = mstGroupService.save(mstGroupDTO);
        return ResponseEntity.created(new URI("/api/mst-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mst-groups : Updates an existing mstGroup.
     *
     * @param mstGroupDTO the mstGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mstGroupDTO,
     * or with status 400 (Bad Request) if the mstGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the mstGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mst-groups")
    public ResponseEntity<MstGroupDTO> updateMstGroup(@RequestBody MstGroupDTO mstGroupDTO) throws URISyntaxException {
        log.debug("REST request to update MstGroup : {}", mstGroupDTO);
        if (mstGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MstGroupDTO result = mstGroupService.save(mstGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mstGroupDTO.getId().toString()))
            .body(result);
    }
}
