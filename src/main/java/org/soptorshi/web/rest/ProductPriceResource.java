package org.soptorshi.web.rest;
import org.soptorshi.service.ProductPriceService;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.soptorshi.web.rest.util.HeaderUtil;
import org.soptorshi.web.rest.util.PaginationUtil;
import org.soptorshi.service.dto.ProductPriceDTO;
import org.soptorshi.service.dto.ProductPriceCriteria;
import org.soptorshi.service.ProductPriceQueryService;
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
 * REST controller for managing ProductPrice.
 */
@RestController
@RequestMapping("/api")
public class ProductPriceResource {

    private final Logger log = LoggerFactory.getLogger(ProductPriceResource.class);

    private static final String ENTITY_NAME = "productPrice";

    private final ProductPriceService productPriceService;

    private final ProductPriceQueryService productPriceQueryService;

    public ProductPriceResource(ProductPriceService productPriceService, ProductPriceQueryService productPriceQueryService) {
        this.productPriceService = productPriceService;
        this.productPriceQueryService = productPriceQueryService;
    }

    /**
     * POST  /product-prices : Create a new productPrice.
     *
     * @param productPriceDTO the productPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productPriceDTO, or with status 400 (Bad Request) if the productPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-prices")
    public ResponseEntity<ProductPriceDTO> createProductPrice(@RequestBody ProductPriceDTO productPriceDTO) throws URISyntaxException {
        log.debug("REST request to save ProductPrice : {}", productPriceDTO);
        if (productPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new productPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductPriceDTO result = productPriceService.save(productPriceDTO);
        return ResponseEntity.created(new URI("/api/product-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-prices : Updates an existing productPrice.
     *
     * @param productPriceDTO the productPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productPriceDTO,
     * or with status 400 (Bad Request) if the productPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the productPriceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-prices")
    public ResponseEntity<ProductPriceDTO> updateProductPrice(@RequestBody ProductPriceDTO productPriceDTO) throws URISyntaxException {
        log.debug("REST request to update ProductPrice : {}", productPriceDTO);
        if (productPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductPriceDTO result = productPriceService.save(productPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-prices : get all the productPrices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productPrices in body
     */
    @GetMapping("/product-prices")
    public ResponseEntity<List<ProductPriceDTO>> getAllProductPrices(ProductPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductPrices by criteria: {}", criteria);
        Page<ProductPriceDTO> page = productPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-prices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /product-prices/count : count all the productPrices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-prices/count")
    public ResponseEntity<Long> countProductPrices(ProductPriceCriteria criteria) {
        log.debug("REST request to count ProductPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(productPriceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-prices/:id : get the "id" productPrice.
     *
     * @param id the id of the productPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-prices/{id}")
    public ResponseEntity<ProductPriceDTO> getProductPrice(@PathVariable Long id) {
        log.debug("REST request to get ProductPrice : {}", id);
        Optional<ProductPriceDTO> productPriceDTO = productPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productPriceDTO);
    }

    /**
     * DELETE  /product-prices/:id : delete the "id" productPrice.
     *
     * @param id the id of the productPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-prices/{id}")
    public ResponseEntity<Void> deleteProductPrice(@PathVariable Long id) {
        log.debug("REST request to delete ProductPrice : {}", id);
        productPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/product-prices?query=:query : search for the productPrice corresponding
     * to the query.
     *
     * @param query the query of the productPrice search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/product-prices")
    public ResponseEntity<List<ProductPriceDTO>> searchProductPrices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductPrices for query {}", query);
        Page<ProductPriceDTO> page = productPriceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/product-prices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
