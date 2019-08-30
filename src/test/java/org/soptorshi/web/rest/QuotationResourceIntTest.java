package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.search.QuotationSearchRepository;
import org.soptorshi.service.QuotationService;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.mapper.QuotationMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.QuotationCriteria;
import org.soptorshi.service.QuotationQueryService;

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

import org.soptorshi.domain.enumeration.SelectionType;
/**
 * Test class for the QuotationResource REST controller.
 *
 * @see QuotationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class QuotationResourceIntTest {

    private static final String DEFAULT_QUOTATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_QUOTATION_NO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final SelectionType DEFAULT_SELECTION_STATUS = SelectionType.SELECTED;
    private static final SelectionType UPDATED_SELECTION_STATUS = SelectionType.NOT_SELECTED;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationMapper quotationMapper;

    @Autowired
    private QuotationService quotationService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.QuotationSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuotationSearchRepository mockQuotationSearchRepository;

    @Autowired
    private QuotationQueryService quotationQueryService;

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

    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuotationResource quotationResource = new QuotationResource(quotationService, quotationQueryService);
        this.restQuotationMockMvc = MockMvcBuilders.standaloneSetup(quotationResource)
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
    public static Quotation createEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .quotationNo(DEFAULT_QUOTATION_NO)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .selectionStatus(DEFAULT_SELECTION_STATUS)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return quotation;
    }

    @Before
    public void initTest() {
        quotation = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotation() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isCreated());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate + 1);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotation.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testQuotation.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testQuotation.getSelectionStatus()).isEqualTo(DEFAULT_SELECTION_STATUS);
        assertThat(testQuotation.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testQuotation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testQuotation.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    @Transactional
    public void createQuotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation with an existing ID
        quotation.setId(1L);
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    @Transactional
    public void getAllQuotations() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId().intValue()))
            .andExpect(jsonPath("$.quotationNo").value(DEFAULT_QUOTATION_NO.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.selectionStatus").value(DEFAULT_SELECTION_STATUS.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo equals to DEFAULT_QUOTATION_NO
        defaultQuotationShouldBeFound("quotationNo.equals=" + DEFAULT_QUOTATION_NO);

        // Get all the quotationList where quotationNo equals to UPDATED_QUOTATION_NO
        defaultQuotationShouldNotBeFound("quotationNo.equals=" + UPDATED_QUOTATION_NO);
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo in DEFAULT_QUOTATION_NO or UPDATED_QUOTATION_NO
        defaultQuotationShouldBeFound("quotationNo.in=" + DEFAULT_QUOTATION_NO + "," + UPDATED_QUOTATION_NO);

        // Get all the quotationList where quotationNo equals to UPDATED_QUOTATION_NO
        defaultQuotationShouldNotBeFound("quotationNo.in=" + UPDATED_QUOTATION_NO);
    }

    @Test
    @Transactional
    public void getAllQuotationsByQuotationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where quotationNo is not null
        defaultQuotationShouldBeFound("quotationNo.specified=true");

        // Get all the quotationList where quotationNo is null
        defaultQuotationShouldNotBeFound("quotationNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus equals to DEFAULT_SELECTION_STATUS
        defaultQuotationShouldBeFound("selectionStatus.equals=" + DEFAULT_SELECTION_STATUS);

        // Get all the quotationList where selectionStatus equals to UPDATED_SELECTION_STATUS
        defaultQuotationShouldNotBeFound("selectionStatus.equals=" + UPDATED_SELECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus in DEFAULT_SELECTION_STATUS or UPDATED_SELECTION_STATUS
        defaultQuotationShouldBeFound("selectionStatus.in=" + DEFAULT_SELECTION_STATUS + "," + UPDATED_SELECTION_STATUS);

        // Get all the quotationList where selectionStatus equals to UPDATED_SELECTION_STATUS
        defaultQuotationShouldNotBeFound("selectionStatus.in=" + UPDATED_SELECTION_STATUS);
    }

    @Test
    @Transactional
    public void getAllQuotationsBySelectionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where selectionStatus is not null
        defaultQuotationShouldBeFound("selectionStatus.specified=true");

        // Get all the quotationList where selectionStatus is null
        defaultQuotationShouldNotBeFound("selectionStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultQuotationShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the quotationList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultQuotationShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultQuotationShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the quotationList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultQuotationShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllQuotationsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where totalAmount is not null
        defaultQuotationShouldBeFound("totalAmount.specified=true");

        // Get all the quotationList where totalAmount is null
        defaultQuotationShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultQuotationShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the quotationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultQuotationShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the quotationList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultQuotationShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedBy is not null
        defaultQuotationShouldBeFound("modifiedBy.specified=true");

        // Get all the quotationList where modifiedBy is null
        defaultQuotationShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the quotationList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn is not null
        defaultQuotationShouldBeFound("modifiedOn.specified=true");

        // Get all the quotationList where modifiedOn is null
        defaultQuotationShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllQuotationsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultQuotationShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the quotationList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultQuotationShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllQuotationsByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        quotation.setRequisition(requisition);
        quotationRepository.saveAndFlush(quotation);
        Long requisitionId = requisition.getId();

        // Get all the quotationList where requisition equals to requisitionId
        defaultQuotationShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the quotationList where requisition equals to requisitionId + 1
        defaultQuotationShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }


    @Test
    @Transactional
    public void getAllQuotationsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        Vendor vendor = VendorResourceIntTest.createEntity(em);
        em.persist(vendor);
        em.flush();
        quotation.setVendor(vendor);
        quotationRepository.saveAndFlush(quotation);
        Long vendorId = vendor.getId();

        // Get all the quotationList where vendor equals to vendorId
        defaultQuotationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the quotationList where vendor equals to vendorId + 1
        defaultQuotationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultQuotationShouldBeFound(String filter) throws Exception {
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restQuotationMockMvc.perform(get("/api/quotations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultQuotationShouldNotBeFound(String filter) throws Exception {
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuotationMockMvc.perform(get("/api/quotations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).get();
        // Disconnect from session so that the updates on updatedQuotation are not directly saved in db
        em.detach(updatedQuotation);
        updatedQuotation
            .quotationNo(UPDATED_QUOTATION_NO)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .selectionStatus(UPDATED_SELECTION_STATUS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        QuotationDTO quotationDTO = quotationMapper.toDto(updatedQuotation);

        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotation.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testQuotation.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testQuotation.getSelectionStatus()).isEqualTo(UPDATED_SELECTION_STATUS);
        assertThat(testQuotation.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testQuotation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testQuotation.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Create the Quotation
        QuotationDTO quotationDTO = quotationMapper.toDto(quotation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    @Transactional
    public void deleteQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeDelete = quotationRepository.findAll().size();

        // Delete the quotation
        restQuotationMockMvc.perform(delete("/api/quotations/{id}", quotation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).deleteById(quotation.getId());
    }

    @Test
    @Transactional
    public void searchQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);
        when(mockQuotationSearchRepository.search(queryStringQuery("id:" + quotation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(quotation), PageRequest.of(0, 1), 1));
        // Search the quotation
        restQuotationMockMvc.perform(get("/api/_search/quotations?query=id:" + quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].selectionStatus").value(hasItem(DEFAULT_SELECTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotation.class);
        Quotation quotation1 = new Quotation();
        quotation1.setId(1L);
        Quotation quotation2 = new Quotation();
        quotation2.setId(quotation1.getId());
        assertThat(quotation1).isEqualTo(quotation2);
        quotation2.setId(2L);
        assertThat(quotation1).isNotEqualTo(quotation2);
        quotation1.setId(null);
        assertThat(quotation1).isNotEqualTo(quotation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationDTO.class);
        QuotationDTO quotationDTO1 = new QuotationDTO();
        quotationDTO1.setId(1L);
        QuotationDTO quotationDTO2 = new QuotationDTO();
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
        quotationDTO2.setId(quotationDTO1.getId());
        assertThat(quotationDTO1).isEqualTo(quotationDTO2);
        quotationDTO2.setId(2L);
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
        quotationDTO1.setId(null);
        assertThat(quotationDTO1).isNotEqualTo(quotationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(quotationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(quotationMapper.fromId(null)).isNull();
    }
}
