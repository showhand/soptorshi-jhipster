package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.JournalVoucherGenerator;
import org.soptorshi.repository.JournalVoucherGeneratorRepository;
import org.soptorshi.repository.search.JournalVoucherGeneratorSearchRepository;
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
 * Test class for the JournalVoucherGeneratorResource REST controller.
 *
 * @see JournalVoucherGeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class JournalVoucherGeneratorResourceIntTest {

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.JournalVoucherGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private JournalVoucherGeneratorSearchRepository mockJournalVoucherGeneratorSearchRepository;

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

    private MockMvc restJournalVoucherGeneratorMockMvc;

    private JournalVoucherGenerator journalVoucherGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JournalVoucherGeneratorResource journalVoucherGeneratorResource = new JournalVoucherGeneratorResource(journalVoucherGeneratorRepository, mockJournalVoucherGeneratorSearchRepository);
        this.restJournalVoucherGeneratorMockMvc = MockMvcBuilders.standaloneSetup(journalVoucherGeneratorResource)
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
    public static JournalVoucherGenerator createEntity(EntityManager em) {
        JournalVoucherGenerator journalVoucherGenerator = new JournalVoucherGenerator()
            .lastModified(DEFAULT_LAST_MODIFIED);
        return journalVoucherGenerator;
    }

    @Before
    public void initTest() {
        journalVoucherGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournalVoucherGenerator() throws Exception {
        int databaseSizeBeforeCreate = journalVoucherGeneratorRepository.findAll().size();

        // Create the JournalVoucherGenerator
        restJournalVoucherGeneratorMockMvc.perform(post("/api/journal-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherGenerator)))
            .andExpect(status().isCreated());

        // Validate the JournalVoucherGenerator in the database
        List<JournalVoucherGenerator> journalVoucherGeneratorList = journalVoucherGeneratorRepository.findAll();
        assertThat(journalVoucherGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        JournalVoucherGenerator testJournalVoucherGenerator = journalVoucherGeneratorList.get(journalVoucherGeneratorList.size() - 1);
        assertThat(testJournalVoucherGenerator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the JournalVoucherGenerator in Elasticsearch
        verify(mockJournalVoucherGeneratorSearchRepository, times(1)).save(testJournalVoucherGenerator);
    }

    @Test
    @Transactional
    public void createJournalVoucherGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journalVoucherGeneratorRepository.findAll().size();

        // Create the JournalVoucherGenerator with an existing ID
        journalVoucherGenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJournalVoucherGeneratorMockMvc.perform(post("/api/journal-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the JournalVoucherGenerator in the database
        List<JournalVoucherGenerator> journalVoucherGeneratorList = journalVoucherGeneratorRepository.findAll();
        assertThat(journalVoucherGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the JournalVoucherGenerator in Elasticsearch
        verify(mockJournalVoucherGeneratorSearchRepository, times(0)).save(journalVoucherGenerator);
    }

    @Test
    @Transactional
    public void getAllJournalVoucherGenerators() throws Exception {
        // Initialize the database
        journalVoucherGeneratorRepository.saveAndFlush(journalVoucherGenerator);

        // Get all the journalVoucherGeneratorList
        restJournalVoucherGeneratorMockMvc.perform(get("/api/journal-voucher-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getJournalVoucherGenerator() throws Exception {
        // Initialize the database
        journalVoucherGeneratorRepository.saveAndFlush(journalVoucherGenerator);

        // Get the journalVoucherGenerator
        restJournalVoucherGeneratorMockMvc.perform(get("/api/journal-voucher-generators/{id}", journalVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journalVoucherGenerator.getId().intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJournalVoucherGenerator() throws Exception {
        // Get the journalVoucherGenerator
        restJournalVoucherGeneratorMockMvc.perform(get("/api/journal-voucher-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournalVoucherGenerator() throws Exception {
        // Initialize the database
        journalVoucherGeneratorRepository.saveAndFlush(journalVoucherGenerator);

        int databaseSizeBeforeUpdate = journalVoucherGeneratorRepository.findAll().size();

        // Update the journalVoucherGenerator
        JournalVoucherGenerator updatedJournalVoucherGenerator = journalVoucherGeneratorRepository.findById(journalVoucherGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedJournalVoucherGenerator are not directly saved in db
        em.detach(updatedJournalVoucherGenerator);
        updatedJournalVoucherGenerator
            .lastModified(UPDATED_LAST_MODIFIED);

        restJournalVoucherGeneratorMockMvc.perform(put("/api/journal-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJournalVoucherGenerator)))
            .andExpect(status().isOk());

        // Validate the JournalVoucherGenerator in the database
        List<JournalVoucherGenerator> journalVoucherGeneratorList = journalVoucherGeneratorRepository.findAll();
        assertThat(journalVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);
        JournalVoucherGenerator testJournalVoucherGenerator = journalVoucherGeneratorList.get(journalVoucherGeneratorList.size() - 1);
        assertThat(testJournalVoucherGenerator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the JournalVoucherGenerator in Elasticsearch
        verify(mockJournalVoucherGeneratorSearchRepository, times(1)).save(testJournalVoucherGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingJournalVoucherGenerator() throws Exception {
        int databaseSizeBeforeUpdate = journalVoucherGeneratorRepository.findAll().size();

        // Create the JournalVoucherGenerator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJournalVoucherGeneratorMockMvc.perform(put("/api/journal-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journalVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the JournalVoucherGenerator in the database
        List<JournalVoucherGenerator> journalVoucherGeneratorList = journalVoucherGeneratorRepository.findAll();
        assertThat(journalVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the JournalVoucherGenerator in Elasticsearch
        verify(mockJournalVoucherGeneratorSearchRepository, times(0)).save(journalVoucherGenerator);
    }

    @Test
    @Transactional
    public void deleteJournalVoucherGenerator() throws Exception {
        // Initialize the database
        journalVoucherGeneratorRepository.saveAndFlush(journalVoucherGenerator);

        int databaseSizeBeforeDelete = journalVoucherGeneratorRepository.findAll().size();

        // Delete the journalVoucherGenerator
        restJournalVoucherGeneratorMockMvc.perform(delete("/api/journal-voucher-generators/{id}", journalVoucherGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JournalVoucherGenerator> journalVoucherGeneratorList = journalVoucherGeneratorRepository.findAll();
        assertThat(journalVoucherGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the JournalVoucherGenerator in Elasticsearch
        verify(mockJournalVoucherGeneratorSearchRepository, times(1)).deleteById(journalVoucherGenerator.getId());
    }

    @Test
    @Transactional
    public void searchJournalVoucherGenerator() throws Exception {
        // Initialize the database
        journalVoucherGeneratorRepository.saveAndFlush(journalVoucherGenerator);
        when(mockJournalVoucherGeneratorSearchRepository.search(queryStringQuery("id:" + journalVoucherGenerator.getId())))
            .thenReturn(Collections.singletonList(journalVoucherGenerator));
        // Search the journalVoucherGenerator
        restJournalVoucherGeneratorMockMvc.perform(get("/api/_search/journal-voucher-generators?query=id:" + journalVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journalVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JournalVoucherGenerator.class);
        JournalVoucherGenerator journalVoucherGenerator1 = new JournalVoucherGenerator();
        journalVoucherGenerator1.setId(1L);
        JournalVoucherGenerator journalVoucherGenerator2 = new JournalVoucherGenerator();
        journalVoucherGenerator2.setId(journalVoucherGenerator1.getId());
        assertThat(journalVoucherGenerator1).isEqualTo(journalVoucherGenerator2);
        journalVoucherGenerator2.setId(2L);
        assertThat(journalVoucherGenerator1).isNotEqualTo(journalVoucherGenerator2);
        journalVoucherGenerator1.setId(null);
        assertThat(journalVoucherGenerator1).isNotEqualTo(journalVoucherGenerator2);
    }
}
