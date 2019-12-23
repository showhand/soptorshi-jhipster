package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialWorkOrder;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialWorkOrderRepository;
import org.soptorshi.repository.search.CommercialWorkOrderSearchRepository;
import org.soptorshi.service.CommercialWorkOrderService;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialWorkOrderCriteria;
import org.soptorshi.service.CommercialWorkOrderQueryService;

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
 * Test class for the CommercialWorkOrderResource REST controller.
 *
 * @see CommercialWorkOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialWorkOrderResourceIntTest {

    private static final String DEFAULT_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_REF_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_WORK_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORK_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialWorkOrderRepository commercialWorkOrderRepository;

    @Autowired
    private CommercialWorkOrderMapper commercialWorkOrderMapper;

    @Autowired
    private CommercialWorkOrderService commercialWorkOrderService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialWorkOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialWorkOrderSearchRepository mockCommercialWorkOrderSearchRepository;

    @Autowired
    private CommercialWorkOrderQueryService commercialWorkOrderQueryService;

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

    private MockMvc restCommercialWorkOrderMockMvc;

    private CommercialWorkOrder commercialWorkOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialWorkOrderResource commercialWorkOrderResource = new CommercialWorkOrderResource(commercialWorkOrderService, commercialWorkOrderQueryService);
        this.restCommercialWorkOrderMockMvc = MockMvcBuilders.standaloneSetup(commercialWorkOrderResource)
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
    public static CommercialWorkOrder createEntity(EntityManager em) {
        CommercialWorkOrder commercialWorkOrder = new CommercialWorkOrder()
            .refNo(DEFAULT_REF_NO)
            .workOrderDate(DEFAULT_WORK_ORDER_DATE)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialWorkOrder;
    }

    @Before
    public void initTest() {
        commercialWorkOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialWorkOrder() throws Exception {
        int databaseSizeBeforeCreate = commercialWorkOrderRepository.findAll().size();

        // Create the CommercialWorkOrder
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);
        restCommercialWorkOrderMockMvc.perform(post("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialWorkOrder in the database
        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialWorkOrder testCommercialWorkOrder = commercialWorkOrderList.get(commercialWorkOrderList.size() - 1);
        assertThat(testCommercialWorkOrder.getRefNo()).isEqualTo(DEFAULT_REF_NO);
        assertThat(testCommercialWorkOrder.getWorkOrderDate()).isEqualTo(DEFAULT_WORK_ORDER_DATE);
        assertThat(testCommercialWorkOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testCommercialWorkOrder.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testCommercialWorkOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialWorkOrder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialWorkOrder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialWorkOrder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialWorkOrder in Elasticsearch
        verify(mockCommercialWorkOrderSearchRepository, times(1)).save(testCommercialWorkOrder);
    }

    @Test
    @Transactional
    public void createCommercialWorkOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialWorkOrderRepository.findAll().size();

        // Create the CommercialWorkOrder with an existing ID
        commercialWorkOrder.setId(1L);
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialWorkOrderMockMvc.perform(post("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialWorkOrder in the database
        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialWorkOrder in Elasticsearch
        verify(mockCommercialWorkOrderSearchRepository, times(0)).save(commercialWorkOrder);
    }

    @Test
    @Transactional
    public void checkRefNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderRepository.findAll().size();
        // set the field null
        commercialWorkOrder.setRefNo(null);

        // Create the CommercialWorkOrder, which fails.
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);

        restCommercialWorkOrderMockMvc.perform(post("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderRepository.findAll().size();
        // set the field null
        commercialWorkOrder.setWorkOrderDate(null);

        // Create the CommercialWorkOrder, which fails.
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);

        restCommercialWorkOrderMockMvc.perform(post("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialWorkOrderRepository.findAll().size();
        // set the field null
        commercialWorkOrder.setDeliveryDate(null);

        // Create the CommercialWorkOrder, which fails.
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);

        restCommercialWorkOrderMockMvc.perform(post("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrders() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].refNo").value(hasItem(DEFAULT_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].workOrderDate").value(hasItem(DEFAULT_WORK_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialWorkOrder() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get the commercialWorkOrder
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders/{id}", commercialWorkOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialWorkOrder.getId().intValue()))
            .andExpect(jsonPath("$.refNo").value(DEFAULT_REF_NO.toString()))
            .andExpect(jsonPath("$.workOrderDate").value(DEFAULT_WORK_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where refNo equals to DEFAULT_REF_NO
        defaultCommercialWorkOrderShouldBeFound("refNo.equals=" + DEFAULT_REF_NO);

        // Get all the commercialWorkOrderList where refNo equals to UPDATED_REF_NO
        defaultCommercialWorkOrderShouldNotBeFound("refNo.equals=" + UPDATED_REF_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where refNo in DEFAULT_REF_NO or UPDATED_REF_NO
        defaultCommercialWorkOrderShouldBeFound("refNo.in=" + DEFAULT_REF_NO + "," + UPDATED_REF_NO);

        // Get all the commercialWorkOrderList where refNo equals to UPDATED_REF_NO
        defaultCommercialWorkOrderShouldNotBeFound("refNo.in=" + UPDATED_REF_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where refNo is not null
        defaultCommercialWorkOrderShouldBeFound("refNo.specified=true");

        // Get all the commercialWorkOrderList where refNo is null
        defaultCommercialWorkOrderShouldNotBeFound("refNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByWorkOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where workOrderDate equals to DEFAULT_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldBeFound("workOrderDate.equals=" + DEFAULT_WORK_ORDER_DATE);

        // Get all the commercialWorkOrderList where workOrderDate equals to UPDATED_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldNotBeFound("workOrderDate.equals=" + UPDATED_WORK_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByWorkOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where workOrderDate in DEFAULT_WORK_ORDER_DATE or UPDATED_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldBeFound("workOrderDate.in=" + DEFAULT_WORK_ORDER_DATE + "," + UPDATED_WORK_ORDER_DATE);

        // Get all the commercialWorkOrderList where workOrderDate equals to UPDATED_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldNotBeFound("workOrderDate.in=" + UPDATED_WORK_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByWorkOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where workOrderDate is not null
        defaultCommercialWorkOrderShouldBeFound("workOrderDate.specified=true");

        // Get all the commercialWorkOrderList where workOrderDate is null
        defaultCommercialWorkOrderShouldNotBeFound("workOrderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByWorkOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where workOrderDate greater than or equals to DEFAULT_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldBeFound("workOrderDate.greaterOrEqualThan=" + DEFAULT_WORK_ORDER_DATE);

        // Get all the commercialWorkOrderList where workOrderDate greater than or equals to UPDATED_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldNotBeFound("workOrderDate.greaterOrEqualThan=" + UPDATED_WORK_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByWorkOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where workOrderDate less than or equals to DEFAULT_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldNotBeFound("workOrderDate.lessThan=" + DEFAULT_WORK_ORDER_DATE);

        // Get all the commercialWorkOrderList where workOrderDate less than or equals to UPDATED_WORK_ORDER_DATE
        defaultCommercialWorkOrderShouldBeFound("workOrderDate.lessThan=" + UPDATED_WORK_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultCommercialWorkOrderShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the commercialWorkOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultCommercialWorkOrderShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultCommercialWorkOrderShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the commercialWorkOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultCommercialWorkOrderShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where deliveryDate is not null
        defaultCommercialWorkOrderShouldBeFound("deliveryDate.specified=true");

        // Get all the commercialWorkOrderList where deliveryDate is null
        defaultCommercialWorkOrderShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where deliveryDate greater than or equals to DEFAULT_DELIVERY_DATE
        defaultCommercialWorkOrderShouldBeFound("deliveryDate.greaterOrEqualThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the commercialWorkOrderList where deliveryDate greater than or equals to UPDATED_DELIVERY_DATE
        defaultCommercialWorkOrderShouldNotBeFound("deliveryDate.greaterOrEqualThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where deliveryDate less than or equals to DEFAULT_DELIVERY_DATE
        defaultCommercialWorkOrderShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the commercialWorkOrderList where deliveryDate less than or equals to UPDATED_DELIVERY_DATE
        defaultCommercialWorkOrderShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where remarks equals to DEFAULT_REMARKS
        defaultCommercialWorkOrderShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the commercialWorkOrderList where remarks equals to UPDATED_REMARKS
        defaultCommercialWorkOrderShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultCommercialWorkOrderShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the commercialWorkOrderList where remarks equals to UPDATED_REMARKS
        defaultCommercialWorkOrderShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where remarks is not null
        defaultCommercialWorkOrderShouldBeFound("remarks.specified=true");

        // Get all the commercialWorkOrderList where remarks is null
        defaultCommercialWorkOrderShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialWorkOrderShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialWorkOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialWorkOrderShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialWorkOrderShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialWorkOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialWorkOrderShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdBy is not null
        defaultCommercialWorkOrderShouldBeFound("createdBy.specified=true");

        // Get all the commercialWorkOrderList where createdBy is null
        defaultCommercialWorkOrderShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialWorkOrderShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialWorkOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdOn is not null
        defaultCommercialWorkOrderShouldBeFound("createdOn.specified=true");

        // Get all the commercialWorkOrderList where createdOn is null
        defaultCommercialWorkOrderShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialWorkOrderList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialWorkOrderShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialWorkOrderShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialWorkOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialWorkOrderShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialWorkOrderShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialWorkOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialWorkOrderShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedBy is not null
        defaultCommercialWorkOrderShouldBeFound("updatedBy.specified=true");

        // Get all the commercialWorkOrderList where updatedBy is null
        defaultCommercialWorkOrderShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialWorkOrderShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialWorkOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedOn is not null
        defaultCommercialWorkOrderShouldBeFound("updatedOn.specified=true");

        // Get all the commercialWorkOrderList where updatedOn is null
        defaultCommercialWorkOrderShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        // Get all the commercialWorkOrderList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialWorkOrderShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialWorkOrderList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialWorkOrderShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialWorkOrdersByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialWorkOrder.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialWorkOrderList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialWorkOrderShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialWorkOrderList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialWorkOrderShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialWorkOrderShouldBeFound(String filter) throws Exception {
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].refNo").value(hasItem(DEFAULT_REF_NO)))
            .andExpect(jsonPath("$.[*].workOrderDate").value(hasItem(DEFAULT_WORK_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialWorkOrderShouldNotBeFound(String filter) throws Exception {
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialWorkOrder() throws Exception {
        // Get the commercialWorkOrder
        restCommercialWorkOrderMockMvc.perform(get("/api/commercial-work-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialWorkOrder() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        int databaseSizeBeforeUpdate = commercialWorkOrderRepository.findAll().size();

        // Update the commercialWorkOrder
        CommercialWorkOrder updatedCommercialWorkOrder = commercialWorkOrderRepository.findById(commercialWorkOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialWorkOrder are not directly saved in db
        em.detach(updatedCommercialWorkOrder);
        updatedCommercialWorkOrder
            .refNo(UPDATED_REF_NO)
            .workOrderDate(UPDATED_WORK_ORDER_DATE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(updatedCommercialWorkOrder);

        restCommercialWorkOrderMockMvc.perform(put("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialWorkOrder in the database
        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeUpdate);
        CommercialWorkOrder testCommercialWorkOrder = commercialWorkOrderList.get(commercialWorkOrderList.size() - 1);
        assertThat(testCommercialWorkOrder.getRefNo()).isEqualTo(UPDATED_REF_NO);
        assertThat(testCommercialWorkOrder.getWorkOrderDate()).isEqualTo(UPDATED_WORK_ORDER_DATE);
        assertThat(testCommercialWorkOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testCommercialWorkOrder.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testCommercialWorkOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialWorkOrder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialWorkOrder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialWorkOrder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialWorkOrder in Elasticsearch
        verify(mockCommercialWorkOrderSearchRepository, times(1)).save(testCommercialWorkOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialWorkOrder() throws Exception {
        int databaseSizeBeforeUpdate = commercialWorkOrderRepository.findAll().size();

        // Create the CommercialWorkOrder
        CommercialWorkOrderDTO commercialWorkOrderDTO = commercialWorkOrderMapper.toDto(commercialWorkOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialWorkOrderMockMvc.perform(put("/api/commercial-work-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialWorkOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialWorkOrder in the database
        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialWorkOrder in Elasticsearch
        verify(mockCommercialWorkOrderSearchRepository, times(0)).save(commercialWorkOrder);
    }

    @Test
    @Transactional
    public void deleteCommercialWorkOrder() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);

        int databaseSizeBeforeDelete = commercialWorkOrderRepository.findAll().size();

        // Delete the commercialWorkOrder
        restCommercialWorkOrderMockMvc.perform(delete("/api/commercial-work-orders/{id}", commercialWorkOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialWorkOrder> commercialWorkOrderList = commercialWorkOrderRepository.findAll();
        assertThat(commercialWorkOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialWorkOrder in Elasticsearch
        verify(mockCommercialWorkOrderSearchRepository, times(1)).deleteById(commercialWorkOrder.getId());
    }

    @Test
    @Transactional
    public void searchCommercialWorkOrder() throws Exception {
        // Initialize the database
        commercialWorkOrderRepository.saveAndFlush(commercialWorkOrder);
        when(mockCommercialWorkOrderSearchRepository.search(queryStringQuery("id:" + commercialWorkOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialWorkOrder), PageRequest.of(0, 1), 1));
        // Search the commercialWorkOrder
        restCommercialWorkOrderMockMvc.perform(get("/api/_search/commercial-work-orders?query=id:" + commercialWorkOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialWorkOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].refNo").value(hasItem(DEFAULT_REF_NO)))
            .andExpect(jsonPath("$.[*].workOrderDate").value(hasItem(DEFAULT_WORK_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialWorkOrder.class);
        CommercialWorkOrder commercialWorkOrder1 = new CommercialWorkOrder();
        commercialWorkOrder1.setId(1L);
        CommercialWorkOrder commercialWorkOrder2 = new CommercialWorkOrder();
        commercialWorkOrder2.setId(commercialWorkOrder1.getId());
        assertThat(commercialWorkOrder1).isEqualTo(commercialWorkOrder2);
        commercialWorkOrder2.setId(2L);
        assertThat(commercialWorkOrder1).isNotEqualTo(commercialWorkOrder2);
        commercialWorkOrder1.setId(null);
        assertThat(commercialWorkOrder1).isNotEqualTo(commercialWorkOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialWorkOrderDTO.class);
        CommercialWorkOrderDTO commercialWorkOrderDTO1 = new CommercialWorkOrderDTO();
        commercialWorkOrderDTO1.setId(1L);
        CommercialWorkOrderDTO commercialWorkOrderDTO2 = new CommercialWorkOrderDTO();
        assertThat(commercialWorkOrderDTO1).isNotEqualTo(commercialWorkOrderDTO2);
        commercialWorkOrderDTO2.setId(commercialWorkOrderDTO1.getId());
        assertThat(commercialWorkOrderDTO1).isEqualTo(commercialWorkOrderDTO2);
        commercialWorkOrderDTO2.setId(2L);
        assertThat(commercialWorkOrderDTO1).isNotEqualTo(commercialWorkOrderDTO2);
        commercialWorkOrderDTO1.setId(null);
        assertThat(commercialWorkOrderDTO1).isNotEqualTo(commercialWorkOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialWorkOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialWorkOrderMapper.fromId(null)).isNull();
    }
}
