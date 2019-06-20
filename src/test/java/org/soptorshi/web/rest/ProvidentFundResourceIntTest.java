package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ProvidentFund;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.ProvidentFundRepository;
import org.soptorshi.repository.search.ProvidentFundSearchRepository;
import org.soptorshi.service.ProvidentFundService;
import org.soptorshi.service.dto.ProvidentFundDTO;
import org.soptorshi.service.mapper.ProvidentFundMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ProvidentFundCriteria;
import org.soptorshi.service.ProvidentFundQueryService;

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

import org.soptorshi.domain.enumeration.ProvidentFundStatus;
/**
 * Test class for the ProvidentFundResource REST controller.
 *
 * @see ProvidentFundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ProvidentFundResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final ProvidentFundStatus DEFAULT_STATUS = ProvidentFundStatus.ACTIVE;
    private static final ProvidentFundStatus UPDATED_STATUS = ProvidentFundStatus.NOT_ACTIVE;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProvidentFundRepository providentFundRepository;

    @Autowired
    private ProvidentFundMapper providentFundMapper;

    @Autowired
    private ProvidentFundService providentFundService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ProvidentFundSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProvidentFundSearchRepository mockProvidentFundSearchRepository;

    @Autowired
    private ProvidentFundQueryService providentFundQueryService;

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

    private MockMvc restProvidentFundMockMvc;

    private ProvidentFund providentFund;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProvidentFundResource providentFundResource = new ProvidentFundResource(providentFundService, providentFundQueryService);
        this.restProvidentFundMockMvc = MockMvcBuilders.standaloneSetup(providentFundResource)
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
    public static ProvidentFund createEntity(EntityManager em) {
        ProvidentFund providentFund = new ProvidentFund()
            .startDate(DEFAULT_START_DATE)
            .rate(DEFAULT_RATE)
            .status(DEFAULT_STATUS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return providentFund;
    }

    @Before
    public void initTest() {
        providentFund = createEntity(em);
    }

    @Test
    @Transactional
    public void createProvidentFund() throws Exception {
        int databaseSizeBeforeCreate = providentFundRepository.findAll().size();

        // Create the ProvidentFund
        ProvidentFundDTO providentFundDTO = providentFundMapper.toDto(providentFund);
        restProvidentFundMockMvc.perform(post("/api/provident-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentFundDTO)))
            .andExpect(status().isCreated());

        // Validate the ProvidentFund in the database
        List<ProvidentFund> providentFundList = providentFundRepository.findAll();
        assertThat(providentFundList).hasSize(databaseSizeBeforeCreate + 1);
        ProvidentFund testProvidentFund = providentFundList.get(providentFundList.size() - 1);
        assertThat(testProvidentFund.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProvidentFund.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testProvidentFund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProvidentFund.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProvidentFund.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ProvidentFund in Elasticsearch
        verify(mockProvidentFundSearchRepository, times(1)).save(testProvidentFund);
    }

    @Test
    @Transactional
    public void createProvidentFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providentFundRepository.findAll().size();

        // Create the ProvidentFund with an existing ID
        providentFund.setId(1L);
        ProvidentFundDTO providentFundDTO = providentFundMapper.toDto(providentFund);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvidentFundMockMvc.perform(post("/api/provident-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentFundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProvidentFund in the database
        List<ProvidentFund> providentFundList = providentFundRepository.findAll();
        assertThat(providentFundList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProvidentFund in Elasticsearch
        verify(mockProvidentFundSearchRepository, times(0)).save(providentFund);
    }

    @Test
    @Transactional
    public void getAllProvidentFunds() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList
        restProvidentFundMockMvc.perform(get("/api/provident-funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providentFund.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getProvidentFund() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get the providentFund
        restProvidentFundMockMvc.perform(get("/api/provident-funds/{id}", providentFund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providentFund.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where startDate equals to DEFAULT_START_DATE
        defaultProvidentFundShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the providentFundList where startDate equals to UPDATED_START_DATE
        defaultProvidentFundShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProvidentFundShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the providentFundList where startDate equals to UPDATED_START_DATE
        defaultProvidentFundShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where startDate is not null
        defaultProvidentFundShouldBeFound("startDate.specified=true");

        // Get all the providentFundList where startDate is null
        defaultProvidentFundShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where startDate greater than or equals to DEFAULT_START_DATE
        defaultProvidentFundShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the providentFundList where startDate greater than or equals to UPDATED_START_DATE
        defaultProvidentFundShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where startDate less than or equals to DEFAULT_START_DATE
        defaultProvidentFundShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the providentFundList where startDate less than or equals to UPDATED_START_DATE
        defaultProvidentFundShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllProvidentFundsByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where rate equals to DEFAULT_RATE
        defaultProvidentFundShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the providentFundList where rate equals to UPDATED_RATE
        defaultProvidentFundShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByRateIsInShouldWork() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultProvidentFundShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the providentFundList where rate equals to UPDATED_RATE
        defaultProvidentFundShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where rate is not null
        defaultProvidentFundShouldBeFound("rate.specified=true");

        // Get all the providentFundList where rate is null
        defaultProvidentFundShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where status equals to DEFAULT_STATUS
        defaultProvidentFundShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the providentFundList where status equals to UPDATED_STATUS
        defaultProvidentFundShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProvidentFundShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the providentFundList where status equals to UPDATED_STATUS
        defaultProvidentFundShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where status is not null
        defaultProvidentFundShouldBeFound("status.specified=true");

        // Get all the providentFundList where status is null
        defaultProvidentFundShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultProvidentFundShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the providentFundList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProvidentFundShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultProvidentFundShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the providentFundList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultProvidentFundShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedBy is not null
        defaultProvidentFundShouldBeFound("modifiedBy.specified=true");

        // Get all the providentFundList where modifiedBy is null
        defaultProvidentFundShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultProvidentFundShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the providentFundList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultProvidentFundShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultProvidentFundShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the providentFundList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultProvidentFundShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedOn is not null
        defaultProvidentFundShouldBeFound("modifiedOn.specified=true");

        // Get all the providentFundList where modifiedOn is null
        defaultProvidentFundShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultProvidentFundShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the providentFundList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultProvidentFundShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllProvidentFundsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        // Get all the providentFundList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultProvidentFundShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the providentFundList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultProvidentFundShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllProvidentFundsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        providentFund.setEmployee(employee);
        providentFundRepository.saveAndFlush(providentFund);
        Long employeeId = employee.getId();

        // Get all the providentFundList where employee equals to employeeId
        defaultProvidentFundShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the providentFundList where employee equals to employeeId + 1
        defaultProvidentFundShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProvidentFundShouldBeFound(String filter) throws Exception {
        restProvidentFundMockMvc.perform(get("/api/provident-funds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providentFund.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restProvidentFundMockMvc.perform(get("/api/provident-funds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProvidentFundShouldNotBeFound(String filter) throws Exception {
        restProvidentFundMockMvc.perform(get("/api/provident-funds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProvidentFundMockMvc.perform(get("/api/provident-funds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProvidentFund() throws Exception {
        // Get the providentFund
        restProvidentFundMockMvc.perform(get("/api/provident-funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvidentFund() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        int databaseSizeBeforeUpdate = providentFundRepository.findAll().size();

        // Update the providentFund
        ProvidentFund updatedProvidentFund = providentFundRepository.findById(providentFund.getId()).get();
        // Disconnect from session so that the updates on updatedProvidentFund are not directly saved in db
        em.detach(updatedProvidentFund);
        updatedProvidentFund
            .startDate(UPDATED_START_DATE)
            .rate(UPDATED_RATE)
            .status(UPDATED_STATUS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ProvidentFundDTO providentFundDTO = providentFundMapper.toDto(updatedProvidentFund);

        restProvidentFundMockMvc.perform(put("/api/provident-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentFundDTO)))
            .andExpect(status().isOk());

        // Validate the ProvidentFund in the database
        List<ProvidentFund> providentFundList = providentFundRepository.findAll();
        assertThat(providentFundList).hasSize(databaseSizeBeforeUpdate);
        ProvidentFund testProvidentFund = providentFundList.get(providentFundList.size() - 1);
        assertThat(testProvidentFund.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProvidentFund.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testProvidentFund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProvidentFund.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProvidentFund.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ProvidentFund in Elasticsearch
        verify(mockProvidentFundSearchRepository, times(1)).save(testProvidentFund);
    }

    @Test
    @Transactional
    public void updateNonExistingProvidentFund() throws Exception {
        int databaseSizeBeforeUpdate = providentFundRepository.findAll().size();

        // Create the ProvidentFund
        ProvidentFundDTO providentFundDTO = providentFundMapper.toDto(providentFund);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvidentFundMockMvc.perform(put("/api/provident-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentFundDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProvidentFund in the database
        List<ProvidentFund> providentFundList = providentFundRepository.findAll();
        assertThat(providentFundList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProvidentFund in Elasticsearch
        verify(mockProvidentFundSearchRepository, times(0)).save(providentFund);
    }

    @Test
    @Transactional
    public void deleteProvidentFund() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);

        int databaseSizeBeforeDelete = providentFundRepository.findAll().size();

        // Delete the providentFund
        restProvidentFundMockMvc.perform(delete("/api/provident-funds/{id}", providentFund.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProvidentFund> providentFundList = providentFundRepository.findAll();
        assertThat(providentFundList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProvidentFund in Elasticsearch
        verify(mockProvidentFundSearchRepository, times(1)).deleteById(providentFund.getId());
    }

    @Test
    @Transactional
    public void searchProvidentFund() throws Exception {
        // Initialize the database
        providentFundRepository.saveAndFlush(providentFund);
        when(mockProvidentFundSearchRepository.search(queryStringQuery("id:" + providentFund.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(providentFund), PageRequest.of(0, 1), 1));
        // Search the providentFund
        restProvidentFundMockMvc.perform(get("/api/_search/provident-funds?query=id:" + providentFund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providentFund.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvidentFund.class);
        ProvidentFund providentFund1 = new ProvidentFund();
        providentFund1.setId(1L);
        ProvidentFund providentFund2 = new ProvidentFund();
        providentFund2.setId(providentFund1.getId());
        assertThat(providentFund1).isEqualTo(providentFund2);
        providentFund2.setId(2L);
        assertThat(providentFund1).isNotEqualTo(providentFund2);
        providentFund1.setId(null);
        assertThat(providentFund1).isNotEqualTo(providentFund2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvidentFundDTO.class);
        ProvidentFundDTO providentFundDTO1 = new ProvidentFundDTO();
        providentFundDTO1.setId(1L);
        ProvidentFundDTO providentFundDTO2 = new ProvidentFundDTO();
        assertThat(providentFundDTO1).isNotEqualTo(providentFundDTO2);
        providentFundDTO2.setId(providentFundDTO1.getId());
        assertThat(providentFundDTO1).isEqualTo(providentFundDTO2);
        providentFundDTO2.setId(2L);
        assertThat(providentFundDTO1).isNotEqualTo(providentFundDTO2);
        providentFundDTO1.setId(null);
        assertThat(providentFundDTO1).isNotEqualTo(providentFundDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(providentFundMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(providentFundMapper.fromId(null)).isNull();
    }
}
