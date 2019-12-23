package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.AdvanceManagement;
import org.soptorshi.repository.AdvanceManagementRepository;
import org.soptorshi.repository.search.AdvanceManagementSearchRepository;
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
 * Test class for the AdvanceManagementResource REST controller.
 *
 * @see AdvanceManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AdvanceManagementResourceIntTest {

    @Autowired
    private AdvanceManagementRepository advanceManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AdvanceManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdvanceManagementSearchRepository mockAdvanceManagementSearchRepository;

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

    private MockMvc restAdvanceManagementMockMvc;

    private AdvanceManagement advanceManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdvanceManagementResource advanceManagementResource = new AdvanceManagementResource(advanceManagementRepository, mockAdvanceManagementSearchRepository);
        this.restAdvanceManagementMockMvc = MockMvcBuilders.standaloneSetup(advanceManagementResource)
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
    public static AdvanceManagement createEntity(EntityManager em) {
        AdvanceManagement advanceManagement = new AdvanceManagement();
        return advanceManagement;
    }

    @Before
    public void initTest() {
        advanceManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvanceManagement() throws Exception {
        int databaseSizeBeforeCreate = advanceManagementRepository.findAll().size();

        // Create the AdvanceManagement
        restAdvanceManagementMockMvc.perform(post("/api/advance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceManagement)))
            .andExpect(status().isCreated());

        // Validate the AdvanceManagement in the database
        List<AdvanceManagement> advanceManagementList = advanceManagementRepository.findAll();
        assertThat(advanceManagementList).hasSize(databaseSizeBeforeCreate + 1);
        AdvanceManagement testAdvanceManagement = advanceManagementList.get(advanceManagementList.size() - 1);

        // Validate the AdvanceManagement in Elasticsearch
        verify(mockAdvanceManagementSearchRepository, times(1)).save(testAdvanceManagement);
    }

    @Test
    @Transactional
    public void createAdvanceManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advanceManagementRepository.findAll().size();

        // Create the AdvanceManagement with an existing ID
        advanceManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvanceManagementMockMvc.perform(post("/api/advance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AdvanceManagement in the database
        List<AdvanceManagement> advanceManagementList = advanceManagementRepository.findAll();
        assertThat(advanceManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the AdvanceManagement in Elasticsearch
        verify(mockAdvanceManagementSearchRepository, times(0)).save(advanceManagement);
    }

    @Test
    @Transactional
    public void getAllAdvanceManagements() throws Exception {
        // Initialize the database
        advanceManagementRepository.saveAndFlush(advanceManagement);

        // Get all the advanceManagementList
        restAdvanceManagementMockMvc.perform(get("/api/advance-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advanceManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAdvanceManagement() throws Exception {
        // Initialize the database
        advanceManagementRepository.saveAndFlush(advanceManagement);

        // Get the advanceManagement
        restAdvanceManagementMockMvc.perform(get("/api/advance-managements/{id}", advanceManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(advanceManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdvanceManagement() throws Exception {
        // Get the advanceManagement
        restAdvanceManagementMockMvc.perform(get("/api/advance-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvanceManagement() throws Exception {
        // Initialize the database
        advanceManagementRepository.saveAndFlush(advanceManagement);

        int databaseSizeBeforeUpdate = advanceManagementRepository.findAll().size();

        // Update the advanceManagement
        AdvanceManagement updatedAdvanceManagement = advanceManagementRepository.findById(advanceManagement.getId()).get();
        // Disconnect from session so that the updates on updatedAdvanceManagement are not directly saved in db
        em.detach(updatedAdvanceManagement);

        restAdvanceManagementMockMvc.perform(put("/api/advance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdvanceManagement)))
            .andExpect(status().isOk());

        // Validate the AdvanceManagement in the database
        List<AdvanceManagement> advanceManagementList = advanceManagementRepository.findAll();
        assertThat(advanceManagementList).hasSize(databaseSizeBeforeUpdate);
        AdvanceManagement testAdvanceManagement = advanceManagementList.get(advanceManagementList.size() - 1);

        // Validate the AdvanceManagement in Elasticsearch
        verify(mockAdvanceManagementSearchRepository, times(1)).save(testAdvanceManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvanceManagement() throws Exception {
        int databaseSizeBeforeUpdate = advanceManagementRepository.findAll().size();

        // Create the AdvanceManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdvanceManagementMockMvc.perform(put("/api/advance-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advanceManagement)))
            .andExpect(status().isBadRequest());

        // Validate the AdvanceManagement in the database
        List<AdvanceManagement> advanceManagementList = advanceManagementRepository.findAll();
        assertThat(advanceManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AdvanceManagement in Elasticsearch
        verify(mockAdvanceManagementSearchRepository, times(0)).save(advanceManagement);
    }

    @Test
    @Transactional
    public void deleteAdvanceManagement() throws Exception {
        // Initialize the database
        advanceManagementRepository.saveAndFlush(advanceManagement);

        int databaseSizeBeforeDelete = advanceManagementRepository.findAll().size();

        // Delete the advanceManagement
        restAdvanceManagementMockMvc.perform(delete("/api/advance-managements/{id}", advanceManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdvanceManagement> advanceManagementList = advanceManagementRepository.findAll();
        assertThat(advanceManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AdvanceManagement in Elasticsearch
        verify(mockAdvanceManagementSearchRepository, times(1)).deleteById(advanceManagement.getId());
    }

    @Test
    @Transactional
    public void searchAdvanceManagement() throws Exception {
        // Initialize the database
        advanceManagementRepository.saveAndFlush(advanceManagement);
        when(mockAdvanceManagementSearchRepository.search(queryStringQuery("id:" + advanceManagement.getId())))
            .thenReturn(Collections.singletonList(advanceManagement));
        // Search the advanceManagement
        restAdvanceManagementMockMvc.perform(get("/api/_search/advance-managements?query=id:" + advanceManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advanceManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdvanceManagement.class);
        AdvanceManagement advanceManagement1 = new AdvanceManagement();
        advanceManagement1.setId(1L);
        AdvanceManagement advanceManagement2 = new AdvanceManagement();
        advanceManagement2.setId(advanceManagement1.getId());
        assertThat(advanceManagement1).isEqualTo(advanceManagement2);
        advanceManagement2.setId(2L);
        assertThat(advanceManagement1).isNotEqualTo(advanceManagement2);
        advanceManagement1.setId(null);
        assertThat(advanceManagement1).isNotEqualTo(advanceManagement2);
    }
}
