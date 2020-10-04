package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.PaymentType;
import org.soptorshi.repository.CommercialPaymentInfoRepository;
import org.soptorshi.repository.search.CommercialPaymentInfoSearchRepository;
import org.soptorshi.service.CommercialPaymentInfoQueryService;
import org.soptorshi.service.CommercialPaymentInfoService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.mapper.CommercialPaymentInfoMapper;
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
 * Test class for the CommercialPaymentInfoResource REST controller.
 *
 * @see CommercialPaymentInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPaymentInfoResourceIntTest {

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.LC;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.TT;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT_TO_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT_TO_PAY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT_PAID = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT_PAID = new BigDecimal(2);

    private static final BigDecimal DEFAULT_REMAINING_AMOUNT_TO_PAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMAINING_AMOUNT_TO_PAY = new BigDecimal(2);

    private static final CommercialPaymentStatus DEFAULT_PAYMENT_STATUS = CommercialPaymentStatus.WAITING_FOR_PAYMENT_CONFIRMATION;
    private static final CommercialPaymentStatus UPDATED_PAYMENT_STATUS = CommercialPaymentStatus.PAYMENT_CONFIRMED;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private CommercialPaymentInfoRepository commercialPaymentInfoRepository;

    @Autowired
    private CommercialPaymentInfoMapper commercialPaymentInfoMapper;

    @Autowired
    private CommercialPaymentInfoService commercialPaymentInfoService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPaymentInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPaymentInfoSearchRepository mockCommercialPaymentInfoSearchRepository;

    @Autowired
    private CommercialPaymentInfoQueryService commercialPaymentInfoQueryService;

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

    private MockMvc restCommercialPaymentInfoMockMvc;

    private CommercialPaymentInfo commercialPaymentInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPaymentInfoResource commercialPaymentInfoResource = new CommercialPaymentInfoResource(commercialPaymentInfoService, commercialPaymentInfoQueryService);
        this.restCommercialPaymentInfoMockMvc = MockMvcBuilders.standaloneSetup(commercialPaymentInfoResource)
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
    public static CommercialPaymentInfo createEntity(EntityManager em) {
        CommercialPaymentInfo commercialPaymentInfo = new CommercialPaymentInfo()
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .totalAmountToPay(DEFAULT_TOTAL_AMOUNT_TO_PAY)
            .totalAmountPaid(DEFAULT_TOTAL_AMOUNT_PAID)
            .remainingAmountToPay(DEFAULT_REMAINING_AMOUNT_TO_PAY)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        // Add required entity
        CommercialPi commercialPi = CommercialPiResourceIntTest.createEntity(em);
        em.persist(commercialPi);
        em.flush();
        commercialPaymentInfo.setCommercialPi(commercialPi);
        return commercialPaymentInfo;
    }

    @Before
    public void initTest() {
        commercialPaymentInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPaymentInfo() throws Exception {
        int databaseSizeBeforeCreate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);
        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPaymentInfo testCommercialPaymentInfo = commercialPaymentInfoList.get(commercialPaymentInfoList.size() - 1);
        assertThat(testCommercialPaymentInfo.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testCommercialPaymentInfo.getTotalAmountToPay()).isEqualTo(DEFAULT_TOTAL_AMOUNT_TO_PAY);
        assertThat(testCommercialPaymentInfo.getTotalAmountPaid()).isEqualTo(DEFAULT_TOTAL_AMOUNT_PAID);
        assertThat(testCommercialPaymentInfo.getRemainingAmountToPay()).isEqualTo(DEFAULT_REMAINING_AMOUNT_TO_PAY);
        assertThat(testCommercialPaymentInfo.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testCommercialPaymentInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPaymentInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPaymentInfo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCommercialPaymentInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).save(testCommercialPaymentInfo);
    }

    @Test
    @Transactional
    public void createCommercialPaymentInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo with an existing ID
        commercialPaymentInfo.setId(1L);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(0)).save(commercialPaymentInfo);
    }

    @Test
    @Transactional
    public void checkTotalAmountToPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setTotalAmountToPay(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountPaidIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setTotalAmountPaid(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemainingAmountToPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPaymentInfoRepository.findAll().size();
        // set the field null
        commercialPaymentInfo.setRemainingAmountToPay(null);

        // Create the CommercialPaymentInfo, which fails.
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(post("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfos() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalAmountToPay").value(hasItem(DEFAULT_TOTAL_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].totalAmountPaid").value(hasItem(DEFAULT_TOTAL_AMOUNT_PAID.intValue())))
            .andExpect(jsonPath("$.[*].remainingAmountToPay").value(hasItem(DEFAULT_REMAINING_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/{id}", commercialPaymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPaymentInfo.getId().intValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.totalAmountToPay").value(DEFAULT_TOTAL_AMOUNT_TO_PAY.intValue()))
            .andExpect(jsonPath("$.totalAmountPaid").value(DEFAULT_TOTAL_AMOUNT_PAID.intValue()))
            .andExpect(jsonPath("$.remainingAmountToPay").value(DEFAULT_REMAINING_AMOUNT_TO_PAY.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultCommercialPaymentInfoShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the commercialPaymentInfoList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCommercialPaymentInfoShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultCommercialPaymentInfoShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the commercialPaymentInfoList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCommercialPaymentInfoShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentType is not null
        defaultCommercialPaymentInfoShouldBeFound("paymentType.specified=true");

        // Get all the commercialPaymentInfoList where paymentType is null
        defaultCommercialPaymentInfoShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountToPayIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountToPay equals to DEFAULT_TOTAL_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldBeFound("totalAmountToPay.equals=" + DEFAULT_TOTAL_AMOUNT_TO_PAY);

        // Get all the commercialPaymentInfoList where totalAmountToPay equals to UPDATED_TOTAL_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountToPay.equals=" + UPDATED_TOTAL_AMOUNT_TO_PAY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountToPayIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountToPay in DEFAULT_TOTAL_AMOUNT_TO_PAY or UPDATED_TOTAL_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldBeFound("totalAmountToPay.in=" + DEFAULT_TOTAL_AMOUNT_TO_PAY + "," + UPDATED_TOTAL_AMOUNT_TO_PAY);

        // Get all the commercialPaymentInfoList where totalAmountToPay equals to UPDATED_TOTAL_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountToPay.in=" + UPDATED_TOTAL_AMOUNT_TO_PAY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountToPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountToPay is not null
        defaultCommercialPaymentInfoShouldBeFound("totalAmountToPay.specified=true");

        // Get all the commercialPaymentInfoList where totalAmountToPay is null
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountToPay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountPaid equals to DEFAULT_TOTAL_AMOUNT_PAID
        defaultCommercialPaymentInfoShouldBeFound("totalAmountPaid.equals=" + DEFAULT_TOTAL_AMOUNT_PAID);

        // Get all the commercialPaymentInfoList where totalAmountPaid equals to UPDATED_TOTAL_AMOUNT_PAID
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountPaid.equals=" + UPDATED_TOTAL_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountPaidIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountPaid in DEFAULT_TOTAL_AMOUNT_PAID or UPDATED_TOTAL_AMOUNT_PAID
        defaultCommercialPaymentInfoShouldBeFound("totalAmountPaid.in=" + DEFAULT_TOTAL_AMOUNT_PAID + "," + UPDATED_TOTAL_AMOUNT_PAID);

        // Get all the commercialPaymentInfoList where totalAmountPaid equals to UPDATED_TOTAL_AMOUNT_PAID
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountPaid.in=" + UPDATED_TOTAL_AMOUNT_PAID);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByTotalAmountPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where totalAmountPaid is not null
        defaultCommercialPaymentInfoShouldBeFound("totalAmountPaid.specified=true");

        // Get all the commercialPaymentInfoList where totalAmountPaid is null
        defaultCommercialPaymentInfoShouldNotBeFound("totalAmountPaid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByRemainingAmountToPayIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where remainingAmountToPay equals to DEFAULT_REMAINING_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldBeFound("remainingAmountToPay.equals=" + DEFAULT_REMAINING_AMOUNT_TO_PAY);

        // Get all the commercialPaymentInfoList where remainingAmountToPay equals to UPDATED_REMAINING_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldNotBeFound("remainingAmountToPay.equals=" + UPDATED_REMAINING_AMOUNT_TO_PAY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByRemainingAmountToPayIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where remainingAmountToPay in DEFAULT_REMAINING_AMOUNT_TO_PAY or UPDATED_REMAINING_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldBeFound("remainingAmountToPay.in=" + DEFAULT_REMAINING_AMOUNT_TO_PAY + "," + UPDATED_REMAINING_AMOUNT_TO_PAY);

        // Get all the commercialPaymentInfoList where remainingAmountToPay equals to UPDATED_REMAINING_AMOUNT_TO_PAY
        defaultCommercialPaymentInfoShouldNotBeFound("remainingAmountToPay.in=" + UPDATED_REMAINING_AMOUNT_TO_PAY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByRemainingAmountToPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where remainingAmountToPay is not null
        defaultCommercialPaymentInfoShouldBeFound("remainingAmountToPay.specified=true");

        // Get all the commercialPaymentInfoList where remainingAmountToPay is null
        defaultCommercialPaymentInfoShouldNotBeFound("remainingAmountToPay.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultCommercialPaymentInfoShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the commercialPaymentInfoList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultCommercialPaymentInfoShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultCommercialPaymentInfoShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the commercialPaymentInfoList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultCommercialPaymentInfoShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where paymentStatus is not null
        defaultCommercialPaymentInfoShouldBeFound("paymentStatus.specified=true");

        // Get all the commercialPaymentInfoList where paymentStatus is null
        defaultCommercialPaymentInfoShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPaymentInfoShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPaymentInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPaymentInfoShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPaymentInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdOn is not null
        defaultCommercialPaymentInfoShouldBeFound("createdOn.specified=true");

        // Get all the commercialPaymentInfoList where createdOn is null
        defaultCommercialPaymentInfoShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPaymentInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPaymentInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPaymentInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where createdBy is not null
        defaultCommercialPaymentInfoShouldBeFound("createdBy.specified=true");

        // Get all the commercialPaymentInfoList where createdBy is null
        defaultCommercialPaymentInfoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPaymentInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedOn is not null
        defaultCommercialPaymentInfoShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPaymentInfoList where updatedOn is null
        defaultCommercialPaymentInfoShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPaymentInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPaymentInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        // Get all the commercialPaymentInfoList where updatedBy is not null
        defaultCommercialPaymentInfoShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPaymentInfoList where updatedBy is null
        defaultCommercialPaymentInfoShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPaymentInfosByCommercialPiIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPi commercialPi = CommercialPiResourceIntTest.createEntity(em);
        em.persist(commercialPi);
        em.flush();
        commercialPaymentInfo.setCommercialPi(commercialPi);
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);
        Long commercialPiId = commercialPi.getId();

        // Get all the commercialPaymentInfoList where commercialPi equals to commercialPiId
        defaultCommercialPaymentInfoShouldBeFound("commercialPiId.equals=" + commercialPiId);

        // Get all the commercialPaymentInfoList where commercialPi equals to commercialPiId + 1
        defaultCommercialPaymentInfoShouldNotBeFound("commercialPiId.equals=" + (commercialPiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPaymentInfoShouldBeFound(String filter) throws Exception {
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalAmountToPay").value(hasItem(DEFAULT_TOTAL_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].totalAmountPaid").value(hasItem(DEFAULT_TOTAL_AMOUNT_PAID.intValue())))
            .andExpect(jsonPath("$.[*].remainingAmountToPay").value(hasItem(DEFAULT_REMAINING_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));

        // Check, that the count call also returns 1
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPaymentInfoShouldNotBeFound(String filter) throws Exception {
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPaymentInfo() throws Exception {
        // Get the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/commercial-payment-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        int databaseSizeBeforeUpdate = commercialPaymentInfoRepository.findAll().size();

        // Update the commercialPaymentInfo
        CommercialPaymentInfo updatedCommercialPaymentInfo = commercialPaymentInfoRepository.findById(commercialPaymentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPaymentInfo are not directly saved in db
        em.detach(updatedCommercialPaymentInfo);
        updatedCommercialPaymentInfo
            .paymentType(UPDATED_PAYMENT_TYPE)
            .totalAmountToPay(UPDATED_TOTAL_AMOUNT_TO_PAY)
            .totalAmountPaid(UPDATED_TOTAL_AMOUNT_PAID)
            .remainingAmountToPay(UPDATED_REMAINING_AMOUNT_TO_PAY)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(updatedCommercialPaymentInfo);

        restCommercialPaymentInfoMockMvc.perform(put("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeUpdate);
        CommercialPaymentInfo testCommercialPaymentInfo = commercialPaymentInfoList.get(commercialPaymentInfoList.size() - 1);
        assertThat(testCommercialPaymentInfo.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testCommercialPaymentInfo.getTotalAmountToPay()).isEqualTo(UPDATED_TOTAL_AMOUNT_TO_PAY);
        assertThat(testCommercialPaymentInfo.getTotalAmountPaid()).isEqualTo(UPDATED_TOTAL_AMOUNT_PAID);
        assertThat(testCommercialPaymentInfo.getRemainingAmountToPay()).isEqualTo(UPDATED_REMAINING_AMOUNT_TO_PAY);
        assertThat(testCommercialPaymentInfo.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testCommercialPaymentInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPaymentInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPaymentInfo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCommercialPaymentInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).save(testCommercialPaymentInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPaymentInfo() throws Exception {
        int databaseSizeBeforeUpdate = commercialPaymentInfoRepository.findAll().size();

        // Create the CommercialPaymentInfo
        CommercialPaymentInfoDTO commercialPaymentInfoDTO = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPaymentInfoMockMvc.perform(put("/api/commercial-payment-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPaymentInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPaymentInfo in the database
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(0)).save(commercialPaymentInfo);
    }

    @Test
    @Transactional
    public void deleteCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);

        int databaseSizeBeforeDelete = commercialPaymentInfoRepository.findAll().size();

        // Delete the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(delete("/api/commercial-payment-infos/{id}", commercialPaymentInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPaymentInfo> commercialPaymentInfoList = commercialPaymentInfoRepository.findAll();
        assertThat(commercialPaymentInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPaymentInfo in Elasticsearch
        verify(mockCommercialPaymentInfoSearchRepository, times(1)).deleteById(commercialPaymentInfo.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPaymentInfo() throws Exception {
        // Initialize the database
        commercialPaymentInfoRepository.saveAndFlush(commercialPaymentInfo);
        when(mockCommercialPaymentInfoSearchRepository.search(queryStringQuery("id:" + commercialPaymentInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPaymentInfo), PageRequest.of(0, 1), 1));
        // Search the commercialPaymentInfo
        restCommercialPaymentInfoMockMvc.perform(get("/api/_search/commercial-payment-infos?query=id:" + commercialPaymentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPaymentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].totalAmountToPay").value(hasItem(DEFAULT_TOTAL_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].totalAmountPaid").value(hasItem(DEFAULT_TOTAL_AMOUNT_PAID.intValue())))
            .andExpect(jsonPath("$.[*].remainingAmountToPay").value(hasItem(DEFAULT_REMAINING_AMOUNT_TO_PAY.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPaymentInfo.class);
        CommercialPaymentInfo commercialPaymentInfo1 = new CommercialPaymentInfo();
        commercialPaymentInfo1.setId(1L);
        CommercialPaymentInfo commercialPaymentInfo2 = new CommercialPaymentInfo();
        commercialPaymentInfo2.setId(commercialPaymentInfo1.getId());
        assertThat(commercialPaymentInfo1).isEqualTo(commercialPaymentInfo2);
        commercialPaymentInfo2.setId(2L);
        assertThat(commercialPaymentInfo1).isNotEqualTo(commercialPaymentInfo2);
        commercialPaymentInfo1.setId(null);
        assertThat(commercialPaymentInfo1).isNotEqualTo(commercialPaymentInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPaymentInfoDTO.class);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO1 = new CommercialPaymentInfoDTO();
        commercialPaymentInfoDTO1.setId(1L);
        CommercialPaymentInfoDTO commercialPaymentInfoDTO2 = new CommercialPaymentInfoDTO();
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO2.setId(commercialPaymentInfoDTO1.getId());
        assertThat(commercialPaymentInfoDTO1).isEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO2.setId(2L);
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
        commercialPaymentInfoDTO1.setId(null);
        assertThat(commercialPaymentInfoDTO1).isNotEqualTo(commercialPaymentInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPaymentInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPaymentInfoMapper.fromId(null)).isNull();
    }
}
