package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.HolidayType;
import org.soptorshi.repository.HolidayTypeRepository;
import org.soptorshi.repository.search.HolidayTypeSearchRepository;
import org.soptorshi.service.HolidayTypeService;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.mapper.HolidayTypeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.HolidayTypeCriteria;
import org.soptorshi.service.HolidayTypeQueryService;

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

/**
 * Test class for the HolidayTypeResource REST controller.
 *
 * @see HolidayTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class HolidayTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HolidayTypeRepository holidayTypeRepository;

    @Autowired
    private HolidayTypeMapper holidayTypeMapper;

    @Autowired
    private HolidayTypeService holidayTypeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.HolidayTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private HolidayTypeSearchRepository mockHolidayTypeSearchRepository;

    @Autowired
    private HolidayTypeQueryService holidayTypeQueryService;

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

    private MockMvc restHolidayTypeMockMvc;

    private HolidayType holidayType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HolidayTypeResource holidayTypeResource = new HolidayTypeResource(holidayTypeService, holidayTypeQueryService);
        this.restHolidayTypeMockMvc = MockMvcBuilders.standaloneSetup(holidayTypeResource)
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
    public static HolidayType createEntity(EntityManager em) {
        HolidayType holidayType = new HolidayType()
            .name(DEFAULT_NAME)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return holidayType;
    }

    @Before
    public void initTest() {
        holidayType = createEntity(em);
    }

    @Test
    @Transactional
    public void createHolidayType() throws Exception {
        int databaseSizeBeforeCreate = holidayTypeRepository.findAll().size();

        // Create the HolidayType
        HolidayTypeDTO holidayTypeDTO = holidayTypeMapper.toDto(holidayType);
        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeCreate + 1);
        HolidayType testHolidayType = holidayTypeList.get(holidayTypeList.size() - 1);
        assertThat(testHolidayType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHolidayType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testHolidayType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testHolidayType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testHolidayType.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the HolidayType in Elasticsearch
        verify(mockHolidayTypeSearchRepository, times(1)).save(testHolidayType);
    }

    @Test
    @Transactional
    public void createHolidayTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = holidayTypeRepository.findAll().size();

        // Create the HolidayType with an existing ID
        holidayType.setId(1L);
        HolidayTypeDTO holidayTypeDTO = holidayTypeMapper.toDto(holidayType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the HolidayType in Elasticsearch
        verify(mockHolidayTypeSearchRepository, times(0)).save(holidayType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayTypeRepository.findAll().size();
        // set the field null
        holidayType.setName(null);

        // Create the HolidayType, which fails.
        HolidayTypeDTO holidayTypeDTO = holidayTypeMapper.toDto(holidayType);

        restHolidayTypeMockMvc.perform(post("/api/holiday-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayTypeDTO)))
            .andExpect(status().isBadRequest());

        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHolidayTypes() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getHolidayType() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get the holidayType
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/{id}", holidayType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(holidayType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name equals to DEFAULT_NAME
        defaultHolidayTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the holidayTypeList where name equals to UPDATED_NAME
        defaultHolidayTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHolidayTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the holidayTypeList where name equals to UPDATED_NAME
        defaultHolidayTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where name is not null
        defaultHolidayTypeShouldBeFound("name.specified=true");

        // Get all the holidayTypeList where name is null
        defaultHolidayTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultHolidayTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the holidayTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidayTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultHolidayTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the holidayTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultHolidayTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdBy is not null
        defaultHolidayTypeShouldBeFound("createdBy.specified=true");

        // Get all the holidayTypeList where createdBy is null
        defaultHolidayTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdOn equals to DEFAULT_CREATED_ON
        defaultHolidayTypeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the holidayTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidayTypeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultHolidayTypeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the holidayTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultHolidayTypeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where createdOn is not null
        defaultHolidayTypeShouldBeFound("createdOn.specified=true");

        // Get all the holidayTypeList where createdOn is null
        defaultHolidayTypeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultHolidayTypeShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the holidayTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultHolidayTypeShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultHolidayTypeShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the holidayTypeList where updatedBy equals to UPDATED_UPDATED_BY
        defaultHolidayTypeShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedBy is not null
        defaultHolidayTypeShouldBeFound("updatedBy.specified=true");

        // Get all the holidayTypeList where updatedBy is null
        defaultHolidayTypeShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultHolidayTypeShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the holidayTypeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultHolidayTypeShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultHolidayTypeShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the holidayTypeList where updatedOn equals to UPDATED_UPDATED_ON
        defaultHolidayTypeShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where updatedOn is not null
        defaultHolidayTypeShouldBeFound("updatedOn.specified=true");

        // Get all the holidayTypeList where updatedOn is null
        defaultHolidayTypeShouldNotBeFound("updatedOn.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHolidayTypeShouldBeFound(String filter) throws Exception {
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHolidayTypeShouldNotBeFound(String filter) throws Exception {
        restHolidayTypeMockMvc.perform(get("/api/holiday-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHolidayType() throws Exception {
        // Get the holidayType
        restHolidayTypeMockMvc.perform(get("/api/holiday-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHolidayType() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        int databaseSizeBeforeUpdate = holidayTypeRepository.findAll().size();

        // Update the holidayType
        HolidayType updatedHolidayType = holidayTypeRepository.findById(holidayType.getId()).get();
        // Disconnect from session so that the updates on updatedHolidayType are not directly saved in db
        em.detach(updatedHolidayType);
        updatedHolidayType
            .name(UPDATED_NAME)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        HolidayTypeDTO holidayTypeDTO = holidayTypeMapper.toDto(updatedHolidayType);

        restHolidayTypeMockMvc.perform(put("/api/holiday-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayTypeDTO)))
            .andExpect(status().isOk());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeUpdate);
        HolidayType testHolidayType = holidayTypeList.get(holidayTypeList.size() - 1);
        assertThat(testHolidayType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHolidayType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testHolidayType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testHolidayType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testHolidayType.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the HolidayType in Elasticsearch
        verify(mockHolidayTypeSearchRepository, times(1)).save(testHolidayType);
    }

    @Test
    @Transactional
    public void updateNonExistingHolidayType() throws Exception {
        int databaseSizeBeforeUpdate = holidayTypeRepository.findAll().size();

        // Create the HolidayType
        HolidayTypeDTO holidayTypeDTO = holidayTypeMapper.toDto(holidayType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHolidayTypeMockMvc.perform(put("/api/holiday-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(holidayTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HolidayType in the database
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the HolidayType in Elasticsearch
        verify(mockHolidayTypeSearchRepository, times(0)).save(holidayType);
    }

    @Test
    @Transactional
    public void deleteHolidayType() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        int databaseSizeBeforeDelete = holidayTypeRepository.findAll().size();

        // Delete the holidayType
        restHolidayTypeMockMvc.perform(delete("/api/holiday-types/{id}", holidayType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HolidayType> holidayTypeList = holidayTypeRepository.findAll();
        assertThat(holidayTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the HolidayType in Elasticsearch
        verify(mockHolidayTypeSearchRepository, times(1)).deleteById(holidayType.getId());
    }

    @Test
    @Transactional
    public void searchHolidayType() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);
        when(mockHolidayTypeSearchRepository.search(queryStringQuery("id:" + holidayType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(holidayType), PageRequest.of(0, 1), 1));
        // Search the holidayType
        restHolidayTypeMockMvc.perform(get("/api/_search/holiday-types?query=id:" + holidayType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(holidayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HolidayType.class);
        HolidayType holidayType1 = new HolidayType();
        holidayType1.setId(1L);
        HolidayType holidayType2 = new HolidayType();
        holidayType2.setId(holidayType1.getId());
        assertThat(holidayType1).isEqualTo(holidayType2);
        holidayType2.setId(2L);
        assertThat(holidayType1).isNotEqualTo(holidayType2);
        holidayType1.setId(null);
        assertThat(holidayType1).isNotEqualTo(holidayType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HolidayTypeDTO.class);
        HolidayTypeDTO holidayTypeDTO1 = new HolidayTypeDTO();
        holidayTypeDTO1.setId(1L);
        HolidayTypeDTO holidayTypeDTO2 = new HolidayTypeDTO();
        assertThat(holidayTypeDTO1).isNotEqualTo(holidayTypeDTO2);
        holidayTypeDTO2.setId(holidayTypeDTO1.getId());
        assertThat(holidayTypeDTO1).isEqualTo(holidayTypeDTO2);
        holidayTypeDTO2.setId(2L);
        assertThat(holidayTypeDTO1).isNotEqualTo(holidayTypeDTO2);
        holidayTypeDTO1.setId(null);
        assertThat(holidayTypeDTO1).isNotEqualTo(holidayTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(holidayTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(holidayTypeMapper.fromId(null)).isNull();
    }
}
