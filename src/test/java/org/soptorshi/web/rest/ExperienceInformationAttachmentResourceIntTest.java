package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ExperienceInformationAttachment;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.ExperienceInformationAttachmentRepository;
import org.soptorshi.repository.search.ExperienceInformationAttachmentSearchRepository;
import org.soptorshi.service.ExperienceInformationAttachmentService;
import org.soptorshi.service.dto.ExperienceInformationAttachmentDTO;
import org.soptorshi.service.mapper.ExperienceInformationAttachmentMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Test class for the ExperienceInformationAttachmentResource REST controller.
 *
 * @see ExperienceInformationAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ExperienceInformationAttachmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private ExperienceInformationAttachmentRepository experienceInformationAttachmentRepository;

    @Autowired
    private ExperienceInformationAttachmentMapper experienceInformationAttachmentMapper;

    @Autowired
    private ExperienceInformationAttachmentService experienceInformationAttachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ExperienceInformationAttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExperienceInformationAttachmentSearchRepository mockExperienceInformationAttachmentSearchRepository;

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

    private MockMvc restExperienceInformationAttachmentMockMvc;

    private ExperienceInformationAttachment experienceInformationAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceInformationAttachmentResource experienceInformationAttachmentResource = new ExperienceInformationAttachmentResource(experienceInformationAttachmentService);
        this.restExperienceInformationAttachmentMockMvc = MockMvcBuilders.standaloneSetup(experienceInformationAttachmentResource)
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
    public static ExperienceInformationAttachment createEntity(EntityManager em) {
        ExperienceInformationAttachment experienceInformationAttachment = new ExperienceInformationAttachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        experienceInformationAttachment.setEmployee(employee);
        return experienceInformationAttachment;
    }

    @Before
    public void initTest() {
        experienceInformationAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperienceInformationAttachment() throws Exception {
        int databaseSizeBeforeCreate = experienceInformationAttachmentRepository.findAll().size();

        // Create the ExperienceInformationAttachment
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO = experienceInformationAttachmentMapper.toDto(experienceInformationAttachment);
        restExperienceInformationAttachmentMockMvc.perform(post("/api/experience-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the ExperienceInformationAttachment in the database
        List<ExperienceInformationAttachment> experienceInformationAttachmentList = experienceInformationAttachmentRepository.findAll();
        assertThat(experienceInformationAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        ExperienceInformationAttachment testExperienceInformationAttachment = experienceInformationAttachmentList.get(experienceInformationAttachmentList.size() - 1);
        assertThat(testExperienceInformationAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testExperienceInformationAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the ExperienceInformationAttachment in Elasticsearch
        verify(mockExperienceInformationAttachmentSearchRepository, times(1)).save(testExperienceInformationAttachment);
    }

    @Test
    @Transactional
    public void createExperienceInformationAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceInformationAttachmentRepository.findAll().size();

        // Create the ExperienceInformationAttachment with an existing ID
        experienceInformationAttachment.setId(1L);
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO = experienceInformationAttachmentMapper.toDto(experienceInformationAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceInformationAttachmentMockMvc.perform(post("/api/experience-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceInformationAttachment in the database
        List<ExperienceInformationAttachment> experienceInformationAttachmentList = experienceInformationAttachmentRepository.findAll();
        assertThat(experienceInformationAttachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExperienceInformationAttachment in Elasticsearch
        verify(mockExperienceInformationAttachmentSearchRepository, times(0)).save(experienceInformationAttachment);
    }

    @Test
    @Transactional
    public void getAllExperienceInformationAttachments() throws Exception {
        // Initialize the database
        experienceInformationAttachmentRepository.saveAndFlush(experienceInformationAttachment);

        // Get all the experienceInformationAttachmentList
        restExperienceInformationAttachmentMockMvc.perform(get("/api/experience-information-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getExperienceInformationAttachment() throws Exception {
        // Initialize the database
        experienceInformationAttachmentRepository.saveAndFlush(experienceInformationAttachment);

        // Get the experienceInformationAttachment
        restExperienceInformationAttachmentMockMvc.perform(get("/api/experience-information-attachments/{id}", experienceInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experienceInformationAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingExperienceInformationAttachment() throws Exception {
        // Get the experienceInformationAttachment
        restExperienceInformationAttachmentMockMvc.perform(get("/api/experience-information-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperienceInformationAttachment() throws Exception {
        // Initialize the database
        experienceInformationAttachmentRepository.saveAndFlush(experienceInformationAttachment);

        int databaseSizeBeforeUpdate = experienceInformationAttachmentRepository.findAll().size();

        // Update the experienceInformationAttachment
        ExperienceInformationAttachment updatedExperienceInformationAttachment = experienceInformationAttachmentRepository.findById(experienceInformationAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedExperienceInformationAttachment are not directly saved in db
        em.detach(updatedExperienceInformationAttachment);
        updatedExperienceInformationAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO = experienceInformationAttachmentMapper.toDto(updatedExperienceInformationAttachment);

        restExperienceInformationAttachmentMockMvc.perform(put("/api/experience-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the ExperienceInformationAttachment in the database
        List<ExperienceInformationAttachment> experienceInformationAttachmentList = experienceInformationAttachmentRepository.findAll();
        assertThat(experienceInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);
        ExperienceInformationAttachment testExperienceInformationAttachment = experienceInformationAttachmentList.get(experienceInformationAttachmentList.size() - 1);
        assertThat(testExperienceInformationAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testExperienceInformationAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the ExperienceInformationAttachment in Elasticsearch
        verify(mockExperienceInformationAttachmentSearchRepository, times(1)).save(testExperienceInformationAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingExperienceInformationAttachment() throws Exception {
        int databaseSizeBeforeUpdate = experienceInformationAttachmentRepository.findAll().size();

        // Create the ExperienceInformationAttachment
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO = experienceInformationAttachmentMapper.toDto(experienceInformationAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceInformationAttachmentMockMvc.perform(put("/api/experience-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceInformationAttachment in the database
        List<ExperienceInformationAttachment> experienceInformationAttachmentList = experienceInformationAttachmentRepository.findAll();
        assertThat(experienceInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExperienceInformationAttachment in Elasticsearch
        verify(mockExperienceInformationAttachmentSearchRepository, times(0)).save(experienceInformationAttachment);
    }

    @Test
    @Transactional
    public void deleteExperienceInformationAttachment() throws Exception {
        // Initialize the database
        experienceInformationAttachmentRepository.saveAndFlush(experienceInformationAttachment);

        int databaseSizeBeforeDelete = experienceInformationAttachmentRepository.findAll().size();

        // Delete the experienceInformationAttachment
        restExperienceInformationAttachmentMockMvc.perform(delete("/api/experience-information-attachments/{id}", experienceInformationAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExperienceInformationAttachment> experienceInformationAttachmentList = experienceInformationAttachmentRepository.findAll();
        assertThat(experienceInformationAttachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExperienceInformationAttachment in Elasticsearch
        verify(mockExperienceInformationAttachmentSearchRepository, times(1)).deleteById(experienceInformationAttachment.getId());
    }

    @Test
    @Transactional
    public void searchExperienceInformationAttachment() throws Exception {
        // Initialize the database
        experienceInformationAttachmentRepository.saveAndFlush(experienceInformationAttachment);
        when(mockExperienceInformationAttachmentSearchRepository.search(queryStringQuery("id:" + experienceInformationAttachment.getId())))
            .thenReturn(Collections.singletonList(experienceInformationAttachment));
        // Search the experienceInformationAttachment
        restExperienceInformationAttachmentMockMvc.perform(get("/api/_search/experience-information-attachments?query=id:" + experienceInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceInformationAttachment.class);
        ExperienceInformationAttachment experienceInformationAttachment1 = new ExperienceInformationAttachment();
        experienceInformationAttachment1.setId(1L);
        ExperienceInformationAttachment experienceInformationAttachment2 = new ExperienceInformationAttachment();
        experienceInformationAttachment2.setId(experienceInformationAttachment1.getId());
        assertThat(experienceInformationAttachment1).isEqualTo(experienceInformationAttachment2);
        experienceInformationAttachment2.setId(2L);
        assertThat(experienceInformationAttachment1).isNotEqualTo(experienceInformationAttachment2);
        experienceInformationAttachment1.setId(null);
        assertThat(experienceInformationAttachment1).isNotEqualTo(experienceInformationAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceInformationAttachmentDTO.class);
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO1 = new ExperienceInformationAttachmentDTO();
        experienceInformationAttachmentDTO1.setId(1L);
        ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO2 = new ExperienceInformationAttachmentDTO();
        assertThat(experienceInformationAttachmentDTO1).isNotEqualTo(experienceInformationAttachmentDTO2);
        experienceInformationAttachmentDTO2.setId(experienceInformationAttachmentDTO1.getId());
        assertThat(experienceInformationAttachmentDTO1).isEqualTo(experienceInformationAttachmentDTO2);
        experienceInformationAttachmentDTO2.setId(2L);
        assertThat(experienceInformationAttachmentDTO1).isNotEqualTo(experienceInformationAttachmentDTO2);
        experienceInformationAttachmentDTO1.setId(null);
        assertThat(experienceInformationAttachmentDTO1).isNotEqualTo(experienceInformationAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(experienceInformationAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(experienceInformationAttachmentMapper.fromId(null)).isNull();
    }
}
