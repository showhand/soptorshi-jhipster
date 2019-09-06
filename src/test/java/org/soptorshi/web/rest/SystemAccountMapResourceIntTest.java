package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.SystemAccountMap;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.SystemAccountMapRepository;
import org.soptorshi.repository.search.SystemAccountMapSearchRepository;
import org.soptorshi.service.SystemAccountMapService;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.mapper.SystemAccountMapMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SystemAccountMapCriteria;
import org.soptorshi.service.SystemAccountMapQueryService;

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

import org.soptorshi.domain.enumeration.AccountType;
/**
 * Test class for the SystemAccountMapResource REST controller.
 *
 * @see SystemAccountMapResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SystemAccountMapResourceIntTest {

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.OPENING_BALANCE_ADJUSTMENT_ACCOUNT;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.PROVIDENT_FUND_ACCOUNT;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SystemAccountMapRepository systemAccountMapRepository;

    @Autowired
    private SystemAccountMapMapper systemAccountMapMapper;

    @Autowired
    private SystemAccountMapService systemAccountMapService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SystemAccountMapSearchRepositoryMockConfiguration
     */
    @Autowired
    private SystemAccountMapSearchRepository mockSystemAccountMapSearchRepository;

    @Autowired
    private SystemAccountMapQueryService systemAccountMapQueryService;

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

    private MockMvc restSystemAccountMapMockMvc;

    private SystemAccountMap systemAccountMap;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemAccountMapResource systemAccountMapResource = new SystemAccountMapResource(systemAccountMapService, systemAccountMapQueryService);
        this.restSystemAccountMapMockMvc = MockMvcBuilders.standaloneSetup(systemAccountMapResource)
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
    public static SystemAccountMap createEntity(EntityManager em) {
        SystemAccountMap systemAccountMap = new SystemAccountMap()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return systemAccountMap;
    }

    @Before
    public void initTest() {
        systemAccountMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemAccountMap() throws Exception {
        int databaseSizeBeforeCreate = systemAccountMapRepository.findAll().size();

        // Create the SystemAccountMap
        SystemAccountMapDTO systemAccountMapDTO = systemAccountMapMapper.toDto(systemAccountMap);
        restSystemAccountMapMockMvc.perform(post("/api/system-account-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemAccountMapDTO)))
            .andExpect(status().isCreated());

        // Validate the SystemAccountMap in the database
        List<SystemAccountMap> systemAccountMapList = systemAccountMapRepository.findAll();
        assertThat(systemAccountMapList).hasSize(databaseSizeBeforeCreate + 1);
        SystemAccountMap testSystemAccountMap = systemAccountMapList.get(systemAccountMapList.size() - 1);
        assertThat(testSystemAccountMap.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testSystemAccountMap.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSystemAccountMap.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the SystemAccountMap in Elasticsearch
        verify(mockSystemAccountMapSearchRepository, times(1)).save(testSystemAccountMap);
    }

    @Test
    @Transactional
    public void createSystemAccountMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemAccountMapRepository.findAll().size();

        // Create the SystemAccountMap with an existing ID
        systemAccountMap.setId(1L);
        SystemAccountMapDTO systemAccountMapDTO = systemAccountMapMapper.toDto(systemAccountMap);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemAccountMapMockMvc.perform(post("/api/system-account-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemAccountMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemAccountMap in the database
        List<SystemAccountMap> systemAccountMapList = systemAccountMapRepository.findAll();
        assertThat(systemAccountMapList).hasSize(databaseSizeBeforeCreate);

        // Validate the SystemAccountMap in Elasticsearch
        verify(mockSystemAccountMapSearchRepository, times(0)).save(systemAccountMap);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMaps() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemAccountMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSystemAccountMap() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get the systemAccountMap
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps/{id}", systemAccountMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemAccountMap.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultSystemAccountMapShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the systemAccountMapList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultSystemAccountMapShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultSystemAccountMapShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the systemAccountMapList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultSystemAccountMapShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where accountType is not null
        defaultSystemAccountMapShouldBeFound("accountType.specified=true");

        // Get all the systemAccountMapList where accountType is null
        defaultSystemAccountMapShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSystemAccountMapShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the systemAccountMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSystemAccountMapShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSystemAccountMapShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the systemAccountMapList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSystemAccountMapShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedBy is not null
        defaultSystemAccountMapShouldBeFound("modifiedBy.specified=true");

        // Get all the systemAccountMapList where modifiedBy is null
        defaultSystemAccountMapShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultSystemAccountMapShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the systemAccountMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSystemAccountMapShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultSystemAccountMapShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the systemAccountMapList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSystemAccountMapShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedOn is not null
        defaultSystemAccountMapShouldBeFound("modifiedOn.specified=true");

        // Get all the systemAccountMapList where modifiedOn is null
        defaultSystemAccountMapShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultSystemAccountMapShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the systemAccountMapList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultSystemAccountMapShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSystemAccountMapsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        // Get all the systemAccountMapList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultSystemAccountMapShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the systemAccountMapList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultSystemAccountMapShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllSystemAccountMapsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        MstAccount account = MstAccountResourceIntTest.createEntity(em);
        em.persist(account);
        em.flush();
        systemAccountMap.setAccount(account);
        systemAccountMapRepository.saveAndFlush(systemAccountMap);
        Long accountId = account.getId();

        // Get all the systemAccountMapList where account equals to accountId
        defaultSystemAccountMapShouldBeFound("accountId.equals=" + accountId);

        // Get all the systemAccountMapList where account equals to accountId + 1
        defaultSystemAccountMapShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSystemAccountMapShouldBeFound(String filter) throws Exception {
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemAccountMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSystemAccountMapShouldNotBeFound(String filter) throws Exception {
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSystemAccountMap() throws Exception {
        // Get the systemAccountMap
        restSystemAccountMapMockMvc.perform(get("/api/system-account-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemAccountMap() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        int databaseSizeBeforeUpdate = systemAccountMapRepository.findAll().size();

        // Update the systemAccountMap
        SystemAccountMap updatedSystemAccountMap = systemAccountMapRepository.findById(systemAccountMap.getId()).get();
        // Disconnect from session so that the updates on updatedSystemAccountMap are not directly saved in db
        em.detach(updatedSystemAccountMap);
        updatedSystemAccountMap
            .accountType(UPDATED_ACCOUNT_TYPE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        SystemAccountMapDTO systemAccountMapDTO = systemAccountMapMapper.toDto(updatedSystemAccountMap);

        restSystemAccountMapMockMvc.perform(put("/api/system-account-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemAccountMapDTO)))
            .andExpect(status().isOk());

        // Validate the SystemAccountMap in the database
        List<SystemAccountMap> systemAccountMapList = systemAccountMapRepository.findAll();
        assertThat(systemAccountMapList).hasSize(databaseSizeBeforeUpdate);
        SystemAccountMap testSystemAccountMap = systemAccountMapList.get(systemAccountMapList.size() - 1);
        assertThat(testSystemAccountMap.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testSystemAccountMap.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSystemAccountMap.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the SystemAccountMap in Elasticsearch
        verify(mockSystemAccountMapSearchRepository, times(1)).save(testSystemAccountMap);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemAccountMap() throws Exception {
        int databaseSizeBeforeUpdate = systemAccountMapRepository.findAll().size();

        // Create the SystemAccountMap
        SystemAccountMapDTO systemAccountMapDTO = systemAccountMapMapper.toDto(systemAccountMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemAccountMapMockMvc.perform(put("/api/system-account-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemAccountMapDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SystemAccountMap in the database
        List<SystemAccountMap> systemAccountMapList = systemAccountMapRepository.findAll();
        assertThat(systemAccountMapList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SystemAccountMap in Elasticsearch
        verify(mockSystemAccountMapSearchRepository, times(0)).save(systemAccountMap);
    }

    @Test
    @Transactional
    public void deleteSystemAccountMap() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);

        int databaseSizeBeforeDelete = systemAccountMapRepository.findAll().size();

        // Delete the systemAccountMap
        restSystemAccountMapMockMvc.perform(delete("/api/system-account-maps/{id}", systemAccountMap.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SystemAccountMap> systemAccountMapList = systemAccountMapRepository.findAll();
        assertThat(systemAccountMapList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SystemAccountMap in Elasticsearch
        verify(mockSystemAccountMapSearchRepository, times(1)).deleteById(systemAccountMap.getId());
    }

    @Test
    @Transactional
    public void searchSystemAccountMap() throws Exception {
        // Initialize the database
        systemAccountMapRepository.saveAndFlush(systemAccountMap);
        when(mockSystemAccountMapSearchRepository.search(queryStringQuery("id:" + systemAccountMap.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(systemAccountMap), PageRequest.of(0, 1), 1));
        // Search the systemAccountMap
        restSystemAccountMapMockMvc.perform(get("/api/_search/system-account-maps?query=id:" + systemAccountMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemAccountMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemAccountMap.class);
        SystemAccountMap systemAccountMap1 = new SystemAccountMap();
        systemAccountMap1.setId(1L);
        SystemAccountMap systemAccountMap2 = new SystemAccountMap();
        systemAccountMap2.setId(systemAccountMap1.getId());
        assertThat(systemAccountMap1).isEqualTo(systemAccountMap2);
        systemAccountMap2.setId(2L);
        assertThat(systemAccountMap1).isNotEqualTo(systemAccountMap2);
        systemAccountMap1.setId(null);
        assertThat(systemAccountMap1).isNotEqualTo(systemAccountMap2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemAccountMapDTO.class);
        SystemAccountMapDTO systemAccountMapDTO1 = new SystemAccountMapDTO();
        systemAccountMapDTO1.setId(1L);
        SystemAccountMapDTO systemAccountMapDTO2 = new SystemAccountMapDTO();
        assertThat(systemAccountMapDTO1).isNotEqualTo(systemAccountMapDTO2);
        systemAccountMapDTO2.setId(systemAccountMapDTO1.getId());
        assertThat(systemAccountMapDTO1).isEqualTo(systemAccountMapDTO2);
        systemAccountMapDTO2.setId(2L);
        assertThat(systemAccountMapDTO1).isNotEqualTo(systemAccountMapDTO2);
        systemAccountMapDTO1.setId(null);
        assertThat(systemAccountMapDTO1).isNotEqualTo(systemAccountMapDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemAccountMapMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemAccountMapMapper.fromId(null)).isNull();
    }
}
