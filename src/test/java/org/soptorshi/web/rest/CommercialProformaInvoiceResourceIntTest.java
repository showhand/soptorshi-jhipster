package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialProformaInvoice;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialProformaInvoiceRepository;
import org.soptorshi.repository.search.CommercialProformaInvoiceSearchRepository;
import org.soptorshi.service.CommercialProformaInvoiceService;
import org.soptorshi.service.dto.CommercialProformaInvoiceDTO;
import org.soptorshi.service.mapper.CommercialProformaInvoiceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialProformaInvoiceCriteria;
import org.soptorshi.service.CommercialProformaInvoiceQueryService;

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
 * Test class for the CommercialProformaInvoiceResource REST controller.
 *
 * @see CommercialProformaInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialProformaInvoiceResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PROFORMA_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROFORMA_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PROFORMA_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROFORMA_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HARMONIC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HARMONIC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_OF_DELIVERY = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_OF_DELIVERY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_LANDING = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_LANDING = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialProformaInvoiceRepository commercialProformaInvoiceRepository;

    @Autowired
    private CommercialProformaInvoiceMapper commercialProformaInvoiceMapper;

    @Autowired
    private CommercialProformaInvoiceService commercialProformaInvoiceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialProformaInvoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialProformaInvoiceSearchRepository mockCommercialProformaInvoiceSearchRepository;

    @Autowired
    private CommercialProformaInvoiceQueryService commercialProformaInvoiceQueryService;

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

    private MockMvc restCommercialProformaInvoiceMockMvc;

    private CommercialProformaInvoice commercialProformaInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialProformaInvoiceResource commercialProformaInvoiceResource = new CommercialProformaInvoiceResource(commercialProformaInvoiceService, commercialProformaInvoiceQueryService);
        this.restCommercialProformaInvoiceMockMvc = MockMvcBuilders.standaloneSetup(commercialProformaInvoiceResource)
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
    public static CommercialProformaInvoice createEntity(EntityManager em) {
        CommercialProformaInvoice commercialProformaInvoice = new CommercialProformaInvoice()
            .companyName(DEFAULT_COMPANY_NAME)
            .companyDescriptionOrAddress(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS)
            .proformaNo(DEFAULT_PROFORMA_NO)
            .proformaDate(DEFAULT_PROFORMA_DATE)
            .harmonicCode(DEFAULT_HARMONIC_CODE)
            .paymentTerm(DEFAULT_PAYMENT_TERM)
            .termsOfDelivery(DEFAULT_TERMS_OF_DELIVERY)
            .shipmentDate(DEFAULT_SHIPMENT_DATE)
            .portOfLanding(DEFAULT_PORT_OF_LANDING)
            .portOfDestination(DEFAULT_PORT_OF_DESTINATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialProformaInvoice;
    }

    @Before
    public void initTest() {
        commercialProformaInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialProformaInvoice() throws Exception {
        int databaseSizeBeforeCreate = commercialProformaInvoiceRepository.findAll().size();

        // Create the CommercialProformaInvoice
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);
        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialProformaInvoice in the database
        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialProformaInvoice testCommercialProformaInvoice = commercialProformaInvoiceList.get(commercialProformaInvoiceList.size() - 1);
        assertThat(testCommercialProformaInvoice.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCommercialProformaInvoice.getCompanyDescriptionOrAddress()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS);
        assertThat(testCommercialProformaInvoice.getProformaNo()).isEqualTo(DEFAULT_PROFORMA_NO);
        assertThat(testCommercialProformaInvoice.getProformaDate()).isEqualTo(DEFAULT_PROFORMA_DATE);
        assertThat(testCommercialProformaInvoice.getHarmonicCode()).isEqualTo(DEFAULT_HARMONIC_CODE);
        assertThat(testCommercialProformaInvoice.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
        assertThat(testCommercialProformaInvoice.getTermsOfDelivery()).isEqualTo(DEFAULT_TERMS_OF_DELIVERY);
        assertThat(testCommercialProformaInvoice.getShipmentDate()).isEqualTo(DEFAULT_SHIPMENT_DATE);
        assertThat(testCommercialProformaInvoice.getPortOfLanding()).isEqualTo(DEFAULT_PORT_OF_LANDING);
        assertThat(testCommercialProformaInvoice.getPortOfDestination()).isEqualTo(DEFAULT_PORT_OF_DESTINATION);
        assertThat(testCommercialProformaInvoice.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialProformaInvoice.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialProformaInvoice.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialProformaInvoice.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialProformaInvoice in Elasticsearch
        verify(mockCommercialProformaInvoiceSearchRepository, times(1)).save(testCommercialProformaInvoice);
    }

    @Test
    @Transactional
    public void createCommercialProformaInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialProformaInvoiceRepository.findAll().size();

        // Create the CommercialProformaInvoice with an existing ID
        commercialProformaInvoice.setId(1L);
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialProformaInvoice in the database
        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialProformaInvoice in Elasticsearch
        verify(mockCommercialProformaInvoiceSearchRepository, times(0)).save(commercialProformaInvoice);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setCompanyName(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProformaNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setProformaNo(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHarmonicCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setHarmonicCode(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentTermIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setPaymentTerm(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTermsOfDeliveryIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setTermsOfDelivery(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShipmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setShipmentDate(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortOfLandingIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setPortOfLanding(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortOfDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProformaInvoiceRepository.findAll().size();
        // set the field null
        commercialProformaInvoice.setPortOfDestination(null);

        // Create the CommercialProformaInvoice, which fails.
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(post("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoices() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProformaInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyDescriptionOrAddress").value(hasItem(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO.toString())))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM.toString())))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].portOfLanding").value(hasItem(DEFAULT_PORT_OF_LANDING.toString())))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialProformaInvoice() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get the commercialProformaInvoice
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices/{id}", commercialProformaInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialProformaInvoice.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyDescriptionOrAddress").value(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS.toString()))
            .andExpect(jsonPath("$.proformaNo").value(DEFAULT_PROFORMA_NO.toString()))
            .andExpect(jsonPath("$.proformaDate").value(DEFAULT_PROFORMA_DATE.toString()))
            .andExpect(jsonPath("$.harmonicCode").value(DEFAULT_HARMONIC_CODE.toString()))
            .andExpect(jsonPath("$.paymentTerm").value(DEFAULT_PAYMENT_TERM.toString()))
            .andExpect(jsonPath("$.termsOfDelivery").value(DEFAULT_TERMS_OF_DELIVERY.toString()))
            .andExpect(jsonPath("$.shipmentDate").value(DEFAULT_SHIPMENT_DATE.toString()))
            .andExpect(jsonPath("$.portOfLanding").value(DEFAULT_PORT_OF_LANDING.toString()))
            .andExpect(jsonPath("$.portOfDestination").value(DEFAULT_PORT_OF_DESTINATION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCommercialProformaInvoiceShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the commercialProformaInvoiceList where companyName equals to UPDATED_COMPANY_NAME
        defaultCommercialProformaInvoiceShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCommercialProformaInvoiceShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the commercialProformaInvoiceList where companyName equals to UPDATED_COMPANY_NAME
        defaultCommercialProformaInvoiceShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyName is not null
        defaultCommercialProformaInvoiceShouldBeFound("companyName.specified=true");

        // Get all the commercialProformaInvoiceList where companyName is null
        defaultCommercialProformaInvoiceShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyDescriptionOrAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress equals to DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS
        defaultCommercialProformaInvoiceShouldBeFound("companyDescriptionOrAddress.equals=" + DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS);

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress equals to UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS
        defaultCommercialProformaInvoiceShouldNotBeFound("companyDescriptionOrAddress.equals=" + UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyDescriptionOrAddressIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress in DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS or UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS
        defaultCommercialProformaInvoiceShouldBeFound("companyDescriptionOrAddress.in=" + DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS + "," + UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS);

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress equals to UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS
        defaultCommercialProformaInvoiceShouldNotBeFound("companyDescriptionOrAddress.in=" + UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCompanyDescriptionOrAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress is not null
        defaultCommercialProformaInvoiceShouldBeFound("companyDescriptionOrAddress.specified=true");

        // Get all the commercialProformaInvoiceList where companyDescriptionOrAddress is null
        defaultCommercialProformaInvoiceShouldNotBeFound("companyDescriptionOrAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaNo equals to DEFAULT_PROFORMA_NO
        defaultCommercialProformaInvoiceShouldBeFound("proformaNo.equals=" + DEFAULT_PROFORMA_NO);

        // Get all the commercialProformaInvoiceList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaNo.equals=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaNo in DEFAULT_PROFORMA_NO or UPDATED_PROFORMA_NO
        defaultCommercialProformaInvoiceShouldBeFound("proformaNo.in=" + DEFAULT_PROFORMA_NO + "," + UPDATED_PROFORMA_NO);

        // Get all the commercialProformaInvoiceList where proformaNo equals to UPDATED_PROFORMA_NO
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaNo.in=" + UPDATED_PROFORMA_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaNo is not null
        defaultCommercialProformaInvoiceShouldBeFound("proformaNo.specified=true");

        // Get all the commercialProformaInvoiceList where proformaNo is null
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaDate equals to DEFAULT_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldBeFound("proformaDate.equals=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialProformaInvoiceList where proformaDate equals to UPDATED_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaDate.equals=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaDate in DEFAULT_PROFORMA_DATE or UPDATED_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldBeFound("proformaDate.in=" + DEFAULT_PROFORMA_DATE + "," + UPDATED_PROFORMA_DATE);

        // Get all the commercialProformaInvoiceList where proformaDate equals to UPDATED_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaDate.in=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaDate is not null
        defaultCommercialProformaInvoiceShouldBeFound("proformaDate.specified=true");

        // Get all the commercialProformaInvoiceList where proformaDate is null
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaDate greater than or equals to DEFAULT_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldBeFound("proformaDate.greaterOrEqualThan=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialProformaInvoiceList where proformaDate greater than or equals to UPDATED_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaDate.greaterOrEqualThan=" + UPDATED_PROFORMA_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByProformaDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where proformaDate less than or equals to DEFAULT_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("proformaDate.lessThan=" + DEFAULT_PROFORMA_DATE);

        // Get all the commercialProformaInvoiceList where proformaDate less than or equals to UPDATED_PROFORMA_DATE
        defaultCommercialProformaInvoiceShouldBeFound("proformaDate.lessThan=" + UPDATED_PROFORMA_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByHarmonicCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where harmonicCode equals to DEFAULT_HARMONIC_CODE
        defaultCommercialProformaInvoiceShouldBeFound("harmonicCode.equals=" + DEFAULT_HARMONIC_CODE);

        // Get all the commercialProformaInvoiceList where harmonicCode equals to UPDATED_HARMONIC_CODE
        defaultCommercialProformaInvoiceShouldNotBeFound("harmonicCode.equals=" + UPDATED_HARMONIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByHarmonicCodeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where harmonicCode in DEFAULT_HARMONIC_CODE or UPDATED_HARMONIC_CODE
        defaultCommercialProformaInvoiceShouldBeFound("harmonicCode.in=" + DEFAULT_HARMONIC_CODE + "," + UPDATED_HARMONIC_CODE);

        // Get all the commercialProformaInvoiceList where harmonicCode equals to UPDATED_HARMONIC_CODE
        defaultCommercialProformaInvoiceShouldNotBeFound("harmonicCode.in=" + UPDATED_HARMONIC_CODE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByHarmonicCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where harmonicCode is not null
        defaultCommercialProformaInvoiceShouldBeFound("harmonicCode.specified=true");

        // Get all the commercialProformaInvoiceList where harmonicCode is null
        defaultCommercialProformaInvoiceShouldNotBeFound("harmonicCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPaymentTermIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where paymentTerm equals to DEFAULT_PAYMENT_TERM
        defaultCommercialProformaInvoiceShouldBeFound("paymentTerm.equals=" + DEFAULT_PAYMENT_TERM);

        // Get all the commercialProformaInvoiceList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCommercialProformaInvoiceShouldNotBeFound("paymentTerm.equals=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPaymentTermIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where paymentTerm in DEFAULT_PAYMENT_TERM or UPDATED_PAYMENT_TERM
        defaultCommercialProformaInvoiceShouldBeFound("paymentTerm.in=" + DEFAULT_PAYMENT_TERM + "," + UPDATED_PAYMENT_TERM);

        // Get all the commercialProformaInvoiceList where paymentTerm equals to UPDATED_PAYMENT_TERM
        defaultCommercialProformaInvoiceShouldNotBeFound("paymentTerm.in=" + UPDATED_PAYMENT_TERM);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPaymentTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where paymentTerm is not null
        defaultCommercialProformaInvoiceShouldBeFound("paymentTerm.specified=true");

        // Get all the commercialProformaInvoiceList where paymentTerm is null
        defaultCommercialProformaInvoiceShouldNotBeFound("paymentTerm.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByTermsOfDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where termsOfDelivery equals to DEFAULT_TERMS_OF_DELIVERY
        defaultCommercialProformaInvoiceShouldBeFound("termsOfDelivery.equals=" + DEFAULT_TERMS_OF_DELIVERY);

        // Get all the commercialProformaInvoiceList where termsOfDelivery equals to UPDATED_TERMS_OF_DELIVERY
        defaultCommercialProformaInvoiceShouldNotBeFound("termsOfDelivery.equals=" + UPDATED_TERMS_OF_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByTermsOfDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where termsOfDelivery in DEFAULT_TERMS_OF_DELIVERY or UPDATED_TERMS_OF_DELIVERY
        defaultCommercialProformaInvoiceShouldBeFound("termsOfDelivery.in=" + DEFAULT_TERMS_OF_DELIVERY + "," + UPDATED_TERMS_OF_DELIVERY);

        // Get all the commercialProformaInvoiceList where termsOfDelivery equals to UPDATED_TERMS_OF_DELIVERY
        defaultCommercialProformaInvoiceShouldNotBeFound("termsOfDelivery.in=" + UPDATED_TERMS_OF_DELIVERY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByTermsOfDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where termsOfDelivery is not null
        defaultCommercialProformaInvoiceShouldBeFound("termsOfDelivery.specified=true");

        // Get all the commercialProformaInvoiceList where termsOfDelivery is null
        defaultCommercialProformaInvoiceShouldNotBeFound("termsOfDelivery.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByShipmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where shipmentDate equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialProformaInvoiceShouldBeFound("shipmentDate.equals=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialProformaInvoiceList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("shipmentDate.equals=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByShipmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where shipmentDate in DEFAULT_SHIPMENT_DATE or UPDATED_SHIPMENT_DATE
        defaultCommercialProformaInvoiceShouldBeFound("shipmentDate.in=" + DEFAULT_SHIPMENT_DATE + "," + UPDATED_SHIPMENT_DATE);

        // Get all the commercialProformaInvoiceList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialProformaInvoiceShouldNotBeFound("shipmentDate.in=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByShipmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where shipmentDate is not null
        defaultCommercialProformaInvoiceShouldBeFound("shipmentDate.specified=true");

        // Get all the commercialProformaInvoiceList where shipmentDate is null
        defaultCommercialProformaInvoiceShouldNotBeFound("shipmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfLandingIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfLanding equals to DEFAULT_PORT_OF_LANDING
        defaultCommercialProformaInvoiceShouldBeFound("portOfLanding.equals=" + DEFAULT_PORT_OF_LANDING);

        // Get all the commercialProformaInvoiceList where portOfLanding equals to UPDATED_PORT_OF_LANDING
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfLanding.equals=" + UPDATED_PORT_OF_LANDING);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfLandingIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfLanding in DEFAULT_PORT_OF_LANDING or UPDATED_PORT_OF_LANDING
        defaultCommercialProformaInvoiceShouldBeFound("portOfLanding.in=" + DEFAULT_PORT_OF_LANDING + "," + UPDATED_PORT_OF_LANDING);

        // Get all the commercialProformaInvoiceList where portOfLanding equals to UPDATED_PORT_OF_LANDING
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfLanding.in=" + UPDATED_PORT_OF_LANDING);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfLandingIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfLanding is not null
        defaultCommercialProformaInvoiceShouldBeFound("portOfLanding.specified=true");

        // Get all the commercialProformaInvoiceList where portOfLanding is null
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfLanding.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfDestination equals to DEFAULT_PORT_OF_DESTINATION
        defaultCommercialProformaInvoiceShouldBeFound("portOfDestination.equals=" + DEFAULT_PORT_OF_DESTINATION);

        // Get all the commercialProformaInvoiceList where portOfDestination equals to UPDATED_PORT_OF_DESTINATION
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfDestination.equals=" + UPDATED_PORT_OF_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfDestination in DEFAULT_PORT_OF_DESTINATION or UPDATED_PORT_OF_DESTINATION
        defaultCommercialProformaInvoiceShouldBeFound("portOfDestination.in=" + DEFAULT_PORT_OF_DESTINATION + "," + UPDATED_PORT_OF_DESTINATION);

        // Get all the commercialProformaInvoiceList where portOfDestination equals to UPDATED_PORT_OF_DESTINATION
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfDestination.in=" + UPDATED_PORT_OF_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByPortOfDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where portOfDestination is not null
        defaultCommercialProformaInvoiceShouldBeFound("portOfDestination.specified=true");

        // Get all the commercialProformaInvoiceList where portOfDestination is null
        defaultCommercialProformaInvoiceShouldNotBeFound("portOfDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialProformaInvoiceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialProformaInvoiceList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialProformaInvoiceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialProformaInvoiceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialProformaInvoiceList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialProformaInvoiceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdBy is not null
        defaultCommercialProformaInvoiceShouldBeFound("createdBy.specified=true");

        // Get all the commercialProformaInvoiceList where createdBy is null
        defaultCommercialProformaInvoiceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialProformaInvoiceList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialProformaInvoiceList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdOn is not null
        defaultCommercialProformaInvoiceShouldBeFound("createdOn.specified=true");

        // Get all the commercialProformaInvoiceList where createdOn is null
        defaultCommercialProformaInvoiceShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialProformaInvoiceList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialProformaInvoiceList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialProformaInvoiceShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialProformaInvoiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialProformaInvoiceShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialProformaInvoiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedBy is not null
        defaultCommercialProformaInvoiceShouldBeFound("updatedBy.specified=true");

        // Get all the commercialProformaInvoiceList where updatedBy is null
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialProformaInvoiceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialProformaInvoiceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedOn is not null
        defaultCommercialProformaInvoiceShouldBeFound("updatedOn.specified=true");

        // Get all the commercialProformaInvoiceList where updatedOn is null
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialProformaInvoiceList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        // Get all the commercialProformaInvoiceList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialProformaInvoiceShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialProformaInvoiceList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialProformaInvoiceShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialProformaInvoicesByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialProformaInvoice.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialProformaInvoiceList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialProformaInvoiceShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialProformaInvoiceList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialProformaInvoiceShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialProformaInvoiceShouldBeFound(String filter) throws Exception {
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProformaInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyDescriptionOrAddress").value(hasItem(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS)))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE)))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE)))
            .andExpect(jsonPath("$.[*].portOfLanding").value(hasItem(DEFAULT_PORT_OF_LANDING)))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialProformaInvoiceShouldNotBeFound(String filter) throws Exception {
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialProformaInvoice() throws Exception {
        // Get the commercialProformaInvoice
        restCommercialProformaInvoiceMockMvc.perform(get("/api/commercial-proforma-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialProformaInvoice() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        int databaseSizeBeforeUpdate = commercialProformaInvoiceRepository.findAll().size();

        // Update the commercialProformaInvoice
        CommercialProformaInvoice updatedCommercialProformaInvoice = commercialProformaInvoiceRepository.findById(commercialProformaInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialProformaInvoice are not directly saved in db
        em.detach(updatedCommercialProformaInvoice);
        updatedCommercialProformaInvoice
            .companyName(UPDATED_COMPANY_NAME)
            .companyDescriptionOrAddress(UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS)
            .proformaNo(UPDATED_PROFORMA_NO)
            .proformaDate(UPDATED_PROFORMA_DATE)
            .harmonicCode(UPDATED_HARMONIC_CODE)
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .termsOfDelivery(UPDATED_TERMS_OF_DELIVERY)
            .shipmentDate(UPDATED_SHIPMENT_DATE)
            .portOfLanding(UPDATED_PORT_OF_LANDING)
            .portOfDestination(UPDATED_PORT_OF_DESTINATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(updatedCommercialProformaInvoice);

        restCommercialProformaInvoiceMockMvc.perform(put("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialProformaInvoice in the database
        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeUpdate);
        CommercialProformaInvoice testCommercialProformaInvoice = commercialProformaInvoiceList.get(commercialProformaInvoiceList.size() - 1);
        assertThat(testCommercialProformaInvoice.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCommercialProformaInvoice.getCompanyDescriptionOrAddress()).isEqualTo(UPDATED_COMPANY_DESCRIPTION_OR_ADDRESS);
        assertThat(testCommercialProformaInvoice.getProformaNo()).isEqualTo(UPDATED_PROFORMA_NO);
        assertThat(testCommercialProformaInvoice.getProformaDate()).isEqualTo(UPDATED_PROFORMA_DATE);
        assertThat(testCommercialProformaInvoice.getHarmonicCode()).isEqualTo(UPDATED_HARMONIC_CODE);
        assertThat(testCommercialProformaInvoice.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
        assertThat(testCommercialProformaInvoice.getTermsOfDelivery()).isEqualTo(UPDATED_TERMS_OF_DELIVERY);
        assertThat(testCommercialProformaInvoice.getShipmentDate()).isEqualTo(UPDATED_SHIPMENT_DATE);
        assertThat(testCommercialProformaInvoice.getPortOfLanding()).isEqualTo(UPDATED_PORT_OF_LANDING);
        assertThat(testCommercialProformaInvoice.getPortOfDestination()).isEqualTo(UPDATED_PORT_OF_DESTINATION);
        assertThat(testCommercialProformaInvoice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialProformaInvoice.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialProformaInvoice.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialProformaInvoice.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialProformaInvoice in Elasticsearch
        verify(mockCommercialProformaInvoiceSearchRepository, times(1)).save(testCommercialProformaInvoice);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialProformaInvoice() throws Exception {
        int databaseSizeBeforeUpdate = commercialProformaInvoiceRepository.findAll().size();

        // Create the CommercialProformaInvoice
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialProformaInvoiceMockMvc.perform(put("/api/commercial-proforma-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProformaInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialProformaInvoice in the database
        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialProformaInvoice in Elasticsearch
        verify(mockCommercialProformaInvoiceSearchRepository, times(0)).save(commercialProformaInvoice);
    }

    @Test
    @Transactional
    public void deleteCommercialProformaInvoice() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);

        int databaseSizeBeforeDelete = commercialProformaInvoiceRepository.findAll().size();

        // Delete the commercialProformaInvoice
        restCommercialProformaInvoiceMockMvc.perform(delete("/api/commercial-proforma-invoices/{id}", commercialProformaInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialProformaInvoice> commercialProformaInvoiceList = commercialProformaInvoiceRepository.findAll();
        assertThat(commercialProformaInvoiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialProformaInvoice in Elasticsearch
        verify(mockCommercialProformaInvoiceSearchRepository, times(1)).deleteById(commercialProformaInvoice.getId());
    }

    @Test
    @Transactional
    public void searchCommercialProformaInvoice() throws Exception {
        // Initialize the database
        commercialProformaInvoiceRepository.saveAndFlush(commercialProformaInvoice);
        when(mockCommercialProformaInvoiceSearchRepository.search(queryStringQuery("id:" + commercialProformaInvoice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialProformaInvoice), PageRequest.of(0, 1), 1));
        // Search the commercialProformaInvoice
        restCommercialProformaInvoiceMockMvc.perform(get("/api/_search/commercial-proforma-invoices?query=id:" + commercialProformaInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProformaInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyDescriptionOrAddress").value(hasItem(DEFAULT_COMPANY_DESCRIPTION_OR_ADDRESS)))
            .andExpect(jsonPath("$.[*].proformaNo").value(hasItem(DEFAULT_PROFORMA_NO)))
            .andExpect(jsonPath("$.[*].proformaDate").value(hasItem(DEFAULT_PROFORMA_DATE.toString())))
            .andExpect(jsonPath("$.[*].harmonicCode").value(hasItem(DEFAULT_HARMONIC_CODE)))
            .andExpect(jsonPath("$.[*].paymentTerm").value(hasItem(DEFAULT_PAYMENT_TERM)))
            .andExpect(jsonPath("$.[*].termsOfDelivery").value(hasItem(DEFAULT_TERMS_OF_DELIVERY)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE)))
            .andExpect(jsonPath("$.[*].portOfLanding").value(hasItem(DEFAULT_PORT_OF_LANDING)))
            .andExpect(jsonPath("$.[*].portOfDestination").value(hasItem(DEFAULT_PORT_OF_DESTINATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialProformaInvoice.class);
        CommercialProformaInvoice commercialProformaInvoice1 = new CommercialProformaInvoice();
        commercialProformaInvoice1.setId(1L);
        CommercialProformaInvoice commercialProformaInvoice2 = new CommercialProformaInvoice();
        commercialProformaInvoice2.setId(commercialProformaInvoice1.getId());
        assertThat(commercialProformaInvoice1).isEqualTo(commercialProformaInvoice2);
        commercialProformaInvoice2.setId(2L);
        assertThat(commercialProformaInvoice1).isNotEqualTo(commercialProformaInvoice2);
        commercialProformaInvoice1.setId(null);
        assertThat(commercialProformaInvoice1).isNotEqualTo(commercialProformaInvoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialProformaInvoiceDTO.class);
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO1 = new CommercialProformaInvoiceDTO();
        commercialProformaInvoiceDTO1.setId(1L);
        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO2 = new CommercialProformaInvoiceDTO();
        assertThat(commercialProformaInvoiceDTO1).isNotEqualTo(commercialProformaInvoiceDTO2);
        commercialProformaInvoiceDTO2.setId(commercialProformaInvoiceDTO1.getId());
        assertThat(commercialProformaInvoiceDTO1).isEqualTo(commercialProformaInvoiceDTO2);
        commercialProformaInvoiceDTO2.setId(2L);
        assertThat(commercialProformaInvoiceDTO1).isNotEqualTo(commercialProformaInvoiceDTO2);
        commercialProformaInvoiceDTO1.setId(null);
        assertThat(commercialProformaInvoiceDTO1).isNotEqualTo(commercialProformaInvoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialProformaInvoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialProformaInvoiceMapper.fromId(null)).isNull();
    }
}
