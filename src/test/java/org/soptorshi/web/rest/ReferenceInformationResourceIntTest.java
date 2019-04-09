package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ReferenceInformation;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.ReferenceInformationRepository;
import org.soptorshi.repository.search.ReferenceInformationSearchRepository;
import org.soptorshi.service.ReferenceInformationService;
import org.soptorshi.service.dto.ReferenceInformationDTO;
import org.soptorshi.service.mapper.ReferenceInformationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ReferenceInformationCriteria;
import org.soptorshi.service.ReferenceInformationQueryService;

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
 * Test class for the ReferenceInformationResource REST controller.
 *
 * @see ReferenceInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ReferenceInformationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ReferenceInformationRepository referenceInformationRepository;

    @Autowired
    private ReferenceInformationMapper referenceInformationMapper;

    @Autowired
    private ReferenceInformationService referenceInformationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ReferenceInformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReferenceInformationSearchRepository mockReferenceInformationSearchRepository;

    @Autowired
    private ReferenceInformationQueryService referenceInformationQueryService;

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

    private MockMvc restReferenceInformationMockMvc;

    private ReferenceInformation referenceInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReferenceInformationResource referenceInformationResource = new ReferenceInformationResource(referenceInformationService, referenceInformationQueryService);
        this.restReferenceInformationMockMvc = MockMvcBuilders.standaloneSetup(referenceInformationResource)
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
    public static ReferenceInformation createEntity(EntityManager em) {
        ReferenceInformation referenceInformation = new ReferenceInformation()
            .name(DEFAULT_NAME)
            .designation(DEFAULT_DESIGNATION)
            .organization(DEFAULT_ORGANIZATION)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return referenceInformation;
    }

    @Before
    public void initTest() {
        referenceInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenceInformation() throws Exception {
        int databaseSizeBeforeCreate = referenceInformationRepository.findAll().size();

        // Create the ReferenceInformation
        ReferenceInformationDTO referenceInformationDTO = referenceInformationMapper.toDto(referenceInformation);
        restReferenceInformationMockMvc.perform(post("/api/reference-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferenceInformation in the database
        List<ReferenceInformation> referenceInformationList = referenceInformationRepository.findAll();
        assertThat(referenceInformationList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceInformation testReferenceInformation = referenceInformationList.get(referenceInformationList.size() - 1);
        assertThat(testReferenceInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReferenceInformation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testReferenceInformation.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testReferenceInformation.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);

        // Validate the ReferenceInformation in Elasticsearch
        verify(mockReferenceInformationSearchRepository, times(1)).save(testReferenceInformation);
    }

    @Test
    @Transactional
    public void createReferenceInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenceInformationRepository.findAll().size();

        // Create the ReferenceInformation with an existing ID
        referenceInformation.setId(1L);
        ReferenceInformationDTO referenceInformationDTO = referenceInformationMapper.toDto(referenceInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceInformationMockMvc.perform(post("/api/reference-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceInformation in the database
        List<ReferenceInformation> referenceInformationList = referenceInformationRepository.findAll();
        assertThat(referenceInformationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReferenceInformation in Elasticsearch
        verify(mockReferenceInformationSearchRepository, times(0)).save(referenceInformation);
    }

    @Test
    @Transactional
    public void getAllReferenceInformations() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList
        restReferenceInformationMockMvc.perform(get("/api/reference-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getReferenceInformation() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get the referenceInformation
        restReferenceInformationMockMvc.perform(get("/api/reference-informations/{id}", referenceInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(referenceInformation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where name equals to DEFAULT_NAME
        defaultReferenceInformationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the referenceInformationList where name equals to UPDATED_NAME
        defaultReferenceInformationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReferenceInformationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the referenceInformationList where name equals to UPDATED_NAME
        defaultReferenceInformationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where name is not null
        defaultReferenceInformationShouldBeFound("name.specified=true");

        // Get all the referenceInformationList where name is null
        defaultReferenceInformationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where designation equals to DEFAULT_DESIGNATION
        defaultReferenceInformationShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the referenceInformationList where designation equals to UPDATED_DESIGNATION
        defaultReferenceInformationShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultReferenceInformationShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the referenceInformationList where designation equals to UPDATED_DESIGNATION
        defaultReferenceInformationShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where designation is not null
        defaultReferenceInformationShouldBeFound("designation.specified=true");

        // Get all the referenceInformationList where designation is null
        defaultReferenceInformationShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where organization equals to DEFAULT_ORGANIZATION
        defaultReferenceInformationShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the referenceInformationList where organization equals to UPDATED_ORGANIZATION
        defaultReferenceInformationShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultReferenceInformationShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the referenceInformationList where organization equals to UPDATED_ORGANIZATION
        defaultReferenceInformationShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where organization is not null
        defaultReferenceInformationShouldBeFound("organization.specified=true");

        // Get all the referenceInformationList where organization is null
        defaultReferenceInformationShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultReferenceInformationShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the referenceInformationList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultReferenceInformationShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultReferenceInformationShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the referenceInformationList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultReferenceInformationShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        // Get all the referenceInformationList where contactNumber is not null
        defaultReferenceInformationShouldBeFound("contactNumber.specified=true");

        // Get all the referenceInformationList where contactNumber is null
        defaultReferenceInformationShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllReferenceInformationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        referenceInformation.setEmployee(employee);
        referenceInformationRepository.saveAndFlush(referenceInformation);
        Long employeeId = employee.getId();

        // Get all the referenceInformationList where employee equals to employeeId
        defaultReferenceInformationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the referenceInformationList where employee equals to employeeId + 1
        defaultReferenceInformationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReferenceInformationShouldBeFound(String filter) throws Exception {
        restReferenceInformationMockMvc.perform(get("/api/reference-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));

        // Check, that the count call also returns 1
        restReferenceInformationMockMvc.perform(get("/api/reference-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReferenceInformationShouldNotBeFound(String filter) throws Exception {
        restReferenceInformationMockMvc.perform(get("/api/reference-informations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferenceInformationMockMvc.perform(get("/api/reference-informations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReferenceInformation() throws Exception {
        // Get the referenceInformation
        restReferenceInformationMockMvc.perform(get("/api/reference-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenceInformation() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        int databaseSizeBeforeUpdate = referenceInformationRepository.findAll().size();

        // Update the referenceInformation
        ReferenceInformation updatedReferenceInformation = referenceInformationRepository.findById(referenceInformation.getId()).get();
        // Disconnect from session so that the updates on updatedReferenceInformation are not directly saved in db
        em.detach(updatedReferenceInformation);
        updatedReferenceInformation
            .name(UPDATED_NAME)
            .designation(UPDATED_DESIGNATION)
            .organization(UPDATED_ORGANIZATION)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        ReferenceInformationDTO referenceInformationDTO = referenceInformationMapper.toDto(updatedReferenceInformation);

        restReferenceInformationMockMvc.perform(put("/api/reference-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceInformationDTO)))
            .andExpect(status().isOk());

        // Validate the ReferenceInformation in the database
        List<ReferenceInformation> referenceInformationList = referenceInformationRepository.findAll();
        assertThat(referenceInformationList).hasSize(databaseSizeBeforeUpdate);
        ReferenceInformation testReferenceInformation = referenceInformationList.get(referenceInformationList.size() - 1);
        assertThat(testReferenceInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReferenceInformation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testReferenceInformation.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testReferenceInformation.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);

        // Validate the ReferenceInformation in Elasticsearch
        verify(mockReferenceInformationSearchRepository, times(1)).save(testReferenceInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingReferenceInformation() throws Exception {
        int databaseSizeBeforeUpdate = referenceInformationRepository.findAll().size();

        // Create the ReferenceInformation
        ReferenceInformationDTO referenceInformationDTO = referenceInformationMapper.toDto(referenceInformation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceInformationMockMvc.perform(put("/api/reference-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(referenceInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceInformation in the database
        List<ReferenceInformation> referenceInformationList = referenceInformationRepository.findAll();
        assertThat(referenceInformationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReferenceInformation in Elasticsearch
        verify(mockReferenceInformationSearchRepository, times(0)).save(referenceInformation);
    }

    @Test
    @Transactional
    public void deleteReferenceInformation() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);

        int databaseSizeBeforeDelete = referenceInformationRepository.findAll().size();

        // Delete the referenceInformation
        restReferenceInformationMockMvc.perform(delete("/api/reference-informations/{id}", referenceInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReferenceInformation> referenceInformationList = referenceInformationRepository.findAll();
        assertThat(referenceInformationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReferenceInformation in Elasticsearch
        verify(mockReferenceInformationSearchRepository, times(1)).deleteById(referenceInformation.getId());
    }

    @Test
    @Transactional
    public void searchReferenceInformation() throws Exception {
        // Initialize the database
        referenceInformationRepository.saveAndFlush(referenceInformation);
        when(mockReferenceInformationSearchRepository.search(queryStringQuery("id:" + referenceInformation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(referenceInformation), PageRequest.of(0, 1), 1));
        // Search the referenceInformation
        restReferenceInformationMockMvc.perform(get("/api/_search/reference-informations?query=id:" + referenceInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceInformation.class);
        ReferenceInformation referenceInformation1 = new ReferenceInformation();
        referenceInformation1.setId(1L);
        ReferenceInformation referenceInformation2 = new ReferenceInformation();
        referenceInformation2.setId(referenceInformation1.getId());
        assertThat(referenceInformation1).isEqualTo(referenceInformation2);
        referenceInformation2.setId(2L);
        assertThat(referenceInformation1).isNotEqualTo(referenceInformation2);
        referenceInformation1.setId(null);
        assertThat(referenceInformation1).isNotEqualTo(referenceInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceInformationDTO.class);
        ReferenceInformationDTO referenceInformationDTO1 = new ReferenceInformationDTO();
        referenceInformationDTO1.setId(1L);
        ReferenceInformationDTO referenceInformationDTO2 = new ReferenceInformationDTO();
        assertThat(referenceInformationDTO1).isNotEqualTo(referenceInformationDTO2);
        referenceInformationDTO2.setId(referenceInformationDTO1.getId());
        assertThat(referenceInformationDTO1).isEqualTo(referenceInformationDTO2);
        referenceInformationDTO2.setId(2L);
        assertThat(referenceInformationDTO1).isNotEqualTo(referenceInformationDTO2);
        referenceInformationDTO1.setId(null);
        assertThat(referenceInformationDTO1).isNotEqualTo(referenceInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(referenceInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(referenceInformationMapper.fromId(null)).isNull();
    }
}
