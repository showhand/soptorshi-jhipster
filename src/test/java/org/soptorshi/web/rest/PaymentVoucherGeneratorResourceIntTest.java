package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PaymentVoucherGenerator;
import org.soptorshi.repository.PaymentVoucherGeneratorRepository;
import org.soptorshi.repository.search.PaymentVoucherGeneratorSearchRepository;
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
 * Test class for the PaymentVoucherGeneratorResource REST controller.
 *
 * @see PaymentVoucherGeneratorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PaymentVoucherGeneratorResourceIntTest {

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PaymentVoucherGeneratorSearchRepositoryMockConfiguration
     */
    @Autowired
    private PaymentVoucherGeneratorSearchRepository mockPaymentVoucherGeneratorSearchRepository;

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

    private MockMvc restPaymentVoucherGeneratorMockMvc;

    private PaymentVoucherGenerator paymentVoucherGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentVoucherGeneratorResource paymentVoucherGeneratorResource = new PaymentVoucherGeneratorResource(paymentVoucherGeneratorRepository, mockPaymentVoucherGeneratorSearchRepository);
        this.restPaymentVoucherGeneratorMockMvc = MockMvcBuilders.standaloneSetup(paymentVoucherGeneratorResource)
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
    public static PaymentVoucherGenerator createEntity(EntityManager em) {
        PaymentVoucherGenerator paymentVoucherGenerator = new PaymentVoucherGenerator()
            .lastModified(DEFAULT_LAST_MODIFIED);
        return paymentVoucherGenerator;
    }

    @Before
    public void initTest() {
        paymentVoucherGenerator = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentVoucherGenerator() throws Exception {
        int databaseSizeBeforeCreate = paymentVoucherGeneratorRepository.findAll().size();

        // Create the PaymentVoucherGenerator
        restPaymentVoucherGeneratorMockMvc.perform(post("/api/payment-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherGenerator)))
            .andExpect(status().isCreated());

        // Validate the PaymentVoucherGenerator in the database
        List<PaymentVoucherGenerator> paymentVoucherGeneratorList = paymentVoucherGeneratorRepository.findAll();
        assertThat(paymentVoucherGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentVoucherGenerator testPaymentVoucherGenerator = paymentVoucherGeneratorList.get(paymentVoucherGeneratorList.size() - 1);
        assertThat(testPaymentVoucherGenerator.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);

        // Validate the PaymentVoucherGenerator in Elasticsearch
        verify(mockPaymentVoucherGeneratorSearchRepository, times(1)).save(testPaymentVoucherGenerator);
    }

    @Test
    @Transactional
    public void createPaymentVoucherGeneratorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentVoucherGeneratorRepository.findAll().size();

        // Create the PaymentVoucherGenerator with an existing ID
        paymentVoucherGenerator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentVoucherGeneratorMockMvc.perform(post("/api/payment-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentVoucherGenerator in the database
        List<PaymentVoucherGenerator> paymentVoucherGeneratorList = paymentVoucherGeneratorRepository.findAll();
        assertThat(paymentVoucherGeneratorList).hasSize(databaseSizeBeforeCreate);

        // Validate the PaymentVoucherGenerator in Elasticsearch
        verify(mockPaymentVoucherGeneratorSearchRepository, times(0)).save(paymentVoucherGenerator);
    }

    @Test
    @Transactional
    public void getAllPaymentVoucherGenerators() throws Exception {
        // Initialize the database
        paymentVoucherGeneratorRepository.saveAndFlush(paymentVoucherGenerator);

        // Get all the paymentVoucherGeneratorList
        restPaymentVoucherGeneratorMockMvc.perform(get("/api/payment-voucher-generators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentVoucherGenerator() throws Exception {
        // Initialize the database
        paymentVoucherGeneratorRepository.saveAndFlush(paymentVoucherGenerator);

        // Get the paymentVoucherGenerator
        restPaymentVoucherGeneratorMockMvc.perform(get("/api/payment-voucher-generators/{id}", paymentVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentVoucherGenerator.getId().intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentVoucherGenerator() throws Exception {
        // Get the paymentVoucherGenerator
        restPaymentVoucherGeneratorMockMvc.perform(get("/api/payment-voucher-generators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentVoucherGenerator() throws Exception {
        // Initialize the database
        paymentVoucherGeneratorRepository.saveAndFlush(paymentVoucherGenerator);

        int databaseSizeBeforeUpdate = paymentVoucherGeneratorRepository.findAll().size();

        // Update the paymentVoucherGenerator
        PaymentVoucherGenerator updatedPaymentVoucherGenerator = paymentVoucherGeneratorRepository.findById(paymentVoucherGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentVoucherGenerator are not directly saved in db
        em.detach(updatedPaymentVoucherGenerator);
        updatedPaymentVoucherGenerator
            .lastModified(UPDATED_LAST_MODIFIED);

        restPaymentVoucherGeneratorMockMvc.perform(put("/api/payment-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentVoucherGenerator)))
            .andExpect(status().isOk());

        // Validate the PaymentVoucherGenerator in the database
        List<PaymentVoucherGenerator> paymentVoucherGeneratorList = paymentVoucherGeneratorRepository.findAll();
        assertThat(paymentVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);
        PaymentVoucherGenerator testPaymentVoucherGenerator = paymentVoucherGeneratorList.get(paymentVoucherGeneratorList.size() - 1);
        assertThat(testPaymentVoucherGenerator.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);

        // Validate the PaymentVoucherGenerator in Elasticsearch
        verify(mockPaymentVoucherGeneratorSearchRepository, times(1)).save(testPaymentVoucherGenerator);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentVoucherGenerator() throws Exception {
        int databaseSizeBeforeUpdate = paymentVoucherGeneratorRepository.findAll().size();

        // Create the PaymentVoucherGenerator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentVoucherGeneratorMockMvc.perform(put("/api/payment-voucher-generators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentVoucherGenerator)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentVoucherGenerator in the database
        List<PaymentVoucherGenerator> paymentVoucherGeneratorList = paymentVoucherGeneratorRepository.findAll();
        assertThat(paymentVoucherGeneratorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PaymentVoucherGenerator in Elasticsearch
        verify(mockPaymentVoucherGeneratorSearchRepository, times(0)).save(paymentVoucherGenerator);
    }

    @Test
    @Transactional
    public void deletePaymentVoucherGenerator() throws Exception {
        // Initialize the database
        paymentVoucherGeneratorRepository.saveAndFlush(paymentVoucherGenerator);

        int databaseSizeBeforeDelete = paymentVoucherGeneratorRepository.findAll().size();

        // Delete the paymentVoucherGenerator
        restPaymentVoucherGeneratorMockMvc.perform(delete("/api/payment-voucher-generators/{id}", paymentVoucherGenerator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentVoucherGenerator> paymentVoucherGeneratorList = paymentVoucherGeneratorRepository.findAll();
        assertThat(paymentVoucherGeneratorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PaymentVoucherGenerator in Elasticsearch
        verify(mockPaymentVoucherGeneratorSearchRepository, times(1)).deleteById(paymentVoucherGenerator.getId());
    }

    @Test
    @Transactional
    public void searchPaymentVoucherGenerator() throws Exception {
        // Initialize the database
        paymentVoucherGeneratorRepository.saveAndFlush(paymentVoucherGenerator);
        when(mockPaymentVoucherGeneratorSearchRepository.search(queryStringQuery("id:" + paymentVoucherGenerator.getId())))
            .thenReturn(Collections.singletonList(paymentVoucherGenerator));
        // Search the paymentVoucherGenerator
        restPaymentVoucherGeneratorMockMvc.perform(get("/api/_search/payment-voucher-generators?query=id:" + paymentVoucherGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentVoucherGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentVoucherGenerator.class);
        PaymentVoucherGenerator paymentVoucherGenerator1 = new PaymentVoucherGenerator();
        paymentVoucherGenerator1.setId(1L);
        PaymentVoucherGenerator paymentVoucherGenerator2 = new PaymentVoucherGenerator();
        paymentVoucherGenerator2.setId(paymentVoucherGenerator1.getId());
        assertThat(paymentVoucherGenerator1).isEqualTo(paymentVoucherGenerator2);
        paymentVoucherGenerator2.setId(2L);
        assertThat(paymentVoucherGenerator1).isNotEqualTo(paymentVoucherGenerator2);
        paymentVoucherGenerator1.setId(null);
        assertThat(paymentVoucherGenerator1).isNotEqualTo(paymentVoucherGenerator2);
    }
}
