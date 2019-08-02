package org.soptorshi.web.rest;
import org.soptorshi.service.ItemCategoryService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ItemCategoryDTO;
import org.soptorshi.service.dto.ItemCategoryCriteria;
import org.soptorshi.service.ItemCategoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ItemCategory.
 */
@RestController
@RequestMapping("/api")
public class ItemCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ItemCategoryResource.class);

    private static final String ENTITY_NAME = "itemCategory";

    private final ItemCategoryService itemCategoryService;

    private final ItemCategoryQueryService itemCategoryQueryService;

    public ItemCategoryResource(ItemCategoryService itemCategoryService, ItemCategoryQueryService itemCategoryQueryService) {
        this.itemCategoryService = itemCategoryService;
        this.itemCategoryQueryService = itemCategoryQueryService;
    }

    /**
     * POST  /item-categories : Create a new itemCategory.
     *
     * @param itemCategoryDTO the itemCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemCategoryDTO, or with status 400 (Bad Request) if the itemCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-categories")
    public ResponseEntity<ItemCategoryDTO> createItemCategory(@Valid @RequestBody ItemCategoryDTO itemCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ItemCategory : {}", itemCategoryDTO);
        if (itemCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCategoryDTO result = itemCategoryService.save(itemCategoryDTO);
        return ResponseEntity.created(new URI("/api/item-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-categories : Updates an existing itemCategory.
     *
     * @param itemCategoryDTO the itemCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemCategoryDTO,
     * or with status 400 (Bad Request) if the itemCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-categories")
    public ResponseEntity<ItemCategoryDTO> updateItemCategory(@Valid @RequestBody ItemCategoryDTO itemCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ItemCategory : {}", itemCategoryDTO);
        if (itemCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCategoryDTO result = itemCategoryService.save(itemCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-categories : get all the itemCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of itemCategories in body
     */
    @GetMapping("/item-categories")
    public ResponseEntity<List<ItemCategoryDTO>> getAllItemCategories(ItemCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemCategories by criteria: {}", criteria);
        Page<ItemCategoryDTO> page = itemCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /item-categories/count : count all the itemCategories.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/item-categories/count")
    public ResponseEntity<Long> countItemCategories(ItemCategoryCriteria criteria) {
        log.debug("REST request to count ItemCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /item-categories/:id : get the "id" itemCategory.
     *
     * @param id the id of the itemCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-categories/{id}")
    public ResponseEntity<ItemCategoryDTO> getItemCategory(@PathVariable Long id) {
        log.debug("REST request to get ItemCategory : {}", id);
        Optional<ItemCategoryDTO> itemCategoryDTO = itemCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCategoryDTO);
    }

    /**
     * DELETE  /item-categories/:id : delete the "id" itemCategory.
     *
     * @param id the id of the itemCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-categories/{id}")
    public ResponseEntity<Void> deleteItemCategory(@PathVariable Long id) {
        log.debug("REST request to delete ItemCategory : {}", id);
        itemCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/item-categories?query=:query : search for the itemCategory corresponding
     * to the query.
     *
     * @param query the query of the itemCategory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/item-categories")
    public ResponseEntity<List<ItemCategoryDTO>> searchItemCategories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemCategories for query {}", query);
        Page<ItemCategoryDTO> page = itemCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/item-categories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
