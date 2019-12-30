package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.domain.enumeration.CommercialPoStatus;
import org.soptorshi.repository.CommercialPoRepository;
import org.soptorshi.repository.search.CommercialPoSearchRepository;
import org.soptorshi.service.CommercialPoQueryService;
import org.soptorshi.service.CommercialPoService;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPoMapper;
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
 * Test class for the CommercialPoResource REST controller.
 *
 * @see CommercialPoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPoResourceIntTest {

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

    private static final CommercialPoStatus DEFAULT_PO_STATUS = CommercialPoStatus.PO_CREATED;
    private static final CommercialPoStatus UPDATED_PO_STATUS = CommercialPoStatus.PREPARING_ORDER;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommercialPoRepository commercialPoRepository;

    @Autowired
    private CommercialPoMapper commercialPoMapper;

    @Autowired
    private CommercialPoService commercialPoService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPoSearchRepository mockCommercialPoSearchRepository;

    @Autowired
    private CommercialPoQueryService commercialPoQueryService;

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

    private MockMvc restCommercialPoMockMvc;

    private CommercialPo commercialPo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPoResource commercialPoResource = new CommercialPoResource(commercialPoService, commercialPoQueryService);
        this.restCommercialPoMockMvc = MockMvcBuilders.standaloneSetup(commercialPoResource)
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
    public static CommercialPo createEntity(EntityManager em) {
        CommercialPo commercialPo = new CommercialPo()
            .purchaseOrderNo(DEFAULT_PURCHASE_ORDER_NO)
            .purchaseOrderDate(DEFAULT_PURCHASE_ORDER_DATE)
            .originOfGoods(DEFAULT_ORIGIN_OF_GOODS)
            .finalDestination(DEFAULT_FINAL_DESTINATION)
            .shipmentDate(DEFAULT_SHIPMENT_DATE)
            .poStatus(DEFAULT_PO_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPo;
    }

    @Before
    public void initTest() {
        commercialPo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPo() throws Exception {
        int databaseSizeBeforeCreate = commercialPoRepository.findAll().size();

        // Create the CommercialPo
        CommercialPoDTO commercialPoDTO = commercialPoMapper.toDto(commercialPo);
        restCommercialPoMockMvc.perform(post("/api/commercial-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPo in the database
        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPo testCommercialPo = commercialPoList.get(commercialPoList.size() - 1);
        assertThat(testCommercialPo.getPurchaseOrderNo()).isEqualTo(DEFAULT_PURCHASE_ORDER_NO);
        assertThat(testCommercialPo.getPurchaseOrderDate()).isEqualTo(DEFAULT_PURCHASE_ORDER_DATE);
        assertThat(testCommercialPo.getOriginOfGoods()).isEqualTo(DEFAULT_ORIGIN_OF_GOODS);
        assertThat(testCommercialPo.getFinalDestination()).isEqualTo(DEFAULT_FINAL_DESTINATION);
        assertThat(testCommercialPo.getShipmentDate()).isEqualTo(DEFAULT_SHIPMENT_DATE);
        assertThat(testCommercialPo.getPoStatus()).isEqualTo(DEFAULT_PO_STATUS);
        assertThat(testCommercialPo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCommercialPo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPo in Elasticsearch
        verify(mockCommercialPoSearchRepository, times(1)).save(testCommercialPo);
    }

    @Test
    @Transactional
    public void createCommercialPoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPoRepository.findAll().size();

        // Create the CommercialPo with an existing ID
        commercialPo.setId(1L);
        CommercialPoDTO commercialPoDTO = commercialPoMapper.toDto(commercialPo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPoMockMvc.perform(post("/api/commercial-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPo in the database
        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPo in Elasticsearch
        verify(mockCommercialPoSearchRepository, times(0)).save(commercialPo);
    }

    @Test
    @Transactional
    public void checkPurchaseOrderNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPoRepository.findAll().size();
        // set the field null
        commercialPo.setPurchaseOrderNo(null);

        // Create the CommercialPo, which fails.
        CommercialPoDTO commercialPoDTO = commercialPoMapper.toDto(commercialPo);

        restCommercialPoMockMvc.perform(post("/api/commercial-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPos() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList
        restCommercialPoMockMvc.perform(get("/api/commercial-pos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS.toString())))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].poStatus").value(hasItem(DEFAULT_PO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPo() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get the commercialPo
        restCommercialPoMockMvc.perform(get("/api/commercial-pos/{id}", commercialPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPo.getId().intValue()))
            .andExpect(jsonPath("$.purchaseOrderNo").value(DEFAULT_PURCHASE_ORDER_NO.toString()))
            .andExpect(jsonPath("$.purchaseOrderDate").value(DEFAULT_PURCHASE_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.originOfGoods").value(DEFAULT_ORIGIN_OF_GOODS.toString()))
            .andExpect(jsonPath("$.finalDestination").value(DEFAULT_FINAL_DESTINATION.toString()))
            .andExpect(jsonPath("$.shipmentDate").value(DEFAULT_SHIPMENT_DATE.toString()))
            .andExpect(jsonPath("$.poStatus").value(DEFAULT_PO_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderNo equals to DEFAULT_PURCHASE_ORDER_NO
        defaultCommercialPoShouldBeFound("purchaseOrderNo.equals=" + DEFAULT_PURCHASE_ORDER_NO);

        // Get all the commercialPoList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPoShouldNotBeFound("purchaseOrderNo.equals=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderNo in DEFAULT_PURCHASE_ORDER_NO or UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPoShouldBeFound("purchaseOrderNo.in=" + DEFAULT_PURCHASE_ORDER_NO + "," + UPDATED_PURCHASE_ORDER_NO);

        // Get all the commercialPoList where purchaseOrderNo equals to UPDATED_PURCHASE_ORDER_NO
        defaultCommercialPoShouldNotBeFound("purchaseOrderNo.in=" + UPDATED_PURCHASE_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderNo is not null
        defaultCommercialPoShouldBeFound("purchaseOrderNo.specified=true");

        // Get all the commercialPoList where purchaseOrderNo is null
        defaultCommercialPoShouldNotBeFound("purchaseOrderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderDate equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldBeFound("purchaseOrderDate.equals=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPoList where purchaseOrderDate equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldNotBeFound("purchaseOrderDate.equals=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderDate in DEFAULT_PURCHASE_ORDER_DATE or UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldBeFound("purchaseOrderDate.in=" + DEFAULT_PURCHASE_ORDER_DATE + "," + UPDATED_PURCHASE_ORDER_DATE);

        // Get all the commercialPoList where purchaseOrderDate equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldNotBeFound("purchaseOrderDate.in=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderDate is not null
        defaultCommercialPoShouldBeFound("purchaseOrderDate.specified=true");

        // Get all the commercialPoList where purchaseOrderDate is null
        defaultCommercialPoShouldNotBeFound("purchaseOrderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderDate greater than or equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldBeFound("purchaseOrderDate.greaterOrEqualThan=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPoList where purchaseOrderDate greater than or equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldNotBeFound("purchaseOrderDate.greaterOrEqualThan=" + UPDATED_PURCHASE_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPurchaseOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where purchaseOrderDate less than or equals to DEFAULT_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldNotBeFound("purchaseOrderDate.lessThan=" + DEFAULT_PURCHASE_ORDER_DATE);

        // Get all the commercialPoList where purchaseOrderDate less than or equals to UPDATED_PURCHASE_ORDER_DATE
        defaultCommercialPoShouldBeFound("purchaseOrderDate.lessThan=" + UPDATED_PURCHASE_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPosByOriginOfGoodsIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where originOfGoods equals to DEFAULT_ORIGIN_OF_GOODS
        defaultCommercialPoShouldBeFound("originOfGoods.equals=" + DEFAULT_ORIGIN_OF_GOODS);

        // Get all the commercialPoList where originOfGoods equals to UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPoShouldNotBeFound("originOfGoods.equals=" + UPDATED_ORIGIN_OF_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByOriginOfGoodsIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where originOfGoods in DEFAULT_ORIGIN_OF_GOODS or UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPoShouldBeFound("originOfGoods.in=" + DEFAULT_ORIGIN_OF_GOODS + "," + UPDATED_ORIGIN_OF_GOODS);

        // Get all the commercialPoList where originOfGoods equals to UPDATED_ORIGIN_OF_GOODS
        defaultCommercialPoShouldNotBeFound("originOfGoods.in=" + UPDATED_ORIGIN_OF_GOODS);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByOriginOfGoodsIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where originOfGoods is not null
        defaultCommercialPoShouldBeFound("originOfGoods.specified=true");

        // Get all the commercialPoList where originOfGoods is null
        defaultCommercialPoShouldNotBeFound("originOfGoods.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByFinalDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where finalDestination equals to DEFAULT_FINAL_DESTINATION
        defaultCommercialPoShouldBeFound("finalDestination.equals=" + DEFAULT_FINAL_DESTINATION);

        // Get all the commercialPoList where finalDestination equals to UPDATED_FINAL_DESTINATION
        defaultCommercialPoShouldNotBeFound("finalDestination.equals=" + UPDATED_FINAL_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByFinalDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where finalDestination in DEFAULT_FINAL_DESTINATION or UPDATED_FINAL_DESTINATION
        defaultCommercialPoShouldBeFound("finalDestination.in=" + DEFAULT_FINAL_DESTINATION + "," + UPDATED_FINAL_DESTINATION);

        // Get all the commercialPoList where finalDestination equals to UPDATED_FINAL_DESTINATION
        defaultCommercialPoShouldNotBeFound("finalDestination.in=" + UPDATED_FINAL_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByFinalDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where finalDestination is not null
        defaultCommercialPoShouldBeFound("finalDestination.specified=true");

        // Get all the commercialPoList where finalDestination is null
        defaultCommercialPoShouldNotBeFound("finalDestination.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByShipmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where shipmentDate equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPoShouldBeFound("shipmentDate.equals=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPoList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPoShouldNotBeFound("shipmentDate.equals=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByShipmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where shipmentDate in DEFAULT_SHIPMENT_DATE or UPDATED_SHIPMENT_DATE
        defaultCommercialPoShouldBeFound("shipmentDate.in=" + DEFAULT_SHIPMENT_DATE + "," + UPDATED_SHIPMENT_DATE);

        // Get all the commercialPoList where shipmentDate equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPoShouldNotBeFound("shipmentDate.in=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByShipmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where shipmentDate is not null
        defaultCommercialPoShouldBeFound("shipmentDate.specified=true");

        // Get all the commercialPoList where shipmentDate is null
        defaultCommercialPoShouldNotBeFound("shipmentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByShipmentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where shipmentDate greater than or equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPoShouldBeFound("shipmentDate.greaterOrEqualThan=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPoList where shipmentDate greater than or equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPoShouldNotBeFound("shipmentDate.greaterOrEqualThan=" + UPDATED_SHIPMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByShipmentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where shipmentDate less than or equals to DEFAULT_SHIPMENT_DATE
        defaultCommercialPoShouldNotBeFound("shipmentDate.lessThan=" + DEFAULT_SHIPMENT_DATE);

        // Get all the commercialPoList where shipmentDate less than or equals to UPDATED_SHIPMENT_DATE
        defaultCommercialPoShouldBeFound("shipmentDate.lessThan=" + UPDATED_SHIPMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllCommercialPosByPoStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where poStatus equals to DEFAULT_PO_STATUS
        defaultCommercialPoShouldBeFound("poStatus.equals=" + DEFAULT_PO_STATUS);

        // Get all the commercialPoList where poStatus equals to UPDATED_PO_STATUS
        defaultCommercialPoShouldNotBeFound("poStatus.equals=" + UPDATED_PO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPoStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where poStatus in DEFAULT_PO_STATUS or UPDATED_PO_STATUS
        defaultCommercialPoShouldBeFound("poStatus.in=" + DEFAULT_PO_STATUS + "," + UPDATED_PO_STATUS);

        // Get all the commercialPoList where poStatus equals to UPDATED_PO_STATUS
        defaultCommercialPoShouldNotBeFound("poStatus.in=" + UPDATED_PO_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByPoStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where poStatus is not null
        defaultCommercialPoShouldBeFound("poStatus.specified=true");

        // Get all the commercialPoList where poStatus is null
        defaultCommercialPoShouldNotBeFound("poStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPoList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdBy is not null
        defaultCommercialPoShouldBeFound("createdBy.specified=true");

        // Get all the commercialPoList where createdBy is null
        defaultCommercialPoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdOn equals to DEFAULT_CREATED_ON
        defaultCommercialPoShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the commercialPoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPoShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultCommercialPoShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the commercialPoList where createdOn equals to UPDATED_CREATED_ON
        defaultCommercialPoShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where createdOn is not null
        defaultCommercialPoShouldBeFound("createdOn.specified=true");

        // Get all the commercialPoList where createdOn is null
        defaultCommercialPoShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPoShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPoShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPoShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPoList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPoShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedBy is not null
        defaultCommercialPoShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPoList where updatedBy is null
        defaultCommercialPoShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPoShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPoShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPoShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPoList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPoShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPosByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        // Get all the commercialPoList where updatedOn is not null
        defaultCommercialPoShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPoList where updatedOn is null
        defaultCommercialPoShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPosByCommercialPiIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPi commercialPi = CommercialPiResourceIntTest.createEntity(em);
        em.persist(commercialPi);
        em.flush();
        commercialPo.setCommercialPi(commercialPi);
        commercialPoRepository.saveAndFlush(commercialPo);
        Long commercialPiId = commercialPi.getId();

        // Get all the commercialPoList where commercialPi equals to commercialPiId
        defaultCommercialPoShouldBeFound("commercialPiId.equals=" + commercialPiId);

        // Get all the commercialPoList where commercialPi equals to commercialPiId + 1
        defaultCommercialPoShouldNotBeFound("commercialPiId.equals=" + (commercialPiId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPoShouldBeFound(String filter) throws Exception {
        restCommercialPoMockMvc.perform(get("/api/commercial-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS)))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].poStatus").value(hasItem(DEFAULT_PO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPoMockMvc.perform(get("/api/commercial-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPoShouldNotBeFound(String filter) throws Exception {
        restCommercialPoMockMvc.perform(get("/api/commercial-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPoMockMvc.perform(get("/api/commercial-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPo() throws Exception {
        // Get the commercialPo
        restCommercialPoMockMvc.perform(get("/api/commercial-pos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPo() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        int databaseSizeBeforeUpdate = commercialPoRepository.findAll().size();

        // Update the commercialPo
        CommercialPo updatedCommercialPo = commercialPoRepository.findById(commercialPo.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPo are not directly saved in db
        em.detach(updatedCommercialPo);
        updatedCommercialPo
            .purchaseOrderNo(UPDATED_PURCHASE_ORDER_NO)
            .purchaseOrderDate(UPDATED_PURCHASE_ORDER_DATE)
            .originOfGoods(UPDATED_ORIGIN_OF_GOODS)
            .finalDestination(UPDATED_FINAL_DESTINATION)
            .shipmentDate(UPDATED_SHIPMENT_DATE)
            .poStatus(UPDATED_PO_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPoDTO commercialPoDTO = commercialPoMapper.toDto(updatedCommercialPo);

        restCommercialPoMockMvc.perform(put("/api/commercial-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPo in the database
        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeUpdate);
        CommercialPo testCommercialPo = commercialPoList.get(commercialPoList.size() - 1);
        assertThat(testCommercialPo.getPurchaseOrderNo()).isEqualTo(UPDATED_PURCHASE_ORDER_NO);
        assertThat(testCommercialPo.getPurchaseOrderDate()).isEqualTo(UPDATED_PURCHASE_ORDER_DATE);
        assertThat(testCommercialPo.getOriginOfGoods()).isEqualTo(UPDATED_ORIGIN_OF_GOODS);
        assertThat(testCommercialPo.getFinalDestination()).isEqualTo(UPDATED_FINAL_DESTINATION);
        assertThat(testCommercialPo.getShipmentDate()).isEqualTo(UPDATED_SHIPMENT_DATE);
        assertThat(testCommercialPo.getPoStatus()).isEqualTo(UPDATED_PO_STATUS);
        assertThat(testCommercialPo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCommercialPo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPo in Elasticsearch
        verify(mockCommercialPoSearchRepository, times(1)).save(testCommercialPo);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPo() throws Exception {
        int databaseSizeBeforeUpdate = commercialPoRepository.findAll().size();

        // Create the CommercialPo
        CommercialPoDTO commercialPoDTO = commercialPoMapper.toDto(commercialPo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPoMockMvc.perform(put("/api/commercial-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPo in the database
        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPo in Elasticsearch
        verify(mockCommercialPoSearchRepository, times(0)).save(commercialPo);
    }

    @Test
    @Transactional
    public void deleteCommercialPo() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);

        int databaseSizeBeforeDelete = commercialPoRepository.findAll().size();

        // Delete the commercialPo
        restCommercialPoMockMvc.perform(delete("/api/commercial-pos/{id}", commercialPo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPo> commercialPoList = commercialPoRepository.findAll();
        assertThat(commercialPoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPo in Elasticsearch
        verify(mockCommercialPoSearchRepository, times(1)).deleteById(commercialPo.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPo() throws Exception {
        // Initialize the database
        commercialPoRepository.saveAndFlush(commercialPo);
        when(mockCommercialPoSearchRepository.search(queryStringQuery("id:" + commercialPo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPo), PageRequest.of(0, 1), 1));
        // Search the commercialPo
        restCommercialPoMockMvc.perform(get("/api/_search/commercial-pos?query=id:" + commercialPo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPo.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseOrderNo").value(hasItem(DEFAULT_PURCHASE_ORDER_NO)))
            .andExpect(jsonPath("$.[*].purchaseOrderDate").value(hasItem(DEFAULT_PURCHASE_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].originOfGoods").value(hasItem(DEFAULT_ORIGIN_OF_GOODS)))
            .andExpect(jsonPath("$.[*].finalDestination").value(hasItem(DEFAULT_FINAL_DESTINATION)))
            .andExpect(jsonPath("$.[*].shipmentDate").value(hasItem(DEFAULT_SHIPMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].poStatus").value(hasItem(DEFAULT_PO_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPo.class);
        CommercialPo commercialPo1 = new CommercialPo();
        commercialPo1.setId(1L);
        CommercialPo commercialPo2 = new CommercialPo();
        commercialPo2.setId(commercialPo1.getId());
        assertThat(commercialPo1).isEqualTo(commercialPo2);
        commercialPo2.setId(2L);
        assertThat(commercialPo1).isNotEqualTo(commercialPo2);
        commercialPo1.setId(null);
        assertThat(commercialPo1).isNotEqualTo(commercialPo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPoDTO.class);
        CommercialPoDTO commercialPoDTO1 = new CommercialPoDTO();
        commercialPoDTO1.setId(1L);
        CommercialPoDTO commercialPoDTO2 = new CommercialPoDTO();
        assertThat(commercialPoDTO1).isNotEqualTo(commercialPoDTO2);
        commercialPoDTO2.setId(commercialPoDTO1.getId());
        assertThat(commercialPoDTO1).isEqualTo(commercialPoDTO2);
        commercialPoDTO2.setId(2L);
        assertThat(commercialPoDTO1).isNotEqualTo(commercialPoDTO2);
        commercialPoDTO1.setId(null);
        assertThat(commercialPoDTO1).isNotEqualTo(commercialPoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPoMapper.fromId(null)).isNull();
    }
}
