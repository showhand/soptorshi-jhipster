package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Loan;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.LoanRepository;
import org.soptorshi.repository.search.LoanSearchRepository;
import org.soptorshi.service.LoanService;
import org.soptorshi.service.dto.LoanDTO;
import org.soptorshi.service.mapper.LoanMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.LoanCriteria;
import org.soptorshi.service.LoanQueryService;

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

import org.soptorshi.domain.enumeration.PaymentStatus;
/**
 * Test class for the LoanResource REST controller.
 *
 * @see LoanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class LoanResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_TAKEN_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TAKEN_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTHLY_PAYABLE = 1D;
    private static final Double UPDATED_MONTHLY_PAYABLE = 2D;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.NOT_PAID;

    private static final BigDecimal DEFAULT_LEFT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LEFT = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private LoanService loanService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.LoanSearchRepositoryMockConfiguration
     */
    @Autowired
    private LoanSearchRepository mockLoanSearchRepository;

    @Autowired
    private LoanQueryService loanQueryService;

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

    private MockMvc restLoanMockMvc;

    private Loan loan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanResource loanResource = new LoanResource(loanService, loanQueryService);
        this.restLoanMockMvc = MockMvcBuilders.standaloneSetup(loanResource)
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
    public static Loan createEntity(EntityManager em) {
        Loan loan = new Loan()
            .amount(DEFAULT_AMOUNT)
            .takenOn(DEFAULT_TAKEN_ON)
            .monthlyPayable(DEFAULT_MONTHLY_PAYABLE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .left(DEFAULT_LEFT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return loan;
    }

    @Before
    public void initTest() {
        loan = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoan() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan
        LoanDTO loanDTO = loanMapper.toDto(loan);
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanDTO)))
            .andExpect(status().isCreated());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate + 1);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testLoan.getTakenOn()).isEqualTo(DEFAULT_TAKEN_ON);
        assertThat(testLoan.getMonthlyPayable()).isEqualTo(DEFAULT_MONTHLY_PAYABLE);
        assertThat(testLoan.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testLoan.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testLoan.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testLoan.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).save(testLoan);
    }

    @Test
    @Transactional
    public void createLoanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan with an existing ID
        loan.setId(1L);
        LoanDTO loanDTO = loanMapper.toDto(loan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(0)).save(loan);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanRepository.findAll().size();
        // set the field null
        loan.setAmount(null);

        // Create the Loan, which fails.
        LoanDTO loanDTO = loanMapper.toDto(loan);

        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanDTO)))
            .andExpect(status().isBadRequest());

        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].takenOn").value(hasItem(DEFAULT_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loan.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.takenOn").value(DEFAULT_TAKEN_ON.toString()))
            .andExpect(jsonPath("$.monthlyPayable").value(DEFAULT_MONTHLY_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllLoansByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where amount equals to DEFAULT_AMOUNT
        defaultLoanShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the loanList where amount equals to UPDATED_AMOUNT
        defaultLoanShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLoansByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultLoanShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the loanList where amount equals to UPDATED_AMOUNT
        defaultLoanShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLoansByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where amount is not null
        defaultLoanShouldBeFound("amount.specified=true");

        // Get all the loanList where amount is null
        defaultLoanShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByTakenOnIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where takenOn equals to DEFAULT_TAKEN_ON
        defaultLoanShouldBeFound("takenOn.equals=" + DEFAULT_TAKEN_ON);

        // Get all the loanList where takenOn equals to UPDATED_TAKEN_ON
        defaultLoanShouldNotBeFound("takenOn.equals=" + UPDATED_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLoansByTakenOnIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where takenOn in DEFAULT_TAKEN_ON or UPDATED_TAKEN_ON
        defaultLoanShouldBeFound("takenOn.in=" + DEFAULT_TAKEN_ON + "," + UPDATED_TAKEN_ON);

        // Get all the loanList where takenOn equals to UPDATED_TAKEN_ON
        defaultLoanShouldNotBeFound("takenOn.in=" + UPDATED_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLoansByTakenOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where takenOn is not null
        defaultLoanShouldBeFound("takenOn.specified=true");

        // Get all the loanList where takenOn is null
        defaultLoanShouldNotBeFound("takenOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByTakenOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where takenOn greater than or equals to DEFAULT_TAKEN_ON
        defaultLoanShouldBeFound("takenOn.greaterOrEqualThan=" + DEFAULT_TAKEN_ON);

        // Get all the loanList where takenOn greater than or equals to UPDATED_TAKEN_ON
        defaultLoanShouldNotBeFound("takenOn.greaterOrEqualThan=" + UPDATED_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLoansByTakenOnIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where takenOn less than or equals to DEFAULT_TAKEN_ON
        defaultLoanShouldNotBeFound("takenOn.lessThan=" + DEFAULT_TAKEN_ON);

        // Get all the loanList where takenOn less than or equals to UPDATED_TAKEN_ON
        defaultLoanShouldBeFound("takenOn.lessThan=" + UPDATED_TAKEN_ON);
    }


    @Test
    @Transactional
    public void getAllLoansByMonthlyPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where monthlyPayable equals to DEFAULT_MONTHLY_PAYABLE
        defaultLoanShouldBeFound("monthlyPayable.equals=" + DEFAULT_MONTHLY_PAYABLE);

        // Get all the loanList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultLoanShouldNotBeFound("monthlyPayable.equals=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllLoansByMonthlyPayableIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where monthlyPayable in DEFAULT_MONTHLY_PAYABLE or UPDATED_MONTHLY_PAYABLE
        defaultLoanShouldBeFound("monthlyPayable.in=" + DEFAULT_MONTHLY_PAYABLE + "," + UPDATED_MONTHLY_PAYABLE);

        // Get all the loanList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultLoanShouldNotBeFound("monthlyPayable.in=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllLoansByMonthlyPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where monthlyPayable is not null
        defaultLoanShouldBeFound("monthlyPayable.specified=true");

        // Get all the loanList where monthlyPayable is null
        defaultLoanShouldNotBeFound("monthlyPayable.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultLoanShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the loanList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultLoanShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllLoansByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultLoanShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the loanList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultLoanShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllLoansByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where paymentStatus is not null
        defaultLoanShouldBeFound("paymentStatus.specified=true");

        // Get all the loanList where paymentStatus is null
        defaultLoanShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByLeftIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where left equals to DEFAULT_LEFT
        defaultLoanShouldBeFound("left.equals=" + DEFAULT_LEFT);

        // Get all the loanList where left equals to UPDATED_LEFT
        defaultLoanShouldNotBeFound("left.equals=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllLoansByLeftIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where left in DEFAULT_LEFT or UPDATED_LEFT
        defaultLoanShouldBeFound("left.in=" + DEFAULT_LEFT + "," + UPDATED_LEFT);

        // Get all the loanList where left equals to UPDATED_LEFT
        defaultLoanShouldNotBeFound("left.in=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllLoansByLeftIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where left is not null
        defaultLoanShouldBeFound("left.specified=true");

        // Get all the loanList where left is null
        defaultLoanShouldNotBeFound("left.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultLoanShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the loanList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLoanShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultLoanShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the loanList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultLoanShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedBy is not null
        defaultLoanShouldBeFound("modifiedBy.specified=true");

        // Get all the loanList where modifiedBy is null
        defaultLoanShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultLoanShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the loanList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultLoanShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultLoanShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the loanList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultLoanShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedDate is not null
        defaultLoanShouldBeFound("modifiedDate.specified=true");

        // Get all the loanList where modifiedDate is null
        defaultLoanShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedDate greater than or equals to DEFAULT_MODIFIED_DATE
        defaultLoanShouldBeFound("modifiedDate.greaterOrEqualThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the loanList where modifiedDate greater than or equals to UPDATED_MODIFIED_DATE
        defaultLoanShouldNotBeFound("modifiedDate.greaterOrEqualThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllLoansByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList where modifiedDate less than or equals to DEFAULT_MODIFIED_DATE
        defaultLoanShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the loanList where modifiedDate less than or equals to UPDATED_MODIFIED_DATE
        defaultLoanShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllLoansByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        loan.setEmployee(employee);
        loanRepository.saveAndFlush(loan);
        Long employeeId = employee.getId();

        // Get all the loanList where employee equals to employeeId
        defaultLoanShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the loanList where employee equals to employeeId + 1
        defaultLoanShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLoanShouldBeFound(String filter) throws Exception {
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].takenOn").value(hasItem(DEFAULT_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restLoanMockMvc.perform(get("/api/loans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLoanShouldNotBeFound(String filter) throws Exception {
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanMockMvc.perform(get("/api/loans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLoan() throws Exception {
        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Update the loan
        Loan updatedLoan = loanRepository.findById(loan.getId()).get();
        // Disconnect from session so that the updates on updatedLoan are not directly saved in db
        em.detach(updatedLoan);
        updatedLoan
            .amount(UPDATED_AMOUNT)
            .takenOn(UPDATED_TAKEN_ON)
            .monthlyPayable(UPDATED_MONTHLY_PAYABLE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .left(UPDATED_LEFT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        LoanDTO loanDTO = loanMapper.toDto(updatedLoan);

        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanDTO)))
            .andExpect(status().isOk());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testLoan.getTakenOn()).isEqualTo(UPDATED_TAKEN_ON);
        assertThat(testLoan.getMonthlyPayable()).isEqualTo(UPDATED_MONTHLY_PAYABLE);
        assertThat(testLoan.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testLoan.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testLoan.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testLoan.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).save(testLoan);
    }

    @Test
    @Transactional
    public void updateNonExistingLoan() throws Exception {
        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Create the Loan
        LoanDTO loanDTO = loanMapper.toDto(loan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(0)).save(loan);
    }

    @Test
    @Transactional
    public void deleteLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeDelete = loanRepository.findAll().size();

        // Delete the loan
        restLoanMockMvc.perform(delete("/api/loans/{id}", loan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Loan in Elasticsearch
        verify(mockLoanSearchRepository, times(1)).deleteById(loan.getId());
    }

    @Test
    @Transactional
    public void searchLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);
        when(mockLoanSearchRepository.search(queryStringQuery("id:" + loan.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(loan), PageRequest.of(0, 1), 1));
        // Search the loan
        restLoanMockMvc.perform(get("/api/_search/loans?query=id:" + loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].takenOn").value(hasItem(DEFAULT_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loan.class);
        Loan loan1 = new Loan();
        loan1.setId(1L);
        Loan loan2 = new Loan();
        loan2.setId(loan1.getId());
        assertThat(loan1).isEqualTo(loan2);
        loan2.setId(2L);
        assertThat(loan1).isNotEqualTo(loan2);
        loan1.setId(null);
        assertThat(loan1).isNotEqualTo(loan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDTO.class);
        LoanDTO loanDTO1 = new LoanDTO();
        loanDTO1.setId(1L);
        LoanDTO loanDTO2 = new LoanDTO();
        assertThat(loanDTO1).isNotEqualTo(loanDTO2);
        loanDTO2.setId(loanDTO1.getId());
        assertThat(loanDTO1).isEqualTo(loanDTO2);
        loanDTO2.setId(2L);
        assertThat(loanDTO1).isNotEqualTo(loanDTO2);
        loanDTO1.setId(null);
        assertThat(loanDTO1).isNotEqualTo(loanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loanMapper.fromId(null)).isNull();
    }
}
