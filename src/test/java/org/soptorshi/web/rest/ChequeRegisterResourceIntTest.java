package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ChequeRegister;
import org.soptorshi.repository.ChequeRegisterRepository;
import org.soptorshi.repository.search.ChequeRegisterSearchRepository;
import org.soptorshi.service.ChequeRegisterService;
import org.soptorshi.service.dto.ChequeRegisterDTO;
import org.soptorshi.service.mapper.ChequeRegisterMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ChequeRegisterCriteria;
import org.soptorshi.service.ChequeRegisterQueryService;

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

/**
 * Test class for the ChequeRegisterResource REST controller.
 *
 * @see ChequeRegisterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ChequeRegisterResourceIntTest {

    private static final String DEFAULT_CHEQUE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHEQUE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CHEQUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHEQUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REALIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REALIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ChequeRegisterRepository chequeRegisterRepository;

    @Autowired
    private ChequeRegisterMapper chequeRegisterMapper;

    @Autowired
    private ChequeRegisterService chequeRegisterService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ChequeRegisterSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChequeRegisterSearchRepository mockChequeRegisterSearchRepository;

    @Autowired
    private ChequeRegisterQueryService chequeRegisterQueryService;

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

    private MockMvc restChequeRegisterMockMvc;

    private ChequeRegister chequeRegister;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChequeRegisterResource chequeRegisterResource = new ChequeRegisterResource(chequeRegisterService, chequeRegisterQueryService);
        this.restChequeRegisterMockMvc = MockMvcBuilders.standaloneSetup(chequeRegisterResource)
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
    public static ChequeRegister createEntity(EntityManager em) {
        ChequeRegister chequeRegister = new ChequeRegister()
            .chequeNo(DEFAULT_CHEQUE_NO)
            .chequeDate(DEFAULT_CHEQUE_DATE)
            .status(DEFAULT_STATUS)
            .realizationDate(DEFAULT_REALIZATION_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return chequeRegister;
    }

    @Before
    public void initTest() {
        chequeRegister = createEntity(em);
    }

    @Test
    @Transactional
    public void createChequeRegister() throws Exception {
        int databaseSizeBeforeCreate = chequeRegisterRepository.findAll().size();

        // Create the ChequeRegister
        ChequeRegisterDTO chequeRegisterDTO = chequeRegisterMapper.toDto(chequeRegister);
        restChequeRegisterMockMvc.perform(post("/api/cheque-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chequeRegisterDTO)))
            .andExpect(status().isCreated());

        // Validate the ChequeRegister in the database
        List<ChequeRegister> chequeRegisterList = chequeRegisterRepository.findAll();
        assertThat(chequeRegisterList).hasSize(databaseSizeBeforeCreate + 1);
        ChequeRegister testChequeRegister = chequeRegisterList.get(chequeRegisterList.size() - 1);
        assertThat(testChequeRegister.getChequeNo()).isEqualTo(DEFAULT_CHEQUE_NO);
        assertThat(testChequeRegister.getChequeDate()).isEqualTo(DEFAULT_CHEQUE_DATE);
        assertThat(testChequeRegister.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testChequeRegister.getRealizationDate()).isEqualTo(DEFAULT_REALIZATION_DATE);
        assertThat(testChequeRegister.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testChequeRegister.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ChequeRegister in Elasticsearch
        verify(mockChequeRegisterSearchRepository, times(1)).save(testChequeRegister);
    }

    @Test
    @Transactional
    public void createChequeRegisterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chequeRegisterRepository.findAll().size();

        // Create the ChequeRegister with an existing ID
        chequeRegister.setId(1L);
        ChequeRegisterDTO chequeRegisterDTO = chequeRegisterMapper.toDto(chequeRegister);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChequeRegisterMockMvc.perform(post("/api/cheque-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chequeRegisterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChequeRegister in the database
        List<ChequeRegister> chequeRegisterList = chequeRegisterRepository.findAll();
        assertThat(chequeRegisterList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChequeRegister in Elasticsearch
        verify(mockChequeRegisterSearchRepository, times(0)).save(chequeRegister);
    }

    @Test
    @Transactional
    public void getAllChequeRegisters() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chequeRegister.getId().intValue())))
            .andExpect(jsonPath("$.[*].chequeNo").value(hasItem(DEFAULT_CHEQUE_NO.toString())))
            .andExpect(jsonPath("$.[*].chequeDate").value(hasItem(DEFAULT_CHEQUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].realizationDate").value(hasItem(DEFAULT_REALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getChequeRegister() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get the chequeRegister
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers/{id}", chequeRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chequeRegister.getId().intValue()))
            .andExpect(jsonPath("$.chequeNo").value(DEFAULT_CHEQUE_NO.toString()))
            .andExpect(jsonPath("$.chequeDate").value(DEFAULT_CHEQUE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.realizationDate").value(DEFAULT_REALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeNo equals to DEFAULT_CHEQUE_NO
        defaultChequeRegisterShouldBeFound("chequeNo.equals=" + DEFAULT_CHEQUE_NO);

        // Get all the chequeRegisterList where chequeNo equals to UPDATED_CHEQUE_NO
        defaultChequeRegisterShouldNotBeFound("chequeNo.equals=" + UPDATED_CHEQUE_NO);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeNoIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeNo in DEFAULT_CHEQUE_NO or UPDATED_CHEQUE_NO
        defaultChequeRegisterShouldBeFound("chequeNo.in=" + DEFAULT_CHEQUE_NO + "," + UPDATED_CHEQUE_NO);

        // Get all the chequeRegisterList where chequeNo equals to UPDATED_CHEQUE_NO
        defaultChequeRegisterShouldNotBeFound("chequeNo.in=" + UPDATED_CHEQUE_NO);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeNo is not null
        defaultChequeRegisterShouldBeFound("chequeNo.specified=true");

        // Get all the chequeRegisterList where chequeNo is null
        defaultChequeRegisterShouldNotBeFound("chequeNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeDate equals to DEFAULT_CHEQUE_DATE
        defaultChequeRegisterShouldBeFound("chequeDate.equals=" + DEFAULT_CHEQUE_DATE);

        // Get all the chequeRegisterList where chequeDate equals to UPDATED_CHEQUE_DATE
        defaultChequeRegisterShouldNotBeFound("chequeDate.equals=" + UPDATED_CHEQUE_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeDateIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeDate in DEFAULT_CHEQUE_DATE or UPDATED_CHEQUE_DATE
        defaultChequeRegisterShouldBeFound("chequeDate.in=" + DEFAULT_CHEQUE_DATE + "," + UPDATED_CHEQUE_DATE);

        // Get all the chequeRegisterList where chequeDate equals to UPDATED_CHEQUE_DATE
        defaultChequeRegisterShouldNotBeFound("chequeDate.in=" + UPDATED_CHEQUE_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeDate is not null
        defaultChequeRegisterShouldBeFound("chequeDate.specified=true");

        // Get all the chequeRegisterList where chequeDate is null
        defaultChequeRegisterShouldNotBeFound("chequeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeDate greater than or equals to DEFAULT_CHEQUE_DATE
        defaultChequeRegisterShouldBeFound("chequeDate.greaterOrEqualThan=" + DEFAULT_CHEQUE_DATE);

        // Get all the chequeRegisterList where chequeDate greater than or equals to UPDATED_CHEQUE_DATE
        defaultChequeRegisterShouldNotBeFound("chequeDate.greaterOrEqualThan=" + UPDATED_CHEQUE_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByChequeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where chequeDate less than or equals to DEFAULT_CHEQUE_DATE
        defaultChequeRegisterShouldNotBeFound("chequeDate.lessThan=" + DEFAULT_CHEQUE_DATE);

        // Get all the chequeRegisterList where chequeDate less than or equals to UPDATED_CHEQUE_DATE
        defaultChequeRegisterShouldBeFound("chequeDate.lessThan=" + UPDATED_CHEQUE_DATE);
    }


    @Test
    @Transactional
    public void getAllChequeRegistersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where status equals to DEFAULT_STATUS
        defaultChequeRegisterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the chequeRegisterList where status equals to UPDATED_STATUS
        defaultChequeRegisterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultChequeRegisterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the chequeRegisterList where status equals to UPDATED_STATUS
        defaultChequeRegisterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where status is not null
        defaultChequeRegisterShouldBeFound("status.specified=true");

        // Get all the chequeRegisterList where status is null
        defaultChequeRegisterShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByRealizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where realizationDate equals to DEFAULT_REALIZATION_DATE
        defaultChequeRegisterShouldBeFound("realizationDate.equals=" + DEFAULT_REALIZATION_DATE);

        // Get all the chequeRegisterList where realizationDate equals to UPDATED_REALIZATION_DATE
        defaultChequeRegisterShouldNotBeFound("realizationDate.equals=" + UPDATED_REALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByRealizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where realizationDate in DEFAULT_REALIZATION_DATE or UPDATED_REALIZATION_DATE
        defaultChequeRegisterShouldBeFound("realizationDate.in=" + DEFAULT_REALIZATION_DATE + "," + UPDATED_REALIZATION_DATE);

        // Get all the chequeRegisterList where realizationDate equals to UPDATED_REALIZATION_DATE
        defaultChequeRegisterShouldNotBeFound("realizationDate.in=" + UPDATED_REALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByRealizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where realizationDate is not null
        defaultChequeRegisterShouldBeFound("realizationDate.specified=true");

        // Get all the chequeRegisterList where realizationDate is null
        defaultChequeRegisterShouldNotBeFound("realizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByRealizationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where realizationDate greater than or equals to DEFAULT_REALIZATION_DATE
        defaultChequeRegisterShouldBeFound("realizationDate.greaterOrEqualThan=" + DEFAULT_REALIZATION_DATE);

        // Get all the chequeRegisterList where realizationDate greater than or equals to UPDATED_REALIZATION_DATE
        defaultChequeRegisterShouldNotBeFound("realizationDate.greaterOrEqualThan=" + UPDATED_REALIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByRealizationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where realizationDate less than or equals to DEFAULT_REALIZATION_DATE
        defaultChequeRegisterShouldNotBeFound("realizationDate.lessThan=" + DEFAULT_REALIZATION_DATE);

        // Get all the chequeRegisterList where realizationDate less than or equals to UPDATED_REALIZATION_DATE
        defaultChequeRegisterShouldBeFound("realizationDate.lessThan=" + UPDATED_REALIZATION_DATE);
    }


    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultChequeRegisterShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the chequeRegisterList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultChequeRegisterShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultChequeRegisterShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the chequeRegisterList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultChequeRegisterShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedBy is not null
        defaultChequeRegisterShouldBeFound("modifiedBy.specified=true");

        // Get all the chequeRegisterList where modifiedBy is null
        defaultChequeRegisterShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultChequeRegisterShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the chequeRegisterList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultChequeRegisterShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultChequeRegisterShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the chequeRegisterList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultChequeRegisterShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedOn is not null
        defaultChequeRegisterShouldBeFound("modifiedOn.specified=true");

        // Get all the chequeRegisterList where modifiedOn is null
        defaultChequeRegisterShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultChequeRegisterShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the chequeRegisterList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultChequeRegisterShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllChequeRegistersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        // Get all the chequeRegisterList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultChequeRegisterShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the chequeRegisterList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultChequeRegisterShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChequeRegisterShouldBeFound(String filter) throws Exception {
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chequeRegister.getId().intValue())))
            .andExpect(jsonPath("$.[*].chequeNo").value(hasItem(DEFAULT_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].chequeDate").value(hasItem(DEFAULT_CHEQUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].realizationDate").value(hasItem(DEFAULT_REALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChequeRegisterShouldNotBeFound(String filter) throws Exception {
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChequeRegister() throws Exception {
        // Get the chequeRegister
        restChequeRegisterMockMvc.perform(get("/api/cheque-registers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChequeRegister() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        int databaseSizeBeforeUpdate = chequeRegisterRepository.findAll().size();

        // Update the chequeRegister
        ChequeRegister updatedChequeRegister = chequeRegisterRepository.findById(chequeRegister.getId()).get();
        // Disconnect from session so that the updates on updatedChequeRegister are not directly saved in db
        em.detach(updatedChequeRegister);
        updatedChequeRegister
            .chequeNo(UPDATED_CHEQUE_NO)
            .chequeDate(UPDATED_CHEQUE_DATE)
            .status(UPDATED_STATUS)
            .realizationDate(UPDATED_REALIZATION_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ChequeRegisterDTO chequeRegisterDTO = chequeRegisterMapper.toDto(updatedChequeRegister);

        restChequeRegisterMockMvc.perform(put("/api/cheque-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chequeRegisterDTO)))
            .andExpect(status().isOk());

        // Validate the ChequeRegister in the database
        List<ChequeRegister> chequeRegisterList = chequeRegisterRepository.findAll();
        assertThat(chequeRegisterList).hasSize(databaseSizeBeforeUpdate);
        ChequeRegister testChequeRegister = chequeRegisterList.get(chequeRegisterList.size() - 1);
        assertThat(testChequeRegister.getChequeNo()).isEqualTo(UPDATED_CHEQUE_NO);
        assertThat(testChequeRegister.getChequeDate()).isEqualTo(UPDATED_CHEQUE_DATE);
        assertThat(testChequeRegister.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testChequeRegister.getRealizationDate()).isEqualTo(UPDATED_REALIZATION_DATE);
        assertThat(testChequeRegister.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testChequeRegister.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ChequeRegister in Elasticsearch
        verify(mockChequeRegisterSearchRepository, times(1)).save(testChequeRegister);
    }

    @Test
    @Transactional
    public void updateNonExistingChequeRegister() throws Exception {
        int databaseSizeBeforeUpdate = chequeRegisterRepository.findAll().size();

        // Create the ChequeRegister
        ChequeRegisterDTO chequeRegisterDTO = chequeRegisterMapper.toDto(chequeRegister);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChequeRegisterMockMvc.perform(put("/api/cheque-registers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chequeRegisterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChequeRegister in the database
        List<ChequeRegister> chequeRegisterList = chequeRegisterRepository.findAll();
        assertThat(chequeRegisterList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChequeRegister in Elasticsearch
        verify(mockChequeRegisterSearchRepository, times(0)).save(chequeRegister);
    }

    @Test
    @Transactional
    public void deleteChequeRegister() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);

        int databaseSizeBeforeDelete = chequeRegisterRepository.findAll().size();

        // Delete the chequeRegister
        restChequeRegisterMockMvc.perform(delete("/api/cheque-registers/{id}", chequeRegister.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChequeRegister> chequeRegisterList = chequeRegisterRepository.findAll();
        assertThat(chequeRegisterList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChequeRegister in Elasticsearch
        verify(mockChequeRegisterSearchRepository, times(1)).deleteById(chequeRegister.getId());
    }

    @Test
    @Transactional
    public void searchChequeRegister() throws Exception {
        // Initialize the database
        chequeRegisterRepository.saveAndFlush(chequeRegister);
        when(mockChequeRegisterSearchRepository.search(queryStringQuery("id:" + chequeRegister.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(chequeRegister), PageRequest.of(0, 1), 1));
        // Search the chequeRegister
        restChequeRegisterMockMvc.perform(get("/api/_search/cheque-registers?query=id:" + chequeRegister.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chequeRegister.getId().intValue())))
            .andExpect(jsonPath("$.[*].chequeNo").value(hasItem(DEFAULT_CHEQUE_NO)))
            .andExpect(jsonPath("$.[*].chequeDate").value(hasItem(DEFAULT_CHEQUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].realizationDate").value(hasItem(DEFAULT_REALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChequeRegister.class);
        ChequeRegister chequeRegister1 = new ChequeRegister();
        chequeRegister1.setId(1L);
        ChequeRegister chequeRegister2 = new ChequeRegister();
        chequeRegister2.setId(chequeRegister1.getId());
        assertThat(chequeRegister1).isEqualTo(chequeRegister2);
        chequeRegister2.setId(2L);
        assertThat(chequeRegister1).isNotEqualTo(chequeRegister2);
        chequeRegister1.setId(null);
        assertThat(chequeRegister1).isNotEqualTo(chequeRegister2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChequeRegisterDTO.class);
        ChequeRegisterDTO chequeRegisterDTO1 = new ChequeRegisterDTO();
        chequeRegisterDTO1.setId(1L);
        ChequeRegisterDTO chequeRegisterDTO2 = new ChequeRegisterDTO();
        assertThat(chequeRegisterDTO1).isNotEqualTo(chequeRegisterDTO2);
        chequeRegisterDTO2.setId(chequeRegisterDTO1.getId());
        assertThat(chequeRegisterDTO1).isEqualTo(chequeRegisterDTO2);
        chequeRegisterDTO2.setId(2L);
        assertThat(chequeRegisterDTO1).isNotEqualTo(chequeRegisterDTO2);
        chequeRegisterDTO1.setId(null);
        assertThat(chequeRegisterDTO1).isNotEqualTo(chequeRegisterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chequeRegisterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chequeRegisterMapper.fromId(null)).isNull();
    }
}
