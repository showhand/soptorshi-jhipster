package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.SalaryVoucherRelation;
import org.soptorshi.domain.Office;
import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.search.SalaryVoucherRelationSearchRepository;
import org.soptorshi.service.SalaryVoucherRelationService;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.mapper.SalaryVoucherRelationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SalaryVoucherRelationCriteria;
import org.soptorshi.service.SalaryVoucherRelationQueryService;

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
/**
 * Test class for the SalaryVoucherRelationResource REST controller.
 *
 * @see SalaryVoucherRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SalaryVoucherRelationResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SalaryVoucherRelationRepository salaryVoucherRelationRepository;

    @Autowired
    private SalaryVoucherRelationMapper salaryVoucherRelationMapper;

    @Autowired
    private SalaryVoucherRelationService salaryVoucherRelationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SalaryVoucherRelationSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalaryVoucherRelationSearchRepository mockSalaryVoucherRelationSearchRepository;

    @Autowired
    private SalaryVoucherRelationQueryService salaryVoucherRelationQueryService;

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

    private MockMvc restSalaryVoucherRelationMockMvc;

    private SalaryVoucherRelation salaryVoucherRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaryVoucherRelationResource salaryVoucherRelationResource = new SalaryVoucherRelationResource(salaryVoucherRelationService, salaryVoucherRelationQueryService);
        this.restSalaryVoucherRelationMockMvc = MockMvcBuilders.standaloneSetup(salaryVoucherRelationResource)
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
    public static SalaryVoucherRelation createEntity(EntityManager em) {
        SalaryVoucherRelation salaryVoucherRelation = new SalaryVoucherRelation()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .voucherNo(DEFAULT_VOUCHER_NO)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return salaryVoucherRelation;
    }

    @Before
    public void initTest() {
        salaryVoucherRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalaryVoucherRelation() throws Exception {
        int databaseSizeBeforeCreate = salaryVoucherRelationRepository.findAll().size();

        // Create the SalaryVoucherRelation
        SalaryVoucherRelationDTO salaryVoucherRelationDTO = salaryVoucherRelationMapper.toDto(salaryVoucherRelation);
        restSalaryVoucherRelationMockMvc.perform(post("/api/salary-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryVoucherRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the SalaryVoucherRelation in the database
        List<SalaryVoucherRelation> salaryVoucherRelationList = salaryVoucherRelationRepository.findAll();
        assertThat(salaryVoucherRelationList).hasSize(databaseSizeBeforeCreate + 1);
        SalaryVoucherRelation testSalaryVoucherRelation = salaryVoucherRelationList.get(salaryVoucherRelationList.size() - 1);
        assertThat(testSalaryVoucherRelation.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSalaryVoucherRelation.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSalaryVoucherRelation.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testSalaryVoucherRelation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSalaryVoucherRelation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the SalaryVoucherRelation in Elasticsearch
        verify(mockSalaryVoucherRelationSearchRepository, times(1)).save(testSalaryVoucherRelation);
    }

    @Test
    @Transactional
    public void createSalaryVoucherRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaryVoucherRelationRepository.findAll().size();

        // Create the SalaryVoucherRelation with an existing ID
        salaryVoucherRelation.setId(1L);
        SalaryVoucherRelationDTO salaryVoucherRelationDTO = salaryVoucherRelationMapper.toDto(salaryVoucherRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaryVoucherRelationMockMvc.perform(post("/api/salary-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalaryVoucherRelation in the database
        List<SalaryVoucherRelation> salaryVoucherRelationList = salaryVoucherRelationRepository.findAll();
        assertThat(salaryVoucherRelationList).hasSize(databaseSizeBeforeCreate);

        // Validate the SalaryVoucherRelation in Elasticsearch
        verify(mockSalaryVoucherRelationSearchRepository, times(0)).save(salaryVoucherRelation);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelations() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSalaryVoucherRelation() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get the salaryVoucherRelation
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations/{id}", salaryVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salaryVoucherRelation.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where year equals to DEFAULT_YEAR
        defaultSalaryVoucherRelationShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the salaryVoucherRelationList where year equals to UPDATED_YEAR
        defaultSalaryVoucherRelationShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSalaryVoucherRelationShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the salaryVoucherRelationList where year equals to UPDATED_YEAR
        defaultSalaryVoucherRelationShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where year is not null
        defaultSalaryVoucherRelationShouldBeFound("year.specified=true");

        // Get all the salaryVoucherRelationList where year is null
        defaultSalaryVoucherRelationShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where year greater than or equals to DEFAULT_YEAR
        defaultSalaryVoucherRelationShouldBeFound("year.greaterOrEqualThan=" + DEFAULT_YEAR);

        // Get all the salaryVoucherRelationList where year greater than or equals to UPDATED_YEAR
        defaultSalaryVoucherRelationShouldNotBeFound("year.greaterOrEqualThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where year less than or equals to DEFAULT_YEAR
        defaultSalaryVoucherRelationShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the salaryVoucherRelationList where year less than or equals to UPDATED_YEAR
        defaultSalaryVoucherRelationShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }


    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where month equals to DEFAULT_MONTH
        defaultSalaryVoucherRelationShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the salaryVoucherRelationList where month equals to UPDATED_MONTH
        defaultSalaryVoucherRelationShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultSalaryVoucherRelationShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the salaryVoucherRelationList where month equals to UPDATED_MONTH
        defaultSalaryVoucherRelationShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where month is not null
        defaultSalaryVoucherRelationShouldBeFound("month.specified=true");

        // Get all the salaryVoucherRelationList where month is null
        defaultSalaryVoucherRelationShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultSalaryVoucherRelationShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the salaryVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultSalaryVoucherRelationShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultSalaryVoucherRelationShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the salaryVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultSalaryVoucherRelationShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where voucherNo is not null
        defaultSalaryVoucherRelationShouldBeFound("voucherNo.specified=true");

        // Get all the salaryVoucherRelationList where voucherNo is null
        defaultSalaryVoucherRelationShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSalaryVoucherRelationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the salaryVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSalaryVoucherRelationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the salaryVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedBy is not null
        defaultSalaryVoucherRelationShouldBeFound("modifiedBy.specified=true");

        // Get all the salaryVoucherRelationList where modifiedBy is null
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultSalaryVoucherRelationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultSalaryVoucherRelationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the salaryVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedOn is not null
        defaultSalaryVoucherRelationShouldBeFound("modifiedOn.specified=true");

        // Get all the salaryVoucherRelationList where modifiedOn is null
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultSalaryVoucherRelationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryVoucherRelationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        // Get all the salaryVoucherRelationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultSalaryVoucherRelationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryVoucherRelationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultSalaryVoucherRelationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllSalaryVoucherRelationsByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        Office office = OfficeResourceIntTest.createEntity(em);
        em.persist(office);
        em.flush();
        salaryVoucherRelation.setOffice(office);
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);
        Long officeId = office.getId();

        // Get all the salaryVoucherRelationList where office equals to officeId
        defaultSalaryVoucherRelationShouldBeFound("officeId.equals=" + officeId);

        // Get all the salaryVoucherRelationList where office equals to officeId + 1
        defaultSalaryVoucherRelationShouldNotBeFound("officeId.equals=" + (officeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSalaryVoucherRelationShouldBeFound(String filter) throws Exception {
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSalaryVoucherRelationShouldNotBeFound(String filter) throws Exception {
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSalaryVoucherRelation() throws Exception {
        // Get the salaryVoucherRelation
        restSalaryVoucherRelationMockMvc.perform(get("/api/salary-voucher-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalaryVoucherRelation() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        int databaseSizeBeforeUpdate = salaryVoucherRelationRepository.findAll().size();

        // Update the salaryVoucherRelation
        SalaryVoucherRelation updatedSalaryVoucherRelation = salaryVoucherRelationRepository.findById(salaryVoucherRelation.getId()).get();
        // Disconnect from session so that the updates on updatedSalaryVoucherRelation are not directly saved in db
        em.detach(updatedSalaryVoucherRelation);
        updatedSalaryVoucherRelation
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .voucherNo(UPDATED_VOUCHER_NO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        SalaryVoucherRelationDTO salaryVoucherRelationDTO = salaryVoucherRelationMapper.toDto(updatedSalaryVoucherRelation);

        restSalaryVoucherRelationMockMvc.perform(put("/api/salary-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryVoucherRelationDTO)))
            .andExpect(status().isOk());

        // Validate the SalaryVoucherRelation in the database
        List<SalaryVoucherRelation> salaryVoucherRelationList = salaryVoucherRelationRepository.findAll();
        assertThat(salaryVoucherRelationList).hasSize(databaseSizeBeforeUpdate);
        SalaryVoucherRelation testSalaryVoucherRelation = salaryVoucherRelationList.get(salaryVoucherRelationList.size() - 1);
        assertThat(testSalaryVoucherRelation.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSalaryVoucherRelation.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSalaryVoucherRelation.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testSalaryVoucherRelation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSalaryVoucherRelation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the SalaryVoucherRelation in Elasticsearch
        verify(mockSalaryVoucherRelationSearchRepository, times(1)).save(testSalaryVoucherRelation);
    }

    @Test
    @Transactional
    public void updateNonExistingSalaryVoucherRelation() throws Exception {
        int databaseSizeBeforeUpdate = salaryVoucherRelationRepository.findAll().size();

        // Create the SalaryVoucherRelation
        SalaryVoucherRelationDTO salaryVoucherRelationDTO = salaryVoucherRelationMapper.toDto(salaryVoucherRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaryVoucherRelationMockMvc.perform(put("/api/salary-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalaryVoucherRelation in the database
        List<SalaryVoucherRelation> salaryVoucherRelationList = salaryVoucherRelationRepository.findAll();
        assertThat(salaryVoucherRelationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SalaryVoucherRelation in Elasticsearch
        verify(mockSalaryVoucherRelationSearchRepository, times(0)).save(salaryVoucherRelation);
    }

    @Test
    @Transactional
    public void deleteSalaryVoucherRelation() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);

        int databaseSizeBeforeDelete = salaryVoucherRelationRepository.findAll().size();

        // Delete the salaryVoucherRelation
        restSalaryVoucherRelationMockMvc.perform(delete("/api/salary-voucher-relations/{id}", salaryVoucherRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalaryVoucherRelation> salaryVoucherRelationList = salaryVoucherRelationRepository.findAll();
        assertThat(salaryVoucherRelationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SalaryVoucherRelation in Elasticsearch
        verify(mockSalaryVoucherRelationSearchRepository, times(1)).deleteById(salaryVoucherRelation.getId());
    }

    @Test
    @Transactional
    public void searchSalaryVoucherRelation() throws Exception {
        // Initialize the database
        salaryVoucherRelationRepository.saveAndFlush(salaryVoucherRelation);
        when(mockSalaryVoucherRelationSearchRepository.search(queryStringQuery("id:" + salaryVoucherRelation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(salaryVoucherRelation), PageRequest.of(0, 1), 1));
        // Search the salaryVoucherRelation
        restSalaryVoucherRelationMockMvc.perform(get("/api/_search/salary-voucher-relations?query=id:" + salaryVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaryVoucherRelation.class);
        SalaryVoucherRelation salaryVoucherRelation1 = new SalaryVoucherRelation();
        salaryVoucherRelation1.setId(1L);
        SalaryVoucherRelation salaryVoucherRelation2 = new SalaryVoucherRelation();
        salaryVoucherRelation2.setId(salaryVoucherRelation1.getId());
        assertThat(salaryVoucherRelation1).isEqualTo(salaryVoucherRelation2);
        salaryVoucherRelation2.setId(2L);
        assertThat(salaryVoucherRelation1).isNotEqualTo(salaryVoucherRelation2);
        salaryVoucherRelation1.setId(null);
        assertThat(salaryVoucherRelation1).isNotEqualTo(salaryVoucherRelation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaryVoucherRelationDTO.class);
        SalaryVoucherRelationDTO salaryVoucherRelationDTO1 = new SalaryVoucherRelationDTO();
        salaryVoucherRelationDTO1.setId(1L);
        SalaryVoucherRelationDTO salaryVoucherRelationDTO2 = new SalaryVoucherRelationDTO();
        assertThat(salaryVoucherRelationDTO1).isNotEqualTo(salaryVoucherRelationDTO2);
        salaryVoucherRelationDTO2.setId(salaryVoucherRelationDTO1.getId());
        assertThat(salaryVoucherRelationDTO1).isEqualTo(salaryVoucherRelationDTO2);
        salaryVoucherRelationDTO2.setId(2L);
        assertThat(salaryVoucherRelationDTO1).isNotEqualTo(salaryVoucherRelationDTO2);
        salaryVoucherRelationDTO1.setId(null);
        assertThat(salaryVoucherRelationDTO1).isNotEqualTo(salaryVoucherRelationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salaryVoucherRelationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salaryVoucherRelationMapper.fromId(null)).isNull();
    }
}
