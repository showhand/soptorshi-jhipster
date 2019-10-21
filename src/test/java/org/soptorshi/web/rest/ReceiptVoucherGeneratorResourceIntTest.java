package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.soptorshi.repository.ReceiptVoucherGeneratorRepository;
import org.soptorshi.repository.search.ReceiptVoucherGeneratorSearchRepository;
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
 * Test class for the ReceiptVoucherGeneratorResource REST controller.
 *
 * @see ReceiptVoucherGeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ReceiptVoucherGeneratorResourceIntTest {

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReceiptVoucherGeneratorRepository receiptVoucherGeneratorRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ReceiptVoucherGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReceiptVoucherGeneratorSearchRepository mockReceiptVoucherGeneratorSearchRepository;

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

    private MockMvc restReceiptVoucherGeneratorMockMvc;

    private ReceiptVoucherGenerator receiptVoucherGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReceiptVoucherGeneratorResource receiptVoucherGeneratorResource = new ReceiptVoucherGeneratorResource(receiptVoucherGeneratorRepository, mockReceiptVoucherGeneratorSearchRepository);
        this.restReceiptVoucherGeneratorMockMvc = MockMvcBuilders.standaloneSetup(receiptVoucherGeneratorResource)
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
    public static ReceiptVoucherGenerator createEntity(EntityManager em) {
        ReceiptVoucherGenerator receiptVoucherGenerator = new ReceiptVoucherGenerator()
            .lastModified(DEFAULT_LAST_MODIFIED);
        return receiptVoucherGenerator;
    }

    @Before
    public void initTest() {
        receiptVoucherGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceiptVoucherGenerator() throws Exception {
        int databaseSizeBeforeCreate = receiptVoucherGeneratorRepository.findAll().size();

        // Create the ReceiptVoucherGenerator
        restReceiptVoucherGeneratorMockMvc.perform(post("/api/receipt-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherGenerator)))
            .andExpect(status().isCreated());

        // Validate the ReceiptVoucherGenerator in the database
        List<ReceiptVoucherGenerator> receiptVoucherGeneratorList = receiptVoucherGeneratorRepository.findAll();
        assertThat(receiptVoucherGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiptVoucherGenerator testReceiptVoucherGenerator = receiptVoucherGeneratorList.get(receiptVoucherGeneratorList.size() - 1);
        assertThat(testReceiptVoucherGenerator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the ReceiptVoucherGenerator in Elasticsearch
        verify(mockReceiptVoucherGeneratorSearchRepository, times(1)).save(testReceiptVoucherGenerator);
    }

    @Test
    @Transactional
    public void createReceiptVoucherGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptVoucherGeneratorRepository.findAll().size();

        // Create the ReceiptVoucherGenerator with an existing ID
        receiptVoucherGenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptVoucherGeneratorMockMvc.perform(post("/api/receipt-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptVoucherGenerator in the database
        List<ReceiptVoucherGenerator> receiptVoucherGeneratorList = receiptVoucherGeneratorRepository.findAll();
        assertThat(receiptVoucherGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReceiptVoucherGenerator in Elasticsearch
        verify(mockReceiptVoucherGeneratorSearchRepository, times(0)).save(receiptVoucherGenerator);
    }

    @Test
    @Transactional
    public void getAllReceiptVoucherGenerators() throws Exception {
        // Initialize the database
        receiptVoucherGeneratorRepository.saveAndFlush(receiptVoucherGenerator);

        // Get all the receiptVoucherGeneratorList
        restReceiptVoucherGeneratorMockMvc.perform(get("/api/receipt-voucher-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getReceiptVoucherGenerator() throws Exception {
        // Initialize the database
        receiptVoucherGeneratorRepository.saveAndFlush(receiptVoucherGenerator);

        // Get the receiptVoucherGenerator
        restReceiptVoucherGeneratorMockMvc.perform(get("/api/receipt-voucher-generators/{id}", receiptVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receiptVoucherGenerator.getId().intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceiptVoucherGenerator() throws Exception {
        // Get the receiptVoucherGenerator
        restReceiptVoucherGeneratorMockMvc.perform(get("/api/receipt-voucher-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceiptVoucherGenerator() throws Exception {
        // Initialize the database
        receiptVoucherGeneratorRepository.saveAndFlush(receiptVoucherGenerator);

        int databaseSizeBeforeUpdate = receiptVoucherGeneratorRepository.findAll().size();

        // Update the receiptVoucherGenerator
        ReceiptVoucherGenerator updatedReceiptVoucherGenerator = receiptVoucherGeneratorRepository.findById(receiptVoucherGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedReceiptVoucherGenerator are not directly saved in db
        em.detach(updatedReceiptVoucherGenerator);
        updatedReceiptVoucherGenerator
            .lastModified(UPDATED_LAST_MODIFIED);

        restReceiptVoucherGeneratorMockMvc.perform(put("/api/receipt-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReceiptVoucherGenerator)))
            .andExpect(status().isOk());

        // Validate the ReceiptVoucherGenerator in the database
        List<ReceiptVoucherGenerator> receiptVoucherGeneratorList = receiptVoucherGeneratorRepository.findAll();
        assertThat(receiptVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);
        ReceiptVoucherGenerator testReceiptVoucherGenerator = receiptVoucherGeneratorList.get(receiptVoucherGeneratorList.size() - 1);
        assertThat(testReceiptVoucherGenerator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the ReceiptVoucherGenerator in Elasticsearch
        verify(mockReceiptVoucherGeneratorSearchRepository, times(1)).save(testReceiptVoucherGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingReceiptVoucherGenerator() throws Exception {
        int databaseSizeBeforeUpdate = receiptVoucherGeneratorRepository.findAll().size();

        // Create the ReceiptVoucherGenerator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptVoucherGeneratorMockMvc.perform(put("/api/receipt-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptVoucherGenerator in the database
        List<ReceiptVoucherGenerator> receiptVoucherGeneratorList = receiptVoucherGeneratorRepository.findAll();
        assertThat(receiptVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReceiptVoucherGenerator in Elasticsearch
        verify(mockReceiptVoucherGeneratorSearchRepository, times(0)).save(receiptVoucherGenerator);
    }

    @Test
    @Transactional
    public void deleteReceiptVoucherGenerator() throws Exception {
        // Initialize the database
        receiptVoucherGeneratorRepository.saveAndFlush(receiptVoucherGenerator);

        int databaseSizeBeforeDelete = receiptVoucherGeneratorRepository.findAll().size();

        // Delete the receiptVoucherGenerator
        restReceiptVoucherGeneratorMockMvc.perform(delete("/api/receipt-voucher-generators/{id}", receiptVoucherGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReceiptVoucherGenerator> receiptVoucherGeneratorList = receiptVoucherGeneratorRepository.findAll();
        assertThat(receiptVoucherGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReceiptVoucherGenerator in Elasticsearch
        verify(mockReceiptVoucherGeneratorSearchRepository, times(1)).deleteById(receiptVoucherGenerator.getId());
    }

    @Test
    @Transactional
    public void searchReceiptVoucherGenerator() throws Exception {
        // Initialize the database
        receiptVoucherGeneratorRepository.saveAndFlush(receiptVoucherGenerator);
        when(mockReceiptVoucherGeneratorSearchRepository.search(queryStringQuery("id:" + receiptVoucherGenerator.getId())))
            .thenReturn(Collections.singletonList(receiptVoucherGenerator));
        // Search the receiptVoucherGenerator
        restReceiptVoucherGeneratorMockMvc.perform(get("/api/_search/receipt-voucher-generators?query=id:" + receiptVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptVoucherGenerator.class);
        ReceiptVoucherGenerator receiptVoucherGenerator1 = new ReceiptVoucherGenerator();
        receiptVoucherGenerator1.setId(1L);
        ReceiptVoucherGenerator receiptVoucherGenerator2 = new ReceiptVoucherGenerator();
        receiptVoucherGenerator2.setId(receiptVoucherGenerator1.getId());
        assertThat(receiptVoucherGenerator1).isEqualTo(receiptVoucherGenerator2);
        receiptVoucherGenerator2.setId(2L);
        assertThat(receiptVoucherGenerator1).isNotEqualTo(receiptVoucherGenerator2);
        receiptVoucherGenerator1.setId(null);
        assertThat(receiptVoucherGenerator1).isNotEqualTo(receiptVoucherGenerator2);
    }
}
