package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialWorkOrderDetails;
import org.soptorshi.domain.CommercialWorkOrder;
import org.soptorshi.repository.CommercialWorkOrderDetailsRepository;
import org.soptorshi.repository.search.CommercialWorkOrderDetailsSearchRepository;
import org.soptorshi.service.CommercialWorkOrderDetailsService;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderDetailsMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsCriteria;
import org.soptorshi.service.CommercialWorkOrderDetailsQueryService;

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

import org.soptorshi.domain.enumeration.CommercialCurrency;
/**
 * Test class for the CommercialWorkOrderDetailsResource REST controller.
 *
 * @see CommercialWorkOrderDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialWorkOrderDetailsResourceIntTest {

    private static final String DEFAULT_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final CommercialCurrency DEFAULT_CURRENCY_TYPE = CommercialCurrency.BDT;
    private static final CommercialCurrency UPDATED_CURRENCY_TYPE = CommercialCurrency.US_DOLLAR;

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialWorkOrderDetailsRepository commercialWorkOrderDetailsRepository;

    @Autowired
    private CommercialWorkOrderDetailsMapper commercialWorkOrderDetailsMapper;

    @Autowired
    private CommercialWorkOrderDetailsService commercialWorkOrderDetailsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialWorkOrderDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialWorkOrderDetailsSearchRepository mockCommercialWorkOrderDetailsSearchRepository;

    @Autowired
    private CommercialWorkOrderDetailsQueryService commercialWorkOrderDetailsQueryService;

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

    private MockMvc restCommercialWorkOrderDetailsMockMvc;

    private CommercialWorkOrderDetails commercialWorkOrderDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialWorkOrderDetailsResource commercialWorkOrderDetailsResource = new CommercialWorkOrderDetailsResource(commercialWorkOrderDetailsService, commercialWorkOrderDetailsQueryService);
        this.restCommercialWorkOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(commercialWorkOrderDetailsResource)
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
    public static CommercialWorkOrderDetails createEntity(EntityManager em) {
        CommercialWorkOrderDetails commercialWorkOrderDetails = new CommercialWorkOrderDetails()
            .goods(DEFAULT_GOODS)
            .reason(DEFAULT_REASON)
            .size(DEFAULT_SIZE)
            .color(DEFAULT_COLOR)
            .quantity(DEFAULT_QUANTITY)
            .currencyType(DEFAULT_CURRENCY_TYPE)
            .rate(DEFAULT_RATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialWorkOrderDetails;
    }

    @Before
    public void initTest() {
        commercialWorkOrderDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialWorkOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = commercialWorkOrderDetailsRepository.findAll().size();

        // Create the CommercialWorkOrderDetails
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);
        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialWorkOrderDetails in the database
        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialWorkOrderDetails testCommercialWorkOrderDetails = commercialWorkOrderDetailsList.get(commercialWorkOrderDetailsList.size() - 1);
        assertThat(testCommercialWorkOrderDetails.getGoods()).isEqualTo(DEFAULT_GOODS);
        assertThat(testCommercialWorkOrderDetails.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testCommercialWorkOrderDetails.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testCommercialWorkOrderDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCommercialWorkOrderDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCommercialWorkOrderDetails.getCurrencyType()).isEqualTo(DEFAULT_CURRENCY_TYPE);
        assertThat(testCommercialWorkOrderDetails.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCommercialWorkOrderDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialWorkOrderDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialWorkOrderDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialWorkOrderDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialWorkOrderDetails in Elasticsearch
        verify(mockCommercialWorkOrderDetailsSearchRepository, times(1)).save(testCommercialWorkOrderDetails);
    }

    @Test
    @Transactional
    public void createCommercialWorkOrderDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialWorkOrderDetailsRepository.findAll().size();

        // Create the CommercialWorkOrderDetails with an existing ID
        commercialWorkOrderDetails.setId(1L);
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialWorkOrderDetails in the database
        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialWorkOrderDetails in Elasticsearch
        verify(mockCommercialWorkOrderDetailsSearchRepository, times(0)).save(commercialWorkOrderDetails);
    }

    @Test
    @Transactional
    public void checkGoodsIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderDetailsRepository.findAll().size();
        // set the field null
        commercialWorkOrderDetails.setGoods(null);

        // Create the CommercialWorkOrderDetails, which fails.
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderDetailsRepository.findAll().size();
        // set the field null
        commercialWorkOrderDetails.setQuantity(null);

        // Create the CommercialWorkOrderDetails, which fails.
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderDetailsRepository.findAll().size();
        // set the field null
        commercialWorkOrderDetails.setCurrencyType(null);

        // Create the CommercialWorkOrderDetails, which fails.
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderDetailsRepository.findAll().size();
        // set the field null
        commercialWorkOrderDetails.setRate(null);

        // Create the CommercialWorkOrderDetails, which fails.
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        restCommercialWorkOrderDetailsMockMvc.perform(post("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetails() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialWorkOrderDetails() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get the commercialWorkOrderDetails
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details/{id}", commercialWorkOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialWorkOrderDetails.getId().intValue()))
            .andExpect(jsonPath("$.goods").value(DEFAULT_GOODS.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.currencyType").value(DEFAULT_CURRENCY_TYPE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where goods equals to DEFAULT_GOODS
        defaultCommercialWorkOrderDetailsShouldBeFound("goods.equals=" + DEFAULT_GOODS);

        // Get all the commercialWorkOrderDetailsList where goods equals to UPDATED_GOODS
        defaultCommercialWorkOrderDetailsShouldNotBeFound("goods.equals=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where goods in DEFAULT_GOODS or UPDATED_GOODS
        defaultCommercialWorkOrderDetailsShouldBeFound("goods.in=" + DEFAULT_GOODS + "," + UPDATED_GOODS);

        // Get all the commercialWorkOrderDetailsList where goods equals to UPDATED_GOODS
        defaultCommercialWorkOrderDetailsShouldNotBeFound("goods.in=" + UPDATED_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where goods is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("goods.specified=true");

        // Get all the commercialWorkOrderDetailsList where goods is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("goods.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where reason equals to DEFAULT_REASON
        defaultCommercialWorkOrderDetailsShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the commercialWorkOrderDetailsList where reason equals to UPDATED_REASON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultCommercialWorkOrderDetailsShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the commercialWorkOrderDetailsList where reason equals to UPDATED_REASON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where reason is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("reason.specified=true");

        // Get all the commercialWorkOrderDetailsList where reason is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsBySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where size equals to DEFAULT_SIZE
        defaultCommercialWorkOrderDetailsShouldBeFound("size.equals=" + DEFAULT_SIZE);

        // Get all the commercialWorkOrderDetailsList where size equals to UPDATED_SIZE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("size.equals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsBySizeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where size in DEFAULT_SIZE or UPDATED_SIZE
        defaultCommercialWorkOrderDetailsShouldBeFound("size.in=" + DEFAULT_SIZE + "," + UPDATED_SIZE);

        // Get all the commercialWorkOrderDetailsList where size equals to UPDATED_SIZE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("size.in=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsBySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where size is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("size.specified=true");

        // Get all the commercialWorkOrderDetailsList where size is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("size.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where color equals to DEFAULT_COLOR
        defaultCommercialWorkOrderDetailsShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the commercialWorkOrderDetailsList where color equals to UPDATED_COLOR
        defaultCommercialWorkOrderDetailsShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultCommercialWorkOrderDetailsShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the commercialWorkOrderDetailsList where color equals to UPDATED_COLOR
        defaultCommercialWorkOrderDetailsShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where color is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("color.specified=true");

        // Get all the commercialWorkOrderDetailsList where color is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where quantity equals to DEFAULT_QUANTITY
        defaultCommercialWorkOrderDetailsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the commercialWorkOrderDetailsList where quantity equals to UPDATED_QUANTITY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultCommercialWorkOrderDetailsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the commercialWorkOrderDetailsList where quantity equals to UPDATED_QUANTITY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where quantity is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("quantity.specified=true");

        // Get all the commercialWorkOrderDetailsList where quantity is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCurrencyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where currencyType equals to DEFAULT_CURRENCY_TYPE
        defaultCommercialWorkOrderDetailsShouldBeFound("currencyType.equals=" + DEFAULT_CURRENCY_TYPE);

        // Get all the commercialWorkOrderDetailsList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("currencyType.equals=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCurrencyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where currencyType in DEFAULT_CURRENCY_TYPE or UPDATED_CURRENCY_TYPE
        defaultCommercialWorkOrderDetailsShouldBeFound("currencyType.in=" + DEFAULT_CURRENCY_TYPE + "," + UPDATED_CURRENCY_TYPE);

        // Get all the commercialWorkOrderDetailsList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("currencyType.in=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCurrencyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where currencyType is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("currencyType.specified=true");

        // Get all the commercialWorkOrderDetailsList where currencyType is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("currencyType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where rate equals to DEFAULT_RATE
        defaultCommercialWorkOrderDetailsShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the commercialWorkOrderDetailsList where rate equals to UPDATED_RATE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByRateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultCommercialWorkOrderDetailsShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the commercialWorkOrderDetailsList where rate equals to UPDATED_RATE
        defaultCommercialWorkOrderDetailsShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where rate is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("rate.specified=true");

        // Get all the commercialWorkOrderDetailsList where rate is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialWorkOrderDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialWorkOrderDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialWorkOrderDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialWorkOrderDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdBy is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("createdBy.specified=true");

        // Get all the commercialWorkOrderDetailsList where createdBy is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialWorkOrderDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdOn is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("createdOn.specified=true");

        // Get all the commercialWorkOrderDetailsList where createdOn is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderDetailsList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderDetailsList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialWorkOrderDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialWorkOrderDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedBy is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedBy.specified=true");

        // Get all the commercialWorkOrderDetailsList where updatedBy is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialWorkOrderDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedOn is not null
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedOn.specified=true");

        // Get all the commercialWorkOrderDetailsList where updatedOn is null
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderDetailsList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        // Get all the commercialWorkOrderDetailsList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderDetailsList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderDetailsShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrderDetailsByCommercialWorkOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialWorkOrder commercialWorkOrder = CommercialWorkOrderResourceIntTest.createEntity(em);
        em.persist(commercialWorkOrder);
        em.flush();
        commercialWorkOrderDetails.setCommercialWorkOrder(commercialWorkOrder);
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);
        Long commercialWorkOrderId = commercialWorkOrder.getId();

        // Get all the commercialWorkOrderDetailsList where commercialWorkOrder equals to commercialWorkOrderId
        defaultCommercialWorkOrderDetailsShouldBeFound("commercialWorkOrderId.equals=" + commercialWorkOrderId);

        // Get all the commercialWorkOrderDetailsList where commercialWorkOrder equals to commercialWorkOrderId + 1
        defaultCommercialWorkOrderDetailsShouldNotBeFound("commercialWorkOrderId.equals=" + (commercialWorkOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialWorkOrderDetailsShouldBeFound(String filter) throws Exception {
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialWorkOrderDetailsShouldNotBeFound(String filter) throws Exception {
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialWorkOrderDetails() throws Exception {
        // Get the commercialWorkOrderDetails
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/commercial-work-order-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialWorkOrderDetails() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        int databaseSizeBeforeUpdate = commercialWorkOrderDetailsRepository.findAll().size();

        // Update the commercialWorkOrderDetails
        CommercialWorkOrderDetails updatedCommercialWorkOrderDetails = commercialWorkOrderDetailsRepository.findById(commercialWorkOrderDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialWorkOrderDetails are not directly saved in db
        em.detach(updatedCommercialWorkOrderDetails);
        updatedCommercialWorkOrderDetails
            .goods(UPDATED_GOODS)
            .reason(UPDATED_REASON)
            .size(UPDATED_SIZE)
            .color(UPDATED_COLOR)
            .quantity(UPDATED_QUANTITY)
            .currencyType(UPDATED_CURRENCY_TYPE)
            .rate(UPDATED_RATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(updatedCommercialWorkOrderDetails);

        restCommercialWorkOrderDetailsMockMvc.perform(put("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialWorkOrderDetails in the database
        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeUpdate);
        CommercialWorkOrderDetails testCommercialWorkOrderDetails = commercialWorkOrderDetailsList.get(commercialWorkOrderDetailsList.size() - 1);
        assertThat(testCommercialWorkOrderDetails.getGoods()).isEqualTo(UPDATED_GOODS);
        assertThat(testCommercialWorkOrderDetails.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testCommercialWorkOrderDetails.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testCommercialWorkOrderDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCommercialWorkOrderDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCommercialWorkOrderDetails.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
        assertThat(testCommercialWorkOrderDetails.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCommercialWorkOrderDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialWorkOrderDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialWorkOrderDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialWorkOrderDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialWorkOrderDetails in Elasticsearch
        verify(mockCommercialWorkOrderDetailsSearchRepository, times(1)).save(testCommercialWorkOrderDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialWorkOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = commercialWorkOrderDetailsRepository.findAll().size();

        // Create the CommercialWorkOrderDetails
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialWorkOrderDetailsMockMvc.perform(put("/api/commercial-work-order-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialWorkOrderDetails in the database
        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialWorkOrderDetails in Elasticsearch
        verify(mockCommercialWorkOrderDetailsSearchRepository, times(0)).save(commercialWorkOrderDetails);
    }

    @Test
    @Transactional
    public void deleteCommercialWorkOrderDetails() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);

        int databaseSizeBeforeDelete = commercialWorkOrderDetailsRepository.findAll().size();

        // Delete the commercialWorkOrderDetails
        restCommercialWorkOrderDetailsMockMvc.perform(delete("/api/commercial-work-order-details/{id}", commercialWorkOrderDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialWorkOrderDetails> commercialWorkOrderDetailsList = commercialWorkOrderDetailsRepository.findAll();
        assertThat(commercialWorkOrderDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialWorkOrderDetails in Elasticsearch
        verify(mockCommercialWorkOrderDetailsSearchRepository, times(1)).deleteById(commercialWorkOrderDetails.getId());
    }

    @Test
    @Transactional
    public void searchCommercialWorkOrderDetails() throws Exception {
        // Initialize the database
        commercialWorkOrderDetailsRepository.saveAndFlush(commercialWorkOrderDetails);
        when(mockCommercialWorkOrderDetailsSearchRepository.search(queryStringQuery("id:" + commercialWorkOrderDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialWorkOrderDetails), PageRequest.of(0, 1), 1));
        // Search the commercialWorkOrderDetails
        restCommercialWorkOrderDetailsMockMvc.perform(get("/api/_search/commercial-work-order-details?query=id:" + commercialWorkOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].goods").value(hasItem(DEFAULT_GOODS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialWorkOrderDetails.class);
        CommercialWorkOrderDetails commercialWorkOrderDetails1 = new CommercialWorkOrderDetails();
        commercialWorkOrderDetails1.setId(1L);
        CommercialWorkOrderDetails commercialWorkOrderDetails2 = new CommercialWorkOrderDetails();
        commercialWorkOrderDetails2.setId(commercialWorkOrderDetails1.getId());
        assertThat(commercialWorkOrderDetails1).isEqualTo(commercialWorkOrderDetails2);
        commercialWorkOrderDetails2.setId(2L);
        assertThat(commercialWorkOrderDetails1).isNotEqualTo(commercialWorkOrderDetails2);
        commercialWorkOrderDetails1.setId(null);
        assertThat(commercialWorkOrderDetails1).isNotEqualTo(commercialWorkOrderDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialWorkOrderDetailsDTO.class);
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO1 = new CommercialWorkOrderDetailsDTO();
        commercialWorkOrderDetailsDTO1.setId(1L);
        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO2 = new CommercialWorkOrderDetailsDTO();
        assertThat(commercialWorkOrderDetailsDTO1).isNotEqualTo(commercialWorkOrderDetailsDTO2);
        commercialWorkOrderDetailsDTO2.setId(commercialWorkOrderDetailsDTO1.getId());
        assertThat(commercialWorkOrderDetailsDTO1).isEqualTo(commercialWorkOrderDetailsDTO2);
        commercialWorkOrderDetailsDTO2.setId(2L);
        assertThat(commercialWorkOrderDetailsDTO1).isNotEqualTo(commercialWorkOrderDetailsDTO2);
        commercialWorkOrderDetailsDTO1.setId(null);
        assertThat(commercialWorkOrderDetailsDTO1).isNotEqualTo(commercialWorkOrderDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialWorkOrderDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialWorkOrderDetailsMapper.fromId(null)).isNull();
    }
}
