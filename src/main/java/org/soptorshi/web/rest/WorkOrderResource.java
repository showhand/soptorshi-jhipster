package org.soptorshi.web.rest;
import org.soptorshi.service.WorkOrderService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.WorkOrderDTO;
import org.soptorshi.service.dto.WorkOrderCriteria;
import org.soptorshi.service.WorkOrderQueryService;
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
 * REST controller for managing WorkOrder.
 */
@RestController
@RequestMapping("/api")
public class WorkOrderResource {

    private final Logger log = LoggerFactory.getLogger(WorkOrderResource.class);

    private static final String ENTITY_NAME = "workOrder";

    private final WorkOrderService workOrderService;

    private final WorkOrderQueryService workOrderQueryService;

    public WorkOrderResource(WorkOrderService workOrderService, WorkOrderQueryService workOrderQueryService) {
        this.workOrderService = workOrderService;
        this.workOrderQueryService = workOrderQueryService;
    }

    /**
     * POST  /work-orders : Create a new workOrder.
     *
     * @param workOrderDTO the workOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workOrderDTO, or with status 400 (Bad Request) if the workOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-orders")
    public ResponseEntity<WorkOrderDTO> createWorkOrder(@RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to save WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new workOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.created(new URI("/api/work-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-orders : Updates an existing workOrder.
     *
     * @param workOrderDTO the workOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workOrderDTO,
     * or with status 400 (Bad Request) if the workOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the workOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-orders")
    public ResponseEntity<WorkOrderDTO> updateWorkOrder(@RequestBody WorkOrderDTO workOrderDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOrder : {}", workOrderDTO);
        if (workOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkOrderDTO result = workOrderService.save(workOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-orders : get all the workOrders.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of workOrders in body
     */
    @GetMapping("/work-orders")
    public ResponseEntity<List<WorkOrderDTO>> getAllWorkOrders(WorkOrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkOrders by criteria: {}", criteria);
        Page<WorkOrderDTO> page = workOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /work-orders/count : count all the workOrders.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/work-orders/count")
    public ResponseEntity<Long> countWorkOrders(WorkOrderCriteria criteria) {
        log.debug("REST request to count WorkOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(workOrderQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /work-orders/:id : get the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-orders/{id}")
    public ResponseEntity<WorkOrderDTO> getWorkOrder(@PathVariable Long id) {
        log.debug("REST request to get WorkOrder : {}", id);
        Optional<WorkOrderDTO> workOrderDTO = workOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workOrderDTO);
    }

    /**
     * DELETE  /work-orders/:id : delete the "id" workOrder.
     *
     * @param id the id of the workOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-orders/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
        log.debug("REST request to delete WorkOrder : {}", id);
        workOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/work-orders?query=:query : search for the workOrder corresponding
     * to the query.
     *
     * @param query the query of the workOrder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/work-orders")
    public ResponseEntity<List<WorkOrderDTO>> searchWorkOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorkOrders for query {}", query);
        Page<WorkOrderDTO> page = workOrderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/work-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
