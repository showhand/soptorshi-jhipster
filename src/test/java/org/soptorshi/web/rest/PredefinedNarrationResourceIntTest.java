package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PredefinedNarration;
import org.soptorshi.domain.Voucher;
import org.soptorshi.repository.PredefinedNarrationRepository;
import org.soptorshi.repository.search.PredefinedNarrationSearchRepository;
import org.soptorshi.service.PredefinedNarrationService;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.mapper.PredefinedNarrationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PredefinedNarrationCriteria;
import org.soptorshi.service.PredefinedNarrationQueryService;

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
 * Test class for the PredefinedNarrationResource REST controller.
 *
 * @see PredefinedNarrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PredefinedNarrationResourceIntTest {

    private static final String DEFAULT_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_NARRATION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PredefinedNarrationRepository predefinedNarrationRepository;

    @Autowired
    private PredefinedNarrationMapper predefinedNarrationMapper;

    @Autowired
    private PredefinedNarrationService predefinedNarrationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PredefinedNarrationSearchRepositoryMockConfiguration
     */
    @Autowired
    private PredefinedNarrationSearchRepository mockPredefinedNarrationSearchRepository;

    @Autowired
    private PredefinedNarrationQueryService predefinedNarrationQueryService;

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

    private MockMvc restPredefinedNarrationMockMvc;

    private PredefinedNarration predefinedNarration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PredefinedNarrationResource predefinedNarrationResource = new PredefinedNarrationResource(predefinedNarrationService, predefinedNarrationQueryService);
        this.restPredefinedNarrationMockMvc = MockMvcBuilders.standaloneSetup(predefinedNarrationResource)
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
    public static PredefinedNarration createEntity(EntityManager em) {
        PredefinedNarration predefinedNarration = new PredefinedNarration()
            .narration(DEFAULT_NARRATION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return predefinedNarration;
    }

    @Before
    public void initTest() {
        predefinedNarration = createEntity(em);
    }

    @Test
    @Transactional
    public void createPredefinedNarration() throws Exception {
        int databaseSizeBeforeCreate = predefinedNarrationRepository.findAll().size();

        // Create the PredefinedNarration
        PredefinedNarrationDTO predefinedNarrationDTO = predefinedNarrationMapper.toDto(predefinedNarration);
        restPredefinedNarrationMockMvc.perform(post("/api/predefined-narrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(predefinedNarrationDTO)))
            .andExpect(status().isCreated());

        // Validate the PredefinedNarration in the database
        List<PredefinedNarration> predefinedNarrationList = predefinedNarrationRepository.findAll();
        assertThat(predefinedNarrationList).hasSize(databaseSizeBeforeCreate + 1);
        PredefinedNarration testPredefinedNarration = predefinedNarrationList.get(predefinedNarrationList.size() - 1);
        assertThat(testPredefinedNarration.getNarration()).isEqualTo(DEFAULT_NARRATION);
        assertThat(testPredefinedNarration.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPredefinedNarration.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the PredefinedNarration in Elasticsearch
        verify(mockPredefinedNarrationSearchRepository, times(1)).save(testPredefinedNarration);
    }

    @Test
    @Transactional
    public void createPredefinedNarrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = predefinedNarrationRepository.findAll().size();

        // Create the PredefinedNarration with an existing ID
        predefinedNarration.setId(1L);
        PredefinedNarrationDTO predefinedNarrationDTO = predefinedNarrationMapper.toDto(predefinedNarration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPredefinedNarrationMockMvc.perform(post("/api/predefined-narrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(predefinedNarrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PredefinedNarration in the database
        List<PredefinedNarration> predefinedNarrationList = predefinedNarrationRepository.findAll();
        assertThat(predefinedNarrationList).hasSize(databaseSizeBeforeCreate);

        // Validate the PredefinedNarration in Elasticsearch
        verify(mockPredefinedNarrationSearchRepository, times(0)).save(predefinedNarration);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrations() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(predefinedNarration.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPredefinedNarration() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get the predefinedNarration
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations/{id}", predefinedNarration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(predefinedNarration.getId().intValue()))
            .andExpect(jsonPath("$.narration").value(DEFAULT_NARRATION.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where narration equals to DEFAULT_NARRATION
        defaultPredefinedNarrationShouldBeFound("narration.equals=" + DEFAULT_NARRATION);

        // Get all the predefinedNarrationList where narration equals to UPDATED_NARRATION
        defaultPredefinedNarrationShouldNotBeFound("narration.equals=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where narration in DEFAULT_NARRATION or UPDATED_NARRATION
        defaultPredefinedNarrationShouldBeFound("narration.in=" + DEFAULT_NARRATION + "," + UPDATED_NARRATION);

        // Get all the predefinedNarrationList where narration equals to UPDATED_NARRATION
        defaultPredefinedNarrationShouldNotBeFound("narration.in=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where narration is not null
        defaultPredefinedNarrationShouldBeFound("narration.specified=true");

        // Get all the predefinedNarrationList where narration is null
        defaultPredefinedNarrationShouldNotBeFound("narration.specified=false");
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultPredefinedNarrationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the predefinedNarrationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPredefinedNarrationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultPredefinedNarrationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the predefinedNarrationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPredefinedNarrationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedBy is not null
        defaultPredefinedNarrationShouldBeFound("modifiedBy.specified=true");

        // Get all the predefinedNarrationList where modifiedBy is null
        defaultPredefinedNarrationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultPredefinedNarrationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the predefinedNarrationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPredefinedNarrationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultPredefinedNarrationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the predefinedNarrationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPredefinedNarrationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedOn is not null
        defaultPredefinedNarrationShouldBeFound("modifiedOn.specified=true");

        // Get all the predefinedNarrationList where modifiedOn is null
        defaultPredefinedNarrationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultPredefinedNarrationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the predefinedNarrationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultPredefinedNarrationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPredefinedNarrationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        // Get all the predefinedNarrationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultPredefinedNarrationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the predefinedNarrationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultPredefinedNarrationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllPredefinedNarrationsByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        Voucher voucher = VoucherResourceIntTest.createEntity(em);
        em.persist(voucher);
        em.flush();
        predefinedNarration.setVoucher(voucher);
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);
        Long voucherId = voucher.getId();

        // Get all the predefinedNarrationList where voucher equals to voucherId
        defaultPredefinedNarrationShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the predefinedNarrationList where voucher equals to voucherId + 1
        defaultPredefinedNarrationShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPredefinedNarrationShouldBeFound(String filter) throws Exception {
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(predefinedNarration.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPredefinedNarrationShouldNotBeFound(String filter) throws Exception {
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPredefinedNarration() throws Exception {
        // Get the predefinedNarration
        restPredefinedNarrationMockMvc.perform(get("/api/predefined-narrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePredefinedNarration() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        int databaseSizeBeforeUpdate = predefinedNarrationRepository.findAll().size();

        // Update the predefinedNarration
        PredefinedNarration updatedPredefinedNarration = predefinedNarrationRepository.findById(predefinedNarration.getId()).get();
        // Disconnect from session so that the updates on updatedPredefinedNarration are not directly saved in db
        em.detach(updatedPredefinedNarration);
        updatedPredefinedNarration
            .narration(UPDATED_NARRATION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        PredefinedNarrationDTO predefinedNarrationDTO = predefinedNarrationMapper.toDto(updatedPredefinedNarration);

        restPredefinedNarrationMockMvc.perform(put("/api/predefined-narrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(predefinedNarrationDTO)))
            .andExpect(status().isOk());

        // Validate the PredefinedNarration in the database
        List<PredefinedNarration> predefinedNarrationList = predefinedNarrationRepository.findAll();
        assertThat(predefinedNarrationList).hasSize(databaseSizeBeforeUpdate);
        PredefinedNarration testPredefinedNarration = predefinedNarrationList.get(predefinedNarrationList.size() - 1);
        assertThat(testPredefinedNarration.getNarration()).isEqualTo(UPDATED_NARRATION);
        assertThat(testPredefinedNarration.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPredefinedNarration.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the PredefinedNarration in Elasticsearch
        verify(mockPredefinedNarrationSearchRepository, times(1)).save(testPredefinedNarration);
    }

    @Test
    @Transactional
    public void updateNonExistingPredefinedNarration() throws Exception {
        int databaseSizeBeforeUpdate = predefinedNarrationRepository.findAll().size();

        // Create the PredefinedNarration
        PredefinedNarrationDTO predefinedNarrationDTO = predefinedNarrationMapper.toDto(predefinedNarration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPredefinedNarrationMockMvc.perform(put("/api/predefined-narrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(predefinedNarrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PredefinedNarration in the database
        List<PredefinedNarration> predefinedNarrationList = predefinedNarrationRepository.findAll();
        assertThat(predefinedNarrationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PredefinedNarration in Elasticsearch
        verify(mockPredefinedNarrationSearchRepository, times(0)).save(predefinedNarration);
    }

    @Test
    @Transactional
    public void deletePredefinedNarration() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);

        int databaseSizeBeforeDelete = predefinedNarrationRepository.findAll().size();

        // Delete the predefinedNarration
        restPredefinedNarrationMockMvc.perform(delete("/api/predefined-narrations/{id}", predefinedNarration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PredefinedNarration> predefinedNarrationList = predefinedNarrationRepository.findAll();
        assertThat(predefinedNarrationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PredefinedNarration in Elasticsearch
        verify(mockPredefinedNarrationSearchRepository, times(1)).deleteById(predefinedNarration.getId());
    }

    @Test
    @Transactional
    public void searchPredefinedNarration() throws Exception {
        // Initialize the database
        predefinedNarrationRepository.saveAndFlush(predefinedNarration);
        when(mockPredefinedNarrationSearchRepository.search(queryStringQuery("id:" + predefinedNarration.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(predefinedNarration), PageRequest.of(0, 1), 1));
        // Search the predefinedNarration
        restPredefinedNarrationMockMvc.perform(get("/api/_search/predefined-narrations?query=id:" + predefinedNarration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(predefinedNarration.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PredefinedNarration.class);
        PredefinedNarration predefinedNarration1 = new PredefinedNarration();
        predefinedNarration1.setId(1L);
        PredefinedNarration predefinedNarration2 = new PredefinedNarration();
        predefinedNarration2.setId(predefinedNarration1.getId());
        assertThat(predefinedNarration1).isEqualTo(predefinedNarration2);
        predefinedNarration2.setId(2L);
        assertThat(predefinedNarration1).isNotEqualTo(predefinedNarration2);
        predefinedNarration1.setId(null);
        assertThat(predefinedNarration1).isNotEqualTo(predefinedNarration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PredefinedNarrationDTO.class);
        PredefinedNarrationDTO predefinedNarrationDTO1 = new PredefinedNarrationDTO();
        predefinedNarrationDTO1.setId(1L);
        PredefinedNarrationDTO predefinedNarrationDTO2 = new PredefinedNarrationDTO();
        assertThat(predefinedNarrationDTO1).isNotEqualTo(predefinedNarrationDTO2);
        predefinedNarrationDTO2.setId(predefinedNarrationDTO1.getId());
        assertThat(predefinedNarrationDTO1).isEqualTo(predefinedNarrationDTO2);
        predefinedNarrationDTO2.setId(2L);
        assertThat(predefinedNarrationDTO1).isNotEqualTo(predefinedNarrationDTO2);
        predefinedNarrationDTO1.setId(null);
        assertThat(predefinedNarrationDTO1).isNotEqualTo(predefinedNarrationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(predefinedNarrationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(predefinedNarrationMapper.fromId(null)).isNull();
    }
}
