package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.enumeration.CommercialStatus;
import org.soptorshi.repository.CommercialPoStatusRepository;
import org.soptorshi.repository.search.CommercialPoStatusSearchRepository;
import org.soptorshi.service.CommercialPoStatusQueryService;
import org.soptorshi.service.CommercialPoStatusService;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.mapper.CommercialPoStatusMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the CommercialPoStatusResource REST controller.
 *
 * @see CommercialPoStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialPoStatusResourceIntTest {

    private static final CommercialStatus DEFAULT_STATUS = CommercialStatus.WAITING_FOR_PROFORMA_INVOICE;
    private static final CommercialStatus UPDATED_STATUS = CommercialStatus.WAITING_FOR_PROFORMA_INVOICE_APPROVAL;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommercialPoStatusRepository commercialPoStatusRepository;

    @Autowired
    private CommercialPoStatusMapper commercialPoStatusMapper;

    @Autowired
    private CommercialPoStatusService commercialPoStatusService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialPoStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialPoStatusSearchRepository mockCommercialPoStatusSearchRepository;

    @Autowired
    private CommercialPoStatusQueryService commercialPoStatusQueryService;

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

    private MockMvc restCommercialPoStatusMockMvc;

    private CommercialPoStatus commercialPoStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialPoStatusResource commercialPoStatusResource = new CommercialPoStatusResource(commercialPoStatusService, commercialPoStatusQueryService);
        this.restCommercialPoStatusMockMvc = MockMvcBuilders.standaloneSetup(commercialPoStatusResource)
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
    public static CommercialPoStatus createEntity(EntityManager em) {
        CommercialPoStatus commercialPoStatus = new CommercialPoStatus()
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createOn(DEFAULT_CREATE_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return commercialPoStatus;
    }

    @Before
    public void initTest() {
        commercialPoStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialPoStatus() throws Exception {
        int databaseSizeBeforeCreate = commercialPoStatusRepository.findAll().size();

        // Create the CommercialPoStatus
        CommercialPoStatusDTO commercialPoStatusDTO = commercialPoStatusMapper.toDto(commercialPoStatus);
        restCommercialPoStatusMockMvc.perform(post("/api/commercial-po-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialPoStatus in the database
        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialPoStatus testCommercialPoStatus = commercialPoStatusList.get(commercialPoStatusList.size() - 1);
        assertThat(testCommercialPoStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCommercialPoStatus.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCommercialPoStatus.getCreateOn()).isEqualTo(DEFAULT_CREATE_ON);
        assertThat(testCommercialPoStatus.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCommercialPoStatus.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the CommercialPoStatus in Elasticsearch
        verify(mockCommercialPoStatusSearchRepository, times(1)).save(testCommercialPoStatus);
    }

    @Test
    @Transactional
    public void createCommercialPoStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialPoStatusRepository.findAll().size();

        // Create the CommercialPoStatus with an existing ID
        commercialPoStatus.setId(1L);
        CommercialPoStatusDTO commercialPoStatusDTO = commercialPoStatusMapper.toDto(commercialPoStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialPoStatusMockMvc.perform(post("/api/commercial-po-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPoStatus in the database
        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialPoStatus in Elasticsearch
        verify(mockCommercialPoStatusSearchRepository, times(0)).save(commercialPoStatus);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercialPoStatusRepository.findAll().size();
        // set the field null
        commercialPoStatus.setStatus(null);

        // Create the CommercialPoStatus, which fails.
        CommercialPoStatusDTO commercialPoStatusDTO = commercialPoStatusMapper.toDto(commercialPoStatus);

        restCommercialPoStatusMockMvc.perform(post("/api/commercial-po-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatuses() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPoStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getCommercialPoStatus() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get the commercialPoStatus
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses/{id}", commercialPoStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialPoStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createOn").value(DEFAULT_CREATE_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where status equals to DEFAULT_STATUS
        defaultCommercialPoStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the commercialPoStatusList where status equals to UPDATED_STATUS
        defaultCommercialPoStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCommercialPoStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the commercialPoStatusList where status equals to UPDATED_STATUS
        defaultCommercialPoStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where status is not null
        defaultCommercialPoStatusShouldBeFound("status.specified=true");

        // Get all the commercialPoStatusList where status is null
        defaultCommercialPoStatusShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createdBy equals to DEFAULT_CREATED_BY
        defaultCommercialPoStatusShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the commercialPoStatusList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPoStatusShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCommercialPoStatusShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the commercialPoStatusList where createdBy equals to UPDATED_CREATED_BY
        defaultCommercialPoStatusShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createdBy is not null
        defaultCommercialPoStatusShouldBeFound("createdBy.specified=true");

        // Get all the commercialPoStatusList where createdBy is null
        defaultCommercialPoStatusShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreateOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createOn equals to DEFAULT_CREATE_ON
        defaultCommercialPoStatusShouldBeFound("createOn.equals=" + DEFAULT_CREATE_ON);

        // Get all the commercialPoStatusList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPoStatusShouldNotBeFound("createOn.equals=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreateOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createOn in DEFAULT_CREATE_ON or UPDATED_CREATE_ON
        defaultCommercialPoStatusShouldBeFound("createOn.in=" + DEFAULT_CREATE_ON + "," + UPDATED_CREATE_ON);

        // Get all the commercialPoStatusList where createOn equals to UPDATED_CREATE_ON
        defaultCommercialPoStatusShouldNotBeFound("createOn.in=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreateOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createOn is not null
        defaultCommercialPoStatusShouldBeFound("createOn.specified=true");

        // Get all the commercialPoStatusList where createOn is null
        defaultCommercialPoStatusShouldNotBeFound("createOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreateOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createOn greater than or equals to DEFAULT_CREATE_ON
        defaultCommercialPoStatusShouldBeFound("createOn.greaterOrEqualThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPoStatusList where createOn greater than or equals to UPDATED_CREATE_ON
        defaultCommercialPoStatusShouldNotBeFound("createOn.greaterOrEqualThan=" + UPDATED_CREATE_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCreateOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where createOn less than or equals to DEFAULT_CREATE_ON
        defaultCommercialPoStatusShouldNotBeFound("createOn.lessThan=" + DEFAULT_CREATE_ON);

        // Get all the commercialPoStatusList where createOn less than or equals to UPDATED_CREATE_ON
        defaultCommercialPoStatusShouldBeFound("createOn.lessThan=" + UPDATED_CREATE_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCommercialPoStatusShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the commercialPoStatusList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPoStatusShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCommercialPoStatusShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the commercialPoStatusList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCommercialPoStatusShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedBy is not null
        defaultCommercialPoStatusShouldBeFound("updatedBy.specified=true");

        // Get all the commercialPoStatusList where updatedBy is null
        defaultCommercialPoStatusShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultCommercialPoStatusShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPoStatusList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPoStatusShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultCommercialPoStatusShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the commercialPoStatusList where updatedOn equals to UPDATED_UPDATED_ON
        defaultCommercialPoStatusShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedOn is not null
        defaultCommercialPoStatusShouldBeFound("updatedOn.specified=true");

        // Get all the commercialPoStatusList where updatedOn is null
        defaultCommercialPoStatusShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedOn greater than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPoStatusShouldBeFound("updatedOn.greaterOrEqualThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPoStatusList where updatedOn greater than or equals to UPDATED_UPDATED_ON
        defaultCommercialPoStatusShouldNotBeFound("updatedOn.greaterOrEqualThan=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllCommercialPoStatusesByUpdatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        // Get all the commercialPoStatusList where updatedOn less than or equals to DEFAULT_UPDATED_ON
        defaultCommercialPoStatusShouldNotBeFound("updatedOn.lessThan=" + DEFAULT_UPDATED_ON);

        // Get all the commercialPoStatusList where updatedOn less than or equals to UPDATED_UPDATED_ON
        defaultCommercialPoStatusShouldBeFound("updatedOn.lessThan=" + UPDATED_UPDATED_ON);
    }


    @Test
    @Transactional
    public void getAllCommercialPoStatusesByCommercialPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPurchaseOrder commercialPurchaseOrder = CommercialPurchaseOrderResourceIntTest.createEntity(em);
        em.persist(commercialPurchaseOrder);
        em.flush();
        commercialPoStatus.setCommercialPurchaseOrder(commercialPurchaseOrder);
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);
        Long commercialPurchaseOrderId = commercialPurchaseOrder.getId();

        // Get all the commercialPoStatusList where commercialPurchaseOrder equals to commercialPurchaseOrderId
        defaultCommercialPoStatusShouldBeFound("commercialPurchaseOrderId.equals=" + commercialPurchaseOrderId);

        // Get all the commercialPoStatusList where commercialPurchaseOrder equals to commercialPurchaseOrderId + 1
        defaultCommercialPoStatusShouldNotBeFound("commercialPurchaseOrderId.equals=" + (commercialPurchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialPoStatusShouldBeFound(String filter) throws Exception {
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPoStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialPoStatusShouldNotBeFound(String filter) throws Exception {
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialPoStatus() throws Exception {
        // Get the commercialPoStatus
        restCommercialPoStatusMockMvc.perform(get("/api/commercial-po-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialPoStatus() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        int databaseSizeBeforeUpdate = commercialPoStatusRepository.findAll().size();

        // Update the commercialPoStatus
        CommercialPoStatus updatedCommercialPoStatus = commercialPoStatusRepository.findById(commercialPoStatus.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialPoStatus are not directly saved in db
        em.detach(updatedCommercialPoStatus);
        updatedCommercialPoStatus
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createOn(UPDATED_CREATE_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        CommercialPoStatusDTO commercialPoStatusDTO = commercialPoStatusMapper.toDto(updatedCommercialPoStatus);

        restCommercialPoStatusMockMvc.perform(put("/api/commercial-po-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialPoStatus in the database
        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeUpdate);
        CommercialPoStatus testCommercialPoStatus = commercialPoStatusList.get(commercialPoStatusList.size() - 1);
        assertThat(testCommercialPoStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCommercialPoStatus.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCommercialPoStatus.getCreateOn()).isEqualTo(UPDATED_CREATE_ON);
        assertThat(testCommercialPoStatus.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCommercialPoStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the CommercialPoStatus in Elasticsearch
        verify(mockCommercialPoStatusSearchRepository, times(1)).save(testCommercialPoStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialPoStatus() throws Exception {
        int databaseSizeBeforeUpdate = commercialPoStatusRepository.findAll().size();

        // Create the CommercialPoStatus
        CommercialPoStatusDTO commercialPoStatusDTO = commercialPoStatusMapper.toDto(commercialPoStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialPoStatusMockMvc.perform(put("/api/commercial-po-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialPoStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialPoStatus in the database
        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialPoStatus in Elasticsearch
        verify(mockCommercialPoStatusSearchRepository, times(0)).save(commercialPoStatus);
    }

    @Test
    @Transactional
    public void deleteCommercialPoStatus() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);

        int databaseSizeBeforeDelete = commercialPoStatusRepository.findAll().size();

        // Delete the commercialPoStatus
        restCommercialPoStatusMockMvc.perform(delete("/api/commercial-po-statuses/{id}", commercialPoStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialPoStatus> commercialPoStatusList = commercialPoStatusRepository.findAll();
        assertThat(commercialPoStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialPoStatus in Elasticsearch
        verify(mockCommercialPoStatusSearchRepository, times(1)).deleteById(commercialPoStatus.getId());
    }

    @Test
    @Transactional
    public void searchCommercialPoStatus() throws Exception {
        // Initialize the database
        commercialPoStatusRepository.saveAndFlush(commercialPoStatus);
        when(mockCommercialPoStatusSearchRepository.search(queryStringQuery("id:" + commercialPoStatus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialPoStatus), PageRequest.of(0, 1), 1));
        // Search the commercialPoStatus
        restCommercialPoStatusMockMvc.perform(get("/api/_search/commercial-po-statuses?query=id:" + commercialPoStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialPoStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createOn").value(hasItem(DEFAULT_CREATE_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPoStatus.class);
        CommercialPoStatus commercialPoStatus1 = new CommercialPoStatus();
        commercialPoStatus1.setId(1L);
        CommercialPoStatus commercialPoStatus2 = new CommercialPoStatus();
        commercialPoStatus2.setId(commercialPoStatus1.getId());
        assertThat(commercialPoStatus1).isEqualTo(commercialPoStatus2);
        commercialPoStatus2.setId(2L);
        assertThat(commercialPoStatus1).isNotEqualTo(commercialPoStatus2);
        commercialPoStatus1.setId(null);
        assertThat(commercialPoStatus1).isNotEqualTo(commercialPoStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialPoStatusDTO.class);
        CommercialPoStatusDTO commercialPoStatusDTO1 = new CommercialPoStatusDTO();
        commercialPoStatusDTO1.setId(1L);
        CommercialPoStatusDTO commercialPoStatusDTO2 = new CommercialPoStatusDTO();
        assertThat(commercialPoStatusDTO1).isNotEqualTo(commercialPoStatusDTO2);
        commercialPoStatusDTO2.setId(commercialPoStatusDTO1.getId());
        assertThat(commercialPoStatusDTO1).isEqualTo(commercialPoStatusDTO2);
        commercialPoStatusDTO2.setId(2L);
        assertThat(commercialPoStatusDTO1).isNotEqualTo(commercialPoStatusDTO2);
        commercialPoStatusDTO1.setId(null);
        assertThat(commercialPoStatusDTO1).isNotEqualTo(commercialPoStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialPoStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialPoStatusMapper.fromId(null)).isNull();
    }
}
