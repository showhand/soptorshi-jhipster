package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DebtorLedger;
import org.soptorshi.domain.Customer;
import org.soptorshi.repository.DebtorLedgerRepository;
import org.soptorshi.repository.search.DebtorLedgerSearchRepository;
import org.soptorshi.service.DebtorLedgerService;
import org.soptorshi.service.dto.DebtorLedgerDTO;
import org.soptorshi.service.mapper.DebtorLedgerMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DebtorLedgerCriteria;
import org.soptorshi.service.DebtorLedgerQueryService;

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

import org.soptorshi.domain.enumeration.BillClosingFlag;
/**
 * Test class for the DebtorLedgerResource REST controller.
 *
 * @see DebtorLedgerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DebtorLedgerResourceIntTest {

    private static final String DEFAULT_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BILL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BILL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAID_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAID_AMOUNT = new BigDecimal(2);

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
    private DebtorLedgerRepository debtorLedgerRepository;

    @Autowired
    private DebtorLedgerMapper debtorLedgerMapper;

    @Autowired
    private DebtorLedgerService debtorLedgerService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DebtorLedgerSearchRepositoryMockConfiguration
     */
    @Autowired
    private DebtorLedgerSearchRepository mockDebtorLedgerSearchRepository;

    @Autowired
    private DebtorLedgerQueryService debtorLedgerQueryService;

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

    private MockMvc restDebtorLedgerMockMvc;

    private DebtorLedger debtorLedger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DebtorLedgerResource debtorLedgerResource = new DebtorLedgerResource(debtorLedgerService, debtorLedgerQueryService);
        this.restDebtorLedgerMockMvc = MockMvcBuilders.standaloneSetup(debtorLedgerResource)
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
    public static DebtorLedger createEntity(EntityManager em) {
        DebtorLedger debtorLedger = new DebtorLedger()
            .serialNo(DEFAULT_SERIAL_NO)
            .billNo(DEFAULT_BILL_NO)
            .billDate(DEFAULT_BILL_DATE)
            .amount(DEFAULT_AMOUNT)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .billClosingFlag(DEFAULT_BILL_CLOSING_FLAG)
            .dueDate(DEFAULT_DUE_DATE)
            .vatNo(DEFAULT_VAT_NO)
            .contCode(DEFAULT_CONT_CODE)
            .orderNo(DEFAULT_ORDER_NO)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return debtorLedger;
    }

    @Before
    public void initTest() {
        debtorLedger = createEntity(em);
    }

    @Test
    @Transactional
    public void createDebtorLedger() throws Exception {
        int databaseSizeBeforeCreate = debtorLedgerRepository.findAll().size();

        // Create the DebtorLedger
        DebtorLedgerDTO debtorLedgerDTO = debtorLedgerMapper.toDto(debtorLedger);
        restDebtorLedgerMockMvc.perform(post("/api/debtor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtorLedgerDTO)))
            .andExpect(status().isCreated());

        // Validate the DebtorLedger in the database
        List<DebtorLedger> debtorLedgerList = debtorLedgerRepository.findAll();
        assertThat(debtorLedgerList).hasSize(databaseSizeBeforeCreate + 1);
        DebtorLedger testDebtorLedger = debtorLedgerList.get(debtorLedgerList.size() - 1);
        assertThat(testDebtorLedger.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testDebtorLedger.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testDebtorLedger.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testDebtorLedger.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDebtorLedger.getPaidAmount()).isEqualTo(DEFAULT_PAID_AMOUNT);
        assertThat(testDebtorLedger.getBillClosingFlag()).isEqualTo(DEFAULT_BILL_CLOSING_FLAG);
        assertThat(testDebtorLedger.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testDebtorLedger.getVatNo()).isEqualTo(DEFAULT_VAT_NO);
        assertThat(testDebtorLedger.getContCode()).isEqualTo(DEFAULT_CONT_CODE);
        assertThat(testDebtorLedger.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testDebtorLedger.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDebtorLedger.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the DebtorLedger in Elasticsearch
        verify(mockDebtorLedgerSearchRepository, times(1)).save(testDebtorLedger);
    }

    @Test
    @Transactional
    public void createDebtorLedgerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = debtorLedgerRepository.findAll().size();

        // Create the DebtorLedger with an existing ID
        debtorLedger.setId(1L);
        DebtorLedgerDTO debtorLedgerDTO = debtorLedgerMapper.toDto(debtorLedger);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDebtorLedgerMockMvc.perform(post("/api/debtor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtorLedgerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebtorLedger in the database
        List<DebtorLedger> debtorLedgerList = debtorLedgerRepository.findAll();
        assertThat(debtorLedgerList).hasSize(databaseSizeBeforeCreate);

        // Validate the DebtorLedger in Elasticsearch
        verify(mockDebtorLedgerSearchRepository, times(0)).save(debtorLedger);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgers() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO.toString())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
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
    public void getDebtorLedger() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get the debtorLedger
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers/{id}", debtorLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(debtorLedger.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.intValue()))
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
    public void getAllDebtorLedgersBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where serialNo equals to DEFAULT_SERIAL_NO
        defaultDebtorLedgerShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the debtorLedgerList where serialNo equals to UPDATED_SERIAL_NO
        defaultDebtorLedgerShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultDebtorLedgerShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the debtorLedgerList where serialNo equals to UPDATED_SERIAL_NO
        defaultDebtorLedgerShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where serialNo is not null
        defaultDebtorLedgerShouldBeFound("serialNo.specified=true");

        // Get all the debtorLedgerList where serialNo is null
        defaultDebtorLedgerShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillNoIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billNo equals to DEFAULT_BILL_NO
        defaultDebtorLedgerShouldBeFound("billNo.equals=" + DEFAULT_BILL_NO);

        // Get all the debtorLedgerList where billNo equals to UPDATED_BILL_NO
        defaultDebtorLedgerShouldNotBeFound("billNo.equals=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillNoIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billNo in DEFAULT_BILL_NO or UPDATED_BILL_NO
        defaultDebtorLedgerShouldBeFound("billNo.in=" + DEFAULT_BILL_NO + "," + UPDATED_BILL_NO);

        // Get all the debtorLedgerList where billNo equals to UPDATED_BILL_NO
        defaultDebtorLedgerShouldNotBeFound("billNo.in=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billNo is not null
        defaultDebtorLedgerShouldBeFound("billNo.specified=true");

        // Get all the debtorLedgerList where billNo is null
        defaultDebtorLedgerShouldNotBeFound("billNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billDate equals to DEFAULT_BILL_DATE
        defaultDebtorLedgerShouldBeFound("billDate.equals=" + DEFAULT_BILL_DATE);

        // Get all the debtorLedgerList where billDate equals to UPDATED_BILL_DATE
        defaultDebtorLedgerShouldNotBeFound("billDate.equals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billDate in DEFAULT_BILL_DATE or UPDATED_BILL_DATE
        defaultDebtorLedgerShouldBeFound("billDate.in=" + DEFAULT_BILL_DATE + "," + UPDATED_BILL_DATE);

        // Get all the debtorLedgerList where billDate equals to UPDATED_BILL_DATE
        defaultDebtorLedgerShouldNotBeFound("billDate.in=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billDate is not null
        defaultDebtorLedgerShouldBeFound("billDate.specified=true");

        // Get all the debtorLedgerList where billDate is null
        defaultDebtorLedgerShouldNotBeFound("billDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billDate greater than or equals to DEFAULT_BILL_DATE
        defaultDebtorLedgerShouldBeFound("billDate.greaterOrEqualThan=" + DEFAULT_BILL_DATE);

        // Get all the debtorLedgerList where billDate greater than or equals to UPDATED_BILL_DATE
        defaultDebtorLedgerShouldNotBeFound("billDate.greaterOrEqualThan=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillDateIsLessThanSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billDate less than or equals to DEFAULT_BILL_DATE
        defaultDebtorLedgerShouldNotBeFound("billDate.lessThan=" + DEFAULT_BILL_DATE);

        // Get all the debtorLedgerList where billDate less than or equals to UPDATED_BILL_DATE
        defaultDebtorLedgerShouldBeFound("billDate.lessThan=" + UPDATED_BILL_DATE);
    }


    @Test
    @Transactional
    public void getAllDebtorLedgersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where amount equals to DEFAULT_AMOUNT
        defaultDebtorLedgerShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the debtorLedgerList where amount equals to UPDATED_AMOUNT
        defaultDebtorLedgerShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDebtorLedgerShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the debtorLedgerList where amount equals to UPDATED_AMOUNT
        defaultDebtorLedgerShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where amount is not null
        defaultDebtorLedgerShouldBeFound("amount.specified=true");

        // Get all the debtorLedgerList where amount is null
        defaultDebtorLedgerShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByPaidAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where paidAmount equals to DEFAULT_PAID_AMOUNT
        defaultDebtorLedgerShouldBeFound("paidAmount.equals=" + DEFAULT_PAID_AMOUNT);

        // Get all the debtorLedgerList where paidAmount equals to UPDATED_PAID_AMOUNT
        defaultDebtorLedgerShouldNotBeFound("paidAmount.equals=" + UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByPaidAmountIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where paidAmount in DEFAULT_PAID_AMOUNT or UPDATED_PAID_AMOUNT
        defaultDebtorLedgerShouldBeFound("paidAmount.in=" + DEFAULT_PAID_AMOUNT + "," + UPDATED_PAID_AMOUNT);

        // Get all the debtorLedgerList where paidAmount equals to UPDATED_PAID_AMOUNT
        defaultDebtorLedgerShouldNotBeFound("paidAmount.in=" + UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByPaidAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where paidAmount is not null
        defaultDebtorLedgerShouldBeFound("paidAmount.specified=true");

        // Get all the debtorLedgerList where paidAmount is null
        defaultDebtorLedgerShouldNotBeFound("paidAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillClosingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billClosingFlag equals to DEFAULT_BILL_CLOSING_FLAG
        defaultDebtorLedgerShouldBeFound("billClosingFlag.equals=" + DEFAULT_BILL_CLOSING_FLAG);

        // Get all the debtorLedgerList where billClosingFlag equals to UPDATED_BILL_CLOSING_FLAG
        defaultDebtorLedgerShouldNotBeFound("billClosingFlag.equals=" + UPDATED_BILL_CLOSING_FLAG);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillClosingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billClosingFlag in DEFAULT_BILL_CLOSING_FLAG or UPDATED_BILL_CLOSING_FLAG
        defaultDebtorLedgerShouldBeFound("billClosingFlag.in=" + DEFAULT_BILL_CLOSING_FLAG + "," + UPDATED_BILL_CLOSING_FLAG);

        // Get all the debtorLedgerList where billClosingFlag equals to UPDATED_BILL_CLOSING_FLAG
        defaultDebtorLedgerShouldNotBeFound("billClosingFlag.in=" + UPDATED_BILL_CLOSING_FLAG);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByBillClosingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where billClosingFlag is not null
        defaultDebtorLedgerShouldBeFound("billClosingFlag.specified=true");

        // Get all the debtorLedgerList where billClosingFlag is null
        defaultDebtorLedgerShouldNotBeFound("billClosingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where dueDate equals to DEFAULT_DUE_DATE
        defaultDebtorLedgerShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the debtorLedgerList where dueDate equals to UPDATED_DUE_DATE
        defaultDebtorLedgerShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultDebtorLedgerShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the debtorLedgerList where dueDate equals to UPDATED_DUE_DATE
        defaultDebtorLedgerShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where dueDate is not null
        defaultDebtorLedgerShouldBeFound("dueDate.specified=true");

        // Get all the debtorLedgerList where dueDate is null
        defaultDebtorLedgerShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where dueDate greater than or equals to DEFAULT_DUE_DATE
        defaultDebtorLedgerShouldBeFound("dueDate.greaterOrEqualThan=" + DEFAULT_DUE_DATE);

        // Get all the debtorLedgerList where dueDate greater than or equals to UPDATED_DUE_DATE
        defaultDebtorLedgerShouldNotBeFound("dueDate.greaterOrEqualThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where dueDate less than or equals to DEFAULT_DUE_DATE
        defaultDebtorLedgerShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the debtorLedgerList where dueDate less than or equals to UPDATED_DUE_DATE
        defaultDebtorLedgerShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllDebtorLedgersByVatNoIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where vatNo equals to DEFAULT_VAT_NO
        defaultDebtorLedgerShouldBeFound("vatNo.equals=" + DEFAULT_VAT_NO);

        // Get all the debtorLedgerList where vatNo equals to UPDATED_VAT_NO
        defaultDebtorLedgerShouldNotBeFound("vatNo.equals=" + UPDATED_VAT_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByVatNoIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where vatNo in DEFAULT_VAT_NO or UPDATED_VAT_NO
        defaultDebtorLedgerShouldBeFound("vatNo.in=" + DEFAULT_VAT_NO + "," + UPDATED_VAT_NO);

        // Get all the debtorLedgerList where vatNo equals to UPDATED_VAT_NO
        defaultDebtorLedgerShouldNotBeFound("vatNo.in=" + UPDATED_VAT_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByVatNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where vatNo is not null
        defaultDebtorLedgerShouldBeFound("vatNo.specified=true");

        // Get all the debtorLedgerList where vatNo is null
        defaultDebtorLedgerShouldNotBeFound("vatNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByContCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where contCode equals to DEFAULT_CONT_CODE
        defaultDebtorLedgerShouldBeFound("contCode.equals=" + DEFAULT_CONT_CODE);

        // Get all the debtorLedgerList where contCode equals to UPDATED_CONT_CODE
        defaultDebtorLedgerShouldNotBeFound("contCode.equals=" + UPDATED_CONT_CODE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByContCodeIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where contCode in DEFAULT_CONT_CODE or UPDATED_CONT_CODE
        defaultDebtorLedgerShouldBeFound("contCode.in=" + DEFAULT_CONT_CODE + "," + UPDATED_CONT_CODE);

        // Get all the debtorLedgerList where contCode equals to UPDATED_CONT_CODE
        defaultDebtorLedgerShouldNotBeFound("contCode.in=" + UPDATED_CONT_CODE);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByContCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where contCode is not null
        defaultDebtorLedgerShouldBeFound("contCode.specified=true");

        // Get all the debtorLedgerList where contCode is null
        defaultDebtorLedgerShouldNotBeFound("contCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where orderNo equals to DEFAULT_ORDER_NO
        defaultDebtorLedgerShouldBeFound("orderNo.equals=" + DEFAULT_ORDER_NO);

        // Get all the debtorLedgerList where orderNo equals to UPDATED_ORDER_NO
        defaultDebtorLedgerShouldNotBeFound("orderNo.equals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where orderNo in DEFAULT_ORDER_NO or UPDATED_ORDER_NO
        defaultDebtorLedgerShouldBeFound("orderNo.in=" + DEFAULT_ORDER_NO + "," + UPDATED_ORDER_NO);

        // Get all the debtorLedgerList where orderNo equals to UPDATED_ORDER_NO
        defaultDebtorLedgerShouldNotBeFound("orderNo.in=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where orderNo is not null
        defaultDebtorLedgerShouldBeFound("orderNo.specified=true");

        // Get all the debtorLedgerList where orderNo is null
        defaultDebtorLedgerShouldNotBeFound("orderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDebtorLedgerShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the debtorLedgerList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDebtorLedgerShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDebtorLedgerShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the debtorLedgerList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDebtorLedgerShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedBy is not null
        defaultDebtorLedgerShouldBeFound("modifiedBy.specified=true");

        // Get all the debtorLedgerList where modifiedBy is null
        defaultDebtorLedgerShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultDebtorLedgerShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the debtorLedgerList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDebtorLedgerShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultDebtorLedgerShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the debtorLedgerList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDebtorLedgerShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedOn is not null
        defaultDebtorLedgerShouldBeFound("modifiedOn.specified=true");

        // Get all the debtorLedgerList where modifiedOn is null
        defaultDebtorLedgerShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultDebtorLedgerShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the debtorLedgerList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultDebtorLedgerShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDebtorLedgersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        // Get all the debtorLedgerList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultDebtorLedgerShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the debtorLedgerList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultDebtorLedgerShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllDebtorLedgersByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        Customer customer = CustomerResourceIntTest.createEntity(em);
        em.persist(customer);
        em.flush();
        debtorLedger.setCustomer(customer);
        debtorLedgerRepository.saveAndFlush(debtorLedger);
        Long customerId = customer.getId();

        // Get all the debtorLedgerList where customer equals to customerId
        defaultDebtorLedgerShouldBeFound("customerId.equals=" + customerId);

        // Get all the debtorLedgerList where customer equals to customerId + 1
        defaultDebtorLedgerShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDebtorLedgerShouldBeFound(String filter) throws Exception {
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billClosingFlag").value(hasItem(DEFAULT_BILL_CLOSING_FLAG.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO)))
            .andExpect(jsonPath("$.[*].contCode").value(hasItem(DEFAULT_CONT_CODE)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDebtorLedgerShouldNotBeFound(String filter) throws Exception {
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDebtorLedger() throws Exception {
        // Get the debtorLedger
        restDebtorLedgerMockMvc.perform(get("/api/debtor-ledgers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDebtorLedger() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        int databaseSizeBeforeUpdate = debtorLedgerRepository.findAll().size();

        // Update the debtorLedger
        DebtorLedger updatedDebtorLedger = debtorLedgerRepository.findById(debtorLedger.getId()).get();
        // Disconnect from session so that the updates on updatedDebtorLedger are not directly saved in db
        em.detach(updatedDebtorLedger);
        updatedDebtorLedger
            .serialNo(UPDATED_SERIAL_NO)
            .billNo(UPDATED_BILL_NO)
            .billDate(UPDATED_BILL_DATE)
            .amount(UPDATED_AMOUNT)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .billClosingFlag(UPDATED_BILL_CLOSING_FLAG)
            .dueDate(UPDATED_DUE_DATE)
            .vatNo(UPDATED_VAT_NO)
            .contCode(UPDATED_CONT_CODE)
            .orderNo(UPDATED_ORDER_NO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        DebtorLedgerDTO debtorLedgerDTO = debtorLedgerMapper.toDto(updatedDebtorLedger);

        restDebtorLedgerMockMvc.perform(put("/api/debtor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtorLedgerDTO)))
            .andExpect(status().isOk());

        // Validate the DebtorLedger in the database
        List<DebtorLedger> debtorLedgerList = debtorLedgerRepository.findAll();
        assertThat(debtorLedgerList).hasSize(databaseSizeBeforeUpdate);
        DebtorLedger testDebtorLedger = debtorLedgerList.get(debtorLedgerList.size() - 1);
        assertThat(testDebtorLedger.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testDebtorLedger.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testDebtorLedger.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testDebtorLedger.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDebtorLedger.getPaidAmount()).isEqualTo(UPDATED_PAID_AMOUNT);
        assertThat(testDebtorLedger.getBillClosingFlag()).isEqualTo(UPDATED_BILL_CLOSING_FLAG);
        assertThat(testDebtorLedger.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testDebtorLedger.getVatNo()).isEqualTo(UPDATED_VAT_NO);
        assertThat(testDebtorLedger.getContCode()).isEqualTo(UPDATED_CONT_CODE);
        assertThat(testDebtorLedger.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testDebtorLedger.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDebtorLedger.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the DebtorLedger in Elasticsearch
        verify(mockDebtorLedgerSearchRepository, times(1)).save(testDebtorLedger);
    }

    @Test
    @Transactional
    public void updateNonExistingDebtorLedger() throws Exception {
        int databaseSizeBeforeUpdate = debtorLedgerRepository.findAll().size();

        // Create the DebtorLedger
        DebtorLedgerDTO debtorLedgerDTO = debtorLedgerMapper.toDto(debtorLedger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDebtorLedgerMockMvc.perform(put("/api/debtor-ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(debtorLedgerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DebtorLedger in the database
        List<DebtorLedger> debtorLedgerList = debtorLedgerRepository.findAll();
        assertThat(debtorLedgerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DebtorLedger in Elasticsearch
        verify(mockDebtorLedgerSearchRepository, times(0)).save(debtorLedger);
    }

    @Test
    @Transactional
    public void deleteDebtorLedger() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);

        int databaseSizeBeforeDelete = debtorLedgerRepository.findAll().size();

        // Delete the debtorLedger
        restDebtorLedgerMockMvc.perform(delete("/api/debtor-ledgers/{id}", debtorLedger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DebtorLedger> debtorLedgerList = debtorLedgerRepository.findAll();
        assertThat(debtorLedgerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DebtorLedger in Elasticsearch
        verify(mockDebtorLedgerSearchRepository, times(1)).deleteById(debtorLedger.getId());
    }

    @Test
    @Transactional
    public void searchDebtorLedger() throws Exception {
        // Initialize the database
        debtorLedgerRepository.saveAndFlush(debtorLedger);
        when(mockDebtorLedgerSearchRepository.search(queryStringQuery("id:" + debtorLedger.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(debtorLedger), PageRequest.of(0, 1), 1));
        // Search the debtorLedger
        restDebtorLedgerMockMvc.perform(get("/api/_search/debtor-ledgers?query=id:" + debtorLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(debtorLedger.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.intValue())))
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
        TestUtil.equalsVerifier(DebtorLedger.class);
        DebtorLedger debtorLedger1 = new DebtorLedger();
        debtorLedger1.setId(1L);
        DebtorLedger debtorLedger2 = new DebtorLedger();
        debtorLedger2.setId(debtorLedger1.getId());
        assertThat(debtorLedger1).isEqualTo(debtorLedger2);
        debtorLedger2.setId(2L);
        assertThat(debtorLedger1).isNotEqualTo(debtorLedger2);
        debtorLedger1.setId(null);
        assertThat(debtorLedger1).isNotEqualTo(debtorLedger2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtorLedgerDTO.class);
        DebtorLedgerDTO debtorLedgerDTO1 = new DebtorLedgerDTO();
        debtorLedgerDTO1.setId(1L);
        DebtorLedgerDTO debtorLedgerDTO2 = new DebtorLedgerDTO();
        assertThat(debtorLedgerDTO1).isNotEqualTo(debtorLedgerDTO2);
        debtorLedgerDTO2.setId(debtorLedgerDTO1.getId());
        assertThat(debtorLedgerDTO1).isEqualTo(debtorLedgerDTO2);
        debtorLedgerDTO2.setId(2L);
        assertThat(debtorLedgerDTO1).isNotEqualTo(debtorLedgerDTO2);
        debtorLedgerDTO1.setId(null);
        assertThat(debtorLedgerDTO1).isNotEqualTo(debtorLedgerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(debtorLedgerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(debtorLedgerMapper.fromId(null)).isNull();
    }
}
