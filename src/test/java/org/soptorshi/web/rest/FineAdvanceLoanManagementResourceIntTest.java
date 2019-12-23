package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.FineAdvanceLoanManagement;
import org.soptorshi.repository.FineAdvanceLoanManagementRepository;
import org.soptorshi.repository.search.FineAdvanceLoanManagementSearchRepository;
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
 * Test class for the FineAdvanceLoanManagementResource REST controller.
 *
 * @see FineAdvanceLoanManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class FineAdvanceLoanManagementResourceIntTest {

    @Autowired
    private FineAdvanceLoanManagementRepository fineAdvanceLoanManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.FineAdvanceLoanManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private FineAdvanceLoanManagementSearchRepository mockFineAdvanceLoanManagementSearchRepository;

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

    private MockMvc restFineAdvanceLoanManagementMockMvc;

    private FineAdvanceLoanManagement fineAdvanceLoanManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FineAdvanceLoanManagementResource fineAdvanceLoanManagementResource = new FineAdvanceLoanManagementResource(fineAdvanceLoanManagementRepository, mockFineAdvanceLoanManagementSearchRepository);
        this.restFineAdvanceLoanManagementMockMvc = MockMvcBuilders.standaloneSetup(fineAdvanceLoanManagementResource)
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
    public static FineAdvanceLoanManagement createEntity(EntityManager em) {
        FineAdvanceLoanManagement fineAdvanceLoanManagement = new FineAdvanceLoanManagement();
        return fineAdvanceLoanManagement;
    }

    @Before
    public void initTest() {
        fineAdvanceLoanManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createFineAdvanceLoanManagement() throws Exception {
        int databaseSizeBeforeCreate = fineAdvanceLoanManagementRepository.findAll().size();

        // Create the FineAdvanceLoanManagement
        restFineAdvanceLoanManagementMockMvc.perform(post("/api/fine-advance-loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineAdvanceLoanManagement)))
            .andExpect(status().isCreated());

        // Validate the FineAdvanceLoanManagement in the database
        List<FineAdvanceLoanManagement> fineAdvanceLoanManagementList = fineAdvanceLoanManagementRepository.findAll();
        assertThat(fineAdvanceLoanManagementList).hasSize(databaseSizeBeforeCreate + 1);
        FineAdvanceLoanManagement testFineAdvanceLoanManagement = fineAdvanceLoanManagementList.get(fineAdvanceLoanManagementList.size() - 1);

        // Validate the FineAdvanceLoanManagement in Elasticsearch
        verify(mockFineAdvanceLoanManagementSearchRepository, times(1)).save(testFineAdvanceLoanManagement);
    }

    @Test
    @Transactional
    public void createFineAdvanceLoanManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fineAdvanceLoanManagementRepository.findAll().size();

        // Create the FineAdvanceLoanManagement with an existing ID
        fineAdvanceLoanManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFineAdvanceLoanManagementMockMvc.perform(post("/api/fine-advance-loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineAdvanceLoanManagement)))
            .andExpect(status().isBadRequest());

        // Validate the FineAdvanceLoanManagement in the database
        List<FineAdvanceLoanManagement> fineAdvanceLoanManagementList = fineAdvanceLoanManagementRepository.findAll();
        assertThat(fineAdvanceLoanManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the FineAdvanceLoanManagement in Elasticsearch
        verify(mockFineAdvanceLoanManagementSearchRepository, times(0)).save(fineAdvanceLoanManagement);
    }

    @Test
    @Transactional
    public void getAllFineAdvanceLoanManagements() throws Exception {
        // Initialize the database
        fineAdvanceLoanManagementRepository.saveAndFlush(fineAdvanceLoanManagement);

        // Get all the fineAdvanceLoanManagementList
        restFineAdvanceLoanManagementMockMvc.perform(get("/api/fine-advance-loan-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fineAdvanceLoanManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFineAdvanceLoanManagement() throws Exception {
        // Initialize the database
        fineAdvanceLoanManagementRepository.saveAndFlush(fineAdvanceLoanManagement);

        // Get the fineAdvanceLoanManagement
        restFineAdvanceLoanManagementMockMvc.perform(get("/api/fine-advance-loan-managements/{id}", fineAdvanceLoanManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fineAdvanceLoanManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFineAdvanceLoanManagement() throws Exception {
        // Get the fineAdvanceLoanManagement
        restFineAdvanceLoanManagementMockMvc.perform(get("/api/fine-advance-loan-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFineAdvanceLoanManagement() throws Exception {
        // Initialize the database
        fineAdvanceLoanManagementRepository.saveAndFlush(fineAdvanceLoanManagement);

        int databaseSizeBeforeUpdate = fineAdvanceLoanManagementRepository.findAll().size();

        // Update the fineAdvanceLoanManagement
        FineAdvanceLoanManagement updatedFineAdvanceLoanManagement = fineAdvanceLoanManagementRepository.findById(fineAdvanceLoanManagement.getId()).get();
        // Disconnect from session so that the updates on updatedFineAdvanceLoanManagement are not directly saved in db
        em.detach(updatedFineAdvanceLoanManagement);

        restFineAdvanceLoanManagementMockMvc.perform(put("/api/fine-advance-loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFineAdvanceLoanManagement)))
            .andExpect(status().isOk());

        // Validate the FineAdvanceLoanManagement in the database
        List<FineAdvanceLoanManagement> fineAdvanceLoanManagementList = fineAdvanceLoanManagementRepository.findAll();
        assertThat(fineAdvanceLoanManagementList).hasSize(databaseSizeBeforeUpdate);
        FineAdvanceLoanManagement testFineAdvanceLoanManagement = fineAdvanceLoanManagementList.get(fineAdvanceLoanManagementList.size() - 1);

        // Validate the FineAdvanceLoanManagement in Elasticsearch
        verify(mockFineAdvanceLoanManagementSearchRepository, times(1)).save(testFineAdvanceLoanManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingFineAdvanceLoanManagement() throws Exception {
        int databaseSizeBeforeUpdate = fineAdvanceLoanManagementRepository.findAll().size();

        // Create the FineAdvanceLoanManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFineAdvanceLoanManagementMockMvc.perform(put("/api/fine-advance-loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fineAdvanceLoanManagement)))
            .andExpect(status().isBadRequest());

        // Validate the FineAdvanceLoanManagement in the database
        List<FineAdvanceLoanManagement> fineAdvanceLoanManagementList = fineAdvanceLoanManagementRepository.findAll();
        assertThat(fineAdvanceLoanManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FineAdvanceLoanManagement in Elasticsearch
        verify(mockFineAdvanceLoanManagementSearchRepository, times(0)).save(fineAdvanceLoanManagement);
    }

    @Test
    @Transactional
    public void deleteFineAdvanceLoanManagement() throws Exception {
        // Initialize the database
        fineAdvanceLoanManagementRepository.saveAndFlush(fineAdvanceLoanManagement);

        int databaseSizeBeforeDelete = fineAdvanceLoanManagementRepository.findAll().size();

        // Delete the fineAdvanceLoanManagement
        restFineAdvanceLoanManagementMockMvc.perform(delete("/api/fine-advance-loan-managements/{id}", fineAdvanceLoanManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FineAdvanceLoanManagement> fineAdvanceLoanManagementList = fineAdvanceLoanManagementRepository.findAll();
        assertThat(fineAdvanceLoanManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FineAdvanceLoanManagement in Elasticsearch
        verify(mockFineAdvanceLoanManagementSearchRepository, times(1)).deleteById(fineAdvanceLoanManagement.getId());
    }

    @Test
    @Transactional
    public void searchFineAdvanceLoanManagement() throws Exception {
        // Initialize the database
        fineAdvanceLoanManagementRepository.saveAndFlush(fineAdvanceLoanManagement);
        when(mockFineAdvanceLoanManagementSearchRepository.search(queryStringQuery("id:" + fineAdvanceLoanManagement.getId())))
            .thenReturn(Collections.singletonList(fineAdvanceLoanManagement));
        // Search the fineAdvanceLoanManagement
        restFineAdvanceLoanManagementMockMvc.perform(get("/api/_search/fine-advance-loan-managements?query=id:" + fineAdvanceLoanManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fineAdvanceLoanManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FineAdvanceLoanManagement.class);
        FineAdvanceLoanManagement fineAdvanceLoanManagement1 = new FineAdvanceLoanManagement();
        fineAdvanceLoanManagement1.setId(1L);
        FineAdvanceLoanManagement fineAdvanceLoanManagement2 = new FineAdvanceLoanManagement();
        fineAdvanceLoanManagement2.setId(fineAdvanceLoanManagement1.getId());
        assertThat(fineAdvanceLoanManagement1).isEqualTo(fineAdvanceLoanManagement2);
        fineAdvanceLoanManagement2.setId(2L);
        assertThat(fineAdvanceLoanManagement1).isNotEqualTo(fineAdvanceLoanManagement2);
        fineAdvanceLoanManagement1.setId(null);
        assertThat(fineAdvanceLoanManagement1).isNotEqualTo(fineAdvanceLoanManagement2);
    }
}
