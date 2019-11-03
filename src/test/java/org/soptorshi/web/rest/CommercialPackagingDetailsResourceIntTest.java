package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.domain.CommercialPackagingDetails;
import org.soptorshi.repository.CommercialPackagingDetailsRepository;
import org.soptorshi.repository.search.CommercialPackagingDetailsSearchRepository;
import org.soptorshi.service.CommercialPackagingDetailsQueryService;
import org.soptorshi.service.CommercialPackagingDetailsService;
import org.soptorshi.service.dto.CommercialPackagingDetailsDTO;
import org.soptorshi.service.mapper.CommercialPackagingDetailsMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the CommercialPackagingDetailsResource REST controller.
 *
 * @see CommercialPackagingDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPackagingDetailsResourceIntTest {

    private static final LocalDate DEFAULT_PRO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SHIFT_1 = "AAAAAAAAAA";
    private static final String UPDATED_SHIFT_1 = "BBBBBBBBBB";

    private static final Double DEFAULT_SHIFT_1_TOTAL = 1D;
    private static final Double UPDATED_SHIFT_1_TOTAL = 2D;

    private static final String DEFAULT_SHIFT_2 = "AAAAAAAAAA";
    private static final String UPDATED_SHIFT_2 = "BBBBBBBBBB";

    private static final Double DEFAULT_SHIFT_2_TOTAL = 1D;
    private static final Double UPDATED_SHIFT_2_TOTAL = 2D;

    private static final Double DEFAULT_DAY_TOTAL = 1D;
    private static final Double UPDATED_DAY_TOTAL = 2D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_ON = "BBBBBBBBBB";

    @Autowired
    private CommercialPackagingDetailsRepository commercialPackagingDetailsRepository;

    @Autowired
    private CommercialPackagingDetailsMapper commercialPackagingDetailsMapper;

    @Autowired
    private CommercialPackagingDetailsService commercialPackagingDetailsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPackagingDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPackagingDetailsSearchRepository mockCommercialPackagingDetailsSearchRepository;

    @Autowired
    private CommercialPackagingDetailsQueryService commercialPackagingDetailsQueryService;

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

    private MockMvc restCommercialPackagingDetailsMockMvc;

    private CommercialPackagingDetails commercialPackagingDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPackagingDetailsResource commercialPackagingDetailsResource = new CommercialPackagingDetailsResource(commercialPackagingDetailsService, commercialPackagingDetailsQueryService);
        this.restCommercialPackagingDetailsMockMvc = MockMvcBuilders.standaloneSetup(commercialPackagingDetailsResource)
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
    public static CommercialPackagingDetails createEntity(EntityManager em) {
        CommercialPackagingDetails commercialPackagingDetails = new CommercialPackagingDetails()
            .proDate(DEFAULT_PRO_DATE)
            .expDate(DEFAULT_EXP_DATE)
            .shift1(DEFAULT_SHIFT_1)
            .shift1Total(DEFAULT_SHIFT_1_TOTAL)
            .shift2(DEFAULT_SHIFT_2)
            .shift2Total(DEFAULT_SHIFT_2_TOTAL)
            .dayTotal(DEFAULT_DAY_TOTAL)
            .total(DEFAULT_TOTAL)
            .createdBy(DEFAULT_CREATED_BY)
            .createOn(DEFAULT_CREATE_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPackagingDetails;
    }

    @Before
    public void initTest() {
        commercialPackagingDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPackagingDetails() throws Exception {
        int databaseSizeBeforeCreate = commercialPackagingDetailsRepository.findAll().size();

        // Create the CommercialPackagingDetails
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO = commercialPackagingDetailsMapper.toDto(commercialPackagingDetails);
        restCommercialPackagingDetailsMockMvc.perform(post("/api/commercial-packaging-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPackagingDetails in the database
        List<CommercialPackagingDetails> commercialPackagingDetailsList = commercialPackagingDetailsRepository.findAll();
        assertThat(commercialPackagingDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPackagingDetails testCommercialPackagingDetails = commercialPackagingDetailsList.get(commercialPackagingDetailsList.size() - 1);
        assertThat(testCommercialPackagingDetails.getProDate()).isEqualTo(DEFAULT_PRO_DATE);
        assertThat(testCommercialPackagingDetails.getExpDate()).isEqualTo(DEFAULT_EXP_DATE);
        assertThat(testCommercialPackagingDetails.getShift1()).isEqualTo(DEFAULT_SHIFT_1);
        assertThat(testCommercialPackagingDetails.getShift1Total()).isEqualTo(DEFAULT_SHIFT_1_TOTAL);
        assertThat(testCommercialPackagingDetails.getShift2()).isEqualTo(DEFAULT_SHIFT_2);
        assertThat(testCommercialPackagingDetails.getShift2Total()).isEqualTo(DEFAULT_SHIFT_2_TOTAL);
        assertThat(testCommercialPackagingDetails.getDayTotal()).isEqualTo(DEFAULT_DAY_TOTAL);
        assertThat(testCommercialPackagingDetails.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testCommercialPackagingDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPackagingDetails.getCreateOn()).isEqualTo(DEFAULT_CREATE_ON);
        assertThat(testCommercialPackagingDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPackagingDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPackagingDetails in Elasticsearch
        verify(mockCommercialPackagingDetailsSearchRepository, times(1)).save(testCommercialPackagingDetails);
    }

    @Test
    @Transactional
    public void createCommercialPackagingDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPackagingDetailsRepository.findAll().size();

        // Create the CommercialPackagingDetails with an existing ID
        commercialPackagingDetails.setId(1L);
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO = commercialPackagingDetailsMapper.toDto(commercialPackagingDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPackagingDetailsMockMvc.perform(post("/api/commercial-packaging-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPackagingDetails in the database
        List<CommercialPackagingDetails> commercialPackagingDetailsList = commercialPackagingDetailsRepository.findAll();
        assertThat(commercialPackagingDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPackagingDetails in Elasticsearch
        verify(mockCommercialPackagingDetailsSearchRepository, times(0)).save(commercialPackagingDetails);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetails() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackagingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].proDate").value(hasItem(DEFAULT_PRO_DATE.toString())))
            .andExpect(jsonPath("$.[*].expDate").value(hasItem(DEFAULT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].shift1").value(hasItem(DEFAULT_SHIFT_1.toString())))
            .andExpect(jsonPath("$.[*].shift1Total").value(hasItem(DEFAULT_SHIFT_1_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].shift2").value(hasItem(DEFAULT_SHIFT_2.toString())))
            .andExpect(jsonPath("$.[*].shift2Total").value(hasItem(DEFAULT_SHIFT_2_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dayTotal").value(hasItem(DEFAULT_DAY_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPackagingDetails() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get the commercialPackagingDetails
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details/{id}", commercialPackagingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPackagingDetails.getId().intValue()))
            .andExpect(jsonPath("$.proDate").value(DEFAULT_PRO_DATE.toString()))
            .andExpect(jsonPath("$.expDate").value(DEFAULT_EXP_DATE.toString()))
            .andExpect(jsonPath("$.shift1").value(DEFAULT_SHIFT_1.toString()))
            .andExpect(jsonPath("$.shift1Total").value(DEFAULT_SHIFT_1_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.shift2").value(DEFAULT_SHIFT_2.toString()))
            .andExpect(jsonPath("$.shift2Total").value(DEFAULT_SHIFT_2_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.dayTotal").value(DEFAULT_DAY_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createOn").value(DEFAULT_CREATE_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByProDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where proDate equals to DEFAULT_PRO_DATE
        defaultCommercialPackagingDetailsShouldBeFound("proDate.equals=" + DEFAULT_PRO_DATE);

        // Get all the commercialPackagingDetailsList where proDate equals to UPDATED_PRO_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("proDate.equals=" + UPDATED_PRO_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByProDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where proDate in DEFAULT_PRO_DATE or UPDATED_PRO_DATE
        defaultCommercialPackagingDetailsShouldBeFound("proDate.in=" + DEFAULT_PRO_DATE + "," + UPDATED_PRO_DATE);

        // Get all the commercialPackagingDetailsList where proDate equals to UPDATED_PRO_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("proDate.in=" + UPDATED_PRO_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByProDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where proDate is not null
        defaultCommercialPackagingDetailsShouldBeFound("proDate.specified=true");

        // Get all the commercialPackagingDetailsList where proDate is null
        defaultCommercialPackagingDetailsShouldNotBeFound("proDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByProDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where proDate greater than or equals to DEFAULT_PRO_DATE
        defaultCommercialPackagingDetailsShouldBeFound("proDate.greaterOrEqualThan=" + DEFAULT_PRO_DATE);

        // Get all the commercialPackagingDetailsList where proDate greater than or equals to UPDATED_PRO_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("proDate.greaterOrEqualThan=" + UPDATED_PRO_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByProDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where proDate less than or equals to DEFAULT_PRO_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("proDate.lessThan=" + DEFAULT_PRO_DATE);

        // Get all the commercialPackagingDetailsList where proDate less than or equals to UPDATED_PRO_DATE
        defaultCommercialPackagingDetailsShouldBeFound("proDate.lessThan=" + UPDATED_PRO_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByExpDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where expDate equals to DEFAULT_EXP_DATE
        defaultCommercialPackagingDetailsShouldBeFound("expDate.equals=" + DEFAULT_EXP_DATE);

        // Get all the commercialPackagingDetailsList where expDate equals to UPDATED_EXP_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("expDate.equals=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByExpDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where expDate in DEFAULT_EXP_DATE or UPDATED_EXP_DATE
        defaultCommercialPackagingDetailsShouldBeFound("expDate.in=" + DEFAULT_EXP_DATE + "," + UPDATED_EXP_DATE);

        // Get all the commercialPackagingDetailsList where expDate equals to UPDATED_EXP_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("expDate.in=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByExpDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where expDate is not null
        defaultCommercialPackagingDetailsShouldBeFound("expDate.specified=true");

        // Get all the commercialPackagingDetailsList where expDate is null
        defaultCommercialPackagingDetailsShouldNotBeFound("expDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByExpDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where expDate greater than or equals to DEFAULT_EXP_DATE
        defaultCommercialPackagingDetailsShouldBeFound("expDate.greaterOrEqualThan=" + DEFAULT_EXP_DATE);

        // Get all the commercialPackagingDetailsList where expDate greater than or equals to UPDATED_EXP_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("expDate.greaterOrEqualThan=" + UPDATED_EXP_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByExpDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where expDate less than or equals to DEFAULT_EXP_DATE
        defaultCommercialPackagingDetailsShouldNotBeFound("expDate.lessThan=" + DEFAULT_EXP_DATE);

        // Get all the commercialPackagingDetailsList where expDate less than or equals to UPDATED_EXP_DATE
        defaultCommercialPackagingDetailsShouldBeFound("expDate.lessThan=" + UPDATED_EXP_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1IsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1 equals to DEFAULT_SHIFT_1
        defaultCommercialPackagingDetailsShouldBeFound("shift1.equals=" + DEFAULT_SHIFT_1);

        // Get all the commercialPackagingDetailsList where shift1 equals to UPDATED_SHIFT_1
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1.equals=" + UPDATED_SHIFT_1);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1IsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1 in DEFAULT_SHIFT_1 or UPDATED_SHIFT_1
        defaultCommercialPackagingDetailsShouldBeFound("shift1.in=" + DEFAULT_SHIFT_1 + "," + UPDATED_SHIFT_1);

        // Get all the commercialPackagingDetailsList where shift1 equals to UPDATED_SHIFT_1
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1.in=" + UPDATED_SHIFT_1);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1IsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1 is not null
        defaultCommercialPackagingDetailsShouldBeFound("shift1.specified=true");

        // Get all the commercialPackagingDetailsList where shift1 is null
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1TotalIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1Total equals to DEFAULT_SHIFT_1_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("shift1Total.equals=" + DEFAULT_SHIFT_1_TOTAL);

        // Get all the commercialPackagingDetailsList where shift1Total equals to UPDATED_SHIFT_1_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1Total.equals=" + UPDATED_SHIFT_1_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1TotalIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1Total in DEFAULT_SHIFT_1_TOTAL or UPDATED_SHIFT_1_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("shift1Total.in=" + DEFAULT_SHIFT_1_TOTAL + "," + UPDATED_SHIFT_1_TOTAL);

        // Get all the commercialPackagingDetailsList where shift1Total equals to UPDATED_SHIFT_1_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1Total.in=" + UPDATED_SHIFT_1_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift1TotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift1Total is not null
        defaultCommercialPackagingDetailsShouldBeFound("shift1Total.specified=true");

        // Get all the commercialPackagingDetailsList where shift1Total is null
        defaultCommercialPackagingDetailsShouldNotBeFound("shift1Total.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2IsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2 equals to DEFAULT_SHIFT_2
        defaultCommercialPackagingDetailsShouldBeFound("shift2.equals=" + DEFAULT_SHIFT_2);

        // Get all the commercialPackagingDetailsList where shift2 equals to UPDATED_SHIFT_2
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2.equals=" + UPDATED_SHIFT_2);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2IsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2 in DEFAULT_SHIFT_2 or UPDATED_SHIFT_2
        defaultCommercialPackagingDetailsShouldBeFound("shift2.in=" + DEFAULT_SHIFT_2 + "," + UPDATED_SHIFT_2);

        // Get all the commercialPackagingDetailsList where shift2 equals to UPDATED_SHIFT_2
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2.in=" + UPDATED_SHIFT_2);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2IsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2 is not null
        defaultCommercialPackagingDetailsShouldBeFound("shift2.specified=true");

        // Get all the commercialPackagingDetailsList where shift2 is null
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2TotalIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2Total equals to DEFAULT_SHIFT_2_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("shift2Total.equals=" + DEFAULT_SHIFT_2_TOTAL);

        // Get all the commercialPackagingDetailsList where shift2Total equals to UPDATED_SHIFT_2_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2Total.equals=" + UPDATED_SHIFT_2_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2TotalIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2Total in DEFAULT_SHIFT_2_TOTAL or UPDATED_SHIFT_2_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("shift2Total.in=" + DEFAULT_SHIFT_2_TOTAL + "," + UPDATED_SHIFT_2_TOTAL);

        // Get all the commercialPackagingDetailsList where shift2Total equals to UPDATED_SHIFT_2_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2Total.in=" + UPDATED_SHIFT_2_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByShift2TotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where shift2Total is not null
        defaultCommercialPackagingDetailsShouldBeFound("shift2Total.specified=true");

        // Get all the commercialPackagingDetailsList where shift2Total is null
        defaultCommercialPackagingDetailsShouldNotBeFound("shift2Total.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByDayTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where dayTotal equals to DEFAULT_DAY_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("dayTotal.equals=" + DEFAULT_DAY_TOTAL);

        // Get all the commercialPackagingDetailsList where dayTotal equals to UPDATED_DAY_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("dayTotal.equals=" + UPDATED_DAY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByDayTotalIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where dayTotal in DEFAULT_DAY_TOTAL or UPDATED_DAY_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("dayTotal.in=" + DEFAULT_DAY_TOTAL + "," + UPDATED_DAY_TOTAL);

        // Get all the commercialPackagingDetailsList where dayTotal equals to UPDATED_DAY_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("dayTotal.in=" + UPDATED_DAY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByDayTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where dayTotal is not null
        defaultCommercialPackagingDetailsShouldBeFound("dayTotal.specified=true");

        // Get all the commercialPackagingDetailsList where dayTotal is null
        defaultCommercialPackagingDetailsShouldNotBeFound("dayTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where total equals to DEFAULT_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the commercialPackagingDetailsList where total equals to UPDATED_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultCommercialPackagingDetailsShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the commercialPackagingDetailsList where total equals to UPDATED_TOTAL
        defaultCommercialPackagingDetailsShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where total is not null
        defaultCommercialPackagingDetailsShouldBeFound("total.specified=true");

        // Get all the commercialPackagingDetailsList where total is null
        defaultCommercialPackagingDetailsShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPackagingDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPackagingDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPackagingDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPackagingDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPackagingDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPackagingDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createdBy is not null
        defaultCommercialPackagingDetailsShouldBeFound("createdBy.specified=true");

        // Get all the commercialPackagingDetailsList where createdBy is null
        defaultCommercialPackagingDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreateOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createOn equals to DEFAULT_CREATE_ON
        defaultCommercialPackagingDetailsShouldBeFound("createOn.equals=" + DEFAULT_CREATE_ON);

        // Get all the commercialPackagingDetailsList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("createOn.equals=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreateOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createOn in DEFAULT_CREATE_ON or UPDATED_CREATE_ON
        defaultCommercialPackagingDetailsShouldBeFound("createOn.in=" + DEFAULT_CREATE_ON + "," + UPDATED_CREATE_ON);

        // Get all the commercialPackagingDetailsList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("createOn.in=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreateOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createOn is not null
        defaultCommercialPackagingDetailsShouldBeFound("createOn.specified=true");

        // Get all the commercialPackagingDetailsList where createOn is null
        defaultCommercialPackagingDetailsShouldNotBeFound("createOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreateOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createOn greater than or equals to DEFAULT_CREATE_ON
        defaultCommercialPackagingDetailsShouldBeFound("createOn.greaterOrEqualThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPackagingDetailsList where createOn greater than or equals to UPDATED_CREATE_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("createOn.greaterOrEqualThan=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCreateOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where createOn less than or equals to DEFAULT_CREATE_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("createOn.lessThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPackagingDetailsList where createOn less than or equals to UPDATED_CREATE_ON
        defaultCommercialPackagingDetailsShouldBeFound("createOn.lessThan=" + UPDATED_CREATE_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPackagingDetailsShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPackagingDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPackagingDetailsShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPackagingDetailsList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedBy is not null
        defaultCommercialPackagingDetailsShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPackagingDetailsList where updatedBy is null
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPackagingDetailsShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPackagingDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPackagingDetailsShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPackagingDetailsList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        // Get all the commercialPackagingDetailsList where updatedOn is not null
        defaultCommercialPackagingDetailsShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPackagingDetailsList where updatedOn is null
        defaultCommercialPackagingDetailsShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPackagingDetailsByCommercialPackagingIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPackaging commercialPackaging = CommercialPackagingResourceIntTest.createEntity(em);
        em.persist(commercialPackaging);
        em.flush();
        commercialPackagingDetails.setCommercialPackaging(commercialPackaging);
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);
        Long commercialPackagingId = commercialPackaging.getId();

        // Get all the commercialPackagingDetailsList where commercialPackaging equals to commercialPackagingId
        defaultCommercialPackagingDetailsShouldBeFound("commercialPackagingId.equals=" + commercialPackagingId);

        // Get all the commercialPackagingDetailsList where commercialPackaging equals to commercialPackagingId + 1
        defaultCommercialPackagingDetailsShouldNotBeFound("commercialPackagingId.equals=" + (commercialPackagingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPackagingDetailsShouldBeFound(String filter) throws Exception {
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackagingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].proDate").value(hasItem(DEFAULT_PRO_DATE.toString())))
            .andExpect(jsonPath("$.[*].expDate").value(hasItem(DEFAULT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].shift1").value(hasItem(DEFAULT_SHIFT_1)))
            .andExpect(jsonPath("$.[*].shift1Total").value(hasItem(DEFAULT_SHIFT_1_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].shift2").value(hasItem(DEFAULT_SHIFT_2)))
            .andExpect(jsonPath("$.[*].shift2Total").value(hasItem(DEFAULT_SHIFT_2_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dayTotal").value(hasItem(DEFAULT_DAY_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON)));

        // Check, that the count call also returns 1
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPackagingDetailsShouldNotBeFound(String filter) throws Exception {
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPackagingDetails() throws Exception {
        // Get the commercialPackagingDetails
        restCommercialPackagingDetailsMockMvc.perform(get("/api/commercial-packaging-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPackagingDetails() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        int databaseSizeBeforeUpdate = commercialPackagingDetailsRepository.findAll().size();

        // Update the commercialPackagingDetails
        CommercialPackagingDetails updatedCommercialPackagingDetails = commercialPackagingDetailsRepository.findById(commercialPackagingDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPackagingDetails are not directly saved in db
        em.detach(updatedCommercialPackagingDetails);
        updatedCommercialPackagingDetails
            .proDate(UPDATED_PRO_DATE)
            .expDate(UPDATED_EXP_DATE)
            .shift1(UPDATED_SHIFT_1)
            .shift1Total(UPDATED_SHIFT_1_TOTAL)
            .shift2(UPDATED_SHIFT_2)
            .shift2Total(UPDATED_SHIFT_2_TOTAL)
            .dayTotal(UPDATED_DAY_TOTAL)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createOn(UPDATED_CREATE_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO = commercialPackagingDetailsMapper.toDto(updatedCommercialPackagingDetails);

        restCommercialPackagingDetailsMockMvc.perform(put("/api/commercial-packaging-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPackagingDetails in the database
        List<CommercialPackagingDetails> commercialPackagingDetailsList = commercialPackagingDetailsRepository.findAll();
        assertThat(commercialPackagingDetailsList).hasSize(databaseSizeBeforeUpdate);
        CommercialPackagingDetails testCommercialPackagingDetails = commercialPackagingDetailsList.get(commercialPackagingDetailsList.size() - 1);
        assertThat(testCommercialPackagingDetails.getProDate()).isEqualTo(UPDATED_PRO_DATE);
        assertThat(testCommercialPackagingDetails.getExpDate()).isEqualTo(UPDATED_EXP_DATE);
        assertThat(testCommercialPackagingDetails.getShift1()).isEqualTo(UPDATED_SHIFT_1);
        assertThat(testCommercialPackagingDetails.getShift1Total()).isEqualTo(UPDATED_SHIFT_1_TOTAL);
        assertThat(testCommercialPackagingDetails.getShift2()).isEqualTo(UPDATED_SHIFT_2);
        assertThat(testCommercialPackagingDetails.getShift2Total()).isEqualTo(UPDATED_SHIFT_2_TOTAL);
        assertThat(testCommercialPackagingDetails.getDayTotal()).isEqualTo(UPDATED_DAY_TOTAL);
        assertThat(testCommercialPackagingDetails.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testCommercialPackagingDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPackagingDetails.getCreateOn()).isEqualTo(UPDATED_CREATE_ON);
        assertThat(testCommercialPackagingDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPackagingDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPackagingDetails in Elasticsearch
        verify(mockCommercialPackagingDetailsSearchRepository, times(1)).save(testCommercialPackagingDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPackagingDetails() throws Exception {
        int databaseSizeBeforeUpdate = commercialPackagingDetailsRepository.findAll().size();

        // Create the CommercialPackagingDetails
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO = commercialPackagingDetailsMapper.toDto(commercialPackagingDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPackagingDetailsMockMvc.perform(put("/api/commercial-packaging-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPackagingDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPackagingDetails in the database
        List<CommercialPackagingDetails> commercialPackagingDetailsList = commercialPackagingDetailsRepository.findAll();
        assertThat(commercialPackagingDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPackagingDetails in Elasticsearch
        verify(mockCommercialPackagingDetailsSearchRepository, times(0)).save(commercialPackagingDetails);
    }

    @Test
    @Transactional
    public void deleteCommercialPackagingDetails() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);

        int databaseSizeBeforeDelete = commercialPackagingDetailsRepository.findAll().size();

        // Delete the commercialPackagingDetails
        restCommercialPackagingDetailsMockMvc.perform(delete("/api/commercial-packaging-details/{id}", commercialPackagingDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPackagingDetails> commercialPackagingDetailsList = commercialPackagingDetailsRepository.findAll();
        assertThat(commercialPackagingDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPackagingDetails in Elasticsearch
        verify(mockCommercialPackagingDetailsSearchRepository, times(1)).deleteById(commercialPackagingDetails.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPackagingDetails() throws Exception {
        // Initialize the database
        commercialPackagingDetailsRepository.saveAndFlush(commercialPackagingDetails);
        when(mockCommercialPackagingDetailsSearchRepository.search(queryStringQuery("id:" + commercialPackagingDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPackagingDetails), PageRequest.of(0, 1), 1));
        // Search the commercialPackagingDetails
        restCommercialPackagingDetailsMockMvc.perform(get("/api/_search/commercial-packaging-details?query=id:" + commercialPackagingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPackagingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].proDate").value(hasItem(DEFAULT_PRO_DATE.toString())))
            .andExpect(jsonPath("$.[*].expDate").value(hasItem(DEFAULT_EXP_DATE.toString())))
            .andExpect(jsonPath("$.[*].shift1").value(hasItem(DEFAULT_SHIFT_1)))
            .andExpect(jsonPath("$.[*].shift1Total").value(hasItem(DEFAULT_SHIFT_1_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].shift2").value(hasItem(DEFAULT_SHIFT_2)))
            .andExpect(jsonPath("$.[*].shift2Total").value(hasItem(DEFAULT_SHIFT_2_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dayTotal").value(hasItem(DEFAULT_DAY_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPackagingDetails.class);
        CommercialPackagingDetails commercialPackagingDetails1 = new CommercialPackagingDetails();
        commercialPackagingDetails1.setId(1L);
        CommercialPackagingDetails commercialPackagingDetails2 = new CommercialPackagingDetails();
        commercialPackagingDetails2.setId(commercialPackagingDetails1.getId());
        assertThat(commercialPackagingDetails1).isEqualTo(commercialPackagingDetails2);
        commercialPackagingDetails2.setId(2L);
        assertThat(commercialPackagingDetails1).isNotEqualTo(commercialPackagingDetails2);
        commercialPackagingDetails1.setId(null);
        assertThat(commercialPackagingDetails1).isNotEqualTo(commercialPackagingDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPackagingDetailsDTO.class);
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO1 = new CommercialPackagingDetailsDTO();
        commercialPackagingDetailsDTO1.setId(1L);
        CommercialPackagingDetailsDTO commercialPackagingDetailsDTO2 = new CommercialPackagingDetailsDTO();
        assertThat(commercialPackagingDetailsDTO1).isNotEqualTo(commercialPackagingDetailsDTO2);
        commercialPackagingDetailsDTO2.setId(commercialPackagingDetailsDTO1.getId());
        assertThat(commercialPackagingDetailsDTO1).isEqualTo(commercialPackagingDetailsDTO2);
        commercialPackagingDetailsDTO2.setId(2L);
        assertThat(commercialPackagingDetailsDTO1).isNotEqualTo(commercialPackagingDetailsDTO2);
        commercialPackagingDetailsDTO1.setId(null);
        assertThat(commercialPackagingDetailsDTO1).isNotEqualTo(commercialPackagingDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPackagingDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPackagingDetailsMapper.fromId(null)).isNull();
    }
}
