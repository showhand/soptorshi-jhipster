package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.CommercialPurchaseOrderItem;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.repository.CommercialPurchaseOrderItemRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderItemSearchRepository;
import org.soptorshi.service.CommercialPurchaseOrderItemService;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderItemMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemCriteria;
import org.soptorshi.service.CommercialPurchaseOrderItemQueryService;

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

import org.soptorshi.domain.enumeration.CommercialCurrency;
/**
 * Test class for the CommercialPurchaseOrderItemResource REST controller.
 *
 * @see CommercialPurchaseOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPurchaseOrderItemResourceIntTest {

    private static final String DEFAULT_GOODS_OR_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_GOODS_OR_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGING = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGING = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE_OR_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE_OR_GRADE = "BBBBBBBBBB";

    private static final Double DEFAULT_QTY_OR_MC = 1D;
    private static final Double UPDATED_QTY_OR_MC = 2D;

    private static final Double DEFAULT_QTY_OR_KGS = 1D;
    private static final Double UPDATED_QTY_OR_KGS = 2D;

    private static final Double DEFAULT_RATE_OR_KG = 1D;
    private static final Double UPDATED_RATE_OR_KG = 2D;

    private static final CommercialCurrency DEFAULT_CURRENCY_TYPE = CommercialCurrency.BDT;
    private static final CommercialCurrency UPDATED_CURRENCY_TYPE = CommercialCurrency.US_DOLLAR;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialPurchaseOrderItemRepository commercialPurchaseOrderItemRepository;

    @Autowired
    private CommercialPurchaseOrderItemMapper commercialPurchaseOrderItemMapper;

    @Autowired
    private CommercialPurchaseOrderItemService commercialPurchaseOrderItemService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPurchaseOrderItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPurchaseOrderItemSearchRepository mockCommercialPurchaseOrderItemSearchRepository;

    @Autowired
    private CommercialPurchaseOrderItemQueryService commercialPurchaseOrderItemQueryService;

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

    private MockMvc restCommercialPurchaseOrderItemMockMvc;

    private CommercialPurchaseOrderItem commercialPurchaseOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPurchaseOrderItemResource commercialPurchaseOrderItemResource = new CommercialPurchaseOrderItemResource(commercialPurchaseOrderItemService, commercialPurchaseOrderItemQueryService);
        this.restCommercialPurchaseOrderItemMockMvc = MockMvcBuilders.standaloneSetup(commercialPurchaseOrderItemResource)
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
    public static CommercialPurchaseOrderItem createEntity(EntityManager em) {
        CommercialPurchaseOrderItem commercialPurchaseOrderItem = new CommercialPurchaseOrderItem()
            .goodsOrServices(DEFAULT_GOODS_OR_SERVICES)
            .descriptionOfGoodsOrServices(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES)
            .packaging(DEFAULT_PACKAGING)
            .sizeOrGrade(DEFAULT_SIZE_OR_GRADE)
            .qtyOrMc(DEFAULT_QTY_OR_MC)
            .qtyOrKgs(DEFAULT_QTY_OR_KGS)
            .rateOrKg(DEFAULT_RATE_OR_KG)
            .currencyType(DEFAULT_CURRENCY_TYPE)
            .total(DEFAULT_TOTAL)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPurchaseOrderItem;
    }

    @Before
    public void initTest() {
        commercialPurchaseOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPurchaseOrderItem() throws Exception {
        int databaseSizeBeforeCreate = commercialPurchaseOrderItemRepository.findAll().size();

        // Create the CommercialPurchaseOrderItem
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);
        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPurchaseOrderItem in the database
        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPurchaseOrderItem testCommercialPurchaseOrderItem = commercialPurchaseOrderItemList.get(commercialPurchaseOrderItemList.size() - 1);
        assertThat(testCommercialPurchaseOrderItem.getGoodsOrServices()).isEqualTo(DEFAULT_GOODS_OR_SERVICES);
        assertThat(testCommercialPurchaseOrderItem.getDescriptionOfGoodsOrServices()).isEqualTo(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES);
        assertThat(testCommercialPurchaseOrderItem.getPackaging()).isEqualTo(DEFAULT_PACKAGING);
        assertThat(testCommercialPurchaseOrderItem.getSizeOrGrade()).isEqualTo(DEFAULT_SIZE_OR_GRADE);
        assertThat(testCommercialPurchaseOrderItem.getQtyOrMc()).isEqualTo(DEFAULT_QTY_OR_MC);
        assertThat(testCommercialPurchaseOrderItem.getQtyOrKgs()).isEqualTo(DEFAULT_QTY_OR_KGS);
        assertThat(testCommercialPurchaseOrderItem.getRateOrKg()).isEqualTo(DEFAULT_RATE_OR_KG);
        assertThat(testCommercialPurchaseOrderItem.getCurrencyType()).isEqualTo(DEFAULT_CURRENCY_TYPE);
        assertThat(testCommercialPurchaseOrderItem.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testCommercialPurchaseOrderItem.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPurchaseOrderItem.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPurchaseOrderItem.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPurchaseOrderItem.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPurchaseOrderItem in Elasticsearch
        verify(mockCommercialPurchaseOrderItemSearchRepository, times(1)).save(testCommercialPurchaseOrderItem);
    }

    @Test
    @Transactional
    public void createCommercialPurchaseOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPurchaseOrderItemRepository.findAll().size();

        // Create the CommercialPurchaseOrderItem with an existing ID
        commercialPurchaseOrderItem.setId(1L);
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPurchaseOrderItem in the database
        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPurchaseOrderItem in Elasticsearch
        verify(mockCommercialPurchaseOrderItemSearchRepository, times(0)).save(commercialPurchaseOrderItem);
    }

    @Test
    @Transactional
    public void checkGoodsOrServicesIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setGoodsOrServices(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeOrGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setSizeOrGrade(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyOrMcIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setQtyOrMc(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyOrKgsIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setQtyOrKgs(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateOrKgIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setRateOrKg(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setCurrencyType(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPurchaseOrderItemRepository.findAll().size();
        // set the field null
        commercialPurchaseOrderItem.setTotal(null);

        // Create the CommercialPurchaseOrderItem, which fails.
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(post("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItems() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodsOrServices").value(hasItem(DEFAULT_GOODS_OR_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].descriptionOfGoodsOrServices").value(hasItem(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES.toString())))
            .andExpect(jsonPath("$.[*].packaging").value(hasItem(DEFAULT_PACKAGING.toString())))
            .andExpect(jsonPath("$.[*].sizeOrGrade").value(hasItem(DEFAULT_SIZE_OR_GRADE.toString())))
            .andExpect(jsonPath("$.[*].qtyOrMc").value(hasItem(DEFAULT_QTY_OR_MC.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyOrKgs").value(hasItem(DEFAULT_QTY_OR_KGS.doubleValue())))
            .andExpect(jsonPath("$.[*].rateOrKg").value(hasItem(DEFAULT_RATE_OR_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCommercialPurchaseOrderItem() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get the commercialPurchaseOrderItem
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items/{id}", commercialPurchaseOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPurchaseOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.goodsOrServices").value(DEFAULT_GOODS_OR_SERVICES.toString()))
            .andExpect(jsonPath("$.descriptionOfGoodsOrServices").value(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES.toString()))
            .andExpect(jsonPath("$.packaging").value(DEFAULT_PACKAGING.toString()))
            .andExpect(jsonPath("$.sizeOrGrade").value(DEFAULT_SIZE_OR_GRADE.toString()))
            .andExpect(jsonPath("$.qtyOrMc").value(DEFAULT_QTY_OR_MC.doubleValue()))
            .andExpect(jsonPath("$.qtyOrKgs").value(DEFAULT_QTY_OR_KGS.doubleValue()))
            .andExpect(jsonPath("$.rateOrKg").value(DEFAULT_RATE_OR_KG.doubleValue()))
            .andExpect(jsonPath("$.currencyType").value(DEFAULT_CURRENCY_TYPE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByGoodsOrServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where goodsOrServices equals to DEFAULT_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldBeFound("goodsOrServices.equals=" + DEFAULT_GOODS_OR_SERVICES);

        // Get all the commercialPurchaseOrderItemList where goodsOrServices equals to UPDATED_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldNotBeFound("goodsOrServices.equals=" + UPDATED_GOODS_OR_SERVICES);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByGoodsOrServicesIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where goodsOrServices in DEFAULT_GOODS_OR_SERVICES or UPDATED_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldBeFound("goodsOrServices.in=" + DEFAULT_GOODS_OR_SERVICES + "," + UPDATED_GOODS_OR_SERVICES);

        // Get all the commercialPurchaseOrderItemList where goodsOrServices equals to UPDATED_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldNotBeFound("goodsOrServices.in=" + UPDATED_GOODS_OR_SERVICES);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByGoodsOrServicesIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where goodsOrServices is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("goodsOrServices.specified=true");

        // Get all the commercialPurchaseOrderItemList where goodsOrServices is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("goodsOrServices.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByDescriptionOfGoodsOrServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices equals to DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldBeFound("descriptionOfGoodsOrServices.equals=" + DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES);

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices equals to UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldNotBeFound("descriptionOfGoodsOrServices.equals=" + UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByDescriptionOfGoodsOrServicesIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices in DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES or UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldBeFound("descriptionOfGoodsOrServices.in=" + DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES + "," + UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES);

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices equals to UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES
        defaultCommercialPurchaseOrderItemShouldNotBeFound("descriptionOfGoodsOrServices.in=" + UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByDescriptionOfGoodsOrServicesIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("descriptionOfGoodsOrServices.specified=true");

        // Get all the commercialPurchaseOrderItemList where descriptionOfGoodsOrServices is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("descriptionOfGoodsOrServices.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByPackagingIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where packaging equals to DEFAULT_PACKAGING
        defaultCommercialPurchaseOrderItemShouldBeFound("packaging.equals=" + DEFAULT_PACKAGING);

        // Get all the commercialPurchaseOrderItemList where packaging equals to UPDATED_PACKAGING
        defaultCommercialPurchaseOrderItemShouldNotBeFound("packaging.equals=" + UPDATED_PACKAGING);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByPackagingIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where packaging in DEFAULT_PACKAGING or UPDATED_PACKAGING
        defaultCommercialPurchaseOrderItemShouldBeFound("packaging.in=" + DEFAULT_PACKAGING + "," + UPDATED_PACKAGING);

        // Get all the commercialPurchaseOrderItemList where packaging equals to UPDATED_PACKAGING
        defaultCommercialPurchaseOrderItemShouldNotBeFound("packaging.in=" + UPDATED_PACKAGING);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByPackagingIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where packaging is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("packaging.specified=true");

        // Get all the commercialPurchaseOrderItemList where packaging is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("packaging.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsBySizeOrGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade equals to DEFAULT_SIZE_OR_GRADE
        defaultCommercialPurchaseOrderItemShouldBeFound("sizeOrGrade.equals=" + DEFAULT_SIZE_OR_GRADE);

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade equals to UPDATED_SIZE_OR_GRADE
        defaultCommercialPurchaseOrderItemShouldNotBeFound("sizeOrGrade.equals=" + UPDATED_SIZE_OR_GRADE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsBySizeOrGradeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade in DEFAULT_SIZE_OR_GRADE or UPDATED_SIZE_OR_GRADE
        defaultCommercialPurchaseOrderItemShouldBeFound("sizeOrGrade.in=" + DEFAULT_SIZE_OR_GRADE + "," + UPDATED_SIZE_OR_GRADE);

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade equals to UPDATED_SIZE_OR_GRADE
        defaultCommercialPurchaseOrderItemShouldNotBeFound("sizeOrGrade.in=" + UPDATED_SIZE_OR_GRADE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsBySizeOrGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("sizeOrGrade.specified=true");

        // Get all the commercialPurchaseOrderItemList where sizeOrGrade is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("sizeOrGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrMcIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrMc equals to DEFAULT_QTY_OR_MC
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrMc.equals=" + DEFAULT_QTY_OR_MC);

        // Get all the commercialPurchaseOrderItemList where qtyOrMc equals to UPDATED_QTY_OR_MC
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrMc.equals=" + UPDATED_QTY_OR_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrMcIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrMc in DEFAULT_QTY_OR_MC or UPDATED_QTY_OR_MC
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrMc.in=" + DEFAULT_QTY_OR_MC + "," + UPDATED_QTY_OR_MC);

        // Get all the commercialPurchaseOrderItemList where qtyOrMc equals to UPDATED_QTY_OR_MC
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrMc.in=" + UPDATED_QTY_OR_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrMcIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrMc is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrMc.specified=true");

        // Get all the commercialPurchaseOrderItemList where qtyOrMc is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrMc.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrKgsIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs equals to DEFAULT_QTY_OR_KGS
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrKgs.equals=" + DEFAULT_QTY_OR_KGS);

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs equals to UPDATED_QTY_OR_KGS
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrKgs.equals=" + UPDATED_QTY_OR_KGS);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrKgsIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs in DEFAULT_QTY_OR_KGS or UPDATED_QTY_OR_KGS
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrKgs.in=" + DEFAULT_QTY_OR_KGS + "," + UPDATED_QTY_OR_KGS);

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs equals to UPDATED_QTY_OR_KGS
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrKgs.in=" + UPDATED_QTY_OR_KGS);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByQtyOrKgsIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("qtyOrKgs.specified=true");

        // Get all the commercialPurchaseOrderItemList where qtyOrKgs is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("qtyOrKgs.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByRateOrKgIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where rateOrKg equals to DEFAULT_RATE_OR_KG
        defaultCommercialPurchaseOrderItemShouldBeFound("rateOrKg.equals=" + DEFAULT_RATE_OR_KG);

        // Get all the commercialPurchaseOrderItemList where rateOrKg equals to UPDATED_RATE_OR_KG
        defaultCommercialPurchaseOrderItemShouldNotBeFound("rateOrKg.equals=" + UPDATED_RATE_OR_KG);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByRateOrKgIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where rateOrKg in DEFAULT_RATE_OR_KG or UPDATED_RATE_OR_KG
        defaultCommercialPurchaseOrderItemShouldBeFound("rateOrKg.in=" + DEFAULT_RATE_OR_KG + "," + UPDATED_RATE_OR_KG);

        // Get all the commercialPurchaseOrderItemList where rateOrKg equals to UPDATED_RATE_OR_KG
        defaultCommercialPurchaseOrderItemShouldNotBeFound("rateOrKg.in=" + UPDATED_RATE_OR_KG);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByRateOrKgIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where rateOrKg is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("rateOrKg.specified=true");

        // Get all the commercialPurchaseOrderItemList where rateOrKg is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("rateOrKg.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCurrencyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where currencyType equals to DEFAULT_CURRENCY_TYPE
        defaultCommercialPurchaseOrderItemShouldBeFound("currencyType.equals=" + DEFAULT_CURRENCY_TYPE);

        // Get all the commercialPurchaseOrderItemList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialPurchaseOrderItemShouldNotBeFound("currencyType.equals=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCurrencyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where currencyType in DEFAULT_CURRENCY_TYPE or UPDATED_CURRENCY_TYPE
        defaultCommercialPurchaseOrderItemShouldBeFound("currencyType.in=" + DEFAULT_CURRENCY_TYPE + "," + UPDATED_CURRENCY_TYPE);

        // Get all the commercialPurchaseOrderItemList where currencyType equals to UPDATED_CURRENCY_TYPE
        defaultCommercialPurchaseOrderItemShouldNotBeFound("currencyType.in=" + UPDATED_CURRENCY_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCurrencyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where currencyType is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("currencyType.specified=true");

        // Get all the commercialPurchaseOrderItemList where currencyType is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("currencyType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where total equals to DEFAULT_TOTAL
        defaultCommercialPurchaseOrderItemShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the commercialPurchaseOrderItemList where total equals to UPDATED_TOTAL
        defaultCommercialPurchaseOrderItemShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultCommercialPurchaseOrderItemShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the commercialPurchaseOrderItemList where total equals to UPDATED_TOTAL
        defaultCommercialPurchaseOrderItemShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where total is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("total.specified=true");

        // Get all the commercialPurchaseOrderItemList where total is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPurchaseOrderItemShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPurchaseOrderItemList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderItemShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPurchaseOrderItemList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdBy is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("createdBy.specified=true");

        // Get all the commercialPurchaseOrderItemList where createdBy is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderItemList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPurchaseOrderItemList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdOn is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("createdOn.specified=true");

        // Get all the commercialPurchaseOrderItemList where createdOn is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdOn greater than or equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("createdOn.greaterOrEqualThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderItemList where createdOn greater than or equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdOn.greaterOrEqualThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where createdOn less than or equals to DEFAULT_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the commercialPurchaseOrderItemList where createdOn less than or equals to UPDATED_CREATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPurchaseOrderItemList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPurchaseOrderItemList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedBy is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPurchaseOrderItemList where updatedBy is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderItemList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPurchaseOrderItemList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedOn is not null
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPurchaseOrderItemList where updatedOn is null
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderItemList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        // Get all the commercialPurchaseOrderItemList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPurchaseOrderItemList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialPurchaseOrderItemShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPurchaseOrderItemsByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialPurchaseOrderItem.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialPurchaseOrderItemList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialPurchaseOrderItemShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialPurchaseOrderItemList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialPurchaseOrderItemShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPurchaseOrderItemShouldBeFound(String filter) throws Exception {
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodsOrServices").value(hasItem(DEFAULT_GOODS_OR_SERVICES)))
            .andExpect(jsonPath("$.[*].descriptionOfGoodsOrServices").value(hasItem(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES)))
            .andExpect(jsonPath("$.[*].packaging").value(hasItem(DEFAULT_PACKAGING)))
            .andExpect(jsonPath("$.[*].sizeOrGrade").value(hasItem(DEFAULT_SIZE_OR_GRADE)))
            .andExpect(jsonPath("$.[*].qtyOrMc").value(hasItem(DEFAULT_QTY_OR_MC.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyOrKgs").value(hasItem(DEFAULT_QTY_OR_KGS.doubleValue())))
            .andExpect(jsonPath("$.[*].rateOrKg").value(hasItem(DEFAULT_RATE_OR_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPurchaseOrderItemShouldNotBeFound(String filter) throws Exception {
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPurchaseOrderItem() throws Exception {
        // Get the commercialPurchaseOrderItem
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/commercial-purchase-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPurchaseOrderItem() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        int databaseSizeBeforeUpdate = commercialPurchaseOrderItemRepository.findAll().size();

        // Update the commercialPurchaseOrderItem
        CommercialPurchaseOrderItem updatedCommercialPurchaseOrderItem = commercialPurchaseOrderItemRepository.findById(commercialPurchaseOrderItem.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPurchaseOrderItem are not directly saved in db
        em.detach(updatedCommercialPurchaseOrderItem);
        updatedCommercialPurchaseOrderItem
            .goodsOrServices(UPDATED_GOODS_OR_SERVICES)
            .descriptionOfGoodsOrServices(UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES)
            .packaging(UPDATED_PACKAGING)
            .sizeOrGrade(UPDATED_SIZE_OR_GRADE)
            .qtyOrMc(UPDATED_QTY_OR_MC)
            .qtyOrKgs(UPDATED_QTY_OR_KGS)
            .rateOrKg(UPDATED_RATE_OR_KG)
            .currencyType(UPDATED_CURRENCY_TYPE)
            .total(UPDATED_TOTAL)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(updatedCommercialPurchaseOrderItem);

        restCommercialPurchaseOrderItemMockMvc.perform(put("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPurchaseOrderItem in the database
        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeUpdate);
        CommercialPurchaseOrderItem testCommercialPurchaseOrderItem = commercialPurchaseOrderItemList.get(commercialPurchaseOrderItemList.size() - 1);
        assertThat(testCommercialPurchaseOrderItem.getGoodsOrServices()).isEqualTo(UPDATED_GOODS_OR_SERVICES);
        assertThat(testCommercialPurchaseOrderItem.getDescriptionOfGoodsOrServices()).isEqualTo(UPDATED_DESCRIPTION_OF_GOODS_OR_SERVICES);
        assertThat(testCommercialPurchaseOrderItem.getPackaging()).isEqualTo(UPDATED_PACKAGING);
        assertThat(testCommercialPurchaseOrderItem.getSizeOrGrade()).isEqualTo(UPDATED_SIZE_OR_GRADE);
        assertThat(testCommercialPurchaseOrderItem.getQtyOrMc()).isEqualTo(UPDATED_QTY_OR_MC);
        assertThat(testCommercialPurchaseOrderItem.getQtyOrKgs()).isEqualTo(UPDATED_QTY_OR_KGS);
        assertThat(testCommercialPurchaseOrderItem.getRateOrKg()).isEqualTo(UPDATED_RATE_OR_KG);
        assertThat(testCommercialPurchaseOrderItem.getCurrencyType()).isEqualTo(UPDATED_CURRENCY_TYPE);
        assertThat(testCommercialPurchaseOrderItem.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testCommercialPurchaseOrderItem.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPurchaseOrderItem.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPurchaseOrderItem.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPurchaseOrderItem.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPurchaseOrderItem in Elasticsearch
        verify(mockCommercialPurchaseOrderItemSearchRepository, times(1)).save(testCommercialPurchaseOrderItem);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPurchaseOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = commercialPurchaseOrderItemRepository.findAll().size();

        // Create the CommercialPurchaseOrderItem
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPurchaseOrderItemMockMvc.perform(put("/api/commercial-purchase-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPurchaseOrderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPurchaseOrderItem in the database
        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPurchaseOrderItem in Elasticsearch
        verify(mockCommercialPurchaseOrderItemSearchRepository, times(0)).save(commercialPurchaseOrderItem);
    }

    @Test
    @Transactional
    public void deleteCommercialPurchaseOrderItem() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);

        int databaseSizeBeforeDelete = commercialPurchaseOrderItemRepository.findAll().size();

        // Delete the commercialPurchaseOrderItem
        restCommercialPurchaseOrderItemMockMvc.perform(delete("/api/commercial-purchase-order-items/{id}", commercialPurchaseOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPurchaseOrderItem> commercialPurchaseOrderItemList = commercialPurchaseOrderItemRepository.findAll();
        assertThat(commercialPurchaseOrderItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPurchaseOrderItem in Elasticsearch
        verify(mockCommercialPurchaseOrderItemSearchRepository, times(1)).deleteById(commercialPurchaseOrderItem.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPurchaseOrderItem() throws Exception {
        // Initialize the database
        commercialPurchaseOrderItemRepository.saveAndFlush(commercialPurchaseOrderItem);
        when(mockCommercialPurchaseOrderItemSearchRepository.search(queryStringQuery("id:" + commercialPurchaseOrderItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPurchaseOrderItem), PageRequest.of(0, 1), 1));
        // Search the commercialPurchaseOrderItem
        restCommercialPurchaseOrderItemMockMvc.perform(get("/api/_search/commercial-purchase-order-items?query=id:" + commercialPurchaseOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPurchaseOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodsOrServices").value(hasItem(DEFAULT_GOODS_OR_SERVICES)))
            .andExpect(jsonPath("$.[*].descriptionOfGoodsOrServices").value(hasItem(DEFAULT_DESCRIPTION_OF_GOODS_OR_SERVICES)))
            .andExpect(jsonPath("$.[*].packaging").value(hasItem(DEFAULT_PACKAGING)))
            .andExpect(jsonPath("$.[*].sizeOrGrade").value(hasItem(DEFAULT_SIZE_OR_GRADE)))
            .andExpect(jsonPath("$.[*].qtyOrMc").value(hasItem(DEFAULT_QTY_OR_MC.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyOrKgs").value(hasItem(DEFAULT_QTY_OR_KGS.doubleValue())))
            .andExpect(jsonPath("$.[*].rateOrKg").value(hasItem(DEFAULT_RATE_OR_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyType").value(hasItem(DEFAULT_CURRENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPurchaseOrderItem.class);
        CommercialPurchaseOrderItem commercialPurchaseOrderItem1 = new CommercialPurchaseOrderItem();
        commercialPurchaseOrderItem1.setId(1L);
        CommercialPurchaseOrderItem commercialPurchaseOrderItem2 = new CommercialPurchaseOrderItem();
        commercialPurchaseOrderItem2.setId(commercialPurchaseOrderItem1.getId());
        assertThat(commercialPurchaseOrderItem1).isEqualTo(commercialPurchaseOrderItem2);
        commercialPurchaseOrderItem2.setId(2L);
        assertThat(commercialPurchaseOrderItem1).isNotEqualTo(commercialPurchaseOrderItem2);
        commercialPurchaseOrderItem1.setId(null);
        assertThat(commercialPurchaseOrderItem1).isNotEqualTo(commercialPurchaseOrderItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPurchaseOrderItemDTO.class);
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO1 = new CommercialPurchaseOrderItemDTO();
        commercialPurchaseOrderItemDTO1.setId(1L);
        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO2 = new CommercialPurchaseOrderItemDTO();
        assertThat(commercialPurchaseOrderItemDTO1).isNotEqualTo(commercialPurchaseOrderItemDTO2);
        commercialPurchaseOrderItemDTO2.setId(commercialPurchaseOrderItemDTO1.getId());
        assertThat(commercialPurchaseOrderItemDTO1).isEqualTo(commercialPurchaseOrderItemDTO2);
        commercialPurchaseOrderItemDTO2.setId(2L);
        assertThat(commercialPurchaseOrderItemDTO1).isNotEqualTo(commercialPurchaseOrderItemDTO2);
        commercialPurchaseOrderItemDTO1.setId(null);
        assertThat(commercialPurchaseOrderItemDTO1).isNotEqualTo(commercialPurchaseOrderItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPurchaseOrderItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPurchaseOrderItemMapper.fromId(null)).isNull();
    }
}
