package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ReceiptVoucher;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.ReceiptVoucherRepository;
import org.soptorshi.repository.search.ReceiptVoucherSearchRepository;
import org.soptorshi.service.ReceiptVoucherService;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.mapper.ReceiptVoucherMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ReceiptVoucherCriteria;
import org.soptorshi.service.ReceiptVoucherQueryService;

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
 * Test class for the ReceiptVoucherResource REST controller.
 *
 * @see ReceiptVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ReceiptVoucherResourceIntTest {

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
    private ReceiptVoucherRepository receiptVoucherRepository;

    @Autowired
    private ReceiptVoucherMapper receiptVoucherMapper;

    @Autowired
    private ReceiptVoucherService receiptVoucherService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ReceiptVoucherSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReceiptVoucherSearchRepository mockReceiptVoucherSearchRepository;

    @Autowired
    private ReceiptVoucherQueryService receiptVoucherQueryService;

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

    private MockMvc restReceiptVoucherMockMvc;

    private ReceiptVoucher receiptVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiptVoucherResource receiptVoucherResource = new ReceiptVoucherResource(receiptVoucherService, receiptVoucherQueryService);
        this.restReceiptVoucherMockMvc = MockMvcBuilders.standaloneSetup(receiptVoucherResource)
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
    public static ReceiptVoucher createEntity(EntityManager em) {
        ReceiptVoucher receiptVoucher = new ReceiptVoucher()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .postDate(DEFAULT_POST_DATE)
            .applicationType(DEFAULT_APPLICATION_TYPE)
            .applicationId(DEFAULT_APPLICATION_ID)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return receiptVoucher;
    }

    @Before
    public void initTest() {
        receiptVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceiptVoucher() throws Exception {
        int databaseSizeBeforeCreate = receiptVoucherRepository.findAll().size();

        // Create the ReceiptVoucher
        ReceiptVoucherDTO receiptVoucherDTO = receiptVoucherMapper.toDto(receiptVoucher);
        restReceiptVoucherMockMvc.perform(post("/api/receipt-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the ReceiptVoucher in the database
        List<ReceiptVoucher> receiptVoucherList = receiptVoucherRepository.findAll();
        assertThat(receiptVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiptVoucher testReceiptVoucher = receiptVoucherList.get(receiptVoucherList.size() - 1);
        assertThat(testReceiptVoucher.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testReceiptVoucher.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testReceiptVoucher.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testReceiptVoucher.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testReceiptVoucher.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testReceiptVoucher.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testReceiptVoucher.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ReceiptVoucher in Elasticsearch
        verify(mockReceiptVoucherSearchRepository, times(1)).save(testReceiptVoucher);
    }

    @Test
    @Transactional
    public void createReceiptVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptVoucherRepository.findAll().size();

        // Create the ReceiptVoucher with an existing ID
        receiptVoucher.setId(1L);
        ReceiptVoucherDTO receiptVoucherDTO = receiptVoucherMapper.toDto(receiptVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptVoucherMockMvc.perform(post("/api/receipt-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptVoucher in the database
        List<ReceiptVoucher> receiptVoucherList = receiptVoucherRepository.findAll();
        assertThat(receiptVoucherList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReceiptVoucher in Elasticsearch
        verify(mockReceiptVoucherSearchRepository, times(0)).save(receiptVoucher);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchers() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptVoucher.getId().intValue())))
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
    public void getReceiptVoucher() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get the receiptVoucher
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers/{id}", receiptVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receiptVoucher.getId().intValue()))
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
    public void getAllReceiptVouchersByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultReceiptVoucherShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the receiptVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultReceiptVoucherShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultReceiptVoucherShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the receiptVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultReceiptVoucherShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherNo is not null
        defaultReceiptVoucherShouldBeFound("voucherNo.specified=true");

        // Get all the receiptVoucherList where voucherNo is null
        defaultReceiptVoucherShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultReceiptVoucherShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the receiptVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultReceiptVoucherShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultReceiptVoucherShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the receiptVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultReceiptVoucherShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherDate is not null
        defaultReceiptVoucherShouldBeFound("voucherDate.specified=true");

        // Get all the receiptVoucherList where voucherDate is null
        defaultReceiptVoucherShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherDate greater than or equals to DEFAULT_VOUCHER_DATE
        defaultReceiptVoucherShouldBeFound("voucherDate.greaterOrEqualThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the receiptVoucherList where voucherDate greater than or equals to UPDATED_VOUCHER_DATE
        defaultReceiptVoucherShouldNotBeFound("voucherDate.greaterOrEqualThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where voucherDate less than or equals to DEFAULT_VOUCHER_DATE
        defaultReceiptVoucherShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the receiptVoucherList where voucherDate less than or equals to UPDATED_VOUCHER_DATE
        defaultReceiptVoucherShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllReceiptVouchersByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where postDate equals to DEFAULT_POST_DATE
        defaultReceiptVoucherShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the receiptVoucherList where postDate equals to UPDATED_POST_DATE
        defaultReceiptVoucherShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultReceiptVoucherShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the receiptVoucherList where postDate equals to UPDATED_POST_DATE
        defaultReceiptVoucherShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where postDate is not null
        defaultReceiptVoucherShouldBeFound("postDate.specified=true");

        // Get all the receiptVoucherList where postDate is null
        defaultReceiptVoucherShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByPostDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where postDate greater than or equals to DEFAULT_POST_DATE
        defaultReceiptVoucherShouldBeFound("postDate.greaterOrEqualThan=" + DEFAULT_POST_DATE);

        // Get all the receiptVoucherList where postDate greater than or equals to UPDATED_POST_DATE
        defaultReceiptVoucherShouldNotBeFound("postDate.greaterOrEqualThan=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByPostDateIsLessThanSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where postDate less than or equals to DEFAULT_POST_DATE
        defaultReceiptVoucherShouldNotBeFound("postDate.lessThan=" + DEFAULT_POST_DATE);

        // Get all the receiptVoucherList where postDate less than or equals to UPDATED_POST_DATE
        defaultReceiptVoucherShouldBeFound("postDate.lessThan=" + UPDATED_POST_DATE);
    }


    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationType equals to DEFAULT_APPLICATION_TYPE
        defaultReceiptVoucherShouldBeFound("applicationType.equals=" + DEFAULT_APPLICATION_TYPE);

        // Get all the receiptVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultReceiptVoucherShouldNotBeFound("applicationType.equals=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationType in DEFAULT_APPLICATION_TYPE or UPDATED_APPLICATION_TYPE
        defaultReceiptVoucherShouldBeFound("applicationType.in=" + DEFAULT_APPLICATION_TYPE + "," + UPDATED_APPLICATION_TYPE);

        // Get all the receiptVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultReceiptVoucherShouldNotBeFound("applicationType.in=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationType is not null
        defaultReceiptVoucherShouldBeFound("applicationType.specified=true");

        // Get all the receiptVoucherList where applicationType is null
        defaultReceiptVoucherShouldNotBeFound("applicationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationId equals to DEFAULT_APPLICATION_ID
        defaultReceiptVoucherShouldBeFound("applicationId.equals=" + DEFAULT_APPLICATION_ID);

        // Get all the receiptVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultReceiptVoucherShouldNotBeFound("applicationId.equals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationIdIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationId in DEFAULT_APPLICATION_ID or UPDATED_APPLICATION_ID
        defaultReceiptVoucherShouldBeFound("applicationId.in=" + DEFAULT_APPLICATION_ID + "," + UPDATED_APPLICATION_ID);

        // Get all the receiptVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultReceiptVoucherShouldNotBeFound("applicationId.in=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationId is not null
        defaultReceiptVoucherShouldBeFound("applicationId.specified=true");

        // Get all the receiptVoucherList where applicationId is null
        defaultReceiptVoucherShouldNotBeFound("applicationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationId greater than or equals to DEFAULT_APPLICATION_ID
        defaultReceiptVoucherShouldBeFound("applicationId.greaterOrEqualThan=" + DEFAULT_APPLICATION_ID);

        // Get all the receiptVoucherList where applicationId greater than or equals to UPDATED_APPLICATION_ID
        defaultReceiptVoucherShouldNotBeFound("applicationId.greaterOrEqualThan=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByApplicationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where applicationId less than or equals to DEFAULT_APPLICATION_ID
        defaultReceiptVoucherShouldNotBeFound("applicationId.lessThan=" + DEFAULT_APPLICATION_ID);

        // Get all the receiptVoucherList where applicationId less than or equals to UPDATED_APPLICATION_ID
        defaultReceiptVoucherShouldBeFound("applicationId.lessThan=" + UPDATED_APPLICATION_ID);
    }


    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultReceiptVoucherShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the receiptVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultReceiptVoucherShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultReceiptVoucherShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the receiptVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultReceiptVoucherShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedBy is not null
        defaultReceiptVoucherShouldBeFound("modifiedBy.specified=true");

        // Get all the receiptVoucherList where modifiedBy is null
        defaultReceiptVoucherShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultReceiptVoucherShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the receiptVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultReceiptVoucherShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultReceiptVoucherShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the receiptVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultReceiptVoucherShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedOn is not null
        defaultReceiptVoucherShouldBeFound("modifiedOn.specified=true");

        // Get all the receiptVoucherList where modifiedOn is null
        defaultReceiptVoucherShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultReceiptVoucherShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the receiptVoucherList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultReceiptVoucherShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllReceiptVouchersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        // Get all the receiptVoucherList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultReceiptVoucherShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the receiptVoucherList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultReceiptVoucherShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllReceiptVouchersByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MstAccount account = MstAccountResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        receiptVoucher.setAccount(account);
        receiptVoucherRepository.saveAndFlush(receiptVoucher);
        Long accountId = account.getId();

        // Get all the receiptVoucherList where account equals to accountId
        defaultReceiptVoucherShouldBeFound("accountId.equals=" + accountId);

        // Get all the receiptVoucherList where account equals to accountId + 1
        defaultReceiptVoucherShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReceiptVoucherShouldBeFound(String filter) throws Exception {
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReceiptVoucherShouldNotBeFound(String filter) throws Exception {
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReceiptVoucher() throws Exception {
        // Get the receiptVoucher
        restReceiptVoucherMockMvc.perform(get("/api/receipt-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceiptVoucher() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        int databaseSizeBeforeUpdate = receiptVoucherRepository.findAll().size();

        // Update the receiptVoucher
        ReceiptVoucher updatedReceiptVoucher = receiptVoucherRepository.findById(receiptVoucher.getId()).get();
        // Disconnect from session so that the updates on updatedReceiptVoucher are not directly saved in db
        em.detach(updatedReceiptVoucher);
        updatedReceiptVoucher
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .postDate(UPDATED_POST_DATE)
            .applicationType(UPDATED_APPLICATION_TYPE)
            .applicationId(UPDATED_APPLICATION_ID)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ReceiptVoucherDTO receiptVoucherDTO = receiptVoucherMapper.toDto(updatedReceiptVoucher);

        restReceiptVoucherMockMvc.perform(put("/api/receipt-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the ReceiptVoucher in the database
        List<ReceiptVoucher> receiptVoucherList = receiptVoucherRepository.findAll();
        assertThat(receiptVoucherList).hasSize(databaseSizeBeforeUpdate);
        ReceiptVoucher testReceiptVoucher = receiptVoucherList.get(receiptVoucherList.size() - 1);
        assertThat(testReceiptVoucher.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testReceiptVoucher.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testReceiptVoucher.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testReceiptVoucher.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testReceiptVoucher.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testReceiptVoucher.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testReceiptVoucher.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ReceiptVoucher in Elasticsearch
        verify(mockReceiptVoucherSearchRepository, times(1)).save(testReceiptVoucher);
    }

    @Test
    @Transactional
    public void updateNonExistingReceiptVoucher() throws Exception {
        int databaseSizeBeforeUpdate = receiptVoucherRepository.findAll().size();

        // Create the ReceiptVoucher
        ReceiptVoucherDTO receiptVoucherDTO = receiptVoucherMapper.toDto(receiptVoucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptVoucherMockMvc.perform(put("/api/receipt-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptVoucher in the database
        List<ReceiptVoucher> receiptVoucherList = receiptVoucherRepository.findAll();
        assertThat(receiptVoucherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReceiptVoucher in Elasticsearch
        verify(mockReceiptVoucherSearchRepository, times(0)).save(receiptVoucher);
    }

    @Test
    @Transactional
    public void deleteReceiptVoucher() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);

        int databaseSizeBeforeDelete = receiptVoucherRepository.findAll().size();

        // Delete the receiptVoucher
        restReceiptVoucherMockMvc.perform(delete("/api/receipt-vouchers/{id}", receiptVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReceiptVoucher> receiptVoucherList = receiptVoucherRepository.findAll();
        assertThat(receiptVoucherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReceiptVoucher in Elasticsearch
        verify(mockReceiptVoucherSearchRepository, times(1)).deleteById(receiptVoucher.getId());
    }

    @Test
    @Transactional
    public void searchReceiptVoucher() throws Exception {
        // Initialize the database
        receiptVoucherRepository.saveAndFlush(receiptVoucher);
        when(mockReceiptVoucherSearchRepository.search(queryStringQuery("id:" + receiptVoucher.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(receiptVoucher), PageRequest.of(0, 1), 1));
        // Search the receiptVoucher
        restReceiptVoucherMockMvc.perform(get("/api/_search/receipt-vouchers?query=id:" + receiptVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptVoucher.getId().intValue())))
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
        TestUtil.equalsVerifier(ReceiptVoucher.class);
        ReceiptVoucher receiptVoucher1 = new ReceiptVoucher();
        receiptVoucher1.setId(1L);
        ReceiptVoucher receiptVoucher2 = new ReceiptVoucher();
        receiptVoucher2.setId(receiptVoucher1.getId());
        assertThat(receiptVoucher1).isEqualTo(receiptVoucher2);
        receiptVoucher2.setId(2L);
        assertThat(receiptVoucher1).isNotEqualTo(receiptVoucher2);
        receiptVoucher1.setId(null);
        assertThat(receiptVoucher1).isNotEqualTo(receiptVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptVoucherDTO.class);
        ReceiptVoucherDTO receiptVoucherDTO1 = new ReceiptVoucherDTO();
        receiptVoucherDTO1.setId(1L);
        ReceiptVoucherDTO receiptVoucherDTO2 = new ReceiptVoucherDTO();
        assertThat(receiptVoucherDTO1).isNotEqualTo(receiptVoucherDTO2);
        receiptVoucherDTO2.setId(receiptVoucherDTO1.getId());
        assertThat(receiptVoucherDTO1).isEqualTo(receiptVoucherDTO2);
        receiptVoucherDTO2.setId(2L);
        assertThat(receiptVoucherDTO1).isNotEqualTo(receiptVoucherDTO2);
        receiptVoucherDTO1.setId(null);
        assertThat(receiptVoucherDTO1).isNotEqualTo(receiptVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(receiptVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(receiptVoucherMapper.fromId(null)).isNull();
    }
}
