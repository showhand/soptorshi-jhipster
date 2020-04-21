package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.JournalVoucher;
import org.soptorshi.domain.Currency;
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.service.JournalVoucherService;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.JournalVoucherMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.JournalVoucherCriteria;
import org.soptorshi.service.JournalVoucherQueryService;

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

import org.soptorshi.domain.enumeration.VoucherType;
import org.soptorshi.domain.enumeration.VoucherReferenceType;
import org.soptorshi.domain.enumeration.ApplicationType;
/**
 * Test class for the JournalVoucherResource REST controller.
 *
 * @see JournalVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class JournalVoucherResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VOUCHER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VOUCHER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_POST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final VoucherType DEFAULT_TYPE = VoucherType.SELLING;
    private static final VoucherType UPDATED_TYPE = VoucherType.BUYING;

    private static final BigDecimal DEFAULT_CONVERSION_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_CONVERSION_FACTOR = new BigDecimal(2);

    private static final VoucherReferenceType DEFAULT_REFERENCE = VoucherReferenceType.PAYROLL;
    private static final VoucherReferenceType UPDATED_REFERENCE = VoucherReferenceType.PURCHASE_ORDER;

    private static final ApplicationType DEFAULT_APPLICATION_TYPE = ApplicationType.REQUISITION;
    private static final ApplicationType UPDATED_APPLICATION_TYPE = ApplicationType.PURCHASE_ORDER;

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final Long DEFAULT_REFERENCE_ID = 1L;
    private static final Long UPDATED_REFERENCE_ID = 2L;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private JournalVoucherRepository journalVoucherRepository;

    @Autowired
    private JournalVoucherMapper journalVoucherMapper;

    @Autowired
    private JournalVoucherService journalVoucherService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.JournalVoucherSearchRepositoryMockConfiguration
     */
    @Autowired
    private JournalVoucherSearchRepository mockJournalVoucherSearchRepository;

    @Autowired
    private JournalVoucherQueryService journalVoucherQueryService;

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

    private MockMvc restJournalVoucherMockMvc;

    private JournalVoucher journalVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JournalVoucherResource journalVoucherResource = new JournalVoucherResource(journalVoucherService, journalVoucherQueryService);
        this.restJournalVoucherMockMvc = MockMvcBuilders.standaloneSetup(journalVoucherResource)
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
    public static JournalVoucher createEntity(EntityManager em) {
        JournalVoucher journalVoucher = new JournalVoucher()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .postDate(DEFAULT_POST_DATE)
            .type(DEFAULT_TYPE)
            .conversionFactor(DEFAULT_CONVERSION_FACTOR)
            .reference(DEFAULT_REFERENCE)
            .applicationType(DEFAULT_APPLICATION_TYPE)
            .applicationId(DEFAULT_APPLICATION_ID)
            .referenceId(DEFAULT_REFERENCE_ID)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return journalVoucher;
    }

    @Before
    public void initTest() {
        journalVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournalVoucher() throws Exception {
        int databaseSizeBeforeCreate = journalVoucherRepository.findAll().size();

        // Create the JournalVoucher
        JournalVoucherDTO journalVoucherDTO = journalVoucherMapper.toDto(journalVoucher);
        restJournalVoucherMockMvc.perform(post("/api/journal-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the JournalVoucher in the database
        List<JournalVoucher> journalVoucherList = journalVoucherRepository.findAll();
        assertThat(journalVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        JournalVoucher testJournalVoucher = journalVoucherList.get(journalVoucherList.size() - 1);
        assertThat(testJournalVoucher.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testJournalVoucher.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testJournalVoucher.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testJournalVoucher.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJournalVoucher.getConversionFactor()).isEqualTo(DEFAULT_CONVERSION_FACTOR);
        assertThat(testJournalVoucher.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testJournalVoucher.getApplicationType()).isEqualTo(DEFAULT_APPLICATION_TYPE);
        assertThat(testJournalVoucher.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testJournalVoucher.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
        assertThat(testJournalVoucher.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testJournalVoucher.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the JournalVoucher in Elasticsearch
        verify(mockJournalVoucherSearchRepository, times(1)).save(testJournalVoucher);
    }

    @Test
    @Transactional
    public void createJournalVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalVoucherRepository.findAll().size();

        // Create the JournalVoucher with an existing ID
        journalVoucher.setId(1L);
        JournalVoucherDTO journalVoucherDTO = journalVoucherMapper.toDto(journalVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalVoucherMockMvc.perform(post("/api/journal-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JournalVoucher in the database
        List<JournalVoucher> journalVoucherList = journalVoucherRepository.findAll();
        assertThat(journalVoucherList).hasSize(databaseSizeBeforeCreate);

        // Validate the JournalVoucher in Elasticsearch
        verify(mockJournalVoucherSearchRepository, times(0)).save(journalVoucher);
    }

    @Test
    @Transactional
    public void getAllJournalVouchers() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].conversionFactor").value(hasItem(DEFAULT_CONVERSION_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getJournalVoucher() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get the journalVoucher
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers/{id}", journalVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journalVoucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.conversionFactor").value(DEFAULT_CONVERSION_FACTOR.intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.applicationType").value(DEFAULT_APPLICATION_TYPE.toString()))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultJournalVoucherShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the journalVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultJournalVoucherShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultJournalVoucherShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the journalVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultJournalVoucherShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherNo is not null
        defaultJournalVoucherShouldBeFound("voucherNo.specified=true");

        // Get all the journalVoucherList where voucherNo is null
        defaultJournalVoucherShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultJournalVoucherShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the journalVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultJournalVoucherShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultJournalVoucherShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the journalVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultJournalVoucherShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherDate is not null
        defaultJournalVoucherShouldBeFound("voucherDate.specified=true");

        // Get all the journalVoucherList where voucherDate is null
        defaultJournalVoucherShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherDate greater than or equals to DEFAULT_VOUCHER_DATE
        defaultJournalVoucherShouldBeFound("voucherDate.greaterOrEqualThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the journalVoucherList where voucherDate greater than or equals to UPDATED_VOUCHER_DATE
        defaultJournalVoucherShouldNotBeFound("voucherDate.greaterOrEqualThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where voucherDate less than or equals to DEFAULT_VOUCHER_DATE
        defaultJournalVoucherShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the journalVoucherList where voucherDate less than or equals to UPDATED_VOUCHER_DATE
        defaultJournalVoucherShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllJournalVouchersByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where postDate equals to DEFAULT_POST_DATE
        defaultJournalVoucherShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the journalVoucherList where postDate equals to UPDATED_POST_DATE
        defaultJournalVoucherShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultJournalVoucherShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the journalVoucherList where postDate equals to UPDATED_POST_DATE
        defaultJournalVoucherShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where postDate is not null
        defaultJournalVoucherShouldBeFound("postDate.specified=true");

        // Get all the journalVoucherList where postDate is null
        defaultJournalVoucherShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByPostDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where postDate greater than or equals to DEFAULT_POST_DATE
        defaultJournalVoucherShouldBeFound("postDate.greaterOrEqualThan=" + DEFAULT_POST_DATE);

        // Get all the journalVoucherList where postDate greater than or equals to UPDATED_POST_DATE
        defaultJournalVoucherShouldNotBeFound("postDate.greaterOrEqualThan=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByPostDateIsLessThanSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where postDate less than or equals to DEFAULT_POST_DATE
        defaultJournalVoucherShouldNotBeFound("postDate.lessThan=" + DEFAULT_POST_DATE);

        // Get all the journalVoucherList where postDate less than or equals to UPDATED_POST_DATE
        defaultJournalVoucherShouldBeFound("postDate.lessThan=" + UPDATED_POST_DATE);
    }


    @Test
    @Transactional
    public void getAllJournalVouchersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where type equals to DEFAULT_TYPE
        defaultJournalVoucherShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the journalVoucherList where type equals to UPDATED_TYPE
        defaultJournalVoucherShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultJournalVoucherShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the journalVoucherList where type equals to UPDATED_TYPE
        defaultJournalVoucherShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where type is not null
        defaultJournalVoucherShouldBeFound("type.specified=true");

        // Get all the journalVoucherList where type is null
        defaultJournalVoucherShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByConversionFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where conversionFactor equals to DEFAULT_CONVERSION_FACTOR
        defaultJournalVoucherShouldBeFound("conversionFactor.equals=" + DEFAULT_CONVERSION_FACTOR);

        // Get all the journalVoucherList where conversionFactor equals to UPDATED_CONVERSION_FACTOR
        defaultJournalVoucherShouldNotBeFound("conversionFactor.equals=" + UPDATED_CONVERSION_FACTOR);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByConversionFactorIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where conversionFactor in DEFAULT_CONVERSION_FACTOR or UPDATED_CONVERSION_FACTOR
        defaultJournalVoucherShouldBeFound("conversionFactor.in=" + DEFAULT_CONVERSION_FACTOR + "," + UPDATED_CONVERSION_FACTOR);

        // Get all the journalVoucherList where conversionFactor equals to UPDATED_CONVERSION_FACTOR
        defaultJournalVoucherShouldNotBeFound("conversionFactor.in=" + UPDATED_CONVERSION_FACTOR);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByConversionFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where conversionFactor is not null
        defaultJournalVoucherShouldBeFound("conversionFactor.specified=true");

        // Get all the journalVoucherList where conversionFactor is null
        defaultJournalVoucherShouldNotBeFound("conversionFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where reference equals to DEFAULT_REFERENCE
        defaultJournalVoucherShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the journalVoucherList where reference equals to UPDATED_REFERENCE
        defaultJournalVoucherShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultJournalVoucherShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the journalVoucherList where reference equals to UPDATED_REFERENCE
        defaultJournalVoucherShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where reference is not null
        defaultJournalVoucherShouldBeFound("reference.specified=true");

        // Get all the journalVoucherList where reference is null
        defaultJournalVoucherShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationType equals to DEFAULT_APPLICATION_TYPE
        defaultJournalVoucherShouldBeFound("applicationType.equals=" + DEFAULT_APPLICATION_TYPE);

        // Get all the journalVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultJournalVoucherShouldNotBeFound("applicationType.equals=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationType in DEFAULT_APPLICATION_TYPE or UPDATED_APPLICATION_TYPE
        defaultJournalVoucherShouldBeFound("applicationType.in=" + DEFAULT_APPLICATION_TYPE + "," + UPDATED_APPLICATION_TYPE);

        // Get all the journalVoucherList where applicationType equals to UPDATED_APPLICATION_TYPE
        defaultJournalVoucherShouldNotBeFound("applicationType.in=" + UPDATED_APPLICATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationType is not null
        defaultJournalVoucherShouldBeFound("applicationType.specified=true");

        // Get all the journalVoucherList where applicationType is null
        defaultJournalVoucherShouldNotBeFound("applicationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationId equals to DEFAULT_APPLICATION_ID
        defaultJournalVoucherShouldBeFound("applicationId.equals=" + DEFAULT_APPLICATION_ID);

        // Get all the journalVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultJournalVoucherShouldNotBeFound("applicationId.equals=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationIdIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationId in DEFAULT_APPLICATION_ID or UPDATED_APPLICATION_ID
        defaultJournalVoucherShouldBeFound("applicationId.in=" + DEFAULT_APPLICATION_ID + "," + UPDATED_APPLICATION_ID);

        // Get all the journalVoucherList where applicationId equals to UPDATED_APPLICATION_ID
        defaultJournalVoucherShouldNotBeFound("applicationId.in=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationId is not null
        defaultJournalVoucherShouldBeFound("applicationId.specified=true");

        // Get all the journalVoucherList where applicationId is null
        defaultJournalVoucherShouldNotBeFound("applicationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationId greater than or equals to DEFAULT_APPLICATION_ID
        defaultJournalVoucherShouldBeFound("applicationId.greaterOrEqualThan=" + DEFAULT_APPLICATION_ID);

        // Get all the journalVoucherList where applicationId greater than or equals to UPDATED_APPLICATION_ID
        defaultJournalVoucherShouldNotBeFound("applicationId.greaterOrEqualThan=" + UPDATED_APPLICATION_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByApplicationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where applicationId less than or equals to DEFAULT_APPLICATION_ID
        defaultJournalVoucherShouldNotBeFound("applicationId.lessThan=" + DEFAULT_APPLICATION_ID);

        // Get all the journalVoucherList where applicationId less than or equals to UPDATED_APPLICATION_ID
        defaultJournalVoucherShouldBeFound("applicationId.lessThan=" + UPDATED_APPLICATION_ID);
    }


    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where referenceId equals to DEFAULT_REFERENCE_ID
        defaultJournalVoucherShouldBeFound("referenceId.equals=" + DEFAULT_REFERENCE_ID);

        // Get all the journalVoucherList where referenceId equals to UPDATED_REFERENCE_ID
        defaultJournalVoucherShouldNotBeFound("referenceId.equals=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIdIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where referenceId in DEFAULT_REFERENCE_ID or UPDATED_REFERENCE_ID
        defaultJournalVoucherShouldBeFound("referenceId.in=" + DEFAULT_REFERENCE_ID + "," + UPDATED_REFERENCE_ID);

        // Get all the journalVoucherList where referenceId equals to UPDATED_REFERENCE_ID
        defaultJournalVoucherShouldNotBeFound("referenceId.in=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where referenceId is not null
        defaultJournalVoucherShouldBeFound("referenceId.specified=true");

        // Get all the journalVoucherList where referenceId is null
        defaultJournalVoucherShouldNotBeFound("referenceId.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where referenceId greater than or equals to DEFAULT_REFERENCE_ID
        defaultJournalVoucherShouldBeFound("referenceId.greaterOrEqualThan=" + DEFAULT_REFERENCE_ID);

        // Get all the journalVoucherList where referenceId greater than or equals to UPDATED_REFERENCE_ID
        defaultJournalVoucherShouldNotBeFound("referenceId.greaterOrEqualThan=" + UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByReferenceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where referenceId less than or equals to DEFAULT_REFERENCE_ID
        defaultJournalVoucherShouldNotBeFound("referenceId.lessThan=" + DEFAULT_REFERENCE_ID);

        // Get all the journalVoucherList where referenceId less than or equals to UPDATED_REFERENCE_ID
        defaultJournalVoucherShouldBeFound("referenceId.lessThan=" + UPDATED_REFERENCE_ID);
    }


    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultJournalVoucherShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the journalVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultJournalVoucherShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultJournalVoucherShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the journalVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultJournalVoucherShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedBy is not null
        defaultJournalVoucherShouldBeFound("modifiedBy.specified=true");

        // Get all the journalVoucherList where modifiedBy is null
        defaultJournalVoucherShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultJournalVoucherShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the journalVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultJournalVoucherShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultJournalVoucherShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the journalVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultJournalVoucherShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedOn is not null
        defaultJournalVoucherShouldBeFound("modifiedOn.specified=true");

        // Get all the journalVoucherList where modifiedOn is null
        defaultJournalVoucherShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultJournalVoucherShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the journalVoucherList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultJournalVoucherShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllJournalVouchersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        // Get all the journalVoucherList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultJournalVoucherShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the journalVoucherList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultJournalVoucherShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllJournalVouchersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Currency currency = CurrencyResourceIntTest.createEntity(em);
        em.persist(currency);
        em.flush();
        journalVoucher.setCurrency(currency);
        journalVoucherRepository.saveAndFlush(journalVoucher);
        Long currencyId = currency.getId();

        // Get all the journalVoucherList where currency equals to currencyId
        defaultJournalVoucherShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the journalVoucherList where currency equals to currencyId + 1
        defaultJournalVoucherShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultJournalVoucherShouldBeFound(String filter) throws Exception {
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].conversionFactor").value(hasItem(DEFAULT_CONVERSION_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultJournalVoucherShouldNotBeFound(String filter) throws Exception {
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingJournalVoucher() throws Exception {
        // Get the journalVoucher
        restJournalVoucherMockMvc.perform(get("/api/journal-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournalVoucher() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        int databaseSizeBeforeUpdate = journalVoucherRepository.findAll().size();

        // Update the journalVoucher
        JournalVoucher updatedJournalVoucher = journalVoucherRepository.findById(journalVoucher.getId()).get();
        // Disconnect from session so that the updates on updatedJournalVoucher are not directly saved in db
        em.detach(updatedJournalVoucher);
        updatedJournalVoucher
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .postDate(UPDATED_POST_DATE)
            .type(UPDATED_TYPE)
            .conversionFactor(UPDATED_CONVERSION_FACTOR)
            .reference(UPDATED_REFERENCE)
            .applicationType(UPDATED_APPLICATION_TYPE)
            .applicationId(UPDATED_APPLICATION_ID)
            .referenceId(UPDATED_REFERENCE_ID)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        JournalVoucherDTO journalVoucherDTO = journalVoucherMapper.toDto(updatedJournalVoucher);

        restJournalVoucherMockMvc.perform(put("/api/journal-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the JournalVoucher in the database
        List<JournalVoucher> journalVoucherList = journalVoucherRepository.findAll();
        assertThat(journalVoucherList).hasSize(databaseSizeBeforeUpdate);
        JournalVoucher testJournalVoucher = journalVoucherList.get(journalVoucherList.size() - 1);
        assertThat(testJournalVoucher.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testJournalVoucher.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testJournalVoucher.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testJournalVoucher.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJournalVoucher.getConversionFactor()).isEqualTo(UPDATED_CONVERSION_FACTOR);
        assertThat(testJournalVoucher.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testJournalVoucher.getApplicationType()).isEqualTo(UPDATED_APPLICATION_TYPE);
        assertThat(testJournalVoucher.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testJournalVoucher.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
        assertThat(testJournalVoucher.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testJournalVoucher.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the JournalVoucher in Elasticsearch
        verify(mockJournalVoucherSearchRepository, times(1)).save(testJournalVoucher);
    }

    @Test
    @Transactional
    public void updateNonExistingJournalVoucher() throws Exception {
        int databaseSizeBeforeUpdate = journalVoucherRepository.findAll().size();

        // Create the JournalVoucher
        JournalVoucherDTO journalVoucherDTO = journalVoucherMapper.toDto(journalVoucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJournalVoucherMockMvc.perform(put("/api/journal-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JournalVoucher in the database
        List<JournalVoucher> journalVoucherList = journalVoucherRepository.findAll();
        assertThat(journalVoucherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the JournalVoucher in Elasticsearch
        verify(mockJournalVoucherSearchRepository, times(0)).save(journalVoucher);
    }

    @Test
    @Transactional
    public void deleteJournalVoucher() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);

        int databaseSizeBeforeDelete = journalVoucherRepository.findAll().size();

        // Delete the journalVoucher
        restJournalVoucherMockMvc.perform(delete("/api/journal-vouchers/{id}", journalVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JournalVoucher> journalVoucherList = journalVoucherRepository.findAll();
        assertThat(journalVoucherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the JournalVoucher in Elasticsearch
        verify(mockJournalVoucherSearchRepository, times(1)).deleteById(journalVoucher.getId());
    }

    @Test
    @Transactional
    public void searchJournalVoucher() throws Exception {
        // Initialize the database
        journalVoucherRepository.saveAndFlush(journalVoucher);
        when(mockJournalVoucherSearchRepository.search(queryStringQuery("id:" + journalVoucher.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(journalVoucher), PageRequest.of(0, 1), 1));
        // Search the journalVoucher
        restJournalVoucherMockMvc.perform(get("/api/_search/journal-vouchers?query=id:" + journalVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].conversionFactor").value(hasItem(DEFAULT_CONVERSION_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].applicationType").value(hasItem(DEFAULT_APPLICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalVoucher.class);
        JournalVoucher journalVoucher1 = new JournalVoucher();
        journalVoucher1.setId(1L);
        JournalVoucher journalVoucher2 = new JournalVoucher();
        journalVoucher2.setId(journalVoucher1.getId());
        assertThat(journalVoucher1).isEqualTo(journalVoucher2);
        journalVoucher2.setId(2L);
        assertThat(journalVoucher1).isNotEqualTo(journalVoucher2);
        journalVoucher1.setId(null);
        assertThat(journalVoucher1).isNotEqualTo(journalVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalVoucherDTO.class);
        JournalVoucherDTO journalVoucherDTO1 = new JournalVoucherDTO();
        journalVoucherDTO1.setId(1L);
        JournalVoucherDTO journalVoucherDTO2 = new JournalVoucherDTO();
        assertThat(journalVoucherDTO1).isNotEqualTo(journalVoucherDTO2);
        journalVoucherDTO2.setId(journalVoucherDTO1.getId());
        assertThat(journalVoucherDTO1).isEqualTo(journalVoucherDTO2);
        journalVoucherDTO2.setId(2L);
        assertThat(journalVoucherDTO1).isNotEqualTo(journalVoucherDTO2);
        journalVoucherDTO1.setId(null);
        assertThat(journalVoucherDTO1).isNotEqualTo(journalVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(journalVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(journalVoucherMapper.fromId(null)).isNull();
    }
}
