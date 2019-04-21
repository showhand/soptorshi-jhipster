package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ExperienceInformation;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.ExperienceInformationRepository;
import org.soptorshi.repository.search.ExperienceInformationSearchRepository;
import org.soptorshi.service.ExperienceInformationService;
import org.soptorshi.service.dto.ExperienceInformationDTO;
import org.soptorshi.service.mapper.ExperienceInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ExperienceInformationCriteria;
import org.soptorshi.service.ExperienceInformationQueryService;

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

import org.soptorshi.domain.enumeration.EmploymentType;
/**
 * Test class for the ExperienceInformationResource REST controller.
 *
 * @see ExperienceInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ExperienceInformationResourceIntTest {

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final EmploymentType DEFAULT_EMPLOYMENT_TYPE = EmploymentType.PERMANENT;
    private static final EmploymentType UPDATED_EMPLOYMENT_TYPE = EmploymentType.TEMPORARY;

    @Autowired
    private ExperienceInformationRepository experienceInformationRepository;

    @Autowired
    private ExperienceInformationMapper experienceInformationMapper;

    @Autowired
    private ExperienceInformationService experienceInformationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ExperienceInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExperienceInformationSearchRepository mockExperienceInformationSearchRepository;

    @Autowired
    private ExperienceInformationQueryService experienceInformationQueryService;

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

    private MockMvc restExperienceInformationMockMvc;

    private ExperienceInformation experienceInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceInformationResource experienceInformationResource = new ExperienceInformationResource(experienceInformationService, experienceInformationQueryService);
        this.restExperienceInformationMockMvc = MockMvcBuilders.standaloneSetup(experienceInformationResource)
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
    public static ExperienceInformation createEntity(EntityManager em) {
        ExperienceInformation experienceInformation = new ExperienceInformation()
            .organization(DEFAULT_ORGANIZATION)
            .designation(DEFAULT_DESIGNATION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE);
        return experienceInformation;
    }

    @Before
    public void initTest() {
        experienceInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperienceInformation() throws Exception {
        int databaseSizeBeforeCreate = experienceInformationRepository.findAll().size();

        // Create the ExperienceInformation
        ExperienceInformationDTO experienceInformationDTO = experienceInformationMapper.toDto(experienceInformation);
        restExperienceInformationMockMvc.perform(post("/api/experience-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the ExperienceInformation in the database
        List<ExperienceInformation> experienceInformationList = experienceInformationRepository.findAll();
        assertThat(experienceInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ExperienceInformation testExperienceInformation = experienceInformationList.get(experienceInformationList.size() - 1);
        assertThat(testExperienceInformation.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testExperienceInformation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testExperienceInformation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExperienceInformation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExperienceInformation.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);

        // Validate the ExperienceInformation in Elasticsearch
        verify(mockExperienceInformationSearchRepository, times(1)).save(testExperienceInformation);
    }

    @Test
    @Transactional
    public void createExperienceInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceInformationRepository.findAll().size();

        // Create the ExperienceInformation with an existing ID
        experienceInformation.setId(1L);
        ExperienceInformationDTO experienceInformationDTO = experienceInformationMapper.toDto(experienceInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceInformationMockMvc.perform(post("/api/experience-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceInformation in the database
        List<ExperienceInformation> experienceInformationList = experienceInformationRepository.findAll();
        assertThat(experienceInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExperienceInformation in Elasticsearch
        verify(mockExperienceInformationSearchRepository, times(0)).save(experienceInformation);
    }

    @Test
    @Transactional
    public void getAllExperienceInformations() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList
        restExperienceInformationMockMvc.perform(get("/api/experience-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getExperienceInformation() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get the experienceInformation
        restExperienceInformationMockMvc.perform(get("/api/experience-informations/{id}", experienceInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experienceInformation.getId().intValue()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where organization equals to DEFAULT_ORGANIZATION
        defaultExperienceInformationShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the experienceInformationList where organization equals to UPDATED_ORGANIZATION
        defaultExperienceInformationShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultExperienceInformationShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the experienceInformationList where organization equals to UPDATED_ORGANIZATION
        defaultExperienceInformationShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where organization is not null
        defaultExperienceInformationShouldBeFound("organization.specified=true");

        // Get all the experienceInformationList where organization is null
        defaultExperienceInformationShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where designation equals to DEFAULT_DESIGNATION
        defaultExperienceInformationShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the experienceInformationList where designation equals to UPDATED_DESIGNATION
        defaultExperienceInformationShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultExperienceInformationShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the experienceInformationList where designation equals to UPDATED_DESIGNATION
        defaultExperienceInformationShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where designation is not null
        defaultExperienceInformationShouldBeFound("designation.specified=true");

        // Get all the experienceInformationList where designation is null
        defaultExperienceInformationShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where startDate equals to DEFAULT_START_DATE
        defaultExperienceInformationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the experienceInformationList where startDate equals to UPDATED_START_DATE
        defaultExperienceInformationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultExperienceInformationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the experienceInformationList where startDate equals to UPDATED_START_DATE
        defaultExperienceInformationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where startDate is not null
        defaultExperienceInformationShouldBeFound("startDate.specified=true");

        // Get all the experienceInformationList where startDate is null
        defaultExperienceInformationShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where startDate greater than or equals to DEFAULT_START_DATE
        defaultExperienceInformationShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the experienceInformationList where startDate greater than or equals to UPDATED_START_DATE
        defaultExperienceInformationShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where startDate less than or equals to DEFAULT_START_DATE
        defaultExperienceInformationShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the experienceInformationList where startDate less than or equals to UPDATED_START_DATE
        defaultExperienceInformationShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllExperienceInformationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where endDate equals to DEFAULT_END_DATE
        defaultExperienceInformationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the experienceInformationList where endDate equals to UPDATED_END_DATE
        defaultExperienceInformationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultExperienceInformationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the experienceInformationList where endDate equals to UPDATED_END_DATE
        defaultExperienceInformationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where endDate is not null
        defaultExperienceInformationShouldBeFound("endDate.specified=true");

        // Get all the experienceInformationList where endDate is null
        defaultExperienceInformationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where endDate greater than or equals to DEFAULT_END_DATE
        defaultExperienceInformationShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the experienceInformationList where endDate greater than or equals to UPDATED_END_DATE
        defaultExperienceInformationShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where endDate less than or equals to DEFAULT_END_DATE
        defaultExperienceInformationShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the experienceInformationList where endDate less than or equals to UPDATED_END_DATE
        defaultExperienceInformationShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllExperienceInformationsByEmploymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where employmentType equals to DEFAULT_EMPLOYMENT_TYPE
        defaultExperienceInformationShouldBeFound("employmentType.equals=" + DEFAULT_EMPLOYMENT_TYPE);

        // Get all the experienceInformationList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultExperienceInformationShouldNotBeFound("employmentType.equals=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEmploymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where employmentType in DEFAULT_EMPLOYMENT_TYPE or UPDATED_EMPLOYMENT_TYPE
        defaultExperienceInformationShouldBeFound("employmentType.in=" + DEFAULT_EMPLOYMENT_TYPE + "," + UPDATED_EMPLOYMENT_TYPE);

        // Get all the experienceInformationList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultExperienceInformationShouldNotBeFound("employmentType.in=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEmploymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        // Get all the experienceInformationList where employmentType is not null
        defaultExperienceInformationShouldBeFound("employmentType.specified=true");

        // Get all the experienceInformationList where employmentType is null
        defaultExperienceInformationShouldNotBeFound("employmentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllExperienceInformationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        experienceInformation.setEmployee(employee);
        experienceInformationRepository.saveAndFlush(experienceInformation);
        Long employeeId = employee.getId();

        // Get all the experienceInformationList where employee equals to employeeId
        defaultExperienceInformationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the experienceInformationList where employee equals to employeeId + 1
        defaultExperienceInformationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultExperienceInformationShouldBeFound(String filter) throws Exception {
        restExperienceInformationMockMvc.perform(get("/api/experience-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())));

        // Check, that the count call also returns 1
        restExperienceInformationMockMvc.perform(get("/api/experience-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultExperienceInformationShouldNotBeFound(String filter) throws Exception {
        restExperienceInformationMockMvc.perform(get("/api/experience-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExperienceInformationMockMvc.perform(get("/api/experience-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingExperienceInformation() throws Exception {
        // Get the experienceInformation
        restExperienceInformationMockMvc.perform(get("/api/experience-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperienceInformation() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        int databaseSizeBeforeUpdate = experienceInformationRepository.findAll().size();

        // Update the experienceInformation
        ExperienceInformation updatedExperienceInformation = experienceInformationRepository.findById(experienceInformation.getId()).get();
        // Disconnect from session so that the updates on updatedExperienceInformation are not directly saved in db
        em.detach(updatedExperienceInformation);
        updatedExperienceInformation
            .organization(UPDATED_ORGANIZATION)
            .designation(UPDATED_DESIGNATION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .employmentType(UPDATED_EMPLOYMENT_TYPE);
        ExperienceInformationDTO experienceInformationDTO = experienceInformationMapper.toDto(updatedExperienceInformation);

        restExperienceInformationMockMvc.perform(put("/api/experience-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationDTO)))
            .andExpect(status().isOk());

        // Validate the ExperienceInformation in the database
        List<ExperienceInformation> experienceInformationList = experienceInformationRepository.findAll();
        assertThat(experienceInformationList).hasSize(databaseSizeBeforeUpdate);
        ExperienceInformation testExperienceInformation = experienceInformationList.get(experienceInformationList.size() - 1);
        assertThat(testExperienceInformation.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testExperienceInformation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testExperienceInformation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExperienceInformation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExperienceInformation.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);

        // Validate the ExperienceInformation in Elasticsearch
        verify(mockExperienceInformationSearchRepository, times(1)).save(testExperienceInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingExperienceInformation() throws Exception {
        int databaseSizeBeforeUpdate = experienceInformationRepository.findAll().size();

        // Create the ExperienceInformation
        ExperienceInformationDTO experienceInformationDTO = experienceInformationMapper.toDto(experienceInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceInformationMockMvc.perform(put("/api/experience-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceInformation in the database
        List<ExperienceInformation> experienceInformationList = experienceInformationRepository.findAll();
        assertThat(experienceInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExperienceInformation in Elasticsearch
        verify(mockExperienceInformationSearchRepository, times(0)).save(experienceInformation);
    }

    @Test
    @Transactional
    public void deleteExperienceInformation() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);

        int databaseSizeBeforeDelete = experienceInformationRepository.findAll().size();

        // Delete the experienceInformation
        restExperienceInformationMockMvc.perform(delete("/api/experience-informations/{id}", experienceInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperienceInformation> experienceInformationList = experienceInformationRepository.findAll();
        assertThat(experienceInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExperienceInformation in Elasticsearch
        verify(mockExperienceInformationSearchRepository, times(1)).deleteById(experienceInformation.getId());
    }

    @Test
    @Transactional
    public void searchExperienceInformation() throws Exception {
        // Initialize the database
        experienceInformationRepository.saveAndFlush(experienceInformation);
        when(mockExperienceInformationSearchRepository.search(queryStringQuery("id:" + experienceInformation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(experienceInformation), PageRequest.of(0, 1), 1));
        // Search the experienceInformation
        restExperienceInformationMockMvc.perform(get("/api/_search/experience-informations?query=id:" + experienceInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceInformation.class);
        ExperienceInformation experienceInformation1 = new ExperienceInformation();
        experienceInformation1.setId(1L);
        ExperienceInformation experienceInformation2 = new ExperienceInformation();
        experienceInformation2.setId(experienceInformation1.getId());
        assertThat(experienceInformation1).isEqualTo(experienceInformation2);
        experienceInformation2.setId(2L);
        assertThat(experienceInformation1).isNotEqualTo(experienceInformation2);
        experienceInformation1.setId(null);
        assertThat(experienceInformation1).isNotEqualTo(experienceInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceInformationDTO.class);
        ExperienceInformationDTO experienceInformationDTO1 = new ExperienceInformationDTO();
        experienceInformationDTO1.setId(1L);
        ExperienceInformationDTO experienceInformationDTO2 = new ExperienceInformationDTO();
        assertThat(experienceInformationDTO1).isNotEqualTo(experienceInformationDTO2);
        experienceInformationDTO2.setId(experienceInformationDTO1.getId());
        assertThat(experienceInformationDTO1).isEqualTo(experienceInformationDTO2);
        experienceInformationDTO2.setId(2L);
        assertThat(experienceInformationDTO1).isNotEqualTo(experienceInformationDTO2);
        experienceInformationDTO1.setId(null);
        assertThat(experienceInformationDTO1).isNotEqualTo(experienceInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(experienceInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(experienceInformationMapper.fromId(null)).isNull();
    }
}
