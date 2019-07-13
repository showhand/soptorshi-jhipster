package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ProductPrice;
import org.soptorshi.domain.Product;
import org.soptorshi.repository.ProductPriceRepository;
import org.soptorshi.repository.search.ProductPriceSearchRepository;
import org.soptorshi.service.ProductPriceService;
import org.soptorshi.service.dto.ProductPriceDTO;
import org.soptorshi.service.mapper.ProductPriceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ProductPriceCriteria;
import org.soptorshi.service.ProductPriceQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductPriceResource REST controller.
 *
 * @see ProductPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ProductPriceResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final LocalDate DEFAULT_PRICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductPriceMapper productPriceMapper;

    @Autowired
    private ProductPriceService productPriceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ProductPriceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductPriceSearchRepository mockProductPriceSearchRepository;

    @Autowired
    private ProductPriceQueryService productPriceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductPriceMockMvc;

    private ProductPrice productPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductPriceResource productPriceResource = new ProductPriceResource(productPriceService, productPriceQueryService);
        this.restProductPriceMockMvc = MockMvcBuilders.standaloneSetup(productPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPrice createEntity(EntityManager em) {
        ProductPrice productPrice = new ProductPrice()
            .price(DEFAULT_PRICE)
            .priceDate(DEFAULT_PRICE_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return productPrice;
    }

    @Before
    public void initTest() {
        productPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPrice() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();

        // Create the ProductPrice
        ProductPriceDTO productPriceDTO = productPriceMapper.toDto(productPrice);
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductPrice.getPriceDate()).isEqualTo(DEFAULT_PRICE_DATE);
        assertThat(testProductPrice.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProductPrice.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ProductPrice in Elasticsearch
        verify(mockProductPriceSearchRepository, times(1)).save(testProductPrice);
    }

    @Test
    @Transactional
    public void createProductPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();

        // Create the ProductPrice with an existing ID
        productPrice.setId(1L);
        ProductPriceDTO productPriceDTO = productPriceMapper.toDto(productPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductPrice in Elasticsearch
        verify(mockProductPriceSearchRepository, times(0)).save(productPrice);
    }

    @Test
    @Transactional
    public void getAllProductPrices() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", productPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.priceDate").value(DEFAULT_PRICE_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price equals to DEFAULT_PRICE
        defaultProductPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productPriceList where price equals to UPDATED_PRICE
        defaultProductPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productPriceList where price equals to UPDATED_PRICE
        defaultProductPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is not null
        defaultProductPriceShouldBeFound("price.specified=true");

        // Get all the productPriceList where price is null
        defaultProductPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where priceDate equals to DEFAULT_PRICE_DATE
        defaultProductPriceShouldBeFound("priceDate.equals=" + DEFAULT_PRICE_DATE);

        // Get all the productPriceList where priceDate equals to UPDATED_PRICE_DATE
        defaultProductPriceShouldNotBeFound("priceDate.equals=" + UPDATED_PRICE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceDateIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where priceDate in DEFAULT_PRICE_DATE or UPDATED_PRICE_DATE
        defaultProductPriceShouldBeFound("priceDate.in=" + DEFAULT_PRICE_DATE + "," + UPDATED_PRICE_DATE);

        // Get all the productPriceList where priceDate equals to UPDATED_PRICE_DATE
        defaultProductPriceShouldNotBeFound("priceDate.in=" + UPDATED_PRICE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where priceDate is not null
        defaultProductPriceShouldBeFound("priceDate.specified=true");

        // Get all the productPriceList where priceDate is null
        defaultProductPriceShouldNotBeFound("priceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where priceDate greater than or equals to DEFAULT_PRICE_DATE
        defaultProductPriceShouldBeFound("priceDate.greaterOrEqualThan=" + DEFAULT_PRICE_DATE);

        // Get all the productPriceList where priceDate greater than or equals to UPDATED_PRICE_DATE
        defaultProductPriceShouldNotBeFound("priceDate.greaterOrEqualThan=" + UPDATED_PRICE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where priceDate less than or equals to DEFAULT_PRICE_DATE
        defaultProductPriceShouldNotBeFound("priceDate.lessThan=" + DEFAULT_PRICE_DATE);

        // Get all the productPriceList where priceDate less than or equals to UPDATED_PRICE_DATE
        defaultProductPriceShouldBeFound("priceDate.lessThan=" + UPDATED_PRICE_DATE);
    }


    @Test
    @Transactional
    public void getAllProductPricesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultProductPriceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the productPriceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProductPriceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultProductPriceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the productPriceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProductPriceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedBy is not null
        defaultProductPriceShouldBeFound("modifiedBy.specified=true");

        // Get all the productPriceList where modifiedBy is null
        defaultProductPriceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultProductPriceShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the productPriceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultProductPriceShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultProductPriceShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the productPriceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultProductPriceShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedOn is not null
        defaultProductPriceShouldBeFound("modifiedOn.specified=true");

        // Get all the productPriceList where modifiedOn is null
        defaultProductPriceShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultProductPriceShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the productPriceList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultProductPriceShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProductPricesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultProductPriceShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the productPriceList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultProductPriceShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllProductPricesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        productPrice.setProduct(product);
        productPriceRepository.saveAndFlush(productPrice);
        Long productId = product.getId();

        // Get all the productPriceList where product equals to productId
        defaultProductPriceShouldBeFound("productId.equals=" + productId);

        // Get all the productPriceList where product equals to productId + 1
        defaultProductPriceShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductPriceShouldBeFound(String filter) throws Exception {
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restProductPriceMockMvc.perform(get("/api/product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductPriceShouldNotBeFound(String filter) throws Exception {
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductPriceMockMvc.perform(get("/api/product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductPrice() throws Exception {
        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // Update the productPrice
        ProductPrice updatedProductPrice = productPriceRepository.findById(productPrice.getId()).get();
        // Disconnect from session so that the updates on updatedProductPrice are not directly saved in db
        em.detach(updatedProductPrice);
        updatedProductPrice
            .price(UPDATED_PRICE)
            .priceDate(UPDATED_PRICE_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ProductPriceDTO productPriceDTO = productPriceMapper.toDto(updatedProductPrice);

        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductPrice.getPriceDate()).isEqualTo(UPDATED_PRICE_DATE);
        assertThat(testProductPrice.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProductPrice.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ProductPrice in Elasticsearch
        verify(mockProductPriceSearchRepository, times(1)).save(testProductPrice);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPrice() throws Exception {
        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // Create the ProductPrice
        ProductPriceDTO productPriceDTO = productPriceMapper.toDto(productPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductPrice in Elasticsearch
        verify(mockProductPriceSearchRepository, times(0)).save(productPrice);
    }

    @Test
    @Transactional
    public void deleteProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        int databaseSizeBeforeDelete = productPriceRepository.findAll().size();

        // Delete the productPrice
        restProductPriceMockMvc.perform(delete("/api/product-prices/{id}", productPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductPrice in Elasticsearch
        verify(mockProductPriceSearchRepository, times(1)).deleteById(productPrice.getId());
    }

    @Test
    @Transactional
    public void searchProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);
        when(mockProductPriceSearchRepository.search(queryStringQuery("id:" + productPrice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productPrice), PageRequest.of(0, 1), 1));
        // Search the productPrice
        restProductPriceMockMvc.perform(get("/api/_search/product-prices?query=id:" + productPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPrice.class);
        ProductPrice productPrice1 = new ProductPrice();
        productPrice1.setId(1L);
        ProductPrice productPrice2 = new ProductPrice();
        productPrice2.setId(productPrice1.getId());
        assertThat(productPrice1).isEqualTo(productPrice2);
        productPrice2.setId(2L);
        assertThat(productPrice1).isNotEqualTo(productPrice2);
        productPrice1.setId(null);
        assertThat(productPrice1).isNotEqualTo(productPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPriceDTO.class);
        ProductPriceDTO productPriceDTO1 = new ProductPriceDTO();
        productPriceDTO1.setId(1L);
        ProductPriceDTO productPriceDTO2 = new ProductPriceDTO();
        assertThat(productPriceDTO1).isNotEqualTo(productPriceDTO2);
        productPriceDTO2.setId(productPriceDTO1.getId());
        assertThat(productPriceDTO1).isEqualTo(productPriceDTO2);
        productPriceDTO2.setId(2L);
        assertThat(productPriceDTO1).isNotEqualTo(productPriceDTO2);
        productPriceDTO1.setId(null);
        assertThat(productPriceDTO1).isNotEqualTo(productPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productPriceMapper.fromId(null)).isNull();
    }
}
