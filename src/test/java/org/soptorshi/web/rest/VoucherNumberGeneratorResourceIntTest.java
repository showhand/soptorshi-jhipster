package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.VoucherNumberGenerator;
import org.soptorshi.repository.VoucherNumberGeneratorRepository;
import org.soptorshi.repository.search.VoucherNumberGeneratorSearchRepository;
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

import org.soptorshi.domain.enumeration.VoucherType;
/**
 * Test class for the VoucherNumberGeneratorResource REST controller.
 *
 * @see VoucherNumberGeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class VoucherNumberGeneratorResourceIntTest {

    private static final VoucherType DEFAULT_VOUCHER_TYPE = VoucherType.SELLING;
    private static final VoucherType UPDATED_VOUCHER_TYPE = VoucherType.BUYING;

    private static final Integer DEFAULT_VOUCHER_NUMBER = 1;
    private static final Integer UPDATED_VOUCHER_NUMBER = 2;

    @Autowired
    private VoucherNumberGeneratorRepository voucherNumberGeneratorRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.VoucherNumberGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private VoucherNumberGeneratorSearchRepository mockVoucherNumberGeneratorSearchRepository;

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

    private MockMvc restVoucherNumberGeneratorMockMvc;

    private VoucherNumberGenerator voucherNumberGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherNumberGeneratorResource voucherNumberGeneratorResource = new VoucherNumberGeneratorResource(voucherNumberGeneratorRepository, mockVoucherNumberGeneratorSearchRepository);
        this.restVoucherNumberGeneratorMockMvc = MockMvcBuilders.standaloneSetup(voucherNumberGeneratorResource)
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
    public static VoucherNumberGenerator createEntity(EntityManager em) {
        VoucherNumberGenerator voucherNumberGenerator = new VoucherNumberGenerator()
            .voucherType(DEFAULT_VOUCHER_TYPE)
            .voucherNumber(DEFAULT_VOUCHER_NUMBER);
        return voucherNumberGenerator;
    }

    @Before
    public void initTest() {
        voucherNumberGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherNumberGenerator() throws Exception {
        int databaseSizeBeforeCreate = voucherNumberGeneratorRepository.findAll().size();

        // Create the VoucherNumberGenerator
        restVoucherNumberGeneratorMockMvc.perform(post("/api/voucher-number-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberGenerator)))
            .andExpect(status().isCreated());

        // Validate the VoucherNumberGenerator in the database
        List<VoucherNumberGenerator> voucherNumberGeneratorList = voucherNumberGeneratorRepository.findAll();
        assertThat(voucherNumberGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherNumberGenerator testVoucherNumberGenerator = voucherNumberGeneratorList.get(voucherNumberGeneratorList.size() - 1);
        assertThat(testVoucherNumberGenerator.getVoucherType()).isEqualTo(DEFAULT_VOUCHER_TYPE);
        assertThat(testVoucherNumberGenerator.getVoucherNumber()).isEqualTo(DEFAULT_VOUCHER_NUMBER);

        // Validate the VoucherNumberGenerator in Elasticsearch
        verify(mockVoucherNumberGeneratorSearchRepository, times(1)).save(testVoucherNumberGenerator);
    }

    @Test
    @Transactional
    public void createVoucherNumberGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherNumberGeneratorRepository.findAll().size();

        // Create the VoucherNumberGenerator with an existing ID
        voucherNumberGenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherNumberGeneratorMockMvc.perform(post("/api/voucher-number-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherNumberGenerator in the database
        List<VoucherNumberGenerator> voucherNumberGeneratorList = voucherNumberGeneratorRepository.findAll();
        assertThat(voucherNumberGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the VoucherNumberGenerator in Elasticsearch
        verify(mockVoucherNumberGeneratorSearchRepository, times(0)).save(voucherNumberGenerator);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberGenerators() throws Exception {
        // Initialize the database
        voucherNumberGeneratorRepository.saveAndFlush(voucherNumberGenerator);

        // Get all the voucherNumberGeneratorList
        restVoucherNumberGeneratorMockMvc.perform(get("/api/voucher-number-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherNumberGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherType").value(hasItem(DEFAULT_VOUCHER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].voucherNumber").value(hasItem(DEFAULT_VOUCHER_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getVoucherNumberGenerator() throws Exception {
        // Initialize the database
        voucherNumberGeneratorRepository.saveAndFlush(voucherNumberGenerator);

        // Get the voucherNumberGenerator
        restVoucherNumberGeneratorMockMvc.perform(get("/api/voucher-number-generators/{id}", voucherNumberGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherNumberGenerator.getId().intValue()))
            .andExpect(jsonPath("$.voucherType").value(DEFAULT_VOUCHER_TYPE.toString()))
            .andExpect(jsonPath("$.voucherNumber").value(DEFAULT_VOUCHER_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingVoucherNumberGenerator() throws Exception {
        // Get the voucherNumberGenerator
        restVoucherNumberGeneratorMockMvc.perform(get("/api/voucher-number-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherNumberGenerator() throws Exception {
        // Initialize the database
        voucherNumberGeneratorRepository.saveAndFlush(voucherNumberGenerator);

        int databaseSizeBeforeUpdate = voucherNumberGeneratorRepository.findAll().size();

        // Update the voucherNumberGenerator
        VoucherNumberGenerator updatedVoucherNumberGenerator = voucherNumberGeneratorRepository.findById(voucherNumberGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherNumberGenerator are not directly saved in db
        em.detach(updatedVoucherNumberGenerator);
        updatedVoucherNumberGenerator
            .voucherType(UPDATED_VOUCHER_TYPE)
            .voucherNumber(UPDATED_VOUCHER_NUMBER);

        restVoucherNumberGeneratorMockMvc.perform(put("/api/voucher-number-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoucherNumberGenerator)))
            .andExpect(status().isOk());

        // Validate the VoucherNumberGenerator in the database
        List<VoucherNumberGenerator> voucherNumberGeneratorList = voucherNumberGeneratorRepository.findAll();
        assertThat(voucherNumberGeneratorList).hasSize(databaseSizeBeforeUpdate);
        VoucherNumberGenerator testVoucherNumberGenerator = voucherNumberGeneratorList.get(voucherNumberGeneratorList.size() - 1);
        assertThat(testVoucherNumberGenerator.getVoucherType()).isEqualTo(UPDATED_VOUCHER_TYPE);
        assertThat(testVoucherNumberGenerator.getVoucherNumber()).isEqualTo(UPDATED_VOUCHER_NUMBER);

        // Validate the VoucherNumberGenerator in Elasticsearch
        verify(mockVoucherNumberGeneratorSearchRepository, times(1)).save(testVoucherNumberGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherNumberGenerator() throws Exception {
        int databaseSizeBeforeUpdate = voucherNumberGeneratorRepository.findAll().size();

        // Create the VoucherNumberGenerator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherNumberGeneratorMockMvc.perform(put("/api/voucher-number-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherNumberGenerator in the database
        List<VoucherNumberGenerator> voucherNumberGeneratorList = voucherNumberGeneratorRepository.findAll();
        assertThat(voucherNumberGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VoucherNumberGenerator in Elasticsearch
        verify(mockVoucherNumberGeneratorSearchRepository, times(0)).save(voucherNumberGenerator);
    }

    @Test
    @Transactional
    public void deleteVoucherNumberGenerator() throws Exception {
        // Initialize the database
        voucherNumberGeneratorRepository.saveAndFlush(voucherNumberGenerator);

        int databaseSizeBeforeDelete = voucherNumberGeneratorRepository.findAll().size();

        // Delete the voucherNumberGenerator
        restVoucherNumberGeneratorMockMvc.perform(delete("/api/voucher-number-generators/{id}", voucherNumberGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherNumberGenerator> voucherNumberGeneratorList = voucherNumberGeneratorRepository.findAll();
        assertThat(voucherNumberGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VoucherNumberGenerator in Elasticsearch
        verify(mockVoucherNumberGeneratorSearchRepository, times(1)).deleteById(voucherNumberGenerator.getId());
    }

    @Test
    @Transactional
    public void searchVoucherNumberGenerator() throws Exception {
        // Initialize the database
        voucherNumberGeneratorRepository.saveAndFlush(voucherNumberGenerator);
        when(mockVoucherNumberGeneratorSearchRepository.search(queryStringQuery("id:" + voucherNumberGenerator.getId())))
            .thenReturn(Collections.singletonList(voucherNumberGenerator));
        // Search the voucherNumberGenerator
        restVoucherNumberGeneratorMockMvc.perform(get("/api/_search/voucher-number-generators?query=id:" + voucherNumberGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherNumberGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherType").value(hasItem(DEFAULT_VOUCHER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].voucherNumber").value(hasItem(DEFAULT_VOUCHER_NUMBER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherNumberGenerator.class);
        VoucherNumberGenerator voucherNumberGenerator1 = new VoucherNumberGenerator();
        voucherNumberGenerator1.setId(1L);
        VoucherNumberGenerator voucherNumberGenerator2 = new VoucherNumberGenerator();
        voucherNumberGenerator2.setId(voucherNumberGenerator1.getId());
        assertThat(voucherNumberGenerator1).isEqualTo(voucherNumberGenerator2);
        voucherNumberGenerator2.setId(2L);
        assertThat(voucherNumberGenerator1).isNotEqualTo(voucherNumberGenerator2);
        voucherNumberGenerator1.setId(null);
        assertThat(voucherNumberGenerator1).isNotEqualTo(voucherNumberGenerator2);
    }
}
