package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.repository.StockOutItemRepository;
import org.soptorshi.repository.search.StockOutItemSearchRepository;
import org.soptorshi.service.StockOutItemQueryService;
import org.soptorshi.service.StockOutItemService;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.service.mapper.StockOutItemMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
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
 * Test class for the StockOutItemResource REST controller.
 *
 * @see StockOutItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class StockOutItemResourceIntTest {

    private static final String DEFAULT_CONTAINER_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_TRACKING_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final String DEFAULT_STOCK_OUT_BY = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_OUT_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_STOCK_OUT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOCK_OUT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECEIVER_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVING_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private StockOutItemRepository stockOutItemRepository;

    @Autowired
    private StockOutItemMapper stockOutItemMapper;

    @Autowired
    private StockOutItemService stockOutItemService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.StockOutItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockOutItemSearchRepository mockStockOutItemSearchRepository;

    @Autowired
    private StockOutItemQueryService stockOutItemQueryService;

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

    private MockMvc restStockOutItemMockMvc;

    private StockOutItem stockOutItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockOutItemResource stockOutItemResource = new StockOutItemResource(stockOutItemService, stockOutItemQueryService);
        this.restStockOutItemMockMvc = MockMvcBuilders.standaloneSetup(stockOutItemResource)
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
    public static StockOutItem createEntity(EntityManager em) {
        StockOutItem stockOutItem = new StockOutItem()
            .containerTrackingId(DEFAULT_CONTAINER_TRACKING_ID)
            .quantity(DEFAULT_QUANTITY)
            .stockOutBy(DEFAULT_STOCK_OUT_BY)
            .stockOutDate(DEFAULT_STOCK_OUT_DATE)
            .receiverId(DEFAULT_RECEIVER_ID)
            .receivingPlace(DEFAULT_RECEIVING_PLACE)
            .remarks(DEFAULT_REMARKS);
        return stockOutItem;
    }

    @Before
    public void initTest() {
        stockOutItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockOutItem() throws Exception {
        int databaseSizeBeforeCreate = stockOutItemRepository.findAll().size();

        // Create the StockOutItem
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(stockOutItem);
        restStockOutItemMockMvc.perform(post("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StockOutItem in the database
        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockOutItem testStockOutItem = stockOutItemList.get(stockOutItemList.size() - 1);
        assertThat(testStockOutItem.getContainerTrackingId()).isEqualTo(DEFAULT_CONTAINER_TRACKING_ID);
        assertThat(testStockOutItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockOutItem.getStockOutBy()).isEqualTo(DEFAULT_STOCK_OUT_BY);
        assertThat(testStockOutItem.getStockOutDate()).isEqualTo(DEFAULT_STOCK_OUT_DATE);
        assertThat(testStockOutItem.getReceiverId()).isEqualTo(DEFAULT_RECEIVER_ID);
        assertThat(testStockOutItem.getReceivingPlace()).isEqualTo(DEFAULT_RECEIVING_PLACE);
        assertThat(testStockOutItem.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the StockOutItem in Elasticsearch
        verify(mockStockOutItemSearchRepository, times(1)).save(testStockOutItem);
    }

    @Test
    @Transactional
    public void createStockOutItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockOutItemRepository.findAll().size();

        // Create the StockOutItem with an existing ID
        stockOutItem.setId(1L);
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(stockOutItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockOutItemMockMvc.perform(post("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockOutItem in the database
        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockOutItem in Elasticsearch
        verify(mockStockOutItemSearchRepository, times(0)).save(stockOutItem);
    }

    @Test
    @Transactional
    public void checkContainerTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockOutItemRepository.findAll().size();
        // set the field null
        stockOutItem.setContainerTrackingId(null);

        // Create the StockOutItem, which fails.
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(stockOutItem);

        restStockOutItemMockMvc.perform(post("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockOutItemRepository.findAll().size();
        // set the field null
        stockOutItem.setQuantity(null);

        // Create the StockOutItem, which fails.
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(stockOutItem);

        restStockOutItemMockMvc.perform(post("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockOutItems() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList
        restStockOutItemMockMvc.perform(get("/api/stock-out-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockOutItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].stockOutBy").value(hasItem(DEFAULT_STOCK_OUT_BY.toString())))
            .andExpect(jsonPath("$.[*].stockOutDate").value(hasItem(DEFAULT_STOCK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiverId").value(hasItem(DEFAULT_RECEIVER_ID.toString())))
            .andExpect(jsonPath("$.[*].receivingPlace").value(hasItem(DEFAULT_RECEIVING_PLACE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getStockOutItem() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get the stockOutItem
        restStockOutItemMockMvc.perform(get("/api/stock-out-items/{id}", stockOutItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockOutItem.getId().intValue()))
            .andExpect(jsonPath("$.containerTrackingId").value(DEFAULT_CONTAINER_TRACKING_ID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.stockOutBy").value(DEFAULT_STOCK_OUT_BY.toString()))
            .andExpect(jsonPath("$.stockOutDate").value(DEFAULT_STOCK_OUT_DATE.toString()))
            .andExpect(jsonPath("$.receiverId").value(DEFAULT_RECEIVER_ID.toString()))
            .andExpect(jsonPath("$.receivingPlace").value(DEFAULT_RECEIVING_PLACE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByContainerTrackingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where containerTrackingId equals to DEFAULT_CONTAINER_TRACKING_ID
        defaultStockOutItemShouldBeFound("containerTrackingId.equals=" + DEFAULT_CONTAINER_TRACKING_ID);

        // Get all the stockOutItemList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockOutItemShouldNotBeFound("containerTrackingId.equals=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByContainerTrackingIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where containerTrackingId in DEFAULT_CONTAINER_TRACKING_ID or UPDATED_CONTAINER_TRACKING_ID
        defaultStockOutItemShouldBeFound("containerTrackingId.in=" + DEFAULT_CONTAINER_TRACKING_ID + "," + UPDATED_CONTAINER_TRACKING_ID);

        // Get all the stockOutItemList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockOutItemShouldNotBeFound("containerTrackingId.in=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByContainerTrackingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where containerTrackingId is not null
        defaultStockOutItemShouldBeFound("containerTrackingId.specified=true");

        // Get all the stockOutItemList where containerTrackingId is null
        defaultStockOutItemShouldNotBeFound("containerTrackingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where quantity equals to DEFAULT_QUANTITY
        defaultStockOutItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the stockOutItemList where quantity equals to UPDATED_QUANTITY
        defaultStockOutItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultStockOutItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the stockOutItemList where quantity equals to UPDATED_QUANTITY
        defaultStockOutItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where quantity is not null
        defaultStockOutItemShouldBeFound("quantity.specified=true");

        // Get all the stockOutItemList where quantity is null
        defaultStockOutItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutBy equals to DEFAULT_STOCK_OUT_BY
        defaultStockOutItemShouldBeFound("stockOutBy.equals=" + DEFAULT_STOCK_OUT_BY);

        // Get all the stockOutItemList where stockOutBy equals to UPDATED_STOCK_OUT_BY
        defaultStockOutItemShouldNotBeFound("stockOutBy.equals=" + UPDATED_STOCK_OUT_BY);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutByIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutBy in DEFAULT_STOCK_OUT_BY or UPDATED_STOCK_OUT_BY
        defaultStockOutItemShouldBeFound("stockOutBy.in=" + DEFAULT_STOCK_OUT_BY + "," + UPDATED_STOCK_OUT_BY);

        // Get all the stockOutItemList where stockOutBy equals to UPDATED_STOCK_OUT_BY
        defaultStockOutItemShouldNotBeFound("stockOutBy.in=" + UPDATED_STOCK_OUT_BY);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutBy is not null
        defaultStockOutItemShouldBeFound("stockOutBy.specified=true");

        // Get all the stockOutItemList where stockOutBy is null
        defaultStockOutItemShouldNotBeFound("stockOutBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutDate equals to DEFAULT_STOCK_OUT_DATE
        defaultStockOutItemShouldBeFound("stockOutDate.equals=" + DEFAULT_STOCK_OUT_DATE);

        // Get all the stockOutItemList where stockOutDate equals to UPDATED_STOCK_OUT_DATE
        defaultStockOutItemShouldNotBeFound("stockOutDate.equals=" + UPDATED_STOCK_OUT_DATE);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutDate in DEFAULT_STOCK_OUT_DATE or UPDATED_STOCK_OUT_DATE
        defaultStockOutItemShouldBeFound("stockOutDate.in=" + DEFAULT_STOCK_OUT_DATE + "," + UPDATED_STOCK_OUT_DATE);

        // Get all the stockOutItemList where stockOutDate equals to UPDATED_STOCK_OUT_DATE
        defaultStockOutItemShouldNotBeFound("stockOutDate.in=" + UPDATED_STOCK_OUT_DATE);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByStockOutDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where stockOutDate is not null
        defaultStockOutItemShouldBeFound("stockOutDate.specified=true");

        // Get all the stockOutItemList where stockOutDate is null
        defaultStockOutItemShouldNotBeFound("stockOutDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceiverIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receiverId equals to DEFAULT_RECEIVER_ID
        defaultStockOutItemShouldBeFound("receiverId.equals=" + DEFAULT_RECEIVER_ID);

        // Get all the stockOutItemList where receiverId equals to UPDATED_RECEIVER_ID
        defaultStockOutItemShouldNotBeFound("receiverId.equals=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceiverIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receiverId in DEFAULT_RECEIVER_ID or UPDATED_RECEIVER_ID
        defaultStockOutItemShouldBeFound("receiverId.in=" + DEFAULT_RECEIVER_ID + "," + UPDATED_RECEIVER_ID);

        // Get all the stockOutItemList where receiverId equals to UPDATED_RECEIVER_ID
        defaultStockOutItemShouldNotBeFound("receiverId.in=" + UPDATED_RECEIVER_ID);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceiverIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receiverId is not null
        defaultStockOutItemShouldBeFound("receiverId.specified=true");

        // Get all the stockOutItemList where receiverId is null
        defaultStockOutItemShouldNotBeFound("receiverId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceivingPlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receivingPlace equals to DEFAULT_RECEIVING_PLACE
        defaultStockOutItemShouldBeFound("receivingPlace.equals=" + DEFAULT_RECEIVING_PLACE);

        // Get all the stockOutItemList where receivingPlace equals to UPDATED_RECEIVING_PLACE
        defaultStockOutItemShouldNotBeFound("receivingPlace.equals=" + UPDATED_RECEIVING_PLACE);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceivingPlaceIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receivingPlace in DEFAULT_RECEIVING_PLACE or UPDATED_RECEIVING_PLACE
        defaultStockOutItemShouldBeFound("receivingPlace.in=" + DEFAULT_RECEIVING_PLACE + "," + UPDATED_RECEIVING_PLACE);

        // Get all the stockOutItemList where receivingPlace equals to UPDATED_RECEIVING_PLACE
        defaultStockOutItemShouldNotBeFound("receivingPlace.in=" + UPDATED_RECEIVING_PLACE);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByReceivingPlaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where receivingPlace is not null
        defaultStockOutItemShouldBeFound("receivingPlace.specified=true");

        // Get all the stockOutItemList where receivingPlace is null
        defaultStockOutItemShouldNotBeFound("receivingPlace.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where remarks equals to DEFAULT_REMARKS
        defaultStockOutItemShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the stockOutItemList where remarks equals to UPDATED_REMARKS
        defaultStockOutItemShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultStockOutItemShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the stockOutItemList where remarks equals to UPDATED_REMARKS
        defaultStockOutItemShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        // Get all the stockOutItemList where remarks is not null
        defaultStockOutItemShouldBeFound("remarks.specified=true");

        // Get all the stockOutItemList where remarks is null
        defaultStockOutItemShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockOutItemsByProductCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategories = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategories);
        em.flush();
        stockOutItem.setProductCategories(productCategories);
        stockOutItemRepository.saveAndFlush(stockOutItem);
        Long productCategoriesId = productCategories.getId();

        // Get all the stockOutItemList where productCategories equals to productCategoriesId
        defaultStockOutItemShouldBeFound("productCategoriesId.equals=" + productCategoriesId);

        // Get all the stockOutItemList where productCategories equals to productCategoriesId + 1
        defaultStockOutItemShouldNotBeFound("productCategoriesId.equals=" + (productCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllStockOutItemsByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        Product products = ProductResourceIntTest.createEntity(em);
        em.persist(products);
        em.flush();
        stockOutItem.setProducts(products);
        stockOutItemRepository.saveAndFlush(stockOutItem);
        Long productsId = products.getId();

        // Get all the stockOutItemList where products equals to productsId
        defaultStockOutItemShouldBeFound("productsId.equals=" + productsId);

        // Get all the stockOutItemList where products equals to productsId + 1
        defaultStockOutItemShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockOutItemsByInventoryLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventoryLocation inventoryLocations = InventoryLocationResourceIntTest.createEntity(em);
        em.persist(inventoryLocations);
        em.flush();
        stockOutItem.setInventoryLocations(inventoryLocations);
        stockOutItemRepository.saveAndFlush(stockOutItem);
        Long inventoryLocationsId = inventoryLocations.getId();

        // Get all the stockOutItemList where inventoryLocations equals to inventoryLocationsId
        defaultStockOutItemShouldBeFound("inventoryLocationsId.equals=" + inventoryLocationsId);

        // Get all the stockOutItemList where inventoryLocations equals to inventoryLocationsId + 1
        defaultStockOutItemShouldNotBeFound("inventoryLocationsId.equals=" + (inventoryLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockOutItemsByInventorySubLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventorySubLocation inventorySubLocations = InventorySubLocationResourceIntTest.createEntity(em);
        em.persist(inventorySubLocations);
        em.flush();
        stockOutItem.setInventorySubLocations(inventorySubLocations);
        stockOutItemRepository.saveAndFlush(stockOutItem);
        Long inventorySubLocationsId = inventorySubLocations.getId();

        // Get all the stockOutItemList where inventorySubLocations equals to inventorySubLocationsId
        defaultStockOutItemShouldBeFound("inventorySubLocationsId.equals=" + inventorySubLocationsId);

        // Get all the stockOutItemList where inventorySubLocations equals to inventorySubLocationsId + 1
        defaultStockOutItemShouldNotBeFound("inventorySubLocationsId.equals=" + (inventorySubLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockOutItemsByStockInItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        StockInItem stockInItems = StockInItemResourceIntTest.createEntity(em);
        em.persist(stockInItems);
        em.flush();
        stockOutItem.setStockInItems(stockInItems);
        stockOutItemRepository.saveAndFlush(stockOutItem);
        Long stockInItemsId = stockInItems.getId();

        // Get all the stockOutItemList where stockInItems equals to stockInItemsId
        defaultStockOutItemShouldBeFound("stockInItemsId.equals=" + stockInItemsId);

        // Get all the stockOutItemList where stockInItems equals to stockInItemsId + 1
        defaultStockOutItemShouldNotBeFound("stockInItemsId.equals=" + (stockInItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStockOutItemShouldBeFound(String filter) throws Exception {
        restStockOutItemMockMvc.perform(get("/api/stock-out-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockOutItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].stockOutBy").value(hasItem(DEFAULT_STOCK_OUT_BY)))
            .andExpect(jsonPath("$.[*].stockOutDate").value(hasItem(DEFAULT_STOCK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiverId").value(hasItem(DEFAULT_RECEIVER_ID)))
            .andExpect(jsonPath("$.[*].receivingPlace").value(hasItem(DEFAULT_RECEIVING_PLACE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restStockOutItemMockMvc.perform(get("/api/stock-out-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStockOutItemShouldNotBeFound(String filter) throws Exception {
        restStockOutItemMockMvc.perform(get("/api/stock-out-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockOutItemMockMvc.perform(get("/api/stock-out-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockOutItem() throws Exception {
        // Get the stockOutItem
        restStockOutItemMockMvc.perform(get("/api/stock-out-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockOutItem() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        int databaseSizeBeforeUpdate = stockOutItemRepository.findAll().size();

        // Update the stockOutItem
        StockOutItem updatedStockOutItem = stockOutItemRepository.findById(stockOutItem.getId()).get();
        // Disconnect from session so that the updates on updatedStockOutItem are not directly saved in db
        em.detach(updatedStockOutItem);
        updatedStockOutItem
            .containerTrackingId(UPDATED_CONTAINER_TRACKING_ID)
            .quantity(UPDATED_QUANTITY)
            .stockOutBy(UPDATED_STOCK_OUT_BY)
            .stockOutDate(UPDATED_STOCK_OUT_DATE)
            .receiverId(UPDATED_RECEIVER_ID)
            .receivingPlace(UPDATED_RECEIVING_PLACE)
            .remarks(UPDATED_REMARKS);
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(updatedStockOutItem);

        restStockOutItemMockMvc.perform(put("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isOk());

        // Validate the StockOutItem in the database
        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeUpdate);
        StockOutItem testStockOutItem = stockOutItemList.get(stockOutItemList.size() - 1);
        assertThat(testStockOutItem.getContainerTrackingId()).isEqualTo(UPDATED_CONTAINER_TRACKING_ID);
        assertThat(testStockOutItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockOutItem.getStockOutBy()).isEqualTo(UPDATED_STOCK_OUT_BY);
        assertThat(testStockOutItem.getStockOutDate()).isEqualTo(UPDATED_STOCK_OUT_DATE);
        assertThat(testStockOutItem.getReceiverId()).isEqualTo(UPDATED_RECEIVER_ID);
        assertThat(testStockOutItem.getReceivingPlace()).isEqualTo(UPDATED_RECEIVING_PLACE);
        assertThat(testStockOutItem.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the StockOutItem in Elasticsearch
        verify(mockStockOutItemSearchRepository, times(1)).save(testStockOutItem);
    }

    @Test
    @Transactional
    public void updateNonExistingStockOutItem() throws Exception {
        int databaseSizeBeforeUpdate = stockOutItemRepository.findAll().size();

        // Create the StockOutItem
        StockOutItemDTO stockOutItemDTO = stockOutItemMapper.toDto(stockOutItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockOutItemMockMvc.perform(put("/api/stock-out-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockOutItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockOutItem in the database
        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockOutItem in Elasticsearch
        verify(mockStockOutItemSearchRepository, times(0)).save(stockOutItem);
    }

    @Test
    @Transactional
    public void deleteStockOutItem() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);

        int databaseSizeBeforeDelete = stockOutItemRepository.findAll().size();

        // Delete the stockOutItem
        restStockOutItemMockMvc.perform(delete("/api/stock-out-items/{id}", stockOutItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockOutItem> stockOutItemList = stockOutItemRepository.findAll();
        assertThat(stockOutItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockOutItem in Elasticsearch
        verify(mockStockOutItemSearchRepository, times(1)).deleteById(stockOutItem.getId());
    }

    @Test
    @Transactional
    public void searchStockOutItem() throws Exception {
        // Initialize the database
        stockOutItemRepository.saveAndFlush(stockOutItem);
        when(mockStockOutItemSearchRepository.search(queryStringQuery("id:" + stockOutItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockOutItem), PageRequest.of(0, 1), 1));
        // Search the stockOutItem
        restStockOutItemMockMvc.perform(get("/api/_search/stock-out-items?query=id:" + stockOutItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockOutItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].stockOutBy").value(hasItem(DEFAULT_STOCK_OUT_BY)))
            .andExpect(jsonPath("$.[*].stockOutDate").value(hasItem(DEFAULT_STOCK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiverId").value(hasItem(DEFAULT_RECEIVER_ID)))
            .andExpect(jsonPath("$.[*].receivingPlace").value(hasItem(DEFAULT_RECEIVING_PLACE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockOutItem.class);
        StockOutItem stockOutItem1 = new StockOutItem();
        stockOutItem1.setId(1L);
        StockOutItem stockOutItem2 = new StockOutItem();
        stockOutItem2.setId(stockOutItem1.getId());
        assertThat(stockOutItem1).isEqualTo(stockOutItem2);
        stockOutItem2.setId(2L);
        assertThat(stockOutItem1).isNotEqualTo(stockOutItem2);
        stockOutItem1.setId(null);
        assertThat(stockOutItem1).isNotEqualTo(stockOutItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockOutItemDTO.class);
        StockOutItemDTO stockOutItemDTO1 = new StockOutItemDTO();
        stockOutItemDTO1.setId(1L);
        StockOutItemDTO stockOutItemDTO2 = new StockOutItemDTO();
        assertThat(stockOutItemDTO1).isNotEqualTo(stockOutItemDTO2);
        stockOutItemDTO2.setId(stockOutItemDTO1.getId());
        assertThat(stockOutItemDTO1).isEqualTo(stockOutItemDTO2);
        stockOutItemDTO2.setId(2L);
        assertThat(stockOutItemDTO1).isNotEqualTo(stockOutItemDTO2);
        stockOutItemDTO1.setId(null);
        assertThat(stockOutItemDTO1).isNotEqualTo(stockOutItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockOutItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockOutItemMapper.fromId(null)).isNull();
    }
}
