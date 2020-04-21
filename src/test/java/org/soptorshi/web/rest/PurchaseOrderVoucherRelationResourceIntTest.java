package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.domain.Voucher;
import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;
import org.soptorshi.repository.search.PurchaseOrderVoucherRelationSearchRepository;
import org.soptorshi.service.PurchaseOrderVoucherRelationService;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;
import org.soptorshi.service.mapper.PurchaseOrderVoucherRelationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationCriteria;
import org.soptorshi.service.PurchaseOrderVoucherRelationQueryService;

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
 * Test class for the PurchaseOrderVoucherRelationResource REST controller.
 *
 * @see PurchaseOrderVoucherRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PurchaseOrderVoucherRelationResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository;

    @Autowired
    private PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper;

    @Autowired
    private PurchaseOrderVoucherRelationService purchaseOrderVoucherRelationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PurchaseOrderVoucherRelationSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseOrderVoucherRelationSearchRepository mockPurchaseOrderVoucherRelationSearchRepository;

    @Autowired
    private PurchaseOrderVoucherRelationQueryService purchaseOrderVoucherRelationQueryService;

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

    private MockMvc restPurchaseOrderVoucherRelationMockMvc;

    private PurchaseOrderVoucherRelation purchaseOrderVoucherRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderVoucherRelationResource purchaseOrderVoucherRelationResource = new PurchaseOrderVoucherRelationResource(purchaseOrderVoucherRelationService, purchaseOrderVoucherRelationQueryService);
        this.restPurchaseOrderVoucherRelationMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderVoucherRelationResource)
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
    public static PurchaseOrderVoucherRelation createEntity(EntityManager em) {
        PurchaseOrderVoucherRelation purchaseOrderVoucherRelation = new PurchaseOrderVoucherRelation()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .amount(DEFAULT_AMOUNT)
            .createBy(DEFAULT_CREATE_BY)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return purchaseOrderVoucherRelation;
    }

    @Before
    public void initTest() {
        purchaseOrderVoucherRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderVoucherRelation() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderVoucherRelationRepository.findAll().size();

        // Create the PurchaseOrderVoucherRelation
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = purchaseOrderVoucherRelationMapper.toDto(purchaseOrderVoucherRelation);
        restPurchaseOrderVoucherRelationMockMvc.perform(post("/api/purchase-order-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderVoucherRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderVoucherRelation in the database
        List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelationList = purchaseOrderVoucherRelationRepository.findAll();
        assertThat(purchaseOrderVoucherRelationList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderVoucherRelation testPurchaseOrderVoucherRelation = purchaseOrderVoucherRelationList.get(purchaseOrderVoucherRelationList.size() - 1);
        assertThat(testPurchaseOrderVoucherRelation.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testPurchaseOrderVoucherRelation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPurchaseOrderVoucherRelation.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPurchaseOrderVoucherRelation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPurchaseOrderVoucherRelation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the PurchaseOrderVoucherRelation in Elasticsearch
        verify(mockPurchaseOrderVoucherRelationSearchRepository, times(1)).save(testPurchaseOrderVoucherRelation);
    }

    @Test
    @Transactional
    public void createPurchaseOrderVoucherRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderVoucherRelationRepository.findAll().size();

        // Create the PurchaseOrderVoucherRelation with an existing ID
        purchaseOrderVoucherRelation.setId(1L);
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = purchaseOrderVoucherRelationMapper.toDto(purchaseOrderVoucherRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderVoucherRelationMockMvc.perform(post("/api/purchase-order-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderVoucherRelation in the database
        List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelationList = purchaseOrderVoucherRelationRepository.findAll();
        assertThat(purchaseOrderVoucherRelationList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseOrderVoucherRelation in Elasticsearch
        verify(mockPurchaseOrderVoucherRelationSearchRepository, times(0)).save(purchaseOrderVoucherRelation);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelations() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrderVoucherRelation() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get the purchaseOrderVoucherRelation
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations/{id}", purchaseOrderVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderVoucherRelation.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultPurchaseOrderVoucherRelationShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the purchaseOrderVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultPurchaseOrderVoucherRelationShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the purchaseOrderVoucherRelationList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where voucherNo is not null
        defaultPurchaseOrderVoucherRelationShouldBeFound("voucherNo.specified=true");

        // Get all the purchaseOrderVoucherRelationList where voucherNo is null
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where amount equals to DEFAULT_AMOUNT
        defaultPurchaseOrderVoucherRelationShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the purchaseOrderVoucherRelationList where amount equals to UPDATED_AMOUNT
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPurchaseOrderVoucherRelationShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the purchaseOrderVoucherRelationList where amount equals to UPDATED_AMOUNT
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where amount is not null
        defaultPurchaseOrderVoucherRelationShouldBeFound("amount.specified=true");

        // Get all the purchaseOrderVoucherRelationList where amount is null
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByCreateByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where createBy equals to DEFAULT_CREATE_BY
        defaultPurchaseOrderVoucherRelationShouldBeFound("createBy.equals=" + DEFAULT_CREATE_BY);

        // Get all the purchaseOrderVoucherRelationList where createBy equals to UPDATED_CREATE_BY
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("createBy.equals=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByCreateByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where createBy in DEFAULT_CREATE_BY or UPDATED_CREATE_BY
        defaultPurchaseOrderVoucherRelationShouldBeFound("createBy.in=" + DEFAULT_CREATE_BY + "," + UPDATED_CREATE_BY);

        // Get all the purchaseOrderVoucherRelationList where createBy equals to UPDATED_CREATE_BY
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("createBy.in=" + UPDATED_CREATE_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByCreateByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where createBy is not null
        defaultPurchaseOrderVoucherRelationShouldBeFound("createBy.specified=true");

        // Get all the purchaseOrderVoucherRelationList where createBy is null
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("createBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the purchaseOrderVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the purchaseOrderVoucherRelationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedBy is not null
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedBy.specified=true");

        // Get all the purchaseOrderVoucherRelationList where modifiedBy is null
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn is not null
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedOn.specified=true");

        // Get all the purchaseOrderVoucherRelationList where modifiedOn is null
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the purchaseOrderVoucherRelationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultPurchaseOrderVoucherRelationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        Voucher voucher = VoucherResourceIntTest.createEntity(em);
        em.persist(voucher);
        em.flush();
        purchaseOrderVoucherRelation.setVoucher(voucher);
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);
        Long voucherId = voucher.getId();

        // Get all the purchaseOrderVoucherRelationList where voucher equals to voucherId
        defaultPurchaseOrderVoucherRelationShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the purchaseOrderVoucherRelationList where voucher equals to voucherId + 1
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderVoucherRelationsByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        purchaseOrderVoucherRelation.setPurchaseOrder(purchaseOrder);
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the purchaseOrderVoucherRelationList where purchaseOrder equals to purchaseOrderId
        defaultPurchaseOrderVoucherRelationShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the purchaseOrderVoucherRelationList where purchaseOrder equals to purchaseOrderId + 1
        defaultPurchaseOrderVoucherRelationShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPurchaseOrderVoucherRelationShouldBeFound(String filter) throws Exception {
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPurchaseOrderVoucherRelationShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseOrderVoucherRelation() throws Exception {
        // Get the purchaseOrderVoucherRelation
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/purchase-order-voucher-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderVoucherRelation() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        int databaseSizeBeforeUpdate = purchaseOrderVoucherRelationRepository.findAll().size();

        // Update the purchaseOrderVoucherRelation
        PurchaseOrderVoucherRelation updatedPurchaseOrderVoucherRelation = purchaseOrderVoucherRelationRepository.findById(purchaseOrderVoucherRelation.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrderVoucherRelation are not directly saved in db
        em.detach(updatedPurchaseOrderVoucherRelation);
        updatedPurchaseOrderVoucherRelation
            .voucherNo(UPDATED_VOUCHER_NO)
            .amount(UPDATED_AMOUNT)
            .createBy(UPDATED_CREATE_BY)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = purchaseOrderVoucherRelationMapper.toDto(updatedPurchaseOrderVoucherRelation);

        restPurchaseOrderVoucherRelationMockMvc.perform(put("/api/purchase-order-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderVoucherRelationDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderVoucherRelation in the database
        List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelationList = purchaseOrderVoucherRelationRepository.findAll();
        assertThat(purchaseOrderVoucherRelationList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderVoucherRelation testPurchaseOrderVoucherRelation = purchaseOrderVoucherRelationList.get(purchaseOrderVoucherRelationList.size() - 1);
        assertThat(testPurchaseOrderVoucherRelation.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testPurchaseOrderVoucherRelation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPurchaseOrderVoucherRelation.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPurchaseOrderVoucherRelation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPurchaseOrderVoucherRelation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the PurchaseOrderVoucherRelation in Elasticsearch
        verify(mockPurchaseOrderVoucherRelationSearchRepository, times(1)).save(testPurchaseOrderVoucherRelation);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrderVoucherRelation() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderVoucherRelationRepository.findAll().size();

        // Create the PurchaseOrderVoucherRelation
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = purchaseOrderVoucherRelationMapper.toDto(purchaseOrderVoucherRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderVoucherRelationMockMvc.perform(put("/api/purchase-order-voucher-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderVoucherRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderVoucherRelation in the database
        List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelationList = purchaseOrderVoucherRelationRepository.findAll();
        assertThat(purchaseOrderVoucherRelationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseOrderVoucherRelation in Elasticsearch
        verify(mockPurchaseOrderVoucherRelationSearchRepository, times(0)).save(purchaseOrderVoucherRelation);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderVoucherRelation() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);

        int databaseSizeBeforeDelete = purchaseOrderVoucherRelationRepository.findAll().size();

        // Delete the purchaseOrderVoucherRelation
        restPurchaseOrderVoucherRelationMockMvc.perform(delete("/api/purchase-order-voucher-relations/{id}", purchaseOrderVoucherRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelationList = purchaseOrderVoucherRelationRepository.findAll();
        assertThat(purchaseOrderVoucherRelationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseOrderVoucherRelation in Elasticsearch
        verify(mockPurchaseOrderVoucherRelationSearchRepository, times(1)).deleteById(purchaseOrderVoucherRelation.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseOrderVoucherRelation() throws Exception {
        // Initialize the database
        purchaseOrderVoucherRelationRepository.saveAndFlush(purchaseOrderVoucherRelation);
        when(mockPurchaseOrderVoucherRelationSearchRepository.search(queryStringQuery("id:" + purchaseOrderVoucherRelation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseOrderVoucherRelation), PageRequest.of(0, 1), 1));
        // Search the purchaseOrderVoucherRelation
        restPurchaseOrderVoucherRelationMockMvc.perform(get("/api/_search/purchase-order-voucher-relations?query=id:" + purchaseOrderVoucherRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderVoucherRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderVoucherRelation.class);
        PurchaseOrderVoucherRelation purchaseOrderVoucherRelation1 = new PurchaseOrderVoucherRelation();
        purchaseOrderVoucherRelation1.setId(1L);
        PurchaseOrderVoucherRelation purchaseOrderVoucherRelation2 = new PurchaseOrderVoucherRelation();
        purchaseOrderVoucherRelation2.setId(purchaseOrderVoucherRelation1.getId());
        assertThat(purchaseOrderVoucherRelation1).isEqualTo(purchaseOrderVoucherRelation2);
        purchaseOrderVoucherRelation2.setId(2L);
        assertThat(purchaseOrderVoucherRelation1).isNotEqualTo(purchaseOrderVoucherRelation2);
        purchaseOrderVoucherRelation1.setId(null);
        assertThat(purchaseOrderVoucherRelation1).isNotEqualTo(purchaseOrderVoucherRelation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderVoucherRelationDTO.class);
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO1 = new PurchaseOrderVoucherRelationDTO();
        purchaseOrderVoucherRelationDTO1.setId(1L);
        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO2 = new PurchaseOrderVoucherRelationDTO();
        assertThat(purchaseOrderVoucherRelationDTO1).isNotEqualTo(purchaseOrderVoucherRelationDTO2);
        purchaseOrderVoucherRelationDTO2.setId(purchaseOrderVoucherRelationDTO1.getId());
        assertThat(purchaseOrderVoucherRelationDTO1).isEqualTo(purchaseOrderVoucherRelationDTO2);
        purchaseOrderVoucherRelationDTO2.setId(2L);
        assertThat(purchaseOrderVoucherRelationDTO1).isNotEqualTo(purchaseOrderVoucherRelationDTO2);
        purchaseOrderVoucherRelationDTO1.setId(null);
        assertThat(purchaseOrderVoucherRelationDTO1).isNotEqualTo(purchaseOrderVoucherRelationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrderVoucherRelationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrderVoucherRelationMapper.fromId(null)).isNull();
    }
}
