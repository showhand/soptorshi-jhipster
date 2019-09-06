package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CreditorLedger;
import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.CreditorLedgerRepository;
import org.soptorshi.repository.search.CreditorLedgerSearchRepository;
import org.soptorshi.service.CreditorLedgerService;
import org.soptorshi.service.dto.CreditorLedgerDTO;
import org.soptorshi.service.mapper.CreditorLedgerMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CreditorLedgerCriteria;
import org.soptorshi.service.CreditorLedgerQueryService;

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
import org.soptorshi.domain.enumeration.BillClosingFlag;
/**
 * Test class for the CreditorLedgerResource REST controller.
 *
 * @see CreditorLedgerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CreditorLedgerResourceIntTest {

    private static final Integer DEFAULT_SERIAL_NO = 1;
    private static final Integer UPDATED_SERIAL_NO = 2;

    private static final String DEFAULT_BILL_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAID_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAID_AMOUNT = new BigDecimal(2);

    private static final BalanceType DEFAULT_BALANCE_TYPE = BalanceType.DEBIT;
    private static final BalanceType UPDATED_BALANCE_TYPE = BalanceType.CREDIT;

    private static final BillClosingFlag DEFAULT_BILL_CLOSING_FLAG = BillClosingFlag.OPEN;
    private static final BillClosingFlag UPDATED_BILL_CLOSING_FLAG = BillClosingFlag.CLOSED;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VAT_NO = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CreditorLedgerRepository creditorLedgerRepository;

    @Autowired
    private CreditorLedgerMapper creditorLedgerMapper;

    @Autowired
    private CreditorLedgerService creditorLedgerService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CreditorLedgerSearchRepositoryMockConfiguration
     */
    @Autowired
    private CreditorLedgerSearchRepository mockCreditorLedgerSearchRepository;

    @Autowired
    private CreditorLedgerQueryService creditorLedgerQueryService;

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

    private MockMvc restCreditorLedgerMockMvc;

    private CreditorLedger creditorLedger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditorLedgerResource creditorLedgerResource = new CreditorLedgerResource(creditorLedgerService, creditorLedgerQueryService);
        this.restCreditorLedgerMockMvc = MockMvcBuilders.standaloneSetup(creditorLedgerResource)
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
    public static CreditorLedger createEntity(EntityManager em) {
        CreditorLedger creditorLedger = new CreditorLedger()
            .serialNo(DEFAULT_SERIAL_NO)
            .billNo(DEFAULT_BILL_NO)
            .billDate(DEFAULT_BILL_DATE)
            .amount(DEFAULT_AMOUNT)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .balanceType(DEFAULT_BALANCE_TYPE)
            .billClosingFlag(DEFAULT_BILL_CLOSING_FLAG)
            .dueDate(DEFAULT_DUE_DATE)
            .vatNo(DEFAULT_VAT_NO)
            .contCode(DEFAULT_CONT_CODE)
            .orderNo(DEFAULT_ORDER_NO)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return creditorLedger;
    }

    @Before
    public void initTest() {
        creditorLedger = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreditorLedger() throws Exception {
        int databaseSizeBeforeCreate = creditorLedgerRepository.findAll().size();

        // Create the CreditorLedger
        CreditorLedgerDTO creditorLedgerDTO = creditorLedgerMapper.toDto(creditorLedger);
        restCreditorLedgerMockMvc.perform(post("/api/creditor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditorLedgerDTO)))
            .andExpect(status().isCreated());

        // Validate the CreditorLedger in the database
        List<CreditorLedger> creditorLedgerList = creditorLedgerRepository.findAll();
        assertThat(creditorLedgerList).hasSize(databaseSizeBeforeCreate + 1);
        CreditorLedger testCreditorLedger = creditorLedgerList.get(creditorLedgerList.size() - 1);
        assertThat(testCreditorLedger.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testCreditorLedger.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testCreditorLedger.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testCreditorLedger.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCreditorLedger.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testCreditorLedger.getBalanceType()).isEqualTo(DEFAULT_BALANCE_TYPE);
        assertThat(testCreditorLedger.getBillClosingFlag()).isEqualTo(DEFAULT_BILL_CLOSING_FLAG);
        assertThat(testCreditorLedger.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCreditorLedger.getVatNo()).isEqualTo(DEFAULT_VAT_NO);
        assertThat(testCreditorLedger.getContCode()).isEqualTo(DEFAULT_CONT_CODE);
        assertThat(testCreditorLedger.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testCreditorLedger.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCreditorLedger.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the CreditorLedger in Elasticsearch
        verify(mockCreditorLedgerSearchRepository, times(1)).save(testCreditorLedger);
    }

    @Test
    @Transactional
    public void createCreditorLedgerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditorLedgerRepository.findAll().size();

        // Create the CreditorLedger with an existing ID
        creditorLedger.setId(1L);
        CreditorLedgerDTO creditorLedgerDTO = creditorLedgerMapper.toDto(creditorLedger);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditorLedgerMockMvc.perform(post("/api/creditor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditorLedgerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditorLedger in the database
        List<CreditorLedger> creditorLedgerList = creditorLedgerRepository.findAll();
        assertThat(creditorLedgerList).hasSize(databaseSizeBeforeCreate);

        // Validate the CreditorLedger in Elasticsearch
        verify(mockCreditorLedgerSearchRepository, times(0)).save(creditorLedger);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgers() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].billClosingFlag").value(hasItem(DEFAULT_BILL_CLOSING_FLAG.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO.toString())))
            .andExpect(jsonPath("$.[*].contCode").value(hasItem(DEFAULT_CONT_CODE.toString())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCreditorLedger() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get the creditorLedger
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers/{id}", creditorLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creditorLedger.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.intValue()))
            .andExpect(jsonPath("$.balanceType").value(DEFAULT_BALANCE_TYPE.toString()))
            .andExpect(jsonPath("$.billClosingFlag").value(DEFAULT_BILL_CLOSING_FLAG.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.vatNo").value(DEFAULT_VAT_NO.toString()))
            .andExpect(jsonPath("$.contCode").value(DEFAULT_CONT_CODE.toString()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where serialNo equals to DEFAULT_SERIAL_NO
        defaultCreditorLedgerShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the creditorLedgerList where serialNo equals to UPDATED_SERIAL_NO
        defaultCreditorLedgerShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultCreditorLedgerShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the creditorLedgerList where serialNo equals to UPDATED_SERIAL_NO
        defaultCreditorLedgerShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where serialNo is not null
        defaultCreditorLedgerShouldBeFound("serialNo.specified=true");

        // Get all the creditorLedgerList where serialNo is null
        defaultCreditorLedgerShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where serialNo greater than or equals to DEFAULT_SERIAL_NO
        defaultCreditorLedgerShouldBeFound("serialNo.greaterOrEqualThan=" + DEFAULT_SERIAL_NO);

        // Get all the creditorLedgerList where serialNo greater than or equals to UPDATED_SERIAL_NO
        defaultCreditorLedgerShouldNotBeFound("serialNo.greaterOrEqualThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where serialNo less than or equals to DEFAULT_SERIAL_NO
        defaultCreditorLedgerShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the creditorLedgerList where serialNo less than or equals to UPDATED_SERIAL_NO
        defaultCreditorLedgerShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllCreditorLedgersByBillNoIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billNo equals to DEFAULT_BILL_NO
        defaultCreditorLedgerShouldBeFound("billNo.equals=" + DEFAULT_BILL_NO);

        // Get all the creditorLedgerList where billNo equals to UPDATED_BILL_NO
        defaultCreditorLedgerShouldNotBeFound("billNo.equals=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillNoIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billNo in DEFAULT_BILL_NO or UPDATED_BILL_NO
        defaultCreditorLedgerShouldBeFound("billNo.in=" + DEFAULT_BILL_NO + "," + UPDATED_BILL_NO);

        // Get all the creditorLedgerList where billNo equals to UPDATED_BILL_NO
        defaultCreditorLedgerShouldNotBeFound("billNo.in=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billNo is not null
        defaultCreditorLedgerShouldBeFound("billNo.specified=true");

        // Get all the creditorLedgerList where billNo is null
        defaultCreditorLedgerShouldNotBeFound("billNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billDate equals to DEFAULT_BILL_DATE
        defaultCreditorLedgerShouldBeFound("billDate.equals=" + DEFAULT_BILL_DATE);

        // Get all the creditorLedgerList where billDate equals to UPDATED_BILL_DATE
        defaultCreditorLedgerShouldNotBeFound("billDate.equals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billDate in DEFAULT_BILL_DATE or UPDATED_BILL_DATE
        defaultCreditorLedgerShouldBeFound("billDate.in=" + DEFAULT_BILL_DATE + "," + UPDATED_BILL_DATE);

        // Get all the creditorLedgerList where billDate equals to UPDATED_BILL_DATE
        defaultCreditorLedgerShouldNotBeFound("billDate.in=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billDate is not null
        defaultCreditorLedgerShouldBeFound("billDate.specified=true");

        // Get all the creditorLedgerList where billDate is null
        defaultCreditorLedgerShouldNotBeFound("billDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billDate greater than or equals to DEFAULT_BILL_DATE
        defaultCreditorLedgerShouldBeFound("billDate.greaterOrEqualThan=" + DEFAULT_BILL_DATE);

        // Get all the creditorLedgerList where billDate greater than or equals to UPDATED_BILL_DATE
        defaultCreditorLedgerShouldNotBeFound("billDate.greaterOrEqualThan=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillDateIsLessThanSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billDate less than or equals to DEFAULT_BILL_DATE
        defaultCreditorLedgerShouldNotBeFound("billDate.lessThan=" + DEFAULT_BILL_DATE);

        // Get all the creditorLedgerList where billDate less than or equals to UPDATED_BILL_DATE
        defaultCreditorLedgerShouldBeFound("billDate.lessThan=" + UPDATED_BILL_DATE);
    }


    @Test
    @Transactional
    public void getAllCreditorLedgersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where amount equals to DEFAULT_AMOUNT
        defaultCreditorLedgerShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the creditorLedgerList where amount equals to UPDATED_AMOUNT
        defaultCreditorLedgerShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCreditorLedgerShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the creditorLedgerList where amount equals to UPDATED_AMOUNT
        defaultCreditorLedgerShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where amount is not null
        defaultCreditorLedgerShouldBeFound("amount.specified=true");

        // Get all the creditorLedgerList where amount is null
        defaultCreditorLedgerShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByPaidAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where paidAmount equals to DEFAULT_PAID_AMOUNT
        defaultCreditorLedgerShouldBeFound("paidAmount.equals=" + DEFAULT_PAID_AMOUNT);

        // Get all the creditorLedgerList where paidAmount equals to UPDATED_PAID_AMOUNT
        defaultCreditorLedgerShouldNotBeFound("paidAmount.equals=" + UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByPaidAmountIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where paidAmount in DEFAULT_PAID_AMOUNT or UPDATED_PAID_AMOUNT
        defaultCreditorLedgerShouldBeFound("paidAmount.in=" + DEFAULT_PAID_AMOUNT + "," + UPDATED_PAID_AMOUNT);

        // Get all the creditorLedgerList where paidAmount equals to UPDATED_PAID_AMOUNT
        defaultCreditorLedgerShouldNotBeFound("paidAmount.in=" + UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByPaidAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where paidAmount is not null
        defaultCreditorLedgerShouldBeFound("paidAmount.specified=true");

        // Get all the creditorLedgerList where paidAmount is null
        defaultCreditorLedgerShouldNotBeFound("paidAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBalanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where balanceType equals to DEFAULT_BALANCE_TYPE
        defaultCreditorLedgerShouldBeFound("balanceType.equals=" + DEFAULT_BALANCE_TYPE);

        // Get all the creditorLedgerList where balanceType equals to UPDATED_BALANCE_TYPE
        defaultCreditorLedgerShouldNotBeFound("balanceType.equals=" + UPDATED_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBalanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where balanceType in DEFAULT_BALANCE_TYPE or UPDATED_BALANCE_TYPE
        defaultCreditorLedgerShouldBeFound("balanceType.in=" + DEFAULT_BALANCE_TYPE + "," + UPDATED_BALANCE_TYPE);

        // Get all the creditorLedgerList where balanceType equals to UPDATED_BALANCE_TYPE
        defaultCreditorLedgerShouldNotBeFound("balanceType.in=" + UPDATED_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBalanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where balanceType is not null
        defaultCreditorLedgerShouldBeFound("balanceType.specified=true");

        // Get all the creditorLedgerList where balanceType is null
        defaultCreditorLedgerShouldNotBeFound("balanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillClosingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billClosingFlag equals to DEFAULT_BILL_CLOSING_FLAG
        defaultCreditorLedgerShouldBeFound("billClosingFlag.equals=" + DEFAULT_BILL_CLOSING_FLAG);

        // Get all the creditorLedgerList where billClosingFlag equals to UPDATED_BILL_CLOSING_FLAG
        defaultCreditorLedgerShouldNotBeFound("billClosingFlag.equals=" + UPDATED_BILL_CLOSING_FLAG);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillClosingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billClosingFlag in DEFAULT_BILL_CLOSING_FLAG or UPDATED_BILL_CLOSING_FLAG
        defaultCreditorLedgerShouldBeFound("billClosingFlag.in=" + DEFAULT_BILL_CLOSING_FLAG + "," + UPDATED_BILL_CLOSING_FLAG);

        // Get all the creditorLedgerList where billClosingFlag equals to UPDATED_BILL_CLOSING_FLAG
        defaultCreditorLedgerShouldNotBeFound("billClosingFlag.in=" + UPDATED_BILL_CLOSING_FLAG);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByBillClosingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where billClosingFlag is not null
        defaultCreditorLedgerShouldBeFound("billClosingFlag.specified=true");

        // Get all the creditorLedgerList where billClosingFlag is null
        defaultCreditorLedgerShouldNotBeFound("billClosingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where dueDate equals to DEFAULT_DUE_DATE
        defaultCreditorLedgerShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the creditorLedgerList where dueDate equals to UPDATED_DUE_DATE
        defaultCreditorLedgerShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultCreditorLedgerShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the creditorLedgerList where dueDate equals to UPDATED_DUE_DATE
        defaultCreditorLedgerShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where dueDate is not null
        defaultCreditorLedgerShouldBeFound("dueDate.specified=true");

        // Get all the creditorLedgerList where dueDate is null
        defaultCreditorLedgerShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where dueDate greater than or equals to DEFAULT_DUE_DATE
        defaultCreditorLedgerShouldBeFound("dueDate.greaterOrEqualThan=" + DEFAULT_DUE_DATE);

        // Get all the creditorLedgerList where dueDate greater than or equals to UPDATED_DUE_DATE
        defaultCreditorLedgerShouldNotBeFound("dueDate.greaterOrEqualThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where dueDate less than or equals to DEFAULT_DUE_DATE
        defaultCreditorLedgerShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the creditorLedgerList where dueDate less than or equals to UPDATED_DUE_DATE
        defaultCreditorLedgerShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllCreditorLedgersByVatNoIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where vatNo equals to DEFAULT_VAT_NO
        defaultCreditorLedgerShouldBeFound("vatNo.equals=" + DEFAULT_VAT_NO);

        // Get all the creditorLedgerList where vatNo equals to UPDATED_VAT_NO
        defaultCreditorLedgerShouldNotBeFound("vatNo.equals=" + UPDATED_VAT_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByVatNoIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where vatNo in DEFAULT_VAT_NO or UPDATED_VAT_NO
        defaultCreditorLedgerShouldBeFound("vatNo.in=" + DEFAULT_VAT_NO + "," + UPDATED_VAT_NO);

        // Get all the creditorLedgerList where vatNo equals to UPDATED_VAT_NO
        defaultCreditorLedgerShouldNotBeFound("vatNo.in=" + UPDATED_VAT_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByVatNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where vatNo is not null
        defaultCreditorLedgerShouldBeFound("vatNo.specified=true");

        // Get all the creditorLedgerList where vatNo is null
        defaultCreditorLedgerShouldNotBeFound("vatNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByContCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where contCode equals to DEFAULT_CONT_CODE
        defaultCreditorLedgerShouldBeFound("contCode.equals=" + DEFAULT_CONT_CODE);

        // Get all the creditorLedgerList where contCode equals to UPDATED_CONT_CODE
        defaultCreditorLedgerShouldNotBeFound("contCode.equals=" + UPDATED_CONT_CODE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByContCodeIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where contCode in DEFAULT_CONT_CODE or UPDATED_CONT_CODE
        defaultCreditorLedgerShouldBeFound("contCode.in=" + DEFAULT_CONT_CODE + "," + UPDATED_CONT_CODE);

        // Get all the creditorLedgerList where contCode equals to UPDATED_CONT_CODE
        defaultCreditorLedgerShouldNotBeFound("contCode.in=" + UPDATED_CONT_CODE);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByContCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where contCode is not null
        defaultCreditorLedgerShouldBeFound("contCode.specified=true");

        // Get all the creditorLedgerList where contCode is null
        defaultCreditorLedgerShouldNotBeFound("contCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where orderNo equals to DEFAULT_ORDER_NO
        defaultCreditorLedgerShouldBeFound("orderNo.equals=" + DEFAULT_ORDER_NO);

        // Get all the creditorLedgerList where orderNo equals to UPDATED_ORDER_NO
        defaultCreditorLedgerShouldNotBeFound("orderNo.equals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where orderNo in DEFAULT_ORDER_NO or UPDATED_ORDER_NO
        defaultCreditorLedgerShouldBeFound("orderNo.in=" + DEFAULT_ORDER_NO + "," + UPDATED_ORDER_NO);

        // Get all the creditorLedgerList where orderNo equals to UPDATED_ORDER_NO
        defaultCreditorLedgerShouldNotBeFound("orderNo.in=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where orderNo is not null
        defaultCreditorLedgerShouldBeFound("orderNo.specified=true");

        // Get all the creditorLedgerList where orderNo is null
        defaultCreditorLedgerShouldNotBeFound("orderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultCreditorLedgerShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the creditorLedgerList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCreditorLedgerShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultCreditorLedgerShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the creditorLedgerList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCreditorLedgerShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedBy is not null
        defaultCreditorLedgerShouldBeFound("modifiedBy.specified=true");

        // Get all the creditorLedgerList where modifiedBy is null
        defaultCreditorLedgerShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultCreditorLedgerShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the creditorLedgerList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultCreditorLedgerShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultCreditorLedgerShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the creditorLedgerList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultCreditorLedgerShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedOn is not null
        defaultCreditorLedgerShouldBeFound("modifiedOn.specified=true");

        // Get all the creditorLedgerList where modifiedOn is null
        defaultCreditorLedgerShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultCreditorLedgerShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the creditorLedgerList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultCreditorLedgerShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCreditorLedgersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        // Get all the creditorLedgerList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultCreditorLedgerShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the creditorLedgerList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultCreditorLedgerShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllCreditorLedgersByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        Vendor vendor = VendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        creditorLedger.setVendor(vendor);
        creditorLedgerRepository.saveAndFlush(creditorLedger);
        Long vendorId = vendor.getId();

        // Get all the creditorLedgerList where vendor equals to vendorId
        defaultCreditorLedgerShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the creditorLedgerList where vendor equals to vendorId + 1
        defaultCreditorLedgerShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCreditorLedgerShouldBeFound(String filter) throws Exception {
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].billClosingFlag").value(hasItem(DEFAULT_BILL_CLOSING_FLAG.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO)))
            .andExpect(jsonPath("$.[*].contCode").value(hasItem(DEFAULT_CONT_CODE)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCreditorLedgerShouldNotBeFound(String filter) throws Exception {
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCreditorLedger() throws Exception {
        // Get the creditorLedger
        restCreditorLedgerMockMvc.perform(get("/api/creditor-ledgers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreditorLedger() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        int databaseSizeBeforeUpdate = creditorLedgerRepository.findAll().size();

        // Update the creditorLedger
        CreditorLedger updatedCreditorLedger = creditorLedgerRepository.findById(creditorLedger.getId()).get();
        // Disconnect from session so that the updates on updatedCreditorLedger are not directly saved in db
        em.detach(updatedCreditorLedger);
        updatedCreditorLedger
            .serialNo(UPDATED_SERIAL_NO)
            .billNo(UPDATED_BILL_NO)
            .billDate(UPDATED_BILL_DATE)
            .amount(UPDATED_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .balanceType(UPDATED_BALANCE_TYPE)
            .billClosingFlag(UPDATED_BILL_CLOSING_FLAG)
            .dueDate(UPDATED_DUE_DATE)
            .vatNo(UPDATED_VAT_NO)
            .contCode(UPDATED_CONT_CODE)
            .orderNo(UPDATED_ORDER_NO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        CreditorLedgerDTO creditorLedgerDTO = creditorLedgerMapper.toDto(updatedCreditorLedger);

        restCreditorLedgerMockMvc.perform(put("/api/creditor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditorLedgerDTO)))
            .andExpect(status().isOk());

        // Validate the CreditorLedger in the database
        List<CreditorLedger> creditorLedgerList = creditorLedgerRepository.findAll();
        assertThat(creditorLedgerList).hasSize(databaseSizeBeforeUpdate);
        CreditorLedger testCreditorLedger = creditorLedgerList.get(creditorLedgerList.size() - 1);
        assertThat(testCreditorLedger.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testCreditorLedger.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCreditorLedger.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCreditorLedger.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCreditorLedger.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testCreditorLedger.getBalanceType()).isEqualTo(UPDATED_BALANCE_TYPE);
        assertThat(testCreditorLedger.getBillClosingFlag()).isEqualTo(UPDATED_BILL_CLOSING_FLAG);
        assertThat(testCreditorLedger.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCreditorLedger.getVatNo()).isEqualTo(UPDATED_VAT_NO);
        assertThat(testCreditorLedger.getContCode()).isEqualTo(UPDATED_CONT_CODE);
        assertThat(testCreditorLedger.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testCreditorLedger.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCreditorLedger.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the CreditorLedger in Elasticsearch
        verify(mockCreditorLedgerSearchRepository, times(1)).save(testCreditorLedger);
    }

    @Test
    @Transactional
    public void updateNonExistingCreditorLedger() throws Exception {
        int databaseSizeBeforeUpdate = creditorLedgerRepository.findAll().size();

        // Create the CreditorLedger
        CreditorLedgerDTO creditorLedgerDTO = creditorLedgerMapper.toDto(creditorLedger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditorLedgerMockMvc.perform(put("/api/creditor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditorLedgerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CreditorLedger in the database
        List<CreditorLedger> creditorLedgerList = creditorLedgerRepository.findAll();
        assertThat(creditorLedgerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CreditorLedger in Elasticsearch
        verify(mockCreditorLedgerSearchRepository, times(0)).save(creditorLedger);
    }

    @Test
    @Transactional
    public void deleteCreditorLedger() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);

        int databaseSizeBeforeDelete = creditorLedgerRepository.findAll().size();

        // Delete the creditorLedger
        restCreditorLedgerMockMvc.perform(delete("/api/creditor-ledgers/{id}", creditorLedger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CreditorLedger> creditorLedgerList = creditorLedgerRepository.findAll();
        assertThat(creditorLedgerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CreditorLedger in Elasticsearch
        verify(mockCreditorLedgerSearchRepository, times(1)).deleteById(creditorLedger.getId());
    }

    @Test
    @Transactional
    public void searchCreditorLedger() throws Exception {
        // Initialize the database
        creditorLedgerRepository.saveAndFlush(creditorLedger);
        when(mockCreditorLedgerSearchRepository.search(queryStringQuery("id:" + creditorLedger.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(creditorLedger), PageRequest.of(0, 1), 1));
        // Search the creditorLedger
        restCreditorLedgerMockMvc.perform(get("/api/_search/creditor-ledgers?query=id:" + creditorLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].billClosingFlag").value(hasItem(DEFAULT_BILL_CLOSING_FLAG.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO)))
            .andExpect(jsonPath("$.[*].contCode").value(hasItem(DEFAULT_CONT_CODE)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditorLedger.class);
        CreditorLedger creditorLedger1 = new CreditorLedger();
        creditorLedger1.setId(1L);
        CreditorLedger creditorLedger2 = new CreditorLedger();
        creditorLedger2.setId(creditorLedger1.getId());
        assertThat(creditorLedger1).isEqualTo(creditorLedger2);
        creditorLedger2.setId(2L);
        assertThat(creditorLedger1).isNotEqualTo(creditorLedger2);
        creditorLedger1.setId(null);
        assertThat(creditorLedger1).isNotEqualTo(creditorLedger2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditorLedgerDTO.class);
        CreditorLedgerDTO creditorLedgerDTO1 = new CreditorLedgerDTO();
        creditorLedgerDTO1.setId(1L);
        CreditorLedgerDTO creditorLedgerDTO2 = new CreditorLedgerDTO();
        assertThat(creditorLedgerDTO1).isNotEqualTo(creditorLedgerDTO2);
        creditorLedgerDTO2.setId(creditorLedgerDTO1.getId());
        assertThat(creditorLedgerDTO1).isEqualTo(creditorLedgerDTO2);
        creditorLedgerDTO2.setId(2L);
        assertThat(creditorLedgerDTO1).isNotEqualTo(creditorLedgerDTO2);
        creditorLedgerDTO1.setId(null);
        assertThat(creditorLedgerDTO1).isNotEqualTo(creditorLedgerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditorLedgerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditorLedgerMapper.fromId(null)).isNull();
    }
}
