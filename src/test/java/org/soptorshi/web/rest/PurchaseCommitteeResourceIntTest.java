package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PurchaseCommittee;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.PurchaseCommitteeRepository;
import org.soptorshi.repository.search.PurchaseCommitteeSearchRepository;
import org.soptorshi.service.PurchaseCommitteeService;
import org.soptorshi.service.dto.PurchaseCommitteeDTO;
import org.soptorshi.service.mapper.PurchaseCommitteeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PurchaseCommitteeCriteria;
import org.soptorshi.service.PurchaseCommitteeQueryService;

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
 * Test class for the PurchaseCommitteeResource REST controller.
 *
 * @see PurchaseCommitteeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PurchaseCommitteeResourceIntTest {

    @Autowired
    private PurchaseCommitteeRepository purchaseCommitteeRepository;

    @Autowired
    private PurchaseCommitteeMapper purchaseCommitteeMapper;

    @Autowired
    private PurchaseCommitteeService purchaseCommitteeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PurchaseCommitteeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseCommitteeSearchRepository mockPurchaseCommitteeSearchRepository;

    @Autowired
    private PurchaseCommitteeQueryService purchaseCommitteeQueryService;

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

    private MockMvc restPurchaseCommitteeMockMvc;

    private PurchaseCommittee purchaseCommittee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseCommitteeResource purchaseCommitteeResource = new PurchaseCommitteeResource(purchaseCommitteeService, purchaseCommitteeQueryService);
        this.restPurchaseCommitteeMockMvc = MockMvcBuilders.standaloneSetup(purchaseCommitteeResource)
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
    public static PurchaseCommittee createEntity(EntityManager em) {
        PurchaseCommittee purchaseCommittee = new PurchaseCommittee();
        return purchaseCommittee;
    }

    @Before
    public void initTest() {
        purchaseCommittee = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseCommittee() throws Exception {
        int databaseSizeBeforeCreate = purchaseCommitteeRepository.findAll().size();

        // Create the PurchaseCommittee
        PurchaseCommitteeDTO purchaseCommitteeDTO = purchaseCommitteeMapper.toDto(purchaseCommittee);
        restPurchaseCommitteeMockMvc.perform(post("/api/purchase-committees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseCommitteeDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseCommittee in the database
        List<PurchaseCommittee> purchaseCommitteeList = purchaseCommitteeRepository.findAll();
        assertThat(purchaseCommitteeList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseCommittee testPurchaseCommittee = purchaseCommitteeList.get(purchaseCommitteeList.size() - 1);

        // Validate the PurchaseCommittee in Elasticsearch
        verify(mockPurchaseCommitteeSearchRepository, times(1)).save(testPurchaseCommittee);
    }

    @Test
    @Transactional
    public void createPurchaseCommitteeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseCommitteeRepository.findAll().size();

        // Create the PurchaseCommittee with an existing ID
        purchaseCommittee.setId(1L);
        PurchaseCommitteeDTO purchaseCommitteeDTO = purchaseCommitteeMapper.toDto(purchaseCommittee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseCommitteeMockMvc.perform(post("/api/purchase-committees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseCommitteeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseCommittee in the database
        List<PurchaseCommittee> purchaseCommitteeList = purchaseCommitteeRepository.findAll();
        assertThat(purchaseCommitteeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseCommittee in Elasticsearch
        verify(mockPurchaseCommitteeSearchRepository, times(0)).save(purchaseCommittee);
    }

    @Test
    @Transactional
    public void getAllPurchaseCommittees() throws Exception {
        // Initialize the database
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);

        // Get all the purchaseCommitteeList
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseCommittee.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPurchaseCommittee() throws Exception {
        // Initialize the database
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);

        // Get the purchaseCommittee
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees/{id}", purchaseCommittee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseCommittee.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllPurchaseCommitteesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        purchaseCommittee.setEmployee(employee);
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);
        Long employeeId = employee.getId();

        // Get all the purchaseCommitteeList where employee equals to employeeId
        defaultPurchaseCommitteeShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the purchaseCommitteeList where employee equals to employeeId + 1
        defaultPurchaseCommitteeShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPurchaseCommitteeShouldBeFound(String filter) throws Exception {
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseCommittee.getId().intValue())));

        // Check, that the count call also returns 1
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPurchaseCommitteeShouldNotBeFound(String filter) throws Exception {
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseCommittee() throws Exception {
        // Get the purchaseCommittee
        restPurchaseCommitteeMockMvc.perform(get("/api/purchase-committees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseCommittee() throws Exception {
        // Initialize the database
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);

        int databaseSizeBeforeUpdate = purchaseCommitteeRepository.findAll().size();

        // Update the purchaseCommittee
        PurchaseCommittee updatedPurchaseCommittee = purchaseCommitteeRepository.findById(purchaseCommittee.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseCommittee are not directly saved in db
        em.detach(updatedPurchaseCommittee);
        PurchaseCommitteeDTO purchaseCommitteeDTO = purchaseCommitteeMapper.toDto(updatedPurchaseCommittee);

        restPurchaseCommitteeMockMvc.perform(put("/api/purchase-committees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseCommitteeDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseCommittee in the database
        List<PurchaseCommittee> purchaseCommitteeList = purchaseCommitteeRepository.findAll();
        assertThat(purchaseCommitteeList).hasSize(databaseSizeBeforeUpdate);
        PurchaseCommittee testPurchaseCommittee = purchaseCommitteeList.get(purchaseCommitteeList.size() - 1);

        // Validate the PurchaseCommittee in Elasticsearch
        verify(mockPurchaseCommitteeSearchRepository, times(1)).save(testPurchaseCommittee);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseCommittee() throws Exception {
        int databaseSizeBeforeUpdate = purchaseCommitteeRepository.findAll().size();

        // Create the PurchaseCommittee
        PurchaseCommitteeDTO purchaseCommitteeDTO = purchaseCommitteeMapper.toDto(purchaseCommittee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseCommitteeMockMvc.perform(put("/api/purchase-committees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseCommitteeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseCommittee in the database
        List<PurchaseCommittee> purchaseCommitteeList = purchaseCommitteeRepository.findAll();
        assertThat(purchaseCommitteeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseCommittee in Elasticsearch
        verify(mockPurchaseCommitteeSearchRepository, times(0)).save(purchaseCommittee);
    }

    @Test
    @Transactional
    public void deletePurchaseCommittee() throws Exception {
        // Initialize the database
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);

        int databaseSizeBeforeDelete = purchaseCommitteeRepository.findAll().size();

        // Delete the purchaseCommittee
        restPurchaseCommitteeMockMvc.perform(delete("/api/purchase-committees/{id}", purchaseCommittee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseCommittee> purchaseCommitteeList = purchaseCommitteeRepository.findAll();
        assertThat(purchaseCommitteeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseCommittee in Elasticsearch
        verify(mockPurchaseCommitteeSearchRepository, times(1)).deleteById(purchaseCommittee.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseCommittee() throws Exception {
        // Initialize the database
        purchaseCommitteeRepository.saveAndFlush(purchaseCommittee);
        when(mockPurchaseCommitteeSearchRepository.search(queryStringQuery("id:" + purchaseCommittee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseCommittee), PageRequest.of(0, 1), 1));
        // Search the purchaseCommittee
        restPurchaseCommitteeMockMvc.perform(get("/api/_search/purchase-committees?query=id:" + purchaseCommittee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseCommittee.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseCommittee.class);
        PurchaseCommittee purchaseCommittee1 = new PurchaseCommittee();
        purchaseCommittee1.setId(1L);
        PurchaseCommittee purchaseCommittee2 = new PurchaseCommittee();
        purchaseCommittee2.setId(purchaseCommittee1.getId());
        assertThat(purchaseCommittee1).isEqualTo(purchaseCommittee2);
        purchaseCommittee2.setId(2L);
        assertThat(purchaseCommittee1).isNotEqualTo(purchaseCommittee2);
        purchaseCommittee1.setId(null);
        assertThat(purchaseCommittee1).isNotEqualTo(purchaseCommittee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseCommitteeDTO.class);
        PurchaseCommitteeDTO purchaseCommitteeDTO1 = new PurchaseCommitteeDTO();
        purchaseCommitteeDTO1.setId(1L);
        PurchaseCommitteeDTO purchaseCommitteeDTO2 = new PurchaseCommitteeDTO();
        assertThat(purchaseCommitteeDTO1).isNotEqualTo(purchaseCommitteeDTO2);
        purchaseCommitteeDTO2.setId(purchaseCommitteeDTO1.getId());
        assertThat(purchaseCommitteeDTO1).isEqualTo(purchaseCommitteeDTO2);
        purchaseCommitteeDTO2.setId(2L);
        assertThat(purchaseCommitteeDTO1).isNotEqualTo(purchaseCommitteeDTO2);
        purchaseCommitteeDTO1.setId(null);
        assertThat(purchaseCommitteeDTO1).isNotEqualTo(purchaseCommitteeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseCommitteeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseCommitteeMapper.fromId(null)).isNull();
    }
}
