package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.domain.PurchaseOrderMessages;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.Quotation;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.search.PurchaseOrderSearchRepository;
import org.soptorshi.service.PurchaseOrderService;
import org.soptorshi.service.dto.PurchaseOrderDTO;
import org.soptorshi.service.mapper.PurchaseOrderMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PurchaseOrderCriteria;
import org.soptorshi.service.PurchaseOrderQueryService;

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

import org.soptorshi.domain.enumeration.PurchaseOrderStatus;
/**
 * Test class for the PurchaseOrderResource REST controller.
 *
 * @see PurchaseOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PurchaseOrderResourceIntTest {

    private static final String DEFAULT_PURCHASE_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ORDER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REFERRED_TO = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LABOR_OR_OTHER_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LABOR_OR_OTHER_AMOUNT = new BigDecimal(2);

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final PurchaseOrderStatus DEFAULT_STATUS = PurchaseOrderStatus.WAITING_FOR_ACCOUNTS_APPROVAL;
    private static final PurchaseOrderStatus UPDATED_STATUS = PurchaseOrderStatus.APPROVED_BY_ACCOUNTS;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PurchaseOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseOrderSearchRepository mockPurchaseOrderSearchRepository;

    @Autowired
    private PurchaseOrderQueryService purchaseOrderQueryService;

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

    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource(purchaseOrderService, purchaseOrderQueryService);
        this.restPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderResource)
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
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .purchaseOrderNo(DEFAULT_PURCHASE_ORDER_NO)
            .workOrderNo(DEFAULT_WORK_ORDER_NO)
            .issueDate(DEFAULT_ISSUE_DATE)
            .referredTo(DEFAULT_REFERRED_TO)
            .subject(DEFAULT_SUBJECT)
            .note(DEFAULT_NOTE)
            .laborOrOtherAmount(DEFAULT_LABOR_OR_OTHER_AMOUNT)
            .discount(DEFAULT_DISCOUNT)
            .status(DEFAULT_STATUS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return purchaseOrder;
    }

    @Before
    public void initTest() {
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getPurchaseOrderNo()).isEqualTo(DEFAULT_PURCHASE_ORDER_NO);
        assertThat(testPurchaseOrder.getWorkOrderNo()).isEqualTo(DEFAULT_WORK_ORDER_NO);
        assertThat(testPurchaseOrder.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testPurchaseOrder.getReferredTo()).isEqualTo(DEFAULT_REFERRED_TO);
        assertThat(testPurchaseOrder.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testPurchaseOrder.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testPurchaseOrder.getLaborOrOtherAmount()).isEqualTo(DEFAULT_LABOR_OR_OTHER_AMOUNT);
        assertThat(testPurchaseOrder.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testPurchaseOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPurchaseOrder.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPurchaseOrder.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the PurchaseOrder in Elasticsearch
        verify(mockPurchaseOrderSearchRepository, times(1)).save(testPurchaseOrder);
    }

    @Test
    @Transactional
    public void createPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder with an existing ID
        purchaseOrder.setId(1L);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseOrder in Elasticsearch
        verify(mockPurchaseOrderSearchRepository, times(0)).save(purchaseOrder);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].workOrderNo").value(hasItem(DEFAULT_WORK_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.purchaseOrderNo").value(DEFAULT_PURCHASE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.workOrderNo").value(DEFAULT_WORK_ORDER_NO.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.referredTo").value(DEFAULT_REFERRED_TO.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.laborOrOtherAmount").value(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByPurchaseOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where purchaseOrderNo equals to DEFAULT_PURCHASE_ORDER_NO
        defaultPurchaseOrderShouldBeFound("purchaseOrderNo.equals=" + DEFAULT_PURCHASE_ORDER_NO);

        // Get all the purchaseOrderList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("purchaseOrderNo.equals=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByPurchaseOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where purchaseOrderNo in DEFAULT_PURCHASE_ORDER_NO or UPDATED_PURCHASE_ORDER_NO
        defaultPurchaseOrderShouldBeFound("purchaseOrderNo.in=" + DEFAULT_PURCHASE_ORDER_NO + "," + UPDATED_PURCHASE_ORDER_NO);

        // Get all the purchaseOrderList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("purchaseOrderNo.in=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByPurchaseOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where purchaseOrderNo is not null
        defaultPurchaseOrderShouldBeFound("purchaseOrderNo.specified=true");

        // Get all the purchaseOrderList where purchaseOrderNo is null
        defaultPurchaseOrderShouldNotBeFound("purchaseOrderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByWorkOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where workOrderNo equals to DEFAULT_WORK_ORDER_NO
        defaultPurchaseOrderShouldBeFound("workOrderNo.equals=" + DEFAULT_WORK_ORDER_NO);

        // Get all the purchaseOrderList where workOrderNo equals to UPDATED_WORK_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("workOrderNo.equals=" + UPDATED_WORK_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByWorkOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where workOrderNo in DEFAULT_WORK_ORDER_NO or UPDATED_WORK_ORDER_NO
        defaultPurchaseOrderShouldBeFound("workOrderNo.in=" + DEFAULT_WORK_ORDER_NO + "," + UPDATED_WORK_ORDER_NO);

        // Get all the purchaseOrderList where workOrderNo equals to UPDATED_WORK_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("workOrderNo.in=" + UPDATED_WORK_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByWorkOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where workOrderNo is not null
        defaultPurchaseOrderShouldBeFound("workOrderNo.specified=true");

        // Get all the purchaseOrderList where workOrderNo is null
        defaultPurchaseOrderShouldNotBeFound("workOrderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where issueDate equals to DEFAULT_ISSUE_DATE
        defaultPurchaseOrderShouldBeFound("issueDate.equals=" + DEFAULT_ISSUE_DATE);

        // Get all the purchaseOrderList where issueDate equals to UPDATED_ISSUE_DATE
        defaultPurchaseOrderShouldNotBeFound("issueDate.equals=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where issueDate in DEFAULT_ISSUE_DATE or UPDATED_ISSUE_DATE
        defaultPurchaseOrderShouldBeFound("issueDate.in=" + DEFAULT_ISSUE_DATE + "," + UPDATED_ISSUE_DATE);

        // Get all the purchaseOrderList where issueDate equals to UPDATED_ISSUE_DATE
        defaultPurchaseOrderShouldNotBeFound("issueDate.in=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where issueDate is not null
        defaultPurchaseOrderShouldBeFound("issueDate.specified=true");

        // Get all the purchaseOrderList where issueDate is null
        defaultPurchaseOrderShouldNotBeFound("issueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIssueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where issueDate greater than or equals to DEFAULT_ISSUE_DATE
        defaultPurchaseOrderShouldBeFound("issueDate.greaterOrEqualThan=" + DEFAULT_ISSUE_DATE);

        // Get all the purchaseOrderList where issueDate greater than or equals to UPDATED_ISSUE_DATE
        defaultPurchaseOrderShouldNotBeFound("issueDate.greaterOrEqualThan=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByIssueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where issueDate less than or equals to DEFAULT_ISSUE_DATE
        defaultPurchaseOrderShouldNotBeFound("issueDate.lessThan=" + DEFAULT_ISSUE_DATE);

        // Get all the purchaseOrderList where issueDate less than or equals to UPDATED_ISSUE_DATE
        defaultPurchaseOrderShouldBeFound("issueDate.lessThan=" + UPDATED_ISSUE_DATE);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByReferredToIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where referredTo equals to DEFAULT_REFERRED_TO
        defaultPurchaseOrderShouldBeFound("referredTo.equals=" + DEFAULT_REFERRED_TO);

        // Get all the purchaseOrderList where referredTo equals to UPDATED_REFERRED_TO
        defaultPurchaseOrderShouldNotBeFound("referredTo.equals=" + UPDATED_REFERRED_TO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByReferredToIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where referredTo in DEFAULT_REFERRED_TO or UPDATED_REFERRED_TO
        defaultPurchaseOrderShouldBeFound("referredTo.in=" + DEFAULT_REFERRED_TO + "," + UPDATED_REFERRED_TO);

        // Get all the purchaseOrderList where referredTo equals to UPDATED_REFERRED_TO
        defaultPurchaseOrderShouldNotBeFound("referredTo.in=" + UPDATED_REFERRED_TO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByReferredToIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where referredTo is not null
        defaultPurchaseOrderShouldBeFound("referredTo.specified=true");

        // Get all the purchaseOrderList where referredTo is null
        defaultPurchaseOrderShouldNotBeFound("referredTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where subject equals to DEFAULT_SUBJECT
        defaultPurchaseOrderShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the purchaseOrderList where subject equals to UPDATED_SUBJECT
        defaultPurchaseOrderShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultPurchaseOrderShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the purchaseOrderList where subject equals to UPDATED_SUBJECT
        defaultPurchaseOrderShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where subject is not null
        defaultPurchaseOrderShouldBeFound("subject.specified=true");

        // Get all the purchaseOrderList where subject is null
        defaultPurchaseOrderShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLaborOrOtherAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where laborOrOtherAmount equals to DEFAULT_LABOR_OR_OTHER_AMOUNT
        defaultPurchaseOrderShouldBeFound("laborOrOtherAmount.equals=" + DEFAULT_LABOR_OR_OTHER_AMOUNT);

        // Get all the purchaseOrderList where laborOrOtherAmount equals to UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("laborOrOtherAmount.equals=" + UPDATED_LABOR_OR_OTHER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLaborOrOtherAmountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where laborOrOtherAmount in DEFAULT_LABOR_OR_OTHER_AMOUNT or UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultPurchaseOrderShouldBeFound("laborOrOtherAmount.in=" + DEFAULT_LABOR_OR_OTHER_AMOUNT + "," + UPDATED_LABOR_OR_OTHER_AMOUNT);

        // Get all the purchaseOrderList where laborOrOtherAmount equals to UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("laborOrOtherAmount.in=" + UPDATED_LABOR_OR_OTHER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByLaborOrOtherAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where laborOrOtherAmount is not null
        defaultPurchaseOrderShouldBeFound("laborOrOtherAmount.specified=true");

        // Get all the purchaseOrderList where laborOrOtherAmount is null
        defaultPurchaseOrderShouldNotBeFound("laborOrOtherAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where discount equals to DEFAULT_DISCOUNT
        defaultPurchaseOrderShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the purchaseOrderList where discount equals to UPDATED_DISCOUNT
        defaultPurchaseOrderShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultPurchaseOrderShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the purchaseOrderList where discount equals to UPDATED_DISCOUNT
        defaultPurchaseOrderShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where discount is not null
        defaultPurchaseOrderShouldBeFound("discount.specified=true");

        // Get all the purchaseOrderList where discount is null
        defaultPurchaseOrderShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where status equals to DEFAULT_STATUS
        defaultPurchaseOrderShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the purchaseOrderList where status equals to UPDATED_STATUS
        defaultPurchaseOrderShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPurchaseOrderShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the purchaseOrderList where status equals to UPDATED_STATUS
        defaultPurchaseOrderShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where status is not null
        defaultPurchaseOrderShouldBeFound("status.specified=true");

        // Get all the purchaseOrderList where status is null
        defaultPurchaseOrderShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the purchaseOrderList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the purchaseOrderList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedBy is not null
        defaultPurchaseOrderShouldBeFound("modifiedBy.specified=true");

        // Get all the purchaseOrderList where modifiedBy is null
        defaultPurchaseOrderShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultPurchaseOrderShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the purchaseOrderList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedOn is not null
        defaultPurchaseOrderShouldBeFound("modifiedOn.specified=true");

        // Get all the purchaseOrderList where modifiedOn is null
        defaultPurchaseOrderShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        PurchaseOrderMessages comments = PurchaseOrderMessagesResourceIntTest.createEntity(em);
        em.persist(comments);
        em.flush();
        purchaseOrder.addComments(comments);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long commentsId = comments.getId();

        // Get all the purchaseOrderList where comments equals to commentsId
        defaultPurchaseOrderShouldBeFound("commentsId.equals=" + commentsId);

        // Get all the purchaseOrderList where comments equals to commentsId + 1
        defaultPurchaseOrderShouldNotBeFound("commentsId.equals=" + (commentsId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        purchaseOrder.setRequisition(requisition);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long requisitionId = requisition.getId();

        // Get all the purchaseOrderList where requisition equals to requisitionId
        defaultPurchaseOrderShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the purchaseOrderList where requisition equals to requisitionId + 1
        defaultPurchaseOrderShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuotationIsEqualToSomething() throws Exception {
        // Initialize the database
        Quotation quotation = QuotationResourceIntTest.createEntity(em);
        em.persist(quotation);
        em.flush();
        purchaseOrder.setQuotation(quotation);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long quotationId = quotation.getId();

        // Get all the purchaseOrderList where quotation equals to quotationId
        defaultPurchaseOrderShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the purchaseOrderList where quotation equals to quotationId + 1
        defaultPurchaseOrderShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPurchaseOrderShouldBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].workOrderNo").value(hasItem(DEFAULT_WORK_ORDER_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPurchaseOrderShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrder are not directly saved in db
        em.detach(updatedPurchaseOrder);
        updatedPurchaseOrder
            .purchaseOrderNo(UPDATED_PURCHASE_ORDER_NO)
            .workOrderNo(UPDATED_WORK_ORDER_NO)
            .issueDate(UPDATED_ISSUE_DATE)
            .referredTo(UPDATED_REFERRED_TO)
            .subject(UPDATED_SUBJECT)
            .note(UPDATED_NOTE)
            .laborOrOtherAmount(UPDATED_LABOR_OR_OTHER_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .status(UPDATED_STATUS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(updatedPurchaseOrder);

        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getPurchaseOrderNo()).isEqualTo(UPDATED_PURCHASE_ORDER_NO);
        assertThat(testPurchaseOrder.getWorkOrderNo()).isEqualTo(UPDATED_WORK_ORDER_NO);
        assertThat(testPurchaseOrder.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testPurchaseOrder.getReferredTo()).isEqualTo(UPDATED_REFERRED_TO);
        assertThat(testPurchaseOrder.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testPurchaseOrder.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testPurchaseOrder.getLaborOrOtherAmount()).isEqualTo(UPDATED_LABOR_OR_OTHER_AMOUNT);
        assertThat(testPurchaseOrder.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testPurchaseOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPurchaseOrder.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPurchaseOrder.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the PurchaseOrder in Elasticsearch
        verify(mockPurchaseOrderSearchRepository, times(1)).save(testPurchaseOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseOrder in Elasticsearch
        verify(mockPurchaseOrderSearchRepository, times(0)).save(purchaseOrder);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Delete the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseOrder in Elasticsearch
        verify(mockPurchaseOrderSearchRepository, times(1)).deleteById(purchaseOrder.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        when(mockPurchaseOrderSearchRepository.search(queryStringQuery("id:" + purchaseOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseOrder), PageRequest.of(0, 1), 1));
        // Search the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/_search/purchase-orders?query=id:" + purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].workOrderNo").value(hasItem(DEFAULT_WORK_ORDER_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrder.class);
        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setId(1L);
        PurchaseOrder purchaseOrder2 = new PurchaseOrder();
        purchaseOrder2.setId(purchaseOrder1.getId());
        assertThat(purchaseOrder1).isEqualTo(purchaseOrder2);
        purchaseOrder2.setId(2L);
        assertThat(purchaseOrder1).isNotEqualTo(purchaseOrder2);
        purchaseOrder1.setId(null);
        assertThat(purchaseOrder1).isNotEqualTo(purchaseOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderDTO.class);
        PurchaseOrderDTO purchaseOrderDTO1 = new PurchaseOrderDTO();
        purchaseOrderDTO1.setId(1L);
        PurchaseOrderDTO purchaseOrderDTO2 = new PurchaseOrderDTO();
        assertThat(purchaseOrderDTO1).isNotEqualTo(purchaseOrderDTO2);
        purchaseOrderDTO2.setId(purchaseOrderDTO1.getId());
        assertThat(purchaseOrderDTO1).isEqualTo(purchaseOrderDTO2);
        purchaseOrderDTO2.setId(2L);
        assertThat(purchaseOrderDTO1).isNotEqualTo(purchaseOrderDTO2);
        purchaseOrderDTO1.setId(null);
        assertThat(purchaseOrderDTO1).isNotEqualTo(purchaseOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrderMapper.fromId(null)).isNull();
    }
}
