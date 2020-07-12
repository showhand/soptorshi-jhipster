package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.repository.SupplyAreaRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.service.SupplyAreaQueryService;
import org.soptorshi.service.SupplyAreaService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.mapper.SupplyAreaMapper;
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
 * Test class for the SupplyAreaResource REST controller.
 *
 * @see SupplyAreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyAreaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplyAreaRepository supplyAreaRepository;

    @Autowired
    private SupplyAreaMapper supplyAreaMapper;

    @Autowired
    private SupplyAreaService supplyAreaService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyAreaSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyAreaSearchRepository mockSupplyAreaSearchRepository;

    @Autowired
    private SupplyAreaQueryService supplyAreaQueryService;

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

    private MockMvc restSupplyAreaMockMvc;

    private SupplyArea supplyArea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyAreaResource supplyAreaResource = new SupplyAreaResource(supplyAreaService, supplyAreaQueryService);
        this.restSupplyAreaMockMvc = MockMvcBuilders.standaloneSetup(supplyAreaResource)
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
    public static SupplyArea createEntity(EntityManager em) {
        SupplyArea supplyArea = new SupplyArea()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyArea.setSupplyZone(supplyZone);
        // Add required entity
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyArea.setSupplyZoneManager(supplyZoneManager);
        return supplyArea;
    }

    @Before
    public void initTest() {
        supplyArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyArea() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaRepository.findAll().size();

        // Create the SupplyArea
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(supplyArea);
        restSupplyAreaMockMvc.perform(post("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyArea in the database
        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyArea testSupplyArea = supplyAreaList.get(supplyAreaList.size() - 1);
        assertThat(testSupplyArea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplyArea.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSupplyArea.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyArea.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyArea.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyArea.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyArea in Elasticsearch
        verify(mockSupplyAreaSearchRepository, times(1)).save(testSupplyArea);
    }

    @Test
    @Transactional
    public void createSupplyAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyAreaRepository.findAll().size();

        // Create the SupplyArea with an existing ID
        supplyArea.setId(1L);
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(supplyArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyAreaMockMvc.perform(post("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyArea in the database
        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyArea in Elasticsearch
        verify(mockSupplyAreaSearchRepository, times(0)).save(supplyArea);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaRepository.findAll().size();
        // set the field null
        supplyArea.setName(null);

        // Create the SupplyArea, which fails.
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(supplyArea);

        restSupplyAreaMockMvc.perform(post("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyAreaRepository.findAll().size();
        // set the field null
        supplyArea.setCode(null);

        // Create the SupplyArea, which fails.
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(supplyArea);

        restSupplyAreaMockMvc.perform(post("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyAreas() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList
        restSupplyAreaMockMvc.perform(get("/api/supply-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyArea() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get the supplyArea
        restSupplyAreaMockMvc.perform(get("/api/supply-areas/{id}", supplyArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyArea.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where name equals to DEFAULT_NAME
        defaultSupplyAreaShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplyAreaList where name equals to UPDATED_NAME
        defaultSupplyAreaShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplyAreaShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplyAreaList where name equals to UPDATED_NAME
        defaultSupplyAreaShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where name is not null
        defaultSupplyAreaShouldBeFound("name.specified=true");

        // Get all the supplyAreaList where name is null
        defaultSupplyAreaShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where code equals to DEFAULT_CODE
        defaultSupplyAreaShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the supplyAreaList where code equals to UPDATED_CODE
        defaultSupplyAreaShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSupplyAreaShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the supplyAreaList where code equals to UPDATED_CODE
        defaultSupplyAreaShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where code is not null
        defaultSupplyAreaShouldBeFound("code.specified=true");

        // Get all the supplyAreaList where code is null
        defaultSupplyAreaShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyAreaShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyAreaList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyAreaShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyAreaList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyAreaShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdBy is not null
        defaultSupplyAreaShouldBeFound("createdBy.specified=true");

        // Get all the supplyAreaList where createdBy is null
        defaultSupplyAreaShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyAreaShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyAreaList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyAreaShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyAreaList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyAreaShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where createdOn is not null
        defaultSupplyAreaShouldBeFound("createdOn.specified=true");

        // Get all the supplyAreaList where createdOn is null
        defaultSupplyAreaShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyAreaShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyAreaList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyAreaShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyAreaList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyAreaShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedBy is not null
        defaultSupplyAreaShouldBeFound("updatedBy.specified=true");

        // Get all the supplyAreaList where updatedBy is null
        defaultSupplyAreaShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyAreaShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyAreaList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyAreaShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyAreaList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyAreaShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyAreasByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        // Get all the supplyAreaList where updatedOn is not null
        defaultSupplyAreaShouldBeFound("updatedOn.specified=true");

        // Get all the supplyAreaList where updatedOn is null
        defaultSupplyAreaShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyAreasBySupplyZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyArea.setSupplyZone(supplyZone);
        supplyAreaRepository.saveAndFlush(supplyArea);
        Long supplyZoneId = supplyZone.getId();

        // Get all the supplyAreaList where supplyZone equals to supplyZoneId
        defaultSupplyAreaShouldBeFound("supplyZoneId.equals=" + supplyZoneId);

        // Get all the supplyAreaList where supplyZone equals to supplyZoneId + 1
        defaultSupplyAreaShouldNotBeFound("supplyZoneId.equals=" + (supplyZoneId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplyAreasBySupplyZoneManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        SupplyZoneManager supplyZoneManager = SupplyZoneManagerResourceIntTest.createEntity(em);
        em.persist(supplyZoneManager);
        em.flush();
        supplyArea.setSupplyZoneManager(supplyZoneManager);
        supplyAreaRepository.saveAndFlush(supplyArea);
        Long supplyZoneManagerId = supplyZoneManager.getId();

        // Get all the supplyAreaList where supplyZoneManager equals to supplyZoneManagerId
        defaultSupplyAreaShouldBeFound("supplyZoneManagerId.equals=" + supplyZoneManagerId);

        // Get all the supplyAreaList where supplyZoneManager equals to supplyZoneManagerId + 1
        defaultSupplyAreaShouldNotBeFound("supplyZoneManagerId.equals=" + (supplyZoneManagerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyAreaShouldBeFound(String filter) throws Exception {
        restSupplyAreaMockMvc.perform(get("/api/supply-areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyAreaMockMvc.perform(get("/api/supply-areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyAreaShouldNotBeFound(String filter) throws Exception {
        restSupplyAreaMockMvc.perform(get("/api/supply-areas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyAreaMockMvc.perform(get("/api/supply-areas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyArea() throws Exception {
        // Get the supplyArea
        restSupplyAreaMockMvc.perform(get("/api/supply-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyArea() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        int databaseSizeBeforeUpdate = supplyAreaRepository.findAll().size();

        // Update the supplyArea
        SupplyArea updatedSupplyArea = supplyAreaRepository.findById(supplyArea.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyArea are not directly saved in db
        em.detach(updatedSupplyArea);
        updatedSupplyArea
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(updatedSupplyArea);

        restSupplyAreaMockMvc.perform(put("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyArea in the database
        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeUpdate);
        SupplyArea testSupplyArea = supplyAreaList.get(supplyAreaList.size() - 1);
        assertThat(testSupplyArea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplyArea.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSupplyArea.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyArea.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyArea.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyArea.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyArea in Elasticsearch
        verify(mockSupplyAreaSearchRepository, times(1)).save(testSupplyArea);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyArea() throws Exception {
        int databaseSizeBeforeUpdate = supplyAreaRepository.findAll().size();

        // Create the SupplyArea
        SupplyAreaDTO supplyAreaDTO = supplyAreaMapper.toDto(supplyArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyAreaMockMvc.perform(put("/api/supply-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyArea in the database
        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyArea in Elasticsearch
        verify(mockSupplyAreaSearchRepository, times(0)).save(supplyArea);
    }

    @Test
    @Transactional
    public void deleteSupplyArea() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);

        int databaseSizeBeforeDelete = supplyAreaRepository.findAll().size();

        // Delete the supplyArea
        restSupplyAreaMockMvc.perform(delete("/api/supply-areas/{id}", supplyArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyArea> supplyAreaList = supplyAreaRepository.findAll();
        assertThat(supplyAreaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyArea in Elasticsearch
        verify(mockSupplyAreaSearchRepository, times(1)).deleteById(supplyArea.getId());
    }

    @Test
    @Transactional
    public void searchSupplyArea() throws Exception {
        // Initialize the database
        supplyAreaRepository.saveAndFlush(supplyArea);
        when(mockSupplyAreaSearchRepository.search(queryStringQuery("id:" + supplyArea.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyArea), PageRequest.of(0, 1), 1));
        // Search the supplyArea
        restSupplyAreaMockMvc.perform(get("/api/_search/supply-areas?query=id:" + supplyArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyArea.class);
        SupplyArea supplyArea1 = new SupplyArea();
        supplyArea1.setId(1L);
        SupplyArea supplyArea2 = new SupplyArea();
        supplyArea2.setId(supplyArea1.getId());
        assertThat(supplyArea1).isEqualTo(supplyArea2);
        supplyArea2.setId(2L);
        assertThat(supplyArea1).isNotEqualTo(supplyArea2);
        supplyArea1.setId(null);
        assertThat(supplyArea1).isNotEqualTo(supplyArea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyAreaDTO.class);
        SupplyAreaDTO supplyAreaDTO1 = new SupplyAreaDTO();
        supplyAreaDTO1.setId(1L);
        SupplyAreaDTO supplyAreaDTO2 = new SupplyAreaDTO();
        assertThat(supplyAreaDTO1).isNotEqualTo(supplyAreaDTO2);
        supplyAreaDTO2.setId(supplyAreaDTO1.getId());
        assertThat(supplyAreaDTO1).isEqualTo(supplyAreaDTO2);
        supplyAreaDTO2.setId(2L);
        assertThat(supplyAreaDTO1).isNotEqualTo(supplyAreaDTO2);
        supplyAreaDTO1.setId(null);
        assertThat(supplyAreaDTO1).isNotEqualTo(supplyAreaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyAreaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyAreaMapper.fromId(null)).isNull();
    }
}
