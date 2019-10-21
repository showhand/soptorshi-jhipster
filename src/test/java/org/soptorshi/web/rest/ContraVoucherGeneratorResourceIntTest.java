package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ContraVoucherGenerator;
import org.soptorshi.repository.ContraVoucherGeneratorRepository;
import org.soptorshi.repository.search.ContraVoucherGeneratorSearchRepository;
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

/**
 * Test class for the ContraVoucherGeneratorResource REST controller.
 *
 * @see ContraVoucherGeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ContraVoucherGeneratorResourceIntTest {

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ContraVoucherGeneratorRepository contraVoucherGeneratorRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ContraVoucherGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContraVoucherGeneratorSearchRepository mockContraVoucherGeneratorSearchRepository;

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

    private MockMvc restContraVoucherGeneratorMockMvc;

    private ContraVoucherGenerator contraVoucherGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContraVoucherGeneratorResource contraVoucherGeneratorResource = new ContraVoucherGeneratorResource(contraVoucherGeneratorRepository, mockContraVoucherGeneratorSearchRepository);
        this.restContraVoucherGeneratorMockMvc = MockMvcBuilders.standaloneSetup(contraVoucherGeneratorResource)
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
    public static ContraVoucherGenerator createEntity(EntityManager em) {
        ContraVoucherGenerator contraVoucherGenerator = new ContraVoucherGenerator()
            .lastModified(DEFAULT_LAST_MODIFIED);
        return contraVoucherGenerator;
    }

    @Before
    public void initTest() {
        contraVoucherGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createContraVoucherGenerator() throws Exception {
        int databaseSizeBeforeCreate = contraVoucherGeneratorRepository.findAll().size();

        // Create the ContraVoucherGenerator
        restContraVoucherGeneratorMockMvc.perform(post("/api/contra-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherGenerator)))
            .andExpect(status().isCreated());

        // Validate the ContraVoucherGenerator in the database
        List<ContraVoucherGenerator> contraVoucherGeneratorList = contraVoucherGeneratorRepository.findAll();
        assertThat(contraVoucherGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        ContraVoucherGenerator testContraVoucherGenerator = contraVoucherGeneratorList.get(contraVoucherGeneratorList.size() - 1);
        assertThat(testContraVoucherGenerator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the ContraVoucherGenerator in Elasticsearch
        verify(mockContraVoucherGeneratorSearchRepository, times(1)).save(testContraVoucherGenerator);
    }

    @Test
    @Transactional
    public void createContraVoucherGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contraVoucherGeneratorRepository.findAll().size();

        // Create the ContraVoucherGenerator with an existing ID
        contraVoucherGenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContraVoucherGeneratorMockMvc.perform(post("/api/contra-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the ContraVoucherGenerator in the database
        List<ContraVoucherGenerator> contraVoucherGeneratorList = contraVoucherGeneratorRepository.findAll();
        assertThat(contraVoucherGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContraVoucherGenerator in Elasticsearch
        verify(mockContraVoucherGeneratorSearchRepository, times(0)).save(contraVoucherGenerator);
    }

    @Test
    @Transactional
    public void getAllContraVoucherGenerators() throws Exception {
        // Initialize the database
        contraVoucherGeneratorRepository.saveAndFlush(contraVoucherGenerator);

        // Get all the contraVoucherGeneratorList
        restContraVoucherGeneratorMockMvc.perform(get("/api/contra-voucher-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contraVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getContraVoucherGenerator() throws Exception {
        // Initialize the database
        contraVoucherGeneratorRepository.saveAndFlush(contraVoucherGenerator);

        // Get the contraVoucherGenerator
        restContraVoucherGeneratorMockMvc.perform(get("/api/contra-voucher-generators/{id}", contraVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contraVoucherGenerator.getId().intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContraVoucherGenerator() throws Exception {
        // Get the contraVoucherGenerator
        restContraVoucherGeneratorMockMvc.perform(get("/api/contra-voucher-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContraVoucherGenerator() throws Exception {
        // Initialize the database
        contraVoucherGeneratorRepository.saveAndFlush(contraVoucherGenerator);

        int databaseSizeBeforeUpdate = contraVoucherGeneratorRepository.findAll().size();

        // Update the contraVoucherGenerator
        ContraVoucherGenerator updatedContraVoucherGenerator = contraVoucherGeneratorRepository.findById(contraVoucherGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedContraVoucherGenerator are not directly saved in db
        em.detach(updatedContraVoucherGenerator);
        updatedContraVoucherGenerator
            .lastModified(UPDATED_LAST_MODIFIED);

        restContraVoucherGeneratorMockMvc.perform(put("/api/contra-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContraVoucherGenerator)))
            .andExpect(status().isOk());

        // Validate the ContraVoucherGenerator in the database
        List<ContraVoucherGenerator> contraVoucherGeneratorList = contraVoucherGeneratorRepository.findAll();
        assertThat(contraVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);
        ContraVoucherGenerator testContraVoucherGenerator = contraVoucherGeneratorList.get(contraVoucherGeneratorList.size() - 1);
        assertThat(testContraVoucherGenerator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the ContraVoucherGenerator in Elasticsearch
        verify(mockContraVoucherGeneratorSearchRepository, times(1)).save(testContraVoucherGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingContraVoucherGenerator() throws Exception {
        int databaseSizeBeforeUpdate = contraVoucherGeneratorRepository.findAll().size();

        // Create the ContraVoucherGenerator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContraVoucherGeneratorMockMvc.perform(put("/api/contra-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the ContraVoucherGenerator in the database
        List<ContraVoucherGenerator> contraVoucherGeneratorList = contraVoucherGeneratorRepository.findAll();
        assertThat(contraVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContraVoucherGenerator in Elasticsearch
        verify(mockContraVoucherGeneratorSearchRepository, times(0)).save(contraVoucherGenerator);
    }

    @Test
    @Transactional
    public void deleteContraVoucherGenerator() throws Exception {
        // Initialize the database
        contraVoucherGeneratorRepository.saveAndFlush(contraVoucherGenerator);

        int databaseSizeBeforeDelete = contraVoucherGeneratorRepository.findAll().size();

        // Delete the contraVoucherGenerator
        restContraVoucherGeneratorMockMvc.perform(delete("/api/contra-voucher-generators/{id}", contraVoucherGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContraVoucherGenerator> contraVoucherGeneratorList = contraVoucherGeneratorRepository.findAll();
        assertThat(contraVoucherGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContraVoucherGenerator in Elasticsearch
        verify(mockContraVoucherGeneratorSearchRepository, times(1)).deleteById(contraVoucherGenerator.getId());
    }

    @Test
    @Transactional
    public void searchContraVoucherGenerator() throws Exception {
        // Initialize the database
        contraVoucherGeneratorRepository.saveAndFlush(contraVoucherGenerator);
        when(mockContraVoucherGeneratorSearchRepository.search(queryStringQuery("id:" + contraVoucherGenerator.getId())))
            .thenReturn(Collections.singletonList(contraVoucherGenerator));
        // Search the contraVoucherGenerator
        restContraVoucherGeneratorMockMvc.perform(get("/api/_search/contra-voucher-generators?query=id:" + contraVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contraVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContraVoucherGenerator.class);
        ContraVoucherGenerator contraVoucherGenerator1 = new ContraVoucherGenerator();
        contraVoucherGenerator1.setId(1L);
        ContraVoucherGenerator contraVoucherGenerator2 = new ContraVoucherGenerator();
        contraVoucherGenerator2.setId(contraVoucherGenerator1.getId());
        assertThat(contraVoucherGenerator1).isEqualTo(contraVoucherGenerator2);
        contraVoucherGenerator2.setId(2L);
        assertThat(contraVoucherGenerator1).isNotEqualTo(contraVoucherGenerator2);
        contraVoucherGenerator1.setId(null);
        assertThat(contraVoucherGenerator1).isNotEqualTo(contraVoucherGenerator2);
    }
}
