package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.WorkOrder;
import org.soptorshi.domain.Requisition;
import org.soptorshi.repository.WorkOrderRepository;
import org.soptorshi.repository.search.WorkOrderSearchRepository;
import org.soptorshi.service.WorkOrderService;
import org.soptorshi.service.dto.WorkOrderDTO;
import org.soptorshi.service.mapper.WorkOrderMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.WorkOrderCriteria;
import org.soptorshi.service.WorkOrderQueryService;

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

/**
 * Test class for the WorkOrderResource REST controller.
 *
 * @see WorkOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class WorkOrderResourceIntTest {

    private static final String DEFAULT_REFERENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NO = "BBBBBBBBBB";

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

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private WorkOrderService workOrderService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.WorkOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private WorkOrderSearchRepository mockWorkOrderSearchRepository;

    @Autowired
    private WorkOrderQueryService workOrderQueryService;

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

    private MockMvc restWorkOrderMockMvc;

    private WorkOrder workOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkOrderResource workOrderResource = new WorkOrderResource(workOrderService, workOrderQueryService);
        this.restWorkOrderMockMvc = MockMvcBuilders.standaloneSetup(workOrderResource)
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
    public static WorkOrder createEntity(EntityManager em) {
        WorkOrder workOrder = new WorkOrder()
            .referenceNo(DEFAULT_REFERENCE_NO)
            .issueDate(DEFAULT_ISSUE_DATE)
            .referredTo(DEFAULT_REFERRED_TO)
            .subject(DEFAULT_SUBJECT)
            .note(DEFAULT_NOTE)
            .laborOrOtherAmount(DEFAULT_LABOR_OR_OTHER_AMOUNT)
            .discount(DEFAULT_DISCOUNT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return workOrder;
    }

    @Before
    public void initTest() {
        workOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkOrder() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);
        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate + 1);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getReferenceNo()).isEqualTo(DEFAULT_REFERENCE_NO);
        assertThat(testWorkOrder.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testWorkOrder.getReferredTo()).isEqualTo(DEFAULT_REFERRED_TO);
        assertThat(testWorkOrder.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testWorkOrder.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testWorkOrder.getLaborOrOtherAmount()).isEqualTo(DEFAULT_LABOR_OR_OTHER_AMOUNT);
        assertThat(testWorkOrder.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testWorkOrder.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testWorkOrder.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the WorkOrder in Elasticsearch
        verify(mockWorkOrderSearchRepository, times(1)).save(testWorkOrder);
    }

    @Test
    @Transactional
    public void createWorkOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workOrderRepository.findAll().size();

        // Create the WorkOrder with an existing ID
        workOrder.setId(1L);
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkOrderMockMvc.perform(post("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorkOrder in Elasticsearch
        verify(mockWorkOrderSearchRepository, times(0)).save(workOrder);
    }

    @Test
    @Transactional
    public void getAllWorkOrders() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList
        restWorkOrderMockMvc.perform(get("/api/work-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceNo").value(hasItem(DEFAULT_REFERENCE_NO.toString())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", workOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workOrder.getId().intValue()))
            .andExpect(jsonPath("$.referenceNo").value(DEFAULT_REFERENCE_NO.toString()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.referredTo").value(DEFAULT_REFERRED_TO.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.laborOrOtherAmount").value(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByReferenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referenceNo equals to DEFAULT_REFERENCE_NO
        defaultWorkOrderShouldBeFound("referenceNo.equals=" + DEFAULT_REFERENCE_NO);

        // Get all the workOrderList where referenceNo equals to UPDATED_REFERENCE_NO
        defaultWorkOrderShouldNotBeFound("referenceNo.equals=" + UPDATED_REFERENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByReferenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referenceNo in DEFAULT_REFERENCE_NO or UPDATED_REFERENCE_NO
        defaultWorkOrderShouldBeFound("referenceNo.in=" + DEFAULT_REFERENCE_NO + "," + UPDATED_REFERENCE_NO);

        // Get all the workOrderList where referenceNo equals to UPDATED_REFERENCE_NO
        defaultWorkOrderShouldNotBeFound("referenceNo.in=" + UPDATED_REFERENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByReferenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referenceNo is not null
        defaultWorkOrderShouldBeFound("referenceNo.specified=true");

        // Get all the workOrderList where referenceNo is null
        defaultWorkOrderShouldNotBeFound("referenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByIssueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where issueDate equals to DEFAULT_ISSUE_DATE
        defaultWorkOrderShouldBeFound("issueDate.equals=" + DEFAULT_ISSUE_DATE);

        // Get all the workOrderList where issueDate equals to UPDATED_ISSUE_DATE
        defaultWorkOrderShouldNotBeFound("issueDate.equals=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByIssueDateIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where issueDate in DEFAULT_ISSUE_DATE or UPDATED_ISSUE_DATE
        defaultWorkOrderShouldBeFound("issueDate.in=" + DEFAULT_ISSUE_DATE + "," + UPDATED_ISSUE_DATE);

        // Get all the workOrderList where issueDate equals to UPDATED_ISSUE_DATE
        defaultWorkOrderShouldNotBeFound("issueDate.in=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByIssueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where issueDate is not null
        defaultWorkOrderShouldBeFound("issueDate.specified=true");

        // Get all the workOrderList where issueDate is null
        defaultWorkOrderShouldNotBeFound("issueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByIssueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where issueDate greater than or equals to DEFAULT_ISSUE_DATE
        defaultWorkOrderShouldBeFound("issueDate.greaterOrEqualThan=" + DEFAULT_ISSUE_DATE);

        // Get all the workOrderList where issueDate greater than or equals to UPDATED_ISSUE_DATE
        defaultWorkOrderShouldNotBeFound("issueDate.greaterOrEqualThan=" + UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByIssueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where issueDate less than or equals to DEFAULT_ISSUE_DATE
        defaultWorkOrderShouldNotBeFound("issueDate.lessThan=" + DEFAULT_ISSUE_DATE);

        // Get all the workOrderList where issueDate less than or equals to UPDATED_ISSUE_DATE
        defaultWorkOrderShouldBeFound("issueDate.lessThan=" + UPDATED_ISSUE_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkOrdersByReferredToIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referredTo equals to DEFAULT_REFERRED_TO
        defaultWorkOrderShouldBeFound("referredTo.equals=" + DEFAULT_REFERRED_TO);

        // Get all the workOrderList where referredTo equals to UPDATED_REFERRED_TO
        defaultWorkOrderShouldNotBeFound("referredTo.equals=" + UPDATED_REFERRED_TO);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByReferredToIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referredTo in DEFAULT_REFERRED_TO or UPDATED_REFERRED_TO
        defaultWorkOrderShouldBeFound("referredTo.in=" + DEFAULT_REFERRED_TO + "," + UPDATED_REFERRED_TO);

        // Get all the workOrderList where referredTo equals to UPDATED_REFERRED_TO
        defaultWorkOrderShouldNotBeFound("referredTo.in=" + UPDATED_REFERRED_TO);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByReferredToIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where referredTo is not null
        defaultWorkOrderShouldBeFound("referredTo.specified=true");

        // Get all the workOrderList where referredTo is null
        defaultWorkOrderShouldNotBeFound("referredTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where subject equals to DEFAULT_SUBJECT
        defaultWorkOrderShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the workOrderList where subject equals to UPDATED_SUBJECT
        defaultWorkOrderShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultWorkOrderShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the workOrderList where subject equals to UPDATED_SUBJECT
        defaultWorkOrderShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where subject is not null
        defaultWorkOrderShouldBeFound("subject.specified=true");

        // Get all the workOrderList where subject is null
        defaultWorkOrderShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByLaborOrOtherAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where laborOrOtherAmount equals to DEFAULT_LABOR_OR_OTHER_AMOUNT
        defaultWorkOrderShouldBeFound("laborOrOtherAmount.equals=" + DEFAULT_LABOR_OR_OTHER_AMOUNT);

        // Get all the workOrderList where laborOrOtherAmount equals to UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultWorkOrderShouldNotBeFound("laborOrOtherAmount.equals=" + UPDATED_LABOR_OR_OTHER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByLaborOrOtherAmountIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where laborOrOtherAmount in DEFAULT_LABOR_OR_OTHER_AMOUNT or UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultWorkOrderShouldBeFound("laborOrOtherAmount.in=" + DEFAULT_LABOR_OR_OTHER_AMOUNT + "," + UPDATED_LABOR_OR_OTHER_AMOUNT);

        // Get all the workOrderList where laborOrOtherAmount equals to UPDATED_LABOR_OR_OTHER_AMOUNT
        defaultWorkOrderShouldNotBeFound("laborOrOtherAmount.in=" + UPDATED_LABOR_OR_OTHER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByLaborOrOtherAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where laborOrOtherAmount is not null
        defaultWorkOrderShouldBeFound("laborOrOtherAmount.specified=true");

        // Get all the workOrderList where laborOrOtherAmount is null
        defaultWorkOrderShouldNotBeFound("laborOrOtherAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where discount equals to DEFAULT_DISCOUNT
        defaultWorkOrderShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the workOrderList where discount equals to UPDATED_DISCOUNT
        defaultWorkOrderShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultWorkOrderShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the workOrderList where discount equals to UPDATED_DISCOUNT
        defaultWorkOrderShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where discount is not null
        defaultWorkOrderShouldBeFound("discount.specified=true");

        // Get all the workOrderList where discount is null
        defaultWorkOrderShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultWorkOrderShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the workOrderList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultWorkOrderShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultWorkOrderShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the workOrderList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultWorkOrderShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedBy is not null
        defaultWorkOrderShouldBeFound("modifiedBy.specified=true");

        // Get all the workOrderList where modifiedBy is null
        defaultWorkOrderShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultWorkOrderShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the workOrderList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultWorkOrderShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultWorkOrderShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the workOrderList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultWorkOrderShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedOn is not null
        defaultWorkOrderShouldBeFound("modifiedOn.specified=true");

        // Get all the workOrderList where modifiedOn is null
        defaultWorkOrderShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultWorkOrderShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the workOrderList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultWorkOrderShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllWorkOrdersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        // Get all the workOrderList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultWorkOrderShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the workOrderList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultWorkOrderShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllWorkOrdersByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        workOrder.setRequisition(requisition);
        workOrderRepository.saveAndFlush(workOrder);
        Long requisitionId = requisition.getId();

        // Get all the workOrderList where requisition equals to requisitionId
        defaultWorkOrderShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the workOrderList where requisition equals to requisitionId + 1
        defaultWorkOrderShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWorkOrderShouldBeFound(String filter) throws Exception {
        restWorkOrderMockMvc.perform(get("/api/work-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceNo").value(hasItem(DEFAULT_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restWorkOrderMockMvc.perform(get("/api/work-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWorkOrderShouldNotBeFound(String filter) throws Exception {
        restWorkOrderMockMvc.perform(get("/api/work-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkOrderMockMvc.perform(get("/api/work-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWorkOrder() throws Exception {
        // Get the workOrder
        restWorkOrderMockMvc.perform(get("/api/work-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Update the workOrder
        WorkOrder updatedWorkOrder = workOrderRepository.findById(workOrder.getId()).get();
        // Disconnect from session so that the updates on updatedWorkOrder are not directly saved in db
        em.detach(updatedWorkOrder);
        updatedWorkOrder
            .referenceNo(UPDATED_REFERENCE_NO)
            .issueDate(UPDATED_ISSUE_DATE)
            .referredTo(UPDATED_REFERRED_TO)
            .subject(UPDATED_SUBJECT)
            .note(UPDATED_NOTE)
            .laborOrOtherAmount(UPDATED_LABOR_OR_OTHER_AMOUNT)
            .discount(UPDATED_DISCOUNT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(updatedWorkOrder);

        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isOk());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate);
        WorkOrder testWorkOrder = workOrderList.get(workOrderList.size() - 1);
        assertThat(testWorkOrder.getReferenceNo()).isEqualTo(UPDATED_REFERENCE_NO);
        assertThat(testWorkOrder.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testWorkOrder.getReferredTo()).isEqualTo(UPDATED_REFERRED_TO);
        assertThat(testWorkOrder.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testWorkOrder.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkOrder.getLaborOrOtherAmount()).isEqualTo(UPDATED_LABOR_OR_OTHER_AMOUNT);
        assertThat(testWorkOrder.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testWorkOrder.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testWorkOrder.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the WorkOrder in Elasticsearch
        verify(mockWorkOrderSearchRepository, times(1)).save(testWorkOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkOrder() throws Exception {
        int databaseSizeBeforeUpdate = workOrderRepository.findAll().size();

        // Create the WorkOrder
        WorkOrderDTO workOrderDTO = workOrderMapper.toDto(workOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkOrderMockMvc.perform(put("/api/work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkOrder in the database
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorkOrder in Elasticsearch
        verify(mockWorkOrderSearchRepository, times(0)).save(workOrder);
    }

    @Test
    @Transactional
    public void deleteWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);

        int databaseSizeBeforeDelete = workOrderRepository.findAll().size();

        // Delete the workOrder
        restWorkOrderMockMvc.perform(delete("/api/work-orders/{id}", workOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkOrder> workOrderList = workOrderRepository.findAll();
        assertThat(workOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorkOrder in Elasticsearch
        verify(mockWorkOrderSearchRepository, times(1)).deleteById(workOrder.getId());
    }

    @Test
    @Transactional
    public void searchWorkOrder() throws Exception {
        // Initialize the database
        workOrderRepository.saveAndFlush(workOrder);
        when(mockWorkOrderSearchRepository.search(queryStringQuery("id:" + workOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(workOrder), PageRequest.of(0, 1), 1));
        // Search the workOrder
        restWorkOrderMockMvc.perform(get("/api/_search/work-orders?query=id:" + workOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].referenceNo").value(hasItem(DEFAULT_REFERENCE_NO)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].referredTo").value(hasItem(DEFAULT_REFERRED_TO)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].laborOrOtherAmount").value(hasItem(DEFAULT_LABOR_OR_OTHER_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrder.class);
        WorkOrder workOrder1 = new WorkOrder();
        workOrder1.setId(1L);
        WorkOrder workOrder2 = new WorkOrder();
        workOrder2.setId(workOrder1.getId());
        assertThat(workOrder1).isEqualTo(workOrder2);
        workOrder2.setId(2L);
        assertThat(workOrder1).isNotEqualTo(workOrder2);
        workOrder1.setId(null);
        assertThat(workOrder1).isNotEqualTo(workOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkOrderDTO.class);
        WorkOrderDTO workOrderDTO1 = new WorkOrderDTO();
        workOrderDTO1.setId(1L);
        WorkOrderDTO workOrderDTO2 = new WorkOrderDTO();
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
        workOrderDTO2.setId(workOrderDTO1.getId());
        assertThat(workOrderDTO1).isEqualTo(workOrderDTO2);
        workOrderDTO2.setId(2L);
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
        workOrderDTO1.setId(null);
        assertThat(workOrderDTO1).isNotEqualTo(workOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workOrderMapper.fromId(null)).isNull();
    }
}
