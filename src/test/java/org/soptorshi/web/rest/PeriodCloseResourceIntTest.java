package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PeriodClose;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.PeriodCloseRepository;
import org.soptorshi.repository.search.PeriodCloseSearchRepository;
import org.soptorshi.service.PeriodCloseService;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.mapper.PeriodCloseMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PeriodCloseCriteria;
import org.soptorshi.service.PeriodCloseQueryService;

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

import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.PeriodCloseFlag;
/**
 * Test class for the PeriodCloseResource REST controller.
 *
 * @see PeriodCloseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PeriodCloseResourceIntTest {

    private static final MonthType DEFAULT_MONTH_TYPE = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH_TYPE = MonthType.FEBRUARY;

    private static final Integer DEFAULT_CLOSE_YEAR = 1;
    private static final Integer UPDATED_CLOSE_YEAR = 2;

    private static final PeriodCloseFlag DEFAULT_FLAG = PeriodCloseFlag.OPEN;
    private static final PeriodCloseFlag UPDATED_FLAG = PeriodCloseFlag.CLOSE;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PeriodCloseRepository periodCloseRepository;

    @Autowired
    private PeriodCloseMapper periodCloseMapper;

    @Autowired
    private PeriodCloseService periodCloseService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PeriodCloseSearchRepositoryMockConfiguration
     */
    @Autowired
    private PeriodCloseSearchRepository mockPeriodCloseSearchRepository;

    @Autowired
    private PeriodCloseQueryService periodCloseQueryService;

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

    private MockMvc restPeriodCloseMockMvc;

    private PeriodClose periodClose;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodCloseResource periodCloseResource = new PeriodCloseResource(periodCloseService, periodCloseQueryService);
        this.restPeriodCloseMockMvc = MockMvcBuilders.standaloneSetup(periodCloseResource)
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
    public static PeriodClose createEntity(EntityManager em) {
        PeriodClose periodClose = new PeriodClose()
            .monthType(DEFAULT_MONTH_TYPE)
            .closeYear(DEFAULT_CLOSE_YEAR)
            .flag(DEFAULT_FLAG)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return periodClose;
    }

    @Before
    public void initTest() {
        periodClose = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodClose() throws Exception {
        int databaseSizeBeforeCreate = periodCloseRepository.findAll().size();

        // Create the PeriodClose
        PeriodCloseDTO periodCloseDTO = periodCloseMapper.toDto(periodClose);
        restPeriodCloseMockMvc.perform(post("/api/period-closes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodCloseDTO)))
            .andExpect(status().isCreated());

        // Validate the PeriodClose in the database
        List<PeriodClose> periodCloseList = periodCloseRepository.findAll();
        assertThat(periodCloseList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodClose testPeriodClose = periodCloseList.get(periodCloseList.size() - 1);
        assertThat(testPeriodClose.getMonthType()).isEqualTo(DEFAULT_MONTH_TYPE);
        assertThat(testPeriodClose.getCloseYear()).isEqualTo(DEFAULT_CLOSE_YEAR);
        assertThat(testPeriodClose.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testPeriodClose.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPeriodClose.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the PeriodClose in Elasticsearch
        verify(mockPeriodCloseSearchRepository, times(1)).save(testPeriodClose);
    }

    @Test
    @Transactional
    public void createPeriodCloseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodCloseRepository.findAll().size();

        // Create the PeriodClose with an existing ID
        periodClose.setId(1L);
        PeriodCloseDTO periodCloseDTO = periodCloseMapper.toDto(periodClose);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodCloseMockMvc.perform(post("/api/period-closes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodCloseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodClose in the database
        List<PeriodClose> periodCloseList = periodCloseRepository.findAll();
        assertThat(periodCloseList).hasSize(databaseSizeBeforeCreate);

        // Validate the PeriodClose in Elasticsearch
        verify(mockPeriodCloseSearchRepository, times(0)).save(periodClose);
    }

    @Test
    @Transactional
    public void getAllPeriodCloses() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList
        restPeriodCloseMockMvc.perform(get("/api/period-closes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodClose.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].closeYear").value(hasItem(DEFAULT_CLOSE_YEAR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPeriodClose() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get the periodClose
        restPeriodCloseMockMvc.perform(get("/api/period-closes/{id}", periodClose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodClose.getId().intValue()))
            .andExpect(jsonPath("$.monthType").value(DEFAULT_MONTH_TYPE.toString()))
            .andExpect(jsonPath("$.closeYear").value(DEFAULT_CLOSE_YEAR))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByMonthTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where monthType equals to DEFAULT_MONTH_TYPE
        defaultPeriodCloseShouldBeFound("monthType.equals=" + DEFAULT_MONTH_TYPE);

        // Get all the periodCloseList where monthType equals to UPDATED_MONTH_TYPE
        defaultPeriodCloseShouldNotBeFound("monthType.equals=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByMonthTypeIsInShouldWork() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where monthType in DEFAULT_MONTH_TYPE or UPDATED_MONTH_TYPE
        defaultPeriodCloseShouldBeFound("monthType.in=" + DEFAULT_MONTH_TYPE + "," + UPDATED_MONTH_TYPE);

        // Get all the periodCloseList where monthType equals to UPDATED_MONTH_TYPE
        defaultPeriodCloseShouldNotBeFound("monthType.in=" + UPDATED_MONTH_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByMonthTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where monthType is not null
        defaultPeriodCloseShouldBeFound("monthType.specified=true");

        // Get all the periodCloseList where monthType is null
        defaultPeriodCloseShouldNotBeFound("monthType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByCloseYearIsEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where closeYear equals to DEFAULT_CLOSE_YEAR
        defaultPeriodCloseShouldBeFound("closeYear.equals=" + DEFAULT_CLOSE_YEAR);

        // Get all the periodCloseList where closeYear equals to UPDATED_CLOSE_YEAR
        defaultPeriodCloseShouldNotBeFound("closeYear.equals=" + UPDATED_CLOSE_YEAR);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByCloseYearIsInShouldWork() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where closeYear in DEFAULT_CLOSE_YEAR or UPDATED_CLOSE_YEAR
        defaultPeriodCloseShouldBeFound("closeYear.in=" + DEFAULT_CLOSE_YEAR + "," + UPDATED_CLOSE_YEAR);

        // Get all the periodCloseList where closeYear equals to UPDATED_CLOSE_YEAR
        defaultPeriodCloseShouldNotBeFound("closeYear.in=" + UPDATED_CLOSE_YEAR);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByCloseYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where closeYear is not null
        defaultPeriodCloseShouldBeFound("closeYear.specified=true");

        // Get all the periodCloseList where closeYear is null
        defaultPeriodCloseShouldNotBeFound("closeYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByCloseYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where closeYear greater than or equals to DEFAULT_CLOSE_YEAR
        defaultPeriodCloseShouldBeFound("closeYear.greaterOrEqualThan=" + DEFAULT_CLOSE_YEAR);

        // Get all the periodCloseList where closeYear greater than or equals to UPDATED_CLOSE_YEAR
        defaultPeriodCloseShouldNotBeFound("closeYear.greaterOrEqualThan=" + UPDATED_CLOSE_YEAR);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByCloseYearIsLessThanSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where closeYear less than or equals to DEFAULT_CLOSE_YEAR
        defaultPeriodCloseShouldNotBeFound("closeYear.lessThan=" + DEFAULT_CLOSE_YEAR);

        // Get all the periodCloseList where closeYear less than or equals to UPDATED_CLOSE_YEAR
        defaultPeriodCloseShouldBeFound("closeYear.lessThan=" + UPDATED_CLOSE_YEAR);
    }


    @Test
    @Transactional
    public void getAllPeriodClosesByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where flag equals to DEFAULT_FLAG
        defaultPeriodCloseShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the periodCloseList where flag equals to UPDATED_FLAG
        defaultPeriodCloseShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultPeriodCloseShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the periodCloseList where flag equals to UPDATED_FLAG
        defaultPeriodCloseShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where flag is not null
        defaultPeriodCloseShouldBeFound("flag.specified=true");

        // Get all the periodCloseList where flag is null
        defaultPeriodCloseShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultPeriodCloseShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the periodCloseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPeriodCloseShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultPeriodCloseShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the periodCloseList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPeriodCloseShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedBy is not null
        defaultPeriodCloseShouldBeFound("modifiedBy.specified=true");

        // Get all the periodCloseList where modifiedBy is null
        defaultPeriodCloseShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultPeriodCloseShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the periodCloseList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPeriodCloseShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultPeriodCloseShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the periodCloseList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPeriodCloseShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedOn is not null
        defaultPeriodCloseShouldBeFound("modifiedOn.specified=true");

        // Get all the periodCloseList where modifiedOn is null
        defaultPeriodCloseShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultPeriodCloseShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the periodCloseList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultPeriodCloseShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPeriodClosesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        // Get all the periodCloseList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultPeriodCloseShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the periodCloseList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultPeriodCloseShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllPeriodClosesByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        periodClose.setFinancialAccountYear(financialAccountYear);
        periodCloseRepository.saveAndFlush(periodClose);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the periodCloseList where financialAccountYear equals to financialAccountYearId
        defaultPeriodCloseShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the periodCloseList where financialAccountYear equals to financialAccountYearId + 1
        defaultPeriodCloseShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPeriodCloseShouldBeFound(String filter) throws Exception {
        restPeriodCloseMockMvc.perform(get("/api/period-closes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodClose.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].closeYear").value(hasItem(DEFAULT_CLOSE_YEAR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restPeriodCloseMockMvc.perform(get("/api/period-closes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPeriodCloseShouldNotBeFound(String filter) throws Exception {
        restPeriodCloseMockMvc.perform(get("/api/period-closes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodCloseMockMvc.perform(get("/api/period-closes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPeriodClose() throws Exception {
        // Get the periodClose
        restPeriodCloseMockMvc.perform(get("/api/period-closes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodClose() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        int databaseSizeBeforeUpdate = periodCloseRepository.findAll().size();

        // Update the periodClose
        PeriodClose updatedPeriodClose = periodCloseRepository.findById(periodClose.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodClose are not directly saved in db
        em.detach(updatedPeriodClose);
        updatedPeriodClose
            .monthType(UPDATED_MONTH_TYPE)
            .closeYear(UPDATED_CLOSE_YEAR)
            .flag(UPDATED_FLAG)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        PeriodCloseDTO periodCloseDTO = periodCloseMapper.toDto(updatedPeriodClose);

        restPeriodCloseMockMvc.perform(put("/api/period-closes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodCloseDTO)))
            .andExpect(status().isOk());

        // Validate the PeriodClose in the database
        List<PeriodClose> periodCloseList = periodCloseRepository.findAll();
        assertThat(periodCloseList).hasSize(databaseSizeBeforeUpdate);
        PeriodClose testPeriodClose = periodCloseList.get(periodCloseList.size() - 1);
        assertThat(testPeriodClose.getMonthType()).isEqualTo(UPDATED_MONTH_TYPE);
        assertThat(testPeriodClose.getCloseYear()).isEqualTo(UPDATED_CLOSE_YEAR);
        assertThat(testPeriodClose.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testPeriodClose.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPeriodClose.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the PeriodClose in Elasticsearch
        verify(mockPeriodCloseSearchRepository, times(1)).save(testPeriodClose);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodClose() throws Exception {
        int databaseSizeBeforeUpdate = periodCloseRepository.findAll().size();

        // Create the PeriodClose
        PeriodCloseDTO periodCloseDTO = periodCloseMapper.toDto(periodClose);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodCloseMockMvc.perform(put("/api/period-closes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodCloseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodClose in the database
        List<PeriodClose> periodCloseList = periodCloseRepository.findAll();
        assertThat(periodCloseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PeriodClose in Elasticsearch
        verify(mockPeriodCloseSearchRepository, times(0)).save(periodClose);
    }

    @Test
    @Transactional
    public void deletePeriodClose() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);

        int databaseSizeBeforeDelete = periodCloseRepository.findAll().size();

        // Delete the periodClose
        restPeriodCloseMockMvc.perform(delete("/api/period-closes/{id}", periodClose.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodClose> periodCloseList = periodCloseRepository.findAll();
        assertThat(periodCloseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PeriodClose in Elasticsearch
        verify(mockPeriodCloseSearchRepository, times(1)).deleteById(periodClose.getId());
    }

    @Test
    @Transactional
    public void searchPeriodClose() throws Exception {
        // Initialize the database
        periodCloseRepository.saveAndFlush(periodClose);
        when(mockPeriodCloseSearchRepository.search(queryStringQuery("id:" + periodClose.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(periodClose), PageRequest.of(0, 1), 1));
        // Search the periodClose
        restPeriodCloseMockMvc.perform(get("/api/_search/period-closes?query=id:" + periodClose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodClose.getId().intValue())))
            .andExpect(jsonPath("$.[*].monthType").value(hasItem(DEFAULT_MONTH_TYPE.toString())))
            .andExpect(jsonPath("$.[*].closeYear").value(hasItem(DEFAULT_CLOSE_YEAR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodClose.class);
        PeriodClose periodClose1 = new PeriodClose();
        periodClose1.setId(1L);
        PeriodClose periodClose2 = new PeriodClose();
        periodClose2.setId(periodClose1.getId());
        assertThat(periodClose1).isEqualTo(periodClose2);
        periodClose2.setId(2L);
        assertThat(periodClose1).isNotEqualTo(periodClose2);
        periodClose1.setId(null);
        assertThat(periodClose1).isNotEqualTo(periodClose2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodCloseDTO.class);
        PeriodCloseDTO periodCloseDTO1 = new PeriodCloseDTO();
        periodCloseDTO1.setId(1L);
        PeriodCloseDTO periodCloseDTO2 = new PeriodCloseDTO();
        assertThat(periodCloseDTO1).isNotEqualTo(periodCloseDTO2);
        periodCloseDTO2.setId(periodCloseDTO1.getId());
        assertThat(periodCloseDTO1).isEqualTo(periodCloseDTO2);
        periodCloseDTO2.setId(2L);
        assertThat(periodCloseDTO1).isNotEqualTo(periodCloseDTO2);
        periodCloseDTO1.setId(null);
        assertThat(periodCloseDTO1).isNotEqualTo(periodCloseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodCloseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodCloseMapper.fromId(null)).isNull();
    }
}
