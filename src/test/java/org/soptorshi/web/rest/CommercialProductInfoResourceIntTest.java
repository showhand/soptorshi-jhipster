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
import org.soptorshi.domain.enumeration.PackColor;
import org.soptorshi.domain.enumeration.ProductSpecification;
import org.soptorshi.domain.enumeration.SurfaceType;
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
public class CommercialProductInfoResourceIntTest {

    private static final Integer DEFAULT_TASK_NO = 1;
    private static final Integer UPDATED_TASK_NO = 2;

    private static final ProductSpecification DEFAULT_PRODUCT_SPECIFICATION = ProductSpecification.FILLET;
    private static final ProductSpecification UPDATED_PRODUCT_SPECIFICATION = ProductSpecification.STEAK;

    private static final String DEFAULT_SP_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SP_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_OFFERED_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_OFFERED_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_OFFERED_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_OFFERED_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OFFERED_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OFFERED_TOTAL_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_SP_GLAZING = 1;
    private static final Integer UPDATED_SP_GLAZING = 2;

    private static final SurfaceType DEFAULT_SP_SURFACE_TYPE = SurfaceType.TRIMMED;
    private static final SurfaceType UPDATED_SP_SURFACE_TYPE = SurfaceType.UNTRIMMED;

    private static final String DEFAULT_SP_OTHERS_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SP_OTHERS_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SP_STICKER = "AAAAAAAAAA";
    private static final String UPDATED_SP_STICKER = "BBBBBBBBBB";

    private static final String DEFAULT_SP_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SP_LABEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SP_QTY_IN_PACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_SP_QTY_IN_PACK = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SP_QTY_IN_MC = new BigDecimal(1);
    private static final BigDecimal UPDATED_SP_QTY_IN_MC = new BigDecimal(2);

    private static final PackColor DEFAULT_IP_COLOR = PackColor.PLAIN;
    private static final PackColor UPDATED_IP_COLOR = PackColor.PRINT;

    private static final String DEFAULT_IP_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_IP_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_IP_STICKER = "AAAAAAAAAA";
    private static final String UPDATED_IP_STICKER = "BBBBBBBBBB";

    private static final String DEFAULT_IP_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_IP_LABEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_IP_QTY_IN_MC = new BigDecimal(1);
    private static final BigDecimal UPDATED_IP_QTY_IN_MC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IP_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_IP_COST = new BigDecimal(2);

    private static final PackColor DEFAULT_MC_COLOR = PackColor.PLAIN;
    private static final PackColor UPDATED_MC_COLOR = PackColor.PRINT;

    private static final String DEFAULT_MC_PLY = "AAAAAAAAAA";
    private static final String UPDATED_MC_PLY = "BBBBBBBBBB";

    private static final String DEFAULT_MC_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_MC_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_MC_STICKER = "AAAAAAAAAA";
    private static final String UPDATED_MC_STICKER = "BBBBBBBBBB";

    private static final String DEFAULT_MC_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_MC_LABEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MC_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_MC_COST = new BigDecimal(2);

    private static final String DEFAULT_CYL_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_CYL_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_CYL_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_CYL_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CYL_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CYL_QTY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CYL_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_CYL_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BUYING_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_QUANTITY = new BigDecimal(2);

    private static final UnitOfMeasurements DEFAULT_BUYING_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_BUYING_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_BUYING_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BUYING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BUYING_PRICE = new BigDecimal(2);

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
            .taskNo(DEFAULT_TASK_NO)
            .productSpecification(DEFAULT_PRODUCT_SPECIFICATION)
            .spSize(DEFAULT_SP_SIZE)
            .offeredQuantity(DEFAULT_OFFERED_QUANTITY)
            .offeredUnit(DEFAULT_OFFERED_UNIT)
            .offeredUnitPrice(DEFAULT_OFFERED_UNIT_PRICE)
            .offeredTotalPrice(DEFAULT_OFFERED_TOTAL_PRICE)
            .spGlazing(DEFAULT_SP_GLAZING)
            .spSurfaceType(DEFAULT_SP_SURFACE_TYPE)
            .spOthersDescription(DEFAULT_SP_OTHERS_DESCRIPTION)
            .spSticker(DEFAULT_SP_STICKER)
            .spLabel(DEFAULT_SP_LABEL)
            .spQtyInPack(DEFAULT_SP_QTY_IN_PACK)
            .spQtyInMc(DEFAULT_SP_QTY_IN_MC)
            .ipColor(DEFAULT_IP_COLOR)
            .ipSize(DEFAULT_IP_SIZE)
            .ipSticker(DEFAULT_IP_STICKER)
            .ipLabel(DEFAULT_IP_LABEL)
            .ipQtyInMc(DEFAULT_IP_QTY_IN_MC)
            .ipCost(DEFAULT_IP_COST)
            .mcColor(DEFAULT_MC_COLOR)
            .mcPly(DEFAULT_MC_PLY)
            .mcSize(DEFAULT_MC_SIZE)
            .mcSticker(DEFAULT_MC_STICKER)
            .mcLabel(DEFAULT_MC_LABEL)
            .mcCost(DEFAULT_MC_COST)
            .cylColor(DEFAULT_CYL_COLOR)
            .cylSize(DEFAULT_CYL_SIZE)
            .cylQty(DEFAULT_CYL_QTY)
            .cylCost(DEFAULT_CYL_COST)
            .buyingQuantity(DEFAULT_BUYING_QUANTITY)
            .buyingUnit(DEFAULT_BUYING_UNIT)
            .buyingUnitPrice(DEFAULT_BUYING_UNIT_PRICE)
            .buyingPrice(DEFAULT_BUYING_PRICE)
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
        assertThat(testCommercialProductInfo.getTaskNo()).isEqualTo(DEFAULT_TASK_NO);
        assertThat(testCommercialProductInfo.getProductSpecification()).isEqualTo(DEFAULT_PRODUCT_SPECIFICATION);
        assertThat(testCommercialProductInfo.getSpSize()).isEqualTo(DEFAULT_SP_SIZE);
        assertThat(testCommercialProductInfo.getOfferedQuantity()).isEqualTo(DEFAULT_OFFERED_QUANTITY);
        assertThat(testCommercialProductInfo.getOfferedUnit()).isEqualTo(DEFAULT_OFFERED_UNIT);
        assertThat(testCommercialProductInfo.getOfferedUnitPrice()).isEqualTo(DEFAULT_OFFERED_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getOfferedTotalPrice()).isEqualTo(DEFAULT_OFFERED_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getSpGlazing()).isEqualTo(DEFAULT_SP_GLAZING);
        assertThat(testCommercialProductInfo.getSpSurfaceType()).isEqualTo(DEFAULT_SP_SURFACE_TYPE);
        assertThat(testCommercialProductInfo.getSpOthersDescription()).isEqualTo(DEFAULT_SP_OTHERS_DESCRIPTION);
        assertThat(testCommercialProductInfo.getSpSticker()).isEqualTo(DEFAULT_SP_STICKER);
        assertThat(testCommercialProductInfo.getSpLabel()).isEqualTo(DEFAULT_SP_LABEL);
        assertThat(testCommercialProductInfo.getSpQtyInPack()).isEqualTo(DEFAULT_SP_QTY_IN_PACK);
        assertThat(testCommercialProductInfo.getSpQtyInMc()).isEqualTo(DEFAULT_SP_QTY_IN_MC);
        assertThat(testCommercialProductInfo.getIpColor()).isEqualTo(DEFAULT_IP_COLOR);
        assertThat(testCommercialProductInfo.getIpSize()).isEqualTo(DEFAULT_IP_SIZE);
        assertThat(testCommercialProductInfo.getIpSticker()).isEqualTo(DEFAULT_IP_STICKER);
        assertThat(testCommercialProductInfo.getIpLabel()).isEqualTo(DEFAULT_IP_LABEL);
        assertThat(testCommercialProductInfo.getIpQtyInMc()).isEqualTo(DEFAULT_IP_QTY_IN_MC);
        assertThat(testCommercialProductInfo.getIpCost()).isEqualTo(DEFAULT_IP_COST);
        assertThat(testCommercialProductInfo.getMcColor()).isEqualTo(DEFAULT_MC_COLOR);
        assertThat(testCommercialProductInfo.getMcPly()).isEqualTo(DEFAULT_MC_PLY);
        assertThat(testCommercialProductInfo.getMcSize()).isEqualTo(DEFAULT_MC_SIZE);
        assertThat(testCommercialProductInfo.getMcSticker()).isEqualTo(DEFAULT_MC_STICKER);
        assertThat(testCommercialProductInfo.getMcLabel()).isEqualTo(DEFAULT_MC_LABEL);
        assertThat(testCommercialProductInfo.getMcCost()).isEqualTo(DEFAULT_MC_COST);
        assertThat(testCommercialProductInfo.getCylColor()).isEqualTo(DEFAULT_CYL_COLOR);
        assertThat(testCommercialProductInfo.getCylSize()).isEqualTo(DEFAULT_CYL_SIZE);
        assertThat(testCommercialProductInfo.getCylQty()).isEqualTo(DEFAULT_CYL_QTY);
        assertThat(testCommercialProductInfo.getCylCost()).isEqualTo(DEFAULT_CYL_COST);
        assertThat(testCommercialProductInfo.getBuyingQuantity()).isEqualTo(DEFAULT_BUYING_QUANTITY);
        assertThat(testCommercialProductInfo.getBuyingUnit()).isEqualTo(DEFAULT_BUYING_UNIT);
        assertThat(testCommercialProductInfo.getBuyingUnitPrice()).isEqualTo(DEFAULT_BUYING_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getBuyingPrice()).isEqualTo(DEFAULT_BUYING_PRICE);
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
    public void checkTaskNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setTaskNo(null);

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
    public void checkProductSpecificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setProductSpecification(null);

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
    public void checkBuyingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialProductInfoRepository.findAll().size();
        // set the field null
        commercialProductInfo.setBuyingPrice(null);

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
            .andExpect(jsonPath("$.[*].taskNo").value(hasItem(DEFAULT_TASK_NO)))
            .andExpect(jsonPath("$.[*].productSpecification").value(hasItem(DEFAULT_PRODUCT_SPECIFICATION.toString())))
            .andExpect(jsonPath("$.[*].spSize").value(hasItem(DEFAULT_SP_SIZE.toString())))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].spGlazing").value(hasItem(DEFAULT_SP_GLAZING)))
            .andExpect(jsonPath("$.[*].spSurfaceType").value(hasItem(DEFAULT_SP_SURFACE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].spOthersDescription").value(hasItem(DEFAULT_SP_OTHERS_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].spSticker").value(hasItem(DEFAULT_SP_STICKER.toString())))
            .andExpect(jsonPath("$.[*].spLabel").value(hasItem(DEFAULT_SP_LABEL.toString())))
            .andExpect(jsonPath("$.[*].spQtyInPack").value(hasItem(DEFAULT_SP_QTY_IN_PACK.intValue())))
            .andExpect(jsonPath("$.[*].spQtyInMc").value(hasItem(DEFAULT_SP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipColor").value(hasItem(DEFAULT_IP_COLOR.toString())))
            .andExpect(jsonPath("$.[*].ipSize").value(hasItem(DEFAULT_IP_SIZE.toString())))
            .andExpect(jsonPath("$.[*].ipSticker").value(hasItem(DEFAULT_IP_STICKER.toString())))
            .andExpect(jsonPath("$.[*].ipLabel").value(hasItem(DEFAULT_IP_LABEL.toString())))
            .andExpect(jsonPath("$.[*].ipQtyInMc").value(hasItem(DEFAULT_IP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipCost").value(hasItem(DEFAULT_IP_COST.intValue())))
            .andExpect(jsonPath("$.[*].mcColor").value(hasItem(DEFAULT_MC_COLOR.toString())))
            .andExpect(jsonPath("$.[*].mcPly").value(hasItem(DEFAULT_MC_PLY.toString())))
            .andExpect(jsonPath("$.[*].mcSize").value(hasItem(DEFAULT_MC_SIZE.toString())))
            .andExpect(jsonPath("$.[*].mcSticker").value(hasItem(DEFAULT_MC_STICKER.toString())))
            .andExpect(jsonPath("$.[*].mcLabel").value(hasItem(DEFAULT_MC_LABEL.toString())))
            .andExpect(jsonPath("$.[*].mcCost").value(hasItem(DEFAULT_MC_COST.intValue())))
            .andExpect(jsonPath("$.[*].cylColor").value(hasItem(DEFAULT_CYL_COLOR.toString())))
            .andExpect(jsonPath("$.[*].cylSize").value(hasItem(DEFAULT_CYL_SIZE.toString())))
            .andExpect(jsonPath("$.[*].cylQty").value(hasItem(DEFAULT_CYL_QTY.intValue())))
            .andExpect(jsonPath("$.[*].cylCost").value(hasItem(DEFAULT_CYL_COST.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingPrice").value(hasItem(DEFAULT_BUYING_PRICE.intValue())))
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
            .andExpect(jsonPath("$.taskNo").value(DEFAULT_TASK_NO))
            .andExpect(jsonPath("$.productSpecification").value(DEFAULT_PRODUCT_SPECIFICATION.toString()))
            .andExpect(jsonPath("$.spSize").value(DEFAULT_SP_SIZE.toString()))
            .andExpect(jsonPath("$.offeredQuantity").value(DEFAULT_OFFERED_QUANTITY.intValue()))
            .andExpect(jsonPath("$.offeredUnit").value(DEFAULT_OFFERED_UNIT.toString()))
            .andExpect(jsonPath("$.offeredUnitPrice").value(DEFAULT_OFFERED_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.offeredTotalPrice").value(DEFAULT_OFFERED_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.spGlazing").value(DEFAULT_SP_GLAZING))
            .andExpect(jsonPath("$.spSurfaceType").value(DEFAULT_SP_SURFACE_TYPE.toString()))
            .andExpect(jsonPath("$.spOthersDescription").value(DEFAULT_SP_OTHERS_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.spSticker").value(DEFAULT_SP_STICKER.toString()))
            .andExpect(jsonPath("$.spLabel").value(DEFAULT_SP_LABEL.toString()))
            .andExpect(jsonPath("$.spQtyInPack").value(DEFAULT_SP_QTY_IN_PACK.intValue()))
            .andExpect(jsonPath("$.spQtyInMc").value(DEFAULT_SP_QTY_IN_MC.intValue()))
            .andExpect(jsonPath("$.ipColor").value(DEFAULT_IP_COLOR.toString()))
            .andExpect(jsonPath("$.ipSize").value(DEFAULT_IP_SIZE.toString()))
            .andExpect(jsonPath("$.ipSticker").value(DEFAULT_IP_STICKER.toString()))
            .andExpect(jsonPath("$.ipLabel").value(DEFAULT_IP_LABEL.toString()))
            .andExpect(jsonPath("$.ipQtyInMc").value(DEFAULT_IP_QTY_IN_MC.intValue()))
            .andExpect(jsonPath("$.ipCost").value(DEFAULT_IP_COST.intValue()))
            .andExpect(jsonPath("$.mcColor").value(DEFAULT_MC_COLOR.toString()))
            .andExpect(jsonPath("$.mcPly").value(DEFAULT_MC_PLY.toString()))
            .andExpect(jsonPath("$.mcSize").value(DEFAULT_MC_SIZE.toString()))
            .andExpect(jsonPath("$.mcSticker").value(DEFAULT_MC_STICKER.toString()))
            .andExpect(jsonPath("$.mcLabel").value(DEFAULT_MC_LABEL.toString()))
            .andExpect(jsonPath("$.mcCost").value(DEFAULT_MC_COST.intValue()))
            .andExpect(jsonPath("$.cylColor").value(DEFAULT_CYL_COLOR.toString()))
            .andExpect(jsonPath("$.cylSize").value(DEFAULT_CYL_SIZE.toString()))
            .andExpect(jsonPath("$.cylQty").value(DEFAULT_CYL_QTY.intValue()))
            .andExpect(jsonPath("$.cylCost").value(DEFAULT_CYL_COST.intValue()))
            .andExpect(jsonPath("$.buyingQuantity").value(DEFAULT_BUYING_QUANTITY.intValue()))
            .andExpect(jsonPath("$.buyingUnit").value(DEFAULT_BUYING_UNIT.toString()))
            .andExpect(jsonPath("$.buyingUnitPrice").value(DEFAULT_BUYING_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.buyingPrice").value(DEFAULT_BUYING_PRICE.intValue()))
            .andExpect(jsonPath("$.buyingTotalPrice").value(DEFAULT_BUYING_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByTaskNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where taskNo equals to DEFAULT_TASK_NO
        defaultCommercialProductInfoShouldBeFound("taskNo.equals=" + DEFAULT_TASK_NO);

        // Get all the commercialProductInfoList where taskNo equals to UPDATED_TASK_NO
        defaultCommercialProductInfoShouldNotBeFound("taskNo.equals=" + UPDATED_TASK_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByTaskNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where taskNo in DEFAULT_TASK_NO or UPDATED_TASK_NO
        defaultCommercialProductInfoShouldBeFound("taskNo.in=" + DEFAULT_TASK_NO + "," + UPDATED_TASK_NO);

        // Get all the commercialProductInfoList where taskNo equals to UPDATED_TASK_NO
        defaultCommercialProductInfoShouldNotBeFound("taskNo.in=" + UPDATED_TASK_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByTaskNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where taskNo is not null
        defaultCommercialProductInfoShouldBeFound("taskNo.specified=true");

        // Get all the commercialProductInfoList where taskNo is null
        defaultCommercialProductInfoShouldNotBeFound("taskNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByTaskNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where taskNo greater than or equals to DEFAULT_TASK_NO
        defaultCommercialProductInfoShouldBeFound("taskNo.greaterOrEqualThan=" + DEFAULT_TASK_NO);

        // Get all the commercialProductInfoList where taskNo greater than or equals to UPDATED_TASK_NO
        defaultCommercialProductInfoShouldNotBeFound("taskNo.greaterOrEqualThan=" + UPDATED_TASK_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByTaskNoIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where taskNo less than or equals to DEFAULT_TASK_NO
        defaultCommercialProductInfoShouldNotBeFound("taskNo.lessThan=" + DEFAULT_TASK_NO);

        // Get all the commercialProductInfoList where taskNo less than or equals to UPDATED_TASK_NO
        defaultCommercialProductInfoShouldBeFound("taskNo.lessThan=" + UPDATED_TASK_NO);
    }


    @Test
    @Transactional
    public void getAllCommercialProductInfosByProductSpecificationIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where productSpecification equals to DEFAULT_PRODUCT_SPECIFICATION
        defaultCommercialProductInfoShouldBeFound("productSpecification.equals=" + DEFAULT_PRODUCT_SPECIFICATION);

        // Get all the commercialProductInfoList where productSpecification equals to UPDATED_PRODUCT_SPECIFICATION
        defaultCommercialProductInfoShouldNotBeFound("productSpecification.equals=" + UPDATED_PRODUCT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByProductSpecificationIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where productSpecification in DEFAULT_PRODUCT_SPECIFICATION or UPDATED_PRODUCT_SPECIFICATION
        defaultCommercialProductInfoShouldBeFound("productSpecification.in=" + DEFAULT_PRODUCT_SPECIFICATION + "," + UPDATED_PRODUCT_SPECIFICATION);

        // Get all the commercialProductInfoList where productSpecification equals to UPDATED_PRODUCT_SPECIFICATION
        defaultCommercialProductInfoShouldNotBeFound("productSpecification.in=" + UPDATED_PRODUCT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByProductSpecificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where productSpecification is not null
        defaultCommercialProductInfoShouldBeFound("productSpecification.specified=true");

        // Get all the commercialProductInfoList where productSpecification is null
        defaultCommercialProductInfoShouldNotBeFound("productSpecification.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSize equals to DEFAULT_SP_SIZE
        defaultCommercialProductInfoShouldBeFound("spSize.equals=" + DEFAULT_SP_SIZE);

        // Get all the commercialProductInfoList where spSize equals to UPDATED_SP_SIZE
        defaultCommercialProductInfoShouldNotBeFound("spSize.equals=" + UPDATED_SP_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSizeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSize in DEFAULT_SP_SIZE or UPDATED_SP_SIZE
        defaultCommercialProductInfoShouldBeFound("spSize.in=" + DEFAULT_SP_SIZE + "," + UPDATED_SP_SIZE);

        // Get all the commercialProductInfoList where spSize equals to UPDATED_SP_SIZE
        defaultCommercialProductInfoShouldNotBeFound("spSize.in=" + UPDATED_SP_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSize is not null
        defaultCommercialProductInfoShouldBeFound("spSize.specified=true");

        // Get all the commercialProductInfoList where spSize is null
        defaultCommercialProductInfoShouldNotBeFound("spSize.specified=false");
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
    public void getAllCommercialProductInfosBySpGlazingIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spGlazing equals to DEFAULT_SP_GLAZING
        defaultCommercialProductInfoShouldBeFound("spGlazing.equals=" + DEFAULT_SP_GLAZING);

        // Get all the commercialProductInfoList where spGlazing equals to UPDATED_SP_GLAZING
        defaultCommercialProductInfoShouldNotBeFound("spGlazing.equals=" + UPDATED_SP_GLAZING);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpGlazingIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spGlazing in DEFAULT_SP_GLAZING or UPDATED_SP_GLAZING
        defaultCommercialProductInfoShouldBeFound("spGlazing.in=" + DEFAULT_SP_GLAZING + "," + UPDATED_SP_GLAZING);

        // Get all the commercialProductInfoList where spGlazing equals to UPDATED_SP_GLAZING
        defaultCommercialProductInfoShouldNotBeFound("spGlazing.in=" + UPDATED_SP_GLAZING);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpGlazingIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spGlazing is not null
        defaultCommercialProductInfoShouldBeFound("spGlazing.specified=true");

        // Get all the commercialProductInfoList where spGlazing is null
        defaultCommercialProductInfoShouldNotBeFound("spGlazing.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpGlazingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spGlazing greater than or equals to DEFAULT_SP_GLAZING
        defaultCommercialProductInfoShouldBeFound("spGlazing.greaterOrEqualThan=" + DEFAULT_SP_GLAZING);

        // Get all the commercialProductInfoList where spGlazing greater than or equals to UPDATED_SP_GLAZING
        defaultCommercialProductInfoShouldNotBeFound("spGlazing.greaterOrEqualThan=" + UPDATED_SP_GLAZING);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpGlazingIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spGlazing less than or equals to DEFAULT_SP_GLAZING
        defaultCommercialProductInfoShouldNotBeFound("spGlazing.lessThan=" + DEFAULT_SP_GLAZING);

        // Get all the commercialProductInfoList where spGlazing less than or equals to UPDATED_SP_GLAZING
        defaultCommercialProductInfoShouldBeFound("spGlazing.lessThan=" + UPDATED_SP_GLAZING);
    }


    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSurfaceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSurfaceType equals to DEFAULT_SP_SURFACE_TYPE
        defaultCommercialProductInfoShouldBeFound("spSurfaceType.equals=" + DEFAULT_SP_SURFACE_TYPE);

        // Get all the commercialProductInfoList where spSurfaceType equals to UPDATED_SP_SURFACE_TYPE
        defaultCommercialProductInfoShouldNotBeFound("spSurfaceType.equals=" + UPDATED_SP_SURFACE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSurfaceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSurfaceType in DEFAULT_SP_SURFACE_TYPE or UPDATED_SP_SURFACE_TYPE
        defaultCommercialProductInfoShouldBeFound("spSurfaceType.in=" + DEFAULT_SP_SURFACE_TYPE + "," + UPDATED_SP_SURFACE_TYPE);

        // Get all the commercialProductInfoList where spSurfaceType equals to UPDATED_SP_SURFACE_TYPE
        defaultCommercialProductInfoShouldNotBeFound("spSurfaceType.in=" + UPDATED_SP_SURFACE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpSurfaceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSurfaceType is not null
        defaultCommercialProductInfoShouldBeFound("spSurfaceType.specified=true");

        // Get all the commercialProductInfoList where spSurfaceType is null
        defaultCommercialProductInfoShouldNotBeFound("spSurfaceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpOthersDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spOthersDescription equals to DEFAULT_SP_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("spOthersDescription.equals=" + DEFAULT_SP_OTHERS_DESCRIPTION);

        // Get all the commercialProductInfoList where spOthersDescription equals to UPDATED_SP_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("spOthersDescription.equals=" + UPDATED_SP_OTHERS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpOthersDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spOthersDescription in DEFAULT_SP_OTHERS_DESCRIPTION or UPDATED_SP_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldBeFound("spOthersDescription.in=" + DEFAULT_SP_OTHERS_DESCRIPTION + "," + UPDATED_SP_OTHERS_DESCRIPTION);

        // Get all the commercialProductInfoList where spOthersDescription equals to UPDATED_SP_OTHERS_DESCRIPTION
        defaultCommercialProductInfoShouldNotBeFound("spOthersDescription.in=" + UPDATED_SP_OTHERS_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpOthersDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spOthersDescription is not null
        defaultCommercialProductInfoShouldBeFound("spOthersDescription.specified=true");

        // Get all the commercialProductInfoList where spOthersDescription is null
        defaultCommercialProductInfoShouldNotBeFound("spOthersDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpStickerIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSticker equals to DEFAULT_SP_STICKER
        defaultCommercialProductInfoShouldBeFound("spSticker.equals=" + DEFAULT_SP_STICKER);

        // Get all the commercialProductInfoList where spSticker equals to UPDATED_SP_STICKER
        defaultCommercialProductInfoShouldNotBeFound("spSticker.equals=" + UPDATED_SP_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpStickerIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSticker in DEFAULT_SP_STICKER or UPDATED_SP_STICKER
        defaultCommercialProductInfoShouldBeFound("spSticker.in=" + DEFAULT_SP_STICKER + "," + UPDATED_SP_STICKER);

        // Get all the commercialProductInfoList where spSticker equals to UPDATED_SP_STICKER
        defaultCommercialProductInfoShouldNotBeFound("spSticker.in=" + UPDATED_SP_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpStickerIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spSticker is not null
        defaultCommercialProductInfoShouldBeFound("spSticker.specified=true");

        // Get all the commercialProductInfoList where spSticker is null
        defaultCommercialProductInfoShouldNotBeFound("spSticker.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spLabel equals to DEFAULT_SP_LABEL
        defaultCommercialProductInfoShouldBeFound("spLabel.equals=" + DEFAULT_SP_LABEL);

        // Get all the commercialProductInfoList where spLabel equals to UPDATED_SP_LABEL
        defaultCommercialProductInfoShouldNotBeFound("spLabel.equals=" + UPDATED_SP_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpLabelIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spLabel in DEFAULT_SP_LABEL or UPDATED_SP_LABEL
        defaultCommercialProductInfoShouldBeFound("spLabel.in=" + DEFAULT_SP_LABEL + "," + UPDATED_SP_LABEL);

        // Get all the commercialProductInfoList where spLabel equals to UPDATED_SP_LABEL
        defaultCommercialProductInfoShouldNotBeFound("spLabel.in=" + UPDATED_SP_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spLabel is not null
        defaultCommercialProductInfoShouldBeFound("spLabel.specified=true");

        // Get all the commercialProductInfoList where spLabel is null
        defaultCommercialProductInfoShouldNotBeFound("spLabel.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInPackIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInPack equals to DEFAULT_SP_QTY_IN_PACK
        defaultCommercialProductInfoShouldBeFound("spQtyInPack.equals=" + DEFAULT_SP_QTY_IN_PACK);

        // Get all the commercialProductInfoList where spQtyInPack equals to UPDATED_SP_QTY_IN_PACK
        defaultCommercialProductInfoShouldNotBeFound("spQtyInPack.equals=" + UPDATED_SP_QTY_IN_PACK);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInPackIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInPack in DEFAULT_SP_QTY_IN_PACK or UPDATED_SP_QTY_IN_PACK
        defaultCommercialProductInfoShouldBeFound("spQtyInPack.in=" + DEFAULT_SP_QTY_IN_PACK + "," + UPDATED_SP_QTY_IN_PACK);

        // Get all the commercialProductInfoList where spQtyInPack equals to UPDATED_SP_QTY_IN_PACK
        defaultCommercialProductInfoShouldNotBeFound("spQtyInPack.in=" + UPDATED_SP_QTY_IN_PACK);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInPackIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInPack is not null
        defaultCommercialProductInfoShouldBeFound("spQtyInPack.specified=true");

        // Get all the commercialProductInfoList where spQtyInPack is null
        defaultCommercialProductInfoShouldNotBeFound("spQtyInPack.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInMcIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInMc equals to DEFAULT_SP_QTY_IN_MC
        defaultCommercialProductInfoShouldBeFound("spQtyInMc.equals=" + DEFAULT_SP_QTY_IN_MC);

        // Get all the commercialProductInfoList where spQtyInMc equals to UPDATED_SP_QTY_IN_MC
        defaultCommercialProductInfoShouldNotBeFound("spQtyInMc.equals=" + UPDATED_SP_QTY_IN_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInMcIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInMc in DEFAULT_SP_QTY_IN_MC or UPDATED_SP_QTY_IN_MC
        defaultCommercialProductInfoShouldBeFound("spQtyInMc.in=" + DEFAULT_SP_QTY_IN_MC + "," + UPDATED_SP_QTY_IN_MC);

        // Get all the commercialProductInfoList where spQtyInMc equals to UPDATED_SP_QTY_IN_MC
        defaultCommercialProductInfoShouldNotBeFound("spQtyInMc.in=" + UPDATED_SP_QTY_IN_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosBySpQtyInMcIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where spQtyInMc is not null
        defaultCommercialProductInfoShouldBeFound("spQtyInMc.specified=true");

        // Get all the commercialProductInfoList where spQtyInMc is null
        defaultCommercialProductInfoShouldNotBeFound("spQtyInMc.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpColorIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipColor equals to DEFAULT_IP_COLOR
        defaultCommercialProductInfoShouldBeFound("ipColor.equals=" + DEFAULT_IP_COLOR);

        // Get all the commercialProductInfoList where ipColor equals to UPDATED_IP_COLOR
        defaultCommercialProductInfoShouldNotBeFound("ipColor.equals=" + UPDATED_IP_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpColorIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipColor in DEFAULT_IP_COLOR or UPDATED_IP_COLOR
        defaultCommercialProductInfoShouldBeFound("ipColor.in=" + DEFAULT_IP_COLOR + "," + UPDATED_IP_COLOR);

        // Get all the commercialProductInfoList where ipColor equals to UPDATED_IP_COLOR
        defaultCommercialProductInfoShouldNotBeFound("ipColor.in=" + UPDATED_IP_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipColor is not null
        defaultCommercialProductInfoShouldBeFound("ipColor.specified=true");

        // Get all the commercialProductInfoList where ipColor is null
        defaultCommercialProductInfoShouldNotBeFound("ipColor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSize equals to DEFAULT_IP_SIZE
        defaultCommercialProductInfoShouldBeFound("ipSize.equals=" + DEFAULT_IP_SIZE);

        // Get all the commercialProductInfoList where ipSize equals to UPDATED_IP_SIZE
        defaultCommercialProductInfoShouldNotBeFound("ipSize.equals=" + UPDATED_IP_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpSizeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSize in DEFAULT_IP_SIZE or UPDATED_IP_SIZE
        defaultCommercialProductInfoShouldBeFound("ipSize.in=" + DEFAULT_IP_SIZE + "," + UPDATED_IP_SIZE);

        // Get all the commercialProductInfoList where ipSize equals to UPDATED_IP_SIZE
        defaultCommercialProductInfoShouldNotBeFound("ipSize.in=" + UPDATED_IP_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSize is not null
        defaultCommercialProductInfoShouldBeFound("ipSize.specified=true");

        // Get all the commercialProductInfoList where ipSize is null
        defaultCommercialProductInfoShouldNotBeFound("ipSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpStickerIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSticker equals to DEFAULT_IP_STICKER
        defaultCommercialProductInfoShouldBeFound("ipSticker.equals=" + DEFAULT_IP_STICKER);

        // Get all the commercialProductInfoList where ipSticker equals to UPDATED_IP_STICKER
        defaultCommercialProductInfoShouldNotBeFound("ipSticker.equals=" + UPDATED_IP_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpStickerIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSticker in DEFAULT_IP_STICKER or UPDATED_IP_STICKER
        defaultCommercialProductInfoShouldBeFound("ipSticker.in=" + DEFAULT_IP_STICKER + "," + UPDATED_IP_STICKER);

        // Get all the commercialProductInfoList where ipSticker equals to UPDATED_IP_STICKER
        defaultCommercialProductInfoShouldNotBeFound("ipSticker.in=" + UPDATED_IP_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpStickerIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipSticker is not null
        defaultCommercialProductInfoShouldBeFound("ipSticker.specified=true");

        // Get all the commercialProductInfoList where ipSticker is null
        defaultCommercialProductInfoShouldNotBeFound("ipSticker.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipLabel equals to DEFAULT_IP_LABEL
        defaultCommercialProductInfoShouldBeFound("ipLabel.equals=" + DEFAULT_IP_LABEL);

        // Get all the commercialProductInfoList where ipLabel equals to UPDATED_IP_LABEL
        defaultCommercialProductInfoShouldNotBeFound("ipLabel.equals=" + UPDATED_IP_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpLabelIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipLabel in DEFAULT_IP_LABEL or UPDATED_IP_LABEL
        defaultCommercialProductInfoShouldBeFound("ipLabel.in=" + DEFAULT_IP_LABEL + "," + UPDATED_IP_LABEL);

        // Get all the commercialProductInfoList where ipLabel equals to UPDATED_IP_LABEL
        defaultCommercialProductInfoShouldNotBeFound("ipLabel.in=" + UPDATED_IP_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipLabel is not null
        defaultCommercialProductInfoShouldBeFound("ipLabel.specified=true");

        // Get all the commercialProductInfoList where ipLabel is null
        defaultCommercialProductInfoShouldNotBeFound("ipLabel.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpQtyInMcIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipQtyInMc equals to DEFAULT_IP_QTY_IN_MC
        defaultCommercialProductInfoShouldBeFound("ipQtyInMc.equals=" + DEFAULT_IP_QTY_IN_MC);

        // Get all the commercialProductInfoList where ipQtyInMc equals to UPDATED_IP_QTY_IN_MC
        defaultCommercialProductInfoShouldNotBeFound("ipQtyInMc.equals=" + UPDATED_IP_QTY_IN_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpQtyInMcIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipQtyInMc in DEFAULT_IP_QTY_IN_MC or UPDATED_IP_QTY_IN_MC
        defaultCommercialProductInfoShouldBeFound("ipQtyInMc.in=" + DEFAULT_IP_QTY_IN_MC + "," + UPDATED_IP_QTY_IN_MC);

        // Get all the commercialProductInfoList where ipQtyInMc equals to UPDATED_IP_QTY_IN_MC
        defaultCommercialProductInfoShouldNotBeFound("ipQtyInMc.in=" + UPDATED_IP_QTY_IN_MC);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpQtyInMcIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipQtyInMc is not null
        defaultCommercialProductInfoShouldBeFound("ipQtyInMc.specified=true");

        // Get all the commercialProductInfoList where ipQtyInMc is null
        defaultCommercialProductInfoShouldNotBeFound("ipQtyInMc.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpCostIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipCost equals to DEFAULT_IP_COST
        defaultCommercialProductInfoShouldBeFound("ipCost.equals=" + DEFAULT_IP_COST);

        // Get all the commercialProductInfoList where ipCost equals to UPDATED_IP_COST
        defaultCommercialProductInfoShouldNotBeFound("ipCost.equals=" + UPDATED_IP_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpCostIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipCost in DEFAULT_IP_COST or UPDATED_IP_COST
        defaultCommercialProductInfoShouldBeFound("ipCost.in=" + DEFAULT_IP_COST + "," + UPDATED_IP_COST);

        // Get all the commercialProductInfoList where ipCost equals to UPDATED_IP_COST
        defaultCommercialProductInfoShouldNotBeFound("ipCost.in=" + UPDATED_IP_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByIpCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where ipCost is not null
        defaultCommercialProductInfoShouldBeFound("ipCost.specified=true");

        // Get all the commercialProductInfoList where ipCost is null
        defaultCommercialProductInfoShouldNotBeFound("ipCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcColorIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcColor equals to DEFAULT_MC_COLOR
        defaultCommercialProductInfoShouldBeFound("mcColor.equals=" + DEFAULT_MC_COLOR);

        // Get all the commercialProductInfoList where mcColor equals to UPDATED_MC_COLOR
        defaultCommercialProductInfoShouldNotBeFound("mcColor.equals=" + UPDATED_MC_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcColorIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcColor in DEFAULT_MC_COLOR or UPDATED_MC_COLOR
        defaultCommercialProductInfoShouldBeFound("mcColor.in=" + DEFAULT_MC_COLOR + "," + UPDATED_MC_COLOR);

        // Get all the commercialProductInfoList where mcColor equals to UPDATED_MC_COLOR
        defaultCommercialProductInfoShouldNotBeFound("mcColor.in=" + UPDATED_MC_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcColor is not null
        defaultCommercialProductInfoShouldBeFound("mcColor.specified=true");

        // Get all the commercialProductInfoList where mcColor is null
        defaultCommercialProductInfoShouldNotBeFound("mcColor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcPlyIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcPly equals to DEFAULT_MC_PLY
        defaultCommercialProductInfoShouldBeFound("mcPly.equals=" + DEFAULT_MC_PLY);

        // Get all the commercialProductInfoList where mcPly equals to UPDATED_MC_PLY
        defaultCommercialProductInfoShouldNotBeFound("mcPly.equals=" + UPDATED_MC_PLY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcPlyIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcPly in DEFAULT_MC_PLY or UPDATED_MC_PLY
        defaultCommercialProductInfoShouldBeFound("mcPly.in=" + DEFAULT_MC_PLY + "," + UPDATED_MC_PLY);

        // Get all the commercialProductInfoList where mcPly equals to UPDATED_MC_PLY
        defaultCommercialProductInfoShouldNotBeFound("mcPly.in=" + UPDATED_MC_PLY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcPlyIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcPly is not null
        defaultCommercialProductInfoShouldBeFound("mcPly.specified=true");

        // Get all the commercialProductInfoList where mcPly is null
        defaultCommercialProductInfoShouldNotBeFound("mcPly.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSize equals to DEFAULT_MC_SIZE
        defaultCommercialProductInfoShouldBeFound("mcSize.equals=" + DEFAULT_MC_SIZE);

        // Get all the commercialProductInfoList where mcSize equals to UPDATED_MC_SIZE
        defaultCommercialProductInfoShouldNotBeFound("mcSize.equals=" + UPDATED_MC_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcSizeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSize in DEFAULT_MC_SIZE or UPDATED_MC_SIZE
        defaultCommercialProductInfoShouldBeFound("mcSize.in=" + DEFAULT_MC_SIZE + "," + UPDATED_MC_SIZE);

        // Get all the commercialProductInfoList where mcSize equals to UPDATED_MC_SIZE
        defaultCommercialProductInfoShouldNotBeFound("mcSize.in=" + UPDATED_MC_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSize is not null
        defaultCommercialProductInfoShouldBeFound("mcSize.specified=true");

        // Get all the commercialProductInfoList where mcSize is null
        defaultCommercialProductInfoShouldNotBeFound("mcSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcStickerIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSticker equals to DEFAULT_MC_STICKER
        defaultCommercialProductInfoShouldBeFound("mcSticker.equals=" + DEFAULT_MC_STICKER);

        // Get all the commercialProductInfoList where mcSticker equals to UPDATED_MC_STICKER
        defaultCommercialProductInfoShouldNotBeFound("mcSticker.equals=" + UPDATED_MC_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcStickerIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSticker in DEFAULT_MC_STICKER or UPDATED_MC_STICKER
        defaultCommercialProductInfoShouldBeFound("mcSticker.in=" + DEFAULT_MC_STICKER + "," + UPDATED_MC_STICKER);

        // Get all the commercialProductInfoList where mcSticker equals to UPDATED_MC_STICKER
        defaultCommercialProductInfoShouldNotBeFound("mcSticker.in=" + UPDATED_MC_STICKER);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcStickerIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcSticker is not null
        defaultCommercialProductInfoShouldBeFound("mcSticker.specified=true");

        // Get all the commercialProductInfoList where mcSticker is null
        defaultCommercialProductInfoShouldNotBeFound("mcSticker.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcLabel equals to DEFAULT_MC_LABEL
        defaultCommercialProductInfoShouldBeFound("mcLabel.equals=" + DEFAULT_MC_LABEL);

        // Get all the commercialProductInfoList where mcLabel equals to UPDATED_MC_LABEL
        defaultCommercialProductInfoShouldNotBeFound("mcLabel.equals=" + UPDATED_MC_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcLabelIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcLabel in DEFAULT_MC_LABEL or UPDATED_MC_LABEL
        defaultCommercialProductInfoShouldBeFound("mcLabel.in=" + DEFAULT_MC_LABEL + "," + UPDATED_MC_LABEL);

        // Get all the commercialProductInfoList where mcLabel equals to UPDATED_MC_LABEL
        defaultCommercialProductInfoShouldNotBeFound("mcLabel.in=" + UPDATED_MC_LABEL);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcLabel is not null
        defaultCommercialProductInfoShouldBeFound("mcLabel.specified=true");

        // Get all the commercialProductInfoList where mcLabel is null
        defaultCommercialProductInfoShouldNotBeFound("mcLabel.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcCostIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcCost equals to DEFAULT_MC_COST
        defaultCommercialProductInfoShouldBeFound("mcCost.equals=" + DEFAULT_MC_COST);

        // Get all the commercialProductInfoList where mcCost equals to UPDATED_MC_COST
        defaultCommercialProductInfoShouldNotBeFound("mcCost.equals=" + UPDATED_MC_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcCostIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcCost in DEFAULT_MC_COST or UPDATED_MC_COST
        defaultCommercialProductInfoShouldBeFound("mcCost.in=" + DEFAULT_MC_COST + "," + UPDATED_MC_COST);

        // Get all the commercialProductInfoList where mcCost equals to UPDATED_MC_COST
        defaultCommercialProductInfoShouldNotBeFound("mcCost.in=" + UPDATED_MC_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByMcCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where mcCost is not null
        defaultCommercialProductInfoShouldBeFound("mcCost.specified=true");

        // Get all the commercialProductInfoList where mcCost is null
        defaultCommercialProductInfoShouldNotBeFound("mcCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylColorIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylColor equals to DEFAULT_CYL_COLOR
        defaultCommercialProductInfoShouldBeFound("cylColor.equals=" + DEFAULT_CYL_COLOR);

        // Get all the commercialProductInfoList where cylColor equals to UPDATED_CYL_COLOR
        defaultCommercialProductInfoShouldNotBeFound("cylColor.equals=" + UPDATED_CYL_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylColorIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylColor in DEFAULT_CYL_COLOR or UPDATED_CYL_COLOR
        defaultCommercialProductInfoShouldBeFound("cylColor.in=" + DEFAULT_CYL_COLOR + "," + UPDATED_CYL_COLOR);

        // Get all the commercialProductInfoList where cylColor equals to UPDATED_CYL_COLOR
        defaultCommercialProductInfoShouldNotBeFound("cylColor.in=" + UPDATED_CYL_COLOR);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylColor is not null
        defaultCommercialProductInfoShouldBeFound("cylColor.specified=true");

        // Get all the commercialProductInfoList where cylColor is null
        defaultCommercialProductInfoShouldNotBeFound("cylColor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylSize equals to DEFAULT_CYL_SIZE
        defaultCommercialProductInfoShouldBeFound("cylSize.equals=" + DEFAULT_CYL_SIZE);

        // Get all the commercialProductInfoList where cylSize equals to UPDATED_CYL_SIZE
        defaultCommercialProductInfoShouldNotBeFound("cylSize.equals=" + UPDATED_CYL_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylSizeIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylSize in DEFAULT_CYL_SIZE or UPDATED_CYL_SIZE
        defaultCommercialProductInfoShouldBeFound("cylSize.in=" + DEFAULT_CYL_SIZE + "," + UPDATED_CYL_SIZE);

        // Get all the commercialProductInfoList where cylSize equals to UPDATED_CYL_SIZE
        defaultCommercialProductInfoShouldNotBeFound("cylSize.in=" + UPDATED_CYL_SIZE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylSize is not null
        defaultCommercialProductInfoShouldBeFound("cylSize.specified=true");

        // Get all the commercialProductInfoList where cylSize is null
        defaultCommercialProductInfoShouldNotBeFound("cylSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylQty equals to DEFAULT_CYL_QTY
        defaultCommercialProductInfoShouldBeFound("cylQty.equals=" + DEFAULT_CYL_QTY);

        // Get all the commercialProductInfoList where cylQty equals to UPDATED_CYL_QTY
        defaultCommercialProductInfoShouldNotBeFound("cylQty.equals=" + UPDATED_CYL_QTY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylQtyIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylQty in DEFAULT_CYL_QTY or UPDATED_CYL_QTY
        defaultCommercialProductInfoShouldBeFound("cylQty.in=" + DEFAULT_CYL_QTY + "," + UPDATED_CYL_QTY);

        // Get all the commercialProductInfoList where cylQty equals to UPDATED_CYL_QTY
        defaultCommercialProductInfoShouldNotBeFound("cylQty.in=" + UPDATED_CYL_QTY);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylQty is not null
        defaultCommercialProductInfoShouldBeFound("cylQty.specified=true");

        // Get all the commercialProductInfoList where cylQty is null
        defaultCommercialProductInfoShouldNotBeFound("cylQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylCostIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylCost equals to DEFAULT_CYL_COST
        defaultCommercialProductInfoShouldBeFound("cylCost.equals=" + DEFAULT_CYL_COST);

        // Get all the commercialProductInfoList where cylCost equals to UPDATED_CYL_COST
        defaultCommercialProductInfoShouldNotBeFound("cylCost.equals=" + UPDATED_CYL_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylCostIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylCost in DEFAULT_CYL_COST or UPDATED_CYL_COST
        defaultCommercialProductInfoShouldBeFound("cylCost.in=" + DEFAULT_CYL_COST + "," + UPDATED_CYL_COST);

        // Get all the commercialProductInfoList where cylCost equals to UPDATED_CYL_COST
        defaultCommercialProductInfoShouldNotBeFound("cylCost.in=" + UPDATED_CYL_COST);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByCylCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where cylCost is not null
        defaultCommercialProductInfoShouldBeFound("cylCost.specified=true");

        // Get all the commercialProductInfoList where cylCost is null
        defaultCommercialProductInfoShouldNotBeFound("cylCost.specified=false");
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
    public void getAllCommercialProductInfosByBuyingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingPrice equals to DEFAULT_BUYING_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingPrice.equals=" + DEFAULT_BUYING_PRICE);

        // Get all the commercialProductInfoList where buyingPrice equals to UPDATED_BUYING_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingPrice.equals=" + UPDATED_BUYING_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingPrice in DEFAULT_BUYING_PRICE or UPDATED_BUYING_PRICE
        defaultCommercialProductInfoShouldBeFound("buyingPrice.in=" + DEFAULT_BUYING_PRICE + "," + UPDATED_BUYING_PRICE);

        // Get all the commercialProductInfoList where buyingPrice equals to UPDATED_BUYING_PRICE
        defaultCommercialProductInfoShouldNotBeFound("buyingPrice.in=" + UPDATED_BUYING_PRICE);
    }

    @Test
    @Transactional
    public void getAllCommercialProductInfosByBuyingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialProductInfoRepository.saveAndFlush(commercialProductInfo);

        // Get all the commercialProductInfoList where buyingPrice is not null
        defaultCommercialProductInfoShouldBeFound("buyingPrice.specified=true");

        // Get all the commercialProductInfoList where buyingPrice is null
        defaultCommercialProductInfoShouldNotBeFound("buyingPrice.specified=false");
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
        CommercialBudget commercialBudget = CommercialBudgetResourceIntTest.createEntity(em);
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
            .andExpect(jsonPath("$.[*].taskNo").value(hasItem(DEFAULT_TASK_NO)))
            .andExpect(jsonPath("$.[*].productSpecification").value(hasItem(DEFAULT_PRODUCT_SPECIFICATION.toString())))
            .andExpect(jsonPath("$.[*].spSize").value(hasItem(DEFAULT_SP_SIZE)))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].spGlazing").value(hasItem(DEFAULT_SP_GLAZING)))
            .andExpect(jsonPath("$.[*].spSurfaceType").value(hasItem(DEFAULT_SP_SURFACE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].spOthersDescription").value(hasItem(DEFAULT_SP_OTHERS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].spSticker").value(hasItem(DEFAULT_SP_STICKER)))
            .andExpect(jsonPath("$.[*].spLabel").value(hasItem(DEFAULT_SP_LABEL)))
            .andExpect(jsonPath("$.[*].spQtyInPack").value(hasItem(DEFAULT_SP_QTY_IN_PACK.intValue())))
            .andExpect(jsonPath("$.[*].spQtyInMc").value(hasItem(DEFAULT_SP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipColor").value(hasItem(DEFAULT_IP_COLOR.toString())))
            .andExpect(jsonPath("$.[*].ipSize").value(hasItem(DEFAULT_IP_SIZE)))
            .andExpect(jsonPath("$.[*].ipSticker").value(hasItem(DEFAULT_IP_STICKER)))
            .andExpect(jsonPath("$.[*].ipLabel").value(hasItem(DEFAULT_IP_LABEL)))
            .andExpect(jsonPath("$.[*].ipQtyInMc").value(hasItem(DEFAULT_IP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipCost").value(hasItem(DEFAULT_IP_COST.intValue())))
            .andExpect(jsonPath("$.[*].mcColor").value(hasItem(DEFAULT_MC_COLOR.toString())))
            .andExpect(jsonPath("$.[*].mcPly").value(hasItem(DEFAULT_MC_PLY)))
            .andExpect(jsonPath("$.[*].mcSize").value(hasItem(DEFAULT_MC_SIZE)))
            .andExpect(jsonPath("$.[*].mcSticker").value(hasItem(DEFAULT_MC_STICKER)))
            .andExpect(jsonPath("$.[*].mcLabel").value(hasItem(DEFAULT_MC_LABEL)))
            .andExpect(jsonPath("$.[*].mcCost").value(hasItem(DEFAULT_MC_COST.intValue())))
            .andExpect(jsonPath("$.[*].cylColor").value(hasItem(DEFAULT_CYL_COLOR)))
            .andExpect(jsonPath("$.[*].cylSize").value(hasItem(DEFAULT_CYL_SIZE)))
            .andExpect(jsonPath("$.[*].cylQty").value(hasItem(DEFAULT_CYL_QTY.intValue())))
            .andExpect(jsonPath("$.[*].cylCost").value(hasItem(DEFAULT_CYL_COST.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingPrice").value(hasItem(DEFAULT_BUYING_PRICE.intValue())))
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
            .taskNo(UPDATED_TASK_NO)
            .productSpecification(UPDATED_PRODUCT_SPECIFICATION)
            .spSize(UPDATED_SP_SIZE)
            .offeredQuantity(UPDATED_OFFERED_QUANTITY)
            .offeredUnit(UPDATED_OFFERED_UNIT)
            .offeredUnitPrice(UPDATED_OFFERED_UNIT_PRICE)
            .offeredTotalPrice(UPDATED_OFFERED_TOTAL_PRICE)
            .spGlazing(UPDATED_SP_GLAZING)
            .spSurfaceType(UPDATED_SP_SURFACE_TYPE)
            .spOthersDescription(UPDATED_SP_OTHERS_DESCRIPTION)
            .spSticker(UPDATED_SP_STICKER)
            .spLabel(UPDATED_SP_LABEL)
            .spQtyInPack(UPDATED_SP_QTY_IN_PACK)
            .spQtyInMc(UPDATED_SP_QTY_IN_MC)
            .ipColor(UPDATED_IP_COLOR)
            .ipSize(UPDATED_IP_SIZE)
            .ipSticker(UPDATED_IP_STICKER)
            .ipLabel(UPDATED_IP_LABEL)
            .ipQtyInMc(UPDATED_IP_QTY_IN_MC)
            .ipCost(UPDATED_IP_COST)
            .mcColor(UPDATED_MC_COLOR)
            .mcPly(UPDATED_MC_PLY)
            .mcSize(UPDATED_MC_SIZE)
            .mcSticker(UPDATED_MC_STICKER)
            .mcLabel(UPDATED_MC_LABEL)
            .mcCost(UPDATED_MC_COST)
            .cylColor(UPDATED_CYL_COLOR)
            .cylSize(UPDATED_CYL_SIZE)
            .cylQty(UPDATED_CYL_QTY)
            .cylCost(UPDATED_CYL_COST)
            .buyingQuantity(UPDATED_BUYING_QUANTITY)
            .buyingUnit(UPDATED_BUYING_UNIT)
            .buyingUnitPrice(UPDATED_BUYING_UNIT_PRICE)
            .buyingPrice(UPDATED_BUYING_PRICE)
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
        assertThat(testCommercialProductInfo.getTaskNo()).isEqualTo(UPDATED_TASK_NO);
        assertThat(testCommercialProductInfo.getProductSpecification()).isEqualTo(UPDATED_PRODUCT_SPECIFICATION);
        assertThat(testCommercialProductInfo.getSpSize()).isEqualTo(UPDATED_SP_SIZE);
        assertThat(testCommercialProductInfo.getOfferedQuantity()).isEqualTo(UPDATED_OFFERED_QUANTITY);
        assertThat(testCommercialProductInfo.getOfferedUnit()).isEqualTo(UPDATED_OFFERED_UNIT);
        assertThat(testCommercialProductInfo.getOfferedUnitPrice()).isEqualTo(UPDATED_OFFERED_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getOfferedTotalPrice()).isEqualTo(UPDATED_OFFERED_TOTAL_PRICE);
        assertThat(testCommercialProductInfo.getSpGlazing()).isEqualTo(UPDATED_SP_GLAZING);
        assertThat(testCommercialProductInfo.getSpSurfaceType()).isEqualTo(UPDATED_SP_SURFACE_TYPE);
        assertThat(testCommercialProductInfo.getSpOthersDescription()).isEqualTo(UPDATED_SP_OTHERS_DESCRIPTION);
        assertThat(testCommercialProductInfo.getSpSticker()).isEqualTo(UPDATED_SP_STICKER);
        assertThat(testCommercialProductInfo.getSpLabel()).isEqualTo(UPDATED_SP_LABEL);
        assertThat(testCommercialProductInfo.getSpQtyInPack()).isEqualTo(UPDATED_SP_QTY_IN_PACK);
        assertThat(testCommercialProductInfo.getSpQtyInMc()).isEqualTo(UPDATED_SP_QTY_IN_MC);
        assertThat(testCommercialProductInfo.getIpColor()).isEqualTo(UPDATED_IP_COLOR);
        assertThat(testCommercialProductInfo.getIpSize()).isEqualTo(UPDATED_IP_SIZE);
        assertThat(testCommercialProductInfo.getIpSticker()).isEqualTo(UPDATED_IP_STICKER);
        assertThat(testCommercialProductInfo.getIpLabel()).isEqualTo(UPDATED_IP_LABEL);
        assertThat(testCommercialProductInfo.getIpQtyInMc()).isEqualTo(UPDATED_IP_QTY_IN_MC);
        assertThat(testCommercialProductInfo.getIpCost()).isEqualTo(UPDATED_IP_COST);
        assertThat(testCommercialProductInfo.getMcColor()).isEqualTo(UPDATED_MC_COLOR);
        assertThat(testCommercialProductInfo.getMcPly()).isEqualTo(UPDATED_MC_PLY);
        assertThat(testCommercialProductInfo.getMcSize()).isEqualTo(UPDATED_MC_SIZE);
        assertThat(testCommercialProductInfo.getMcSticker()).isEqualTo(UPDATED_MC_STICKER);
        assertThat(testCommercialProductInfo.getMcLabel()).isEqualTo(UPDATED_MC_LABEL);
        assertThat(testCommercialProductInfo.getMcCost()).isEqualTo(UPDATED_MC_COST);
        assertThat(testCommercialProductInfo.getCylColor()).isEqualTo(UPDATED_CYL_COLOR);
        assertThat(testCommercialProductInfo.getCylSize()).isEqualTo(UPDATED_CYL_SIZE);
        assertThat(testCommercialProductInfo.getCylQty()).isEqualTo(UPDATED_CYL_QTY);
        assertThat(testCommercialProductInfo.getCylCost()).isEqualTo(UPDATED_CYL_COST);
        assertThat(testCommercialProductInfo.getBuyingQuantity()).isEqualTo(UPDATED_BUYING_QUANTITY);
        assertThat(testCommercialProductInfo.getBuyingUnit()).isEqualTo(UPDATED_BUYING_UNIT);
        assertThat(testCommercialProductInfo.getBuyingUnitPrice()).isEqualTo(UPDATED_BUYING_UNIT_PRICE);
        assertThat(testCommercialProductInfo.getBuyingPrice()).isEqualTo(UPDATED_BUYING_PRICE);
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
            .andExpect(jsonPath("$.[*].taskNo").value(hasItem(DEFAULT_TASK_NO)))
            .andExpect(jsonPath("$.[*].productSpecification").value(hasItem(DEFAULT_PRODUCT_SPECIFICATION.toString())))
            .andExpect(jsonPath("$.[*].spSize").value(hasItem(DEFAULT_SP_SIZE)))
            .andExpect(jsonPath("$.[*].offeredQuantity").value(hasItem(DEFAULT_OFFERED_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].offeredUnit").value(hasItem(DEFAULT_OFFERED_UNIT.toString())))
            .andExpect(jsonPath("$.[*].offeredUnitPrice").value(hasItem(DEFAULT_OFFERED_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].offeredTotalPrice").value(hasItem(DEFAULT_OFFERED_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].spGlazing").value(hasItem(DEFAULT_SP_GLAZING)))
            .andExpect(jsonPath("$.[*].spSurfaceType").value(hasItem(DEFAULT_SP_SURFACE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].spOthersDescription").value(hasItem(DEFAULT_SP_OTHERS_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].spSticker").value(hasItem(DEFAULT_SP_STICKER)))
            .andExpect(jsonPath("$.[*].spLabel").value(hasItem(DEFAULT_SP_LABEL)))
            .andExpect(jsonPath("$.[*].spQtyInPack").value(hasItem(DEFAULT_SP_QTY_IN_PACK.intValue())))
            .andExpect(jsonPath("$.[*].spQtyInMc").value(hasItem(DEFAULT_SP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipColor").value(hasItem(DEFAULT_IP_COLOR.toString())))
            .andExpect(jsonPath("$.[*].ipSize").value(hasItem(DEFAULT_IP_SIZE)))
            .andExpect(jsonPath("$.[*].ipSticker").value(hasItem(DEFAULT_IP_STICKER)))
            .andExpect(jsonPath("$.[*].ipLabel").value(hasItem(DEFAULT_IP_LABEL)))
            .andExpect(jsonPath("$.[*].ipQtyInMc").value(hasItem(DEFAULT_IP_QTY_IN_MC.intValue())))
            .andExpect(jsonPath("$.[*].ipCost").value(hasItem(DEFAULT_IP_COST.intValue())))
            .andExpect(jsonPath("$.[*].mcColor").value(hasItem(DEFAULT_MC_COLOR.toString())))
            .andExpect(jsonPath("$.[*].mcPly").value(hasItem(DEFAULT_MC_PLY)))
            .andExpect(jsonPath("$.[*].mcSize").value(hasItem(DEFAULT_MC_SIZE)))
            .andExpect(jsonPath("$.[*].mcSticker").value(hasItem(DEFAULT_MC_STICKER)))
            .andExpect(jsonPath("$.[*].mcLabel").value(hasItem(DEFAULT_MC_LABEL)))
            .andExpect(jsonPath("$.[*].mcCost").value(hasItem(DEFAULT_MC_COST.intValue())))
            .andExpect(jsonPath("$.[*].cylColor").value(hasItem(DEFAULT_CYL_COLOR)))
            .andExpect(jsonPath("$.[*].cylSize").value(hasItem(DEFAULT_CYL_SIZE)))
            .andExpect(jsonPath("$.[*].cylQty").value(hasItem(DEFAULT_CYL_QTY.intValue())))
            .andExpect(jsonPath("$.[*].cylCost").value(hasItem(DEFAULT_CYL_COST.intValue())))
            .andExpect(jsonPath("$.[*].buyingQuantity").value(hasItem(DEFAULT_BUYING_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].buyingUnit").value(hasItem(DEFAULT_BUYING_UNIT.toString())))
            .andExpect(jsonPath("$.[*].buyingUnitPrice").value(hasItem(DEFAULT_BUYING_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].buyingPrice").value(hasItem(DEFAULT_BUYING_PRICE.intValue())))
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
