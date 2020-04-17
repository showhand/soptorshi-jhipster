package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplySalesRepresentative;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.repository.SupplySalesRepresentativeRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.service.SupplySalesRepresentativeQueryService;
import org.soptorshi.service.SupplySalesRepresentativeService;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.mapper.SupplySalesRepresentativeMapper;
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
 * Test class for the SupplySalesRepresentativeResource REST controller.
 *
 * @see SupplySalesRepresentativeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplySalesRepresentativeResourceIntTest {

    private static final String DEFAULT_SALES_REPRESENTATIVE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SALES_REPRESENTATIVE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplySalesRepresentativeRepository supplySalesRepresentativeRepository;

    @Autowired
    private SupplySalesRepresentativeMapper supplySalesRepresentativeMapper;

    @Autowired
    private SupplySalesRepresentativeService supplySalesRepresentativeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplySalesRepresentativeSearchRepository mockSupplySalesRepresentativeSearchRepository;

    @Autowired
    private SupplySalesRepresentativeQueryService supplySalesRepresentativeQueryService;

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

    private MockMvc restSupplySalesRepresentativeMockMvc;

    private SupplySalesRepresentative supplySalesRepresentative;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplySalesRepresentativeResource supplySalesRepresentativeResource = new SupplySalesRepresentativeResource(supplySalesRepresentativeService, supplySalesRepresentativeQueryService);
        this.restSupplySalesRepresentativeMockMvc = MockMvcBuilders.standaloneSetup(supplySalesRepresentativeResource)
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
    public static SupplySalesRepresentative createEntity(EntityManager em) {
        SupplySalesRepresentative supplySalesRepresentative = new SupplySalesRepresentative()
            .salesRepresentativeName(DEFAULT_SALES_REPRESENTATIVE_NAME)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplySalesRepresentative.setSupplyAreaManager(supplyAreaManager);
        return supplySalesRepresentative;
    }

    @Before
    public void initTest() {
        supplySalesRepresentative = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplySalesRepresentative() throws Exception {
        int databaseSizeBeforeCreate = supplySalesRepresentativeRepository.findAll().size();

        // Create the SupplySalesRepresentative
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);
        restSupplySalesRepresentativeMockMvc.perform(post("/api/supply-sales-representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplySalesRepresentativeDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplySalesRepresentative in the database
        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeCreate + 1);
        SupplySalesRepresentative testSupplySalesRepresentative = supplySalesRepresentativeList.get(supplySalesRepresentativeList.size() - 1);
        assertThat(testSupplySalesRepresentative.getSalesRepresentativeName()).isEqualTo(DEFAULT_SALES_REPRESENTATIVE_NAME);
        assertThat(testSupplySalesRepresentative.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testSupplySalesRepresentative.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplySalesRepresentative.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplySalesRepresentative.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplySalesRepresentative.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplySalesRepresentative in Elasticsearch
        verify(mockSupplySalesRepresentativeSearchRepository, times(1)).save(testSupplySalesRepresentative);
    }

    @Test
    @Transactional
    public void createSupplySalesRepresentativeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplySalesRepresentativeRepository.findAll().size();

        // Create the SupplySalesRepresentative with an existing ID
        supplySalesRepresentative.setId(1L);
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplySalesRepresentativeMockMvc.perform(post("/api/supply-sales-representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplySalesRepresentativeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplySalesRepresentative in the database
        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplySalesRepresentative in Elasticsearch
        verify(mockSupplySalesRepresentativeSearchRepository, times(0)).save(supplySalesRepresentative);
    }

    @Test
    @Transactional
    public void checkSalesRepresentativeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplySalesRepresentativeRepository.findAll().size();
        // set the field null
        supplySalesRepresentative.setSalesRepresentativeName(null);

        // Create the SupplySalesRepresentative, which fails.
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);

        restSupplySalesRepresentativeMockMvc.perform(post("/api/supply-sales-representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplySalesRepresentativeDTO)))
            .andExpect(status().isBadRequest());

        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentatives() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplySalesRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesRepresentativeName").value(hasItem(DEFAULT_SALES_REPRESENTATIVE_NAME.toString())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplySalesRepresentative() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get the supplySalesRepresentative
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives/{id}", supplySalesRepresentative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplySalesRepresentative.getId().intValue()))
            .andExpect(jsonPath("$.salesRepresentativeName").value(DEFAULT_SALES_REPRESENTATIVE_NAME.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySalesRepresentativeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where salesRepresentativeName equals to DEFAULT_SALES_REPRESENTATIVE_NAME
        defaultSupplySalesRepresentativeShouldBeFound("salesRepresentativeName.equals=" + DEFAULT_SALES_REPRESENTATIVE_NAME);

        // Get all the supplySalesRepresentativeList where salesRepresentativeName equals to UPDATED_SALES_REPRESENTATIVE_NAME
        defaultSupplySalesRepresentativeShouldNotBeFound("salesRepresentativeName.equals=" + UPDATED_SALES_REPRESENTATIVE_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySalesRepresentativeNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where salesRepresentativeName in DEFAULT_SALES_REPRESENTATIVE_NAME or UPDATED_SALES_REPRESENTATIVE_NAME
        defaultSupplySalesRepresentativeShouldBeFound("salesRepresentativeName.in=" + DEFAULT_SALES_REPRESENTATIVE_NAME + "," + UPDATED_SALES_REPRESENTATIVE_NAME);

        // Get all the supplySalesRepresentativeList where salesRepresentativeName equals to UPDATED_SALES_REPRESENTATIVE_NAME
        defaultSupplySalesRepresentativeShouldNotBeFound("salesRepresentativeName.in=" + UPDATED_SALES_REPRESENTATIVE_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySalesRepresentativeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where salesRepresentativeName is not null
        defaultSupplySalesRepresentativeShouldBeFound("salesRepresentativeName.specified=true");

        // Get all the supplySalesRepresentativeList where salesRepresentativeName is null
        defaultSupplySalesRepresentativeShouldNotBeFound("salesRepresentativeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByAdditionalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where additionalInformation equals to DEFAULT_ADDITIONAL_INFORMATION
        defaultSupplySalesRepresentativeShouldBeFound("additionalInformation.equals=" + DEFAULT_ADDITIONAL_INFORMATION);

        // Get all the supplySalesRepresentativeList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultSupplySalesRepresentativeShouldNotBeFound("additionalInformation.equals=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByAdditionalInformationIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where additionalInformation in DEFAULT_ADDITIONAL_INFORMATION or UPDATED_ADDITIONAL_INFORMATION
        defaultSupplySalesRepresentativeShouldBeFound("additionalInformation.in=" + DEFAULT_ADDITIONAL_INFORMATION + "," + UPDATED_ADDITIONAL_INFORMATION);

        // Get all the supplySalesRepresentativeList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultSupplySalesRepresentativeShouldNotBeFound("additionalInformation.in=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByAdditionalInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where additionalInformation is not null
        defaultSupplySalesRepresentativeShouldBeFound("additionalInformation.specified=true");

        // Get all the supplySalesRepresentativeList where additionalInformation is null
        defaultSupplySalesRepresentativeShouldNotBeFound("additionalInformation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplySalesRepresentativeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplySalesRepresentativeList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplySalesRepresentativeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplySalesRepresentativeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplySalesRepresentativeList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplySalesRepresentativeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdBy is not null
        defaultSupplySalesRepresentativeShouldBeFound("createdBy.specified=true");

        // Get all the supplySalesRepresentativeList where createdBy is null
        defaultSupplySalesRepresentativeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplySalesRepresentativeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplySalesRepresentativeList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplySalesRepresentativeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplySalesRepresentativeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplySalesRepresentativeList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplySalesRepresentativeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where createdOn is not null
        defaultSupplySalesRepresentativeShouldBeFound("createdOn.specified=true");

        // Get all the supplySalesRepresentativeList where createdOn is null
        defaultSupplySalesRepresentativeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplySalesRepresentativeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplySalesRepresentativeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplySalesRepresentativeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplySalesRepresentativeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedBy is not null
        defaultSupplySalesRepresentativeShouldBeFound("updatedBy.specified=true");

        // Get all the supplySalesRepresentativeList where updatedBy is null
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplySalesRepresentativeShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplySalesRepresentativeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplySalesRepresentativeShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplySalesRepresentativeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        // Get all the supplySalesRepresentativeList where updatedOn is not null
        defaultSupplySalesRepresentativeShouldBeFound("updatedOn.specified=true");

        // Get all the supplySalesRepresentativeList where updatedOn is null
        defaultSupplySalesRepresentativeShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplySalesRepresentative.setSupplyZone(supplyZone);
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplySalesRepresentativeList where supplyZone equals to supplyZoneId
        defaultSupplySalesRepresentativeShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplySalesRepresentativeList where supplyZone equals to supplyZoneId + 1
        defaultSupplySalesRepresentativeShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplySalesRepresentative.setSupplyArea(supplyArea);
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplySalesRepresentativeList where supplyArea equals to supplyAreaId
        defaultSupplySalesRepresentativeShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplySalesRepresentativeList where supplyArea equals to supplyAreaId + 1
        defaultSupplySalesRepresentativeShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplySalesRepresentativesBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplySalesRepresentative.setSupplyAreaManager(supplyAreaManager);
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplySalesRepresentativeList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplySalesRepresentativeShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplySalesRepresentativeList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplySalesRepresentativeShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplySalesRepresentativeShouldBeFound(String filter) throws Exception {
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplySalesRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesRepresentativeName").value(hasItem(DEFAULT_SALES_REPRESENTATIVE_NAME)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplySalesRepresentativeShouldNotBeFound(String filter) throws Exception {
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplySalesRepresentative() throws Exception {
        // Get the supplySalesRepresentative
        restSupplySalesRepresentativeMockMvc.perform(get("/api/supply-sales-representatives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplySalesRepresentative() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        int databaseSizeBeforeUpdate = supplySalesRepresentativeRepository.findAll().size();

        // Update the supplySalesRepresentative
        SupplySalesRepresentative updatedSupplySalesRepresentative = supplySalesRepresentativeRepository.findById(supplySalesRepresentative.getId()).get();
        // Disconnect from session so that the updates on updatedSupplySalesRepresentative are not directly saved in db
        em.detach(updatedSupplySalesRepresentative);
        updatedSupplySalesRepresentative
            .salesRepresentativeName(UPDATED_SALES_REPRESENTATIVE_NAME)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = supplySalesRepresentativeMapper.toDto(updatedSupplySalesRepresentative);

        restSupplySalesRepresentativeMockMvc.perform(put("/api/supply-sales-representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplySalesRepresentativeDTO)))
            .andExpect(status().isOk());

        // Validate the SupplySalesRepresentative in the database
        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeUpdate);
        SupplySalesRepresentative testSupplySalesRepresentative = supplySalesRepresentativeList.get(supplySalesRepresentativeList.size() - 1);
        assertThat(testSupplySalesRepresentative.getSalesRepresentativeName()).isEqualTo(UPDATED_SALES_REPRESENTATIVE_NAME);
        assertThat(testSupplySalesRepresentative.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testSupplySalesRepresentative.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplySalesRepresentative.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplySalesRepresentative.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplySalesRepresentative.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplySalesRepresentative in Elasticsearch
        verify(mockSupplySalesRepresentativeSearchRepository, times(1)).save(testSupplySalesRepresentative);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplySalesRepresentative() throws Exception {
        int databaseSizeBeforeUpdate = supplySalesRepresentativeRepository.findAll().size();

        // Create the SupplySalesRepresentative
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplySalesRepresentativeMockMvc.perform(put("/api/supply-sales-representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplySalesRepresentativeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplySalesRepresentative in the database
        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplySalesRepresentative in Elasticsearch
        verify(mockSupplySalesRepresentativeSearchRepository, times(0)).save(supplySalesRepresentative);
    }

    @Test
    @Transactional
    public void deleteSupplySalesRepresentative() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);

        int databaseSizeBeforeDelete = supplySalesRepresentativeRepository.findAll().size();

        // Delete the supplySalesRepresentative
        restSupplySalesRepresentativeMockMvc.perform(delete("/api/supply-sales-representatives/{id}", supplySalesRepresentative.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplySalesRepresentative> supplySalesRepresentativeList = supplySalesRepresentativeRepository.findAll();
        assertThat(supplySalesRepresentativeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplySalesRepresentative in Elasticsearch
        verify(mockSupplySalesRepresentativeSearchRepository, times(1)).deleteById(supplySalesRepresentative.getId());
    }

    @Test
    @Transactional
    public void searchSupplySalesRepresentative() throws Exception {
        // Initialize the database
        supplySalesRepresentativeRepository.saveAndFlush(supplySalesRepresentative);
        when(mockSupplySalesRepresentativeSearchRepository.search(queryStringQuery("id:" + supplySalesRepresentative.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplySalesRepresentative), PageRequest.of(0, 1), 1));
        // Search the supplySalesRepresentative
        restSupplySalesRepresentativeMockMvc.perform(get("/api/_search/supply-sales-representatives?query=id:" + supplySalesRepresentative.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplySalesRepresentative.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesRepresentativeName").value(hasItem(DEFAULT_SALES_REPRESENTATIVE_NAME)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplySalesRepresentative.class);
        SupplySalesRepresentative supplySalesRepresentative1 = new SupplySalesRepresentative();
        supplySalesRepresentative1.setId(1L);
        SupplySalesRepresentative supplySalesRepresentative2 = new SupplySalesRepresentative();
        supplySalesRepresentative2.setId(supplySalesRepresentative1.getId());
        assertThat(supplySalesRepresentative1).isEqualTo(supplySalesRepresentative2);
        supplySalesRepresentative2.setId(2L);
        assertThat(supplySalesRepresentative1).isNotEqualTo(supplySalesRepresentative2);
        supplySalesRepresentative1.setId(null);
        assertThat(supplySalesRepresentative1).isNotEqualTo(supplySalesRepresentative2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplySalesRepresentativeDTO.class);
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO1 = new SupplySalesRepresentativeDTO();
        supplySalesRepresentativeDTO1.setId(1L);
        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO2 = new SupplySalesRepresentativeDTO();
        assertThat(supplySalesRepresentativeDTO1).isNotEqualTo(supplySalesRepresentativeDTO2);
        supplySalesRepresentativeDTO2.setId(supplySalesRepresentativeDTO1.getId());
        assertThat(supplySalesRepresentativeDTO1).isEqualTo(supplySalesRepresentativeDTO2);
        supplySalesRepresentativeDTO2.setId(2L);
        assertThat(supplySalesRepresentativeDTO1).isNotEqualTo(supplySalesRepresentativeDTO2);
        supplySalesRepresentativeDTO1.setId(null);
        assertThat(supplySalesRepresentativeDTO1).isNotEqualTo(supplySalesRepresentativeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplySalesRepresentativeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplySalesRepresentativeMapper.fromId(null)).isNull();
    }
}
