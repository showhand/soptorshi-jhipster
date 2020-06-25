package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.repository.SupplyAreaManagerRepository;
import org.soptorshi.repository.search.SupplyAreaManagerSearchRepository;
import org.soptorshi.service.SupplyAreaManagerQueryService;
import org.soptorshi.service.SupplyAreaManagerService;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaManagerMapper;
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
 * Test class for the SupplyAreaManagerResource REST controller.
 *
 * @see SupplyAreaManagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyAreaManagerResourceIntTest {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final SupplyAreaManagerStatus DEFAULT_STATUS = SupplyAreaManagerStatus.ACTIVE;
    private static final SupplyAreaManagerStatus UPDATED_STATUS = SupplyAreaManagerStatus.INACTIVE;

    @Autowired
    private SupplyAreaManagerRepository supplyAreaManagerRepository;

    @Autowired
    private SupplyAreaManagerMapper supplyAreaManagerMapper;

    @Autowired
    private SupplyAreaManagerService supplyAreaManagerService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyAreaManagerSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyAreaManagerSearchRepository mockSupplyAreaManagerSearchRepository;

    @Autowired
    private SupplyAreaManagerQueryService supplyAreaManagerQueryService;

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

    private MockMvc restSupplyAreaManagerMockMvc;

    private SupplyAreaManager supplyAreaManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyAreaManagerResource supplyAreaManagerResource = new SupplyAreaManagerResource(supplyAreaManagerService, supplyAreaManagerQueryService);
        this.restSupplyAreaManagerMockMvc = MockMvcBuilders.standaloneSetup(supplyAreaManagerResource)
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
    public static SupplyAreaManager createEntity(EntityManager em) {
        SupplyAreaManager supplyAreaManager = new SupplyAreaManager()
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyAreaManager.setSupplyZone(supplyZone);
        // Add required entity
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyAreaManager.setSupplyArea(supplyArea);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        supplyAreaManager.setEmployee(employee);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyAreaManager.setSupplyZoneManager(supplyZoneManager);
        return supplyAreaManager;
    }

    @Before
    public void initTest() {
        supplyAreaManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyAreaManager() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaManagerRepository.findAll().size();

        // Create the SupplyAreaManager
        SupplyAreaManagerDTO supplyAreaManagerDTO = supplyAreaManagerMapper.toDto(supplyAreaManager);
        restSupplyAreaManagerMockMvc.perform(post("/api/supply-area-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaManagerDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyAreaManager in the database
        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyAreaManager testSupplyAreaManager = supplyAreaManagerList.get(supplyAreaManagerList.size() - 1);
        assertThat(testSupplyAreaManager.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyAreaManager.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyAreaManager.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyAreaManager.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testSupplyAreaManager.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSupplyAreaManager.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SupplyAreaManager in Elasticsearch
        verify(mockSupplyAreaManagerSearchRepository, times(1)).save(testSupplyAreaManager);
    }

    @Test
    @Transactional
    public void createSupplyAreaManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaManagerRepository.findAll().size();

        // Create the SupplyAreaManager with an existing ID
        supplyAreaManager.setId(1L);
        SupplyAreaManagerDTO supplyAreaManagerDTO = supplyAreaManagerMapper.toDto(supplyAreaManager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyAreaManagerMockMvc.perform(post("/api/supply-area-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyAreaManager in the database
        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyAreaManager in Elasticsearch
        verify(mockSupplyAreaManagerSearchRepository, times(0)).save(supplyAreaManager);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaManagerRepository.findAll().size();
        // set the field null
        supplyAreaManager.setStatus(null);

        // Create the SupplyAreaManager, which fails.
        SupplyAreaManagerDTO supplyAreaManagerDTO = supplyAreaManagerMapper.toDto(supplyAreaManager);

        restSupplyAreaManagerMockMvc.perform(post("/api/supply-area-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaManagerDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagers() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSupplyAreaManager() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get the supplyAreaManager
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers/{id}", supplyAreaManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyAreaManager.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyAreaManagerShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyAreaManagerList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaManagerShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyAreaManagerShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyAreaManagerList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaManagerShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdBy is not null
        defaultSupplyAreaManagerShouldBeFound("createdBy.specified=true");

        // Get all the supplyAreaManagerList where createdBy is null
        defaultSupplyAreaManagerShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyAreaManagerShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyAreaManagerList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaManagerShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyAreaManagerShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyAreaManagerList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaManagerShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where createdOn is not null
        defaultSupplyAreaManagerShouldBeFound("createdOn.specified=true");

        // Get all the supplyAreaManagerList where createdOn is null
        defaultSupplyAreaManagerShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyAreaManagerShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyAreaManagerList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaManagerShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyAreaManagerShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyAreaManagerList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaManagerShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedBy is not null
        defaultSupplyAreaManagerShouldBeFound("updatedBy.specified=true");

        // Get all the supplyAreaManagerList where updatedBy is null
        defaultSupplyAreaManagerShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyAreaManagerShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyAreaManagerList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaManagerShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyAreaManagerShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyAreaManagerList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaManagerShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where updatedOn is not null
        defaultSupplyAreaManagerShouldBeFound("updatedOn.specified=true");

        // Get all the supplyAreaManagerList where updatedOn is null
        defaultSupplyAreaManagerShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where endDate equals to DEFAULT_END_DATE
        defaultSupplyAreaManagerShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the supplyAreaManagerList where endDate equals to UPDATED_END_DATE
        defaultSupplyAreaManagerShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultSupplyAreaManagerShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the supplyAreaManagerList where endDate equals to UPDATED_END_DATE
        defaultSupplyAreaManagerShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where endDate is not null
        defaultSupplyAreaManagerShouldBeFound("endDate.specified=true");

        // Get all the supplyAreaManagerList where endDate is null
        defaultSupplyAreaManagerShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where endDate greater than or equals to DEFAULT_END_DATE
        defaultSupplyAreaManagerShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the supplyAreaManagerList where endDate greater than or equals to UPDATED_END_DATE
        defaultSupplyAreaManagerShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where endDate less than or equals to DEFAULT_END_DATE
        defaultSupplyAreaManagerShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the supplyAreaManagerList where endDate less than or equals to UPDATED_END_DATE
        defaultSupplyAreaManagerShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllSupplyAreaManagersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where status equals to DEFAULT_STATUS
        defaultSupplyAreaManagerShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the supplyAreaManagerList where status equals to UPDATED_STATUS
        defaultSupplyAreaManagerShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSupplyAreaManagerShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the supplyAreaManagerList where status equals to UPDATED_STATUS
        defaultSupplyAreaManagerShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        // Get all the supplyAreaManagerList where status is not null
        defaultSupplyAreaManagerShouldBeFound("status.specified=true");

        // Get all the supplyAreaManagerList where status is null
        defaultSupplyAreaManagerShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaManagersBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyAreaManager.setSupplyZone(supplyZone);
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyAreaManagerList where supplyZone equals to supplyZoneId
        defaultSupplyAreaManagerShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyAreaManagerList where supplyZone equals to supplyZoneId + 1
        defaultSupplyAreaManagerShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaManagersBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyAreaManager.setSupplyArea(supplyArea);
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyAreaManagerList where supplyArea equals to supplyAreaId
        defaultSupplyAreaManagerShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyAreaManagerList where supplyArea equals to supplyAreaId + 1
        defaultSupplyAreaManagerShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaManagersByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        supplyAreaManager.setEmployee(employee);
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);
        Long employeeId = employee.getId();

        // Get all the supplyAreaManagerList where employee equals to employeeId
        defaultSupplyAreaManagerShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the supplyAreaManagerList where employee equals to employeeId + 1
        defaultSupplyAreaManagerShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaManagersBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyAreaManager.setSupplyZoneManager(supplyZoneManager);
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyAreaManagerList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyAreaManagerShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyAreaManagerList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyAreaManagerShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyAreaManagerShouldBeFound(String filter) throws Exception {
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyAreaManagerShouldNotBeFound(String filter) throws Exception {
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyAreaManager() throws Exception {
        // Get the supplyAreaManager
        restSupplyAreaManagerMockMvc.perform(get("/api/supply-area-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyAreaManager() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        int databaseSizeBeforeUpdate = supplyAreaManagerRepository.findAll().size();

        // Update the supplyAreaManager
        SupplyAreaManager updatedSupplyAreaManager = supplyAreaManagerRepository.findById(supplyAreaManager.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyAreaManager are not directly saved in db
        em.detach(updatedSupplyAreaManager);
        updatedSupplyAreaManager
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS);
        SupplyAreaManagerDTO supplyAreaManagerDTO = supplyAreaManagerMapper.toDto(updatedSupplyAreaManager);

        restSupplyAreaManagerMockMvc.perform(put("/api/supply-area-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaManagerDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyAreaManager in the database
        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeUpdate);
        SupplyAreaManager testSupplyAreaManager = supplyAreaManagerList.get(supplyAreaManagerList.size() - 1);
        assertThat(testSupplyAreaManager.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyAreaManager.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyAreaManager.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyAreaManager.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testSupplyAreaManager.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSupplyAreaManager.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SupplyAreaManager in Elasticsearch
        verify(mockSupplyAreaManagerSearchRepository, times(1)).save(testSupplyAreaManager);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyAreaManager() throws Exception {
        int databaseSizeBeforeUpdate = supplyAreaManagerRepository.findAll().size();

        // Create the SupplyAreaManager
        SupplyAreaManagerDTO supplyAreaManagerDTO = supplyAreaManagerMapper.toDto(supplyAreaManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyAreaManagerMockMvc.perform(put("/api/supply-area-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyAreaManager in the database
        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyAreaManager in Elasticsearch
        verify(mockSupplyAreaManagerSearchRepository, times(0)).save(supplyAreaManager);
    }

    @Test
    @Transactional
    public void deleteSupplyAreaManager() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);

        int databaseSizeBeforeDelete = supplyAreaManagerRepository.findAll().size();

        // Delete the supplyAreaManager
        restSupplyAreaManagerMockMvc.perform(delete("/api/supply-area-managers/{id}", supplyAreaManager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyAreaManager> supplyAreaManagerList = supplyAreaManagerRepository.findAll();
        assertThat(supplyAreaManagerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyAreaManager in Elasticsearch
        verify(mockSupplyAreaManagerSearchRepository, times(1)).deleteById(supplyAreaManager.getId());
    }

    @Test
    @Transactional
    public void searchSupplyAreaManager() throws Exception {
        // Initialize the database
        supplyAreaManagerRepository.saveAndFlush(supplyAreaManager);
        when(mockSupplyAreaManagerSearchRepository.search(queryStringQuery("id:" + supplyAreaManager.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyAreaManager), PageRequest.of(0, 1), 1));
        // Search the supplyAreaManager
        restSupplyAreaManagerMockMvc.perform(get("/api/_search/supply-area-managers?query=id:" + supplyAreaManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyAreaManager.class);
        SupplyAreaManager supplyAreaManager1 = new SupplyAreaManager();
        supplyAreaManager1.setId(1L);
        SupplyAreaManager supplyAreaManager2 = new SupplyAreaManager();
        supplyAreaManager2.setId(supplyAreaManager1.getId());
        assertThat(supplyAreaManager1).isEqualTo(supplyAreaManager2);
        supplyAreaManager2.setId(2L);
        assertThat(supplyAreaManager1).isNotEqualTo(supplyAreaManager2);
        supplyAreaManager1.setId(null);
        assertThat(supplyAreaManager1).isNotEqualTo(supplyAreaManager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyAreaManagerDTO.class);
        SupplyAreaManagerDTO supplyAreaManagerDTO1 = new SupplyAreaManagerDTO();
        supplyAreaManagerDTO1.setId(1L);
        SupplyAreaManagerDTO supplyAreaManagerDTO2 = new SupplyAreaManagerDTO();
        assertThat(supplyAreaManagerDTO1).isNotEqualTo(supplyAreaManagerDTO2);
        supplyAreaManagerDTO2.setId(supplyAreaManagerDTO1.getId());
        assertThat(supplyAreaManagerDTO1).isEqualTo(supplyAreaManagerDTO2);
        supplyAreaManagerDTO2.setId(2L);
        assertThat(supplyAreaManagerDTO1).isNotEqualTo(supplyAreaManagerDTO2);
        supplyAreaManagerDTO1.setId(null);
        assertThat(supplyAreaManagerDTO1).isNotEqualTo(supplyAreaManagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyAreaManagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyAreaManagerMapper.fromId(null)).isNull();
    }
}
