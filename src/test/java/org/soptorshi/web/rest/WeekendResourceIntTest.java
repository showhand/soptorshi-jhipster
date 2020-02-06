package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.enumeration.DayOfWeek;
import org.soptorshi.domain.enumeration.WeekendStatus;
import org.soptorshi.repository.WeekendRepository;
import org.soptorshi.repository.search.WeekendSearchRepository;
import org.soptorshi.service.WeekendQueryService;
import org.soptorshi.service.WeekendService;
import org.soptorshi.service.dto.WeekendDTO;
import org.soptorshi.service.mapper.WeekendMapper;
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
 * Test class for the WeekendResource REST controller.
 *
 * @see WeekendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class WeekendResourceIntTest {

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final LocalDate DEFAULT_ACTIVE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTIVE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final DayOfWeek DEFAULT_DAY_1 = DayOfWeek.SUNDAY;
    private static final DayOfWeek UPDATED_DAY_1 = DayOfWeek.MONDAY;

    private static final DayOfWeek DEFAULT_DAY_2 = DayOfWeek.SUNDAY;
    private static final DayOfWeek UPDATED_DAY_2 = DayOfWeek.MONDAY;

    private static final DayOfWeek DEFAULT_DAY_3 = DayOfWeek.SUNDAY;
    private static final DayOfWeek UPDATED_DAY_3 = DayOfWeek.MONDAY;

    private static final WeekendStatus DEFAULT_STATUS = WeekendStatus.ACTIVE;
    private static final WeekendStatus UPDATED_STATUS = WeekendStatus.INACTIVE;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WeekendRepository weekendRepository;

    @Autowired
    private WeekendMapper weekendMapper;

    @Autowired
    private WeekendService weekendService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.WeekendSearchRepositoryMockConfiguration
     */
    @Autowired
    private WeekendSearchRepository mockWeekendSearchRepository;

    @Autowired
    private WeekendQueryService weekendQueryService;

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

    private MockMvc restWeekendMockMvc;

    private Weekend weekend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeekendResource weekendResource = new WeekendResource(weekendService, weekendQueryService);
        this.restWeekendMockMvc = MockMvcBuilders.standaloneSetup(weekendResource)
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
    public static Weekend createEntity(EntityManager em) {
        Weekend weekend = new Weekend()
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS)
            .activeFrom(DEFAULT_ACTIVE_FROM)
            .activeTo(DEFAULT_ACTIVE_TO)
            .day1(DEFAULT_DAY_1)
            .day2(DEFAULT_DAY_2)
            .day3(DEFAULT_DAY_3)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return weekend;
    }

    @Before
    public void initTest() {
        weekend = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeekend() throws Exception {
        int databaseSizeBeforeCreate = weekendRepository.findAll().size();

        // Create the Weekend
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);
        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isCreated());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeCreate + 1);
        Weekend testWeekend = weekendList.get(weekendList.size() - 1);
        assertThat(testWeekend.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testWeekend.getActiveFrom()).isEqualTo(DEFAULT_ACTIVE_FROM);
        assertThat(testWeekend.getActiveTo()).isEqualTo(DEFAULT_ACTIVE_TO);
        assertThat(testWeekend.getDay1()).isEqualTo(DEFAULT_DAY_1);
        assertThat(testWeekend.getDay2()).isEqualTo(DEFAULT_DAY_2);
        assertThat(testWeekend.getDay3()).isEqualTo(DEFAULT_DAY_3);
        assertThat(testWeekend.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWeekend.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWeekend.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWeekend.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWeekend.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the Weekend in Elasticsearch
        verify(mockWeekendSearchRepository, times(1)).save(testWeekend);
    }

    @Test
    @Transactional
    public void createWeekendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekendRepository.findAll().size();

        // Create the Weekend with an existing ID
        weekend.setId(1L);
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeCreate);

        // Validate the Weekend in Elasticsearch
        verify(mockWeekendSearchRepository, times(0)).save(weekend);
    }

    @Test
    @Transactional
    public void checkNumberOfDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = weekendRepository.findAll().size();
        // set the field null
        weekend.setNumberOfDays(null);

        // Create the Weekend, which fails.
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);

        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isBadRequest());

        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDay1IsRequired() throws Exception {
        int databaseSizeBeforeTest = weekendRepository.findAll().size();
        // set the field null
        weekend.setDay1(null);

        // Create the Weekend, which fails.
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);

        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isBadRequest());

        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = weekendRepository.findAll().size();
        // set the field null
        weekend.setStatus(null);

        // Create the Weekend, which fails.
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);

        restWeekendMockMvc.perform(post("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isBadRequest());

        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWeekends() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekend.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())))
            .andExpect(jsonPath("$.[*].day1").value(hasItem(DEFAULT_DAY_1.toString())))
            .andExpect(jsonPath("$.[*].day2").value(hasItem(DEFAULT_DAY_2.toString())))
            .andExpect(jsonPath("$.[*].day3").value(hasItem(DEFAULT_DAY_3.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getWeekend() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get the weekend
        restWeekendMockMvc.perform(get("/api/weekends/{id}", weekend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weekend.getId().intValue()))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.activeFrom").value(DEFAULT_ACTIVE_FROM.toString()))
            .andExpect(jsonPath("$.activeTo").value(DEFAULT_ACTIVE_TO.toString()))
            .andExpect(jsonPath("$.day1").value(DEFAULT_DAY_1.toString()))
            .andExpect(jsonPath("$.day2").value(DEFAULT_DAY_2.toString()))
            .andExpect(jsonPath("$.day3").value(DEFAULT_DAY_3.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllWeekendsByNumberOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where numberOfDays equals to DEFAULT_NUMBER_OF_DAYS
        defaultWeekendShouldBeFound("numberOfDays.equals=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the weekendList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultWeekendShouldNotBeFound("numberOfDays.equals=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByNumberOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where numberOfDays in DEFAULT_NUMBER_OF_DAYS or UPDATED_NUMBER_OF_DAYS
        defaultWeekendShouldBeFound("numberOfDays.in=" + DEFAULT_NUMBER_OF_DAYS + "," + UPDATED_NUMBER_OF_DAYS);

        // Get all the weekendList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultWeekendShouldNotBeFound("numberOfDays.in=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByNumberOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where numberOfDays is not null
        defaultWeekendShouldBeFound("numberOfDays.specified=true");

        // Get all the weekendList where numberOfDays is null
        defaultWeekendShouldNotBeFound("numberOfDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByNumberOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where numberOfDays greater than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultWeekendShouldBeFound("numberOfDays.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the weekendList where numberOfDays greater than or equals to (DEFAULT_NUMBER_OF_DAYS + 1)
        defaultWeekendShouldNotBeFound("numberOfDays.greaterOrEqualThan=" + (DEFAULT_NUMBER_OF_DAYS + 1));
    }

    @Test
    @Transactional
    public void getAllWeekendsByNumberOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where numberOfDays less than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultWeekendShouldNotBeFound("numberOfDays.lessThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the weekendList where numberOfDays less than or equals to (DEFAULT_NUMBER_OF_DAYS + 1)
        defaultWeekendShouldBeFound("numberOfDays.lessThan=" + (DEFAULT_NUMBER_OF_DAYS + 1));
    }


    @Test
    @Transactional
    public void getAllWeekendsByActiveFromIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeFrom equals to DEFAULT_ACTIVE_FROM
        defaultWeekendShouldBeFound("activeFrom.equals=" + DEFAULT_ACTIVE_FROM);

        // Get all the weekendList where activeFrom equals to UPDATED_ACTIVE_FROM
        defaultWeekendShouldNotBeFound("activeFrom.equals=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveFromIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeFrom in DEFAULT_ACTIVE_FROM or UPDATED_ACTIVE_FROM
        defaultWeekendShouldBeFound("activeFrom.in=" + DEFAULT_ACTIVE_FROM + "," + UPDATED_ACTIVE_FROM);

        // Get all the weekendList where activeFrom equals to UPDATED_ACTIVE_FROM
        defaultWeekendShouldNotBeFound("activeFrom.in=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeFrom is not null
        defaultWeekendShouldBeFound("activeFrom.specified=true");

        // Get all the weekendList where activeFrom is null
        defaultWeekendShouldNotBeFound("activeFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeFrom greater than or equals to DEFAULT_ACTIVE_FROM
        defaultWeekendShouldBeFound("activeFrom.greaterOrEqualThan=" + DEFAULT_ACTIVE_FROM);

        // Get all the weekendList where activeFrom greater than or equals to UPDATED_ACTIVE_FROM
        defaultWeekendShouldNotBeFound("activeFrom.greaterOrEqualThan=" + UPDATED_ACTIVE_FROM);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveFromIsLessThanSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeFrom less than or equals to DEFAULT_ACTIVE_FROM
        defaultWeekendShouldNotBeFound("activeFrom.lessThan=" + DEFAULT_ACTIVE_FROM);

        // Get all the weekendList where activeFrom less than or equals to UPDATED_ACTIVE_FROM
        defaultWeekendShouldBeFound("activeFrom.lessThan=" + UPDATED_ACTIVE_FROM);
    }


    @Test
    @Transactional
    public void getAllWeekendsByActiveToIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeTo equals to DEFAULT_ACTIVE_TO
        defaultWeekendShouldBeFound("activeTo.equals=" + DEFAULT_ACTIVE_TO);

        // Get all the weekendList where activeTo equals to UPDATED_ACTIVE_TO
        defaultWeekendShouldNotBeFound("activeTo.equals=" + UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveToIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeTo in DEFAULT_ACTIVE_TO or UPDATED_ACTIVE_TO
        defaultWeekendShouldBeFound("activeTo.in=" + DEFAULT_ACTIVE_TO + "," + UPDATED_ACTIVE_TO);

        // Get all the weekendList where activeTo equals to UPDATED_ACTIVE_TO
        defaultWeekendShouldNotBeFound("activeTo.in=" + UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveToIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeTo is not null
        defaultWeekendShouldBeFound("activeTo.specified=true");

        // Get all the weekendList where activeTo is null
        defaultWeekendShouldNotBeFound("activeTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeTo greater than or equals to DEFAULT_ACTIVE_TO
        defaultWeekendShouldBeFound("activeTo.greaterOrEqualThan=" + DEFAULT_ACTIVE_TO);

        // Get all the weekendList where activeTo greater than or equals to UPDATED_ACTIVE_TO
        defaultWeekendShouldNotBeFound("activeTo.greaterOrEqualThan=" + UPDATED_ACTIVE_TO);
    }

    @Test
    @Transactional
    public void getAllWeekendsByActiveToIsLessThanSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where activeTo less than or equals to DEFAULT_ACTIVE_TO
        defaultWeekendShouldNotBeFound("activeTo.lessThan=" + DEFAULT_ACTIVE_TO);

        // Get all the weekendList where activeTo less than or equals to UPDATED_ACTIVE_TO
        defaultWeekendShouldBeFound("activeTo.lessThan=" + UPDATED_ACTIVE_TO);
    }


    @Test
    @Transactional
    public void getAllWeekendsByDay1IsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day1 equals to DEFAULT_DAY_1
        defaultWeekendShouldBeFound("day1.equals=" + DEFAULT_DAY_1);

        // Get all the weekendList where day1 equals to UPDATED_DAY_1
        defaultWeekendShouldNotBeFound("day1.equals=" + UPDATED_DAY_1);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay1IsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day1 in DEFAULT_DAY_1 or UPDATED_DAY_1
        defaultWeekendShouldBeFound("day1.in=" + DEFAULT_DAY_1 + "," + UPDATED_DAY_1);

        // Get all the weekendList where day1 equals to UPDATED_DAY_1
        defaultWeekendShouldNotBeFound("day1.in=" + UPDATED_DAY_1);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay1IsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day1 is not null
        defaultWeekendShouldBeFound("day1.specified=true");

        // Get all the weekendList where day1 is null
        defaultWeekendShouldNotBeFound("day1.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay2IsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day2 equals to DEFAULT_DAY_2
        defaultWeekendShouldBeFound("day2.equals=" + DEFAULT_DAY_2);

        // Get all the weekendList where day2 equals to UPDATED_DAY_2
        defaultWeekendShouldNotBeFound("day2.equals=" + UPDATED_DAY_2);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay2IsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day2 in DEFAULT_DAY_2 or UPDATED_DAY_2
        defaultWeekendShouldBeFound("day2.in=" + DEFAULT_DAY_2 + "," + UPDATED_DAY_2);

        // Get all the weekendList where day2 equals to UPDATED_DAY_2
        defaultWeekendShouldNotBeFound("day2.in=" + UPDATED_DAY_2);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay2IsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day2 is not null
        defaultWeekendShouldBeFound("day2.specified=true");

        // Get all the weekendList where day2 is null
        defaultWeekendShouldNotBeFound("day2.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay3IsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day3 equals to DEFAULT_DAY_3
        defaultWeekendShouldBeFound("day3.equals=" + DEFAULT_DAY_3);

        // Get all the weekendList where day3 equals to UPDATED_DAY_3
        defaultWeekendShouldNotBeFound("day3.equals=" + UPDATED_DAY_3);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay3IsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day3 in DEFAULT_DAY_3 or UPDATED_DAY_3
        defaultWeekendShouldBeFound("day3.in=" + DEFAULT_DAY_3 + "," + UPDATED_DAY_3);

        // Get all the weekendList where day3 equals to UPDATED_DAY_3
        defaultWeekendShouldNotBeFound("day3.in=" + UPDATED_DAY_3);
    }

    @Test
    @Transactional
    public void getAllWeekendsByDay3IsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where day3 is not null
        defaultWeekendShouldBeFound("day3.specified=true");

        // Get all the weekendList where day3 is null
        defaultWeekendShouldNotBeFound("day3.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status equals to DEFAULT_STATUS
        defaultWeekendShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the weekendList where status equals to UPDATED_STATUS
        defaultWeekendShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWeekendShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the weekendList where status equals to UPDATED_STATUS
        defaultWeekendShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWeekendsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where status is not null
        defaultWeekendShouldBeFound("status.specified=true");

        // Get all the weekendList where status is null
        defaultWeekendShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdBy equals to DEFAULT_CREATED_BY
        defaultWeekendShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the weekendList where createdBy equals to UPDATED_CREATED_BY
        defaultWeekendShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWeekendShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the weekendList where createdBy equals to UPDATED_CREATED_BY
        defaultWeekendShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdBy is not null
        defaultWeekendShouldBeFound("createdBy.specified=true");

        // Get all the weekendList where createdBy is null
        defaultWeekendShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdOn equals to DEFAULT_CREATED_ON
        defaultWeekendShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the weekendList where createdOn equals to UPDATED_CREATED_ON
        defaultWeekendShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultWeekendShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the weekendList where createdOn equals to UPDATED_CREATED_ON
        defaultWeekendShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllWeekendsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where createdOn is not null
        defaultWeekendShouldBeFound("createdOn.specified=true");

        // Get all the weekendList where createdOn is null
        defaultWeekendShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultWeekendShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the weekendList where updatedBy equals to UPDATED_UPDATED_BY
        defaultWeekendShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultWeekendShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the weekendList where updatedBy equals to UPDATED_UPDATED_BY
        defaultWeekendShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedBy is not null
        defaultWeekendShouldBeFound("updatedBy.specified=true");

        // Get all the weekendList where updatedBy is null
        defaultWeekendShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultWeekendShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the weekendList where updatedOn equals to UPDATED_UPDATED_ON
        defaultWeekendShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultWeekendShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the weekendList where updatedOn equals to UPDATED_UPDATED_ON
        defaultWeekendShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllWeekendsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        // Get all the weekendList where updatedOn is not null
        defaultWeekendShouldBeFound("updatedOn.specified=true");

        // Get all the weekendList where updatedOn is null
        defaultWeekendShouldNotBeFound("updatedOn.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWeekendShouldBeFound(String filter) throws Exception {
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekend.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())))
            .andExpect(jsonPath("$.[*].day1").value(hasItem(DEFAULT_DAY_1.toString())))
            .andExpect(jsonPath("$.[*].day2").value(hasItem(DEFAULT_DAY_2.toString())))
            .andExpect(jsonPath("$.[*].day3").value(hasItem(DEFAULT_DAY_3.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restWeekendMockMvc.perform(get("/api/weekends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWeekendShouldNotBeFound(String filter) throws Exception {
        restWeekendMockMvc.perform(get("/api/weekends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWeekendMockMvc.perform(get("/api/weekends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWeekend() throws Exception {
        // Get the weekend
        restWeekendMockMvc.perform(get("/api/weekends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeekend() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        int databaseSizeBeforeUpdate = weekendRepository.findAll().size();

        // Update the weekend
        Weekend updatedWeekend = weekendRepository.findById(weekend.getId()).get();
        // Disconnect from session so that the updates on updatedWeekend are not directly saved in db
        em.detach(updatedWeekend);
        updatedWeekend
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .activeFrom(UPDATED_ACTIVE_FROM)
            .activeTo(UPDATED_ACTIVE_TO)
            .day1(UPDATED_DAY_1)
            .day2(UPDATED_DAY_2)
            .day3(UPDATED_DAY_3)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        WeekendDTO weekendDTO = weekendMapper.toDto(updatedWeekend);

        restWeekendMockMvc.perform(put("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isOk());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeUpdate);
        Weekend testWeekend = weekendList.get(weekendList.size() - 1);
        assertThat(testWeekend.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testWeekend.getActiveFrom()).isEqualTo(UPDATED_ACTIVE_FROM);
        assertThat(testWeekend.getActiveTo()).isEqualTo(UPDATED_ACTIVE_TO);
        assertThat(testWeekend.getDay1()).isEqualTo(UPDATED_DAY_1);
        assertThat(testWeekend.getDay2()).isEqualTo(UPDATED_DAY_2);
        assertThat(testWeekend.getDay3()).isEqualTo(UPDATED_DAY_3);
        assertThat(testWeekend.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWeekend.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWeekend.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWeekend.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWeekend.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the Weekend in Elasticsearch
        verify(mockWeekendSearchRepository, times(1)).save(testWeekend);
    }

    @Test
    @Transactional
    public void updateNonExistingWeekend() throws Exception {
        int databaseSizeBeforeUpdate = weekendRepository.findAll().size();

        // Create the Weekend
        WeekendDTO weekendDTO = weekendMapper.toDto(weekend);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekendMockMvc.perform(put("/api/weekends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weekendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Weekend in the database
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Weekend in Elasticsearch
        verify(mockWeekendSearchRepository, times(0)).save(weekend);
    }

    @Test
    @Transactional
    public void deleteWeekend() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);

        int databaseSizeBeforeDelete = weekendRepository.findAll().size();

        // Delete the weekend
        restWeekendMockMvc.perform(delete("/api/weekends/{id}", weekend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Weekend> weekendList = weekendRepository.findAll();
        assertThat(weekendList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Weekend in Elasticsearch
        verify(mockWeekendSearchRepository, times(1)).deleteById(weekend.getId());
    }

    @Test
    @Transactional
    public void searchWeekend() throws Exception {
        // Initialize the database
        weekendRepository.saveAndFlush(weekend);
        when(mockWeekendSearchRepository.search(queryStringQuery("id:" + weekend.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(weekend), PageRequest.of(0, 1), 1));
        // Search the weekend
        restWeekendMockMvc.perform(get("/api/_search/weekends?query=id:" + weekend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekend.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].activeFrom").value(hasItem(DEFAULT_ACTIVE_FROM.toString())))
            .andExpect(jsonPath("$.[*].activeTo").value(hasItem(DEFAULT_ACTIVE_TO.toString())))
            .andExpect(jsonPath("$.[*].day1").value(hasItem(DEFAULT_DAY_1.toString())))
            .andExpect(jsonPath("$.[*].day2").value(hasItem(DEFAULT_DAY_2.toString())))
            .andExpect(jsonPath("$.[*].day3").value(hasItem(DEFAULT_DAY_3.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Weekend.class);
        Weekend weekend1 = new Weekend();
        weekend1.setId(1L);
        Weekend weekend2 = new Weekend();
        weekend2.setId(weekend1.getId());
        assertThat(weekend1).isEqualTo(weekend2);
        weekend2.setId(2L);
        assertThat(weekend1).isNotEqualTo(weekend2);
        weekend1.setId(null);
        assertThat(weekend1).isNotEqualTo(weekend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeekendDTO.class);
        WeekendDTO weekendDTO1 = new WeekendDTO();
        weekendDTO1.setId(1L);
        WeekendDTO weekendDTO2 = new WeekendDTO();
        assertThat(weekendDTO1).isNotEqualTo(weekendDTO2);
        weekendDTO2.setId(weekendDTO1.getId());
        assertThat(weekendDTO1).isEqualTo(weekendDTO2);
        weekendDTO2.setId(2L);
        assertThat(weekendDTO1).isNotEqualTo(weekendDTO2);
        weekendDTO1.setId(null);
        assertThat(weekendDTO1).isNotEqualTo(weekendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(weekendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(weekendMapper.fromId(null)).isNull();
    }
}
