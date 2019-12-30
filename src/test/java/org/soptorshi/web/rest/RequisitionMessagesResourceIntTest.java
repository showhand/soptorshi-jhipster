package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.RequisitionMessages;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Requisition;
import org.soptorshi.repository.RequisitionMessagesRepository;
import org.soptorshi.repository.search.RequisitionMessagesSearchRepository;
import org.soptorshi.service.RequisitionMessagesService;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.mapper.RequisitionMessagesMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.RequisitionMessagesCriteria;
import org.soptorshi.service.RequisitionMessagesQueryService;

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
 * Test class for the RequisitionMessagesResource REST controller.
 *
 * @see RequisitionMessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class RequisitionMessagesResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMMENTED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENTED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RequisitionMessagesRepository requisitionMessagesRepository;

    @Autowired
    private RequisitionMessagesMapper requisitionMessagesMapper;

    @Autowired
    private RequisitionMessagesService requisitionMessagesService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.RequisitionMessagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequisitionMessagesSearchRepository mockRequisitionMessagesSearchRepository;

    @Autowired
    private RequisitionMessagesQueryService requisitionMessagesQueryService;

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

    private MockMvc restRequisitionMessagesMockMvc;

    private RequisitionMessages requisitionMessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequisitionMessagesResource requisitionMessagesResource = new RequisitionMessagesResource(requisitionMessagesService, requisitionMessagesQueryService);
        this.restRequisitionMessagesMockMvc = MockMvcBuilders.standaloneSetup(requisitionMessagesResource)
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
    public static RequisitionMessages createEntity(EntityManager em) {
        RequisitionMessages requisitionMessages = new RequisitionMessages()
            .comments(DEFAULT_COMMENTS)
            .commentedOn(DEFAULT_COMMENTED_ON);
        return requisitionMessages;
    }

    @Before
    public void initTest() {
        requisitionMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisitionMessages() throws Exception {
        int databaseSizeBeforeCreate = requisitionMessagesRepository.findAll().size();

        // Create the RequisitionMessages
        RequisitionMessagesDTO requisitionMessagesDTO = requisitionMessagesMapper.toDto(requisitionMessages);
        restRequisitionMessagesMockMvc.perform(post("/api/requisition-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the RequisitionMessages in the database
        List<RequisitionMessages> requisitionMessagesList = requisitionMessagesRepository.findAll();
        assertThat(requisitionMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        RequisitionMessages testRequisitionMessages = requisitionMessagesList.get(requisitionMessagesList.size() - 1);
        assertThat(testRequisitionMessages.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testRequisitionMessages.getCommentedOn()).isEqualTo(DEFAULT_COMMENTED_ON);

        // Validate the RequisitionMessages in Elasticsearch
        verify(mockRequisitionMessagesSearchRepository, times(1)).save(testRequisitionMessages);
    }

    @Test
    @Transactional
    public void createRequisitionMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionMessagesRepository.findAll().size();

        // Create the RequisitionMessages with an existing ID
        requisitionMessages.setId(1L);
        RequisitionMessagesDTO requisitionMessagesDTO = requisitionMessagesMapper.toDto(requisitionMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionMessagesMockMvc.perform(post("/api/requisition-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionMessages in the database
        List<RequisitionMessages> requisitionMessagesList = requisitionMessagesRepository.findAll();
        assertThat(requisitionMessagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the RequisitionMessages in Elasticsearch
        verify(mockRequisitionMessagesSearchRepository, times(0)).save(requisitionMessages);
    }

    @Test
    @Transactional
    public void getAllRequisitionMessages() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getRequisitionMessages() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get the requisitionMessages
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages/{id}", requisitionMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisitionMessages.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.commentedOn").value(DEFAULT_COMMENTED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommentedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList where commentedOn equals to DEFAULT_COMMENTED_ON
        defaultRequisitionMessagesShouldBeFound("commentedOn.equals=" + DEFAULT_COMMENTED_ON);

        // Get all the requisitionMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultRequisitionMessagesShouldNotBeFound("commentedOn.equals=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommentedOnIsInShouldWork() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList where commentedOn in DEFAULT_COMMENTED_ON or UPDATED_COMMENTED_ON
        defaultRequisitionMessagesShouldBeFound("commentedOn.in=" + DEFAULT_COMMENTED_ON + "," + UPDATED_COMMENTED_ON);

        // Get all the requisitionMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultRequisitionMessagesShouldNotBeFound("commentedOn.in=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommentedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList where commentedOn is not null
        defaultRequisitionMessagesShouldBeFound("commentedOn.specified=true");

        // Get all the requisitionMessagesList where commentedOn is null
        defaultRequisitionMessagesShouldNotBeFound("commentedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommentedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList where commentedOn greater than or equals to DEFAULT_COMMENTED_ON
        defaultRequisitionMessagesShouldBeFound("commentedOn.greaterOrEqualThan=" + DEFAULT_COMMENTED_ON);

        // Get all the requisitionMessagesList where commentedOn greater than or equals to UPDATED_COMMENTED_ON
        defaultRequisitionMessagesShouldNotBeFound("commentedOn.greaterOrEqualThan=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommentedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        // Get all the requisitionMessagesList where commentedOn less than or equals to DEFAULT_COMMENTED_ON
        defaultRequisitionMessagesShouldNotBeFound("commentedOn.lessThan=" + DEFAULT_COMMENTED_ON);

        // Get all the requisitionMessagesList where commentedOn less than or equals to UPDATED_COMMENTED_ON
        defaultRequisitionMessagesShouldBeFound("commentedOn.lessThan=" + UPDATED_COMMENTED_ON);
    }


    @Test
    @Transactional
    public void getAllRequisitionMessagesByCommenterIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee commenter = EmployeeResourceIntTest.createEntity(em);
        em.persist(commenter);
        em.flush();
        requisitionMessages.setCommenter(commenter);
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);
        Long commenterId = commenter.getId();

        // Get all the requisitionMessagesList where commenter equals to commenterId
        defaultRequisitionMessagesShouldBeFound("commenterId.equals=" + commenterId);

        // Get all the requisitionMessagesList where commenter equals to commenterId + 1
        defaultRequisitionMessagesShouldNotBeFound("commenterId.equals=" + (commenterId + 1));
    }


    @Test
    @Transactional
    public void getAllRequisitionMessagesByRequisitionIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisition = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisition);
        em.flush();
        requisitionMessages.setRequisition(requisition);
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);
        Long requisitionId = requisition.getId();

        // Get all the requisitionMessagesList where requisition equals to requisitionId
        defaultRequisitionMessagesShouldBeFound("requisitionId.equals=" + requisitionId);

        // Get all the requisitionMessagesList where requisition equals to requisitionId + 1
        defaultRequisitionMessagesShouldNotBeFound("requisitionId.equals=" + (requisitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRequisitionMessagesShouldBeFound(String filter) throws Exception {
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));

        // Check, that the count call also returns 1
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRequisitionMessagesShouldNotBeFound(String filter) throws Exception {
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRequisitionMessages() throws Exception {
        // Get the requisitionMessages
        restRequisitionMessagesMockMvc.perform(get("/api/requisition-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisitionMessages() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        int databaseSizeBeforeUpdate = requisitionMessagesRepository.findAll().size();

        // Update the requisitionMessages
        RequisitionMessages updatedRequisitionMessages = requisitionMessagesRepository.findById(requisitionMessages.getId()).get();
        // Disconnect from session so that the updates on updatedRequisitionMessages are not directly saved in db
        em.detach(updatedRequisitionMessages);
        updatedRequisitionMessages
            .comments(UPDATED_COMMENTS)
            .commentedOn(UPDATED_COMMENTED_ON);
        RequisitionMessagesDTO requisitionMessagesDTO = requisitionMessagesMapper.toDto(updatedRequisitionMessages);

        restRequisitionMessagesMockMvc.perform(put("/api/requisition-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the RequisitionMessages in the database
        List<RequisitionMessages> requisitionMessagesList = requisitionMessagesRepository.findAll();
        assertThat(requisitionMessagesList).hasSize(databaseSizeBeforeUpdate);
        RequisitionMessages testRequisitionMessages = requisitionMessagesList.get(requisitionMessagesList.size() - 1);
        assertThat(testRequisitionMessages.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testRequisitionMessages.getCommentedOn()).isEqualTo(UPDATED_COMMENTED_ON);

        // Validate the RequisitionMessages in Elasticsearch
        verify(mockRequisitionMessagesSearchRepository, times(1)).save(testRequisitionMessages);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisitionMessages() throws Exception {
        int databaseSizeBeforeUpdate = requisitionMessagesRepository.findAll().size();

        // Create the RequisitionMessages
        RequisitionMessagesDTO requisitionMessagesDTO = requisitionMessagesMapper.toDto(requisitionMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitionMessagesMockMvc.perform(put("/api/requisition-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequisitionMessages in the database
        List<RequisitionMessages> requisitionMessagesList = requisitionMessagesRepository.findAll();
        assertThat(requisitionMessagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RequisitionMessages in Elasticsearch
        verify(mockRequisitionMessagesSearchRepository, times(0)).save(requisitionMessages);
    }

    @Test
    @Transactional
    public void deleteRequisitionMessages() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);

        int databaseSizeBeforeDelete = requisitionMessagesRepository.findAll().size();

        // Delete the requisitionMessages
        restRequisitionMessagesMockMvc.perform(delete("/api/requisition-messages/{id}", requisitionMessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RequisitionMessages> requisitionMessagesList = requisitionMessagesRepository.findAll();
        assertThat(requisitionMessagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RequisitionMessages in Elasticsearch
        verify(mockRequisitionMessagesSearchRepository, times(1)).deleteById(requisitionMessages.getId());
    }

    @Test
    @Transactional
    public void searchRequisitionMessages() throws Exception {
        // Initialize the database
        requisitionMessagesRepository.saveAndFlush(requisitionMessages);
        when(mockRequisitionMessagesSearchRepository.search(queryStringQuery("id:" + requisitionMessages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requisitionMessages), PageRequest.of(0, 1), 1));
        // Search the requisitionMessages
        restRequisitionMessagesMockMvc.perform(get("/api/_search/requisition-messages?query=id:" + requisitionMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisitionMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionMessages.class);
        RequisitionMessages requisitionMessages1 = new RequisitionMessages();
        requisitionMessages1.setId(1L);
        RequisitionMessages requisitionMessages2 = new RequisitionMessages();
        requisitionMessages2.setId(requisitionMessages1.getId());
        assertThat(requisitionMessages1).isEqualTo(requisitionMessages2);
        requisitionMessages2.setId(2L);
        assertThat(requisitionMessages1).isNotEqualTo(requisitionMessages2);
        requisitionMessages1.setId(null);
        assertThat(requisitionMessages1).isNotEqualTo(requisitionMessages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionMessagesDTO.class);
        RequisitionMessagesDTO requisitionMessagesDTO1 = new RequisitionMessagesDTO();
        requisitionMessagesDTO1.setId(1L);
        RequisitionMessagesDTO requisitionMessagesDTO2 = new RequisitionMessagesDTO();
        assertThat(requisitionMessagesDTO1).isNotEqualTo(requisitionMessagesDTO2);
        requisitionMessagesDTO2.setId(requisitionMessagesDTO1.getId());
        assertThat(requisitionMessagesDTO1).isEqualTo(requisitionMessagesDTO2);
        requisitionMessagesDTO2.setId(2L);
        assertThat(requisitionMessagesDTO1).isNotEqualTo(requisitionMessagesDTO2);
        requisitionMessagesDTO1.setId(null);
        assertThat(requisitionMessagesDTO1).isNotEqualTo(requisitionMessagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requisitionMessagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requisitionMessagesMapper.fromId(null)).isNull();
    }
}
