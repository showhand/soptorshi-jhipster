package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyChallanRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.service.SupplyChallanQueryService;
import org.soptorshi.service.SupplyChallanService;
import org.soptorshi.service.dto.SupplyChallanDTO;
import org.soptorshi.service.mapper.SupplyChallanMapper;
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
 * Test class for the SupplyChallanResource REST controller.
 *
 * @see SupplyChallanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyChallanResourceIntTest {

    private static final String DEFAULT_CHALLAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHALLAN_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_CHALLAN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_CHALLAN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplyChallanRepository supplyChallanRepository;

    @Autowired
    private SupplyChallanMapper supplyChallanMapper;

    @Autowired
    private SupplyChallanService supplyChallanService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyChallanSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyChallanSearchRepository mockSupplyChallanSearchRepository;

    @Autowired
    private SupplyChallanQueryService supplyChallanQueryService;

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

    private MockMvc restSupplyChallanMockMvc;

    private SupplyChallan supplyChallan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyChallanResource supplyChallanResource = new SupplyChallanResource(supplyChallanService, supplyChallanQueryService);
        this.restSupplyChallanMockMvc = MockMvcBuilders.standaloneSetup(supplyChallanResource)
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
    public static SupplyChallan createEntity(EntityManager em) {
        SupplyChallan supplyChallan = new SupplyChallan()
            .challanNo(DEFAULT_CHALLAN_NO)
            .dateOfChallan(DEFAULT_DATE_OF_CHALLAN)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyChallan.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyChallan.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyChallan.setSupplyArea(supplyArea);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyChallan.setSupplyAreaManager(supplyAreaManager);
        // Add required entity
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyChallan.setSupplySalesRepresentative(supplySalesRepresentative);
        // Add required entity
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyChallan.setSupplyShop(supplyShop);
        return supplyChallan;
    }

    @Before
    public void initTest() {
        supplyChallan = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyChallan() throws Exception {
        int databaseSizeBeforeCreate = supplyChallanRepository.findAll().size();

        // Create the SupplyChallan
        SupplyChallanDTO supplyChallanDTO = supplyChallanMapper.toDto(supplyChallan);
        restSupplyChallanMockMvc.perform(post("/api/supply-challans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyChallanDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyChallan in the database
        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyChallan testSupplyChallan = supplyChallanList.get(supplyChallanList.size() - 1);
        assertThat(testSupplyChallan.getChallanNo()).isEqualTo(DEFAULT_CHALLAN_NO);
        assertThat(testSupplyChallan.getDateOfChallan()).isEqualTo(DEFAULT_DATE_OF_CHALLAN);
        assertThat(testSupplyChallan.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSupplyChallan.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyChallan.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyChallan.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyChallan.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyChallan in Elasticsearch
        verify(mockSupplyChallanSearchRepository, times(1)).save(testSupplyChallan);
    }

    @Test
    @Transactional
    public void createSupplyChallanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyChallanRepository.findAll().size();

        // Create the SupplyChallan with an existing ID
        supplyChallan.setId(1L);
        SupplyChallanDTO supplyChallanDTO = supplyChallanMapper.toDto(supplyChallan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyChallanMockMvc.perform(post("/api/supply-challans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyChallanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyChallan in the database
        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyChallan in Elasticsearch
        verify(mockSupplyChallanSearchRepository, times(0)).save(supplyChallan);
    }

    @Test
    @Transactional
    public void checkChallanNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyChallanRepository.findAll().size();
        // set the field null
        supplyChallan.setChallanNo(null);

        // Create the SupplyChallan, which fails.
        SupplyChallanDTO supplyChallanDTO = supplyChallanMapper.toDto(supplyChallan);

        restSupplyChallanMockMvc.perform(post("/api/supply-challans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyChallanDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyChallans() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList
        restSupplyChallanMockMvc.perform(get("/api/supply-challans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyChallan.getId().intValue())))
            .andExpect(jsonPath("$.[*].challanNo").value(hasItem(DEFAULT_CHALLAN_NO.toString())))
            .andExpect(jsonPath("$.[*].dateOfChallan").value(hasItem(DEFAULT_DATE_OF_CHALLAN.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyChallan() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get the supplyChallan
        restSupplyChallanMockMvc.perform(get("/api/supply-challans/{id}", supplyChallan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyChallan.getId().intValue()))
            .andExpect(jsonPath("$.challanNo").value(DEFAULT_CHALLAN_NO.toString()))
            .andExpect(jsonPath("$.dateOfChallan").value(DEFAULT_DATE_OF_CHALLAN.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByChallanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where challanNo equals to DEFAULT_CHALLAN_NO
        defaultSupplyChallanShouldBeFound("challanNo.equals=" + DEFAULT_CHALLAN_NO);

        // Get all the supplyChallanList where challanNo equals to UPDATED_CHALLAN_NO
        defaultSupplyChallanShouldNotBeFound("challanNo.equals=" + UPDATED_CHALLAN_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByChallanNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where challanNo in DEFAULT_CHALLAN_NO or UPDATED_CHALLAN_NO
        defaultSupplyChallanShouldBeFound("challanNo.in=" + DEFAULT_CHALLAN_NO + "," + UPDATED_CHALLAN_NO);

        // Get all the supplyChallanList where challanNo equals to UPDATED_CHALLAN_NO
        defaultSupplyChallanShouldNotBeFound("challanNo.in=" + UPDATED_CHALLAN_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByChallanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where challanNo is not null
        defaultSupplyChallanShouldBeFound("challanNo.specified=true");

        // Get all the supplyChallanList where challanNo is null
        defaultSupplyChallanShouldNotBeFound("challanNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByDateOfChallanIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where dateOfChallan equals to DEFAULT_DATE_OF_CHALLAN
        defaultSupplyChallanShouldBeFound("dateOfChallan.equals=" + DEFAULT_DATE_OF_CHALLAN);

        // Get all the supplyChallanList where dateOfChallan equals to UPDATED_DATE_OF_CHALLAN
        defaultSupplyChallanShouldNotBeFound("dateOfChallan.equals=" + UPDATED_DATE_OF_CHALLAN);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByDateOfChallanIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where dateOfChallan in DEFAULT_DATE_OF_CHALLAN or UPDATED_DATE_OF_CHALLAN
        defaultSupplyChallanShouldBeFound("dateOfChallan.in=" + DEFAULT_DATE_OF_CHALLAN + "," + UPDATED_DATE_OF_CHALLAN);

        // Get all the supplyChallanList where dateOfChallan equals to UPDATED_DATE_OF_CHALLAN
        defaultSupplyChallanShouldNotBeFound("dateOfChallan.in=" + UPDATED_DATE_OF_CHALLAN);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByDateOfChallanIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where dateOfChallan is not null
        defaultSupplyChallanShouldBeFound("dateOfChallan.specified=true");

        // Get all the supplyChallanList where dateOfChallan is null
        defaultSupplyChallanShouldNotBeFound("dateOfChallan.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByDateOfChallanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where dateOfChallan greater than or equals to DEFAULT_DATE_OF_CHALLAN
        defaultSupplyChallanShouldBeFound("dateOfChallan.greaterOrEqualThan=" + DEFAULT_DATE_OF_CHALLAN);

        // Get all the supplyChallanList where dateOfChallan greater than or equals to UPDATED_DATE_OF_CHALLAN
        defaultSupplyChallanShouldNotBeFound("dateOfChallan.greaterOrEqualThan=" + UPDATED_DATE_OF_CHALLAN);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByDateOfChallanIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where dateOfChallan less than or equals to DEFAULT_DATE_OF_CHALLAN
        defaultSupplyChallanShouldNotBeFound("dateOfChallan.lessThan=" + DEFAULT_DATE_OF_CHALLAN);

        // Get all the supplyChallanList where dateOfChallan less than or equals to UPDATED_DATE_OF_CHALLAN
        defaultSupplyChallanShouldBeFound("dateOfChallan.lessThan=" + UPDATED_DATE_OF_CHALLAN);
    }


    @Test
    @Transactional
    public void getAllSupplyChallansByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where remarks equals to DEFAULT_REMARKS
        defaultSupplyChallanShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the supplyChallanList where remarks equals to UPDATED_REMARKS
        defaultSupplyChallanShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSupplyChallanShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the supplyChallanList where remarks equals to UPDATED_REMARKS
        defaultSupplyChallanShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where remarks is not null
        defaultSupplyChallanShouldBeFound("remarks.specified=true");

        // Get all the supplyChallanList where remarks is null
        defaultSupplyChallanShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyChallanShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyChallanList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyChallanShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyChallanShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyChallanList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyChallanShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdBy is not null
        defaultSupplyChallanShouldBeFound("createdBy.specified=true");

        // Get all the supplyChallanList where createdBy is null
        defaultSupplyChallanShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyChallanShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyChallanList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyChallanShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyChallanShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyChallanList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyChallanShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where createdOn is not null
        defaultSupplyChallanShouldBeFound("createdOn.specified=true");

        // Get all the supplyChallanList where createdOn is null
        defaultSupplyChallanShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyChallanShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyChallanList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyChallanShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyChallanShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyChallanList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyChallanShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedBy is not null
        defaultSupplyChallanShouldBeFound("updatedBy.specified=true");

        // Get all the supplyChallanList where updatedBy is null
        defaultSupplyChallanShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyChallanShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyChallanList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyChallanShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyChallanShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyChallanList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyChallanShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyChallansByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        // Get all the supplyChallanList where updatedOn is not null
        defaultSupplyChallanShouldBeFound("updatedOn.specified=true");

        // Get all the supplyChallanList where updatedOn is null
        defaultSupplyChallanShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyChallan.setSupplyZone(supplyZone);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyChallanList where supplyZone equals to supplyZoneId
        defaultSupplyChallanShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyChallanList where supplyZone equals to supplyZoneId + 1
        defaultSupplyChallanShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyChallan.setSupplyZoneManager(supplyZoneManager);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyChallanList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyChallanShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyChallanList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyChallanShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyChallan.setSupplyArea(supplyArea);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyChallanList where supplyArea equals to supplyAreaId
        defaultSupplyChallanShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyChallanList where supplyArea equals to supplyAreaId + 1
        defaultSupplyChallanShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyChallan.setSupplyAreaManager(supplyAreaManager);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplyChallanList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplyChallanShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplyChallanList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplyChallanShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplySalesRepresentativeIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyChallan.setSupplySalesRepresentative(supplySalesRepresentative);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplySalesRepresentativeId = supplySalesRepresentative.getId();

        // Get all the supplyChallanList where supplySalesRepresentative equals to supplySalesRepresentativeId
        defaultSupplyChallanShouldBeFound("supplySalesRepresentativeId.equals=" + supplySalesRepresentativeId);

        // Get all the supplyChallanList where supplySalesRepresentative equals to supplySalesRepresentativeId + 1
        defaultSupplyChallanShouldNotBeFound("supplySalesRepresentativeId.equals=" + (supplySalesRepresentativeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyShopIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyChallan.setSupplyShop(supplyShop);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyShopId = supplyShop.getId();

        // Get all the supplyChallanList where supplyShop equals to supplyShopId
        defaultSupplyChallanShouldBeFound("supplyShopId.equals=" + supplyShopId);

        // Get all the supplyChallanList where supplyShop equals to supplyShopId + 1
        defaultSupplyChallanShouldNotBeFound("supplyShopId.equals=" + (supplyShopId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyChallansBySupplyOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyOrder supplyOrder = SupplyOrderResourceIntTest.createEntity(em);
        em.persist(supplyOrder);
        em.flush();
        supplyChallan.setSupplyOrder(supplyOrder);
        supplyChallanRepository.saveAndFlush(supplyChallan);
        Long supplyOrderId = supplyOrder.getId();

        // Get all the supplyChallanList where supplyOrder equals to supplyOrderId
        defaultSupplyChallanShouldBeFound("supplyOrderId.equals=" + supplyOrderId);

        // Get all the supplyChallanList where supplyOrder equals to supplyOrderId + 1
        defaultSupplyChallanShouldNotBeFound("supplyOrderId.equals=" + (supplyOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyChallanShouldBeFound(String filter) throws Exception {
        restSupplyChallanMockMvc.perform(get("/api/supply-challans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyChallan.getId().intValue())))
            .andExpect(jsonPath("$.[*].challanNo").value(hasItem(DEFAULT_CHALLAN_NO)))
            .andExpect(jsonPath("$.[*].dateOfChallan").value(hasItem(DEFAULT_DATE_OF_CHALLAN.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyChallanMockMvc.perform(get("/api/supply-challans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyChallanShouldNotBeFound(String filter) throws Exception {
        restSupplyChallanMockMvc.perform(get("/api/supply-challans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyChallanMockMvc.perform(get("/api/supply-challans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyChallan() throws Exception {
        // Get the supplyChallan
        restSupplyChallanMockMvc.perform(get("/api/supply-challans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyChallan() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        int databaseSizeBeforeUpdate = supplyChallanRepository.findAll().size();

        // Update the supplyChallan
        SupplyChallan updatedSupplyChallan = supplyChallanRepository.findById(supplyChallan.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyChallan are not directly saved in db
        em.detach(updatedSupplyChallan);
        updatedSupplyChallan
            .challanNo(UPDATED_CHALLAN_NO)
            .dateOfChallan(UPDATED_DATE_OF_CHALLAN)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyChallanDTO supplyChallanDTO = supplyChallanMapper.toDto(updatedSupplyChallan);

        restSupplyChallanMockMvc.perform(put("/api/supply-challans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyChallanDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyChallan in the database
        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeUpdate);
        SupplyChallan testSupplyChallan = supplyChallanList.get(supplyChallanList.size() - 1);
        assertThat(testSupplyChallan.getChallanNo()).isEqualTo(UPDATED_CHALLAN_NO);
        assertThat(testSupplyChallan.getDateOfChallan()).isEqualTo(UPDATED_DATE_OF_CHALLAN);
        assertThat(testSupplyChallan.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSupplyChallan.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyChallan.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyChallan.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyChallan.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyChallan in Elasticsearch
        verify(mockSupplyChallanSearchRepository, times(1)).save(testSupplyChallan);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyChallan() throws Exception {
        int databaseSizeBeforeUpdate = supplyChallanRepository.findAll().size();

        // Create the SupplyChallan
        SupplyChallanDTO supplyChallanDTO = supplyChallanMapper.toDto(supplyChallan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyChallanMockMvc.perform(put("/api/supply-challans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyChallanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyChallan in the database
        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyChallan in Elasticsearch
        verify(mockSupplyChallanSearchRepository, times(0)).save(supplyChallan);
    }

    @Test
    @Transactional
    public void deleteSupplyChallan() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);

        int databaseSizeBeforeDelete = supplyChallanRepository.findAll().size();

        // Delete the supplyChallan
        restSupplyChallanMockMvc.perform(delete("/api/supply-challans/{id}", supplyChallan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyChallan> supplyChallanList = supplyChallanRepository.findAll();
        assertThat(supplyChallanList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyChallan in Elasticsearch
        verify(mockSupplyChallanSearchRepository, times(1)).deleteById(supplyChallan.getId());
    }

    @Test
    @Transactional
    public void searchSupplyChallan() throws Exception {
        // Initialize the database
        supplyChallanRepository.saveAndFlush(supplyChallan);
        when(mockSupplyChallanSearchRepository.search(queryStringQuery("id:" + supplyChallan.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyChallan), PageRequest.of(0, 1), 1));
        // Search the supplyChallan
        restSupplyChallanMockMvc.perform(get("/api/_search/supply-challans?query=id:" + supplyChallan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyChallan.getId().intValue())))
            .andExpect(jsonPath("$.[*].challanNo").value(hasItem(DEFAULT_CHALLAN_NO)))
            .andExpect(jsonPath("$.[*].dateOfChallan").value(hasItem(DEFAULT_DATE_OF_CHALLAN.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyChallan.class);
        SupplyChallan supplyChallan1 = new SupplyChallan();
        supplyChallan1.setId(1L);
        SupplyChallan supplyChallan2 = new SupplyChallan();
        supplyChallan2.setId(supplyChallan1.getId());
        assertThat(supplyChallan1).isEqualTo(supplyChallan2);
        supplyChallan2.setId(2L);
        assertThat(supplyChallan1).isNotEqualTo(supplyChallan2);
        supplyChallan1.setId(null);
        assertThat(supplyChallan1).isNotEqualTo(supplyChallan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyChallanDTO.class);
        SupplyChallanDTO supplyChallanDTO1 = new SupplyChallanDTO();
        supplyChallanDTO1.setId(1L);
        SupplyChallanDTO supplyChallanDTO2 = new SupplyChallanDTO();
        assertThat(supplyChallanDTO1).isNotEqualTo(supplyChallanDTO2);
        supplyChallanDTO2.setId(supplyChallanDTO1.getId());
        assertThat(supplyChallanDTO1).isEqualTo(supplyChallanDTO2);
        supplyChallanDTO2.setId(2L);
        assertThat(supplyChallanDTO1).isNotEqualTo(supplyChallanDTO2);
        supplyChallanDTO1.setId(null);
        assertThat(supplyChallanDTO1).isNotEqualTo(supplyChallanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyChallanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyChallanMapper.fromId(null)).isNull();
    }
}
