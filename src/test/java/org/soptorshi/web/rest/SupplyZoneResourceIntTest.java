package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.repository.SupplyZoneRepository;
import org.soptorshi.repository.search.SupplyZoneSearchRepository;
import org.soptorshi.service.SupplyZoneQueryService;
import org.soptorshi.service.SupplyZoneService;
import org.soptorshi.service.dto.SupplyZoneDTO;
import org.soptorshi.service.mapper.SupplyZoneMapper;
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
 * Test class for the SupplyZoneResource REST controller.
 *
 * @see SupplyZoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyZoneResourceIntTest {

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
    private SupplyZoneRepository supplyZoneRepository;

    @Autowired
    private SupplyZoneMapper supplyZoneMapper;

    @Autowired
    private SupplyZoneService supplyZoneService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyZoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyZoneSearchRepository mockSupplyZoneSearchRepository;

    @Autowired
    private SupplyZoneQueryService supplyZoneQueryService;

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

    private MockMvc restSupplyZoneMockMvc;

    private SupplyZone supplyZone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyZoneResource supplyZoneResource = new SupplyZoneResource(supplyZoneService, supplyZoneQueryService);
        this.restSupplyZoneMockMvc = MockMvcBuilders.standaloneSetup(supplyZoneResource)
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
    public static SupplyZone createEntity(EntityManager em) {
        SupplyZone supplyZone = new SupplyZone()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return supplyZone;
    }

    @Before
    public void initTest() {
        supplyZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyZone() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneRepository.findAll().size();

        // Create the SupplyZone
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(supplyZone);
        restSupplyZoneMockMvc.perform(post("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyZone in the database
        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyZone testSupplyZone = supplyZoneList.get(supplyZoneList.size() - 1);
        assertThat(testSupplyZone.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplyZone.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSupplyZone.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyZone.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyZone.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyZone.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the SupplyZone in Elasticsearch
        verify(mockSupplyZoneSearchRepository, times(1)).save(testSupplyZone);
    }

    @Test
    @Transactional
    public void createSupplyZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneRepository.findAll().size();

        // Create the SupplyZone with an existing ID
        supplyZone.setId(1L);
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(supplyZone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyZoneMockMvc.perform(post("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZone in the database
        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyZone in Elasticsearch
        verify(mockSupplyZoneSearchRepository, times(0)).save(supplyZone);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneRepository.findAll().size();
        // set the field null
        supplyZone.setName(null);

        // Create the SupplyZone, which fails.
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(supplyZone);

        restSupplyZoneMockMvc.perform(post("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneRepository.findAll().size();
        // set the field null
        supplyZone.setCode(null);

        // Create the SupplyZone, which fails.
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(supplyZone);

        restSupplyZoneMockMvc.perform(post("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyZones() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList
        restSupplyZoneMockMvc.perform(get("/api/supply-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getSupplyZone() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get the supplyZone
        restSupplyZoneMockMvc.perform(get("/api/supply-zones/{id}", supplyZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyZone.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where name equals to DEFAULT_NAME
        defaultSupplyZoneShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplyZoneList where name equals to UPDATED_NAME
        defaultSupplyZoneShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplyZoneShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplyZoneList where name equals to UPDATED_NAME
        defaultSupplyZoneShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where name is not null
        defaultSupplyZoneShouldBeFound("name.specified=true");

        // Get all the supplyZoneList where name is null
        defaultSupplyZoneShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where code equals to DEFAULT_CODE
        defaultSupplyZoneShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the supplyZoneList where code equals to UPDATED_CODE
        defaultSupplyZoneShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSupplyZoneShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the supplyZoneList where code equals to UPDATED_CODE
        defaultSupplyZoneShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where code is not null
        defaultSupplyZoneShouldBeFound("code.specified=true");

        // Get all the supplyZoneList where code is null
        defaultSupplyZoneShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdBy equals to DEFAULT_CREATED_BY
        defaultSupplyZoneShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the supplyZoneList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSupplyZoneShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the supplyZoneList where createdBy equals to UPDATED_CREATED_BY
        defaultSupplyZoneShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdBy is not null
        defaultSupplyZoneShouldBeFound("createdBy.specified=true");

        // Get all the supplyZoneList where createdBy is null
        defaultSupplyZoneShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdOn equals to DEFAULT_CREATED_ON
        defaultSupplyZoneShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the supplyZoneList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSupplyZoneShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the supplyZoneList where createdOn equals to UPDATED_CREATED_ON
        defaultSupplyZoneShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where createdOn is not null
        defaultSupplyZoneShouldBeFound("createdOn.specified=true");

        // Get all the supplyZoneList where createdOn is null
        defaultSupplyZoneShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultSupplyZoneShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the supplyZoneList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultSupplyZoneShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the supplyZoneList where updatedBy equals to UPDATED_UPDATED_BY
        defaultSupplyZoneShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedBy is not null
        defaultSupplyZoneShouldBeFound("updatedBy.specified=true");

        // Get all the supplyZoneList where updatedBy is null
        defaultSupplyZoneShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultSupplyZoneShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the supplyZoneList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultSupplyZoneShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the supplyZoneList where updatedOn equals to UPDATED_UPDATED_ON
        defaultSupplyZoneShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllSupplyZonesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        // Get all the supplyZoneList where updatedOn is not null
        defaultSupplyZoneShouldBeFound("updatedOn.specified=true");

        // Get all the supplyZoneList where updatedOn is null
        defaultSupplyZoneShouldNotBeFound("updatedOn.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSupplyZoneShouldBeFound(String filter) throws Exception {
        restSupplyZoneMockMvc.perform(get("/api/supply-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restSupplyZoneMockMvc.perform(get("/api/supply-zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSupplyZoneShouldNotBeFound(String filter) throws Exception {
        restSupplyZoneMockMvc.perform(get("/api/supply-zones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplyZoneMockMvc.perform(get("/api/supply-zones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplyZone() throws Exception {
        // Get the supplyZone
        restSupplyZoneMockMvc.perform(get("/api/supply-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyZone() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        int databaseSizeBeforeUpdate = supplyZoneRepository.findAll().size();

        // Update the supplyZone
        SupplyZone updatedSupplyZone = supplyZoneRepository.findById(supplyZone.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyZone are not directly saved in db
        em.detach(updatedSupplyZone);
        updatedSupplyZone
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(updatedSupplyZone);

        restSupplyZoneMockMvc.perform(put("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyZone in the database
        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeUpdate);
        SupplyZone testSupplyZone = supplyZoneList.get(supplyZoneList.size() - 1);
        assertThat(testSupplyZone.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplyZone.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSupplyZone.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyZone.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyZone.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyZone.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the SupplyZone in Elasticsearch
        verify(mockSupplyZoneSearchRepository, times(1)).save(testSupplyZone);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyZone() throws Exception {
        int databaseSizeBeforeUpdate = supplyZoneRepository.findAll().size();

        // Create the SupplyZone
        SupplyZoneDTO supplyZoneDTO = supplyZoneMapper.toDto(supplyZone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyZoneMockMvc.perform(put("/api/supply-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZone in the database
        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyZone in Elasticsearch
        verify(mockSupplyZoneSearchRepository, times(0)).save(supplyZone);
    }

    @Test
    @Transactional
    public void deleteSupplyZone() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);

        int databaseSizeBeforeDelete = supplyZoneRepository.findAll().size();

        // Delete the supplyZone
        restSupplyZoneMockMvc.perform(delete("/api/supply-zones/{id}", supplyZone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyZone> supplyZoneList = supplyZoneRepository.findAll();
        assertThat(supplyZoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyZone in Elasticsearch
        verify(mockSupplyZoneSearchRepository, times(1)).deleteById(supplyZone.getId());
    }

    @Test
    @Transactional
    public void searchSupplyZone() throws Exception {
        // Initialize the database
        supplyZoneRepository.saveAndFlush(supplyZone);
        when(mockSupplyZoneSearchRepository.search(queryStringQuery("id:" + supplyZone.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(supplyZone), PageRequest.of(0, 1), 1));
        // Search the supplyZone
        restSupplyZoneMockMvc.perform(get("/api/_search/supply-zones?query=id:" + supplyZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZone.getId().intValue())))
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
        TestUtil.equalsVerifier(SupplyZone.class);
        SupplyZone supplyZone1 = new SupplyZone();
        supplyZone1.setId(1L);
        SupplyZone supplyZone2 = new SupplyZone();
        supplyZone2.setId(supplyZone1.getId());
        assertThat(supplyZone1).isEqualTo(supplyZone2);
        supplyZone2.setId(2L);
        assertThat(supplyZone1).isNotEqualTo(supplyZone2);
        supplyZone1.setId(null);
        assertThat(supplyZone1).isNotEqualTo(supplyZone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneDTO.class);
        SupplyZoneDTO supplyZoneDTO1 = new SupplyZoneDTO();
        supplyZoneDTO1.setId(1L);
        SupplyZoneDTO supplyZoneDTO2 = new SupplyZoneDTO();
        assertThat(supplyZoneDTO1).isNotEqualTo(supplyZoneDTO2);
        supplyZoneDTO2.setId(supplyZoneDTO1.getId());
        assertThat(supplyZoneDTO1).isEqualTo(supplyZoneDTO2);
        supplyZoneDTO2.setId(2L);
        assertThat(supplyZoneDTO1).isNotEqualTo(supplyZoneDTO2);
        supplyZoneDTO1.setId(null);
        assertThat(supplyZoneDTO1).isNotEqualTo(supplyZoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyZoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyZoneMapper.fromId(null)).isNull();
    }
}
