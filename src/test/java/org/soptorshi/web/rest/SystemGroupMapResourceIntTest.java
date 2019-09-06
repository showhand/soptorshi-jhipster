package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.search.SystemGroupMapSearchRepository;
import org.soptorshi.service.SystemGroupMapService;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.mapper.SystemGroupMapMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SystemGroupMapCriteria;
import org.soptorshi.service.SystemGroupMapQueryService;

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

import org.soptorshi.domain.enumeration.GroupType;
/**
 * Test class for the SystemGroupMapResource REST controller.
 *
 * @see SystemGroupMapResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SystemGroupMapResourceIntTest {

    private static final GroupType DEFAULT_GROUP_TYPE = GroupType.ASSETS;
    private static final GroupType UPDATED_GROUP_TYPE = GroupType.LIABILITIES;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SystemGroupMapRepository systemGroupMapRepository;

    @Autowired
    private SystemGroupMapMapper systemGroupMapMapper;

    @Autowired
    private SystemGroupMapService systemGroupMapService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SystemGroupMapSearchRepositoryMockConfiguration
     */
    @Autowired
    private SystemGroupMapSearchRepository mockSystemGroupMapSearchRepository;

    @Autowired
    private SystemGroupMapQueryService systemGroupMapQueryService;

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

    private MockMvc restSystemGroupMapMockMvc;

    private SystemGroupMap systemGroupMap;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemGroupMapResource systemGroupMapResource = new SystemGroupMapResource(systemGroupMapService, systemGroupMapQueryService);
        this.restSystemGroupMapMockMvc = MockMvcBuilders.standaloneSetup(systemGroupMapResource)
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
    public static SystemGroupMap createEntity(EntityManager em) {
        SystemGroupMap systemGroupMap = new SystemGroupMap()
            .groupType(DEFAULT_GROUP_TYPE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return systemGroupMap;
    }

    @Before
    public void initTest() {
        systemGroupMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemGroupMap() throws Exception {
        int databaseSizeBeforeCreate = systemGroupMapRepository.findAll().size();

        // Create the SystemGroupMap
        SystemGroupMapDTO systemGroupMapDTO = systemGroupMapMapper.toDto(systemGroupMap);
        restSystemGroupMapMockMvc.perform(post("/api/system-group-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemGroupMapDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemGroupMap in the database
        List<SystemGroupMap> systemGroupMapList = systemGroupMapRepository.findAll();
        assertThat(systemGroupMapList).hasSize(databaseSizeBeforeCreate + 1);
        SystemGroupMap testSystemGroupMap = systemGroupMapList.get(systemGroupMapList.size() - 1);
        assertThat(testSystemGroupMap.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testSystemGroupMap.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSystemGroupMap.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the SystemGroupMap in Elasticsearch
        verify(mockSystemGroupMapSearchRepository, times(1)).save(testSystemGroupMap);
    }

    @Test
    @Transactional
    public void createSystemGroupMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemGroupMapRepository.findAll().size();

        // Create the SystemGroupMap with an existing ID
        systemGroupMap.setId(1L);
        SystemGroupMapDTO systemGroupMapDTO = systemGroupMapMapper.toDto(systemGroupMap);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemGroupMapMockMvc.perform(post("/api/system-group-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemGroupMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemGroupMap in the database
        List<SystemGroupMap> systemGroupMapList = systemGroupMapRepository.findAll();
        assertThat(systemGroupMapList).hasSize(databaseSizeBeforeCreate);

        // Validate the SystemGroupMap in Elasticsearch
        verify(mockSystemGroupMapSearchRepository, times(0)).save(systemGroupMap);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMaps() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemGroupMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemGroupMap() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get the systemGroupMap
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps/{id}", systemGroupMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemGroupMap.getId().intValue()))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByGroupTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where groupType equals to DEFAULT_GROUP_TYPE
        defaultSystemGroupMapShouldBeFound("groupType.equals=" + DEFAULT_GROUP_TYPE);

        // Get all the systemGroupMapList where groupType equals to UPDATED_GROUP_TYPE
        defaultSystemGroupMapShouldNotBeFound("groupType.equals=" + UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByGroupTypeIsInShouldWork() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where groupType in DEFAULT_GROUP_TYPE or UPDATED_GROUP_TYPE
        defaultSystemGroupMapShouldBeFound("groupType.in=" + DEFAULT_GROUP_TYPE + "," + UPDATED_GROUP_TYPE);

        // Get all the systemGroupMapList where groupType equals to UPDATED_GROUP_TYPE
        defaultSystemGroupMapShouldNotBeFound("groupType.in=" + UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByGroupTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where groupType is not null
        defaultSystemGroupMapShouldBeFound("groupType.specified=true");

        // Get all the systemGroupMapList where groupType is null
        defaultSystemGroupMapShouldNotBeFound("groupType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSystemGroupMapShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the systemGroupMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSystemGroupMapShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSystemGroupMapShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the systemGroupMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSystemGroupMapShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedBy is not null
        defaultSystemGroupMapShouldBeFound("modifiedBy.specified=true");

        // Get all the systemGroupMapList where modifiedBy is null
        defaultSystemGroupMapShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultSystemGroupMapShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the systemGroupMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSystemGroupMapShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultSystemGroupMapShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the systemGroupMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSystemGroupMapShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedOn is not null
        defaultSystemGroupMapShouldBeFound("modifiedOn.specified=true");

        // Get all the systemGroupMapList where modifiedOn is null
        defaultSystemGroupMapShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultSystemGroupMapShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the systemGroupMapList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultSystemGroupMapShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemGroupMapsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        // Get all the systemGroupMapList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultSystemGroupMapShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the systemGroupMapList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultSystemGroupMapShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllSystemGroupMapsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        MstGroup group = MstGroupResourceIntTest.createEntity(em);
        em.persist(group);
        em.flush();
        systemGroupMap.setGroup(group);
        systemGroupMapRepository.saveAndFlush(systemGroupMap);
        Long groupId = group.getId();

        // Get all the systemGroupMapList where group equals to groupId
        defaultSystemGroupMapShouldBeFound("groupId.equals=" + groupId);

        // Get all the systemGroupMapList where group equals to groupId + 1
        defaultSystemGroupMapShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSystemGroupMapShouldBeFound(String filter) throws Exception {
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemGroupMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSystemGroupMapShouldNotBeFound(String filter) throws Exception {
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSystemGroupMap() throws Exception {
        // Get the systemGroupMap
        restSystemGroupMapMockMvc.perform(get("/api/system-group-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemGroupMap() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        int databaseSizeBeforeUpdate = systemGroupMapRepository.findAll().size();

        // Update the systemGroupMap
        SystemGroupMap updatedSystemGroupMap = systemGroupMapRepository.findById(systemGroupMap.getId()).get();
        // Disconnect from session so that the updates on updatedSystemGroupMap are not directly saved in db
        em.detach(updatedSystemGroupMap);
        updatedSystemGroupMap
            .groupType(UPDATED_GROUP_TYPE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        SystemGroupMapDTO systemGroupMapDTO = systemGroupMapMapper.toDto(updatedSystemGroupMap);

        restSystemGroupMapMockMvc.perform(put("/api/system-group-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemGroupMapDTO)))
            .andExpect(status().isOk());

        // Validate the SystemGroupMap in the database
        List<SystemGroupMap> systemGroupMapList = systemGroupMapRepository.findAll();
        assertThat(systemGroupMapList).hasSize(databaseSizeBeforeUpdate);
        SystemGroupMap testSystemGroupMap = systemGroupMapList.get(systemGroupMapList.size() - 1);
        assertThat(testSystemGroupMap.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testSystemGroupMap.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSystemGroupMap.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the SystemGroupMap in Elasticsearch
        verify(mockSystemGroupMapSearchRepository, times(1)).save(testSystemGroupMap);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemGroupMap() throws Exception {
        int databaseSizeBeforeUpdate = systemGroupMapRepository.findAll().size();

        // Create the SystemGroupMap
        SystemGroupMapDTO systemGroupMapDTO = systemGroupMapMapper.toDto(systemGroupMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemGroupMapMockMvc.perform(put("/api/system-group-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemGroupMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemGroupMap in the database
        List<SystemGroupMap> systemGroupMapList = systemGroupMapRepository.findAll();
        assertThat(systemGroupMapList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SystemGroupMap in Elasticsearch
        verify(mockSystemGroupMapSearchRepository, times(0)).save(systemGroupMap);
    }

    @Test
    @Transactional
    public void deleteSystemGroupMap() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);

        int databaseSizeBeforeDelete = systemGroupMapRepository.findAll().size();

        // Delete the systemGroupMap
        restSystemGroupMapMockMvc.perform(delete("/api/system-group-maps/{id}", systemGroupMap.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SystemGroupMap> systemGroupMapList = systemGroupMapRepository.findAll();
        assertThat(systemGroupMapList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SystemGroupMap in Elasticsearch
        verify(mockSystemGroupMapSearchRepository, times(1)).deleteById(systemGroupMap.getId());
    }

    @Test
    @Transactional
    public void searchSystemGroupMap() throws Exception {
        // Initialize the database
        systemGroupMapRepository.saveAndFlush(systemGroupMap);
        when(mockSystemGroupMapSearchRepository.search(queryStringQuery("id:" + systemGroupMap.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(systemGroupMap), PageRequest.of(0, 1), 1));
        // Search the systemGroupMap
        restSystemGroupMapMockMvc.perform(get("/api/_search/system-group-maps?query=id:" + systemGroupMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemGroupMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemGroupMap.class);
        SystemGroupMap systemGroupMap1 = new SystemGroupMap();
        systemGroupMap1.setId(1L);
        SystemGroupMap systemGroupMap2 = new SystemGroupMap();
        systemGroupMap2.setId(systemGroupMap1.getId());
        assertThat(systemGroupMap1).isEqualTo(systemGroupMap2);
        systemGroupMap2.setId(2L);
        assertThat(systemGroupMap1).isNotEqualTo(systemGroupMap2);
        systemGroupMap1.setId(null);
        assertThat(systemGroupMap1).isNotEqualTo(systemGroupMap2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemGroupMapDTO.class);
        SystemGroupMapDTO systemGroupMapDTO1 = new SystemGroupMapDTO();
        systemGroupMapDTO1.setId(1L);
        SystemGroupMapDTO systemGroupMapDTO2 = new SystemGroupMapDTO();
        assertThat(systemGroupMapDTO1).isNotEqualTo(systemGroupMapDTO2);
        systemGroupMapDTO2.setId(systemGroupMapDTO1.getId());
        assertThat(systemGroupMapDTO1).isEqualTo(systemGroupMapDTO2);
        systemGroupMapDTO2.setId(2L);
        assertThat(systemGroupMapDTO1).isNotEqualTo(systemGroupMapDTO2);
        systemGroupMapDTO1.setId(null);
        assertThat(systemGroupMapDTO1).isNotEqualTo(systemGroupMapDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemGroupMapMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemGroupMapMapper.fromId(null)).isNull();
    }
}
