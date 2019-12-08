package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.LoanManagement;
import org.soptorshi.repository.LoanManagementRepository;
import org.soptorshi.repository.search.LoanManagementSearchRepository;
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
 * Test class for the LoanManagementResource REST controller.
 *
 * @see LoanManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class LoanManagementResourceIntTest {

    @Autowired
    private LoanManagementRepository loanManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.LoanManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private LoanManagementSearchRepository mockLoanManagementSearchRepository;

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

    private MockMvc restLoanManagementMockMvc;

    private LoanManagement loanManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanManagementResource loanManagementResource = new LoanManagementResource(loanManagementRepository, mockLoanManagementSearchRepository);
        this.restLoanManagementMockMvc = MockMvcBuilders.standaloneSetup(loanManagementResource)
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
    public static LoanManagement createEntity(EntityManager em) {
        LoanManagement loanManagement = new LoanManagement();
        return loanManagement;
    }

    @Before
    public void initTest() {
        loanManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanManagement() throws Exception {
        int databaseSizeBeforeCreate = loanManagementRepository.findAll().size();

        // Create the LoanManagement
        restLoanManagementMockMvc.perform(post("/api/loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanManagement)))
            .andExpect(status().isCreated());

        // Validate the LoanManagement in the database
        List<LoanManagement> loanManagementList = loanManagementRepository.findAll();
        assertThat(loanManagementList).hasSize(databaseSizeBeforeCreate + 1);
        LoanManagement testLoanManagement = loanManagementList.get(loanManagementList.size() - 1);

        // Validate the LoanManagement in Elasticsearch
        verify(mockLoanManagementSearchRepository, times(1)).save(testLoanManagement);
    }

    @Test
    @Transactional
    public void createLoanManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanManagementRepository.findAll().size();

        // Create the LoanManagement with an existing ID
        loanManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanManagementMockMvc.perform(post("/api/loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanManagement)))
            .andExpect(status().isBadRequest());

        // Validate the LoanManagement in the database
        List<LoanManagement> loanManagementList = loanManagementRepository.findAll();
        assertThat(loanManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the LoanManagement in Elasticsearch
        verify(mockLoanManagementSearchRepository, times(0)).save(loanManagement);
    }

    @Test
    @Transactional
    public void getAllLoanManagements() throws Exception {
        // Initialize the database
        loanManagementRepository.saveAndFlush(loanManagement);

        // Get all the loanManagementList
        restLoanManagementMockMvc.perform(get("/api/loan-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getLoanManagement() throws Exception {
        // Initialize the database
        loanManagementRepository.saveAndFlush(loanManagement);

        // Get the loanManagement
        restLoanManagementMockMvc.perform(get("/api/loan-managements/{id}", loanManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanManagement() throws Exception {
        // Get the loanManagement
        restLoanManagementMockMvc.perform(get("/api/loan-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanManagement() throws Exception {
        // Initialize the database
        loanManagementRepository.saveAndFlush(loanManagement);

        int databaseSizeBeforeUpdate = loanManagementRepository.findAll().size();

        // Update the loanManagement
        LoanManagement updatedLoanManagement = loanManagementRepository.findById(loanManagement.getId()).get();
        // Disconnect from session so that the updates on updatedLoanManagement are not directly saved in db
        em.detach(updatedLoanManagement);

        restLoanManagementMockMvc.perform(put("/api/loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoanManagement)))
            .andExpect(status().isOk());

        // Validate the LoanManagement in the database
        List<LoanManagement> loanManagementList = loanManagementRepository.findAll();
        assertThat(loanManagementList).hasSize(databaseSizeBeforeUpdate);
        LoanManagement testLoanManagement = loanManagementList.get(loanManagementList.size() - 1);

        // Validate the LoanManagement in Elasticsearch
        verify(mockLoanManagementSearchRepository, times(1)).save(testLoanManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanManagement() throws Exception {
        int databaseSizeBeforeUpdate = loanManagementRepository.findAll().size();

        // Create the LoanManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanManagementMockMvc.perform(put("/api/loan-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanManagement)))
            .andExpect(status().isBadRequest());

        // Validate the LoanManagement in the database
        List<LoanManagement> loanManagementList = loanManagementRepository.findAll();
        assertThat(loanManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LoanManagement in Elasticsearch
        verify(mockLoanManagementSearchRepository, times(0)).save(loanManagement);
    }

    @Test
    @Transactional
    public void deleteLoanManagement() throws Exception {
        // Initialize the database
        loanManagementRepository.saveAndFlush(loanManagement);

        int databaseSizeBeforeDelete = loanManagementRepository.findAll().size();

        // Delete the loanManagement
        restLoanManagementMockMvc.perform(delete("/api/loan-managements/{id}", loanManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoanManagement> loanManagementList = loanManagementRepository.findAll();
        assertThat(loanManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LoanManagement in Elasticsearch
        verify(mockLoanManagementSearchRepository, times(1)).deleteById(loanManagement.getId());
    }

    @Test
    @Transactional
    public void searchLoanManagement() throws Exception {
        // Initialize the database
        loanManagementRepository.saveAndFlush(loanManagement);
        when(mockLoanManagementSearchRepository.search(queryStringQuery("id:" + loanManagement.getId())))
            .thenReturn(Collections.singletonList(loanManagement));
        // Search the loanManagement
        restLoanManagementMockMvc.perform(get("/api/_search/loan-managements?query=id:" + loanManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanManagement.class);
        LoanManagement loanManagement1 = new LoanManagement();
        loanManagement1.setId(1L);
        LoanManagement loanManagement2 = new LoanManagement();
        loanManagement2.setId(loanManagement1.getId());
        assertThat(loanManagement1).isEqualTo(loanManagement2);
        loanManagement2.setId(2L);
        assertThat(loanManagement1).isNotEqualTo(loanManagement2);
        loanManagement1.setId(null);
        assertThat(loanManagement1).isNotEqualTo(loanManagement2);
    }
}
