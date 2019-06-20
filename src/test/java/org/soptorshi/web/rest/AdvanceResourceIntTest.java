package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Advance;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AdvanceRepository;
import org.soptorshi.repository.search.AdvanceSearchRepository;
import org.soptorshi.service.AdvanceService;
import org.soptorshi.service.dto.AdvanceDTO;
import org.soptorshi.service.mapper.AdvanceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.AdvanceCriteria;
import org.soptorshi.service.AdvanceQueryService;

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
 * Test class for the AdvanceResource REST controller.
 *
 * @see AdvanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AdvanceResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROVIDED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROVIDED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTHLY_PAYABLE = 1D;
    private static final Double UPDATED_MONTHLY_PAYABLE = 2D;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PAID;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.NOT_PAID;

    private static final BigDecimal DEFAULT_LEFT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LEFT = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AdvanceRepository advanceRepository;

    @Autowired
    private AdvanceMapper advanceMapper;

    @Autowired
    private AdvanceService advanceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AdvanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdvanceSearchRepository mockAdvanceSearchRepository;

    @Autowired
    private AdvanceQueryService advanceQueryService;

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

    private MockMvc restAdvanceMockMvc;

    private Advance advance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdvanceResource advanceResource = new AdvanceResource(advanceService, advanceQueryService);
        this.restAdvanceMockMvc = MockMvcBuilders.standaloneSetup(advanceResource)
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
    public static Advance createEntity(EntityManager em) {
        Advance advance = new Advance()
            .amount(DEFAULT_AMOUNT)
            .reason(DEFAULT_REASON)
            .providedOn(DEFAULT_PROVIDED_ON)
            .monthlyPayable(DEFAULT_MONTHLY_PAYABLE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .left(DEFAULT_LEFT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return advance;
    }

    @Before
    public void initTest() {
        advance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvance() throws Exception {
        int databaseSizeBeforeCreate = advanceRepository.findAll().size();

        // Create the Advance
        AdvanceDTO advanceDTO = advanceMapper.toDto(advance);
        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeCreate + 1);
        Advance testAdvance = advanceList.get(advanceList.size() - 1);
        assertThat(testAdvance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAdvance.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testAdvance.getProvidedOn()).isEqualTo(DEFAULT_PROVIDED_ON);
        assertThat(testAdvance.getMonthlyPayable()).isEqualTo(DEFAULT_MONTHLY_PAYABLE);
        assertThat(testAdvance.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testAdvance.getLeft()).isEqualTo(DEFAULT_LEFT);
        assertThat(testAdvance.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAdvance.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Advance in Elasticsearch
        verify(mockAdvanceSearchRepository, times(1)).save(testAdvance);
    }

    @Test
    @Transactional
    public void createAdvanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advanceRepository.findAll().size();

        // Create the Advance with an existing ID
        advance.setId(1L);
        AdvanceDTO advanceDTO = advanceMapper.toDto(advance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvanceMockMvc.perform(post("/api/advances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Advance in Elasticsearch
        verify(mockAdvanceSearchRepository, times(0)).save(advance);
    }

    @Test
    @Transactional
    public void getAllAdvances() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advance.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].providedOn").value(hasItem(DEFAULT_PROVIDED_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getAdvance() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get the advance
        restAdvanceMockMvc.perform(get("/api/advances/{id}", advance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(advance.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.providedOn").value(DEFAULT_PROVIDED_ON.toString()))
            .andExpect(jsonPath("$.monthlyPayable").value(DEFAULT_MONTHLY_PAYABLE.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.left").value(DEFAULT_LEFT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount equals to DEFAULT_AMOUNT
        defaultAdvanceShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the advanceList where amount equals to UPDATED_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultAdvanceShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the advanceList where amount equals to UPDATED_AMOUNT
        defaultAdvanceShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where amount is not null
        defaultAdvanceShouldBeFound("amount.specified=true");

        // Get all the advanceList where amount is null
        defaultAdvanceShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn equals to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.equals=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.equals=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn in DEFAULT_PROVIDED_ON or UPDATED_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.in=" + DEFAULT_PROVIDED_ON + "," + UPDATED_PROVIDED_ON);

        // Get all the advanceList where providedOn equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.in=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn is not null
        defaultAdvanceShouldBeFound("providedOn.specified=true");

        // Get all the advanceList where providedOn is null
        defaultAdvanceShouldNotBeFound("providedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn greater than or equals to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.greaterOrEqualThan=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn greater than or equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.greaterOrEqualThan=" + UPDATED_PROVIDED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByProvidedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where providedOn less than or equals to DEFAULT_PROVIDED_ON
        defaultAdvanceShouldNotBeFound("providedOn.lessThan=" + DEFAULT_PROVIDED_ON);

        // Get all the advanceList where providedOn less than or equals to UPDATED_PROVIDED_ON
        defaultAdvanceShouldBeFound("providedOn.lessThan=" + UPDATED_PROVIDED_ON);
    }


    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPayable equals to DEFAULT_MONTHLY_PAYABLE
        defaultAdvanceShouldBeFound("monthlyPayable.equals=" + DEFAULT_MONTHLY_PAYABLE);

        // Get all the advanceList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultAdvanceShouldNotBeFound("monthlyPayable.equals=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPayableIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPayable in DEFAULT_MONTHLY_PAYABLE or UPDATED_MONTHLY_PAYABLE
        defaultAdvanceShouldBeFound("monthlyPayable.in=" + DEFAULT_MONTHLY_PAYABLE + "," + UPDATED_MONTHLY_PAYABLE);

        // Get all the advanceList where monthlyPayable equals to UPDATED_MONTHLY_PAYABLE
        defaultAdvanceShouldNotBeFound("monthlyPayable.in=" + UPDATED_MONTHLY_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllAdvancesByMonthlyPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where monthlyPayable is not null
        defaultAdvanceShouldBeFound("monthlyPayable.specified=true");

        // Get all the advanceList where monthlyPayable is null
        defaultAdvanceShouldNotBeFound("monthlyPayable.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultAdvanceShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the advanceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the advanceList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAdvanceShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllAdvancesByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where paymentStatus is not null
        defaultAdvanceShouldBeFound("paymentStatus.specified=true");

        // Get all the advanceList where paymentStatus is null
        defaultAdvanceShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByLeftIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where left equals to DEFAULT_LEFT
        defaultAdvanceShouldBeFound("left.equals=" + DEFAULT_LEFT);

        // Get all the advanceList where left equals to UPDATED_LEFT
        defaultAdvanceShouldNotBeFound("left.equals=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByLeftIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where left in DEFAULT_LEFT or UPDATED_LEFT
        defaultAdvanceShouldBeFound("left.in=" + DEFAULT_LEFT + "," + UPDATED_LEFT);

        // Get all the advanceList where left equals to UPDATED_LEFT
        defaultAdvanceShouldNotBeFound("left.in=" + UPDATED_LEFT);
    }

    @Test
    @Transactional
    public void getAllAdvancesByLeftIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where left is not null
        defaultAdvanceShouldBeFound("left.specified=true");

        // Get all the advanceList where left is null
        defaultAdvanceShouldNotBeFound("left.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultAdvanceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the advanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAdvanceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultAdvanceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the advanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultAdvanceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedBy is not null
        defaultAdvanceShouldBeFound("modifiedBy.specified=true");

        // Get all the advanceList where modifiedBy is null
        defaultAdvanceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultAdvanceShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the advanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultAdvanceShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultAdvanceShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the advanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultAdvanceShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedOn is not null
        defaultAdvanceShouldBeFound("modifiedOn.specified=true");

        // Get all the advanceList where modifiedOn is null
        defaultAdvanceShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultAdvanceShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the advanceList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultAdvanceShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllAdvancesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        // Get all the advanceList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultAdvanceShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the advanceList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultAdvanceShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllAdvancesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        advance.setEmployee(employee);
        advanceRepository.saveAndFlush(advance);
        Long employeeId = employee.getId();

        // Get all the advanceList where employee equals to employeeId
        defaultAdvanceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the advanceList where employee equals to employeeId + 1
        defaultAdvanceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAdvanceShouldBeFound(String filter) throws Exception {
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advance.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].providedOn").value(hasItem(DEFAULT_PROVIDED_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restAdvanceMockMvc.perform(get("/api/advances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAdvanceShouldNotBeFound(String filter) throws Exception {
        restAdvanceMockMvc.perform(get("/api/advances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdvanceMockMvc.perform(get("/api/advances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdvance() throws Exception {
        // Get the advance
        restAdvanceMockMvc.perform(get("/api/advances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvance() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        int databaseSizeBeforeUpdate = advanceRepository.findAll().size();

        // Update the advance
        Advance updatedAdvance = advanceRepository.findById(advance.getId()).get();
        // Disconnect from session so that the updates on updatedAdvance are not directly saved in db
        em.detach(updatedAdvance);
        updatedAdvance
            .amount(UPDATED_AMOUNT)
            .reason(UPDATED_REASON)
            .providedOn(UPDATED_PROVIDED_ON)
            .monthlyPayable(UPDATED_MONTHLY_PAYABLE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .left(UPDATED_LEFT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        AdvanceDTO advanceDTO = advanceMapper.toDto(updatedAdvance);

        restAdvanceMockMvc.perform(put("/api/advances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceDTO)))
            .andExpect(status().isOk());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeUpdate);
        Advance testAdvance = advanceList.get(advanceList.size() - 1);
        assertThat(testAdvance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAdvance.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testAdvance.getProvidedOn()).isEqualTo(UPDATED_PROVIDED_ON);
        assertThat(testAdvance.getMonthlyPayable()).isEqualTo(UPDATED_MONTHLY_PAYABLE);
        assertThat(testAdvance.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testAdvance.getLeft()).isEqualTo(UPDATED_LEFT);
        assertThat(testAdvance.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAdvance.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Advance in Elasticsearch
        verify(mockAdvanceSearchRepository, times(1)).save(testAdvance);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvance() throws Exception {
        int databaseSizeBeforeUpdate = advanceRepository.findAll().size();

        // Create the Advance
        AdvanceDTO advanceDTO = advanceMapper.toDto(advance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvanceMockMvc.perform(put("/api/advances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Advance in the database
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Advance in Elasticsearch
        verify(mockAdvanceSearchRepository, times(0)).save(advance);
    }

    @Test
    @Transactional
    public void deleteAdvance() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);

        int databaseSizeBeforeDelete = advanceRepository.findAll().size();

        // Delete the advance
        restAdvanceMockMvc.perform(delete("/api/advances/{id}", advance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Advance> advanceList = advanceRepository.findAll();
        assertThat(advanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Advance in Elasticsearch
        verify(mockAdvanceSearchRepository, times(1)).deleteById(advance.getId());
    }

    @Test
    @Transactional
    public void searchAdvance() throws Exception {
        // Initialize the database
        advanceRepository.saveAndFlush(advance);
        when(mockAdvanceSearchRepository.search(queryStringQuery("id:" + advance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(advance), PageRequest.of(0, 1), 1));
        // Search the advance
        restAdvanceMockMvc.perform(get("/api/_search/advances?query=id:" + advance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advance.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].providedOn").value(hasItem(DEFAULT_PROVIDED_ON.toString())))
            .andExpect(jsonPath("$.[*].monthlyPayable").value(hasItem(DEFAULT_MONTHLY_PAYABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].left").value(hasItem(DEFAULT_LEFT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Advance.class);
        Advance advance1 = new Advance();
        advance1.setId(1L);
        Advance advance2 = new Advance();
        advance2.setId(advance1.getId());
        assertThat(advance1).isEqualTo(advance2);
        advance2.setId(2L);
        assertThat(advance1).isNotEqualTo(advance2);
        advance1.setId(null);
        assertThat(advance1).isNotEqualTo(advance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvanceDTO.class);
        AdvanceDTO advanceDTO1 = new AdvanceDTO();
        advanceDTO1.setId(1L);
        AdvanceDTO advanceDTO2 = new AdvanceDTO();
        assertThat(advanceDTO1).isNotEqualTo(advanceDTO2);
        advanceDTO2.setId(advanceDTO1.getId());
        assertThat(advanceDTO1).isEqualTo(advanceDTO2);
        advanceDTO2.setId(2L);
        assertThat(advanceDTO1).isNotEqualTo(advanceDTO2);
        advanceDTO1.setId(null);
        assertThat(advanceDTO1).isNotEqualTo(advanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(advanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(advanceMapper.fromId(null)).isNull();
    }
}
