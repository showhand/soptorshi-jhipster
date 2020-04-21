package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.RequisitionMessages;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.Department;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.service.RequisitionService;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.RequisitionCriteria;
import org.soptorshi.service.RequisitionQueryService;

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

import org.soptorshi.domain.enumeration.RequisitionType;
import org.soptorshi.domain.enumeration.RequisitionStatus;
/**
 * Test class for the RequisitionResource REST controller.
 *
 * @see RequisitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class RequisitionResourceIntTest {

    private static final String DEFAULT_REQUISITION_NO = "AAAAAAAAAA";
    private static final String UPDATED_REQUISITION_NO = "BBBBBBBBBB";

    private static final RequisitionType DEFAULT_REQUISITION_TYPE = RequisitionType.NORMAL;
    private static final RequisitionType UPDATED_REQUISITION_TYPE = RequisitionType.SUPPLY_CHAIN;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REQUISITION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUISITION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final RequisitionStatus DEFAULT_STATUS = RequisitionStatus.WAITING_FOR_HEADS_APPROVAL;
    private static final RequisitionStatus UPDATED_STATUS = RequisitionStatus.REJECTED_BY_HEAD;

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final String DEFAULT_HEAD_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_REMARKS = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TO_HEAD = 1L;
    private static final Long UPDATED_REF_TO_HEAD = 2L;

    private static final String DEFAULT_PURCHASE_COMMITTEE_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_COMMITTEE_REMARKS = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TO_PURCHASE_COMMITTEE = 1L;
    private static final Long UPDATED_REF_TO_PURCHASE_COMMITTEE = 2L;

    private static final String DEFAULT_CFO_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_CFO_REMARKS = "BBBBBBBBBB";

    private static final Long DEFAULT_REF_TO_CFO = 1L;
    private static final Long UPDATED_REF_TO_CFO = 2L;

    private static final Long DEFAULT_COMMERCIAL_ID = 1L;
    private static final Long UPDATED_COMMERCIAL_ID = 2L;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequisitionRepository requisitionRepository;

    @Autowired
    private RequisitionMapper requisitionMapper;

    @Autowired
    private RequisitionService requisitionService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.RequisitionSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisitionSearchRepository mockRequisitionSearchRepository;

    @Autowired
    private RequisitionQueryService requisitionQueryService;

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

    private MockMvc restRequisitionMockMvc;

    private Requisition requisition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitionResource requisitionResource = new RequisitionResource(requisitionService, requisitionQueryService);
        this.restRequisitionMockMvc = MockMvcBuilders.standaloneSetup(requisitionResource)
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
    public static Requisition createEntity(EntityManager em) {
        Requisition requisition = new Requisition()
            .requisitionNo(DEFAULT_REQUISITION_NO)
            .requisitionType(DEFAULT_REQUISITION_TYPE)
            .reason(DEFAULT_REASON)
            .requisitionDate(DEFAULT_REQUISITION_DATE)
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS)
            .selected(DEFAULT_SELECTED)
            .headRemarks(DEFAULT_HEAD_REMARKS)
            .refToHead(DEFAULT_REF_TO_HEAD)
            .purchaseCommitteeRemarks(DEFAULT_PURCHASE_COMMITTEE_REMARKS)
            .refToPurchaseCommittee(DEFAULT_REF_TO_PURCHASE_COMMITTEE)
            .cfoRemarks(DEFAULT_CFO_REMARKS)
            .refToCfo(DEFAULT_REF_TO_CFO)
            .commercialId(DEFAULT_COMMERCIAL_ID)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return requisition;
    }

    @Before
    public void initTest() {
        requisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisition() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);
        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate + 1);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getRequisitionNo()).isEqualTo(DEFAULT_REQUISITION_NO);
        assertThat(testRequisition.getRequisitionType()).isEqualTo(DEFAULT_REQUISITION_TYPE);
        assertThat(testRequisition.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testRequisition.getRequisitionDate()).isEqualTo(DEFAULT_REQUISITION_DATE);
        assertThat(testRequisition.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRequisition.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequisition.isSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testRequisition.getHeadRemarks()).isEqualTo(DEFAULT_HEAD_REMARKS);
        assertThat(testRequisition.getRefToHead()).isEqualTo(DEFAULT_REF_TO_HEAD);
        assertThat(testRequisition.getPurchaseCommitteeRemarks()).isEqualTo(DEFAULT_PURCHASE_COMMITTEE_REMARKS);
        assertThat(testRequisition.getRefToPurchaseCommittee()).isEqualTo(DEFAULT_REF_TO_PURCHASE_COMMITTEE);
        assertThat(testRequisition.getCfoRemarks()).isEqualTo(DEFAULT_CFO_REMARKS);
        assertThat(testRequisition.getRefToCfo()).isEqualTo(DEFAULT_REF_TO_CFO);
        assertThat(testRequisition.getCommercialId()).isEqualTo(DEFAULT_COMMERCIAL_ID);
        assertThat(testRequisition.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRequisition.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).save(testRequisition);
    }

    @Test
    @Transactional
    public void createRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition with an existing ID
        requisition.setId(1L);
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(0)).save(requisition);
    }

    @Test
    @Transactional
    public void getAllRequisitions() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].requisitionNo").value(hasItem(DEFAULT_REQUISITION_NO.toString())))
            .andExpect(jsonPath("$.[*].requisitionType").value(hasItem(DEFAULT_REQUISITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].requisitionDate").value(hasItem(DEFAULT_REQUISITION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].headRemarks").value(hasItem(DEFAULT_HEAD_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToHead").value(hasItem(DEFAULT_REF_TO_HEAD.intValue())))
            .andExpect(jsonPath("$.[*].purchaseCommitteeRemarks").value(hasItem(DEFAULT_PURCHASE_COMMITTEE_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToPurchaseCommittee").value(hasItem(DEFAULT_REF_TO_PURCHASE_COMMITTEE.intValue())))
            .andExpect(jsonPath("$.[*].cfoRemarks").value(hasItem(DEFAULT_CFO_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToCfo").value(hasItem(DEFAULT_REF_TO_CFO.intValue())))
            .andExpect(jsonPath("$.[*].commercialId").value(hasItem(DEFAULT_COMMERCIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisition.getId().intValue()))
            .andExpect(jsonPath("$.requisitionNo").value(DEFAULT_REQUISITION_NO.toString()))
            .andExpect(jsonPath("$.requisitionType").value(DEFAULT_REQUISITION_TYPE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.requisitionDate").value(DEFAULT_REQUISITION_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()))
            .andExpect(jsonPath("$.headRemarks").value(DEFAULT_HEAD_REMARKS.toString()))
            .andExpect(jsonPath("$.refToHead").value(DEFAULT_REF_TO_HEAD.intValue()))
            .andExpect(jsonPath("$.purchaseCommitteeRemarks").value(DEFAULT_PURCHASE_COMMITTEE_REMARKS.toString()))
            .andExpect(jsonPath("$.refToPurchaseCommittee").value(DEFAULT_REF_TO_PURCHASE_COMMITTEE.intValue()))
            .andExpect(jsonPath("$.cfoRemarks").value(DEFAULT_CFO_REMARKS.toString()))
            .andExpect(jsonPath("$.refToCfo").value(DEFAULT_REF_TO_CFO.intValue()))
            .andExpect(jsonPath("$.commercialId").value(DEFAULT_COMMERCIAL_ID.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionNoIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionNo equals to DEFAULT_REQUISITION_NO
        defaultRequisitionShouldBeFound("requisitionNo.equals=" + DEFAULT_REQUISITION_NO);

        // Get all the requisitionList where requisitionNo equals to UPDATED_REQUISITION_NO
        defaultRequisitionShouldNotBeFound("requisitionNo.equals=" + UPDATED_REQUISITION_NO);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionNoIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionNo in DEFAULT_REQUISITION_NO or UPDATED_REQUISITION_NO
        defaultRequisitionShouldBeFound("requisitionNo.in=" + DEFAULT_REQUISITION_NO + "," + UPDATED_REQUISITION_NO);

        // Get all the requisitionList where requisitionNo equals to UPDATED_REQUISITION_NO
        defaultRequisitionShouldNotBeFound("requisitionNo.in=" + UPDATED_REQUISITION_NO);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionNo is not null
        defaultRequisitionShouldBeFound("requisitionNo.specified=true");

        // Get all the requisitionList where requisitionNo is null
        defaultRequisitionShouldNotBeFound("requisitionNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionType equals to DEFAULT_REQUISITION_TYPE
        defaultRequisitionShouldBeFound("requisitionType.equals=" + DEFAULT_REQUISITION_TYPE);

        // Get all the requisitionList where requisitionType equals to UPDATED_REQUISITION_TYPE
        defaultRequisitionShouldNotBeFound("requisitionType.equals=" + UPDATED_REQUISITION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionType in DEFAULT_REQUISITION_TYPE or UPDATED_REQUISITION_TYPE
        defaultRequisitionShouldBeFound("requisitionType.in=" + DEFAULT_REQUISITION_TYPE + "," + UPDATED_REQUISITION_TYPE);

        // Get all the requisitionList where requisitionType equals to UPDATED_REQUISITION_TYPE
        defaultRequisitionShouldNotBeFound("requisitionType.in=" + UPDATED_REQUISITION_TYPE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionType is not null
        defaultRequisitionShouldBeFound("requisitionType.specified=true");

        // Get all the requisitionList where requisitionType is null
        defaultRequisitionShouldNotBeFound("requisitionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionDate equals to DEFAULT_REQUISITION_DATE
        defaultRequisitionShouldBeFound("requisitionDate.equals=" + DEFAULT_REQUISITION_DATE);

        // Get all the requisitionList where requisitionDate equals to UPDATED_REQUISITION_DATE
        defaultRequisitionShouldNotBeFound("requisitionDate.equals=" + UPDATED_REQUISITION_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionDateIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionDate in DEFAULT_REQUISITION_DATE or UPDATED_REQUISITION_DATE
        defaultRequisitionShouldBeFound("requisitionDate.in=" + DEFAULT_REQUISITION_DATE + "," + UPDATED_REQUISITION_DATE);

        // Get all the requisitionList where requisitionDate equals to UPDATED_REQUISITION_DATE
        defaultRequisitionShouldNotBeFound("requisitionDate.in=" + UPDATED_REQUISITION_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionDate is not null
        defaultRequisitionShouldBeFound("requisitionDate.specified=true");

        // Get all the requisitionList where requisitionDate is null
        defaultRequisitionShouldNotBeFound("requisitionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionDate greater than or equals to DEFAULT_REQUISITION_DATE
        defaultRequisitionShouldBeFound("requisitionDate.greaterOrEqualThan=" + DEFAULT_REQUISITION_DATE);

        // Get all the requisitionList where requisitionDate greater than or equals to UPDATED_REQUISITION_DATE
        defaultRequisitionShouldNotBeFound("requisitionDate.greaterOrEqualThan=" + UPDATED_REQUISITION_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRequisitionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where requisitionDate less than or equals to DEFAULT_REQUISITION_DATE
        defaultRequisitionShouldNotBeFound("requisitionDate.lessThan=" + DEFAULT_REQUISITION_DATE);

        // Get all the requisitionList where requisitionDate less than or equals to UPDATED_REQUISITION_DATE
        defaultRequisitionShouldBeFound("requisitionDate.lessThan=" + UPDATED_REQUISITION_DATE);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where amount equals to DEFAULT_AMOUNT
        defaultRequisitionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the requisitionList where amount equals to UPDATED_AMOUNT
        defaultRequisitionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultRequisitionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the requisitionList where amount equals to UPDATED_AMOUNT
        defaultRequisitionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where amount is not null
        defaultRequisitionShouldBeFound("amount.specified=true");

        // Get all the requisitionList where amount is null
        defaultRequisitionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status equals to DEFAULT_STATUS
        defaultRequisitionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the requisitionList where status equals to UPDATED_STATUS
        defaultRequisitionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRequisitionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the requisitionList where status equals to UPDATED_STATUS
        defaultRequisitionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where status is not null
        defaultRequisitionShouldBeFound("status.specified=true");

        // Get all the requisitionList where status is null
        defaultRequisitionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsBySelectedIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where selected equals to DEFAULT_SELECTED
        defaultRequisitionShouldBeFound("selected.equals=" + DEFAULT_SELECTED);

        // Get all the requisitionList where selected equals to UPDATED_SELECTED
        defaultRequisitionShouldNotBeFound("selected.equals=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    public void getAllRequisitionsBySelectedIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where selected in DEFAULT_SELECTED or UPDATED_SELECTED
        defaultRequisitionShouldBeFound("selected.in=" + DEFAULT_SELECTED + "," + UPDATED_SELECTED);

        // Get all the requisitionList where selected equals to UPDATED_SELECTED
        defaultRequisitionShouldNotBeFound("selected.in=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    public void getAllRequisitionsBySelectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where selected is not null
        defaultRequisitionShouldBeFound("selected.specified=true");

        // Get all the requisitionList where selected is null
        defaultRequisitionShouldNotBeFound("selected.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToHeadIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToHead equals to DEFAULT_REF_TO_HEAD
        defaultRequisitionShouldBeFound("refToHead.equals=" + DEFAULT_REF_TO_HEAD);

        // Get all the requisitionList where refToHead equals to UPDATED_REF_TO_HEAD
        defaultRequisitionShouldNotBeFound("refToHead.equals=" + UPDATED_REF_TO_HEAD);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToHeadIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToHead in DEFAULT_REF_TO_HEAD or UPDATED_REF_TO_HEAD
        defaultRequisitionShouldBeFound("refToHead.in=" + DEFAULT_REF_TO_HEAD + "," + UPDATED_REF_TO_HEAD);

        // Get all the requisitionList where refToHead equals to UPDATED_REF_TO_HEAD
        defaultRequisitionShouldNotBeFound("refToHead.in=" + UPDATED_REF_TO_HEAD);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToHeadIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToHead is not null
        defaultRequisitionShouldBeFound("refToHead.specified=true");

        // Get all the requisitionList where refToHead is null
        defaultRequisitionShouldNotBeFound("refToHead.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToHeadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToHead greater than or equals to DEFAULT_REF_TO_HEAD
        defaultRequisitionShouldBeFound("refToHead.greaterOrEqualThan=" + DEFAULT_REF_TO_HEAD);

        // Get all the requisitionList where refToHead greater than or equals to UPDATED_REF_TO_HEAD
        defaultRequisitionShouldNotBeFound("refToHead.greaterOrEqualThan=" + UPDATED_REF_TO_HEAD);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToHeadIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToHead less than or equals to DEFAULT_REF_TO_HEAD
        defaultRequisitionShouldNotBeFound("refToHead.lessThan=" + DEFAULT_REF_TO_HEAD);

        // Get all the requisitionList where refToHead less than or equals to UPDATED_REF_TO_HEAD
        defaultRequisitionShouldBeFound("refToHead.lessThan=" + UPDATED_REF_TO_HEAD);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByRefToPurchaseCommitteeIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToPurchaseCommittee equals to DEFAULT_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldBeFound("refToPurchaseCommittee.equals=" + DEFAULT_REF_TO_PURCHASE_COMMITTEE);

        // Get all the requisitionList where refToPurchaseCommittee equals to UPDATED_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldNotBeFound("refToPurchaseCommittee.equals=" + UPDATED_REF_TO_PURCHASE_COMMITTEE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToPurchaseCommitteeIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToPurchaseCommittee in DEFAULT_REF_TO_PURCHASE_COMMITTEE or UPDATED_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldBeFound("refToPurchaseCommittee.in=" + DEFAULT_REF_TO_PURCHASE_COMMITTEE + "," + UPDATED_REF_TO_PURCHASE_COMMITTEE);

        // Get all the requisitionList where refToPurchaseCommittee equals to UPDATED_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldNotBeFound("refToPurchaseCommittee.in=" + UPDATED_REF_TO_PURCHASE_COMMITTEE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToPurchaseCommitteeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToPurchaseCommittee is not null
        defaultRequisitionShouldBeFound("refToPurchaseCommittee.specified=true");

        // Get all the requisitionList where refToPurchaseCommittee is null
        defaultRequisitionShouldNotBeFound("refToPurchaseCommittee.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToPurchaseCommitteeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToPurchaseCommittee greater than or equals to DEFAULT_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldBeFound("refToPurchaseCommittee.greaterOrEqualThan=" + DEFAULT_REF_TO_PURCHASE_COMMITTEE);

        // Get all the requisitionList where refToPurchaseCommittee greater than or equals to UPDATED_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldNotBeFound("refToPurchaseCommittee.greaterOrEqualThan=" + UPDATED_REF_TO_PURCHASE_COMMITTEE);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToPurchaseCommitteeIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToPurchaseCommittee less than or equals to DEFAULT_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldNotBeFound("refToPurchaseCommittee.lessThan=" + DEFAULT_REF_TO_PURCHASE_COMMITTEE);

        // Get all the requisitionList where refToPurchaseCommittee less than or equals to UPDATED_REF_TO_PURCHASE_COMMITTEE
        defaultRequisitionShouldBeFound("refToPurchaseCommittee.lessThan=" + UPDATED_REF_TO_PURCHASE_COMMITTEE);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByRefToCfoIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToCfo equals to DEFAULT_REF_TO_CFO
        defaultRequisitionShouldBeFound("refToCfo.equals=" + DEFAULT_REF_TO_CFO);

        // Get all the requisitionList where refToCfo equals to UPDATED_REF_TO_CFO
        defaultRequisitionShouldNotBeFound("refToCfo.equals=" + UPDATED_REF_TO_CFO);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToCfoIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToCfo in DEFAULT_REF_TO_CFO or UPDATED_REF_TO_CFO
        defaultRequisitionShouldBeFound("refToCfo.in=" + DEFAULT_REF_TO_CFO + "," + UPDATED_REF_TO_CFO);

        // Get all the requisitionList where refToCfo equals to UPDATED_REF_TO_CFO
        defaultRequisitionShouldNotBeFound("refToCfo.in=" + UPDATED_REF_TO_CFO);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToCfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToCfo is not null
        defaultRequisitionShouldBeFound("refToCfo.specified=true");

        // Get all the requisitionList where refToCfo is null
        defaultRequisitionShouldNotBeFound("refToCfo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToCfoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToCfo greater than or equals to DEFAULT_REF_TO_CFO
        defaultRequisitionShouldBeFound("refToCfo.greaterOrEqualThan=" + DEFAULT_REF_TO_CFO);

        // Get all the requisitionList where refToCfo greater than or equals to UPDATED_REF_TO_CFO
        defaultRequisitionShouldNotBeFound("refToCfo.greaterOrEqualThan=" + UPDATED_REF_TO_CFO);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByRefToCfoIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where refToCfo less than or equals to DEFAULT_REF_TO_CFO
        defaultRequisitionShouldNotBeFound("refToCfo.lessThan=" + DEFAULT_REF_TO_CFO);

        // Get all the requisitionList where refToCfo less than or equals to UPDATED_REF_TO_CFO
        defaultRequisitionShouldBeFound("refToCfo.lessThan=" + UPDATED_REF_TO_CFO);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByCommercialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where commercialId equals to DEFAULT_COMMERCIAL_ID
        defaultRequisitionShouldBeFound("commercialId.equals=" + DEFAULT_COMMERCIAL_ID);

        // Get all the requisitionList where commercialId equals to UPDATED_COMMERCIAL_ID
        defaultRequisitionShouldNotBeFound("commercialId.equals=" + UPDATED_COMMERCIAL_ID);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByCommercialIdIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where commercialId in DEFAULT_COMMERCIAL_ID or UPDATED_COMMERCIAL_ID
        defaultRequisitionShouldBeFound("commercialId.in=" + DEFAULT_COMMERCIAL_ID + "," + UPDATED_COMMERCIAL_ID);

        // Get all the requisitionList where commercialId equals to UPDATED_COMMERCIAL_ID
        defaultRequisitionShouldNotBeFound("commercialId.in=" + UPDATED_COMMERCIAL_ID);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByCommercialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where commercialId is not null
        defaultRequisitionShouldBeFound("commercialId.specified=true");

        // Get all the requisitionList where commercialId is null
        defaultRequisitionShouldNotBeFound("commercialId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByCommercialIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where commercialId greater than or equals to DEFAULT_COMMERCIAL_ID
        defaultRequisitionShouldBeFound("commercialId.greaterOrEqualThan=" + DEFAULT_COMMERCIAL_ID);

        // Get all the requisitionList where commercialId greater than or equals to UPDATED_COMMERCIAL_ID
        defaultRequisitionShouldNotBeFound("commercialId.greaterOrEqualThan=" + UPDATED_COMMERCIAL_ID);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByCommercialIdIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where commercialId less than or equals to DEFAULT_COMMERCIAL_ID
        defaultRequisitionShouldNotBeFound("commercialId.lessThan=" + DEFAULT_COMMERCIAL_ID);

        // Get all the requisitionList where commercialId less than or equals to UPDATED_COMMERCIAL_ID
        defaultRequisitionShouldBeFound("commercialId.lessThan=" + UPDATED_COMMERCIAL_ID);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultRequisitionShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the requisitionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultRequisitionShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the requisitionList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedBy is not null
        defaultRequisitionShouldBeFound("modifiedBy.specified=true");

        // Get all the requisitionList where modifiedBy is null
        defaultRequisitionShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultRequisitionShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultRequisitionShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the requisitionList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedOn is not null
        defaultRequisitionShouldBeFound("modifiedOn.specified=true");

        // Get all the requisitionList where modifiedOn is null
        defaultRequisitionShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllRequisitionsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        RequisitionMessages comments = RequisitionMessagesResourceIntTest.createEntity(em);
        em.persist(comments);
        em.flush();
        requisition.addComments(comments);
        requisitionRepository.saveAndFlush(requisition);
        Long commentsId = comments.getId();

        // Get all the requisitionList where comments equals to commentsId
        defaultRequisitionShouldBeFound("commentsId.equals=" + commentsId);

        // Get all the requisitionList where comments equals to commentsId + 1
        defaultRequisitionShouldNotBeFound("commentsId.equals=" + (commentsId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        requisition.setEmployee(employee);
        requisitionRepository.saveAndFlush(requisition);
        Long employeeId = employee.getId();

        // Get all the requisitionList where employee equals to employeeId
        defaultRequisitionShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the requisitionList where employee equals to employeeId + 1
        defaultRequisitionShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        Office office = OfficeResourceIntTest.createEntity(em);
        em.persist(office);
        em.flush();
        requisition.setOffice(office);
        requisitionRepository.saveAndFlush(requisition);
        Long officeId = office.getId();

        // Get all the requisitionList where office equals to officeId
        defaultRequisitionShouldBeFound("officeId.equals=" + officeId);

        // Get all the requisitionList where office equals to officeId + 1
        defaultRequisitionShouldNotBeFound("officeId.equals=" + (officeId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        requisition.setProductCategory(productCategory);
        requisitionRepository.saveAndFlush(requisition);
        Long productCategoryId = productCategory.getId();

        // Get all the requisitionList where productCategory equals to productCategoryId
        defaultRequisitionShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the requisitionList where productCategory equals to productCategoryId + 1
        defaultRequisitionShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        requisition.setDepartment(department);
        requisitionRepository.saveAndFlush(requisition);
        Long departmentId = department.getId();

        // Get all the requisitionList where department equals to departmentId
        defaultRequisitionShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the requisitionList where department equals to departmentId + 1
        defaultRequisitionShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRequisitionShouldBeFound(String filter) throws Exception {
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].requisitionNo").value(hasItem(DEFAULT_REQUISITION_NO)))
            .andExpect(jsonPath("$.[*].requisitionType").value(hasItem(DEFAULT_REQUISITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].requisitionDate").value(hasItem(DEFAULT_REQUISITION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].headRemarks").value(hasItem(DEFAULT_HEAD_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToHead").value(hasItem(DEFAULT_REF_TO_HEAD.intValue())))
            .andExpect(jsonPath("$.[*].purchaseCommitteeRemarks").value(hasItem(DEFAULT_PURCHASE_COMMITTEE_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToPurchaseCommittee").value(hasItem(DEFAULT_REF_TO_PURCHASE_COMMITTEE.intValue())))
            .andExpect(jsonPath("$.[*].cfoRemarks").value(hasItem(DEFAULT_CFO_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToCfo").value(hasItem(DEFAULT_REF_TO_CFO.intValue())))
            .andExpect(jsonPath("$.[*].commercialId").value(hasItem(DEFAULT_COMMERCIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restRequisitionMockMvc.perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRequisitionShouldNotBeFound(String filter) throws Exception {
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionMockMvc.perform(get("/api/requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequisition() throws Exception {
        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition
        Requisition updatedRequisition = requisitionRepository.findById(requisition.getId()).get();
        // Disconnect from session so that the updates on updatedRequisition are not directly saved in db
        em.detach(updatedRequisition);
        updatedRequisition
            .requisitionNo(UPDATED_REQUISITION_NO)
            .requisitionType(UPDATED_REQUISITION_TYPE)
            .reason(UPDATED_REASON)
            .requisitionDate(UPDATED_REQUISITION_DATE)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .selected(UPDATED_SELECTED)
            .headRemarks(UPDATED_HEAD_REMARKS)
            .refToHead(UPDATED_REF_TO_HEAD)
            .purchaseCommitteeRemarks(UPDATED_PURCHASE_COMMITTEE_REMARKS)
            .refToPurchaseCommittee(UPDATED_REF_TO_PURCHASE_COMMITTEE)
            .cfoRemarks(UPDATED_CFO_REMARKS)
            .refToCfo(UPDATED_REF_TO_CFO)
            .commercialId(UPDATED_COMMERCIAL_ID)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(updatedRequisition);

        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getRequisitionNo()).isEqualTo(UPDATED_REQUISITION_NO);
        assertThat(testRequisition.getRequisitionType()).isEqualTo(UPDATED_REQUISITION_TYPE);
        assertThat(testRequisition.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testRequisition.getRequisitionDate()).isEqualTo(UPDATED_REQUISITION_DATE);
        assertThat(testRequisition.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRequisition.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequisition.isSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testRequisition.getHeadRemarks()).isEqualTo(UPDATED_HEAD_REMARKS);
        assertThat(testRequisition.getRefToHead()).isEqualTo(UPDATED_REF_TO_HEAD);
        assertThat(testRequisition.getPurchaseCommitteeRemarks()).isEqualTo(UPDATED_PURCHASE_COMMITTEE_REMARKS);
        assertThat(testRequisition.getRefToPurchaseCommittee()).isEqualTo(UPDATED_REF_TO_PURCHASE_COMMITTEE);
        assertThat(testRequisition.getCfoRemarks()).isEqualTo(UPDATED_CFO_REMARKS);
        assertThat(testRequisition.getRefToCfo()).isEqualTo(UPDATED_REF_TO_CFO);
        assertThat(testRequisition.getCommercialId()).isEqualTo(UPDATED_COMMERCIAL_ID);
        assertThat(testRequisition.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRequisition.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).save(testRequisition);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisition() throws Exception {
        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.toDto(requisition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(0)).save(requisition);
    }

    @Test
    @Transactional
    public void deleteRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        int databaseSizeBeforeDelete = requisitionRepository.findAll().size();

        // Delete the requisition
        restRequisitionMockMvc.perform(delete("/api/requisitions/{id}", requisition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Requisition in Elasticsearch
        verify(mockRequisitionSearchRepository, times(1)).deleteById(requisition.getId());
    }

    @Test
    @Transactional
    public void searchRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        when(mockRequisitionSearchRepository.search(queryStringQuery("id:" + requisition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisition), PageRequest.of(0, 1), 1));
        // Search the requisition
        restRequisitionMockMvc.perform(get("/api/_search/requisitions?query=id:" + requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].requisitionNo").value(hasItem(DEFAULT_REQUISITION_NO)))
            .andExpect(jsonPath("$.[*].requisitionType").value(hasItem(DEFAULT_REQUISITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].requisitionDate").value(hasItem(DEFAULT_REQUISITION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].headRemarks").value(hasItem(DEFAULT_HEAD_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToHead").value(hasItem(DEFAULT_REF_TO_HEAD.intValue())))
            .andExpect(jsonPath("$.[*].purchaseCommitteeRemarks").value(hasItem(DEFAULT_PURCHASE_COMMITTEE_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToPurchaseCommittee").value(hasItem(DEFAULT_REF_TO_PURCHASE_COMMITTEE.intValue())))
            .andExpect(jsonPath("$.[*].cfoRemarks").value(hasItem(DEFAULT_CFO_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].refToCfo").value(hasItem(DEFAULT_REF_TO_CFO.intValue())))
            .andExpect(jsonPath("$.[*].commercialId").value(hasItem(DEFAULT_COMMERCIAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requisition.class);
        Requisition requisition1 = new Requisition();
        requisition1.setId(1L);
        Requisition requisition2 = new Requisition();
        requisition2.setId(requisition1.getId());
        assertThat(requisition1).isEqualTo(requisition2);
        requisition2.setId(2L);
        assertThat(requisition1).isNotEqualTo(requisition2);
        requisition1.setId(null);
        assertThat(requisition1).isNotEqualTo(requisition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionDTO.class);
        RequisitionDTO requisitionDTO1 = new RequisitionDTO();
        requisitionDTO1.setId(1L);
        RequisitionDTO requisitionDTO2 = new RequisitionDTO();
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
        requisitionDTO2.setId(requisitionDTO1.getId());
        assertThat(requisitionDTO1).isEqualTo(requisitionDTO2);
        requisitionDTO2.setId(2L);
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
        requisitionDTO1.setId(null);
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requisitionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requisitionMapper.fromId(null)).isNull();
    }
}
