package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.VendorRepository;
import org.soptorshi.repository.search.VendorSearchRepository;
import org.soptorshi.service.VendorService;
import org.soptorshi.service.dto.VendorDTO;
import org.soptorshi.service.mapper.VendorMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.VendorCriteria;
import org.soptorshi.service.VendorQueryService;

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
import org.springframework.util.Base64Utils;
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

import org.soptorshi.domain.enumeration.VendorRemarks;
/**
 * Test class for the VendorResource REST controller.
 *
 * @see VendorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class VendorResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final VendorRemarks DEFAULT_REMARKS = VendorRemarks.VERY_GOOD;
    private static final VendorRemarks UPDATED_REMARKS = VendorRemarks.GOOD;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorMapper vendorMapper;

    @Autowired
    private VendorService vendorService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.VendorSearchRepositoryMockConfiguration
     */
    @Autowired
    private VendorSearchRepository mockVendorSearchRepository;

    @Autowired
    private VendorQueryService vendorQueryService;

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

    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendorResource vendorResource = new VendorResource(vendorService, vendorQueryService);
        this.restVendorMockMvc = MockMvcBuilders.standaloneSetup(vendorResource)
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
    public static Vendor createEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .companyName(DEFAULT_COMPANY_NAME)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .remarks(DEFAULT_REMARKS);
        return vendor;
    }

    @Before
    public void initTest() {
        vendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testVendor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVendor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVendor.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testVendor.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).save(testVendor);
    }

    @Test
    @Transactional
    public void createVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor with an existing ID
        vendor.setId(1L);
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(0)).save(vendor);
    }

    @Test
    @Transactional
    public void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllVendorsByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where companyName equals to DEFAULT_COMPANY_NAME
        defaultVendorShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the vendorList where companyName equals to UPDATED_COMPANY_NAME
        defaultVendorShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultVendorShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the vendorList where companyName equals to UPDATED_COMPANY_NAME
        defaultVendorShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where companyName is not null
        defaultVendorShouldBeFound("companyName.specified=true");

        // Get all the vendorList where companyName is null
        defaultVendorShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultVendorShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the vendorList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultVendorShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVendorsByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultVendorShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the vendorList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultVendorShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllVendorsByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where contactNumber is not null
        defaultVendorShouldBeFound("contactNumber.specified=true");

        // Get all the vendorList where contactNumber is null
        defaultVendorShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where remarks equals to DEFAULT_REMARKS
        defaultVendorShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the vendorList where remarks equals to UPDATED_REMARKS
        defaultVendorShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllVendorsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultVendorShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the vendorList where remarks equals to UPDATED_REMARKS
        defaultVendorShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllVendorsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where remarks is not null
        defaultVendorShouldBeFound("remarks.specified=true");

        // Get all the vendorList where remarks is null
        defaultVendorShouldNotBeFound("remarks.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVendorShouldBeFound(String filter) throws Exception {
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));

        // Check, that the count call also returns 1
        restVendorMockMvc.perform(get("/api/vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVendorShouldNotBeFound(String filter) throws Exception {
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorMockMvc.perform(get("/api/vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = vendorRepository.findById(vendor.getId()).get();
        // Disconnect from session so that the updates on updatedVendor are not directly saved in db
        em.detach(updatedVendor);
        updatedVendor
            .companyName(UPDATED_COMPANY_NAME)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .remarks(UPDATED_REMARKS);
        VendorDTO vendorDTO = vendorMapper.toDto(updatedVendor);

        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testVendor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVendor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVendor.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testVendor.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).save(testVendor);
    }

    @Test
    @Transactional
    public void updateNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(0)).save(vendor);
    }

    @Test
    @Transactional
    public void deleteVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Delete the vendor
        restVendorMockMvc.perform(delete("/api/vendors/{id}", vendor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Vendor in Elasticsearch
        verify(mockVendorSearchRepository, times(1)).deleteById(vendor.getId());
    }

    @Test
    @Transactional
    public void searchVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        when(mockVendorSearchRepository.search(queryStringQuery("id:" + vendor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vendor), PageRequest.of(0, 1), 1));
        // Search the vendor
        restVendorMockMvc.perform(get("/api/_search/vendors?query=id:" + vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vendor.class);
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        Vendor vendor2 = new Vendor();
        vendor2.setId(vendor1.getId());
        assertThat(vendor1).isEqualTo(vendor2);
        vendor2.setId(2L);
        assertThat(vendor1).isNotEqualTo(vendor2);
        vendor1.setId(null);
        assertThat(vendor1).isNotEqualTo(vendor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendorDTO.class);
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setId(1L);
        VendorDTO vendorDTO2 = new VendorDTO();
        assertThat(vendorDTO1).isNotEqualTo(vendorDTO2);
        vendorDTO2.setId(vendorDTO1.getId());
        assertThat(vendorDTO1).isEqualTo(vendorDTO2);
        vendorDTO2.setId(2L);
        assertThat(vendorDTO1).isNotEqualTo(vendorDTO2);
        vendorDTO1.setId(null);
        assertThat(vendorDTO1).isNotEqualTo(vendorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vendorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vendorMapper.fromId(null)).isNull();
    }
}
