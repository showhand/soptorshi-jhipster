package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.StockStatus;
import org.soptorshi.domain.StockInItem;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.Product;
import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.repository.StockStatusRepository;
import org.soptorshi.repository.search.StockStatusSearchRepository;
import org.soptorshi.service.StockStatusService;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockStatusMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.StockStatusCriteria;
import org.soptorshi.service.StockStatusQueryService;

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
import java.time.Instant;
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
/**
 * Test class for the StockStatusResource REST controller.
 *
 * @see StockStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class StockStatusResourceIntTest {

    private static final String DEFAULT_CONTAINER_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_TRACKING_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_AVAILABLE_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AVAILABLE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_PRICE = new BigDecimal(2);

    private static final String DEFAULT_STOCK_IN_BY = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_IN_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_STOCK_IN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOCK_IN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StockStatusRepository stockStatusRepository;

    @Autowired
    private StockStatusMapper stockStatusMapper;

    @Autowired
    private StockStatusService stockStatusService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.StockStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockStatusSearchRepository mockStockStatusSearchRepository;

    @Autowired
    private StockStatusQueryService stockStatusQueryService;

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

    private MockMvc restStockStatusMockMvc;

    private StockStatus stockStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockStatusResource stockStatusResource = new StockStatusResource(stockStatusService, stockStatusQueryService);
        this.restStockStatusMockMvc = MockMvcBuilders.standaloneSetup(stockStatusResource)
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
    public static StockStatus createEntity(EntityManager em) {
        StockStatus stockStatus = new StockStatus()
            .containerTrackingId(DEFAULT_CONTAINER_TRACKING_ID)
            .totalQuantity(DEFAULT_TOTAL_QUANTITY)
            .unit(DEFAULT_UNIT)
            .availableQuantity(DEFAULT_AVAILABLE_QUANTITY)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .availablePrice(DEFAULT_AVAILABLE_PRICE)
            .stockInBy(DEFAULT_STOCK_IN_BY)
            .stockInDate(DEFAULT_STOCK_IN_DATE);
        return stockStatus;
    }

    @Before
    public void initTest() {
        stockStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockStatus() throws Exception {
        int databaseSizeBeforeCreate = stockStatusRepository.findAll().size();

        // Create the StockStatus
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);
        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the StockStatus in the database
        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeCreate + 1);
        StockStatus testStockStatus = stockStatusList.get(stockStatusList.size() - 1);
        assertThat(testStockStatus.getContainerTrackingId()).isEqualTo(DEFAULT_CONTAINER_TRACKING_ID);
        assertThat(testStockStatus.getTotalQuantity()).isEqualTo(DEFAULT_TOTAL_QUANTITY);
        assertThat(testStockStatus.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testStockStatus.getAvailableQuantity()).isEqualTo(DEFAULT_AVAILABLE_QUANTITY);
        assertThat(testStockStatus.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testStockStatus.getAvailablePrice()).isEqualTo(DEFAULT_AVAILABLE_PRICE);
        assertThat(testStockStatus.getStockInBy()).isEqualTo(DEFAULT_STOCK_IN_BY);
        assertThat(testStockStatus.getStockInDate()).isEqualTo(DEFAULT_STOCK_IN_DATE);

        // Validate the StockStatus in Elasticsearch
        verify(mockStockStatusSearchRepository, times(1)).save(testStockStatus);
    }

    @Test
    @Transactional
    public void createStockStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockStatusRepository.findAll().size();

        // Create the StockStatus with an existing ID
        stockStatus.setId(1L);
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockStatus in the database
        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockStatus in Elasticsearch
        verify(mockStockStatusSearchRepository, times(0)).save(stockStatus);
    }

    @Test
    @Transactional
    public void checkContainerTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setContainerTrackingId(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setTotalQuantity(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setUnit(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setAvailableQuantity(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setTotalPrice(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailablePriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockStatusRepository.findAll().size();
        // set the field null
        stockStatus.setAvailablePrice(null);

        // Create the StockStatus, which fails.
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        restStockStatusMockMvc.perform(post("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockStatuses() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList
        restStockStatusMockMvc.perform(get("/api/stock-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID.toString())))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].availableQuantity").value(hasItem(DEFAULT_AVAILABLE_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].availablePrice").value(hasItem(DEFAULT_AVAILABLE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY.toString())))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStockStatus() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get the stockStatus
        restStockStatusMockMvc.perform(get("/api/stock-statuses/{id}", stockStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockStatus.getId().intValue()))
            .andExpect(jsonPath("$.containerTrackingId").value(DEFAULT_CONTAINER_TRACKING_ID.toString()))
            .andExpect(jsonPath("$.totalQuantity").value(DEFAULT_TOTAL_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.availableQuantity").value(DEFAULT_AVAILABLE_QUANTITY.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.availablePrice").value(DEFAULT_AVAILABLE_PRICE.intValue()))
            .andExpect(jsonPath("$.stockInBy").value(DEFAULT_STOCK_IN_BY.toString()))
            .andExpect(jsonPath("$.stockInDate").value(DEFAULT_STOCK_IN_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllStockStatusesByContainerTrackingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where containerTrackingId equals to DEFAULT_CONTAINER_TRACKING_ID
        defaultStockStatusShouldBeFound("containerTrackingId.equals=" + DEFAULT_CONTAINER_TRACKING_ID);

        // Get all the stockStatusList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockStatusShouldNotBeFound("containerTrackingId.equals=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByContainerTrackingIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where containerTrackingId in DEFAULT_CONTAINER_TRACKING_ID or UPDATED_CONTAINER_TRACKING_ID
        defaultStockStatusShouldBeFound("containerTrackingId.in=" + DEFAULT_CONTAINER_TRACKING_ID + "," + UPDATED_CONTAINER_TRACKING_ID);

        // Get all the stockStatusList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockStatusShouldNotBeFound("containerTrackingId.in=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByContainerTrackingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where containerTrackingId is not null
        defaultStockStatusShouldBeFound("containerTrackingId.specified=true");

        // Get all the stockStatusList where containerTrackingId is null
        defaultStockStatusShouldNotBeFound("containerTrackingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalQuantity equals to DEFAULT_TOTAL_QUANTITY
        defaultStockStatusShouldBeFound("totalQuantity.equals=" + DEFAULT_TOTAL_QUANTITY);

        // Get all the stockStatusList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultStockStatusShouldNotBeFound("totalQuantity.equals=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalQuantity in DEFAULT_TOTAL_QUANTITY or UPDATED_TOTAL_QUANTITY
        defaultStockStatusShouldBeFound("totalQuantity.in=" + DEFAULT_TOTAL_QUANTITY + "," + UPDATED_TOTAL_QUANTITY);

        // Get all the stockStatusList where totalQuantity equals to UPDATED_TOTAL_QUANTITY
        defaultStockStatusShouldNotBeFound("totalQuantity.in=" + UPDATED_TOTAL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalQuantity is not null
        defaultStockStatusShouldBeFound("totalQuantity.specified=true");

        // Get all the stockStatusList where totalQuantity is null
        defaultStockStatusShouldNotBeFound("totalQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where unit equals to DEFAULT_UNIT
        defaultStockStatusShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the stockStatusList where unit equals to UPDATED_UNIT
        defaultStockStatusShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultStockStatusShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the stockStatusList where unit equals to UPDATED_UNIT
        defaultStockStatusShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where unit is not null
        defaultStockStatusShouldBeFound("unit.specified=true");

        // Get all the stockStatusList where unit is null
        defaultStockStatusShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailableQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availableQuantity equals to DEFAULT_AVAILABLE_QUANTITY
        defaultStockStatusShouldBeFound("availableQuantity.equals=" + DEFAULT_AVAILABLE_QUANTITY);

        // Get all the stockStatusList where availableQuantity equals to UPDATED_AVAILABLE_QUANTITY
        defaultStockStatusShouldNotBeFound("availableQuantity.equals=" + UPDATED_AVAILABLE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailableQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availableQuantity in DEFAULT_AVAILABLE_QUANTITY or UPDATED_AVAILABLE_QUANTITY
        defaultStockStatusShouldBeFound("availableQuantity.in=" + DEFAULT_AVAILABLE_QUANTITY + "," + UPDATED_AVAILABLE_QUANTITY);

        // Get all the stockStatusList where availableQuantity equals to UPDATED_AVAILABLE_QUANTITY
        defaultStockStatusShouldNotBeFound("availableQuantity.in=" + UPDATED_AVAILABLE_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailableQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availableQuantity is not null
        defaultStockStatusShouldBeFound("availableQuantity.specified=true");

        // Get all the stockStatusList where availableQuantity is null
        defaultStockStatusShouldNotBeFound("availableQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultStockStatusShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the stockStatusList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultStockStatusShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultStockStatusShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the stockStatusList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultStockStatusShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where totalPrice is not null
        defaultStockStatusShouldBeFound("totalPrice.specified=true");

        // Get all the stockStatusList where totalPrice is null
        defaultStockStatusShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailablePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availablePrice equals to DEFAULT_AVAILABLE_PRICE
        defaultStockStatusShouldBeFound("availablePrice.equals=" + DEFAULT_AVAILABLE_PRICE);

        // Get all the stockStatusList where availablePrice equals to UPDATED_AVAILABLE_PRICE
        defaultStockStatusShouldNotBeFound("availablePrice.equals=" + UPDATED_AVAILABLE_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailablePriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availablePrice in DEFAULT_AVAILABLE_PRICE or UPDATED_AVAILABLE_PRICE
        defaultStockStatusShouldBeFound("availablePrice.in=" + DEFAULT_AVAILABLE_PRICE + "," + UPDATED_AVAILABLE_PRICE);

        // Get all the stockStatusList where availablePrice equals to UPDATED_AVAILABLE_PRICE
        defaultStockStatusShouldNotBeFound("availablePrice.in=" + UPDATED_AVAILABLE_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByAvailablePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where availablePrice is not null
        defaultStockStatusShouldBeFound("availablePrice.specified=true");

        // Get all the stockStatusList where availablePrice is null
        defaultStockStatusShouldNotBeFound("availablePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInBy equals to DEFAULT_STOCK_IN_BY
        defaultStockStatusShouldBeFound("stockInBy.equals=" + DEFAULT_STOCK_IN_BY);

        // Get all the stockStatusList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockStatusShouldNotBeFound("stockInBy.equals=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInByIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInBy in DEFAULT_STOCK_IN_BY or UPDATED_STOCK_IN_BY
        defaultStockStatusShouldBeFound("stockInBy.in=" + DEFAULT_STOCK_IN_BY + "," + UPDATED_STOCK_IN_BY);

        // Get all the stockStatusList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockStatusShouldNotBeFound("stockInBy.in=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInBy is not null
        defaultStockStatusShouldBeFound("stockInBy.specified=true");

        // Get all the stockStatusList where stockInBy is null
        defaultStockStatusShouldNotBeFound("stockInBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInDate equals to DEFAULT_STOCK_IN_DATE
        defaultStockStatusShouldBeFound("stockInDate.equals=" + DEFAULT_STOCK_IN_DATE);

        // Get all the stockStatusList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockStatusShouldNotBeFound("stockInDate.equals=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInDate in DEFAULT_STOCK_IN_DATE or UPDATED_STOCK_IN_DATE
        defaultStockStatusShouldBeFound("stockInDate.in=" + DEFAULT_STOCK_IN_DATE + "," + UPDATED_STOCK_IN_DATE);

        // Get all the stockStatusList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockStatusShouldNotBeFound("stockInDate.in=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        // Get all the stockStatusList where stockInDate is not null
        defaultStockStatusShouldBeFound("stockInDate.specified=true");

        // Get all the stockStatusList where stockInDate is null
        defaultStockStatusShouldNotBeFound("stockInDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockStatusesByStockInItemIsEqualToSomething() throws Exception {
        // Initialize the database
        StockInItem stockInItem = StockInItemResourceIntTest.createEntity(em);
        em.persist(stockInItem);
        em.flush();
        stockStatus.setStockInItem(stockInItem);
        stockStatusRepository.saveAndFlush(stockStatus);
        Long stockInItemId = stockInItem.getId();

        // Get all the stockStatusList where stockInItem equals to stockInItemId
        defaultStockStatusShouldBeFound("stockInItemId.equals=" + stockInItemId);

        // Get all the stockStatusList where stockInItem equals to stockInItemId + 1
        defaultStockStatusShouldNotBeFound("stockInItemId.equals=" + (stockInItemId + 1));
    }


    @Test
    @Transactional
    public void getAllStockStatusesByProductCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategories = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategories);
        em.flush();
        stockStatus.setProductCategories(productCategories);
        stockStatusRepository.saveAndFlush(stockStatus);
        Long productCategoriesId = productCategories.getId();

        // Get all the stockStatusList where productCategories equals to productCategoriesId
        defaultStockStatusShouldBeFound("productCategoriesId.equals=" + productCategoriesId);

        // Get all the stockStatusList where productCategories equals to productCategoriesId + 1
        defaultStockStatusShouldNotBeFound("productCategoriesId.equals=" + (productCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllStockStatusesByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        Product products = ProductResourceIntTest.createEntity(em);
        em.persist(products);
        em.flush();
        stockStatus.setProducts(products);
        stockStatusRepository.saveAndFlush(stockStatus);
        Long productsId = products.getId();

        // Get all the stockStatusList where products equals to productsId
        defaultStockStatusShouldBeFound("productsId.equals=" + productsId);

        // Get all the stockStatusList where products equals to productsId + 1
        defaultStockStatusShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockStatusesByInventoryLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventoryLocation inventoryLocations = InventoryLocationResourceIntTest.createEntity(em);
        em.persist(inventoryLocations);
        em.flush();
        stockStatus.setInventoryLocations(inventoryLocations);
        stockStatusRepository.saveAndFlush(stockStatus);
        Long inventoryLocationsId = inventoryLocations.getId();

        // Get all the stockStatusList where inventoryLocations equals to inventoryLocationsId
        defaultStockStatusShouldBeFound("inventoryLocationsId.equals=" + inventoryLocationsId);

        // Get all the stockStatusList where inventoryLocations equals to inventoryLocationsId + 1
        defaultStockStatusShouldNotBeFound("inventoryLocationsId.equals=" + (inventoryLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockStatusesByInventorySubLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventorySubLocation inventorySubLocations = InventorySubLocationResourceIntTest.createEntity(em);
        em.persist(inventorySubLocations);
        em.flush();
        stockStatus.setInventorySubLocations(inventorySubLocations);
        stockStatusRepository.saveAndFlush(stockStatus);
        Long inventorySubLocationsId = inventorySubLocations.getId();

        // Get all the stockStatusList where inventorySubLocations equals to inventorySubLocationsId
        defaultStockStatusShouldBeFound("inventorySubLocationsId.equals=" + inventorySubLocationsId);

        // Get all the stockStatusList where inventorySubLocations equals to inventorySubLocationsId + 1
        defaultStockStatusShouldNotBeFound("inventorySubLocationsId.equals=" + (inventorySubLocationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStockStatusShouldBeFound(String filter) throws Exception {
        restStockStatusMockMvc.perform(get("/api/stock-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].availableQuantity").value(hasItem(DEFAULT_AVAILABLE_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].availablePrice").value(hasItem(DEFAULT_AVAILABLE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())));

        // Check, that the count call also returns 1
        restStockStatusMockMvc.perform(get("/api/stock-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStockStatusShouldNotBeFound(String filter) throws Exception {
        restStockStatusMockMvc.perform(get("/api/stock-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockStatusMockMvc.perform(get("/api/stock-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockStatus() throws Exception {
        // Get the stockStatus
        restStockStatusMockMvc.perform(get("/api/stock-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockStatus() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        int databaseSizeBeforeUpdate = stockStatusRepository.findAll().size();

        // Update the stockStatus
        StockStatus updatedStockStatus = stockStatusRepository.findById(stockStatus.getId()).get();
        // Disconnect from session so that the updates on updatedStockStatus are not directly saved in db
        em.detach(updatedStockStatus);
        updatedStockStatus
            .containerTrackingId(UPDATED_CONTAINER_TRACKING_ID)
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .unit(UPDATED_UNIT)
            .availableQuantity(UPDATED_AVAILABLE_QUANTITY)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .availablePrice(UPDATED_AVAILABLE_PRICE)
            .stockInBy(UPDATED_STOCK_IN_BY)
            .stockInDate(UPDATED_STOCK_IN_DATE);
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(updatedStockStatus);

        restStockStatusMockMvc.perform(put("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isOk());

        // Validate the StockStatus in the database
        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeUpdate);
        StockStatus testStockStatus = stockStatusList.get(stockStatusList.size() - 1);
        assertThat(testStockStatus.getContainerTrackingId()).isEqualTo(UPDATED_CONTAINER_TRACKING_ID);
        assertThat(testStockStatus.getTotalQuantity()).isEqualTo(UPDATED_TOTAL_QUANTITY);
        assertThat(testStockStatus.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testStockStatus.getAvailableQuantity()).isEqualTo(UPDATED_AVAILABLE_QUANTITY);
        assertThat(testStockStatus.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testStockStatus.getAvailablePrice()).isEqualTo(UPDATED_AVAILABLE_PRICE);
        assertThat(testStockStatus.getStockInBy()).isEqualTo(UPDATED_STOCK_IN_BY);
        assertThat(testStockStatus.getStockInDate()).isEqualTo(UPDATED_STOCK_IN_DATE);

        // Validate the StockStatus in Elasticsearch
        verify(mockStockStatusSearchRepository, times(1)).save(testStockStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingStockStatus() throws Exception {
        int databaseSizeBeforeUpdate = stockStatusRepository.findAll().size();

        // Create the StockStatus
        StockStatusDTO stockStatusDTO = stockStatusMapper.toDto(stockStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockStatusMockMvc.perform(put("/api/stock-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockStatus in the database
        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockStatus in Elasticsearch
        verify(mockStockStatusSearchRepository, times(0)).save(stockStatus);
    }

    @Test
    @Transactional
    public void deleteStockStatus() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);

        int databaseSizeBeforeDelete = stockStatusRepository.findAll().size();

        // Delete the stockStatus
        restStockStatusMockMvc.perform(delete("/api/stock-statuses/{id}", stockStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockStatus> stockStatusList = stockStatusRepository.findAll();
        assertThat(stockStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockStatus in Elasticsearch
        verify(mockStockStatusSearchRepository, times(1)).deleteById(stockStatus.getId());
    }

    @Test
    @Transactional
    public void searchStockStatus() throws Exception {
        // Initialize the database
        stockStatusRepository.saveAndFlush(stockStatus);
        when(mockStockStatusSearchRepository.search(queryStringQuery("id:" + stockStatus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockStatus), PageRequest.of(0, 1), 1));
        // Search the stockStatus
        restStockStatusMockMvc.perform(get("/api/_search/stock-statuses?query=id:" + stockStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].availableQuantity").value(hasItem(DEFAULT_AVAILABLE_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].availablePrice").value(hasItem(DEFAULT_AVAILABLE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockStatus.class);
        StockStatus stockStatus1 = new StockStatus();
        stockStatus1.setId(1L);
        StockStatus stockStatus2 = new StockStatus();
        stockStatus2.setId(stockStatus1.getId());
        assertThat(stockStatus1).isEqualTo(stockStatus2);
        stockStatus2.setId(2L);
        assertThat(stockStatus1).isNotEqualTo(stockStatus2);
        stockStatus1.setId(null);
        assertThat(stockStatus1).isNotEqualTo(stockStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockStatusDTO.class);
        StockStatusDTO stockStatusDTO1 = new StockStatusDTO();
        stockStatusDTO1.setId(1L);
        StockStatusDTO stockStatusDTO2 = new StockStatusDTO();
        assertThat(stockStatusDTO1).isNotEqualTo(stockStatusDTO2);
        stockStatusDTO2.setId(stockStatusDTO1.getId());
        assertThat(stockStatusDTO1).isEqualTo(stockStatusDTO2);
        stockStatusDTO2.setId(2L);
        assertThat(stockStatusDTO1).isNotEqualTo(stockStatusDTO2);
        stockStatusDTO1.setId(null);
        assertThat(stockStatusDTO1).isNotEqualTo(stockStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockStatusMapper.fromId(null)).isNull();
    }
}
