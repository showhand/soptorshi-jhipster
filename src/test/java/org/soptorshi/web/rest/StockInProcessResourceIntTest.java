package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.StockInProcess;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.Product;
import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.service.StockInProcessService;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.StockInProcessCriteria;
import org.soptorshi.service.StockInProcessQueryService;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.domain.enumeration.ContainerCategory;
/**
 * Test class for the StockInProcessResource REST controller.
 *
 * @see StockInProcessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class StockInProcessResourceIntTest {

    private static final Double DEFAULT_TOTAL_QUANTITY = 1D;
    private static final Double UPDATED_TOTAL_QUANTITY = 2D;

    private static final UnitOfMeasurements DEFAULT_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_UNIT = UnitOfMeasurements.KG;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Integer DEFAULT_TOTAL_CONTAINER = 1;
    private static final Integer UPDATED_TOTAL_CONTAINER = 2;

    private static final ContainerCategory DEFAULT_CONTAINER_CATEGORY = ContainerCategory.BOTTLE;
    private static final ContainerCategory UPDATED_CONTAINER_CATEGORY = ContainerCategory.DRUM;

    private static final String DEFAULT_CONTAINER_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_TRACKING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY_PER_CONTAINER = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY_PER_CONTAINER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STOCK_IN_BY = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_IN_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_STOCK_IN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOCK_IN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PURCHASE_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private StockInProcessRepository stockInProcessRepository;

    @Autowired
    private StockInProcessMapper stockInProcessMapper;

    @Autowired
    private StockInProcessService stockInProcessService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.StockInProcessSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockInProcessSearchRepository mockStockInProcessSearchRepository;

    @Autowired
    private StockInProcessQueryService stockInProcessQueryService;

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

    private MockMvc restStockInProcessMockMvc;

    private StockInProcess stockInProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockInProcessResource stockInProcessResource = new StockInProcessResource(stockInProcessService, stockInProcessQueryService);
        this.restStockInProcessMockMvc = MockMvcBuilders.standaloneSetup(stockInProcessResource)
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
    public static StockInProcess createEntity(EntityManager em) {
        StockInProcess stockInProcess = new StockInProcess()
            .totalQuantity(DEFAULT_TOTAL_QUANTITY)
            .unit(DEFAULT_UNIT)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .totalContainer(DEFAULT_TOTAL_CONTAINER)
            .containerCategory(DEFAULT_CONTAINER_CATEGORY)
            .containerTrackingId(DEFAULT_CONTAINER_TRACKING_ID)
            .quantityPerContainer(DEFAULT_QUANTITY_PER_CONTAINER)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .stockInBy(DEFAULT_STOCK_IN_BY)
            .stockInDate(DEFAULT_STOCK_IN_DATE)
            .purchaseOrderId(DEFAULT_PURCHASE_ORDER_ID)
            .remarks(DEFAULT_REMARKS);
        return stockInProcess;
    }

    @Before
    public void initTest() {
        stockInProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockInProcess() throws Exception {
        int databaseSizeBeforeCreate = stockInProcessRepository.findAll().size();

        // Create the StockInProcess
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);
        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the StockInProcess in the database
        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeCreate + 1);
        StockInProcess testStockInProcess = stockInProcessList.get(stockInProcessList.size() - 1);
        assertThat(testStockInProcess.getTotalQuantity()).isEqualTo(DEFAULT_TOTAL_QUANTITY);
        assertThat(testStockInProcess.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testStockInProcess.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testStockInProcess.getTotalContainer()).isEqualTo(DEFAULT_TOTAL_CONTAINER);
        assertThat(testStockInProcess.getContainerCategory()).isEqualTo(DEFAULT_CONTAINER_CATEGORY);
        assertThat(testStockInProcess.getContainerTrackingId()).isEqualTo(DEFAULT_CONTAINER_TRACKING_ID);
        assertThat(testStockInProcess.getQuantityPerContainer()).isEqualTo(DEFAULT_QUANTITY_PER_CONTAINER);
        assertThat(testStockInProcess.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testStockInProcess.getStockInBy()).isEqualTo(DEFAULT_STOCK_IN_BY);
        assertThat(testStockInProcess.getStockInDate()).isEqualTo(DEFAULT_STOCK_IN_DATE);
        assertThat(testStockInProcess.getPurchaseOrderId()).isEqualTo(DEFAULT_PURCHASE_ORDER_ID);
        assertThat(testStockInProcess.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the StockInProcess in Elasticsearch
        verify(mockStockInProcessSearchRepository, times(1)).save(testStockInProcess);
    }

    @Test
    @Transactional
    public void createStockInProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockInProcessRepository.findAll().size();

        // Create the StockInProcess with an existing ID
        stockInProcess.setId(1L);
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockInProcess in the database
        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockInProcess in Elasticsearch
        verify(mockStockInProcessSearchRepository, times(0)).save(stockInProcess);
    }

    @Test
    @Transactional
    public void checkTotalQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setTotalQuantity(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setUnit(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setUnitPrice(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalContainerIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setTotalContainer(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContainerCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setContainerCategory(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContainerTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setContainerTrackingId(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityPerContainerIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInProcessRepository.findAll().size();
        // set the field null
        stockInProcess.setQuantityPerContainer(null);

        // Create the StockInProcess, which fails.
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        restStockInProcessMockMvc.perform(post("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockInProcesses() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalContainer").value(hasItem(DEFAULT_TOTAL_CONTAINER)))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID.toString())))
            .andExpect(jsonPath("$.[*].quantityPerContainer").value(hasItem(DEFAULT_QUANTITY_PER_CONTAINER.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY.toString())))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getStockInProcess() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get the stockInProcess
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes/{id}", stockInProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockInProcess.getId().intValue()))
            .andExpect(jsonPath("$.totalQuantity").value(DEFAULT_TOTAL_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.totalContainer").value(DEFAULT_TOTAL_CONTAINER))
            .andExpect(jsonPath("$.containerCategory").value(DEFAULT_CONTAINER_CATEGORY.toString()))
            .andExpect(jsonPath("$.containerTrackingId").value(DEFAULT_CONTAINER_TRACKING_ID.toString()))
            .andExpect(jsonPath("$.quantityPerContainer").value(DEFAULT_QUANTITY_PER_CONTAINER.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.stockInBy").value(DEFAULT_STOCK_IN_BY.toString()))
            .andExpect(jsonPath("$.stockInDate").value(DEFAULT_STOCK_IN_DATE.toString()))
            .andExpect(jsonPath("$.purchaseOrderId").value(DEFAULT_PURCHASE_ORDER_ID.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalQuantity equals to DEFAULT_TOTAL_QUANTITY
        defaultStockInProcessShouldBeFound("totalQuantity.equals=" + DEFAULT_TOTAL_QUANTITY);

        // Get all the stockInProcessList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultStockInProcessShouldNotBeFound("totalQuantity.equals=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalQuantity in DEFAULT_TOTAL_QUANTITY or UPDATED_TOTAL_QUANTITY
        defaultStockInProcessShouldBeFound("totalQuantity.in=" + DEFAULT_TOTAL_QUANTITY + "," + UPDATED_TOTAL_QUANTITY);

        // Get all the stockInProcessList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultStockInProcessShouldNotBeFound("totalQuantity.in=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalQuantity is not null
        defaultStockInProcessShouldBeFound("totalQuantity.specified=true");

        // Get all the stockInProcessList where totalQuantity is null
        defaultStockInProcessShouldNotBeFound("totalQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unit equals to DEFAULT_UNIT
        defaultStockInProcessShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the stockInProcessList where unit equals to UPDATED_UNIT
        defaultStockInProcessShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultStockInProcessShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the stockInProcessList where unit equals to UPDATED_UNIT
        defaultStockInProcessShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unit is not null
        defaultStockInProcessShouldBeFound("unit.specified=true");

        // Get all the stockInProcessList where unit is null
        defaultStockInProcessShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultStockInProcessShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the stockInProcessList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockInProcessShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultStockInProcessShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the stockInProcessList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultStockInProcessShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where unitPrice is not null
        defaultStockInProcessShouldBeFound("unitPrice.specified=true");

        // Get all the stockInProcessList where unitPrice is null
        defaultStockInProcessShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalContainerIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalContainer equals to DEFAULT_TOTAL_CONTAINER
        defaultStockInProcessShouldBeFound("totalContainer.equals=" + DEFAULT_TOTAL_CONTAINER);

        // Get all the stockInProcessList where totalContainer equals to UPDATED_TOTAL_CONTAINER
        defaultStockInProcessShouldNotBeFound("totalContainer.equals=" + UPDATED_TOTAL_CONTAINER);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalContainerIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalContainer in DEFAULT_TOTAL_CONTAINER or UPDATED_TOTAL_CONTAINER
        defaultStockInProcessShouldBeFound("totalContainer.in=" + DEFAULT_TOTAL_CONTAINER + "," + UPDATED_TOTAL_CONTAINER);

        // Get all the stockInProcessList where totalContainer equals to UPDATED_TOTAL_CONTAINER
        defaultStockInProcessShouldNotBeFound("totalContainer.in=" + UPDATED_TOTAL_CONTAINER);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalContainerIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalContainer is not null
        defaultStockInProcessShouldBeFound("totalContainer.specified=true");

        // Get all the stockInProcessList where totalContainer is null
        defaultStockInProcessShouldNotBeFound("totalContainer.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalContainerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalContainer greater than or equals to DEFAULT_TOTAL_CONTAINER
        defaultStockInProcessShouldBeFound("totalContainer.greaterOrEqualThan=" + DEFAULT_TOTAL_CONTAINER);

        // Get all the stockInProcessList where totalContainer greater than or equals to UPDATED_TOTAL_CONTAINER
        defaultStockInProcessShouldNotBeFound("totalContainer.greaterOrEqualThan=" + UPDATED_TOTAL_CONTAINER);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByTotalContainerIsLessThanSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where totalContainer less than or equals to DEFAULT_TOTAL_CONTAINER
        defaultStockInProcessShouldNotBeFound("totalContainer.lessThan=" + DEFAULT_TOTAL_CONTAINER);

        // Get all the stockInProcessList where totalContainer less than or equals to UPDATED_TOTAL_CONTAINER
        defaultStockInProcessShouldBeFound("totalContainer.lessThan=" + UPDATED_TOTAL_CONTAINER);
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByContainerCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerCategory equals to DEFAULT_CONTAINER_CATEGORY
        defaultStockInProcessShouldBeFound("containerCategory.equals=" + DEFAULT_CONTAINER_CATEGORY);

        // Get all the stockInProcessList where containerCategory equals to UPDATED_CONTAINER_CATEGORY
        defaultStockInProcessShouldNotBeFound("containerCategory.equals=" + UPDATED_CONTAINER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByContainerCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerCategory in DEFAULT_CONTAINER_CATEGORY or UPDATED_CONTAINER_CATEGORY
        defaultStockInProcessShouldBeFound("containerCategory.in=" + DEFAULT_CONTAINER_CATEGORY + "," + UPDATED_CONTAINER_CATEGORY);

        // Get all the stockInProcessList where containerCategory equals to UPDATED_CONTAINER_CATEGORY
        defaultStockInProcessShouldNotBeFound("containerCategory.in=" + UPDATED_CONTAINER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByContainerCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerCategory is not null
        defaultStockInProcessShouldBeFound("containerCategory.specified=true");

        // Get all the stockInProcessList where containerCategory is null
        defaultStockInProcessShouldNotBeFound("containerCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByContainerTrackingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerTrackingId equals to DEFAULT_CONTAINER_TRACKING_ID
        defaultStockInProcessShouldBeFound("containerTrackingId.equals=" + DEFAULT_CONTAINER_TRACKING_ID);

        // Get all the stockInProcessList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockInProcessShouldNotBeFound("containerTrackingId.equals=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByContainerTrackingIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerTrackingId in DEFAULT_CONTAINER_TRACKING_ID or UPDATED_CONTAINER_TRACKING_ID
        defaultStockInProcessShouldBeFound("containerTrackingId.in=" + DEFAULT_CONTAINER_TRACKING_ID + "," + UPDATED_CONTAINER_TRACKING_ID);

        // Get all the stockInProcessList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockInProcessShouldNotBeFound("containerTrackingId.in=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByContainerTrackingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where containerTrackingId is not null
        defaultStockInProcessShouldBeFound("containerTrackingId.specified=true");

        // Get all the stockInProcessList where containerTrackingId is null
        defaultStockInProcessShouldNotBeFound("containerTrackingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByQuantityPerContainerIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where quantityPerContainer equals to DEFAULT_QUANTITY_PER_CONTAINER
        defaultStockInProcessShouldBeFound("quantityPerContainer.equals=" + DEFAULT_QUANTITY_PER_CONTAINER);

        // Get all the stockInProcessList where quantityPerContainer equals to UPDATED_QUANTITY_PER_CONTAINER
        defaultStockInProcessShouldNotBeFound("quantityPerContainer.equals=" + UPDATED_QUANTITY_PER_CONTAINER);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByQuantityPerContainerIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where quantityPerContainer in DEFAULT_QUANTITY_PER_CONTAINER or UPDATED_QUANTITY_PER_CONTAINER
        defaultStockInProcessShouldBeFound("quantityPerContainer.in=" + DEFAULT_QUANTITY_PER_CONTAINER + "," + UPDATED_QUANTITY_PER_CONTAINER);

        // Get all the stockInProcessList where quantityPerContainer equals to UPDATED_QUANTITY_PER_CONTAINER
        defaultStockInProcessShouldNotBeFound("quantityPerContainer.in=" + UPDATED_QUANTITY_PER_CONTAINER);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByQuantityPerContainerIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where quantityPerContainer is not null
        defaultStockInProcessShouldBeFound("quantityPerContainer.specified=true");

        // Get all the stockInProcessList where quantityPerContainer is null
        defaultStockInProcessShouldNotBeFound("quantityPerContainer.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultStockInProcessShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInProcessList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultStockInProcessShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultStockInProcessShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the stockInProcessList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultStockInProcessShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where expiryDate is not null
        defaultStockInProcessShouldBeFound("expiryDate.specified=true");

        // Get all the stockInProcessList where expiryDate is null
        defaultStockInProcessShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where expiryDate greater than or equals to DEFAULT_EXPIRY_DATE
        defaultStockInProcessShouldBeFound("expiryDate.greaterOrEqualThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInProcessList where expiryDate greater than or equals to UPDATED_EXPIRY_DATE
        defaultStockInProcessShouldNotBeFound("expiryDate.greaterOrEqualThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where expiryDate less than or equals to DEFAULT_EXPIRY_DATE
        defaultStockInProcessShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInProcessList where expiryDate less than or equals to UPDATED_EXPIRY_DATE
        defaultStockInProcessShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByStockInByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInBy equals to DEFAULT_STOCK_IN_BY
        defaultStockInProcessShouldBeFound("stockInBy.equals=" + DEFAULT_STOCK_IN_BY);

        // Get all the stockInProcessList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockInProcessShouldNotBeFound("stockInBy.equals=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByStockInByIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInBy in DEFAULT_STOCK_IN_BY or UPDATED_STOCK_IN_BY
        defaultStockInProcessShouldBeFound("stockInBy.in=" + DEFAULT_STOCK_IN_BY + "," + UPDATED_STOCK_IN_BY);

        // Get all the stockInProcessList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockInProcessShouldNotBeFound("stockInBy.in=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByStockInByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInBy is not null
        defaultStockInProcessShouldBeFound("stockInBy.specified=true");

        // Get all the stockInProcessList where stockInBy is null
        defaultStockInProcessShouldNotBeFound("stockInBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByStockInDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInDate equals to DEFAULT_STOCK_IN_DATE
        defaultStockInProcessShouldBeFound("stockInDate.equals=" + DEFAULT_STOCK_IN_DATE);

        // Get all the stockInProcessList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockInProcessShouldNotBeFound("stockInDate.equals=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByStockInDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInDate in DEFAULT_STOCK_IN_DATE or UPDATED_STOCK_IN_DATE
        defaultStockInProcessShouldBeFound("stockInDate.in=" + DEFAULT_STOCK_IN_DATE + "," + UPDATED_STOCK_IN_DATE);

        // Get all the stockInProcessList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockInProcessShouldNotBeFound("stockInDate.in=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByStockInDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where stockInDate is not null
        defaultStockInProcessShouldBeFound("stockInDate.specified=true");

        // Get all the stockInProcessList where stockInDate is null
        defaultStockInProcessShouldNotBeFound("stockInDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByPurchaseOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where purchaseOrderId equals to DEFAULT_PURCHASE_ORDER_ID
        defaultStockInProcessShouldBeFound("purchaseOrderId.equals=" + DEFAULT_PURCHASE_ORDER_ID);

        // Get all the stockInProcessList where purchaseOrderId equals to UPDATED_PURCHASE_ORDER_ID
        defaultStockInProcessShouldNotBeFound("purchaseOrderId.equals=" + UPDATED_PURCHASE_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByPurchaseOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where purchaseOrderId in DEFAULT_PURCHASE_ORDER_ID or UPDATED_PURCHASE_ORDER_ID
        defaultStockInProcessShouldBeFound("purchaseOrderId.in=" + DEFAULT_PURCHASE_ORDER_ID + "," + UPDATED_PURCHASE_ORDER_ID);

        // Get all the stockInProcessList where purchaseOrderId equals to UPDATED_PURCHASE_ORDER_ID
        defaultStockInProcessShouldNotBeFound("purchaseOrderId.in=" + UPDATED_PURCHASE_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByPurchaseOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where purchaseOrderId is not null
        defaultStockInProcessShouldBeFound("purchaseOrderId.specified=true");

        // Get all the stockInProcessList where purchaseOrderId is null
        defaultStockInProcessShouldNotBeFound("purchaseOrderId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where remarks equals to DEFAULT_REMARKS
        defaultStockInProcessShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the stockInProcessList where remarks equals to UPDATED_REMARKS
        defaultStockInProcessShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultStockInProcessShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the stockInProcessList where remarks equals to UPDATED_REMARKS
        defaultStockInProcessShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        // Get all the stockInProcessList where remarks is not null
        defaultStockInProcessShouldBeFound("remarks.specified=true");

        // Get all the stockInProcessList where remarks is null
        defaultStockInProcessShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInProcessesByProductCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategories = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategories);
        em.flush();
        stockInProcess.setProductCategories(productCategories);
        stockInProcessRepository.saveAndFlush(stockInProcess);
        Long productCategoriesId = productCategories.getId();

        // Get all the stockInProcessList where productCategories equals to productCategoriesId
        defaultStockInProcessShouldBeFound("productCategoriesId.equals=" + productCategoriesId);

        // Get all the stockInProcessList where productCategories equals to productCategoriesId + 1
        defaultStockInProcessShouldNotBeFound("productCategoriesId.equals=" + (productCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        Product products = ProductResourceIntTest.createEntity(em);
        em.persist(products);
        em.flush();
        stockInProcess.setProducts(products);
        stockInProcessRepository.saveAndFlush(stockInProcess);
        Long productsId = products.getId();

        // Get all the stockInProcessList where products equals to productsId
        defaultStockInProcessShouldBeFound("productsId.equals=" + productsId);

        // Get all the stockInProcessList where products equals to productsId + 1
        defaultStockInProcessShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByInventoryLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventoryLocation inventoryLocations = InventoryLocationResourceIntTest.createEntity(em);
        em.persist(inventoryLocations);
        em.flush();
        stockInProcess.setInventoryLocations(inventoryLocations);
        stockInProcessRepository.saveAndFlush(stockInProcess);
        Long inventoryLocationsId = inventoryLocations.getId();

        // Get all the stockInProcessList where inventoryLocations equals to inventoryLocationsId
        defaultStockInProcessShouldBeFound("inventoryLocationsId.equals=" + inventoryLocationsId);

        // Get all the stockInProcessList where inventoryLocations equals to inventoryLocationsId + 1
        defaultStockInProcessShouldNotBeFound("inventoryLocationsId.equals=" + (inventoryLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByInventorySubLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventorySubLocation inventorySubLocations = InventorySubLocationResourceIntTest.createEntity(em);
        em.persist(inventorySubLocations);
        em.flush();
        stockInProcess.setInventorySubLocations(inventorySubLocations);
        stockInProcessRepository.saveAndFlush(stockInProcess);
        Long inventorySubLocationsId = inventorySubLocations.getId();

        // Get all the stockInProcessList where inventorySubLocations equals to inventorySubLocationsId
        defaultStockInProcessShouldBeFound("inventorySubLocationsId.equals=" + inventorySubLocationsId);

        // Get all the stockInProcessList where inventorySubLocations equals to inventorySubLocationsId + 1
        defaultStockInProcessShouldNotBeFound("inventorySubLocationsId.equals=" + (inventorySubLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInProcessesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        Vendor vendor = VendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        stockInProcess.setVendor(vendor);
        stockInProcessRepository.saveAndFlush(stockInProcess);
        Long vendorId = vendor.getId();

        // Get all the stockInProcessList where vendor equals to vendorId
        defaultStockInProcessShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the stockInProcessList where vendor equals to vendorId + 1
        defaultStockInProcessShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStockInProcessShouldBeFound(String filter) throws Exception {
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalContainer").value(hasItem(DEFAULT_TOTAL_CONTAINER)))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].quantityPerContainer").value(hasItem(DEFAULT_QUANTITY_PER_CONTAINER)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStockInProcessShouldNotBeFound(String filter) throws Exception {
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockInProcess() throws Exception {
        // Get the stockInProcess
        restStockInProcessMockMvc.perform(get("/api/stock-in-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockInProcess() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        int databaseSizeBeforeUpdate = stockInProcessRepository.findAll().size();

        // Update the stockInProcess
        StockInProcess updatedStockInProcess = stockInProcessRepository.findById(stockInProcess.getId()).get();
        // Disconnect from session so that the updates on updatedStockInProcess are not directly saved in db
        em.detach(updatedStockInProcess);
        updatedStockInProcess
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .unit(UPDATED_UNIT)
            .unitPrice(UPDATED_UNIT_PRICE)
            .totalContainer(UPDATED_TOTAL_CONTAINER)
            .containerCategory(UPDATED_CONTAINER_CATEGORY)
            .containerTrackingId(UPDATED_CONTAINER_TRACKING_ID)
            .quantityPerContainer(UPDATED_QUANTITY_PER_CONTAINER)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .stockInBy(UPDATED_STOCK_IN_BY)
            .stockInDate(UPDATED_STOCK_IN_DATE)
            .purchaseOrderId(UPDATED_PURCHASE_ORDER_ID)
            .remarks(UPDATED_REMARKS);
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(updatedStockInProcess);

        restStockInProcessMockMvc.perform(put("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isOk());

        // Validate the StockInProcess in the database
        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeUpdate);
        StockInProcess testStockInProcess = stockInProcessList.get(stockInProcessList.size() - 1);
        assertThat(testStockInProcess.getTotalQuantity()).isEqualTo(UPDATED_TOTAL_QUANTITY);
        assertThat(testStockInProcess.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testStockInProcess.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testStockInProcess.getTotalContainer()).isEqualTo(UPDATED_TOTAL_CONTAINER);
        assertThat(testStockInProcess.getContainerCategory()).isEqualTo(UPDATED_CONTAINER_CATEGORY);
        assertThat(testStockInProcess.getContainerTrackingId()).isEqualTo(UPDATED_CONTAINER_TRACKING_ID);
        assertThat(testStockInProcess.getQuantityPerContainer()).isEqualTo(UPDATED_QUANTITY_PER_CONTAINER);
        assertThat(testStockInProcess.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testStockInProcess.getStockInBy()).isEqualTo(UPDATED_STOCK_IN_BY);
        assertThat(testStockInProcess.getStockInDate()).isEqualTo(UPDATED_STOCK_IN_DATE);
        assertThat(testStockInProcess.getPurchaseOrderId()).isEqualTo(UPDATED_PURCHASE_ORDER_ID);
        assertThat(testStockInProcess.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the StockInProcess in Elasticsearch
        verify(mockStockInProcessSearchRepository, times(1)).save(testStockInProcess);
    }

    @Test
    @Transactional
    public void updateNonExistingStockInProcess() throws Exception {
        int databaseSizeBeforeUpdate = stockInProcessRepository.findAll().size();

        // Create the StockInProcess
        StockInProcessDTO stockInProcessDTO = stockInProcessMapper.toDto(stockInProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockInProcessMockMvc.perform(put("/api/stock-in-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockInProcess in the database
        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockInProcess in Elasticsearch
        verify(mockStockInProcessSearchRepository, times(0)).save(stockInProcess);
    }

    @Test
    @Transactional
    public void deleteStockInProcess() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);

        int databaseSizeBeforeDelete = stockInProcessRepository.findAll().size();

        // Delete the stockInProcess
        restStockInProcessMockMvc.perform(delete("/api/stock-in-processes/{id}", stockInProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockInProcess> stockInProcessList = stockInProcessRepository.findAll();
        assertThat(stockInProcessList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockInProcess in Elasticsearch
        verify(mockStockInProcessSearchRepository, times(1)).deleteById(stockInProcess.getId());
    }

    @Test
    @Transactional
    public void searchStockInProcess() throws Exception {
        // Initialize the database
        stockInProcessRepository.saveAndFlush(stockInProcess);
        when(mockStockInProcessSearchRepository.search(queryStringQuery("id:" + stockInProcess.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockInProcess), PageRequest.of(0, 1), 1));
        // Search the stockInProcess
        restStockInProcessMockMvc.perform(get("/api/_search/stock-in-processes?query=id:" + stockInProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalContainer").value(hasItem(DEFAULT_TOTAL_CONTAINER)))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].quantityPerContainer").value(hasItem(DEFAULT_QUANTITY_PER_CONTAINER)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockInProcess.class);
        StockInProcess stockInProcess1 = new StockInProcess();
        stockInProcess1.setId(1L);
        StockInProcess stockInProcess2 = new StockInProcess();
        stockInProcess2.setId(stockInProcess1.getId());
        assertThat(stockInProcess1).isEqualTo(stockInProcess2);
        stockInProcess2.setId(2L);
        assertThat(stockInProcess1).isNotEqualTo(stockInProcess2);
        stockInProcess1.setId(null);
        assertThat(stockInProcess1).isNotEqualTo(stockInProcess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockInProcessDTO.class);
        StockInProcessDTO stockInProcessDTO1 = new StockInProcessDTO();
        stockInProcessDTO1.setId(1L);
        StockInProcessDTO stockInProcessDTO2 = new StockInProcessDTO();
        assertThat(stockInProcessDTO1).isNotEqualTo(stockInProcessDTO2);
        stockInProcessDTO2.setId(stockInProcessDTO1.getId());
        assertThat(stockInProcessDTO1).isEqualTo(stockInProcessDTO2);
        stockInProcessDTO2.setId(2L);
        assertThat(stockInProcessDTO1).isNotEqualTo(stockInProcessDTO2);
        stockInProcessDTO1.setId(null);
        assertThat(stockInProcessDTO1).isNotEqualTo(stockInProcessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockInProcessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockInProcessMapper.fromId(null)).isNull();
    }
}
