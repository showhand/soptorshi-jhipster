package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Product;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.SupplyOrderDetailsRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.service.SupplyOrderDetailsQueryService;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SupplyOrderDetailsResource REST controller.
 *
 * @see SupplyOrderDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyOrderDetailsResourceIntTest {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private SupplyOrderDetailsRepository supplyOrderDetailsRepository;

    @Autowired
    private SupplyOrderDetailsMapper supplyOrderDetailsMapper;

    @Autowired
    private SupplyOrderDetailsService supplyOrderDetailsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyOrderDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyOrderDetailsSearchRepository mockSupplyOrderDetailsSearchRepository;

    @Autowired
    private SupplyOrderDetailsQueryService supplyOrderDetailsQueryService;

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

    private MockMvc restSupplyOrderDetailsMockMvc;

    private SupplyOrderDetails supplyOrderDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyOrderDetailsResource supplyOrderDetailsResource = new SupplyOrderDetailsResource(supplyOrderDetailsService, supplyOrderDetailsQueryService);
        this.restSupplyOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(supplyOrderDetailsResource)
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
    public static SupplyOrderDetails createEntity(EntityManager em) {
        SupplyOrderDetails supplyOrderDetails = new SupplyOrderDetails()
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE);
        // Add required entity
        SupplyOrder supplyOrder = SupplyOrderResourceIntTest.createEntity(em);
        em.persist(supplyOrder);
        em.flush();
        supplyOrderDetails.setSupplyOrder(supplyOrder);
        // Add required entity
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        supplyOrderDetails.setProductCategory(productCategory);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        supplyOrderDetails.setProduct(product);
        return supplyOrderDetails;
    }

    @Before
    public void initTest() {
        supplyOrderDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = supplyOrderDetailsRepository.findAll().size();

        // Create the SupplyOrderDetails
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(supplyOrderDetails);
        restSupplyOrderDetailsMockMvc.perform(post("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyOrderDetails in the database
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyOrderDetails testSupplyOrderDetails = supplyOrderDetailsList.get(supplyOrderDetailsList.size() - 1);
        assertThat(testSupplyOrderDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyOrderDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyOrderDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyOrderDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testSupplyOrderDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSupplyOrderDetails.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the SupplyOrderDetails in Elasticsearch
        verify(mockSupplyOrderDetailsSearchRepository, times(1)).save(testSupplyOrderDetails);
    }

    @Test
    @Transactional
    public void createSupplyOrderDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyOrderDetailsRepository.findAll().size();

        // Create the SupplyOrderDetails with an existing ID
        supplyOrderDetails.setId(1L);
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(supplyOrderDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyOrderDetailsMockMvc.perform(post("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyOrderDetails in the database
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyOrderDetails in Elasticsearch
        verify(mockSupplyOrderDetailsSearchRepository, times(0)).save(supplyOrderDetails);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderDetailsRepository.findAll().size();
        // set the field null
        supplyOrderDetails.setQuantity(null);

        // Create the SupplyOrderDetails, which fails.
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(supplyOrderDetails);

        restSupplyOrderDetailsMockMvc.perform(post("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderDetailsRepository.findAll().size();
        // set the field null
        supplyOrderDetails.setPrice(null);

        // Create the SupplyOrderDetails, which fails.
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(supplyOrderDetails);

        restSupplyOrderDetailsMockMvc.perform(post("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetails() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getSupplyOrderDetails() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get the supplyOrderDetails
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details/{id}", supplyOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyOrderDetails.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyOrderDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyOrderDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyOrderDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyOrderDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyOrderDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyOrderDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdBy is not null
        defaultSupplyOrderDetailsShouldBeFound("createdBy.specified=true");

        // Get all the supplyOrderDetailsList where createdBy is null
        defaultSupplyOrderDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyOrderDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyOrderDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyOrderDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyOrderDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyOrderDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyOrderDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where createdOn is not null
        defaultSupplyOrderDetailsShouldBeFound("createdOn.specified=true");

        // Get all the supplyOrderDetailsList where createdOn is null
        defaultSupplyOrderDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyOrderDetailsShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyOrderDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyOrderDetailsShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyOrderDetailsShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyOrderDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyOrderDetailsShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedBy is not null
        defaultSupplyOrderDetailsShouldBeFound("updatedBy.specified=true");

        // Get all the supplyOrderDetailsList where updatedBy is null
        defaultSupplyOrderDetailsShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyOrderDetailsShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyOrderDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyOrderDetailsShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyOrderDetailsShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyOrderDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyOrderDetailsShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where updatedOn is not null
        defaultSupplyOrderDetailsShouldBeFound("updatedOn.specified=true");

        // Get all the supplyOrderDetailsList where updatedOn is null
        defaultSupplyOrderDetailsShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where quantity equals to DEFAULT_QUANTITY
        defaultSupplyOrderDetailsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the supplyOrderDetailsList where quantity equals to UPDATED_QUANTITY
        defaultSupplyOrderDetailsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultSupplyOrderDetailsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the supplyOrderDetailsList where quantity equals to UPDATED_QUANTITY
        defaultSupplyOrderDetailsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where quantity is not null
        defaultSupplyOrderDetailsShouldBeFound("quantity.specified=true");

        // Get all the supplyOrderDetailsList where quantity is null
        defaultSupplyOrderDetailsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where price equals to DEFAULT_PRICE
        defaultSupplyOrderDetailsShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the supplyOrderDetailsList where price equals to UPDATED_PRICE
        defaultSupplyOrderDetailsShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultSupplyOrderDetailsShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the supplyOrderDetailsList where price equals to UPDATED_PRICE
        defaultSupplyOrderDetailsShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where price is not null
        defaultSupplyOrderDetailsShouldBeFound("price.specified=true");

        // Get all the supplyOrderDetailsList where price is null
        defaultSupplyOrderDetailsShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsBySupplyOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyOrder supplyOrder = SupplyOrderResourceIntTest.createEntity(em);
        em.persist(supplyOrder);
        em.flush();
        supplyOrderDetails.setSupplyOrder(supplyOrder);
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);
        Long supplyOrderId = supplyOrder.getId();

        // Get all the supplyOrderDetailsList where supplyOrder equals to supplyOrderId
        defaultSupplyOrderDetailsShouldBeFound("supplyOrderId.equals=" + supplyOrderId);

        // Get all the supplyOrderDetailsList where supplyOrder equals to supplyOrderId + 1
        defaultSupplyOrderDetailsShouldNotBeFound("supplyOrderId.equals=" + (supplyOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        supplyOrderDetails.setProductCategory(productCategory);
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);
        Long productCategoryId = productCategory.getId();

        // Get all the supplyOrderDetailsList where productCategory equals to productCategoryId
        defaultSupplyOrderDetailsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the supplyOrderDetailsList where productCategory equals to productCategoryId + 1
        defaultSupplyOrderDetailsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        supplyOrderDetails.setProduct(product);
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);
        Long productId = product.getId();

        // Get all the supplyOrderDetailsList where product equals to productId
        defaultSupplyOrderDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the supplyOrderDetailsList where product equals to productId + 1
        defaultSupplyOrderDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyOrderDetailsShouldBeFound(String filter) throws Exception {
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));

        // Check, that the count call also returns 1
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyOrderDetailsShouldNotBeFound(String filter) throws Exception {
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyOrderDetails() throws Exception {
        // Get the supplyOrderDetails
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyOrderDetails() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        int databaseSizeBeforeUpdate = supplyOrderDetailsRepository.findAll().size();

        // Update the supplyOrderDetails
        SupplyOrderDetails updatedSupplyOrderDetails = supplyOrderDetailsRepository.findById(supplyOrderDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyOrderDetails are not directly saved in db
        em.detach(updatedSupplyOrderDetails);
        updatedSupplyOrderDetails
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE);
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(updatedSupplyOrderDetails);

        restSupplyOrderDetailsMockMvc.perform(put("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyOrderDetails in the database
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeUpdate);
        SupplyOrderDetails testSupplyOrderDetails = supplyOrderDetailsList.get(supplyOrderDetailsList.size() - 1);
        assertThat(testSupplyOrderDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyOrderDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyOrderDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyOrderDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testSupplyOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSupplyOrderDetails.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the SupplyOrderDetails in Elasticsearch
        verify(mockSupplyOrderDetailsSearchRepository, times(1)).save(testSupplyOrderDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = supplyOrderDetailsRepository.findAll().size();

        // Create the SupplyOrderDetails
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(supplyOrderDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyOrderDetailsMockMvc.perform(put("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyOrderDetails in the database
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyOrderDetails in Elasticsearch
        verify(mockSupplyOrderDetailsSearchRepository, times(0)).save(supplyOrderDetails);
    }

    @Test
    @Transactional
    public void deleteSupplyOrderDetails() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        int databaseSizeBeforeDelete = supplyOrderDetailsRepository.findAll().size();

        // Delete the supplyOrderDetails
        restSupplyOrderDetailsMockMvc.perform(delete("/api/supply-order-details/{id}", supplyOrderDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyOrderDetails in Elasticsearch
        verify(mockSupplyOrderDetailsSearchRepository, times(1)).deleteById(supplyOrderDetails.getId());
    }

    @Test
    @Transactional
    public void searchSupplyOrderDetails() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);
        when(mockSupplyOrderDetailsSearchRepository.search(queryStringQuery("id:" + supplyOrderDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyOrderDetails), PageRequest.of(0, 1), 1));
        // Search the supplyOrderDetails
        restSupplyOrderDetailsMockMvc.perform(get("/api/_search/supply-order-details?query=id:" + supplyOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyOrderDetails.class);
        SupplyOrderDetails supplyOrderDetails1 = new SupplyOrderDetails();
        supplyOrderDetails1.setId(1L);
        SupplyOrderDetails supplyOrderDetails2 = new SupplyOrderDetails();
        supplyOrderDetails2.setId(supplyOrderDetails1.getId());
        assertThat(supplyOrderDetails1).isEqualTo(supplyOrderDetails2);
        supplyOrderDetails2.setId(2L);
        assertThat(supplyOrderDetails1).isNotEqualTo(supplyOrderDetails2);
        supplyOrderDetails1.setId(null);
        assertThat(supplyOrderDetails1).isNotEqualTo(supplyOrderDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyOrderDetailsDTO.class);
        SupplyOrderDetailsDTO supplyOrderDetailsDTO1 = new SupplyOrderDetailsDTO();
        supplyOrderDetailsDTO1.setId(1L);
        SupplyOrderDetailsDTO supplyOrderDetailsDTO2 = new SupplyOrderDetailsDTO();
        assertThat(supplyOrderDetailsDTO1).isNotEqualTo(supplyOrderDetailsDTO2);
        supplyOrderDetailsDTO2.setId(supplyOrderDetailsDTO1.getId());
        assertThat(supplyOrderDetailsDTO1).isEqualTo(supplyOrderDetailsDTO2);
        supplyOrderDetailsDTO2.setId(2L);
        assertThat(supplyOrderDetailsDTO1).isNotEqualTo(supplyOrderDetailsDTO2);
        supplyOrderDetailsDTO1.setId(null);
        assertThat(supplyOrderDetailsDTO1).isNotEqualTo(supplyOrderDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyOrderDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyOrderDetailsMapper.fromId(null)).isNull();
    }
}
