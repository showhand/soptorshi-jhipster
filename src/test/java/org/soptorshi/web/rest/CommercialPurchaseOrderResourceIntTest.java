package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderSearchRepository;
import org.soptorshi.service.CommercialPurchaseOrderService;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialPurchaseOrderCriteria;
import org.soptorshi.service.CommercialPurchaseOrderQueryService;

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
 * Test class for the CommercialPurchaseOrderResource REST controller.
 *
 * @see CommercialPurchaseOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPurchaseOrderResourceIntTest {

    private static final String DEFAULT_PURCHASE_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORIGIN_OF_GOODS = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_OF_GOODS = "BBBBBBBBBB";

    private static final String DEFAULT_FINAL_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_DESTINATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHIPMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIPMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialPurchaseOrderRepository commercialPurchaseOrderRepository;

    @Autowired
    private CommercialPurchaseOrderMapper commercialPurchaseOrderMapper;

    @Autowired
    private CommercialPurchaseOrderService commercialPurchaseOrderService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPurchaseOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPurchaseOrderSearchRepository mockCommercialPurchaseOrderSearchRepository;

    @Autowired
    private CommercialPurchaseOrderQueryService commercialPurchaseOrderQueryService;

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

    private MockMvc restCommercialPurchaseOrderMockMvc;

    private CommercialPurchaseOrder commercialPurchaseOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPurchaseOrderResource commercialPurchaseOrderResource = new CommercialPurchaseOrderResource(commercialPurchaseOrderService, commercialPurchaseOrderQueryService);
        this.restCommercialPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(commercialPurchaseOrderResource)
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
    public static CommercialPurchaseOrder createEntity(EntityManager em) {
        CommercialPurchaseOrder commercialPurchaseOrder = new CommercialPurchaseOrder()
            .purchaseOrderNo(DEFAULT_PURCHASE_ORDER_NO)
            .purchaseOrderDate(DEFAULT_PURCHASE_ORDER_DATE)
            .originOfGoods(DEFAULT_ORIGIN_OF_GOODS)
            .finalDestination(DEFAULT_FINAL_DESTINATION)
            .shipmentDate(DEFAULT_SHIPMENT_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPurchaseOrder;
    }

    @Before
    public void initTest() {
        commercialPurchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = commercialPurchaseOrderRepository.findAll().size();

        // Create the CommercialPurchaseOrder
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);
        restCommercialPurchaseOrderMockMvc.perform(post("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPurchaseOrder in the database
        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPurchaseOrder testCommercialPurchaseOrder = commercialPurchaseOrderList.get(commercialPurchaseOrderList.size() - 1);
        assertThat(testCommercialPurchaseOrder.getPurchaseOrderNo()).isEqualTo(DEFAULT_PURCHASE_ORDER_NO);
        assertThat(testCommercialPurchaseOrder.getPurchaseOrderDate()).isEqualTo(DEFAULT_PURCHASE_ORDER_DATE);
        assertThat(testCommercialPurchaseOrder.getOriginOfGoods()).isEqualTo(DEFAULT_ORIGIN_OF_GOODS);
        assertThat(testCommercialPurchaseOrder.getFinalDestination()).isEqualTo(DEFAULT_FINAL_DESTINATION);
        assertThat(testCommercialPurchaseOrder.getShipmentDate()).isEqualTo(DEFAULT_SHIPMENT_DATE);
        assertThat(testCommercialPurchaseOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPurchaseOrder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPurchaseOrder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPurchaseOrder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPurchaseOrder in Elasticsearch
        verify(mockCommercialPurchaseOrderSearchRepository, times(1)).save(testCommercialPurchaseOrder);
    }

    @Test
    @Transactional
    public void createCommercialPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPurchaseOrderRepository.findAll().size();

        // Create the CommercialPurchaseOrder with an existing ID
        commercialPurchaseOrder.setId(1L);
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPurchaseOrderMockMvc.perform(post("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPurchaseOrder in the database
        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPurchaseOrder in Elasticsearch
        verify(mockCommercialPurchaseOrderSearchRepository, times(0)).save(commercialPurchaseOrder);
    }

    @Test
    @Transactional
    public void checkPurchaseOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderRepository.findAll().size();
        // set the field null
        commercialPurchaseOrder.setPurchaseOrderNo(null);

        // Create the CommercialPurchaseOrder, which fails.
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);

        restCommercialPurchaseOrderMockMvc.perform(post("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderRepository.findAll().size();
        // set the field null
        commercialPurchaseOrder.setPurchaseOrderDate(null);

        // Create the CommercialPurchaseOrder, which fails.
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);

        restCommercialPurchaseOrderMockMvc.perform(post("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShipmentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderRepository.findAll().size();
        // set the field null
        commercialPurchaseOrder.setShipmentDate(null);

        // Create the CommercialPurchaseOrder, which fails.
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);

        restCommercialPurchaseOrderMockMvc.perform(post("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrders() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS.toString())))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialPurchaseOrder() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get the commercialPurchaseOrder
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders/{id}", commercialPurchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPurchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.purchaseOrderNo").value(DEFAULT_PURCHASE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.purchaseOrderDate").value(DEFAULT_PURCHASE_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.originOfGoods").value(DEFAULT_ORIGIN_OF_GOODS.toString()))
            .andExpect(jsonPath("$.finalDestination").value(DEFAULT_FINAL_DESTINATION.toString()))
            .andExpect(jsonPath("$.shipmentDate").value(DEFAULT_SHIPMENT_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderNo equals to DEFAULT_PURCHASE_ORDER_NO
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderNo.equals=" + DEFAULT_PURCHASE_ORDER_NO);

        // Get all the commercialPurchaseOrderList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderNo.equals=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderNo in DEFAULT_PURCHASE_ORDER_NO or UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderNo.in=" + DEFAULT_PURCHASE_ORDER_NO + "," + UPDATED_PURCHASE_ORDER_NO);

        // Get all the commercialPurchaseOrderList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderNo.in=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderNo is not null
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderNo.specified=true");

        // Get all the commercialPurchaseOrderList where purchaseOrderNo is null
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderDate.equals=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderDate.equals=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate in DEFAULT_PURCHASE_ORDER_DATE or UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderDate.in=" + DEFAULT_PURCHASE_ORDER_DATE + "," + UPDATED_PURCHASE_ORDER_DATE);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderDate.in=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate is not null
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderDate.specified=true");

        // Get all the commercialPurchaseOrderList where purchaseOrderDate is null
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate greater than or equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderDate.greaterOrEqualThan=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate greater than or equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderDate.greaterOrEqualThan=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByPurchaseOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate less than or equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("purchaseOrderDate.lessThan=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPurchaseOrderList where purchaseOrderDate less than or equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPurchaseOrderShouldBeFound("purchaseOrderDate.lessThan=" + UPDATED_PURCHASE_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByOriginOfGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where originOfGoods equals to DEFAULT_ORIGIN_OF_GOODS
        defaultCommercialPurchaseOrderShouldBeFound("originOfGoods.equals=" + DEFAULT_ORIGIN_OF_GOODS);

        // Get all the commercialPurchaseOrderList where originOfGoods equals to UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPurchaseOrderShouldNotBeFound("originOfGoods.equals=" + UPDATED_ORIGIN_OF_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByOriginOfGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where originOfGoods in DEFAULT_ORIGIN_OF_GOODS or UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPurchaseOrderShouldBeFound("originOfGoods.in=" + DEFAULT_ORIGIN_OF_GOODS + "," + UPDATED_ORIGIN_OF_GOODS);

        // Get all the commercialPurchaseOrderList where originOfGoods equals to UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPurchaseOrderShouldNotBeFound("originOfGoods.in=" + UPDATED_ORIGIN_OF_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByOriginOfGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where originOfGoods is not null
        defaultCommercialPurchaseOrderShouldBeFound("originOfGoods.specified=true");

        // Get all the commercialPurchaseOrderList where originOfGoods is null
        defaultCommercialPurchaseOrderShouldNotBeFound("originOfGoods.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByFinalDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where finalDestination equals to DEFAULT_FINAL_DESTINATION
        defaultCommercialPurchaseOrderShouldBeFound("finalDestination.equals=" + DEFAULT_FINAL_DESTINATION);

        // Get all the commercialPurchaseOrderList where finalDestination equals to UPDATED_FINAL_DESTINATION
        defaultCommercialPurchaseOrderShouldNotBeFound("finalDestination.equals=" + UPDATED_FINAL_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByFinalDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where finalDestination in DEFAULT_FINAL_DESTINATION or UPDATED_FINAL_DESTINATION
        defaultCommercialPurchaseOrderShouldBeFound("finalDestination.in=" + DEFAULT_FINAL_DESTINATION + "," + UPDATED_FINAL_DESTINATION);

        // Get all the commercialPurchaseOrderList where finalDestination equals to UPDATED_FINAL_DESTINATION
        defaultCommercialPurchaseOrderShouldNotBeFound("finalDestination.in=" + UPDATED_FINAL_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByFinalDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where finalDestination is not null
        defaultCommercialPurchaseOrderShouldBeFound("finalDestination.specified=true");

        // Get all the commercialPurchaseOrderList where finalDestination is null
        defaultCommercialPurchaseOrderShouldNotBeFound("finalDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByShipmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where shipmentDate equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldBeFound("shipmentDate.equals=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPurchaseOrderList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("shipmentDate.equals=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByShipmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where shipmentDate in DEFAULT_SHIPMENT_DATE or UPDATED_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldBeFound("shipmentDate.in=" + DEFAULT_SHIPMENT_DATE + "," + UPDATED_SHIPMENT_DATE);

        // Get all the commercialPurchaseOrderList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("shipmentDate.in=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByShipmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where shipmentDate is not null
        defaultCommercialPurchaseOrderShouldBeFound("shipmentDate.specified=true");

        // Get all the commercialPurchaseOrderList where shipmentDate is null
        defaultCommercialPurchaseOrderShouldNotBeFound("shipmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByShipmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where shipmentDate greater than or equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldBeFound("shipmentDate.greaterOrEqualThan=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPurchaseOrderList where shipmentDate greater than or equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("shipmentDate.greaterOrEqualThan=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByShipmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where shipmentDate less than or equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldNotBeFound("shipmentDate.lessThan=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPurchaseOrderList where shipmentDate less than or equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPurchaseOrderShouldBeFound("shipmentDate.lessThan=" + UPDATED_SHIPMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPurchaseOrderShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPurchaseOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPurchaseOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdBy is not null
        defaultCommercialPurchaseOrderShouldBeFound("createdBy.specified=true");

        // Get all the commercialPurchaseOrderList where createdBy is null
        defaultCommercialPurchaseOrderShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPurchaseOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdOn is not null
        defaultCommercialPurchaseOrderShouldBeFound("createdOn.specified=true");

        // Get all the commercialPurchaseOrderList where createdOn is null
        defaultCommercialPurchaseOrderShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPurchaseOrderShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPurchaseOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPurchaseOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedBy is not null
        defaultCommercialPurchaseOrderShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPurchaseOrderList where updatedBy is null
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPurchaseOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedOn is not null
        defaultCommercialPurchaseOrderShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPurchaseOrderList where updatedOn is null
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrdersByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        // Get all the commercialPurchaseOrderList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPurchaseOrderShouldBeFound(String filter) throws Exception {
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS)))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPurchaseOrderShouldNotBeFound(String filter) throws Exception {
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPurchaseOrder() throws Exception {
        // Get the commercialPurchaseOrder
        restCommercialPurchaseOrderMockMvc.perform(get("/api/commercial-purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPurchaseOrder() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        int databaseSizeBeforeUpdate = commercialPurchaseOrderRepository.findAll().size();

        // Update the commercialPurchaseOrder
        CommercialPurchaseOrder updatedCommercialPurchaseOrder = commercialPurchaseOrderRepository.findById(commercialPurchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPurchaseOrder are not directly saved in db
        em.detach(updatedCommercialPurchaseOrder);
        updatedCommercialPurchaseOrder
            .purchaseOrderNo(UPDATED_PURCHASE_ORDER_NO)
            .purchaseOrderDate(UPDATED_PURCHASE_ORDER_DATE)
            .originOfGoods(UPDATED_ORIGIN_OF_GOODS)
            .finalDestination(UPDATED_FINAL_DESTINATION)
            .shipmentDate(UPDATED_SHIPMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(updatedCommercialPurchaseOrder);

        restCommercialPurchaseOrderMockMvc.perform(put("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPurchaseOrder in the database
        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        CommercialPurchaseOrder testCommercialPurchaseOrder = commercialPurchaseOrderList.get(commercialPurchaseOrderList.size() - 1);
        assertThat(testCommercialPurchaseOrder.getPurchaseOrderNo()).isEqualTo(UPDATED_PURCHASE_ORDER_NO);
        assertThat(testCommercialPurchaseOrder.getPurchaseOrderDate()).isEqualTo(UPDATED_PURCHASE_ORDER_DATE);
        assertThat(testCommercialPurchaseOrder.getOriginOfGoods()).isEqualTo(UPDATED_ORIGIN_OF_GOODS);
        assertThat(testCommercialPurchaseOrder.getFinalDestination()).isEqualTo(UPDATED_FINAL_DESTINATION);
        assertThat(testCommercialPurchaseOrder.getShipmentDate()).isEqualTo(UPDATED_SHIPMENT_DATE);
        assertThat(testCommercialPurchaseOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPurchaseOrder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPurchaseOrder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPurchaseOrder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPurchaseOrder in Elasticsearch
        verify(mockCommercialPurchaseOrderSearchRepository, times(1)).save(testCommercialPurchaseOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = commercialPurchaseOrderRepository.findAll().size();

        // Create the CommercialPurchaseOrder
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = commercialPurchaseOrderMapper.toDto(commercialPurchaseOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPurchaseOrderMockMvc.perform(put("/api/commercial-purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPurchaseOrder in the database
        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPurchaseOrder in Elasticsearch
        verify(mockCommercialPurchaseOrderSearchRepository, times(0)).save(commercialPurchaseOrder);
    }

    @Test
    @Transactional
    public void deleteCommercialPurchaseOrder() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);

        int databaseSizeBeforeDelete = commercialPurchaseOrderRepository.findAll().size();

        // Delete the commercialPurchaseOrder
        restCommercialPurchaseOrderMockMvc.perform(delete("/api/commercial-purchase-orders/{id}", commercialPurchaseOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPurchaseOrder> commercialPurchaseOrderList = commercialPurchaseOrderRepository.findAll();
        assertThat(commercialPurchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPurchaseOrder in Elasticsearch
        verify(mockCommercialPurchaseOrderSearchRepository, times(1)).deleteById(commercialPurchaseOrder.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPurchaseOrder() throws Exception {
        // Initialize the database
        commercialPurchaseOrderRepository.saveAndFlush(commercialPurchaseOrder);
        when(mockCommercialPurchaseOrderSearchRepository.search(queryStringQuery("id:" + commercialPurchaseOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPurchaseOrder), PageRequest.of(0, 1), 1));
        // Search the commercialPurchaseOrder
        restCommercialPurchaseOrderMockMvc.perform(get("/api/_search/commercial-purchase-orders?query=id:" + commercialPurchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS)))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPurchaseOrder.class);
        CommercialPurchaseOrder commercialPurchaseOrder1 = new CommercialPurchaseOrder();
        commercialPurchaseOrder1.setId(1L);
        CommercialPurchaseOrder commercialPurchaseOrder2 = new CommercialPurchaseOrder();
        commercialPurchaseOrder2.setId(commercialPurchaseOrder1.getId());
        assertThat(commercialPurchaseOrder1).isEqualTo(commercialPurchaseOrder2);
        commercialPurchaseOrder2.setId(2L);
        assertThat(commercialPurchaseOrder1).isNotEqualTo(commercialPurchaseOrder2);
        commercialPurchaseOrder1.setId(null);
        assertThat(commercialPurchaseOrder1).isNotEqualTo(commercialPurchaseOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPurchaseOrderDTO.class);
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO1 = new CommercialPurchaseOrderDTO();
        commercialPurchaseOrderDTO1.setId(1L);
        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO2 = new CommercialPurchaseOrderDTO();
        assertThat(commercialPurchaseOrderDTO1).isNotEqualTo(commercialPurchaseOrderDTO2);
        commercialPurchaseOrderDTO2.setId(commercialPurchaseOrderDTO1.getId());
        assertThat(commercialPurchaseOrderDTO1).isEqualTo(commercialPurchaseOrderDTO2);
        commercialPurchaseOrderDTO2.setId(2L);
        assertThat(commercialPurchaseOrderDTO1).isNotEqualTo(commercialPurchaseOrderDTO2);
        commercialPurchaseOrderDTO1.setId(null);
        assertThat(commercialPurchaseOrderDTO1).isNotEqualTo(commercialPurchaseOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPurchaseOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPurchaseOrderMapper.fromId(null)).isNull();
    }
}
