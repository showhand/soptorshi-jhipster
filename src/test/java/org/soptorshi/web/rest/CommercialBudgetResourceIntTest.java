package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialCustomerCategory;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;
import org.soptorshi.repository.CommercialBudgetRepository;
import org.soptorshi.repository.search.CommercialBudgetSearchRepository;
import org.soptorshi.service.CommercialBudgetQueryService;
import org.soptorshi.service.CommercialBudgetService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.mapper.CommercialBudgetMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the CommercialBudgetResource REST controller.
 *
 * @see CommercialBudgetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialBudgetResourceIntTest {

    private static final String DEFAULT_BUDGET_NO = "AAAAAAAAAA";
    private static final String UPDATED_BUDGET_NO = "BBBBBBBBBB";

    private static final CommercialOrderCategory DEFAULT_TYPE = CommercialOrderCategory.LOCAL;
    private static final CommercialOrderCategory UPDATED_TYPE = CommercialOrderCategory.FOREIGN;

    private static final CommercialCustomerCategory DEFAULT_CUSTOMER = CommercialCustomerCategory.ZONE;
    private static final CommercialCustomerCategory UPDATED_CUSTOMER = CommercialCustomerCategory.FOREIGN;

    private static final LocalDate DEFAULT_BUDGET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUDGET_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_OFFERED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_OFFERED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_BUYING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_BUYING_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROFIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROFIT_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROFIT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROFIT_PERCENTAGE = new BigDecimal(2);

    private static final CommercialBudgetStatus DEFAULT_BUDGET_STATUS = CommercialBudgetStatus.WAITING_FOR_APPROVAL;
    private static final CommercialBudgetStatus UPDATED_BUDGET_STATUS = CommercialBudgetStatus.APPROVED;

    private static final String DEFAULT_PROFORMA_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROFORMA_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommercialBudgetRepository commercialBudgetRepository;

    @Autowired
    private CommercialBudgetMapper commercialBudgetMapper;

    @Autowired
    private CommercialBudgetService commercialBudgetService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialBudgetSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialBudgetSearchRepository mockCommercialBudgetSearchRepository;

    @Autowired
    private CommercialBudgetQueryService commercialBudgetQueryService;

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

    private MockMvc restCommercialBudgetMockMvc;

    private CommercialBudget commercialBudget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialBudgetResource commercialBudgetResource = new CommercialBudgetResource(commercialBudgetService, commercialBudgetQueryService);
        this.restCommercialBudgetMockMvc = MockMvcBuilders.standaloneSetup(commercialBudgetResource)
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
    public static CommercialBudget createEntity(EntityManager em) {
        CommercialBudget commercialBudget = new CommercialBudget()
            .budgetNo(DEFAULT_BUDGET_NO)
            .type(DEFAULT_TYPE)
            .customer(DEFAULT_CUSTOMER)
            .budgetDate(DEFAULT_BUDGET_DATE)
            .totalQuantity(DEFAULT_TOTAL_QUANTITY)
            .totalOfferedPrice(DEFAULT_TOTAL_OFFERED_PRICE)
            .totalBuyingPrice(DEFAULT_TOTAL_BUYING_PRICE)
            .profitAmount(DEFAULT_PROFIT_AMOUNT)
            .profitPercentage(DEFAULT_PROFIT_PERCENTAGE)
            .budgetStatus(DEFAULT_BUDGET_STATUS)
            .proformaNo(DEFAULT_PROFORMA_NO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialBudget;
    }

    @Before
    public void initTest() {
        commercialBudget = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialBudget() throws Exception {
        int databaseSizeBeforeCreate = commercialBudgetRepository.findAll().size();

        // Create the CommercialBudget
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);
        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialBudget in the database
        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialBudget testCommercialBudget = commercialBudgetList.get(commercialBudgetList.size() - 1);
        assertThat(testCommercialBudget.getBudgetNo()).isEqualTo(DEFAULT_BUDGET_NO);
        assertThat(testCommercialBudget.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCommercialBudget.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testCommercialBudget.getBudgetDate()).isEqualTo(DEFAULT_BUDGET_DATE);
        assertThat(testCommercialBudget.getTotalQuantity()).isEqualTo(DEFAULT_TOTAL_QUANTITY);
        assertThat(testCommercialBudget.getTotalOfferedPrice()).isEqualTo(DEFAULT_TOTAL_OFFERED_PRICE);
        assertThat(testCommercialBudget.getTotalBuyingPrice()).isEqualTo(DEFAULT_TOTAL_BUYING_PRICE);
        assertThat(testCommercialBudget.getProfitAmount()).isEqualTo(DEFAULT_PROFIT_AMOUNT);
        assertThat(testCommercialBudget.getProfitPercentage()).isEqualTo(DEFAULT_PROFIT_PERCENTAGE);
        assertThat(testCommercialBudget.getBudgetStatus()).isEqualTo(DEFAULT_BUDGET_STATUS);
        assertThat(testCommercialBudget.getProformaNo()).isEqualTo(DEFAULT_PROFORMA_NO);
        assertThat(testCommercialBudget.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialBudget.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialBudget.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialBudget.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialBudget in Elasticsearch
        verify(mockCommercialBudgetSearchRepository, times(1)).save(testCommercialBudget);
    }

    @Test
    @Transactional
    public void createCommercialBudgetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialBudgetRepository.findAll().size();

        // Create the CommercialBudget with an existing ID
        commercialBudget.setId(1L);
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialBudget in the database
        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialBudget in Elasticsearch
        verify(mockCommercialBudgetSearchRepository, times(0)).save(commercialBudget);
    }

    @Test
    @Transactional
    public void checkBudgetNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialBudgetRepository.findAll().size();
        // set the field null
        commercialBudget.setBudgetNo(null);

        // Create the CommercialBudget, which fails.
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialBudgetRepository.findAll().size();
        // set the field null
        commercialBudget.setType(null);

        // Create the CommercialBudget, which fails.
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialBudgetRepository.findAll().size();
        // set the field null
        commercialBudget.setCustomer(null);

        // Create the CommercialBudget, which fails.
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBudgetDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialBudgetRepository.findAll().size();
        // set the field null
        commercialBudget.setBudgetDate(null);

        // Create the CommercialBudget, which fails.
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        restCommercialBudgetMockMvc.perform(post("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgets() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialBudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].budgetNo").value(hasItem(DEFAULT_BUDGET_NO.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].budgetDate").value(hasItem(DEFAULT_BUDGET_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalOfferedPrice").value(hasItem(DEFAULT_TOTAL_OFFERED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalBuyingPrice").value(hasItem(DEFAULT_TOTAL_BUYING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].profitAmount").value(hasItem(DEFAULT_PROFIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].profitPercentage").value(hasItem(DEFAULT_PROFIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].budgetStatus").value(hasItem(DEFAULT_BUDGET_STATUS.toString())))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialBudget() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get the commercialBudget
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets/{id}", commercialBudget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialBudget.getId().intValue()))
            .andExpect(jsonPath("$.budgetNo").value(DEFAULT_BUDGET_NO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER.toString()))
            .andExpect(jsonPath("$.budgetDate").value(DEFAULT_BUDGET_DATE.toString()))
            .andExpect(jsonPath("$.totalQuantity").value(DEFAULT_TOTAL_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalOfferedPrice").value(DEFAULT_TOTAL_OFFERED_PRICE.intValue()))
            .andExpect(jsonPath("$.totalBuyingPrice").value(DEFAULT_TOTAL_BUYING_PRICE.intValue()))
            .andExpect(jsonPath("$.profitAmount").value(DEFAULT_PROFIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.profitPercentage").value(DEFAULT_PROFIT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.budgetStatus").value(DEFAULT_BUDGET_STATUS.toString()))
            .andExpect(jsonPath("$.proformaNo").value(DEFAULT_PROFORMA_NO.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetNo equals to DEFAULT_BUDGET_NO
        defaultCommercialBudgetShouldBeFound("budgetNo.equals=" + DEFAULT_BUDGET_NO);

        // Get all the commercialBudgetList where budgetNo equals to UPDATED_BUDGET_NO
        defaultCommercialBudgetShouldNotBeFound("budgetNo.equals=" + UPDATED_BUDGET_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetNo in DEFAULT_BUDGET_NO or UPDATED_BUDGET_NO
        defaultCommercialBudgetShouldBeFound("budgetNo.in=" + DEFAULT_BUDGET_NO + "," + UPDATED_BUDGET_NO);

        // Get all the commercialBudgetList where budgetNo equals to UPDATED_BUDGET_NO
        defaultCommercialBudgetShouldNotBeFound("budgetNo.in=" + UPDATED_BUDGET_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetNo is not null
        defaultCommercialBudgetShouldBeFound("budgetNo.specified=true");

        // Get all the commercialBudgetList where budgetNo is null
        defaultCommercialBudgetShouldNotBeFound("budgetNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where type equals to DEFAULT_TYPE
        defaultCommercialBudgetShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the commercialBudgetList where type equals to UPDATED_TYPE
        defaultCommercialBudgetShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCommercialBudgetShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the commercialBudgetList where type equals to UPDATED_TYPE
        defaultCommercialBudgetShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where type is not null
        defaultCommercialBudgetShouldBeFound("type.specified=true");

        // Get all the commercialBudgetList where type is null
        defaultCommercialBudgetShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where customer equals to DEFAULT_CUSTOMER
        defaultCommercialBudgetShouldBeFound("customer.equals=" + DEFAULT_CUSTOMER);

        // Get all the commercialBudgetList where customer equals to UPDATED_CUSTOMER
        defaultCommercialBudgetShouldNotBeFound("customer.equals=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCustomerIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where customer in DEFAULT_CUSTOMER or UPDATED_CUSTOMER
        defaultCommercialBudgetShouldBeFound("customer.in=" + DEFAULT_CUSTOMER + "," + UPDATED_CUSTOMER);

        // Get all the commercialBudgetList where customer equals to UPDATED_CUSTOMER
        defaultCommercialBudgetShouldNotBeFound("customer.in=" + UPDATED_CUSTOMER);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where customer is not null
        defaultCommercialBudgetShouldBeFound("customer.specified=true");

        // Get all the commercialBudgetList where customer is null
        defaultCommercialBudgetShouldNotBeFound("customer.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetDate equals to DEFAULT_BUDGET_DATE
        defaultCommercialBudgetShouldBeFound("budgetDate.equals=" + DEFAULT_BUDGET_DATE);

        // Get all the commercialBudgetList where budgetDate equals to UPDATED_BUDGET_DATE
        defaultCommercialBudgetShouldNotBeFound("budgetDate.equals=" + UPDATED_BUDGET_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetDate in DEFAULT_BUDGET_DATE or UPDATED_BUDGET_DATE
        defaultCommercialBudgetShouldBeFound("budgetDate.in=" + DEFAULT_BUDGET_DATE + "," + UPDATED_BUDGET_DATE);

        // Get all the commercialBudgetList where budgetDate equals to UPDATED_BUDGET_DATE
        defaultCommercialBudgetShouldNotBeFound("budgetDate.in=" + UPDATED_BUDGET_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetDate is not null
        defaultCommercialBudgetShouldBeFound("budgetDate.specified=true");

        // Get all the commercialBudgetList where budgetDate is null
        defaultCommercialBudgetShouldNotBeFound("budgetDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetDate greater than or equals to DEFAULT_BUDGET_DATE
        defaultCommercialBudgetShouldBeFound("budgetDate.greaterOrEqualThan=" + DEFAULT_BUDGET_DATE);

        // Get all the commercialBudgetList where budgetDate greater than or equals to UPDATED_BUDGET_DATE
        defaultCommercialBudgetShouldNotBeFound("budgetDate.greaterOrEqualThan=" + UPDATED_BUDGET_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetDate less than or equals to DEFAULT_BUDGET_DATE
        defaultCommercialBudgetShouldNotBeFound("budgetDate.lessThan=" + DEFAULT_BUDGET_DATE);

        // Get all the commercialBudgetList where budgetDate less than or equals to UPDATED_BUDGET_DATE
        defaultCommercialBudgetShouldBeFound("budgetDate.lessThan=" + UPDATED_BUDGET_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalQuantity equals to DEFAULT_TOTAL_QUANTITY
        defaultCommercialBudgetShouldBeFound("totalQuantity.equals=" + DEFAULT_TOTAL_QUANTITY);

        // Get all the commercialBudgetList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultCommercialBudgetShouldNotBeFound("totalQuantity.equals=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalQuantity in DEFAULT_TOTAL_QUANTITY or UPDATED_TOTAL_QUANTITY
        defaultCommercialBudgetShouldBeFound("totalQuantity.in=" + DEFAULT_TOTAL_QUANTITY + "," + UPDATED_TOTAL_QUANTITY);

        // Get all the commercialBudgetList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultCommercialBudgetShouldNotBeFound("totalQuantity.in=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalQuantity is not null
        defaultCommercialBudgetShouldBeFound("totalQuantity.specified=true");

        // Get all the commercialBudgetList where totalQuantity is null
        defaultCommercialBudgetShouldNotBeFound("totalQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalOfferedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalOfferedPrice equals to DEFAULT_TOTAL_OFFERED_PRICE
        defaultCommercialBudgetShouldBeFound("totalOfferedPrice.equals=" + DEFAULT_TOTAL_OFFERED_PRICE);

        // Get all the commercialBudgetList where totalOfferedPrice equals to UPDATED_TOTAL_OFFERED_PRICE
        defaultCommercialBudgetShouldNotBeFound("totalOfferedPrice.equals=" + UPDATED_TOTAL_OFFERED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalOfferedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalOfferedPrice in DEFAULT_TOTAL_OFFERED_PRICE or UPDATED_TOTAL_OFFERED_PRICE
        defaultCommercialBudgetShouldBeFound("totalOfferedPrice.in=" + DEFAULT_TOTAL_OFFERED_PRICE + "," + UPDATED_TOTAL_OFFERED_PRICE);

        // Get all the commercialBudgetList where totalOfferedPrice equals to UPDATED_TOTAL_OFFERED_PRICE
        defaultCommercialBudgetShouldNotBeFound("totalOfferedPrice.in=" + UPDATED_TOTAL_OFFERED_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalOfferedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalOfferedPrice is not null
        defaultCommercialBudgetShouldBeFound("totalOfferedPrice.specified=true");

        // Get all the commercialBudgetList where totalOfferedPrice is null
        defaultCommercialBudgetShouldNotBeFound("totalOfferedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalBuyingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalBuyingPrice equals to DEFAULT_TOTAL_BUYING_PRICE
        defaultCommercialBudgetShouldBeFound("totalBuyingPrice.equals=" + DEFAULT_TOTAL_BUYING_PRICE);

        // Get all the commercialBudgetList where totalBuyingPrice equals to UPDATED_TOTAL_BUYING_PRICE
        defaultCommercialBudgetShouldNotBeFound("totalBuyingPrice.equals=" + UPDATED_TOTAL_BUYING_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalBuyingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalBuyingPrice in DEFAULT_TOTAL_BUYING_PRICE or UPDATED_TOTAL_BUYING_PRICE
        defaultCommercialBudgetShouldBeFound("totalBuyingPrice.in=" + DEFAULT_TOTAL_BUYING_PRICE + "," + UPDATED_TOTAL_BUYING_PRICE);

        // Get all the commercialBudgetList where totalBuyingPrice equals to UPDATED_TOTAL_BUYING_PRICE
        defaultCommercialBudgetShouldNotBeFound("totalBuyingPrice.in=" + UPDATED_TOTAL_BUYING_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByTotalBuyingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where totalBuyingPrice is not null
        defaultCommercialBudgetShouldBeFound("totalBuyingPrice.specified=true");

        // Get all the commercialBudgetList where totalBuyingPrice is null
        defaultCommercialBudgetShouldNotBeFound("totalBuyingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitAmount equals to DEFAULT_PROFIT_AMOUNT
        defaultCommercialBudgetShouldBeFound("profitAmount.equals=" + DEFAULT_PROFIT_AMOUNT);

        // Get all the commercialBudgetList where profitAmount equals to UPDATED_PROFIT_AMOUNT
        defaultCommercialBudgetShouldNotBeFound("profitAmount.equals=" + UPDATED_PROFIT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitAmountIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitAmount in DEFAULT_PROFIT_AMOUNT or UPDATED_PROFIT_AMOUNT
        defaultCommercialBudgetShouldBeFound("profitAmount.in=" + DEFAULT_PROFIT_AMOUNT + "," + UPDATED_PROFIT_AMOUNT);

        // Get all the commercialBudgetList where profitAmount equals to UPDATED_PROFIT_AMOUNT
        defaultCommercialBudgetShouldNotBeFound("profitAmount.in=" + UPDATED_PROFIT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitAmount is not null
        defaultCommercialBudgetShouldBeFound("profitAmount.specified=true");

        // Get all the commercialBudgetList where profitAmount is null
        defaultCommercialBudgetShouldNotBeFound("profitAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitPercentage equals to DEFAULT_PROFIT_PERCENTAGE
        defaultCommercialBudgetShouldBeFound("profitPercentage.equals=" + DEFAULT_PROFIT_PERCENTAGE);

        // Get all the commercialBudgetList where profitPercentage equals to UPDATED_PROFIT_PERCENTAGE
        defaultCommercialBudgetShouldNotBeFound("profitPercentage.equals=" + UPDATED_PROFIT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitPercentage in DEFAULT_PROFIT_PERCENTAGE or UPDATED_PROFIT_PERCENTAGE
        defaultCommercialBudgetShouldBeFound("profitPercentage.in=" + DEFAULT_PROFIT_PERCENTAGE + "," + UPDATED_PROFIT_PERCENTAGE);

        // Get all the commercialBudgetList where profitPercentage equals to UPDATED_PROFIT_PERCENTAGE
        defaultCommercialBudgetShouldNotBeFound("profitPercentage.in=" + UPDATED_PROFIT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProfitPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where profitPercentage is not null
        defaultCommercialBudgetShouldBeFound("profitPercentage.specified=true");

        // Get all the commercialBudgetList where profitPercentage is null
        defaultCommercialBudgetShouldNotBeFound("profitPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetStatus equals to DEFAULT_BUDGET_STATUS
        defaultCommercialBudgetShouldBeFound("budgetStatus.equals=" + DEFAULT_BUDGET_STATUS);

        // Get all the commercialBudgetList where budgetStatus equals to UPDATED_BUDGET_STATUS
        defaultCommercialBudgetShouldNotBeFound("budgetStatus.equals=" + UPDATED_BUDGET_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetStatus in DEFAULT_BUDGET_STATUS or UPDATED_BUDGET_STATUS
        defaultCommercialBudgetShouldBeFound("budgetStatus.in=" + DEFAULT_BUDGET_STATUS + "," + UPDATED_BUDGET_STATUS);

        // Get all the commercialBudgetList where budgetStatus equals to UPDATED_BUDGET_STATUS
        defaultCommercialBudgetShouldNotBeFound("budgetStatus.in=" + UPDATED_BUDGET_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByBudgetStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where budgetStatus is not null
        defaultCommercialBudgetShouldBeFound("budgetStatus.specified=true");

        // Get all the commercialBudgetList where budgetStatus is null
        defaultCommercialBudgetShouldNotBeFound("budgetStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProformaNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where proformaNo equals to DEFAULT_PROFORMA_NO
        defaultCommercialBudgetShouldBeFound("proformaNo.equals=" + DEFAULT_PROFORMA_NO);

        // Get all the commercialBudgetList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialBudgetShouldNotBeFound("proformaNo.equals=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProformaNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where proformaNo in DEFAULT_PROFORMA_NO or UPDATED_PROFORMA_NO
        defaultCommercialBudgetShouldBeFound("proformaNo.in=" + DEFAULT_PROFORMA_NO + "," + UPDATED_PROFORMA_NO);

        // Get all the commercialBudgetList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialBudgetShouldNotBeFound("proformaNo.in=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByProformaNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where proformaNo is not null
        defaultCommercialBudgetShouldBeFound("proformaNo.specified=true");

        // Get all the commercialBudgetList where proformaNo is null
        defaultCommercialBudgetShouldNotBeFound("proformaNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialBudgetShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialBudgetList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialBudgetShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialBudgetShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialBudgetList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialBudgetShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdBy is not null
        defaultCommercialBudgetShouldBeFound("createdBy.specified=true");

        // Get all the commercialBudgetList where createdBy is null
        defaultCommercialBudgetShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialBudgetShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialBudgetList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialBudgetShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialBudgetShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialBudgetList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialBudgetShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where createdOn is not null
        defaultCommercialBudgetShouldBeFound("createdOn.specified=true");

        // Get all the commercialBudgetList where createdOn is null
        defaultCommercialBudgetShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialBudgetShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialBudgetList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialBudgetShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialBudgetShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialBudgetList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialBudgetShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedBy is not null
        defaultCommercialBudgetShouldBeFound("updatedBy.specified=true");

        // Get all the commercialBudgetList where updatedBy is null
        defaultCommercialBudgetShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialBudgetShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialBudgetList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialBudgetShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialBudgetShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialBudgetList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialBudgetShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialBudgetsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        // Get all the commercialBudgetList where updatedOn is not null
        defaultCommercialBudgetShouldBeFound("updatedOn.specified=true");

        // Get all the commercialBudgetList where updatedOn is null
        defaultCommercialBudgetShouldNotBeFound("updatedOn.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialBudgetShouldBeFound(String filter) throws Exception {
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialBudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].budgetNo").value(hasItem(DEFAULT_BUDGET_NO)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].budgetDate").value(hasItem(DEFAULT_BUDGET_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalOfferedPrice").value(hasItem(DEFAULT_TOTAL_OFFERED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalBuyingPrice").value(hasItem(DEFAULT_TOTAL_BUYING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].profitAmount").value(hasItem(DEFAULT_PROFIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].profitPercentage").value(hasItem(DEFAULT_PROFIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].budgetStatus").value(hasItem(DEFAULT_BUDGET_STATUS.toString())))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialBudgetShouldNotBeFound(String filter) throws Exception {
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialBudget() throws Exception {
        // Get the commercialBudget
        restCommercialBudgetMockMvc.perform(get("/api/commercial-budgets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialBudget() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        int databaseSizeBeforeUpdate = commercialBudgetRepository.findAll().size();

        // Update the commercialBudget
        CommercialBudget updatedCommercialBudget = commercialBudgetRepository.findById(commercialBudget.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialBudget are not directly saved in db
        em.detach(updatedCommercialBudget);
        updatedCommercialBudget
            .budgetNo(UPDATED_BUDGET_NO)
            .type(UPDATED_TYPE)
            .customer(UPDATED_CUSTOMER)
            .budgetDate(UPDATED_BUDGET_DATE)
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .totalOfferedPrice(UPDATED_TOTAL_OFFERED_PRICE)
            .totalBuyingPrice(UPDATED_TOTAL_BUYING_PRICE)
            .profitAmount(UPDATED_PROFIT_AMOUNT)
            .profitPercentage(UPDATED_PROFIT_PERCENTAGE)
            .budgetStatus(UPDATED_BUDGET_STATUS)
            .proformaNo(UPDATED_PROFORMA_NO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(updatedCommercialBudget);

        restCommercialBudgetMockMvc.perform(put("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialBudget in the database
        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeUpdate);
        CommercialBudget testCommercialBudget = commercialBudgetList.get(commercialBudgetList.size() - 1);
        assertThat(testCommercialBudget.getBudgetNo()).isEqualTo(UPDATED_BUDGET_NO);
        assertThat(testCommercialBudget.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCommercialBudget.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testCommercialBudget.getBudgetDate()).isEqualTo(UPDATED_BUDGET_DATE);
        assertThat(testCommercialBudget.getTotalQuantity()).isEqualTo(UPDATED_TOTAL_QUANTITY);
        assertThat(testCommercialBudget.getTotalOfferedPrice()).isEqualTo(UPDATED_TOTAL_OFFERED_PRICE);
        assertThat(testCommercialBudget.getTotalBuyingPrice()).isEqualTo(UPDATED_TOTAL_BUYING_PRICE);
        assertThat(testCommercialBudget.getProfitAmount()).isEqualTo(UPDATED_PROFIT_AMOUNT);
        assertThat(testCommercialBudget.getProfitPercentage()).isEqualTo(UPDATED_PROFIT_PERCENTAGE);
        assertThat(testCommercialBudget.getBudgetStatus()).isEqualTo(UPDATED_BUDGET_STATUS);
        assertThat(testCommercialBudget.getProformaNo()).isEqualTo(UPDATED_PROFORMA_NO);
        assertThat(testCommercialBudget.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialBudget.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialBudget.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialBudget.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialBudget in Elasticsearch
        verify(mockCommercialBudgetSearchRepository, times(1)).save(testCommercialBudget);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialBudget() throws Exception {
        int databaseSizeBeforeUpdate = commercialBudgetRepository.findAll().size();

        // Create the CommercialBudget
        CommercialBudgetDTO commercialBudgetDTO = commercialBudgetMapper.toDto(commercialBudget);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialBudgetMockMvc.perform(put("/api/commercial-budgets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialBudgetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialBudget in the database
        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialBudget in Elasticsearch
        verify(mockCommercialBudgetSearchRepository, times(0)).save(commercialBudget);
    }

    @Test
    @Transactional
    public void deleteCommercialBudget() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);

        int databaseSizeBeforeDelete = commercialBudgetRepository.findAll().size();

        // Delete the commercialBudget
        restCommercialBudgetMockMvc.perform(delete("/api/commercial-budgets/{id}", commercialBudget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialBudget> commercialBudgetList = commercialBudgetRepository.findAll();
        assertThat(commercialBudgetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialBudget in Elasticsearch
        verify(mockCommercialBudgetSearchRepository, times(1)).deleteById(commercialBudget.getId());
    }

    @Test
    @Transactional
    public void searchCommercialBudget() throws Exception {
        // Initialize the database
        commercialBudgetRepository.saveAndFlush(commercialBudget);
        when(mockCommercialBudgetSearchRepository.search(queryStringQuery("id:" + commercialBudget.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialBudget), PageRequest.of(0, 1), 1));
        // Search the commercialBudget
        restCommercialBudgetMockMvc.perform(get("/api/_search/commercial-budgets?query=id:" + commercialBudget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialBudget.getId().intValue())))
            .andExpect(jsonPath("$.[*].budgetNo").value(hasItem(DEFAULT_BUDGET_NO)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customer").value(hasItem(DEFAULT_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].budgetDate").value(hasItem(DEFAULT_BUDGET_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalOfferedPrice").value(hasItem(DEFAULT_TOTAL_OFFERED_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalBuyingPrice").value(hasItem(DEFAULT_TOTAL_BUYING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].profitAmount").value(hasItem(DEFAULT_PROFIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].profitPercentage").value(hasItem(DEFAULT_PROFIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].budgetStatus").value(hasItem(DEFAULT_BUDGET_STATUS.toString())))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialBudget.class);
        CommercialBudget commercialBudget1 = new CommercialBudget();
        commercialBudget1.setId(1L);
        CommercialBudget commercialBudget2 = new CommercialBudget();
        commercialBudget2.setId(commercialBudget1.getId());
        assertThat(commercialBudget1).isEqualTo(commercialBudget2);
        commercialBudget2.setId(2L);
        assertThat(commercialBudget1).isNotEqualTo(commercialBudget2);
        commercialBudget1.setId(null);
        assertThat(commercialBudget1).isNotEqualTo(commercialBudget2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialBudgetDTO.class);
        CommercialBudgetDTO commercialBudgetDTO1 = new CommercialBudgetDTO();
        commercialBudgetDTO1.setId(1L);
        CommercialBudgetDTO commercialBudgetDTO2 = new CommercialBudgetDTO();
        assertThat(commercialBudgetDTO1).isNotEqualTo(commercialBudgetDTO2);
        commercialBudgetDTO2.setId(commercialBudgetDTO1.getId());
        assertThat(commercialBudgetDTO1).isEqualTo(commercialBudgetDTO2);
        commercialBudgetDTO2.setId(2L);
        assertThat(commercialBudgetDTO1).isNotEqualTo(commercialBudgetDTO2);
        commercialBudgetDTO1.setId(null);
        assertThat(commercialBudgetDTO1).isNotEqualTo(commercialBudgetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialBudgetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialBudgetMapper.fromId(null)).isNull();
    }
}
