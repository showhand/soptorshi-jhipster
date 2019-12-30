package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.PurchaseOrderMessages;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.repository.PurchaseOrderMessagesRepository;
import org.soptorshi.repository.search.PurchaseOrderMessagesSearchRepository;
import org.soptorshi.service.PurchaseOrderMessagesService;
import org.soptorshi.service.dto.PurchaseOrderMessagesDTO;
import org.soptorshi.service.mapper.PurchaseOrderMessagesMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.PurchaseOrderMessagesCriteria;
import org.soptorshi.service.PurchaseOrderMessagesQueryService;

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
 * Test class for the PurchaseOrderMessagesResource REST controller.
 *
 * @see PurchaseOrderMessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class PurchaseOrderMessagesResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMMENTED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENTED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PurchaseOrderMessagesRepository purchaseOrderMessagesRepository;

    @Autowired
    private PurchaseOrderMessagesMapper purchaseOrderMessagesMapper;

    @Autowired
    private PurchaseOrderMessagesService purchaseOrderMessagesService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.PurchaseOrderMessagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseOrderMessagesSearchRepository mockPurchaseOrderMessagesSearchRepository;

    @Autowired
    private PurchaseOrderMessagesQueryService purchaseOrderMessagesQueryService;

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

    private MockMvc restPurchaseOrderMessagesMockMvc;

    private PurchaseOrderMessages purchaseOrderMessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderMessagesResource purchaseOrderMessagesResource = new PurchaseOrderMessagesResource(purchaseOrderMessagesService, purchaseOrderMessagesQueryService);
        this.restPurchaseOrderMessagesMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderMessagesResource)
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
    public static PurchaseOrderMessages createEntity(EntityManager em) {
        PurchaseOrderMessages purchaseOrderMessages = new PurchaseOrderMessages()
            .comments(DEFAULT_COMMENTS)
            .commentedOn(DEFAULT_COMMENTED_ON);
        return purchaseOrderMessages;
    }

    @Before
    public void initTest() {
        purchaseOrderMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderMessages() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderMessagesRepository.findAll().size();

        // Create the PurchaseOrderMessages
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO = purchaseOrderMessagesMapper.toDto(purchaseOrderMessages);
        restPurchaseOrderMessagesMockMvc.perform(post("/api/purchase-order-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderMessages in the database
        List<PurchaseOrderMessages> purchaseOrderMessagesList = purchaseOrderMessagesRepository.findAll();
        assertThat(purchaseOrderMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderMessages testPurchaseOrderMessages = purchaseOrderMessagesList.get(purchaseOrderMessagesList.size() - 1);
        assertThat(testPurchaseOrderMessages.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPurchaseOrderMessages.getCommentedOn()).isEqualTo(DEFAULT_COMMENTED_ON);

        // Validate the PurchaseOrderMessages in Elasticsearch
        verify(mockPurchaseOrderMessagesSearchRepository, times(1)).save(testPurchaseOrderMessages);
    }

    @Test
    @Transactional
    public void createPurchaseOrderMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderMessagesRepository.findAll().size();

        // Create the PurchaseOrderMessages with an existing ID
        purchaseOrderMessages.setId(1L);
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO = purchaseOrderMessagesMapper.toDto(purchaseOrderMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMessagesMockMvc.perform(post("/api/purchase-order-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderMessages in the database
        List<PurchaseOrderMessages> purchaseOrderMessagesList = purchaseOrderMessagesRepository.findAll();
        assertThat(purchaseOrderMessagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseOrderMessages in Elasticsearch
        verify(mockPurchaseOrderMessagesSearchRepository, times(0)).save(purchaseOrderMessages);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessages() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrderMessages() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get the purchaseOrderMessages
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages/{id}", purchaseOrderMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderMessages.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.commentedOn").value(DEFAULT_COMMENTED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommentedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList where commentedOn equals to DEFAULT_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldBeFound("commentedOn.equals=" + DEFAULT_COMMENTED_ON);

        // Get all the purchaseOrderMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldNotBeFound("commentedOn.equals=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommentedOnIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList where commentedOn in DEFAULT_COMMENTED_ON or UPDATED_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldBeFound("commentedOn.in=" + DEFAULT_COMMENTED_ON + "," + UPDATED_COMMENTED_ON);

        // Get all the purchaseOrderMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldNotBeFound("commentedOn.in=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommentedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList where commentedOn is not null
        defaultPurchaseOrderMessagesShouldBeFound("commentedOn.specified=true");

        // Get all the purchaseOrderMessagesList where commentedOn is null
        defaultPurchaseOrderMessagesShouldNotBeFound("commentedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommentedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList where commentedOn greater than or equals to DEFAULT_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldBeFound("commentedOn.greaterOrEqualThan=" + DEFAULT_COMMENTED_ON);

        // Get all the purchaseOrderMessagesList where commentedOn greater than or equals to UPDATED_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldNotBeFound("commentedOn.greaterOrEqualThan=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommentedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        // Get all the purchaseOrderMessagesList where commentedOn less than or equals to DEFAULT_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldNotBeFound("commentedOn.lessThan=" + DEFAULT_COMMENTED_ON);

        // Get all the purchaseOrderMessagesList where commentedOn less than or equals to UPDATED_COMMENTED_ON
        defaultPurchaseOrderMessagesShouldBeFound("commentedOn.lessThan=" + UPDATED_COMMENTED_ON);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByCommenterIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee commenter = EmployeeResourceIntTest.createEntity(em);
        em.persist(commenter);
        em.flush();
        purchaseOrderMessages.setCommenter(commenter);
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);
        Long commenterId = commenter.getId();

        // Get all the purchaseOrderMessagesList where commenter equals to commenterId
        defaultPurchaseOrderMessagesShouldBeFound("commenterId.equals=" + commenterId);

        // Get all the purchaseOrderMessagesList where commenter equals to commenterId + 1
        defaultPurchaseOrderMessagesShouldNotBeFound("commenterId.equals=" + (commenterId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderMessagesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIntTest.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        purchaseOrderMessages.setPurchaseOrder(purchaseOrder);
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the purchaseOrderMessagesList where purchaseOrder equals to purchaseOrderId
        defaultPurchaseOrderMessagesShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the purchaseOrderMessagesList where purchaseOrder equals to purchaseOrderId + 1
        defaultPurchaseOrderMessagesShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPurchaseOrderMessagesShouldBeFound(String filter) throws Exception {
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPurchaseOrderMessagesShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseOrderMessages() throws Exception {
        // Get the purchaseOrderMessages
        restPurchaseOrderMessagesMockMvc.perform(get("/api/purchase-order-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderMessages() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        int databaseSizeBeforeUpdate = purchaseOrderMessagesRepository.findAll().size();

        // Update the purchaseOrderMessages
        PurchaseOrderMessages updatedPurchaseOrderMessages = purchaseOrderMessagesRepository.findById(purchaseOrderMessages.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrderMessages are not directly saved in db
        em.detach(updatedPurchaseOrderMessages);
        updatedPurchaseOrderMessages
            .comments(UPDATED_COMMENTS)
            .commentedOn(UPDATED_COMMENTED_ON);
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO = purchaseOrderMessagesMapper.toDto(updatedPurchaseOrderMessages);

        restPurchaseOrderMessagesMockMvc.perform(put("/api/purchase-order-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderMessages in the database
        List<PurchaseOrderMessages> purchaseOrderMessagesList = purchaseOrderMessagesRepository.findAll();
        assertThat(purchaseOrderMessagesList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderMessages testPurchaseOrderMessages = purchaseOrderMessagesList.get(purchaseOrderMessagesList.size() - 1);
        assertThat(testPurchaseOrderMessages.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPurchaseOrderMessages.getCommentedOn()).isEqualTo(UPDATED_COMMENTED_ON);

        // Validate the PurchaseOrderMessages in Elasticsearch
        verify(mockPurchaseOrderMessagesSearchRepository, times(1)).save(testPurchaseOrderMessages);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrderMessages() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderMessagesRepository.findAll().size();

        // Create the PurchaseOrderMessages
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO = purchaseOrderMessagesMapper.toDto(purchaseOrderMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMessagesMockMvc.perform(put("/api/purchase-order-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderMessages in the database
        List<PurchaseOrderMessages> purchaseOrderMessagesList = purchaseOrderMessagesRepository.findAll();
        assertThat(purchaseOrderMessagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseOrderMessages in Elasticsearch
        verify(mockPurchaseOrderMessagesSearchRepository, times(0)).save(purchaseOrderMessages);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderMessages() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);

        int databaseSizeBeforeDelete = purchaseOrderMessagesRepository.findAll().size();

        // Delete the purchaseOrderMessages
        restPurchaseOrderMessagesMockMvc.perform(delete("/api/purchase-order-messages/{id}", purchaseOrderMessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseOrderMessages> purchaseOrderMessagesList = purchaseOrderMessagesRepository.findAll();
        assertThat(purchaseOrderMessagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseOrderMessages in Elasticsearch
        verify(mockPurchaseOrderMessagesSearchRepository, times(1)).deleteById(purchaseOrderMessages.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseOrderMessages() throws Exception {
        // Initialize the database
        purchaseOrderMessagesRepository.saveAndFlush(purchaseOrderMessages);
        when(mockPurchaseOrderMessagesSearchRepository.search(queryStringQuery("id:" + purchaseOrderMessages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseOrderMessages), PageRequest.of(0, 1), 1));
        // Search the purchaseOrderMessages
        restPurchaseOrderMessagesMockMvc.perform(get("/api/_search/purchase-order-messages?query=id:" + purchaseOrderMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderMessages.class);
        PurchaseOrderMessages purchaseOrderMessages1 = new PurchaseOrderMessages();
        purchaseOrderMessages1.setId(1L);
        PurchaseOrderMessages purchaseOrderMessages2 = new PurchaseOrderMessages();
        purchaseOrderMessages2.setId(purchaseOrderMessages1.getId());
        assertThat(purchaseOrderMessages1).isEqualTo(purchaseOrderMessages2);
        purchaseOrderMessages2.setId(2L);
        assertThat(purchaseOrderMessages1).isNotEqualTo(purchaseOrderMessages2);
        purchaseOrderMessages1.setId(null);
        assertThat(purchaseOrderMessages1).isNotEqualTo(purchaseOrderMessages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderMessagesDTO.class);
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO1 = new PurchaseOrderMessagesDTO();
        purchaseOrderMessagesDTO1.setId(1L);
        PurchaseOrderMessagesDTO purchaseOrderMessagesDTO2 = new PurchaseOrderMessagesDTO();
        assertThat(purchaseOrderMessagesDTO1).isNotEqualTo(purchaseOrderMessagesDTO2);
        purchaseOrderMessagesDTO2.setId(purchaseOrderMessagesDTO1.getId());
        assertThat(purchaseOrderMessagesDTO1).isEqualTo(purchaseOrderMessagesDTO2);
        purchaseOrderMessagesDTO2.setId(2L);
        assertThat(purchaseOrderMessagesDTO1).isNotEqualTo(purchaseOrderMessagesDTO2);
        purchaseOrderMessagesDTO1.setId(null);
        assertThat(purchaseOrderMessagesDTO1).isNotEqualTo(purchaseOrderMessagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrderMessagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrderMessagesMapper.fromId(null)).isNull();
    }
}
