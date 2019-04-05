package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Employee;
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.search.EmployeeSearchRepository;
import org.soptorshi.service.EmployeeService;
import org.soptorshi.service.dto.EmployeeDTO;
import org.soptorshi.service.mapper.EmployeeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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

    private static final Double DEFAULT_AGE = 1D;
    private static final Double UPDATED_AGE = 2D;

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

    private static final EmployeeStatus DEFAULT_EMPLOYEE_STATUS = EmployeeStatus.ACTIVE;
    private static final EmployeeStatus UPDATED_EMPLOYEE_STATUS = EmployeeStatus.TERMINATED;

    private static final EmploymentType DEFAULT_EMPLOYMENT_TYPE = EmploymentType.PERMANENT;
    private static final EmploymentType UPDATED_EMPLOYMENT_TYPE = EmploymentType.TEMPORARY;

    private static final LocalDate DEFAULT_TERMINATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TERMINATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REASON_OF_TERMINATION = "AAAAAAAAAA";
    private static final String UPDATED_REASON_OF_TERMINATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USER_ACCOUNT = false;
    private static final Boolean UPDATED_USER_ACCOUNT = true;

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
        final EmployeeResource employeeResource = new EmployeeResource(employeeService);
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
            .age(DEFAULT_AGE)
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
            .employeeStatus(DEFAULT_EMPLOYEE_STATUS)
            .employmentType(DEFAULT_EMPLOYMENT_TYPE)
            .terminationDate(DEFAULT_TERMINATION_DATE)
            .reasonOfTermination(DEFAULT_REASON_OF_TERMINATION)
            .userAccount(DEFAULT_USER_ACCOUNT);
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
        assertThat(testEmployee.getAge()).isEqualTo(DEFAULT_AGE);
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
        assertThat(testEmployee.getEmployeeStatus()).isEqualTo(DEFAULT_EMPLOYEE_STATUS);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testEmployee.getReasonOfTermination()).isEqualTo(DEFAULT_REASON_OF_TERMINATION);
        assertThat(testEmployee.isUserAccount()).isEqualTo(DEFAULT_USER_ACCOUNT);

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
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
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
            .andExpect(jsonPath("$.[*].employeeStatus").value(hasItem(DEFAULT_EMPLOYEE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reasonOfTermination").value(hasItem(DEFAULT_REASON_OF_TERMINATION.toString())))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT.booleanValue())));
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
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.doubleValue()))
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
            .andExpect(jsonPath("$.employeeStatus").value(DEFAULT_EMPLOYEE_STATUS.toString()))
            .andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE.toString()))
            .andExpect(jsonPath("$.reasonOfTermination").value(DEFAULT_REASON_OF_TERMINATION.toString()))
            .andExpect(jsonPath("$.userAccount").value(DEFAULT_USER_ACCOUNT.booleanValue()));
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
            .age(UPDATED_AGE)
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
            .employeeStatus(UPDATED_EMPLOYEE_STATUS)
            .employmentType(UPDATED_EMPLOYMENT_TYPE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .reasonOfTermination(UPDATED_REASON_OF_TERMINATION)
            .userAccount(UPDATED_USER_ACCOUNT);
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
        assertThat(testEmployee.getAge()).isEqualTo(UPDATED_AGE);
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
        assertThat(testEmployee.getEmployeeStatus()).isEqualTo(UPDATED_EMPLOYEE_STATUS);
        assertThat(testEmployee.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
        assertThat(testEmployee.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testEmployee.getReasonOfTermination()).isEqualTo(UPDATED_REASON_OF_TERMINATION);
        assertThat(testEmployee.isUserAccount()).isEqualTo(UPDATED_USER_ACCOUNT);

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
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
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
            .andExpect(jsonPath("$.[*].employeeStatus").value(hasItem(DEFAULT_EMPLOYEE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].reasonOfTermination").value(hasItem(DEFAULT_REASON_OF_TERMINATION)))
            .andExpect(jsonPath("$.[*].userAccount").value(hasItem(DEFAULT_USER_ACCOUNT.booleanValue())));
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
