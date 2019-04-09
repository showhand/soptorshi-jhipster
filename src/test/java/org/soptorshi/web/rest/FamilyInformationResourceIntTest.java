package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.FamilyInformation;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.FamilyInformationRepository;
import org.soptorshi.repository.search.FamilyInformationSearchRepository;
import org.soptorshi.service.FamilyInformationService;
import org.soptorshi.service.dto.FamilyInformationDTO;
import org.soptorshi.service.mapper.FamilyInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.FamilyInformationCriteria;
import org.soptorshi.service.FamilyInformationQueryService;

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
 * Test class for the FamilyInformationResource REST controller.
 *
 * @see FamilyInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class FamilyInformationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private FamilyInformationRepository familyInformationRepository;

    @Autowired
    private FamilyInformationMapper familyInformationMapper;

    @Autowired
    private FamilyInformationService familyInformationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.FamilyInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private FamilyInformationSearchRepository mockFamilyInformationSearchRepository;

    @Autowired
    private FamilyInformationQueryService familyInformationQueryService;

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

    private MockMvc restFamilyInformationMockMvc;

    private FamilyInformation familyInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyInformationResource familyInformationResource = new FamilyInformationResource(familyInformationService, familyInformationQueryService);
        this.restFamilyInformationMockMvc = MockMvcBuilders.standaloneSetup(familyInformationResource)
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
    public static FamilyInformation createEntity(EntityManager em) {
        FamilyInformation familyInformation = new FamilyInformation()
            .name(DEFAULT_NAME)
            .relation(DEFAULT_RELATION)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return familyInformation;
    }

    @Before
    public void initTest() {
        familyInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyInformation() throws Exception {
        int databaseSizeBeforeCreate = familyInformationRepository.findAll().size();

        // Create the FamilyInformation
        FamilyInformationDTO familyInformationDTO = familyInformationMapper.toDto(familyInformation);
        restFamilyInformationMockMvc.perform(post("/api/family-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the FamilyInformation in the database
        List<FamilyInformation> familyInformationList = familyInformationRepository.findAll();
        assertThat(familyInformationList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyInformation testFamilyInformation = familyInformationList.get(familyInformationList.size() - 1);
        assertThat(testFamilyInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFamilyInformation.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testFamilyInformation.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);

        // Validate the FamilyInformation in Elasticsearch
        verify(mockFamilyInformationSearchRepository, times(1)).save(testFamilyInformation);
    }

    @Test
    @Transactional
    public void createFamilyInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyInformationRepository.findAll().size();

        // Create the FamilyInformation with an existing ID
        familyInformation.setId(1L);
        FamilyInformationDTO familyInformationDTO = familyInformationMapper.toDto(familyInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyInformationMockMvc.perform(post("/api/family-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyInformation in the database
        List<FamilyInformation> familyInformationList = familyInformationRepository.findAll();
        assertThat(familyInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the FamilyInformation in Elasticsearch
        verify(mockFamilyInformationSearchRepository, times(0)).save(familyInformation);
    }

    @Test
    @Transactional
    public void getAllFamilyInformations() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList
        restFamilyInformationMockMvc.perform(get("/api/family-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getFamilyInformation() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get the familyInformation
        restFamilyInformationMockMvc.perform(get("/api/family-informations/{id}", familyInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familyInformation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where name equals to DEFAULT_NAME
        defaultFamilyInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the familyInformationList where name equals to UPDATED_NAME
        defaultFamilyInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFamilyInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the familyInformationList where name equals to UPDATED_NAME
        defaultFamilyInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where name is not null
        defaultFamilyInformationShouldBeFound("name.specified=true");

        // Get all the familyInformationList where name is null
        defaultFamilyInformationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where relation equals to DEFAULT_RELATION
        defaultFamilyInformationShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the familyInformationList where relation equals to UPDATED_RELATION
        defaultFamilyInformationShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultFamilyInformationShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the familyInformationList where relation equals to UPDATED_RELATION
        defaultFamilyInformationShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where relation is not null
        defaultFamilyInformationShouldBeFound("relation.specified=true");

        // Get all the familyInformationList where relation is null
        defaultFamilyInformationShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultFamilyInformationShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the familyInformationList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultFamilyInformationShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultFamilyInformationShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the familyInformationList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultFamilyInformationShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        // Get all the familyInformationList where contactNumber is not null
        defaultFamilyInformationShouldBeFound("contactNumber.specified=true");

        // Get all the familyInformationList where contactNumber is null
        defaultFamilyInformationShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllFamilyInformationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        familyInformation.setEmployee(employee);
        familyInformationRepository.saveAndFlush(familyInformation);
        Long employeeId = employee.getId();

        // Get all the familyInformationList where employee equals to employeeId
        defaultFamilyInformationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the familyInformationList where employee equals to employeeId + 1
        defaultFamilyInformationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFamilyInformationShouldBeFound(String filter) throws Exception {
        restFamilyInformationMockMvc.perform(get("/api/family-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));

        // Check, that the count call also returns 1
        restFamilyInformationMockMvc.perform(get("/api/family-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFamilyInformationShouldNotBeFound(String filter) throws Exception {
        restFamilyInformationMockMvc.perform(get("/api/family-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFamilyInformationMockMvc.perform(get("/api/family-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFamilyInformation() throws Exception {
        // Get the familyInformation
        restFamilyInformationMockMvc.perform(get("/api/family-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyInformation() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        int databaseSizeBeforeUpdate = familyInformationRepository.findAll().size();

        // Update the familyInformation
        FamilyInformation updatedFamilyInformation = familyInformationRepository.findById(familyInformation.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyInformation are not directly saved in db
        em.detach(updatedFamilyInformation);
        updatedFamilyInformation
            .name(UPDATED_NAME)
            .relation(UPDATED_RELATION)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        FamilyInformationDTO familyInformationDTO = familyInformationMapper.toDto(updatedFamilyInformation);

        restFamilyInformationMockMvc.perform(put("/api/family-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyInformationDTO)))
            .andExpect(status().isOk());

        // Validate the FamilyInformation in the database
        List<FamilyInformation> familyInformationList = familyInformationRepository.findAll();
        assertThat(familyInformationList).hasSize(databaseSizeBeforeUpdate);
        FamilyInformation testFamilyInformation = familyInformationList.get(familyInformationList.size() - 1);
        assertThat(testFamilyInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFamilyInformation.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testFamilyInformation.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);

        // Validate the FamilyInformation in Elasticsearch
        verify(mockFamilyInformationSearchRepository, times(1)).save(testFamilyInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyInformation() throws Exception {
        int databaseSizeBeforeUpdate = familyInformationRepository.findAll().size();

        // Create the FamilyInformation
        FamilyInformationDTO familyInformationDTO = familyInformationMapper.toDto(familyInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyInformationMockMvc.perform(put("/api/family-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyInformation in the database
        List<FamilyInformation> familyInformationList = familyInformationRepository.findAll();
        assertThat(familyInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FamilyInformation in Elasticsearch
        verify(mockFamilyInformationSearchRepository, times(0)).save(familyInformation);
    }

    @Test
    @Transactional
    public void deleteFamilyInformation() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);

        int databaseSizeBeforeDelete = familyInformationRepository.findAll().size();

        // Delete the familyInformation
        restFamilyInformationMockMvc.perform(delete("/api/family-informations/{id}", familyInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FamilyInformation> familyInformationList = familyInformationRepository.findAll();
        assertThat(familyInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FamilyInformation in Elasticsearch
        verify(mockFamilyInformationSearchRepository, times(1)).deleteById(familyInformation.getId());
    }

    @Test
    @Transactional
    public void searchFamilyInformation() throws Exception {
        // Initialize the database
        familyInformationRepository.saveAndFlush(familyInformation);
        when(mockFamilyInformationSearchRepository.search(queryStringQuery("id:" + familyInformation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(familyInformation), PageRequest.of(0, 1), 1));
        // Search the familyInformation
        restFamilyInformationMockMvc.perform(get("/api/_search/family-informations?query=id:" + familyInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyInformation.class);
        FamilyInformation familyInformation1 = new FamilyInformation();
        familyInformation1.setId(1L);
        FamilyInformation familyInformation2 = new FamilyInformation();
        familyInformation2.setId(familyInformation1.getId());
        assertThat(familyInformation1).isEqualTo(familyInformation2);
        familyInformation2.setId(2L);
        assertThat(familyInformation1).isNotEqualTo(familyInformation2);
        familyInformation1.setId(null);
        assertThat(familyInformation1).isNotEqualTo(familyInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyInformationDTO.class);
        FamilyInformationDTO familyInformationDTO1 = new FamilyInformationDTO();
        familyInformationDTO1.setId(1L);
        FamilyInformationDTO familyInformationDTO2 = new FamilyInformationDTO();
        assertThat(familyInformationDTO1).isNotEqualTo(familyInformationDTO2);
        familyInformationDTO2.setId(familyInformationDTO1.getId());
        assertThat(familyInformationDTO1).isEqualTo(familyInformationDTO2);
        familyInformationDTO2.setId(2L);
        assertThat(familyInformationDTO1).isNotEqualTo(familyInformationDTO2);
        familyInformationDTO1.setId(null);
        assertThat(familyInformationDTO1).isNotEqualTo(familyInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(familyInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(familyInformationMapper.fromId(null)).isNull();
    }
}
