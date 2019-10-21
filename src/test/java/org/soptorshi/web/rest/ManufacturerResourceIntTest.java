package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Manufacturer;
import org.soptorshi.repository.ManufacturerRepository;
import org.soptorshi.repository.search.ManufacturerSearchRepository;
import org.soptorshi.service.dto.ManufacturerDTO;
import org.soptorshi.service.impl.ManufacturerServiceImpl;
import org.soptorshi.service.mapper.ManufacturerMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.ManufacturerQueryService;

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
 * Test class for the ManufacturerResource REST controller.
 *
 * @see ManufacturerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ManufacturerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ManufacturerMapper manufacturerMapper;

    @Autowired
    private ManufacturerServiceImpl manufacturerServiceImpl;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ManufacturerSearchRepositoryMockConfiguration
     */
    @Autowired
    private ManufacturerSearchRepository mockManufacturerSearchRepository;

    @Autowired
    private ManufacturerQueryService manufacturerQueryService;

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

    private MockMvc restManufacturerMockMvc;

    private Manufacturer manufacturer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ManufacturerResource manufacturerResource = new ManufacturerResource(manufacturerServiceImpl, manufacturerQueryService);
        this.restManufacturerMockMvc = MockMvcBuilders.standaloneSetup(manufacturerResource)
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
    public static Manufacturer createEntity(EntityManager em) {
        Manufacturer manufacturer = new Manufacturer()
            .name(DEFAULT_NAME)
            .contact(DEFAULT_CONTACT)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .remarks(DEFAULT_REMARKS);
        return manufacturer;
    }

    @Before
    public void initTest() {
        manufacturer = createEntity(em);
    }

    @Test
    @Transactional
    public void createManufacturer() throws Exception {
        int databaseSizeBeforeCreate = manufacturerRepository.findAll().size();

        // Create the Manufacturer
        ManufacturerDTO manufacturerDTO = manufacturerMapper.toDto(manufacturer);
        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDTO)))
            .andExpect(status().isCreated());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeCreate + 1);
        Manufacturer testManufacturer = manufacturerList.get(manufacturerList.size() - 1);
        assertThat(testManufacturer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufacturer.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testManufacturer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testManufacturer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testManufacturer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testManufacturer.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the Manufacturer in Elasticsearch
        verify(mockManufacturerSearchRepository, times(1)).save(testManufacturer);
    }

    @Test
    @Transactional
    public void createManufacturerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manufacturerRepository.findAll().size();

        // Create the Manufacturer with an existing ID
        manufacturer.setId(1L);
        ManufacturerDTO manufacturerDTO = manufacturerMapper.toDto(manufacturer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Manufacturer in Elasticsearch
        verify(mockManufacturerSearchRepository, times(0)).save(manufacturer);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = manufacturerRepository.findAll().size();
        // set the field null
        manufacturer.setName(null);

        // Create the Manufacturer, which fails.
        ManufacturerDTO manufacturerDTO = manufacturerMapper.toDto(manufacturer);

        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDTO)))
            .andExpect(status().isBadRequest());

        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManufacturers() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList
        restManufacturerMockMvc.perform(get("/api/manufacturers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manufacturer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllManufacturersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where name equals to DEFAULT_NAME
        defaultManufacturerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the manufacturerList where name equals to UPDATED_NAME
        defaultManufacturerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManufacturersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultManufacturerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the manufacturerList where name equals to UPDATED_NAME
        defaultManufacturerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllManufacturersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where name is not null
        defaultManufacturerShouldBeFound("name.specified=true");

        // Get all the manufacturerList where name is null
        defaultManufacturerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllManufacturersByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where contact equals to DEFAULT_CONTACT
        defaultManufacturerShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the manufacturerList where contact equals to UPDATED_CONTACT
        defaultManufacturerShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllManufacturersByContactIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultManufacturerShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the manufacturerList where contact equals to UPDATED_CONTACT
        defaultManufacturerShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllManufacturersByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where contact is not null
        defaultManufacturerShouldBeFound("contact.specified=true");

        // Get all the manufacturerList where contact is null
        defaultManufacturerShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    public void getAllManufacturersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where email equals to DEFAULT_EMAIL
        defaultManufacturerShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the manufacturerList where email equals to UPDATED_EMAIL
        defaultManufacturerShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllManufacturersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultManufacturerShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the manufacturerList where email equals to UPDATED_EMAIL
        defaultManufacturerShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllManufacturersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where email is not null
        defaultManufacturerShouldBeFound("email.specified=true");

        // Get all the manufacturerList where email is null
        defaultManufacturerShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllManufacturersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where address equals to DEFAULT_ADDRESS
        defaultManufacturerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the manufacturerList where address equals to UPDATED_ADDRESS
        defaultManufacturerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllManufacturersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultManufacturerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the manufacturerList where address equals to UPDATED_ADDRESS
        defaultManufacturerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllManufacturersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where address is not null
        defaultManufacturerShouldBeFound("address.specified=true");

        // Get all the manufacturerList where address is null
        defaultManufacturerShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllManufacturersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where description equals to DEFAULT_DESCRIPTION
        defaultManufacturerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the manufacturerList where description equals to UPDATED_DESCRIPTION
        defaultManufacturerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllManufacturersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultManufacturerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the manufacturerList where description equals to UPDATED_DESCRIPTION
        defaultManufacturerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllManufacturersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where description is not null
        defaultManufacturerShouldBeFound("description.specified=true");

        // Get all the manufacturerList where description is null
        defaultManufacturerShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllManufacturersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where remarks equals to DEFAULT_REMARKS
        defaultManufacturerShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the manufacturerList where remarks equals to UPDATED_REMARKS
        defaultManufacturerShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllManufacturersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultManufacturerShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the manufacturerList where remarks equals to UPDATED_REMARKS
        defaultManufacturerShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllManufacturersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList where remarks is not null
        defaultManufacturerShouldBeFound("remarks.specified=true");

        // Get all the manufacturerList where remarks is null
        defaultManufacturerShouldNotBeFound("remarks.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultManufacturerShouldBeFound(String filter) throws Exception {
        restManufacturerMockMvc.perform(get("/api/manufacturers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restManufacturerMockMvc.perform(get("/api/manufacturers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultManufacturerShouldNotBeFound(String filter) throws Exception {
        restManufacturerMockMvc.perform(get("/api/manufacturers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restManufacturerMockMvc.perform(get("/api/manufacturers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        int databaseSizeBeforeUpdate = manufacturerRepository.findAll().size();

        // Update the manufacturer
        Manufacturer updatedManufacturer = manufacturerRepository.findById(manufacturer.getId()).get();
        // Disconnect from session so that the updates on updatedManufacturer are not directly saved in db
        em.detach(updatedManufacturer);
        updatedManufacturer
            .name(UPDATED_NAME)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .remarks(UPDATED_REMARKS);
        ManufacturerDTO manufacturerDTO = manufacturerMapper.toDto(updatedManufacturer);

        restManufacturerMockMvc.perform(put("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDTO)))
            .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeUpdate);
        Manufacturer testManufacturer = manufacturerList.get(manufacturerList.size() - 1);
        assertThat(testManufacturer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufacturer.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testManufacturer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testManufacturer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testManufacturer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testManufacturer.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the Manufacturer in Elasticsearch
        verify(mockManufacturerSearchRepository, times(1)).save(testManufacturer);
    }

    @Test
    @Transactional
    public void updateNonExistingManufacturer() throws Exception {
        int databaseSizeBeforeUpdate = manufacturerRepository.findAll().size();

        // Create the Manufacturer
        ManufacturerDTO manufacturerDTO = manufacturerMapper.toDto(manufacturer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufacturerMockMvc.perform(put("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Manufacturer in Elasticsearch
        verify(mockManufacturerSearchRepository, times(0)).save(manufacturer);
    }

    @Test
    @Transactional
    public void deleteManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        int databaseSizeBeforeDelete = manufacturerRepository.findAll().size();

        // Delete the manufacturer
        restManufacturerMockMvc.perform(delete("/api/manufacturers/{id}", manufacturer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Manufacturer in Elasticsearch
        verify(mockManufacturerSearchRepository, times(1)).deleteById(manufacturer.getId());
    }

    @Test
    @Transactional
    public void searchManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);
        when(mockManufacturerSearchRepository.search(queryStringQuery("id:" + manufacturer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(manufacturer), PageRequest.of(0, 1), 1));
        // Search the manufacturer
        restManufacturerMockMvc.perform(get("/api/_search/manufacturers?query=id:" + manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manufacturer.class);
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setId(1L);
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setId(manufacturer1.getId());
        assertThat(manufacturer1).isEqualTo(manufacturer2);
        manufacturer2.setId(2L);
        assertThat(manufacturer1).isNotEqualTo(manufacturer2);
        manufacturer1.setId(null);
        assertThat(manufacturer1).isNotEqualTo(manufacturer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManufacturerDTO.class);
        ManufacturerDTO manufacturerDTO1 = new ManufacturerDTO();
        manufacturerDTO1.setId(1L);
        ManufacturerDTO manufacturerDTO2 = new ManufacturerDTO();
        assertThat(manufacturerDTO1).isNotEqualTo(manufacturerDTO2);
        manufacturerDTO2.setId(manufacturerDTO1.getId());
        assertThat(manufacturerDTO1).isEqualTo(manufacturerDTO2);
        manufacturerDTO2.setId(2L);
        assertThat(manufacturerDTO1).isNotEqualTo(manufacturerDTO2);
        manufacturerDTO1.setId(null);
        assertThat(manufacturerDTO1).isNotEqualTo(manufacturerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(manufacturerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(manufacturerMapper.fromId(null)).isNull();
    }
}
