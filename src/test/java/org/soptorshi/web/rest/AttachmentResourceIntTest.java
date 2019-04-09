package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Attachment;
import org.soptorshi.domain.AcademicInformation;
import org.soptorshi.domain.TrainingInformation;
import org.soptorshi.domain.ExperienceInformation;
import org.soptorshi.repository.AttachmentRepository;
import org.soptorshi.repository.search.AttachmentSearchRepository;
import org.soptorshi.service.AttachmentService;
import org.soptorshi.service.dto.AttachmentDTO;
import org.soptorshi.service.mapper.AttachmentMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.AttachmentCriteria;
import org.soptorshi.service.AttachmentQueryService;

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
 * Test class for the AttachmentResource REST controller.
 *
 * @see AttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AttachmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private AttachmentService attachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private AttachmentSearchRepository mockAttachmentSearchRepository;

    @Autowired
    private AttachmentQueryService attachmentQueryService;

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

    private MockMvc restAttachmentMockMvc;

    private Attachment attachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttachmentResource attachmentResource = new AttachmentResource(attachmentService, attachmentQueryService);
        this.restAttachmentMockMvc = MockMvcBuilders.standaloneSetup(attachmentResource)
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
    public static Attachment createEntity(EntityManager em) {
        Attachment attachment = new Attachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return attachment;
    }

    @Before
    public void initTest() {
        attachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachment() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate + 1);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the Attachment in Elasticsearch
        verify(mockAttachmentSearchRepository, times(1)).save(testAttachment);
    }

    @Test
    @Transactional
    public void createAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment with an existing ID
        attachment.setId(1L);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Attachment in Elasticsearch
        verify(mockAttachmentSearchRepository, times(0)).save(attachment);
    }

    @Test
    @Transactional
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getAllAttachmentsByAcademicInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        AcademicInformation academicInformation = AcademicInformationResourceIntTest.createEntity(em);
        em.persist(academicInformation);
        em.flush();
        attachment.setAcademicInformation(academicInformation);
        attachmentRepository.saveAndFlush(attachment);
        Long academicInformationId = academicInformation.getId();

        // Get all the attachmentList where academicInformation equals to academicInformationId
        defaultAttachmentShouldBeFound("academicInformationId.equals=" + academicInformationId);

        // Get all the attachmentList where academicInformation equals to academicInformationId + 1
        defaultAttachmentShouldNotBeFound("academicInformationId.equals=" + (academicInformationId + 1));
    }


    @Test
    @Transactional
    public void getAllAttachmentsByTrainingInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        TrainingInformation trainingInformation = TrainingInformationResourceIntTest.createEntity(em);
        em.persist(trainingInformation);
        em.flush();
        attachment.setTrainingInformation(trainingInformation);
        attachmentRepository.saveAndFlush(attachment);
        Long trainingInformationId = trainingInformation.getId();

        // Get all the attachmentList where trainingInformation equals to trainingInformationId
        defaultAttachmentShouldBeFound("trainingInformationId.equals=" + trainingInformationId);

        // Get all the attachmentList where trainingInformation equals to trainingInformationId + 1
        defaultAttachmentShouldNotBeFound("trainingInformationId.equals=" + (trainingInformationId + 1));
    }


    @Test
    @Transactional
    public void getAllAttachmentsByExperienceInformationIsEqualToSomething() throws Exception {
        // Initialize the database
        ExperienceInformation experienceInformation = ExperienceInformationResourceIntTest.createEntity(em);
        em.persist(experienceInformation);
        em.flush();
        attachment.setExperienceInformation(experienceInformation);
        attachmentRepository.saveAndFlush(attachment);
        Long experienceInformationId = experienceInformation.getId();

        // Get all the attachmentList where experienceInformation equals to experienceInformationId
        defaultAttachmentShouldBeFound("experienceInformationId.equals=" + experienceInformationId);

        // Get all the attachmentList where experienceInformation equals to experienceInformationId + 1
        defaultAttachmentShouldNotBeFound("experienceInformationId.equals=" + (experienceInformationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAttachmentShouldBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAttachmentShouldNotBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAttachment() throws Exception {
        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findById(attachment.getId()).get();
        // Disconnect from session so that the updates on updatedAttachment are not directly saved in db
        em.detach(updatedAttachment);
        updatedAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(updatedAttachment);

        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isOk());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the Attachment in Elasticsearch
        verify(mockAttachmentSearchRepository, times(1)).save(testAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachment() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Create the Attachment
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Attachment in Elasticsearch
        verify(mockAttachmentSearchRepository, times(0)).save(attachment);
    }

    @Test
    @Transactional
    public void deleteAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        int databaseSizeBeforeDelete = attachmentRepository.findAll().size();

        // Delete the attachment
        restAttachmentMockMvc.perform(delete("/api/attachments/{id}", attachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Attachment in Elasticsearch
        verify(mockAttachmentSearchRepository, times(1)).deleteById(attachment.getId());
    }

    @Test
    @Transactional
    public void searchAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);
        when(mockAttachmentSearchRepository.search(queryStringQuery("id:" + attachment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attachment), PageRequest.of(0, 1), 1));
        // Search the attachment
        restAttachmentMockMvc.perform(get("/api/_search/attachments?query=id:" + attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attachment.class);
        Attachment attachment1 = new Attachment();
        attachment1.setId(1L);
        Attachment attachment2 = new Attachment();
        attachment2.setId(attachment1.getId());
        assertThat(attachment1).isEqualTo(attachment2);
        attachment2.setId(2L);
        assertThat(attachment1).isNotEqualTo(attachment2);
        attachment1.setId(null);
        assertThat(attachment1).isNotEqualTo(attachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentDTO.class);
        AttachmentDTO attachmentDTO1 = new AttachmentDTO();
        attachmentDTO1.setId(1L);
        AttachmentDTO attachmentDTO2 = new AttachmentDTO();
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO2.setId(attachmentDTO1.getId());
        assertThat(attachmentDTO1).isEqualTo(attachmentDTO2);
        attachmentDTO2.setId(2L);
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
        attachmentDTO1.setId(null);
        assertThat(attachmentDTO1).isNotEqualTo(attachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attachmentMapper.fromId(null)).isNull();
    }
}
