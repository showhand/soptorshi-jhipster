package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Tax;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.TaxRepository;
import org.soptorshi.repository.search.TaxSearchRepository;
import org.soptorshi.service.TaxService;
import org.soptorshi.service.dto.TaxDTO;
import org.soptorshi.service.mapper.TaxMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.TaxCriteria;
import org.soptorshi.service.TaxQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

import org.soptorshi.domain.enumeration.TaxStatus;
/**
 * Test class for the TaxResource REST controller.
 *
 * @see TaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class TaxResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final TaxStatus DEFAULT_TAX_STATUS = TaxStatus.ACTIVE;
    private static final TaxStatus UPDATED_TAX_STATUS = TaxStatus.INACTIVE;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxMapper taxMapper;

    @Autowired
    private TaxService taxService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.TaxSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaxSearchRepository mockTaxSearchRepository;

    @Autowired
    private TaxQueryService taxQueryService;

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

    private MockMvc restTaxMockMvc;

    private Tax tax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxResource taxResource = new TaxResource(taxService, taxQueryService);
        this.restTaxMockMvc = MockMvcBuilders.standaloneSetup(taxResource)
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
    public static Tax createEntity(EntityManager em) {
        Tax tax = new Tax()
            .amount(DEFAULT_AMOUNT)
            .taxStatus(DEFAULT_TAX_STATUS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return tax;
    }

    @Before
    public void initTest() {
        tax = createEntity(em);
    }

    @Test
    @Transactional
    public void createTax() throws Exception {
        int databaseSizeBeforeCreate = taxRepository.findAll().size();

        // Create the Tax
        TaxDTO taxDTO = taxMapper.toDto(tax);
        restTaxMockMvc.perform(post("/api/taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isCreated());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeCreate + 1);
        Tax testTax = taxList.get(taxList.size() - 1);
        assertThat(testTax.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTax.getTaxStatus()).isEqualTo(DEFAULT_TAX_STATUS);
        assertThat(testTax.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTax.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Tax in Elasticsearch
        verify(mockTaxSearchRepository, times(1)).save(testTax);
    }

    @Test
    @Transactional
    public void createTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxRepository.findAll().size();

        // Create the Tax with an existing ID
        tax.setId(1L);
        TaxDTO taxDTO = taxMapper.toDto(tax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxMockMvc.perform(post("/api/taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tax in Elasticsearch
        verify(mockTaxSearchRepository, times(0)).save(tax);
    }

    @Test
    @Transactional
    public void getAllTaxes() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxStatus").value(hasItem(DEFAULT_TAX_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get the tax
        restTaxMockMvc.perform(get("/api/taxes/{id}", tax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tax.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.taxStatus").value(DEFAULT_TAX_STATUS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllTaxesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where amount equals to DEFAULT_AMOUNT
        defaultTaxShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the taxList where amount equals to UPDATED_AMOUNT
        defaultTaxShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTaxesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTaxShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the taxList where amount equals to UPDATED_AMOUNT
        defaultTaxShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllTaxesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where amount is not null
        defaultTaxShouldBeFound("amount.specified=true");

        // Get all the taxList where amount is null
        defaultTaxShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByTaxStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where taxStatus equals to DEFAULT_TAX_STATUS
        defaultTaxShouldBeFound("taxStatus.equals=" + DEFAULT_TAX_STATUS);

        // Get all the taxList where taxStatus equals to UPDATED_TAX_STATUS
        defaultTaxShouldNotBeFound("taxStatus.equals=" + UPDATED_TAX_STATUS);
    }

    @Test
    @Transactional
    public void getAllTaxesByTaxStatusIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where taxStatus in DEFAULT_TAX_STATUS or UPDATED_TAX_STATUS
        defaultTaxShouldBeFound("taxStatus.in=" + DEFAULT_TAX_STATUS + "," + UPDATED_TAX_STATUS);

        // Get all the taxList where taxStatus equals to UPDATED_TAX_STATUS
        defaultTaxShouldNotBeFound("taxStatus.in=" + UPDATED_TAX_STATUS);
    }

    @Test
    @Transactional
    public void getAllTaxesByTaxStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where taxStatus is not null
        defaultTaxShouldBeFound("taxStatus.specified=true");

        // Get all the taxList where taxStatus is null
        defaultTaxShouldNotBeFound("taxStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultTaxShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the taxList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultTaxShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultTaxShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the taxList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultTaxShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedBy is not null
        defaultTaxShouldBeFound("modifiedBy.specified=true");

        // Get all the taxList where modifiedBy is null
        defaultTaxShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultTaxShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the taxList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultTaxShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultTaxShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the taxList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultTaxShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedOn is not null
        defaultTaxShouldBeFound("modifiedOn.specified=true");

        // Get all the taxList where modifiedOn is null
        defaultTaxShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultTaxShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the taxList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultTaxShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTaxesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        // Get all the taxList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultTaxShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the taxList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultTaxShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllTaxesByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        tax.setFinancialAccountYear(financialAccountYear);
        taxRepository.saveAndFlush(tax);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the taxList where financialAccountYear equals to financialAccountYearId
        defaultTaxShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the taxList where financialAccountYear equals to financialAccountYearId + 1
        defaultTaxShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }


    @Test
    @Transactional
    public void getAllTaxesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        tax.setEmployee(employee);
        taxRepository.saveAndFlush(tax);
        Long employeeId = employee.getId();

        // Get all the taxList where employee equals to employeeId
        defaultTaxShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the taxList where employee equals to employeeId + 1
        defaultTaxShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTaxShouldBeFound(String filter) throws Exception {
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxStatus").value(hasItem(DEFAULT_TAX_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restTaxMockMvc.perform(get("/api/taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTaxShouldNotBeFound(String filter) throws Exception {
        restTaxMockMvc.perform(get("/api/taxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaxMockMvc.perform(get("/api/taxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTax() throws Exception {
        // Get the tax
        restTaxMockMvc.perform(get("/api/taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        int databaseSizeBeforeUpdate = taxRepository.findAll().size();

        // Update the tax
        Tax updatedTax = taxRepository.findById(tax.getId()).get();
        // Disconnect from session so that the updates on updatedTax are not directly saved in db
        em.detach(updatedTax);
        updatedTax
            .amount(UPDATED_AMOUNT)
            .taxStatus(UPDATED_TAX_STATUS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        TaxDTO taxDTO = taxMapper.toDto(updatedTax);

        restTaxMockMvc.perform(put("/api/taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isOk());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeUpdate);
        Tax testTax = taxList.get(taxList.size() - 1);
        assertThat(testTax.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTax.getTaxStatus()).isEqualTo(UPDATED_TAX_STATUS);
        assertThat(testTax.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTax.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Tax in Elasticsearch
        verify(mockTaxSearchRepository, times(1)).save(testTax);
    }

    @Test
    @Transactional
    public void updateNonExistingTax() throws Exception {
        int databaseSizeBeforeUpdate = taxRepository.findAll().size();

        // Create the Tax
        TaxDTO taxDTO = taxMapper.toDto(tax);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxMockMvc.perform(put("/api/taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tax in the database
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tax in Elasticsearch
        verify(mockTaxSearchRepository, times(0)).save(tax);
    }

    @Test
    @Transactional
    public void deleteTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);

        int databaseSizeBeforeDelete = taxRepository.findAll().size();

        // Delete the tax
        restTaxMockMvc.perform(delete("/api/taxes/{id}", tax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax> taxList = taxRepository.findAll();
        assertThat(taxList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tax in Elasticsearch
        verify(mockTaxSearchRepository, times(1)).deleteById(tax.getId());
    }

    @Test
    @Transactional
    public void searchTax() throws Exception {
        // Initialize the database
        taxRepository.saveAndFlush(tax);
        when(mockTaxSearchRepository.search(queryStringQuery("id:" + tax.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tax), PageRequest.of(0, 1), 1));
        // Search the tax
        restTaxMockMvc.perform(get("/api/_search/taxes?query=id:" + tax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tax.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxStatus").value(hasItem(DEFAULT_TAX_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tax.class);
        Tax tax1 = new Tax();
        tax1.setId(1L);
        Tax tax2 = new Tax();
        tax2.setId(tax1.getId());
        assertThat(tax1).isEqualTo(tax2);
        tax2.setId(2L);
        assertThat(tax1).isNotEqualTo(tax2);
        tax1.setId(null);
        assertThat(tax1).isNotEqualTo(tax2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxDTO.class);
        TaxDTO taxDTO1 = new TaxDTO();
        taxDTO1.setId(1L);
        TaxDTO taxDTO2 = new TaxDTO();
        assertThat(taxDTO1).isNotEqualTo(taxDTO2);
        taxDTO2.setId(taxDTO1.getId());
        assertThat(taxDTO1).isEqualTo(taxDTO2);
        taxDTO2.setId(2L);
        assertThat(taxDTO1).isNotEqualTo(taxDTO2);
        taxDTO1.setId(null);
        assertThat(taxDTO1).isNotEqualTo(taxDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(taxMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(taxMapper.fromId(null)).isNull();
    }
}
