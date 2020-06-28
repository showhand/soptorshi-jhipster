package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyZoneWiseAccumulationStatus;
import org.soptorshi.repository.SupplyZoneWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepository;
import org.soptorshi.service.SupplyZoneWiseAccumulationQueryService;
import org.soptorshi.service.SupplyZoneWiseAccumulationService;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyZoneWiseAccumulationMapper;
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
 * Test class for the SupplyZoneWiseAccumulationResource REST controller.
 *
 * @see SupplyZoneWiseAccumulationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyZoneWiseAccumulationResourceIntTest {

    private static final String DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_ZONE_WISE_ACCUMULATION_REF_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final SupplyZoneWiseAccumulationStatus DEFAULT_STATUS = SupplyZoneWiseAccumulationStatus.PENDING;
    private static final SupplyZoneWiseAccumulationStatus UPDATED_STATUS = SupplyZoneWiseAccumulationStatus.APPROVED;

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
    private SupplyZoneWiseAccumulationRepository supplyZoneWiseAccumulationRepository;

    @Autowired
    private SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper;

    @Autowired
    private SupplyZoneWiseAccumulationService supplyZoneWiseAccumulationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyZoneWiseAccumulationSearchRepository mockSupplyZoneWiseAccumulationSearchRepository;

    @Autowired
    private SupplyZoneWiseAccumulationQueryService supplyZoneWiseAccumulationQueryService;

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

    private MockMvc restSupplyZoneWiseAccumulationMockMvc;

    private SupplyZoneWiseAccumulation supplyZoneWiseAccumulation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyZoneWiseAccumulationResource supplyZoneWiseAccumulationResource = new SupplyZoneWiseAccumulationResource(supplyZoneWiseAccumulationService, supplyZoneWiseAccumulationQueryService);
        this.restSupplyZoneWiseAccumulationMockMvc = MockMvcBuilders.standaloneSetup(supplyZoneWiseAccumulationResource)
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
    public static SupplyZoneWiseAccumulation createEntity(EntityManager em) {
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation = new SupplyZoneWiseAccumulation()
            .zoneWiseAccumulationRefNo(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyZoneWiseAccumulation.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyZoneWiseAccumulation.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        supplyZoneWiseAccumulation.setProductCategory(productCategory);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        supplyZoneWiseAccumulation.setProduct(product);
        return supplyZoneWiseAccumulation;
    }

    @Before
    public void initTest() {
        supplyZoneWiseAccumulation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyZoneWiseAccumulation() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneWiseAccumulationRepository.findAll().size();

        // Create the SupplyZoneWiseAccumulation
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);
        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyZoneWiseAccumulation in the database
        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyZoneWiseAccumulation testSupplyZoneWiseAccumulation = supplyZoneWiseAccumulationList.get(supplyZoneWiseAccumulationList.size() - 1);
        assertThat(testSupplyZoneWiseAccumulation.getZoneWiseAccumulationRefNo()).isEqualTo(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyZoneWiseAccumulation.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSupplyZoneWiseAccumulation.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSupplyZoneWiseAccumulation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSupplyZoneWiseAccumulation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testSupplyZoneWiseAccumulation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyZoneWiseAccumulation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyZoneWiseAccumulation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyZoneWiseAccumulation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyZoneWiseAccumulation in Elasticsearch
        verify(mockSupplyZoneWiseAccumulationSearchRepository, times(1)).save(testSupplyZoneWiseAccumulation);
    }

    @Test
    @Transactional
    public void createSupplyZoneWiseAccumulationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneWiseAccumulationRepository.findAll().size();

        // Create the SupplyZoneWiseAccumulation with an existing ID
        supplyZoneWiseAccumulation.setId(1L);
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneWiseAccumulation in the database
        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyZoneWiseAccumulation in Elasticsearch
        verify(mockSupplyZoneWiseAccumulationSearchRepository, times(0)).save(supplyZoneWiseAccumulation);
    }

    @Test
    @Transactional
    public void checkZoneWiseAccumulationRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyZoneWiseAccumulation.setZoneWiseAccumulationRefNo(null);

        // Create the SupplyZoneWiseAccumulation, which fails.
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyZoneWiseAccumulation.setQuantity(null);

        // Create the SupplyZoneWiseAccumulation, which fails.
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyZoneWiseAccumulation.setPrice(null);

        // Create the SupplyZoneWiseAccumulation, which fails.
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneWiseAccumulationRepository.findAll().size();
        // set the field null
        supplyZoneWiseAccumulation.setStatus(null);

        // Create the SupplyZoneWiseAccumulation, which fails.
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        restSupplyZoneWiseAccumulationMockMvc.perform(post("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulations() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyZoneWiseAccumulation() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get the supplyZoneWiseAccumulation
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations/{id}", supplyZoneWiseAccumulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyZoneWiseAccumulation.getId().intValue()))
            .andExpect(jsonPath("$.zoneWiseAccumulationRefNo").value(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByZoneWiseAccumulationRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo equals to DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyZoneWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.equals=" + DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo equals to UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.equals=" + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByZoneWiseAccumulationRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo in DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO or UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyZoneWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.in=" + DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO + "," + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo equals to UPDATED_ZONE_WISE_ACCUMULATION_REF_NO
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.in=" + UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByZoneWiseAccumulationRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("zoneWiseAccumulationRefNo.specified=true");

        // Get all the supplyZoneWiseAccumulationList where zoneWiseAccumulationRefNo is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("zoneWiseAccumulationRefNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where quantity equals to DEFAULT_QUANTITY
        defaultSupplyZoneWiseAccumulationShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the supplyZoneWiseAccumulationList where quantity equals to UPDATED_QUANTITY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultSupplyZoneWiseAccumulationShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the supplyZoneWiseAccumulationList where quantity equals to UPDATED_QUANTITY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where quantity is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("quantity.specified=true");

        // Get all the supplyZoneWiseAccumulationList where quantity is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where price equals to DEFAULT_PRICE
        defaultSupplyZoneWiseAccumulationShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the supplyZoneWiseAccumulationList where price equals to UPDATED_PRICE
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultSupplyZoneWiseAccumulationShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the supplyZoneWiseAccumulationList where price equals to UPDATED_PRICE
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where price is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("price.specified=true");

        // Get all the supplyZoneWiseAccumulationList where price is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where status equals to DEFAULT_STATUS
        defaultSupplyZoneWiseAccumulationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the supplyZoneWiseAccumulationList where status equals to UPDATED_STATUS
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSupplyZoneWiseAccumulationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the supplyZoneWiseAccumulationList where status equals to UPDATED_STATUS
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where status is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("status.specified=true");

        // Get all the supplyZoneWiseAccumulationList where status is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where remarks equals to DEFAULT_REMARKS
        defaultSupplyZoneWiseAccumulationShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the supplyZoneWiseAccumulationList where remarks equals to UPDATED_REMARKS
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSupplyZoneWiseAccumulationShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the supplyZoneWiseAccumulationList where remarks equals to UPDATED_REMARKS
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where remarks is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("remarks.specified=true");

        // Get all the supplyZoneWiseAccumulationList where remarks is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyZoneWiseAccumulationList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyZoneWiseAccumulationList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdBy is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdBy.specified=true");

        // Get all the supplyZoneWiseAccumulationList where createdBy is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyZoneWiseAccumulationList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyZoneWiseAccumulationList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where createdOn is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("createdOn.specified=true");

        // Get all the supplyZoneWiseAccumulationList where createdOn is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyZoneWiseAccumulationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyZoneWiseAccumulationList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedBy is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedBy.specified=true");

        // Get all the supplyZoneWiseAccumulationList where updatedBy is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyZoneWiseAccumulationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyZoneWiseAccumulationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        // Get all the supplyZoneWiseAccumulationList where updatedOn is not null
        defaultSupplyZoneWiseAccumulationShouldBeFound("updatedOn.specified=true");

        // Get all the supplyZoneWiseAccumulationList where updatedOn is null
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyZoneWiseAccumulation.setSupplyZone(supplyZone);
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyZoneWiseAccumulationList where supplyZone equals to supplyZoneId
        defaultSupplyZoneWiseAccumulationShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyZoneWiseAccumulationList where supplyZone equals to supplyZoneId + 1
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyZoneWiseAccumulation.setSupplyZoneManager(supplyZoneManager);
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyZoneWiseAccumulationList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyZoneWiseAccumulationShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyZoneWiseAccumulationList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        supplyZoneWiseAccumulation.setProductCategory(productCategory);
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);
        Long productCategoryId = productCategory.getId();

        // Get all the supplyZoneWiseAccumulationList where productCategory equals to productCategoryId
        defaultSupplyZoneWiseAccumulationShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the supplyZoneWiseAccumulationList where productCategory equals to productCategoryId + 1
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyZoneWiseAccumulationsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        supplyZoneWiseAccumulation.setProduct(product);
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);
        Long productId = product.getId();

        // Get all the supplyZoneWiseAccumulationList where product equals to productId
        defaultSupplyZoneWiseAccumulationShouldBeFound("productId.equals=" + productId);

        // Get all the supplyZoneWiseAccumulationList where product equals to productId + 1
        defaultSupplyZoneWiseAccumulationShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyZoneWiseAccumulationShouldBeFound(String filter) throws Exception {
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyZoneWiseAccumulationShouldNotBeFound(String filter) throws Exception {
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyZoneWiseAccumulation() throws Exception {
        // Get the supplyZoneWiseAccumulation
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/supply-zone-wise-accumulations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyZoneWiseAccumulation() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        int databaseSizeBeforeUpdate = supplyZoneWiseAccumulationRepository.findAll().size();

        // Update the supplyZoneWiseAccumulation
        SupplyZoneWiseAccumulation updatedSupplyZoneWiseAccumulation = supplyZoneWiseAccumulationRepository.findById(supplyZoneWiseAccumulation.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyZoneWiseAccumulation are not directly saved in db
        em.detach(updatedSupplyZoneWiseAccumulation);
        updatedSupplyZoneWiseAccumulation
            .zoneWiseAccumulationRefNo(UPDATED_ZONE_WISE_ACCUMULATION_REF_NO)
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(updatedSupplyZoneWiseAccumulation);

        restSupplyZoneWiseAccumulationMockMvc.perform(put("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyZoneWiseAccumulation in the database
        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeUpdate);
        SupplyZoneWiseAccumulation testSupplyZoneWiseAccumulation = supplyZoneWiseAccumulationList.get(supplyZoneWiseAccumulationList.size() - 1);
        assertThat(testSupplyZoneWiseAccumulation.getZoneWiseAccumulationRefNo()).isEqualTo(UPDATED_ZONE_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyZoneWiseAccumulation.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSupplyZoneWiseAccumulation.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSupplyZoneWiseAccumulation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSupplyZoneWiseAccumulation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testSupplyZoneWiseAccumulation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyZoneWiseAccumulation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyZoneWiseAccumulation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyZoneWiseAccumulation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyZoneWiseAccumulation in Elasticsearch
        verify(mockSupplyZoneWiseAccumulationSearchRepository, times(1)).save(testSupplyZoneWiseAccumulation);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyZoneWiseAccumulation() throws Exception {
        int databaseSizeBeforeUpdate = supplyZoneWiseAccumulationRepository.findAll().size();

        // Create the SupplyZoneWiseAccumulation
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyZoneWiseAccumulationMockMvc.perform(put("/api/supply-zone-wise-accumulations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneWiseAccumulationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneWiseAccumulation in the database
        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyZoneWiseAccumulation in Elasticsearch
        verify(mockSupplyZoneWiseAccumulationSearchRepository, times(0)).save(supplyZoneWiseAccumulation);
    }

    @Test
    @Transactional
    public void deleteSupplyZoneWiseAccumulation() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);

        int databaseSizeBeforeDelete = supplyZoneWiseAccumulationRepository.findAll().size();

        // Delete the supplyZoneWiseAccumulation
        restSupplyZoneWiseAccumulationMockMvc.perform(delete("/api/supply-zone-wise-accumulations/{id}", supplyZoneWiseAccumulation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyZoneWiseAccumulation> supplyZoneWiseAccumulationList = supplyZoneWiseAccumulationRepository.findAll();
        assertThat(supplyZoneWiseAccumulationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyZoneWiseAccumulation in Elasticsearch
        verify(mockSupplyZoneWiseAccumulationSearchRepository, times(1)).deleteById(supplyZoneWiseAccumulation.getId());
    }

    @Test
    @Transactional
    public void searchSupplyZoneWiseAccumulation() throws Exception {
        // Initialize the database
        supplyZoneWiseAccumulationRepository.saveAndFlush(supplyZoneWiseAccumulation);
        when(mockSupplyZoneWiseAccumulationSearchRepository.search(queryStringQuery("id:" + supplyZoneWiseAccumulation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyZoneWiseAccumulation), PageRequest.of(0, 1), 1));
        // Search the supplyZoneWiseAccumulation
        restSupplyZoneWiseAccumulationMockMvc.perform(get("/api/_search/supply-zone-wise-accumulations?query=id:" + supplyZoneWiseAccumulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneWiseAccumulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].zoneWiseAccumulationRefNo").value(hasItem(DEFAULT_ZONE_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneWiseAccumulation.class);
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation1 = new SupplyZoneWiseAccumulation();
        supplyZoneWiseAccumulation1.setId(1L);
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation2 = new SupplyZoneWiseAccumulation();
        supplyZoneWiseAccumulation2.setId(supplyZoneWiseAccumulation1.getId());
        assertThat(supplyZoneWiseAccumulation1).isEqualTo(supplyZoneWiseAccumulation2);
        supplyZoneWiseAccumulation2.setId(2L);
        assertThat(supplyZoneWiseAccumulation1).isNotEqualTo(supplyZoneWiseAccumulation2);
        supplyZoneWiseAccumulation1.setId(null);
        assertThat(supplyZoneWiseAccumulation1).isNotEqualTo(supplyZoneWiseAccumulation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneWiseAccumulationDTO.class);
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO1 = new SupplyZoneWiseAccumulationDTO();
        supplyZoneWiseAccumulationDTO1.setId(1L);
        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO2 = new SupplyZoneWiseAccumulationDTO();
        assertThat(supplyZoneWiseAccumulationDTO1).isNotEqualTo(supplyZoneWiseAccumulationDTO2);
        supplyZoneWiseAccumulationDTO2.setId(supplyZoneWiseAccumulationDTO1.getId());
        assertThat(supplyZoneWiseAccumulationDTO1).isEqualTo(supplyZoneWiseAccumulationDTO2);
        supplyZoneWiseAccumulationDTO2.setId(2L);
        assertThat(supplyZoneWiseAccumulationDTO1).isNotEqualTo(supplyZoneWiseAccumulationDTO2);
        supplyZoneWiseAccumulationDTO1.setId(null);
        assertThat(supplyZoneWiseAccumulationDTO1).isNotEqualTo(supplyZoneWiseAccumulationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyZoneWiseAccumulationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyZoneWiseAccumulationMapper.fromId(null)).isNull();
    }
}
