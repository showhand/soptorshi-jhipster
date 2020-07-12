package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyAreaWiseAccumulationStatus;
import org.soptorshi.repository.SupplyAreaWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepository;
import org.soptorshi.service.SupplyAreaWiseAccumulationQueryService;
import org.soptorshi.service.SupplyAreaWiseAccumulationService;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
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
import java.math.BigDecimal;
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
 * Test class for the SupplyAreaWiseAccumulationResource REST controller.
 *
 * @see SupplyAreaWiseAccumulationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyAreaWiseAccumulationResourceIntTest {

    private static final String DEFAULT_AREA_WISE_ACCUMULATION_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_AREA_WISE_ACCUMULATION_REF_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final SupplyAreaWiseAccumulationStatus DEFAULT_STATUS = SupplyAreaWiseAccumulationStatus.PENDING;
    private static final SupplyAreaWiseAccumulationStatus UPDATED_STATUS = SupplyAreaWiseAccumulationStatus.FORWARDED;

    private static final String DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_ZONE_WISE_ACCUMULATION_REF_NO = "BBBBBBBBBB";

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
    private SupplyAreaWiseAccumulationRepository supplyAreaWiseAccumulationRepository;

    @Autowired
    private SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper;

    @Autowired
    private SupplyAreaWiseAccumulationService supplyAreaWiseAccumulationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyAreaWiseAccumulationSearchRepository mockSupplyAreaWiseAccumulationSearchRepository;

    @Autowired
    private SupplyAreaWiseAccumulationQueryService supplyAreaWiseAccumulationQueryService;

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

    private MockMvc restSupplyAreaWiseAccumulationMockMvc;

    private SupplyAreaWiseAccumulation supplyAreaWiseAccumulation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyAreaWiseAccumulationResource supplyAreaWiseAccumulationResource = new SupplyAreaWiseAccumulationResource(supplyAreaWiseAccumulationService, supplyAreaWiseAccumulationQueryService);
        this.restSupplyAreaWiseAccumulationMockMvc = MockMvcBuilders.standaloneSetup(supplyAreaWiseAccumulationResource)
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
    public static SupplyAreaWiseAccumulation createEntity(EntityManager em) {
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation = new SupplyAreaWiseAccumulation()
            .areaWiseAccumulationRefNo(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .zoneWiseAccumulationRefNo(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyArea(supplyArea);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyAreaManager(supplyAreaManager);
        return supplyAreaWiseAccumulation;
    }

    @Before
    public void initTest() {
        supplyAreaWiseAccumulation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyAreaWiseAccumulation() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaWiseAccumulationRepository.findAll().size();

        // Create the SupplyAreaWiseAccumulation
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);
        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyAreaWiseAccumulation in the database
        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyAreaWiseAccumulation testSupplyAreaWiseAccumulation = supplyAreaWiseAccumulationList.get(supplyAreaWiseAccumulationList.size() - 1);
        assertThat(testSupplyAreaWiseAccumulation.getAreaWiseAccumulationRefNo()).isEqualTo(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyAreaWiseAccumulation.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSupplyAreaWiseAccumulation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSupplyAreaWiseAccumulation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSupplyAreaWiseAccumulation.getZoneWiseAccumulationRefNo()).isEqualTo(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyAreaWiseAccumulation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSupplyAreaWiseAccumulation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyAreaWiseAccumulation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyAreaWiseAccumulation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyAreaWiseAccumulation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyAreaWiseAccumulation in Elasticsearch
        verify(mockSupplyAreaWiseAccumulationSearchRepository, times(1)).save(testSupplyAreaWiseAccumulation);
    }

    @Test
    @Transactional
    public void createSupplyAreaWiseAccumulationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaWiseAccumulationRepository.findAll().size();

        // Create the SupplyAreaWiseAccumulation with an existing ID
        supplyAreaWiseAccumulation.setId(1L);
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyAreaWiseAccumulation in the database
        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyAreaWiseAccumulation in Elasticsearch
        verify(mockSupplyAreaWiseAccumulationSearchRepository, times(0)).save(supplyAreaWiseAccumulation);
    }

    @Test
    @Transactional
    public void checkAreaWiseAccumulationRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyAreaWiseAccumulation.setAreaWiseAccumulationRefNo(null);

        // Create the SupplyAreaWiseAccumulation, which fails.
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyAreaWiseAccumulation.setQuantity(null);

        // Create the SupplyAreaWiseAccumulation, which fails.
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyAreaWiseAccumulation.setPrice(null);

        // Create the SupplyAreaWiseAccumulation, which fails.
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyAreaWiseAccumulation.setStatus(null);

        // Create the SupplyAreaWiseAccumulation, which fails.
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        restSupplyAreaWiseAccumulationMockMvc.perform(post("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulations() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyAreaWiseAccumulation() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get the supplyAreaWiseAccumulation
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations/{id}", supplyAreaWiseAccumulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyAreaWiseAccumulation.getId().intValue()))
            .andExpect(jsonPath("$.areaWiseAccumulationRefNo").value(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.zoneWiseAccumulationRefNo").value(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByAreaWiseAccumulationRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo equals to DEFAULT_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldBeFound("areaWiseAccumulationRefNo.equals=" + DEFAULT_AREA_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo equals to UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("areaWiseAccumulationRefNo.equals=" + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByAreaWiseAccumulationRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo in DEFAULT_AREA_WISE_ACCUMULATION_REF_NO or UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldBeFound("areaWiseAccumulationRefNo.in=" + DEFAULT_AREA_WISE_ACCUMULATION_REF_NO + "," + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo equals to UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("areaWiseAccumulationRefNo.in=" + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByAreaWiseAccumulationRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("areaWiseAccumulationRefNo.specified=true");

        // Get all the supplyAreaWiseAccumulationList where areaWiseAccumulationRefNo is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("areaWiseAccumulationRefNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where quantity equals to DEFAULT_QUANTITY
        defaultSupplyAreaWiseAccumulationShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the supplyAreaWiseAccumulationList where quantity equals to UPDATED_QUANTITY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultSupplyAreaWiseAccumulationShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the supplyAreaWiseAccumulationList where quantity equals to UPDATED_QUANTITY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where quantity is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("quantity.specified=true");

        // Get all the supplyAreaWiseAccumulationList where quantity is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where price equals to DEFAULT_PRICE
        defaultSupplyAreaWiseAccumulationShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the supplyAreaWiseAccumulationList where price equals to UPDATED_PRICE
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultSupplyAreaWiseAccumulationShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the supplyAreaWiseAccumulationList where price equals to UPDATED_PRICE
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where price is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("price.specified=true");

        // Get all the supplyAreaWiseAccumulationList where price is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where status equals to DEFAULT_STATUS
        defaultSupplyAreaWiseAccumulationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the supplyAreaWiseAccumulationList where status equals to UPDATED_STATUS
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSupplyAreaWiseAccumulationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the supplyAreaWiseAccumulationList where status equals to UPDATED_STATUS
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where status is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("status.specified=true");

        // Get all the supplyAreaWiseAccumulationList where status is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByZoneWiseAccumulationRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo equals to DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.equals=" + DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo equals to UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.equals=" + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByZoneWiseAccumulationRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo in DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO or UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.in=" + DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO + "," + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo equals to UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.in=" + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByZoneWiseAccumulationRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.specified=true");

        // Get all the supplyAreaWiseAccumulationList where zoneWiseAccumulationRefNo is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where remarks equals to DEFAULT_REMARKS
        defaultSupplyAreaWiseAccumulationShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the supplyAreaWiseAccumulationList where remarks equals to UPDATED_REMARKS
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSupplyAreaWiseAccumulationShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the supplyAreaWiseAccumulationList where remarks equals to UPDATED_REMARKS
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where remarks is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("remarks.specified=true");

        // Get all the supplyAreaWiseAccumulationList where remarks is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyAreaWiseAccumulationList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyAreaWiseAccumulationList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdBy is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdBy.specified=true");

        // Get all the supplyAreaWiseAccumulationList where createdBy is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyAreaWiseAccumulationList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyAreaWiseAccumulationList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where createdOn is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("createdOn.specified=true");

        // Get all the supplyAreaWiseAccumulationList where createdOn is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyAreaWiseAccumulationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyAreaWiseAccumulationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedBy is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedBy.specified=true");

        // Get all the supplyAreaWiseAccumulationList where updatedBy is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyAreaWiseAccumulationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyAreaWiseAccumulationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        // Get all the supplyAreaWiseAccumulationList where updatedOn is not null
        defaultSupplyAreaWiseAccumulationShouldBeFound("updatedOn.specified=true");

        // Get all the supplyAreaWiseAccumulationList where updatedOn is null
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyZone(supplyZone);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyAreaWiseAccumulationList where supplyZone equals to supplyZoneId
        defaultSupplyAreaWiseAccumulationShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyAreaWiseAccumulationList where supplyZone equals to supplyZoneId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyZoneManager(supplyZoneManager);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyAreaWiseAccumulationList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyAreaWiseAccumulationShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyAreaWiseAccumulationList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyArea(supplyArea);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyAreaWiseAccumulationList where supplyArea equals to supplyAreaId
        defaultSupplyAreaWiseAccumulationShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyAreaWiseAccumulationList where supplyArea equals to supplyAreaId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyAreaWiseAccumulation.setSupplyAreaManager(supplyAreaManager);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplyAreaWiseAccumulationList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplyAreaWiseAccumulationShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplyAreaWiseAccumulationList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        supplyAreaWiseAccumulation.setProductCategory(productCategory);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long productCategoryId = productCategory.getId();

        // Get all the supplyAreaWiseAccumulationList where productCategory equals to productCategoryId
        defaultSupplyAreaWiseAccumulationShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the supplyAreaWiseAccumulationList where productCategory equals to productCategoryId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreaWiseAccumulationsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        supplyAreaWiseAccumulation.setProduct(product);
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        Long productId = product.getId();

        // Get all the supplyAreaWiseAccumulationList where product equals to productId
        defaultSupplyAreaWiseAccumulationShouldBeFound("productId.equals=" + productId);

        // Get all the supplyAreaWiseAccumulationList where product equals to productId + 1
        defaultSupplyAreaWiseAccumulationShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyAreaWiseAccumulationShouldBeFound(String filter) throws Exception {
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyAreaWiseAccumulationShouldNotBeFound(String filter) throws Exception {
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyAreaWiseAccumulation() throws Exception {
        // Get the supplyAreaWiseAccumulation
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/supply-area-wise-accumulations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyAreaWiseAccumulation() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        int databaseSizeBeforeUpdate = supplyAreaWiseAccumulationRepository.findAll().size();

        // Update the supplyAreaWiseAccumulation
        SupplyAreaWiseAccumulation updatedSupplyAreaWiseAccumulation = supplyAreaWiseAccumulationRepository.findById(supplyAreaWiseAccumulation.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyAreaWiseAccumulation are not directly saved in db
        em.detach(updatedSupplyAreaWiseAccumulation);
        updatedSupplyAreaWiseAccumulation
            .areaWiseAccumulationRefNo(UPDATED_AREA_WISE_ACCUMULATION_REF_NO)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .zoneWiseAccumulationRefNo(UPDATED_ZONE_WISE_ACCUMULATION_REF_NO)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(updatedSupplyAreaWiseAccumulation);

        restSupplyAreaWiseAccumulationMockMvc.perform(put("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyAreaWiseAccumulation in the database
        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeUpdate);
        SupplyAreaWiseAccumulation testSupplyAreaWiseAccumulation = supplyAreaWiseAccumulationList.get(supplyAreaWiseAccumulationList.size() - 1);
        assertThat(testSupplyAreaWiseAccumulation.getAreaWiseAccumulationRefNo()).isEqualTo(UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyAreaWiseAccumulation.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSupplyAreaWiseAccumulation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSupplyAreaWiseAccumulation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSupplyAreaWiseAccumulation.getZoneWiseAccumulationRefNo()).isEqualTo(UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyAreaWiseAccumulation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSupplyAreaWiseAccumulation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyAreaWiseAccumulation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyAreaWiseAccumulation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyAreaWiseAccumulation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyAreaWiseAccumulation in Elasticsearch
        verify(mockSupplyAreaWiseAccumulationSearchRepository, times(1)).save(testSupplyAreaWiseAccumulation);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyAreaWiseAccumulation() throws Exception {
        int databaseSizeBeforeUpdate = supplyAreaWiseAccumulationRepository.findAll().size();

        // Create the SupplyAreaWiseAccumulation
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyAreaWiseAccumulationMockMvc.perform(put("/api/supply-area-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyAreaWiseAccumulation in the database
        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyAreaWiseAccumulation in Elasticsearch
        verify(mockSupplyAreaWiseAccumulationSearchRepository, times(0)).save(supplyAreaWiseAccumulation);
    }

    @Test
    @Transactional
    public void deleteSupplyAreaWiseAccumulation() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);

        int databaseSizeBeforeDelete = supplyAreaWiseAccumulationRepository.findAll().size();

        // Delete the supplyAreaWiseAccumulation
        restSupplyAreaWiseAccumulationMockMvc.perform(delete("/api/supply-area-wise-accumulations/{id}", supplyAreaWiseAccumulation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulationList = supplyAreaWiseAccumulationRepository.findAll();
        assertThat(supplyAreaWiseAccumulationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyAreaWiseAccumulation in Elasticsearch
        verify(mockSupplyAreaWiseAccumulationSearchRepository, times(1)).deleteById(supplyAreaWiseAccumulation.getId());
    }

    @Test
    @Transactional
    public void searchSupplyAreaWiseAccumulation() throws Exception {
        // Initialize the database
        supplyAreaWiseAccumulationRepository.saveAndFlush(supplyAreaWiseAccumulation);
        when(mockSupplyAreaWiseAccumulationSearchRepository.search(queryStringQuery("id:" + supplyAreaWiseAccumulation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyAreaWiseAccumulation), PageRequest.of(0, 1), 1));
        // Search the supplyAreaWiseAccumulation
        restSupplyAreaWiseAccumulationMockMvc.perform(get("/api/_search/supply-area-wise-accumulations?query=id:" + supplyAreaWiseAccumulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyAreaWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyAreaWiseAccumulation.class);
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation1 = new SupplyAreaWiseAccumulation();
        supplyAreaWiseAccumulation1.setId(1L);
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation2 = new SupplyAreaWiseAccumulation();
        supplyAreaWiseAccumulation2.setId(supplyAreaWiseAccumulation1.getId());
        assertThat(supplyAreaWiseAccumulation1).isEqualTo(supplyAreaWiseAccumulation2);
        supplyAreaWiseAccumulation2.setId(2L);
        assertThat(supplyAreaWiseAccumulation1).isNotEqualTo(supplyAreaWiseAccumulation2);
        supplyAreaWiseAccumulation1.setId(null);
        assertThat(supplyAreaWiseAccumulation1).isNotEqualTo(supplyAreaWiseAccumulation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyAreaWiseAccumulationDTO.class);
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO1 = new SupplyAreaWiseAccumulationDTO();
        supplyAreaWiseAccumulationDTO1.setId(1L);
        SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO2 = new SupplyAreaWiseAccumulationDTO();
        assertThat(supplyAreaWiseAccumulationDTO1).isNotEqualTo(supplyAreaWiseAccumulationDTO2);
        supplyAreaWiseAccumulationDTO2.setId(supplyAreaWiseAccumulationDTO1.getId());
        assertThat(supplyAreaWiseAccumulationDTO1).isEqualTo(supplyAreaWiseAccumulationDTO2);
        supplyAreaWiseAccumulationDTO2.setId(2L);
        assertThat(supplyAreaWiseAccumulationDTO1).isNotEqualTo(supplyAreaWiseAccumulationDTO2);
        supplyAreaWiseAccumulationDTO1.setId(null);
        assertThat(supplyAreaWiseAccumulationDTO1).isNotEqualTo(supplyAreaWiseAccumulationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyAreaWiseAccumulationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyAreaWiseAccumulationMapper.fromId(null)).isNull();
    }
}
