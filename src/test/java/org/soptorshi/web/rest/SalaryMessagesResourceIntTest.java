package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.SalaryMessages;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.repository.SalaryMessagesRepository;
import org.soptorshi.repository.search.SalaryMessagesSearchRepository;
import org.soptorshi.service.SalaryMessagesService;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.mapper.SalaryMessagesMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SalaryMessagesCriteria;
import org.soptorshi.service.SalaryMessagesQueryService;

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
 * Test class for the SalaryMessagesResource REST controller.
 *
 * @see SalaryMessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SalaryMessagesResourceIntTest {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMMENTED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMENTED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SalaryMessagesRepository salaryMessagesRepository;

    @Autowired
    private SalaryMessagesMapper salaryMessagesMapper;

    @Autowired
    private SalaryMessagesService salaryMessagesService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SalaryMessagesSearchRepositoryMockConfiguration
     */
    @Autowired
    private SalaryMessagesSearchRepository mockSalaryMessagesSearchRepository;

    @Autowired
    private SalaryMessagesQueryService salaryMessagesQueryService;

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

    private MockMvc restSalaryMessagesMockMvc;

    private SalaryMessages salaryMessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaryMessagesResource salaryMessagesResource = new SalaryMessagesResource(salaryMessagesService, salaryMessagesQueryService);
        this.restSalaryMessagesMockMvc = MockMvcBuilders.standaloneSetup(salaryMessagesResource)
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
    public static SalaryMessages createEntity(EntityManager em) {
        SalaryMessages salaryMessages = new SalaryMessages()
            .comments(DEFAULT_COMMENTS)
            .commentedOn(DEFAULT_COMMENTED_ON);
        return salaryMessages;
    }

    @Before
    public void initTest() {
        salaryMessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalaryMessages() throws Exception {
        int databaseSizeBeforeCreate = salaryMessagesRepository.findAll().size();

        // Create the SalaryMessages
        SalaryMessagesDTO salaryMessagesDTO = salaryMessagesMapper.toDto(salaryMessages);
        restSalaryMessagesMockMvc.perform(post("/api/salary-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryMessagesDTO)))
            .andExpect(status().isCreated());

        // Validate the SalaryMessages in the database
        List<SalaryMessages> salaryMessagesList = salaryMessagesRepository.findAll();
        assertThat(salaryMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        SalaryMessages testSalaryMessages = salaryMessagesList.get(salaryMessagesList.size() - 1);
        assertThat(testSalaryMessages.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testSalaryMessages.getCommentedOn()).isEqualTo(DEFAULT_COMMENTED_ON);

        // Validate the SalaryMessages in Elasticsearch
        verify(mockSalaryMessagesSearchRepository, times(1)).save(testSalaryMessages);
    }

    @Test
    @Transactional
    public void createSalaryMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaryMessagesRepository.findAll().size();

        // Create the SalaryMessages with an existing ID
        salaryMessages.setId(1L);
        SalaryMessagesDTO salaryMessagesDTO = salaryMessagesMapper.toDto(salaryMessages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaryMessagesMockMvc.perform(post("/api/salary-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalaryMessages in the database
        List<SalaryMessages> salaryMessagesList = salaryMessagesRepository.findAll();
        assertThat(salaryMessagesList).hasSize(databaseSizeBeforeCreate);

        // Validate the SalaryMessages in Elasticsearch
        verify(mockSalaryMessagesSearchRepository, times(0)).save(salaryMessages);
    }

    @Test
    @Transactional
    public void getAllSalaryMessages() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSalaryMessages() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get the salaryMessages
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages/{id}", salaryMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salaryMessages.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.commentedOn").value(DEFAULT_COMMENTED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSalaryMessagesByCommentedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList where commentedOn equals to DEFAULT_COMMENTED_ON
        defaultSalaryMessagesShouldBeFound("commentedOn.equals=" + DEFAULT_COMMENTED_ON);

        // Get all the salaryMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultSalaryMessagesShouldNotBeFound("commentedOn.equals=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryMessagesByCommentedOnIsInShouldWork() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList where commentedOn in DEFAULT_COMMENTED_ON or UPDATED_COMMENTED_ON
        defaultSalaryMessagesShouldBeFound("commentedOn.in=" + DEFAULT_COMMENTED_ON + "," + UPDATED_COMMENTED_ON);

        // Get all the salaryMessagesList where commentedOn equals to UPDATED_COMMENTED_ON
        defaultSalaryMessagesShouldNotBeFound("commentedOn.in=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryMessagesByCommentedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList where commentedOn is not null
        defaultSalaryMessagesShouldBeFound("commentedOn.specified=true");

        // Get all the salaryMessagesList where commentedOn is null
        defaultSalaryMessagesShouldNotBeFound("commentedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalaryMessagesByCommentedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList where commentedOn greater than or equals to DEFAULT_COMMENTED_ON
        defaultSalaryMessagesShouldBeFound("commentedOn.greaterOrEqualThan=" + DEFAULT_COMMENTED_ON);

        // Get all the salaryMessagesList where commentedOn greater than or equals to UPDATED_COMMENTED_ON
        defaultSalaryMessagesShouldNotBeFound("commentedOn.greaterOrEqualThan=" + UPDATED_COMMENTED_ON);
    }

    @Test
    @Transactional
    public void getAllSalaryMessagesByCommentedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        // Get all the salaryMessagesList where commentedOn less than or equals to DEFAULT_COMMENTED_ON
        defaultSalaryMessagesShouldNotBeFound("commentedOn.lessThan=" + DEFAULT_COMMENTED_ON);

        // Get all the salaryMessagesList where commentedOn less than or equals to UPDATED_COMMENTED_ON
        defaultSalaryMessagesShouldBeFound("commentedOn.lessThan=" + UPDATED_COMMENTED_ON);
    }


    @Test
    @Transactional
    public void getAllSalaryMessagesByCommenterIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee commenter = EmployeeResourceIntTest.createEntity(em);
        em.persist(commenter);
        em.flush();
        salaryMessages.setCommenter(commenter);
        salaryMessagesRepository.saveAndFlush(salaryMessages);
        Long commenterId = commenter.getId();

        // Get all the salaryMessagesList where commenter equals to commenterId
        defaultSalaryMessagesShouldBeFound("commenterId.equals=" + commenterId);

        // Get all the salaryMessagesList where commenter equals to commenterId + 1
        defaultSalaryMessagesShouldNotBeFound("commenterId.equals=" + (commenterId + 1));
    }


    @Test
    @Transactional
    public void getAllSalaryMessagesByMonthlySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        MonthlySalary monthlySalary = MonthlySalaryResourceIntTest.createEntity(em);
        em.persist(monthlySalary);
        em.flush();
        salaryMessages.setMonthlySalary(monthlySalary);
        salaryMessagesRepository.saveAndFlush(salaryMessages);
        Long monthlySalaryId = monthlySalary.getId();

        // Get all the salaryMessagesList where monthlySalary equals to monthlySalaryId
        defaultSalaryMessagesShouldBeFound("monthlySalaryId.equals=" + monthlySalaryId);

        // Get all the salaryMessagesList where monthlySalary equals to monthlySalaryId + 1
        defaultSalaryMessagesShouldNotBeFound("monthlySalaryId.equals=" + (monthlySalaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSalaryMessagesShouldBeFound(String filter) throws Exception {
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));

        // Check, that the count call also returns 1
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSalaryMessagesShouldNotBeFound(String filter) throws Exception {
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSalaryMessages() throws Exception {
        // Get the salaryMessages
        restSalaryMessagesMockMvc.perform(get("/api/salary-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalaryMessages() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        int databaseSizeBeforeUpdate = salaryMessagesRepository.findAll().size();

        // Update the salaryMessages
        SalaryMessages updatedSalaryMessages = salaryMessagesRepository.findById(salaryMessages.getId()).get();
        // Disconnect from session so that the updates on updatedSalaryMessages are not directly saved in db
        em.detach(updatedSalaryMessages);
        updatedSalaryMessages
            .comments(UPDATED_COMMENTS)
            .commentedOn(UPDATED_COMMENTED_ON);
        SalaryMessagesDTO salaryMessagesDTO = salaryMessagesMapper.toDto(updatedSalaryMessages);

        restSalaryMessagesMockMvc.perform(put("/api/salary-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryMessagesDTO)))
            .andExpect(status().isOk());

        // Validate the SalaryMessages in the database
        List<SalaryMessages> salaryMessagesList = salaryMessagesRepository.findAll();
        assertThat(salaryMessagesList).hasSize(databaseSizeBeforeUpdate);
        SalaryMessages testSalaryMessages = salaryMessagesList.get(salaryMessagesList.size() - 1);
        assertThat(testSalaryMessages.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testSalaryMessages.getCommentedOn()).isEqualTo(UPDATED_COMMENTED_ON);

        // Validate the SalaryMessages in Elasticsearch
        verify(mockSalaryMessagesSearchRepository, times(1)).save(testSalaryMessages);
    }

    @Test
    @Transactional
    public void updateNonExistingSalaryMessages() throws Exception {
        int databaseSizeBeforeUpdate = salaryMessagesRepository.findAll().size();

        // Create the SalaryMessages
        SalaryMessagesDTO salaryMessagesDTO = salaryMessagesMapper.toDto(salaryMessages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaryMessagesMockMvc.perform(put("/api/salary-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryMessagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalaryMessages in the database
        List<SalaryMessages> salaryMessagesList = salaryMessagesRepository.findAll();
        assertThat(salaryMessagesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SalaryMessages in Elasticsearch
        verify(mockSalaryMessagesSearchRepository, times(0)).save(salaryMessages);
    }

    @Test
    @Transactional
    public void deleteSalaryMessages() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);

        int databaseSizeBeforeDelete = salaryMessagesRepository.findAll().size();

        // Delete the salaryMessages
        restSalaryMessagesMockMvc.perform(delete("/api/salary-messages/{id}", salaryMessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalaryMessages> salaryMessagesList = salaryMessagesRepository.findAll();
        assertThat(salaryMessagesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SalaryMessages in Elasticsearch
        verify(mockSalaryMessagesSearchRepository, times(1)).deleteById(salaryMessages.getId());
    }

    @Test
    @Transactional
    public void searchSalaryMessages() throws Exception {
        // Initialize the database
        salaryMessagesRepository.saveAndFlush(salaryMessages);
        when(mockSalaryMessagesSearchRepository.search(queryStringQuery("id:" + salaryMessages.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(salaryMessages), PageRequest.of(0, 1), 1));
        // Search the salaryMessages
        restSalaryMessagesMockMvc.perform(get("/api/_search/salary-messages?query=id:" + salaryMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaryMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].commentedOn").value(hasItem(DEFAULT_COMMENTED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaryMessages.class);
        SalaryMessages salaryMessages1 = new SalaryMessages();
        salaryMessages1.setId(1L);
        SalaryMessages salaryMessages2 = new SalaryMessages();
        salaryMessages2.setId(salaryMessages1.getId());
        assertThat(salaryMessages1).isEqualTo(salaryMessages2);
        salaryMessages2.setId(2L);
        assertThat(salaryMessages1).isNotEqualTo(salaryMessages2);
        salaryMessages1.setId(null);
        assertThat(salaryMessages1).isNotEqualTo(salaryMessages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaryMessagesDTO.class);
        SalaryMessagesDTO salaryMessagesDTO1 = new SalaryMessagesDTO();
        salaryMessagesDTO1.setId(1L);
        SalaryMessagesDTO salaryMessagesDTO2 = new SalaryMessagesDTO();
        assertThat(salaryMessagesDTO1).isNotEqualTo(salaryMessagesDTO2);
        salaryMessagesDTO2.setId(salaryMessagesDTO1.getId());
        assertThat(salaryMessagesDTO1).isEqualTo(salaryMessagesDTO2);
        salaryMessagesDTO2.setId(2L);
        assertThat(salaryMessagesDTO1).isNotEqualTo(salaryMessagesDTO2);
        salaryMessagesDTO1.setId(null);
        assertThat(salaryMessagesDTO1).isNotEqualTo(salaryMessagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salaryMessagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salaryMessagesMapper.fromId(null)).isNull();
    }
}
