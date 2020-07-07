package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.SupplyZoneManagerRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.service.SupplyZoneManagerQueryService;
import org.soptorshi.service.SupplyZoneManagerService;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
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
 * Test class for the SupplyZoneManagerResource REST controller.
 *
 * @see SupplyZoneManagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyZoneManagerResourceIntTest {

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SupplyZoneManagerStatus DEFAULT_STATUS = SupplyZoneManagerStatus.ACTIVE;
    private static final SupplyZoneManagerStatus UPDATED_STATUS = SupplyZoneManagerStatus.INACTIVE;

    @Autowired
    private SupplyZoneManagerRepository supplyZoneManagerRepository;

    @Autowired
    private SupplyZoneManagerMapper supplyZoneManagerMapper;

    @Autowired
    private SupplyZoneManagerService supplyZoneManagerService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyZoneManagerSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyZoneManagerSearchRepository mockSupplyZoneManagerSearchRepository;

    @Autowired
    private SupplyZoneManagerQueryService supplyZoneManagerQueryService;

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

    private MockMvc restSupplyZoneManagerMockMvc;

    private SupplyZoneManager supplyZoneManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyZoneManagerResource supplyZoneManagerResource = new SupplyZoneManagerResource(supplyZoneManagerService, supplyZoneManagerQueryService);
        this.restSupplyZoneManagerMockMvc = MockMvcBuilders.standaloneSetup(supplyZoneManagerResource)
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
    public static SupplyZoneManager createEntity(EntityManager em) {
        SupplyZoneManager supplyZoneManager = new SupplyZoneManager()
            .endDate(DEFAULT_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyZoneManager.setSupplyZone(supplyZone);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        supplyZoneManager.setEmployee(employee);
        return supplyZoneManager;
    }

    @Before
    public void initTest() {
        supplyZoneManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyZoneManager() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);
        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyZoneManager testSupplyZoneManager = supplyZoneManagerList.get(supplyZoneManagerList.size() - 1);
        assertThat(testSupplyZoneManager.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSupplyZoneManager.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyZoneManager.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyZoneManager.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyZoneManager.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testSupplyZoneManager.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).save(testSupplyZoneManager);
    }

    @Test
    @Transactional
    public void createSupplyZoneManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager with an existing ID
        supplyZoneManager.setId(1L);
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(0)).save(supplyZoneManager);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneManagerRepository.findAll().size();
        // set the field null
        supplyZoneManager.setStatus(null);

        // Create the SupplyZoneManager, which fails.
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagers() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/{id}", supplyZoneManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyZoneManager.getId().intValue()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where endDate equals to DEFAULT_END_DATE
        defaultSupplyZoneManagerShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the supplyZoneManagerList where endDate equals to UPDATED_END_DATE
        defaultSupplyZoneManagerShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultSupplyZoneManagerShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the supplyZoneManagerList where endDate equals to UPDATED_END_DATE
        defaultSupplyZoneManagerShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where endDate is not null
        defaultSupplyZoneManagerShouldBeFound("endDate.specified=true");

        // Get all the supplyZoneManagerList where endDate is null
        defaultSupplyZoneManagerShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where endDate greater than or equals to DEFAULT_END_DATE
        defaultSupplyZoneManagerShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the supplyZoneManagerList where endDate greater than or equals to UPDATED_END_DATE
        defaultSupplyZoneManagerShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where endDate less than or equals to DEFAULT_END_DATE
        defaultSupplyZoneManagerShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the supplyZoneManagerList where endDate less than or equals to UPDATED_END_DATE
        defaultSupplyZoneManagerShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyZoneManagerShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyZoneManagerList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneManagerShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyZoneManagerShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyZoneManagerList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneManagerShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdBy is not null
        defaultSupplyZoneManagerShouldBeFound("createdBy.specified=true");

        // Get all the supplyZoneManagerList where createdBy is null
        defaultSupplyZoneManagerShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyZoneManagerShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyZoneManagerList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneManagerShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyZoneManagerShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyZoneManagerList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneManagerShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where createdOn is not null
        defaultSupplyZoneManagerShouldBeFound("createdOn.specified=true");

        // Get all the supplyZoneManagerList where createdOn is null
        defaultSupplyZoneManagerShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyZoneManagerShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyZoneManagerList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneManagerShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyZoneManagerShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyZoneManagerList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneManagerShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedBy is not null
        defaultSupplyZoneManagerShouldBeFound("updatedBy.specified=true");

        // Get all the supplyZoneManagerList where updatedBy is null
        defaultSupplyZoneManagerShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyZoneManagerShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyZoneManagerList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneManagerShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyZoneManagerShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyZoneManagerList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneManagerShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where updatedOn is not null
        defaultSupplyZoneManagerShouldBeFound("updatedOn.specified=true");

        // Get all the supplyZoneManagerList where updatedOn is null
        defaultSupplyZoneManagerShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where status equals to DEFAULT_STATUS
        defaultSupplyZoneManagerShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the supplyZoneManagerList where status equals to UPDATED_STATUS
        defaultSupplyZoneManagerShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSupplyZoneManagerShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the supplyZoneManagerList where status equals to UPDATED_STATUS
        defaultSupplyZoneManagerShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList where status is not null
        defaultSupplyZoneManagerShouldBeFound("status.specified=true");

        // Get all the supplyZoneManagerList where status is null
        defaultSupplyZoneManagerShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagersBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyZoneManager.setSupplyZone(supplyZone);
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyZoneManagerList where supplyZone equals to supplyZoneId
        defaultSupplyZoneManagerShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyZoneManagerList where supplyZone equals to supplyZoneId + 1
        defaultSupplyZoneManagerShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyZoneManagersByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        supplyZoneManager.setEmployee(employee);
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);
        Long employeeId = employee.getId();

        // Get all the supplyZoneManagerList where employee equals to employeeId
        defaultSupplyZoneManagerShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the supplyZoneManagerList where employee equals to employeeId + 1
        defaultSupplyZoneManagerShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyZoneManagerShouldBeFound(String filter) throws Exception {
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyZoneManagerShouldNotBeFound(String filter) throws Exception {
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyZoneManager() throws Exception {
        // Get the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        int databaseSizeBeforeUpdate = supplyZoneManagerRepository.findAll().size();

        // Update the supplyZoneManager
        SupplyZoneManager updatedSupplyZoneManager = supplyZoneManagerRepository.findById(supplyZoneManager.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyZoneManager are not directly saved in db
        em.detach(updatedSupplyZoneManager);
        updatedSupplyZoneManager
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS);
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(updatedSupplyZoneManager);

        restSupplyZoneManagerMockMvc.perform(put("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeUpdate);
        SupplyZoneManager testSupplyZoneManager = supplyZoneManagerList.get(supplyZoneManagerList.size() - 1);
        assertThat(testSupplyZoneManager.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSupplyZoneManager.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyZoneManager.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyZoneManager.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyZoneManager.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testSupplyZoneManager.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).save(testSupplyZoneManager);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyZoneManager() throws Exception {
        int databaseSizeBeforeUpdate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyZoneManagerMockMvc.perform(put("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(0)).save(supplyZoneManager);
    }

    @Test
    @Transactional
    public void deleteSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        int databaseSizeBeforeDelete = supplyZoneManagerRepository.findAll().size();

        // Delete the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(delete("/api/supply-zone-managers/{id}", supplyZoneManager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).deleteById(supplyZoneManager.getId());
    }

    @Test
    @Transactional
    public void searchSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);
        when(mockSupplyZoneManagerSearchRepository.search(queryStringQuery("id:" + supplyZoneManager.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyZoneManager), PageRequest.of(0, 1), 1));
        // Search the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/_search/supply-zone-managers?query=id:" + supplyZoneManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneManager.class);
        SupplyZoneManager supplyZoneManager1 = new SupplyZoneManager();
        supplyZoneManager1.setId(1L);
        SupplyZoneManager supplyZoneManager2 = new SupplyZoneManager();
        supplyZoneManager2.setId(supplyZoneManager1.getId());
        assertThat(supplyZoneManager1).isEqualTo(supplyZoneManager2);
        supplyZoneManager2.setId(2L);
        assertThat(supplyZoneManager1).isNotEqualTo(supplyZoneManager2);
        supplyZoneManager1.setId(null);
        assertThat(supplyZoneManager1).isNotEqualTo(supplyZoneManager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneManagerDTO.class);
        SupplyZoneManagerDTO supplyZoneManagerDTO1 = new SupplyZoneManagerDTO();
        supplyZoneManagerDTO1.setId(1L);
        SupplyZoneManagerDTO supplyZoneManagerDTO2 = new SupplyZoneManagerDTO();
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO2.setId(supplyZoneManagerDTO1.getId());
        assertThat(supplyZoneManagerDTO1).isEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO2.setId(2L);
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO1.setId(null);
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyZoneManagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyZoneManagerMapper.fromId(null)).isNull();
    }
}
