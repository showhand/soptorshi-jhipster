package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Voucher;
import org.soptorshi.repository.VoucherRepository;
import org.soptorshi.repository.search.VoucherSearchRepository;
import org.soptorshi.service.VoucherService;
import org.soptorshi.service.dto.VoucherDTO;
import org.soptorshi.service.mapper.VoucherMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.VoucherCriteria;
import org.soptorshi.service.VoucherQueryService;

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
 * Test class for the VoucherResource REST controller.
 *
 * @see VoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class VoucherResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_ON = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_ON = "BBBBBBBBBB";

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private VoucherService voucherService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.VoucherSearchRepositoryMockConfiguration
     */
    @Autowired
    private VoucherSearchRepository mockVoucherSearchRepository;

    @Autowired
    private VoucherQueryService voucherQueryService;

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

    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherResource voucherResource = new VoucherResource(voucherService, voucherQueryService);
        this.restVoucherMockMvc = MockMvcBuilders.standaloneSetup(voucherResource)
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
    public static Voucher createEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return voucher;
    }

    @Before
    public void initTest() {
        voucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucher() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isCreated());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate + 1);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoucher.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testVoucher.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVoucher.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);

        // Validate the Voucher in Elasticsearch
        verify(mockVoucherSearchRepository, times(1)).save(testVoucher);
    }

    @Test
    @Transactional
    public void createVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher with an existing ID
        voucher.setId(1L);
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc.perform(post("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate);

        // Validate the Voucher in Elasticsearch
        verify(mockVoucherSearchRepository, times(0)).save(voucher);
    }

    @Test
    @Transactional
    public void getAllVouchers() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()));
    }

    @Test
    @Transactional
    public void getAllVouchersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where name equals to DEFAULT_NAME
        defaultVoucherShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the voucherList where name equals to UPDATED_NAME
        defaultVoucherShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVouchersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVoucherShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the voucherList where name equals to UPDATED_NAME
        defaultVoucherShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVouchersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where name is not null
        defaultVoucherShouldBeFound("name.specified=true");

        // Get all the voucherList where name is null
        defaultVoucherShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where shortName equals to DEFAULT_SHORT_NAME
        defaultVoucherShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the voucherList where shortName equals to UPDATED_SHORT_NAME
        defaultVoucherShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllVouchersByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultVoucherShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the voucherList where shortName equals to UPDATED_SHORT_NAME
        defaultVoucherShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllVouchersByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where shortName is not null
        defaultVoucherShouldBeFound("shortName.specified=true");

        // Get all the voucherList where shortName is null
        defaultVoucherShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultVoucherShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the voucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultVoucherShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultVoucherShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the voucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultVoucherShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedOn is not null
        defaultVoucherShouldBeFound("modifiedOn.specified=true");

        // Get all the voucherList where modifiedOn is null
        defaultVoucherShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultVoucherShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the voucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultVoucherShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultVoucherShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the voucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultVoucherShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedBy is not null
        defaultVoucherShouldBeFound("modifiedBy.specified=true");

        // Get all the voucherList where modifiedBy is null
        defaultVoucherShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedBy greater than or equals to DEFAULT_MODIFIED_BY
        defaultVoucherShouldBeFound("modifiedBy.greaterOrEqualThan=" + DEFAULT_MODIFIED_BY);

        // Get all the voucherList where modifiedBy greater than or equals to UPDATED_MODIFIED_BY
        defaultVoucherShouldNotBeFound("modifiedBy.greaterOrEqualThan=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllVouchersByModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where modifiedBy less than or equals to DEFAULT_MODIFIED_BY
        defaultVoucherShouldNotBeFound("modifiedBy.lessThan=" + DEFAULT_MODIFIED_BY);

        // Get all the voucherList where modifiedBy less than or equals to UPDATED_MODIFIED_BY
        defaultVoucherShouldBeFound("modifiedBy.lessThan=" + UPDATED_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVoucherShouldBeFound(String filter) throws Exception {
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())));

        // Check, that the count call also returns 1
        restVoucherMockMvc.perform(get("/api/vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVoucherShouldNotBeFound(String filter) throws Exception {
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherMockMvc.perform(get("/api/vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY);
        VoucherDTO voucherDTO = voucherMapper.toDto(updatedVoucher);

        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoucher.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testVoucher.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVoucher.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);

        // Validate the Voucher in Elasticsearch
        verify(mockVoucherSearchRepository, times(1)).save(testVoucher);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Create the Voucher
        VoucherDTO voucherDTO = voucherMapper.toDto(voucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc.perform(put("/api/vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Voucher in Elasticsearch
        verify(mockVoucherSearchRepository, times(0)).save(voucher);
    }

    @Test
    @Transactional
    public void deleteVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        int databaseSizeBeforeDelete = voucherRepository.findAll().size();

        // Delete the voucher
        restVoucherMockMvc.perform(delete("/api/vouchers/{id}", voucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Voucher in Elasticsearch
        verify(mockVoucherSearchRepository, times(1)).deleteById(voucher.getId());
    }

    @Test
    @Transactional
    public void searchVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);
        when(mockVoucherSearchRepository.search(queryStringQuery("id:" + voucher.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(voucher), PageRequest.of(0, 1), 1));
        // Search the voucher
        restVoucherMockMvc.perform(get("/api/_search/vouchers?query=id:" + voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voucher.class);
        Voucher voucher1 = new Voucher();
        voucher1.setId(1L);
        Voucher voucher2 = new Voucher();
        voucher2.setId(voucher1.getId());
        assertThat(voucher1).isEqualTo(voucher2);
        voucher2.setId(2L);
        assertThat(voucher1).isNotEqualTo(voucher2);
        voucher1.setId(null);
        assertThat(voucher1).isNotEqualTo(voucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherDTO.class);
        VoucherDTO voucherDTO1 = new VoucherDTO();
        voucherDTO1.setId(1L);
        VoucherDTO voucherDTO2 = new VoucherDTO();
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
        voucherDTO2.setId(voucherDTO1.getId());
        assertThat(voucherDTO1).isEqualTo(voucherDTO2);
        voucherDTO2.setId(2L);
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
        voucherDTO1.setId(null);
        assertThat(voucherDTO1).isNotEqualTo(voucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherMapper.fromId(null)).isNull();
    }
}
