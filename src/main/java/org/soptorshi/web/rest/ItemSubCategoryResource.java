package org.soptorshi.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.service.ItemSubCategoryQueryService;
import org.soptorshi.service.ItemSubCategoryService;
import org.soptorshi.service.dto.ItemSubCategoryCriteria;
import org.soptorshi.service.dto.ItemSubCategoryDTO;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemSubCategory.
 */
@RestController
@RequestMapping("/api")
public class ItemSubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ItemSubCategoryResource.class);

    private static final String ENTITY_NAME = "itemSubCategory";

    private final ItemSubCategoryService itemSubCategoryService;

    private final ItemSubCategoryQueryService itemSubCategoryQueryService;

    public ItemSubCategoryResource(ItemSubCategoryService itemSubCategoryService, ItemSubCategoryQueryService itemSubCategoryQueryService) {
        this.itemSubCategoryService = itemSubCategoryService;
        this.itemSubCategoryQueryService = itemSubCategoryQueryService;
    }

    /**
     * POST  /item-sub-categories : Create a new itemSubCategory.
     *
     * @param itemSubCategoryDTO the itemSubCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemSubCategoryDTO, or with status 400 (Bad Request) if the itemSubCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-sub-categories")
    public ResponseEntity<ItemSubCategoryDTO> createItemSubCategory(@Valid @RequestBody ItemSubCategoryDTO itemSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ItemSubCategory : {}", itemSubCategoryDTO);
        if (itemSubCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemSubCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemSubCategoryDTO result = itemSubCategoryService.save(itemSubCategoryDTO);
        return ResponseEntity.created(new URI("/api/item-sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-sub-categories : Updates an existing itemSubCategory.
     *
     * @param itemSubCategoryDTO the itemSubCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemSubCategoryDTO,
     * or with status 400 (Bad Request) if the itemSubCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemSubCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-sub-categories")
    public ResponseEntity<ItemSubCategoryDTO> updateItemSubCategory(@Valid @RequestBody ItemSubCategoryDTO itemSubCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ItemSubCategory : {}", itemSubCategoryDTO);
        if (itemSubCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemSubCategoryDTO result = itemSubCategoryService.save(itemSubCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemSubCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-sub-categories : get all the itemSubCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemSubCategories in body
     */
    @GetMapping("/item-sub-categories")
    public ResponseEntity<List<ItemSubCategoryDTO>> getAllItemSubCategories(ItemSubCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemSubCategories by criteria: {}", criteria);
        Page<ItemSubCategoryDTO> page = itemSubCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-sub-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-sub-categories/count : count all the itemSubCategories.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-sub-categories/count")
    public ResponseEntity<Long> countItemSubCategories(ItemSubCategoryCriteria criteria) {
        log.debug("REST request to count ItemSubCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemSubCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-sub-categories/:id : get the "id" itemSubCategory.
     *
     * @param id the id of the itemSubCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemSubCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-sub-categories/{id}")
    public ResponseEntity<ItemSubCategoryDTO> getItemSubCategory(@PathVariable Long id) {
        log.debug("REST request to get ItemSubCategory : {}", id);
        Optional<ItemSubCategoryDTO> itemSubCategoryDTO = itemSubCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemSubCategoryDTO);
    }

    /**
     * DELETE  /item-sub-categories/:id : delete the "id" itemSubCategory.
     *
     * @param id the id of the itemSubCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-sub-categories/{id}")
    public ResponseEntity<Void> deleteItemSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete ItemSubCategory : {}", id);
        itemSubCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/item-sub-categories?query=:query : search for the itemSubCategory corresponding
     * to the query.
     *
     * @param query the query of the itemSubCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/item-sub-categories")
    public ResponseEntity<List<ItemSubCategoryDTO>> searchItemSubCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemSubCategories for query {}", query);
        Page<ItemSubCategoryDTO> page = itemSubCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/item-sub-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
