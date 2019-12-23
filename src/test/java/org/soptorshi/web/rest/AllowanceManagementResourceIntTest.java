package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.AllowanceManagement;
import org.soptorshi.repository.AllowanceManagementRepository;
import org.soptorshi.repository.search.AllowanceManagementSearchRepository;
import org.soptorshi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AllowanceManagementResource REST controller.
 *
 * @see AllowanceManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AllowanceManagementResourceIntTest {

    @Autowired
    private AllowanceManagementRepository allowanceManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AllowanceManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private AllowanceManagementSearchRepository mockAllowanceManagementSearchRepository;

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

    private MockMvc restAllowanceManagementMockMvc;

    private AllowanceManagement allowanceManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllowanceManagementResource allowanceManagementResource = new AllowanceManagementResource(allowanceManagementRepository, mockAllowanceManagementSearchRepository);
        this.restAllowanceManagementMockMvc = MockMvcBuilders.standaloneSetup(allowanceManagementResource)
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
    public static AllowanceManagement createEntity(EntityManager em) {
        AllowanceManagement allowanceManagement = new AllowanceManagement();
        return allowanceManagement;
    }

    @Before
    public void initTest() {
        allowanceManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllowanceManagement() throws Exception {
        int databaseSizeBeforeCreate = allowanceManagementRepository.findAll().size();

        // Create the AllowanceManagement
        restAllowanceManagementMockMvc.perform(post("/api/allowance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowanceManagement)))
            .andExpect(status().isCreated());

        // Validate the AllowanceManagement in the database
        List<AllowanceManagement> allowanceManagementList = allowanceManagementRepository.findAll();
        assertThat(allowanceManagementList).hasSize(databaseSizeBeforeCreate + 1);
        AllowanceManagement testAllowanceManagement = allowanceManagementList.get(allowanceManagementList.size() - 1);

        // Validate the AllowanceManagement in Elasticsearch
        verify(mockAllowanceManagementSearchRepository, times(1)).save(testAllowanceManagement);
    }

    @Test
    @Transactional
    public void createAllowanceManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allowanceManagementRepository.findAll().size();

        // Create the AllowanceManagement with an existing ID
        allowanceManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllowanceManagementMockMvc.perform(post("/api/allowance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AllowanceManagement in the database
        List<AllowanceManagement> allowanceManagementList = allowanceManagementRepository.findAll();
        assertThat(allowanceManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the AllowanceManagement in Elasticsearch
        verify(mockAllowanceManagementSearchRepository, times(0)).save(allowanceManagement);
    }

    @Test
    @Transactional
    public void getAllAllowanceManagements() throws Exception {
        // Initialize the database
        allowanceManagementRepository.saveAndFlush(allowanceManagement);

        // Get all the allowanceManagementList
        restAllowanceManagementMockMvc.perform(get("/api/allowance-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allowanceManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAllowanceManagement() throws Exception {
        // Initialize the database
        allowanceManagementRepository.saveAndFlush(allowanceManagement);

        // Get the allowanceManagement
        restAllowanceManagementMockMvc.perform(get("/api/allowance-managements/{id}", allowanceManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allowanceManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAllowanceManagement() throws Exception {
        // Get the allowanceManagement
        restAllowanceManagementMockMvc.perform(get("/api/allowance-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllowanceManagement() throws Exception {
        // Initialize the database
        allowanceManagementRepository.saveAndFlush(allowanceManagement);

        int databaseSizeBeforeUpdate = allowanceManagementRepository.findAll().size();

        // Update the allowanceManagement
        AllowanceManagement updatedAllowanceManagement = allowanceManagementRepository.findById(allowanceManagement.getId()).get();
        // Disconnect from session so that the updates on updatedAllowanceManagement are not directly saved in db
        em.detach(updatedAllowanceManagement);

        restAllowanceManagementMockMvc.perform(put("/api/allowance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllowanceManagement)))
            .andExpect(status().isOk());

        // Validate the AllowanceManagement in the database
        List<AllowanceManagement> allowanceManagementList = allowanceManagementRepository.findAll();
        assertThat(allowanceManagementList).hasSize(databaseSizeBeforeUpdate);
        AllowanceManagement testAllowanceManagement = allowanceManagementList.get(allowanceManagementList.size() - 1);

        // Validate the AllowanceManagement in Elasticsearch
        verify(mockAllowanceManagementSearchRepository, times(1)).save(testAllowanceManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingAllowanceManagement() throws Exception {
        int databaseSizeBeforeUpdate = allowanceManagementRepository.findAll().size();

        // Create the AllowanceManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllowanceManagementMockMvc.perform(put("/api/allowance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allowanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AllowanceManagement in the database
        List<AllowanceManagement> allowanceManagementList = allowanceManagementRepository.findAll();
        assertThat(allowanceManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AllowanceManagement in Elasticsearch
        verify(mockAllowanceManagementSearchRepository, times(0)).save(allowanceManagement);
    }

    @Test
    @Transactional
    public void deleteAllowanceManagement() throws Exception {
        // Initialize the database
        allowanceManagementRepository.saveAndFlush(allowanceManagement);

        int databaseSizeBeforeDelete = allowanceManagementRepository.findAll().size();

        // Delete the allowanceManagement
        restAllowanceManagementMockMvc.perform(delete("/api/allowance-managements/{id}", allowanceManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AllowanceManagement> allowanceManagementList = allowanceManagementRepository.findAll();
        assertThat(allowanceManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AllowanceManagement in Elasticsearch
        verify(mockAllowanceManagementSearchRepository, times(1)).deleteById(allowanceManagement.getId());
    }

    @Test
    @Transactional
    public void searchAllowanceManagement() throws Exception {
        // Initialize the database
        allowanceManagementRepository.saveAndFlush(allowanceManagement);
        when(mockAllowanceManagementSearchRepository.search(queryStringQuery("id:" + allowanceManagement.getId())))
            .thenReturn(Collections.singletonList(allowanceManagement));
        // Search the allowanceManagement
        restAllowanceManagementMockMvc.perform(get("/api/_search/allowance-managements?query=id:" + allowanceManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allowanceManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllowanceManagement.class);
        AllowanceManagement allowanceManagement1 = new AllowanceManagement();
        allowanceManagement1.setId(1L);
        AllowanceManagement allowanceManagement2 = new AllowanceManagement();
        allowanceManagement2.setId(allowanceManagement1.getId());
        assertThat(allowanceManagement1).isEqualTo(allowanceManagement2);
        allowanceManagement2.setId(2L);
        assertThat(allowanceManagement1).isNotEqualTo(allowanceManagement2);
        allowanceManagement1.setId(null);
        assertThat(allowanceManagement1).isNotEqualTo(allowanceManagement2);
    }
}
