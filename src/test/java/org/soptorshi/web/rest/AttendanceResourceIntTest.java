/*
package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.repository.AttendanceRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.dto.AttendanceDTO;
import org.soptorshi.service.extended.AttendanceExtendedService;
import org.soptorshi.service.mapper.AttendanceMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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

*/
/**
 * Test class for the AttendanceResource REST controller.
 *
 * @see AttendanceResource
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AttendanceResourceIntTest {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ATTENDANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATTENDANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OUT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OUT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private AttendanceExtendedService attendanceService;

    */
/**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AttendanceSearchRepositoryMockConfiguration
     *//*

    @Autowired
    private AttendanceSearchRepository mockAttendanceSearchRepository;

    @Autowired
    private AttendanceQueryService attendanceQueryService;

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

    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceResource attendanceResource = new AttendanceResource(attendanceService, attendanceQueryService);
        this.restAttendanceMockMvc = MockMvcBuilders.standaloneSetup(attendanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    */
/**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*

    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .attendanceDate(DEFAULT_ATTENDANCE_DATE)
            .inTime(DEFAULT_IN_TIME)
            .outTime(DEFAULT_OUT_TIME);
        return attendance;
    }

    @Before
    public void initTest() {
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testAttendance.getAttendanceDate()).isEqualTo(DEFAULT_ATTENDANCE_DATE);
        assertThat(testAttendance.getInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testAttendance.getOutTime()).isEqualTo(DEFAULT_OUT_TIME);

        // Validate the Attendance in Elasticsearch
        verify(mockAttendanceSearchRepository, times(1)).save(testAttendance);
    }

    @Test
    @Transactional
    public void createAttendanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance with an existing ID
        attendance.setId(1L);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Attendance in Elasticsearch
        verify(mockAttendanceSearchRepository, times(0)).save(attendance);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.toString())))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())));
    }

    @Test
    @Transactional
    public void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.toString()))
            .andExpect(jsonPath("$.attendanceDate").value(DEFAULT_ATTENDANCE_DATE.toString()))
            .andExpect(jsonPath("$.inTime").value(DEFAULT_IN_TIME.toString()))
            .andExpect(jsonPath("$.outTime").value(DEFAULT_OUT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the attendanceList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultAttendanceShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeId is not null
        defaultAttendanceShouldBeFound("employeeId.specified=true");

        // Get all the attendanceList where employeeId is null
        defaultAttendanceShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDate equals to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceShouldBeFound("attendanceDate.equals=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceList where attendanceDate equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceShouldNotBeFound("attendanceDate.equals=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDate in DEFAULT_ATTENDANCE_DATE or UPDATED_ATTENDANCE_DATE
        defaultAttendanceShouldBeFound("attendanceDate.in=" + DEFAULT_ATTENDANCE_DATE + "," + UPDATED_ATTENDANCE_DATE);

        // Get all the attendanceList where attendanceDate equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceShouldNotBeFound("attendanceDate.in=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDate is not null
        defaultAttendanceShouldBeFound("attendanceDate.specified=true");

        // Get all the attendanceList where attendanceDate is null
        defaultAttendanceShouldNotBeFound("attendanceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDate greater than or equals to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceShouldBeFound("attendanceDate.greaterOrEqualThan=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceList where attendanceDate greater than or equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceShouldNotBeFound("attendanceDate.greaterOrEqualThan=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDate less than or equals to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceShouldNotBeFound("attendanceDate.lessThan=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceList where attendanceDate less than or equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceShouldBeFound("attendanceDate.lessThan=" + UPDATED_ATTENDANCE_DATE);
    }


    @Test
    @Transactional
    public void getAllAttendancesByInTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where inTime equals to DEFAULT_IN_TIME
        defaultAttendanceShouldBeFound("inTime.equals=" + DEFAULT_IN_TIME);

        // Get all the attendanceList where inTime equals to UPDATED_IN_TIME
        defaultAttendanceShouldNotBeFound("inTime.equals=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByInTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where inTime in DEFAULT_IN_TIME or UPDATED_IN_TIME
        defaultAttendanceShouldBeFound("inTime.in=" + DEFAULT_IN_TIME + "," + UPDATED_IN_TIME);

        // Get all the attendanceList where inTime equals to UPDATED_IN_TIME
        defaultAttendanceShouldNotBeFound("inTime.in=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByInTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where inTime is not null
        defaultAttendanceShouldBeFound("inTime.specified=true");

        // Get all the attendanceList where inTime is null
        defaultAttendanceShouldNotBeFound("inTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByOutTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where outTime equals to DEFAULT_OUT_TIME
        defaultAttendanceShouldBeFound("outTime.equals=" + DEFAULT_OUT_TIME);

        // Get all the attendanceList where outTime equals to UPDATED_OUT_TIME
        defaultAttendanceShouldNotBeFound("outTime.equals=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByOutTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where outTime in DEFAULT_OUT_TIME or UPDATED_OUT_TIME
        defaultAttendanceShouldBeFound("outTime.in=" + DEFAULT_OUT_TIME + "," + UPDATED_OUT_TIME);

        // Get all the attendanceList where outTime equals to UPDATED_OUT_TIME
        defaultAttendanceShouldNotBeFound("outTime.in=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByOutTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where outTime is not null
        defaultAttendanceShouldBeFound("outTime.specified=true");

        // Get all the attendanceList where outTime is null
        defaultAttendanceShouldNotBeFound("outTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceExcelUploadIsEqualToSomething() throws Exception {
        // Initialize the database
        AttendanceExcelUpload attendanceExcelUpload = AttendanceExcelUploadResourceIntTest.createEntity(em);
        em.persist(attendanceExcelUpload);
        em.flush();
        attendance.setAttendanceExcelUpload(attendanceExcelUpload);
        attendanceRepository.saveAndFlush(attendance);
        Long attendanceExcelUploadId = attendanceExcelUpload.getId();

        // Get all the attendanceList where attendanceExcelUpload equals to attendanceExcelUploadId
        defaultAttendanceShouldBeFound("attendanceExcelUploadId.equals=" + attendanceExcelUploadId);

        // Get all the attendanceList where attendanceExcelUpload equals to attendanceExcelUploadId + 1
        defaultAttendanceShouldNotBeFound("attendanceExcelUploadId.equals=" + (attendanceExcelUploadId + 1));
    }

    */
/**
     * Executes the search, and checks that the default entity is returned
     *//*

    private void defaultAttendanceShouldBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())));

        // Check, that the count call also returns 1
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    */
/**
     * Executes the search, and checks that the default entity is not returned
     *//*

    private void defaultAttendanceShouldNotBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findById(attendance.getId()).get();
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .employeeId(UPDATED_EMPLOYEE_ID)
            .attendanceDate(UPDATED_ATTENDANCE_DATE)
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(updatedAttendance);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testAttendance.getAttendanceDate()).isEqualTo(UPDATED_ATTENDANCE_DATE);
        assertThat(testAttendance.getInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testAttendance.getOutTime()).isEqualTo(UPDATED_OUT_TIME);

        // Validate the Attendance in Elasticsearch
        verify(mockAttendanceSearchRepository, times(1)).save(testAttendance);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Attendance in Elasticsearch
        verify(mockAttendanceSearchRepository, times(0)).save(attendance);
    }

    @Test
    @Transactional
    public void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Delete the attendance
        restAttendanceMockMvc.perform(delete("/api/attendances/{id}", attendance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Attendance in Elasticsearch
        verify(mockAttendanceSearchRepository, times(1)).deleteById(attendance.getId());
    }

    @Test
    @Transactional
    public void searchAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        when(mockAttendanceSearchRepository.search(queryStringQuery("id:" + attendance.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(attendance), PageRequest.of(0, 1), 1));
        // Search the attendance
        restAttendanceMockMvc.perform(get("/api/_search/attendances?query=id:" + attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendance.class);
        Attendance attendance1 = new Attendance();
        attendance1.setId(1L);
        Attendance attendance2 = new Attendance();
        attendance2.setId(attendance1.getId());
        assertThat(attendance1).isEqualTo(attendance2);
        attendance2.setId(2L);
        assertThat(attendance1).isNotEqualTo(attendance2);
        attendance1.setId(null);
        assertThat(attendance1).isNotEqualTo(attendance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceDTO.class);
        AttendanceDTO attendanceDTO1 = new AttendanceDTO();
        attendanceDTO1.setId(1L);
        AttendanceDTO attendanceDTO2 = new AttendanceDTO();
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO2.setId(attendanceDTO1.getId());
        assertThat(attendanceDTO1).isEqualTo(attendanceDTO2);
        attendanceDTO2.setId(2L);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO1.setId(null);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendanceMapper.fromId(null)).isNull();
    }
}
*/
