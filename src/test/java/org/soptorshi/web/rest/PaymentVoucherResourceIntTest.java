package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PaymentVoucher;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.mapper.PaymentVoucherMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PaymentVoucherCriteria;
import org.soptorshi.service.PaymentVoucherQueryService;

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

import org.soptorshi.domain.enumeration.ApplicationType;
/**
 * Test class for the PaymentVoucherResource REST controller.
 *
 * @see PaymentVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PaymentVoucherResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VOUCHER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VOUCHER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_POST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ApplicationType DEFAULT_APPLICATION_TYPE = ApplicationType.REQUISITION;
    private static final ApplicationType UPDATED_APPLICATION_TYPE = ApplicationType.PURCHASE_ORDER;

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PaymentVoucherRepository paymentVoucherRepository;

    @Autowired
    private PaymentVoucherMapper paymentVoucherMapper;

    @Autowired
    private PaymentVoucherService paymentVoucherService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PaymentVoucherSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentVoucherSearchRepository mockPaymentVoucherSearchRepository;

    @Autowired
    private PaymentVoucherQueryService paymentVoucherQueryService;

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

    private MockMvc restPaymentVoucherMockMvc;

    private PaymentVoucher paymentVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentVoucherResource paymentVoucherResource = new PaymentVoucherResource(paymentVoucherService, paymentVoucherQueryService);
        this.restPaymentVoucherMockMvc = MockMvcBuilders.standaloneSetup(paymentVoucherResource)
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
    public static PaymentVoucher createEntity(EntityManager em) {
        PaymentVoucher paymentVoucher = new PaymentVoucher()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .postDate(DEFAULT_POST_DATE)
            .applicationType(DEFAULT_APPLICATION_TYPE)
            .applicationId(DEFAULT_APPLICATION_ID)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return paymentVoucher;
    }

    @Before
    public void initTest() {
        paymentVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentVoucher() throws Exception {
        int databaseSizeBeforeCreate = paymentVoucherRepository.findAll().size();

        // Create the PaymentVoucher
        PaymentVoucherDTO paymentVoucherDTO = paymentVoucherMapper.toDto(paymentVoucher);
        restPaymentVoucherMockMvc.perform(post("/api/payment-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentVoucher in the database
        List<PaymentVoucher> paymentVoucherList = paymentVoucherRepository.findAll();
        assertThat(paymentVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentVoucher testPaymentVoucher = paymentVoucherList.get(paymentVoucherList.size() - 1);
        assertThat(testPaymentVoucher.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testPaymentVoucher.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testPaymentVoucher.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testPaymentVoucher.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testPaymentVoucher.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testPaymentVoucher.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPaymentVoucher.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the PaymentVoucher in Elasticsearch
        verify(mockPaymentVoucherSearchRepository, times(1)).save(testPaymentVoucher);
    }

    @Test
    @Transactional
    public void createPaymentVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentVoucherRepository.findAll().size();

        // Create the PaymentVoucher with an existing ID
        paymentVoucher.setId(1L);
        PaymentVoucherDTO paymentVoucherDTO = paymentVoucherMapper.toDto(paymentVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentVoucherMockMvc.perform(post("/api/payment-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentVoucher in the database
        List<PaymentVoucher> paymentVoucherList = paymentVoucherRepository.findAll();
        assertThat(paymentVoucherList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentVoucher in Elasticsearch
        verify(mockPaymentVoucherSearchRepository, times(0)).save(paymentVoucher);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchers() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentVoucher() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get the paymentVoucher
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers/{id}", paymentVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentVoucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultPaymentVoucherShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the paymentVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultPaymentVoucherShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultPaymentVoucherShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the paymentVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultPaymentVoucherShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherNo is not null
        defaultPaymentVoucherShouldBeFound("voucherNo.specified=true");

        // Get all the paymentVoucherList where voucherNo is null
        defaultPaymentVoucherShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultPaymentVoucherShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the paymentVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultPaymentVoucherShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultPaymentVoucherShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the paymentVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultPaymentVoucherShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherDate is not null
        defaultPaymentVoucherShouldBeFound("voucherDate.specified=true");

        // Get all the paymentVoucherList where voucherDate is null
        defaultPaymentVoucherShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherDate greater than or equals to DEFAULT_VOUCHER_DATE
        defaultPaymentVoucherShouldBeFound("voucherDate.greaterOrEqualThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the paymentVoucherList where voucherDate greater than or equals to UPDATED_VOUCHER_DATE
        defaultPaymentVoucherShouldNotBeFound("voucherDate.greaterOrEqualThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where voucherDate less than or equals to DEFAULT_VOUCHER_DATE
        defaultPaymentVoucherShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the paymentVoucherList where voucherDate less than or equals to UPDATED_VOUCHER_DATE
        defaultPaymentVoucherShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllPaymentVouchersByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where postDate equals to DEFAULT_POST_DATE
        defaultPaymentVoucherShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the paymentVoucherList where postDate equals to UPDATED_POST_DATE
        defaultPaymentVoucherShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultPaymentVoucherShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the paymentVoucherList where postDate equals to UPDATED_POST_DATE
        defaultPaymentVoucherShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where postDate is not null
        defaultPaymentVoucherShouldBeFound("postDate.specified=true");

        // Get all the paymentVoucherList where postDate is null
        defaultPaymentVoucherShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByPostDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where postDate greater than or equals to DEFAULT_POST_DATE
        defaultPaymentVoucherShouldBeFound("postDate.greaterOrEqualThan=" + DEFAULT_POST_DATE);

        // Get all the paymentVoucherList where postDate greater than or equals to UPDATED_POST_DATE
        defaultPaymentVoucherShouldNotBeFound("postDate.greaterOrEqualThan=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByPostDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where postDate less than or equals to DEFAULT_POST_DATE
        defaultPaymentVoucherShouldNotBeFound("postDate.lessThan=" + DEFAULT_POST_DATE);

        // Get all the paymentVoucherList where postDate less than or equals to UPDATED_POST_DATE
        defaultPaymentVoucherShouldBeFound("postDate.lessThan=" + UPDATED_POST_DATE);
    }


    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationType equals to DEFAULT_APPLICATION_TYPE
        defaultPaymentVoucherShouldBeFound("applicationType.equals=" + DEFAULT_APPLICATION_TYPE);

        // Get all the paymentVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultPaymentVoucherShouldNotBeFound("applicationType.equals=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationType in DEFAULT_APPLICATION_TYPE or UPDATED_APPLICATION_TYPE
        defaultPaymentVoucherShouldBeFound("applicationType.in=" + DEFAULT_APPLICATION_TYPE + "," + UPDATED_APPLICATION_TYPE);

        // Get all the paymentVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultPaymentVoucherShouldNotBeFound("applicationType.in=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationType is not null
        defaultPaymentVoucherShouldBeFound("applicationType.specified=true");

        // Get all the paymentVoucherList where applicationType is null
        defaultPaymentVoucherShouldNotBeFound("applicationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationId equals to DEFAULT_APPLICATION_ID
        defaultPaymentVoucherShouldBeFound("applicationId.equals=" + DEFAULT_APPLICATION_ID);

        // Get all the paymentVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultPaymentVoucherShouldNotBeFound("applicationId.equals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationId in DEFAULT_APPLICATION_ID or UPDATED_APPLICATION_ID
        defaultPaymentVoucherShouldBeFound("applicationId.in=" + DEFAULT_APPLICATION_ID + "," + UPDATED_APPLICATION_ID);

        // Get all the paymentVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultPaymentVoucherShouldNotBeFound("applicationId.in=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationId is not null
        defaultPaymentVoucherShouldBeFound("applicationId.specified=true");

        // Get all the paymentVoucherList where applicationId is null
        defaultPaymentVoucherShouldNotBeFound("applicationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationId greater than or equals to DEFAULT_APPLICATION_ID
        defaultPaymentVoucherShouldBeFound("applicationId.greaterOrEqualThan=" + DEFAULT_APPLICATION_ID);

        // Get all the paymentVoucherList where applicationId greater than or equals to UPDATED_APPLICATION_ID
        defaultPaymentVoucherShouldNotBeFound("applicationId.greaterOrEqualThan=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByApplicationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where applicationId less than or equals to DEFAULT_APPLICATION_ID
        defaultPaymentVoucherShouldNotBeFound("applicationId.lessThan=" + DEFAULT_APPLICATION_ID);

        // Get all the paymentVoucherList where applicationId less than or equals to UPDATED_APPLICATION_ID
        defaultPaymentVoucherShouldBeFound("applicationId.lessThan=" + UPDATED_APPLICATION_ID);
    }


    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultPaymentVoucherShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the paymentVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPaymentVoucherShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultPaymentVoucherShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the paymentVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPaymentVoucherShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedBy is not null
        defaultPaymentVoucherShouldBeFound("modifiedBy.specified=true");

        // Get all the paymentVoucherList where modifiedBy is null
        defaultPaymentVoucherShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultPaymentVoucherShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the paymentVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPaymentVoucherShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultPaymentVoucherShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the paymentVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPaymentVoucherShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedOn is not null
        defaultPaymentVoucherShouldBeFound("modifiedOn.specified=true");

        // Get all the paymentVoucherList where modifiedOn is null
        defaultPaymentVoucherShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultPaymentVoucherShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the paymentVoucherList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultPaymentVoucherShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPaymentVouchersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        // Get all the paymentVoucherList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultPaymentVoucherShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the paymentVoucherList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultPaymentVoucherShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllPaymentVouchersByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MstAccount account = MstAccountResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        paymentVoucher.setAccount(account);
        paymentVoucherRepository.saveAndFlush(paymentVoucher);
        Long accountId = account.getId();

        // Get all the paymentVoucherList where account equals to accountId
        defaultPaymentVoucherShouldBeFound("accountId.equals=" + accountId);

        // Get all the paymentVoucherList where account equals to accountId + 1
        defaultPaymentVoucherShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPaymentVoucherShouldBeFound(String filter) throws Exception {
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPaymentVoucherShouldNotBeFound(String filter) throws Exception {
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaymentVoucher() throws Exception {
        // Get the paymentVoucher
        restPaymentVoucherMockMvc.perform(get("/api/payment-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentVoucher() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        int databaseSizeBeforeUpdate = paymentVoucherRepository.findAll().size();

        // Update the paymentVoucher
        PaymentVoucher updatedPaymentVoucher = paymentVoucherRepository.findById(paymentVoucher.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentVoucher are not directly saved in db
        em.detach(updatedPaymentVoucher);
        updatedPaymentVoucher
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .postDate(UPDATED_POST_DATE)
            .applicationType(UPDATED_APPLICATION_TYPE)
            .applicationId(UPDATED_APPLICATION_ID)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        PaymentVoucherDTO paymentVoucherDTO = paymentVoucherMapper.toDto(updatedPaymentVoucher);

        restPaymentVoucherMockMvc.perform(put("/api/payment-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentVoucher in the database
        List<PaymentVoucher> paymentVoucherList = paymentVoucherRepository.findAll();
        assertThat(paymentVoucherList).hasSize(databaseSizeBeforeUpdate);
        PaymentVoucher testPaymentVoucher = paymentVoucherList.get(paymentVoucherList.size() - 1);
        assertThat(testPaymentVoucher.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testPaymentVoucher.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testPaymentVoucher.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testPaymentVoucher.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testPaymentVoucher.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testPaymentVoucher.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPaymentVoucher.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the PaymentVoucher in Elasticsearch
        verify(mockPaymentVoucherSearchRepository, times(1)).save(testPaymentVoucher);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentVoucher() throws Exception {
        int databaseSizeBeforeUpdate = paymentVoucherRepository.findAll().size();

        // Create the PaymentVoucher
        PaymentVoucherDTO paymentVoucherDTO = paymentVoucherMapper.toDto(paymentVoucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentVoucherMockMvc.perform(put("/api/payment-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentVoucher in the database
        List<PaymentVoucher> paymentVoucherList = paymentVoucherRepository.findAll();
        assertThat(paymentVoucherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentVoucher in Elasticsearch
        verify(mockPaymentVoucherSearchRepository, times(0)).save(paymentVoucher);
    }

    @Test
    @Transactional
    public void deletePaymentVoucher() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);

        int databaseSizeBeforeDelete = paymentVoucherRepository.findAll().size();

        // Delete the paymentVoucher
        restPaymentVoucherMockMvc.perform(delete("/api/payment-vouchers/{id}", paymentVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentVoucher> paymentVoucherList = paymentVoucherRepository.findAll();
        assertThat(paymentVoucherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentVoucher in Elasticsearch
        verify(mockPaymentVoucherSearchRepository, times(1)).deleteById(paymentVoucher.getId());
    }

    @Test
    @Transactional
    public void searchPaymentVoucher() throws Exception {
        // Initialize the database
        paymentVoucherRepository.saveAndFlush(paymentVoucher);
        when(mockPaymentVoucherSearchRepository.search(queryStringQuery("id:" + paymentVoucher.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(paymentVoucher), PageRequest.of(0, 1), 1));
        // Search the paymentVoucher
        restPaymentVoucherMockMvc.perform(get("/api/_search/payment-vouchers?query=id:" + paymentVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentVoucher.class);
        PaymentVoucher paymentVoucher1 = new PaymentVoucher();
        paymentVoucher1.setId(1L);
        PaymentVoucher paymentVoucher2 = new PaymentVoucher();
        paymentVoucher2.setId(paymentVoucher1.getId());
        assertThat(paymentVoucher1).isEqualTo(paymentVoucher2);
        paymentVoucher2.setId(2L);
        assertThat(paymentVoucher1).isNotEqualTo(paymentVoucher2);
        paymentVoucher1.setId(null);
        assertThat(paymentVoucher1).isNotEqualTo(paymentVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentVoucherDTO.class);
        PaymentVoucherDTO paymentVoucherDTO1 = new PaymentVoucherDTO();
        paymentVoucherDTO1.setId(1L);
        PaymentVoucherDTO paymentVoucherDTO2 = new PaymentVoucherDTO();
        assertThat(paymentVoucherDTO1).isNotEqualTo(paymentVoucherDTO2);
        paymentVoucherDTO2.setId(paymentVoucherDTO1.getId());
        assertThat(paymentVoucherDTO1).isEqualTo(paymentVoucherDTO2);
        paymentVoucherDTO2.setId(2L);
        assertThat(paymentVoucherDTO1).isNotEqualTo(paymentVoucherDTO2);
        paymentVoucherDTO1.setId(null);
        assertThat(paymentVoucherDTO1).isNotEqualTo(paymentVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentVoucherMapper.fromId(null)).isNull();
    }
}
