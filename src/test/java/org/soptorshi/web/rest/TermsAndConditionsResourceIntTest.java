package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.TermsAndConditions;
import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.repository.TermsAndConditionsRepository;
import org.soptorshi.repository.search.TermsAndConditionsSearchRepository;
import org.soptorshi.service.TermsAndConditionsService;
import org.soptorshi.service.dto.TermsAndConditionsDTO;
import org.soptorshi.service.mapper.TermsAndConditionsMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.TermsAndConditionsCriteria;
import org.soptorshi.service.TermsAndConditionsQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Test class for the TermsAndConditionsResource REST controller.
 *
 * @see TermsAndConditionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class TermsAndConditionsResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TermsAndConditionsRepository termsAndConditionsRepository;

    @Autowired
    private TermsAndConditionsMapper termsAndConditionsMapper;

    @Autowired
    private TermsAndConditionsService termsAndConditionsService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.TermsAndConditionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private TermsAndConditionsSearchRepository mockTermsAndConditionsSearchRepository;

    @Autowired
    private TermsAndConditionsQueryService termsAndConditionsQueryService;

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

    private MockMvc restTermsAndConditionsMockMvc;

    private TermsAndConditions termsAndConditions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TermsAndConditionsResource termsAndConditionsResource = new TermsAndConditionsResource(termsAndConditionsService, termsAndConditionsQueryService);
        this.restTermsAndConditionsMockMvc = MockMvcBuilders.standaloneSetup(termsAndConditionsResource)
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
    public static TermsAndConditions createEntity(EntityManager em) {
        TermsAndConditions termsAndConditions = new TermsAndConditions()
            .description(DEFAULT_DESCRIPTION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return termsAndConditions;
    }

    @Before
    public void initTest() {
        termsAndConditions = createEntity(em);
    }

    @Test
    @Transactional
    public void createTermsAndConditions() throws Exception {
        int databaseSizeBeforeCreate = termsAndConditionsRepository.findAll().size();

        // Create the TermsAndConditions
        TermsAndConditionsDTO termsAndConditionsDTO = termsAndConditionsMapper.toDto(termsAndConditions);
        restTermsAndConditionsMockMvc.perform(post("/api/terms-and-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termsAndConditionsDTO)))
            .andExpect(status().isCreated());

        // Validate the TermsAndConditions in the database
        List<TermsAndConditions> termsAndConditionsList = termsAndConditionsRepository.findAll();
        assertThat(termsAndConditionsList).hasSize(databaseSizeBeforeCreate + 1);
        TermsAndConditions testTermsAndConditions = termsAndConditionsList.get(termsAndConditionsList.size() - 1);
        assertThat(testTermsAndConditions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTermsAndConditions.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTermsAndConditions.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the TermsAndConditions in Elasticsearch
        verify(mockTermsAndConditionsSearchRepository, times(1)).save(testTermsAndConditions);
    }

    @Test
    @Transactional
    public void createTermsAndConditionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termsAndConditionsRepository.findAll().size();

        // Create the TermsAndConditions with an existing ID
        termsAndConditions.setId(1L);
        TermsAndConditionsDTO termsAndConditionsDTO = termsAndConditionsMapper.toDto(termsAndConditions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermsAndConditionsMockMvc.perform(post("/api/terms-and-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termsAndConditionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TermsAndConditions in the database
        List<TermsAndConditions> termsAndConditionsList = termsAndConditionsRepository.findAll();
        assertThat(termsAndConditionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the TermsAndConditions in Elasticsearch
        verify(mockTermsAndConditionsSearchRepository, times(0)).save(termsAndConditions);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditions() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termsAndConditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getTermsAndConditions() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get the termsAndConditions
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions/{id}", termsAndConditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(termsAndConditions.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultTermsAndConditionsShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the termsAndConditionsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultTermsAndConditionsShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultTermsAndConditionsShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the termsAndConditionsList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultTermsAndConditionsShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedBy is not null
        defaultTermsAndConditionsShouldBeFound("modifiedBy.specified=true");

        // Get all the termsAndConditionsList where modifiedBy is null
        defaultTermsAndConditionsShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultTermsAndConditionsShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the termsAndConditionsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultTermsAndConditionsShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultTermsAndConditionsShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the termsAndConditionsList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultTermsAndConditionsShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedOn is not null
        defaultTermsAndConditionsShouldBeFound("modifiedOn.specified=true");

        // Get all the termsAndConditionsList where modifiedOn is null
        defaultTermsAndConditionsShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultTermsAndConditionsShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the termsAndConditionsList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultTermsAndConditionsShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllTermsAndConditionsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        // Get all the termsAndConditionsList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultTermsAndConditionsShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the termsAndConditionsList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultTermsAndConditionsShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllTermsAndConditionsByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        termsAndConditions.setPurchaseOrder(purchaseOrder);
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the termsAndConditionsList where purchaseOrder equals to purchaseOrderId
        defaultTermsAndConditionsShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the termsAndConditionsList where purchaseOrder equals to purchaseOrderId + 1
        defaultTermsAndConditionsShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTermsAndConditionsShouldBeFound(String filter) throws Exception {
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termsAndConditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTermsAndConditionsShouldNotBeFound(String filter) throws Exception {
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTermsAndConditions() throws Exception {
        // Get the termsAndConditions
        restTermsAndConditionsMockMvc.perform(get("/api/terms-and-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTermsAndConditions() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        int databaseSizeBeforeUpdate = termsAndConditionsRepository.findAll().size();

        // Update the termsAndConditions
        TermsAndConditions updatedTermsAndConditions = termsAndConditionsRepository.findById(termsAndConditions.getId()).get();
        // Disconnect from session so that the updates on updatedTermsAndConditions are not directly saved in db
        em.detach(updatedTermsAndConditions);
        updatedTermsAndConditions
            .description(UPDATED_DESCRIPTION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        TermsAndConditionsDTO termsAndConditionsDTO = termsAndConditionsMapper.toDto(updatedTermsAndConditions);

        restTermsAndConditionsMockMvc.perform(put("/api/terms-and-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termsAndConditionsDTO)))
            .andExpect(status().isOk());

        // Validate the TermsAndConditions in the database
        List<TermsAndConditions> termsAndConditionsList = termsAndConditionsRepository.findAll();
        assertThat(termsAndConditionsList).hasSize(databaseSizeBeforeUpdate);
        TermsAndConditions testTermsAndConditions = termsAndConditionsList.get(termsAndConditionsList.size() - 1);
        assertThat(testTermsAndConditions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTermsAndConditions.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTermsAndConditions.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the TermsAndConditions in Elasticsearch
        verify(mockTermsAndConditionsSearchRepository, times(1)).save(testTermsAndConditions);
    }

    @Test
    @Transactional
    public void updateNonExistingTermsAndConditions() throws Exception {
        int databaseSizeBeforeUpdate = termsAndConditionsRepository.findAll().size();

        // Create the TermsAndConditions
        TermsAndConditionsDTO termsAndConditionsDTO = termsAndConditionsMapper.toDto(termsAndConditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermsAndConditionsMockMvc.perform(put("/api/terms-and-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(termsAndConditionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TermsAndConditions in the database
        List<TermsAndConditions> termsAndConditionsList = termsAndConditionsRepository.findAll();
        assertThat(termsAndConditionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TermsAndConditions in Elasticsearch
        verify(mockTermsAndConditionsSearchRepository, times(0)).save(termsAndConditions);
    }

    @Test
    @Transactional
    public void deleteTermsAndConditions() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);

        int databaseSizeBeforeDelete = termsAndConditionsRepository.findAll().size();

        // Delete the termsAndConditions
        restTermsAndConditionsMockMvc.perform(delete("/api/terms-and-conditions/{id}", termsAndConditions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TermsAndConditions> termsAndConditionsList = termsAndConditionsRepository.findAll();
        assertThat(termsAndConditionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TermsAndConditions in Elasticsearch
        verify(mockTermsAndConditionsSearchRepository, times(1)).deleteById(termsAndConditions.getId());
    }

    @Test
    @Transactional
    public void searchTermsAndConditions() throws Exception {
        // Initialize the database
        termsAndConditionsRepository.saveAndFlush(termsAndConditions);
        when(mockTermsAndConditionsSearchRepository.search(queryStringQuery("id:" + termsAndConditions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(termsAndConditions), PageRequest.of(0, 1), 1));
        // Search the termsAndConditions
        restTermsAndConditionsMockMvc.perform(get("/api/_search/terms-and-conditions?query=id:" + termsAndConditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termsAndConditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermsAndConditions.class);
        TermsAndConditions termsAndConditions1 = new TermsAndConditions();
        termsAndConditions1.setId(1L);
        TermsAndConditions termsAndConditions2 = new TermsAndConditions();
        termsAndConditions2.setId(termsAndConditions1.getId());
        assertThat(termsAndConditions1).isEqualTo(termsAndConditions2);
        termsAndConditions2.setId(2L);
        assertThat(termsAndConditions1).isNotEqualTo(termsAndConditions2);
        termsAndConditions1.setId(null);
        assertThat(termsAndConditions1).isNotEqualTo(termsAndConditions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermsAndConditionsDTO.class);
        TermsAndConditionsDTO termsAndConditionsDTO1 = new TermsAndConditionsDTO();
        termsAndConditionsDTO1.setId(1L);
        TermsAndConditionsDTO termsAndConditionsDTO2 = new TermsAndConditionsDTO();
        assertThat(termsAndConditionsDTO1).isNotEqualTo(termsAndConditionsDTO2);
        termsAndConditionsDTO2.setId(termsAndConditionsDTO1.getId());
        assertThat(termsAndConditionsDTO1).isEqualTo(termsAndConditionsDTO2);
        termsAndConditionsDTO2.setId(2L);
        assertThat(termsAndConditionsDTO1).isNotEqualTo(termsAndConditionsDTO2);
        termsAndConditionsDTO1.setId(null);
        assertThat(termsAndConditionsDTO1).isNotEqualTo(termsAndConditionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(termsAndConditionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(termsAndConditionsMapper.fromId(null)).isNull();
    }
}
