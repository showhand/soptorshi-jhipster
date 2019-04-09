package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.AcademicInformationAttachment;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AcademicInformationAttachmentRepository;
import org.soptorshi.repository.search.AcademicInformationAttachmentSearchRepository;
import org.soptorshi.service.AcademicInformationAttachmentService;
import org.soptorshi.service.dto.AcademicInformationAttachmentDTO;
import org.soptorshi.service.mapper.AcademicInformationAttachmentMapper;
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
 * Test class for the AcademicInformationAttachmentResource REST controller.
 *
 * @see AcademicInformationAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AcademicInformationAttachmentResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private AcademicInformationAttachmentRepository academicInformationAttachmentRepository;

    @Autowired
    private AcademicInformationAttachmentMapper academicInformationAttachmentMapper;

    @Autowired
    private AcademicInformationAttachmentService academicInformationAttachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AcademicInformationAttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private AcademicInformationAttachmentSearchRepository mockAcademicInformationAttachmentSearchRepository;

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

    private MockMvc restAcademicInformationAttachmentMockMvc;

    private AcademicInformationAttachment academicInformationAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademicInformationAttachmentResource academicInformationAttachmentResource = new AcademicInformationAttachmentResource(academicInformationAttachmentService);
        this.restAcademicInformationAttachmentMockMvc = MockMvcBuilders.standaloneSetup(academicInformationAttachmentResource)
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
    public static AcademicInformationAttachment createEntity(EntityManager em) {
        AcademicInformationAttachment academicInformationAttachment = new AcademicInformationAttachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        academicInformationAttachment.setEmployee(employee);
        return academicInformationAttachment;
    }

    @Before
    public void initTest() {
        academicInformationAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicInformationAttachment() throws Exception {
        int databaseSizeBeforeCreate = academicInformationAttachmentRepository.findAll().size();

        // Create the AcademicInformationAttachment
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO = academicInformationAttachmentMapper.toDto(academicInformationAttachment);
        restAcademicInformationAttachmentMockMvc.perform(post("/api/academic-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicInformationAttachment in the database
        List<AcademicInformationAttachment> academicInformationAttachmentList = academicInformationAttachmentRepository.findAll();
        assertThat(academicInformationAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicInformationAttachment testAcademicInformationAttachment = academicInformationAttachmentList.get(academicInformationAttachmentList.size() - 1);
        assertThat(testAcademicInformationAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAcademicInformationAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the AcademicInformationAttachment in Elasticsearch
        verify(mockAcademicInformationAttachmentSearchRepository, times(1)).save(testAcademicInformationAttachment);
    }

    @Test
    @Transactional
    public void createAcademicInformationAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicInformationAttachmentRepository.findAll().size();

        // Create the AcademicInformationAttachment with an existing ID
        academicInformationAttachment.setId(1L);
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO = academicInformationAttachmentMapper.toDto(academicInformationAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicInformationAttachmentMockMvc.perform(post("/api/academic-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicInformationAttachment in the database
        List<AcademicInformationAttachment> academicInformationAttachmentList = academicInformationAttachmentRepository.findAll();
        assertThat(academicInformationAttachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the AcademicInformationAttachment in Elasticsearch
        verify(mockAcademicInformationAttachmentSearchRepository, times(0)).save(academicInformationAttachment);
    }

    @Test
    @Transactional
    public void getAllAcademicInformationAttachments() throws Exception {
        // Initialize the database
        academicInformationAttachmentRepository.saveAndFlush(academicInformationAttachment);

        // Get all the academicInformationAttachmentList
        restAcademicInformationAttachmentMockMvc.perform(get("/api/academic-information-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }
    
    @Test
    @Transactional
    public void getAcademicInformationAttachment() throws Exception {
        // Initialize the database
        academicInformationAttachmentRepository.saveAndFlush(academicInformationAttachment);

        // Get the academicInformationAttachment
        restAcademicInformationAttachmentMockMvc.perform(get("/api/academic-information-attachments/{id}", academicInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicInformationAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicInformationAttachment() throws Exception {
        // Get the academicInformationAttachment
        restAcademicInformationAttachmentMockMvc.perform(get("/api/academic-information-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicInformationAttachment() throws Exception {
        // Initialize the database
        academicInformationAttachmentRepository.saveAndFlush(academicInformationAttachment);

        int databaseSizeBeforeUpdate = academicInformationAttachmentRepository.findAll().size();

        // Update the academicInformationAttachment
        AcademicInformationAttachment updatedAcademicInformationAttachment = academicInformationAttachmentRepository.findById(academicInformationAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicInformationAttachment are not directly saved in db
        em.detach(updatedAcademicInformationAttachment);
        updatedAcademicInformationAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO = academicInformationAttachmentMapper.toDto(updatedAcademicInformationAttachment);

        restAcademicInformationAttachmentMockMvc.perform(put("/api/academic-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicInformationAttachment in the database
        List<AcademicInformationAttachment> academicInformationAttachmentList = academicInformationAttachmentRepository.findAll();
        assertThat(academicInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);
        AcademicInformationAttachment testAcademicInformationAttachment = academicInformationAttachmentList.get(academicInformationAttachmentList.size() - 1);
        assertThat(testAcademicInformationAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAcademicInformationAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the AcademicInformationAttachment in Elasticsearch
        verify(mockAcademicInformationAttachmentSearchRepository, times(1)).save(testAcademicInformationAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicInformationAttachment() throws Exception {
        int databaseSizeBeforeUpdate = academicInformationAttachmentRepository.findAll().size();

        // Create the AcademicInformationAttachment
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO = academicInformationAttachmentMapper.toDto(academicInformationAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicInformationAttachmentMockMvc.perform(put("/api/academic-information-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicInformationAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicInformationAttachment in the database
        List<AcademicInformationAttachment> academicInformationAttachmentList = academicInformationAttachmentRepository.findAll();
        assertThat(academicInformationAttachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AcademicInformationAttachment in Elasticsearch
        verify(mockAcademicInformationAttachmentSearchRepository, times(0)).save(academicInformationAttachment);
    }

    @Test
    @Transactional
    public void deleteAcademicInformationAttachment() throws Exception {
        // Initialize the database
        academicInformationAttachmentRepository.saveAndFlush(academicInformationAttachment);

        int databaseSizeBeforeDelete = academicInformationAttachmentRepository.findAll().size();

        // Delete the academicInformationAttachment
        restAcademicInformationAttachmentMockMvc.perform(delete("/api/academic-information-attachments/{id}", academicInformationAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AcademicInformationAttachment> academicInformationAttachmentList = academicInformationAttachmentRepository.findAll();
        assertThat(academicInformationAttachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AcademicInformationAttachment in Elasticsearch
        verify(mockAcademicInformationAttachmentSearchRepository, times(1)).deleteById(academicInformationAttachment.getId());
    }

    @Test
    @Transactional
    public void searchAcademicInformationAttachment() throws Exception {
        // Initialize the database
        academicInformationAttachmentRepository.saveAndFlush(academicInformationAttachment);
        when(mockAcademicInformationAttachmentSearchRepository.search(queryStringQuery("id:" + academicInformationAttachment.getId())))
            .thenReturn(Collections.singletonList(academicInformationAttachment));
        // Search the academicInformationAttachment
        restAcademicInformationAttachmentMockMvc.perform(get("/api/_search/academic-information-attachments?query=id:" + academicInformationAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicInformationAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicInformationAttachment.class);
        AcademicInformationAttachment academicInformationAttachment1 = new AcademicInformationAttachment();
        academicInformationAttachment1.setId(1L);
        AcademicInformationAttachment academicInformationAttachment2 = new AcademicInformationAttachment();
        academicInformationAttachment2.setId(academicInformationAttachment1.getId());
        assertThat(academicInformationAttachment1).isEqualTo(academicInformationAttachment2);
        academicInformationAttachment2.setId(2L);
        assertThat(academicInformationAttachment1).isNotEqualTo(academicInformationAttachment2);
        academicInformationAttachment1.setId(null);
        assertThat(academicInformationAttachment1).isNotEqualTo(academicInformationAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicInformationAttachmentDTO.class);
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO1 = new AcademicInformationAttachmentDTO();
        academicInformationAttachmentDTO1.setId(1L);
        AcademicInformationAttachmentDTO academicInformationAttachmentDTO2 = new AcademicInformationAttachmentDTO();
        assertThat(academicInformationAttachmentDTO1).isNotEqualTo(academicInformationAttachmentDTO2);
        academicInformationAttachmentDTO2.setId(academicInformationAttachmentDTO1.getId());
        assertThat(academicInformationAttachmentDTO1).isEqualTo(academicInformationAttachmentDTO2);
        academicInformationAttachmentDTO2.setId(2L);
        assertThat(academicInformationAttachmentDTO1).isNotEqualTo(academicInformationAttachmentDTO2);
        academicInformationAttachmentDTO1.setId(null);
        assertThat(academicInformationAttachmentDTO1).isNotEqualTo(academicInformationAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(academicInformationAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(academicInformationAttachmentMapper.fromId(null)).isNull();
    }
}
