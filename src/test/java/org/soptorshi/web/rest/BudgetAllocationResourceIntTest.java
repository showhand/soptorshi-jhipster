package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.BudgetAllocation;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.Department;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.BudgetAllocationRepository;
import org.soptorshi.repository.search.BudgetAllocationSearchRepository;
import org.soptorshi.service.BudgetAllocationService;
import org.soptorshi.service.dto.BudgetAllocationDTO;
import org.soptorshi.service.mapper.BudgetAllocationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.BudgetAllocationCriteria;
import org.soptorshi.service.BudgetAllocationQueryService;

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
 * Test class for the BudgetAllocationResource REST controller.
 *
 * @see BudgetAllocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class BudgetAllocationResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private BudgetAllocationRepository budgetAllocationRepository;

    @Autowired
    private BudgetAllocationMapper budgetAllocationMapper;

    @Autowired
    private BudgetAllocationService budgetAllocationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.BudgetAllocationSearchRepositoryMockConfiguration
     */
    @Autowired
    private BudgetAllocationSearchRepository mockBudgetAllocationSearchRepository;

    @Autowired
    private BudgetAllocationQueryService budgetAllocationQueryService;

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

    private MockMvc restBudgetAllocationMockMvc;

    private BudgetAllocation budgetAllocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BudgetAllocationResource budgetAllocationResource = new BudgetAllocationResource(budgetAllocationService, budgetAllocationQueryService);
        this.restBudgetAllocationMockMvc = MockMvcBuilders.standaloneSetup(budgetAllocationResource)
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
    public static BudgetAllocation createEntity(EntityManager em) {
        BudgetAllocation budgetAllocation = new BudgetAllocation()
            .amount(DEFAULT_AMOUNT);
        return budgetAllocation;
    }

    @Before
    public void initTest() {
        budgetAllocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createBudgetAllocation() throws Exception {
        int databaseSizeBeforeCreate = budgetAllocationRepository.findAll().size();

        // Create the BudgetAllocation
        BudgetAllocationDTO budgetAllocationDTO = budgetAllocationMapper.toDto(budgetAllocation);
        restBudgetAllocationMockMvc.perform(post("/api/budget-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetAllocationDTO)))
            .andExpect(status().isCreated());

        // Validate the BudgetAllocation in the database
        List<BudgetAllocation> budgetAllocationList = budgetAllocationRepository.findAll();
        assertThat(budgetAllocationList).hasSize(databaseSizeBeforeCreate + 1);
        BudgetAllocation testBudgetAllocation = budgetAllocationList.get(budgetAllocationList.size() - 1);
        assertThat(testBudgetAllocation.getAmount()).isEqualTo(DEFAULT_AMOUNT);

        // Validate the BudgetAllocation in Elasticsearch
        verify(mockBudgetAllocationSearchRepository, times(1)).save(testBudgetAllocation);
    }

    @Test
    @Transactional
    public void createBudgetAllocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = budgetAllocationRepository.findAll().size();

        // Create the BudgetAllocation with an existing ID
        budgetAllocation.setId(1L);
        BudgetAllocationDTO budgetAllocationDTO = budgetAllocationMapper.toDto(budgetAllocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBudgetAllocationMockMvc.perform(post("/api/budget-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetAllocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetAllocation in the database
        List<BudgetAllocation> budgetAllocationList = budgetAllocationRepository.findAll();
        assertThat(budgetAllocationList).hasSize(databaseSizeBeforeCreate);

        // Validate the BudgetAllocation in Elasticsearch
        verify(mockBudgetAllocationSearchRepository, times(0)).save(budgetAllocation);
    }

    @Test
    @Transactional
    public void getAllBudgetAllocations() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        // Get all the budgetAllocationList
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetAllocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getBudgetAllocation() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        // Get the budgetAllocation
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations/{id}", budgetAllocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(budgetAllocation.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getAllBudgetAllocationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        // Get all the budgetAllocationList where amount equals to DEFAULT_AMOUNT
        defaultBudgetAllocationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the budgetAllocationList where amount equals to UPDATED_AMOUNT
        defaultBudgetAllocationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBudgetAllocationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        // Get all the budgetAllocationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultBudgetAllocationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the budgetAllocationList where amount equals to UPDATED_AMOUNT
        defaultBudgetAllocationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBudgetAllocationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        // Get all the budgetAllocationList where amount is not null
        defaultBudgetAllocationShouldBeFound("amount.specified=true");

        // Get all the budgetAllocationList where amount is null
        defaultBudgetAllocationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBudgetAllocationsByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        Office office = OfficeResourceIntTest.createEntity(em);
        em.persist(office);
        em.flush();
        budgetAllocation.setOffice(office);
        budgetAllocationRepository.saveAndFlush(budgetAllocation);
        Long officeId = office.getId();

        // Get all the budgetAllocationList where office equals to officeId
        defaultBudgetAllocationShouldBeFound("officeId.equals=" + officeId);

        // Get all the budgetAllocationList where office equals to officeId + 1
        defaultBudgetAllocationShouldNotBeFound("officeId.equals=" + (officeId + 1));
    }


    @Test
    @Transactional
    public void getAllBudgetAllocationsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        budgetAllocation.setDepartment(department);
        budgetAllocationRepository.saveAndFlush(budgetAllocation);
        Long departmentId = department.getId();

        // Get all the budgetAllocationList where department equals to departmentId
        defaultBudgetAllocationShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the budgetAllocationList where department equals to departmentId + 1
        defaultBudgetAllocationShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllBudgetAllocationsByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        budgetAllocation.setFinancialAccountYear(financialAccountYear);
        budgetAllocationRepository.saveAndFlush(budgetAllocation);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the budgetAllocationList where financialAccountYear equals to financialAccountYearId
        defaultBudgetAllocationShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the budgetAllocationList where financialAccountYear equals to financialAccountYearId + 1
        defaultBudgetAllocationShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBudgetAllocationShouldBeFound(String filter) throws Exception {
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetAllocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBudgetAllocationShouldNotBeFound(String filter) throws Exception {
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBudgetAllocation() throws Exception {
        // Get the budgetAllocation
        restBudgetAllocationMockMvc.perform(get("/api/budget-allocations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBudgetAllocation() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        int databaseSizeBeforeUpdate = budgetAllocationRepository.findAll().size();

        // Update the budgetAllocation
        BudgetAllocation updatedBudgetAllocation = budgetAllocationRepository.findById(budgetAllocation.getId()).get();
        // Disconnect from session so that the updates on updatedBudgetAllocation are not directly saved in db
        em.detach(updatedBudgetAllocation);
        updatedBudgetAllocation
            .amount(UPDATED_AMOUNT);
        BudgetAllocationDTO budgetAllocationDTO = budgetAllocationMapper.toDto(updatedBudgetAllocation);

        restBudgetAllocationMockMvc.perform(put("/api/budget-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetAllocationDTO)))
            .andExpect(status().isOk());

        // Validate the BudgetAllocation in the database
        List<BudgetAllocation> budgetAllocationList = budgetAllocationRepository.findAll();
        assertThat(budgetAllocationList).hasSize(databaseSizeBeforeUpdate);
        BudgetAllocation testBudgetAllocation = budgetAllocationList.get(budgetAllocationList.size() - 1);
        assertThat(testBudgetAllocation.getAmount()).isEqualTo(UPDATED_AMOUNT);

        // Validate the BudgetAllocation in Elasticsearch
        verify(mockBudgetAllocationSearchRepository, times(1)).save(testBudgetAllocation);
    }

    @Test
    @Transactional
    public void updateNonExistingBudgetAllocation() throws Exception {
        int databaseSizeBeforeUpdate = budgetAllocationRepository.findAll().size();

        // Create the BudgetAllocation
        BudgetAllocationDTO budgetAllocationDTO = budgetAllocationMapper.toDto(budgetAllocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBudgetAllocationMockMvc.perform(put("/api/budget-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(budgetAllocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BudgetAllocation in the database
        List<BudgetAllocation> budgetAllocationList = budgetAllocationRepository.findAll();
        assertThat(budgetAllocationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BudgetAllocation in Elasticsearch
        verify(mockBudgetAllocationSearchRepository, times(0)).save(budgetAllocation);
    }

    @Test
    @Transactional
    public void deleteBudgetAllocation() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);

        int databaseSizeBeforeDelete = budgetAllocationRepository.findAll().size();

        // Delete the budgetAllocation
        restBudgetAllocationMockMvc.perform(delete("/api/budget-allocations/{id}", budgetAllocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BudgetAllocation> budgetAllocationList = budgetAllocationRepository.findAll();
        assertThat(budgetAllocationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BudgetAllocation in Elasticsearch
        verify(mockBudgetAllocationSearchRepository, times(1)).deleteById(budgetAllocation.getId());
    }

    @Test
    @Transactional
    public void searchBudgetAllocation() throws Exception {
        // Initialize the database
        budgetAllocationRepository.saveAndFlush(budgetAllocation);
        when(mockBudgetAllocationSearchRepository.search(queryStringQuery("id:" + budgetAllocation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(budgetAllocation), PageRequest.of(0, 1), 1));
        // Search the budgetAllocation
        restBudgetAllocationMockMvc.perform(get("/api/_search/budget-allocations?query=id:" + budgetAllocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(budgetAllocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetAllocation.class);
        BudgetAllocation budgetAllocation1 = new BudgetAllocation();
        budgetAllocation1.setId(1L);
        BudgetAllocation budgetAllocation2 = new BudgetAllocation();
        budgetAllocation2.setId(budgetAllocation1.getId());
        assertThat(budgetAllocation1).isEqualTo(budgetAllocation2);
        budgetAllocation2.setId(2L);
        assertThat(budgetAllocation1).isNotEqualTo(budgetAllocation2);
        budgetAllocation1.setId(null);
        assertThat(budgetAllocation1).isNotEqualTo(budgetAllocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BudgetAllocationDTO.class);
        BudgetAllocationDTO budgetAllocationDTO1 = new BudgetAllocationDTO();
        budgetAllocationDTO1.setId(1L);
        BudgetAllocationDTO budgetAllocationDTO2 = new BudgetAllocationDTO();
        assertThat(budgetAllocationDTO1).isNotEqualTo(budgetAllocationDTO2);
        budgetAllocationDTO2.setId(budgetAllocationDTO1.getId());
        assertThat(budgetAllocationDTO1).isEqualTo(budgetAllocationDTO2);
        budgetAllocationDTO2.setId(2L);
        assertThat(budgetAllocationDTO1).isNotEqualTo(budgetAllocationDTO2);
        budgetAllocationDTO1.setId(null);
        assertThat(budgetAllocationDTO1).isNotEqualTo(budgetAllocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(budgetAllocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(budgetAllocationMapper.fromId(null)).isNull();
    }
}
