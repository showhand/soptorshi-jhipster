package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.repository.InventorySubLocationRepository;
import org.soptorshi.repository.search.InventorySubLocationSearchRepository;
import org.soptorshi.service.InventorySubLocationService;
import org.soptorshi.service.dto.InventorySubLocationDTO;
import org.soptorshi.service.mapper.InventorySubLocationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.InventorySubLocationCriteria;
import org.soptorshi.service.InventorySubLocationQueryService;

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

import org.soptorshi.domain.enumeration.InventorySubLocationCategory;
/**
 * Test class for the InventorySubLocationResource REST controller.
 *
 * @see InventorySubLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class InventorySubLocationResourceIntTest {

    private static final InventorySubLocationCategory DEFAULT_CATEGORY = InventorySubLocationCategory.SHELF;
    private static final InventorySubLocationCategory UPDATED_CATEGORY = InventorySubLocationCategory.FREEZER;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InventorySubLocationRepository inventorySubLocationRepository;

    @Autowired
    private InventorySubLocationMapper inventorySubLocationMapper;

    @Autowired
    private InventorySubLocationService inventorySubLocationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.InventorySubLocationSearchRepositoryMockConfiguration
     */
    @Autowired
    private InventorySubLocationSearchRepository mockInventorySubLocationSearchRepository;

    @Autowired
    private InventorySubLocationQueryService inventorySubLocationQueryService;

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

    private MockMvc restInventorySubLocationMockMvc;

    private InventorySubLocation inventorySubLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventorySubLocationResource inventorySubLocationResource = new InventorySubLocationResource(inventorySubLocationService, inventorySubLocationQueryService);
        this.restInventorySubLocationMockMvc = MockMvcBuilders.standaloneSetup(inventorySubLocationResource)
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
    public static InventorySubLocation createEntity(EntityManager em) {
        InventorySubLocation inventorySubLocation = new InventorySubLocation()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return inventorySubLocation;
    }

    @Before
    public void initTest() {
        inventorySubLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventorySubLocation() throws Exception {
        int databaseSizeBeforeCreate = inventorySubLocationRepository.findAll().size();

        // Create the InventorySubLocation
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(inventorySubLocation);
        restInventorySubLocationMockMvc.perform(post("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the InventorySubLocation in the database
        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeCreate + 1);
        InventorySubLocation testInventorySubLocation = inventorySubLocationList.get(inventorySubLocationList.size() - 1);
        assertThat(testInventorySubLocation.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testInventorySubLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventorySubLocation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testInventorySubLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the InventorySubLocation in Elasticsearch
        verify(mockInventorySubLocationSearchRepository, times(1)).save(testInventorySubLocation);
    }

    @Test
    @Transactional
    public void createInventorySubLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventorySubLocationRepository.findAll().size();

        // Create the InventorySubLocation with an existing ID
        inventorySubLocation.setId(1L);
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(inventorySubLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventorySubLocationMockMvc.perform(post("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventorySubLocation in the database
        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeCreate);

        // Validate the InventorySubLocation in Elasticsearch
        verify(mockInventorySubLocationSearchRepository, times(0)).save(inventorySubLocation);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventorySubLocationRepository.findAll().size();
        // set the field null
        inventorySubLocation.setCategory(null);

        // Create the InventorySubLocation, which fails.
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(inventorySubLocation);

        restInventorySubLocationMockMvc.perform(post("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isBadRequest());

        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventorySubLocationRepository.findAll().size();
        // set the field null
        inventorySubLocation.setName(null);

        // Create the InventorySubLocation, which fails.
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(inventorySubLocation);

        restInventorySubLocationMockMvc.perform(post("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isBadRequest());

        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocations() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventorySubLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getInventorySubLocation() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get the inventorySubLocation
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations/{id}", inventorySubLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventorySubLocation.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where category equals to DEFAULT_CATEGORY
        defaultInventorySubLocationShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the inventorySubLocationList where category equals to UPDATED_CATEGORY
        defaultInventorySubLocationShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultInventorySubLocationShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the inventorySubLocationList where category equals to UPDATED_CATEGORY
        defaultInventorySubLocationShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where category is not null
        defaultInventorySubLocationShouldBeFound("category.specified=true");

        // Get all the inventorySubLocationList where category is null
        defaultInventorySubLocationShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where name equals to DEFAULT_NAME
        defaultInventorySubLocationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the inventorySubLocationList where name equals to UPDATED_NAME
        defaultInventorySubLocationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInventorySubLocationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the inventorySubLocationList where name equals to UPDATED_NAME
        defaultInventorySubLocationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where name is not null
        defaultInventorySubLocationShouldBeFound("name.specified=true");

        // Get all the inventorySubLocationList where name is null
        defaultInventorySubLocationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where shortName equals to DEFAULT_SHORT_NAME
        defaultInventorySubLocationShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the inventorySubLocationList where shortName equals to UPDATED_SHORT_NAME
        defaultInventorySubLocationShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultInventorySubLocationShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the inventorySubLocationList where shortName equals to UPDATED_SHORT_NAME
        defaultInventorySubLocationShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where shortName is not null
        defaultInventorySubLocationShouldBeFound("shortName.specified=true");

        // Get all the inventorySubLocationList where shortName is null
        defaultInventorySubLocationShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where description equals to DEFAULT_DESCRIPTION
        defaultInventorySubLocationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the inventorySubLocationList where description equals to UPDATED_DESCRIPTION
        defaultInventorySubLocationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInventorySubLocationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the inventorySubLocationList where description equals to UPDATED_DESCRIPTION
        defaultInventorySubLocationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        // Get all the inventorySubLocationList where description is not null
        defaultInventorySubLocationShouldBeFound("description.specified=true");

        // Get all the inventorySubLocationList where description is null
        defaultInventorySubLocationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventorySubLocationsByInventoryLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventoryLocation inventoryLocations = InventoryLocationResourceIntTest.createEntity(em);
        em.persist(inventoryLocations);
        em.flush();
        inventorySubLocation.setInventoryLocations(inventoryLocations);
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);
        Long inventoryLocationsId = inventoryLocations.getId();

        // Get all the inventorySubLocationList where inventoryLocations equals to inventoryLocationsId
        defaultInventorySubLocationShouldBeFound("inventoryLocationsId.equals=" + inventoryLocationsId);

        // Get all the inventorySubLocationList where inventoryLocations equals to inventoryLocationsId + 1
        defaultInventorySubLocationShouldNotBeFound("inventoryLocationsId.equals=" + (inventoryLocationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInventorySubLocationShouldBeFound(String filter) throws Exception {
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventorySubLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInventorySubLocationShouldNotBeFound(String filter) throws Exception {
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInventorySubLocation() throws Exception {
        // Get the inventorySubLocation
        restInventorySubLocationMockMvc.perform(get("/api/inventory-sub-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventorySubLocation() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        int databaseSizeBeforeUpdate = inventorySubLocationRepository.findAll().size();

        // Update the inventorySubLocation
        InventorySubLocation updatedInventorySubLocation = inventorySubLocationRepository.findById(inventorySubLocation.getId()).get();
        // Disconnect from session so that the updates on updatedInventorySubLocation are not directly saved in db
        em.detach(updatedInventorySubLocation);
        updatedInventorySubLocation
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION);
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(updatedInventorySubLocation);

        restInventorySubLocationMockMvc.perform(put("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isOk());

        // Validate the InventorySubLocation in the database
        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeUpdate);
        InventorySubLocation testInventorySubLocation = inventorySubLocationList.get(inventorySubLocationList.size() - 1);
        assertThat(testInventorySubLocation.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testInventorySubLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventorySubLocation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testInventorySubLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the InventorySubLocation in Elasticsearch
        verify(mockInventorySubLocationSearchRepository, times(1)).save(testInventorySubLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingInventorySubLocation() throws Exception {
        int databaseSizeBeforeUpdate = inventorySubLocationRepository.findAll().size();

        // Create the InventorySubLocation
        InventorySubLocationDTO inventorySubLocationDTO = inventorySubLocationMapper.toDto(inventorySubLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventorySubLocationMockMvc.perform(put("/api/inventory-sub-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventorySubLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventorySubLocation in the database
        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InventorySubLocation in Elasticsearch
        verify(mockInventorySubLocationSearchRepository, times(0)).save(inventorySubLocation);
    }

    @Test
    @Transactional
    public void deleteInventorySubLocation() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);

        int databaseSizeBeforeDelete = inventorySubLocationRepository.findAll().size();

        // Delete the inventorySubLocation
        restInventorySubLocationMockMvc.perform(delete("/api/inventory-sub-locations/{id}", inventorySubLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventorySubLocation> inventorySubLocationList = inventorySubLocationRepository.findAll();
        assertThat(inventorySubLocationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InventorySubLocation in Elasticsearch
        verify(mockInventorySubLocationSearchRepository, times(1)).deleteById(inventorySubLocation.getId());
    }

    @Test
    @Transactional
    public void searchInventorySubLocation() throws Exception {
        // Initialize the database
        inventorySubLocationRepository.saveAndFlush(inventorySubLocation);
        when(mockInventorySubLocationSearchRepository.search(queryStringQuery("id:" + inventorySubLocation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(inventorySubLocation), PageRequest.of(0, 1), 1));
        // Search the inventorySubLocation
        restInventorySubLocationMockMvc.perform(get("/api/_search/inventory-sub-locations?query=id:" + inventorySubLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventorySubLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventorySubLocation.class);
        InventorySubLocation inventorySubLocation1 = new InventorySubLocation();
        inventorySubLocation1.setId(1L);
        InventorySubLocation inventorySubLocation2 = new InventorySubLocation();
        inventorySubLocation2.setId(inventorySubLocation1.getId());
        assertThat(inventorySubLocation1).isEqualTo(inventorySubLocation2);
        inventorySubLocation2.setId(2L);
        assertThat(inventorySubLocation1).isNotEqualTo(inventorySubLocation2);
        inventorySubLocation1.setId(null);
        assertThat(inventorySubLocation1).isNotEqualTo(inventorySubLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventorySubLocationDTO.class);
        InventorySubLocationDTO inventorySubLocationDTO1 = new InventorySubLocationDTO();
        inventorySubLocationDTO1.setId(1L);
        InventorySubLocationDTO inventorySubLocationDTO2 = new InventorySubLocationDTO();
        assertThat(inventorySubLocationDTO1).isNotEqualTo(inventorySubLocationDTO2);
        inventorySubLocationDTO2.setId(inventorySubLocationDTO1.getId());
        assertThat(inventorySubLocationDTO1).isEqualTo(inventorySubLocationDTO2);
        inventorySubLocationDTO2.setId(2L);
        assertThat(inventorySubLocationDTO1).isNotEqualTo(inventorySubLocationDTO2);
        inventorySubLocationDTO1.setId(null);
        assertThat(inventorySubLocationDTO1).isNotEqualTo(inventorySubLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventorySubLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inventorySubLocationMapper.fromId(null)).isNull();
    }
}
