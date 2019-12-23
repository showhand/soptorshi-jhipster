package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.FineManagement;
import org.soptorshi.repository.FineManagementRepository;
import org.soptorshi.repository.search.FineManagementSearchRepository;
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
 * Test class for the FineManagementResource REST controller.
 *
 * @see FineManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class FineManagementResourceIntTest {

    @Autowired
    private FineManagementRepository fineManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.FineManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private FineManagementSearchRepository mockFineManagementSearchRepository;

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

    private MockMvc restFineManagementMockMvc;

    private FineManagement fineManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FineManagementResource fineManagementResource = new FineManagementResource(fineManagementRepository, mockFineManagementSearchRepository);
        this.restFineManagementMockMvc = MockMvcBuilders.standaloneSetup(fineManagementResource)
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
    public static FineManagement createEntity(EntityManager em) {
        FineManagement fineManagement = new FineManagement();
        return fineManagement;
    }

    @Before
    public void initTest() {
        fineManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createFineManagement() throws Exception {
        int databaseSizeBeforeCreate = fineManagementRepository.findAll().size();

        // Create the FineManagement
        restFineManagementMockMvc.perform(post("/api/fine-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineManagement)))
            .andExpect(status().isCreated());

        // Validate the FineManagement in the database
        List<FineManagement> fineManagementList = fineManagementRepository.findAll();
        assertThat(fineManagementList).hasSize(databaseSizeBeforeCreate + 1);
        FineManagement testFineManagement = fineManagementList.get(fineManagementList.size() - 1);

        // Validate the FineManagement in Elasticsearch
        verify(mockFineManagementSearchRepository, times(1)).save(testFineManagement);
    }

    @Test
    @Transactional
    public void createFineManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fineManagementRepository.findAll().size();

        // Create the FineManagement with an existing ID
        fineManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFineManagementMockMvc.perform(post("/api/fine-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineManagement)))
            .andExpect(status().isBadRequest());

        // Validate the FineManagement in the database
        List<FineManagement> fineManagementList = fineManagementRepository.findAll();
        assertThat(fineManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the FineManagement in Elasticsearch
        verify(mockFineManagementSearchRepository, times(0)).save(fineManagement);
    }

    @Test
    @Transactional
    public void getAllFineManagements() throws Exception {
        // Initialize the database
        fineManagementRepository.saveAndFlush(fineManagement);

        // Get all the fineManagementList
        restFineManagementMockMvc.perform(get("/api/fine-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fineManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFineManagement() throws Exception {
        // Initialize the database
        fineManagementRepository.saveAndFlush(fineManagement);

        // Get the fineManagement
        restFineManagementMockMvc.perform(get("/api/fine-managements/{id}", fineManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fineManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFineManagement() throws Exception {
        // Get the fineManagement
        restFineManagementMockMvc.perform(get("/api/fine-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFineManagement() throws Exception {
        // Initialize the database
        fineManagementRepository.saveAndFlush(fineManagement);

        int databaseSizeBeforeUpdate = fineManagementRepository.findAll().size();

        // Update the fineManagement
        FineManagement updatedFineManagement = fineManagementRepository.findById(fineManagement.getId()).get();
        // Disconnect from session so that the updates on updatedFineManagement are not directly saved in db
        em.detach(updatedFineManagement);

        restFineManagementMockMvc.perform(put("/api/fine-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFineManagement)))
            .andExpect(status().isOk());

        // Validate the FineManagement in the database
        List<FineManagement> fineManagementList = fineManagementRepository.findAll();
        assertThat(fineManagementList).hasSize(databaseSizeBeforeUpdate);
        FineManagement testFineManagement = fineManagementList.get(fineManagementList.size() - 1);

        // Validate the FineManagement in Elasticsearch
        verify(mockFineManagementSearchRepository, times(1)).save(testFineManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingFineManagement() throws Exception {
        int databaseSizeBeforeUpdate = fineManagementRepository.findAll().size();

        // Create the FineManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFineManagementMockMvc.perform(put("/api/fine-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineManagement)))
            .andExpect(status().isBadRequest());

        // Validate the FineManagement in the database
        List<FineManagement> fineManagementList = fineManagementRepository.findAll();
        assertThat(fineManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FineManagement in Elasticsearch
        verify(mockFineManagementSearchRepository, times(0)).save(fineManagement);
    }

    @Test
    @Transactional
    public void deleteFineManagement() throws Exception {
        // Initialize the database
        fineManagementRepository.saveAndFlush(fineManagement);

        int databaseSizeBeforeDelete = fineManagementRepository.findAll().size();

        // Delete the fineManagement
        restFineManagementMockMvc.perform(delete("/api/fine-managements/{id}", fineManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FineManagement> fineManagementList = fineManagementRepository.findAll();
        assertThat(fineManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FineManagement in Elasticsearch
        verify(mockFineManagementSearchRepository, times(1)).deleteById(fineManagement.getId());
    }

    @Test
    @Transactional
    public void searchFineManagement() throws Exception {
        // Initialize the database
        fineManagementRepository.saveAndFlush(fineManagement);
        when(mockFineManagementSearchRepository.search(queryStringQuery("id:" + fineManagement.getId())))
            .thenReturn(Collections.singletonList(fineManagement));
        // Search the fineManagement
        restFineManagementMockMvc.perform(get("/api/_search/fine-managements?query=id:" + fineManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fineManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FineManagement.class);
        FineManagement fineManagement1 = new FineManagement();
        fineManagement1.setId(1L);
        FineManagement fineManagement2 = new FineManagement();
        fineManagement2.setId(fineManagement1.getId());
        assertThat(fineManagement1).isEqualTo(fineManagement2);
        fineManagement2.setId(2L);
        assertThat(fineManagement1).isNotEqualTo(fineManagement2);
        fineManagement1.setId(null);
        assertThat(fineManagement1).isNotEqualTo(fineManagement2);
    }
}
