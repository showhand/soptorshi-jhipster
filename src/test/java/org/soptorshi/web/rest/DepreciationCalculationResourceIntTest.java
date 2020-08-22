package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DepreciationCalculation;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.DepreciationCalculationRepository;
import org.soptorshi.repository.search.DepreciationCalculationSearchRepository;
import org.soptorshi.service.DepreciationCalculationService;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.service.mapper.DepreciationCalculationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DepreciationCalculationCriteria;
import org.soptorshi.service.DepreciationCalculationQueryService;

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
import java.time.Instant;
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

import org.soptorshi.domain.enumeration.MonthType;
/**
 * Test class for the DepreciationCalculationResource REST controller.
 *
 * @see DepreciationCalculationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DepreciationCalculationResourceIntTest {

    private static final MonthType DEFAULT_MONTH_TYPE = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH_TYPE = MonthType.FEBRUARY;

    private static final Boolean DEFAULT_IS_EXECUTED = false;
    private static final Boolean UPDATED_IS_EXECUTED = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DepreciationCalculationRepository depreciationCalculationRepository;

    @Autowired
    private DepreciationCalculationMapper depreciationCalculationMapper;

    @Autowired
    private DepreciationCalculationService depreciationCalculationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DepreciationCalculationSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepreciationCalculationSearchRepository mockDepreciationCalculationSearchRepository;

    @Autowired
    private DepreciationCalculationQueryService depreciationCalculationQueryService;

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

    private MockMvc restDepreciationCalculationMockMvc;

    private DepreciationCalculation depreciationCalculation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepreciationCalculationResource depreciationCalculationResource = new DepreciationCalculationResource(depreciationCalculationService, depreciationCalculationQueryService);
        this.restDepreciationCalculationMockMvc = MockMvcBuilders.standaloneSetup(depreciationCalculationResource)
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
    public static DepreciationCalculation createEntity(EntityManager em) {
        DepreciationCalculation depreciationCalculation = new DepreciationCalculation()
            .monthType(DEFAULT_MONTH_TYPE)
            .isExecuted(DEFAULT_IS_EXECUTED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return depreciationCalculation;
    }

    @Before
    public void initTest() {
        depreciationCalculation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepreciationCalculation() throws Exception {
        int databaseSizeBeforeCreate = depreciationCalculationRepository.findAll().size();

        // Create the DepreciationCalculation
        DepreciationCalculationDTO depreciationCalculationDTO = depreciationCalculationMapper.toDto(depreciationCalculation);
        restDepreciationCalculationMockMvc.perform(post("/api/depreciation-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationCalculationDTO)))
            .andExpect(status().isCreated());

        // Validate the DepreciationCalculation in the database
        List<DepreciationCalculation> depreciationCalculationList = depreciationCalculationRepository.findAll();
        assertThat(depreciationCalculationList).hasSize(databaseSizeBeforeCreate + 1);
        DepreciationCalculation testDepreciationCalculation = depreciationCalculationList.get(depreciationCalculationList.size() - 1);
        assertThat(testDepreciationCalculation.getMonthType()).isEqualTo(DEFAULT_MONTH_TYPE);
        assertThat(testDepreciationCalculation.isIsExecuted()).isEqualTo(DEFAULT_IS_EXECUTED);
        assertThat(testDepreciationCalculation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDepreciationCalculation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDepreciationCalculation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDepreciationCalculation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the DepreciationCalculation in Elasticsearch
        verify(mockDepreciationCalculationSearchRepository, times(1)).save(testDepreciationCalculation);
    }

    @Test
    @Transactional
    public void createDepreciationCalculationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depreciationCalculationRepository.findAll().size();

        // Create the DepreciationCalculation with an existing ID
        depreciationCalculation.setId(1L);
        DepreciationCalculationDTO depreciationCalculationDTO = depreciationCalculationMapper.toDto(depreciationCalculation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepreciationCalculationMockMvc.perform(post("/api/depreciation-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationCalculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationCalculation in the database
        List<DepreciationCalculation> depreciationCalculationList = depreciationCalculationRepository.findAll();
        assertThat(depreciationCalculationList).hasSize(databaseSizeBeforeCreate);

        // Validate the DepreciationCalculation in Elasticsearch
        verify(mockDepreciationCalculationSearchRepository, times(0)).save(depreciationCalculation);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculations() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationCalculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isExecuted").value(hasItem(DEFAULT_IS_EXECUTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getDepreciationCalculation() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get the depreciationCalculation
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations/{id}", depreciationCalculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depreciationCalculation.getId().intValue()))
            .andExpect(jsonPath("$.monthType").value(DEFAULT_MONTH_TYPE.toString()))
            .andExpect(jsonPath("$.isExecuted").value(DEFAULT_IS_EXECUTED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByMonthTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where monthType equals to DEFAULT_MONTH_TYPE
        defaultDepreciationCalculationShouldBeFound("monthType.equals=" + DEFAULT_MONTH_TYPE);

        // Get all the depreciationCalculationList where monthType equals to UPDATED_MONTH_TYPE
        defaultDepreciationCalculationShouldNotBeFound("monthType.equals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByMonthTypeIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where monthType in DEFAULT_MONTH_TYPE or UPDATED_MONTH_TYPE
        defaultDepreciationCalculationShouldBeFound("monthType.in=" + DEFAULT_MONTH_TYPE + "," + UPDATED_MONTH_TYPE);

        // Get all the depreciationCalculationList where monthType equals to UPDATED_MONTH_TYPE
        defaultDepreciationCalculationShouldNotBeFound("monthType.in=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByMonthTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where monthType is not null
        defaultDepreciationCalculationShouldBeFound("monthType.specified=true");

        // Get all the depreciationCalculationList where monthType is null
        defaultDepreciationCalculationShouldNotBeFound("monthType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByIsExecutedIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where isExecuted equals to DEFAULT_IS_EXECUTED
        defaultDepreciationCalculationShouldBeFound("isExecuted.equals=" + DEFAULT_IS_EXECUTED);

        // Get all the depreciationCalculationList where isExecuted equals to UPDATED_IS_EXECUTED
        defaultDepreciationCalculationShouldNotBeFound("isExecuted.equals=" + UPDATED_IS_EXECUTED);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByIsExecutedIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where isExecuted in DEFAULT_IS_EXECUTED or UPDATED_IS_EXECUTED
        defaultDepreciationCalculationShouldBeFound("isExecuted.in=" + DEFAULT_IS_EXECUTED + "," + UPDATED_IS_EXECUTED);

        // Get all the depreciationCalculationList where isExecuted equals to UPDATED_IS_EXECUTED
        defaultDepreciationCalculationShouldNotBeFound("isExecuted.in=" + UPDATED_IS_EXECUTED);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByIsExecutedIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where isExecuted is not null
        defaultDepreciationCalculationShouldBeFound("isExecuted.specified=true");

        // Get all the depreciationCalculationList where isExecuted is null
        defaultDepreciationCalculationShouldNotBeFound("isExecuted.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdBy equals to DEFAULT_CREATED_BY
        defaultDepreciationCalculationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the depreciationCalculationList where createdBy equals to UPDATED_CREATED_BY
        defaultDepreciationCalculationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDepreciationCalculationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the depreciationCalculationList where createdBy equals to UPDATED_CREATED_BY
        defaultDepreciationCalculationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdBy is not null
        defaultDepreciationCalculationShouldBeFound("createdBy.specified=true");

        // Get all the depreciationCalculationList where createdBy is null
        defaultDepreciationCalculationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdOn equals to DEFAULT_CREATED_ON
        defaultDepreciationCalculationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the depreciationCalculationList where createdOn equals to UPDATED_CREATED_ON
        defaultDepreciationCalculationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDepreciationCalculationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the depreciationCalculationList where createdOn equals to UPDATED_CREATED_ON
        defaultDepreciationCalculationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where createdOn is not null
        defaultDepreciationCalculationShouldBeFound("createdOn.specified=true");

        // Get all the depreciationCalculationList where createdOn is null
        defaultDepreciationCalculationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDepreciationCalculationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the depreciationCalculationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDepreciationCalculationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDepreciationCalculationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the depreciationCalculationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDepreciationCalculationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedBy is not null
        defaultDepreciationCalculationShouldBeFound("modifiedBy.specified=true");

        // Get all the depreciationCalculationList where modifiedBy is null
        defaultDepreciationCalculationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultDepreciationCalculationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the depreciationCalculationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDepreciationCalculationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultDepreciationCalculationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the depreciationCalculationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDepreciationCalculationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        // Get all the depreciationCalculationList where modifiedOn is not null
        defaultDepreciationCalculationShouldBeFound("modifiedOn.specified=true");

        // Get all the depreciationCalculationList where modifiedOn is null
        defaultDepreciationCalculationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationCalculationsByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        depreciationCalculation.setFinancialAccountYear(financialAccountYear);
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the depreciationCalculationList where financialAccountYear equals to financialAccountYearId
        defaultDepreciationCalculationShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the depreciationCalculationList where financialAccountYear equals to financialAccountYearId + 1
        defaultDepreciationCalculationShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDepreciationCalculationShouldBeFound(String filter) throws Exception {
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationCalculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isExecuted").value(hasItem(DEFAULT_IS_EXECUTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDepreciationCalculationShouldNotBeFound(String filter) throws Exception {
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepreciationCalculation() throws Exception {
        // Get the depreciationCalculation
        restDepreciationCalculationMockMvc.perform(get("/api/depreciation-calculations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepreciationCalculation() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        int databaseSizeBeforeUpdate = depreciationCalculationRepository.findAll().size();

        // Update the depreciationCalculation
        DepreciationCalculation updatedDepreciationCalculation = depreciationCalculationRepository.findById(depreciationCalculation.getId()).get();
        // Disconnect from session so that the updates on updatedDepreciationCalculation are not directly saved in db
        em.detach(updatedDepreciationCalculation);
        updatedDepreciationCalculation
            .monthType(UPDATED_MONTH_TYPE)
            .isExecuted(UPDATED_IS_EXECUTED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        DepreciationCalculationDTO depreciationCalculationDTO = depreciationCalculationMapper.toDto(updatedDepreciationCalculation);

        restDepreciationCalculationMockMvc.perform(put("/api/depreciation-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationCalculationDTO)))
            .andExpect(status().isOk());

        // Validate the DepreciationCalculation in the database
        List<DepreciationCalculation> depreciationCalculationList = depreciationCalculationRepository.findAll();
        assertThat(depreciationCalculationList).hasSize(databaseSizeBeforeUpdate);
        DepreciationCalculation testDepreciationCalculation = depreciationCalculationList.get(depreciationCalculationList.size() - 1);
        assertThat(testDepreciationCalculation.getMonthType()).isEqualTo(UPDATED_MONTH_TYPE);
        assertThat(testDepreciationCalculation.isIsExecuted()).isEqualTo(UPDATED_IS_EXECUTED);
        assertThat(testDepreciationCalculation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepreciationCalculation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDepreciationCalculation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDepreciationCalculation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the DepreciationCalculation in Elasticsearch
        verify(mockDepreciationCalculationSearchRepository, times(1)).save(testDepreciationCalculation);
    }

    @Test
    @Transactional
    public void updateNonExistingDepreciationCalculation() throws Exception {
        int databaseSizeBeforeUpdate = depreciationCalculationRepository.findAll().size();

        // Create the DepreciationCalculation
        DepreciationCalculationDTO depreciationCalculationDTO = depreciationCalculationMapper.toDto(depreciationCalculation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepreciationCalculationMockMvc.perform(put("/api/depreciation-calculations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationCalculationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationCalculation in the database
        List<DepreciationCalculation> depreciationCalculationList = depreciationCalculationRepository.findAll();
        assertThat(depreciationCalculationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DepreciationCalculation in Elasticsearch
        verify(mockDepreciationCalculationSearchRepository, times(0)).save(depreciationCalculation);
    }

    @Test
    @Transactional
    public void deleteDepreciationCalculation() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);

        int databaseSizeBeforeDelete = depreciationCalculationRepository.findAll().size();

        // Delete the depreciationCalculation
        restDepreciationCalculationMockMvc.perform(delete("/api/depreciation-calculations/{id}", depreciationCalculation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepreciationCalculation> depreciationCalculationList = depreciationCalculationRepository.findAll();
        assertThat(depreciationCalculationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DepreciationCalculation in Elasticsearch
        verify(mockDepreciationCalculationSearchRepository, times(1)).deleteById(depreciationCalculation.getId());
    }

    @Test
    @Transactional
    public void searchDepreciationCalculation() throws Exception {
        // Initialize the database
        depreciationCalculationRepository.saveAndFlush(depreciationCalculation);
        when(mockDepreciationCalculationSearchRepository.search(queryStringQuery("id:" + depreciationCalculation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(depreciationCalculation), PageRequest.of(0, 1), 1));
        // Search the depreciationCalculation
        restDepreciationCalculationMockMvc.perform(get("/api/_search/depreciation-calculations?query=id:" + depreciationCalculation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationCalculation.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isExecuted").value(hasItem(DEFAULT_IS_EXECUTED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationCalculation.class);
        DepreciationCalculation depreciationCalculation1 = new DepreciationCalculation();
        depreciationCalculation1.setId(1L);
        DepreciationCalculation depreciationCalculation2 = new DepreciationCalculation();
        depreciationCalculation2.setId(depreciationCalculation1.getId());
        assertThat(depreciationCalculation1).isEqualTo(depreciationCalculation2);
        depreciationCalculation2.setId(2L);
        assertThat(depreciationCalculation1).isNotEqualTo(depreciationCalculation2);
        depreciationCalculation1.setId(null);
        assertThat(depreciationCalculation1).isNotEqualTo(depreciationCalculation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationCalculationDTO.class);
        DepreciationCalculationDTO depreciationCalculationDTO1 = new DepreciationCalculationDTO();
        depreciationCalculationDTO1.setId(1L);
        DepreciationCalculationDTO depreciationCalculationDTO2 = new DepreciationCalculationDTO();
        assertThat(depreciationCalculationDTO1).isNotEqualTo(depreciationCalculationDTO2);
        depreciationCalculationDTO2.setId(depreciationCalculationDTO1.getId());
        assertThat(depreciationCalculationDTO1).isEqualTo(depreciationCalculationDTO2);
        depreciationCalculationDTO2.setId(2L);
        assertThat(depreciationCalculationDTO1).isNotEqualTo(depreciationCalculationDTO2);
        depreciationCalculationDTO1.setId(null);
        assertThat(depreciationCalculationDTO1).isNotEqualTo(depreciationCalculationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depreciationCalculationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depreciationCalculationMapper.fromId(null)).isNull();
    }
}
