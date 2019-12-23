package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialPackagingRepository;
import org.soptorshi.repository.search.CommercialPackagingSearchRepository;
import org.soptorshi.service.CommercialPackagingService;
import org.soptorshi.service.dto.CommercialPackagingDTO;
import org.soptorshi.service.mapper.CommercialPackagingMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialPackagingCriteria;
import org.soptorshi.service.CommercialPackagingQueryService;

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
 * Test class for the CommercialPackagingResource REST controller.
 *
 * @see CommercialPackagingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPackagingResourceIntTest {

    private static final String DEFAULT_CONSIGNMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONSIGNMENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONSIGNMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONSIGNMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialPackagingRepository commercialPackagingRepository;

    @Autowired
    private CommercialPackagingMapper commercialPackagingMapper;

    @Autowired
    private CommercialPackagingService commercialPackagingService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPackagingSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPackagingSearchRepository mockCommercialPackagingSearchRepository;

    @Autowired
    private CommercialPackagingQueryService commercialPackagingQueryService;

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

    private MockMvc restCommercialPackagingMockMvc;

    private CommercialPackaging commercialPackaging;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPackagingResource commercialPackagingResource = new CommercialPackagingResource(commercialPackagingService, commercialPackagingQueryService);
        this.restCommercialPackagingMockMvc = MockMvcBuilders.standaloneSetup(commercialPackagingResource)
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
    public static CommercialPackaging createEntity(EntityManager em) {
        CommercialPackaging commercialPackaging = new CommercialPackaging()
            .consignmentNo(DEFAULT_CONSIGNMENT_NO)
            .consignmentDate(DEFAULT_CONSIGNMENT_DATE)
            .brand(DEFAULT_BRAND)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPackaging;
    }

    @Before
    public void initTest() {
        commercialPackaging = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPackaging() throws Exception {
        int databaseSizeBeforeCreate = commercialPackagingRepository.findAll().size();

        // Create the CommercialPackaging
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(commercialPackaging);
        restCommercialPackagingMockMvc.perform(post("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPackaging in the database
        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPackaging testCommercialPackaging = commercialPackagingList.get(commercialPackagingList.size() - 1);
        assertThat(testCommercialPackaging.getConsignmentNo()).isEqualTo(DEFAULT_CONSIGNMENT_NO);
        assertThat(testCommercialPackaging.getConsignmentDate()).isEqualTo(DEFAULT_CONSIGNMENT_DATE);
        assertThat(testCommercialPackaging.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCommercialPackaging.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPackaging.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPackaging.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPackaging.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPackaging in Elasticsearch
        verify(mockCommercialPackagingSearchRepository, times(1)).save(testCommercialPackaging);
    }

    @Test
    @Transactional
    public void createCommercialPackagingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPackagingRepository.findAll().size();

        // Create the CommercialPackaging with an existing ID
        commercialPackaging.setId(1L);
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(commercialPackaging);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPackagingMockMvc.perform(post("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPackaging in the database
        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPackaging in Elasticsearch
        verify(mockCommercialPackagingSearchRepository, times(0)).save(commercialPackaging);
    }

    @Test
    @Transactional
    public void checkConsignmentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPackagingRepository.findAll().size();
        // set the field null
        commercialPackaging.setConsignmentNo(null);

        // Create the CommercialPackaging, which fails.
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(commercialPackaging);

        restCommercialPackagingMockMvc.perform(post("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsignmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPackagingRepository.findAll().size();
        // set the field null
        commercialPackaging.setConsignmentDate(null);

        // Create the CommercialPackaging, which fails.
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(commercialPackaging);

        restCommercialPackagingMockMvc.perform(post("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagings() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackaging.getId().intValue())))
            .andExpect(jsonPath("$.[*].consignmentNo").value(hasItem(DEFAULT_CONSIGNMENT_NO.toString())))
            .andExpect(jsonPath("$.[*].consignmentDate").value(hasItem(DEFAULT_CONSIGNMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialPackaging() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get the commercialPackaging
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings/{id}", commercialPackaging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPackaging.getId().intValue()))
            .andExpect(jsonPath("$.consignmentNo").value(DEFAULT_CONSIGNMENT_NO.toString()))
            .andExpect(jsonPath("$.consignmentDate").value(DEFAULT_CONSIGNMENT_DATE.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentNo equals to DEFAULT_CONSIGNMENT_NO
        defaultCommercialPackagingShouldBeFound("consignmentNo.equals=" + DEFAULT_CONSIGNMENT_NO);

        // Get all the commercialPackagingList where consignmentNo equals to UPDATED_CONSIGNMENT_NO
        defaultCommercialPackagingShouldNotBeFound("consignmentNo.equals=" + UPDATED_CONSIGNMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentNo in DEFAULT_CONSIGNMENT_NO or UPDATED_CONSIGNMENT_NO
        defaultCommercialPackagingShouldBeFound("consignmentNo.in=" + DEFAULT_CONSIGNMENT_NO + "," + UPDATED_CONSIGNMENT_NO);

        // Get all the commercialPackagingList where consignmentNo equals to UPDATED_CONSIGNMENT_NO
        defaultCommercialPackagingShouldNotBeFound("consignmentNo.in=" + UPDATED_CONSIGNMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentNo is not null
        defaultCommercialPackagingShouldBeFound("consignmentNo.specified=true");

        // Get all the commercialPackagingList where consignmentNo is null
        defaultCommercialPackagingShouldNotBeFound("consignmentNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentDate equals to DEFAULT_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldBeFound("consignmentDate.equals=" + DEFAULT_CONSIGNMENT_DATE);

        // Get all the commercialPackagingList where consignmentDate equals to UPDATED_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldNotBeFound("consignmentDate.equals=" + UPDATED_CONSIGNMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentDate in DEFAULT_CONSIGNMENT_DATE or UPDATED_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldBeFound("consignmentDate.in=" + DEFAULT_CONSIGNMENT_DATE + "," + UPDATED_CONSIGNMENT_DATE);

        // Get all the commercialPackagingList where consignmentDate equals to UPDATED_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldNotBeFound("consignmentDate.in=" + UPDATED_CONSIGNMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentDate is not null
        defaultCommercialPackagingShouldBeFound("consignmentDate.specified=true");

        // Get all the commercialPackagingList where consignmentDate is null
        defaultCommercialPackagingShouldNotBeFound("consignmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentDate greater than or equals to DEFAULT_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldBeFound("consignmentDate.greaterOrEqualThan=" + DEFAULT_CONSIGNMENT_DATE);

        // Get all the commercialPackagingList where consignmentDate greater than or equals to UPDATED_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldNotBeFound("consignmentDate.greaterOrEqualThan=" + UPDATED_CONSIGNMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByConsignmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where consignmentDate less than or equals to DEFAULT_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldNotBeFound("consignmentDate.lessThan=" + DEFAULT_CONSIGNMENT_DATE);

        // Get all the commercialPackagingList where consignmentDate less than or equals to UPDATED_CONSIGNMENT_DATE
        defaultCommercialPackagingShouldBeFound("consignmentDate.lessThan=" + UPDATED_CONSIGNMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where brand equals to DEFAULT_BRAND
        defaultCommercialPackagingShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the commercialPackagingList where brand equals to UPDATED_BRAND
        defaultCommercialPackagingShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultCommercialPackagingShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the commercialPackagingList where brand equals to UPDATED_BRAND
        defaultCommercialPackagingShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where brand is not null
        defaultCommercialPackagingShouldBeFound("brand.specified=true");

        // Get all the commercialPackagingList where brand is null
        defaultCommercialPackagingShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPackagingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPackagingList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPackagingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPackagingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPackagingList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPackagingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdBy is not null
        defaultCommercialPackagingShouldBeFound("createdBy.specified=true");

        // Get all the commercialPackagingList where createdBy is null
        defaultCommercialPackagingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPackagingShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPackagingList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPackagingShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPackagingShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPackagingList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPackagingShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdOn is not null
        defaultCommercialPackagingShouldBeFound("createdOn.specified=true");

        // Get all the commercialPackagingList where createdOn is null
        defaultCommercialPackagingShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialPackagingShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPackagingList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialPackagingShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialPackagingShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPackagingList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialPackagingShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPackagingShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPackagingList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPackagingShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPackagingShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPackagingList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPackagingShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedBy is not null
        defaultCommercialPackagingShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPackagingList where updatedBy is null
        defaultCommercialPackagingShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPackagingShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPackagingList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPackagingShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPackagingList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedOn is not null
        defaultCommercialPackagingShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPackagingList where updatedOn is null
        defaultCommercialPackagingShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPackagingShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPackagingList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingsByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        // Get all the commercialPackagingList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPackagingShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPackagingList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingsByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialPackaging.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialPackagingRepository.saveAndFlush(commercialPackaging);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialPackagingList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialPackagingShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialPackagingList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialPackagingShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPackagingShouldBeFound(String filter) throws Exception {
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackaging.getId().intValue())))
            .andExpect(jsonPath("$.[*].consignmentNo").value(hasItem(DEFAULT_CONSIGNMENT_NO)))
            .andExpect(jsonPath("$.[*].consignmentDate").value(hasItem(DEFAULT_CONSIGNMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPackagingShouldNotBeFound(String filter) throws Exception {
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPackaging() throws Exception {
        // Get the commercialPackaging
        restCommercialPackagingMockMvc.perform(get("/api/commercial-packagings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPackaging() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        int databaseSizeBeforeUpdate = commercialPackagingRepository.findAll().size();

        // Update the commercialPackaging
        CommercialPackaging updatedCommercialPackaging = commercialPackagingRepository.findById(commercialPackaging.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPackaging are not directly saved in db
        em.detach(updatedCommercialPackaging);
        updatedCommercialPackaging
            .consignmentNo(UPDATED_CONSIGNMENT_NO)
            .consignmentDate(UPDATED_CONSIGNMENT_DATE)
            .brand(UPDATED_BRAND)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(updatedCommercialPackaging);

        restCommercialPackagingMockMvc.perform(put("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPackaging in the database
        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeUpdate);
        CommercialPackaging testCommercialPackaging = commercialPackagingList.get(commercialPackagingList.size() - 1);
        assertThat(testCommercialPackaging.getConsignmentNo()).isEqualTo(UPDATED_CONSIGNMENT_NO);
        assertThat(testCommercialPackaging.getConsignmentDate()).isEqualTo(UPDATED_CONSIGNMENT_DATE);
        assertThat(testCommercialPackaging.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCommercialPackaging.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPackaging.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPackaging.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPackaging.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPackaging in Elasticsearch
        verify(mockCommercialPackagingSearchRepository, times(1)).save(testCommercialPackaging);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPackaging() throws Exception {
        int databaseSizeBeforeUpdate = commercialPackagingRepository.findAll().size();

        // Create the CommercialPackaging
        CommercialPackagingDTO commercialPackagingDTO = commercialPackagingMapper.toDto(commercialPackaging);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPackagingMockMvc.perform(put("/api/commercial-packagings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPackaging in the database
        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPackaging in Elasticsearch
        verify(mockCommercialPackagingSearchRepository, times(0)).save(commercialPackaging);
    }

    @Test
    @Transactional
    public void deleteCommercialPackaging() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);

        int databaseSizeBeforeDelete = commercialPackagingRepository.findAll().size();

        // Delete the commercialPackaging
        restCommercialPackagingMockMvc.perform(delete("/api/commercial-packagings/{id}", commercialPackaging.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPackaging> commercialPackagingList = commercialPackagingRepository.findAll();
        assertThat(commercialPackagingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPackaging in Elasticsearch
        verify(mockCommercialPackagingSearchRepository, times(1)).deleteById(commercialPackaging.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPackaging() throws Exception {
        // Initialize the database
        commercialPackagingRepository.saveAndFlush(commercialPackaging);
        when(mockCommercialPackagingSearchRepository.search(queryStringQuery("id:" + commercialPackaging.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPackaging), PageRequest.of(0, 1), 1));
        // Search the commercialPackaging
        restCommercialPackagingMockMvc.perform(get("/api/_search/commercial-packagings?query=id:" + commercialPackaging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackaging.getId().intValue())))
            .andExpect(jsonPath("$.[*].consignmentNo").value(hasItem(DEFAULT_CONSIGNMENT_NO)))
            .andExpect(jsonPath("$.[*].consignmentDate").value(hasItem(DEFAULT_CONSIGNMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPackaging.class);
        CommercialPackaging commercialPackaging1 = new CommercialPackaging();
        commercialPackaging1.setId(1L);
        CommercialPackaging commercialPackaging2 = new CommercialPackaging();
        commercialPackaging2.setId(commercialPackaging1.getId());
        assertThat(commercialPackaging1).isEqualTo(commercialPackaging2);
        commercialPackaging2.setId(2L);
        assertThat(commercialPackaging1).isNotEqualTo(commercialPackaging2);
        commercialPackaging1.setId(null);
        assertThat(commercialPackaging1).isNotEqualTo(commercialPackaging2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPackagingDTO.class);
        CommercialPackagingDTO commercialPackagingDTO1 = new CommercialPackagingDTO();
        commercialPackagingDTO1.setId(1L);
        CommercialPackagingDTO commercialPackagingDTO2 = new CommercialPackagingDTO();
        assertThat(commercialPackagingDTO1).isNotEqualTo(commercialPackagingDTO2);
        commercialPackagingDTO2.setId(commercialPackagingDTO1.getId());
        assertThat(commercialPackagingDTO1).isEqualTo(commercialPackagingDTO2);
        commercialPackagingDTO2.setId(2L);
        assertThat(commercialPackagingDTO1).isNotEqualTo(commercialPackagingDTO2);
        commercialPackagingDTO1.setId(null);
        assertThat(commercialPackagingDTO1).isNotEqualTo(commercialPackagingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPackagingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPackagingMapper.fromId(null)).isNull();
    }
}
