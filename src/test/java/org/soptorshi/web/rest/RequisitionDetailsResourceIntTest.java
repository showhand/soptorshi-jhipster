package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.RequisitionDetails;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.Product;
import org.soptorshi.repository.RequisitionDetailsRepository;
import org.soptorshi.repository.search.RequisitionDetailsSearchRepository;
import org.soptorshi.service.RequisitionDetailsService;
import org.soptorshi.service.dto.RequisitionDetailsDTO;
import org.soptorshi.service.mapper.RequisitionDetailsMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.RequisitionDetailsCriteria;
import org.soptorshi.service.RequisitionDetailsQueryService;

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

import org.soptorshi.domain.enumeration.UnitOfMeasurements;
/**
 * Test class for the RequisitionDetailsResource REST controller.
 *
 * @see RequisitionDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class RequisitionDetailsResourceIntTest {

    private static final LocalDate DEFAULT_REQUIRED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUIRED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ESTIMATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final UnitOfMeasurements DEFAULT_UOM = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_UOM = UnitOfMeasurements.KG;

    private static final Integer DEFAULT_UNIT = 1;
    private static final Integer UPDATED_UNIT = 2;

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequisitionDetailsRepository requisitionDetailsRepository;

    @Autowired
    private RequisitionDetailsMapper requisitionDetailsMapper;

    @Autowired
    private RequisitionDetailsService requisitionDetailsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.RequisitionDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisitionDetailsSearchRepository mockRequisitionDetailsSearchRepository;

    @Autowired
    private RequisitionDetailsQueryService requisitionDetailsQueryService;

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

    private MockMvc restRequisitionDetailsMockMvc;

    private RequisitionDetails requisitionDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitionDetailsResource requisitionDetailsResource = new RequisitionDetailsResource(requisitionDetailsService, requisitionDetailsQueryService);
        this.restRequisitionDetailsMockMvc = MockMvcBuilders.standaloneSetup(requisitionDetailsResource)
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
    public static RequisitionDetails createEntity(EntityManager em) {
        RequisitionDetails requisitionDetails = new RequisitionDetails()
            .requiredOn(DEFAULT_REQUIRED_ON)
            .estimatedDate(DEFAULT_ESTIMATED_DATE)
            .uom(DEFAULT_UOM)
            .unit(DEFAULT_UNIT)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return requisitionDetails;
    }

    @Before
    public void initTest() {
        requisitionDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisitionDetails() throws Exception {
        int databaseSizeBeforeCreate = requisitionDetailsRepository.findAll().size();

        // Create the RequisitionDetails
        RequisitionDetailsDTO requisitionDetailsDTO = requisitionDetailsMapper.toDto(requisitionDetails);
        restRequisitionDetailsMockMvc.perform(post("/api/requisition-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the RequisitionDetails in the database
        List<RequisitionDetails> requisitionDetailsList = requisitionDetailsRepository.findAll();
        assertThat(requisitionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        RequisitionDetails testRequisitionDetails = requisitionDetailsList.get(requisitionDetailsList.size() - 1);
        assertThat(testRequisitionDetails.getRequiredOn()).isEqualTo(DEFAULT_REQUIRED_ON);
        assertThat(testRequisitionDetails.getEstimatedDate()).isEqualTo(DEFAULT_ESTIMATED_DATE);
        assertThat(testRequisitionDetails.getUom()).isEqualTo(DEFAULT_UOM);
        assertThat(testRequisitionDetails.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testRequisitionDetails.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testRequisitionDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRequisitionDetails.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRequisitionDetails.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the RequisitionDetails in Elasticsearch
        verify(mockRequisitionDetailsSearchRepository, times(1)).save(testRequisitionDetails);
    }

    @Test
    @Transactional
    public void createRequisitionDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionDetailsRepository.findAll().size();

        // Create the RequisitionDetails with an existing ID
        requisitionDetails.setId(1L);
        RequisitionDetailsDTO requisitionDetailsDTO = requisitionDetailsMapper.toDto(requisitionDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionDetailsMockMvc.perform(post("/api/requisition-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionDetails in the database
        List<RequisitionDetails> requisitionDetailsList = requisitionDetailsRepository.findAll();
        assertThat(requisitionDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the RequisitionDetails in Elasticsearch
        verify(mockRequisitionDetailsSearchRepository, times(0)).save(requisitionDetails);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetails() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].requiredOn").value(hasItem(DEFAULT_REQUIRED_ON.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getRequisitionDetails() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get the requisitionDetails
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details/{id}", requisitionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisitionDetails.getId().intValue()))
            .andExpect(jsonPath("$.requiredOn").value(DEFAULT_REQUIRED_ON.toString()))
            .andExpect(jsonPath("$.estimatedDate").value(DEFAULT_ESTIMATED_DATE.toString()))
            .andExpect(jsonPath("$.uom").value(DEFAULT_UOM.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequiredOnIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where requiredOn equals to DEFAULT_REQUIRED_ON
        defaultRequisitionDetailsShouldBeFound("requiredOn.equals=" + DEFAULT_REQUIRED_ON);

        // Get all the requisitionDetailsList where requiredOn equals to UPDATED_REQUIRED_ON
        defaultRequisitionDetailsShouldNotBeFound("requiredOn.equals=" + UPDATED_REQUIRED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequiredOnIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where requiredOn in DEFAULT_REQUIRED_ON or UPDATED_REQUIRED_ON
        defaultRequisitionDetailsShouldBeFound("requiredOn.in=" + DEFAULT_REQUIRED_ON + "," + UPDATED_REQUIRED_ON);

        // Get all the requisitionDetailsList where requiredOn equals to UPDATED_REQUIRED_ON
        defaultRequisitionDetailsShouldNotBeFound("requiredOn.in=" + UPDATED_REQUIRED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequiredOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where requiredOn is not null
        defaultRequisitionDetailsShouldBeFound("requiredOn.specified=true");

        // Get all the requisitionDetailsList where requiredOn is null
        defaultRequisitionDetailsShouldNotBeFound("requiredOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequiredOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where requiredOn greater than or equals to DEFAULT_REQUIRED_ON
        defaultRequisitionDetailsShouldBeFound("requiredOn.greaterOrEqualThan=" + DEFAULT_REQUIRED_ON);

        // Get all the requisitionDetailsList where requiredOn greater than or equals to UPDATED_REQUIRED_ON
        defaultRequisitionDetailsShouldNotBeFound("requiredOn.greaterOrEqualThan=" + UPDATED_REQUIRED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequiredOnIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where requiredOn less than or equals to DEFAULT_REQUIRED_ON
        defaultRequisitionDetailsShouldNotBeFound("requiredOn.lessThan=" + DEFAULT_REQUIRED_ON);

        // Get all the requisitionDetailsList where requiredOn less than or equals to UPDATED_REQUIRED_ON
        defaultRequisitionDetailsShouldBeFound("requiredOn.lessThan=" + UPDATED_REQUIRED_ON);
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByEstimatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where estimatedDate equals to DEFAULT_ESTIMATED_DATE
        defaultRequisitionDetailsShouldBeFound("estimatedDate.equals=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requisitionDetailsList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultRequisitionDetailsShouldNotBeFound("estimatedDate.equals=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByEstimatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where estimatedDate in DEFAULT_ESTIMATED_DATE or UPDATED_ESTIMATED_DATE
        defaultRequisitionDetailsShouldBeFound("estimatedDate.in=" + DEFAULT_ESTIMATED_DATE + "," + UPDATED_ESTIMATED_DATE);

        // Get all the requisitionDetailsList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultRequisitionDetailsShouldNotBeFound("estimatedDate.in=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByEstimatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where estimatedDate is not null
        defaultRequisitionDetailsShouldBeFound("estimatedDate.specified=true");

        // Get all the requisitionDetailsList where estimatedDate is null
        defaultRequisitionDetailsShouldNotBeFound("estimatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByEstimatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where estimatedDate greater than or equals to DEFAULT_ESTIMATED_DATE
        defaultRequisitionDetailsShouldBeFound("estimatedDate.greaterOrEqualThan=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requisitionDetailsList where estimatedDate greater than or equals to UPDATED_ESTIMATED_DATE
        defaultRequisitionDetailsShouldNotBeFound("estimatedDate.greaterOrEqualThan=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByEstimatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where estimatedDate less than or equals to DEFAULT_ESTIMATED_DATE
        defaultRequisitionDetailsShouldNotBeFound("estimatedDate.lessThan=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requisitionDetailsList where estimatedDate less than or equals to UPDATED_ESTIMATED_DATE
        defaultRequisitionDetailsShouldBeFound("estimatedDate.lessThan=" + UPDATED_ESTIMATED_DATE);
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByUomIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where uom equals to DEFAULT_UOM
        defaultRequisitionDetailsShouldBeFound("uom.equals=" + DEFAULT_UOM);

        // Get all the requisitionDetailsList where uom equals to UPDATED_UOM
        defaultRequisitionDetailsShouldNotBeFound("uom.equals=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUomIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where uom in DEFAULT_UOM or UPDATED_UOM
        defaultRequisitionDetailsShouldBeFound("uom.in=" + DEFAULT_UOM + "," + UPDATED_UOM);

        // Get all the requisitionDetailsList where uom equals to UPDATED_UOM
        defaultRequisitionDetailsShouldNotBeFound("uom.in=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUomIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where uom is not null
        defaultRequisitionDetailsShouldBeFound("uom.specified=true");

        // Get all the requisitionDetailsList where uom is null
        defaultRequisitionDetailsShouldNotBeFound("uom.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unit equals to DEFAULT_UNIT
        defaultRequisitionDetailsShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the requisitionDetailsList where unit equals to UPDATED_UNIT
        defaultRequisitionDetailsShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultRequisitionDetailsShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the requisitionDetailsList where unit equals to UPDATED_UNIT
        defaultRequisitionDetailsShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unit is not null
        defaultRequisitionDetailsShouldBeFound("unit.specified=true");

        // Get all the requisitionDetailsList where unit is null
        defaultRequisitionDetailsShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unit greater than or equals to DEFAULT_UNIT
        defaultRequisitionDetailsShouldBeFound("unit.greaterOrEqualThan=" + DEFAULT_UNIT);

        // Get all the requisitionDetailsList where unit greater than or equals to UPDATED_UNIT
        defaultRequisitionDetailsShouldNotBeFound("unit.greaterOrEqualThan=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unit less than or equals to DEFAULT_UNIT
        defaultRequisitionDetailsShouldNotBeFound("unit.lessThan=" + DEFAULT_UNIT);

        // Get all the requisitionDetailsList where unit less than or equals to UPDATED_UNIT
        defaultRequisitionDetailsShouldBeFound("unit.lessThan=" + UPDATED_UNIT);
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultRequisitionDetailsShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the requisitionDetailsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultRequisitionDetailsShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultRequisitionDetailsShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the requisitionDetailsList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultRequisitionDetailsShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where unitPrice is not null
        defaultRequisitionDetailsShouldBeFound("unitPrice.specified=true");

        // Get all the requisitionDetailsList where unitPrice is null
        defaultRequisitionDetailsShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where quantity equals to DEFAULT_QUANTITY
        defaultRequisitionDetailsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the requisitionDetailsList where quantity equals to UPDATED_QUANTITY
        defaultRequisitionDetailsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultRequisitionDetailsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the requisitionDetailsList where quantity equals to UPDATED_QUANTITY
        defaultRequisitionDetailsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where quantity is not null
        defaultRequisitionDetailsShouldBeFound("quantity.specified=true");

        // Get all the requisitionDetailsList where quantity is null
        defaultRequisitionDetailsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultRequisitionDetailsShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the requisitionDetailsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionDetailsShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultRequisitionDetailsShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the requisitionDetailsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionDetailsShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedBy is not null
        defaultRequisitionDetailsShouldBeFound("modifiedBy.specified=true");

        // Get all the requisitionDetailsList where modifiedBy is null
        defaultRequisitionDetailsShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultRequisitionDetailsShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionDetailsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionDetailsShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultRequisitionDetailsShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the requisitionDetailsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionDetailsShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedOn is not null
        defaultRequisitionDetailsShouldBeFound("modifiedOn.specified=true");

        // Get all the requisitionDetailsList where modifiedOn is null
        defaultRequisitionDetailsShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionDetailsShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionDetailsList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionDetailsShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionDetailsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        // Get all the requisitionDetailsList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionDetailsShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionDetailsList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionDetailsShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategory = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategory);
        em.flush();
        requisitionDetails.setProductCategory(productCategory);
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);
        Long productCategoryId = productCategory.getId();

        // Get all the requisitionDetailsList where productCategory equals to productCategoryId
        defaultRequisitionDetailsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the requisitionDetailsList where productCategory equals to productCategoryId + 1
        defaultRequisitionDetailsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        requisitionDetails.setRequisition(requisition);
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);
        Long requisitionId = requisition.getId();

        // Get all the requisitionDetailsList where requisition equals to requisitionId
        defaultRequisitionDetailsShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the requisitionDetailsList where requisition equals to requisitionId + 1
        defaultRequisitionDetailsShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        requisitionDetails.setProduct(product);
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);
        Long productId = product.getId();

        // Get all the requisitionDetailsList where product equals to productId
        defaultRequisitionDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the requisitionDetailsList where product equals to productId + 1
        defaultRequisitionDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRequisitionDetailsShouldBeFound(String filter) throws Exception {
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].requiredOn").value(hasItem(DEFAULT_REQUIRED_ON.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRequisitionDetailsShouldNotBeFound(String filter) throws Exception {
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequisitionDetails() throws Exception {
        // Get the requisitionDetails
        restRequisitionDetailsMockMvc.perform(get("/api/requisition-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisitionDetails() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        int databaseSizeBeforeUpdate = requisitionDetailsRepository.findAll().size();

        // Update the requisitionDetails
        RequisitionDetails updatedRequisitionDetails = requisitionDetailsRepository.findById(requisitionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedRequisitionDetails are not directly saved in db
        em.detach(updatedRequisitionDetails);
        updatedRequisitionDetails
            .requiredOn(UPDATED_REQUIRED_ON)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .uom(UPDATED_UOM)
            .unit(UPDATED_UNIT)
            .unitPrice(UPDATED_UNIT_PRICE)
            .quantity(UPDATED_QUANTITY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        RequisitionDetailsDTO requisitionDetailsDTO = requisitionDetailsMapper.toDto(updatedRequisitionDetails);

        restRequisitionDetailsMockMvc.perform(put("/api/requisition-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the RequisitionDetails in the database
        List<RequisitionDetails> requisitionDetailsList = requisitionDetailsRepository.findAll();
        assertThat(requisitionDetailsList).hasSize(databaseSizeBeforeUpdate);
        RequisitionDetails testRequisitionDetails = requisitionDetailsList.get(requisitionDetailsList.size() - 1);
        assertThat(testRequisitionDetails.getRequiredOn()).isEqualTo(UPDATED_REQUIRED_ON);
        assertThat(testRequisitionDetails.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testRequisitionDetails.getUom()).isEqualTo(UPDATED_UOM);
        assertThat(testRequisitionDetails.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testRequisitionDetails.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testRequisitionDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRequisitionDetails.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRequisitionDetails.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the RequisitionDetails in Elasticsearch
        verify(mockRequisitionDetailsSearchRepository, times(1)).save(testRequisitionDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisitionDetails() throws Exception {
        int databaseSizeBeforeUpdate = requisitionDetailsRepository.findAll().size();

        // Create the RequisitionDetails
        RequisitionDetailsDTO requisitionDetailsDTO = requisitionDetailsMapper.toDto(requisitionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionDetailsMockMvc.perform(put("/api/requisition-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionDetails in the database
        List<RequisitionDetails> requisitionDetailsList = requisitionDetailsRepository.findAll();
        assertThat(requisitionDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RequisitionDetails in Elasticsearch
        verify(mockRequisitionDetailsSearchRepository, times(0)).save(requisitionDetails);
    }

    @Test
    @Transactional
    public void deleteRequisitionDetails() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);

        int databaseSizeBeforeDelete = requisitionDetailsRepository.findAll().size();

        // Delete the requisitionDetails
        restRequisitionDetailsMockMvc.perform(delete("/api/requisition-details/{id}", requisitionDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequisitionDetails> requisitionDetailsList = requisitionDetailsRepository.findAll();
        assertThat(requisitionDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RequisitionDetails in Elasticsearch
        verify(mockRequisitionDetailsSearchRepository, times(1)).deleteById(requisitionDetails.getId());
    }

    @Test
    @Transactional
    public void searchRequisitionDetails() throws Exception {
        // Initialize the database
        requisitionDetailsRepository.saveAndFlush(requisitionDetails);
        when(mockRequisitionDetailsSearchRepository.search(queryStringQuery("id:" + requisitionDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisitionDetails), PageRequest.of(0, 1), 1));
        // Search the requisitionDetails
        restRequisitionDetailsMockMvc.perform(get("/api/_search/requisition-details?query=id:" + requisitionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].requiredOn").value(hasItem(DEFAULT_REQUIRED_ON.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionDetails.class);
        RequisitionDetails requisitionDetails1 = new RequisitionDetails();
        requisitionDetails1.setId(1L);
        RequisitionDetails requisitionDetails2 = new RequisitionDetails();
        requisitionDetails2.setId(requisitionDetails1.getId());
        assertThat(requisitionDetails1).isEqualTo(requisitionDetails2);
        requisitionDetails2.setId(2L);
        assertThat(requisitionDetails1).isNotEqualTo(requisitionDetails2);
        requisitionDetails1.setId(null);
        assertThat(requisitionDetails1).isNotEqualTo(requisitionDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionDetailsDTO.class);
        RequisitionDetailsDTO requisitionDetailsDTO1 = new RequisitionDetailsDTO();
        requisitionDetailsDTO1.setId(1L);
        RequisitionDetailsDTO requisitionDetailsDTO2 = new RequisitionDetailsDTO();
        assertThat(requisitionDetailsDTO1).isNotEqualTo(requisitionDetailsDTO2);
        requisitionDetailsDTO2.setId(requisitionDetailsDTO1.getId());
        assertThat(requisitionDetailsDTO1).isEqualTo(requisitionDetailsDTO2);
        requisitionDetailsDTO2.setId(2L);
        assertThat(requisitionDetailsDTO1).isNotEqualTo(requisitionDetailsDTO2);
        requisitionDetailsDTO1.setId(null);
        assertThat(requisitionDetailsDTO1).isNotEqualTo(requisitionDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requisitionDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requisitionDetailsMapper.fromId(null)).isNull();
    }
}
