package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Office;
import org.soptorshi.repository.OfficeRepository;
import org.soptorshi.repository.search.OfficeSearchRepository;
import org.soptorshi.service.OfficeService;
import org.soptorshi.service.dto.OfficeDTO;
import org.soptorshi.service.mapper.OfficeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.OfficeCriteria;
import org.soptorshi.service.OfficeQueryService;

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
 * Test class for the OfficeResource REST controller.
 *
 * @see OfficeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class OfficeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private OfficeService officeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.OfficeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OfficeSearchRepository mockOfficeSearchRepository;

    @Autowired
    private OfficeQueryService officeQueryService;

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

    private MockMvc restOfficeMockMvc;

    private Office office;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeResource officeResource = new OfficeResource(officeService, officeQueryService);
        this.restOfficeMockMvc = MockMvcBuilders.standaloneSetup(officeResource)
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
    public static Office createEntity(EntityManager em) {
        Office office = new Office()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .location(DEFAULT_LOCATION);
        return office;
    }

    @Before
    public void initTest() {
        office = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffice() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeDTO)))
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate + 1);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOffice.getLocation()).isEqualTo(DEFAULT_LOCATION);

        // Validate the Office in Elasticsearch
        verify(mockOfficeSearchRepository, times(1)).save(testOffice);
    }

    @Test
    @Transactional
    public void createOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office with an existing ID
        office.setId(1L);
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Office in Elasticsearch
        verify(mockOfficeSearchRepository, times(0)).save(office);
    }

    @Test
    @Transactional
    public void getAllOffices() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }
    
    @Test
    @Transactional
    public void getOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(office.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name equals to DEFAULT_NAME
        defaultOfficeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the officeList where name equals to UPDATED_NAME
        defaultOfficeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOfficeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the officeList where name equals to UPDATED_NAME
        defaultOfficeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name is not null
        defaultOfficeShouldBeFound("name.specified=true");

        // Get all the officeList where name is null
        defaultOfficeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where description equals to DEFAULT_DESCRIPTION
        defaultOfficeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the officeList where description equals to UPDATED_DESCRIPTION
        defaultOfficeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOfficesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOfficeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the officeList where description equals to UPDATED_DESCRIPTION
        defaultOfficeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOfficesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where description is not null
        defaultOfficeShouldBeFound("description.specified=true");

        // Get all the officeList where description is null
        defaultOfficeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where location equals to DEFAULT_LOCATION
        defaultOfficeShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the officeList where location equals to UPDATED_LOCATION
        defaultOfficeShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOfficesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultOfficeShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the officeList where location equals to UPDATED_LOCATION
        defaultOfficeShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOfficesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where location is not null
        defaultOfficeShouldBeFound("location.specified=true");

        // Get all the officeList where location is null
        defaultOfficeShouldNotBeFound("location.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOfficeShouldBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));

        // Check, that the count call also returns 1
        restOfficeMockMvc.perform(get("/api/offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOfficeShouldNotBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfficeMockMvc.perform(get("/api/offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office
        Office updatedOffice = officeRepository.findById(office.getId()).get();
        // Disconnect from session so that the updates on updatedOffice are not directly saved in db
        em.detach(updatedOffice);
        updatedOffice
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION);
        OfficeDTO officeDTO = officeMapper.toDto(updatedOffice);

        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeDTO)))
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOffice.getLocation()).isEqualTo(UPDATED_LOCATION);

        // Validate the Office in Elasticsearch
        verify(mockOfficeSearchRepository, times(1)).save(testOffice);
    }

    @Test
    @Transactional
    public void updateNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Create the Office
        OfficeDTO officeDTO = officeMapper.toDto(office);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Office in Elasticsearch
        verify(mockOfficeSearchRepository, times(0)).save(office);
    }

    @Test
    @Transactional
    public void deleteOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        int databaseSizeBeforeDelete = officeRepository.findAll().size();

        // Delete the office
        restOfficeMockMvc.perform(delete("/api/offices/{id}", office.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Office in Elasticsearch
        verify(mockOfficeSearchRepository, times(1)).deleteById(office.getId());
    }

    @Test
    @Transactional
    public void searchOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);
        when(mockOfficeSearchRepository.search(queryStringQuery("id:" + office.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(office), PageRequest.of(0, 1), 1));
        // Search the office
        restOfficeMockMvc.perform(get("/api/_search/offices?query=id:" + office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Office.class);
        Office office1 = new Office();
        office1.setId(1L);
        Office office2 = new Office();
        office2.setId(office1.getId());
        assertThat(office1).isEqualTo(office2);
        office2.setId(2L);
        assertThat(office1).isNotEqualTo(office2);
        office1.setId(null);
        assertThat(office1).isNotEqualTo(office2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeDTO.class);
        OfficeDTO officeDTO1 = new OfficeDTO();
        officeDTO1.setId(1L);
        OfficeDTO officeDTO2 = new OfficeDTO();
        assertThat(officeDTO1).isNotEqualTo(officeDTO2);
        officeDTO2.setId(officeDTO1.getId());
        assertThat(officeDTO1).isEqualTo(officeDTO2);
        officeDTO2.setId(2L);
        assertThat(officeDTO1).isNotEqualTo(officeDTO2);
        officeDTO1.setId(null);
        assertThat(officeDTO1).isNotEqualTo(officeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(officeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(officeMapper.fromId(null)).isNull();
    }
}
