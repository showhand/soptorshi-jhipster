package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.Voucher;
import org.soptorshi.domain.Currency;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.search.DtTransactionSearchRepository;
import org.soptorshi.service.DtTransactionService;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.DtTransactionQueryService;

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
import org.soptorshi.domain.enumeration.VoucherType;
import org.soptorshi.domain.enumeration.InstrumentType;
/**
 * Test class for the DtTransactionResource REST controller.
 *
 * @see DtTransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DtTransactionResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VOUCHER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VOUCHER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SERIAL_NO = 1;
    private static final Integer UPDATED_SERIAL_NO = 2;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BalanceType DEFAULT_BALANCE_TYPE = BalanceType.DEBIT;
    private static final BalanceType UPDATED_BALANCE_TYPE = BalanceType.CREDIT;

    private static final VoucherType DEFAULT_TYPE = VoucherType.SELLING;
    private static final VoucherType UPDATED_TYPE = VoucherType.BUYING;

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final InstrumentType DEFAULT_INSTRUMENT_TYPE = InstrumentType.CHEQUE;
    private static final InstrumentType UPDATED_INSTRUMENT_TYPE = InstrumentType.PAY_ORDER;

    private static final String DEFAULT_INSTRUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INSTRUMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSTRUMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_F_CURRENCY = new BigDecimal(1);
    private static final BigDecimal UPDATED_F_CURRENCY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CONV_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONV_FACTOR = new BigDecimal(2);

    private static final LocalDate DEFAULT_POST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_NARRATION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private DtTransactionRepository dtTransactionRepository;

    @Autowired
    private DtTransactionMapper dtTransactionMapper;

    @Autowired
    private DtTransactionService dtTransactionService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DtTransactionSearchRepositoryMockConfiguration
     */
    @Autowired
    private DtTransactionSearchRepository mockDtTransactionSearchRepository;

    @Autowired
    private DtTransactionQueryService dtTransactionQueryService;

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

    private MockMvc restDtTransactionMockMvc;

    private DtTransaction dtTransaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DtTransactionResource dtTransactionResource = new DtTransactionResource(dtTransactionService, dtTransactionQueryService);
        this.restDtTransactionMockMvc = MockMvcBuilders.standaloneSetup(dtTransactionResource)
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
    public static DtTransaction createEntity(EntityManager em) {
        DtTransaction dtTransaction = new DtTransaction()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .serialNo(DEFAULT_SERIAL_NO)
            .amount(DEFAULT_AMOUNT)
            .balanceType(DEFAULT_BALANCE_TYPE)
            .type(DEFAULT_TYPE)
            .invoiceNo(DEFAULT_INVOICE_NO)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .instrumentType(DEFAULT_INSTRUMENT_TYPE)
            .instrumentNo(DEFAULT_INSTRUMENT_NO)
            .instrumentDate(DEFAULT_INSTRUMENT_DATE)
            .fCurrency(DEFAULT_F_CURRENCY)
            .convFactor(DEFAULT_CONV_FACTOR)
            .postDate(DEFAULT_POST_DATE)
            .narration(DEFAULT_NARRATION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .reference(DEFAULT_REFERENCE);
        return dtTransaction;
    }

    @Before
    public void initTest() {
        dtTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDtTransaction() throws Exception {
        int databaseSizeBeforeCreate = dtTransactionRepository.findAll().size();

        // Create the DtTransaction
        DtTransactionDTO dtTransactionDTO = dtTransactionMapper.toDto(dtTransaction);
        restDtTransactionMockMvc.perform(post("/api/dt-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dtTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the DtTransaction in the database
        List<DtTransaction> dtTransactionList = dtTransactionRepository.findAll();
        assertThat(dtTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        DtTransaction testDtTransaction = dtTransactionList.get(dtTransactionList.size() - 1);
        assertThat(testDtTransaction.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testDtTransaction.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testDtTransaction.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testDtTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDtTransaction.getBalanceType()).isEqualTo(DEFAULT_BALANCE_TYPE);
        assertThat(testDtTransaction.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDtTransaction.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testDtTransaction.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testDtTransaction.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testDtTransaction.getInstrumentNo()).isEqualTo(DEFAULT_INSTRUMENT_NO);
        assertThat(testDtTransaction.getInstrumentDate()).isEqualTo(DEFAULT_INSTRUMENT_DATE);
        assertThat(testDtTransaction.getfCurrency()).isEqualTo(DEFAULT_F_CURRENCY);
        assertThat(testDtTransaction.getConvFactor()).isEqualTo(DEFAULT_CONV_FACTOR);
        assertThat(testDtTransaction.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testDtTransaction.getNarration()).isEqualTo(DEFAULT_NARRATION);
        assertThat(testDtTransaction.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDtTransaction.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testDtTransaction.getReference()).isEqualTo(DEFAULT_REFERENCE);

        // Validate the DtTransaction in Elasticsearch
        verify(mockDtTransactionSearchRepository, times(1)).save(testDtTransaction);
    }

    @Test
    @Transactional
    public void createDtTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dtTransactionRepository.findAll().size();

        // Create the DtTransaction with an existing ID
        dtTransaction.setId(1L);
        DtTransactionDTO dtTransactionDTO = dtTransactionMapper.toDto(dtTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDtTransactionMockMvc.perform(post("/api/dt-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dtTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DtTransaction in the database
        List<DtTransaction> dtTransactionList = dtTransactionRepository.findAll();
        assertThat(dtTransactionList).hasSize(databaseSizeBeforeCreate);

        // Validate the DtTransaction in Elasticsearch
        verify(mockDtTransactionSearchRepository, times(0)).save(dtTransaction);
    }

    @Test
    @Transactional
    public void getAllDtTransactions() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList
        restDtTransactionMockMvc.perform(get("/api/dt-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dtTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].instrumentNo").value(hasItem(DEFAULT_INSTRUMENT_NO.toString())))
            .andExpect(jsonPath("$.[*].instrumentDate").value(hasItem(DEFAULT_INSTRUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fCurrency").value(hasItem(DEFAULT_F_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].convFactor").value(hasItem(DEFAULT_CONV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }
    
    @Test
    @Transactional
    public void getDtTransaction() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get the dtTransaction
        restDtTransactionMockMvc.perform(get("/api/dt-transactions/{id}", dtTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dtTransaction.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.balanceType").value(DEFAULT_BALANCE_TYPE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.instrumentType").value(DEFAULT_INSTRUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.instrumentNo").value(DEFAULT_INSTRUMENT_NO.toString()))
            .andExpect(jsonPath("$.instrumentDate").value(DEFAULT_INSTRUMENT_DATE.toString()))
            .andExpect(jsonPath("$.fCurrency").value(DEFAULT_F_CURRENCY.intValue()))
            .andExpect(jsonPath("$.convFactor").value(DEFAULT_CONV_FACTOR.intValue()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()))
            .andExpect(jsonPath("$.narration").value(DEFAULT_NARRATION.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultDtTransactionShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the dtTransactionList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultDtTransactionShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultDtTransactionShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the dtTransactionList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultDtTransactionShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherNo is not null
        defaultDtTransactionShouldBeFound("voucherNo.specified=true");

        // Get all the dtTransactionList where voucherNo is null
        defaultDtTransactionShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultDtTransactionShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the dtTransactionList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultDtTransactionShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultDtTransactionShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the dtTransactionList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultDtTransactionShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherDate is not null
        defaultDtTransactionShouldBeFound("voucherDate.specified=true");

        // Get all the dtTransactionList where voucherDate is null
        defaultDtTransactionShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherDate greater than or equals to DEFAULT_VOUCHER_DATE
        defaultDtTransactionShouldBeFound("voucherDate.greaterOrEqualThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the dtTransactionList where voucherDate greater than or equals to UPDATED_VOUCHER_DATE
        defaultDtTransactionShouldNotBeFound("voucherDate.greaterOrEqualThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where voucherDate less than or equals to DEFAULT_VOUCHER_DATE
        defaultDtTransactionShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the dtTransactionList where voucherDate less than or equals to UPDATED_VOUCHER_DATE
        defaultDtTransactionShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where serialNo equals to DEFAULT_SERIAL_NO
        defaultDtTransactionShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the dtTransactionList where serialNo equals to UPDATED_SERIAL_NO
        defaultDtTransactionShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultDtTransactionShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the dtTransactionList where serialNo equals to UPDATED_SERIAL_NO
        defaultDtTransactionShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where serialNo is not null
        defaultDtTransactionShouldBeFound("serialNo.specified=true");

        // Get all the dtTransactionList where serialNo is null
        defaultDtTransactionShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where serialNo greater than or equals to DEFAULT_SERIAL_NO
        defaultDtTransactionShouldBeFound("serialNo.greaterOrEqualThan=" + DEFAULT_SERIAL_NO);

        // Get all the dtTransactionList where serialNo greater than or equals to UPDATED_SERIAL_NO
        defaultDtTransactionShouldNotBeFound("serialNo.greaterOrEqualThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where serialNo less than or equals to DEFAULT_SERIAL_NO
        defaultDtTransactionShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the dtTransactionList where serialNo less than or equals to UPDATED_SERIAL_NO
        defaultDtTransactionShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where amount equals to DEFAULT_AMOUNT
        defaultDtTransactionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the dtTransactionList where amount equals to UPDATED_AMOUNT
        defaultDtTransactionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDtTransactionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the dtTransactionList where amount equals to UPDATED_AMOUNT
        defaultDtTransactionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where amount is not null
        defaultDtTransactionShouldBeFound("amount.specified=true");

        // Get all the dtTransactionList where amount is null
        defaultDtTransactionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByBalanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where balanceType equals to DEFAULT_BALANCE_TYPE
        defaultDtTransactionShouldBeFound("balanceType.equals=" + DEFAULT_BALANCE_TYPE);

        // Get all the dtTransactionList where balanceType equals to UPDATED_BALANCE_TYPE
        defaultDtTransactionShouldNotBeFound("balanceType.equals=" + UPDATED_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByBalanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where balanceType in DEFAULT_BALANCE_TYPE or UPDATED_BALANCE_TYPE
        defaultDtTransactionShouldBeFound("balanceType.in=" + DEFAULT_BALANCE_TYPE + "," + UPDATED_BALANCE_TYPE);

        // Get all the dtTransactionList where balanceType equals to UPDATED_BALANCE_TYPE
        defaultDtTransactionShouldNotBeFound("balanceType.in=" + UPDATED_BALANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByBalanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where balanceType is not null
        defaultDtTransactionShouldBeFound("balanceType.specified=true");

        // Get all the dtTransactionList where balanceType is null
        defaultDtTransactionShouldNotBeFound("balanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where type equals to DEFAULT_TYPE
        defaultDtTransactionShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dtTransactionList where type equals to UPDATED_TYPE
        defaultDtTransactionShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDtTransactionShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dtTransactionList where type equals to UPDATED_TYPE
        defaultDtTransactionShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where type is not null
        defaultDtTransactionShouldBeFound("type.specified=true");

        // Get all the dtTransactionList where type is null
        defaultDtTransactionShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceNo equals to DEFAULT_INVOICE_NO
        defaultDtTransactionShouldBeFound("invoiceNo.equals=" + DEFAULT_INVOICE_NO);

        // Get all the dtTransactionList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultDtTransactionShouldNotBeFound("invoiceNo.equals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceNo in DEFAULT_INVOICE_NO or UPDATED_INVOICE_NO
        defaultDtTransactionShouldBeFound("invoiceNo.in=" + DEFAULT_INVOICE_NO + "," + UPDATED_INVOICE_NO);

        // Get all the dtTransactionList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultDtTransactionShouldNotBeFound("invoiceNo.in=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceNo is not null
        defaultDtTransactionShouldBeFound("invoiceNo.specified=true");

        // Get all the dtTransactionList where invoiceNo is null
        defaultDtTransactionShouldNotBeFound("invoiceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultDtTransactionShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the dtTransactionList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultDtTransactionShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultDtTransactionShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the dtTransactionList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultDtTransactionShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceDate is not null
        defaultDtTransactionShouldBeFound("invoiceDate.specified=true");

        // Get all the dtTransactionList where invoiceDate is null
        defaultDtTransactionShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceDate greater than or equals to DEFAULT_INVOICE_DATE
        defaultDtTransactionShouldBeFound("invoiceDate.greaterOrEqualThan=" + DEFAULT_INVOICE_DATE);

        // Get all the dtTransactionList where invoiceDate greater than or equals to UPDATED_INVOICE_DATE
        defaultDtTransactionShouldNotBeFound("invoiceDate.greaterOrEqualThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where invoiceDate less than or equals to DEFAULT_INVOICE_DATE
        defaultDtTransactionShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the dtTransactionList where invoiceDate less than or equals to UPDATED_INVOICE_DATE
        defaultDtTransactionShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentType equals to DEFAULT_INSTRUMENT_TYPE
        defaultDtTransactionShouldBeFound("instrumentType.equals=" + DEFAULT_INSTRUMENT_TYPE);

        // Get all the dtTransactionList where instrumentType equals to UPDATED_INSTRUMENT_TYPE
        defaultDtTransactionShouldNotBeFound("instrumentType.equals=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentType in DEFAULT_INSTRUMENT_TYPE or UPDATED_INSTRUMENT_TYPE
        defaultDtTransactionShouldBeFound("instrumentType.in=" + DEFAULT_INSTRUMENT_TYPE + "," + UPDATED_INSTRUMENT_TYPE);

        // Get all the dtTransactionList where instrumentType equals to UPDATED_INSTRUMENT_TYPE
        defaultDtTransactionShouldNotBeFound("instrumentType.in=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentType is not null
        defaultDtTransactionShouldBeFound("instrumentType.specified=true");

        // Get all the dtTransactionList where instrumentType is null
        defaultDtTransactionShouldNotBeFound("instrumentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentNo equals to DEFAULT_INSTRUMENT_NO
        defaultDtTransactionShouldBeFound("instrumentNo.equals=" + DEFAULT_INSTRUMENT_NO);

        // Get all the dtTransactionList where instrumentNo equals to UPDATED_INSTRUMENT_NO
        defaultDtTransactionShouldNotBeFound("instrumentNo.equals=" + UPDATED_INSTRUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentNo in DEFAULT_INSTRUMENT_NO or UPDATED_INSTRUMENT_NO
        defaultDtTransactionShouldBeFound("instrumentNo.in=" + DEFAULT_INSTRUMENT_NO + "," + UPDATED_INSTRUMENT_NO);

        // Get all the dtTransactionList where instrumentNo equals to UPDATED_INSTRUMENT_NO
        defaultDtTransactionShouldNotBeFound("instrumentNo.in=" + UPDATED_INSTRUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentNo is not null
        defaultDtTransactionShouldBeFound("instrumentNo.specified=true");

        // Get all the dtTransactionList where instrumentNo is null
        defaultDtTransactionShouldNotBeFound("instrumentNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentDate equals to DEFAULT_INSTRUMENT_DATE
        defaultDtTransactionShouldBeFound("instrumentDate.equals=" + DEFAULT_INSTRUMENT_DATE);

        // Get all the dtTransactionList where instrumentDate equals to UPDATED_INSTRUMENT_DATE
        defaultDtTransactionShouldNotBeFound("instrumentDate.equals=" + UPDATED_INSTRUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentDateIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentDate in DEFAULT_INSTRUMENT_DATE or UPDATED_INSTRUMENT_DATE
        defaultDtTransactionShouldBeFound("instrumentDate.in=" + DEFAULT_INSTRUMENT_DATE + "," + UPDATED_INSTRUMENT_DATE);

        // Get all the dtTransactionList where instrumentDate equals to UPDATED_INSTRUMENT_DATE
        defaultDtTransactionShouldNotBeFound("instrumentDate.in=" + UPDATED_INSTRUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentDate is not null
        defaultDtTransactionShouldBeFound("instrumentDate.specified=true");

        // Get all the dtTransactionList where instrumentDate is null
        defaultDtTransactionShouldNotBeFound("instrumentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentDate greater than or equals to DEFAULT_INSTRUMENT_DATE
        defaultDtTransactionShouldBeFound("instrumentDate.greaterOrEqualThan=" + DEFAULT_INSTRUMENT_DATE);

        // Get all the dtTransactionList where instrumentDate greater than or equals to UPDATED_INSTRUMENT_DATE
        defaultDtTransactionShouldNotBeFound("instrumentDate.greaterOrEqualThan=" + UPDATED_INSTRUMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByInstrumentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where instrumentDate less than or equals to DEFAULT_INSTRUMENT_DATE
        defaultDtTransactionShouldNotBeFound("instrumentDate.lessThan=" + DEFAULT_INSTRUMENT_DATE);

        // Get all the dtTransactionList where instrumentDate less than or equals to UPDATED_INSTRUMENT_DATE
        defaultDtTransactionShouldBeFound("instrumentDate.lessThan=" + UPDATED_INSTRUMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByfCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where fCurrency equals to DEFAULT_F_CURRENCY
        defaultDtTransactionShouldBeFound("fCurrency.equals=" + DEFAULT_F_CURRENCY);

        // Get all the dtTransactionList where fCurrency equals to UPDATED_F_CURRENCY
        defaultDtTransactionShouldNotBeFound("fCurrency.equals=" + UPDATED_F_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByfCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where fCurrency in DEFAULT_F_CURRENCY or UPDATED_F_CURRENCY
        defaultDtTransactionShouldBeFound("fCurrency.in=" + DEFAULT_F_CURRENCY + "," + UPDATED_F_CURRENCY);

        // Get all the dtTransactionList where fCurrency equals to UPDATED_F_CURRENCY
        defaultDtTransactionShouldNotBeFound("fCurrency.in=" + UPDATED_F_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByfCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where fCurrency is not null
        defaultDtTransactionShouldBeFound("fCurrency.specified=true");

        // Get all the dtTransactionList where fCurrency is null
        defaultDtTransactionShouldNotBeFound("fCurrency.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByConvFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where convFactor equals to DEFAULT_CONV_FACTOR
        defaultDtTransactionShouldBeFound("convFactor.equals=" + DEFAULT_CONV_FACTOR);

        // Get all the dtTransactionList where convFactor equals to UPDATED_CONV_FACTOR
        defaultDtTransactionShouldNotBeFound("convFactor.equals=" + UPDATED_CONV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByConvFactorIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where convFactor in DEFAULT_CONV_FACTOR or UPDATED_CONV_FACTOR
        defaultDtTransactionShouldBeFound("convFactor.in=" + DEFAULT_CONV_FACTOR + "," + UPDATED_CONV_FACTOR);

        // Get all the dtTransactionList where convFactor equals to UPDATED_CONV_FACTOR
        defaultDtTransactionShouldNotBeFound("convFactor.in=" + UPDATED_CONV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByConvFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where convFactor is not null
        defaultDtTransactionShouldBeFound("convFactor.specified=true");

        // Get all the dtTransactionList where convFactor is null
        defaultDtTransactionShouldNotBeFound("convFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where postDate equals to DEFAULT_POST_DATE
        defaultDtTransactionShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the dtTransactionList where postDate equals to UPDATED_POST_DATE
        defaultDtTransactionShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultDtTransactionShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the dtTransactionList where postDate equals to UPDATED_POST_DATE
        defaultDtTransactionShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where postDate is not null
        defaultDtTransactionShouldBeFound("postDate.specified=true");

        // Get all the dtTransactionList where postDate is null
        defaultDtTransactionShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByPostDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where postDate greater than or equals to DEFAULT_POST_DATE
        defaultDtTransactionShouldBeFound("postDate.greaterOrEqualThan=" + DEFAULT_POST_DATE);

        // Get all the dtTransactionList where postDate greater than or equals to UPDATED_POST_DATE
        defaultDtTransactionShouldNotBeFound("postDate.greaterOrEqualThan=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByPostDateIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where postDate less than or equals to DEFAULT_POST_DATE
        defaultDtTransactionShouldNotBeFound("postDate.lessThan=" + DEFAULT_POST_DATE);

        // Get all the dtTransactionList where postDate less than or equals to UPDATED_POST_DATE
        defaultDtTransactionShouldBeFound("postDate.lessThan=" + UPDATED_POST_DATE);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where narration equals to DEFAULT_NARRATION
        defaultDtTransactionShouldBeFound("narration.equals=" + DEFAULT_NARRATION);

        // Get all the dtTransactionList where narration equals to UPDATED_NARRATION
        defaultDtTransactionShouldNotBeFound("narration.equals=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where narration in DEFAULT_NARRATION or UPDATED_NARRATION
        defaultDtTransactionShouldBeFound("narration.in=" + DEFAULT_NARRATION + "," + UPDATED_NARRATION);

        // Get all the dtTransactionList where narration equals to UPDATED_NARRATION
        defaultDtTransactionShouldNotBeFound("narration.in=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where narration is not null
        defaultDtTransactionShouldBeFound("narration.specified=true");

        // Get all the dtTransactionList where narration is null
        defaultDtTransactionShouldNotBeFound("narration.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDtTransactionShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the dtTransactionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDtTransactionShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDtTransactionShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the dtTransactionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDtTransactionShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedBy is not null
        defaultDtTransactionShouldBeFound("modifiedBy.specified=true");

        // Get all the dtTransactionList where modifiedBy is null
        defaultDtTransactionShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultDtTransactionShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the dtTransactionList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDtTransactionShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultDtTransactionShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the dtTransactionList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDtTransactionShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedOn is not null
        defaultDtTransactionShouldBeFound("modifiedOn.specified=true");

        // Get all the dtTransactionList where modifiedOn is null
        defaultDtTransactionShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultDtTransactionShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the dtTransactionList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultDtTransactionShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultDtTransactionShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the dtTransactionList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultDtTransactionShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where reference equals to DEFAULT_REFERENCE
        defaultDtTransactionShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the dtTransactionList where reference equals to UPDATED_REFERENCE
        defaultDtTransactionShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultDtTransactionShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the dtTransactionList where reference equals to UPDATED_REFERENCE
        defaultDtTransactionShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        // Get all the dtTransactionList where reference is not null
        defaultDtTransactionShouldBeFound("reference.specified=true");

        // Get all the dtTransactionList where reference is null
        defaultDtTransactionShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    public void getAllDtTransactionsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MstAccount account = MstAccountResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        dtTransaction.setAccount(account);
        dtTransactionRepository.saveAndFlush(dtTransaction);
        Long accountId = account.getId();

        // Get all the dtTransactionList where account equals to accountId
        defaultDtTransactionShouldBeFound("accountId.equals=" + accountId);

        // Get all the dtTransactionList where account equals to accountId + 1
        defaultDtTransactionShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        Voucher voucher = VoucherResourceIntTest.createEntity(em);
        em.persist(voucher);
        em.flush();
        dtTransaction.setVoucher(voucher);
        dtTransactionRepository.saveAndFlush(dtTransaction);
        Long voucherId = voucher.getId();

        // Get all the dtTransactionList where voucher equals to voucherId
        defaultDtTransactionShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the dtTransactionList where voucher equals to voucherId + 1
        defaultDtTransactionShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }


    @Test
    @Transactional
    public void getAllDtTransactionsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Currency currency = CurrencyResourceIntTest.createEntity(em);
        em.persist(currency);
        em.flush();
        dtTransaction.setCurrency(currency);
        dtTransactionRepository.saveAndFlush(dtTransaction);
        Long currencyId = currency.getId();

        // Get all the dtTransactionList where currency equals to currencyId
        defaultDtTransactionShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the dtTransactionList where currency equals to currencyId + 1
        defaultDtTransactionShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDtTransactionShouldBeFound(String filter) throws Exception {
        restDtTransactionMockMvc.perform(get("/api/dt-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dtTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].instrumentNo").value(hasItem(DEFAULT_INSTRUMENT_NO)))
            .andExpect(jsonPath("$.[*].instrumentDate").value(hasItem(DEFAULT_INSTRUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fCurrency").value(hasItem(DEFAULT_F_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].convFactor").value(hasItem(DEFAULT_CONV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));

        // Check, that the count call also returns 1
        restDtTransactionMockMvc.perform(get("/api/dt-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDtTransactionShouldNotBeFound(String filter) throws Exception {
        restDtTransactionMockMvc.perform(get("/api/dt-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDtTransactionMockMvc.perform(get("/api/dt-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDtTransaction() throws Exception {
        // Get the dtTransaction
        restDtTransactionMockMvc.perform(get("/api/dt-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDtTransaction() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        int databaseSizeBeforeUpdate = dtTransactionRepository.findAll().size();

        // Update the dtTransaction
        DtTransaction updatedDtTransaction = dtTransactionRepository.findById(dtTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedDtTransaction are not directly saved in db
        em.detach(updatedDtTransaction);
        updatedDtTransaction
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .serialNo(UPDATED_SERIAL_NO)
            .amount(UPDATED_AMOUNT)
            .balanceType(UPDATED_BALANCE_TYPE)
            .type(UPDATED_TYPE)
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .instrumentNo(UPDATED_INSTRUMENT_NO)
            .instrumentDate(UPDATED_INSTRUMENT_DATE)
            .fCurrency(UPDATED_F_CURRENCY)
            .convFactor(UPDATED_CONV_FACTOR)
            .postDate(UPDATED_POST_DATE)
            .narration(UPDATED_NARRATION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .reference(UPDATED_REFERENCE);
        DtTransactionDTO dtTransactionDTO = dtTransactionMapper.toDto(updatedDtTransaction);

        restDtTransactionMockMvc.perform(put("/api/dt-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dtTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the DtTransaction in the database
        List<DtTransaction> dtTransactionList = dtTransactionRepository.findAll();
        assertThat(dtTransactionList).hasSize(databaseSizeBeforeUpdate);
        DtTransaction testDtTransaction = dtTransactionList.get(dtTransactionList.size() - 1);
        assertThat(testDtTransaction.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testDtTransaction.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testDtTransaction.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testDtTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDtTransaction.getBalanceType()).isEqualTo(UPDATED_BALANCE_TYPE);
        assertThat(testDtTransaction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDtTransaction.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testDtTransaction.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testDtTransaction.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testDtTransaction.getInstrumentNo()).isEqualTo(UPDATED_INSTRUMENT_NO);
        assertThat(testDtTransaction.getInstrumentDate()).isEqualTo(UPDATED_INSTRUMENT_DATE);
        assertThat(testDtTransaction.getfCurrency()).isEqualTo(UPDATED_F_CURRENCY);
        assertThat(testDtTransaction.getConvFactor()).isEqualTo(UPDATED_CONV_FACTOR);
        assertThat(testDtTransaction.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testDtTransaction.getNarration()).isEqualTo(UPDATED_NARRATION);
        assertThat(testDtTransaction.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDtTransaction.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDtTransaction.getReference()).isEqualTo(UPDATED_REFERENCE);

        // Validate the DtTransaction in Elasticsearch
        verify(mockDtTransactionSearchRepository, times(1)).save(testDtTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingDtTransaction() throws Exception {
        int databaseSizeBeforeUpdate = dtTransactionRepository.findAll().size();

        // Create the DtTransaction
        DtTransactionDTO dtTransactionDTO = dtTransactionMapper.toDto(dtTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDtTransactionMockMvc.perform(put("/api/dt-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dtTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DtTransaction in the database
        List<DtTransaction> dtTransactionList = dtTransactionRepository.findAll();
        assertThat(dtTransactionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DtTransaction in Elasticsearch
        verify(mockDtTransactionSearchRepository, times(0)).save(dtTransaction);
    }

    @Test
    @Transactional
    public void deleteDtTransaction() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);

        int databaseSizeBeforeDelete = dtTransactionRepository.findAll().size();

        // Delete the dtTransaction
        restDtTransactionMockMvc.perform(delete("/api/dt-transactions/{id}", dtTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DtTransaction> dtTransactionList = dtTransactionRepository.findAll();
        assertThat(dtTransactionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DtTransaction in Elasticsearch
        verify(mockDtTransactionSearchRepository, times(1)).deleteById(dtTransaction.getId());
    }

    @Test
    @Transactional
    public void searchDtTransaction() throws Exception {
        // Initialize the database
        dtTransactionRepository.saveAndFlush(dtTransaction);
        when(mockDtTransactionSearchRepository.search(queryStringQuery("id:" + dtTransaction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dtTransaction), PageRequest.of(0, 1), 1));
        // Search the dtTransaction
        restDtTransactionMockMvc.perform(get("/api/_search/dt-transactions?query=id:" + dtTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dtTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].balanceType").value(hasItem(DEFAULT_BALANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].instrumentNo").value(hasItem(DEFAULT_INSTRUMENT_NO)))
            .andExpect(jsonPath("$.[*].instrumentDate").value(hasItem(DEFAULT_INSTRUMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fCurrency").value(hasItem(DEFAULT_F_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].convFactor").value(hasItem(DEFAULT_CONV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DtTransaction.class);
        DtTransaction dtTransaction1 = new DtTransaction();
        dtTransaction1.setId(1L);
        DtTransaction dtTransaction2 = new DtTransaction();
        dtTransaction2.setId(dtTransaction1.getId());
        assertThat(dtTransaction1).isEqualTo(dtTransaction2);
        dtTransaction2.setId(2L);
        assertThat(dtTransaction1).isNotEqualTo(dtTransaction2);
        dtTransaction1.setId(null);
        assertThat(dtTransaction1).isNotEqualTo(dtTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DtTransactionDTO.class);
        DtTransactionDTO dtTransactionDTO1 = new DtTransactionDTO();
        dtTransactionDTO1.setId(1L);
        DtTransactionDTO dtTransactionDTO2 = new DtTransactionDTO();
        assertThat(dtTransactionDTO1).isNotEqualTo(dtTransactionDTO2);
        dtTransactionDTO2.setId(dtTransactionDTO1.getId());
        assertThat(dtTransactionDTO1).isEqualTo(dtTransactionDTO2);
        dtTransactionDTO2.setId(2L);
        assertThat(dtTransactionDTO1).isNotEqualTo(dtTransactionDTO2);
        dtTransactionDTO1.setId(null);
        assertThat(dtTransactionDTO1).isNotEqualTo(dtTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dtTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dtTransactionMapper.fromId(null)).isNull();
    }
}
