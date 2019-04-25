package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.TrainingInformation;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.TrainingInformationRepository;
import org.soptorshi.repository.search.TrainingInformationSearchRepository;
import org.soptorshi.service.TrainingInformationService;
import org.soptorshi.service.dto.TrainingInformationDTO;
import org.soptorshi.service.mapper.TrainingInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.TrainingInformationCriteria;
import org.soptorshi.service.TrainingInformationQueryService;

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
 * Test class for the TrainingInformationResource REST controller.
 *
 * @see TrainingInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class TrainingInformationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    @Autowired
    private TrainingInformationRepository trainingInformationRepository;

    @Autowired
    private TrainingInformationMapper trainingInformationMapper;

    @Autowired
    private TrainingInformationService trainingInformationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.TrainingInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingInformationSearchRepository mockTrainingInformationSearchRepository;

    @Autowired
    private TrainingInformationQueryService trainingInformationQueryService;

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

    private MockMvc restTrainingInformationMockMvc;

    private TrainingInformation trainingInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingInformationResource trainingInformationResource = new TrainingInformationResource(trainingInformationService, trainingInformationQueryService);
        this.restTrainingInformationMockMvc = MockMvcBuilders.standaloneSetup(trainingInformationResource)
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
    public static TrainingInformation createEntity(EntityManager em) {
        TrainingInformation trainingInformation = new TrainingInformation()
            .name(DEFAULT_NAME)
            .subject(DEFAULT_SUBJECT)
            .organization(DEFAULT_ORGANIZATION);
        return trainingInformation;
    }

    @Before
    public void initTest() {
        trainingInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingInformation() throws Exception {
        int databaseSizeBeforeCreate = trainingInformationRepository.findAll().size();

        // Create the TrainingInformation
        TrainingInformationDTO trainingInformationDTO = trainingInformationMapper.toDto(trainingInformation);
        restTrainingInformationMockMvc.perform(post("/api/training-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingInformation in the database
        List<TrainingInformation> trainingInformationList = trainingInformationRepository.findAll();
        assertThat(trainingInformationList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingInformation testTrainingInformation = trainingInformationList.get(trainingInformationList.size() - 1);
        assertThat(testTrainingInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrainingInformation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTrainingInformation.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);

        // Validate the TrainingInformation in Elasticsearch
        verify(mockTrainingInformationSearchRepository, times(1)).save(testTrainingInformation);
    }

    @Test
    @Transactional
    public void createTrainingInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingInformationRepository.findAll().size();

        // Create the TrainingInformation with an existing ID
        trainingInformation.setId(1L);
        TrainingInformationDTO trainingInformationDTO = trainingInformationMapper.toDto(trainingInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingInformationMockMvc.perform(post("/api/training-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingInformation in the database
        List<TrainingInformation> trainingInformationList = trainingInformationRepository.findAll();
        assertThat(trainingInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingInformation in Elasticsearch
        verify(mockTrainingInformationSearchRepository, times(0)).save(trainingInformation);
    }

    @Test
    @Transactional
    public void getAllTrainingInformations() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList
        restTrainingInformationMockMvc.perform(get("/api/training-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())));
    }
    
    @Test
    @Transactional
    public void getTrainingInformation() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get the trainingInformation
        restTrainingInformationMockMvc.perform(get("/api/training-informations/{id}", trainingInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainingInformation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()));
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where name equals to DEFAULT_NAME
        defaultTrainingInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the trainingInformationList where name equals to UPDATED_NAME
        defaultTrainingInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTrainingInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the trainingInformationList where name equals to UPDATED_NAME
        defaultTrainingInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where name is not null
        defaultTrainingInformationShouldBeFound("name.specified=true");

        // Get all the trainingInformationList where name is null
        defaultTrainingInformationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where subject equals to DEFAULT_SUBJECT
        defaultTrainingInformationShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the trainingInformationList where subject equals to UPDATED_SUBJECT
        defaultTrainingInformationShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultTrainingInformationShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the trainingInformationList where subject equals to UPDATED_SUBJECT
        defaultTrainingInformationShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where subject is not null
        defaultTrainingInformationShouldBeFound("subject.specified=true");

        // Get all the trainingInformationList where subject is null
        defaultTrainingInformationShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where organization equals to DEFAULT_ORGANIZATION
        defaultTrainingInformationShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the trainingInformationList where organization equals to UPDATED_ORGANIZATION
        defaultTrainingInformationShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultTrainingInformationShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the trainingInformationList where organization equals to UPDATED_ORGANIZATION
        defaultTrainingInformationShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        // Get all the trainingInformationList where organization is not null
        defaultTrainingInformationShouldBeFound("organization.specified=true");

        // Get all the trainingInformationList where organization is null
        defaultTrainingInformationShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingInformationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        trainingInformation.setEmployee(employee);
        trainingInformationRepository.saveAndFlush(trainingInformation);
        Long employeeId = employee.getId();

        // Get all the trainingInformationList where employee equals to employeeId
        defaultTrainingInformationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the trainingInformationList where employee equals to employeeId + 1
        defaultTrainingInformationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTrainingInformationShouldBeFound(String filter) throws Exception {
        restTrainingInformationMockMvc.perform(get("/api/training-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)));

        // Check, that the count call also returns 1
        restTrainingInformationMockMvc.perform(get("/api/training-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTrainingInformationShouldNotBeFound(String filter) throws Exception {
        restTrainingInformationMockMvc.perform(get("/api/training-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingInformationMockMvc.perform(get("/api/training-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTrainingInformation() throws Exception {
        // Get the trainingInformation
        restTrainingInformationMockMvc.perform(get("/api/training-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingInformation() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        int databaseSizeBeforeUpdate = trainingInformationRepository.findAll().size();

        // Update the trainingInformation
        TrainingInformation updatedTrainingInformation = trainingInformationRepository.findById(trainingInformation.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingInformation are not directly saved in db
        em.detach(updatedTrainingInformation);
        updatedTrainingInformation
            .name(UPDATED_NAME)
            .subject(UPDATED_SUBJECT)
            .organization(UPDATED_ORGANIZATION);
        TrainingInformationDTO trainingInformationDTO = trainingInformationMapper.toDto(updatedTrainingInformation);

        restTrainingInformationMockMvc.perform(put("/api/training-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationDTO)))
            .andExpect(status().isOk());

        // Validate the TrainingInformation in the database
        List<TrainingInformation> trainingInformationList = trainingInformationRepository.findAll();
        assertThat(trainingInformationList).hasSize(databaseSizeBeforeUpdate);
        TrainingInformation testTrainingInformation = trainingInformationList.get(trainingInformationList.size() - 1);
        assertThat(testTrainingInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrainingInformation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTrainingInformation.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);

        // Validate the TrainingInformation in Elasticsearch
        verify(mockTrainingInformationSearchRepository, times(1)).save(testTrainingInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingInformation() throws Exception {
        int databaseSizeBeforeUpdate = trainingInformationRepository.findAll().size();

        // Create the TrainingInformation
        TrainingInformationDTO trainingInformationDTO = trainingInformationMapper.toDto(trainingInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingInformationMockMvc.perform(put("/api/training-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingInformation in the database
        List<TrainingInformation> trainingInformationList = trainingInformationRepository.findAll();
        assertThat(trainingInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingInformation in Elasticsearch
        verify(mockTrainingInformationSearchRepository, times(0)).save(trainingInformation);
    }

    @Test
    @Transactional
    public void deleteTrainingInformation() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);

        int databaseSizeBeforeDelete = trainingInformationRepository.findAll().size();

        // Delete the trainingInformation
        restTrainingInformationMockMvc.perform(delete("/api/training-informations/{id}", trainingInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingInformation> trainingInformationList = trainingInformationRepository.findAll();
        assertThat(trainingInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingInformation in Elasticsearch
        verify(mockTrainingInformationSearchRepository, times(1)).deleteById(trainingInformation.getId());
    }

    @Test
    @Transactional
    public void searchTrainingInformation() throws Exception {
        // Initialize the database
        trainingInformationRepository.saveAndFlush(trainingInformation);
        when(mockTrainingInformationSearchRepository.search(queryStringQuery("id:" + trainingInformation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(trainingInformation), PageRequest.of(0, 1), 1));
        // Search the trainingInformation
        restTrainingInformationMockMvc.perform(get("/api/_search/training-informations?query=id:" + trainingInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingInformation.class);
        TrainingInformation trainingInformation1 = new TrainingInformation();
        trainingInformation1.setId(1L);
        TrainingInformation trainingInformation2 = new TrainingInformation();
        trainingInformation2.setId(trainingInformation1.getId());
        assertThat(trainingInformation1).isEqualTo(trainingInformation2);
        trainingInformation2.setId(2L);
        assertThat(trainingInformation1).isNotEqualTo(trainingInformation2);
        trainingInformation1.setId(null);
        assertThat(trainingInformation1).isNotEqualTo(trainingInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingInformationDTO.class);
        TrainingInformationDTO trainingInformationDTO1 = new TrainingInformationDTO();
        trainingInformationDTO1.setId(1L);
        TrainingInformationDTO trainingInformationDTO2 = new TrainingInformationDTO();
        assertThat(trainingInformationDTO1).isNotEqualTo(trainingInformationDTO2);
        trainingInformationDTO2.setId(trainingInformationDTO1.getId());
        assertThat(trainingInformationDTO1).isEqualTo(trainingInformationDTO2);
        trainingInformationDTO2.setId(2L);
        assertThat(trainingInformationDTO1).isNotEqualTo(trainingInformationDTO2);
        trainingInformationDTO1.setId(null);
        assertThat(trainingInformationDTO1).isNotEqualTo(trainingInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trainingInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trainingInformationMapper.fromId(null)).isNull();
    }
}
