package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ProvidentManagement;
import org.soptorshi.repository.ProvidentManagementRepository;
import org.soptorshi.repository.search.ProvidentManagementSearchRepository;
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
 * Test class for the ProvidentManagementResource REST controller.
 *
 * @see ProvidentManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ProvidentManagementResourceIntTest {

    @Autowired
    private ProvidentManagementRepository providentManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ProvidentManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProvidentManagementSearchRepository mockProvidentManagementSearchRepository;

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

    private MockMvc restProvidentManagementMockMvc;

    private ProvidentManagement providentManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProvidentManagementResource providentManagementResource = new ProvidentManagementResource(providentManagementRepository, mockProvidentManagementSearchRepository);
        this.restProvidentManagementMockMvc = MockMvcBuilders.standaloneSetup(providentManagementResource)
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
    public static ProvidentManagement createEntity(EntityManager em) {
        ProvidentManagement providentManagement = new ProvidentManagement();
        return providentManagement;
    }

    @Before
    public void initTest() {
        providentManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createProvidentManagement() throws Exception {
        int databaseSizeBeforeCreate = providentManagementRepository.findAll().size();

        // Create the ProvidentManagement
        restProvidentManagementMockMvc.perform(post("/api/provident-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentManagement)))
            .andExpect(status().isCreated());

        // Validate the ProvidentManagement in the database
        List<ProvidentManagement> providentManagementList = providentManagementRepository.findAll();
        assertThat(providentManagementList).hasSize(databaseSizeBeforeCreate + 1);
        ProvidentManagement testProvidentManagement = providentManagementList.get(providentManagementList.size() - 1);

        // Validate the ProvidentManagement in Elasticsearch
        verify(mockProvidentManagementSearchRepository, times(1)).save(testProvidentManagement);
    }

    @Test
    @Transactional
    public void createProvidentManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providentManagementRepository.findAll().size();

        // Create the ProvidentManagement with an existing ID
        providentManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvidentManagementMockMvc.perform(post("/api/provident-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentManagement)))
            .andExpect(status().isBadRequest());

        // Validate the ProvidentManagement in the database
        List<ProvidentManagement> providentManagementList = providentManagementRepository.findAll();
        assertThat(providentManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProvidentManagement in Elasticsearch
        verify(mockProvidentManagementSearchRepository, times(0)).save(providentManagement);
    }

    @Test
    @Transactional
    public void getAllProvidentManagements() throws Exception {
        // Initialize the database
        providentManagementRepository.saveAndFlush(providentManagement);

        // Get all the providentManagementList
        restProvidentManagementMockMvc.perform(get("/api/provident-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providentManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProvidentManagement() throws Exception {
        // Initialize the database
        providentManagementRepository.saveAndFlush(providentManagement);

        // Get the providentManagement
        restProvidentManagementMockMvc.perform(get("/api/provident-managements/{id}", providentManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providentManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProvidentManagement() throws Exception {
        // Get the providentManagement
        restProvidentManagementMockMvc.perform(get("/api/provident-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvidentManagement() throws Exception {
        // Initialize the database
        providentManagementRepository.saveAndFlush(providentManagement);

        int databaseSizeBeforeUpdate = providentManagementRepository.findAll().size();

        // Update the providentManagement
        ProvidentManagement updatedProvidentManagement = providentManagementRepository.findById(providentManagement.getId()).get();
        // Disconnect from session so that the updates on updatedProvidentManagement are not directly saved in db
        em.detach(updatedProvidentManagement);

        restProvidentManagementMockMvc.perform(put("/api/provident-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProvidentManagement)))
            .andExpect(status().isOk());

        // Validate the ProvidentManagement in the database
        List<ProvidentManagement> providentManagementList = providentManagementRepository.findAll();
        assertThat(providentManagementList).hasSize(databaseSizeBeforeUpdate);
        ProvidentManagement testProvidentManagement = providentManagementList.get(providentManagementList.size() - 1);

        // Validate the ProvidentManagement in Elasticsearch
        verify(mockProvidentManagementSearchRepository, times(1)).save(testProvidentManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingProvidentManagement() throws Exception {
        int databaseSizeBeforeUpdate = providentManagementRepository.findAll().size();

        // Create the ProvidentManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvidentManagementMockMvc.perform(put("/api/provident-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providentManagement)))
            .andExpect(status().isBadRequest());

        // Validate the ProvidentManagement in the database
        List<ProvidentManagement> providentManagementList = providentManagementRepository.findAll();
        assertThat(providentManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProvidentManagement in Elasticsearch
        verify(mockProvidentManagementSearchRepository, times(0)).save(providentManagement);
    }

    @Test
    @Transactional
    public void deleteProvidentManagement() throws Exception {
        // Initialize the database
        providentManagementRepository.saveAndFlush(providentManagement);

        int databaseSizeBeforeDelete = providentManagementRepository.findAll().size();

        // Delete the providentManagement
        restProvidentManagementMockMvc.perform(delete("/api/provident-managements/{id}", providentManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProvidentManagement> providentManagementList = providentManagementRepository.findAll();
        assertThat(providentManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProvidentManagement in Elasticsearch
        verify(mockProvidentManagementSearchRepository, times(1)).deleteById(providentManagement.getId());
    }

    @Test
    @Transactional
    public void searchProvidentManagement() throws Exception {
        // Initialize the database
        providentManagementRepository.saveAndFlush(providentManagement);
        when(mockProvidentManagementSearchRepository.search(queryStringQuery("id:" + providentManagement.getId())))
            .thenReturn(Collections.singletonList(providentManagement));
        // Search the providentManagement
        restProvidentManagementMockMvc.perform(get("/api/_search/provident-managements?query=id:" + providentManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providentManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvidentManagement.class);
        ProvidentManagement providentManagement1 = new ProvidentManagement();
        providentManagement1.setId(1L);
        ProvidentManagement providentManagement2 = new ProvidentManagement();
        providentManagement2.setId(providentManagement1.getId());
        assertThat(providentManagement1).isEqualTo(providentManagement2);
        providentManagement2.setId(2L);
        assertThat(providentManagement1).isNotEqualTo(providentManagement2);
        providentManagement1.setId(null);
        assertThat(providentManagement1).isNotEqualTo(providentManagement2);
    }
}
