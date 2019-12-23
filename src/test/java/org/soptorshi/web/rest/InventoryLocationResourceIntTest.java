package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.repository.InventoryLocationRepository;
import org.soptorshi.repository.search.InventoryLocationSearchRepository;
import org.soptorshi.service.InventoryLocationService;
import org.soptorshi.service.dto.InventoryLocationDTO;
import org.soptorshi.service.mapper.InventoryLocationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.InventoryLocationCriteria;
import org.soptorshi.service.InventoryLocationQueryService;

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
 * Test class for the InventoryLocationResource REST controller.
 *
 * @see InventoryLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class InventoryLocationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InventoryLocationRepository inventoryLocationRepository;

    @Autowired
    private InventoryLocationMapper inventoryLocationMapper;

    @Autowired
    private InventoryLocationService inventoryLocationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.InventoryLocationSearchRepositoryMockConfiguration
     */
    @Autowired
    private InventoryLocationSearchRepository mockInventoryLocationSearchRepository;

    @Autowired
    private InventoryLocationQueryService inventoryLocationQueryService;

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

    private MockMvc restInventoryLocationMockMvc;

    private InventoryLocation inventoryLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryLocationResource inventoryLocationResource = new InventoryLocationResource(inventoryLocationService, inventoryLocationQueryService);
        this.restInventoryLocationMockMvc = MockMvcBuilders.standaloneSetup(inventoryLocationResource)
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
    public static InventoryLocation createEntity(EntityManager em) {
        InventoryLocation inventoryLocation = new InventoryLocation()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return inventoryLocation;
    }

    @Before
    public void initTest() {
        inventoryLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryLocation() throws Exception {
        int databaseSizeBeforeCreate = inventoryLocationRepository.findAll().size();

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);
        restInventoryLocationMockMvc.perform(post("/api/inventory-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the InventoryLocation in the database
        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryLocation testInventoryLocation = inventoryLocationList.get(inventoryLocationList.size() - 1);
        assertThat(testInventoryLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryLocation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testInventoryLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the InventoryLocation in Elasticsearch
        verify(mockInventoryLocationSearchRepository, times(1)).save(testInventoryLocation);
    }

    @Test
    @Transactional
    public void createInventoryLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryLocationRepository.findAll().size();

        // Create the InventoryLocation with an existing ID
        inventoryLocation.setId(1L);
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryLocationMockMvc.perform(post("/api/inventory-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeCreate);

        // Validate the InventoryLocation in Elasticsearch
        verify(mockInventoryLocationSearchRepository, times(0)).save(inventoryLocation);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryLocationRepository.findAll().size();
        // set the field null
        inventoryLocation.setName(null);

        // Create the InventoryLocation, which fails.
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        restInventoryLocationMockMvc.perform(post("/api/inventory-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventoryLocations() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getInventoryLocation() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get the inventoryLocation
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations/{id}", inventoryLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name equals to DEFAULT_NAME
        defaultInventoryLocationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the inventoryLocationList where name equals to UPDATED_NAME
        defaultInventoryLocationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInventoryLocationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the inventoryLocationList where name equals to UPDATED_NAME
        defaultInventoryLocationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where name is not null
        defaultInventoryLocationShouldBeFound("name.specified=true");

        // Get all the inventoryLocationList where name is null
        defaultInventoryLocationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where shortName equals to DEFAULT_SHORT_NAME
        defaultInventoryLocationShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the inventoryLocationList where shortName equals to UPDATED_SHORT_NAME
        defaultInventoryLocationShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultInventoryLocationShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the inventoryLocationList where shortName equals to UPDATED_SHORT_NAME
        defaultInventoryLocationShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where shortName is not null
        defaultInventoryLocationShouldBeFound("shortName.specified=true");

        // Get all the inventoryLocationList where shortName is null
        defaultInventoryLocationShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where description equals to DEFAULT_DESCRIPTION
        defaultInventoryLocationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryLocationList where description equals to UPDATED_DESCRIPTION
        defaultInventoryLocationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInventoryLocationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the inventoryLocationList where description equals to UPDATED_DESCRIPTION
        defaultInventoryLocationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoryLocationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        // Get all the inventoryLocationList where description is not null
        defaultInventoryLocationShouldBeFound("description.specified=true");

        // Get all the inventoryLocationList where description is null
        defaultInventoryLocationShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInventoryLocationShouldBeFound(String filter) throws Exception {
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInventoryLocationShouldNotBeFound(String filter) throws Exception {
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInventoryLocation() throws Exception {
        // Get the inventoryLocation
        restInventoryLocationMockMvc.perform(get("/api/inventory-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryLocation() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        int databaseSizeBeforeUpdate = inventoryLocationRepository.findAll().size();

        // Update the inventoryLocation
        InventoryLocation updatedInventoryLocation = inventoryLocationRepository.findById(inventoryLocation.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryLocation are not directly saved in db
        em.detach(updatedInventoryLocation);
        updatedInventoryLocation
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION);
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(updatedInventoryLocation);

        restInventoryLocationMockMvc.perform(put("/api/inventory-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLocationDTO)))
            .andExpect(status().isOk());

        // Validate the InventoryLocation in the database
        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeUpdate);
        InventoryLocation testInventoryLocation = inventoryLocationList.get(inventoryLocationList.size() - 1);
        assertThat(testInventoryLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryLocation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testInventoryLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the InventoryLocation in Elasticsearch
        verify(mockInventoryLocationSearchRepository, times(1)).save(testInventoryLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryLocation() throws Exception {
        int databaseSizeBeforeUpdate = inventoryLocationRepository.findAll().size();

        // Create the InventoryLocation
        InventoryLocationDTO inventoryLocationDTO = inventoryLocationMapper.toDto(inventoryLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryLocationMockMvc.perform(put("/api/inventory-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryLocation in the database
        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InventoryLocation in Elasticsearch
        verify(mockInventoryLocationSearchRepository, times(0)).save(inventoryLocation);
    }

    @Test
    @Transactional
    public void deleteInventoryLocation() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);

        int databaseSizeBeforeDelete = inventoryLocationRepository.findAll().size();

        // Delete the inventoryLocation
        restInventoryLocationMockMvc.perform(delete("/api/inventory-locations/{id}", inventoryLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InventoryLocation> inventoryLocationList = inventoryLocationRepository.findAll();
        assertThat(inventoryLocationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InventoryLocation in Elasticsearch
        verify(mockInventoryLocationSearchRepository, times(1)).deleteById(inventoryLocation.getId());
    }

    @Test
    @Transactional
    public void searchInventoryLocation() throws Exception {
        // Initialize the database
        inventoryLocationRepository.saveAndFlush(inventoryLocation);
        when(mockInventoryLocationSearchRepository.search(queryStringQuery("id:" + inventoryLocation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(inventoryLocation), PageRequest.of(0, 1), 1));
        // Search the inventoryLocation
        restInventoryLocationMockMvc.perform(get("/api/_search/inventory-locations?query=id:" + inventoryLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryLocation.class);
        InventoryLocation inventoryLocation1 = new InventoryLocation();
        inventoryLocation1.setId(1L);
        InventoryLocation inventoryLocation2 = new InventoryLocation();
        inventoryLocation2.setId(inventoryLocation1.getId());
        assertThat(inventoryLocation1).isEqualTo(inventoryLocation2);
        inventoryLocation2.setId(2L);
        assertThat(inventoryLocation1).isNotEqualTo(inventoryLocation2);
        inventoryLocation1.setId(null);
        assertThat(inventoryLocation1).isNotEqualTo(inventoryLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryLocationDTO.class);
        InventoryLocationDTO inventoryLocationDTO1 = new InventoryLocationDTO();
        inventoryLocationDTO1.setId(1L);
        InventoryLocationDTO inventoryLocationDTO2 = new InventoryLocationDTO();
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO2.setId(inventoryLocationDTO1.getId());
        assertThat(inventoryLocationDTO1).isEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO2.setId(2L);
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
        inventoryLocationDTO1.setId(null);
        assertThat(inventoryLocationDTO1).isNotEqualTo(inventoryLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inventoryLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inventoryLocationMapper.fromId(null)).isNull();
    }
}
