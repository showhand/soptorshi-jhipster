package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialInvoice;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.CommercialProformaInvoice;
import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.repository.CommercialInvoiceRepository;
import org.soptorshi.repository.search.CommercialInvoiceSearchRepository;
import org.soptorshi.service.CommercialInvoiceService;
import org.soptorshi.service.dto.CommercialInvoiceDTO;
import org.soptorshi.service.mapper.CommercialInvoiceMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialInvoiceCriteria;
import org.soptorshi.service.CommercialInvoiceQueryService;

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
 * Test class for the CommercialInvoiceResource REST controller.
 *
 * @see CommercialInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialInvoiceResourceIntTest {

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_OF_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_OF_PAYMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONSIGNED_TO = "AAAAAAAAAA";
    private static final String UPDATED_CONSIGNED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_LOADING = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_LOADING = "BBBBBBBBBB";

    private static final String DEFAULT_PORT_OF_DISCHARGE = "AAAAAAAAAA";
    private static final String UPDATED_PORT_OF_DISCHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO = "AAAAAAAAAA";
    private static final String UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialInvoiceRepository commercialInvoiceRepository;

    @Autowired
    private CommercialInvoiceMapper commercialInvoiceMapper;

    @Autowired
    private CommercialInvoiceService commercialInvoiceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialInvoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialInvoiceSearchRepository mockCommercialInvoiceSearchRepository;

    @Autowired
    private CommercialInvoiceQueryService commercialInvoiceQueryService;

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

    private MockMvc restCommercialInvoiceMockMvc;

    private CommercialInvoice commercialInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialInvoiceResource commercialInvoiceResource = new CommercialInvoiceResource(commercialInvoiceService, commercialInvoiceQueryService);
        this.restCommercialInvoiceMockMvc = MockMvcBuilders.standaloneSetup(commercialInvoiceResource)
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
    public static CommercialInvoice createEntity(EntityManager em) {
        CommercialInvoice commercialInvoice = new CommercialInvoice()
            .invoiceNo(DEFAULT_INVOICE_NO)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .termsOfPayment(DEFAULT_TERMS_OF_PAYMENT)
            .consignedTo(DEFAULT_CONSIGNED_TO)
            .portOfLoading(DEFAULT_PORT_OF_LOADING)
            .portOfDischarge(DEFAULT_PORT_OF_DISCHARGE)
            .exportRegistrationCertificateNo(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialInvoice;
    }

    @Before
    public void initTest() {
        commercialInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialInvoice() throws Exception {
        int databaseSizeBeforeCreate = commercialInvoiceRepository.findAll().size();

        // Create the CommercialInvoice
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);
        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialInvoice in the database
        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialInvoice testCommercialInvoice = commercialInvoiceList.get(commercialInvoiceList.size() - 1);
        assertThat(testCommercialInvoice.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testCommercialInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testCommercialInvoice.getTermsOfPayment()).isEqualTo(DEFAULT_TERMS_OF_PAYMENT);
        assertThat(testCommercialInvoice.getConsignedTo()).isEqualTo(DEFAULT_CONSIGNED_TO);
        assertThat(testCommercialInvoice.getPortOfLoading()).isEqualTo(DEFAULT_PORT_OF_LOADING);
        assertThat(testCommercialInvoice.getPortOfDischarge()).isEqualTo(DEFAULT_PORT_OF_DISCHARGE);
        assertThat(testCommercialInvoice.getExportRegistrationCertificateNo()).isEqualTo(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO);
        assertThat(testCommercialInvoice.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialInvoice.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialInvoice.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialInvoice.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialInvoice in Elasticsearch
        verify(mockCommercialInvoiceSearchRepository, times(1)).save(testCommercialInvoice);
    }

    @Test
    @Transactional
    public void createCommercialInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialInvoiceRepository.findAll().size();

        // Create the CommercialInvoice with an existing ID
        commercialInvoice.setId(1L);
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialInvoice in the database
        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialInvoice in Elasticsearch
        verify(mockCommercialInvoiceSearchRepository, times(0)).save(commercialInvoice);
    }

    @Test
    @Transactional
    public void checkInvoiceNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setInvoiceNo(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setInvoiceDate(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTermsOfPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setTermsOfPayment(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsignedToIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setConsignedTo(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortOfLoadingIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setPortOfLoading(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortOfDischargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setPortOfDischarge(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExportRegistrationCertificateNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialInvoiceRepository.findAll().size();
        // set the field null
        commercialInvoice.setExportRegistrationCertificateNo(null);

        // Create the CommercialInvoice, which fails.
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        restCommercialInvoiceMockMvc.perform(post("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoices() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].termsOfPayment").value(hasItem(DEFAULT_TERMS_OF_PAYMENT.toString())))
            .andExpect(jsonPath("$.[*].consignedTo").value(hasItem(DEFAULT_CONSIGNED_TO.toString())))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING.toString())))
            .andExpect(jsonPath("$.[*].portOfDischarge").value(hasItem(DEFAULT_PORT_OF_DISCHARGE.toString())))
            .andExpect(jsonPath("$.[*].exportRegistrationCertificateNo").value(hasItem(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialInvoice() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get the commercialInvoice
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices/{id}", commercialInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.termsOfPayment").value(DEFAULT_TERMS_OF_PAYMENT.toString()))
            .andExpect(jsonPath("$.consignedTo").value(DEFAULT_CONSIGNED_TO.toString()))
            .andExpect(jsonPath("$.portOfLoading").value(DEFAULT_PORT_OF_LOADING.toString()))
            .andExpect(jsonPath("$.portOfDischarge").value(DEFAULT_PORT_OF_DISCHARGE.toString()))
            .andExpect(jsonPath("$.exportRegistrationCertificateNo").value(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceNo equals to DEFAULT_INVOICE_NO
        defaultCommercialInvoiceShouldBeFound("invoiceNo.equals=" + DEFAULT_INVOICE_NO);

        // Get all the commercialInvoiceList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultCommercialInvoiceShouldNotBeFound("invoiceNo.equals=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceNo in DEFAULT_INVOICE_NO or UPDATED_INVOICE_NO
        defaultCommercialInvoiceShouldBeFound("invoiceNo.in=" + DEFAULT_INVOICE_NO + "," + UPDATED_INVOICE_NO);

        // Get all the commercialInvoiceList where invoiceNo equals to UPDATED_INVOICE_NO
        defaultCommercialInvoiceShouldNotBeFound("invoiceNo.in=" + UPDATED_INVOICE_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceNo is not null
        defaultCommercialInvoiceShouldBeFound("invoiceNo.specified=true");

        // Get all the commercialInvoiceList where invoiceNo is null
        defaultCommercialInvoiceShouldNotBeFound("invoiceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultCommercialInvoiceShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the commercialInvoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultCommercialInvoiceShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultCommercialInvoiceShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the commercialInvoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultCommercialInvoiceShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where invoiceDate is not null
        defaultCommercialInvoiceShouldBeFound("invoiceDate.specified=true");

        // Get all the commercialInvoiceList where invoiceDate is null
        defaultCommercialInvoiceShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByTermsOfPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where termsOfPayment equals to DEFAULT_TERMS_OF_PAYMENT
        defaultCommercialInvoiceShouldBeFound("termsOfPayment.equals=" + DEFAULT_TERMS_OF_PAYMENT);

        // Get all the commercialInvoiceList where termsOfPayment equals to UPDATED_TERMS_OF_PAYMENT
        defaultCommercialInvoiceShouldNotBeFound("termsOfPayment.equals=" + UPDATED_TERMS_OF_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByTermsOfPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where termsOfPayment in DEFAULT_TERMS_OF_PAYMENT or UPDATED_TERMS_OF_PAYMENT
        defaultCommercialInvoiceShouldBeFound("termsOfPayment.in=" + DEFAULT_TERMS_OF_PAYMENT + "," + UPDATED_TERMS_OF_PAYMENT);

        // Get all the commercialInvoiceList where termsOfPayment equals to UPDATED_TERMS_OF_PAYMENT
        defaultCommercialInvoiceShouldNotBeFound("termsOfPayment.in=" + UPDATED_TERMS_OF_PAYMENT);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByTermsOfPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where termsOfPayment is not null
        defaultCommercialInvoiceShouldBeFound("termsOfPayment.specified=true");

        // Get all the commercialInvoiceList where termsOfPayment is null
        defaultCommercialInvoiceShouldNotBeFound("termsOfPayment.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByConsignedToIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where consignedTo equals to DEFAULT_CONSIGNED_TO
        defaultCommercialInvoiceShouldBeFound("consignedTo.equals=" + DEFAULT_CONSIGNED_TO);

        // Get all the commercialInvoiceList where consignedTo equals to UPDATED_CONSIGNED_TO
        defaultCommercialInvoiceShouldNotBeFound("consignedTo.equals=" + UPDATED_CONSIGNED_TO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByConsignedToIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where consignedTo in DEFAULT_CONSIGNED_TO or UPDATED_CONSIGNED_TO
        defaultCommercialInvoiceShouldBeFound("consignedTo.in=" + DEFAULT_CONSIGNED_TO + "," + UPDATED_CONSIGNED_TO);

        // Get all the commercialInvoiceList where consignedTo equals to UPDATED_CONSIGNED_TO
        defaultCommercialInvoiceShouldNotBeFound("consignedTo.in=" + UPDATED_CONSIGNED_TO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByConsignedToIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where consignedTo is not null
        defaultCommercialInvoiceShouldBeFound("consignedTo.specified=true");

        // Get all the commercialInvoiceList where consignedTo is null
        defaultCommercialInvoiceShouldNotBeFound("consignedTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfLoadingIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfLoading equals to DEFAULT_PORT_OF_LOADING
        defaultCommercialInvoiceShouldBeFound("portOfLoading.equals=" + DEFAULT_PORT_OF_LOADING);

        // Get all the commercialInvoiceList where portOfLoading equals to UPDATED_PORT_OF_LOADING
        defaultCommercialInvoiceShouldNotBeFound("portOfLoading.equals=" + UPDATED_PORT_OF_LOADING);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfLoadingIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfLoading in DEFAULT_PORT_OF_LOADING or UPDATED_PORT_OF_LOADING
        defaultCommercialInvoiceShouldBeFound("portOfLoading.in=" + DEFAULT_PORT_OF_LOADING + "," + UPDATED_PORT_OF_LOADING);

        // Get all the commercialInvoiceList where portOfLoading equals to UPDATED_PORT_OF_LOADING
        defaultCommercialInvoiceShouldNotBeFound("portOfLoading.in=" + UPDATED_PORT_OF_LOADING);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfLoadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfLoading is not null
        defaultCommercialInvoiceShouldBeFound("portOfLoading.specified=true");

        // Get all the commercialInvoiceList where portOfLoading is null
        defaultCommercialInvoiceShouldNotBeFound("portOfLoading.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfDischargeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfDischarge equals to DEFAULT_PORT_OF_DISCHARGE
        defaultCommercialInvoiceShouldBeFound("portOfDischarge.equals=" + DEFAULT_PORT_OF_DISCHARGE);

        // Get all the commercialInvoiceList where portOfDischarge equals to UPDATED_PORT_OF_DISCHARGE
        defaultCommercialInvoiceShouldNotBeFound("portOfDischarge.equals=" + UPDATED_PORT_OF_DISCHARGE);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfDischargeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfDischarge in DEFAULT_PORT_OF_DISCHARGE or UPDATED_PORT_OF_DISCHARGE
        defaultCommercialInvoiceShouldBeFound("portOfDischarge.in=" + DEFAULT_PORT_OF_DISCHARGE + "," + UPDATED_PORT_OF_DISCHARGE);

        // Get all the commercialInvoiceList where portOfDischarge equals to UPDATED_PORT_OF_DISCHARGE
        defaultCommercialInvoiceShouldNotBeFound("portOfDischarge.in=" + UPDATED_PORT_OF_DISCHARGE);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByPortOfDischargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where portOfDischarge is not null
        defaultCommercialInvoiceShouldBeFound("portOfDischarge.specified=true");

        // Get all the commercialInvoiceList where portOfDischarge is null
        defaultCommercialInvoiceShouldNotBeFound("portOfDischarge.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByExportRegistrationCertificateNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo equals to DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO
        defaultCommercialInvoiceShouldBeFound("exportRegistrationCertificateNo.equals=" + DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO);

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo equals to UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO
        defaultCommercialInvoiceShouldNotBeFound("exportRegistrationCertificateNo.equals=" + UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByExportRegistrationCertificateNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo in DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO or UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO
        defaultCommercialInvoiceShouldBeFound("exportRegistrationCertificateNo.in=" + DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO + "," + UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO);

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo equals to UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO
        defaultCommercialInvoiceShouldNotBeFound("exportRegistrationCertificateNo.in=" + UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByExportRegistrationCertificateNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo is not null
        defaultCommercialInvoiceShouldBeFound("exportRegistrationCertificateNo.specified=true");

        // Get all the commercialInvoiceList where exportRegistrationCertificateNo is null
        defaultCommercialInvoiceShouldNotBeFound("exportRegistrationCertificateNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialInvoiceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialInvoiceList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialInvoiceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialInvoiceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialInvoiceList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialInvoiceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdBy is not null
        defaultCommercialInvoiceShouldBeFound("createdBy.specified=true");

        // Get all the commercialInvoiceList where createdBy is null
        defaultCommercialInvoiceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialInvoiceShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialInvoiceList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialInvoiceShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialInvoiceShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialInvoiceList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialInvoiceShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdOn is not null
        defaultCommercialInvoiceShouldBeFound("createdOn.specified=true");

        // Get all the commercialInvoiceList where createdOn is null
        defaultCommercialInvoiceShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialInvoiceShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialInvoiceList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialInvoiceShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialInvoiceShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialInvoiceList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialInvoiceShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialInvoiceShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialInvoiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialInvoiceShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialInvoiceShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialInvoiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialInvoiceShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedBy is not null
        defaultCommercialInvoiceShouldBeFound("updatedBy.specified=true");

        // Get all the commercialInvoiceList where updatedBy is null
        defaultCommercialInvoiceShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialInvoiceShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialInvoiceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialInvoiceShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialInvoiceShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialInvoiceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialInvoiceShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedOn is not null
        defaultCommercialInvoiceShouldBeFound("updatedOn.specified=true");

        // Get all the commercialInvoiceList where updatedOn is null
        defaultCommercialInvoiceShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialInvoiceShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialInvoiceList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialInvoiceShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialInvoicesByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        // Get all the commercialInvoiceList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialInvoiceShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialInvoiceList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialInvoiceShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialInvoicesByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialInvoice.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialInvoiceList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialInvoiceShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialInvoiceList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialInvoiceShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }


    @Test
    @Transactional
    public void getAllCommercialInvoicesByCommercialProformaInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialProformaInvoice commercialProformaInvoice = CommercialProformaInvoiceResourceIntTest.createEntity(em);
        em.persist(commercialProformaInvoice);
        em.flush();
        commercialInvoice.setCommercialProformaInvoice(commercialProformaInvoice);
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);
        Long commercialProformaInvoiceId = commercialProformaInvoice.getId();

        // Get all the commercialInvoiceList where commercialProformaInvoice equals to commercialProformaInvoiceId
        defaultCommercialInvoiceShouldBeFound("commercialProformaInvoiceId.equals=" + commercialProformaInvoiceId);

        // Get all the commercialInvoiceList where commercialProformaInvoice equals to commercialProformaInvoiceId + 1
        defaultCommercialInvoiceShouldNotBeFound("commercialProformaInvoiceId.equals=" + (commercialProformaInvoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllCommercialInvoicesByCommercialPackagingIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPackaging commercialPackaging = CommercialPackagingResourceIntTest.createEntity(em);
        em.persist(commercialPackaging);
        em.flush();
        commercialInvoice.setCommercialPackaging(commercialPackaging);
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);
        Long commercialPackagingId = commercialPackaging.getId();

        // Get all the commercialInvoiceList where commercialPackaging equals to commercialPackagingId
        defaultCommercialInvoiceShouldBeFound("commercialPackagingId.equals=" + commercialPackagingId);

        // Get all the commercialInvoiceList where commercialPackaging equals to commercialPackagingId + 1
        defaultCommercialInvoiceShouldNotBeFound("commercialPackagingId.equals=" + (commercialPackagingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialInvoiceShouldBeFound(String filter) throws Exception {
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE)))
            .andExpect(jsonPath("$.[*].termsOfPayment").value(hasItem(DEFAULT_TERMS_OF_PAYMENT)))
            .andExpect(jsonPath("$.[*].consignedTo").value(hasItem(DEFAULT_CONSIGNED_TO)))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING)))
            .andExpect(jsonPath("$.[*].portOfDischarge").value(hasItem(DEFAULT_PORT_OF_DISCHARGE)))
            .andExpect(jsonPath("$.[*].exportRegistrationCertificateNo").value(hasItem(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialInvoiceShouldNotBeFound(String filter) throws Exception {
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialInvoice() throws Exception {
        // Get the commercialInvoice
        restCommercialInvoiceMockMvc.perform(get("/api/commercial-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialInvoice() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        int databaseSizeBeforeUpdate = commercialInvoiceRepository.findAll().size();

        // Update the commercialInvoice
        CommercialInvoice updatedCommercialInvoice = commercialInvoiceRepository.findById(commercialInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialInvoice are not directly saved in db
        em.detach(updatedCommercialInvoice);
        updatedCommercialInvoice
            .invoiceNo(UPDATED_INVOICE_NO)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .termsOfPayment(UPDATED_TERMS_OF_PAYMENT)
            .consignedTo(UPDATED_CONSIGNED_TO)
            .portOfLoading(UPDATED_PORT_OF_LOADING)
            .portOfDischarge(UPDATED_PORT_OF_DISCHARGE)
            .exportRegistrationCertificateNo(UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(updatedCommercialInvoice);

        restCommercialInvoiceMockMvc.perform(put("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialInvoice in the database
        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeUpdate);
        CommercialInvoice testCommercialInvoice = commercialInvoiceList.get(commercialInvoiceList.size() - 1);
        assertThat(testCommercialInvoice.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testCommercialInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testCommercialInvoice.getTermsOfPayment()).isEqualTo(UPDATED_TERMS_OF_PAYMENT);
        assertThat(testCommercialInvoice.getConsignedTo()).isEqualTo(UPDATED_CONSIGNED_TO);
        assertThat(testCommercialInvoice.getPortOfLoading()).isEqualTo(UPDATED_PORT_OF_LOADING);
        assertThat(testCommercialInvoice.getPortOfDischarge()).isEqualTo(UPDATED_PORT_OF_DISCHARGE);
        assertThat(testCommercialInvoice.getExportRegistrationCertificateNo()).isEqualTo(UPDATED_EXPORT_REGISTRATION_CERTIFICATE_NO);
        assertThat(testCommercialInvoice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialInvoice.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialInvoice.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialInvoice.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialInvoice in Elasticsearch
        verify(mockCommercialInvoiceSearchRepository, times(1)).save(testCommercialInvoice);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialInvoice() throws Exception {
        int databaseSizeBeforeUpdate = commercialInvoiceRepository.findAll().size();

        // Create the CommercialInvoice
        CommercialInvoiceDTO commercialInvoiceDTO = commercialInvoiceMapper.toDto(commercialInvoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialInvoiceMockMvc.perform(put("/api/commercial-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialInvoice in the database
        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialInvoice in Elasticsearch
        verify(mockCommercialInvoiceSearchRepository, times(0)).save(commercialInvoice);
    }

    @Test
    @Transactional
    public void deleteCommercialInvoice() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);

        int databaseSizeBeforeDelete = commercialInvoiceRepository.findAll().size();

        // Delete the commercialInvoice
        restCommercialInvoiceMockMvc.perform(delete("/api/commercial-invoices/{id}", commercialInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialInvoice> commercialInvoiceList = commercialInvoiceRepository.findAll();
        assertThat(commercialInvoiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialInvoice in Elasticsearch
        verify(mockCommercialInvoiceSearchRepository, times(1)).deleteById(commercialInvoice.getId());
    }

    @Test
    @Transactional
    public void searchCommercialInvoice() throws Exception {
        // Initialize the database
        commercialInvoiceRepository.saveAndFlush(commercialInvoice);
        when(mockCommercialInvoiceSearchRepository.search(queryStringQuery("id:" + commercialInvoice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialInvoice), PageRequest.of(0, 1), 1));
        // Search the commercialInvoice
        restCommercialInvoiceMockMvc.perform(get("/api/_search/commercial-invoices?query=id:" + commercialInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE)))
            .andExpect(jsonPath("$.[*].termsOfPayment").value(hasItem(DEFAULT_TERMS_OF_PAYMENT)))
            .andExpect(jsonPath("$.[*].consignedTo").value(hasItem(DEFAULT_CONSIGNED_TO)))
            .andExpect(jsonPath("$.[*].portOfLoading").value(hasItem(DEFAULT_PORT_OF_LOADING)))
            .andExpect(jsonPath("$.[*].portOfDischarge").value(hasItem(DEFAULT_PORT_OF_DISCHARGE)))
            .andExpect(jsonPath("$.[*].exportRegistrationCertificateNo").value(hasItem(DEFAULT_EXPORT_REGISTRATION_CERTIFICATE_NO)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialInvoice.class);
        CommercialInvoice commercialInvoice1 = new CommercialInvoice();
        commercialInvoice1.setId(1L);
        CommercialInvoice commercialInvoice2 = new CommercialInvoice();
        commercialInvoice2.setId(commercialInvoice1.getId());
        assertThat(commercialInvoice1).isEqualTo(commercialInvoice2);
        commercialInvoice2.setId(2L);
        assertThat(commercialInvoice1).isNotEqualTo(commercialInvoice2);
        commercialInvoice1.setId(null);
        assertThat(commercialInvoice1).isNotEqualTo(commercialInvoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialInvoiceDTO.class);
        CommercialInvoiceDTO commercialInvoiceDTO1 = new CommercialInvoiceDTO();
        commercialInvoiceDTO1.setId(1L);
        CommercialInvoiceDTO commercialInvoiceDTO2 = new CommercialInvoiceDTO();
        assertThat(commercialInvoiceDTO1).isNotEqualTo(commercialInvoiceDTO2);
        commercialInvoiceDTO2.setId(commercialInvoiceDTO1.getId());
        assertThat(commercialInvoiceDTO1).isEqualTo(commercialInvoiceDTO2);
        commercialInvoiceDTO2.setId(2L);
        assertThat(commercialInvoiceDTO1).isNotEqualTo(commercialInvoiceDTO2);
        commercialInvoiceDTO1.setId(null);
        assertThat(commercialInvoiceDTO1).isNotEqualTo(commercialInvoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialInvoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialInvoiceMapper.fromId(null)).isNull();
    }
}
