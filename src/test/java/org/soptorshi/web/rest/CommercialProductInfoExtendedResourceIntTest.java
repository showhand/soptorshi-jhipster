package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.domain.Product;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.service.CommercialProductInfoQueryService;
import org.soptorshi.service.CommercialProductInfoService;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
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
 * Test class for the CommercialProductInfoResource REST controller.
 *
 * @see CommercialProductInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialProductInfoExtendedResourceIntTest {

    private static final Integer DEFAULT_SERIAL_NO = 1;
    private static final Integer UPDATED_SERIAL_NO = 2;

    private static final String DEFAULT_PACKAGING_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGING_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_OFFERED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_OFFERED_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_OFFERED_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_OFFERED_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OFFERED_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_TOTAL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BUYING_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_BUYING_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_BUYING_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_BUYING_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BUYING_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_TOTAL_PRICE = new BigDecimal(2);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommercialProductInfoRepository commercialProductInfoRepository;

    @Autowired
    private CommercialProductInfoMapper commercialProductInfoMapper;

    @Autowired
    private CommercialProductInfoService commercialProductInfoService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialProductInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialProductInfoSearchRepository mockCommercialProductInfoSearchRepository;

    @Autowired
    private CommercialProductInfoQueryService commercialProductInfoQueryService;

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

    private MockMvc restCommercialProductInfoMockMvc;

    private CommercialProductInfo commercialProductInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialProductInfoResource commercialProductInfoResource = new CommercialProductInfoResource(commercialProductInfoService, commercialProductInfoQueryService);
        this.restCommercialProductInfoMockMvc = MockMvcBuilders.standaloneSetup(commercialProductInfoResource)
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
    public static CommercialProductInfo createEntity(EntityManager em) {
        CommercialProductInfo commercialProductInfo = new CommercialProductInfo()
            .serialNo(DEFAULT_SERIAL_NO)
            .packagingDescription(DEFAULT_PACKAGING_DESCRIPTION)
            .othersDescription(DEFAULT_OTHERS_DESCRIPTION)
            .offeredQuantity(DEFAULT_OFFERED_QUANTITY)
            .offeredUnit(DEFAULT_OFFERED_UNIT)
            .offeredUnitPrice(DEFAULT_OFFERED_UNIT_PRICE)
            .offeredTotalPrice(DEFAULT_OFFERED_TOTAL_PRICE)
            .buyingQuantity(DEFAULT_BUYING_QUANTITY)
            .buyingUnit(DEFAULT_BUYING_UNIT)
            .buyingUnitPrice(DEFAULT_BUYING_UNIT_PRICE)
            .buyingTotalPrice(DEFAULT_BUYING_TOTAL_PRICE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialProductInfo;
    }

    @Before
    public void initTest() {
        commercialProductInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialProductInfo() throws Exception {
        int databaseSizeBeforeCreate = commercialProductInfoRepository.findAll().size();

        // Create the CommercialProductInfo
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);
        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialProductInfo in the database
        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialProductInfo testCommercialProductInfo = commercialProductInfoList.get(commercialProductInfoList.size() - 1);
        assertThat(testCommercialProductInfo.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testCommercialProductInfo.getPackagingDescription()).isEqualTo(DEFAULT_PACKAGING_DESCRIPTION);
        assertThat(testCommercialProductInfo.getOthersDescription()).isEqualTo(DEFAULT_OTHERS_DESCRIPTION);
        assertThat(testCommercialProductInfo.getOfferedQuantity()).isEqualTo(DEFAULT_OFFERED_QUANTITY);
        assertThat(testCommercialProductInfo.getOfferedUnit()).isEqualTo(DEFAULT_OFFERED_UNIT);
        assertThat(testCommercialProductInfo.getOfferedUnitPrice()).isEqualTo(DEFAULT_OFFERED_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getOfferedTotalPrice()).isEqualTo(DEFAULT_OFFERED_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getBuyingQuantity()).isEqualTo(DEFAULT_BUYING_QUANTITY);
        assertThat(testCommercialProductInfo.getBuyingUnit()).isEqualTo(DEFAULT_BUYING_UNIT);
        assertThat(testCommercialProductInfo.getBuyingUnitPrice()).isEqualTo(DEFAULT_BUYING_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getBuyingTotalPrice()).isEqualTo(DEFAULT_BUYING_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialProductInfo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialProductInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialProductInfo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialProductInfo in Elasticsearch
        verify(mockCommercialProductInfoSearchRepository, times(1)).save(testCommercialProductInfo);
    }

    @Test
    @Transactional
    public void createCommercialProductInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialProductInfoRepository.findAll().size();

        // Create the CommercialProductInfo with an existing ID
        commercialProductInfo.setId(1L);
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialProductInfo in the database
        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialProductInfo in Elasticsearch
        verify(mockCommercialProductInfoSearchRepository, times(0)).save(commercialProductInfo);
    }

    @Test
    @Transactional
    public void checkSerialNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setSerialNo(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfferedQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setOfferedQuantity(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfferedUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setOfferedUnit(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfferedUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setOfferedUnitPrice(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfferedTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setOfferedTotalPrice(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyingQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setBuyingQuantity(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyingUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setBuyingUnit(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyingUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setBuyingUnitPrice(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyingTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setBuyingTotalPrice(null);

        // Create the CommercialProductInfo, which fails.
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        restCommercialProductInfoMockMvc.perform(post("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfos() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProductInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].packagingDescription").value(hasItem(DEFAULT_PACKAGING_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].othersDescription").value(hasItem(DEFAULT_OTHERS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingTotalPrice").value(hasItem(DEFAULT_BUYING_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialProductInfo() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get the commercialProductInfo
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos/{id}", commercialProductInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialProductInfo.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.packagingDescription").value(DEFAULT_PACKAGING_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.othersDescription").value(DEFAULT_OTHERS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.offeredQuantity").value(DEFAULT_OFFERED_QUANTITY.intValue()))
            .andExpect(jsonPath("$.offeredUnit").value(DEFAULT_OFFERED_UNIT.toString()))
            .andExpect(jsonPath("$.offeredUnitPrice").value(DEFAULT_OFFERED_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.offeredTotalPrice").value(DEFAULT_OFFERED_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.buyingQuantity").value(DEFAULT_BUYING_QUANTITY.intValue()))
            .andExpect(jsonPath("$.buyingUnit").value(DEFAULT_BUYING_UNIT.toString()))
            .andExpect(jsonPath("$.buyingUnitPrice").value(DEFAULT_BUYING_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.buyingTotalPrice").value(DEFAULT_BUYING_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where serialNo equals to DEFAULT_SERIAL_NO
        defaultCommercialProductInfoShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the commercialProductInfoList where serialNo equals to UPDATED_SERIAL_NO
        defaultCommercialProductInfoShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultCommercialProductInfoShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the commercialProductInfoList where serialNo equals to UPDATED_SERIAL_NO
        defaultCommercialProductInfoShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where serialNo is not null
        defaultCommercialProductInfoShouldBeFound("serialNo.specified=true");

        // Get all the commercialProductInfoList where serialNo is null
        defaultCommercialProductInfoShouldNotBeFound("serialNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySerialNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where serialNo greater than or equals to DEFAULT_SERIAL_NO
        defaultCommercialProductInfoShouldBeFound("serialNo.greaterOrEqualThan=" + DEFAULT_SERIAL_NO);

        // Get all the commercialProductInfoList where serialNo greater than or equals to UPDATED_SERIAL_NO
        defaultCommercialProductInfoShouldNotBeFound("serialNo.greaterOrEqualThan=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySerialNoIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where serialNo less than or equals to DEFAULT_SERIAL_NO
        defaultCommercialProductInfoShouldNotBeFound("serialNo.lessThan=" + DEFAULT_SERIAL_NO);

        // Get all the commercialProductInfoList where serialNo less than or equals to UPDATED_SERIAL_NO
        defaultCommercialProductInfoShouldBeFound("serialNo.lessThan=" + UPDATED_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllCommercialProductInfosByPackagingDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where packagingDescription equals to DEFAULT_PACKAGING_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("packagingDescription.equals=" + DEFAULT_PACKAGING_DESCRIPTION);

        // Get all the commercialProductInfoList where packagingDescription equals to UPDATED_PACKAGING_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("packagingDescription.equals=" + UPDATED_PACKAGING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByPackagingDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where packagingDescription in DEFAULT_PACKAGING_DESCRIPTION or UPDATED_PACKAGING_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("packagingDescription.in=" + DEFAULT_PACKAGING_DESCRIPTION + "," + UPDATED_PACKAGING_DESCRIPTION);

        // Get all the commercialProductInfoList where packagingDescription equals to UPDATED_PACKAGING_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("packagingDescription.in=" + UPDATED_PACKAGING_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByPackagingDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where packagingDescription is not null
        defaultCommercialProductInfoShouldBeFound("packagingDescription.specified=true");

        // Get all the commercialProductInfoList where packagingDescription is null
        defaultCommercialProductInfoShouldNotBeFound("packagingDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOthersDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where othersDescription equals to DEFAULT_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("othersDescription.equals=" + DEFAULT_OTHERS_DESCRIPTION);

        // Get all the commercialProductInfoList where othersDescription equals to UPDATED_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("othersDescription.equals=" + UPDATED_OTHERS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOthersDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where othersDescription in DEFAULT_OTHERS_DESCRIPTION or UPDATED_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("othersDescription.in=" + DEFAULT_OTHERS_DESCRIPTION + "," + UPDATED_OTHERS_DESCRIPTION);

        // Get all the commercialProductInfoList where othersDescription equals to UPDATED_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("othersDescription.in=" + UPDATED_OTHERS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOthersDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where othersDescription is not null
        defaultCommercialProductInfoShouldBeFound("othersDescription.specified=true");

        // Get all the commercialProductInfoList where othersDescription is null
        defaultCommercialProductInfoShouldNotBeFound("othersDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredQuantity equals to DEFAULT_OFFERED_QUANTITY
        defaultCommercialProductInfoShouldBeFound("offeredQuantity.equals=" + DEFAULT_OFFERED_QUANTITY);

        // Get all the commercialProductInfoList where offeredQuantity equals to UPDATED_OFFERED_QUANTITY
        defaultCommercialProductInfoShouldNotBeFound("offeredQuantity.equals=" + UPDATED_OFFERED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredQuantity in DEFAULT_OFFERED_QUANTITY or UPDATED_OFFERED_QUANTITY
        defaultCommercialProductInfoShouldBeFound("offeredQuantity.in=" + DEFAULT_OFFERED_QUANTITY + "," + UPDATED_OFFERED_QUANTITY);

        // Get all the commercialProductInfoList where offeredQuantity equals to UPDATED_OFFERED_QUANTITY
        defaultCommercialProductInfoShouldNotBeFound("offeredQuantity.in=" + UPDATED_OFFERED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredQuantity is not null
        defaultCommercialProductInfoShouldBeFound("offeredQuantity.specified=true");

        // Get all the commercialProductInfoList where offeredQuantity is null
        defaultCommercialProductInfoShouldNotBeFound("offeredQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnit equals to DEFAULT_OFFERED_UNIT
        defaultCommercialProductInfoShouldBeFound("offeredUnit.equals=" + DEFAULT_OFFERED_UNIT);

        // Get all the commercialProductInfoList where offeredUnit equals to UPDATED_OFFERED_UNIT
        defaultCommercialProductInfoShouldNotBeFound("offeredUnit.equals=" + UPDATED_OFFERED_UNIT);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnit in DEFAULT_OFFERED_UNIT or UPDATED_OFFERED_UNIT
        defaultCommercialProductInfoShouldBeFound("offeredUnit.in=" + DEFAULT_OFFERED_UNIT + "," + UPDATED_OFFERED_UNIT);

        // Get all the commercialProductInfoList where offeredUnit equals to UPDATED_OFFERED_UNIT
        defaultCommercialProductInfoShouldNotBeFound("offeredUnit.in=" + UPDATED_OFFERED_UNIT);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnit is not null
        defaultCommercialProductInfoShouldBeFound("offeredUnit.specified=true");

        // Get all the commercialProductInfoList where offeredUnit is null
        defaultCommercialProductInfoShouldNotBeFound("offeredUnit.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnitPrice equals to DEFAULT_OFFERED_UNIT_PRICE
        defaultCommercialProductInfoShouldBeFound("offeredUnitPrice.equals=" + DEFAULT_OFFERED_UNIT_PRICE);

        // Get all the commercialProductInfoList where offeredUnitPrice equals to UPDATED_OFFERED_UNIT_PRICE
        defaultCommercialProductInfoShouldNotBeFound("offeredUnitPrice.equals=" + UPDATED_OFFERED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnitPrice in DEFAULT_OFFERED_UNIT_PRICE or UPDATED_OFFERED_UNIT_PRICE
        defaultCommercialProductInfoShouldBeFound("offeredUnitPrice.in=" + DEFAULT_OFFERED_UNIT_PRICE + "," + UPDATED_OFFERED_UNIT_PRICE);

        // Get all the commercialProductInfoList where offeredUnitPrice equals to UPDATED_OFFERED_UNIT_PRICE
        defaultCommercialProductInfoShouldNotBeFound("offeredUnitPrice.in=" + UPDATED_OFFERED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredUnitPrice is not null
        defaultCommercialProductInfoShouldBeFound("offeredUnitPrice.specified=true");

        // Get all the commercialProductInfoList where offeredUnitPrice is null
        defaultCommercialProductInfoShouldNotBeFound("offeredUnitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredTotalPrice equals to DEFAULT_OFFERED_TOTAL_PRICE
        defaultCommercialProductInfoShouldBeFound("offeredTotalPrice.equals=" + DEFAULT_OFFERED_TOTAL_PRICE);

        // Get all the commercialProductInfoList where offeredTotalPrice equals to UPDATED_OFFERED_TOTAL_PRICE
        defaultCommercialProductInfoShouldNotBeFound("offeredTotalPrice.equals=" + UPDATED_OFFERED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredTotalPrice in DEFAULT_OFFERED_TOTAL_PRICE or UPDATED_OFFERED_TOTAL_PRICE
        defaultCommercialProductInfoShouldBeFound("offeredTotalPrice.in=" + DEFAULT_OFFERED_TOTAL_PRICE + "," + UPDATED_OFFERED_TOTAL_PRICE);

        // Get all the commercialProductInfoList where offeredTotalPrice equals to UPDATED_OFFERED_TOTAL_PRICE
        defaultCommercialProductInfoShouldNotBeFound("offeredTotalPrice.in=" + UPDATED_OFFERED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByOfferedTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where offeredTotalPrice is not null
        defaultCommercialProductInfoShouldBeFound("offeredTotalPrice.specified=true");

        // Get all the commercialProductInfoList where offeredTotalPrice is null
        defaultCommercialProductInfoShouldNotBeFound("offeredTotalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingQuantity equals to DEFAULT_BUYING_QUANTITY
        defaultCommercialProductInfoShouldBeFound("buyingQuantity.equals=" + DEFAULT_BUYING_QUANTITY);

        // Get all the commercialProductInfoList where buyingQuantity equals to UPDATED_BUYING_QUANTITY
        defaultCommercialProductInfoShouldNotBeFound("buyingQuantity.equals=" + UPDATED_BUYING_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingQuantity in DEFAULT_BUYING_QUANTITY or UPDATED_BUYING_QUANTITY
        defaultCommercialProductInfoShouldBeFound("buyingQuantity.in=" + DEFAULT_BUYING_QUANTITY + "," + UPDATED_BUYING_QUANTITY);

        // Get all the commercialProductInfoList where buyingQuantity equals to UPDATED_BUYING_QUANTITY
        defaultCommercialProductInfoShouldNotBeFound("buyingQuantity.in=" + UPDATED_BUYING_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingQuantity is not null
        defaultCommercialProductInfoShouldBeFound("buyingQuantity.specified=true");

        // Get all the commercialProductInfoList where buyingQuantity is null
        defaultCommercialProductInfoShouldNotBeFound("buyingQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnit equals to DEFAULT_BUYING_UNIT
        defaultCommercialProductInfoShouldBeFound("buyingUnit.equals=" + DEFAULT_BUYING_UNIT);

        // Get all the commercialProductInfoList where buyingUnit equals to UPDATED_BUYING_UNIT
        defaultCommercialProductInfoShouldNotBeFound("buyingUnit.equals=" + UPDATED_BUYING_UNIT);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnit in DEFAULT_BUYING_UNIT or UPDATED_BUYING_UNIT
        defaultCommercialProductInfoShouldBeFound("buyingUnit.in=" + DEFAULT_BUYING_UNIT + "," + UPDATED_BUYING_UNIT);

        // Get all the commercialProductInfoList where buyingUnit equals to UPDATED_BUYING_UNIT
        defaultCommercialProductInfoShouldNotBeFound("buyingUnit.in=" + UPDATED_BUYING_UNIT);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnit is not null
        defaultCommercialProductInfoShouldBeFound("buyingUnit.specified=true");

        // Get all the commercialProductInfoList where buyingUnit is null
        defaultCommercialProductInfoShouldNotBeFound("buyingUnit.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnitPrice equals to DEFAULT_BUYING_UNIT_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingUnitPrice.equals=" + DEFAULT_BUYING_UNIT_PRICE);

        // Get all the commercialProductInfoList where buyingUnitPrice equals to UPDATED_BUYING_UNIT_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingUnitPrice.equals=" + UPDATED_BUYING_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnitPrice in DEFAULT_BUYING_UNIT_PRICE or UPDATED_BUYING_UNIT_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingUnitPrice.in=" + DEFAULT_BUYING_UNIT_PRICE + "," + UPDATED_BUYING_UNIT_PRICE);

        // Get all the commercialProductInfoList where buyingUnitPrice equals to UPDATED_BUYING_UNIT_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingUnitPrice.in=" + UPDATED_BUYING_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingUnitPrice is not null
        defaultCommercialProductInfoShouldBeFound("buyingUnitPrice.specified=true");

        // Get all the commercialProductInfoList where buyingUnitPrice is null
        defaultCommercialProductInfoShouldNotBeFound("buyingUnitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingTotalPrice equals to DEFAULT_BUYING_TOTAL_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingTotalPrice.equals=" + DEFAULT_BUYING_TOTAL_PRICE);

        // Get all the commercialProductInfoList where buyingTotalPrice equals to UPDATED_BUYING_TOTAL_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingTotalPrice.equals=" + UPDATED_BUYING_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingTotalPrice in DEFAULT_BUYING_TOTAL_PRICE or UPDATED_BUYING_TOTAL_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingTotalPrice.in=" + DEFAULT_BUYING_TOTAL_PRICE + "," + UPDATED_BUYING_TOTAL_PRICE);

        // Get all the commercialProductInfoList where buyingTotalPrice equals to UPDATED_BUYING_TOTAL_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingTotalPrice.in=" + UPDATED_BUYING_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingTotalPrice is not null
        defaultCommercialProductInfoShouldBeFound("buyingTotalPrice.specified=true");

        // Get all the commercialProductInfoList where buyingTotalPrice is null
        defaultCommercialProductInfoShouldNotBeFound("buyingTotalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialProductInfoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialProductInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialProductInfoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialProductInfoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialProductInfoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialProductInfoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdBy is not null
        defaultCommercialProductInfoShouldBeFound("createdBy.specified=true");

        // Get all the commercialProductInfoList where createdBy is null
        defaultCommercialProductInfoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialProductInfoShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialProductInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialProductInfoShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialProductInfoShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialProductInfoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialProductInfoShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where createdOn is not null
        defaultCommercialProductInfoShouldBeFound("createdOn.specified=true");

        // Get all the commercialProductInfoList where createdOn is null
        defaultCommercialProductInfoShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialProductInfoShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialProductInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialProductInfoShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialProductInfoShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialProductInfoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialProductInfoShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedBy is not null
        defaultCommercialProductInfoShouldBeFound("updatedBy.specified=true");

        // Get all the commercialProductInfoList where updatedBy is null
        defaultCommercialProductInfoShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialProductInfoShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialProductInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialProductInfoShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialProductInfoShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialProductInfoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialProductInfoShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where updatedOn is not null
        defaultCommercialProductInfoShouldBeFound("updatedOn.specified=true");

        // Get all the commercialProductInfoList where updatedOn is null
        defaultCommercialProductInfoShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCommercialBudgetIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialBudget commercialBudget = CommercialBudgetExtendedResourceIntTest.createEntity(em);
        em.persist(commercialBudget);
        em.flush();
        commercialProductInfo.setCommercialBudget(commercialBudget);
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);
        Long commercialBudgetId = commercialBudget.getId();

        // Get all the commercialProductInfoList where commercialBudget equals to commercialBudgetId
        defaultCommercialProductInfoShouldBeFound("commercialBudgetId.equals=" + commercialBudgetId);

        // Get all the commercialProductInfoList where commercialBudget equals to commercialBudgetId + 1
        defaultCommercialProductInfoShouldNotBeFound("commercialBudgetId.equals=" + (commercialBudgetId + 1));
    }


    @Test
    @Transactional
    public void getAllCommercialProductInfosByProductCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategories = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategories);
        em.flush();
        commercialProductInfo.setProductCategories(productCategories);
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);
        Long productCategoriesId = productCategories.getId();

        // Get all the commercialProductInfoList where productCategories equals to productCategoriesId
        defaultCommercialProductInfoShouldBeFound("productCategoriesId.equals=" + productCategoriesId);

        // Get all the commercialProductInfoList where productCategories equals to productCategoriesId + 1
        defaultCommercialProductInfoShouldNotBeFound("productCategoriesId.equals=" + (productCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllCommercialProductInfosByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        Product products = ProductResourceIntTest.createEntity(em);
        em.persist(products);
        em.flush();
        commercialProductInfo.setProducts(products);
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);
        Long productsId = products.getId();

        // Get all the commercialProductInfoList where products equals to productsId
        defaultCommercialProductInfoShouldBeFound("productsId.equals=" + productsId);

        // Get all the commercialProductInfoList where products equals to productsId + 1
        defaultCommercialProductInfoShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialProductInfoShouldBeFound(String filter) throws Exception {
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProductInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].packagingDescription").value(hasItem(DEFAULT_PACKAGING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].othersDescription").value(hasItem(DEFAULT_OTHERS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingTotalPrice").value(hasItem(DEFAULT_BUYING_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialProductInfoShouldNotBeFound(String filter) throws Exception {
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialProductInfo() throws Exception {
        // Get the commercialProductInfo
        restCommercialProductInfoMockMvc.perform(get("/api/commercial-product-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialProductInfo() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        int databaseSizeBeforeUpdate = commercialProductInfoRepository.findAll().size();

        // Update the commercialProductInfo
        CommercialProductInfo updatedCommercialProductInfo = commercialProductInfoRepository.findById(commercialProductInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialProductInfo are not directly saved in db
        em.detach(updatedCommercialProductInfo);
        updatedCommercialProductInfo
            .serialNo(UPDATED_SERIAL_NO)
            .packagingDescription(UPDATED_PACKAGING_DESCRIPTION)
            .othersDescription(UPDATED_OTHERS_DESCRIPTION)
            .offeredQuantity(UPDATED_OFFERED_QUANTITY)
            .offeredUnit(UPDATED_OFFERED_UNIT)
            .offeredUnitPrice(UPDATED_OFFERED_UNIT_PRICE)
            .offeredTotalPrice(UPDATED_OFFERED_TOTAL_PRICE)
            .buyingQuantity(UPDATED_BUYING_QUANTITY)
            .buyingUnit(UPDATED_BUYING_UNIT)
            .buyingUnitPrice(UPDATED_BUYING_UNIT_PRICE)
            .buyingTotalPrice(UPDATED_BUYING_TOTAL_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(updatedCommercialProductInfo);

        restCommercialProductInfoMockMvc.perform(put("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialProductInfo in the database
        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeUpdate);
        CommercialProductInfo testCommercialProductInfo = commercialProductInfoList.get(commercialProductInfoList.size() - 1);
        assertThat(testCommercialProductInfo.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testCommercialProductInfo.getPackagingDescription()).isEqualTo(UPDATED_PACKAGING_DESCRIPTION);
        assertThat(testCommercialProductInfo.getOthersDescription()).isEqualTo(UPDATED_OTHERS_DESCRIPTION);
        assertThat(testCommercialProductInfo.getOfferedQuantity()).isEqualTo(UPDATED_OFFERED_QUANTITY);
        assertThat(testCommercialProductInfo.getOfferedUnit()).isEqualTo(UPDATED_OFFERED_UNIT);
        assertThat(testCommercialProductInfo.getOfferedUnitPrice()).isEqualTo(UPDATED_OFFERED_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getOfferedTotalPrice()).isEqualTo(UPDATED_OFFERED_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getBuyingQuantity()).isEqualTo(UPDATED_BUYING_QUANTITY);
        assertThat(testCommercialProductInfo.getBuyingUnit()).isEqualTo(UPDATED_BUYING_UNIT);
        assertThat(testCommercialProductInfo.getBuyingUnitPrice()).isEqualTo(UPDATED_BUYING_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getBuyingTotalPrice()).isEqualTo(UPDATED_BUYING_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialProductInfo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialProductInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialProductInfo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialProductInfo in Elasticsearch
        verify(mockCommercialProductInfoSearchRepository, times(1)).save(testCommercialProductInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialProductInfo() throws Exception {
        int databaseSizeBeforeUpdate = commercialProductInfoRepository.findAll().size();

        // Create the CommercialProductInfo
        CommercialProductInfoDTO commercialProductInfoDTO = commercialProductInfoMapper.toDto(commercialProductInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialProductInfoMockMvc.perform(put("/api/commercial-product-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialProductInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialProductInfo in the database
        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialProductInfo in Elasticsearch
        verify(mockCommercialProductInfoSearchRepository, times(0)).save(commercialProductInfo);
    }

    @Test
    @Transactional
    public void deleteCommercialProductInfo() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        int databaseSizeBeforeDelete = commercialProductInfoRepository.findAll().size();

        // Delete the commercialProductInfo
        restCommercialProductInfoMockMvc.perform(delete("/api/commercial-product-infos/{id}", commercialProductInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialProductInfo> commercialProductInfoList = commercialProductInfoRepository.findAll();
        assertThat(commercialProductInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialProductInfo in Elasticsearch
        verify(mockCommercialProductInfoSearchRepository, times(1)).deleteById(commercialProductInfo.getId());
    }

    @Test
    @Transactional
    public void searchCommercialProductInfo() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);
        when(mockCommercialProductInfoSearchRepository.search(queryStringQuery("id:" + commercialProductInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialProductInfo), PageRequest.of(0, 1), 1));
        // Search the commercialProductInfo
        restCommercialProductInfoMockMvc.perform(get("/api/_search/commercial-product-infos?query=id:" + commercialProductInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialProductInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].packagingDescription").value(hasItem(DEFAULT_PACKAGING_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].othersDescription").value(hasItem(DEFAULT_OTHERS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingTotalPrice").value(hasItem(DEFAULT_BUYING_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialProductInfo.class);
        CommercialProductInfo commercialProductInfo1 = new CommercialProductInfo();
        commercialProductInfo1.setId(1L);
        CommercialProductInfo commercialProductInfo2 = new CommercialProductInfo();
        commercialProductInfo2.setId(commercialProductInfo1.getId());
        assertThat(commercialProductInfo1).isEqualTo(commercialProductInfo2);
        commercialProductInfo2.setId(2L);
        assertThat(commercialProductInfo1).isNotEqualTo(commercialProductInfo2);
        commercialProductInfo1.setId(null);
        assertThat(commercialProductInfo1).isNotEqualTo(commercialProductInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialProductInfoDTO.class);
        CommercialProductInfoDTO commercialProductInfoDTO1 = new CommercialProductInfoDTO();
        commercialProductInfoDTO1.setId(1L);
        CommercialProductInfoDTO commercialProductInfoDTO2 = new CommercialProductInfoDTO();
        assertThat(commercialProductInfoDTO1).isNotEqualTo(commercialProductInfoDTO2);
        commercialProductInfoDTO2.setId(commercialProductInfoDTO1.getId());
        assertThat(commercialProductInfoDTO1).isEqualTo(commercialProductInfoDTO2);
        commercialProductInfoDTO2.setId(2L);
        assertThat(commercialProductInfoDTO1).isNotEqualTo(commercialProductInfoDTO2);
        commercialProductInfoDTO1.setId(null);
        assertThat(commercialProductInfoDTO1).isNotEqualTo(commercialProductInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialProductInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialProductInfoMapper.fromId(null)).isNull();
    }
}
