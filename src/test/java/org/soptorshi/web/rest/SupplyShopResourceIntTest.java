package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyShopRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.service.SupplyShopQueryService;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.dto.SupplyShopDTO;
import org.soptorshi.service.mapper.SupplyShopMapper;
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
 * Test class for the SupplyShopResource REST controller.
 *
 * @see SupplyShopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyShopResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplyShopRepository supplyShopRepository;

    @Autowired
    private SupplyShopMapper supplyShopMapper;

    @Autowired
    private SupplyShopService supplyShopService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyShopSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyShopSearchRepository mockSupplyShopSearchRepository;

    @Autowired
    private SupplyShopQueryService supplyShopQueryService;

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

    private MockMvc restSupplyShopMockMvc;

    private SupplyShop supplyShop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyShopResource supplyShopResource = new SupplyShopResource(supplyShopService, supplyShopQueryService);
        this.restSupplyShopMockMvc = MockMvcBuilders.standaloneSetup(supplyShopResource)
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
    public static SupplyShop createEntity(EntityManager em) {
        SupplyShop supplyShop = new SupplyShop()
            .name(DEFAULT_NAME)
            .contact(DEFAULT_CONTACT)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyShop.setSupplyZone(supplyZone);
        // Add required entity
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyShop.setSupplyArea(supplyArea);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyShop.setSupplyZoneManager(supplyZoneManager);
        // Add required entity
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyShop.setSupplyAreaManager(supplyAreaManager);
        // Add required entity
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyShop.setSupplySalesRepresentative(supplySalesRepresentative);
        return supplyShop;
    }

    @Before
    public void initTest() {
        supplyShop = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyShop() throws Exception {
        int databaseSizeBeforeCreate = supplyShopRepository.findAll().size();

        // Create the SupplyShop
        SupplyShopDTO supplyShopDTO = supplyShopMapper.toDto(supplyShop);
        restSupplyShopMockMvc.perform(post("/api/supply-shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyShopDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyShop in the database
        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyShop testSupplyShop = supplyShopList.get(supplyShopList.size() - 1);
        assertThat(testSupplyShop.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplyShop.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testSupplyShop.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupplyShop.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSupplyShop.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
        assertThat(testSupplyShop.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyShop.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyShop.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyShop.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyShop in Elasticsearch
        verify(mockSupplyShopSearchRepository, times(1)).save(testSupplyShop);
    }

    @Test
    @Transactional
    public void createSupplyShopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyShopRepository.findAll().size();

        // Create the SupplyShop with an existing ID
        supplyShop.setId(1L);
        SupplyShopDTO supplyShopDTO = supplyShopMapper.toDto(supplyShop);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyShopMockMvc.perform(post("/api/supply-shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyShopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyShop in the database
        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyShop in Elasticsearch
        verify(mockSupplyShopSearchRepository, times(0)).save(supplyShop);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyShopRepository.findAll().size();
        // set the field null
        supplyShop.setName(null);

        // Create the SupplyShop, which fails.
        SupplyShopDTO supplyShopDTO = supplyShopMapper.toDto(supplyShop);

        restSupplyShopMockMvc.perform(post("/api/supply-shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyShopDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyShops() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList
        restSupplyShopMockMvc.perform(get("/api/supply-shops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyShop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyShop() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get the supplyShop
        restSupplyShopMockMvc.perform(get("/api/supply-shops/{id}", supplyShop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyShop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where name equals to DEFAULT_NAME
        defaultSupplyShopShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplyShopList where name equals to UPDATED_NAME
        defaultSupplyShopShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplyShopShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplyShopList where name equals to UPDATED_NAME
        defaultSupplyShopShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where name is not null
        defaultSupplyShopShouldBeFound("name.specified=true");

        // Get all the supplyShopList where name is null
        defaultSupplyShopShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where contact equals to DEFAULT_CONTACT
        defaultSupplyShopShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the supplyShopList where contact equals to UPDATED_CONTACT
        defaultSupplyShopShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultSupplyShopShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the supplyShopList where contact equals to UPDATED_CONTACT
        defaultSupplyShopShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where contact is not null
        defaultSupplyShopShouldBeFound("contact.specified=true");

        // Get all the supplyShopList where contact is null
        defaultSupplyShopShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where email equals to DEFAULT_EMAIL
        defaultSupplyShopShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the supplyShopList where email equals to UPDATED_EMAIL
        defaultSupplyShopShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSupplyShopShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the supplyShopList where email equals to UPDATED_EMAIL
        defaultSupplyShopShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where email is not null
        defaultSupplyShopShouldBeFound("email.specified=true");

        // Get all the supplyShopList where email is null
        defaultSupplyShopShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where address equals to DEFAULT_ADDRESS
        defaultSupplyShopShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the supplyShopList where address equals to UPDATED_ADDRESS
        defaultSupplyShopShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultSupplyShopShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the supplyShopList where address equals to UPDATED_ADDRESS
        defaultSupplyShopShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where address is not null
        defaultSupplyShopShouldBeFound("address.specified=true");

        // Get all the supplyShopList where address is null
        defaultSupplyShopShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAdditionalInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where additionalInformation equals to DEFAULT_ADDITIONAL_INFORMATION
        defaultSupplyShopShouldBeFound("additionalInformation.equals=" + DEFAULT_ADDITIONAL_INFORMATION);

        // Get all the supplyShopList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultSupplyShopShouldNotBeFound("additionalInformation.equals=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAdditionalInformationIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where additionalInformation in DEFAULT_ADDITIONAL_INFORMATION or UPDATED_ADDITIONAL_INFORMATION
        defaultSupplyShopShouldBeFound("additionalInformation.in=" + DEFAULT_ADDITIONAL_INFORMATION + "," + UPDATED_ADDITIONAL_INFORMATION);

        // Get all the supplyShopList where additionalInformation equals to UPDATED_ADDITIONAL_INFORMATION
        defaultSupplyShopShouldNotBeFound("additionalInformation.in=" + UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByAdditionalInformationIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where additionalInformation is not null
        defaultSupplyShopShouldBeFound("additionalInformation.specified=true");

        // Get all the supplyShopList where additionalInformation is null
        defaultSupplyShopShouldNotBeFound("additionalInformation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyShopShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyShopList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyShopShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyShopShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyShopList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyShopShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdBy is not null
        defaultSupplyShopShouldBeFound("createdBy.specified=true");

        // Get all the supplyShopList where createdBy is null
        defaultSupplyShopShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyShopShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyShopList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyShopShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyShopShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyShopList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyShopShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where createdOn is not null
        defaultSupplyShopShouldBeFound("createdOn.specified=true");

        // Get all the supplyShopList where createdOn is null
        defaultSupplyShopShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyShopShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyShopList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyShopShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyShopShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyShopList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyShopShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedBy is not null
        defaultSupplyShopShouldBeFound("updatedBy.specified=true");

        // Get all the supplyShopList where updatedBy is null
        defaultSupplyShopShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyShopShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyShopList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyShopShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyShopShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyShopList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyShopShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyShopsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        // Get all the supplyShopList where updatedOn is not null
        defaultSupplyShopShouldBeFound("updatedOn.specified=true");

        // Get all the supplyShopList where updatedOn is null
        defaultSupplyShopShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyShopsBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyShop.setSupplyZone(supplyZone);
        supplyShopRepository.saveAndFlush(supplyShop);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyShopList where supplyZone equals to supplyZoneId
        defaultSupplyShopShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyShopList where supplyZone equals to supplyZoneId + 1
        defaultSupplyShopShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyShopsBySupplyAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyArea supplyArea = SupplyAreaResourceIntTest.createEntity(em);
        em.persist(supplyArea);
        em.flush();
        supplyShop.setSupplyArea(supplyArea);
        supplyShopRepository.saveAndFlush(supplyShop);
        Long supplyAreaId = supplyArea.getId();

        // Get all the supplyShopList where supplyArea equals to supplyAreaId
        defaultSupplyShopShouldBeFound("supplyAreaId.equals=" + supplyAreaId);

        // Get all the supplyShopList where supplyArea equals to supplyAreaId + 1
        defaultSupplyShopShouldNotBeFound("supplyAreaId.equals=" + (supplyAreaId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyShopsBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyShop.setSupplyZoneManager(supplyZoneManager);
        supplyShopRepository.saveAndFlush(supplyShop);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyShopList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyShopShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyShopList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyShopShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyShopsBySupplyAreaManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyAreaManager supplyAreaManager = SupplyAreaManagerResourceIntTest.createEntity(em);
        em.persist(supplyAreaManager);
        em.flush();
        supplyShop.setSupplyAreaManager(supplyAreaManager);
        supplyShopRepository.saveAndFlush(supplyShop);
        Long supplyAreaManagerId = supplyAreaManager.getId();

        // Get all the supplyShopList where supplyAreaManager equals to supplyAreaManagerId
        defaultSupplyShopShouldBeFound("supplyAreaManagerId.equals=" + supplyAreaManagerId);

        // Get all the supplyShopList where supplyAreaManager equals to supplyAreaManagerId + 1
        defaultSupplyShopShouldNotBeFound("supplyAreaManagerId.equals=" + (supplyAreaManagerId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyShopsBySupplySalesRepresentativeIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplySalesRepresentative supplySalesRepresentative = SupplySalesRepresentativeResourceIntTest.createEntity(em);
        em.persist(supplySalesRepresentative);
        em.flush();
        supplyShop.setSupplySalesRepresentative(supplySalesRepresentative);
        supplyShopRepository.saveAndFlush(supplyShop);
        Long supplySalesRepresentativeId = supplySalesRepresentative.getId();

        // Get all the supplyShopList where supplySalesRepresentative equals to supplySalesRepresentativeId
        defaultSupplyShopShouldBeFound("supplySalesRepresentativeId.equals=" + supplySalesRepresentativeId);

        // Get all the supplyShopList where supplySalesRepresentative equals to supplySalesRepresentativeId + 1
        defaultSupplyShopShouldNotBeFound("supplySalesRepresentativeId.equals=" + (supplySalesRepresentativeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyShopShouldBeFound(String filter) throws Exception {
        restSupplyShopMockMvc.perform(get("/api/supply-shops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyShop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyShopMockMvc.perform(get("/api/supply-shops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyShopShouldNotBeFound(String filter) throws Exception {
        restSupplyShopMockMvc.perform(get("/api/supply-shops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyShopMockMvc.perform(get("/api/supply-shops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyShop() throws Exception {
        // Get the supplyShop
        restSupplyShopMockMvc.perform(get("/api/supply-shops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyShop() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        int databaseSizeBeforeUpdate = supplyShopRepository.findAll().size();

        // Update the supplyShop
        SupplyShop updatedSupplyShop = supplyShopRepository.findById(supplyShop.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyShop are not directly saved in db
        em.detach(updatedSupplyShop);
        updatedSupplyShop
            .name(UPDATED_NAME)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyShopDTO supplyShopDTO = supplyShopMapper.toDto(updatedSupplyShop);

        restSupplyShopMockMvc.perform(put("/api/supply-shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyShopDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyShop in the database
        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeUpdate);
        SupplyShop testSupplyShop = supplyShopList.get(supplyShopList.size() - 1);
        assertThat(testSupplyShop.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplyShop.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSupplyShop.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupplyShop.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSupplyShop.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
        assertThat(testSupplyShop.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyShop.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyShop.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyShop.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyShop in Elasticsearch
        verify(mockSupplyShopSearchRepository, times(1)).save(testSupplyShop);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyShop() throws Exception {
        int databaseSizeBeforeUpdate = supplyShopRepository.findAll().size();

        // Create the SupplyShop
        SupplyShopDTO supplyShopDTO = supplyShopMapper.toDto(supplyShop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyShopMockMvc.perform(put("/api/supply-shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyShopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyShop in the database
        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyShop in Elasticsearch
        verify(mockSupplyShopSearchRepository, times(0)).save(supplyShop);
    }

    @Test
    @Transactional
    public void deleteSupplyShop() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);

        int databaseSizeBeforeDelete = supplyShopRepository.findAll().size();

        // Delete the supplyShop
        restSupplyShopMockMvc.perform(delete("/api/supply-shops/{id}", supplyShop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyShop> supplyShopList = supplyShopRepository.findAll();
        assertThat(supplyShopList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyShop in Elasticsearch
        verify(mockSupplyShopSearchRepository, times(1)).deleteById(supplyShop.getId());
    }

    @Test
    @Transactional
    public void searchSupplyShop() throws Exception {
        // Initialize the database
        supplyShopRepository.saveAndFlush(supplyShop);
        when(mockSupplyShopSearchRepository.search(queryStringQuery("id:" + supplyShop.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyShop), PageRequest.of(0, 1), 1));
        // Search the supplyShop
        restSupplyShopMockMvc.perform(get("/api/_search/supply-shops?query=id:" + supplyShop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyShop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyShop.class);
        SupplyShop supplyShop1 = new SupplyShop();
        supplyShop1.setId(1L);
        SupplyShop supplyShop2 = new SupplyShop();
        supplyShop2.setId(supplyShop1.getId());
        assertThat(supplyShop1).isEqualTo(supplyShop2);
        supplyShop2.setId(2L);
        assertThat(supplyShop1).isNotEqualTo(supplyShop2);
        supplyShop1.setId(null);
        assertThat(supplyShop1).isNotEqualTo(supplyShop2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyShopDTO.class);
        SupplyShopDTO supplyShopDTO1 = new SupplyShopDTO();
        supplyShopDTO1.setId(1L);
        SupplyShopDTO supplyShopDTO2 = new SupplyShopDTO();
        assertThat(supplyShopDTO1).isNotEqualTo(supplyShopDTO2);
        supplyShopDTO2.setId(supplyShopDTO1.getId());
        assertThat(supplyShopDTO1).isEqualTo(supplyShopDTO2);
        supplyShopDTO2.setId(2L);
        assertThat(supplyShopDTO1).isNotEqualTo(supplyShopDTO2);
        supplyShopDTO1.setId(null);
        assertThat(supplyShopDTO1).isNotEqualTo(supplyShopDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyShopMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyShopMapper.fromId(null)).isNull();
    }
}
