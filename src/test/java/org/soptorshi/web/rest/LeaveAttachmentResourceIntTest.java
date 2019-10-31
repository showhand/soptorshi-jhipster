package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.domain.LeaveAttachment;
import org.soptorshi.repository.LeaveAttachmentRepository;
import org.soptorshi.repository.search.LeaveAttachmentSearchRepository;
import org.soptorshi.service.LeaveAttachmentQueryService;
import org.soptorshi.service.LeaveAttachmentService;
import org.soptorshi.service.dto.LeaveAttachmentDTO;
import org.soptorshi.service.mapper.LeaveAttachmentMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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
 * Test class for the LeaveAttachmentResource REST controller.
 *
 * @see LeaveAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class LeaveAttachmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private LeaveAttachmentRepository leaveAttachmentRepository;

    @Autowired
    private LeaveAttachmentMapper leaveAttachmentMapper;

    @Autowired
    private LeaveAttachmentService leaveAttachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.LeaveAttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeaveAttachmentSearchRepository mockLeaveAttachmentSearchRepository;

    @Autowired
    private LeaveAttachmentQueryService leaveAttachmentQueryService;

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

    private MockMvc restLeaveAttachmentMockMvc;

    private LeaveAttachment leaveAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveAttachmentResource leaveAttachmentResource = new LeaveAttachmentResource(leaveAttachmentService, leaveAttachmentQueryService);
        this.restLeaveAttachmentMockMvc = MockMvcBuilders.standaloneSetup(leaveAttachmentResource)
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
    public static LeaveAttachment createEntity(EntityManager em) {
        LeaveAttachment leaveAttachment = new LeaveAttachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return leaveAttachment;
    }

    @Before
    public void initTest() {
        leaveAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveAttachment() throws Exception {
        int databaseSizeBeforeCreate = leaveAttachmentRepository.findAll().size();

        // Create the LeaveAttachment
        LeaveAttachmentDTO leaveAttachmentDTO = leaveAttachmentMapper.toDto(leaveAttachment);
        restLeaveAttachmentMockMvc.perform(post("/api/leave-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveAttachment in the database
        List<LeaveAttachment> leaveAttachmentList = leaveAttachmentRepository.findAll();
        assertThat(leaveAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveAttachment testLeaveAttachment = leaveAttachmentList.get(leaveAttachmentList.size() - 1);
        assertThat(testLeaveAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testLeaveAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the LeaveAttachment in Elasticsearch
        verify(mockLeaveAttachmentSearchRepository, times(1)).save(testLeaveAttachment);
    }

    @Test
    @Transactional
    public void createLeaveAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveAttachmentRepository.findAll().size();

        // Create the LeaveAttachment with an existing ID
        leaveAttachment.setId(1L);
        LeaveAttachmentDTO leaveAttachmentDTO = leaveAttachmentMapper.toDto(leaveAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveAttachmentMockMvc.perform(post("/api/leave-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveAttachment in the database
        List<LeaveAttachment> leaveAttachmentList = leaveAttachmentRepository.findAll();
        assertThat(leaveAttachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeaveAttachment in Elasticsearch
        verify(mockLeaveAttachmentSearchRepository, times(0)).save(leaveAttachment);
    }

    @Test
    @Transactional
    public void getAllLeaveAttachments() throws Exception {
        // Initialize the database
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);

        // Get all the leaveAttachmentList
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getLeaveAttachment() throws Exception {
        // Initialize the database
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);

        // Get the leaveAttachment
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments/{id}", leaveAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaveAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getAllLeaveAttachmentsByLeaveApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        LeaveApplication leaveApplication = LeaveApplicationResourceIntTest.createEntity(em);
        em.persist(leaveApplication);
        em.flush();
        leaveAttachment.setLeaveApplication(leaveApplication);
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);
        Long leaveApplicationId = leaveApplication.getId();

        // Get all the leaveAttachmentList where leaveApplication equals to leaveApplicationId
        defaultLeaveAttachmentShouldBeFound("leaveApplicationId.equals=" + leaveApplicationId);

        // Get all the leaveAttachmentList where leaveApplication equals to leaveApplicationId + 1
        defaultLeaveAttachmentShouldNotBeFound("leaveApplicationId.equals=" + (leaveApplicationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLeaveAttachmentShouldBeFound(String filter) throws Exception {
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLeaveAttachmentShouldNotBeFound(String filter) throws Exception {
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLeaveAttachment() throws Exception {
        // Get the leaveAttachment
        restLeaveAttachmentMockMvc.perform(get("/api/leave-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveAttachment() throws Exception {
        // Initialize the database
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);

        int databaseSizeBeforeUpdate = leaveAttachmentRepository.findAll().size();

        // Update the leaveAttachment
        LeaveAttachment updatedLeaveAttachment = leaveAttachmentRepository.findById(leaveAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveAttachment are not directly saved in db
        em.detach(updatedLeaveAttachment);
        updatedLeaveAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        LeaveAttachmentDTO leaveAttachmentDTO = leaveAttachmentMapper.toDto(updatedLeaveAttachment);

        restLeaveAttachmentMockMvc.perform(put("/api/leave-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveAttachment in the database
        List<LeaveAttachment> leaveAttachmentList = leaveAttachmentRepository.findAll();
        assertThat(leaveAttachmentList).hasSize(databaseSizeBeforeUpdate);
        LeaveAttachment testLeaveAttachment = leaveAttachmentList.get(leaveAttachmentList.size() - 1);
        assertThat(testLeaveAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testLeaveAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the LeaveAttachment in Elasticsearch
        verify(mockLeaveAttachmentSearchRepository, times(1)).save(testLeaveAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveAttachment() throws Exception {
        int databaseSizeBeforeUpdate = leaveAttachmentRepository.findAll().size();

        // Create the LeaveAttachment
        LeaveAttachmentDTO leaveAttachmentDTO = leaveAttachmentMapper.toDto(leaveAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveAttachmentMockMvc.perform(put("/api/leave-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveAttachment in the database
        List<LeaveAttachment> leaveAttachmentList = leaveAttachmentRepository.findAll();
        assertThat(leaveAttachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeaveAttachment in Elasticsearch
        verify(mockLeaveAttachmentSearchRepository, times(0)).save(leaveAttachment);
    }

    @Test
    @Transactional
    public void deleteLeaveAttachment() throws Exception {
        // Initialize the database
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);

        int databaseSizeBeforeDelete = leaveAttachmentRepository.findAll().size();

        // Delete the leaveAttachment
        restLeaveAttachmentMockMvc.perform(delete("/api/leave-attachments/{id}", leaveAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeaveAttachment> leaveAttachmentList = leaveAttachmentRepository.findAll();
        assertThat(leaveAttachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeaveAttachment in Elasticsearch
        verify(mockLeaveAttachmentSearchRepository, times(1)).deleteById(leaveAttachment.getId());
    }

    @Test
    @Transactional
    public void searchLeaveAttachment() throws Exception {
        // Initialize the database
        leaveAttachmentRepository.saveAndFlush(leaveAttachment);
        when(mockLeaveAttachmentSearchRepository.search(queryStringQuery("id:" + leaveAttachment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leaveAttachment), PageRequest.of(0, 1), 1));
        // Search the leaveAttachment
        restLeaveAttachmentMockMvc.perform(get("/api/_search/leave-attachments?query=id:" + leaveAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveAttachment.class);
        LeaveAttachment leaveAttachment1 = new LeaveAttachment();
        leaveAttachment1.setId(1L);
        LeaveAttachment leaveAttachment2 = new LeaveAttachment();
        leaveAttachment2.setId(leaveAttachment1.getId());
        assertThat(leaveAttachment1).isEqualTo(leaveAttachment2);
        leaveAttachment2.setId(2L);
        assertThat(leaveAttachment1).isNotEqualTo(leaveAttachment2);
        leaveAttachment1.setId(null);
        assertThat(leaveAttachment1).isNotEqualTo(leaveAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveAttachmentDTO.class);
        LeaveAttachmentDTO leaveAttachmentDTO1 = new LeaveAttachmentDTO();
        leaveAttachmentDTO1.setId(1L);
        LeaveAttachmentDTO leaveAttachmentDTO2 = new LeaveAttachmentDTO();
        assertThat(leaveAttachmentDTO1).isNotEqualTo(leaveAttachmentDTO2);
        leaveAttachmentDTO2.setId(leaveAttachmentDTO1.getId());
        assertThat(leaveAttachmentDTO1).isEqualTo(leaveAttachmentDTO2);
        leaveAttachmentDTO2.setId(2L);
        assertThat(leaveAttachmentDTO1).isNotEqualTo(leaveAttachmentDTO2);
        leaveAttachmentDTO1.setId(null);
        assertThat(leaveAttachmentDTO1).isNotEqualTo(leaveAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaveAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaveAttachmentMapper.fromId(null)).isNull();
    }
}
