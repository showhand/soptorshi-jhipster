package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.soptorshi.repository.search.LeaveApplicationSearchRepository;
import org.soptorshi.service.dto.LeaveApplicationDTO;
import org.soptorshi.service.extended.LeaveApplicationServiceImplExtended;
import org.soptorshi.service.mapper.LeaveApplicationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.LeaveApplicationQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.soptorshi.domain.enumeration.LeaveStatus;
/**
 * Test class for the LeaveApplicationResource REST controller.
 *
 * @see LeaveApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class LeaveApplicationResourceIntTest {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_APPLIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPLIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPLIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTION_TAKEN_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TAKEN_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_TAKEN_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_TAKEN_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LeaveStatus DEFAULT_STATUS = LeaveStatus.WAITING;
    private static final LeaveStatus UPDATED_STATUS = LeaveStatus.ACCEPTED;

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;

    @Autowired
    private LeaveApplicationServiceImplExtended leaveApplicationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.LeaveApplicationSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeaveApplicationSearchRepository mockLeaveApplicationSearchRepository;

    @Autowired
    private LeaveApplicationQueryService leaveApplicationQueryService;

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

    private MockMvc restLeaveApplicationMockMvc;

    private LeaveApplication leaveApplication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveApplicationResource leaveApplicationResource = new LeaveApplicationResource(leaveApplicationService, leaveApplicationQueryService);
        this.restLeaveApplicationMockMvc = MockMvcBuilders.standaloneSetup(leaveApplicationResource)
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
    public static LeaveApplication createEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS)
            .reason(DEFAULT_REASON)
            .appliedBy(DEFAULT_APPLIED_BY)
            .appliedOn(DEFAULT_APPLIED_ON)
            .actionTakenBy(DEFAULT_ACTION_TAKEN_BY)
            .actionTakenOn(DEFAULT_ACTION_TAKEN_ON)
            .status(DEFAULT_STATUS);
        return leaveApplication;
    }

    @Before
    public void initTest() {
        leaveApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveApplication() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testLeaveApplication.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testLeaveApplication.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testLeaveApplication.getAppliedBy()).isEqualTo(DEFAULT_APPLIED_BY);
        assertThat(testLeaveApplication.getAppliedOn()).isEqualTo(DEFAULT_APPLIED_ON);
        assertThat(testLeaveApplication.getActionTakenBy()).isEqualTo(DEFAULT_ACTION_TAKEN_BY);
        assertThat(testLeaveApplication.getActionTakenOn()).isEqualTo(DEFAULT_ACTION_TAKEN_ON);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the LeaveApplication in Elasticsearch
        verify(mockLeaveApplicationSearchRepository, times(1)).save(testLeaveApplication);
    }

    @Test
    @Transactional
    public void createLeaveApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();

        // Create the LeaveApplication with an existing ID
        leaveApplication.setId(1L);
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeaveApplication in Elasticsearch
        verify(mockLeaveApplicationSearchRepository, times(0)).save(leaveApplication);
    }

    @Test
    @Transactional
    public void checkEmployeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setEmployeeId(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setFromDate(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setToDate(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setNumberOfDays(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setReason(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppliedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setAppliedBy(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTakenByIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setActionTakenBy(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setStatus(null);

        // Create the LeaveApplication, which fails.
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveApplications() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].appliedBy").value(hasItem(DEFAULT_APPLIED_BY.toString())))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].actionTakenBy").value(hasItem(DEFAULT_ACTION_TAKEN_BY.toString())))
            .andExpect(jsonPath("$.[*].actionTakenOn").value(hasItem(DEFAULT_ACTION_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", leaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApplication.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.appliedBy").value(DEFAULT_APPLIED_BY.toString()))
            .andExpect(jsonPath("$.appliedOn").value(DEFAULT_APPLIED_ON.toString()))
            .andExpect(jsonPath("$.actionTakenBy").value(DEFAULT_ACTION_TAKEN_BY.toString()))
            .andExpect(jsonPath("$.actionTakenOn").value(DEFAULT_ACTION_TAKEN_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultLeaveApplicationShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the leaveApplicationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultLeaveApplicationShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultLeaveApplicationShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the leaveApplicationList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultLeaveApplicationShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where employeeId is not null
        defaultLeaveApplicationShouldBeFound("employeeId.specified=true");

        // Get all the leaveApplicationList where employeeId is null
        defaultLeaveApplicationShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where fromDate equals to DEFAULT_FROM_DATE
        defaultLeaveApplicationShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the leaveApplicationList where fromDate equals to UPDATED_FROM_DATE
        defaultLeaveApplicationShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultLeaveApplicationShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the leaveApplicationList where fromDate equals to UPDATED_FROM_DATE
        defaultLeaveApplicationShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where fromDate is not null
        defaultLeaveApplicationShouldBeFound("fromDate.specified=true");

        // Get all the leaveApplicationList where fromDate is null
        defaultLeaveApplicationShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where fromDate greater than or equals to DEFAULT_FROM_DATE
        defaultLeaveApplicationShouldBeFound("fromDate.greaterOrEqualThan=" + DEFAULT_FROM_DATE);

        // Get all the leaveApplicationList where fromDate greater than or equals to UPDATED_FROM_DATE
        defaultLeaveApplicationShouldNotBeFound("fromDate.greaterOrEqualThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where fromDate less than or equals to DEFAULT_FROM_DATE
        defaultLeaveApplicationShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the leaveApplicationList where fromDate less than or equals to UPDATED_FROM_DATE
        defaultLeaveApplicationShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate equals to DEFAULT_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the leaveApplicationList where toDate equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the leaveApplicationList where toDate equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate is not null
        defaultLeaveApplicationShouldBeFound("toDate.specified=true");

        // Get all the leaveApplicationList where toDate is null
        defaultLeaveApplicationShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate greater than or equals to DEFAULT_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.greaterOrEqualThan=" + DEFAULT_TO_DATE);

        // Get all the leaveApplicationList where toDate greater than or equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.greaterOrEqualThan=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToDateIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where toDate less than or equals to DEFAULT_TO_DATE
        defaultLeaveApplicationShouldNotBeFound("toDate.lessThan=" + DEFAULT_TO_DATE);

        // Get all the leaveApplicationList where toDate less than or equals to UPDATED_TO_DATE
        defaultLeaveApplicationShouldBeFound("toDate.lessThan=" + UPDATED_TO_DATE);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByNumberOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where numberOfDays equals to DEFAULT_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldBeFound("numberOfDays.equals=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the leaveApplicationList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("numberOfDays.equals=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByNumberOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where numberOfDays in DEFAULT_NUMBER_OF_DAYS or UPDATED_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldBeFound("numberOfDays.in=" + DEFAULT_NUMBER_OF_DAYS + "," + UPDATED_NUMBER_OF_DAYS);

        // Get all the leaveApplicationList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("numberOfDays.in=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByNumberOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where numberOfDays is not null
        defaultLeaveApplicationShouldBeFound("numberOfDays.specified=true");

        // Get all the leaveApplicationList where numberOfDays is null
        defaultLeaveApplicationShouldNotBeFound("numberOfDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByNumberOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where numberOfDays greater than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldBeFound("numberOfDays.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the leaveApplicationList where numberOfDays greater than or equals to UPDATED_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("numberOfDays.greaterOrEqualThan=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByNumberOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where numberOfDays less than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldNotBeFound("numberOfDays.lessThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the leaveApplicationList where numberOfDays less than or equals to UPDATED_NUMBER_OF_DAYS
        defaultLeaveApplicationShouldBeFound("numberOfDays.lessThan=" + UPDATED_NUMBER_OF_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason equals to DEFAULT_REASON
        defaultLeaveApplicationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultLeaveApplicationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the leaveApplicationList where reason equals to UPDATED_REASON
        defaultLeaveApplicationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where reason is not null
        defaultLeaveApplicationShouldBeFound("reason.specified=true");

        // Get all the leaveApplicationList where reason is null
        defaultLeaveApplicationShouldNotBeFound("reason.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy equals to DEFAULT_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.equals=" + DEFAULT_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy equals to UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.equals=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy in DEFAULT_APPLIED_BY or UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.in=" + DEFAULT_APPLIED_BY + "," + UPDATED_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy equals to UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.in=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy is not null
        defaultLeaveApplicationShouldBeFound("appliedBy.specified=true");

        // Get all the leaveApplicationList where appliedBy is null
        defaultLeaveApplicationShouldNotBeFound("appliedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn equals to DEFAULT_APPLIED_ON
        defaultLeaveApplicationShouldBeFound("appliedOn.equals=" + DEFAULT_APPLIED_ON);

        // Get all the leaveApplicationList where appliedOn equals to UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldNotBeFound("appliedOn.equals=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn in DEFAULT_APPLIED_ON or UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldBeFound("appliedOn.in=" + DEFAULT_APPLIED_ON + "," + UPDATED_APPLIED_ON);

        // Get all the leaveApplicationList where appliedOn equals to UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldNotBeFound("appliedOn.in=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn is not null
        defaultLeaveApplicationShouldBeFound("appliedOn.specified=true");

        // Get all the leaveApplicationList where appliedOn is null
        defaultLeaveApplicationShouldNotBeFound("appliedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy equals to DEFAULT_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.equals=" + DEFAULT_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy equals to UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.equals=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy in DEFAULT_ACTION_TAKEN_BY or UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.in=" + DEFAULT_ACTION_TAKEN_BY + "," + UPDATED_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy equals to UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.in=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy is not null
        defaultLeaveApplicationShouldBeFound("actionTakenBy.specified=true");

        // Get all the leaveApplicationList where actionTakenBy is null
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn equals to DEFAULT_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldBeFound("actionTakenOn.equals=" + DEFAULT_ACTION_TAKEN_ON);

        // Get all the leaveApplicationList where actionTakenOn equals to UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.equals=" + UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn in DEFAULT_ACTION_TAKEN_ON or UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldBeFound("actionTakenOn.in=" + DEFAULT_ACTION_TAKEN_ON + "," + UPDATED_ACTION_TAKEN_ON);

        // Get all the leaveApplicationList where actionTakenOn equals to UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.in=" + UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn is not null
        defaultLeaveApplicationShouldBeFound("actionTakenOn.specified=true");

        // Get all the leaveApplicationList where actionTakenOn is null
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status is not null
        defaultLeaveApplicationShouldBeFound("status.specified=true");

        // Get all the leaveApplicationList where status is null
        defaultLeaveApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByLeaveTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        LeaveType leaveTypes = LeaveTypeResourceIntTest.createEntity(em);
        em.persist(leaveTypes);
        em.flush();
        leaveApplication.setLeaveTypes(leaveTypes);
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long leaveTypesId = leaveTypes.getId();

        // Get all the leaveApplicationList where leaveTypes equals to leaveTypesId
        defaultLeaveApplicationShouldBeFound("leaveTypesId.equals=" + leaveTypesId);

        // Get all the leaveApplicationList where leaveTypes equals to leaveTypesId + 1
        defaultLeaveApplicationShouldNotBeFound("leaveTypesId.equals=" + (leaveTypesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLeaveApplicationShouldBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].appliedBy").value(hasItem(DEFAULT_APPLIED_BY)))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].actionTakenBy").value(hasItem(DEFAULT_ACTION_TAKEN_BY)))
            .andExpect(jsonPath("$.[*].actionTakenOn").value(hasItem(DEFAULT_ACTION_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLeaveApplicationShouldNotBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLeaveApplication() throws Exception {
        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication
        LeaveApplication updatedLeaveApplication = leaveApplicationRepository.findById(leaveApplication.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveApplication are not directly saved in db
        em.detach(updatedLeaveApplication);
        updatedLeaveApplication
            .employeeId(UPDATED_EMPLOYEE_ID)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .reason(UPDATED_REASON)
            .appliedBy(UPDATED_APPLIED_BY)
            .appliedOn(UPDATED_APPLIED_ON)
            .actionTakenBy(UPDATED_ACTION_TAKEN_BY)
            .actionTakenOn(UPDATED_ACTION_TAKEN_ON)
            .status(UPDATED_STATUS);
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(updatedLeaveApplication);

        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testLeaveApplication.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testLeaveApplication.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testLeaveApplication.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveApplication.getAppliedBy()).isEqualTo(UPDATED_APPLIED_BY);
        assertThat(testLeaveApplication.getAppliedOn()).isEqualTo(UPDATED_APPLIED_ON);
        assertThat(testLeaveApplication.getActionTakenBy()).isEqualTo(UPDATED_ACTION_TAKEN_BY);
        assertThat(testLeaveApplication.getActionTakenOn()).isEqualTo(UPDATED_ACTION_TAKEN_ON);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the LeaveApplication in Elasticsearch
        verify(mockLeaveApplicationSearchRepository, times(1)).save(testLeaveApplication);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Create the LeaveApplication
        LeaveApplicationDTO leaveApplicationDTO = leaveApplicationMapper.toDto(leaveApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeaveApplication in Elasticsearch
        verify(mockLeaveApplicationSearchRepository, times(0)).save(leaveApplication);
    }

    @Test
    @Transactional
    public void deleteLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        int databaseSizeBeforeDelete = leaveApplicationRepository.findAll().size();

        // Delete the leaveApplication
        restLeaveApplicationMockMvc.perform(delete("/api/leave-applications/{id}", leaveApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeaveApplication in Elasticsearch
        verify(mockLeaveApplicationSearchRepository, times(1)).deleteById(leaveApplication.getId());
    }

    @Test
    @Transactional
    public void searchLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        when(mockLeaveApplicationSearchRepository.search(queryStringQuery("id:" + leaveApplication.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leaveApplication), PageRequest.of(0, 1), 1));
        // Search the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/_search/leave-applications?query=id:" + leaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].appliedBy").value(hasItem(DEFAULT_APPLIED_BY)))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].actionTakenBy").value(hasItem(DEFAULT_ACTION_TAKEN_BY)))
            .andExpect(jsonPath("$.[*].actionTakenOn").value(hasItem(DEFAULT_ACTION_TAKEN_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveApplication.class);
        LeaveApplication leaveApplication1 = new LeaveApplication();
        leaveApplication1.setId(1L);
        LeaveApplication leaveApplication2 = new LeaveApplication();
        leaveApplication2.setId(leaveApplication1.getId());
        assertThat(leaveApplication1).isEqualTo(leaveApplication2);
        leaveApplication2.setId(2L);
        assertThat(leaveApplication1).isNotEqualTo(leaveApplication2);
        leaveApplication1.setId(null);
        assertThat(leaveApplication1).isNotEqualTo(leaveApplication2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveApplicationDTO.class);
        LeaveApplicationDTO leaveApplicationDTO1 = new LeaveApplicationDTO();
        leaveApplicationDTO1.setId(1L);
        LeaveApplicationDTO leaveApplicationDTO2 = new LeaveApplicationDTO();
        assertThat(leaveApplicationDTO1).isNotEqualTo(leaveApplicationDTO2);
        leaveApplicationDTO2.setId(leaveApplicationDTO1.getId());
        assertThat(leaveApplicationDTO1).isEqualTo(leaveApplicationDTO2);
        leaveApplicationDTO2.setId(2L);
        assertThat(leaveApplicationDTO1).isNotEqualTo(leaveApplicationDTO2);
        leaveApplicationDTO1.setId(null);
        assertThat(leaveApplicationDTO1).isNotEqualTo(leaveApplicationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaveApplicationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaveApplicationMapper.fromId(null)).isNull();
    }
}
