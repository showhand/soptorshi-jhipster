package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Holiday;
import org.soptorshi.domain.HolidayType;
import org.soptorshi.domain.enumeration.HolidayImposedAuthority;
import org.soptorshi.domain.enumeration.YesOrNo;
import org.soptorshi.repository.HolidayRepository;
import org.soptorshi.repository.search.HolidaySearchRepository;
import org.soptorshi.service.HolidayQueryService;
import org.soptorshi.service.HolidayService;
import org.soptorshi.service.dto.HolidayDTO;
import org.soptorshi.service.mapper.HolidayMapper;
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
 * Test class for the HolidayResource REST controller.
 *
 * @see HolidayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class HolidayResourceIntTest {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_NUMBER_OF_DAYS = 2;

    private static final YesOrNo DEFAULT_MOON_DEPENDENCY = YesOrNo.YES;
    private static final YesOrNo UPDATED_MOON_DEPENDENCY = YesOrNo.NO;

    private static final HolidayImposedAuthority DEFAULT_HOLIDAY_DECLARED_BY = HolidayImposedAuthority.GOVERNMENT;
    private static final HolidayImposedAuthority UPDATED_HOLIDAY_DECLARED_BY = HolidayImposedAuthority.ORGANIZATIONAL;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_HOLIDAY_YEAR = 1;
    private static final Integer UPDATED_HOLIDAY_YEAR = 2;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private HolidayService holidayService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.HolidaySearchRepositoryMockConfiguration
     */
    @Autowired
    private HolidaySearchRepository mockHolidaySearchRepository;

    @Autowired
    private HolidayQueryService holidayQueryService;

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

    private MockMvc restHolidayMockMvc;

    private Holiday holiday;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HolidayResource holidayResource = new HolidayResource(holidayService, holidayQueryService);
        this.restHolidayMockMvc = MockMvcBuilders.standaloneSetup(holidayResource)
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
    public static Holiday createEntity(EntityManager em) {
        Holiday holiday = new Holiday()
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .numberOfDays(DEFAULT_NUMBER_OF_DAYS)
            .moonDependency(DEFAULT_MOON_DEPENDENCY)
            .holidayDeclaredBy(DEFAULT_HOLIDAY_DECLARED_BY)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .holidayYear(DEFAULT_HOLIDAY_YEAR);
        // Add required entity
        HolidayType holidayType = HolidayTypeResourceIntTest.createEntity(em);
        em.persist(holidayType);
        em.flush();
        holiday.setHolidayType(holidayType);
        return holiday;
    }

    @Before
    public void initTest() {
        holiday = createEntity(em);
    }

    @Test
    @Transactional
    public void createHoliday() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isCreated());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate + 1);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHoliday.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHoliday.getNumberOfDays()).isEqualTo(DEFAULT_NUMBER_OF_DAYS);
        assertThat(testHoliday.getMoonDependency()).isEqualTo(DEFAULT_MOON_DEPENDENCY);
        assertThat(testHoliday.getHolidayDeclaredBy()).isEqualTo(DEFAULT_HOLIDAY_DECLARED_BY);
        assertThat(testHoliday.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testHoliday.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHoliday.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHoliday.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testHoliday.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testHoliday.getHolidayYear()).isEqualTo(DEFAULT_HOLIDAY_YEAR);

        // Validate the Holiday in Elasticsearch
        verify(mockHolidaySearchRepository, times(1)).save(testHoliday);
    }

    @Test
    @Transactional
    public void createHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidayRepository.findAll().size();

        // Create the Holiday with an existing ID
        holiday.setId(1L);
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeCreate);

        // Validate the Holiday in Elasticsearch
        verify(mockHolidaySearchRepository, times(0)).save(holiday);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setFromDate(null);

        // Create the Holiday, which fails.
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setToDate(null);

        // Create the Holiday, which fails.
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setNumberOfDays(null);

        // Create the Holiday, which fails.
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMoonDependencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setMoonDependency(null);

        // Create the Holiday, which fails.
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHolidayDeclaredByIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayRepository.findAll().size();
        // set the field null
        holiday.setHolidayDeclaredBy(null);

        // Create the Holiday, which fails.
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        restHolidayMockMvc.perform(post("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHolidays() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())))
            .andExpect(jsonPath("$.[*].holidayDeclaredBy").value(hasItem(DEFAULT_HOLIDAY_DECLARED_BY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].holidayYear").value(hasItem(DEFAULT_HOLIDAY_YEAR)));
    }

    @Test
    @Transactional
    public void getHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(holiday.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.numberOfDays").value(DEFAULT_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.moonDependency").value(DEFAULT_MOON_DEPENDENCY.toString()))
            .andExpect(jsonPath("$.holidayDeclaredBy").value(DEFAULT_HOLIDAY_DECLARED_BY.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.holidayYear").value(DEFAULT_HOLIDAY_YEAR));
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where fromDate equals to DEFAULT_FROM_DATE
        defaultHolidayShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the holidayList where fromDate equals to UPDATED_FROM_DATE
        defaultHolidayShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultHolidayShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the holidayList where fromDate equals to UPDATED_FROM_DATE
        defaultHolidayShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where fromDate is not null
        defaultHolidayShouldBeFound("fromDate.specified=true");

        // Get all the holidayList where fromDate is null
        defaultHolidayShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where fromDate greater than or equals to DEFAULT_FROM_DATE
        defaultHolidayShouldBeFound("fromDate.greaterOrEqualThan=" + DEFAULT_FROM_DATE);

        // Get all the holidayList where fromDate greater than or equals to UPDATED_FROM_DATE
        defaultHolidayShouldNotBeFound("fromDate.greaterOrEqualThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where fromDate less than or equals to DEFAULT_FROM_DATE
        defaultHolidayShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the holidayList where fromDate less than or equals to UPDATED_FROM_DATE
        defaultHolidayShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllHolidaysByToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where toDate equals to DEFAULT_TO_DATE
        defaultHolidayShouldBeFound("toDate.equals=" + DEFAULT_TO_DATE);

        // Get all the holidayList where toDate equals to UPDATED_TO_DATE
        defaultHolidayShouldNotBeFound("toDate.equals=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToDateIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where toDate in DEFAULT_TO_DATE or UPDATED_TO_DATE
        defaultHolidayShouldBeFound("toDate.in=" + DEFAULT_TO_DATE + "," + UPDATED_TO_DATE);

        // Get all the holidayList where toDate equals to UPDATED_TO_DATE
        defaultHolidayShouldNotBeFound("toDate.in=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where toDate is not null
        defaultHolidayShouldBeFound("toDate.specified=true");

        // Get all the holidayList where toDate is null
        defaultHolidayShouldNotBeFound("toDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByToDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where toDate greater than or equals to DEFAULT_TO_DATE
        defaultHolidayShouldBeFound("toDate.greaterOrEqualThan=" + DEFAULT_TO_DATE);

        // Get all the holidayList where toDate greater than or equals to UPDATED_TO_DATE
        defaultHolidayShouldNotBeFound("toDate.greaterOrEqualThan=" + UPDATED_TO_DATE);
    }

    @Test
    @Transactional
    public void getAllHolidaysByToDateIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where toDate less than or equals to DEFAULT_TO_DATE
        defaultHolidayShouldNotBeFound("toDate.lessThan=" + DEFAULT_TO_DATE);

        // Get all the holidayList where toDate less than or equals to UPDATED_TO_DATE
        defaultHolidayShouldBeFound("toDate.lessThan=" + UPDATED_TO_DATE);
    }


    @Test
    @Transactional
    public void getAllHolidaysByNumberOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where numberOfDays equals to DEFAULT_NUMBER_OF_DAYS
        defaultHolidayShouldBeFound("numberOfDays.equals=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the holidayList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultHolidayShouldNotBeFound("numberOfDays.equals=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByNumberOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where numberOfDays in DEFAULT_NUMBER_OF_DAYS or UPDATED_NUMBER_OF_DAYS
        defaultHolidayShouldBeFound("numberOfDays.in=" + DEFAULT_NUMBER_OF_DAYS + "," + UPDATED_NUMBER_OF_DAYS);

        // Get all the holidayList where numberOfDays equals to UPDATED_NUMBER_OF_DAYS
        defaultHolidayShouldNotBeFound("numberOfDays.in=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByNumberOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where numberOfDays is not null
        defaultHolidayShouldBeFound("numberOfDays.specified=true");

        // Get all the holidayList where numberOfDays is null
        defaultHolidayShouldNotBeFound("numberOfDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByNumberOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where numberOfDays greater than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultHolidayShouldBeFound("numberOfDays.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the holidayList where numberOfDays greater than or equals to UPDATED_NUMBER_OF_DAYS
        defaultHolidayShouldNotBeFound("numberOfDays.greaterOrEqualThan=" + UPDATED_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllHolidaysByNumberOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where numberOfDays less than or equals to DEFAULT_NUMBER_OF_DAYS
        defaultHolidayShouldNotBeFound("numberOfDays.lessThan=" + DEFAULT_NUMBER_OF_DAYS);

        // Get all the holidayList where numberOfDays less than or equals to UPDATED_NUMBER_OF_DAYS
        defaultHolidayShouldBeFound("numberOfDays.lessThan=" + UPDATED_NUMBER_OF_DAYS);
    }


    @Test
    @Transactional
    public void getAllHolidaysByMoonDependencyIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where moonDependency equals to DEFAULT_MOON_DEPENDENCY
        defaultHolidayShouldBeFound("moonDependency.equals=" + DEFAULT_MOON_DEPENDENCY);

        // Get all the holidayList where moonDependency equals to UPDATED_MOON_DEPENDENCY
        defaultHolidayShouldNotBeFound("moonDependency.equals=" + UPDATED_MOON_DEPENDENCY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByMoonDependencyIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where moonDependency in DEFAULT_MOON_DEPENDENCY or UPDATED_MOON_DEPENDENCY
        defaultHolidayShouldBeFound("moonDependency.in=" + DEFAULT_MOON_DEPENDENCY + "," + UPDATED_MOON_DEPENDENCY);

        // Get all the holidayList where moonDependency equals to UPDATED_MOON_DEPENDENCY
        defaultHolidayShouldNotBeFound("moonDependency.in=" + UPDATED_MOON_DEPENDENCY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByMoonDependencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where moonDependency is not null
        defaultHolidayShouldBeFound("moonDependency.specified=true");

        // Get all the holidayList where moonDependency is null
        defaultHolidayShouldNotBeFound("moonDependency.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayDeclaredByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDeclaredBy equals to DEFAULT_HOLIDAY_DECLARED_BY
        defaultHolidayShouldBeFound("holidayDeclaredBy.equals=" + DEFAULT_HOLIDAY_DECLARED_BY);

        // Get all the holidayList where holidayDeclaredBy equals to UPDATED_HOLIDAY_DECLARED_BY
        defaultHolidayShouldNotBeFound("holidayDeclaredBy.equals=" + UPDATED_HOLIDAY_DECLARED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayDeclaredByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDeclaredBy in DEFAULT_HOLIDAY_DECLARED_BY or UPDATED_HOLIDAY_DECLARED_BY
        defaultHolidayShouldBeFound("holidayDeclaredBy.in=" + DEFAULT_HOLIDAY_DECLARED_BY + "," + UPDATED_HOLIDAY_DECLARED_BY);

        // Get all the holidayList where holidayDeclaredBy equals to UPDATED_HOLIDAY_DECLARED_BY
        defaultHolidayShouldNotBeFound("holidayDeclaredBy.in=" + UPDATED_HOLIDAY_DECLARED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayDeclaredByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayDeclaredBy is not null
        defaultHolidayShouldBeFound("holidayDeclaredBy.specified=true");

        // Get all the holidayList where holidayDeclaredBy is null
        defaultHolidayShouldNotBeFound("holidayDeclaredBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdBy equals to DEFAULT_CREATED_BY
        defaultHolidayShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the holidayList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidayShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultHolidayShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the holidayList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidayShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdBy is not null
        defaultHolidayShouldBeFound("createdBy.specified=true");

        // Get all the holidayList where createdBy is null
        defaultHolidayShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdOn equals to DEFAULT_CREATED_ON
        defaultHolidayShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the holidayList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidayShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultHolidayShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the holidayList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidayShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidaysByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where createdOn is not null
        defaultHolidayShouldBeFound("createdOn.specified=true");

        // Get all the holidayList where createdOn is null
        defaultHolidayShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultHolidayShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the holidayList where updatedBy equals to UPDATED_UPDATED_BY
        defaultHolidayShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultHolidayShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the holidayList where updatedBy equals to UPDATED_UPDATED_BY
        defaultHolidayShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedBy is not null
        defaultHolidayShouldBeFound("updatedBy.specified=true");

        // Get all the holidayList where updatedBy is null
        defaultHolidayShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultHolidayShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the holidayList where updatedOn equals to UPDATED_UPDATED_ON
        defaultHolidayShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultHolidayShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the holidayList where updatedOn equals to UPDATED_UPDATED_ON
        defaultHolidayShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidaysByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where updatedOn is not null
        defaultHolidayShouldBeFound("updatedOn.specified=true");

        // Get all the holidayList where updatedOn is null
        defaultHolidayShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayYearIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayYear equals to DEFAULT_HOLIDAY_YEAR
        defaultHolidayShouldBeFound("holidayYear.equals=" + DEFAULT_HOLIDAY_YEAR);

        // Get all the holidayList where holidayYear equals to UPDATED_HOLIDAY_YEAR
        defaultHolidayShouldNotBeFound("holidayYear.equals=" + UPDATED_HOLIDAY_YEAR);
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayYearIsInShouldWork() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayYear in DEFAULT_HOLIDAY_YEAR or UPDATED_HOLIDAY_YEAR
        defaultHolidayShouldBeFound("holidayYear.in=" + DEFAULT_HOLIDAY_YEAR + "," + UPDATED_HOLIDAY_YEAR);

        // Get all the holidayList where holidayYear equals to UPDATED_HOLIDAY_YEAR
        defaultHolidayShouldNotBeFound("holidayYear.in=" + UPDATED_HOLIDAY_YEAR);
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayYear is not null
        defaultHolidayShouldBeFound("holidayYear.specified=true");

        // Get all the holidayList where holidayYear is null
        defaultHolidayShouldNotBeFound("holidayYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayYear greater than or equals to DEFAULT_HOLIDAY_YEAR
        defaultHolidayShouldBeFound("holidayYear.greaterOrEqualThan=" + DEFAULT_HOLIDAY_YEAR);

        // Get all the holidayList where holidayYear greater than or equals to UPDATED_HOLIDAY_YEAR
        defaultHolidayShouldNotBeFound("holidayYear.greaterOrEqualThan=" + UPDATED_HOLIDAY_YEAR);
    }

    @Test
    @Transactional
    public void getAllHolidaysByHolidayYearIsLessThanSomething() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidayList where holidayYear less than or equals to DEFAULT_HOLIDAY_YEAR
        defaultHolidayShouldNotBeFound("holidayYear.lessThan=" + DEFAULT_HOLIDAY_YEAR);

        // Get all the holidayList where holidayYear less than or equals to UPDATED_HOLIDAY_YEAR
        defaultHolidayShouldBeFound("holidayYear.lessThan=" + UPDATED_HOLIDAY_YEAR);
    }


    @Test
    @Transactional
    public void getAllHolidaysByHolidayTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        HolidayType holidayType = HolidayTypeResourceIntTest.createEntity(em);
        em.persist(holidayType);
        em.flush();
        holiday.setHolidayType(holidayType);
        holidayRepository.saveAndFlush(holiday);
        Long holidayTypeId = holidayType.getId();

        // Get all the holidayList where holidayType equals to holidayTypeId
        defaultHolidayShouldBeFound("holidayTypeId.equals=" + holidayTypeId);

        // Get all the holidayList where holidayType equals to holidayTypeId + 1
        defaultHolidayShouldNotBeFound("holidayTypeId.equals=" + (holidayTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHolidayShouldBeFound(String filter) throws Exception {
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())))
            .andExpect(jsonPath("$.[*].holidayDeclaredBy").value(hasItem(DEFAULT_HOLIDAY_DECLARED_BY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].holidayYear").value(hasItem(DEFAULT_HOLIDAY_YEAR)));

        // Check, that the count call also returns 1
        restHolidayMockMvc.perform(get("/api/holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHolidayShouldNotBeFound(String filter) throws Exception {
        restHolidayMockMvc.perform(get("/api/holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidayMockMvc.perform(get("/api/holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHoliday() throws Exception {
        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Update the holiday
        Holiday updatedHoliday = holidayRepository.findById(holiday.getId()).get();
        // Disconnect from session so that the updates on updatedHoliday are not directly saved in db
        em.detach(updatedHoliday);
        updatedHoliday
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .numberOfDays(UPDATED_NUMBER_OF_DAYS)
            .moonDependency(UPDATED_MOON_DEPENDENCY)
            .holidayDeclaredBy(UPDATED_HOLIDAY_DECLARED_BY)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .holidayYear(UPDATED_HOLIDAY_YEAR);
        HolidayDTO holidayDTO = holidayMapper.toDto(updatedHoliday);

        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);
        Holiday testHoliday = holidayList.get(holidayList.size() - 1);
        assertThat(testHoliday.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHoliday.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHoliday.getNumberOfDays()).isEqualTo(UPDATED_NUMBER_OF_DAYS);
        assertThat(testHoliday.getMoonDependency()).isEqualTo(UPDATED_MOON_DEPENDENCY);
        assertThat(testHoliday.getHolidayDeclaredBy()).isEqualTo(UPDATED_HOLIDAY_DECLARED_BY);
        assertThat(testHoliday.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testHoliday.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHoliday.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHoliday.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHoliday.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testHoliday.getHolidayYear()).isEqualTo(UPDATED_HOLIDAY_YEAR);

        // Validate the Holiday in Elasticsearch
        verify(mockHolidaySearchRepository, times(1)).save(testHoliday);
    }

    @Test
    @Transactional
    public void updateNonExistingHoliday() throws Exception {
        int databaseSizeBeforeUpdate = holidayRepository.findAll().size();

        // Create the Holiday
        HolidayDTO holidayDTO = holidayMapper.toDto(holiday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayMockMvc.perform(put("/api/holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Holiday in the database
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Holiday in Elasticsearch
        verify(mockHolidaySearchRepository, times(0)).save(holiday);
    }

    @Test
    @Transactional
    public void deleteHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        int databaseSizeBeforeDelete = holidayRepository.findAll().size();

        // Delete the holiday
        restHolidayMockMvc.perform(delete("/api/holidays/{id}", holiday.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Holiday> holidayList = holidayRepository.findAll();
        assertThat(holidayList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Holiday in Elasticsearch
        verify(mockHolidaySearchRepository, times(1)).deleteById(holiday.getId());
    }

    @Test
    @Transactional
    public void searchHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);
        when(mockHolidaySearchRepository.search(queryStringQuery("id:" + holiday.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(holiday), PageRequest.of(0, 1), 1));
        // Search the holiday
        restHolidayMockMvc.perform(get("/api/_search/holidays?query=id:" + holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holiday.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].numberOfDays").value(hasItem(DEFAULT_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())))
            .andExpect(jsonPath("$.[*].holidayDeclaredBy").value(hasItem(DEFAULT_HOLIDAY_DECLARED_BY.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].holidayYear").value(hasItem(DEFAULT_HOLIDAY_YEAR)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Holiday.class);
        Holiday holiday1 = new Holiday();
        holiday1.setId(1L);
        Holiday holiday2 = new Holiday();
        holiday2.setId(holiday1.getId());
        assertThat(holiday1).isEqualTo(holiday2);
        holiday2.setId(2L);
        assertThat(holiday1).isNotEqualTo(holiday2);
        holiday1.setId(null);
        assertThat(holiday1).isNotEqualTo(holiday2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HolidayDTO.class);
        HolidayDTO holidayDTO1 = new HolidayDTO();
        holidayDTO1.setId(1L);
        HolidayDTO holidayDTO2 = new HolidayDTO();
        assertThat(holidayDTO1).isNotEqualTo(holidayDTO2);
        holidayDTO2.setId(holidayDTO1.getId());
        assertThat(holidayDTO1).isEqualTo(holidayDTO2);
        holidayDTO2.setId(2L);
        assertThat(holidayDTO1).isNotEqualTo(holidayDTO2);
        holidayDTO1.setId(null);
        assertThat(holidayDTO1).isNotEqualTo(holidayDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(holidayMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(holidayMapper.fromId(null)).isNull();
    }
}
