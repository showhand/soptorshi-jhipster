package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.MontlySalaryVouchers;
import org.soptorshi.repository.MontlySalaryVouchersRepository;
import org.soptorshi.repository.search.MontlySalaryVouchersSearchRepository;
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
 * Test class for the MontlySalaryVouchersResource REST controller.
 *
 * @see MontlySalaryVouchersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class MontlySalaryVouchersResourceIntTest {

    @Autowired
    private MontlySalaryVouchersRepository montlySalaryVouchersRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.MontlySalaryVouchersSearchRepositoryMockConfiguration
     */
    @Autowired
    private MontlySalaryVouchersSearchRepository mockMontlySalaryVouchersSearchRepository;

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

    private MockMvc restMontlySalaryVouchersMockMvc;

    private MontlySalaryVouchers montlySalaryVouchers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MontlySalaryVouchersResource montlySalaryVouchersResource = new MontlySalaryVouchersResource(montlySalaryVouchersRepository, mockMontlySalaryVouchersSearchRepository);
        this.restMontlySalaryVouchersMockMvc = MockMvcBuilders.standaloneSetup(montlySalaryVouchersResource)
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
    public static MontlySalaryVouchers createEntity(EntityManager em) {
        MontlySalaryVouchers montlySalaryVouchers = new MontlySalaryVouchers();
        return montlySalaryVouchers;
    }

    @Before
    public void initTest() {
        montlySalaryVouchers = createEntity(em);
    }

    @Test
    @Transactional
    public void createMontlySalaryVouchers() throws Exception {
        int databaseSizeBeforeCreate = montlySalaryVouchersRepository.findAll().size();

        // Create the MontlySalaryVouchers
        restMontlySalaryVouchersMockMvc.perform(post("/api/montly-salary-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(montlySalaryVouchers)))
            .andExpect(status().isCreated());

        // Validate the MontlySalaryVouchers in the database
        List<MontlySalaryVouchers> montlySalaryVouchersList = montlySalaryVouchersRepository.findAll();
        assertThat(montlySalaryVouchersList).hasSize(databaseSizeBeforeCreate + 1);
        MontlySalaryVouchers testMontlySalaryVouchers = montlySalaryVouchersList.get(montlySalaryVouchersList.size() - 1);

        // Validate the MontlySalaryVouchers in Elasticsearch
        verify(mockMontlySalaryVouchersSearchRepository, times(1)).save(testMontlySalaryVouchers);
    }

    @Test
    @Transactional
    public void createMontlySalaryVouchersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = montlySalaryVouchersRepository.findAll().size();

        // Create the MontlySalaryVouchers with an existing ID
        montlySalaryVouchers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMontlySalaryVouchersMockMvc.perform(post("/api/montly-salary-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(montlySalaryVouchers)))
            .andExpect(status().isBadRequest());

        // Validate the MontlySalaryVouchers in the database
        List<MontlySalaryVouchers> montlySalaryVouchersList = montlySalaryVouchersRepository.findAll();
        assertThat(montlySalaryVouchersList).hasSize(databaseSizeBeforeCreate);

        // Validate the MontlySalaryVouchers in Elasticsearch
        verify(mockMontlySalaryVouchersSearchRepository, times(0)).save(montlySalaryVouchers);
    }

    @Test
    @Transactional
    public void getAllMontlySalaryVouchers() throws Exception {
        // Initialize the database
        montlySalaryVouchersRepository.saveAndFlush(montlySalaryVouchers);

        // Get all the montlySalaryVouchersList
        restMontlySalaryVouchersMockMvc.perform(get("/api/montly-salary-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(montlySalaryVouchers.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMontlySalaryVouchers() throws Exception {
        // Initialize the database
        montlySalaryVouchersRepository.saveAndFlush(montlySalaryVouchers);

        // Get the montlySalaryVouchers
        restMontlySalaryVouchersMockMvc.perform(get("/api/montly-salary-vouchers/{id}", montlySalaryVouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(montlySalaryVouchers.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMontlySalaryVouchers() throws Exception {
        // Get the montlySalaryVouchers
        restMontlySalaryVouchersMockMvc.perform(get("/api/montly-salary-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMontlySalaryVouchers() throws Exception {
        // Initialize the database
        montlySalaryVouchersRepository.saveAndFlush(montlySalaryVouchers);

        int databaseSizeBeforeUpdate = montlySalaryVouchersRepository.findAll().size();

        // Update the montlySalaryVouchers
        MontlySalaryVouchers updatedMontlySalaryVouchers = montlySalaryVouchersRepository.findById(montlySalaryVouchers.getId()).get();
        // Disconnect from session so that the updates on updatedMontlySalaryVouchers are not directly saved in db
        em.detach(updatedMontlySalaryVouchers);

        restMontlySalaryVouchersMockMvc.perform(put("/api/montly-salary-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMontlySalaryVouchers)))
            .andExpect(status().isOk());

        // Validate the MontlySalaryVouchers in the database
        List<MontlySalaryVouchers> montlySalaryVouchersList = montlySalaryVouchersRepository.findAll();
        assertThat(montlySalaryVouchersList).hasSize(databaseSizeBeforeUpdate);
        MontlySalaryVouchers testMontlySalaryVouchers = montlySalaryVouchersList.get(montlySalaryVouchersList.size() - 1);

        // Validate the MontlySalaryVouchers in Elasticsearch
        verify(mockMontlySalaryVouchersSearchRepository, times(1)).save(testMontlySalaryVouchers);
    }

    @Test
    @Transactional
    public void updateNonExistingMontlySalaryVouchers() throws Exception {
        int databaseSizeBeforeUpdate = montlySalaryVouchersRepository.findAll().size();

        // Create the MontlySalaryVouchers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMontlySalaryVouchersMockMvc.perform(put("/api/montly-salary-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(montlySalaryVouchers)))
            .andExpect(status().isBadRequest());

        // Validate the MontlySalaryVouchers in the database
        List<MontlySalaryVouchers> montlySalaryVouchersList = montlySalaryVouchersRepository.findAll();
        assertThat(montlySalaryVouchersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MontlySalaryVouchers in Elasticsearch
        verify(mockMontlySalaryVouchersSearchRepository, times(0)).save(montlySalaryVouchers);
    }

    @Test
    @Transactional
    public void deleteMontlySalaryVouchers() throws Exception {
        // Initialize the database
        montlySalaryVouchersRepository.saveAndFlush(montlySalaryVouchers);

        int databaseSizeBeforeDelete = montlySalaryVouchersRepository.findAll().size();

        // Delete the montlySalaryVouchers
        restMontlySalaryVouchersMockMvc.perform(delete("/api/montly-salary-vouchers/{id}", montlySalaryVouchers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MontlySalaryVouchers> montlySalaryVouchersList = montlySalaryVouchersRepository.findAll();
        assertThat(montlySalaryVouchersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MontlySalaryVouchers in Elasticsearch
        verify(mockMontlySalaryVouchersSearchRepository, times(1)).deleteById(montlySalaryVouchers.getId());
    }

    @Test
    @Transactional
    public void searchMontlySalaryVouchers() throws Exception {
        // Initialize the database
        montlySalaryVouchersRepository.saveAndFlush(montlySalaryVouchers);
        when(mockMontlySalaryVouchersSearchRepository.search(queryStringQuery("id:" + montlySalaryVouchers.getId())))
            .thenReturn(Collections.singletonList(montlySalaryVouchers));
        // Search the montlySalaryVouchers
        restMontlySalaryVouchersMockMvc.perform(get("/api/_search/montly-salary-vouchers?query=id:" + montlySalaryVouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(montlySalaryVouchers.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MontlySalaryVouchers.class);
        MontlySalaryVouchers montlySalaryVouchers1 = new MontlySalaryVouchers();
        montlySalaryVouchers1.setId(1L);
        MontlySalaryVouchers montlySalaryVouchers2 = new MontlySalaryVouchers();
        montlySalaryVouchers2.setId(montlySalaryVouchers1.getId());
        assertThat(montlySalaryVouchers1).isEqualTo(montlySalaryVouchers2);
        montlySalaryVouchers2.setId(2L);
        assertThat(montlySalaryVouchers1).isNotEqualTo(montlySalaryVouchers2);
        montlySalaryVouchers1.setId(null);
        assertThat(montlySalaryVouchers1).isNotEqualTo(montlySalaryVouchers2);
    }
}
