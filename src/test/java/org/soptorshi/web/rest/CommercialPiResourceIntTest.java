package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.PaymentType;
import org.soptorshi.repository.CommercialPiRepository;
import org.soptorshi.repository.search.CommercialPiSearchRepository;
import org.soptorshi.service.CommercialPiQueryService;
import org.soptorshi.service.CommercialPiService;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.mapper.CommercialPiMapper;
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
 * Test class for the CommercialPiResource REST controller.
 *
 * @see CommercialPiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPiResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROFORMA_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROFORMA_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROFORMA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROFORMA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HARMONIC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HARMONIC_CODE = "BBBBBBBBBB";

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.LC;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.TT;

    private static final String DEFAULT_PAYMENT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_OF_DELIVERY = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_OF_DELIVERY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_LOADING = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_LOADING = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_NO = "BBBBBBBBBB";

    private static final CommercialPiStatus DEFAULT_PI_STATUS = CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER;
    private static final CommercialPiStatus UPDATED_PI_STATUS = CommercialPiStatus.PI_APPROVED_BY_THE_CUSTOMER;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommercialPiRepository commercialPiRepository;

    @Autowired
    private CommercialPiMapper commercialPiMapper;

    @Autowired
    private CommercialPiService commercialPiService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPiSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPiSearchRepository mockCommercialPiSearchRepository;

    @Autowired
    private CommercialPiQueryService commercialPiQueryService;

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

    private MockMvc restCommercialPiMockMvc;

    private CommercialPi commercialPi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPiResource commercialPiResource = new CommercialPiResource(commercialPiService, commercialPiQueryService);
        this.restCommercialPiMockMvc = MockMvcBuilders.standaloneSetup(commercialPiResource)
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
    public static CommercialPi createEntity(EntityManager em) {
        CommercialPi commercialPi = new CommercialPi()
            .companyName(DEFAULT_COMPANY_NAME)
            .companyDescription(DEFAULT_COMPANY_DESCRIPTION)
            .proformaNo(DEFAULT_PROFORMA_NO)
            .proformaDate(DEFAULT_PROFORMA_DATE)
            .harmonicCode(DEFAULT_HARMONIC_CODE)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .paymentTerm(DEFAULT_PAYMENT_TERM)
            .termsOfDelivery(DEFAULT_TERMS_OF_DELIVERY)
            .shipmentDate(DEFAULT_SHIPMENT_DATE)
            .portOfLoading(DEFAULT_PORT_OF_LOADING)
            .portOfDestination(DEFAULT_PORT_OF_DESTINATION)
            .purchaseOrderNo(DEFAULT_PURCHASE_ORDER_NO)
            .piStatus(DEFAULT_PI_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPi;
    }

    @Before
    public void initTest() {
        commercialPi = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPi() throws Exception {
        int databaseSizeBeforeCreate = commercialPiRepository.findAll().size();

        // Create the CommercialPi
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(commercialPi);
        restCommercialPiMockMvc.perform(post("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPi in the database
        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPi testCommercialPi = commercialPiList.get(commercialPiList.size() - 1);
        assertThat(testCommercialPi.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCommercialPi.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testCommercialPi.getProformaNo()).isEqualTo(DEFAULT_PROFORMA_NO);
        assertThat(testCommercialPi.getProformaDate()).isEqualTo(DEFAULT_PROFORMA_DATE);
        assertThat(testCommercialPi.getHarmonicCode()).isEqualTo(DEFAULT_HARMONIC_CODE);
        assertThat(testCommercialPi.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testCommercialPi.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
        assertThat(testCommercialPi.getTermsOfDelivery()).isEqualTo(DEFAULT_TERMS_OF_DELIVERY);
        assertThat(testCommercialPi.getShipmentDate()).isEqualTo(DEFAULT_SHIPMENT_DATE);
        assertThat(testCommercialPi.getPortOfLoading()).isEqualTo(DEFAULT_PORT_OF_LOADING);
        assertThat(testCommercialPi.getPortOfDestination()).isEqualTo(DEFAULT_PORT_OF_DESTINATION);
        assertThat(testCommercialPi.getPurchaseOrderNo()).isEqualTo(DEFAULT_PURCHASE_ORDER_NO);
        assertThat(testCommercialPi.getPiStatus()).isEqualTo(DEFAULT_PI_STATUS);
        assertThat(testCommercialPi.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPi.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPi.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPi.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPi in Elasticsearch
        verify(mockCommercialPiSearchRepository, times(1)).save(testCommercialPi);
    }

    @Test
    @Transactional
    public void createCommercialPiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPiRepository.findAll().size();

        // Create the CommercialPi with an existing ID
        commercialPi.setId(1L);
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(commercialPi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPiMockMvc.perform(post("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPi in the database
        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPi in Elasticsearch
        verify(mockCommercialPiSearchRepository, times(0)).save(commercialPi);
    }

    @Test
    @Transactional
    public void checkProformaNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPiRepository.findAll().size();
        // set the field null
        commercialPi.setProformaNo(null);

        // Create the CommercialPi, which fails.
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(commercialPi);

        restCommercialPiMockMvc.perform(post("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPiRepository.findAll().size();
        // set the field null
        commercialPi.setPaymentType(null);

        // Create the CommercialPi, which fails.
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(commercialPi);

        restCommercialPiMockMvc.perform(post("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPis() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList
        restCommercialPiMockMvc.perform(get("/api/commercial-pis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPi.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO.toString())))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM.toString())))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING.toString())))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].piStatus").value(hasItem(DEFAULT_PI_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPi() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get the commercialPi
        restCommercialPiMockMvc.perform(get("/api/commercial-pis/{id}", commercialPi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPi.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyDescription").value(DEFAULT_COMPANY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.proformaNo").value(DEFAULT_PROFORMA_NO.toString()))
            .andExpect(jsonPath("$.proformaDate").value(DEFAULT_PROFORMA_DATE.toString()))
            .andExpect(jsonPath("$.harmonicCode").value(DEFAULT_HARMONIC_CODE.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentTerm").value(DEFAULT_PAYMENT_TERM.toString()))
            .andExpect(jsonPath("$.termsOfDelivery").value(DEFAULT_TERMS_OF_DELIVERY.toString()))
            .andExpect(jsonPath("$.shipmentDate").value(DEFAULT_SHIPMENT_DATE.toString()))
            .andExpect(jsonPath("$.portOfLoading").value(DEFAULT_PORT_OF_LOADING.toString()))
            .andExpect(jsonPath("$.portOfDestination").value(DEFAULT_PORT_OF_DESTINATION.toString()))
            .andExpect(jsonPath("$.purchaseOrderNo").value(DEFAULT_PURCHASE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.piStatus").value(DEFAULT_PI_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCommercialPiShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the commercialPiList where companyName equals to UPDATED_COMPANY_NAME
        defaultCommercialPiShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCommercialPiShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the commercialPiList where companyName equals to UPDATED_COMPANY_NAME
        defaultCommercialPiShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyName is not null
        defaultCommercialPiShouldBeFound("companyName.specified=true");

        // Get all the commercialPiList where companyName is null
        defaultCommercialPiShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyDescription equals to DEFAULT_COMPANY_DESCRIPTION
        defaultCommercialPiShouldBeFound("companyDescription.equals=" + DEFAULT_COMPANY_DESCRIPTION);

        // Get all the commercialPiList where companyDescription equals to UPDATED_COMPANY_DESCRIPTION
        defaultCommercialPiShouldNotBeFound("companyDescription.equals=" + UPDATED_COMPANY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyDescription in DEFAULT_COMPANY_DESCRIPTION or UPDATED_COMPANY_DESCRIPTION
        defaultCommercialPiShouldBeFound("companyDescription.in=" + DEFAULT_COMPANY_DESCRIPTION + "," + UPDATED_COMPANY_DESCRIPTION);

        // Get all the commercialPiList where companyDescription equals to UPDATED_COMPANY_DESCRIPTION
        defaultCommercialPiShouldNotBeFound("companyDescription.in=" + UPDATED_COMPANY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCompanyDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where companyDescription is not null
        defaultCommercialPiShouldBeFound("companyDescription.specified=true");

        // Get all the commercialPiList where companyDescription is null
        defaultCommercialPiShouldNotBeFound("companyDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaNo equals to DEFAULT_PROFORMA_NO
        defaultCommercialPiShouldBeFound("proformaNo.equals=" + DEFAULT_PROFORMA_NO);

        // Get all the commercialPiList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialPiShouldNotBeFound("proformaNo.equals=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaNo in DEFAULT_PROFORMA_NO or UPDATED_PROFORMA_NO
        defaultCommercialPiShouldBeFound("proformaNo.in=" + DEFAULT_PROFORMA_NO + "," + UPDATED_PROFORMA_NO);

        // Get all the commercialPiList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialPiShouldNotBeFound("proformaNo.in=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaNo is not null
        defaultCommercialPiShouldBeFound("proformaNo.specified=true");

        // Get all the commercialPiList where proformaNo is null
        defaultCommercialPiShouldNotBeFound("proformaNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaDate equals to DEFAULT_PROFORMA_DATE
        defaultCommercialPiShouldBeFound("proformaDate.equals=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialPiList where proformaDate equals to UPDATED_PROFORMA_DATE
        defaultCommercialPiShouldNotBeFound("proformaDate.equals=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaDate in DEFAULT_PROFORMA_DATE or UPDATED_PROFORMA_DATE
        defaultCommercialPiShouldBeFound("proformaDate.in=" + DEFAULT_PROFORMA_DATE + "," + UPDATED_PROFORMA_DATE);

        // Get all the commercialPiList where proformaDate equals to UPDATED_PROFORMA_DATE
        defaultCommercialPiShouldNotBeFound("proformaDate.in=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaDate is not null
        defaultCommercialPiShouldBeFound("proformaDate.specified=true");

        // Get all the commercialPiList where proformaDate is null
        defaultCommercialPiShouldNotBeFound("proformaDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaDate greater than or equals to DEFAULT_PROFORMA_DATE
        defaultCommercialPiShouldBeFound("proformaDate.greaterOrEqualThan=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialPiList where proformaDate greater than or equals to UPDATED_PROFORMA_DATE
        defaultCommercialPiShouldNotBeFound("proformaDate.greaterOrEqualThan=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByProformaDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where proformaDate less than or equals to DEFAULT_PROFORMA_DATE
        defaultCommercialPiShouldNotBeFound("proformaDate.lessThan=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialPiList where proformaDate less than or equals to UPDATED_PROFORMA_DATE
        defaultCommercialPiShouldBeFound("proformaDate.lessThan=" + UPDATED_PROFORMA_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPisByHarmonicCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where harmonicCode equals to DEFAULT_HARMONIC_CODE
        defaultCommercialPiShouldBeFound("harmonicCode.equals=" + DEFAULT_HARMONIC_CODE);

        // Get all the commercialPiList where harmonicCode equals to UPDATED_HARMONIC_CODE
        defaultCommercialPiShouldNotBeFound("harmonicCode.equals=" + UPDATED_HARMONIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByHarmonicCodeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where harmonicCode in DEFAULT_HARMONIC_CODE or UPDATED_HARMONIC_CODE
        defaultCommercialPiShouldBeFound("harmonicCode.in=" + DEFAULT_HARMONIC_CODE + "," + UPDATED_HARMONIC_CODE);

        // Get all the commercialPiList where harmonicCode equals to UPDATED_HARMONIC_CODE
        defaultCommercialPiShouldNotBeFound("harmonicCode.in=" + UPDATED_HARMONIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByHarmonicCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where harmonicCode is not null
        defaultCommercialPiShouldBeFound("harmonicCode.specified=true");

        // Get all the commercialPiList where harmonicCode is null
        defaultCommercialPiShouldNotBeFound("harmonicCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultCommercialPiShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the commercialPiList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCommercialPiShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultCommercialPiShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the commercialPiList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultCommercialPiShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentType is not null
        defaultCommercialPiShouldBeFound("paymentType.specified=true");

        // Get all the commercialPiList where paymentType is null
        defaultCommercialPiShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTermIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentTerm equals to DEFAULT_PAYMENT_TERM
        defaultCommercialPiShouldBeFound("paymentTerm.equals=" + DEFAULT_PAYMENT_TERM);

        // Get all the commercialPiList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCommercialPiShouldNotBeFound("paymentTerm.equals=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTermIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentTerm in DEFAULT_PAYMENT_TERM or UPDATED_PAYMENT_TERM
        defaultCommercialPiShouldBeFound("paymentTerm.in=" + DEFAULT_PAYMENT_TERM + "," + UPDATED_PAYMENT_TERM);

        // Get all the commercialPiList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCommercialPiShouldNotBeFound("paymentTerm.in=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPaymentTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where paymentTerm is not null
        defaultCommercialPiShouldBeFound("paymentTerm.specified=true");

        // Get all the commercialPiList where paymentTerm is null
        defaultCommercialPiShouldNotBeFound("paymentTerm.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByTermsOfDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where termsOfDelivery equals to DEFAULT_TERMS_OF_DELIVERY
        defaultCommercialPiShouldBeFound("termsOfDelivery.equals=" + DEFAULT_TERMS_OF_DELIVERY);

        // Get all the commercialPiList where termsOfDelivery equals to UPDATED_TERMS_OF_DELIVERY
        defaultCommercialPiShouldNotBeFound("termsOfDelivery.equals=" + UPDATED_TERMS_OF_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByTermsOfDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where termsOfDelivery in DEFAULT_TERMS_OF_DELIVERY or UPDATED_TERMS_OF_DELIVERY
        defaultCommercialPiShouldBeFound("termsOfDelivery.in=" + DEFAULT_TERMS_OF_DELIVERY + "," + UPDATED_TERMS_OF_DELIVERY);

        // Get all the commercialPiList where termsOfDelivery equals to UPDATED_TERMS_OF_DELIVERY
        defaultCommercialPiShouldNotBeFound("termsOfDelivery.in=" + UPDATED_TERMS_OF_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByTermsOfDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where termsOfDelivery is not null
        defaultCommercialPiShouldBeFound("termsOfDelivery.specified=true");

        // Get all the commercialPiList where termsOfDelivery is null
        defaultCommercialPiShouldNotBeFound("termsOfDelivery.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByShipmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where shipmentDate equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPiShouldBeFound("shipmentDate.equals=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPiList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPiShouldNotBeFound("shipmentDate.equals=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByShipmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where shipmentDate in DEFAULT_SHIPMENT_DATE or UPDATED_SHIPMENT_DATE
        defaultCommercialPiShouldBeFound("shipmentDate.in=" + DEFAULT_SHIPMENT_DATE + "," + UPDATED_SHIPMENT_DATE);

        // Get all the commercialPiList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPiShouldNotBeFound("shipmentDate.in=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByShipmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where shipmentDate is not null
        defaultCommercialPiShouldBeFound("shipmentDate.specified=true");

        // Get all the commercialPiList where shipmentDate is null
        defaultCommercialPiShouldNotBeFound("shipmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfLoadingIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfLoading equals to DEFAULT_PORT_OF_LOADING
        defaultCommercialPiShouldBeFound("portOfLoading.equals=" + DEFAULT_PORT_OF_LOADING);

        // Get all the commercialPiList where portOfLoading equals to UPDATED_PORT_OF_LOADING
        defaultCommercialPiShouldNotBeFound("portOfLoading.equals=" + UPDATED_PORT_OF_LOADING);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfLoadingIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfLoading in DEFAULT_PORT_OF_LOADING or UPDATED_PORT_OF_LOADING
        defaultCommercialPiShouldBeFound("portOfLoading.in=" + DEFAULT_PORT_OF_LOADING + "," + UPDATED_PORT_OF_LOADING);

        // Get all the commercialPiList where portOfLoading equals to UPDATED_PORT_OF_LOADING
        defaultCommercialPiShouldNotBeFound("portOfLoading.in=" + UPDATED_PORT_OF_LOADING);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfLoadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfLoading is not null
        defaultCommercialPiShouldBeFound("portOfLoading.specified=true");

        // Get all the commercialPiList where portOfLoading is null
        defaultCommercialPiShouldNotBeFound("portOfLoading.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfDestination equals to DEFAULT_PORT_OF_DESTINATION
        defaultCommercialPiShouldBeFound("portOfDestination.equals=" + DEFAULT_PORT_OF_DESTINATION);

        // Get all the commercialPiList where portOfDestination equals to UPDATED_PORT_OF_DESTINATION
        defaultCommercialPiShouldNotBeFound("portOfDestination.equals=" + UPDATED_PORT_OF_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfDestination in DEFAULT_PORT_OF_DESTINATION or UPDATED_PORT_OF_DESTINATION
        defaultCommercialPiShouldBeFound("portOfDestination.in=" + DEFAULT_PORT_OF_DESTINATION + "," + UPDATED_PORT_OF_DESTINATION);

        // Get all the commercialPiList where portOfDestination equals to UPDATED_PORT_OF_DESTINATION
        defaultCommercialPiShouldNotBeFound("portOfDestination.in=" + UPDATED_PORT_OF_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPortOfDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where portOfDestination is not null
        defaultCommercialPiShouldBeFound("portOfDestination.specified=true");

        // Get all the commercialPiList where portOfDestination is null
        defaultCommercialPiShouldNotBeFound("portOfDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPurchaseOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where purchaseOrderNo equals to DEFAULT_PURCHASE_ORDER_NO
        defaultCommercialPiShouldBeFound("purchaseOrderNo.equals=" + DEFAULT_PURCHASE_ORDER_NO);

        // Get all the commercialPiList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPiShouldNotBeFound("purchaseOrderNo.equals=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPurchaseOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where purchaseOrderNo in DEFAULT_PURCHASE_ORDER_NO or UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPiShouldBeFound("purchaseOrderNo.in=" + DEFAULT_PURCHASE_ORDER_NO + "," + UPDATED_PURCHASE_ORDER_NO);

        // Get all the commercialPiList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPiShouldNotBeFound("purchaseOrderNo.in=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPurchaseOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where purchaseOrderNo is not null
        defaultCommercialPiShouldBeFound("purchaseOrderNo.specified=true");

        // Get all the commercialPiList where purchaseOrderNo is null
        defaultCommercialPiShouldNotBeFound("purchaseOrderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPiStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where piStatus equals to DEFAULT_PI_STATUS
        defaultCommercialPiShouldBeFound("piStatus.equals=" + DEFAULT_PI_STATUS);

        // Get all the commercialPiList where piStatus equals to UPDATED_PI_STATUS
        defaultCommercialPiShouldNotBeFound("piStatus.equals=" + UPDATED_PI_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPiStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where piStatus in DEFAULT_PI_STATUS or UPDATED_PI_STATUS
        defaultCommercialPiShouldBeFound("piStatus.in=" + DEFAULT_PI_STATUS + "," + UPDATED_PI_STATUS);

        // Get all the commercialPiList where piStatus equals to UPDATED_PI_STATUS
        defaultCommercialPiShouldNotBeFound("piStatus.in=" + UPDATED_PI_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByPiStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where piStatus is not null
        defaultCommercialPiShouldBeFound("piStatus.specified=true");

        // Get all the commercialPiList where piStatus is null
        defaultCommercialPiShouldNotBeFound("piStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPiShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPiList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPiShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPiShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPiList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPiShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdBy is not null
        defaultCommercialPiShouldBeFound("createdBy.specified=true");

        // Get all the commercialPiList where createdBy is null
        defaultCommercialPiShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPiShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPiList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPiShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPiShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPiList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPiShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where createdOn is not null
        defaultCommercialPiShouldBeFound("createdOn.specified=true");

        // Get all the commercialPiList where createdOn is null
        defaultCommercialPiShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPiShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPiList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPiShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPiShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPiList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPiShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedBy is not null
        defaultCommercialPiShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPiList where updatedBy is null
        defaultCommercialPiShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPiShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPiList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPiShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPiShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPiList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPiShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPisByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        // Get all the commercialPiList where updatedOn is not null
        defaultCommercialPiShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPiList where updatedOn is null
        defaultCommercialPiShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPisByCommercialBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialBudget commercialBudget = CommercialBudgetResourceIntTest.createEntity(em);
        em.persist(commercialBudget);
        em.flush();
        commercialPi.setCommercialBudget(commercialBudget);
        commercialPiRepository.saveAndFlush(commercialPi);
        Long commercialBudgetId = commercialBudget.getId();

        // Get all the commercialPiList where commercialBudget equals to commercialBudgetId
        defaultCommercialPiShouldBeFound("commercialBudgetId.equals=" + commercialBudgetId);

        // Get all the commercialPiList where commercialBudget equals to commercialBudgetId + 1
        defaultCommercialPiShouldNotBeFound("commercialBudgetId.equals=" + (commercialBudgetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPiShouldBeFound(String filter) throws Exception {
        restCommercialPiMockMvc.perform(get("/api/commercial-pis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPi.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE)))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING)))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION)))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].piStatus").value(hasItem(DEFAULT_PI_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPiMockMvc.perform(get("/api/commercial-pis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPiShouldNotBeFound(String filter) throws Exception {
        restCommercialPiMockMvc.perform(get("/api/commercial-pis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPiMockMvc.perform(get("/api/commercial-pis/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPi() throws Exception {
        // Get the commercialPi
        restCommercialPiMockMvc.perform(get("/api/commercial-pis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPi() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        int databaseSizeBeforeUpdate = commercialPiRepository.findAll().size();

        // Update the commercialPi
        CommercialPi updatedCommercialPi = commercialPiRepository.findById(commercialPi.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPi are not directly saved in db
        em.detach(updatedCommercialPi);
        updatedCommercialPi
            .companyName(UPDATED_COMPANY_NAME)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .proformaNo(UPDATED_PROFORMA_NO)
            .proformaDate(UPDATED_PROFORMA_DATE)
            .harmonicCode(UPDATED_HARMONIC_CODE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .termsOfDelivery(UPDATED_TERMS_OF_DELIVERY)
            .shipmentDate(UPDATED_SHIPMENT_DATE)
            .portOfLoading(UPDATED_PORT_OF_LOADING)
            .portOfDestination(UPDATED_PORT_OF_DESTINATION)
            .purchaseOrderNo(UPDATED_PURCHASE_ORDER_NO)
            .piStatus(UPDATED_PI_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(updatedCommercialPi);

        restCommercialPiMockMvc.perform(put("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPi in the database
        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeUpdate);
        CommercialPi testCommercialPi = commercialPiList.get(commercialPiList.size() - 1);
        assertThat(testCommercialPi.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCommercialPi.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testCommercialPi.getProformaNo()).isEqualTo(UPDATED_PROFORMA_NO);
        assertThat(testCommercialPi.getProformaDate()).isEqualTo(UPDATED_PROFORMA_DATE);
        assertThat(testCommercialPi.getHarmonicCode()).isEqualTo(UPDATED_HARMONIC_CODE);
        assertThat(testCommercialPi.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testCommercialPi.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
        assertThat(testCommercialPi.getTermsOfDelivery()).isEqualTo(UPDATED_TERMS_OF_DELIVERY);
        assertThat(testCommercialPi.getShipmentDate()).isEqualTo(UPDATED_SHIPMENT_DATE);
        assertThat(testCommercialPi.getPortOfLoading()).isEqualTo(UPDATED_PORT_OF_LOADING);
        assertThat(testCommercialPi.getPortOfDestination()).isEqualTo(UPDATED_PORT_OF_DESTINATION);
        assertThat(testCommercialPi.getPurchaseOrderNo()).isEqualTo(UPDATED_PURCHASE_ORDER_NO);
        assertThat(testCommercialPi.getPiStatus()).isEqualTo(UPDATED_PI_STATUS);
        assertThat(testCommercialPi.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPi.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPi.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPi.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPi in Elasticsearch
        verify(mockCommercialPiSearchRepository, times(1)).save(testCommercialPi);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPi() throws Exception {
        int databaseSizeBeforeUpdate = commercialPiRepository.findAll().size();

        // Create the CommercialPi
        CommercialPiDTO commercialPiDTO = commercialPiMapper.toDto(commercialPi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPiMockMvc.perform(put("/api/commercial-pis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPi in the database
        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPi in Elasticsearch
        verify(mockCommercialPiSearchRepository, times(0)).save(commercialPi);
    }

    @Test
    @Transactional
    public void deleteCommercialPi() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);

        int databaseSizeBeforeDelete = commercialPiRepository.findAll().size();

        // Delete the commercialPi
        restCommercialPiMockMvc.perform(delete("/api/commercial-pis/{id}", commercialPi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPi> commercialPiList = commercialPiRepository.findAll();
        assertThat(commercialPiList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPi in Elasticsearch
        verify(mockCommercialPiSearchRepository, times(1)).deleteById(commercialPi.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPi() throws Exception {
        // Initialize the database
        commercialPiRepository.saveAndFlush(commercialPi);
        when(mockCommercialPiSearchRepository.search(queryStringQuery("id:" + commercialPi.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPi), PageRequest.of(0, 1), 1));
        // Search the commercialPi
        restCommercialPiMockMvc.perform(get("/api/_search/commercial-pis?query=id:" + commercialPi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPi.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE)))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING)))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION)))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].piStatus").value(hasItem(DEFAULT_PI_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPi.class);
        CommercialPi commercialPi1 = new CommercialPi();
        commercialPi1.setId(1L);
        CommercialPi commercialPi2 = new CommercialPi();
        commercialPi2.setId(commercialPi1.getId());
        assertThat(commercialPi1).isEqualTo(commercialPi2);
        commercialPi2.setId(2L);
        assertThat(commercialPi1).isNotEqualTo(commercialPi2);
        commercialPi1.setId(null);
        assertThat(commercialPi1).isNotEqualTo(commercialPi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPiDTO.class);
        CommercialPiDTO commercialPiDTO1 = new CommercialPiDTO();
        commercialPiDTO1.setId(1L);
        CommercialPiDTO commercialPiDTO2 = new CommercialPiDTO();
        assertThat(commercialPiDTO1).isNotEqualTo(commercialPiDTO2);
        commercialPiDTO2.setId(commercialPiDTO1.getId());
        assertThat(commercialPiDTO1).isEqualTo(commercialPiDTO2);
        commercialPiDTO2.setId(2L);
        assertThat(commercialPiDTO1).isNotEqualTo(commercialPiDTO2);
        commercialPiDTO1.setId(null);
        assertThat(commercialPiDTO1).isNotEqualTo(commercialPiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPiMapper.fromId(null)).isNull();
    }
}
