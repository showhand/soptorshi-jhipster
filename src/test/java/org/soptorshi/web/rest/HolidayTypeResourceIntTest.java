package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.HolidayType;
import org.soptorshi.domain.enumeration.YesOrNo;
import org.soptorshi.repository.HolidayTypeRepository;
import org.soptorshi.repository.search.HolidayTypeSearchRepository;
import org.soptorshi.service.HolidayTypeQueryService;
import org.soptorshi.service.HolidayTypeService;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.mapper.HolidayTypeMapper;
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
 * Test class for the HolidayTypeResource REST controller.
 *
 * @see HolidayTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class HolidayTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final YesOrNo DEFAULT_MOON_DEPENDENCY = YesOrNo.YES;
    private static final YesOrNo UPDATED_MOON_DEPENDENCY = YesOrNo.NO;

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
            .moonDependency(DEFAULT_MOON_DEPENDENCY);
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
        assertThat(testHolidayType.getMoonDependency()).isEqualTo(DEFAULT_MOON_DEPENDENCY);

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
    public void checkMoonDependencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = holidayTypeRepository.findAll().size();
        // set the field null
        holidayType.setMoonDependency(null);

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
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())));
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
            .andExpect(jsonPath("$.moonDependency").value(DEFAULT_MOON_DEPENDENCY.toString()));
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
    public void getAllHolidayTypesByMoonDependencyIsEqualToSomething() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where moonDependency equals to DEFAULT_MOON_DEPENDENCY
        defaultHolidayTypeShouldBeFound("moonDependency.equals=" + DEFAULT_MOON_DEPENDENCY);

        // Get all the holidayTypeList where moonDependency equals to UPDATED_MOON_DEPENDENCY
        defaultHolidayTypeShouldNotBeFound("moonDependency.equals=" + UPDATED_MOON_DEPENDENCY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByMoonDependencyIsInShouldWork() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where moonDependency in DEFAULT_MOON_DEPENDENCY or UPDATED_MOON_DEPENDENCY
        defaultHolidayTypeShouldBeFound("moonDependency.in=" + DEFAULT_MOON_DEPENDENCY + "," + UPDATED_MOON_DEPENDENCY);

        // Get all the holidayTypeList where moonDependency equals to UPDATED_MOON_DEPENDENCY
        defaultHolidayTypeShouldNotBeFound("moonDependency.in=" + UPDATED_MOON_DEPENDENCY);
    }

    @Test
    @Transactional
    public void getAllHolidayTypesByMoonDependencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        holidayTypeRepository.saveAndFlush(holidayType);

        // Get all the holidayTypeList where moonDependency is not null
        defaultHolidayTypeShouldBeFound("moonDependency.specified=true");

        // Get all the holidayTypeList where moonDependency is null
        defaultHolidayTypeShouldNotBeFound("moonDependency.specified=false");
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
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())));

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
            .moonDependency(UPDATED_MOON_DEPENDENCY);
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
        assertThat(testHolidayType.getMoonDependency()).isEqualTo(UPDATED_MOON_DEPENDENCY);

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
            .andExpect(jsonPath("$.[*].moonDependency").value(hasItem(DEFAULT_MOON_DEPENDENCY.toString())));
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
