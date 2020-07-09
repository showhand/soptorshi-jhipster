package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyMoneyCollectionRepository;
import org.soptorshi.repository.search.SupplyMoneyCollectionSearchRepository;
import org.soptorshi.service.SupplyMoneyCollectionQueryService;
import org.soptorshi.service.SupplyMoneyCollectionService;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;
import org.soptorshi.service.mapper.SupplyMoneyCollectionMapper;
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
 * Test class for the SupplyMoneyCollectionResource REST controller.
 *
 * @see SupplyMoneyCollectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyMoneyCollectionResourceIntTest {

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final Double DEFAULT_RECEIVED_AMOUNT = 1D;
    private static final Double UPDATED_RECEIVED_AMOUNT = 2D;

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
    private SupplyMoneyCollectionRepository supplyMoneyCollectionRepository;

    @Autowired
    private SupplyMoneyCollectionMapper supplyMoneyCollectionMapper;

    @Autowired
    private SupplyMoneyCollectionService supplyMoneyCollectionService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyMoneyCollectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyMoneyCollectionSearchRepository mockSupplyMoneyCollectionSearchRepository;

    @Autowired
    private SupplyMoneyCollectionQueryService supplyMoneyCollectionQueryService;

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

    private MockMvc restSupplyMoneyCollectionMockMvc;

    private SupplyMoneyCollection supplyMoneyCollection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyMoneyCollectionResource supplyMoneyCollectionResource = new SupplyMoneyCollectionResource(supplyMoneyCollectionService, supplyMoneyCollectionQueryService);
        this.restSupplyMoneyCollectionMockMvc = MockMvcBuilders.standaloneSetup(supplyMoneyCollectionResource)
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
    public static SupplyMoneyCollection createEntity(EntityManager em) {
        SupplyMoneyCollection supplyMoneyCollection = new SupplyMoneyCollection()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .receivedAmount(DEFAULT_RECEIVED_AMOUNT)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyMoneyCollection.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyMoneyCollection.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyMoneyCollection.setSupplyArea(supplyArea);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyMoneyCollection.setSupplyAreaManager(supplyAreaManager);
        // Add required entity
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyMoneyCollection.setSupplyShop(supplyShop);
        return supplyMoneyCollection;
    }

    @Before
    public void initTest() {
        supplyMoneyCollection = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyMoneyCollection() throws Exception {
        int databaseSizeBeforeCreate = supplyMoneyCollectionRepository.findAll().size();

        // Create the SupplyMoneyCollection
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO = supplyMoneyCollectionMapper.toDto(supplyMoneyCollection);
        restSupplyMoneyCollectionMockMvc.perform(post("/api/supply-money-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyMoneyCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyMoneyCollection in the database
        List<SupplyMoneyCollection> supplyMoneyCollectionList = supplyMoneyCollectionRepository.findAll();
        assertThat(supplyMoneyCollectionList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyMoneyCollection testSupplyMoneyCollection = supplyMoneyCollectionList.get(supplyMoneyCollectionList.size() - 1);
        assertThat(testSupplyMoneyCollection.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testSupplyMoneyCollection.getReceivedAmount()).isEqualTo(DEFAULT_RECEIVED_AMOUNT);
        assertThat(testSupplyMoneyCollection.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSupplyMoneyCollection.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyMoneyCollection.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyMoneyCollection.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyMoneyCollection.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyMoneyCollection in Elasticsearch
        verify(mockSupplyMoneyCollectionSearchRepository, times(1)).save(testSupplyMoneyCollection);
    }

    @Test
    @Transactional
    public void createSupplyMoneyCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyMoneyCollectionRepository.findAll().size();

        // Create the SupplyMoneyCollection with an existing ID
        supplyMoneyCollection.setId(1L);
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO = supplyMoneyCollectionMapper.toDto(supplyMoneyCollection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyMoneyCollectionMockMvc.perform(post("/api/supply-money-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyMoneyCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyMoneyCollection in the database
        List<SupplyMoneyCollection> supplyMoneyCollectionList = supplyMoneyCollectionRepository.findAll();
        assertThat(supplyMoneyCollectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyMoneyCollection in Elasticsearch
        verify(mockSupplyMoneyCollectionSearchRepository, times(0)).save(supplyMoneyCollection);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollections() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyMoneyCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedAmount").value(hasItem(DEFAULT_RECEIVED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyMoneyCollection() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get the supplyMoneyCollection
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections/{id}", supplyMoneyCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyMoneyCollection.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.receivedAmount").value(DEFAULT_RECEIVED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultSupplyMoneyCollectionShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the supplyMoneyCollectionList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultSupplyMoneyCollectionShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultSupplyMoneyCollectionShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the supplyMoneyCollectionList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultSupplyMoneyCollectionShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where totalAmount is not null
        defaultSupplyMoneyCollectionShouldBeFound("totalAmount.specified=true");

        // Get all the supplyMoneyCollectionList where totalAmount is null
        defaultSupplyMoneyCollectionShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByReceivedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where receivedAmount equals to DEFAULT_RECEIVED_AMOUNT
        defaultSupplyMoneyCollectionShouldBeFound("receivedAmount.equals=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the supplyMoneyCollectionList where receivedAmount equals to UPDATED_RECEIVED_AMOUNT
        defaultSupplyMoneyCollectionShouldNotBeFound("receivedAmount.equals=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByReceivedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where receivedAmount in DEFAULT_RECEIVED_AMOUNT or UPDATED_RECEIVED_AMOUNT
        defaultSupplyMoneyCollectionShouldBeFound("receivedAmount.in=" + DEFAULT_RECEIVED_AMOUNT + "," + UPDATED_RECEIVED_AMOUNT);

        // Get all the supplyMoneyCollectionList where receivedAmount equals to UPDATED_RECEIVED_AMOUNT
        defaultSupplyMoneyCollectionShouldNotBeFound("receivedAmount.in=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByReceivedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where receivedAmount is not null
        defaultSupplyMoneyCollectionShouldBeFound("receivedAmount.specified=true");

        // Get all the supplyMoneyCollectionList where receivedAmount is null
        defaultSupplyMoneyCollectionShouldNotBeFound("receivedAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where remarks equals to DEFAULT_REMARKS
        defaultSupplyMoneyCollectionShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the supplyMoneyCollectionList where remarks equals to UPDATED_REMARKS
        defaultSupplyMoneyCollectionShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSupplyMoneyCollectionShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the supplyMoneyCollectionList where remarks equals to UPDATED_REMARKS
        defaultSupplyMoneyCollectionShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where remarks is not null
        defaultSupplyMoneyCollectionShouldBeFound("remarks.specified=true");

        // Get all the supplyMoneyCollectionList where remarks is null
        defaultSupplyMoneyCollectionShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyMoneyCollectionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyMoneyCollectionList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyMoneyCollectionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyMoneyCollectionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyMoneyCollectionList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyMoneyCollectionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdBy is not null
        defaultSupplyMoneyCollectionShouldBeFound("createdBy.specified=true");

        // Get all the supplyMoneyCollectionList where createdBy is null
        defaultSupplyMoneyCollectionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyMoneyCollectionShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyMoneyCollectionList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyMoneyCollectionShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyMoneyCollectionShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyMoneyCollectionList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyMoneyCollectionShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where createdOn is not null
        defaultSupplyMoneyCollectionShouldBeFound("createdOn.specified=true");

        // Get all the supplyMoneyCollectionList where createdOn is null
        defaultSupplyMoneyCollectionShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyMoneyCollectionShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyMoneyCollectionList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyMoneyCollectionShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyMoneyCollectionList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedBy is not null
        defaultSupplyMoneyCollectionShouldBeFound("updatedBy.specified=true");

        // Get all the supplyMoneyCollectionList where updatedBy is null
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyMoneyCollectionShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyMoneyCollectionList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyMoneyCollectionShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyMoneyCollectionList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        // Get all the supplyMoneyCollectionList where updatedOn is not null
        defaultSupplyMoneyCollectionShouldBeFound("updatedOn.specified=true");

        // Get all the supplyMoneyCollectionList where updatedOn is null
        defaultSupplyMoneyCollectionShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyMoneyCollection.setSupplyZone(supplyZone);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyMoneyCollectionList where supplyZone equals to supplyZoneId
        defaultSupplyMoneyCollectionShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyMoneyCollectionList where supplyZone equals to supplyZoneId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyMoneyCollection.setSupplyZoneManager(supplyZoneManager);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyMoneyCollectionList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyMoneyCollectionShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyMoneyCollectionList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyMoneyCollection.setSupplyArea(supplyArea);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyMoneyCollectionList where supplyArea equals to supplyAreaId
        defaultSupplyMoneyCollectionShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyMoneyCollectionList where supplyArea equals to supplyAreaId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyMoneyCollection.setSupplyAreaManager(supplyAreaManager);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplyMoneyCollectionList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplyMoneyCollectionShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplyMoneyCollectionList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplySalesRepresentativeIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyMoneyCollection.setSupplySalesRepresentative(supplySalesRepresentative);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplySalesRepresentativeId = supplySalesRepresentative.getId();

        // Get all the supplyMoneyCollectionList where supplySalesRepresentative equals to supplySalesRepresentativeId
        defaultSupplyMoneyCollectionShouldBeFound("supplySalesRepresentativeId.equals=" + supplySalesRepresentativeId);

        // Get all the supplyMoneyCollectionList where supplySalesRepresentative equals to supplySalesRepresentativeId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplySalesRepresentativeId.equals=" + (supplySalesRepresentativeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyShopIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyMoneyCollection.setSupplyShop(supplyShop);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyShopId = supplyShop.getId();

        // Get all the supplyMoneyCollectionList where supplyShop equals to supplyShopId
        defaultSupplyMoneyCollectionShouldBeFound("supplyShopId.equals=" + supplyShopId);

        // Get all the supplyMoneyCollectionList where supplyShop equals to supplyShopId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyShopId.equals=" + (supplyShopId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyMoneyCollectionsBySupplyOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyOrder supplyOrder = SupplyOrderResourceIntTest.createEntity(em);
        em.persist(supplyOrder);
        em.flush();
        supplyMoneyCollection.setSupplyOrder(supplyOrder);
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        Long supplyOrderId = supplyOrder.getId();

        // Get all the supplyMoneyCollectionList where supplyOrder equals to supplyOrderId
        defaultSupplyMoneyCollectionShouldBeFound("supplyOrderId.equals=" + supplyOrderId);

        // Get all the supplyMoneyCollectionList where supplyOrder equals to supplyOrderId + 1
        defaultSupplyMoneyCollectionShouldNotBeFound("supplyOrderId.equals=" + (supplyOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyMoneyCollectionShouldBeFound(String filter) throws Exception {
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyMoneyCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedAmount").value(hasItem(DEFAULT_RECEIVED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyMoneyCollectionShouldNotBeFound(String filter) throws Exception {
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyMoneyCollection() throws Exception {
        // Get the supplyMoneyCollection
        restSupplyMoneyCollectionMockMvc.perform(get("/api/supply-money-collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyMoneyCollection() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        int databaseSizeBeforeUpdate = supplyMoneyCollectionRepository.findAll().size();

        // Update the supplyMoneyCollection
        SupplyMoneyCollection updatedSupplyMoneyCollection = supplyMoneyCollectionRepository.findById(supplyMoneyCollection.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyMoneyCollection are not directly saved in db
        em.detach(updatedSupplyMoneyCollection);
        updatedSupplyMoneyCollection
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .receivedAmount(UPDATED_RECEIVED_AMOUNT)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO = supplyMoneyCollectionMapper.toDto(updatedSupplyMoneyCollection);

        restSupplyMoneyCollectionMockMvc.perform(put("/api/supply-money-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyMoneyCollectionDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyMoneyCollection in the database
        List<SupplyMoneyCollection> supplyMoneyCollectionList = supplyMoneyCollectionRepository.findAll();
        assertThat(supplyMoneyCollectionList).hasSize(databaseSizeBeforeUpdate);
        SupplyMoneyCollection testSupplyMoneyCollection = supplyMoneyCollectionList.get(supplyMoneyCollectionList.size() - 1);
        assertThat(testSupplyMoneyCollection.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testSupplyMoneyCollection.getReceivedAmount()).isEqualTo(UPDATED_RECEIVED_AMOUNT);
        assertThat(testSupplyMoneyCollection.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSupplyMoneyCollection.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyMoneyCollection.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyMoneyCollection.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyMoneyCollection.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyMoneyCollection in Elasticsearch
        verify(mockSupplyMoneyCollectionSearchRepository, times(1)).save(testSupplyMoneyCollection);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyMoneyCollection() throws Exception {
        int databaseSizeBeforeUpdate = supplyMoneyCollectionRepository.findAll().size();

        // Create the SupplyMoneyCollection
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO = supplyMoneyCollectionMapper.toDto(supplyMoneyCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyMoneyCollectionMockMvc.perform(put("/api/supply-money-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyMoneyCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyMoneyCollection in the database
        List<SupplyMoneyCollection> supplyMoneyCollectionList = supplyMoneyCollectionRepository.findAll();
        assertThat(supplyMoneyCollectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyMoneyCollection in Elasticsearch
        verify(mockSupplyMoneyCollectionSearchRepository, times(0)).save(supplyMoneyCollection);
    }

    @Test
    @Transactional
    public void deleteSupplyMoneyCollection() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);

        int databaseSizeBeforeDelete = supplyMoneyCollectionRepository.findAll().size();

        // Delete the supplyMoneyCollection
        restSupplyMoneyCollectionMockMvc.perform(delete("/api/supply-money-collections/{id}", supplyMoneyCollection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyMoneyCollection> supplyMoneyCollectionList = supplyMoneyCollectionRepository.findAll();
        assertThat(supplyMoneyCollectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyMoneyCollection in Elasticsearch
        verify(mockSupplyMoneyCollectionSearchRepository, times(1)).deleteById(supplyMoneyCollection.getId());
    }

    @Test
    @Transactional
    public void searchSupplyMoneyCollection() throws Exception {
        // Initialize the database
        supplyMoneyCollectionRepository.saveAndFlush(supplyMoneyCollection);
        when(mockSupplyMoneyCollectionSearchRepository.search(queryStringQuery("id:" + supplyMoneyCollection.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyMoneyCollection), PageRequest.of(0, 1), 1));
        // Search the supplyMoneyCollection
        restSupplyMoneyCollectionMockMvc.perform(get("/api/_search/supply-money-collections?query=id:" + supplyMoneyCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyMoneyCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedAmount").value(hasItem(DEFAULT_RECEIVED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyMoneyCollection.class);
        SupplyMoneyCollection supplyMoneyCollection1 = new SupplyMoneyCollection();
        supplyMoneyCollection1.setId(1L);
        SupplyMoneyCollection supplyMoneyCollection2 = new SupplyMoneyCollection();
        supplyMoneyCollection2.setId(supplyMoneyCollection1.getId());
        assertThat(supplyMoneyCollection1).isEqualTo(supplyMoneyCollection2);
        supplyMoneyCollection2.setId(2L);
        assertThat(supplyMoneyCollection1).isNotEqualTo(supplyMoneyCollection2);
        supplyMoneyCollection1.setId(null);
        assertThat(supplyMoneyCollection1).isNotEqualTo(supplyMoneyCollection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyMoneyCollectionDTO.class);
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO1 = new SupplyMoneyCollectionDTO();
        supplyMoneyCollectionDTO1.setId(1L);
        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO2 = new SupplyMoneyCollectionDTO();
        assertThat(supplyMoneyCollectionDTO1).isNotEqualTo(supplyMoneyCollectionDTO2);
        supplyMoneyCollectionDTO2.setId(supplyMoneyCollectionDTO1.getId());
        assertThat(supplyMoneyCollectionDTO1).isEqualTo(supplyMoneyCollectionDTO2);
        supplyMoneyCollectionDTO2.setId(2L);
        assertThat(supplyMoneyCollectionDTO1).isNotEqualTo(supplyMoneyCollectionDTO2);
        supplyMoneyCollectionDTO1.setId(null);
        assertThat(supplyMoneyCollectionDTO1).isNotEqualTo(supplyMoneyCollectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyMoneyCollectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyMoneyCollectionMapper.fromId(null)).isNull();
    }
}
