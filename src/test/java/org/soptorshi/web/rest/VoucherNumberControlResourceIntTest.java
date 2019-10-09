package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.VoucherNumberControl;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.Voucher;
import org.soptorshi.repository.VoucherNumberControlRepository;
import org.soptorshi.repository.search.VoucherNumberControlSearchRepository;
import org.soptorshi.service.VoucherNumberControlService;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.mapper.VoucherNumberControlMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.VoucherNumberControlCriteria;
import org.soptorshi.service.VoucherNumberControlQueryService;

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

import org.soptorshi.domain.enumeration.VoucherResetBasis;
/**
 * Test class for the VoucherNumberControlResource REST controller.
 *
 * @see VoucherNumberControlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class VoucherNumberControlResourceIntTest {

    private static final VoucherResetBasis DEFAULT_RESET_BASIS = VoucherResetBasis.YEARLY;
    private static final VoucherResetBasis UPDATED_RESET_BASIS = VoucherResetBasis.MONTHLY;

    private static final Integer DEFAULT_START_VOUCHER_NO = 1;
    private static final Integer UPDATED_START_VOUCHER_NO = 2;

    private static final BigDecimal DEFAULT_VOUCHER_LIMIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOUCHER_LIMIT = new BigDecimal(2);

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private VoucherNumberControlRepository voucherNumberControlRepository;

    @Autowired
    private VoucherNumberControlMapper voucherNumberControlMapper;

    @Autowired
    private VoucherNumberControlService voucherNumberControlService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.VoucherNumberControlSearchRepositoryMockConfiguration
     */
    @Autowired
    private VoucherNumberControlSearchRepository mockVoucherNumberControlSearchRepository;

    @Autowired
    private VoucherNumberControlQueryService voucherNumberControlQueryService;

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

    private MockMvc restVoucherNumberControlMockMvc;

    private VoucherNumberControl voucherNumberControl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoucherNumberControlResource voucherNumberControlResource = new VoucherNumberControlResource(voucherNumberControlService, voucherNumberControlQueryService);
        this.restVoucherNumberControlMockMvc = MockMvcBuilders.standaloneSetup(voucherNumberControlResource)
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
    public static VoucherNumberControl createEntity(EntityManager em) {
        VoucherNumberControl voucherNumberControl = new VoucherNumberControl()
            .resetBasis(DEFAULT_RESET_BASIS)
            .startVoucherNo(DEFAULT_START_VOUCHER_NO)
            .voucherLimit(DEFAULT_VOUCHER_LIMIT)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return voucherNumberControl;
    }

    @Before
    public void initTest() {
        voucherNumberControl = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucherNumberControl() throws Exception {
        int databaseSizeBeforeCreate = voucherNumberControlRepository.findAll().size();

        // Create the VoucherNumberControl
        VoucherNumberControlDTO voucherNumberControlDTO = voucherNumberControlMapper.toDto(voucherNumberControl);
        restVoucherNumberControlMockMvc.perform(post("/api/voucher-number-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberControlDTO)))
            .andExpect(status().isCreated());

        // Validate the VoucherNumberControl in the database
        List<VoucherNumberControl> voucherNumberControlList = voucherNumberControlRepository.findAll();
        assertThat(voucherNumberControlList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherNumberControl testVoucherNumberControl = voucherNumberControlList.get(voucherNumberControlList.size() - 1);
        assertThat(testVoucherNumberControl.getResetBasis()).isEqualTo(DEFAULT_RESET_BASIS);
        assertThat(testVoucherNumberControl.getStartVoucherNo()).isEqualTo(DEFAULT_START_VOUCHER_NO);
        assertThat(testVoucherNumberControl.getVoucherLimit()).isEqualTo(DEFAULT_VOUCHER_LIMIT);
        assertThat(testVoucherNumberControl.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVoucherNumberControl.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);

        // Validate the VoucherNumberControl in Elasticsearch
        verify(mockVoucherNumberControlSearchRepository, times(1)).save(testVoucherNumberControl);
    }

    @Test
    @Transactional
    public void createVoucherNumberControlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherNumberControlRepository.findAll().size();

        // Create the VoucherNumberControl with an existing ID
        voucherNumberControl.setId(1L);
        VoucherNumberControlDTO voucherNumberControlDTO = voucherNumberControlMapper.toDto(voucherNumberControl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherNumberControlMockMvc.perform(post("/api/voucher-number-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberControlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherNumberControl in the database
        List<VoucherNumberControl> voucherNumberControlList = voucherNumberControlRepository.findAll();
        assertThat(voucherNumberControlList).hasSize(databaseSizeBeforeCreate);

        // Validate the VoucherNumberControl in Elasticsearch
        verify(mockVoucherNumberControlSearchRepository, times(0)).save(voucherNumberControl);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControls() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherNumberControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].resetBasis").value(hasItem(DEFAULT_RESET_BASIS.toString())))
            .andExpect(jsonPath("$.[*].startVoucherNo").value(hasItem(DEFAULT_START_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherLimit").value(hasItem(DEFAULT_VOUCHER_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getVoucherNumberControl() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get the voucherNumberControl
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls/{id}", voucherNumberControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voucherNumberControl.getId().intValue()))
            .andExpect(jsonPath("$.resetBasis").value(DEFAULT_RESET_BASIS.toString()))
            .andExpect(jsonPath("$.startVoucherNo").value(DEFAULT_START_VOUCHER_NO))
            .andExpect(jsonPath("$.voucherLimit").value(DEFAULT_VOUCHER_LIMIT.intValue()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()));
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByResetBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where resetBasis equals to DEFAULT_RESET_BASIS
        defaultVoucherNumberControlShouldBeFound("resetBasis.equals=" + DEFAULT_RESET_BASIS);

        // Get all the voucherNumberControlList where resetBasis equals to UPDATED_RESET_BASIS
        defaultVoucherNumberControlShouldNotBeFound("resetBasis.equals=" + UPDATED_RESET_BASIS);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByResetBasisIsInShouldWork() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where resetBasis in DEFAULT_RESET_BASIS or UPDATED_RESET_BASIS
        defaultVoucherNumberControlShouldBeFound("resetBasis.in=" + DEFAULT_RESET_BASIS + "," + UPDATED_RESET_BASIS);

        // Get all the voucherNumberControlList where resetBasis equals to UPDATED_RESET_BASIS
        defaultVoucherNumberControlShouldNotBeFound("resetBasis.in=" + UPDATED_RESET_BASIS);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByResetBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where resetBasis is not null
        defaultVoucherNumberControlShouldBeFound("resetBasis.specified=true");

        // Get all the voucherNumberControlList where resetBasis is null
        defaultVoucherNumberControlShouldNotBeFound("resetBasis.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByStartVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where startVoucherNo equals to DEFAULT_START_VOUCHER_NO
        defaultVoucherNumberControlShouldBeFound("startVoucherNo.equals=" + DEFAULT_START_VOUCHER_NO);

        // Get all the voucherNumberControlList where startVoucherNo equals to UPDATED_START_VOUCHER_NO
        defaultVoucherNumberControlShouldNotBeFound("startVoucherNo.equals=" + UPDATED_START_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByStartVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where startVoucherNo in DEFAULT_START_VOUCHER_NO or UPDATED_START_VOUCHER_NO
        defaultVoucherNumberControlShouldBeFound("startVoucherNo.in=" + DEFAULT_START_VOUCHER_NO + "," + UPDATED_START_VOUCHER_NO);

        // Get all the voucherNumberControlList where startVoucherNo equals to UPDATED_START_VOUCHER_NO
        defaultVoucherNumberControlShouldNotBeFound("startVoucherNo.in=" + UPDATED_START_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByStartVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where startVoucherNo is not null
        defaultVoucherNumberControlShouldBeFound("startVoucherNo.specified=true");

        // Get all the voucherNumberControlList where startVoucherNo is null
        defaultVoucherNumberControlShouldNotBeFound("startVoucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByStartVoucherNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where startVoucherNo greater than or equals to DEFAULT_START_VOUCHER_NO
        defaultVoucherNumberControlShouldBeFound("startVoucherNo.greaterOrEqualThan=" + DEFAULT_START_VOUCHER_NO);

        // Get all the voucherNumberControlList where startVoucherNo greater than or equals to UPDATED_START_VOUCHER_NO
        defaultVoucherNumberControlShouldNotBeFound("startVoucherNo.greaterOrEqualThan=" + UPDATED_START_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByStartVoucherNoIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where startVoucherNo less than or equals to DEFAULT_START_VOUCHER_NO
        defaultVoucherNumberControlShouldNotBeFound("startVoucherNo.lessThan=" + DEFAULT_START_VOUCHER_NO);

        // Get all the voucherNumberControlList where startVoucherNo less than or equals to UPDATED_START_VOUCHER_NO
        defaultVoucherNumberControlShouldBeFound("startVoucherNo.lessThan=" + UPDATED_START_VOUCHER_NO);
    }


    @Test
    @Transactional
    public void getAllVoucherNumberControlsByVoucherLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where voucherLimit equals to DEFAULT_VOUCHER_LIMIT
        defaultVoucherNumberControlShouldBeFound("voucherLimit.equals=" + DEFAULT_VOUCHER_LIMIT);

        // Get all the voucherNumberControlList where voucherLimit equals to UPDATED_VOUCHER_LIMIT
        defaultVoucherNumberControlShouldNotBeFound("voucherLimit.equals=" + UPDATED_VOUCHER_LIMIT);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByVoucherLimitIsInShouldWork() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where voucherLimit in DEFAULT_VOUCHER_LIMIT or UPDATED_VOUCHER_LIMIT
        defaultVoucherNumberControlShouldBeFound("voucherLimit.in=" + DEFAULT_VOUCHER_LIMIT + "," + UPDATED_VOUCHER_LIMIT);

        // Get all the voucherNumberControlList where voucherLimit equals to UPDATED_VOUCHER_LIMIT
        defaultVoucherNumberControlShouldNotBeFound("voucherLimit.in=" + UPDATED_VOUCHER_LIMIT);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByVoucherLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where voucherLimit is not null
        defaultVoucherNumberControlShouldBeFound("voucherLimit.specified=true");

        // Get all the voucherNumberControlList where voucherLimit is null
        defaultVoucherNumberControlShouldNotBeFound("voucherLimit.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultVoucherNumberControlShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the voucherNumberControlList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultVoucherNumberControlShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultVoucherNumberControlShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the voucherNumberControlList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultVoucherNumberControlShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedOn is not null
        defaultVoucherNumberControlShouldBeFound("modifiedOn.specified=true");

        // Get all the voucherNumberControlList where modifiedOn is null
        defaultVoucherNumberControlShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultVoucherNumberControlShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the voucherNumberControlList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultVoucherNumberControlShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultVoucherNumberControlShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the voucherNumberControlList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultVoucherNumberControlShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultVoucherNumberControlShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the voucherNumberControlList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultVoucherNumberControlShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultVoucherNumberControlShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the voucherNumberControlList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultVoucherNumberControlShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        // Get all the voucherNumberControlList where modifiedBy is not null
        defaultVoucherNumberControlShouldBeFound("modifiedBy.specified=true");

        // Get all the voucherNumberControlList where modifiedBy is null
        defaultVoucherNumberControlShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllVoucherNumberControlsByFinancialAccountYearIsEqualToSomething() throws Exception {
        // Initialize the database
        FinancialAccountYear financialAccountYear = FinancialAccountYearResourceIntTest.createEntity(em);
        em.persist(financialAccountYear);
        em.flush();
        voucherNumberControl.setFinancialAccountYear(financialAccountYear);
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);
        Long financialAccountYearId = financialAccountYear.getId();

        // Get all the voucherNumberControlList where financialAccountYear equals to financialAccountYearId
        defaultVoucherNumberControlShouldBeFound("financialAccountYearId.equals=" + financialAccountYearId);

        // Get all the voucherNumberControlList where financialAccountYear equals to financialAccountYearId + 1
        defaultVoucherNumberControlShouldNotBeFound("financialAccountYearId.equals=" + (financialAccountYearId + 1));
    }


    @Test
    @Transactional
    public void getAllVoucherNumberControlsByVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        Voucher voucher = VoucherResourceIntTest.createEntity(em);
        em.persist(voucher);
        em.flush();
        voucherNumberControl.setVoucher(voucher);
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);
        Long voucherId = voucher.getId();

        // Get all the voucherNumberControlList where voucher equals to voucherId
        defaultVoucherNumberControlShouldBeFound("voucherId.equals=" + voucherId);

        // Get all the voucherNumberControlList where voucher equals to voucherId + 1
        defaultVoucherNumberControlShouldNotBeFound("voucherId.equals=" + (voucherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVoucherNumberControlShouldBeFound(String filter) throws Exception {
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherNumberControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].resetBasis").value(hasItem(DEFAULT_RESET_BASIS.toString())))
            .andExpect(jsonPath("$.[*].startVoucherNo").value(hasItem(DEFAULT_START_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherLimit").value(hasItem(DEFAULT_VOUCHER_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVoucherNumberControlShouldNotBeFound(String filter) throws Exception {
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVoucherNumberControl() throws Exception {
        // Get the voucherNumberControl
        restVoucherNumberControlMockMvc.perform(get("/api/voucher-number-controls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucherNumberControl() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        int databaseSizeBeforeUpdate = voucherNumberControlRepository.findAll().size();

        // Update the voucherNumberControl
        VoucherNumberControl updatedVoucherNumberControl = voucherNumberControlRepository.findById(voucherNumberControl.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherNumberControl are not directly saved in db
        em.detach(updatedVoucherNumberControl);
        updatedVoucherNumberControl
            .resetBasis(UPDATED_RESET_BASIS)
            .startVoucherNo(UPDATED_START_VOUCHER_NO)
            .voucherLimit(UPDATED_VOUCHER_LIMIT)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .modifiedBy(UPDATED_MODIFIED_BY);
        VoucherNumberControlDTO voucherNumberControlDTO = voucherNumberControlMapper.toDto(updatedVoucherNumberControl);

        restVoucherNumberControlMockMvc.perform(put("/api/voucher-number-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberControlDTO)))
            .andExpect(status().isOk());

        // Validate the VoucherNumberControl in the database
        List<VoucherNumberControl> voucherNumberControlList = voucherNumberControlRepository.findAll();
        assertThat(voucherNumberControlList).hasSize(databaseSizeBeforeUpdate);
        VoucherNumberControl testVoucherNumberControl = voucherNumberControlList.get(voucherNumberControlList.size() - 1);
        assertThat(testVoucherNumberControl.getResetBasis()).isEqualTo(UPDATED_RESET_BASIS);
        assertThat(testVoucherNumberControl.getStartVoucherNo()).isEqualTo(UPDATED_START_VOUCHER_NO);
        assertThat(testVoucherNumberControl.getVoucherLimit()).isEqualTo(UPDATED_VOUCHER_LIMIT);
        assertThat(testVoucherNumberControl.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVoucherNumberControl.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);

        // Validate the VoucherNumberControl in Elasticsearch
        verify(mockVoucherNumberControlSearchRepository, times(1)).save(testVoucherNumberControl);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucherNumberControl() throws Exception {
        int databaseSizeBeforeUpdate = voucherNumberControlRepository.findAll().size();

        // Create the VoucherNumberControl
        VoucherNumberControlDTO voucherNumberControlDTO = voucherNumberControlMapper.toDto(voucherNumberControl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherNumberControlMockMvc.perform(put("/api/voucher-number-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voucherNumberControlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherNumberControl in the database
        List<VoucherNumberControl> voucherNumberControlList = voucherNumberControlRepository.findAll();
        assertThat(voucherNumberControlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VoucherNumberControl in Elasticsearch
        verify(mockVoucherNumberControlSearchRepository, times(0)).save(voucherNumberControl);
    }

    @Test
    @Transactional
    public void deleteVoucherNumberControl() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);

        int databaseSizeBeforeDelete = voucherNumberControlRepository.findAll().size();

        // Delete the voucherNumberControl
        restVoucherNumberControlMockMvc.perform(delete("/api/voucher-number-controls/{id}", voucherNumberControl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VoucherNumberControl> voucherNumberControlList = voucherNumberControlRepository.findAll();
        assertThat(voucherNumberControlList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VoucherNumberControl in Elasticsearch
        verify(mockVoucherNumberControlSearchRepository, times(1)).deleteById(voucherNumberControl.getId());
    }

    @Test
    @Transactional
    public void searchVoucherNumberControl() throws Exception {
        // Initialize the database
        voucherNumberControlRepository.saveAndFlush(voucherNumberControl);
        when(mockVoucherNumberControlSearchRepository.search(queryStringQuery("id:" + voucherNumberControl.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(voucherNumberControl), PageRequest.of(0, 1), 1));
        // Search the voucherNumberControl
        restVoucherNumberControlMockMvc.perform(get("/api/_search/voucher-number-controls?query=id:" + voucherNumberControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherNumberControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].resetBasis").value(hasItem(DEFAULT_RESET_BASIS.toString())))
            .andExpect(jsonPath("$.[*].startVoucherNo").value(hasItem(DEFAULT_START_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherLimit").value(hasItem(DEFAULT_VOUCHER_LIMIT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherNumberControl.class);
        VoucherNumberControl voucherNumberControl1 = new VoucherNumberControl();
        voucherNumberControl1.setId(1L);
        VoucherNumberControl voucherNumberControl2 = new VoucherNumberControl();
        voucherNumberControl2.setId(voucherNumberControl1.getId());
        assertThat(voucherNumberControl1).isEqualTo(voucherNumberControl2);
        voucherNumberControl2.setId(2L);
        assertThat(voucherNumberControl1).isNotEqualTo(voucherNumberControl2);
        voucherNumberControl1.setId(null);
        assertThat(voucherNumberControl1).isNotEqualTo(voucherNumberControl2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherNumberControlDTO.class);
        VoucherNumberControlDTO voucherNumberControlDTO1 = new VoucherNumberControlDTO();
        voucherNumberControlDTO1.setId(1L);
        VoucherNumberControlDTO voucherNumberControlDTO2 = new VoucherNumberControlDTO();
        assertThat(voucherNumberControlDTO1).isNotEqualTo(voucherNumberControlDTO2);
        voucherNumberControlDTO2.setId(voucherNumberControlDTO1.getId());
        assertThat(voucherNumberControlDTO1).isEqualTo(voucherNumberControlDTO2);
        voucherNumberControlDTO2.setId(2L);
        assertThat(voucherNumberControlDTO1).isNotEqualTo(voucherNumberControlDTO2);
        voucherNumberControlDTO1.setId(null);
        assertThat(voucherNumberControlDTO1).isNotEqualTo(voucherNumberControlDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(voucherNumberControlMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(voucherNumberControlMapper.fromId(null)).isNull();
    }
}
