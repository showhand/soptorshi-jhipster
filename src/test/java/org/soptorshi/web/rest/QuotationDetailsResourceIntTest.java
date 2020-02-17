package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.RequisitionDetails;
import org.soptorshi.domain.Product;
import org.soptorshi.repository.QuotationDetailsRepository;
import org.soptorshi.repository.search.QuotationDetailsSearchRepository;
import org.soptorshi.service.QuotationDetailsService;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.mapper.QuotationDetailsMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.QuotationDetailsCriteria;
import org.soptorshi.service.QuotationDetailsQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

import org.soptorshi.domain.enumeration.Currency;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.domain.enumeration.PayType;
import org.soptorshi.domain.enumeration.VatStatus;
import org.soptorshi.domain.enumeration.AITStatus;
import org.soptorshi.domain.enumeration.WarrantyStatus;
/**
 * Test class for the QuotationDetailsResource REST controller.
 *
 * @see QuotationDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class QuotationDetailsResourceIntTest {

    private static final Currency DEFAULT_CURRENCY = Currency.TAKA;
    private static final Currency UPDATED_CURRENCY = Currency.DOLLAR;

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_UNIT_OF_MEASUREMENTS = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_UNIT_OF_MEASUREMENTS = UnitOfMeasurements.KG;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final PayType DEFAULT_PAY_TYPE = PayType.CASH;
    private static final PayType UPDATED_PAY_TYPE = PayType.PAY_ORDER;

    private static final BigDecimal DEFAULT_CREDIT_LIMIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_LIMIT = new BigDecimal(2);

    private static final VatStatus DEFAULT_VAT_STATUS = VatStatus.EXCLUDED;
    private static final VatStatus UPDATED_VAT_STATUS = VatStatus.INCLUDED;

    private static final BigDecimal DEFAULT_VAT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT_PERCENTAGE = new BigDecimal(2);

    private static final AITStatus DEFAULT_AIT_STATUS = AITStatus.EXCLUDED;
    private static final AITStatus UPDATED_AIT_STATUS = AITStatus.INCLUDED;

    private static final BigDecimal DEFAULT_AIT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AIT_PERCENTAGE = new BigDecimal(2);

    private static final LocalDate DEFAULT_ESTIMATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final WarrantyStatus DEFAULT_WARRANTY_STATUS = WarrantyStatus.WARRANTY;
    private static final WarrantyStatus UPDATED_WARRANTY_STATUS = WarrantyStatus.NO_WARRANTY;

    private static final String DEFAULT_LOADING_PORT = "AAAAAAAAAA";
    private static final String UPDATED_LOADING_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private QuotationDetailsRepository quotationDetailsRepository;

    @Autowired
    private QuotationDetailsMapper quotationDetailsMapper;

    @Autowired
    private QuotationDetailsService quotationDetailsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.QuotationDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuotationDetailsSearchRepository mockQuotationDetailsSearchRepository;

    @Autowired
    private QuotationDetailsQueryService quotationDetailsQueryService;

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

    private MockMvc restQuotationDetailsMockMvc;

    private QuotationDetails quotationDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuotationDetailsResource quotationDetailsResource = new QuotationDetailsResource(quotationDetailsService, quotationDetailsQueryService);
        this.restQuotationDetailsMockMvc = MockMvcBuilders.standaloneSetup(quotationDetailsResource)
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
    public static QuotationDetails createEntity(EntityManager em) {
        QuotationDetails quotationDetails = new QuotationDetails()
            .currency(DEFAULT_CURRENCY)
            .rate(DEFAULT_RATE)
            .unitOfMeasurements(DEFAULT_UNIT_OF_MEASUREMENTS)
            .quantity(DEFAULT_QUANTITY)
            .payType(DEFAULT_PAY_TYPE)
            .creditLimit(DEFAULT_CREDIT_LIMIT)
            .vatStatus(DEFAULT_VAT_STATUS)
            .vatPercentage(DEFAULT_VAT_PERCENTAGE)
            .aitStatus(DEFAULT_AIT_STATUS)
            .aitPercentage(DEFAULT_AIT_PERCENTAGE)
            .estimatedDate(DEFAULT_ESTIMATED_DATE)
            .warrantyStatus(DEFAULT_WARRANTY_STATUS)
            .loadingPort(DEFAULT_LOADING_PORT)
            .remarks(DEFAULT_REMARKS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return quotationDetails;
    }

    @Before
    public void initTest() {
        quotationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotationDetails() throws Exception {
        int databaseSizeBeforeCreate = quotationDetailsRepository.findAll().size();

        // Create the QuotationDetails
        QuotationDetailsDTO quotationDetailsDTO = quotationDetailsMapper.toDto(quotationDetails);
        restQuotationDetailsMockMvc.perform(post("/api/quotation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the QuotationDetails in the database
        List<QuotationDetails> quotationDetailsList = quotationDetailsRepository.findAll();
        assertThat(quotationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        QuotationDetails testQuotationDetails = quotationDetailsList.get(quotationDetailsList.size() - 1);
        assertThat(testQuotationDetails.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQuotationDetails.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testQuotationDetails.getUnitOfMeasurements()).isEqualTo(DEFAULT_UNIT_OF_MEASUREMENTS);
        assertThat(testQuotationDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testQuotationDetails.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testQuotationDetails.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testQuotationDetails.getVatStatus()).isEqualTo(DEFAULT_VAT_STATUS);
        assertThat(testQuotationDetails.getVatPercentage()).isEqualTo(DEFAULT_VAT_PERCENTAGE);
        assertThat(testQuotationDetails.getAitStatus()).isEqualTo(DEFAULT_AIT_STATUS);
        assertThat(testQuotationDetails.getAitPercentage()).isEqualTo(DEFAULT_AIT_PERCENTAGE);
        assertThat(testQuotationDetails.getEstimatedDate()).isEqualTo(DEFAULT_ESTIMATED_DATE);
        assertThat(testQuotationDetails.getWarrantyStatus()).isEqualTo(DEFAULT_WARRANTY_STATUS);
        assertThat(testQuotationDetails.getLoadingPort()).isEqualTo(DEFAULT_LOADING_PORT);
        assertThat(testQuotationDetails.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testQuotationDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testQuotationDetails.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the QuotationDetails in Elasticsearch
        verify(mockQuotationDetailsSearchRepository, times(1)).save(testQuotationDetails);
    }

    @Test
    @Transactional
    public void createQuotationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotationDetailsRepository.findAll().size();

        // Create the QuotationDetails with an existing ID
        quotationDetails.setId(1L);
        QuotationDetailsDTO quotationDetailsDTO = quotationDetailsMapper.toDto(quotationDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationDetailsMockMvc.perform(post("/api/quotation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuotationDetails in the database
        List<QuotationDetails> quotationDetailsList = quotationDetailsRepository.findAll();
        assertThat(quotationDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the QuotationDetails in Elasticsearch
        verify(mockQuotationDetailsSearchRepository, times(0)).save(quotationDetails);
    }

    @Test
    @Transactional
    public void getAllQuotationDetails() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].unitOfMeasurements").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENTS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vatPercentage").value(hasItem(DEFAULT_VAT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitPercentage").value(hasItem(DEFAULT_AIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getQuotationDetails() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get the quotationDetails
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details/{id}", quotationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotationDetails.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.unitOfMeasurements").value(DEFAULT_UNIT_OF_MEASUREMENTS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT.intValue()))
            .andExpect(jsonPath("$.vatStatus").value(DEFAULT_VAT_STATUS.toString()))
            .andExpect(jsonPath("$.vatPercentage").value(DEFAULT_VAT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.aitStatus").value(DEFAULT_AIT_STATUS.toString()))
            .andExpect(jsonPath("$.aitPercentage").value(DEFAULT_AIT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.estimatedDate").value(DEFAULT_ESTIMATED_DATE.toString()))
            .andExpect(jsonPath("$.warrantyStatus").value(DEFAULT_WARRANTY_STATUS.toString()))
            .andExpect(jsonPath("$.loadingPort").value(DEFAULT_LOADING_PORT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where currency equals to DEFAULT_CURRENCY
        defaultQuotationDetailsShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the quotationDetailsList where currency equals to UPDATED_CURRENCY
        defaultQuotationDetailsShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultQuotationDetailsShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the quotationDetailsList where currency equals to UPDATED_CURRENCY
        defaultQuotationDetailsShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where currency is not null
        defaultQuotationDetailsShouldBeFound("currency.specified=true");

        // Get all the quotationDetailsList where currency is null
        defaultQuotationDetailsShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where rate equals to DEFAULT_RATE
        defaultQuotationDetailsShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the quotationDetailsList where rate equals to UPDATED_RATE
        defaultQuotationDetailsShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByRateIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultQuotationDetailsShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the quotationDetailsList where rate equals to UPDATED_RATE
        defaultQuotationDetailsShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where rate is not null
        defaultQuotationDetailsShouldBeFound("rate.specified=true");

        // Get all the quotationDetailsList where rate is null
        defaultQuotationDetailsShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByUnitOfMeasurementsIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where unitOfMeasurements equals to DEFAULT_UNIT_OF_MEASUREMENTS
        defaultQuotationDetailsShouldBeFound("unitOfMeasurements.equals=" + DEFAULT_UNIT_OF_MEASUREMENTS);

        // Get all the quotationDetailsList where unitOfMeasurements equals to UPDATED_UNIT_OF_MEASUREMENTS
        defaultQuotationDetailsShouldNotBeFound("unitOfMeasurements.equals=" + UPDATED_UNIT_OF_MEASUREMENTS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByUnitOfMeasurementsIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where unitOfMeasurements in DEFAULT_UNIT_OF_MEASUREMENTS or UPDATED_UNIT_OF_MEASUREMENTS
        defaultQuotationDetailsShouldBeFound("unitOfMeasurements.in=" + DEFAULT_UNIT_OF_MEASUREMENTS + "," + UPDATED_UNIT_OF_MEASUREMENTS);

        // Get all the quotationDetailsList where unitOfMeasurements equals to UPDATED_UNIT_OF_MEASUREMENTS
        defaultQuotationDetailsShouldNotBeFound("unitOfMeasurements.in=" + UPDATED_UNIT_OF_MEASUREMENTS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByUnitOfMeasurementsIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where unitOfMeasurements is not null
        defaultQuotationDetailsShouldBeFound("unitOfMeasurements.specified=true");

        // Get all the quotationDetailsList where unitOfMeasurements is null
        defaultQuotationDetailsShouldNotBeFound("unitOfMeasurements.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where quantity equals to DEFAULT_QUANTITY
        defaultQuotationDetailsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the quotationDetailsList where quantity equals to UPDATED_QUANTITY
        defaultQuotationDetailsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultQuotationDetailsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the quotationDetailsList where quantity equals to UPDATED_QUANTITY
        defaultQuotationDetailsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where quantity is not null
        defaultQuotationDetailsShouldBeFound("quantity.specified=true");

        // Get all the quotationDetailsList where quantity is null
        defaultQuotationDetailsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where quantity greater than or equals to DEFAULT_QUANTITY
        defaultQuotationDetailsShouldBeFound("quantity.greaterOrEqualThan=" + DEFAULT_QUANTITY);

        // Get all the quotationDetailsList where quantity greater than or equals to UPDATED_QUANTITY
        defaultQuotationDetailsShouldNotBeFound("quantity.greaterOrEqualThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where quantity less than or equals to DEFAULT_QUANTITY
        defaultQuotationDetailsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the quotationDetailsList where quantity less than or equals to UPDATED_QUANTITY
        defaultQuotationDetailsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllQuotationDetailsByPayTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where payType equals to DEFAULT_PAY_TYPE
        defaultQuotationDetailsShouldBeFound("payType.equals=" + DEFAULT_PAY_TYPE);

        // Get all the quotationDetailsList where payType equals to UPDATED_PAY_TYPE
        defaultQuotationDetailsShouldNotBeFound("payType.equals=" + UPDATED_PAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByPayTypeIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where payType in DEFAULT_PAY_TYPE or UPDATED_PAY_TYPE
        defaultQuotationDetailsShouldBeFound("payType.in=" + DEFAULT_PAY_TYPE + "," + UPDATED_PAY_TYPE);

        // Get all the quotationDetailsList where payType equals to UPDATED_PAY_TYPE
        defaultQuotationDetailsShouldNotBeFound("payType.in=" + UPDATED_PAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByPayTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where payType is not null
        defaultQuotationDetailsShouldBeFound("payType.specified=true");

        // Get all the quotationDetailsList where payType is null
        defaultQuotationDetailsShouldNotBeFound("payType.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCreditLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where creditLimit equals to DEFAULT_CREDIT_LIMIT
        defaultQuotationDetailsShouldBeFound("creditLimit.equals=" + DEFAULT_CREDIT_LIMIT);

        // Get all the quotationDetailsList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultQuotationDetailsShouldNotBeFound("creditLimit.equals=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCreditLimitIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where creditLimit in DEFAULT_CREDIT_LIMIT or UPDATED_CREDIT_LIMIT
        defaultQuotationDetailsShouldBeFound("creditLimit.in=" + DEFAULT_CREDIT_LIMIT + "," + UPDATED_CREDIT_LIMIT);

        // Get all the quotationDetailsList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultQuotationDetailsShouldNotBeFound("creditLimit.in=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByCreditLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where creditLimit is not null
        defaultQuotationDetailsShouldBeFound("creditLimit.specified=true");

        // Get all the quotationDetailsList where creditLimit is null
        defaultQuotationDetailsShouldNotBeFound("creditLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatStatus equals to DEFAULT_VAT_STATUS
        defaultQuotationDetailsShouldBeFound("vatStatus.equals=" + DEFAULT_VAT_STATUS);

        // Get all the quotationDetailsList where vatStatus equals to UPDATED_VAT_STATUS
        defaultQuotationDetailsShouldNotBeFound("vatStatus.equals=" + UPDATED_VAT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatStatus in DEFAULT_VAT_STATUS or UPDATED_VAT_STATUS
        defaultQuotationDetailsShouldBeFound("vatStatus.in=" + DEFAULT_VAT_STATUS + "," + UPDATED_VAT_STATUS);

        // Get all the quotationDetailsList where vatStatus equals to UPDATED_VAT_STATUS
        defaultQuotationDetailsShouldNotBeFound("vatStatus.in=" + UPDATED_VAT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatStatus is not null
        defaultQuotationDetailsShouldBeFound("vatStatus.specified=true");

        // Get all the quotationDetailsList where vatStatus is null
        defaultQuotationDetailsShouldNotBeFound("vatStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatPercentage equals to DEFAULT_VAT_PERCENTAGE
        defaultQuotationDetailsShouldBeFound("vatPercentage.equals=" + DEFAULT_VAT_PERCENTAGE);

        // Get all the quotationDetailsList where vatPercentage equals to UPDATED_VAT_PERCENTAGE
        defaultQuotationDetailsShouldNotBeFound("vatPercentage.equals=" + UPDATED_VAT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatPercentage in DEFAULT_VAT_PERCENTAGE or UPDATED_VAT_PERCENTAGE
        defaultQuotationDetailsShouldBeFound("vatPercentage.in=" + DEFAULT_VAT_PERCENTAGE + "," + UPDATED_VAT_PERCENTAGE);

        // Get all the quotationDetailsList where vatPercentage equals to UPDATED_VAT_PERCENTAGE
        defaultQuotationDetailsShouldNotBeFound("vatPercentage.in=" + UPDATED_VAT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByVatPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where vatPercentage is not null
        defaultQuotationDetailsShouldBeFound("vatPercentage.specified=true");

        // Get all the quotationDetailsList where vatPercentage is null
        defaultQuotationDetailsShouldNotBeFound("vatPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitStatus equals to DEFAULT_AIT_STATUS
        defaultQuotationDetailsShouldBeFound("aitStatus.equals=" + DEFAULT_AIT_STATUS);

        // Get all the quotationDetailsList where aitStatus equals to UPDATED_AIT_STATUS
        defaultQuotationDetailsShouldNotBeFound("aitStatus.equals=" + UPDATED_AIT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitStatus in DEFAULT_AIT_STATUS or UPDATED_AIT_STATUS
        defaultQuotationDetailsShouldBeFound("aitStatus.in=" + DEFAULT_AIT_STATUS + "," + UPDATED_AIT_STATUS);

        // Get all the quotationDetailsList where aitStatus equals to UPDATED_AIT_STATUS
        defaultQuotationDetailsShouldNotBeFound("aitStatus.in=" + UPDATED_AIT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitStatus is not null
        defaultQuotationDetailsShouldBeFound("aitStatus.specified=true");

        // Get all the quotationDetailsList where aitStatus is null
        defaultQuotationDetailsShouldNotBeFound("aitStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitPercentage equals to DEFAULT_AIT_PERCENTAGE
        defaultQuotationDetailsShouldBeFound("aitPercentage.equals=" + DEFAULT_AIT_PERCENTAGE);

        // Get all the quotationDetailsList where aitPercentage equals to UPDATED_AIT_PERCENTAGE
        defaultQuotationDetailsShouldNotBeFound("aitPercentage.equals=" + UPDATED_AIT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitPercentage in DEFAULT_AIT_PERCENTAGE or UPDATED_AIT_PERCENTAGE
        defaultQuotationDetailsShouldBeFound("aitPercentage.in=" + DEFAULT_AIT_PERCENTAGE + "," + UPDATED_AIT_PERCENTAGE);

        // Get all the quotationDetailsList where aitPercentage equals to UPDATED_AIT_PERCENTAGE
        defaultQuotationDetailsShouldNotBeFound("aitPercentage.in=" + UPDATED_AIT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByAitPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where aitPercentage is not null
        defaultQuotationDetailsShouldBeFound("aitPercentage.specified=true");

        // Get all the quotationDetailsList where aitPercentage is null
        defaultQuotationDetailsShouldNotBeFound("aitPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByEstimatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where estimatedDate equals to DEFAULT_ESTIMATED_DATE
        defaultQuotationDetailsShouldBeFound("estimatedDate.equals=" + DEFAULT_ESTIMATED_DATE);

        // Get all the quotationDetailsList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultQuotationDetailsShouldNotBeFound("estimatedDate.equals=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByEstimatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where estimatedDate in DEFAULT_ESTIMATED_DATE or UPDATED_ESTIMATED_DATE
        defaultQuotationDetailsShouldBeFound("estimatedDate.in=" + DEFAULT_ESTIMATED_DATE + "," + UPDATED_ESTIMATED_DATE);

        // Get all the quotationDetailsList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultQuotationDetailsShouldNotBeFound("estimatedDate.in=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByEstimatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where estimatedDate is not null
        defaultQuotationDetailsShouldBeFound("estimatedDate.specified=true");

        // Get all the quotationDetailsList where estimatedDate is null
        defaultQuotationDetailsShouldNotBeFound("estimatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByEstimatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where estimatedDate greater than or equals to DEFAULT_ESTIMATED_DATE
        defaultQuotationDetailsShouldBeFound("estimatedDate.greaterOrEqualThan=" + DEFAULT_ESTIMATED_DATE);

        // Get all the quotationDetailsList where estimatedDate greater than or equals to UPDATED_ESTIMATED_DATE
        defaultQuotationDetailsShouldNotBeFound("estimatedDate.greaterOrEqualThan=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByEstimatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where estimatedDate less than or equals to DEFAULT_ESTIMATED_DATE
        defaultQuotationDetailsShouldNotBeFound("estimatedDate.lessThan=" + DEFAULT_ESTIMATED_DATE);

        // Get all the quotationDetailsList where estimatedDate less than or equals to UPDATED_ESTIMATED_DATE
        defaultQuotationDetailsShouldBeFound("estimatedDate.lessThan=" + UPDATED_ESTIMATED_DATE);
    }


    @Test
    @Transactional
    public void getAllQuotationDetailsByWarrantyStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where warrantyStatus equals to DEFAULT_WARRANTY_STATUS
        defaultQuotationDetailsShouldBeFound("warrantyStatus.equals=" + DEFAULT_WARRANTY_STATUS);

        // Get all the quotationDetailsList where warrantyStatus equals to UPDATED_WARRANTY_STATUS
        defaultQuotationDetailsShouldNotBeFound("warrantyStatus.equals=" + UPDATED_WARRANTY_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByWarrantyStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where warrantyStatus in DEFAULT_WARRANTY_STATUS or UPDATED_WARRANTY_STATUS
        defaultQuotationDetailsShouldBeFound("warrantyStatus.in=" + DEFAULT_WARRANTY_STATUS + "," + UPDATED_WARRANTY_STATUS);

        // Get all the quotationDetailsList where warrantyStatus equals to UPDATED_WARRANTY_STATUS
        defaultQuotationDetailsShouldNotBeFound("warrantyStatus.in=" + UPDATED_WARRANTY_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByWarrantyStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where warrantyStatus is not null
        defaultQuotationDetailsShouldBeFound("warrantyStatus.specified=true");

        // Get all the quotationDetailsList where warrantyStatus is null
        defaultQuotationDetailsShouldNotBeFound("warrantyStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByLoadingPortIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where loadingPort equals to DEFAULT_LOADING_PORT
        defaultQuotationDetailsShouldBeFound("loadingPort.equals=" + DEFAULT_LOADING_PORT);

        // Get all the quotationDetailsList where loadingPort equals to UPDATED_LOADING_PORT
        defaultQuotationDetailsShouldNotBeFound("loadingPort.equals=" + UPDATED_LOADING_PORT);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByLoadingPortIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where loadingPort in DEFAULT_LOADING_PORT or UPDATED_LOADING_PORT
        defaultQuotationDetailsShouldBeFound("loadingPort.in=" + DEFAULT_LOADING_PORT + "," + UPDATED_LOADING_PORT);

        // Get all the quotationDetailsList where loadingPort equals to UPDATED_LOADING_PORT
        defaultQuotationDetailsShouldNotBeFound("loadingPort.in=" + UPDATED_LOADING_PORT);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByLoadingPortIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where loadingPort is not null
        defaultQuotationDetailsShouldBeFound("loadingPort.specified=true");

        // Get all the quotationDetailsList where loadingPort is null
        defaultQuotationDetailsShouldNotBeFound("loadingPort.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultQuotationDetailsShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the quotationDetailsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationDetailsShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultQuotationDetailsShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the quotationDetailsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationDetailsShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedBy is not null
        defaultQuotationDetailsShouldBeFound("modifiedBy.specified=true");

        // Get all the quotationDetailsList where modifiedBy is null
        defaultQuotationDetailsShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultQuotationDetailsShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationDetailsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationDetailsShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultQuotationDetailsShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the quotationDetailsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationDetailsShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedOn is not null
        defaultQuotationDetailsShouldBeFound("modifiedOn.specified=true");

        // Get all the quotationDetailsList where modifiedOn is null
        defaultQuotationDetailsShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationDetailsShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationDetailsList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultQuotationDetailsShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationDetailsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        // Get all the quotationDetailsList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationDetailsShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationDetailsList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultQuotationDetailsShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllQuotationDetailsByQuotationIsEqualToSomething() throws Exception {
        // Initialize the database
        Quotation quotation = QuotationResourceIntTest.createEntity(em);
        em.persist(quotation);
        em.flush();
        quotationDetails.setQuotation(quotation);
        quotationDetailsRepository.saveAndFlush(quotationDetails);
        Long quotationId = quotation.getId();

        // Get all the quotationDetailsList where quotation equals to quotationId
        defaultQuotationDetailsShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the quotationDetailsList where quotation equals to quotationId + 1
        defaultQuotationDetailsShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }


    @Test
    @Transactional
    public void getAllQuotationDetailsByRequisitionDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        RequisitionDetails requisitionDetails = RequisitionDetailsResourceIntTest.createEntity(em);
        em.persist(requisitionDetails);
        em.flush();
        quotationDetails.setRequisitionDetails(requisitionDetails);
        quotationDetailsRepository.saveAndFlush(quotationDetails);
        Long requisitionDetailsId = requisitionDetails.getId();

        // Get all the quotationDetailsList where requisitionDetails equals to requisitionDetailsId
        defaultQuotationDetailsShouldBeFound("requisitionDetailsId.equals=" + requisitionDetailsId);

        // Get all the quotationDetailsList where requisitionDetails equals to requisitionDetailsId + 1
        defaultQuotationDetailsShouldNotBeFound("requisitionDetailsId.equals=" + (requisitionDetailsId + 1));
    }


    @Test
    @Transactional
    public void getAllQuotationDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        quotationDetails.setProduct(product);
        quotationDetailsRepository.saveAndFlush(quotationDetails);
        Long productId = product.getId();

        // Get all the quotationDetailsList where product equals to productId
        defaultQuotationDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the quotationDetailsList where product equals to productId + 1
        defaultQuotationDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQuotationDetailsShouldBeFound(String filter) throws Exception {
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].unitOfMeasurements").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENTS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vatPercentage").value(hasItem(DEFAULT_VAT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitPercentage").value(hasItem(DEFAULT_AIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQuotationDetailsShouldNotBeFound(String filter) throws Exception {
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuotationDetails() throws Exception {
        // Get the quotationDetails
        restQuotationDetailsMockMvc.perform(get("/api/quotation-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotationDetails() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        int databaseSizeBeforeUpdate = quotationDetailsRepository.findAll().size();

        // Update the quotationDetails
        QuotationDetails updatedQuotationDetails = quotationDetailsRepository.findById(quotationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedQuotationDetails are not directly saved in db
        em.detach(updatedQuotationDetails);
        updatedQuotationDetails
            .currency(UPDATED_CURRENCY)
            .rate(UPDATED_RATE)
            .unitOfMeasurements(UPDATED_UNIT_OF_MEASUREMENTS)
            .quantity(UPDATED_QUANTITY)
            .payType(UPDATED_PAY_TYPE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .vatStatus(UPDATED_VAT_STATUS)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .aitStatus(UPDATED_AIT_STATUS)
            .aitPercentage(UPDATED_AIT_PERCENTAGE)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .warrantyStatus(UPDATED_WARRANTY_STATUS)
            .loadingPort(UPDATED_LOADING_PORT)
            .remarks(UPDATED_REMARKS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        QuotationDetailsDTO quotationDetailsDTO = quotationDetailsMapper.toDto(updatedQuotationDetails);

        restQuotationDetailsMockMvc.perform(put("/api/quotation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the QuotationDetails in the database
        List<QuotationDetails> quotationDetailsList = quotationDetailsRepository.findAll();
        assertThat(quotationDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuotationDetails testQuotationDetails = quotationDetailsList.get(quotationDetailsList.size() - 1);
        assertThat(testQuotationDetails.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQuotationDetails.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testQuotationDetails.getUnitOfMeasurements()).isEqualTo(UPDATED_UNIT_OF_MEASUREMENTS);
        assertThat(testQuotationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuotationDetails.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testQuotationDetails.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testQuotationDetails.getVatStatus()).isEqualTo(UPDATED_VAT_STATUS);
        assertThat(testQuotationDetails.getVatPercentage()).isEqualTo(UPDATED_VAT_PERCENTAGE);
        assertThat(testQuotationDetails.getAitStatus()).isEqualTo(UPDATED_AIT_STATUS);
        assertThat(testQuotationDetails.getAitPercentage()).isEqualTo(UPDATED_AIT_PERCENTAGE);
        assertThat(testQuotationDetails.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testQuotationDetails.getWarrantyStatus()).isEqualTo(UPDATED_WARRANTY_STATUS);
        assertThat(testQuotationDetails.getLoadingPort()).isEqualTo(UPDATED_LOADING_PORT);
        assertThat(testQuotationDetails.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testQuotationDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testQuotationDetails.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the QuotationDetails in Elasticsearch
        verify(mockQuotationDetailsSearchRepository, times(1)).save(testQuotationDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quotationDetailsRepository.findAll().size();

        // Create the QuotationDetails
        QuotationDetailsDTO quotationDetailsDTO = quotationDetailsMapper.toDto(quotationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationDetailsMockMvc.perform(put("/api/quotation-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuotationDetails in the database
        List<QuotationDetails> quotationDetailsList = quotationDetailsRepository.findAll();
        assertThat(quotationDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the QuotationDetails in Elasticsearch
        verify(mockQuotationDetailsSearchRepository, times(0)).save(quotationDetails);
    }

    @Test
    @Transactional
    public void deleteQuotationDetails() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);

        int databaseSizeBeforeDelete = quotationDetailsRepository.findAll().size();

        // Delete the quotationDetails
        restQuotationDetailsMockMvc.perform(delete("/api/quotation-details/{id}", quotationDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuotationDetails> quotationDetailsList = quotationDetailsRepository.findAll();
        assertThat(quotationDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QuotationDetails in Elasticsearch
        verify(mockQuotationDetailsSearchRepository, times(1)).deleteById(quotationDetails.getId());
    }

    @Test
    @Transactional
    public void searchQuotationDetails() throws Exception {
        // Initialize the database
        quotationDetailsRepository.saveAndFlush(quotationDetails);
        when(mockQuotationDetailsSearchRepository.search(queryStringQuery("id:" + quotationDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(quotationDetails), PageRequest.of(0, 1), 1));
        // Search the quotationDetails
        restQuotationDetailsMockMvc.perform(get("/api/_search/quotation-details?query=id:" + quotationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
            .andExpect(jsonPath("$.[*].unitOfMeasurements").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENTS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vatPercentage").value(hasItem(DEFAULT_VAT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitPercentage").value(hasItem(DEFAULT_AIT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationDetails.class);
        QuotationDetails quotationDetails1 = new QuotationDetails();
        quotationDetails1.setId(1L);
        QuotationDetails quotationDetails2 = new QuotationDetails();
        quotationDetails2.setId(quotationDetails1.getId());
        assertThat(quotationDetails1).isEqualTo(quotationDetails2);
        quotationDetails2.setId(2L);
        assertThat(quotationDetails1).isNotEqualTo(quotationDetails2);
        quotationDetails1.setId(null);
        assertThat(quotationDetails1).isNotEqualTo(quotationDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationDetailsDTO.class);
        QuotationDetailsDTO quotationDetailsDTO1 = new QuotationDetailsDTO();
        quotationDetailsDTO1.setId(1L);
        QuotationDetailsDTO quotationDetailsDTO2 = new QuotationDetailsDTO();
        assertThat(quotationDetailsDTO1).isNotEqualTo(quotationDetailsDTO2);
        quotationDetailsDTO2.setId(quotationDetailsDTO1.getId());
        assertThat(quotationDetailsDTO1).isEqualTo(quotationDetailsDTO2);
        quotationDetailsDTO2.setId(2L);
        assertThat(quotationDetailsDTO1).isNotEqualTo(quotationDetailsDTO2);
        quotationDetailsDTO1.setId(null);
        assertThat(quotationDetailsDTO1).isNotEqualTo(quotationDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quotationDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quotationDetailsMapper.fromId(null)).isNull();
    }
}
