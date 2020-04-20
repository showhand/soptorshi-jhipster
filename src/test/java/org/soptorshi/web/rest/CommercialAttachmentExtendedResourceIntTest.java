package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.repository.CommercialAttachmentRepository;
import org.soptorshi.repository.search.CommercialAttachmentSearchRepository;
import org.soptorshi.service.CommercialAttachmentQueryService;
import org.soptorshi.service.CommercialAttachmentService;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
import org.soptorshi.service.mapper.CommercialAttachmentMapper;
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
 * Test class for the CommercialAttachmentResource REST controller.
 *
 * @see CommercialAttachmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CommercialAttachmentExtendedResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private CommercialAttachmentRepository commercialAttachmentRepository;

    @Autowired
    private CommercialAttachmentMapper commercialAttachmentMapper;

    @Autowired
    private CommercialAttachmentService commercialAttachmentService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CommercialAttachmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommercialAttachmentSearchRepository mockCommercialAttachmentSearchRepository;

    @Autowired
    private CommercialAttachmentQueryService commercialAttachmentQueryService;

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

    private MockMvc restCommercialAttachmentMockMvc;

    private CommercialAttachment commercialAttachment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommercialAttachmentResource commercialAttachmentResource = new CommercialAttachmentResource(commercialAttachmentService, commercialAttachmentQueryService);
        this.restCommercialAttachmentMockMvc = MockMvcBuilders.standaloneSetup(commercialAttachmentResource)
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
    public static CommercialAttachment createEntity(EntityManager em) {
        CommercialAttachment commercialAttachment = new CommercialAttachment()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return commercialAttachment;
    }

    @Before
    public void initTest() {
        commercialAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercialAttachment() throws Exception {
        int databaseSizeBeforeCreate = commercialAttachmentRepository.findAll().size();

        // Create the CommercialAttachment
        CommercialAttachmentDTO commercialAttachmentDTO = commercialAttachmentMapper.toDto(commercialAttachment);
        restCommercialAttachmentMockMvc.perform(post("/api/commercial-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the CommercialAttachment in the database
        List<CommercialAttachment> commercialAttachmentList = commercialAttachmentRepository.findAll();
        assertThat(commercialAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        CommercialAttachment testCommercialAttachment = commercialAttachmentList.get(commercialAttachmentList.size() - 1);
        assertThat(testCommercialAttachment.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testCommercialAttachment.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the CommercialAttachment in Elasticsearch
        verify(mockCommercialAttachmentSearchRepository, times(1)).save(testCommercialAttachment);
    }

    @Test
    @Transactional
    public void createCommercialAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercialAttachmentRepository.findAll().size();

        // Create the CommercialAttachment with an existing ID
        commercialAttachment.setId(1L);
        CommercialAttachmentDTO commercialAttachmentDTO = commercialAttachmentMapper.toDto(commercialAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercialAttachmentMockMvc.perform(post("/api/commercial-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialAttachment in the database
        List<CommercialAttachment> commercialAttachmentList = commercialAttachmentRepository.findAll();
        assertThat(commercialAttachmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommercialAttachment in Elasticsearch
        verify(mockCommercialAttachmentSearchRepository, times(0)).save(commercialAttachment);
    }

    @Test
    @Transactional
    public void getAllCommercialAttachments() throws Exception {
        // Initialize the database
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);

        // Get all the commercialAttachmentList
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getCommercialAttachment() throws Exception {
        // Initialize the database
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);

        // Get the commercialAttachment
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments/{id}", commercialAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commercialAttachment.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getAllCommercialAttachmentsByCommercialPoIsEqualToSomething() throws Exception {
        // Initialize the database
        CommercialPo commercialPo = CommercialPoExtendedResourceIntTest.createEntity(em);
        em.persist(commercialPo);
        em.flush();
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);
        Long commercialPoId = commercialPo.getId();

        // Get all the commercialAttachmentList where commercialPo equals to commercialPoId
        defaultCommercialAttachmentShouldBeFound("commercialPoId.equals=" + commercialPoId);

        // Get all the commercialAttachmentList where commercialPo equals to commercialPoId + 1
        defaultCommercialAttachmentShouldNotBeFound("commercialPoId.equals=" + (commercialPoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCommercialAttachmentShouldBeFound(String filter) throws Exception {
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));

        // Check, that the count call also returns 1
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCommercialAttachmentShouldNotBeFound(String filter) throws Exception {
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommercialAttachment() throws Exception {
        // Get the commercialAttachment
        restCommercialAttachmentMockMvc.perform(get("/api/commercial-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercialAttachment() throws Exception {
        // Initialize the database
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);

        int databaseSizeBeforeUpdate = commercialAttachmentRepository.findAll().size();

        // Update the commercialAttachment
        CommercialAttachment updatedCommercialAttachment = commercialAttachmentRepository.findById(commercialAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedCommercialAttachment are not directly saved in db
        em.detach(updatedCommercialAttachment);
        updatedCommercialAttachment
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        CommercialAttachmentDTO commercialAttachmentDTO = commercialAttachmentMapper.toDto(updatedCommercialAttachment);

        restCommercialAttachmentMockMvc.perform(put("/api/commercial-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the CommercialAttachment in the database
        List<CommercialAttachment> commercialAttachmentList = commercialAttachmentRepository.findAll();
        assertThat(commercialAttachmentList).hasSize(databaseSizeBeforeUpdate);
        CommercialAttachment testCommercialAttachment = commercialAttachmentList.get(commercialAttachmentList.size() - 1);
        assertThat(testCommercialAttachment.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testCommercialAttachment.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the CommercialAttachment in Elasticsearch
        verify(mockCommercialAttachmentSearchRepository, times(1)).save(testCommercialAttachment);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercialAttachment() throws Exception {
        int databaseSizeBeforeUpdate = commercialAttachmentRepository.findAll().size();

        // Create the CommercialAttachment
        CommercialAttachmentDTO commercialAttachmentDTO = commercialAttachmentMapper.toDto(commercialAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercialAttachmentMockMvc.perform(put("/api/commercial-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commercialAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommercialAttachment in the database
        List<CommercialAttachment> commercialAttachmentList = commercialAttachmentRepository.findAll();
        assertThat(commercialAttachmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommercialAttachment in Elasticsearch
        verify(mockCommercialAttachmentSearchRepository, times(0)).save(commercialAttachment);
    }

    @Test
    @Transactional
    public void deleteCommercialAttachment() throws Exception {
        // Initialize the database
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);

        int databaseSizeBeforeDelete = commercialAttachmentRepository.findAll().size();

        // Delete the commercialAttachment
        restCommercialAttachmentMockMvc.perform(delete("/api/commercial-attachments/{id}", commercialAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommercialAttachment> commercialAttachmentList = commercialAttachmentRepository.findAll();
        assertThat(commercialAttachmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommercialAttachment in Elasticsearch
        verify(mockCommercialAttachmentSearchRepository, times(1)).deleteById(commercialAttachment.getId());
    }

    @Test
    @Transactional
    public void searchCommercialAttachment() throws Exception {
        // Initialize the database
        commercialAttachmentRepository.saveAndFlush(commercialAttachment);
        when(mockCommercialAttachmentSearchRepository.search(queryStringQuery("id:" + commercialAttachment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(commercialAttachment), PageRequest.of(0, 1), 1));
        // Search the commercialAttachment
        restCommercialAttachmentMockMvc.perform(get("/api/_search/commercial-attachments?query=id:" + commercialAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercialAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialAttachment.class);
        CommercialAttachment commercialAttachment1 = new CommercialAttachment();
        commercialAttachment1.setId(1L);
        CommercialAttachment commercialAttachment2 = new CommercialAttachment();
        commercialAttachment2.setId(commercialAttachment1.getId());
        assertThat(commercialAttachment1).isEqualTo(commercialAttachment2);
        commercialAttachment2.setId(2L);
        assertThat(commercialAttachment1).isNotEqualTo(commercialAttachment2);
        commercialAttachment1.setId(null);
        assertThat(commercialAttachment1).isNotEqualTo(commercialAttachment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialAttachmentDTO.class);
        CommercialAttachmentDTO commercialAttachmentDTO1 = new CommercialAttachmentDTO();
        commercialAttachmentDTO1.setId(1L);
        CommercialAttachmentDTO commercialAttachmentDTO2 = new CommercialAttachmentDTO();
        assertThat(commercialAttachmentDTO1).isNotEqualTo(commercialAttachmentDTO2);
        commercialAttachmentDTO2.setId(commercialAttachmentDTO1.getId());
        assertThat(commercialAttachmentDTO1).isEqualTo(commercialAttachmentDTO2);
        commercialAttachmentDTO2.setId(2L);
        assertThat(commercialAttachmentDTO1).isNotEqualTo(commercialAttachmentDTO2);
        commercialAttachmentDTO1.setId(null);
        assertThat(commercialAttachmentDTO1).isNotEqualTo(commercialAttachmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commercialAttachmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commercialAttachmentMapper.fromId(null)).isNull();
    }
}
