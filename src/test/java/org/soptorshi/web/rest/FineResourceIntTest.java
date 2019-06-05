package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Fine;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.FineRepository;
import org.soptorshi.repository.search.FineSearchRepository;
import org.soptorshi.service.FineService;
import org.soptorshi.service.dto.FineDTO;
import org.soptorshi.service.mapper.FineMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.FineCriteria;
import org.soptorshi.service.FineQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Test class for the FineResource REST controller.
 *
 * @see FineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class FineResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_FINE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINE_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private FineMapper fineMapper;

    @Autowired
    private FineService fineService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.FineSearchRepositoryMockConfiguration
     */
    @Autowired
    private FineSearchRepository mockFineSearchRepository;

    @Autowired
    private FineQueryService fineQueryService;

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

    private MockMvc restFineMockMvc;

    private Fine fine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FineResource fineResource = new FineResource(fineService, fineQueryService);
        this.restFineMockMvc = MockMvcBuilders.standaloneSetup(fineResource)
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
    public static Fine createEntity(EntityManager em) {
        Fine fine = new Fine()
            .amount(DEFAULT_AMOUNT)
            .fineDate(DEFAULT_FINE_DATE)
            .monthlyPayable(DEFAULT_MONTHLY_PAYABLE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .left(DEFAULT_LEFT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .reason(DEFAULT_REASON);
        return fine;
    }

    @Before
    public void initTest() {
        fine = createEntity(em);
    }

    @Test
    @Transactional
    public void createFine() throws Exception {
        int databaseSizeBeforeCreate = fineRepository.findAll().size();

        // Create the Fine
        FineDTO fineDTO = fineMapper.toDto(fine);
        restFineMockMvc.perform(post("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isCreated());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeCreate + 1);
        Fine testFine = fineList.get(fineList.size() - 1);
        assertThat(testFine.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFine.getFineDate()).isEqualTo(DEFAULT_FINE_DATE);
        assertThat(testFine.getMonthlyPayable()).isEqualTo(DEFAULT_MONTHLY_PAYABLE);
        assertThat(testFine.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testFine.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testFine.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFine.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testFine.getReason()).isEqualTo(DEFAULT_REASON);

        // Validate the Fine in Elasticsearch
        verify(mockFineSearchRepository, times(1)).save(testFine);
    }

    @Test
    @Transactional
    public void createFineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fineRepository.findAll().size();

        // Create the Fine with an existing ID
        fine.setId(1L);
        FineDTO fineDTO = fineMapper.toDto(fine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFineMockMvc.perform(post("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fine in Elasticsearch
        verify(mockFineSearchRepository, times(0)).save(fine);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = fineRepository.findAll().size();
        // set the field null
        fine.setAmount(null);

        // Create the Fine, which fails.
        FineDTO fineDTO = fineMapper.toDto(fine);

        restFineMockMvc.perform(post("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isBadRequest());

        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFineDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fineRepository.findAll().size();
        // set the field null
        fine.setFineDate(null);

        // Create the Fine, which fails.
        FineDTO fineDTO = fineMapper.toDto(fine);

        restFineMockMvc.perform(post("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isBadRequest());

        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFines() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList
        restFineMockMvc.perform(get("/api/fines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fine.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].fineDate").value(hasItem(DEFAULT_FINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getFine() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get the fine
        restFineMockMvc.perform(get("/api/fines/{id}", fine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fine.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.fineDate").value(DEFAULT_FINE_DATE.toString()))
            .andExpect(jsonPath("$.monthlyPayable").value(DEFAULT_MONTHLY_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount equals to DEFAULT_AMOUNT
        defaultFineShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the fineList where amount equals to UPDATED_AMOUNT
        defaultFineShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFineShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the fineList where amount equals to UPDATED_AMOUNT
        defaultFineShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFinesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where amount is not null
        defaultFineShouldBeFound("amount.specified=true");

        // Get all the fineList where amount is null
        defaultFineShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByFineDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where fineDate equals to DEFAULT_FINE_DATE
        defaultFineShouldBeFound("fineDate.equals=" + DEFAULT_FINE_DATE);

        // Get all the fineList where fineDate equals to UPDATED_FINE_DATE
        defaultFineShouldNotBeFound("fineDate.equals=" + UPDATED_FINE_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByFineDateIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where fineDate in DEFAULT_FINE_DATE or UPDATED_FINE_DATE
        defaultFineShouldBeFound("fineDate.in=" + DEFAULT_FINE_DATE + "," + UPDATED_FINE_DATE);

        // Get all the fineList where fineDate equals to UPDATED_FINE_DATE
        defaultFineShouldNotBeFound("fineDate.in=" + UPDATED_FINE_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByFineDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where fineDate is not null
        defaultFineShouldBeFound("fineDate.specified=true");

        // Get all the fineList where fineDate is null
        defaultFineShouldNotBeFound("fineDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByFineDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where fineDate greater than or equals to DEFAULT_FINE_DATE
        defaultFineShouldBeFound("fineDate.greaterOrEqualThan=" + DEFAULT_FINE_DATE);

        // Get all the fineList where fineDate greater than or equals to UPDATED_FINE_DATE
        defaultFineShouldNotBeFound("fineDate.greaterOrEqualThan=" + UPDATED_FINE_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByFineDateIsLessThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where fineDate less than or equals to DEFAULT_FINE_DATE
        defaultFineShouldNotBeFound("fineDate.lessThan=" + DEFAULT_FINE_DATE);

        // Get all the fineList where fineDate less than or equals to UPDATED_FINE_DATE
        defaultFineShouldBeFound("fineDate.lessThan=" + UPDATED_FINE_DATE);
    }


    @Test
    @Transactional
    public void getAllFinesByMonthlyPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where monthlyPayable equals to DEFAULT_MONTHLY_PAYABLE
        defaultFineShouldBeFound("monthlyPayable.equals=" + DEFAULT_MONTHLY_PAYABLE);

        // Get all the fineList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultFineShouldNotBeFound("monthlyPayable.equals=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllFinesByMonthlyPayableIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where monthlyPayable in DEFAULT_MONTHLY_PAYABLE or UPDATED_MONTHLY_PAYABLE
        defaultFineShouldBeFound("monthlyPayable.in=" + DEFAULT_MONTHLY_PAYABLE + "," + UPDATED_MONTHLY_PAYABLE);

        // Get all the fineList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultFineShouldNotBeFound("monthlyPayable.in=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllFinesByMonthlyPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where monthlyPayable is not null
        defaultFineShouldBeFound("monthlyPayable.specified=true");

        // Get all the fineList where monthlyPayable is null
        defaultFineShouldNotBeFound("monthlyPayable.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultFineShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the fineList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultFineShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultFineShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the fineList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultFineShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where paymentStatus is not null
        defaultFineShouldBeFound("paymentStatus.specified=true");

        // Get all the fineList where paymentStatus is null
        defaultFineShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByLeftIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where left equals to DEFAULT_LEFT
        defaultFineShouldBeFound("left.equals=" + DEFAULT_LEFT);

        // Get all the fineList where left equals to UPDATED_LEFT
        defaultFineShouldNotBeFound("left.equals=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllFinesByLeftIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where left in DEFAULT_LEFT or UPDATED_LEFT
        defaultFineShouldBeFound("left.in=" + DEFAULT_LEFT + "," + UPDATED_LEFT);

        // Get all the fineList where left equals to UPDATED_LEFT
        defaultFineShouldNotBeFound("left.in=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllFinesByLeftIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where left is not null
        defaultFineShouldBeFound("left.specified=true");

        // Get all the fineList where left is null
        defaultFineShouldNotBeFound("left.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultFineShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the fineList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultFineShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultFineShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the fineList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultFineShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedBy is not null
        defaultFineShouldBeFound("modifiedBy.specified=true");

        // Get all the fineList where modifiedBy is null
        defaultFineShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultFineShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the fineList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultFineShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultFineShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the fineList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultFineShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedDate is not null
        defaultFineShouldBeFound("modifiedDate.specified=true");

        // Get all the fineList where modifiedDate is null
        defaultFineShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedDate greater than or equals to DEFAULT_MODIFIED_DATE
        defaultFineShouldBeFound("modifiedDate.greaterOrEqualThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the fineList where modifiedDate greater than or equals to UPDATED_MODIFIED_DATE
        defaultFineShouldNotBeFound("modifiedDate.greaterOrEqualThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllFinesByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        // Get all the fineList where modifiedDate less than or equals to DEFAULT_MODIFIED_DATE
        defaultFineShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the fineList where modifiedDate less than or equals to UPDATED_MODIFIED_DATE
        defaultFineShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllFinesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        fine.setEmployee(employee);
        fineRepository.saveAndFlush(fine);
        Long employeeId = employee.getId();

        // Get all the fineList where employee equals to employeeId
        defaultFineShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the fineList where employee equals to employeeId + 1
        defaultFineShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFineShouldBeFound(String filter) throws Exception {
        restFineMockMvc.perform(get("/api/fines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fine.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].fineDate").value(hasItem(DEFAULT_FINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));

        // Check, that the count call also returns 1
        restFineMockMvc.perform(get("/api/fines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFineShouldNotBeFound(String filter) throws Exception {
        restFineMockMvc.perform(get("/api/fines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFineMockMvc.perform(get("/api/fines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFine() throws Exception {
        // Get the fine
        restFineMockMvc.perform(get("/api/fines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFine() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        int databaseSizeBeforeUpdate = fineRepository.findAll().size();

        // Update the fine
        Fine updatedFine = fineRepository.findById(fine.getId()).get();
        // Disconnect from session so that the updates on updatedFine are not directly saved in db
        em.detach(updatedFine);
        updatedFine
            .amount(UPDATED_AMOUNT)
            .fineDate(UPDATED_FINE_DATE)
            .monthlyPayable(UPDATED_MONTHLY_PAYABLE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .left(UPDATED_LEFT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .reason(UPDATED_REASON);
        FineDTO fineDTO = fineMapper.toDto(updatedFine);

        restFineMockMvc.perform(put("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isOk());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeUpdate);
        Fine testFine = fineList.get(fineList.size() - 1);
        assertThat(testFine.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFine.getFineDate()).isEqualTo(UPDATED_FINE_DATE);
        assertThat(testFine.getMonthlyPayable()).isEqualTo(UPDATED_MONTHLY_PAYABLE);
        assertThat(testFine.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testFine.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testFine.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFine.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testFine.getReason()).isEqualTo(UPDATED_REASON);

        // Validate the Fine in Elasticsearch
        verify(mockFineSearchRepository, times(1)).save(testFine);
    }

    @Test
    @Transactional
    public void updateNonExistingFine() throws Exception {
        int databaseSizeBeforeUpdate = fineRepository.findAll().size();

        // Create the Fine
        FineDTO fineDTO = fineMapper.toDto(fine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFineMockMvc.perform(put("/api/fines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fine in the database
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fine in Elasticsearch
        verify(mockFineSearchRepository, times(0)).save(fine);
    }

    @Test
    @Transactional
    public void deleteFine() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);

        int databaseSizeBeforeDelete = fineRepository.findAll().size();

        // Delete the fine
        restFineMockMvc.perform(delete("/api/fines/{id}", fine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fine> fineList = fineRepository.findAll();
        assertThat(fineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fine in Elasticsearch
        verify(mockFineSearchRepository, times(1)).deleteById(fine.getId());
    }

    @Test
    @Transactional
    public void searchFine() throws Exception {
        // Initialize the database
        fineRepository.saveAndFlush(fine);
        when(mockFineSearchRepository.search(queryStringQuery("id:" + fine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fine), PageRequest.of(0, 1), 1));
        // Search the fine
        restFineMockMvc.perform(get("/api/_search/fines?query=id:" + fine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fine.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].fineDate").value(hasItem(DEFAULT_FINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fine.class);
        Fine fine1 = new Fine();
        fine1.setId(1L);
        Fine fine2 = new Fine();
        fine2.setId(fine1.getId());
        assertThat(fine1).isEqualTo(fine2);
        fine2.setId(2L);
        assertThat(fine1).isNotEqualTo(fine2);
        fine1.setId(null);
        assertThat(fine1).isNotEqualTo(fine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FineDTO.class);
        FineDTO fineDTO1 = new FineDTO();
        fineDTO1.setId(1L);
        FineDTO fineDTO2 = new FineDTO();
        assertThat(fineDTO1).isNotEqualTo(fineDTO2);
        fineDTO2.setId(fineDTO1.getId());
        assertThat(fineDTO1).isEqualTo(fineDTO2);
        fineDTO2.setId(2L);
        assertThat(fineDTO1).isNotEqualTo(fineDTO2);
        fineDTO1.setId(null);
        assertThat(fineDTO1).isNotEqualTo(fineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fineMapper.fromId(null)).isNull();
    }
}
