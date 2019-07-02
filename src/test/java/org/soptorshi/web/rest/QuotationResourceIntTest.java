package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.search.QuotationSearchRepository;
import org.soptorshi.service.QuotationService;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.mapper.QuotationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.QuotationCriteria;
import org.soptorshi.service.QuotationQueryService;

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
import org.soptorshi.domain.enumeration.PayType;
import org.soptorshi.domain.enumeration.VatStatus;
import org.soptorshi.domain.enumeration.AITStatus;
import org.soptorshi.domain.enumeration.WarrantyStatus;
import org.soptorshi.domain.enumeration.SelectionType;
/**
 * Test class for the QuotationResource REST controller.
 *
 * @see QuotationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class QuotationResourceIntTest {

    private static final String DEFAULT_QUOTATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_QUOTATION_NO = "BBBBBBBBBB";

    private static final Currency DEFAULT_CURRENCY = Currency.TAKA;
    private static final Currency UPDATED_CURRENCY = Currency.DOLLAR;

    private static final PayType DEFAULT_PAY_TYPE = PayType.CASH;
    private static final PayType UPDATED_PAY_TYPE = PayType.PAY_ORDER;

    private static final BigDecimal DEFAULT_CREDIT_LIMIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_LIMIT = new BigDecimal(2);

    private static final VatStatus DEFAULT_VAT_STATUS = VatStatus.EXCLUDED;
    private static final VatStatus UPDATED_VAT_STATUS = VatStatus.INCLUDED;

    private static final AITStatus DEFAULT_AIT_STATUS = AITStatus.EXCLUDED;
    private static final AITStatus UPDATED_AIT_STATUS = AITStatus.INCLUDED;

    private static final WarrantyStatus DEFAULT_WARRANTY_STATUS = WarrantyStatus.WARRANTY;
    private static final WarrantyStatus UPDATED_WARRANTY_STATUS = WarrantyStatus.NO_WARRANTY;

    private static final String DEFAULT_LOADING_PORT = "AAAAAAAAAA";
    private static final String UPDATED_LOADING_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final SelectionType DEFAULT_SELECTION_STATUS = SelectionType.SELECTED;
    private static final SelectionType UPDATED_SELECTION_STATUS = SelectionType.NOT_SELECTED;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationMapper quotationMapper;

    @Autowired
    private QuotationService quotationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.QuotationSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuotationSearchRepository mockQuotationSearchRepository;

    @Autowired
    private QuotationQueryService quotationQueryService;

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

    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuotationResource quotationResource = new QuotationResource(quotationService, quotationQueryService);
        this.restQuotationMockMvc = MockMvcBuilders.standaloneSetup(quotationResource)
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
    public static Quotation createEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .quotationNo(DEFAULT_QUOTATION_NO)
            .currency(DEFAULT_CURRENCY)
            .payType(DEFAULT_PAY_TYPE)
            .creditLimit(DEFAULT_CREDIT_LIMIT)
            .vatStatus(DEFAULT_VAT_STATUS)
            .aitStatus(DEFAULT_AIT_STATUS)
            .warrantyStatus(DEFAULT_WARRANTY_STATUS)
            .loadingPort(DEFAULT_LOADING_PORT)
            .remarks(DEFAULT_REMARKS)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .selectionStatus(DEFAULT_SELECTION_STATUS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return quotation;
    }

    @Before
    public void initTest() {
        quotation = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotation() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isCreated());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate + 1);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotation.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testQuotation.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testQuotation.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testQuotation.getVatStatus()).isEqualTo(DEFAULT_VAT_STATUS);
        assertThat(testQuotation.getAitStatus()).isEqualTo(DEFAULT_AIT_STATUS);
        assertThat(testQuotation.getWarrantyStatus()).isEqualTo(DEFAULT_WARRANTY_STATUS);
        assertThat(testQuotation.getLoadingPort()).isEqualTo(DEFAULT_LOADING_PORT);
        assertThat(testQuotation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testQuotation.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testQuotation.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testQuotation.getSelectionStatus()).isEqualTo(DEFAULT_SELECTION_STATUS);
        assertThat(testQuotation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testQuotation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    @Transactional
    public void createQuotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation with an existing ID
        quotation.setId(1L);
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    @Transactional
    public void getAllQuotations() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId().intValue()))
            .andExpect(jsonPath("$.quotationNo").value(DEFAULT_QUOTATION_NO.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT.intValue()))
            .andExpect(jsonPath("$.vatStatus").value(DEFAULT_VAT_STATUS.toString()))
            .andExpect(jsonPath("$.aitStatus").value(DEFAULT_AIT_STATUS.toString()))
            .andExpect(jsonPath("$.warrantyStatus").value(DEFAULT_WARRANTY_STATUS.toString()))
            .andExpect(jsonPath("$.loadingPort").value(DEFAULT_LOADING_PORT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.selectionStatus").value(DEFAULT_SELECTION_STATUS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo equals to DEFAULT_QUOTATION_NO
        defaultQuotationShouldBeFound("quotationNo.equals=" + DEFAULT_QUOTATION_NO);

        // Get all the quotationList where quotationNo equals to UPDATED_QUOTATION_NO
        defaultQuotationShouldNotBeFound("quotationNo.equals=" + UPDATED_QUOTATION_NO);
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo in DEFAULT_QUOTATION_NO or UPDATED_QUOTATION_NO
        defaultQuotationShouldBeFound("quotationNo.in=" + DEFAULT_QUOTATION_NO + "," + UPDATED_QUOTATION_NO);

        // Get all the quotationList where quotationNo equals to UPDATED_QUOTATION_NO
        defaultQuotationShouldNotBeFound("quotationNo.in=" + UPDATED_QUOTATION_NO);
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo is not null
        defaultQuotationShouldBeFound("quotationNo.specified=true");

        // Get all the quotationList where quotationNo is null
        defaultQuotationShouldNotBeFound("quotationNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where currency equals to DEFAULT_CURRENCY
        defaultQuotationShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the quotationList where currency equals to UPDATED_CURRENCY
        defaultQuotationShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultQuotationShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the quotationList where currency equals to UPDATED_CURRENCY
        defaultQuotationShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where currency is not null
        defaultQuotationShouldBeFound("currency.specified=true");

        // Get all the quotationList where currency is null
        defaultQuotationShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByPayTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where payType equals to DEFAULT_PAY_TYPE
        defaultQuotationShouldBeFound("payType.equals=" + DEFAULT_PAY_TYPE);

        // Get all the quotationList where payType equals to UPDATED_PAY_TYPE
        defaultQuotationShouldNotBeFound("payType.equals=" + UPDATED_PAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuotationsByPayTypeIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where payType in DEFAULT_PAY_TYPE or UPDATED_PAY_TYPE
        defaultQuotationShouldBeFound("payType.in=" + DEFAULT_PAY_TYPE + "," + UPDATED_PAY_TYPE);

        // Get all the quotationList where payType equals to UPDATED_PAY_TYPE
        defaultQuotationShouldNotBeFound("payType.in=" + UPDATED_PAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllQuotationsByPayTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where payType is not null
        defaultQuotationShouldBeFound("payType.specified=true");

        // Get all the quotationList where payType is null
        defaultQuotationShouldNotBeFound("payType.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByCreditLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where creditLimit equals to DEFAULT_CREDIT_LIMIT
        defaultQuotationShouldBeFound("creditLimit.equals=" + DEFAULT_CREDIT_LIMIT);

        // Get all the quotationList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultQuotationShouldNotBeFound("creditLimit.equals=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByCreditLimitIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where creditLimit in DEFAULT_CREDIT_LIMIT or UPDATED_CREDIT_LIMIT
        defaultQuotationShouldBeFound("creditLimit.in=" + DEFAULT_CREDIT_LIMIT + "," + UPDATED_CREDIT_LIMIT);

        // Get all the quotationList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultQuotationShouldNotBeFound("creditLimit.in=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByCreditLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where creditLimit is not null
        defaultQuotationShouldBeFound("creditLimit.specified=true");

        // Get all the quotationList where creditLimit is null
        defaultQuotationShouldNotBeFound("creditLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByVatStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where vatStatus equals to DEFAULT_VAT_STATUS
        defaultQuotationShouldBeFound("vatStatus.equals=" + DEFAULT_VAT_STATUS);

        // Get all the quotationList where vatStatus equals to UPDATED_VAT_STATUS
        defaultQuotationShouldNotBeFound("vatStatus.equals=" + UPDATED_VAT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByVatStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where vatStatus in DEFAULT_VAT_STATUS or UPDATED_VAT_STATUS
        defaultQuotationShouldBeFound("vatStatus.in=" + DEFAULT_VAT_STATUS + "," + UPDATED_VAT_STATUS);

        // Get all the quotationList where vatStatus equals to UPDATED_VAT_STATUS
        defaultQuotationShouldNotBeFound("vatStatus.in=" + UPDATED_VAT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByVatStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where vatStatus is not null
        defaultQuotationShouldBeFound("vatStatus.specified=true");

        // Get all the quotationList where vatStatus is null
        defaultQuotationShouldNotBeFound("vatStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByAitStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where aitStatus equals to DEFAULT_AIT_STATUS
        defaultQuotationShouldBeFound("aitStatus.equals=" + DEFAULT_AIT_STATUS);

        // Get all the quotationList where aitStatus equals to UPDATED_AIT_STATUS
        defaultQuotationShouldNotBeFound("aitStatus.equals=" + UPDATED_AIT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByAitStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where aitStatus in DEFAULT_AIT_STATUS or UPDATED_AIT_STATUS
        defaultQuotationShouldBeFound("aitStatus.in=" + DEFAULT_AIT_STATUS + "," + UPDATED_AIT_STATUS);

        // Get all the quotationList where aitStatus equals to UPDATED_AIT_STATUS
        defaultQuotationShouldNotBeFound("aitStatus.in=" + UPDATED_AIT_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByAitStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where aitStatus is not null
        defaultQuotationShouldBeFound("aitStatus.specified=true");

        // Get all the quotationList where aitStatus is null
        defaultQuotationShouldNotBeFound("aitStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByWarrantyStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where warrantyStatus equals to DEFAULT_WARRANTY_STATUS
        defaultQuotationShouldBeFound("warrantyStatus.equals=" + DEFAULT_WARRANTY_STATUS);

        // Get all the quotationList where warrantyStatus equals to UPDATED_WARRANTY_STATUS
        defaultQuotationShouldNotBeFound("warrantyStatus.equals=" + UPDATED_WARRANTY_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByWarrantyStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where warrantyStatus in DEFAULT_WARRANTY_STATUS or UPDATED_WARRANTY_STATUS
        defaultQuotationShouldBeFound("warrantyStatus.in=" + DEFAULT_WARRANTY_STATUS + "," + UPDATED_WARRANTY_STATUS);

        // Get all the quotationList where warrantyStatus equals to UPDATED_WARRANTY_STATUS
        defaultQuotationShouldNotBeFound("warrantyStatus.in=" + UPDATED_WARRANTY_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsByWarrantyStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where warrantyStatus is not null
        defaultQuotationShouldBeFound("warrantyStatus.specified=true");

        // Get all the quotationList where warrantyStatus is null
        defaultQuotationShouldNotBeFound("warrantyStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByLoadingPortIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where loadingPort equals to DEFAULT_LOADING_PORT
        defaultQuotationShouldBeFound("loadingPort.equals=" + DEFAULT_LOADING_PORT);

        // Get all the quotationList where loadingPort equals to UPDATED_LOADING_PORT
        defaultQuotationShouldNotBeFound("loadingPort.equals=" + UPDATED_LOADING_PORT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByLoadingPortIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where loadingPort in DEFAULT_LOADING_PORT or UPDATED_LOADING_PORT
        defaultQuotationShouldBeFound("loadingPort.in=" + DEFAULT_LOADING_PORT + "," + UPDATED_LOADING_PORT);

        // Get all the quotationList where loadingPort equals to UPDATED_LOADING_PORT
        defaultQuotationShouldNotBeFound("loadingPort.in=" + UPDATED_LOADING_PORT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByLoadingPortIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where loadingPort is not null
        defaultQuotationShouldBeFound("loadingPort.specified=true");

        // Get all the quotationList where loadingPort is null
        defaultQuotationShouldNotBeFound("loadingPort.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus equals to DEFAULT_SELECTION_STATUS
        defaultQuotationShouldBeFound("selectionStatus.equals=" + DEFAULT_SELECTION_STATUS);

        // Get all the quotationList where selectionStatus equals to UPDATED_SELECTION_STATUS
        defaultQuotationShouldNotBeFound("selectionStatus.equals=" + UPDATED_SELECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus in DEFAULT_SELECTION_STATUS or UPDATED_SELECTION_STATUS
        defaultQuotationShouldBeFound("selectionStatus.in=" + DEFAULT_SELECTION_STATUS + "," + UPDATED_SELECTION_STATUS);

        // Get all the quotationList where selectionStatus equals to UPDATED_SELECTION_STATUS
        defaultQuotationShouldNotBeFound("selectionStatus.in=" + UPDATED_SELECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus is not null
        defaultQuotationShouldBeFound("selectionStatus.specified=true");

        // Get all the quotationList where selectionStatus is null
        defaultQuotationShouldNotBeFound("selectionStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultQuotationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the quotationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultQuotationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the quotationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy is not null
        defaultQuotationShouldBeFound("modifiedBy.specified=true");

        // Get all the quotationList where modifiedBy is null
        defaultQuotationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the quotationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn is not null
        defaultQuotationShouldBeFound("modifiedOn.specified=true");

        // Get all the quotationList where modifiedOn is null
        defaultQuotationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllQuotationsByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        quotation.setRequisition(requisition);
        quotationRepository.saveAndFlush(quotation);
        Long requisitionId = requisition.getId();

        // Get all the quotationList where requisition equals to requisitionId
        defaultQuotationShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the quotationList where requisition equals to requisitionId + 1
        defaultQuotationShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllQuotationsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        Vendor vendor = VendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        quotation.setVendor(vendor);
        quotationRepository.saveAndFlush(quotation);
        Long vendorId = vendor.getId();

        // Get all the quotationList where vendor equals to vendorId
        defaultQuotationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the quotationList where vendor equals to vendorId + 1
        defaultQuotationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQuotationShouldBeFound(String filter) throws Exception {
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restQuotationMockMvc.perform(get("/api/quotations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQuotationShouldNotBeFound(String filter) throws Exception {
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuotationMockMvc.perform(get("/api/quotations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).get();
        // Disconnect from session so that the updates on updatedQuotation are not directly saved in db
        em.detach(updatedQuotation);
        updatedQuotation
            .quotationNo(UPDATED_QUOTATION_NO)
            .currency(UPDATED_CURRENCY)
            .payType(UPDATED_PAY_TYPE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .vatStatus(UPDATED_VAT_STATUS)
            .aitStatus(UPDATED_AIT_STATUS)
            .warrantyStatus(UPDATED_WARRANTY_STATUS)
            .loadingPort(UPDATED_LOADING_PORT)
            .remarks(UPDATED_REMARKS)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .selectionStatus(UPDATED_SELECTION_STATUS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        QuotationDTO quotationDTO = quotationMapper.toDto(updatedQuotation);

        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotation.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testQuotation.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testQuotation.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testQuotation.getVatStatus()).isEqualTo(UPDATED_VAT_STATUS);
        assertThat(testQuotation.getAitStatus()).isEqualTo(UPDATED_AIT_STATUS);
        assertThat(testQuotation.getWarrantyStatus()).isEqualTo(UPDATED_WARRANTY_STATUS);
        assertThat(testQuotation.getLoadingPort()).isEqualTo(UPDATED_LOADING_PORT);
        assertThat(testQuotation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testQuotation.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testQuotation.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testQuotation.getSelectionStatus()).isEqualTo(UPDATED_SELECTION_STATUS);
        assertThat(testQuotation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testQuotation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    @Transactional
    public void deleteQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeDelete = quotationRepository.findAll().size();

        // Delete the quotation
        restQuotationMockMvc.perform(delete("/api/quotations/{id}", quotation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).deleteById(quotation.getId());
    }

    @Test
    @Transactional
    public void searchQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);
        when(mockQuotationSearchRepository.search(queryStringQuery("id:" + quotation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(quotation), PageRequest.of(0, 1), 1));
        // Search the quotation
        restQuotationMockMvc.perform(get("/api/_search/quotations?query=id:" + quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].vatStatus").value(hasItem(DEFAULT_VAT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].aitStatus").value(hasItem(DEFAULT_AIT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].warrantyStatus").value(hasItem(DEFAULT_WARRANTY_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingPort").value(hasItem(DEFAULT_LOADING_PORT)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotation.class);
        Quotation quotation1 = new Quotation();
        quotation1.setId(1L);
        Quotation quotation2 = new Quotation();
        quotation2.setId(quotation1.getId());
        assertThat(quotation1).isEqualTo(quotation2);
        quotation2.setId(2L);
        assertThat(quotation1).isNotEqualTo(quotation2);
        quotation1.setId(null);
        assertThat(quotation1).isNotEqualTo(quotation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationDTO.class);
        QuotationDTO quotationDTO1 = new QuotationDTO();
        quotationDTO1.setId(1L);
        QuotationDTO quotationDTO2 = new QuotationDTO();
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
        quotationDTO2.setId(quotationDTO1.getId());
        assertThat(quotationDTO1).isEqualTo(quotationDTO2);
        quotationDTO2.setId(2L);
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
        quotationDTO1.setId(null);
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quotationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quotationMapper.fromId(null)).isNull();
    }
}
