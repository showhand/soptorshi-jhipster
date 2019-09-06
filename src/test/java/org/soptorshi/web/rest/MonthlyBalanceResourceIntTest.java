package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.MonthlyBalance;
import org.soptorshi.domain.AccountBalance;
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.search.MonthlyBalanceSearchRepository;
import org.soptorshi.service.MonthlyBalanceService;
import org.soptorshi.service.dto.MonthlyBalanceDTO;
import org.soptorshi.service.mapper.MonthlyBalanceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.MonthlyBalanceCriteria;
import org.soptorshi.service.MonthlyBalanceQueryService;

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

import org.soptorshi.domain.enumeration.MonthType;
/**
 * Test class for the MonthlyBalanceResource REST controller.
 *
 * @see MonthlyBalanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class MonthlyBalanceResourceIntTest {

    private static final MonthType DEFAULT_MONTH_TYPE = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH_TYPE = MonthType.FEBRUARY;

    private static final BigDecimal DEFAULT_TOT_MONTH_DB_BAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOT_MONTH_DB_BAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOT_MONTH_CR_BAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOT_MONTH_CR_BAL = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MonthlyBalanceRepository monthlyBalanceRepository;

    @Autowired
    private MonthlyBalanceMapper monthlyBalanceMapper;

    @Autowired
    private MonthlyBalanceService monthlyBalanceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.MonthlyBalanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private MonthlyBalanceSearchRepository mockMonthlyBalanceSearchRepository;

    @Autowired
    private MonthlyBalanceQueryService monthlyBalanceQueryService;

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

    private MockMvc restMonthlyBalanceMockMvc;

    private MonthlyBalance monthlyBalance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonthlyBalanceResource monthlyBalanceResource = new MonthlyBalanceResource(monthlyBalanceService, monthlyBalanceQueryService);
        this.restMonthlyBalanceMockMvc = MockMvcBuilders.standaloneSetup(monthlyBalanceResource)
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
    public static MonthlyBalance createEntity(EntityManager em) {
        MonthlyBalance monthlyBalance = new MonthlyBalance()
            .monthType(DEFAULT_MONTH_TYPE)
            .totMonthDbBal(DEFAULT_TOT_MONTH_DB_BAL)
            .totMonthCrBal(DEFAULT_TOT_MONTH_CR_BAL)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return monthlyBalance;
    }

    @Before
    public void initTest() {
        monthlyBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlyBalance() throws Exception {
        int databaseSizeBeforeCreate = monthlyBalanceRepository.findAll().size();

        // Create the MonthlyBalance
        MonthlyBalanceDTO monthlyBalanceDTO = monthlyBalanceMapper.toDto(monthlyBalance);
        restMonthlyBalanceMockMvc.perform(post("/api/monthly-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the MonthlyBalance in the database
        List<MonthlyBalance> monthlyBalanceList = monthlyBalanceRepository.findAll();
        assertThat(monthlyBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyBalance testMonthlyBalance = monthlyBalanceList.get(monthlyBalanceList.size() - 1);
        assertThat(testMonthlyBalance.getMonthType()).isEqualTo(DEFAULT_MONTH_TYPE);
        assertThat(testMonthlyBalance.getTotMonthDbBal()).isEqualTo(DEFAULT_TOT_MONTH_DB_BAL);
        assertThat(testMonthlyBalance.getTotMonthCrBal()).isEqualTo(DEFAULT_TOT_MONTH_CR_BAL);
        assertThat(testMonthlyBalance.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMonthlyBalance.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the MonthlyBalance in Elasticsearch
        verify(mockMonthlyBalanceSearchRepository, times(1)).save(testMonthlyBalance);
    }

    @Test
    @Transactional
    public void createMonthlyBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlyBalanceRepository.findAll().size();

        // Create the MonthlyBalance with an existing ID
        monthlyBalance.setId(1L);
        MonthlyBalanceDTO monthlyBalanceDTO = monthlyBalanceMapper.toDto(monthlyBalance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyBalanceMockMvc.perform(post("/api/monthly-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlyBalance in the database
        List<MonthlyBalance> monthlyBalanceList = monthlyBalanceRepository.findAll();
        assertThat(monthlyBalanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the MonthlyBalance in Elasticsearch
        verify(mockMonthlyBalanceSearchRepository, times(0)).save(monthlyBalance);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalances() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totMonthDbBal").value(hasItem(DEFAULT_TOT_MONTH_DB_BAL.intValue())))
            .andExpect(jsonPath("$.[*].totMonthCrBal").value(hasItem(DEFAULT_TOT_MONTH_CR_BAL.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMonthlyBalance() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get the monthlyBalance
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances/{id}", monthlyBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyBalance.getId().intValue()))
            .andExpect(jsonPath("$.monthType").value(DEFAULT_MONTH_TYPE.toString()))
            .andExpect(jsonPath("$.totMonthDbBal").value(DEFAULT_TOT_MONTH_DB_BAL.intValue()))
            .andExpect(jsonPath("$.totMonthCrBal").value(DEFAULT_TOT_MONTH_CR_BAL.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByMonthTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where monthType equals to DEFAULT_MONTH_TYPE
        defaultMonthlyBalanceShouldBeFound("monthType.equals=" + DEFAULT_MONTH_TYPE);

        // Get all the monthlyBalanceList where monthType equals to UPDATED_MONTH_TYPE
        defaultMonthlyBalanceShouldNotBeFound("monthType.equals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByMonthTypeIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where monthType in DEFAULT_MONTH_TYPE or UPDATED_MONTH_TYPE
        defaultMonthlyBalanceShouldBeFound("monthType.in=" + DEFAULT_MONTH_TYPE + "," + UPDATED_MONTH_TYPE);

        // Get all the monthlyBalanceList where monthType equals to UPDATED_MONTH_TYPE
        defaultMonthlyBalanceShouldNotBeFound("monthType.in=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByMonthTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where monthType is not null
        defaultMonthlyBalanceShouldBeFound("monthType.specified=true");

        // Get all the monthlyBalanceList where monthType is null
        defaultMonthlyBalanceShouldNotBeFound("monthType.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthDbBalIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthDbBal equals to DEFAULT_TOT_MONTH_DB_BAL
        defaultMonthlyBalanceShouldBeFound("totMonthDbBal.equals=" + DEFAULT_TOT_MONTH_DB_BAL);

        // Get all the monthlyBalanceList where totMonthDbBal equals to UPDATED_TOT_MONTH_DB_BAL
        defaultMonthlyBalanceShouldNotBeFound("totMonthDbBal.equals=" + UPDATED_TOT_MONTH_DB_BAL);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthDbBalIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthDbBal in DEFAULT_TOT_MONTH_DB_BAL or UPDATED_TOT_MONTH_DB_BAL
        defaultMonthlyBalanceShouldBeFound("totMonthDbBal.in=" + DEFAULT_TOT_MONTH_DB_BAL + "," + UPDATED_TOT_MONTH_DB_BAL);

        // Get all the monthlyBalanceList where totMonthDbBal equals to UPDATED_TOT_MONTH_DB_BAL
        defaultMonthlyBalanceShouldNotBeFound("totMonthDbBal.in=" + UPDATED_TOT_MONTH_DB_BAL);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthDbBalIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthDbBal is not null
        defaultMonthlyBalanceShouldBeFound("totMonthDbBal.specified=true");

        // Get all the monthlyBalanceList where totMonthDbBal is null
        defaultMonthlyBalanceShouldNotBeFound("totMonthDbBal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthCrBalIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthCrBal equals to DEFAULT_TOT_MONTH_CR_BAL
        defaultMonthlyBalanceShouldBeFound("totMonthCrBal.equals=" + DEFAULT_TOT_MONTH_CR_BAL);

        // Get all the monthlyBalanceList where totMonthCrBal equals to UPDATED_TOT_MONTH_CR_BAL
        defaultMonthlyBalanceShouldNotBeFound("totMonthCrBal.equals=" + UPDATED_TOT_MONTH_CR_BAL);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthCrBalIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthCrBal in DEFAULT_TOT_MONTH_CR_BAL or UPDATED_TOT_MONTH_CR_BAL
        defaultMonthlyBalanceShouldBeFound("totMonthCrBal.in=" + DEFAULT_TOT_MONTH_CR_BAL + "," + UPDATED_TOT_MONTH_CR_BAL);

        // Get all the monthlyBalanceList where totMonthCrBal equals to UPDATED_TOT_MONTH_CR_BAL
        defaultMonthlyBalanceShouldNotBeFound("totMonthCrBal.in=" + UPDATED_TOT_MONTH_CR_BAL);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByTotMonthCrBalIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where totMonthCrBal is not null
        defaultMonthlyBalanceShouldBeFound("totMonthCrBal.specified=true");

        // Get all the monthlyBalanceList where totMonthCrBal is null
        defaultMonthlyBalanceShouldNotBeFound("totMonthCrBal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultMonthlyBalanceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the monthlyBalanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMonthlyBalanceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultMonthlyBalanceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the monthlyBalanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMonthlyBalanceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedBy is not null
        defaultMonthlyBalanceShouldBeFound("modifiedBy.specified=true");

        // Get all the monthlyBalanceList where modifiedBy is null
        defaultMonthlyBalanceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultMonthlyBalanceShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlyBalanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMonthlyBalanceShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultMonthlyBalanceShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the monthlyBalanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMonthlyBalanceShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedOn is not null
        defaultMonthlyBalanceShouldBeFound("modifiedOn.specified=true");

        // Get all the monthlyBalanceList where modifiedOn is null
        defaultMonthlyBalanceShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultMonthlyBalanceShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlyBalanceList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultMonthlyBalanceShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlyBalancesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        // Get all the monthlyBalanceList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultMonthlyBalanceShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlyBalanceList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultMonthlyBalanceShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllMonthlyBalancesByAccountBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        AccountBalance accountBalance = AccountBalanceResourceIntTest.createEntity(em);
        em.persist(accountBalance);
        em.flush();
        monthlyBalance.setAccountBalance(accountBalance);
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);
        Long accountBalanceId = accountBalance.getId();

        // Get all the monthlyBalanceList where accountBalance equals to accountBalanceId
        defaultMonthlyBalanceShouldBeFound("accountBalanceId.equals=" + accountBalanceId);

        // Get all the monthlyBalanceList where accountBalance equals to accountBalanceId + 1
        defaultMonthlyBalanceShouldNotBeFound("accountBalanceId.equals=" + (accountBalanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMonthlyBalanceShouldBeFound(String filter) throws Exception {
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totMonthDbBal").value(hasItem(DEFAULT_TOT_MONTH_DB_BAL.intValue())))
            .andExpect(jsonPath("$.[*].totMonthCrBal").value(hasItem(DEFAULT_TOT_MONTH_CR_BAL.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMonthlyBalanceShouldNotBeFound(String filter) throws Exception {
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMonthlyBalance() throws Exception {
        // Get the monthlyBalance
        restMonthlyBalanceMockMvc.perform(get("/api/monthly-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlyBalance() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        int databaseSizeBeforeUpdate = monthlyBalanceRepository.findAll().size();

        // Update the monthlyBalance
        MonthlyBalance updatedMonthlyBalance = monthlyBalanceRepository.findById(monthlyBalance.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlyBalance are not directly saved in db
        em.detach(updatedMonthlyBalance);
        updatedMonthlyBalance
            .monthType(UPDATED_MONTH_TYPE)
            .totMonthDbBal(UPDATED_TOT_MONTH_DB_BAL)
            .totMonthCrBal(UPDATED_TOT_MONTH_CR_BAL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        MonthlyBalanceDTO monthlyBalanceDTO = monthlyBalanceMapper.toDto(updatedMonthlyBalance);

        restMonthlyBalanceMockMvc.perform(put("/api/monthly-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyBalanceDTO)))
            .andExpect(status().isOk());

        // Validate the MonthlyBalance in the database
        List<MonthlyBalance> monthlyBalanceList = monthlyBalanceRepository.findAll();
        assertThat(monthlyBalanceList).hasSize(databaseSizeBeforeUpdate);
        MonthlyBalance testMonthlyBalance = monthlyBalanceList.get(monthlyBalanceList.size() - 1);
        assertThat(testMonthlyBalance.getMonthType()).isEqualTo(UPDATED_MONTH_TYPE);
        assertThat(testMonthlyBalance.getTotMonthDbBal()).isEqualTo(UPDATED_TOT_MONTH_DB_BAL);
        assertThat(testMonthlyBalance.getTotMonthCrBal()).isEqualTo(UPDATED_TOT_MONTH_CR_BAL);
        assertThat(testMonthlyBalance.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMonthlyBalance.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the MonthlyBalance in Elasticsearch
        verify(mockMonthlyBalanceSearchRepository, times(1)).save(testMonthlyBalance);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlyBalance() throws Exception {
        int databaseSizeBeforeUpdate = monthlyBalanceRepository.findAll().size();

        // Create the MonthlyBalance
        MonthlyBalanceDTO monthlyBalanceDTO = monthlyBalanceMapper.toDto(monthlyBalance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyBalanceMockMvc.perform(put("/api/monthly-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlyBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlyBalance in the database
        List<MonthlyBalance> monthlyBalanceList = monthlyBalanceRepository.findAll();
        assertThat(monthlyBalanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MonthlyBalance in Elasticsearch
        verify(mockMonthlyBalanceSearchRepository, times(0)).save(monthlyBalance);
    }

    @Test
    @Transactional
    public void deleteMonthlyBalance() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);

        int databaseSizeBeforeDelete = monthlyBalanceRepository.findAll().size();

        // Delete the monthlyBalance
        restMonthlyBalanceMockMvc.perform(delete("/api/monthly-balances/{id}", monthlyBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MonthlyBalance> monthlyBalanceList = monthlyBalanceRepository.findAll();
        assertThat(monthlyBalanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MonthlyBalance in Elasticsearch
        verify(mockMonthlyBalanceSearchRepository, times(1)).deleteById(monthlyBalance.getId());
    }

    @Test
    @Transactional
    public void searchMonthlyBalance() throws Exception {
        // Initialize the database
        monthlyBalanceRepository.saveAndFlush(monthlyBalance);
        when(mockMonthlyBalanceSearchRepository.search(queryStringQuery("id:" + monthlyBalance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(monthlyBalance), PageRequest.of(0, 1), 1));
        // Search the monthlyBalance
        restMonthlyBalanceMockMvc.perform(get("/api/_search/monthly-balances?query=id:" + monthlyBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totMonthDbBal").value(hasItem(DEFAULT_TOT_MONTH_DB_BAL.intValue())))
            .andExpect(jsonPath("$.[*].totMonthCrBal").value(hasItem(DEFAULT_TOT_MONTH_CR_BAL.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyBalance.class);
        MonthlyBalance monthlyBalance1 = new MonthlyBalance();
        monthlyBalance1.setId(1L);
        MonthlyBalance monthlyBalance2 = new MonthlyBalance();
        monthlyBalance2.setId(monthlyBalance1.getId());
        assertThat(monthlyBalance1).isEqualTo(monthlyBalance2);
        monthlyBalance2.setId(2L);
        assertThat(monthlyBalance1).isNotEqualTo(monthlyBalance2);
        monthlyBalance1.setId(null);
        assertThat(monthlyBalance1).isNotEqualTo(monthlyBalance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyBalanceDTO.class);
        MonthlyBalanceDTO monthlyBalanceDTO1 = new MonthlyBalanceDTO();
        monthlyBalanceDTO1.setId(1L);
        MonthlyBalanceDTO monthlyBalanceDTO2 = new MonthlyBalanceDTO();
        assertThat(monthlyBalanceDTO1).isNotEqualTo(monthlyBalanceDTO2);
        monthlyBalanceDTO2.setId(monthlyBalanceDTO1.getId());
        assertThat(monthlyBalanceDTO1).isEqualTo(monthlyBalanceDTO2);
        monthlyBalanceDTO2.setId(2L);
        assertThat(monthlyBalanceDTO1).isNotEqualTo(monthlyBalanceDTO2);
        monthlyBalanceDTO1.setId(null);
        assertThat(monthlyBalanceDTO1).isNotEqualTo(monthlyBalanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monthlyBalanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monthlyBalanceMapper.fromId(null)).isNull();
    }
}
