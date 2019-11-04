package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.enumeration.CommercialCurrency;
import org.soptorshi.domain.enumeration.CommercialPaymentCategory;
import org.soptorshi.repository.CommercialPaymentInfoRepository;
import org.soptorshi.repository.search.CommercialPaymentInfoSearchRepository;
import org.soptorshi.service.CommercialPaymentInfoQueryService;
import org.soptorshi.service.CommercialPaymentInfoService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.mapper.CommercialPaymentInfoMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the CommercialPaymentInfoResource REST controller.
 *
 * @see CommercialPaymentInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPaymentInfoResourceIntTest {

    private static final CommercialPaymentCategory DEFAULT_PAYMENT_CATEGORY = CommercialPaymentCategory.UNDEFINED;
    private static final CommercialPaymentCategory UPDATED_PAYMENT_CATEGORY = CommercialPaymentCategory.TT_BASED;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final CommercialCurrency DEFAULT_CURRENCY_TYPE = CommercialCurrency.BDT;
    private static final CommercialCurrency UPDATED_CURRENCY_TYPE = CommercialCurrency.US_DOLLAR;

    private static final String DEFAULT_PAYMENT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERMS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialPaymentInfoRepository commercialPaymentInfoRepository;

    @Autowired
    private CommercialPaymentInfoMapper commercialPaymentInfoMapper;

    @Autowired
    private CommercialPaymentInfoService commercialPaymentInfoService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPaymentInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPaymentInfoSearchRepository mockCommercialPaymentInfoSearchRepository;

    @Autowired
    private CommercialPaymentInfoQueryService commercialPaymentInfoQueryService;

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

    private MockMvc restCommercialPaymentInfoMockMvc;

    private CommercialPaymentInfo commercialPaymentInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPaymentInfoResource commercialPaymentInfoResource = new CommercialPaymentInfoResource(commercialPaymentInfoService, commercialPaymentInfoQueryService);
        this.restCommercialPaymentInfoMockMvc = MockMvcBuilders.standaloneSetup(commercialPaymentInfoResource)
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
    public static CommercialPaymentInfo createEntity(EntityManager em) {
        CommercialPaymentInfo commercialPaymentInfo = new CommercialPaymentInfo()
            .paymentCategory(DEFAULT_PAYMENT_CATEGORY)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .currencyType(DEFAULT_CURRENCY_TYPE)
            .paymentTerms(DEFAULT_PAYMENT_TERMS)
            .createdBy(DEFAULT_CREATED_BY)
            .createOn(DEFAULT_CREATE_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPaymentInfo;
    }

    @Before
    public void initTest() {
        commercialPaymentInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPaymentInfo() throws Exception {
        int databaseSizeBeforeCreate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);
        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPaymentInfo testCommercialPaymentInfo = commercialPaymentInfoList.get(commercialPaymentInfoList.size() - 1);
        assertThat(testCommercialPaymentInfo.getPaymentCategory()).isEqualTo(DEFAULT_PAYMENT_CATEGORY);
        assertThat(testCommercialPaymentInfo.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testCommercialPaymentInfo.getCurrencyType()).isEqualTo(DEFAULT_CURRENCY_TYPE);
        assertThat(testCommercialPaymentInfo.getPaymentTerms()).isEqualTo(DEFAULT_PAYMENT_TERMS);
        assertThat(testCommercialPaymentInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPaymentInfo.getCreateOn()).isEqualTo(DEFAULT_CREATE_ON);
        assertThat(testCommercialPaymentInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPaymentInfo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).save(testCommercialPaymentInfo);
    }

    @Test
    @Transactional
    public void createCommercialPaymentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo with an existing ID
        commercialPaymentInfo.setId(1L);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(0)).save(commercialPaymentInfo);
    }

    @Test
    @Transactional
    public void checkPaymentCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setPaymentCategory(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setTotalAmount(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setCurrencyType(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfos() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/{id}", commercialPaymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPaymentInfo.getId().intValue()))
            .andExpect(jsonPath("$.paymentCategory").value(DEFAULT_PAYMENT_CATEGORY.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.currencyType").value(DEFAULT_CURRENCY_TYPE.toString()))
            .andExpect(jsonPath("$.paymentTerms").value(DEFAULT_PAYMENT_TERMS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createOn").value(DEFAULT_CREATE_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentCategory equals to DEFAULT_PAYMENT_CATEGORY
        defaultCommercialPaymentInfoShouldBeFound("paymentCategory.equals=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the commercialPaymentInfoList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultCommercialPaymentInfoShouldNotBeFound("paymentCategory.equals=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentCategory in DEFAULT_PAYMENT_CATEGORY or UPDATED_PAYMENT_CATEGORY
        defaultCommercialPaymentInfoShouldBeFound("paymentCategory.in=" + DEFAULT_PAYMENT_CATEGORY + "," + UPDATED_PAYMENT_CATEGORY);

        // Get all the commercialPaymentInfoList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultCommercialPaymentInfoShouldNotBeFound("paymentCategory.in=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentCategory is not null
        defaultCommercialPaymentInfoShouldBeFound("paymentCategory.specified=true");

        // Get all the commercialPaymentInfoList where paymentCategory is null
        defaultCommercialPaymentInfoShouldNotBeFound("paymentCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultCommercialPaymentInfoShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the commercialPaymentInfoList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultCommercialPaymentInfoShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the commercialPaymentInfoList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmount is not null
        defaultCommercialPaymentInfoShouldBeFound("totalAmount.specified=true");

        // Get all the commercialPaymentInfoList where totalAmount is null
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCurrencyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where currencyType equals to DEFAULT_CURRENCY_TYPE
        defaultCommercialPaymentInfoShouldBeFound("currencyType.equals=" + DEFAULT_CURRENCY_TYPE);

        // Get all the commercialPaymentInfoList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialPaymentInfoShouldNotBeFound("currencyType.equals=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCurrencyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where currencyType in DEFAULT_CURRENCY_TYPE or UPDATED_CURRENCY_TYPE
        defaultCommercialPaymentInfoShouldBeFound("currencyType.in=" + DEFAULT_CURRENCY_TYPE + "," + UPDATED_CURRENCY_TYPE);

        // Get all the commercialPaymentInfoList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialPaymentInfoShouldNotBeFound("currencyType.in=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCurrencyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where currencyType is not null
        defaultCommercialPaymentInfoShouldBeFound("currencyType.specified=true");

        // Get all the commercialPaymentInfoList where currencyType is null
        defaultCommercialPaymentInfoShouldNotBeFound("currencyType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTermsIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentTerms equals to DEFAULT_PAYMENT_TERMS
        defaultCommercialPaymentInfoShouldBeFound("paymentTerms.equals=" + DEFAULT_PAYMENT_TERMS);

        // Get all the commercialPaymentInfoList where paymentTerms equals to UPDATED_PAYMENT_TERMS
        defaultCommercialPaymentInfoShouldNotBeFound("paymentTerms.equals=" + UPDATED_PAYMENT_TERMS);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTermsIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentTerms in DEFAULT_PAYMENT_TERMS or UPDATED_PAYMENT_TERMS
        defaultCommercialPaymentInfoShouldBeFound("paymentTerms.in=" + DEFAULT_PAYMENT_TERMS + "," + UPDATED_PAYMENT_TERMS);

        // Get all the commercialPaymentInfoList where paymentTerms equals to UPDATED_PAYMENT_TERMS
        defaultCommercialPaymentInfoShouldNotBeFound("paymentTerms.in=" + UPDATED_PAYMENT_TERMS);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTermsIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentTerms is not null
        defaultCommercialPaymentInfoShouldBeFound("paymentTerms.specified=true");

        // Get all the commercialPaymentInfoList where paymentTerms is null
        defaultCommercialPaymentInfoShouldNotBeFound("paymentTerms.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPaymentInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPaymentInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPaymentInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy is not null
        defaultCommercialPaymentInfoShouldBeFound("createdBy.specified=true");

        // Get all the commercialPaymentInfoList where createdBy is null
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreateOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createOn equals to DEFAULT_CREATE_ON
        defaultCommercialPaymentInfoShouldBeFound("createOn.equals=" + DEFAULT_CREATE_ON);

        // Get all the commercialPaymentInfoList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createOn.equals=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreateOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createOn in DEFAULT_CREATE_ON or UPDATED_CREATE_ON
        defaultCommercialPaymentInfoShouldBeFound("createOn.in=" + DEFAULT_CREATE_ON + "," + UPDATED_CREATE_ON);

        // Get all the commercialPaymentInfoList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createOn.in=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreateOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createOn is not null
        defaultCommercialPaymentInfoShouldBeFound("createOn.specified=true");

        // Get all the commercialPaymentInfoList where createOn is null
        defaultCommercialPaymentInfoShouldNotBeFound("createOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreateOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createOn greater than or equals to DEFAULT_CREATE_ON
        defaultCommercialPaymentInfoShouldBeFound("createOn.greaterOrEqualThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPaymentInfoList where createOn greater than or equals to UPDATED_CREATE_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createOn.greaterOrEqualThan=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreateOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createOn less than or equals to DEFAULT_CREATE_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createOn.lessThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPaymentInfoList where createOn less than or equals to UPDATED_CREATE_ON
        defaultCommercialPaymentInfoShouldBeFound("createOn.lessThan=" + UPDATED_CREATE_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPaymentInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPaymentInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy is not null
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPaymentInfoList where updatedBy is null
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn is not null
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPaymentInfoList where updatedOn is null
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialPaymentInfo.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialPaymentInfoList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialPaymentInfoShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialPaymentInfoList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialPaymentInfoShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPaymentInfoShouldBeFound(String filter) throws Exception {
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPaymentInfoShouldNotBeFound(String filter) throws Exception {
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPaymentInfo() throws Exception {
        // Get the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        int databaseSizeBeforeUpdate = commercialPaymentInfoRepository.findAll().size();

        // Update the commercialPaymentInfo
        CommercialPaymentInfo updatedCommercialPaymentInfo = commercialPaymentInfoRepository.findById(commercialPaymentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPaymentInfo are not directly saved in db
        em.detach(updatedCommercialPaymentInfo);
        updatedCommercialPaymentInfo
            .paymentCategory(UPDATED_PAYMENT_CATEGORY)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .currencyType(UPDATED_CURRENCY_TYPE)
            .paymentTerms(UPDATED_PAYMENT_TERMS)
            .createdBy(UPDATED_CREATED_BY)
            .createOn(UPDATED_CREATE_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(updatedCommercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(put("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeUpdate);
        CommercialPaymentInfo testCommercialPaymentInfo = commercialPaymentInfoList.get(commercialPaymentInfoList.size() - 1);
        assertThat(testCommercialPaymentInfo.getPaymentCategory()).isEqualTo(UPDATED_PAYMENT_CATEGORY);
        assertThat(testCommercialPaymentInfo.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCommercialPaymentInfo.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
        assertThat(testCommercialPaymentInfo.getPaymentTerms()).isEqualTo(UPDATED_PAYMENT_TERMS);
        assertThat(testCommercialPaymentInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPaymentInfo.getCreateOn()).isEqualTo(UPDATED_CREATE_ON);
        assertThat(testCommercialPaymentInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPaymentInfo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).save(testCommercialPaymentInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPaymentInfo() throws Exception {
        int databaseSizeBeforeUpdate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPaymentInfoMockMvc.perform(put("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(0)).save(commercialPaymentInfo);
    }

    @Test
    @Transactional
    public void deleteCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        int databaseSizeBeforeDelete = commercialPaymentInfoRepository.findAll().size();

        // Delete the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(delete("/api/commercial-payment-infos/{id}", commercialPaymentInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).deleteById(commercialPaymentInfo.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);
        when(mockCommercialPaymentInfoSearchRepository.search(queryStringQuery("id:" + commercialPaymentInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPaymentInfo), PageRequest.of(0, 1), 1));
        // Search the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/_search/commercial-payment-infos?query=id:" + commercialPaymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerms").value(hasItem(DEFAULT_PAYMENT_TERMS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPaymentInfo.class);
        CommercialPaymentInfo commercialPaymentInfo1 = new CommercialPaymentInfo();
        commercialPaymentInfo1.setId(1L);
        CommercialPaymentInfo commercialPaymentInfo2 = new CommercialPaymentInfo();
        commercialPaymentInfo2.setId(commercialPaymentInfo1.getId());
        assertThat(commercialPaymentInfo1).isEqualTo(commercialPaymentInfo2);
        commercialPaymentInfo2.setId(2L);
        assertThat(commercialPaymentInfo1).isNotEqualTo(commercialPaymentInfo2);
        commercialPaymentInfo1.setId(null);
        assertThat(commercialPaymentInfo1).isNotEqualTo(commercialPaymentInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPaymentInfoDTO.class);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO1 = new CommercialPaymentInfoDTO();
        commercialPaymentInfoDTO1.setId(1L);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO2 = new CommercialPaymentInfoDTO();
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO2.setId(commercialPaymentInfoDTO1.getId());
        assertThat(commercialPaymentInfoDTO1).isEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO2.setId(2L);
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO1.setId(null);
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPaymentInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPaymentInfoMapper.fromId(null)).isNull();
    }
}
