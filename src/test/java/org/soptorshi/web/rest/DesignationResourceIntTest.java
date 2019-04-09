package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Designation;
import org.soptorshi.repository.DesignationRepository;
import org.soptorshi.repository.search.DesignationSearchRepository;
import org.soptorshi.service.DesignationService;
import org.soptorshi.service.dto.DesignationDTO;
import org.soptorshi.service.mapper.DesignationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DesignationCriteria;
import org.soptorshi.service.DesignationQueryService;

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
 * Test class for the DesignationResource REST controller.
 *
 * @see DesignationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DesignationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationMapper designationMapper;

    @Autowired
    private DesignationService designationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DesignationSearchRepositoryMockConfiguration
     */
    @Autowired
    private DesignationSearchRepository mockDesignationSearchRepository;

    @Autowired
    private DesignationQueryService designationQueryService;

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

    private MockMvc restDesignationMockMvc;

    private Designation designation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignationResource designationResource = new DesignationResource(designationService, designationQueryService);
        this.restDesignationMockMvc = MockMvcBuilders.standaloneSetup(designationResource)
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
    public static Designation createEntity(EntityManager em) {
        Designation designation = new Designation()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return designation;
    }

    @Before
    public void initTest() {
        designation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesignation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(1)).save(testDesignation);
    }

    @Test
    @Transactional
    public void createDesignationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation with an existing ID
        designation.setId(1L);
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(0)).save(designation);
    }

    @Test
    @Transactional
    public void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name equals to DEFAULT_NAME
        defaultDesignationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDesignationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the designationList where name equals to UPDATED_NAME
        defaultDesignationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where name is not null
        defaultDesignationShouldBeFound("name.specified=true");

        // Get all the designationList where name is null
        defaultDesignationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName equals to DEFAULT_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the designationList where shortName equals to UPDATED_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultDesignationShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the designationList where shortName equals to UPDATED_SHORT_NAME
        defaultDesignationShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllDesignationsByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where shortName is not null
        defaultDesignationShouldBeFound("shortName.specified=true");

        // Get all the designationList where shortName is null
        defaultDesignationShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDesignationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description equals to DEFAULT_DESCRIPTION
        defaultDesignationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the designationList where description equals to UPDATED_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDesignationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDesignationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the designationList where description equals to UPDATED_DESCRIPTION
        defaultDesignationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDesignationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        // Get all the designationList where description is not null
        defaultDesignationShouldBeFound("description.specified=true");

        // Get all the designationList where description is null
        defaultDesignationShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDesignationShouldBeFound(String filter) throws Exception {
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restDesignationMockMvc.perform(get("/api/designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDesignationShouldNotBeFound(String filter) throws Exception {
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDesignationMockMvc.perform(get("/api/designations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findById(designation.getId()).get();
        // Disconnect from session so that the updates on updatedDesignation are not directly saved in db
        em.detach(updatedDesignation);
        updatedDesignation
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION);
        DesignationDTO designationDTO = designationMapper.toDto(updatedDesignation);

        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesignation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testDesignation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(1)).save(testDesignation);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.toDto(designation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(0)).save(designation);
    }

    @Test
    @Transactional
    public void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);

        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Delete the designation
        restDesignationMockMvc.perform(delete("/api/designations/{id}", designation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Designation in Elasticsearch
        verify(mockDesignationSearchRepository, times(1)).deleteById(designation.getId());
    }

    @Test
    @Transactional
    public void searchDesignation() throws Exception {
        // Initialize the database
        designationRepository.saveAndFlush(designation);
        when(mockDesignationSearchRepository.search(queryStringQuery("id:" + designation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(designation), PageRequest.of(0, 1), 1));
        // Search the designation
        restDesignationMockMvc.perform(get("/api/_search/designations?query=id:" + designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designation.class);
        Designation designation1 = new Designation();
        designation1.setId(1L);
        Designation designation2 = new Designation();
        designation2.setId(designation1.getId());
        assertThat(designation1).isEqualTo(designation2);
        designation2.setId(2L);
        assertThat(designation1).isNotEqualTo(designation2);
        designation1.setId(null);
        assertThat(designation1).isNotEqualTo(designation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationDTO.class);
        DesignationDTO designationDTO1 = new DesignationDTO();
        designationDTO1.setId(1L);
        DesignationDTO designationDTO2 = new DesignationDTO();
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
        designationDTO2.setId(designationDTO1.getId());
        assertThat(designationDTO1).isEqualTo(designationDTO2);
        designationDTO2.setId(2L);
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
        designationDTO1.setId(null);
        assertThat(designationDTO1).isNotEqualTo(designationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(designationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(designationMapper.fromId(null)).isNull();
    }
}
