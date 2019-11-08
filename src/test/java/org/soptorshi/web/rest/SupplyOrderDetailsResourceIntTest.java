package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
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

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRODUCT_VOLUME = 1D;
    private static final Double UPDATED_PRODUCT_VOLUME = 2D;

    private static final Double DEFAULT_TOTAL_PRICE = 1D;
    private static final Double UPDATED_TOTAL_PRICE = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
            .productName(DEFAULT_PRODUCT_NAME)
            .productVolume(DEFAULT_PRODUCT_VOLUME)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
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
        assertThat(testSupplyOrderDetails.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testSupplyOrderDetails.getProductVolume()).isEqualTo(DEFAULT_PRODUCT_VOLUME);
        assertThat(testSupplyOrderDetails.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testSupplyOrderDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyOrderDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyOrderDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyOrderDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

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
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderDetailsRepository.findAll().size();
        // set the field null
        supplyOrderDetails.setProductName(null);

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
    public void checkProductVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderDetailsRepository.findAll().size();
        // set the field null
        supplyOrderDetails.setProductVolume(null);

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
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderDetailsRepository.findAll().size();
        // set the field null
        supplyOrderDetails.setTotalPrice(null);

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
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productVolume").value(hasItem(DEFAULT_PRODUCT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
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
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productVolume").value(DEFAULT_PRODUCT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productName equals to DEFAULT_PRODUCT_NAME
        defaultSupplyOrderDetailsShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the supplyOrderDetailsList where productName equals to UPDATED_PRODUCT_NAME
        defaultSupplyOrderDetailsShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultSupplyOrderDetailsShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the supplyOrderDetailsList where productName equals to UPDATED_PRODUCT_NAME
        defaultSupplyOrderDetailsShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productName is not null
        defaultSupplyOrderDetailsShouldBeFound("productName.specified=true");

        // Get all the supplyOrderDetailsList where productName is null
        defaultSupplyOrderDetailsShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductVolumeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productVolume equals to DEFAULT_PRODUCT_VOLUME
        defaultSupplyOrderDetailsShouldBeFound("productVolume.equals=" + DEFAULT_PRODUCT_VOLUME);

        // Get all the supplyOrderDetailsList where productVolume equals to UPDATED_PRODUCT_VOLUME
        defaultSupplyOrderDetailsShouldNotBeFound("productVolume.equals=" + UPDATED_PRODUCT_VOLUME);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductVolumeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productVolume in DEFAULT_PRODUCT_VOLUME or UPDATED_PRODUCT_VOLUME
        defaultSupplyOrderDetailsShouldBeFound("productVolume.in=" + DEFAULT_PRODUCT_VOLUME + "," + UPDATED_PRODUCT_VOLUME);

        // Get all the supplyOrderDetailsList where productVolume equals to UPDATED_PRODUCT_VOLUME
        defaultSupplyOrderDetailsShouldNotBeFound("productVolume.in=" + UPDATED_PRODUCT_VOLUME);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByProductVolumeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where productVolume is not null
        defaultSupplyOrderDetailsShouldBeFound("productVolume.specified=true");

        // Get all the supplyOrderDetailsList where productVolume is null
        defaultSupplyOrderDetailsShouldNotBeFound("productVolume.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultSupplyOrderDetailsShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplyOrderDetailsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultSupplyOrderDetailsShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultSupplyOrderDetailsShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the supplyOrderDetailsList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultSupplyOrderDetailsShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrderDetailsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderDetailsRepository.saveAndFlush(supplyOrderDetails);

        // Get all the supplyOrderDetailsList where totalPrice is not null
        defaultSupplyOrderDetailsShouldBeFound("totalPrice.specified=true");

        // Get all the supplyOrderDetailsList where totalPrice is null
        defaultSupplyOrderDetailsShouldNotBeFound("totalPrice.specified=false");
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

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyOrderDetailsShouldBeFound(String filter) throws Exception {
        restSupplyOrderDetailsMockMvc.perform(get("/api/supply-order-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productVolume").value(hasItem(DEFAULT_PRODUCT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

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
            .productName(UPDATED_PRODUCT_NAME)
            .productVolume(UPDATED_PRODUCT_VOLUME)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyOrderDetailsDTO supplyOrderDetailsDTO = supplyOrderDetailsMapper.toDto(updatedSupplyOrderDetails);

        restSupplyOrderDetailsMockMvc.perform(put("/api/supply-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyOrderDetails in the database
        List<SupplyOrderDetails> supplyOrderDetailsList = supplyOrderDetailsRepository.findAll();
        assertThat(supplyOrderDetailsList).hasSize(databaseSizeBeforeUpdate);
        SupplyOrderDetails testSupplyOrderDetails = supplyOrderDetailsList.get(supplyOrderDetailsList.size() - 1);
        assertThat(testSupplyOrderDetails.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testSupplyOrderDetails.getProductVolume()).isEqualTo(UPDATED_PRODUCT_VOLUME);
        assertThat(testSupplyOrderDetails.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testSupplyOrderDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyOrderDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyOrderDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyOrderDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

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
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productVolume").value(hasItem(DEFAULT_PRODUCT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
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
