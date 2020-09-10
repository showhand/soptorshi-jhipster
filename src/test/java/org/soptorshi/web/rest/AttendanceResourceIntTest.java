package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Attendance;
import org.soptorshi.domain.AttendanceExcelUpload;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.AttendanceRepository;
import org.soptorshi.repository.search.AttendanceSearchRepository;
import org.soptorshi.service.AttendanceQueryService;
import org.soptorshi.service.AttendanceService;
import org.soptorshi.service.dto.AttendanceDTO;
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

/**
 * Test class for the AttendanceResource REST controller.
 *
 * @see AttendanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class AttendanceResourceIntTest {

    private static final LocalDate DEFAULT_ATTENDANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATTENDANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_OUT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OUT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private AttendanceService attendanceService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.AttendanceSearchRepositoryMockConfiguration
     */
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

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .attendanceDate(DEFAULT_ATTENDANCE_DATE)
            .inTime(DEFAULT_IN_TIME)
            .outTime(DEFAULT_OUT_TIME)
            .duration(DEFAULT_DURATION)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        attendance.setEmployee(employee);
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
        assertThat(testAttendance.getAttendanceDate()).isEqualTo(DEFAULT_ATTENDANCE_DATE);
        assertThat(testAttendance.getInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testAttendance.getOutTime()).isEqualTo(DEFAULT_OUT_TIME);
        assertThat(testAttendance.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testAttendance.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAttendance.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttendance.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAttendance.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttendance.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);

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
    public void checkAttendanceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setAttendanceDate(null);

        // Create the Attendance, which fails.
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setInTime(null);

        // Create the Attendance, which fails.
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
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
            .andExpect(jsonPath("$.attendanceDate").value(DEFAULT_ATTENDANCE_DATE.toString()))
            .andExpect(jsonPath("$.inTime").value(DEFAULT_IN_TIME.toString()))
            .andExpect(jsonPath("$.outTime").value(DEFAULT_OUT_TIME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()));
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
    public void getAllAttendancesByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where duration equals to DEFAULT_DURATION
        defaultAttendanceShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the attendanceList where duration equals to UPDATED_DURATION
        defaultAttendanceShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllAttendancesByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultAttendanceShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the attendanceList where duration equals to UPDATED_DURATION
        defaultAttendanceShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void getAllAttendancesByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where duration is not null
        defaultAttendanceShouldBeFound("duration.specified=true");

        // Get all the attendanceList where duration is null
        defaultAttendanceShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks equals to DEFAULT_REMARKS
        defaultAttendanceShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the attendanceList where remarks equals to UPDATED_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultAttendanceShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the attendanceList where remarks equals to UPDATED_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks is not null
        defaultAttendanceShouldBeFound("remarks.specified=true");

        // Get all the attendanceList where remarks is null
        defaultAttendanceShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdBy equals to DEFAULT_CREATED_BY
        defaultAttendanceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the attendanceList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAttendanceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the attendanceList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdBy is not null
        defaultAttendanceShouldBeFound("createdBy.specified=true");

        // Get all the attendanceList where createdBy is null
        defaultAttendanceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdOn equals to DEFAULT_CREATED_ON
        defaultAttendanceShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the attendanceList where createdOn equals to UPDATED_CREATED_ON
        defaultAttendanceShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAttendanceShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the attendanceList where createdOn equals to UPDATED_CREATED_ON
        defaultAttendanceShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendancesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where createdOn is not null
        defaultAttendanceShouldBeFound("createdOn.specified=true");

        // Get all the attendanceList where createdOn is null
        defaultAttendanceShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultAttendanceShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the attendanceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultAttendanceShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultAttendanceShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the attendanceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultAttendanceShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedBy is not null
        defaultAttendanceShouldBeFound("updatedBy.specified=true");

        // Get all the attendanceList where updatedBy is null
        defaultAttendanceShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultAttendanceShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the attendanceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultAttendanceShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultAttendanceShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the attendanceList where updatedOn equals to UPDATED_UPDATED_ON
        defaultAttendanceShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllAttendancesByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where updatedOn is not null
        defaultAttendanceShouldBeFound("updatedOn.specified=true");

        // Get all the attendanceList where updatedOn is null
        defaultAttendanceShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        attendance.setEmployee(employee);
        attendanceRepository.saveAndFlush(attendance);
        Long employeeId = employee.getId();

        // Get all the attendanceList where employee equals to employeeId
        defaultAttendanceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the attendanceList where employee equals to employeeId + 1
        defaultAttendanceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
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

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAttendanceShouldBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));

        // Check, that the count call also returns 1
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
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
            .attendanceDate(UPDATED_ATTENDANCE_DATE)
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .duration(UPDATED_DURATION)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(updatedAttendance);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttendanceDate()).isEqualTo(UPDATED_ATTENDANCE_DATE);
        assertThat(testAttendance.getInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testAttendance.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testAttendance.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testAttendance.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAttendance.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttendance.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAttendance.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttendance.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);

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
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(DEFAULT_OUT_TIME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())));
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
