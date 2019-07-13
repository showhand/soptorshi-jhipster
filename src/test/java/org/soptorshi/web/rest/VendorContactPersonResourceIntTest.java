package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.VendorContactPerson;
import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.VendorContactPersonRepository;
import org.soptorshi.repository.search.VendorContactPersonSearchRepository;
import org.soptorshi.service.VendorContactPersonService;
import org.soptorshi.service.dto.VendorContactPersonDTO;
import org.soptorshi.service.mapper.VendorContactPersonMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.VendorContactPersonCriteria;
import org.soptorshi.service.VendorContactPersonQueryService;

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
 * Test class for the VendorContactPersonResource REST controller.
 *
 * @see VendorContactPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class VendorContactPersonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private VendorContactPersonRepository vendorContactPersonRepository;

    @Autowired
    private VendorContactPersonMapper vendorContactPersonMapper;

    @Autowired
    private VendorContactPersonService vendorContactPersonService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.VendorContactPersonSearchRepositoryMockConfiguration
     */
    @Autowired
    private VendorContactPersonSearchRepository mockVendorContactPersonSearchRepository;

    @Autowired
    private VendorContactPersonQueryService vendorContactPersonQueryService;

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

    private MockMvc restVendorContactPersonMockMvc;

    private VendorContactPerson vendorContactPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorContactPersonResource vendorContactPersonResource = new VendorContactPersonResource(vendorContactPersonService, vendorContactPersonQueryService);
        this.restVendorContactPersonMockMvc = MockMvcBuilders.standaloneSetup(vendorContactPersonResource)
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
    public static VendorContactPerson createEntity(EntityManager em) {
        VendorContactPerson vendorContactPerson = new VendorContactPerson()
            .name(DEFAULT_NAME)
            .designation(DEFAULT_DESIGNATION)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return vendorContactPerson;
    }

    @Before
    public void initTest() {
        vendorContactPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendorContactPerson() throws Exception {
        int databaseSizeBeforeCreate = vendorContactPersonRepository.findAll().size();

        // Create the VendorContactPerson
        VendorContactPersonDTO vendorContactPersonDTO = vendorContactPersonMapper.toDto(vendorContactPerson);
        restVendorContactPersonMockMvc.perform(post("/api/vendor-contact-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorContactPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the VendorContactPerson in the database
        List<VendorContactPerson> vendorContactPersonList = vendorContactPersonRepository.findAll();
        assertThat(vendorContactPersonList).hasSize(databaseSizeBeforeCreate + 1);
        VendorContactPerson testVendorContactPerson = vendorContactPersonList.get(vendorContactPersonList.size() - 1);
        assertThat(testVendorContactPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVendorContactPerson.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testVendorContactPerson.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);

        // Validate the VendorContactPerson in Elasticsearch
        verify(mockVendorContactPersonSearchRepository, times(1)).save(testVendorContactPerson);
    }

    @Test
    @Transactional
    public void createVendorContactPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorContactPersonRepository.findAll().size();

        // Create the VendorContactPerson with an existing ID
        vendorContactPerson.setId(1L);
        VendorContactPersonDTO vendorContactPersonDTO = vendorContactPersonMapper.toDto(vendorContactPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorContactPersonMockMvc.perform(post("/api/vendor-contact-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorContactPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorContactPerson in the database
        List<VendorContactPerson> vendorContactPersonList = vendorContactPersonRepository.findAll();
        assertThat(vendorContactPersonList).hasSize(databaseSizeBeforeCreate);

        // Validate the VendorContactPerson in Elasticsearch
        verify(mockVendorContactPersonSearchRepository, times(0)).save(vendorContactPerson);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeople() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorContactPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getVendorContactPerson() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get the vendorContactPerson
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people/{id}", vendorContactPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendorContactPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where name equals to DEFAULT_NAME
        defaultVendorContactPersonShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vendorContactPersonList where name equals to UPDATED_NAME
        defaultVendorContactPersonShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVendorContactPersonShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vendorContactPersonList where name equals to UPDATED_NAME
        defaultVendorContactPersonShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where name is not null
        defaultVendorContactPersonShouldBeFound("name.specified=true");

        // Get all the vendorContactPersonList where name is null
        defaultVendorContactPersonShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where designation equals to DEFAULT_DESIGNATION
        defaultVendorContactPersonShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the vendorContactPersonList where designation equals to UPDATED_DESIGNATION
        defaultVendorContactPersonShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultVendorContactPersonShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the vendorContactPersonList where designation equals to UPDATED_DESIGNATION
        defaultVendorContactPersonShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where designation is not null
        defaultVendorContactPersonShouldBeFound("designation.specified=true");

        // Get all the vendorContactPersonList where designation is null
        defaultVendorContactPersonShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultVendorContactPersonShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the vendorContactPersonList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultVendorContactPersonShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultVendorContactPersonShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the vendorContactPersonList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultVendorContactPersonShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        // Get all the vendorContactPersonList where contactNumber is not null
        defaultVendorContactPersonShouldBeFound("contactNumber.specified=true");

        // Get all the vendorContactPersonList where contactNumber is null
        defaultVendorContactPersonShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorContactPeopleByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        Vendor vendor = VendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        vendorContactPerson.setVendor(vendor);
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);
        Long vendorId = vendor.getId();

        // Get all the vendorContactPersonList where vendor equals to vendorId
        defaultVendorContactPersonShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the vendorContactPersonList where vendor equals to vendorId + 1
        defaultVendorContactPersonShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVendorContactPersonShouldBeFound(String filter) throws Exception {
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorContactPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));

        // Check, that the count call also returns 1
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVendorContactPersonShouldNotBeFound(String filter) throws Exception {
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVendorContactPerson() throws Exception {
        // Get the vendorContactPerson
        restVendorContactPersonMockMvc.perform(get("/api/vendor-contact-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendorContactPerson() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        int databaseSizeBeforeUpdate = vendorContactPersonRepository.findAll().size();

        // Update the vendorContactPerson
        VendorContactPerson updatedVendorContactPerson = vendorContactPersonRepository.findById(vendorContactPerson.getId()).get();
        // Disconnect from session so that the updates on updatedVendorContactPerson are not directly saved in db
        em.detach(updatedVendorContactPerson);
        updatedVendorContactPerson
            .name(UPDATED_NAME)
            .designation(UPDATED_DESIGNATION)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        VendorContactPersonDTO vendorContactPersonDTO = vendorContactPersonMapper.toDto(updatedVendorContactPerson);

        restVendorContactPersonMockMvc.perform(put("/api/vendor-contact-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorContactPersonDTO)))
            .andExpect(status().isOk());

        // Validate the VendorContactPerson in the database
        List<VendorContactPerson> vendorContactPersonList = vendorContactPersonRepository.findAll();
        assertThat(vendorContactPersonList).hasSize(databaseSizeBeforeUpdate);
        VendorContactPerson testVendorContactPerson = vendorContactPersonList.get(vendorContactPersonList.size() - 1);
        assertThat(testVendorContactPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVendorContactPerson.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testVendorContactPerson.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);

        // Validate the VendorContactPerson in Elasticsearch
        verify(mockVendorContactPersonSearchRepository, times(1)).save(testVendorContactPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingVendorContactPerson() throws Exception {
        int databaseSizeBeforeUpdate = vendorContactPersonRepository.findAll().size();

        // Create the VendorContactPerson
        VendorContactPersonDTO vendorContactPersonDTO = vendorContactPersonMapper.toDto(vendorContactPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorContactPersonMockMvc.perform(put("/api/vendor-contact-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorContactPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VendorContactPerson in the database
        List<VendorContactPerson> vendorContactPersonList = vendorContactPersonRepository.findAll();
        assertThat(vendorContactPersonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VendorContactPerson in Elasticsearch
        verify(mockVendorContactPersonSearchRepository, times(0)).save(vendorContactPerson);
    }

    @Test
    @Transactional
    public void deleteVendorContactPerson() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);

        int databaseSizeBeforeDelete = vendorContactPersonRepository.findAll().size();

        // Delete the vendorContactPerson
        restVendorContactPersonMockMvc.perform(delete("/api/vendor-contact-people/{id}", vendorContactPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VendorContactPerson> vendorContactPersonList = vendorContactPersonRepository.findAll();
        assertThat(vendorContactPersonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VendorContactPerson in Elasticsearch
        verify(mockVendorContactPersonSearchRepository, times(1)).deleteById(vendorContactPerson.getId());
    }

    @Test
    @Transactional
    public void searchVendorContactPerson() throws Exception {
        // Initialize the database
        vendorContactPersonRepository.saveAndFlush(vendorContactPerson);
        when(mockVendorContactPersonSearchRepository.search(queryStringQuery("id:" + vendorContactPerson.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vendorContactPerson), PageRequest.of(0, 1), 1));
        // Search the vendorContactPerson
        restVendorContactPersonMockMvc.perform(get("/api/_search/vendor-contact-people?query=id:" + vendorContactPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendorContactPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorContactPerson.class);
        VendorContactPerson vendorContactPerson1 = new VendorContactPerson();
        vendorContactPerson1.setId(1L);
        VendorContactPerson vendorContactPerson2 = new VendorContactPerson();
        vendorContactPerson2.setId(vendorContactPerson1.getId());
        assertThat(vendorContactPerson1).isEqualTo(vendorContactPerson2);
        vendorContactPerson2.setId(2L);
        assertThat(vendorContactPerson1).isNotEqualTo(vendorContactPerson2);
        vendorContactPerson1.setId(null);
        assertThat(vendorContactPerson1).isNotEqualTo(vendorContactPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorContactPersonDTO.class);
        VendorContactPersonDTO vendorContactPersonDTO1 = new VendorContactPersonDTO();
        vendorContactPersonDTO1.setId(1L);
        VendorContactPersonDTO vendorContactPersonDTO2 = new VendorContactPersonDTO();
        assertThat(vendorContactPersonDTO1).isNotEqualTo(vendorContactPersonDTO2);
        vendorContactPersonDTO2.setId(vendorContactPersonDTO1.getId());
        assertThat(vendorContactPersonDTO1).isEqualTo(vendorContactPersonDTO2);
        vendorContactPersonDTO2.setId(2L);
        assertThat(vendorContactPersonDTO1).isNotEqualTo(vendorContactPersonDTO2);
        vendorContactPersonDTO1.setId(null);
        assertThat(vendorContactPersonDTO1).isNotEqualTo(vendorContactPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vendorContactPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vendorContactPersonMapper.fromId(null)).isNull();
    }
}
