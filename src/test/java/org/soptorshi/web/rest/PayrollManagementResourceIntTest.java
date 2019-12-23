package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PayrollManagement;
import org.soptorshi.repository.PayrollManagementRepository;
import org.soptorshi.repository.search.PayrollManagementSearchRepository;
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
 * Test class for the PayrollManagementResource REST controller.
 *
 * @see PayrollManagementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PayrollManagementResourceIntTest {

    @Autowired
    private PayrollManagementRepository payrollManagementRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PayrollManagementSearchRepositoryMockConfiguration
     */
    @Autowired
    private PayrollManagementSearchRepository mockPayrollManagementSearchRepository;

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

    private MockMvc restPayrollManagementMockMvc;

    private PayrollManagement payrollManagement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollManagementResource payrollManagementResource = new PayrollManagementResource(payrollManagementRepository, mockPayrollManagementSearchRepository);
        this.restPayrollManagementMockMvc = MockMvcBuilders.standaloneSetup(payrollManagementResource)
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
    public static PayrollManagement createEntity(EntityManager em) {
        PayrollManagement payrollManagement = new PayrollManagement();
        return payrollManagement;
    }

    @Before
    public void initTest() {
        payrollManagement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollManagement() throws Exception {
        int databaseSizeBeforeCreate = payrollManagementRepository.findAll().size();

        // Create the PayrollManagement
        restPayrollManagementMockMvc.perform(post("/api/payroll-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollManagement)))
            .andExpect(status().isCreated());

        // Validate the PayrollManagement in the database
        List<PayrollManagement> payrollManagementList = payrollManagementRepository.findAll();
        assertThat(payrollManagementList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollManagement testPayrollManagement = payrollManagementList.get(payrollManagementList.size() - 1);

        // Validate the PayrollManagement in Elasticsearch
        verify(mockPayrollManagementSearchRepository, times(1)).save(testPayrollManagement);
    }

    @Test
    @Transactional
    public void createPayrollManagementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollManagementRepository.findAll().size();

        // Create the PayrollManagement with an existing ID
        payrollManagement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollManagementMockMvc.perform(post("/api/payroll-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollManagement)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollManagement in the database
        List<PayrollManagement> payrollManagementList = payrollManagementRepository.findAll();
        assertThat(payrollManagementList).hasSize(databaseSizeBeforeCreate);

        // Validate the PayrollManagement in Elasticsearch
        verify(mockPayrollManagementSearchRepository, times(0)).save(payrollManagement);
    }

    @Test
    @Transactional
    public void getAllPayrollManagements() throws Exception {
        // Initialize the database
        payrollManagementRepository.saveAndFlush(payrollManagement);

        // Get all the payrollManagementList
        restPayrollManagementMockMvc.perform(get("/api/payroll-managements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollManagement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPayrollManagement() throws Exception {
        // Initialize the database
        payrollManagementRepository.saveAndFlush(payrollManagement);

        // Get the payrollManagement
        restPayrollManagementMockMvc.perform(get("/api/payroll-managements/{id}", payrollManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollManagement.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollManagement() throws Exception {
        // Get the payrollManagement
        restPayrollManagementMockMvc.perform(get("/api/payroll-managements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollManagement() throws Exception {
        // Initialize the database
        payrollManagementRepository.saveAndFlush(payrollManagement);

        int databaseSizeBeforeUpdate = payrollManagementRepository.findAll().size();

        // Update the payrollManagement
        PayrollManagement updatedPayrollManagement = payrollManagementRepository.findById(payrollManagement.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollManagement are not directly saved in db
        em.detach(updatedPayrollManagement);

        restPayrollManagementMockMvc.perform(put("/api/payroll-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollManagement)))
            .andExpect(status().isOk());

        // Validate the PayrollManagement in the database
        List<PayrollManagement> payrollManagementList = payrollManagementRepository.findAll();
        assertThat(payrollManagementList).hasSize(databaseSizeBeforeUpdate);
        PayrollManagement testPayrollManagement = payrollManagementList.get(payrollManagementList.size() - 1);

        // Validate the PayrollManagement in Elasticsearch
        verify(mockPayrollManagementSearchRepository, times(1)).save(testPayrollManagement);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollManagement() throws Exception {
        int databaseSizeBeforeUpdate = payrollManagementRepository.findAll().size();

        // Create the PayrollManagement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollManagementMockMvc.perform(put("/api/payroll-managements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollManagement)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollManagement in the database
        List<PayrollManagement> payrollManagementList = payrollManagementRepository.findAll();
        assertThat(payrollManagementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PayrollManagement in Elasticsearch
        verify(mockPayrollManagementSearchRepository, times(0)).save(payrollManagement);
    }

    @Test
    @Transactional
    public void deletePayrollManagement() throws Exception {
        // Initialize the database
        payrollManagementRepository.saveAndFlush(payrollManagement);

        int databaseSizeBeforeDelete = payrollManagementRepository.findAll().size();

        // Delete the payrollManagement
        restPayrollManagementMockMvc.perform(delete("/api/payroll-managements/{id}", payrollManagement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollManagement> payrollManagementList = payrollManagementRepository.findAll();
        assertThat(payrollManagementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PayrollManagement in Elasticsearch
        verify(mockPayrollManagementSearchRepository, times(1)).deleteById(payrollManagement.getId());
    }

    @Test
    @Transactional
    public void searchPayrollManagement() throws Exception {
        // Initialize the database
        payrollManagementRepository.saveAndFlush(payrollManagement);
        when(mockPayrollManagementSearchRepository.search(queryStringQuery("id:" + payrollManagement.getId())))
            .thenReturn(Collections.singletonList(payrollManagement));
        // Search the payrollManagement
        restPayrollManagementMockMvc.perform(get("/api/_search/payroll-managements?query=id:" + payrollManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollManagement.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollManagement.class);
        PayrollManagement payrollManagement1 = new PayrollManagement();
        payrollManagement1.setId(1L);
        PayrollManagement payrollManagement2 = new PayrollManagement();
        payrollManagement2.setId(payrollManagement1.getId());
        assertThat(payrollManagement1).isEqualTo(payrollManagement2);
        payrollManagement2.setId(2L);
        assertThat(payrollManagement1).isNotEqualTo(payrollManagement2);
        payrollManagement1.setId(null);
        assertThat(payrollManagement1).isNotEqualTo(payrollManagement2);
    }
}
