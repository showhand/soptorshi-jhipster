package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.TrainingInformationAttachment;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.TrainingInformationAttachmentRepository;
import org.soptorshi.repository.search.TrainingInformationAttachmentSearchRepository;
import org.soptorshi.service.TrainingInformationAttachmentService;
import org.soptorshi.service.dto.TrainingInformationAttachmentDTO;
import org.soptorshi.service.mapper.TrainingInformationAttachmentMapper;
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
 * Test class for the TrainingInformationAttachmentResource REST controller.
 *
 * @see TrainingInformationAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class TrainingInformationAttachmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private TrainingInformationAttachmentRepository trainingInformationAttachmentRepository;

    @Autowired
    private TrainingInformationAttachmentMapper trainingInformationAttachmentMapper;

    @Autowired
    private TrainingInformationAttachmentService trainingInformationAttachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.TrainingInformationAttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingInformationAttachmentSearchRepository mockTrainingInformationAttachmentSearchRepository;

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

    private MockMvc restTrainingInformationAttachmentMockMvc;

    private TrainingInformationAttachment trainingInformationAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingInformationAttachmentResource trainingInformationAttachmentResource = new TrainingInformationAttachmentResource(trainingInformationAttachmentService);
        this.restTrainingInformationAttachmentMockMvc = MockMvcBuilders.standaloneSetup(trainingInformationAttachmentResource)
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
    public static TrainingInformationAttachment createEntity(EntityManager em) {
        TrainingInformationAttachment trainingInformationAttachment = new TrainingInformationAttachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        trainingInformationAttachment.setEmployee(employee);
        return trainingInformationAttachment;
    }

    @Before
    public void initTest() {
        trainingInformationAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingInformationAttachment() throws Exception {
        int databaseSizeBeforeCreate = trainingInformationAttachmentRepository.findAll().size();

        // Create the TrainingInformationAttachment
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO = trainingInformationAttachmentMapper.toDto(trainingInformationAttachment);
        restTrainingInformationAttachmentMockMvc.perform(post("/api/training-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingInformationAttachment in the database
        List<TrainingInformationAttachment> trainingInformationAttachmentList = trainingInformationAttachmentRepository.findAll();
        assertThat(trainingInformationAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingInformationAttachment testTrainingInformationAttachment = trainingInformationAttachmentList.get(trainingInformationAttachmentList.size() - 1);
        assertThat(testTrainingInformationAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testTrainingInformationAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the TrainingInformationAttachment in Elasticsearch
        verify(mockTrainingInformationAttachmentSearchRepository, times(1)).save(testTrainingInformationAttachment);
    }

    @Test
    @Transactional
    public void createTrainingInformationAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingInformationAttachmentRepository.findAll().size();

        // Create the TrainingInformationAttachment with an existing ID
        trainingInformationAttachment.setId(1L);
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO = trainingInformationAttachmentMapper.toDto(trainingInformationAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingInformationAttachmentMockMvc.perform(post("/api/training-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingInformationAttachment in the database
        List<TrainingInformationAttachment> trainingInformationAttachmentList = trainingInformationAttachmentRepository.findAll();
        assertThat(trainingInformationAttachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the TrainingInformationAttachment in Elasticsearch
        verify(mockTrainingInformationAttachmentSearchRepository, times(0)).save(trainingInformationAttachment);
    }

    @Test
    @Transactional
    public void getAllTrainingInformationAttachments() throws Exception {
        // Initialize the database
        trainingInformationAttachmentRepository.saveAndFlush(trainingInformationAttachment);

        // Get all the trainingInformationAttachmentList
        restTrainingInformationAttachmentMockMvc.perform(get("/api/training-information-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getTrainingInformationAttachment() throws Exception {
        // Initialize the database
        trainingInformationAttachmentRepository.saveAndFlush(trainingInformationAttachment);

        // Get the trainingInformationAttachment
        restTrainingInformationAttachmentMockMvc.perform(get("/api/training-information-attachments/{id}", trainingInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainingInformationAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingInformationAttachment() throws Exception {
        // Get the trainingInformationAttachment
        restTrainingInformationAttachmentMockMvc.perform(get("/api/training-information-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingInformationAttachment() throws Exception {
        // Initialize the database
        trainingInformationAttachmentRepository.saveAndFlush(trainingInformationAttachment);

        int databaseSizeBeforeUpdate = trainingInformationAttachmentRepository.findAll().size();

        // Update the trainingInformationAttachment
        TrainingInformationAttachment updatedTrainingInformationAttachment = trainingInformationAttachmentRepository.findById(trainingInformationAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingInformationAttachment are not directly saved in db
        em.detach(updatedTrainingInformationAttachment);
        updatedTrainingInformationAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO = trainingInformationAttachmentMapper.toDto(updatedTrainingInformationAttachment);

        restTrainingInformationAttachmentMockMvc.perform(put("/api/training-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the TrainingInformationAttachment in the database
        List<TrainingInformationAttachment> trainingInformationAttachmentList = trainingInformationAttachmentRepository.findAll();
        assertThat(trainingInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);
        TrainingInformationAttachment testTrainingInformationAttachment = trainingInformationAttachmentList.get(trainingInformationAttachmentList.size() - 1);
        assertThat(testTrainingInformationAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testTrainingInformationAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the TrainingInformationAttachment in Elasticsearch
        verify(mockTrainingInformationAttachmentSearchRepository, times(1)).save(testTrainingInformationAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingInformationAttachment() throws Exception {
        int databaseSizeBeforeUpdate = trainingInformationAttachmentRepository.findAll().size();

        // Create the TrainingInformationAttachment
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO = trainingInformationAttachmentMapper.toDto(trainingInformationAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingInformationAttachmentMockMvc.perform(put("/api/training-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingInformationAttachment in the database
        List<TrainingInformationAttachment> trainingInformationAttachmentList = trainingInformationAttachmentRepository.findAll();
        assertThat(trainingInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TrainingInformationAttachment in Elasticsearch
        verify(mockTrainingInformationAttachmentSearchRepository, times(0)).save(trainingInformationAttachment);
    }

    @Test
    @Transactional
    public void deleteTrainingInformationAttachment() throws Exception {
        // Initialize the database
        trainingInformationAttachmentRepository.saveAndFlush(trainingInformationAttachment);

        int databaseSizeBeforeDelete = trainingInformationAttachmentRepository.findAll().size();

        // Delete the trainingInformationAttachment
        restTrainingInformationAttachmentMockMvc.perform(delete("/api/training-information-attachments/{id}", trainingInformationAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingInformationAttachment> trainingInformationAttachmentList = trainingInformationAttachmentRepository.findAll();
        assertThat(trainingInformationAttachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TrainingInformationAttachment in Elasticsearch
        verify(mockTrainingInformationAttachmentSearchRepository, times(1)).deleteById(trainingInformationAttachment.getId());
    }

    @Test
    @Transactional
    public void searchTrainingInformationAttachment() throws Exception {
        // Initialize the database
        trainingInformationAttachmentRepository.saveAndFlush(trainingInformationAttachment);
        when(mockTrainingInformationAttachmentSearchRepository.search(queryStringQuery("id:" + trainingInformationAttachment.getId())))
            .thenReturn(Collections.singletonList(trainingInformationAttachment));
        // Search the trainingInformationAttachment
        restTrainingInformationAttachmentMockMvc.perform(get("/api/_search/training-information-attachments?query=id:" + trainingInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingInformationAttachment.class);
        TrainingInformationAttachment trainingInformationAttachment1 = new TrainingInformationAttachment();
        trainingInformationAttachment1.setId(1L);
        TrainingInformationAttachment trainingInformationAttachment2 = new TrainingInformationAttachment();
        trainingInformationAttachment2.setId(trainingInformationAttachment1.getId());
        assertThat(trainingInformationAttachment1).isEqualTo(trainingInformationAttachment2);
        trainingInformationAttachment2.setId(2L);
        assertThat(trainingInformationAttachment1).isNotEqualTo(trainingInformationAttachment2);
        trainingInformationAttachment1.setId(null);
        assertThat(trainingInformationAttachment1).isNotEqualTo(trainingInformationAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingInformationAttachmentDTO.class);
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO1 = new TrainingInformationAttachmentDTO();
        trainingInformationAttachmentDTO1.setId(1L);
        TrainingInformationAttachmentDTO trainingInformationAttachmentDTO2 = new TrainingInformationAttachmentDTO();
        assertThat(trainingInformationAttachmentDTO1).isNotEqualTo(trainingInformationAttachmentDTO2);
        trainingInformationAttachmentDTO2.setId(trainingInformationAttachmentDTO1.getId());
        assertThat(trainingInformationAttachmentDTO1).isEqualTo(trainingInformationAttachmentDTO2);
        trainingInformationAttachmentDTO2.setId(2L);
        assertThat(trainingInformationAttachmentDTO1).isNotEqualTo(trainingInformationAttachmentDTO2);
        trainingInformationAttachmentDTO1.setId(null);
        assertThat(trainingInformationAttachmentDTO1).isNotEqualTo(trainingInformationAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trainingInformationAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trainingInformationAttachmentMapper.fromId(null)).isNull();
    }
}
