package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.domain.Voucher;
import org.soptorshi.domain.Requisition;
import org.soptorshi.repository.RequisitionVoucherRelationRepository;
import org.soptorshi.repository.search.RequisitionVoucherRelationSearchRepository;
import org.soptorshi.service.RequisitionVoucherRelationService;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
import org.soptorshi.service.mapper.RequisitionVoucherRelationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.RequisitionVoucherRelationCriteria;
import org.soptorshi.service.RequisitionVoucherRelationQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the RequisitionVoucherRelationResource REST controller.
 *
 * @see RequisitionVoucherRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class RequisitionVoucherRelationResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequisitionVoucherRelationRepository requisitionVoucherRelationRepository;

    @Autowired
    private RequisitionVoucherRelationMapper requisitionVoucherRelationMapper;

    @Autowired
    private RequisitionVoucherRelationService requisitionVoucherRelationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.RequisitionVoucherRelationSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisitionVoucherRelationSearchRepository mockRequisitionVoucherRelationSearchRepository;

    @Autowired
    private RequisitionVoucherRelationQueryService requisitionVoucherRelationQueryService;

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

    private MockMvc restRequisitionVoucherRelationMockMvc;

    private RequisitionVoucherRelation requisitionVoucherRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitionVoucherRelationResource requisitionVoucherRelationResource = new RequisitionVoucherRelationResource(requisitionVoucherRelationService, requisitionVoucherRelationQueryService);
        this.restRequisitionVoucherRelationMockMvc = MockMvcBuilders.standaloneSetup(requisitionVoucherRelationResource)
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
    public static RequisitionVoucherRelation createEntity(EntityManager em) {
        RequisitionVoucherRelation requisitionVoucherRelation = new RequisitionVoucherRelation()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .amount(DEFAULT_AMOUNT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return requisitionVoucherRelation;
    }

    @Before
    public void initTest() {
        requisitionVoucherRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisitionVoucherRelation() throws Exception {
        int databaseSizeBeforeCreate = requisitionVoucherRelationRepository.findAll().size();

        // Create the RequisitionVoucherRelation
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = requisitionVoucherRelationMapper.toDto(requisitionVoucherRelation);
        restRequisitionVoucherRelationMockMvc.perform(post("/api/requisition-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionVoucherRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the RequisitionVoucherRelation in the database
        List<RequisitionVoucherRelation> requisitionVoucherRelationList = requisitionVoucherRelationRepository.findAll();
        assertThat(requisitionVoucherRelationList).hasSize(databaseSizeBeforeCreate + 1);
        RequisitionVoucherRelation testRequisitionVoucherRelation = requisitionVoucherRelationList.get(requisitionVoucherRelationList.size() - 1);
        assertThat(testRequisitionVoucherRelation.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testRequisitionVoucherRelation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRequisitionVoucherRelation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRequisitionVoucherRelation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the RequisitionVoucherRelation in Elasticsearch
        verify(mockRequisitionVoucherRelationSearchRepository, times(1)).save(testRequisitionVoucherRelation);
    }

    @Test
    @Transactional
    public void createRequisitionVoucherRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionVoucherRelationRepository.findAll().size();

        // Create the RequisitionVoucherRelation with an existing ID
        requisitionVoucherRelation.setId(1L);
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = requisitionVoucherRelationMapper.toDto(requisitionVoucherRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionVoucherRelationMockMvc.perform(post("/api/requisition-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionVoucherRelation in the database
        List<RequisitionVoucherRelation> requisitionVoucherRelationList = requisitionVoucherRelationRepository.findAll();
        assertThat(requisitionVoucherRelationList).hasSize(databaseSizeBeforeCreate);

        // Validate the RequisitionVoucherRelation in Elasticsearch
        verify(mockRequisitionVoucherRelationSearchRepository, times(0)).save(requisitionVoucherRelation);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelations() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getRequisitionVoucherRelation() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get the requisitionVoucherRelation
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations/{id}", requisitionVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisitionVoucherRelation.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultRequisitionVoucherRelationShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the requisitionVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultRequisitionVoucherRelationShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultRequisitionVoucherRelationShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the requisitionVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultRequisitionVoucherRelationShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where voucherNo is not null
        defaultRequisitionVoucherRelationShouldBeFound("voucherNo.specified=true");

        // Get all the requisitionVoucherRelationList where voucherNo is null
        defaultRequisitionVoucherRelationShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where amount equals to DEFAULT_AMOUNT
        defaultRequisitionVoucherRelationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the requisitionVoucherRelationList where amount equals to UPDATED_AMOUNT
        defaultRequisitionVoucherRelationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultRequisitionVoucherRelationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the requisitionVoucherRelationList where amount equals to UPDATED_AMOUNT
        defaultRequisitionVoucherRelationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where amount is not null
        defaultRequisitionVoucherRelationShouldBeFound("amount.specified=true");

        // Get all the requisitionVoucherRelationList where amount is null
        defaultRequisitionVoucherRelationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultRequisitionVoucherRelationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the requisitionVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultRequisitionVoucherRelationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the requisitionVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedBy is not null
        defaultRequisitionVoucherRelationShouldBeFound("modifiedBy.specified=true");

        // Get all the requisitionVoucherRelationList where modifiedBy is null
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the requisitionVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedOn is not null
        defaultRequisitionVoucherRelationShouldBeFound("modifiedOn.specified=true");

        // Get all the requisitionVoucherRelationList where modifiedOn is null
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionVoucherRelationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        // Get all the requisitionVoucherRelationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the requisitionVoucherRelationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultRequisitionVoucherRelationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        Voucher voucher = VoucherResourceIntTest.createEntity(em);
        em.persist(voucher);
        em.flush();
        requisitionVoucherRelation.setVoucher(voucher);
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);
        Long voucherId = voucher.getId();

        // Get all the requisitionVoucherRelationList where voucher equals to voucherId
        defaultRequisitionVoucherRelationShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the requisitionVoucherRelationList where voucher equals to voucherId + 1
        defaultRequisitionVoucherRelationShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionVoucherRelationsByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        requisitionVoucherRelation.setRequisition(requisition);
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);
        Long requisitionId = requisition.getId();

        // Get all the requisitionVoucherRelationList where requisition equals to requisitionId
        defaultRequisitionVoucherRelationShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the requisitionVoucherRelationList where requisition equals to requisitionId + 1
        defaultRequisitionVoucherRelationShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRequisitionVoucherRelationShouldBeFound(String filter) throws Exception {
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRequisitionVoucherRelationShouldNotBeFound(String filter) throws Exception {
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequisitionVoucherRelation() throws Exception {
        // Get the requisitionVoucherRelation
        restRequisitionVoucherRelationMockMvc.perform(get("/api/requisition-voucher-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisitionVoucherRelation() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        int databaseSizeBeforeUpdate = requisitionVoucherRelationRepository.findAll().size();

        // Update the requisitionVoucherRelation
        RequisitionVoucherRelation updatedRequisitionVoucherRelation = requisitionVoucherRelationRepository.findById(requisitionVoucherRelation.getId()).get();
        // Disconnect from session so that the updates on updatedRequisitionVoucherRelation are not directly saved in db
        em.detach(updatedRequisitionVoucherRelation);
        updatedRequisitionVoucherRelation
            .voucherNo(UPDATED_VOUCHER_NO)
            .amount(UPDATED_AMOUNT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = requisitionVoucherRelationMapper.toDto(updatedRequisitionVoucherRelation);

        restRequisitionVoucherRelationMockMvc.perform(put("/api/requisition-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionVoucherRelationDTO)))
            .andExpect(status().isOk());

        // Validate the RequisitionVoucherRelation in the database
        List<RequisitionVoucherRelation> requisitionVoucherRelationList = requisitionVoucherRelationRepository.findAll();
        assertThat(requisitionVoucherRelationList).hasSize(databaseSizeBeforeUpdate);
        RequisitionVoucherRelation testRequisitionVoucherRelation = requisitionVoucherRelationList.get(requisitionVoucherRelationList.size() - 1);
        assertThat(testRequisitionVoucherRelation.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testRequisitionVoucherRelation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRequisitionVoucherRelation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRequisitionVoucherRelation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the RequisitionVoucherRelation in Elasticsearch
        verify(mockRequisitionVoucherRelationSearchRepository, times(1)).save(testRequisitionVoucherRelation);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisitionVoucherRelation() throws Exception {
        int databaseSizeBeforeUpdate = requisitionVoucherRelationRepository.findAll().size();

        // Create the RequisitionVoucherRelation
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = requisitionVoucherRelationMapper.toDto(requisitionVoucherRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionVoucherRelationMockMvc.perform(put("/api/requisition-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionVoucherRelation in the database
        List<RequisitionVoucherRelation> requisitionVoucherRelationList = requisitionVoucherRelationRepository.findAll();
        assertThat(requisitionVoucherRelationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RequisitionVoucherRelation in Elasticsearch
        verify(mockRequisitionVoucherRelationSearchRepository, times(0)).save(requisitionVoucherRelation);
    }

    @Test
    @Transactional
    public void deleteRequisitionVoucherRelation() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);

        int databaseSizeBeforeDelete = requisitionVoucherRelationRepository.findAll().size();

        // Delete the requisitionVoucherRelation
        restRequisitionVoucherRelationMockMvc.perform(delete("/api/requisition-voucher-relations/{id}", requisitionVoucherRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequisitionVoucherRelation> requisitionVoucherRelationList = requisitionVoucherRelationRepository.findAll();
        assertThat(requisitionVoucherRelationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RequisitionVoucherRelation in Elasticsearch
        verify(mockRequisitionVoucherRelationSearchRepository, times(1)).deleteById(requisitionVoucherRelation.getId());
    }

    @Test
    @Transactional
    public void searchRequisitionVoucherRelation() throws Exception {
        // Initialize the database
        requisitionVoucherRelationRepository.saveAndFlush(requisitionVoucherRelation);
        when(mockRequisitionVoucherRelationSearchRepository.search(queryStringQuery("id:" + requisitionVoucherRelation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisitionVoucherRelation), PageRequest.of(0, 1), 1));
        // Search the requisitionVoucherRelation
        restRequisitionVoucherRelationMockMvc.perform(get("/api/_search/requisition-voucher-relations?query=id:" + requisitionVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionVoucherRelation.class);
        RequisitionVoucherRelation requisitionVoucherRelation1 = new RequisitionVoucherRelation();
        requisitionVoucherRelation1.setId(1L);
        RequisitionVoucherRelation requisitionVoucherRelation2 = new RequisitionVoucherRelation();
        requisitionVoucherRelation2.setId(requisitionVoucherRelation1.getId());
        assertThat(requisitionVoucherRelation1).isEqualTo(requisitionVoucherRelation2);
        requisitionVoucherRelation2.setId(2L);
        assertThat(requisitionVoucherRelation1).isNotEqualTo(requisitionVoucherRelation2);
        requisitionVoucherRelation1.setId(null);
        assertThat(requisitionVoucherRelation1).isNotEqualTo(requisitionVoucherRelation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionVoucherRelationDTO.class);
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO1 = new RequisitionVoucherRelationDTO();
        requisitionVoucherRelationDTO1.setId(1L);
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO2 = new RequisitionVoucherRelationDTO();
        assertThat(requisitionVoucherRelationDTO1).isNotEqualTo(requisitionVoucherRelationDTO2);
        requisitionVoucherRelationDTO2.setId(requisitionVoucherRelationDTO1.getId());
        assertThat(requisitionVoucherRelationDTO1).isEqualTo(requisitionVoucherRelationDTO2);
        requisitionVoucherRelationDTO2.setId(2L);
        assertThat(requisitionVoucherRelationDTO1).isNotEqualTo(requisitionVoucherRelationDTO2);
        requisitionVoucherRelationDTO1.setId(null);
        assertThat(requisitionVoucherRelationDTO1).isNotEqualTo(requisitionVoucherRelationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requisitionVoucherRelationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requisitionVoucherRelationMapper.fromId(null)).isNull();
    }
}
