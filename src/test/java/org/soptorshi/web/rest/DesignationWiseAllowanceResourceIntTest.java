package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DesignationWiseAllowance;
import org.soptorshi.domain.Designation;
import org.soptorshi.repository.DesignationWiseAllowanceRepository;
import org.soptorshi.repository.search.DesignationWiseAllowanceSearchRepository;
import org.soptorshi.service.DesignationWiseAllowanceService;
import org.soptorshi.service.dto.DesignationWiseAllowanceDTO;
import org.soptorshi.service.mapper.DesignationWiseAllowanceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DesignationWiseAllowanceCriteria;
import org.soptorshi.service.DesignationWiseAllowanceQueryService;

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

import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.AllowanceCategory;
/**
 * Test class for the DesignationWiseAllowanceResource REST controller.
 *
 * @see DesignationWiseAllowanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DesignationWiseAllowanceResourceIntTest {

    private static final AllowanceType DEFAULT_ALLOWANCE_TYPE = AllowanceType.HOUSE_RENT;
    private static final AllowanceType UPDATED_ALLOWANCE_TYPE = AllowanceType.MEDICAL_ALLOWANCE;

    private static final AllowanceCategory DEFAULT_ALLOWANCE_CATEGORY = AllowanceCategory.MONTHLY;
    private static final AllowanceCategory UPDATED_ALLOWANCE_CATEGORY = AllowanceCategory.SPECIFIC;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DesignationWiseAllowanceRepository designationWiseAllowanceRepository;

    @Autowired
    private DesignationWiseAllowanceMapper designationWiseAllowanceMapper;

    @Autowired
    private DesignationWiseAllowanceService designationWiseAllowanceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DesignationWiseAllowanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private DesignationWiseAllowanceSearchRepository mockDesignationWiseAllowanceSearchRepository;

    @Autowired
    private DesignationWiseAllowanceQueryService designationWiseAllowanceQueryService;

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

    private MockMvc restDesignationWiseAllowanceMockMvc;

    private DesignationWiseAllowance designationWiseAllowance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignationWiseAllowanceResource designationWiseAllowanceResource = new DesignationWiseAllowanceResource(designationWiseAllowanceService, designationWiseAllowanceQueryService);
        this.restDesignationWiseAllowanceMockMvc = MockMvcBuilders.standaloneSetup(designationWiseAllowanceResource)
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
    public static DesignationWiseAllowance createEntity(EntityManager em) {
        DesignationWiseAllowance designationWiseAllowance = new DesignationWiseAllowance()
            .allowanceType(DEFAULT_ALLOWANCE_TYPE)
            .allowanceCategory(DEFAULT_ALLOWANCE_CATEGORY)
            .amount(DEFAULT_AMOUNT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return designationWiseAllowance;
    }

    @Before
    public void initTest() {
        designationWiseAllowance = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignationWiseAllowance() throws Exception {
        int databaseSizeBeforeCreate = designationWiseAllowanceRepository.findAll().size();

        // Create the DesignationWiseAllowance
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO = designationWiseAllowanceMapper.toDto(designationWiseAllowance);
        restDesignationWiseAllowanceMockMvc.perform(post("/api/designation-wise-allowances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationWiseAllowanceDTO)))
            .andExpect(status().isCreated());

        // Validate the DesignationWiseAllowance in the database
        List<DesignationWiseAllowance> designationWiseAllowanceList = designationWiseAllowanceRepository.findAll();
        assertThat(designationWiseAllowanceList).hasSize(databaseSizeBeforeCreate + 1);
        DesignationWiseAllowance testDesignationWiseAllowance = designationWiseAllowanceList.get(designationWiseAllowanceList.size() - 1);
        assertThat(testDesignationWiseAllowance.getAllowanceType()).isEqualTo(DEFAULT_ALLOWANCE_TYPE);
        assertThat(testDesignationWiseAllowance.getAllowanceCategory()).isEqualTo(DEFAULT_ALLOWANCE_CATEGORY);
        assertThat(testDesignationWiseAllowance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDesignationWiseAllowance.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDesignationWiseAllowance.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the DesignationWiseAllowance in Elasticsearch
        verify(mockDesignationWiseAllowanceSearchRepository, times(1)).save(testDesignationWiseAllowance);
    }

    @Test
    @Transactional
    public void createDesignationWiseAllowanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationWiseAllowanceRepository.findAll().size();

        // Create the DesignationWiseAllowance with an existing ID
        designationWiseAllowance.setId(1L);
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO = designationWiseAllowanceMapper.toDto(designationWiseAllowance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationWiseAllowanceMockMvc.perform(post("/api/designation-wise-allowances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationWiseAllowanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DesignationWiseAllowance in the database
        List<DesignationWiseAllowance> designationWiseAllowanceList = designationWiseAllowanceRepository.findAll();
        assertThat(designationWiseAllowanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the DesignationWiseAllowance in Elasticsearch
        verify(mockDesignationWiseAllowanceSearchRepository, times(0)).save(designationWiseAllowance);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowances() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationWiseAllowance.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].allowanceCategory").value(hasItem(DEFAULT_ALLOWANCE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getDesignationWiseAllowance() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get the designationWiseAllowance
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances/{id}", designationWiseAllowance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designationWiseAllowance.getId().intValue()))
            .andExpect(jsonPath("$.allowanceType").value(DEFAULT_ALLOWANCE_TYPE.toString()))
            .andExpect(jsonPath("$.allowanceCategory").value(DEFAULT_ALLOWANCE_CATEGORY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceType equals to DEFAULT_ALLOWANCE_TYPE
        defaultDesignationWiseAllowanceShouldBeFound("allowanceType.equals=" + DEFAULT_ALLOWANCE_TYPE);

        // Get all the designationWiseAllowanceList where allowanceType equals to UPDATED_ALLOWANCE_TYPE
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceType.equals=" + UPDATED_ALLOWANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceType in DEFAULT_ALLOWANCE_TYPE or UPDATED_ALLOWANCE_TYPE
        defaultDesignationWiseAllowanceShouldBeFound("allowanceType.in=" + DEFAULT_ALLOWANCE_TYPE + "," + UPDATED_ALLOWANCE_TYPE);

        // Get all the designationWiseAllowanceList where allowanceType equals to UPDATED_ALLOWANCE_TYPE
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceType.in=" + UPDATED_ALLOWANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceType is not null
        defaultDesignationWiseAllowanceShouldBeFound("allowanceType.specified=true");

        // Get all the designationWiseAllowanceList where allowanceType is null
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceCategory equals to DEFAULT_ALLOWANCE_CATEGORY
        defaultDesignationWiseAllowanceShouldBeFound("allowanceCategory.equals=" + DEFAULT_ALLOWANCE_CATEGORY);

        // Get all the designationWiseAllowanceList where allowanceCategory equals to UPDATED_ALLOWANCE_CATEGORY
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceCategory.equals=" + UPDATED_ALLOWANCE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceCategory in DEFAULT_ALLOWANCE_CATEGORY or UPDATED_ALLOWANCE_CATEGORY
        defaultDesignationWiseAllowanceShouldBeFound("allowanceCategory.in=" + DEFAULT_ALLOWANCE_CATEGORY + "," + UPDATED_ALLOWANCE_CATEGORY);

        // Get all the designationWiseAllowanceList where allowanceCategory equals to UPDATED_ALLOWANCE_CATEGORY
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceCategory.in=" + UPDATED_ALLOWANCE_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAllowanceCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where allowanceCategory is not null
        defaultDesignationWiseAllowanceShouldBeFound("allowanceCategory.specified=true");

        // Get all the designationWiseAllowanceList where allowanceCategory is null
        defaultDesignationWiseAllowanceShouldNotBeFound("allowanceCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where amount equals to DEFAULT_AMOUNT
        defaultDesignationWiseAllowanceShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the designationWiseAllowanceList where amount equals to UPDATED_AMOUNT
        defaultDesignationWiseAllowanceShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDesignationWiseAllowanceShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the designationWiseAllowanceList where amount equals to UPDATED_AMOUNT
        defaultDesignationWiseAllowanceShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where amount is not null
        defaultDesignationWiseAllowanceShouldBeFound("amount.specified=true");

        // Get all the designationWiseAllowanceList where amount is null
        defaultDesignationWiseAllowanceShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDesignationWiseAllowanceShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the designationWiseAllowanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDesignationWiseAllowanceShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the designationWiseAllowanceList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedBy is not null
        defaultDesignationWiseAllowanceShouldBeFound("modifiedBy.specified=true");

        // Get all the designationWiseAllowanceList where modifiedBy is null
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the designationWiseAllowanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the designationWiseAllowanceList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedOn is not null
        defaultDesignationWiseAllowanceShouldBeFound("modifiedOn.specified=true");

        // Get all the designationWiseAllowanceList where modifiedOn is null
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the designationWiseAllowanceList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        // Get all the designationWiseAllowanceList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the designationWiseAllowanceList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultDesignationWiseAllowanceShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllDesignationWiseAllowancesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        Designation designation = DesignationResourceIntTest.createEntity(em);
        em.persist(designation);
        em.flush();
        designationWiseAllowance.setDesignation(designation);
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);
        Long designationId = designation.getId();

        // Get all the designationWiseAllowanceList where designation equals to designationId
        defaultDesignationWiseAllowanceShouldBeFound("designationId.equals=" + designationId);

        // Get all the designationWiseAllowanceList where designation equals to designationId + 1
        defaultDesignationWiseAllowanceShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDesignationWiseAllowanceShouldBeFound(String filter) throws Exception {
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationWiseAllowance.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].allowanceCategory").value(hasItem(DEFAULT_ALLOWANCE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDesignationWiseAllowanceShouldNotBeFound(String filter) throws Exception {
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDesignationWiseAllowance() throws Exception {
        // Get the designationWiseAllowance
        restDesignationWiseAllowanceMockMvc.perform(get("/api/designation-wise-allowances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignationWiseAllowance() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        int databaseSizeBeforeUpdate = designationWiseAllowanceRepository.findAll().size();

        // Update the designationWiseAllowance
        DesignationWiseAllowance updatedDesignationWiseAllowance = designationWiseAllowanceRepository.findById(designationWiseAllowance.getId()).get();
        // Disconnect from session so that the updates on updatedDesignationWiseAllowance are not directly saved in db
        em.detach(updatedDesignationWiseAllowance);
        updatedDesignationWiseAllowance
            .allowanceType(UPDATED_ALLOWANCE_TYPE)
            .allowanceCategory(UPDATED_ALLOWANCE_CATEGORY)
            .amount(UPDATED_AMOUNT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO = designationWiseAllowanceMapper.toDto(updatedDesignationWiseAllowance);

        restDesignationWiseAllowanceMockMvc.perform(put("/api/designation-wise-allowances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationWiseAllowanceDTO)))
            .andExpect(status().isOk());

        // Validate the DesignationWiseAllowance in the database
        List<DesignationWiseAllowance> designationWiseAllowanceList = designationWiseAllowanceRepository.findAll();
        assertThat(designationWiseAllowanceList).hasSize(databaseSizeBeforeUpdate);
        DesignationWiseAllowance testDesignationWiseAllowance = designationWiseAllowanceList.get(designationWiseAllowanceList.size() - 1);
        assertThat(testDesignationWiseAllowance.getAllowanceType()).isEqualTo(UPDATED_ALLOWANCE_TYPE);
        assertThat(testDesignationWiseAllowance.getAllowanceCategory()).isEqualTo(UPDATED_ALLOWANCE_CATEGORY);
        assertThat(testDesignationWiseAllowance.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDesignationWiseAllowance.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDesignationWiseAllowance.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the DesignationWiseAllowance in Elasticsearch
        verify(mockDesignationWiseAllowanceSearchRepository, times(1)).save(testDesignationWiseAllowance);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignationWiseAllowance() throws Exception {
        int databaseSizeBeforeUpdate = designationWiseAllowanceRepository.findAll().size();

        // Create the DesignationWiseAllowance
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO = designationWiseAllowanceMapper.toDto(designationWiseAllowance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationWiseAllowanceMockMvc.perform(put("/api/designation-wise-allowances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationWiseAllowanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DesignationWiseAllowance in the database
        List<DesignationWiseAllowance> designationWiseAllowanceList = designationWiseAllowanceRepository.findAll();
        assertThat(designationWiseAllowanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DesignationWiseAllowance in Elasticsearch
        verify(mockDesignationWiseAllowanceSearchRepository, times(0)).save(designationWiseAllowance);
    }

    @Test
    @Transactional
    public void deleteDesignationWiseAllowance() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);

        int databaseSizeBeforeDelete = designationWiseAllowanceRepository.findAll().size();

        // Delete the designationWiseAllowance
        restDesignationWiseAllowanceMockMvc.perform(delete("/api/designation-wise-allowances/{id}", designationWiseAllowance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DesignationWiseAllowance> designationWiseAllowanceList = designationWiseAllowanceRepository.findAll();
        assertThat(designationWiseAllowanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DesignationWiseAllowance in Elasticsearch
        verify(mockDesignationWiseAllowanceSearchRepository, times(1)).deleteById(designationWiseAllowance.getId());
    }

    @Test
    @Transactional
    public void searchDesignationWiseAllowance() throws Exception {
        // Initialize the database
        designationWiseAllowanceRepository.saveAndFlush(designationWiseAllowance);
        when(mockDesignationWiseAllowanceSearchRepository.search(queryStringQuery("id:" + designationWiseAllowance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(designationWiseAllowance), PageRequest.of(0, 1), 1));
        // Search the designationWiseAllowance
        restDesignationWiseAllowanceMockMvc.perform(get("/api/_search/designation-wise-allowances?query=id:" + designationWiseAllowance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designationWiseAllowance.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowanceType").value(hasItem(DEFAULT_ALLOWANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].allowanceCategory").value(hasItem(DEFAULT_ALLOWANCE_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationWiseAllowance.class);
        DesignationWiseAllowance designationWiseAllowance1 = new DesignationWiseAllowance();
        designationWiseAllowance1.setId(1L);
        DesignationWiseAllowance designationWiseAllowance2 = new DesignationWiseAllowance();
        designationWiseAllowance2.setId(designationWiseAllowance1.getId());
        assertThat(designationWiseAllowance1).isEqualTo(designationWiseAllowance2);
        designationWiseAllowance2.setId(2L);
        assertThat(designationWiseAllowance1).isNotEqualTo(designationWiseAllowance2);
        designationWiseAllowance1.setId(null);
        assertThat(designationWiseAllowance1).isNotEqualTo(designationWiseAllowance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationWiseAllowanceDTO.class);
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO1 = new DesignationWiseAllowanceDTO();
        designationWiseAllowanceDTO1.setId(1L);
        DesignationWiseAllowanceDTO designationWiseAllowanceDTO2 = new DesignationWiseAllowanceDTO();
        assertThat(designationWiseAllowanceDTO1).isNotEqualTo(designationWiseAllowanceDTO2);
        designationWiseAllowanceDTO2.setId(designationWiseAllowanceDTO1.getId());
        assertThat(designationWiseAllowanceDTO1).isEqualTo(designationWiseAllowanceDTO2);
        designationWiseAllowanceDTO2.setId(2L);
        assertThat(designationWiseAllowanceDTO1).isNotEqualTo(designationWiseAllowanceDTO2);
        designationWiseAllowanceDTO1.setId(null);
        assertThat(designationWiseAllowanceDTO1).isNotEqualTo(designationWiseAllowanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationWiseAllowanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationWiseAllowanceMapper.fromId(null)).isNull();
    }
}
