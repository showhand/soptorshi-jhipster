package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Department;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.Designation;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.search.EmployeeSearchRepository;
import org.soptorshi.service.EmployeeService;
import org.soptorshi.service.dto.EmployeeDTO;
import org.soptorshi.service.mapper.EmployeeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.EmployeeQueryService;

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
import java.math.BigDecimal;
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

import org.soptorshi.domain.enumeration.MaritalStatus;
import org.soptorshi.domain.enumeration.Gender;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.EmploymentType;
/**
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class EmployeeResourceIntTest {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHERS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHERS_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.UNMARRIED;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Religion DEFAULT_RELIGION = Religion.ISLAM;
    private static final Religion UPDATED_RELIGION = Religion.HINDU;

    private static final String DEFAULT_PERMANENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PERMANENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PRESENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_N_ID = "AAAAAAAAAA";
    private static final String UPDATED_N_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TIN = "AAAAAAAAAA";
    private static final String UPDATED_TIN = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOD_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_CONTACT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_MANAGER = 1L;
    private static final Long UPDATED_MANAGER = 2L;

    private static final EmployeeStatus DEFAULT_EMPLOYEE_STATUS = EmployeeStatus.IN_PROBATION;
    private static final EmployeeStatus UPDATED_EMPLOYEE_STATUS = EmployeeStatus.ACTIVE;

    private static final EmploymentType DEFAULT_EMPLOYMENT_TYPE = EmploymentType.PERMANENT;
    private static final EmploymentType UPDATED_EMPLOYMENT_TYPE = EmploymentType.TEMPORARY;

    private static final LocalDate DEFAULT_TERMINATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TERMINATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REASON_OF_TERMINATION = "AAAAAAAAAA";
    private static final String UPDATED_REASON_OF_TERMINATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USER_ACCOUNT = false;
    private static final Boolean UPDATED_USER_ACCOUNT = true;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final BigDecimal DEFAULT_HOURLY_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOURLY_SALARY = new BigDecimal(2);

    private static final String DEFAULT_BANK_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.EmployeeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmployeeSearchRepository mockEmployeeSearchRepository;

    @Autowired
    private EmployeeQueryService employeeQueryService;

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

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource employeeResource = new EmployeeResource(employeeService, employeeQueryService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .fullName(DEFAULT_FULL_NAME)
            .fathersName(DEFAULT_FATHERS_NAME)
            .mothersName(DEFAULT_MOTHERS_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .gender(DEFAULT_GENDER)
            .religion(DEFAULT_RELIGION)
            .permanentAddress(DEFAULT_PERMANENT_ADDRESS)
            .presentAddress(DEFAULT_PRESENT_ADDRESS)
            .nId(DEFAULT_N_ID)
            .tin(DEFAULT_TIN)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .email(DEFAULT_EMAIL)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .emergencyContact(DEFAULT_EMERGENCY_CONTACT)
            .joiningDate(DEFAULT_JOINING_DATE)
            .manager(DEFAULT_MANAGER)
            .employeeStatus(DEFAULT_EMPLOYEE_STATUS)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE)
            .terminationDate(DEFAULT_TERMINATION_DATE)
            .reasonOfTermination(DEFAULT_REASON_OF_TERMINATION)
            .userAccount(DEFAULT_USER_ACCOUNT)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .hourlySalary(DEFAULT_HOURLY_SALARY)
            .bankAccountNo(DEFAULT_BANK_ACCOUNT_NO)
            .bankName(DEFAULT_BANK_NAME);
        return employee;
    }

    @Before
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployee.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testEmployee.getFathersName()).isEqualTo(DEFAULT_FATHERS_NAME);
        assertThat(testEmployee.getMothersName()).isEqualTo(DEFAULT_MOTHERS_NAME);
        assertThat(testEmployee.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployee.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployee.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployee.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testEmployee.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testEmployee.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testEmployee.getnId()).isEqualTo(DEFAULT_N_ID);
        assertThat(testEmployee.getTin()).isEqualTo(DEFAULT_TIN);
        assertThat(testEmployee.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testEmployee.getEmergencyContact()).isEqualTo(DEFAULT_EMERGENCY_CONTACT);
        assertThat(testEmployee.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testEmployee.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testEmployee.getEmployeeStatus()).isEqualTo(DEFAULT_EMPLOYEE_STATUS);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testEmployee.getReasonOfTermination()).isEqualTo(DEFAULT_REASON_OF_TERMINATION);
        assertThat(testEmployee.isUserAccount()).isEqualTo(DEFAULT_USER_ACCOUNT);
        assertThat(testEmployee.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmployee.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testEmployee.getHourlySalary()).isEqualTo(DEFAULT_HOURLY_SALARY);
        assertThat(testEmployee.getBankAccountNo()).isEqualTo(DEFAULT_BANK_ACCOUNT_NO);
        assertThat(testEmployee.getBankName()).isEqualTo(DEFAULT_BANK_NAME);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).save(testEmployee);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(0)).save(employee);
    }

    @Test
    @Transactional
    public void checkEmployeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmployeeId(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFullName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFathersNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFathersName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMothersNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMothersName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setBirthDate(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMaritalStatus(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setGender(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReligionIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setReligion(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPermanentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPermanentAddress(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresentAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPresentAddress(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checknIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setnId(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setContactNumber(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmergencyContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmergencyContact(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME.toString())))
            .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].nId").value(hasItem(DEFAULT_N_ID.toString())))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.intValue())))
            .andExpect(jsonPath("$.[*].employeeStatus").value(hasItem(DEFAULT_EMPLOYEE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reasonOfTermination").value(hasItem(DEFAULT_REASON_OF_TERMINATION.toString())))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].hourlySalary").value(hasItem(DEFAULT_HOURLY_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.fathersName").value(DEFAULT_FATHERS_NAME.toString()))
            .andExpect(jsonPath("$.mothersName").value(DEFAULT_MOTHERS_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.nId").value(DEFAULT_N_ID.toString()))
            .andExpect(jsonPath("$.tin").value(DEFAULT_TIN.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.emergencyContact").value(DEFAULT_EMERGENCY_CONTACT.toString()))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER.intValue()))
            .andExpect(jsonPath("$.employeeStatus").value(DEFAULT_EMPLOYEE_STATUS.toString()))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE.toString()))
            .andExpect(jsonPath("$.reasonOfTermination").value(DEFAULT_REASON_OF_TERMINATION.toString()))
            .andExpect(jsonPath("$.userAccount").value(DEFAULT_USER_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.hourlySalary").value(DEFAULT_HOURLY_SALARY.intValue()))
            .andExpect(jsonPath("$.bankAccountNo").value(DEFAULT_BANK_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEmployeeShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the employeeList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeId is not null
        defaultEmployeeShouldBeFound("employeeId.specified=true");

        // Get all the employeeList where employeeId is null
        defaultEmployeeShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName equals to DEFAULT_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultEmployeeShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the employeeList where fullName equals to UPDATED_FULL_NAME
        defaultEmployeeShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullName is not null
        defaultEmployeeShouldBeFound("fullName.specified=true");

        // Get all the employeeList where fullName is null
        defaultEmployeeShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByFathersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fathersName equals to DEFAULT_FATHERS_NAME
        defaultEmployeeShouldBeFound("fathersName.equals=" + DEFAULT_FATHERS_NAME);

        // Get all the employeeList where fathersName equals to UPDATED_FATHERS_NAME
        defaultEmployeeShouldNotBeFound("fathersName.equals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFathersNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fathersName in DEFAULT_FATHERS_NAME or UPDATED_FATHERS_NAME
        defaultEmployeeShouldBeFound("fathersName.in=" + DEFAULT_FATHERS_NAME + "," + UPDATED_FATHERS_NAME);

        // Get all the employeeList where fathersName equals to UPDATED_FATHERS_NAME
        defaultEmployeeShouldNotBeFound("fathersName.in=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFathersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fathersName is not null
        defaultEmployeeShouldBeFound("fathersName.specified=true");

        // Get all the employeeList where fathersName is null
        defaultEmployeeShouldNotBeFound("fathersName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByMothersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mothersName equals to DEFAULT_MOTHERS_NAME
        defaultEmployeeShouldBeFound("mothersName.equals=" + DEFAULT_MOTHERS_NAME);

        // Get all the employeeList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultEmployeeShouldNotBeFound("mothersName.equals=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMothersNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mothersName in DEFAULT_MOTHERS_NAME or UPDATED_MOTHERS_NAME
        defaultEmployeeShouldBeFound("mothersName.in=" + DEFAULT_MOTHERS_NAME + "," + UPDATED_MOTHERS_NAME);

        // Get all the employeeList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultEmployeeShouldNotBeFound("mothersName.in=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMothersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mothersName is not null
        defaultEmployeeShouldBeFound("mothersName.specified=true");

        // Get all the employeeList where mothersName is null
        defaultEmployeeShouldNotBeFound("mothersName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the employeeList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate is not null
        defaultEmployeeShouldBeFound("birthDate.specified=true");

        // Get all the employeeList where birthDate is null
        defaultEmployeeShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate greater than or equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.greaterOrEqualThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate greater than or equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.greaterOrEqualThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where birthDate less than or equals to DEFAULT_BIRTH_DATE
        defaultEmployeeShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeList where birthDate less than or equals to UPDATED_BIRTH_DATE
        defaultEmployeeShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEmployeeShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the employeeList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where maritalStatus is not null
        defaultEmployeeShouldBeFound("maritalStatus.specified=true");

        // Get all the employeeList where maritalStatus is null
        defaultEmployeeShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender equals to DEFAULT_GENDER
        defaultEmployeeShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultEmployeeShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the employeeList where gender equals to UPDATED_GENDER
        defaultEmployeeShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where gender is not null
        defaultEmployeeShouldBeFound("gender.specified=true");

        // Get all the employeeList where gender is null
        defaultEmployeeShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion equals to DEFAULT_RELIGION
        defaultEmployeeShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the employeeList where religion equals to UPDATED_RELIGION
        defaultEmployeeShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultEmployeeShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the employeeList where religion equals to UPDATED_RELIGION
        defaultEmployeeShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where religion is not null
        defaultEmployeeShouldBeFound("religion.specified=true");

        // Get all the employeeList where religion is null
        defaultEmployeeShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByPermanentAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress equals to DEFAULT_PERMANENT_ADDRESS
        defaultEmployeeShouldBeFound("permanentAddress.equals=" + DEFAULT_PERMANENT_ADDRESS);

        // Get all the employeeList where permanentAddress equals to UPDATED_PERMANENT_ADDRESS
        defaultEmployeeShouldNotBeFound("permanentAddress.equals=" + UPDATED_PERMANENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPermanentAddressIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress in DEFAULT_PERMANENT_ADDRESS or UPDATED_PERMANENT_ADDRESS
        defaultEmployeeShouldBeFound("permanentAddress.in=" + DEFAULT_PERMANENT_ADDRESS + "," + UPDATED_PERMANENT_ADDRESS);

        // Get all the employeeList where permanentAddress equals to UPDATED_PERMANENT_ADDRESS
        defaultEmployeeShouldNotBeFound("permanentAddress.in=" + UPDATED_PERMANENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPermanentAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where permanentAddress is not null
        defaultEmployeeShouldBeFound("permanentAddress.specified=true");

        // Get all the employeeList where permanentAddress is null
        defaultEmployeeShouldNotBeFound("permanentAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByPresentAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where presentAddress equals to DEFAULT_PRESENT_ADDRESS
        defaultEmployeeShouldBeFound("presentAddress.equals=" + DEFAULT_PRESENT_ADDRESS);

        // Get all the employeeList where presentAddress equals to UPDATED_PRESENT_ADDRESS
        defaultEmployeeShouldNotBeFound("presentAddress.equals=" + UPDATED_PRESENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPresentAddressIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where presentAddress in DEFAULT_PRESENT_ADDRESS or UPDATED_PRESENT_ADDRESS
        defaultEmployeeShouldBeFound("presentAddress.in=" + DEFAULT_PRESENT_ADDRESS + "," + UPDATED_PRESENT_ADDRESS);

        // Get all the employeeList where presentAddress equals to UPDATED_PRESENT_ADDRESS
        defaultEmployeeShouldNotBeFound("presentAddress.in=" + UPDATED_PRESENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByPresentAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where presentAddress is not null
        defaultEmployeeShouldBeFound("presentAddress.specified=true");

        // Get all the employeeList where presentAddress is null
        defaultEmployeeShouldNotBeFound("presentAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesBynIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nId equals to DEFAULT_N_ID
        defaultEmployeeShouldBeFound("nId.equals=" + DEFAULT_N_ID);

        // Get all the employeeList where nId equals to UPDATED_N_ID
        defaultEmployeeShouldNotBeFound("nId.equals=" + UPDATED_N_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesBynIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nId in DEFAULT_N_ID or UPDATED_N_ID
        defaultEmployeeShouldBeFound("nId.in=" + DEFAULT_N_ID + "," + UPDATED_N_ID);

        // Get all the employeeList where nId equals to UPDATED_N_ID
        defaultEmployeeShouldNotBeFound("nId.in=" + UPDATED_N_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesBynIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where nId is not null
        defaultEmployeeShouldBeFound("nId.specified=true");

        // Get all the employeeList where nId is null
        defaultEmployeeShouldNotBeFound("nId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTinIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tin equals to DEFAULT_TIN
        defaultEmployeeShouldBeFound("tin.equals=" + DEFAULT_TIN);

        // Get all the employeeList where tin equals to UPDATED_TIN
        defaultEmployeeShouldNotBeFound("tin.equals=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTinIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tin in DEFAULT_TIN or UPDATED_TIN
        defaultEmployeeShouldBeFound("tin.in=" + DEFAULT_TIN + "," + UPDATED_TIN);

        // Get all the employeeList where tin equals to UPDATED_TIN
        defaultEmployeeShouldNotBeFound("tin.in=" + UPDATED_TIN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTinIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where tin is not null
        defaultEmployeeShouldBeFound("tin.specified=true");

        // Get all the employeeList where tin is null
        defaultEmployeeShouldNotBeFound("tin.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contactNumber equals to DEFAULT_CONTACT_NUMBER
        defaultEmployeeShouldBeFound("contactNumber.equals=" + DEFAULT_CONTACT_NUMBER);

        // Get all the employeeList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultEmployeeShouldNotBeFound("contactNumber.equals=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contactNumber in DEFAULT_CONTACT_NUMBER or UPDATED_CONTACT_NUMBER
        defaultEmployeeShouldBeFound("contactNumber.in=" + DEFAULT_CONTACT_NUMBER + "," + UPDATED_CONTACT_NUMBER);

        // Get all the employeeList where contactNumber equals to UPDATED_CONTACT_NUMBER
        defaultEmployeeShouldNotBeFound("contactNumber.in=" + UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where contactNumber is not null
        defaultEmployeeShouldBeFound("contactNumber.specified=true");

        // Get all the employeeList where contactNumber is null
        defaultEmployeeShouldNotBeFound("contactNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBloodGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup equals to DEFAULT_BLOOD_GROUP
        defaultEmployeeShouldBeFound("bloodGroup.equals=" + DEFAULT_BLOOD_GROUP);

        // Get all the employeeList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeShouldNotBeFound("bloodGroup.equals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBloodGroupIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup in DEFAULT_BLOOD_GROUP or UPDATED_BLOOD_GROUP
        defaultEmployeeShouldBeFound("bloodGroup.in=" + DEFAULT_BLOOD_GROUP + "," + UPDATED_BLOOD_GROUP);

        // Get all the employeeList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeShouldNotBeFound("bloodGroup.in=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBloodGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bloodGroup is not null
        defaultEmployeeShouldBeFound("bloodGroup.specified=true");

        // Get all the employeeList where bloodGroup is null
        defaultEmployeeShouldNotBeFound("bloodGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmergencyContactIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContact equals to DEFAULT_EMERGENCY_CONTACT
        defaultEmployeeShouldBeFound("emergencyContact.equals=" + DEFAULT_EMERGENCY_CONTACT);

        // Get all the employeeList where emergencyContact equals to UPDATED_EMERGENCY_CONTACT
        defaultEmployeeShouldNotBeFound("emergencyContact.equals=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmergencyContactIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContact in DEFAULT_EMERGENCY_CONTACT or UPDATED_EMERGENCY_CONTACT
        defaultEmployeeShouldBeFound("emergencyContact.in=" + DEFAULT_EMERGENCY_CONTACT + "," + UPDATED_EMERGENCY_CONTACT);

        // Get all the employeeList where emergencyContact equals to UPDATED_EMERGENCY_CONTACT
        defaultEmployeeShouldNotBeFound("emergencyContact.in=" + UPDATED_EMERGENCY_CONTACT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmergencyContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where emergencyContact is not null
        defaultEmployeeShouldBeFound("emergencyContact.specified=true");

        // Get all the employeeList where emergencyContact is null
        defaultEmployeeShouldNotBeFound("emergencyContact.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate equals to DEFAULT_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.equals=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.equals=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate in DEFAULT_JOINING_DATE or UPDATED_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.in=" + DEFAULT_JOINING_DATE + "," + UPDATED_JOINING_DATE);

        // Get all the employeeList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.in=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate is not null
        defaultEmployeeShouldBeFound("joiningDate.specified=true");

        // Get all the employeeList where joiningDate is null
        defaultEmployeeShouldNotBeFound("joiningDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate greater than or equals to DEFAULT_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.greaterOrEqualThan=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate greater than or equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.greaterOrEqualThan=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByJoiningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where joiningDate less than or equals to DEFAULT_JOINING_DATE
        defaultEmployeeShouldNotBeFound("joiningDate.lessThan=" + DEFAULT_JOINING_DATE);

        // Get all the employeeList where joiningDate less than or equals to UPDATED_JOINING_DATE
        defaultEmployeeShouldBeFound("joiningDate.lessThan=" + UPDATED_JOINING_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where manager equals to DEFAULT_MANAGER
        defaultEmployeeShouldBeFound("manager.equals=" + DEFAULT_MANAGER);

        // Get all the employeeList where manager equals to UPDATED_MANAGER
        defaultEmployeeShouldNotBeFound("manager.equals=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByManagerIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where manager in DEFAULT_MANAGER or UPDATED_MANAGER
        defaultEmployeeShouldBeFound("manager.in=" + DEFAULT_MANAGER + "," + UPDATED_MANAGER);

        // Get all the employeeList where manager equals to UPDATED_MANAGER
        defaultEmployeeShouldNotBeFound("manager.in=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where manager is not null
        defaultEmployeeShouldBeFound("manager.specified=true");

        // Get all the employeeList where manager is null
        defaultEmployeeShouldNotBeFound("manager.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByManagerIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where manager greater than or equals to DEFAULT_MANAGER
        defaultEmployeeShouldBeFound("manager.greaterOrEqualThan=" + DEFAULT_MANAGER);

        // Get all the employeeList where manager greater than or equals to UPDATED_MANAGER
        defaultEmployeeShouldNotBeFound("manager.greaterOrEqualThan=" + UPDATED_MANAGER);
    }

    @Test
    @Transactional
    public void getAllEmployeesByManagerIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where manager less than or equals to DEFAULT_MANAGER
        defaultEmployeeShouldNotBeFound("manager.lessThan=" + DEFAULT_MANAGER);

        // Get all the employeeList where manager less than or equals to UPDATED_MANAGER
        defaultEmployeeShouldBeFound("manager.lessThan=" + UPDATED_MANAGER);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStatus equals to DEFAULT_EMPLOYEE_STATUS
        defaultEmployeeShouldBeFound("employeeStatus.equals=" + DEFAULT_EMPLOYEE_STATUS);

        // Get all the employeeList where employeeStatus equals to UPDATED_EMPLOYEE_STATUS
        defaultEmployeeShouldNotBeFound("employeeStatus.equals=" + UPDATED_EMPLOYEE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStatus in DEFAULT_EMPLOYEE_STATUS or UPDATED_EMPLOYEE_STATUS
        defaultEmployeeShouldBeFound("employeeStatus.in=" + DEFAULT_EMPLOYEE_STATUS + "," + UPDATED_EMPLOYEE_STATUS);

        // Get all the employeeList where employeeStatus equals to UPDATED_EMPLOYEE_STATUS
        defaultEmployeeShouldNotBeFound("employeeStatus.in=" + UPDATED_EMPLOYEE_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStatus is not null
        defaultEmployeeShouldBeFound("employeeStatus.specified=true");

        // Get all the employeeList where employeeStatus is null
        defaultEmployeeShouldNotBeFound("employeeStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmploymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employmentType equals to DEFAULT_EMPLOYMENT_TYPE
        defaultEmployeeShouldBeFound("employmentType.equals=" + DEFAULT_EMPLOYMENT_TYPE);

        // Get all the employeeList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultEmployeeShouldNotBeFound("employmentType.equals=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmploymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employmentType in DEFAULT_EMPLOYMENT_TYPE or UPDATED_EMPLOYMENT_TYPE
        defaultEmployeeShouldBeFound("employmentType.in=" + DEFAULT_EMPLOYMENT_TYPE + "," + UPDATED_EMPLOYMENT_TYPE);

        // Get all the employeeList where employmentType equals to UPDATED_EMPLOYMENT_TYPE
        defaultEmployeeShouldNotBeFound("employmentType.in=" + UPDATED_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmploymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employmentType is not null
        defaultEmployeeShouldBeFound("employmentType.specified=true");

        // Get all the employeeList where employmentType is null
        defaultEmployeeShouldNotBeFound("employmentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate equals to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.equals=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.equals=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate in DEFAULT_TERMINATION_DATE or UPDATED_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.in=" + DEFAULT_TERMINATION_DATE + "," + UPDATED_TERMINATION_DATE);

        // Get all the employeeList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.in=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate is not null
        defaultEmployeeShouldBeFound("terminationDate.specified=true");

        // Get all the employeeList where terminationDate is null
        defaultEmployeeShouldNotBeFound("terminationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate greater than or equals to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.greaterOrEqualThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate greater than or equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.greaterOrEqualThan=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByTerminationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where terminationDate less than or equals to DEFAULT_TERMINATION_DATE
        defaultEmployeeShouldNotBeFound("terminationDate.lessThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the employeeList where terminationDate less than or equals to UPDATED_TERMINATION_DATE
        defaultEmployeeShouldBeFound("terminationDate.lessThan=" + UPDATED_TERMINATION_DATE);
    }


    @Test
    @Transactional
    public void getAllEmployeesByReasonOfTerminationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where reasonOfTermination equals to DEFAULT_REASON_OF_TERMINATION
        defaultEmployeeShouldBeFound("reasonOfTermination.equals=" + DEFAULT_REASON_OF_TERMINATION);

        // Get all the employeeList where reasonOfTermination equals to UPDATED_REASON_OF_TERMINATION
        defaultEmployeeShouldNotBeFound("reasonOfTermination.equals=" + UPDATED_REASON_OF_TERMINATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByReasonOfTerminationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where reasonOfTermination in DEFAULT_REASON_OF_TERMINATION or UPDATED_REASON_OF_TERMINATION
        defaultEmployeeShouldBeFound("reasonOfTermination.in=" + DEFAULT_REASON_OF_TERMINATION + "," + UPDATED_REASON_OF_TERMINATION);

        // Get all the employeeList where reasonOfTermination equals to UPDATED_REASON_OF_TERMINATION
        defaultEmployeeShouldNotBeFound("reasonOfTermination.in=" + UPDATED_REASON_OF_TERMINATION);
    }

    @Test
    @Transactional
    public void getAllEmployeesByReasonOfTerminationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where reasonOfTermination is not null
        defaultEmployeeShouldBeFound("reasonOfTermination.specified=true");

        // Get all the employeeList where reasonOfTermination is null
        defaultEmployeeShouldNotBeFound("reasonOfTermination.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userAccount equals to DEFAULT_USER_ACCOUNT
        defaultEmployeeShouldBeFound("userAccount.equals=" + DEFAULT_USER_ACCOUNT);

        // Get all the employeeList where userAccount equals to UPDATED_USER_ACCOUNT
        defaultEmployeeShouldNotBeFound("userAccount.equals=" + UPDATED_USER_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserAccountIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userAccount in DEFAULT_USER_ACCOUNT or UPDATED_USER_ACCOUNT
        defaultEmployeeShouldBeFound("userAccount.in=" + DEFAULT_USER_ACCOUNT + "," + UPDATED_USER_ACCOUNT);

        // Get all the employeeList where userAccount equals to UPDATED_USER_ACCOUNT
        defaultEmployeeShouldNotBeFound("userAccount.in=" + UPDATED_USER_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userAccount is not null
        defaultEmployeeShouldBeFound("userAccount.specified=true");

        // Get all the employeeList where userAccount is null
        defaultEmployeeShouldNotBeFound("userAccount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByHourlySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hourlySalary equals to DEFAULT_HOURLY_SALARY
        defaultEmployeeShouldBeFound("hourlySalary.equals=" + DEFAULT_HOURLY_SALARY);

        // Get all the employeeList where hourlySalary equals to UPDATED_HOURLY_SALARY
        defaultEmployeeShouldNotBeFound("hourlySalary.equals=" + UPDATED_HOURLY_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByHourlySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hourlySalary in DEFAULT_HOURLY_SALARY or UPDATED_HOURLY_SALARY
        defaultEmployeeShouldBeFound("hourlySalary.in=" + DEFAULT_HOURLY_SALARY + "," + UPDATED_HOURLY_SALARY);

        // Get all the employeeList where hourlySalary equals to UPDATED_HOURLY_SALARY
        defaultEmployeeShouldNotBeFound("hourlySalary.in=" + UPDATED_HOURLY_SALARY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByHourlySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hourlySalary is not null
        defaultEmployeeShouldBeFound("hourlySalary.specified=true");

        // Get all the employeeList where hourlySalary is null
        defaultEmployeeShouldNotBeFound("hourlySalary.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo equals to DEFAULT_BANK_ACCOUNT_NO
        defaultEmployeeShouldBeFound("bankAccountNo.equals=" + DEFAULT_BANK_ACCOUNT_NO);

        // Get all the employeeList where bankAccountNo equals to UPDATED_BANK_ACCOUNT_NO
        defaultEmployeeShouldNotBeFound("bankAccountNo.equals=" + UPDATED_BANK_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo in DEFAULT_BANK_ACCOUNT_NO or UPDATED_BANK_ACCOUNT_NO
        defaultEmployeeShouldBeFound("bankAccountNo.in=" + DEFAULT_BANK_ACCOUNT_NO + "," + UPDATED_BANK_ACCOUNT_NO);

        // Get all the employeeList where bankAccountNo equals to UPDATED_BANK_ACCOUNT_NO
        defaultEmployeeShouldNotBeFound("bankAccountNo.in=" + UPDATED_BANK_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankAccountNo is not null
        defaultEmployeeShouldBeFound("bankAccountNo.specified=true");

        // Get all the employeeList where bankAccountNo is null
        defaultEmployeeShouldNotBeFound("bankAccountNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName equals to DEFAULT_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultEmployeeShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the employeeList where bankName equals to UPDATED_BANK_NAME
        defaultEmployeeShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bankName is not null
        defaultEmployeeShouldBeFound("bankName.specified=true");

        // Get all the employeeList where bankName is null
        defaultEmployeeShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();

        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to departmentId + 1
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        Office office = OfficeResourceIntTest.createEntity(em);
        em.persist(office);
        em.flush();
        employee.setOffice(office);
        employeeRepository.saveAndFlush(employee);
        Long officeId = office.getId();

        // Get all the employeeList where office equals to officeId
        defaultEmployeeShouldBeFound("officeId.equals=" + officeId);

        // Get all the employeeList where office equals to officeId + 1
        defaultEmployeeShouldNotBeFound("officeId.equals=" + (officeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        Designation designation = DesignationResourceIntTest.createEntity(em);
        em.persist(designation);
        em.flush();
        employee.setDesignation(designation);
        employeeRepository.saveAndFlush(employee);
        Long designationId = designation.getId();

        // Get all the employeeList where designation equals to designationId
        defaultEmployeeShouldBeFound("designationId.equals=" + designationId);

        // Get all the employeeList where designation equals to designationId + 1
        defaultEmployeeShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].nId").value(hasItem(DEFAULT_N_ID)))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP)))
            .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.intValue())))
            .andExpect(jsonPath("$.[*].employeeStatus").value(hasItem(DEFAULT_EMPLOYEE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reasonOfTermination").value(hasItem(DEFAULT_REASON_OF_TERMINATION)))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].hourlySalary").value(hasItem(DEFAULT_HOURLY_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .employeeId(UPDATED_EMPLOYEE_ID)
            .fullName(UPDATED_FULL_NAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .mothersName(UPDATED_MOTHERS_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .presentAddress(UPDATED_PRESENT_ADDRESS)
            .nId(UPDATED_N_ID)
            .tin(UPDATED_TIN)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .email(UPDATED_EMAIL)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .emergencyContact(UPDATED_EMERGENCY_CONTACT)
            .joiningDate(UPDATED_JOINING_DATE)
            .manager(UPDATED_MANAGER)
            .employeeStatus(UPDATED_EMPLOYEE_STATUS)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .reasonOfTermination(UPDATED_REASON_OF_TERMINATION)
            .userAccount(UPDATED_USER_ACCOUNT)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .hourlySalary(UPDATED_HOURLY_SALARY)
            .bankAccountNo(UPDATED_BANK_ACCOUNT_NO)
            .bankName(UPDATED_BANK_NAME);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testEmployee.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testEmployee.getMothersName()).isEqualTo(UPDATED_MOTHERS_NAME);
        assertThat(testEmployee.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployee.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployee.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployee.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testEmployee.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testEmployee.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testEmployee.getnId()).isEqualTo(UPDATED_N_ID);
        assertThat(testEmployee.getTin()).isEqualTo(UPDATED_TIN);
        assertThat(testEmployee.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testEmployee.getEmergencyContact()).isEqualTo(UPDATED_EMERGENCY_CONTACT);
        assertThat(testEmployee.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testEmployee.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testEmployee.getEmployeeStatus()).isEqualTo(UPDATED_EMPLOYEE_STATUS);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testEmployee.getReasonOfTermination()).isEqualTo(UPDATED_REASON_OF_TERMINATION);
        assertThat(testEmployee.isUserAccount()).isEqualTo(UPDATED_USER_ACCOUNT);
        assertThat(testEmployee.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmployee.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmployee.getHourlySalary()).isEqualTo(UPDATED_HOURLY_SALARY);
        assertThat(testEmployee.getBankAccountNo()).isEqualTo(UPDATED_BANK_ACCOUNT_NO);
        assertThat(testEmployee.getBankName()).isEqualTo(UPDATED_BANK_NAME);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).save(testEmployee);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(0)).save(employee);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).deleteById(employee.getId());
    }

    @Test
    @Transactional
    public void searchEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        when(mockEmployeeSearchRepository.search(queryStringQuery("id:" + employee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(employee), PageRequest.of(0, 1), 1));
        // Search the employee
        restEmployeeMockMvc.perform(get("/api/_search/employees?query=id:" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].nId").value(hasItem(DEFAULT_N_ID)))
            .andExpect(jsonPath("$.[*].tin").value(hasItem(DEFAULT_TIN)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP)))
            .andExpect(jsonPath("$.[*].emergencyContact").value(hasItem(DEFAULT_EMERGENCY_CONTACT)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.intValue())))
            .andExpect(jsonPath("$.[*].employeeStatus").value(hasItem(DEFAULT_EMPLOYEE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reasonOfTermination").value(hasItem(DEFAULT_REASON_OF_TERMINATION)))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].hourlySalary").value(hasItem(DEFAULT_HOURLY_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].bankAccountNo").value(hasItem(DEFAULT_BANK_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setId(1L);
        Employee employee2 = new Employee();
        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);
        employee2.setId(2L);
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setId(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDTO.class);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setId(1L);
        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO2.setId(employeeDTO1.getId());
        assertThat(employeeDTO1).isEqualTo(employeeDTO2);
        employeeDTO2.setId(2L);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO1.setId(null);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeMapper.fromId(null)).isNull();
    }
}
