package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.search.AccountBalanceSearchRepository;
import org.soptorshi.service.AccountBalanceService;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.mapper.AccountBalanceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.AccountBalanceCriteria;
import org.soptorshi.service.AccountBalanceQueryService;

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

import org.soptorshi.domain.enumeration.BalanceType;
/**
 * Test class for the AccountBalanceResource REST controller.
 *
 * @see AccountBalanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AccountBalanceResourceIntTest {

    private static final BigDecimal DEFAULT_YEAR_OPEN_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_YEAR_OPEN_BALANCE = new BigDecimal(2);

    private static final BalanceType DEFAULT_YEAR_OPEN_BALANCE_TYPE = BalanceType.DEBIT;
    private static final BalanceType UPDATED_YEAR_OPEN_BALANCE_TYPE = BalanceType.CREDIT;

    private static final BigDecimal DEFAULT_TOT_DEBIT_TRANS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOT_DEBIT_TRANS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOT_CREDIT_TRANS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOT_CREDIT_TRANS = new BigDecimal(2);

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Autowired
    private AccountBalanceMapper accountBalanceMapper;

    @Autowired
    private AccountBalanceService accountBalanceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AccountBalanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountBalanceSearchRepository mockAccountBalanceSearchRepository;

    @Autowired
    private AccountBalanceQueryService accountBalanceQueryService;

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

    private MockMvc restAccountBalanceMockMvc;

    private AccountBalance accountBalance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountBalanceResource accountBalanceResource = new AccountBalanceResource(accountBalanceService, accountBalanceQueryService);
        this.restAccountBalanceMockMvc = MockMvcBuilders.standaloneSetup(accountBalanceResource)
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
    public static AccountBalance createEntity(EntityManager em) {
        AccountBalance accountBalance = new AccountBalance()
            .yearOpenBalance(DEFAULT_YEAR_OPEN_BALANCE)
            .yearOpenBalanceType(DEFAULT_YEAR_OPEN_BALANCE_TYPE)
            .totDebitTrans(DEFAULT_TOT_DEBIT_TRANS)
            .totCreditTrans(DEFAULT_TOT_CREDIT_TRANS)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return accountBalance;
    }

    @Before
    public void initTest() {
        accountBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountBalance() throws Exception {
        int databaseSizeBeforeCreate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);
        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        AccountBalance testAccountBalance = accountBalanceList.get(accountBalanceList.size() - 1);
        assertThat(testAccountBalance.getYearOpenBalance()).isEqualTo(DEFAULT_YEAR_OPEN_BALANCE);
        assertThat(testAccountBalance.getYearOpenBalanceType()).isEqualTo(DEFAULT_YEAR_OPEN_BALANCE_TYPE);
        assertThat(testAccountBalance.getTotDebitTrans()).isEqualTo(DEFAULT_TOT_DEBIT_TRANS);
        assertThat(testAccountBalance.getTotCreditTrans()).isEqualTo(DEFAULT_TOT_CREDIT_TRANS);
        assertThat(testAccountBalance.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testAccountBalance.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);

        // Validate the AccountBalance in Elasticsearch
        verify(mockAccountBalanceSearchRepository, times(1)).save(testAccountBalance);
    }

    @Test
    @Transactional
    public void createAccountBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance with an existing ID
        accountBalance.setId(1L);
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccountBalance in Elasticsearch
        verify(mockAccountBalanceSearchRepository, times(0)).save(accountBalance);
    }

    @Test
    @Transactional
    public void getAllAccountBalances() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList
        restAccountBalanceMockMvc.perform(get("/api/account-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totDebitTrans").value(hasItem(DEFAULT_TOT_DEBIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].totCreditTrans").value(hasItem(DEFAULT_TOT_CREDIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get the accountBalance
        restAccountBalanceMockMvc.perform(get("/api/account-balances/{id}", accountBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountBalance.getId().intValue()))
            .andExpect(jsonPath("$.yearOpenBalance").value(DEFAULT_YEAR_OPEN_BALANCE.intValue()))
            .andExpect(jsonPath("$.yearOpenBalanceType").value(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString()))
            .andExpect(jsonPath("$.totDebitTrans").value(DEFAULT_TOT_DEBIT_TRANS.intValue()))
            .andExpect(jsonPath("$.totCreditTrans").value(DEFAULT_TOT_CREDIT_TRANS.intValue()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalance equals to DEFAULT_YEAR_OPEN_BALANCE
        defaultAccountBalanceShouldBeFound("yearOpenBalance.equals=" + DEFAULT_YEAR_OPEN_BALANCE);

        // Get all the accountBalanceList where yearOpenBalance equals to UPDATED_YEAR_OPEN_BALANCE
        defaultAccountBalanceShouldNotBeFound("yearOpenBalance.equals=" + UPDATED_YEAR_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalance in DEFAULT_YEAR_OPEN_BALANCE or UPDATED_YEAR_OPEN_BALANCE
        defaultAccountBalanceShouldBeFound("yearOpenBalance.in=" + DEFAULT_YEAR_OPEN_BALANCE + "," + UPDATED_YEAR_OPEN_BALANCE);

        // Get all the accountBalanceList where yearOpenBalance equals to UPDATED_YEAR_OPEN_BALANCE
        defaultAccountBalanceShouldNotBeFound("yearOpenBalance.in=" + UPDATED_YEAR_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalance is not null
        defaultAccountBalanceShouldBeFound("yearOpenBalance.specified=true");

        // Get all the accountBalanceList where yearOpenBalance is null
        defaultAccountBalanceShouldNotBeFound("yearOpenBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalanceType equals to DEFAULT_YEAR_OPEN_BALANCE_TYPE
        defaultAccountBalanceShouldBeFound("yearOpenBalanceType.equals=" + DEFAULT_YEAR_OPEN_BALANCE_TYPE);

        // Get all the accountBalanceList where yearOpenBalanceType equals to UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultAccountBalanceShouldNotBeFound("yearOpenBalanceType.equals=" + UPDATED_YEAR_OPEN_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalanceType in DEFAULT_YEAR_OPEN_BALANCE_TYPE or UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultAccountBalanceShouldBeFound("yearOpenBalanceType.in=" + DEFAULT_YEAR_OPEN_BALANCE_TYPE + "," + UPDATED_YEAR_OPEN_BALANCE_TYPE);

        // Get all the accountBalanceList where yearOpenBalanceType equals to UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultAccountBalanceShouldNotBeFound("yearOpenBalanceType.in=" + UPDATED_YEAR_OPEN_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByYearOpenBalanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where yearOpenBalanceType is not null
        defaultAccountBalanceShouldBeFound("yearOpenBalanceType.specified=true");

        // Get all the accountBalanceList where yearOpenBalanceType is null
        defaultAccountBalanceShouldNotBeFound("yearOpenBalanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotDebitTransIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totDebitTrans equals to DEFAULT_TOT_DEBIT_TRANS
        defaultAccountBalanceShouldBeFound("totDebitTrans.equals=" + DEFAULT_TOT_DEBIT_TRANS);

        // Get all the accountBalanceList where totDebitTrans equals to UPDATED_TOT_DEBIT_TRANS
        defaultAccountBalanceShouldNotBeFound("totDebitTrans.equals=" + UPDATED_TOT_DEBIT_TRANS);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotDebitTransIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totDebitTrans in DEFAULT_TOT_DEBIT_TRANS or UPDATED_TOT_DEBIT_TRANS
        defaultAccountBalanceShouldBeFound("totDebitTrans.in=" + DEFAULT_TOT_DEBIT_TRANS + "," + UPDATED_TOT_DEBIT_TRANS);

        // Get all the accountBalanceList where totDebitTrans equals to UPDATED_TOT_DEBIT_TRANS
        defaultAccountBalanceShouldNotBeFound("totDebitTrans.in=" + UPDATED_TOT_DEBIT_TRANS);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotDebitTransIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totDebitTrans is not null
        defaultAccountBalanceShouldBeFound("totDebitTrans.specified=true");

        // Get all the accountBalanceList where totDebitTrans is null
        defaultAccountBalanceShouldNotBeFound("totDebitTrans.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotCreditTransIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totCreditTrans equals to DEFAULT_TOT_CREDIT_TRANS
        defaultAccountBalanceShouldBeFound("totCreditTrans.equals=" + DEFAULT_TOT_CREDIT_TRANS);

        // Get all the accountBalanceList where totCreditTrans equals to UPDATED_TOT_CREDIT_TRANS
        defaultAccountBalanceShouldNotBeFound("totCreditTrans.equals=" + UPDATED_TOT_CREDIT_TRANS);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotCreditTransIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totCreditTrans in DEFAULT_TOT_CREDIT_TRANS or UPDATED_TOT_CREDIT_TRANS
        defaultAccountBalanceShouldBeFound("totCreditTrans.in=" + DEFAULT_TOT_CREDIT_TRANS + "," + UPDATED_TOT_CREDIT_TRANS);

        // Get all the accountBalanceList where totCreditTrans equals to UPDATED_TOT_CREDIT_TRANS
        defaultAccountBalanceShouldNotBeFound("totCreditTrans.in=" + UPDATED_TOT_CREDIT_TRANS);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByTotCreditTransIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where totCreditTrans is not null
        defaultAccountBalanceShouldBeFound("totCreditTrans.specified=true");

        // Get all the accountBalanceList where totCreditTrans is null
        defaultAccountBalanceShouldNotBeFound("totCreditTrans.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultAccountBalanceShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the accountBalanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultAccountBalanceShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultAccountBalanceShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the accountBalanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultAccountBalanceShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedOn is not null
        defaultAccountBalanceShouldBeFound("modifiedOn.specified=true");

        // Get all the accountBalanceList where modifiedOn is null
        defaultAccountBalanceShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultAccountBalanceShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the accountBalanceList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultAccountBalanceShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultAccountBalanceShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the accountBalanceList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultAccountBalanceShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAccountBalanceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the accountBalanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAccountBalanceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAccountBalanceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the accountBalanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAccountBalanceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList where modifiedBy is not null
        defaultAccountBalanceShouldBeFound("modifiedBy.specified=true");

        // Get all the accountBalanceList where modifiedBy is null
        defaultAccountBalanceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountBalancesByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        accountBalance.setFinancialAccountYear(financialAccountYear);
        accountBalanceRepository.saveAndFlush(accountBalance);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the accountBalanceList where financialAccountYear equals to financialAccountYearId
        defaultAccountBalanceShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the accountBalanceList where financialAccountYear equals to financialAccountYearId + 1
        defaultAccountBalanceShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }


    @Test
    @Transactional
    public void getAllAccountBalancesByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MstAccount account = MstAccountResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        accountBalance.setAccount(account);
        accountBalanceRepository.saveAndFlush(accountBalance);
        Long accountId = account.getId();

        // Get all the accountBalanceList where account equals to accountId
        defaultAccountBalanceShouldBeFound("accountId.equals=" + accountId);

        // Get all the accountBalanceList where account equals to accountId + 1
        defaultAccountBalanceShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAccountBalanceShouldBeFound(String filter) throws Exception {
        restAccountBalanceMockMvc.perform(get("/api/account-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totDebitTrans").value(hasItem(DEFAULT_TOT_DEBIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].totCreditTrans").value(hasItem(DEFAULT_TOT_CREDIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAccountBalanceMockMvc.perform(get("/api/account-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAccountBalanceShouldNotBeFound(String filter) throws Exception {
        restAccountBalanceMockMvc.perform(get("/api/account-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountBalanceMockMvc.perform(get("/api/account-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAccountBalance() throws Exception {
        // Get the accountBalance
        restAccountBalanceMockMvc.perform(get("/api/account-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        int databaseSizeBeforeUpdate = accountBalanceRepository.findAll().size();

        // Update the accountBalance
        AccountBalance updatedAccountBalance = accountBalanceRepository.findById(accountBalance.getId()).get();
        // Disconnect from session so that the updates on updatedAccountBalance are not directly saved in db
        em.detach(updatedAccountBalance);
        updatedAccountBalance
            .yearOpenBalance(UPDATED_YEAR_OPEN_BALANCE)
            .yearOpenBalanceType(UPDATED_YEAR_OPEN_BALANCE_TYPE)
            .totDebitTrans(UPDATED_TOT_DEBIT_TRANS)
            .totCreditTrans(UPDATED_TOT_CREDIT_TRANS)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY);
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(updatedAccountBalance);

        restAccountBalanceMockMvc.perform(put("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isOk());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeUpdate);
        AccountBalance testAccountBalance = accountBalanceList.get(accountBalanceList.size() - 1);
        assertThat(testAccountBalance.getYearOpenBalance()).isEqualTo(UPDATED_YEAR_OPEN_BALANCE);
        assertThat(testAccountBalance.getYearOpenBalanceType()).isEqualTo(UPDATED_YEAR_OPEN_BALANCE_TYPE);
        assertThat(testAccountBalance.getTotDebitTrans()).isEqualTo(UPDATED_TOT_DEBIT_TRANS);
        assertThat(testAccountBalance.getTotCreditTrans()).isEqualTo(UPDATED_TOT_CREDIT_TRANS);
        assertThat(testAccountBalance.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAccountBalance.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);

        // Validate the AccountBalance in Elasticsearch
        verify(mockAccountBalanceSearchRepository, times(1)).save(testAccountBalance);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountBalance() throws Exception {
        int databaseSizeBeforeUpdate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountBalanceMockMvc.perform(put("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccountBalance in Elasticsearch
        verify(mockAccountBalanceSearchRepository, times(0)).save(accountBalance);
    }

    @Test
    @Transactional
    public void deleteAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        int databaseSizeBeforeDelete = accountBalanceRepository.findAll().size();

        // Delete the accountBalance
        restAccountBalanceMockMvc.perform(delete("/api/account-balances/{id}", accountBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccountBalance in Elasticsearch
        verify(mockAccountBalanceSearchRepository, times(1)).deleteById(accountBalance.getId());
    }

    @Test
    @Transactional
    public void searchAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);
        when(mockAccountBalanceSearchRepository.search(queryStringQuery("id:" + accountBalance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accountBalance), PageRequest.of(0, 1), 1));
        // Search the accountBalance
        restAccountBalanceMockMvc.perform(get("/api/_search/account-balances?query=id:" + accountBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totDebitTrans").value(hasItem(DEFAULT_TOT_DEBIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].totCreditTrans").value(hasItem(DEFAULT_TOT_CREDIT_TRANS.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBalance.class);
        AccountBalance accountBalance1 = new AccountBalance();
        accountBalance1.setId(1L);
        AccountBalance accountBalance2 = new AccountBalance();
        accountBalance2.setId(accountBalance1.getId());
        assertThat(accountBalance1).isEqualTo(accountBalance2);
        accountBalance2.setId(2L);
        assertThat(accountBalance1).isNotEqualTo(accountBalance2);
        accountBalance1.setId(null);
        assertThat(accountBalance1).isNotEqualTo(accountBalance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBalanceDTO.class);
        AccountBalanceDTO accountBalanceDTO1 = new AccountBalanceDTO();
        accountBalanceDTO1.setId(1L);
        AccountBalanceDTO accountBalanceDTO2 = new AccountBalanceDTO();
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
        accountBalanceDTO2.setId(accountBalanceDTO1.getId());
        assertThat(accountBalanceDTO1).isEqualTo(accountBalanceDTO2);
        accountBalanceDTO2.setId(2L);
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
        accountBalanceDTO1.setId(null);
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountBalanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountBalanceMapper.fromId(null)).isNull();
    }
}
