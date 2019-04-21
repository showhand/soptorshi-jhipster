package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.AcademicInformation;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AcademicInformationRepository;
import org.soptorshi.repository.search.AcademicInformationSearchRepository;
import org.soptorshi.service.AcademicInformationService;
import org.soptorshi.service.dto.AcademicInformationDTO;
import org.soptorshi.service.mapper.AcademicInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.AcademicInformationCriteria;
import org.soptorshi.service.AcademicInformationQueryService;

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
 * Test class for the AcademicInformationResource REST controller.
 *
 * @see AcademicInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AcademicInformationResourceIntTest {

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_BOARD_OR_UNIVERSITY = "AAAAAAAAAA";
    private static final String UPDATED_BOARD_OR_UNIVERSITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_PASSING_YEAR = 1;
    private static final Integer UPDATED_PASSING_YEAR = 2;

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    @Autowired
    private AcademicInformationRepository academicInformationRepository;

    @Autowired
    private AcademicInformationMapper academicInformationMapper;

    @Autowired
    private AcademicInformationService academicInformationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AcademicInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private AcademicInformationSearchRepository mockAcademicInformationSearchRepository;

    @Autowired
    private AcademicInformationQueryService academicInformationQueryService;

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

    private MockMvc restAcademicInformationMockMvc;

    private AcademicInformation academicInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademicInformationResource academicInformationResource = new AcademicInformationResource(academicInformationService, academicInformationQueryService);
        this.restAcademicInformationMockMvc = MockMvcBuilders.standaloneSetup(academicInformationResource)
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
    public static AcademicInformation createEntity(EntityManager em) {
        AcademicInformation academicInformation = new AcademicInformation()
            .degree(DEFAULT_DEGREE)
            .boardOrUniversity(DEFAULT_BOARD_OR_UNIVERSITY)
            .passingYear(DEFAULT_PASSING_YEAR)
            .group(DEFAULT_GROUP);
        return academicInformation;
    }

    @Before
    public void initTest() {
        academicInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicInformation() throws Exception {
        int databaseSizeBeforeCreate = academicInformationRepository.findAll().size();

        // Create the AcademicInformation
        AcademicInformationDTO academicInformationDTO = academicInformationMapper.toDto(academicInformation);
        restAcademicInformationMockMvc.perform(post("/api/academic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicInformation in the database
        List<AcademicInformation> academicInformationList = academicInformationRepository.findAll();
        assertThat(academicInformationList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicInformation testAcademicInformation = academicInformationList.get(academicInformationList.size() - 1);
        assertThat(testAcademicInformation.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testAcademicInformation.getBoardOrUniversity()).isEqualTo(DEFAULT_BOARD_OR_UNIVERSITY);
        assertThat(testAcademicInformation.getPassingYear()).isEqualTo(DEFAULT_PASSING_YEAR);
        assertThat(testAcademicInformation.getGroup()).isEqualTo(DEFAULT_GROUP);

        // Validate the AcademicInformation in Elasticsearch
        verify(mockAcademicInformationSearchRepository, times(1)).save(testAcademicInformation);
    }

    @Test
    @Transactional
    public void createAcademicInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicInformationRepository.findAll().size();

        // Create the AcademicInformation with an existing ID
        academicInformation.setId(1L);
        AcademicInformationDTO academicInformationDTO = academicInformationMapper.toDto(academicInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicInformationMockMvc.perform(post("/api/academic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicInformation in the database
        List<AcademicInformation> academicInformationList = academicInformationRepository.findAll();
        assertThat(academicInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the AcademicInformation in Elasticsearch
        verify(mockAcademicInformationSearchRepository, times(0)).save(academicInformation);
    }

    @Test
    @Transactional
    public void getAllAcademicInformations() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList
        restAcademicInformationMockMvc.perform(get("/api/academic-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].boardOrUniversity").value(hasItem(DEFAULT_BOARD_OR_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())));
    }
    
    @Test
    @Transactional
    public void getAcademicInformation() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get the academicInformation
        restAcademicInformationMockMvc.perform(get("/api/academic-informations/{id}", academicInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicInformation.getId().intValue()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.boardOrUniversity").value(DEFAULT_BOARD_OR_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.passingYear").value(DEFAULT_PASSING_YEAR))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()));
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where degree equals to DEFAULT_DEGREE
        defaultAcademicInformationShouldBeFound("degree.equals=" + DEFAULT_DEGREE);

        // Get all the academicInformationList where degree equals to UPDATED_DEGREE
        defaultAcademicInformationShouldNotBeFound("degree.equals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where degree in DEFAULT_DEGREE or UPDATED_DEGREE
        defaultAcademicInformationShouldBeFound("degree.in=" + DEFAULT_DEGREE + "," + UPDATED_DEGREE);

        // Get all the academicInformationList where degree equals to UPDATED_DEGREE
        defaultAcademicInformationShouldNotBeFound("degree.in=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where degree is not null
        defaultAcademicInformationShouldBeFound("degree.specified=true");

        // Get all the academicInformationList where degree is null
        defaultAcademicInformationShouldNotBeFound("degree.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByBoardOrUniversityIsEqualToSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where boardOrUniversity equals to DEFAULT_BOARD_OR_UNIVERSITY
        defaultAcademicInformationShouldBeFound("boardOrUniversity.equals=" + DEFAULT_BOARD_OR_UNIVERSITY);

        // Get all the academicInformationList where boardOrUniversity equals to UPDATED_BOARD_OR_UNIVERSITY
        defaultAcademicInformationShouldNotBeFound("boardOrUniversity.equals=" + UPDATED_BOARD_OR_UNIVERSITY);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByBoardOrUniversityIsInShouldWork() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where boardOrUniversity in DEFAULT_BOARD_OR_UNIVERSITY or UPDATED_BOARD_OR_UNIVERSITY
        defaultAcademicInformationShouldBeFound("boardOrUniversity.in=" + DEFAULT_BOARD_OR_UNIVERSITY + "," + UPDATED_BOARD_OR_UNIVERSITY);

        // Get all the academicInformationList where boardOrUniversity equals to UPDATED_BOARD_OR_UNIVERSITY
        defaultAcademicInformationShouldNotBeFound("boardOrUniversity.in=" + UPDATED_BOARD_OR_UNIVERSITY);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByBoardOrUniversityIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where boardOrUniversity is not null
        defaultAcademicInformationShouldBeFound("boardOrUniversity.specified=true");

        // Get all the academicInformationList where boardOrUniversity is null
        defaultAcademicInformationShouldNotBeFound("boardOrUniversity.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByPassingYearIsEqualToSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where passingYear equals to DEFAULT_PASSING_YEAR
        defaultAcademicInformationShouldBeFound("passingYear.equals=" + DEFAULT_PASSING_YEAR);

        // Get all the academicInformationList where passingYear equals to UPDATED_PASSING_YEAR
        defaultAcademicInformationShouldNotBeFound("passingYear.equals=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByPassingYearIsInShouldWork() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where passingYear in DEFAULT_PASSING_YEAR or UPDATED_PASSING_YEAR
        defaultAcademicInformationShouldBeFound("passingYear.in=" + DEFAULT_PASSING_YEAR + "," + UPDATED_PASSING_YEAR);

        // Get all the academicInformationList where passingYear equals to UPDATED_PASSING_YEAR
        defaultAcademicInformationShouldNotBeFound("passingYear.in=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByPassingYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where passingYear is not null
        defaultAcademicInformationShouldBeFound("passingYear.specified=true");

        // Get all the academicInformationList where passingYear is null
        defaultAcademicInformationShouldNotBeFound("passingYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByPassingYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where passingYear greater than or equals to DEFAULT_PASSING_YEAR
        defaultAcademicInformationShouldBeFound("passingYear.greaterOrEqualThan=" + DEFAULT_PASSING_YEAR);

        // Get all the academicInformationList where passingYear greater than or equals to UPDATED_PASSING_YEAR
        defaultAcademicInformationShouldNotBeFound("passingYear.greaterOrEqualThan=" + UPDATED_PASSING_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByPassingYearIsLessThanSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where passingYear less than or equals to DEFAULT_PASSING_YEAR
        defaultAcademicInformationShouldNotBeFound("passingYear.lessThan=" + DEFAULT_PASSING_YEAR);

        // Get all the academicInformationList where passingYear less than or equals to UPDATED_PASSING_YEAR
        defaultAcademicInformationShouldBeFound("passingYear.lessThan=" + UPDATED_PASSING_YEAR);
    }


    @Test
    @Transactional
    public void getAllAcademicInformationsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where group equals to DEFAULT_GROUP
        defaultAcademicInformationShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the academicInformationList where group equals to UPDATED_GROUP
        defaultAcademicInformationShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultAcademicInformationShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the academicInformationList where group equals to UPDATED_GROUP
        defaultAcademicInformationShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        // Get all the academicInformationList where group is not null
        defaultAcademicInformationShouldBeFound("group.specified=true");

        // Get all the academicInformationList where group is null
        defaultAcademicInformationShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicInformationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        academicInformation.setEmployee(employee);
        academicInformationRepository.saveAndFlush(academicInformation);
        Long employeeId = employee.getId();

        // Get all the academicInformationList where employee equals to employeeId
        defaultAcademicInformationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the academicInformationList where employee equals to employeeId + 1
        defaultAcademicInformationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAcademicInformationShouldBeFound(String filter) throws Exception {
        restAcademicInformationMockMvc.perform(get("/api/academic-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].boardOrUniversity").value(hasItem(DEFAULT_BOARD_OR_UNIVERSITY)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)));

        // Check, that the count call also returns 1
        restAcademicInformationMockMvc.perform(get("/api/academic-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAcademicInformationShouldNotBeFound(String filter) throws Exception {
        restAcademicInformationMockMvc.perform(get("/api/academic-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAcademicInformationMockMvc.perform(get("/api/academic-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAcademicInformation() throws Exception {
        // Get the academicInformation
        restAcademicInformationMockMvc.perform(get("/api/academic-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicInformation() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        int databaseSizeBeforeUpdate = academicInformationRepository.findAll().size();

        // Update the academicInformation
        AcademicInformation updatedAcademicInformation = academicInformationRepository.findById(academicInformation.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicInformation are not directly saved in db
        em.detach(updatedAcademicInformation);
        updatedAcademicInformation
            .degree(UPDATED_DEGREE)
            .boardOrUniversity(UPDATED_BOARD_OR_UNIVERSITY)
            .passingYear(UPDATED_PASSING_YEAR)
            .group(UPDATED_GROUP);
        AcademicInformationDTO academicInformationDTO = academicInformationMapper.toDto(updatedAcademicInformation);

        restAcademicInformationMockMvc.perform(put("/api/academic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicInformation in the database
        List<AcademicInformation> academicInformationList = academicInformationRepository.findAll();
        assertThat(academicInformationList).hasSize(databaseSizeBeforeUpdate);
        AcademicInformation testAcademicInformation = academicInformationList.get(academicInformationList.size() - 1);
        assertThat(testAcademicInformation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testAcademicInformation.getBoardOrUniversity()).isEqualTo(UPDATED_BOARD_OR_UNIVERSITY);
        assertThat(testAcademicInformation.getPassingYear()).isEqualTo(UPDATED_PASSING_YEAR);
        assertThat(testAcademicInformation.getGroup()).isEqualTo(UPDATED_GROUP);

        // Validate the AcademicInformation in Elasticsearch
        verify(mockAcademicInformationSearchRepository, times(1)).save(testAcademicInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicInformation() throws Exception {
        int databaseSizeBeforeUpdate = academicInformationRepository.findAll().size();

        // Create the AcademicInformation
        AcademicInformationDTO academicInformationDTO = academicInformationMapper.toDto(academicInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicInformationMockMvc.perform(put("/api/academic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicInformation in the database
        List<AcademicInformation> academicInformationList = academicInformationRepository.findAll();
        assertThat(academicInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AcademicInformation in Elasticsearch
        verify(mockAcademicInformationSearchRepository, times(0)).save(academicInformation);
    }

    @Test
    @Transactional
    public void deleteAcademicInformation() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);

        int databaseSizeBeforeDelete = academicInformationRepository.findAll().size();

        // Delete the academicInformation
        restAcademicInformationMockMvc.perform(delete("/api/academic-informations/{id}", academicInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AcademicInformation> academicInformationList = academicInformationRepository.findAll();
        assertThat(academicInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AcademicInformation in Elasticsearch
        verify(mockAcademicInformationSearchRepository, times(1)).deleteById(academicInformation.getId());
    }

    @Test
    @Transactional
    public void searchAcademicInformation() throws Exception {
        // Initialize the database
        academicInformationRepository.saveAndFlush(academicInformation);
        when(mockAcademicInformationSearchRepository.search(queryStringQuery("id:" + academicInformation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(academicInformation), PageRequest.of(0, 1), 1));
        // Search the academicInformation
        restAcademicInformationMockMvc.perform(get("/api/_search/academic-informations?query=id:" + academicInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].boardOrUniversity").value(hasItem(DEFAULT_BOARD_OR_UNIVERSITY)))
            .andExpect(jsonPath("$.[*].passingYear").value(hasItem(DEFAULT_PASSING_YEAR)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicInformation.class);
        AcademicInformation academicInformation1 = new AcademicInformation();
        academicInformation1.setId(1L);
        AcademicInformation academicInformation2 = new AcademicInformation();
        academicInformation2.setId(academicInformation1.getId());
        assertThat(academicInformation1).isEqualTo(academicInformation2);
        academicInformation2.setId(2L);
        assertThat(academicInformation1).isNotEqualTo(academicInformation2);
        academicInformation1.setId(null);
        assertThat(academicInformation1).isNotEqualTo(academicInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicInformationDTO.class);
        AcademicInformationDTO academicInformationDTO1 = new AcademicInformationDTO();
        academicInformationDTO1.setId(1L);
        AcademicInformationDTO academicInformationDTO2 = new AcademicInformationDTO();
        assertThat(academicInformationDTO1).isNotEqualTo(academicInformationDTO2);
        academicInformationDTO2.setId(academicInformationDTO1.getId());
        assertThat(academicInformationDTO1).isEqualTo(academicInformationDTO2);
        academicInformationDTO2.setId(2L);
        assertThat(academicInformationDTO1).isNotEqualTo(academicInformationDTO2);
        academicInformationDTO1.setId(null);
        assertThat(academicInformationDTO1).isNotEqualTo(academicInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(academicInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(academicInformationMapper.fromId(null)).isNull();
    }
}
