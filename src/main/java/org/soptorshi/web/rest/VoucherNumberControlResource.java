package org.soptorshi.web.rest;
import org.soptorshi.service.VoucherNumberControlService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.dto.VoucherNumberControlCriteria;
import org.soptorshi.service.VoucherNumberControlQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing VoucherNumberControl.
 */
@RestController
@RequestMapping("/api")
public class VoucherNumberControlResource {

    private final Logger log = LoggerFactory.getLogger(VoucherNumberControlResource.class);

    private static final String ENTITY_NAME = "voucherNumberControl";

    private final VoucherNumberControlService voucherNumberControlService;

    private final VoucherNumberControlQueryService voucherNumberControlQueryService;

    public VoucherNumberControlResource(VoucherNumberControlService voucherNumberControlService, VoucherNumberControlQueryService voucherNumberControlQueryService) {
        this.voucherNumberControlService = voucherNumberControlService;
        this.voucherNumberControlQueryService = voucherNumberControlQueryService;
    }

    /**
     * POST  /voucher-number-controls : Create a new voucherNumberControl.
     *
     * @param voucherNumberControlDTO the voucherNumberControlDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucherNumberControlDTO, or with status 400 (Bad Request) if the voucherNumberControl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voucher-number-controls")
    public ResponseEntity<VoucherNumberControlDTO> createVoucherNumberControl(@RequestBody VoucherNumberControlDTO voucherNumberControlDTO) throws URISyntaxException {
        log.debug("REST request to save VoucherNumberControl : {}", voucherNumberControlDTO);
        if (voucherNumberControlDTO.getId() != null) {
            throw new BadRequestAlertException("A new voucherNumberControl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VoucherNumberControlDTO result = voucherNumberControlService.save(voucherNumberControlDTO);
        return ResponseEntity.created(new URI("/api/voucher-number-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-number-controls : Updates an existing voucherNumberControl.
     *
     * @param voucherNumberControlDTO the voucherNumberControlDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucherNumberControlDTO,
     * or with status 400 (Bad Request) if the voucherNumberControlDTO is not valid,
     * or with status 500 (Internal Server Error) if the voucherNumberControlDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voucher-number-controls")
    public ResponseEntity<VoucherNumberControlDTO> updateVoucherNumberControl(@RequestBody VoucherNumberControlDTO voucherNumberControlDTO) throws URISyntaxException {
        log.debug("REST request to update VoucherNumberControl : {}", voucherNumberControlDTO);
        if (voucherNumberControlDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VoucherNumberControlDTO result = voucherNumberControlService.save(voucherNumberControlDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voucherNumberControlDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-number-controls : get all the voucherNumberControls.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of voucherNumberControls in body
     */
    @GetMapping("/voucher-number-controls")
    public ResponseEntity<List<VoucherNumberControlDTO>> getAllVoucherNumberControls(VoucherNumberControlCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VoucherNumberControls by criteria: {}", criteria);
        Page<VoucherNumberControlDTO> page = voucherNumberControlQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voucher-number-controls");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /voucher-number-controls/count : count all the voucherNumberControls.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/voucher-number-controls/count")
    public ResponseEntity<Long> countVoucherNumberControls(VoucherNumberControlCriteria criteria) {
        log.debug("REST request to count VoucherNumberControls by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherNumberControlQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /voucher-number-controls/:id : get the "id" voucherNumberControl.
     *
     * @param id the id of the voucherNumberControlDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucherNumberControlDTO, or with status 404 (Not Found)
     */
    @GetMapping("/voucher-number-controls/{id}")
    public ResponseEntity<VoucherNumberControlDTO> getVoucherNumberControl(@PathVariable Long id) {
        log.debug("REST request to get VoucherNumberControl : {}", id);
        Optional<VoucherNumberControlDTO> voucherNumberControlDTO = voucherNumberControlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherNumberControlDTO);
    }

    /**
     * DELETE  /voucher-number-controls/:id : delete the "id" voucherNumberControl.
     *
     * @param id the id of the voucherNumberControlDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voucher-number-controls/{id}")
    public ResponseEntity<Void> deleteVoucherNumberControl(@PathVariable Long id) {
        log.debug("REST request to delete VoucherNumberControl : {}", id);
        voucherNumberControlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/voucher-number-controls?query=:query : search for the voucherNumberControl corresponding
     * to the query.
     *
     * @param query the query of the voucherNumberControl search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/voucher-number-controls")
    public ResponseEntity<List<VoucherNumberControlDTO>> searchVoucherNumberControls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VoucherNumberControls for query {}", query);
        Page<VoucherNumberControlDTO> page = voucherNumberControlService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/voucher-number-controls");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
