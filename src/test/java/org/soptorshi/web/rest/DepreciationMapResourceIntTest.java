package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DepreciationMap;
import org.soptorshi.repository.DepreciationMapRepository;
import org.soptorshi.repository.search.DepreciationMapSearchRepository;
import org.soptorshi.service.DepreciationMapService;
import org.soptorshi.service.dto.DepreciationMapDTO;
import org.soptorshi.service.mapper.DepreciationMapMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DepreciationMapCriteria;
import org.soptorshi.service.DepreciationMapQueryService;

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

/**
 * Test class for the DepreciationMapResource REST controller.
 *
 * @see DepreciationMapResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DepreciationMapResourceIntTest {

    private static final Long DEFAULT_DEPRECIATION_ACCOUNT_ID = 1L;
    private static final Long UPDATED_DEPRECIATION_ACCOUNT_ID = 2L;

    private static final String DEFAULT_DEPRECIATION_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPRECIATION_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_ID = 1L;
    private static final Long UPDATED_ACCOUNT_ID = 2L;

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DepreciationMapRepository depreciationMapRepository;

    @Autowired
    private DepreciationMapMapper depreciationMapMapper;

    @Autowired
    private DepreciationMapService depreciationMapService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DepreciationMapSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepreciationMapSearchRepository mockDepreciationMapSearchRepository;

    @Autowired
    private DepreciationMapQueryService depreciationMapQueryService;

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

    private MockMvc restDepreciationMapMockMvc;

    private DepreciationMap depreciationMap;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepreciationMapResource depreciationMapResource = new DepreciationMapResource(depreciationMapService, depreciationMapQueryService);
        this.restDepreciationMapMockMvc = MockMvcBuilders.standaloneSetup(depreciationMapResource)
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
    public static DepreciationMap createEntity(EntityManager em) {
        DepreciationMap depreciationMap = new DepreciationMap()
            .depreciationAccountId(DEFAULT_DEPRECIATION_ACCOUNT_ID)
            .depreciationAccountName(DEFAULT_DEPRECIATION_ACCOUNT_NAME)
            .accountId(DEFAULT_ACCOUNT_ID)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return depreciationMap;
    }

    @Before
    public void initTest() {
        depreciationMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepreciationMap() throws Exception {
        int databaseSizeBeforeCreate = depreciationMapRepository.findAll().size();

        // Create the DepreciationMap
        DepreciationMapDTO depreciationMapDTO = depreciationMapMapper.toDto(depreciationMap);
        restDepreciationMapMockMvc.perform(post("/api/depreciation-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationMapDTO)))
            .andExpect(status().isCreated());

        // Validate the DepreciationMap in the database
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();
        assertThat(depreciationMapList).hasSize(databaseSizeBeforeCreate + 1);
        DepreciationMap testDepreciationMap = depreciationMapList.get(depreciationMapList.size() - 1);
        assertThat(testDepreciationMap.getDepreciationAccountId()).isEqualTo(DEFAULT_DEPRECIATION_ACCOUNT_ID);
        assertThat(testDepreciationMap.getDepreciationAccountName()).isEqualTo(DEFAULT_DEPRECIATION_ACCOUNT_NAME);
        assertThat(testDepreciationMap.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testDepreciationMap.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testDepreciationMap.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDepreciationMap.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDepreciationMap.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDepreciationMap.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the DepreciationMap in Elasticsearch
        verify(mockDepreciationMapSearchRepository, times(1)).save(testDepreciationMap);
    }

    @Test
    @Transactional
    public void createDepreciationMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depreciationMapRepository.findAll().size();

        // Create the DepreciationMap with an existing ID
        depreciationMap.setId(1L);
        DepreciationMapDTO depreciationMapDTO = depreciationMapMapper.toDto(depreciationMap);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepreciationMapMockMvc.perform(post("/api/depreciation-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationMap in the database
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();
        assertThat(depreciationMapList).hasSize(databaseSizeBeforeCreate);

        // Validate the DepreciationMap in Elasticsearch
        verify(mockDepreciationMapSearchRepository, times(0)).save(depreciationMap);
    }

    @Test
    @Transactional
    public void getAllDepreciationMaps() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountId").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountName").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getDepreciationMap() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get the depreciationMap
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps/{id}", depreciationMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depreciationMap.getId().intValue()))
            .andExpect(jsonPath("$.depreciationAccountId").value(DEFAULT_DEPRECIATION_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.depreciationAccountName").value(DEFAULT_DEPRECIATION_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountId equals to DEFAULT_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("depreciationAccountId.equals=" + DEFAULT_DEPRECIATION_ACCOUNT_ID);

        // Get all the depreciationMapList where depreciationAccountId equals to UPDATED_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("depreciationAccountId.equals=" + UPDATED_DEPRECIATION_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountId in DEFAULT_DEPRECIATION_ACCOUNT_ID or UPDATED_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("depreciationAccountId.in=" + DEFAULT_DEPRECIATION_ACCOUNT_ID + "," + UPDATED_DEPRECIATION_ACCOUNT_ID);

        // Get all the depreciationMapList where depreciationAccountId equals to UPDATED_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("depreciationAccountId.in=" + UPDATED_DEPRECIATION_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountId is not null
        defaultDepreciationMapShouldBeFound("depreciationAccountId.specified=true");

        // Get all the depreciationMapList where depreciationAccountId is null
        defaultDepreciationMapShouldNotBeFound("depreciationAccountId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountId greater than or equals to DEFAULT_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("depreciationAccountId.greaterOrEqualThan=" + DEFAULT_DEPRECIATION_ACCOUNT_ID);

        // Get all the depreciationMapList where depreciationAccountId greater than or equals to UPDATED_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("depreciationAccountId.greaterOrEqualThan=" + UPDATED_DEPRECIATION_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountId less than or equals to DEFAULT_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("depreciationAccountId.lessThan=" + DEFAULT_DEPRECIATION_ACCOUNT_ID);

        // Get all the depreciationMapList where depreciationAccountId less than or equals to UPDATED_DEPRECIATION_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("depreciationAccountId.lessThan=" + UPDATED_DEPRECIATION_ACCOUNT_ID);
    }


    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountName equals to DEFAULT_DEPRECIATION_ACCOUNT_NAME
        defaultDepreciationMapShouldBeFound("depreciationAccountName.equals=" + DEFAULT_DEPRECIATION_ACCOUNT_NAME);

        // Get all the depreciationMapList where depreciationAccountName equals to UPDATED_DEPRECIATION_ACCOUNT_NAME
        defaultDepreciationMapShouldNotBeFound("depreciationAccountName.equals=" + UPDATED_DEPRECIATION_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountName in DEFAULT_DEPRECIATION_ACCOUNT_NAME or UPDATED_DEPRECIATION_ACCOUNT_NAME
        defaultDepreciationMapShouldBeFound("depreciationAccountName.in=" + DEFAULT_DEPRECIATION_ACCOUNT_NAME + "," + UPDATED_DEPRECIATION_ACCOUNT_NAME);

        // Get all the depreciationMapList where depreciationAccountName equals to UPDATED_DEPRECIATION_ACCOUNT_NAME
        defaultDepreciationMapShouldNotBeFound("depreciationAccountName.in=" + UPDATED_DEPRECIATION_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByDepreciationAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where depreciationAccountName is not null
        defaultDepreciationMapShouldBeFound("depreciationAccountName.specified=true");

        // Get all the depreciationMapList where depreciationAccountName is null
        defaultDepreciationMapShouldNotBeFound("depreciationAccountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountId equals to DEFAULT_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("accountId.equals=" + DEFAULT_ACCOUNT_ID);

        // Get all the depreciationMapList where accountId equals to UPDATED_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountId in DEFAULT_ACCOUNT_ID or UPDATED_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID);

        // Get all the depreciationMapList where accountId equals to UPDATED_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountId is not null
        defaultDepreciationMapShouldBeFound("accountId.specified=true");

        // Get all the depreciationMapList where accountId is null
        defaultDepreciationMapShouldNotBeFound("accountId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountId greater than or equals to DEFAULT_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("accountId.greaterOrEqualThan=" + DEFAULT_ACCOUNT_ID);

        // Get all the depreciationMapList where accountId greater than or equals to UPDATED_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("accountId.greaterOrEqualThan=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountId less than or equals to DEFAULT_ACCOUNT_ID
        defaultDepreciationMapShouldNotBeFound("accountId.lessThan=" + DEFAULT_ACCOUNT_ID);

        // Get all the depreciationMapList where accountId less than or equals to UPDATED_ACCOUNT_ID
        defaultDepreciationMapShouldBeFound("accountId.lessThan=" + UPDATED_ACCOUNT_ID);
    }


    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultDepreciationMapShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the depreciationMapList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultDepreciationMapShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultDepreciationMapShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the depreciationMapList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultDepreciationMapShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where accountName is not null
        defaultDepreciationMapShouldBeFound("accountName.specified=true");

        // Get all the depreciationMapList where accountName is null
        defaultDepreciationMapShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdBy equals to DEFAULT_CREATED_BY
        defaultDepreciationMapShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the depreciationMapList where createdBy equals to UPDATED_CREATED_BY
        defaultDepreciationMapShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDepreciationMapShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the depreciationMapList where createdBy equals to UPDATED_CREATED_BY
        defaultDepreciationMapShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdBy is not null
        defaultDepreciationMapShouldBeFound("createdBy.specified=true");

        // Get all the depreciationMapList where createdBy is null
        defaultDepreciationMapShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdOn equals to DEFAULT_CREATED_ON
        defaultDepreciationMapShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the depreciationMapList where createdOn equals to UPDATED_CREATED_ON
        defaultDepreciationMapShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDepreciationMapShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the depreciationMapList where createdOn equals to UPDATED_CREATED_ON
        defaultDepreciationMapShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where createdOn is not null
        defaultDepreciationMapShouldBeFound("createdOn.specified=true");

        // Get all the depreciationMapList where createdOn is null
        defaultDepreciationMapShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultDepreciationMapShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the depreciationMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDepreciationMapShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultDepreciationMapShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the depreciationMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultDepreciationMapShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedBy is not null
        defaultDepreciationMapShouldBeFound("modifiedBy.specified=true");

        // Get all the depreciationMapList where modifiedBy is null
        defaultDepreciationMapShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultDepreciationMapShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the depreciationMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDepreciationMapShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultDepreciationMapShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the depreciationMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultDepreciationMapShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllDepreciationMapsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        // Get all the depreciationMapList where modifiedOn is not null
        defaultDepreciationMapShouldBeFound("modifiedOn.specified=true");

        // Get all the depreciationMapList where modifiedOn is null
        defaultDepreciationMapShouldNotBeFound("modifiedOn.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDepreciationMapShouldBeFound(String filter) throws Exception {
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountId").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountName").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDepreciationMapShouldNotBeFound(String filter) throws Exception {
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepreciationMap() throws Exception {
        // Get the depreciationMap
        restDepreciationMapMockMvc.perform(get("/api/depreciation-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepreciationMap() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        int databaseSizeBeforeUpdate = depreciationMapRepository.findAll().size();

        // Update the depreciationMap
        DepreciationMap updatedDepreciationMap = depreciationMapRepository.findById(depreciationMap.getId()).get();
        // Disconnect from session so that the updates on updatedDepreciationMap are not directly saved in db
        em.detach(updatedDepreciationMap);
        updatedDepreciationMap
            .depreciationAccountId(UPDATED_DEPRECIATION_ACCOUNT_ID)
            .depreciationAccountName(UPDATED_DEPRECIATION_ACCOUNT_NAME)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountName(UPDATED_ACCOUNT_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        DepreciationMapDTO depreciationMapDTO = depreciationMapMapper.toDto(updatedDepreciationMap);

        restDepreciationMapMockMvc.perform(put("/api/depreciation-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationMapDTO)))
            .andExpect(status().isOk());

        // Validate the DepreciationMap in the database
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();
        assertThat(depreciationMapList).hasSize(databaseSizeBeforeUpdate);
        DepreciationMap testDepreciationMap = depreciationMapList.get(depreciationMapList.size() - 1);
        assertThat(testDepreciationMap.getDepreciationAccountId()).isEqualTo(UPDATED_DEPRECIATION_ACCOUNT_ID);
        assertThat(testDepreciationMap.getDepreciationAccountName()).isEqualTo(UPDATED_DEPRECIATION_ACCOUNT_NAME);
        assertThat(testDepreciationMap.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testDepreciationMap.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testDepreciationMap.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepreciationMap.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDepreciationMap.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDepreciationMap.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the DepreciationMap in Elasticsearch
        verify(mockDepreciationMapSearchRepository, times(1)).save(testDepreciationMap);
    }

    @Test
    @Transactional
    public void updateNonExistingDepreciationMap() throws Exception {
        int databaseSizeBeforeUpdate = depreciationMapRepository.findAll().size();

        // Create the DepreciationMap
        DepreciationMapDTO depreciationMapDTO = depreciationMapMapper.toDto(depreciationMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepreciationMapMockMvc.perform(put("/api/depreciation-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationMap in the database
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();
        assertThat(depreciationMapList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DepreciationMap in Elasticsearch
        verify(mockDepreciationMapSearchRepository, times(0)).save(depreciationMap);
    }

    @Test
    @Transactional
    public void deleteDepreciationMap() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);

        int databaseSizeBeforeDelete = depreciationMapRepository.findAll().size();

        // Delete the depreciationMap
        restDepreciationMapMockMvc.perform(delete("/api/depreciation-maps/{id}", depreciationMap.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();
        assertThat(depreciationMapList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DepreciationMap in Elasticsearch
        verify(mockDepreciationMapSearchRepository, times(1)).deleteById(depreciationMap.getId());
    }

    @Test
    @Transactional
    public void searchDepreciationMap() throws Exception {
        // Initialize the database
        depreciationMapRepository.saveAndFlush(depreciationMap);
        when(mockDepreciationMapSearchRepository.search(queryStringQuery("id:" + depreciationMap.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(depreciationMap), PageRequest.of(0, 1), 1));
        // Search the depreciationMap
        restDepreciationMapMockMvc.perform(get("/api/_search/depreciation-maps?query=id:" + depreciationMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountId").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].depreciationAccountName").value(hasItem(DEFAULT_DEPRECIATION_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationMap.class);
        DepreciationMap depreciationMap1 = new DepreciationMap();
        depreciationMap1.setId(1L);
        DepreciationMap depreciationMap2 = new DepreciationMap();
        depreciationMap2.setId(depreciationMap1.getId());
        assertThat(depreciationMap1).isEqualTo(depreciationMap2);
        depreciationMap2.setId(2L);
        assertThat(depreciationMap1).isNotEqualTo(depreciationMap2);
        depreciationMap1.setId(null);
        assertThat(depreciationMap1).isNotEqualTo(depreciationMap2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationMapDTO.class);
        DepreciationMapDTO depreciationMapDTO1 = new DepreciationMapDTO();
        depreciationMapDTO1.setId(1L);
        DepreciationMapDTO depreciationMapDTO2 = new DepreciationMapDTO();
        assertThat(depreciationMapDTO1).isNotEqualTo(depreciationMapDTO2);
        depreciationMapDTO2.setId(depreciationMapDTO1.getId());
        assertThat(depreciationMapDTO1).isEqualTo(depreciationMapDTO2);
        depreciationMapDTO2.setId(2L);
        assertThat(depreciationMapDTO1).isNotEqualTo(depreciationMapDTO2);
        depreciationMapDTO1.setId(null);
        assertThat(depreciationMapDTO1).isNotEqualTo(depreciationMapDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depreciationMapMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depreciationMapMapper.fromId(null)).isNull();
    }
}
