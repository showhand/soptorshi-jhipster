package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.soptorshi.repository.SpecialAllowanceTimeLineRepository;
import org.soptorshi.repository.search.SpecialAllowanceTimeLineSearchRepository;
import org.soptorshi.service.SpecialAllowanceTimeLineService;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineDTO;
import org.soptorshi.service.mapper.SpecialAllowanceTimeLineMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineCriteria;
import org.soptorshi.service.SpecialAllowanceTimeLineQueryService;

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

import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.MonthType;
/**
 * Test class for the SpecialAllowanceTimeLineResource REST controller.
 *
 * @see SpecialAllowanceTimeLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SpecialAllowanceTimeLineResourceIntTest {

    private static final AllowanceType DEFAULT_ALLOWANCE_TYPE = AllowanceType.HOUSE_RENT;
    private static final AllowanceType UPDATED_ALLOWANCE_TYPE = AllowanceType.MEDICAL_ALLOWANCE;

    private static final Religion DEFAULT_RELIGION = Religion.ISLAM;
    private static final Religion UPDATED_RELIGION = Religion.HINDU;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository;

    @Autowired
    private SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper;

    @Autowired
    private SpecialAllowanceTimeLineService specialAllowanceTimeLineService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SpecialAllowanceTimeLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private SpecialAllowanceTimeLineSearchRepository mockSpecialAllowanceTimeLineSearchRepository;

    @Autowired
    private SpecialAllowanceTimeLineQueryService specialAllowanceTimeLineQueryService;

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

    private MockMvc restSpecialAllowanceTimeLineMockMvc;

    private SpecialAllowanceTimeLine specialAllowanceTimeLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialAllowanceTimeLineResource specialAllowanceTimeLineResource = new SpecialAllowanceTimeLineResource(specialAllowanceTimeLineService, specialAllowanceTimeLineQueryService);
        this.restSpecialAllowanceTimeLineMockMvc = MockMvcBuilders.standaloneSetup(specialAllowanceTimeLineResource)
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
    public static SpecialAllowanceTimeLine createEntity(EntityManager em) {
        SpecialAllowanceTimeLine specialAllowanceTimeLine = new SpecialAllowanceTimeLine()
            .allowanceType(DEFAULT_ALLOWANCE_TYPE)
            .religion(DEFAULT_RELIGION)
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return specialAllowanceTimeLine;
    }

    @Before
    public void initTest() {
        specialAllowanceTimeLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialAllowanceTimeLine() throws Exception {
        int databaseSizeBeforeCreate = specialAllowanceTimeLineRepository.findAll().size();

        // Create the SpecialAllowanceTimeLine
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO = specialAllowanceTimeLineMapper.toDto(specialAllowanceTimeLine);
        restSpecialAllowanceTimeLineMockMvc.perform(post("/api/special-allowance-time-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialAllowanceTimeLineDTO)))
            .andExpect(status().isCreated());

        // Validate the SpecialAllowanceTimeLine in the database
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLineList = specialAllowanceTimeLineRepository.findAll();
        assertThat(specialAllowanceTimeLineList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialAllowanceTimeLine testSpecialAllowanceTimeLine = specialAllowanceTimeLineList.get(specialAllowanceTimeLineList.size() - 1);
        assertThat(testSpecialAllowanceTimeLine.getAllowanceType()).isEqualTo(DEFAULT_ALLOWANCE_TYPE);
        assertThat(testSpecialAllowanceTimeLine.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testSpecialAllowanceTimeLine.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSpecialAllowanceTimeLine.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSpecialAllowanceTimeLine.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSpecialAllowanceTimeLine.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the SpecialAllowanceTimeLine in Elasticsearch
        verify(mockSpecialAllowanceTimeLineSearchRepository, times(1)).save(testSpecialAllowanceTimeLine);
    }

    @Test
    @Transactional
    public void createSpecialAllowanceTimeLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialAllowanceTimeLineRepository.findAll().size();

        // Create the SpecialAllowanceTimeLine with an existing ID
        specialAllowanceTimeLine.setId(1L);
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO = specialAllowanceTimeLineMapper.toDto(specialAllowanceTimeLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialAllowanceTimeLineMockMvc.perform(post("/api/special-allowance-time-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialAllowanceTimeLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialAllowanceTimeLine in the database
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLineList = specialAllowanceTimeLineRepository.findAll();
        assertThat(specialAllowanceTimeLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the SpecialAllowanceTimeLine in Elasticsearch
        verify(mockSpecialAllowanceTimeLineSearchRepository, times(0)).save(specialAllowanceTimeLine);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLines() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialAllowanceTimeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSpecialAllowanceTimeLine() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get the specialAllowanceTimeLine
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines/{id}", specialAllowanceTimeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialAllowanceTimeLine.getId().intValue()))
            .andExpect(jsonPath("$.allowanceType").value(DEFAULT_ALLOWANCE_TYPE.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByAllowanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where allowanceType equals to DEFAULT_ALLOWANCE_TYPE
        defaultSpecialAllowanceTimeLineShouldBeFound("allowanceType.equals=" + DEFAULT_ALLOWANCE_TYPE);

        // Get all the specialAllowanceTimeLineList where allowanceType equals to UPDATED_ALLOWANCE_TYPE
        defaultSpecialAllowanceTimeLineShouldNotBeFound("allowanceType.equals=" + UPDATED_ALLOWANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByAllowanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where allowanceType in DEFAULT_ALLOWANCE_TYPE or UPDATED_ALLOWANCE_TYPE
        defaultSpecialAllowanceTimeLineShouldBeFound("allowanceType.in=" + DEFAULT_ALLOWANCE_TYPE + "," + UPDATED_ALLOWANCE_TYPE);

        // Get all the specialAllowanceTimeLineList where allowanceType equals to UPDATED_ALLOWANCE_TYPE
        defaultSpecialAllowanceTimeLineShouldNotBeFound("allowanceType.in=" + UPDATED_ALLOWANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByAllowanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where allowanceType is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("allowanceType.specified=true");

        // Get all the specialAllowanceTimeLineList where allowanceType is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("allowanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where religion equals to DEFAULT_RELIGION
        defaultSpecialAllowanceTimeLineShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the specialAllowanceTimeLineList where religion equals to UPDATED_RELIGION
        defaultSpecialAllowanceTimeLineShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultSpecialAllowanceTimeLineShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the specialAllowanceTimeLineList where religion equals to UPDATED_RELIGION
        defaultSpecialAllowanceTimeLineShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where religion is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("religion.specified=true");

        // Get all the specialAllowanceTimeLineList where religion is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where year equals to DEFAULT_YEAR
        defaultSpecialAllowanceTimeLineShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the specialAllowanceTimeLineList where year equals to UPDATED_YEAR
        defaultSpecialAllowanceTimeLineShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSpecialAllowanceTimeLineShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the specialAllowanceTimeLineList where year equals to UPDATED_YEAR
        defaultSpecialAllowanceTimeLineShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where year is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("year.specified=true");

        // Get all the specialAllowanceTimeLineList where year is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where year greater than or equals to DEFAULT_YEAR
        defaultSpecialAllowanceTimeLineShouldBeFound("year.greaterOrEqualThan=" + DEFAULT_YEAR);

        // Get all the specialAllowanceTimeLineList where year greater than or equals to UPDATED_YEAR
        defaultSpecialAllowanceTimeLineShouldNotBeFound("year.greaterOrEqualThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where year less than or equals to DEFAULT_YEAR
        defaultSpecialAllowanceTimeLineShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the specialAllowanceTimeLineList where year less than or equals to UPDATED_YEAR
        defaultSpecialAllowanceTimeLineShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }


    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where month equals to DEFAULT_MONTH
        defaultSpecialAllowanceTimeLineShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the specialAllowanceTimeLineList where month equals to UPDATED_MONTH
        defaultSpecialAllowanceTimeLineShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultSpecialAllowanceTimeLineShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the specialAllowanceTimeLineList where month equals to UPDATED_MONTH
        defaultSpecialAllowanceTimeLineShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where month is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("month.specified=true");

        // Get all the specialAllowanceTimeLineList where month is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the specialAllowanceTimeLineList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the specialAllowanceTimeLineList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedBy is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedBy.specified=true");

        // Get all the specialAllowanceTimeLineList where modifiedBy is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the specialAllowanceTimeLineList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the specialAllowanceTimeLineList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedOn is not null
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedOn.specified=true");

        // Get all the specialAllowanceTimeLineList where modifiedOn is null
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the specialAllowanceTimeLineList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSpecialAllowanceTimeLinesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        // Get all the specialAllowanceTimeLineList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the specialAllowanceTimeLineList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultSpecialAllowanceTimeLineShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSpecialAllowanceTimeLineShouldBeFound(String filter) throws Exception {
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialAllowanceTimeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSpecialAllowanceTimeLineShouldNotBeFound(String filter) throws Exception {
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSpecialAllowanceTimeLine() throws Exception {
        // Get the specialAllowanceTimeLine
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/special-allowance-time-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialAllowanceTimeLine() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        int databaseSizeBeforeUpdate = specialAllowanceTimeLineRepository.findAll().size();

        // Update the specialAllowanceTimeLine
        SpecialAllowanceTimeLine updatedSpecialAllowanceTimeLine = specialAllowanceTimeLineRepository.findById(specialAllowanceTimeLine.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialAllowanceTimeLine are not directly saved in db
        em.detach(updatedSpecialAllowanceTimeLine);
        updatedSpecialAllowanceTimeLine
            .allowanceType(UPDATED_ALLOWANCE_TYPE)
            .religion(UPDATED_RELIGION)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO = specialAllowanceTimeLineMapper.toDto(updatedSpecialAllowanceTimeLine);

        restSpecialAllowanceTimeLineMockMvc.perform(put("/api/special-allowance-time-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialAllowanceTimeLineDTO)))
            .andExpect(status().isOk());

        // Validate the SpecialAllowanceTimeLine in the database
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLineList = specialAllowanceTimeLineRepository.findAll();
        assertThat(specialAllowanceTimeLineList).hasSize(databaseSizeBeforeUpdate);
        SpecialAllowanceTimeLine testSpecialAllowanceTimeLine = specialAllowanceTimeLineList.get(specialAllowanceTimeLineList.size() - 1);
        assertThat(testSpecialAllowanceTimeLine.getAllowanceType()).isEqualTo(UPDATED_ALLOWANCE_TYPE);
        assertThat(testSpecialAllowanceTimeLine.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testSpecialAllowanceTimeLine.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSpecialAllowanceTimeLine.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSpecialAllowanceTimeLine.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSpecialAllowanceTimeLine.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the SpecialAllowanceTimeLine in Elasticsearch
        verify(mockSpecialAllowanceTimeLineSearchRepository, times(1)).save(testSpecialAllowanceTimeLine);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialAllowanceTimeLine() throws Exception {
        int databaseSizeBeforeUpdate = specialAllowanceTimeLineRepository.findAll().size();

        // Create the SpecialAllowanceTimeLine
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO = specialAllowanceTimeLineMapper.toDto(specialAllowanceTimeLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialAllowanceTimeLineMockMvc.perform(put("/api/special-allowance-time-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialAllowanceTimeLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialAllowanceTimeLine in the database
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLineList = specialAllowanceTimeLineRepository.findAll();
        assertThat(specialAllowanceTimeLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SpecialAllowanceTimeLine in Elasticsearch
        verify(mockSpecialAllowanceTimeLineSearchRepository, times(0)).save(specialAllowanceTimeLine);
    }

    @Test
    @Transactional
    public void deleteSpecialAllowanceTimeLine() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);

        int databaseSizeBeforeDelete = specialAllowanceTimeLineRepository.findAll().size();

        // Delete the specialAllowanceTimeLine
        restSpecialAllowanceTimeLineMockMvc.perform(delete("/api/special-allowance-time-lines/{id}", specialAllowanceTimeLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpecialAllowanceTimeLine> specialAllowanceTimeLineList = specialAllowanceTimeLineRepository.findAll();
        assertThat(specialAllowanceTimeLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SpecialAllowanceTimeLine in Elasticsearch
        verify(mockSpecialAllowanceTimeLineSearchRepository, times(1)).deleteById(specialAllowanceTimeLine.getId());
    }

    @Test
    @Transactional
    public void searchSpecialAllowanceTimeLine() throws Exception {
        // Initialize the database
        specialAllowanceTimeLineRepository.saveAndFlush(specialAllowanceTimeLine);
        when(mockSpecialAllowanceTimeLineSearchRepository.search(queryStringQuery("id:" + specialAllowanceTimeLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(specialAllowanceTimeLine), PageRequest.of(0, 1), 1));
        // Search the specialAllowanceTimeLine
        restSpecialAllowanceTimeLineMockMvc.perform(get("/api/_search/special-allowance-time-lines?query=id:" + specialAllowanceTimeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialAllowanceTimeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialAllowanceTimeLine.class);
        SpecialAllowanceTimeLine specialAllowanceTimeLine1 = new SpecialAllowanceTimeLine();
        specialAllowanceTimeLine1.setId(1L);
        SpecialAllowanceTimeLine specialAllowanceTimeLine2 = new SpecialAllowanceTimeLine();
        specialAllowanceTimeLine2.setId(specialAllowanceTimeLine1.getId());
        assertThat(specialAllowanceTimeLine1).isEqualTo(specialAllowanceTimeLine2);
        specialAllowanceTimeLine2.setId(2L);
        assertThat(specialAllowanceTimeLine1).isNotEqualTo(specialAllowanceTimeLine2);
        specialAllowanceTimeLine1.setId(null);
        assertThat(specialAllowanceTimeLine1).isNotEqualTo(specialAllowanceTimeLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialAllowanceTimeLineDTO.class);
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO1 = new SpecialAllowanceTimeLineDTO();
        specialAllowanceTimeLineDTO1.setId(1L);
        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO2 = new SpecialAllowanceTimeLineDTO();
        assertThat(specialAllowanceTimeLineDTO1).isNotEqualTo(specialAllowanceTimeLineDTO2);
        specialAllowanceTimeLineDTO2.setId(specialAllowanceTimeLineDTO1.getId());
        assertThat(specialAllowanceTimeLineDTO1).isEqualTo(specialAllowanceTimeLineDTO2);
        specialAllowanceTimeLineDTO2.setId(2L);
        assertThat(specialAllowanceTimeLineDTO1).isNotEqualTo(specialAllowanceTimeLineDTO2);
        specialAllowanceTimeLineDTO1.setId(null);
        assertThat(specialAllowanceTimeLineDTO1).isNotEqualTo(specialAllowanceTimeLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(specialAllowanceTimeLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(specialAllowanceTimeLineMapper.fromId(null)).isNull();
    }
}
