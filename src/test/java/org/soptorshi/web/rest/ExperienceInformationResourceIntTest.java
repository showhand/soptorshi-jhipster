package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ExperienceInformation;
import org.soptorshi.repository.ExperienceInformationRepository;
import org.soptorshi.repository.search.ExperienceInformationSearchRepository;
import org.soptorshi.service.ExperienceInformationService;
import org.soptorshi.service.dto.ExperienceInformationDTO;
import org.soptorshi.service.mapper.ExperienceInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;

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
        final ExperienceInformationResource experienceInformationResource = new ExperienceInformationResource(experienceInformationService);
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
