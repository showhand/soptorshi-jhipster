package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.OverTime;
import org.soptorshi.repository.OverTimeRepository;
import org.soptorshi.repository.search.OverTimeSearchRepository;
import org.soptorshi.service.OverTimeQueryService;
import org.soptorshi.service.OverTimeService;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.mapper.OverTimeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OverTimeResource REST controller.
 *
 * @see OverTimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class OverTimeResourceIntTest {

    private static final LocalDate DEFAULT_OVER_TIME_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OVER_TIME_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_FROM_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OverTimeRepository overTimeRepository;

    @Autowired
    private OverTimeMapper overTimeMapper;

    @Autowired
    private OverTimeService overTimeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.OverTimeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OverTimeSearchRepository mockOverTimeSearchRepository;

    @Autowired
    private OverTimeQueryService overTimeQueryService;

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

    private MockMvc restOverTimeMockMvc;

    private OverTime overTime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OverTimeResource overTimeResource = new OverTimeResource(overTimeService, overTimeQueryService);
        this.restOverTimeMockMvc = MockMvcBuilders.standaloneSetup(overTimeResource)
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
    public static OverTime createEntity(EntityManager em) {
        OverTime overTime = new OverTime()
            .overTimeDate(DEFAULT_OVER_TIME_DATE)
            .fromTime(DEFAULT_FROM_TIME)
            .toTime(DEFAULT_TO_TIME)
            .duration(DEFAULT_DURATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        overTime.setEmployee(employee);
        return overTime;
    }

    @Before
    public void initTest() {
        overTime = createEntity(em);
    }

    @Test
    @Transactional
    public void createOverTime() throws Exception {
        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);
        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate + 1);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getOverTimeDate()).isEqualTo(DEFAULT_OVER_TIME_DATE);
        assertThat(testOverTime.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testOverTime.getToTime()).isEqualTo(DEFAULT_TO_TIME);
        assertThat(testOverTime.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testOverTime.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOverTime.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOverTime.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOverTime.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the OverTime in Elasticsearch
        verify(mockOverTimeSearchRepository, times(1)).save(testOverTime);
    }

    @Test
    @Transactional
    public void createOverTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = overTimeRepository.findAll().size();

        // Create the OverTime with an existing ID
        overTime.setId(1L);
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeCreate);

        // Validate the OverTime in Elasticsearch
        verify(mockOverTimeSearchRepository, times(0)).save(overTime);
    }

    @Test
    @Transactional
    public void checkOverTimeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = overTimeRepository.findAll().size();
        // set the field null
        overTime.setOverTimeDate(null);

        // Create the OverTime, which fails.
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = overTimeRepository.findAll().size();
        // set the field null
        overTime.setFromTime(null);

        // Create the OverTime, which fails.
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = overTimeRepository.findAll().size();
        // set the field null
        overTime.setToTime(null);

        // Create the OverTime, which fails.
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        restOverTimeMockMvc.perform(post("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOverTimes() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].overTimeDate").value(hasItem(DEFAULT_OVER_TIME_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get the overTime
        restOverTimeMockMvc.perform(get("/api/over-times/{id}", overTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(overTime.getId().intValue()))
            .andExpect(jsonPath("$.overTimeDate").value(DEFAULT_OVER_TIME_DATE.toString()))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.toTime").value(DEFAULT_TO_TIME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllOverTimesByOverTimeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where overTimeDate equals to DEFAULT_OVER_TIME_DATE
        defaultOverTimeShouldBeFound("overTimeDate.equals=" + DEFAULT_OVER_TIME_DATE);

        // Get all the overTimeList where overTimeDate equals to UPDATED_OVER_TIME_DATE
        defaultOverTimeShouldNotBeFound("overTimeDate.equals=" + UPDATED_OVER_TIME_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOverTimeDateIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where overTimeDate in DEFAULT_OVER_TIME_DATE or UPDATED_OVER_TIME_DATE
        defaultOverTimeShouldBeFound("overTimeDate.in=" + DEFAULT_OVER_TIME_DATE + "," + UPDATED_OVER_TIME_DATE);

        // Get all the overTimeList where overTimeDate equals to UPDATED_OVER_TIME_DATE
        defaultOverTimeShouldNotBeFound("overTimeDate.in=" + UPDATED_OVER_TIME_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOverTimeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where overTimeDate is not null
        defaultOverTimeShouldBeFound("overTimeDate.specified=true");

        // Get all the overTimeList where overTimeDate is null
        defaultOverTimeShouldNotBeFound("overTimeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByOverTimeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where overTimeDate greater than or equals to DEFAULT_OVER_TIME_DATE
        defaultOverTimeShouldBeFound("overTimeDate.greaterOrEqualThan=" + DEFAULT_OVER_TIME_DATE);

        // Get all the overTimeList where overTimeDate greater than or equals to UPDATED_OVER_TIME_DATE
        defaultOverTimeShouldNotBeFound("overTimeDate.greaterOrEqualThan=" + UPDATED_OVER_TIME_DATE);
    }

    @Test
    @Transactional
    public void getAllOverTimesByOverTimeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where overTimeDate less than or equals to DEFAULT_OVER_TIME_DATE
        defaultOverTimeShouldNotBeFound("overTimeDate.lessThan=" + DEFAULT_OVER_TIME_DATE);

        // Get all the overTimeList where overTimeDate less than or equals to UPDATED_OVER_TIME_DATE
        defaultOverTimeShouldBeFound("overTimeDate.lessThan=" + UPDATED_OVER_TIME_DATE);
    }


    @Test
    @Transactional
    public void getAllOverTimesByFromTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromTime equals to DEFAULT_FROM_TIME
        defaultOverTimeShouldBeFound("fromTime.equals=" + DEFAULT_FROM_TIME);

        // Get all the overTimeList where fromTime equals to UPDATED_FROM_TIME
        defaultOverTimeShouldNotBeFound("fromTime.equals=" + UPDATED_FROM_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromTimeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromTime in DEFAULT_FROM_TIME or UPDATED_FROM_TIME
        defaultOverTimeShouldBeFound("fromTime.in=" + DEFAULT_FROM_TIME + "," + UPDATED_FROM_TIME);

        // Get all the overTimeList where fromTime equals to UPDATED_FROM_TIME
        defaultOverTimeShouldNotBeFound("fromTime.in=" + UPDATED_FROM_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByFromTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where fromTime is not null
        defaultOverTimeShouldBeFound("fromTime.specified=true");

        // Get all the overTimeList where fromTime is null
        defaultOverTimeShouldNotBeFound("fromTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByToTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toTime equals to DEFAULT_TO_TIME
        defaultOverTimeShouldBeFound("toTime.equals=" + DEFAULT_TO_TIME);

        // Get all the overTimeList where toTime equals to UPDATED_TO_TIME
        defaultOverTimeShouldNotBeFound("toTime.equals=" + UPDATED_TO_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByToTimeIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toTime in DEFAULT_TO_TIME or UPDATED_TO_TIME
        defaultOverTimeShouldBeFound("toTime.in=" + DEFAULT_TO_TIME + "," + UPDATED_TO_TIME);

        // Get all the overTimeList where toTime equals to UPDATED_TO_TIME
        defaultOverTimeShouldNotBeFound("toTime.in=" + UPDATED_TO_TIME);
    }

    @Test
    @Transactional
    public void getAllOverTimesByToTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where toTime is not null
        defaultOverTimeShouldBeFound("toTime.specified=true");

        // Get all the overTimeList where toTime is null
        defaultOverTimeShouldNotBeFound("toTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where duration equals to DEFAULT_DURATION
        defaultOverTimeShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the overTimeList where duration equals to UPDATED_DURATION
        defaultOverTimeShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllOverTimesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultOverTimeShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the overTimeList where duration equals to UPDATED_DURATION
        defaultOverTimeShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllOverTimesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where duration is not null
        defaultOverTimeShouldBeFound("duration.specified=true");

        // Get all the overTimeList where duration is null
        defaultOverTimeShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdBy equals to DEFAULT_CREATED_BY
        defaultOverTimeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the overTimeList where createdBy equals to UPDATED_CREATED_BY
        defaultOverTimeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOverTimeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the overTimeList where createdBy equals to UPDATED_CREATED_BY
        defaultOverTimeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdBy is not null
        defaultOverTimeShouldBeFound("createdBy.specified=true");

        // Get all the overTimeList where createdBy is null
        defaultOverTimeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdOn equals to DEFAULT_CREATED_ON
        defaultOverTimeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the overTimeList where createdOn equals to UPDATED_CREATED_ON
        defaultOverTimeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultOverTimeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the overTimeList where createdOn equals to UPDATED_CREATED_ON
        defaultOverTimeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where createdOn is not null
        defaultOverTimeShouldBeFound("createdOn.specified=true");

        // Get all the overTimeList where createdOn is null
        defaultOverTimeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultOverTimeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the overTimeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultOverTimeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultOverTimeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the overTimeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultOverTimeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedBy is not null
        defaultOverTimeShouldBeFound("updatedBy.specified=true");

        // Get all the overTimeList where updatedBy is null
        defaultOverTimeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultOverTimeShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the overTimeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultOverTimeShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultOverTimeShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the overTimeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultOverTimeShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllOverTimesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        // Get all the overTimeList where updatedOn is not null
        defaultOverTimeShouldBeFound("updatedOn.specified=true");

        // Get all the overTimeList where updatedOn is null
        defaultOverTimeShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOverTimesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        overTime.setEmployee(employee);
        overTimeRepository.saveAndFlush(overTime);
        Long employeeId = employee.getId();

        // Get all the overTimeList where employee equals to employeeId
        defaultOverTimeShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the overTimeList where employee equals to employeeId + 1
        defaultOverTimeShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOverTimeShouldBeFound(String filter) throws Exception {
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].overTimeDate").value(hasItem(DEFAULT_OVER_TIME_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restOverTimeMockMvc.perform(get("/api/over-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOverTimeShouldNotBeFound(String filter) throws Exception {
        restOverTimeMockMvc.perform(get("/api/over-times?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOverTimeMockMvc.perform(get("/api/over-times/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOverTime() throws Exception {
        // Get the overTime
        restOverTimeMockMvc.perform(get("/api/over-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Update the overTime
        OverTime updatedOverTime = overTimeRepository.findById(overTime.getId()).get();
        // Disconnect from session so that the updates on updatedOverTime are not directly saved in db
        em.detach(updatedOverTime);
        updatedOverTime
            .overTimeDate(UPDATED_OVER_TIME_DATE)
            .fromTime(UPDATED_FROM_TIME)
            .toTime(UPDATED_TO_TIME)
            .duration(UPDATED_DURATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(updatedOverTime);

        restOverTimeMockMvc.perform(put("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isOk());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);
        OverTime testOverTime = overTimeList.get(overTimeList.size() - 1);
        assertThat(testOverTime.getOverTimeDate()).isEqualTo(UPDATED_OVER_TIME_DATE);
        assertThat(testOverTime.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testOverTime.getToTime()).isEqualTo(UPDATED_TO_TIME);
        assertThat(testOverTime.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testOverTime.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOverTime.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOverTime.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOverTime.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the OverTime in Elasticsearch
        verify(mockOverTimeSearchRepository, times(1)).save(testOverTime);
    }

    @Test
    @Transactional
    public void updateNonExistingOverTime() throws Exception {
        int databaseSizeBeforeUpdate = overTimeRepository.findAll().size();

        // Create the OverTime
        OverTimeDTO overTimeDTO = overTimeMapper.toDto(overTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOverTimeMockMvc.perform(put("/api/over-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(overTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OverTime in the database
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OverTime in Elasticsearch
        verify(mockOverTimeSearchRepository, times(0)).save(overTime);
    }

    @Test
    @Transactional
    public void deleteOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);

        int databaseSizeBeforeDelete = overTimeRepository.findAll().size();

        // Delete the overTime
        restOverTimeMockMvc.perform(delete("/api/over-times/{id}", overTime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OverTime> overTimeList = overTimeRepository.findAll();
        assertThat(overTimeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OverTime in Elasticsearch
        verify(mockOverTimeSearchRepository, times(1)).deleteById(overTime.getId());
    }

    @Test
    @Transactional
    public void searchOverTime() throws Exception {
        // Initialize the database
        overTimeRepository.saveAndFlush(overTime);
        when(mockOverTimeSearchRepository.search(queryStringQuery("id:" + overTime.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(overTime), PageRequest.of(0, 1), 1));
        // Search the overTime
        restOverTimeMockMvc.perform(get("/api/_search/over-times?query=id:" + overTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(overTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].overTimeDate").value(hasItem(DEFAULT_OVER_TIME_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverTime.class);
        OverTime overTime1 = new OverTime();
        overTime1.setId(1L);
        OverTime overTime2 = new OverTime();
        overTime2.setId(overTime1.getId());
        assertThat(overTime1).isEqualTo(overTime2);
        overTime2.setId(2L);
        assertThat(overTime1).isNotEqualTo(overTime2);
        overTime1.setId(null);
        assertThat(overTime1).isNotEqualTo(overTime2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverTimeDTO.class);
        OverTimeDTO overTimeDTO1 = new OverTimeDTO();
        overTimeDTO1.setId(1L);
        OverTimeDTO overTimeDTO2 = new OverTimeDTO();
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
        overTimeDTO2.setId(overTimeDTO1.getId());
        assertThat(overTimeDTO1).isEqualTo(overTimeDTO2);
        overTimeDTO2.setId(2L);
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
        overTimeDTO1.setId(null);
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(overTimeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(overTimeMapper.fromId(null)).isNull();
    }
}
