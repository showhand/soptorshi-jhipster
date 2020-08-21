package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MstAccountMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.MstAccountCriteria;
import org.soptorshi.service.MstAccountQueryService;

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
import org.soptorshi.domain.enumeration.ReservedFlag;
import org.soptorshi.domain.enumeration.DepreciationType;
/**
 * Test class for the MstAccountResource REST controller.
 *
 * @see MstAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class MstAccountResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_YEAR_OPEN_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_YEAR_OPEN_BALANCE = new BigDecimal(2);

    private static final BalanceType DEFAULT_YEAR_OPEN_BALANCE_TYPE = BalanceType.DEBIT;
    private static final BalanceType UPDATED_YEAR_OPEN_BALANCE_TYPE = BalanceType.CREDIT;

    private static final BigDecimal DEFAULT_YEAR_CLOSE_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_YEAR_CLOSE_BALANCE = new BigDecimal(2);

    private static final ReservedFlag DEFAULT_RESERVED_FLAG = ReservedFlag.RESERVED;
    private static final ReservedFlag UPDATED_RESERVED_FLAG = ReservedFlag.NOT_RESERVED;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_DEPRECIATION_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEPRECIATION_RATE = new BigDecimal(2);

    private static final DepreciationType DEFAULT_DEPRECIATION_TYPE = DepreciationType.MONTHLY;
    private static final DepreciationType UPDATED_DEPRECIATION_TYPE = DepreciationType.YEARLY;

    @Autowired
    private MstAccountRepository mstAccountRepository;

    @Autowired
    private MstAccountMapper mstAccountMapper;

    @Autowired
    private MstAccountService mstAccountService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.MstAccountSearchRepositoryMockConfiguration
     */
    @Autowired
    private MstAccountSearchRepository mockMstAccountSearchRepository;

    @Autowired
    private MstAccountQueryService mstAccountQueryService;

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

    private MockMvc restMstAccountMockMvc;

    private MstAccount mstAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MstAccountResource mstAccountResource = new MstAccountResource(mstAccountService, mstAccountQueryService);
        this.restMstAccountMockMvc = MockMvcBuilders.standaloneSetup(mstAccountResource)
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
    public static MstAccount createEntity(EntityManager em) {
        MstAccount mstAccount = new MstAccount()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .yearOpenBalance(DEFAULT_YEAR_OPEN_BALANCE)
            .yearOpenBalanceType(DEFAULT_YEAR_OPEN_BALANCE_TYPE)
            .yearCloseBalance(DEFAULT_YEAR_CLOSE_BALANCE)
            .reservedFlag(DEFAULT_RESERVED_FLAG)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .depreciationRate(DEFAULT_DEPRECIATION_RATE)
            .depreciationType(DEFAULT_DEPRECIATION_TYPE);
        return mstAccount;
    }

    @Before
    public void initTest() {
        mstAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createMstAccount() throws Exception {
        int databaseSizeBeforeCreate = mstAccountRepository.findAll().size();

        // Create the MstAccount
        MstAccountDTO mstAccountDTO = mstAccountMapper.toDto(mstAccount);
        restMstAccountMockMvc.perform(post("/api/mst-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the MstAccount in the database
        List<MstAccount> mstAccountList = mstAccountRepository.findAll();
        assertThat(mstAccountList).hasSize(databaseSizeBeforeCreate + 1);
        MstAccount testMstAccount = mstAccountList.get(mstAccountList.size() - 1);
        assertThat(testMstAccount.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMstAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMstAccount.getYearOpenBalance()).isEqualTo(DEFAULT_YEAR_OPEN_BALANCE);
        assertThat(testMstAccount.getYearOpenBalanceType()).isEqualTo(DEFAULT_YEAR_OPEN_BALANCE_TYPE);
        assertThat(testMstAccount.getYearCloseBalance()).isEqualTo(DEFAULT_YEAR_CLOSE_BALANCE);
        assertThat(testMstAccount.getReservedFlag()).isEqualTo(DEFAULT_RESERVED_FLAG);
        assertThat(testMstAccount.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMstAccount.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testMstAccount.getDepreciationRate()).isEqualTo(DEFAULT_DEPRECIATION_RATE);
        assertThat(testMstAccount.getDepreciationType()).isEqualTo(DEFAULT_DEPRECIATION_TYPE);

        // Validate the MstAccount in Elasticsearch
        verify(mockMstAccountSearchRepository, times(1)).save(testMstAccount);
    }

    @Test
    @Transactional
    public void createMstAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mstAccountRepository.findAll().size();

        // Create the MstAccount with an existing ID
        mstAccount.setId(1L);
        MstAccountDTO mstAccountDTO = mstAccountMapper.toDto(mstAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMstAccountMockMvc.perform(post("/api/mst-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MstAccount in the database
        List<MstAccount> mstAccountList = mstAccountRepository.findAll();
        assertThat(mstAccountList).hasSize(databaseSizeBeforeCreate);

        // Validate the MstAccount in Elasticsearch
        verify(mockMstAccountSearchRepository, times(0)).save(mstAccount);
    }

    @Test
    @Transactional
    public void getAllMstAccounts() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList
        restMstAccountMockMvc.perform(get("/api/mst-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].yearCloseBalance").value(hasItem(DEFAULT_YEAR_CLOSE_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.intValue())))
            .andExpect(jsonPath("$.[*].depreciationType").value(hasItem(DEFAULT_DEPRECIATION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getMstAccount() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get the mstAccount
        restMstAccountMockMvc.perform(get("/api/mst-accounts/{id}", mstAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mstAccount.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.yearOpenBalance").value(DEFAULT_YEAR_OPEN_BALANCE.intValue()))
            .andExpect(jsonPath("$.yearOpenBalanceType").value(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString()))
            .andExpect(jsonPath("$.yearCloseBalance").value(DEFAULT_YEAR_CLOSE_BALANCE.intValue()))
            .andExpect(jsonPath("$.reservedFlag").value(DEFAULT_RESERVED_FLAG.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.depreciationRate").value(DEFAULT_DEPRECIATION_RATE.intValue()))
            .andExpect(jsonPath("$.depreciationType").value(DEFAULT_DEPRECIATION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllMstAccountsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where code equals to DEFAULT_CODE
        defaultMstAccountShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the mstAccountList where code equals to UPDATED_CODE
        defaultMstAccountShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where code in DEFAULT_CODE or UPDATED_CODE
        defaultMstAccountShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the mstAccountList where code equals to UPDATED_CODE
        defaultMstAccountShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where code is not null
        defaultMstAccountShouldBeFound("code.specified=true");

        // Get all the mstAccountList where code is null
        defaultMstAccountShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where name equals to DEFAULT_NAME
        defaultMstAccountShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mstAccountList where name equals to UPDATED_NAME
        defaultMstAccountShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMstAccountShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mstAccountList where name equals to UPDATED_NAME
        defaultMstAccountShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where name is not null
        defaultMstAccountShouldBeFound("name.specified=true");

        // Get all the mstAccountList where name is null
        defaultMstAccountShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalance equals to DEFAULT_YEAR_OPEN_BALANCE
        defaultMstAccountShouldBeFound("yearOpenBalance.equals=" + DEFAULT_YEAR_OPEN_BALANCE);

        // Get all the mstAccountList where yearOpenBalance equals to UPDATED_YEAR_OPEN_BALANCE
        defaultMstAccountShouldNotBeFound("yearOpenBalance.equals=" + UPDATED_YEAR_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalance in DEFAULT_YEAR_OPEN_BALANCE or UPDATED_YEAR_OPEN_BALANCE
        defaultMstAccountShouldBeFound("yearOpenBalance.in=" + DEFAULT_YEAR_OPEN_BALANCE + "," + UPDATED_YEAR_OPEN_BALANCE);

        // Get all the mstAccountList where yearOpenBalance equals to UPDATED_YEAR_OPEN_BALANCE
        defaultMstAccountShouldNotBeFound("yearOpenBalance.in=" + UPDATED_YEAR_OPEN_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalance is not null
        defaultMstAccountShouldBeFound("yearOpenBalance.specified=true");

        // Get all the mstAccountList where yearOpenBalance is null
        defaultMstAccountShouldNotBeFound("yearOpenBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalanceType equals to DEFAULT_YEAR_OPEN_BALANCE_TYPE
        defaultMstAccountShouldBeFound("yearOpenBalanceType.equals=" + DEFAULT_YEAR_OPEN_BALANCE_TYPE);

        // Get all the mstAccountList where yearOpenBalanceType equals to UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultMstAccountShouldNotBeFound("yearOpenBalanceType.equals=" + UPDATED_YEAR_OPEN_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalanceType in DEFAULT_YEAR_OPEN_BALANCE_TYPE or UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultMstAccountShouldBeFound("yearOpenBalanceType.in=" + DEFAULT_YEAR_OPEN_BALANCE_TYPE + "," + UPDATED_YEAR_OPEN_BALANCE_TYPE);

        // Get all the mstAccountList where yearOpenBalanceType equals to UPDATED_YEAR_OPEN_BALANCE_TYPE
        defaultMstAccountShouldNotBeFound("yearOpenBalanceType.in=" + UPDATED_YEAR_OPEN_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearOpenBalanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearOpenBalanceType is not null
        defaultMstAccountShouldBeFound("yearOpenBalanceType.specified=true");

        // Get all the mstAccountList where yearOpenBalanceType is null
        defaultMstAccountShouldNotBeFound("yearOpenBalanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearCloseBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearCloseBalance equals to DEFAULT_YEAR_CLOSE_BALANCE
        defaultMstAccountShouldBeFound("yearCloseBalance.equals=" + DEFAULT_YEAR_CLOSE_BALANCE);

        // Get all the mstAccountList where yearCloseBalance equals to UPDATED_YEAR_CLOSE_BALANCE
        defaultMstAccountShouldNotBeFound("yearCloseBalance.equals=" + UPDATED_YEAR_CLOSE_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearCloseBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearCloseBalance in DEFAULT_YEAR_CLOSE_BALANCE or UPDATED_YEAR_CLOSE_BALANCE
        defaultMstAccountShouldBeFound("yearCloseBalance.in=" + DEFAULT_YEAR_CLOSE_BALANCE + "," + UPDATED_YEAR_CLOSE_BALANCE);

        // Get all the mstAccountList where yearCloseBalance equals to UPDATED_YEAR_CLOSE_BALANCE
        defaultMstAccountShouldNotBeFound("yearCloseBalance.in=" + UPDATED_YEAR_CLOSE_BALANCE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByYearCloseBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where yearCloseBalance is not null
        defaultMstAccountShouldBeFound("yearCloseBalance.specified=true");

        // Get all the mstAccountList where yearCloseBalance is null
        defaultMstAccountShouldNotBeFound("yearCloseBalance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByReservedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where reservedFlag equals to DEFAULT_RESERVED_FLAG
        defaultMstAccountShouldBeFound("reservedFlag.equals=" + DEFAULT_RESERVED_FLAG);

        // Get all the mstAccountList where reservedFlag equals to UPDATED_RESERVED_FLAG
        defaultMstAccountShouldNotBeFound("reservedFlag.equals=" + UPDATED_RESERVED_FLAG);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByReservedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where reservedFlag in DEFAULT_RESERVED_FLAG or UPDATED_RESERVED_FLAG
        defaultMstAccountShouldBeFound("reservedFlag.in=" + DEFAULT_RESERVED_FLAG + "," + UPDATED_RESERVED_FLAG);

        // Get all the mstAccountList where reservedFlag equals to UPDATED_RESERVED_FLAG
        defaultMstAccountShouldNotBeFound("reservedFlag.in=" + UPDATED_RESERVED_FLAG);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByReservedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where reservedFlag is not null
        defaultMstAccountShouldBeFound("reservedFlag.specified=true");

        // Get all the mstAccountList where reservedFlag is null
        defaultMstAccountShouldNotBeFound("reservedFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultMstAccountShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the mstAccountList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMstAccountShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultMstAccountShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the mstAccountList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMstAccountShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedBy is not null
        defaultMstAccountShouldBeFound("modifiedBy.specified=true");

        // Get all the mstAccountList where modifiedBy is null
        defaultMstAccountShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultMstAccountShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the mstAccountList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMstAccountShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultMstAccountShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the mstAccountList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMstAccountShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedOn is not null
        defaultMstAccountShouldBeFound("modifiedOn.specified=true");

        // Get all the mstAccountList where modifiedOn is null
        defaultMstAccountShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultMstAccountShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the mstAccountList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultMstAccountShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultMstAccountShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the mstAccountList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultMstAccountShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationRateIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationRate equals to DEFAULT_DEPRECIATION_RATE
        defaultMstAccountShouldBeFound("depreciationRate.equals=" + DEFAULT_DEPRECIATION_RATE);

        // Get all the mstAccountList where depreciationRate equals to UPDATED_DEPRECIATION_RATE
        defaultMstAccountShouldNotBeFound("depreciationRate.equals=" + UPDATED_DEPRECIATION_RATE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationRateIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationRate in DEFAULT_DEPRECIATION_RATE or UPDATED_DEPRECIATION_RATE
        defaultMstAccountShouldBeFound("depreciationRate.in=" + DEFAULT_DEPRECIATION_RATE + "," + UPDATED_DEPRECIATION_RATE);

        // Get all the mstAccountList where depreciationRate equals to UPDATED_DEPRECIATION_RATE
        defaultMstAccountShouldNotBeFound("depreciationRate.in=" + UPDATED_DEPRECIATION_RATE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationRate is not null
        defaultMstAccountShouldBeFound("depreciationRate.specified=true");

        // Get all the mstAccountList where depreciationRate is null
        defaultMstAccountShouldNotBeFound("depreciationRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationType equals to DEFAULT_DEPRECIATION_TYPE
        defaultMstAccountShouldBeFound("depreciationType.equals=" + DEFAULT_DEPRECIATION_TYPE);

        // Get all the mstAccountList where depreciationType equals to UPDATED_DEPRECIATION_TYPE
        defaultMstAccountShouldNotBeFound("depreciationType.equals=" + UPDATED_DEPRECIATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationType in DEFAULT_DEPRECIATION_TYPE or UPDATED_DEPRECIATION_TYPE
        defaultMstAccountShouldBeFound("depreciationType.in=" + DEFAULT_DEPRECIATION_TYPE + "," + UPDATED_DEPRECIATION_TYPE);

        // Get all the mstAccountList where depreciationType equals to UPDATED_DEPRECIATION_TYPE
        defaultMstAccountShouldNotBeFound("depreciationType.in=" + UPDATED_DEPRECIATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMstAccountsByDepreciationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        // Get all the mstAccountList where depreciationType is not null
        defaultMstAccountShouldBeFound("depreciationType.specified=true");

        // Get all the mstAccountList where depreciationType is null
        defaultMstAccountShouldNotBeFound("depreciationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstAccountsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        MstGroup group = MstGroupResourceIntTest.createEntity(em);
        em.persist(group);
        em.flush();
        mstAccount.setGroup(group);
        mstAccountRepository.saveAndFlush(mstAccount);
        Long groupId = group.getId();

        // Get all the mstAccountList where group equals to groupId
        defaultMstAccountShouldBeFound("groupId.equals=" + groupId);

        // Get all the mstAccountList where group equals to groupId + 1
        defaultMstAccountShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMstAccountShouldBeFound(String filter) throws Exception {
        restMstAccountMockMvc.perform(get("/api/mst-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].yearCloseBalance").value(hasItem(DEFAULT_YEAR_CLOSE_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.intValue())))
            .andExpect(jsonPath("$.[*].depreciationType").value(hasItem(DEFAULT_DEPRECIATION_TYPE.toString())));

        // Check, that the count call also returns 1
        restMstAccountMockMvc.perform(get("/api/mst-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMstAccountShouldNotBeFound(String filter) throws Exception {
        restMstAccountMockMvc.perform(get("/api/mst-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMstAccountMockMvc.perform(get("/api/mst-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMstAccount() throws Exception {
        // Get the mstAccount
        restMstAccountMockMvc.perform(get("/api/mst-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMstAccount() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        int databaseSizeBeforeUpdate = mstAccountRepository.findAll().size();

        // Update the mstAccount
        MstAccount updatedMstAccount = mstAccountRepository.findById(mstAccount.getId()).get();
        // Disconnect from session so that the updates on updatedMstAccount are not directly saved in db
        em.detach(updatedMstAccount);
        updatedMstAccount
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .yearOpenBalance(UPDATED_YEAR_OPEN_BALANCE)
            .yearOpenBalanceType(UPDATED_YEAR_OPEN_BALANCE_TYPE)
            .yearCloseBalance(UPDATED_YEAR_CLOSE_BALANCE)
            .reservedFlag(UPDATED_RESERVED_FLAG)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .depreciationRate(UPDATED_DEPRECIATION_RATE)
            .depreciationType(UPDATED_DEPRECIATION_TYPE);
        MstAccountDTO mstAccountDTO = mstAccountMapper.toDto(updatedMstAccount);

        restMstAccountMockMvc.perform(put("/api/mst-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstAccountDTO)))
            .andExpect(status().isOk());

        // Validate the MstAccount in the database
        List<MstAccount> mstAccountList = mstAccountRepository.findAll();
        assertThat(mstAccountList).hasSize(databaseSizeBeforeUpdate);
        MstAccount testMstAccount = mstAccountList.get(mstAccountList.size() - 1);
        assertThat(testMstAccount.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMstAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMstAccount.getYearOpenBalance()).isEqualTo(UPDATED_YEAR_OPEN_BALANCE);
        assertThat(testMstAccount.getYearOpenBalanceType()).isEqualTo(UPDATED_YEAR_OPEN_BALANCE_TYPE);
        assertThat(testMstAccount.getYearCloseBalance()).isEqualTo(UPDATED_YEAR_CLOSE_BALANCE);
        assertThat(testMstAccount.getReservedFlag()).isEqualTo(UPDATED_RESERVED_FLAG);
        assertThat(testMstAccount.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMstAccount.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testMstAccount.getDepreciationRate()).isEqualTo(UPDATED_DEPRECIATION_RATE);
        assertThat(testMstAccount.getDepreciationType()).isEqualTo(UPDATED_DEPRECIATION_TYPE);

        // Validate the MstAccount in Elasticsearch
        verify(mockMstAccountSearchRepository, times(1)).save(testMstAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingMstAccount() throws Exception {
        int databaseSizeBeforeUpdate = mstAccountRepository.findAll().size();

        // Create the MstAccount
        MstAccountDTO mstAccountDTO = mstAccountMapper.toDto(mstAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMstAccountMockMvc.perform(put("/api/mst-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MstAccount in the database
        List<MstAccount> mstAccountList = mstAccountRepository.findAll();
        assertThat(mstAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MstAccount in Elasticsearch
        verify(mockMstAccountSearchRepository, times(0)).save(mstAccount);
    }

    @Test
    @Transactional
    public void deleteMstAccount() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);

        int databaseSizeBeforeDelete = mstAccountRepository.findAll().size();

        // Delete the mstAccount
        restMstAccountMockMvc.perform(delete("/api/mst-accounts/{id}", mstAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MstAccount> mstAccountList = mstAccountRepository.findAll();
        assertThat(mstAccountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MstAccount in Elasticsearch
        verify(mockMstAccountSearchRepository, times(1)).deleteById(mstAccount.getId());
    }

    @Test
    @Transactional
    public void searchMstAccount() throws Exception {
        // Initialize the database
        mstAccountRepository.saveAndFlush(mstAccount);
        when(mockMstAccountSearchRepository.search(queryStringQuery("id:" + mstAccount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(mstAccount), PageRequest.of(0, 1), 1));
        // Search the mstAccount
        restMstAccountMockMvc.perform(get("/api/_search/mst-accounts?query=id:" + mstAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].yearOpenBalance").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearOpenBalanceType").value(hasItem(DEFAULT_YEAR_OPEN_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].yearCloseBalance").value(hasItem(DEFAULT_YEAR_CLOSE_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.intValue())))
            .andExpect(jsonPath("$.[*].depreciationType").value(hasItem(DEFAULT_DEPRECIATION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MstAccount.class);
        MstAccount mstAccount1 = new MstAccount();
        mstAccount1.setId(1L);
        MstAccount mstAccount2 = new MstAccount();
        mstAccount2.setId(mstAccount1.getId());
        assertThat(mstAccount1).isEqualTo(mstAccount2);
        mstAccount2.setId(2L);
        assertThat(mstAccount1).isNotEqualTo(mstAccount2);
        mstAccount1.setId(null);
        assertThat(mstAccount1).isNotEqualTo(mstAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MstAccountDTO.class);
        MstAccountDTO mstAccountDTO1 = new MstAccountDTO();
        mstAccountDTO1.setId(1L);
        MstAccountDTO mstAccountDTO2 = new MstAccountDTO();
        assertThat(mstAccountDTO1).isNotEqualTo(mstAccountDTO2);
        mstAccountDTO2.setId(mstAccountDTO1.getId());
        assertThat(mstAccountDTO1).isEqualTo(mstAccountDTO2);
        mstAccountDTO2.setId(2L);
        assertThat(mstAccountDTO1).isNotEqualTo(mstAccountDTO2);
        mstAccountDTO1.setId(null);
        assertThat(mstAccountDTO1).isNotEqualTo(mstAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mstAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mstAccountMapper.fromId(null)).isNull();
    }
}
