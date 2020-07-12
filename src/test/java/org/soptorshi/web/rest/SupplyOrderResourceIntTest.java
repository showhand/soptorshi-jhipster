package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.repository.SupplyOrderRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.service.SupplyOrderQueryService;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the SupplyOrderResource REST controller.
 *
 * @see SupplyOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyOrderResourceIntTest {

    private static final String DEFAULT_ORDER_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_ORDER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ORDER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final SupplyOrderStatus DEFAULT_STATUS = SupplyOrderStatus.ORDER_RECEIVED;
    private static final SupplyOrderStatus UPDATED_STATUS = SupplyOrderStatus.PROCESSING_ORDER;

    private static final String DEFAULT_AREA_WISE_ACCUMULATION_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_AREA_WISE_ACCUMULATION_REF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private SupplyOrderRepository supplyOrderRepository;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;

    @Autowired
    private SupplyOrderService supplyOrderService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyOrderSearchRepository mockSupplyOrderSearchRepository;

    @Autowired
    private SupplyOrderQueryService supplyOrderQueryService;

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

    private MockMvc restSupplyOrderMockMvc;

    private SupplyOrder supplyOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyOrderResource supplyOrderResource = new SupplyOrderResource(supplyOrderService, supplyOrderQueryService);
        this.restSupplyOrderMockMvc = MockMvcBuilders.standaloneSetup(supplyOrderResource)
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
    public static SupplyOrder createEntity(EntityManager em) {
        SupplyOrder supplyOrder = new SupplyOrder()
            .orderNo(DEFAULT_ORDER_NO)
            .dateOfOrder(DEFAULT_DATE_OF_ORDER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .status(DEFAULT_STATUS)
            .areaWiseAccumulationRefNo(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyOrder.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyOrder.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyOrder.setSupplyAreaManager(supplyAreaManager);
        // Add required entity
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyOrder.setSupplyShop(supplyShop);
        return supplyOrder;
    }

    @Before
    public void initTest() {
        supplyOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyOrder() throws Exception {
        int databaseSizeBeforeCreate = supplyOrderRepository.findAll().size();

        // Create the SupplyOrder
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);
        restSupplyOrderMockMvc.perform(post("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyOrder in the database
        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyOrder testSupplyOrder = supplyOrderList.get(supplyOrderList.size() - 1);
        assertThat(testSupplyOrder.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testSupplyOrder.getDateOfOrder()).isEqualTo(DEFAULT_DATE_OF_ORDER);
        assertThat(testSupplyOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyOrder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyOrder.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyOrder.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testSupplyOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testSupplyOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSupplyOrder.getAreaWiseAccumulationRefNo()).isEqualTo(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyOrder.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the SupplyOrder in Elasticsearch
        verify(mockSupplyOrderSearchRepository, times(1)).save(testSupplyOrder);
    }

    @Test
    @Transactional
    public void createSupplyOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyOrderRepository.findAll().size();

        // Create the SupplyOrder with an existing ID
        supplyOrder.setId(1L);
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyOrderMockMvc.perform(post("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyOrder in the database
        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyOrder in Elasticsearch
        verify(mockSupplyOrderSearchRepository, times(0)).save(supplyOrder);
    }

    @Test
    @Transactional
    public void checkOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderRepository.findAll().size();
        // set the field null
        supplyOrder.setOrderNo(null);

        // Create the SupplyOrder, which fails.
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);

        restSupplyOrderMockMvc.perform(post("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyOrderRepository.findAll().size();
        // set the field null
        supplyOrder.setStatus(null);

        // Create the SupplyOrder, which fails.
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);

        restSupplyOrderMockMvc.perform(post("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyOrders() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList
        restSupplyOrderMockMvc.perform(get("/api/supply-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].dateOfOrder").value(hasItem(DEFAULT_DATE_OF_ORDER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getSupplyOrder() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get the supplyOrder
        restSupplyOrderMockMvc.perform(get("/api/supply-orders/{id}", supplyOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.toString()))
            .andExpect(jsonPath("$.dateOfOrder").value(DEFAULT_DATE_OF_ORDER.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.areaWiseAccumulationRefNo").value(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where orderNo equals to DEFAULT_ORDER_NO
        defaultSupplyOrderShouldBeFound("orderNo.equals=" + DEFAULT_ORDER_NO);

        // Get all the supplyOrderList where orderNo equals to UPDATED_ORDER_NO
        defaultSupplyOrderShouldNotBeFound("orderNo.equals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where orderNo in DEFAULT_ORDER_NO or UPDATED_ORDER_NO
        defaultSupplyOrderShouldBeFound("orderNo.in=" + DEFAULT_ORDER_NO + "," + UPDATED_ORDER_NO);

        // Get all the supplyOrderList where orderNo equals to UPDATED_ORDER_NO
        defaultSupplyOrderShouldNotBeFound("orderNo.in=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where orderNo is not null
        defaultSupplyOrderShouldBeFound("orderNo.specified=true");

        // Get all the supplyOrderList where orderNo is null
        defaultSupplyOrderShouldNotBeFound("orderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDateOfOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where dateOfOrder equals to DEFAULT_DATE_OF_ORDER
        defaultSupplyOrderShouldBeFound("dateOfOrder.equals=" + DEFAULT_DATE_OF_ORDER);

        // Get all the supplyOrderList where dateOfOrder equals to UPDATED_DATE_OF_ORDER
        defaultSupplyOrderShouldNotBeFound("dateOfOrder.equals=" + UPDATED_DATE_OF_ORDER);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDateOfOrderIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where dateOfOrder in DEFAULT_DATE_OF_ORDER or UPDATED_DATE_OF_ORDER
        defaultSupplyOrderShouldBeFound("dateOfOrder.in=" + DEFAULT_DATE_OF_ORDER + "," + UPDATED_DATE_OF_ORDER);

        // Get all the supplyOrderList where dateOfOrder equals to UPDATED_DATE_OF_ORDER
        defaultSupplyOrderShouldNotBeFound("dateOfOrder.in=" + UPDATED_DATE_OF_ORDER);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDateOfOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where dateOfOrder is not null
        defaultSupplyOrderShouldBeFound("dateOfOrder.specified=true");

        // Get all the supplyOrderList where dateOfOrder is null
        defaultSupplyOrderShouldNotBeFound("dateOfOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDateOfOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where dateOfOrder greater than or equals to DEFAULT_DATE_OF_ORDER
        defaultSupplyOrderShouldBeFound("dateOfOrder.greaterOrEqualThan=" + DEFAULT_DATE_OF_ORDER);

        // Get all the supplyOrderList where dateOfOrder greater than or equals to UPDATED_DATE_OF_ORDER
        defaultSupplyOrderShouldNotBeFound("dateOfOrder.greaterOrEqualThan=" + UPDATED_DATE_OF_ORDER);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDateOfOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where dateOfOrder less than or equals to DEFAULT_DATE_OF_ORDER
        defaultSupplyOrderShouldNotBeFound("dateOfOrder.lessThan=" + DEFAULT_DATE_OF_ORDER);

        // Get all the supplyOrderList where dateOfOrder less than or equals to UPDATED_DATE_OF_ORDER
        defaultSupplyOrderShouldBeFound("dateOfOrder.lessThan=" + UPDATED_DATE_OF_ORDER);
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyOrderShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyOrderShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyOrderShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyOrderShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdBy is not null
        defaultSupplyOrderShouldBeFound("createdBy.specified=true");

        // Get all the supplyOrderList where createdBy is null
        defaultSupplyOrderShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyOrderShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyOrderShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyOrderShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyOrderShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where createdOn is not null
        defaultSupplyOrderShouldBeFound("createdOn.specified=true");

        // Get all the supplyOrderList where createdOn is null
        defaultSupplyOrderShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyOrderShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyOrderShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyOrderShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyOrderList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyOrderShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedBy is not null
        defaultSupplyOrderShouldBeFound("updatedBy.specified=true");

        // Get all the supplyOrderList where updatedBy is null
        defaultSupplyOrderShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyOrderShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyOrderShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyOrderShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyOrderList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyOrderShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where updatedOn is not null
        defaultSupplyOrderShouldBeFound("updatedOn.specified=true");

        // Get all the supplyOrderList where updatedOn is null
        defaultSupplyOrderShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultSupplyOrderShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the supplyOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultSupplyOrderShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultSupplyOrderShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the supplyOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultSupplyOrderShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where deliveryDate is not null
        defaultSupplyOrderShouldBeFound("deliveryDate.specified=true");

        // Get all the supplyOrderList where deliveryDate is null
        defaultSupplyOrderShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where deliveryDate greater than or equals to DEFAULT_DELIVERY_DATE
        defaultSupplyOrderShouldBeFound("deliveryDate.greaterOrEqualThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the supplyOrderList where deliveryDate greater than or equals to UPDATED_DELIVERY_DATE
        defaultSupplyOrderShouldNotBeFound("deliveryDate.greaterOrEqualThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where deliveryDate less than or equals to DEFAULT_DELIVERY_DATE
        defaultSupplyOrderShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the supplyOrderList where deliveryDate less than or equals to UPDATED_DELIVERY_DATE
        defaultSupplyOrderShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where status equals to DEFAULT_STATUS
        defaultSupplyOrderShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the supplyOrderList where status equals to UPDATED_STATUS
        defaultSupplyOrderShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSupplyOrderShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the supplyOrderList where status equals to UPDATED_STATUS
        defaultSupplyOrderShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where status is not null
        defaultSupplyOrderShouldBeFound("status.specified=true");

        // Get all the supplyOrderList where status is null
        defaultSupplyOrderShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByAreaWiseAccumulationRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where areaWiseAccumulationRefNo equals to DEFAULT_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyOrderShouldBeFound("areaWiseAccumulationRefNo.equals=" + DEFAULT_AREA_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyOrderList where areaWiseAccumulationRefNo equals to UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyOrderShouldNotBeFound("areaWiseAccumulationRefNo.equals=" + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByAreaWiseAccumulationRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where areaWiseAccumulationRefNo in DEFAULT_AREA_WISE_ACCUMULATION_REF_NO or UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyOrderShouldBeFound("areaWiseAccumulationRefNo.in=" + DEFAULT_AREA_WISE_ACCUMULATION_REF_NO + "," + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);

        // Get all the supplyOrderList where areaWiseAccumulationRefNo equals to UPDATED_AREA_WISE_ACCUMULATION_REF_NO
        defaultSupplyOrderShouldNotBeFound("areaWiseAccumulationRefNo.in=" + UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByAreaWiseAccumulationRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where areaWiseAccumulationRefNo is not null
        defaultSupplyOrderShouldBeFound("areaWiseAccumulationRefNo.specified=true");

        // Get all the supplyOrderList where areaWiseAccumulationRefNo is null
        defaultSupplyOrderShouldNotBeFound("areaWiseAccumulationRefNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where remarks equals to DEFAULT_REMARKS
        defaultSupplyOrderShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the supplyOrderList where remarks equals to UPDATED_REMARKS
        defaultSupplyOrderShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultSupplyOrderShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the supplyOrderList where remarks equals to UPDATED_REMARKS
        defaultSupplyOrderShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        // Get all the supplyOrderList where remarks is not null
        defaultSupplyOrderShouldBeFound("remarks.specified=true");

        // Get all the supplyOrderList where remarks is null
        defaultSupplyOrderShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyOrder.setSupplyZone(supplyZone);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyOrderList where supplyZone equals to supplyZoneId
        defaultSupplyOrderShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyOrderList where supplyZone equals to supplyZoneId + 1
        defaultSupplyOrderShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyOrder.setSupplyZoneManager(supplyZoneManager);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyOrderList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyOrderShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyOrderList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyOrderShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyOrder.setSupplyArea(supplyArea);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyOrderList where supplyArea equals to supplyAreaId
        defaultSupplyOrderShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyOrderList where supplyArea equals to supplyAreaId + 1
        defaultSupplyOrderShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplySalesRepresentativeIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyOrder.setSupplySalesRepresentative(supplySalesRepresentative);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplySalesRepresentativeId = supplySalesRepresentative.getId();

        // Get all the supplyOrderList where supplySalesRepresentative equals to supplySalesRepresentativeId
        defaultSupplyOrderShouldBeFound("supplySalesRepresentativeId.equals=" + supplySalesRepresentativeId);

        // Get all the supplyOrderList where supplySalesRepresentative equals to supplySalesRepresentativeId + 1
        defaultSupplyOrderShouldNotBeFound("supplySalesRepresentativeId.equals=" + (supplySalesRepresentativeId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyOrder.setSupplyAreaManager(supplyAreaManager);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplyOrderList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplyOrderShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplyOrderList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplyOrderShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyOrdersBySupplyShopIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyShop supplyShop = SupplyShopResourceIntTest.createEntity(em);
        em.persist(supplyShop);
        em.flush();
        supplyOrder.setSupplyShop(supplyShop);
        supplyOrderRepository.saveAndFlush(supplyOrder);
        Long supplyShopId = supplyShop.getId();

        // Get all the supplyOrderList where supplyShop equals to supplyShopId
        defaultSupplyOrderShouldBeFound("supplyShopId.equals=" + supplyShopId);

        // Get all the supplyOrderList where supplyShop equals to supplyShopId + 1
        defaultSupplyOrderShouldNotBeFound("supplyShopId.equals=" + (supplyShopId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyOrderShouldBeFound(String filter) throws Exception {
        restSupplyOrderMockMvc.perform(get("/api/supply-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].dateOfOrder").value(hasItem(DEFAULT_DATE_OF_ORDER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restSupplyOrderMockMvc.perform(get("/api/supply-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyOrderShouldNotBeFound(String filter) throws Exception {
        restSupplyOrderMockMvc.perform(get("/api/supply-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyOrderMockMvc.perform(get("/api/supply-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyOrder() throws Exception {
        // Get the supplyOrder
        restSupplyOrderMockMvc.perform(get("/api/supply-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyOrder() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        int databaseSizeBeforeUpdate = supplyOrderRepository.findAll().size();

        // Update the supplyOrder
        SupplyOrder updatedSupplyOrder = supplyOrderRepository.findById(supplyOrder.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyOrder are not directly saved in db
        em.detach(updatedSupplyOrder);
        updatedSupplyOrder
            .orderNo(UPDATED_ORDER_NO)
            .dateOfOrder(UPDATED_DATE_OF_ORDER)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .status(UPDATED_STATUS)
            .areaWiseAccumulationRefNo(UPDATED_AREA_WISE_ACCUMULATION_REF_NO)
            .remarks(UPDATED_REMARKS);
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(updatedSupplyOrder);

        restSupplyOrderMockMvc.perform(put("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyOrder in the database
        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeUpdate);
        SupplyOrder testSupplyOrder = supplyOrderList.get(supplyOrderList.size() - 1);
        assertThat(testSupplyOrder.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testSupplyOrder.getDateOfOrder()).isEqualTo(UPDATED_DATE_OF_ORDER);
        assertThat(testSupplyOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyOrder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyOrder.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyOrder.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testSupplyOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testSupplyOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSupplyOrder.getAreaWiseAccumulationRefNo()).isEqualTo(UPDATED_AREA_WISE_ACCUMULATION_REF_NO);
        assertThat(testSupplyOrder.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the SupplyOrder in Elasticsearch
        verify(mockSupplyOrderSearchRepository, times(1)).save(testSupplyOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyOrder() throws Exception {
        int databaseSizeBeforeUpdate = supplyOrderRepository.findAll().size();

        // Create the SupplyOrder
        SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyOrderMockMvc.perform(put("/api/supply-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyOrder in the database
        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyOrder in Elasticsearch
        verify(mockSupplyOrderSearchRepository, times(0)).save(supplyOrder);
    }

    @Test
    @Transactional
    public void deleteSupplyOrder() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);

        int databaseSizeBeforeDelete = supplyOrderRepository.findAll().size();

        // Delete the supplyOrder
        restSupplyOrderMockMvc.perform(delete("/api/supply-orders/{id}", supplyOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyOrder> supplyOrderList = supplyOrderRepository.findAll();
        assertThat(supplyOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyOrder in Elasticsearch
        verify(mockSupplyOrderSearchRepository, times(1)).deleteById(supplyOrder.getId());
    }

    @Test
    @Transactional
    public void searchSupplyOrder() throws Exception {
        // Initialize the database
        supplyOrderRepository.saveAndFlush(supplyOrder);
        when(mockSupplyOrderSearchRepository.search(queryStringQuery("id:" + supplyOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyOrder), PageRequest.of(0, 1), 1));
        // Search the supplyOrder
        restSupplyOrderMockMvc.perform(get("/api/_search/supply-orders?query=id:" + supplyOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO)))
            .andExpect(jsonPath("$.[*].dateOfOrder").value(hasItem(DEFAULT_DATE_OF_ORDER.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].areaWiseAccumulationRefNo").value(hasItem(DEFAULT_AREA_WISE_ACCUMULATION_REF_NO)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyOrder.class);
        SupplyOrder supplyOrder1 = new SupplyOrder();
        supplyOrder1.setId(1L);
        SupplyOrder supplyOrder2 = new SupplyOrder();
        supplyOrder2.setId(supplyOrder1.getId());
        assertThat(supplyOrder1).isEqualTo(supplyOrder2);
        supplyOrder2.setId(2L);
        assertThat(supplyOrder1).isNotEqualTo(supplyOrder2);
        supplyOrder1.setId(null);
        assertThat(supplyOrder1).isNotEqualTo(supplyOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyOrderDTO.class);
        SupplyOrderDTO supplyOrderDTO1 = new SupplyOrderDTO();
        supplyOrderDTO1.setId(1L);
        SupplyOrderDTO supplyOrderDTO2 = new SupplyOrderDTO();
        assertThat(supplyOrderDTO1).isNotEqualTo(supplyOrderDTO2);
        supplyOrderDTO2.setId(supplyOrderDTO1.getId());
        assertThat(supplyOrderDTO1).isEqualTo(supplyOrderDTO2);
        supplyOrderDTO2.setId(2L);
        assertThat(supplyOrderDTO1).isNotEqualTo(supplyOrderDTO2);
        supplyOrderDTO1.setId(null);
        assertThat(supplyOrderDTO1).isNotEqualTo(supplyOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyOrderMapper.fromId(null)).isNull();
    }
}
