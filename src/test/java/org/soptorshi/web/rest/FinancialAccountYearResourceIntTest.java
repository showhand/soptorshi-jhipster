package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.FinancialAccountYearCriteria;
import org.soptorshi.service.FinancialAccountYearQueryService;

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

import org.soptorshi.domain.enumeration.FinancialYearStatus;
/**
 * Test class for the FinancialAccountYearResource REST controller.
 *
 * @see FinancialAccountYearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class FinancialAccountYearResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PREVIOUS_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREVIOUS_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PREVIOUS_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREVIOUS_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DURATION_STR = "AAAAAAAAAA";
    private static final String UPDATED_DURATION_STR = "BBBBBBBBBB";

    private static final FinancialYearStatus DEFAULT_STATUS = FinancialYearStatus.ACTIVE;
    private static final FinancialYearStatus UPDATED_STATUS = FinancialYearStatus.NOT_ACTIVE;

    @Autowired
    private FinancialAccountYearRepository financialAccountYearRepository;

    @Autowired
    private FinancialAccountYearMapper financialAccountYearMapper;

    @Autowired
    private FinancialAccountYearService financialAccountYearService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.FinancialAccountYearSearchRepositoryMockConfiguration
     */
    @Autowired
    private FinancialAccountYearSearchRepository mockFinancialAccountYearSearchRepository;

    @Autowired
    private FinancialAccountYearQueryService financialAccountYearQueryService;

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

    private MockMvc restFinancialAccountYearMockMvc;

    private FinancialAccountYear financialAccountYear;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FinancialAccountYearResource financialAccountYearResource = new FinancialAccountYearResource(financialAccountYearService, financialAccountYearQueryService);
        this.restFinancialAccountYearMockMvc = MockMvcBuilders.standaloneSetup(financialAccountYearResource)
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
    public static FinancialAccountYear createEntity(EntityManager em) {
        FinancialAccountYear financialAccountYear = new FinancialAccountYear()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .previousStartDate(DEFAULT_PREVIOUS_START_DATE)
            .previousEndDate(DEFAULT_PREVIOUS_END_DATE)
            .durationStr(DEFAULT_DURATION_STR)
            .status(DEFAULT_STATUS);
        return financialAccountYear;
    }

    @Before
    public void initTest() {
        financialAccountYear = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinancialAccountYear() throws Exception {
        int databaseSizeBeforeCreate = financialAccountYearRepository.findAll().size();

        // Create the FinancialAccountYear
        FinancialAccountYearDTO financialAccountYearDTO = financialAccountYearMapper.toDto(financialAccountYear);
        restFinancialAccountYearMockMvc.perform(post("/api/financial-account-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountYearDTO)))
            .andExpect(status().isCreated());

        // Validate the FinancialAccountYear in the database
        List<FinancialAccountYear> financialAccountYearList = financialAccountYearRepository.findAll();
        assertThat(financialAccountYearList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialAccountYear testFinancialAccountYear = financialAccountYearList.get(financialAccountYearList.size() - 1);
        assertThat(testFinancialAccountYear.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testFinancialAccountYear.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testFinancialAccountYear.getPreviousStartDate()).isEqualTo(DEFAULT_PREVIOUS_START_DATE);
        assertThat(testFinancialAccountYear.getPreviousEndDate()).isEqualTo(DEFAULT_PREVIOUS_END_DATE);
        assertThat(testFinancialAccountYear.getDurationStr()).isEqualTo(DEFAULT_DURATION_STR);
        assertThat(testFinancialAccountYear.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the FinancialAccountYear in Elasticsearch
        verify(mockFinancialAccountYearSearchRepository, times(1)).save(testFinancialAccountYear);
    }

    @Test
    @Transactional
    public void createFinancialAccountYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financialAccountYearRepository.findAll().size();

        // Create the FinancialAccountYear with an existing ID
        financialAccountYear.setId(1L);
        FinancialAccountYearDTO financialAccountYearDTO = financialAccountYearMapper.toDto(financialAccountYear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialAccountYearMockMvc.perform(post("/api/financial-account-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialAccountYear in the database
        List<FinancialAccountYear> financialAccountYearList = financialAccountYearRepository.findAll();
        assertThat(financialAccountYearList).hasSize(databaseSizeBeforeCreate);

        // Validate the FinancialAccountYear in Elasticsearch
        verify(mockFinancialAccountYearSearchRepository, times(0)).save(financialAccountYear);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYears() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialAccountYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousStartDate").value(hasItem(DEFAULT_PREVIOUS_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousEndDate").value(hasItem(DEFAULT_PREVIOUS_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].durationStr").value(hasItem(DEFAULT_DURATION_STR.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFinancialAccountYear() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get the financialAccountYear
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years/{id}", financialAccountYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(financialAccountYear.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.previousStartDate").value(DEFAULT_PREVIOUS_START_DATE.toString()))
            .andExpect(jsonPath("$.previousEndDate").value(DEFAULT_PREVIOUS_END_DATE.toString()))
            .andExpect(jsonPath("$.durationStr").value(DEFAULT_DURATION_STR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where startDate equals to DEFAULT_START_DATE
        defaultFinancialAccountYearShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the financialAccountYearList where startDate equals to UPDATED_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultFinancialAccountYearShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the financialAccountYearList where startDate equals to UPDATED_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where startDate is not null
        defaultFinancialAccountYearShouldBeFound("startDate.specified=true");

        // Get all the financialAccountYearList where startDate is null
        defaultFinancialAccountYearShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where startDate greater than or equals to DEFAULT_START_DATE
        defaultFinancialAccountYearShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the financialAccountYearList where startDate greater than or equals to UPDATED_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where startDate less than or equals to DEFAULT_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the financialAccountYearList where startDate less than or equals to UPDATED_START_DATE
        defaultFinancialAccountYearShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllFinancialAccountYearsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where endDate equals to DEFAULT_END_DATE
        defaultFinancialAccountYearShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the financialAccountYearList where endDate equals to UPDATED_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultFinancialAccountYearShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the financialAccountYearList where endDate equals to UPDATED_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where endDate is not null
        defaultFinancialAccountYearShouldBeFound("endDate.specified=true");

        // Get all the financialAccountYearList where endDate is null
        defaultFinancialAccountYearShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where endDate greater than or equals to DEFAULT_END_DATE
        defaultFinancialAccountYearShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the financialAccountYearList where endDate greater than or equals to UPDATED_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where endDate less than or equals to DEFAULT_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the financialAccountYearList where endDate less than or equals to UPDATED_END_DATE
        defaultFinancialAccountYearShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousStartDate equals to DEFAULT_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldBeFound("previousStartDate.equals=" + DEFAULT_PREVIOUS_START_DATE);

        // Get all the financialAccountYearList where previousStartDate equals to UPDATED_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousStartDate.equals=" + UPDATED_PREVIOUS_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousStartDate in DEFAULT_PREVIOUS_START_DATE or UPDATED_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldBeFound("previousStartDate.in=" + DEFAULT_PREVIOUS_START_DATE + "," + UPDATED_PREVIOUS_START_DATE);

        // Get all the financialAccountYearList where previousStartDate equals to UPDATED_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousStartDate.in=" + UPDATED_PREVIOUS_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousStartDate is not null
        defaultFinancialAccountYearShouldBeFound("previousStartDate.specified=true");

        // Get all the financialAccountYearList where previousStartDate is null
        defaultFinancialAccountYearShouldNotBeFound("previousStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousStartDate greater than or equals to DEFAULT_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldBeFound("previousStartDate.greaterOrEqualThan=" + DEFAULT_PREVIOUS_START_DATE);

        // Get all the financialAccountYearList where previousStartDate greater than or equals to UPDATED_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousStartDate.greaterOrEqualThan=" + UPDATED_PREVIOUS_START_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousStartDate less than or equals to DEFAULT_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousStartDate.lessThan=" + DEFAULT_PREVIOUS_START_DATE);

        // Get all the financialAccountYearList where previousStartDate less than or equals to UPDATED_PREVIOUS_START_DATE
        defaultFinancialAccountYearShouldBeFound("previousStartDate.lessThan=" + UPDATED_PREVIOUS_START_DATE);
    }


    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousEndDate equals to DEFAULT_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldBeFound("previousEndDate.equals=" + DEFAULT_PREVIOUS_END_DATE);

        // Get all the financialAccountYearList where previousEndDate equals to UPDATED_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousEndDate.equals=" + UPDATED_PREVIOUS_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousEndDate in DEFAULT_PREVIOUS_END_DATE or UPDATED_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldBeFound("previousEndDate.in=" + DEFAULT_PREVIOUS_END_DATE + "," + UPDATED_PREVIOUS_END_DATE);

        // Get all the financialAccountYearList where previousEndDate equals to UPDATED_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousEndDate.in=" + UPDATED_PREVIOUS_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousEndDate is not null
        defaultFinancialAccountYearShouldBeFound("previousEndDate.specified=true");

        // Get all the financialAccountYearList where previousEndDate is null
        defaultFinancialAccountYearShouldNotBeFound("previousEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousEndDate greater than or equals to DEFAULT_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldBeFound("previousEndDate.greaterOrEqualThan=" + DEFAULT_PREVIOUS_END_DATE);

        // Get all the financialAccountYearList where previousEndDate greater than or equals to UPDATED_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousEndDate.greaterOrEqualThan=" + UPDATED_PREVIOUS_END_DATE);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByPreviousEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where previousEndDate less than or equals to DEFAULT_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldNotBeFound("previousEndDate.lessThan=" + DEFAULT_PREVIOUS_END_DATE);

        // Get all the financialAccountYearList where previousEndDate less than or equals to UPDATED_PREVIOUS_END_DATE
        defaultFinancialAccountYearShouldBeFound("previousEndDate.lessThan=" + UPDATED_PREVIOUS_END_DATE);
    }


    @Test
    @Transactional
    public void getAllFinancialAccountYearsByDurationStrIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where durationStr equals to DEFAULT_DURATION_STR
        defaultFinancialAccountYearShouldBeFound("durationStr.equals=" + DEFAULT_DURATION_STR);

        // Get all the financialAccountYearList where durationStr equals to UPDATED_DURATION_STR
        defaultFinancialAccountYearShouldNotBeFound("durationStr.equals=" + UPDATED_DURATION_STR);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByDurationStrIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where durationStr in DEFAULT_DURATION_STR or UPDATED_DURATION_STR
        defaultFinancialAccountYearShouldBeFound("durationStr.in=" + DEFAULT_DURATION_STR + "," + UPDATED_DURATION_STR);

        // Get all the financialAccountYearList where durationStr equals to UPDATED_DURATION_STR
        defaultFinancialAccountYearShouldNotBeFound("durationStr.in=" + UPDATED_DURATION_STR);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByDurationStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where durationStr is not null
        defaultFinancialAccountYearShouldBeFound("durationStr.specified=true");

        // Get all the financialAccountYearList where durationStr is null
        defaultFinancialAccountYearShouldNotBeFound("durationStr.specified=false");
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where status equals to DEFAULT_STATUS
        defaultFinancialAccountYearShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the financialAccountYearList where status equals to UPDATED_STATUS
        defaultFinancialAccountYearShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultFinancialAccountYearShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the financialAccountYearList where status equals to UPDATED_STATUS
        defaultFinancialAccountYearShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllFinancialAccountYearsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        // Get all the financialAccountYearList where status is not null
        defaultFinancialAccountYearShouldBeFound("status.specified=true");

        // Get all the financialAccountYearList where status is null
        defaultFinancialAccountYearShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFinancialAccountYearShouldBeFound(String filter) throws Exception {
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialAccountYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousStartDate").value(hasItem(DEFAULT_PREVIOUS_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousEndDate").value(hasItem(DEFAULT_PREVIOUS_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].durationStr").value(hasItem(DEFAULT_DURATION_STR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFinancialAccountYearShouldNotBeFound(String filter) throws Exception {
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFinancialAccountYear() throws Exception {
        // Get the financialAccountYear
        restFinancialAccountYearMockMvc.perform(get("/api/financial-account-years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinancialAccountYear() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        int databaseSizeBeforeUpdate = financialAccountYearRepository.findAll().size();

        // Update the financialAccountYear
        FinancialAccountYear updatedFinancialAccountYear = financialAccountYearRepository.findById(financialAccountYear.getId()).get();
        // Disconnect from session so that the updates on updatedFinancialAccountYear are not directly saved in db
        em.detach(updatedFinancialAccountYear);
        updatedFinancialAccountYear
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .previousStartDate(UPDATED_PREVIOUS_START_DATE)
            .previousEndDate(UPDATED_PREVIOUS_END_DATE)
            .durationStr(UPDATED_DURATION_STR)
            .status(UPDATED_STATUS);
        FinancialAccountYearDTO financialAccountYearDTO = financialAccountYearMapper.toDto(updatedFinancialAccountYear);

        restFinancialAccountYearMockMvc.perform(put("/api/financial-account-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountYearDTO)))
            .andExpect(status().isOk());

        // Validate the FinancialAccountYear in the database
        List<FinancialAccountYear> financialAccountYearList = financialAccountYearRepository.findAll();
        assertThat(financialAccountYearList).hasSize(databaseSizeBeforeUpdate);
        FinancialAccountYear testFinancialAccountYear = financialAccountYearList.get(financialAccountYearList.size() - 1);
        assertThat(testFinancialAccountYear.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testFinancialAccountYear.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testFinancialAccountYear.getPreviousStartDate()).isEqualTo(UPDATED_PREVIOUS_START_DATE);
        assertThat(testFinancialAccountYear.getPreviousEndDate()).isEqualTo(UPDATED_PREVIOUS_END_DATE);
        assertThat(testFinancialAccountYear.getDurationStr()).isEqualTo(UPDATED_DURATION_STR);
        assertThat(testFinancialAccountYear.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the FinancialAccountYear in Elasticsearch
        verify(mockFinancialAccountYearSearchRepository, times(1)).save(testFinancialAccountYear);
    }

    @Test
    @Transactional
    public void updateNonExistingFinancialAccountYear() throws Exception {
        int databaseSizeBeforeUpdate = financialAccountYearRepository.findAll().size();

        // Create the FinancialAccountYear
        FinancialAccountYearDTO financialAccountYearDTO = financialAccountYearMapper.toDto(financialAccountYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancialAccountYearMockMvc.perform(put("/api/financial-account-years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(financialAccountYearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinancialAccountYear in the database
        List<FinancialAccountYear> financialAccountYearList = financialAccountYearRepository.findAll();
        assertThat(financialAccountYearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FinancialAccountYear in Elasticsearch
        verify(mockFinancialAccountYearSearchRepository, times(0)).save(financialAccountYear);
    }

    @Test
    @Transactional
    public void deleteFinancialAccountYear() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);

        int databaseSizeBeforeDelete = financialAccountYearRepository.findAll().size();

        // Delete the financialAccountYear
        restFinancialAccountYearMockMvc.perform(delete("/api/financial-account-years/{id}", financialAccountYear.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FinancialAccountYear> financialAccountYearList = financialAccountYearRepository.findAll();
        assertThat(financialAccountYearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FinancialAccountYear in Elasticsearch
        verify(mockFinancialAccountYearSearchRepository, times(1)).deleteById(financialAccountYear.getId());
    }

    @Test
    @Transactional
    public void searchFinancialAccountYear() throws Exception {
        // Initialize the database
        financialAccountYearRepository.saveAndFlush(financialAccountYear);
        when(mockFinancialAccountYearSearchRepository.search(queryStringQuery("id:" + financialAccountYear.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(financialAccountYear), PageRequest.of(0, 1), 1));
        // Search the financialAccountYear
        restFinancialAccountYearMockMvc.perform(get("/api/_search/financial-account-years?query=id:" + financialAccountYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialAccountYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousStartDate").value(hasItem(DEFAULT_PREVIOUS_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].previousEndDate").value(hasItem(DEFAULT_PREVIOUS_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].durationStr").value(hasItem(DEFAULT_DURATION_STR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialAccountYear.class);
        FinancialAccountYear financialAccountYear1 = new FinancialAccountYear();
        financialAccountYear1.setId(1L);
        FinancialAccountYear financialAccountYear2 = new FinancialAccountYear();
        financialAccountYear2.setId(financialAccountYear1.getId());
        assertThat(financialAccountYear1).isEqualTo(financialAccountYear2);
        financialAccountYear2.setId(2L);
        assertThat(financialAccountYear1).isNotEqualTo(financialAccountYear2);
        financialAccountYear1.setId(null);
        assertThat(financialAccountYear1).isNotEqualTo(financialAccountYear2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinancialAccountYearDTO.class);
        FinancialAccountYearDTO financialAccountYearDTO1 = new FinancialAccountYearDTO();
        financialAccountYearDTO1.setId(1L);
        FinancialAccountYearDTO financialAccountYearDTO2 = new FinancialAccountYearDTO();
        assertThat(financialAccountYearDTO1).isNotEqualTo(financialAccountYearDTO2);
        financialAccountYearDTO2.setId(financialAccountYearDTO1.getId());
        assertThat(financialAccountYearDTO1).isEqualTo(financialAccountYearDTO2);
        financialAccountYearDTO2.setId(2L);
        assertThat(financialAccountYearDTO1).isNotEqualTo(financialAccountYearDTO2);
        financialAccountYearDTO1.setId(null);
        assertThat(financialAccountYearDTO1).isNotEqualTo(financialAccountYearDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(financialAccountYearMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(financialAccountYearMapper.fromId(null)).isNull();
    }
}
