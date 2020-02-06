package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.enumeration.AttendanceType;
import org.soptorshi.repository.AttendanceExcelUploadRepository;
import org.soptorshi.repository.search.AttendanceExcelUploadSearchRepository;
import org.soptorshi.service.AttendanceExcelUploadQueryService;
import org.soptorshi.service.AttendanceExcelUploadService;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;
import org.soptorshi.service.mapper.AttendanceExcelUploadMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the AttendanceExcelUploadResource REST controller.
 *
 * @see AttendanceExcelUploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AttendanceExcelUploadResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final AttendanceType DEFAULT_TYPE = AttendanceType.FINGER;
    private static final AttendanceType UPDATED_TYPE = AttendanceType.FACE;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AttendanceExcelUploadRepository attendanceExcelUploadRepository;

    @Autowired
    private AttendanceExcelUploadMapper attendanceExcelUploadMapper;

    @Autowired
    private AttendanceExcelUploadService attendanceExcelUploadService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AttendanceExcelUploadSearchRepositoryMockConfiguration
     */
    @Autowired
    private AttendanceExcelUploadSearchRepository mockAttendanceExcelUploadSearchRepository;

    @Autowired
    private AttendanceExcelUploadQueryService attendanceExcelUploadQueryService;

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

    private MockMvc restAttendanceExcelUploadMockMvc;

    private AttendanceExcelUpload attendanceExcelUpload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceExcelUploadResource attendanceExcelUploadResource = new AttendanceExcelUploadResource(attendanceExcelUploadService, attendanceExcelUploadQueryService);
        this.restAttendanceExcelUploadMockMvc = MockMvcBuilders.standaloneSetup(attendanceExcelUploadResource)
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
    public static AttendanceExcelUpload createEntity(EntityManager em) {
        AttendanceExcelUpload attendanceExcelUpload = new AttendanceExcelUpload()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return attendanceExcelUpload;
    }

    @Before
    public void initTest() {
        attendanceExcelUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceExcelUpload() throws Exception {
        int databaseSizeBeforeCreate = attendanceExcelUploadRepository.findAll().size();

        // Create the AttendanceExcelUpload
        AttendanceExcelUploadDTO attendanceExcelUploadDTO = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);
        restAttendanceExcelUploadMockMvc.perform(post("/api/attendance-excel-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceExcelUploadDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendanceExcelUpload in the database
        List<AttendanceExcelUpload> attendanceExcelUploadList = attendanceExcelUploadRepository.findAll();
        assertThat(attendanceExcelUploadList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceExcelUpload testAttendanceExcelUpload = attendanceExcelUploadList.get(attendanceExcelUploadList.size() - 1);
        assertThat(testAttendanceExcelUpload.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAttendanceExcelUpload.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testAttendanceExcelUpload.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAttendanceExcelUpload.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttendanceExcelUpload.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAttendanceExcelUpload.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttendanceExcelUpload.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

        // Validate the AttendanceExcelUpload in Elasticsearch
        verify(mockAttendanceExcelUploadSearchRepository, times(1)).save(testAttendanceExcelUpload);
    }

    @Test
    @Transactional
    public void createAttendanceExcelUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceExcelUploadRepository.findAll().size();

        // Create the AttendanceExcelUpload with an existing ID
        attendanceExcelUpload.setId(1L);
        AttendanceExcelUploadDTO attendanceExcelUploadDTO = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceExcelUploadMockMvc.perform(post("/api/attendance-excel-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceExcelUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceExcelUpload in the database
        List<AttendanceExcelUpload> attendanceExcelUploadList = attendanceExcelUploadRepository.findAll();
        assertThat(attendanceExcelUploadList).hasSize(databaseSizeBeforeCreate);

        // Validate the AttendanceExcelUpload in Elasticsearch
        verify(mockAttendanceExcelUploadSearchRepository, times(0)).save(attendanceExcelUpload);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploads() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceExcelUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getAttendanceExcelUpload() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get the attendanceExcelUpload
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads/{id}", attendanceExcelUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceExcelUpload.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where type equals to DEFAULT_TYPE
        defaultAttendanceExcelUploadShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the attendanceExcelUploadList where type equals to UPDATED_TYPE
        defaultAttendanceExcelUploadShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAttendanceExcelUploadShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the attendanceExcelUploadList where type equals to UPDATED_TYPE
        defaultAttendanceExcelUploadShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where type is not null
        defaultAttendanceExcelUploadShouldBeFound("type.specified=true");

        // Get all the attendanceExcelUploadList where type is null
        defaultAttendanceExcelUploadShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdBy equals to DEFAULT_CREATED_BY
        defaultAttendanceExcelUploadShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the attendanceExcelUploadList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceExcelUploadShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAttendanceExcelUploadShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the attendanceExcelUploadList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceExcelUploadShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdBy is not null
        defaultAttendanceExcelUploadShouldBeFound("createdBy.specified=true");

        // Get all the attendanceExcelUploadList where createdBy is null
        defaultAttendanceExcelUploadShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdOn equals to DEFAULT_CREATED_ON
        defaultAttendanceExcelUploadShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the attendanceExcelUploadList where createdOn equals to UPDATED_CREATED_ON
        defaultAttendanceExcelUploadShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAttendanceExcelUploadShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the attendanceExcelUploadList where createdOn equals to UPDATED_CREATED_ON
        defaultAttendanceExcelUploadShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where createdOn is not null
        defaultAttendanceExcelUploadShouldBeFound("createdOn.specified=true");

        // Get all the attendanceExcelUploadList where createdOn is null
        defaultAttendanceExcelUploadShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultAttendanceExcelUploadShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the attendanceExcelUploadList where updatedBy equals to UPDATED_UPDATED_BY
        defaultAttendanceExcelUploadShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultAttendanceExcelUploadShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the attendanceExcelUploadList where updatedBy equals to UPDATED_UPDATED_BY
        defaultAttendanceExcelUploadShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedBy is not null
        defaultAttendanceExcelUploadShouldBeFound("updatedBy.specified=true");

        // Get all the attendanceExcelUploadList where updatedBy is null
        defaultAttendanceExcelUploadShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultAttendanceExcelUploadShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the attendanceExcelUploadList where updatedOn equals to UPDATED_UPDATED_ON
        defaultAttendanceExcelUploadShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultAttendanceExcelUploadShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the attendanceExcelUploadList where updatedOn equals to UPDATED_UPDATED_ON
        defaultAttendanceExcelUploadShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        // Get all the attendanceExcelUploadList where updatedOn is not null
        defaultAttendanceExcelUploadShouldBeFound("updatedOn.specified=true");

        // Get all the attendanceExcelUploadList where updatedOn is null
        defaultAttendanceExcelUploadShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceExcelUploadsByAttendanceIsEqualToSomething() throws Exception {
        // Initialize the database
        Attendance attendance = AttendanceResourceIntTest.createEntity(em);
        em.persist(attendance);
        em.flush();
        attendanceExcelUpload.addAttendance(attendance);
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);
        Long attendanceId = attendance.getId();

        // Get all the attendanceExcelUploadList where attendance equals to attendanceId
        defaultAttendanceExcelUploadShouldBeFound("attendanceId.equals=" + attendanceId);

        // Get all the attendanceExcelUploadList where attendance equals to attendanceId + 1
        defaultAttendanceExcelUploadShouldNotBeFound("attendanceId.equals=" + (attendanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAttendanceExcelUploadShouldBeFound(String filter) throws Exception {
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceExcelUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAttendanceExcelUploadShouldNotBeFound(String filter) throws Exception {
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAttendanceExcelUpload() throws Exception {
        // Get the attendanceExcelUpload
        restAttendanceExcelUploadMockMvc.perform(get("/api/attendance-excel-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceExcelUpload() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        int databaseSizeBeforeUpdate = attendanceExcelUploadRepository.findAll().size();

        // Update the attendanceExcelUpload
        AttendanceExcelUpload updatedAttendanceExcelUpload = attendanceExcelUploadRepository.findById(attendanceExcelUpload.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceExcelUpload are not directly saved in db
        em.detach(updatedAttendanceExcelUpload);
        updatedAttendanceExcelUpload
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        AttendanceExcelUploadDTO attendanceExcelUploadDTO = attendanceExcelUploadMapper.toDto(updatedAttendanceExcelUpload);

        restAttendanceExcelUploadMockMvc.perform(put("/api/attendance-excel-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceExcelUploadDTO)))
            .andExpect(status().isOk());

        // Validate the AttendanceExcelUpload in the database
        List<AttendanceExcelUpload> attendanceExcelUploadList = attendanceExcelUploadRepository.findAll();
        assertThat(attendanceExcelUploadList).hasSize(databaseSizeBeforeUpdate);
        AttendanceExcelUpload testAttendanceExcelUpload = attendanceExcelUploadList.get(attendanceExcelUploadList.size() - 1);
        assertThat(testAttendanceExcelUpload.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAttendanceExcelUpload.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testAttendanceExcelUpload.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAttendanceExcelUpload.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttendanceExcelUpload.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAttendanceExcelUpload.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttendanceExcelUpload.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

        // Validate the AttendanceExcelUpload in Elasticsearch
        verify(mockAttendanceExcelUploadSearchRepository, times(1)).save(testAttendanceExcelUpload);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceExcelUpload() throws Exception {
        int databaseSizeBeforeUpdate = attendanceExcelUploadRepository.findAll().size();

        // Create the AttendanceExcelUpload
        AttendanceExcelUploadDTO attendanceExcelUploadDTO = attendanceExcelUploadMapper.toDto(attendanceExcelUpload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceExcelUploadMockMvc.perform(put("/api/attendance-excel-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceExcelUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceExcelUpload in the database
        List<AttendanceExcelUpload> attendanceExcelUploadList = attendanceExcelUploadRepository.findAll();
        assertThat(attendanceExcelUploadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AttendanceExcelUpload in Elasticsearch
        verify(mockAttendanceExcelUploadSearchRepository, times(0)).save(attendanceExcelUpload);
    }

    @Test
    @Transactional
    public void deleteAttendanceExcelUpload() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);

        int databaseSizeBeforeDelete = attendanceExcelUploadRepository.findAll().size();

        // Delete the attendanceExcelUpload
        restAttendanceExcelUploadMockMvc.perform(delete("/api/attendance-excel-uploads/{id}", attendanceExcelUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AttendanceExcelUpload> attendanceExcelUploadList = attendanceExcelUploadRepository.findAll();
        assertThat(attendanceExcelUploadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AttendanceExcelUpload in Elasticsearch
        verify(mockAttendanceExcelUploadSearchRepository, times(1)).deleteById(attendanceExcelUpload.getId());
    }

    @Test
    @Transactional
    public void searchAttendanceExcelUpload() throws Exception {
        // Initialize the database
        attendanceExcelUploadRepository.saveAndFlush(attendanceExcelUpload);
        when(mockAttendanceExcelUploadSearchRepository.search(queryStringQuery("id:" + attendanceExcelUpload.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attendanceExcelUpload), PageRequest.of(0, 1), 1));
        // Search the attendanceExcelUpload
        restAttendanceExcelUploadMockMvc.perform(get("/api/_search/attendance-excel-uploads?query=id:" + attendanceExcelUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceExcelUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceExcelUpload.class);
        AttendanceExcelUpload attendanceExcelUpload1 = new AttendanceExcelUpload();
        attendanceExcelUpload1.setId(1L);
        AttendanceExcelUpload attendanceExcelUpload2 = new AttendanceExcelUpload();
        attendanceExcelUpload2.setId(attendanceExcelUpload1.getId());
        assertThat(attendanceExcelUpload1).isEqualTo(attendanceExcelUpload2);
        attendanceExcelUpload2.setId(2L);
        assertThat(attendanceExcelUpload1).isNotEqualTo(attendanceExcelUpload2);
        attendanceExcelUpload1.setId(null);
        assertThat(attendanceExcelUpload1).isNotEqualTo(attendanceExcelUpload2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceExcelUploadDTO.class);
        AttendanceExcelUploadDTO attendanceExcelUploadDTO1 = new AttendanceExcelUploadDTO();
        attendanceExcelUploadDTO1.setId(1L);
        AttendanceExcelUploadDTO attendanceExcelUploadDTO2 = new AttendanceExcelUploadDTO();
        assertThat(attendanceExcelUploadDTO1).isNotEqualTo(attendanceExcelUploadDTO2);
        attendanceExcelUploadDTO2.setId(attendanceExcelUploadDTO1.getId());
        assertThat(attendanceExcelUploadDTO1).isEqualTo(attendanceExcelUploadDTO2);
        attendanceExcelUploadDTO2.setId(2L);
        assertThat(attendanceExcelUploadDTO1).isNotEqualTo(attendanceExcelUploadDTO2);
        attendanceExcelUploadDTO1.setId(null);
        assertThat(attendanceExcelUploadDTO1).isNotEqualTo(attendanceExcelUploadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendanceExcelUploadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendanceExcelUploadMapper.fromId(null)).isNull();
    }
}
