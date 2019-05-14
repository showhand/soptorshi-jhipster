package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Salary;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.SalaryRepository;
import org.soptorshi.repository.search.SalarySearchRepository;
import org.soptorshi.service.SalaryService;
import org.soptorshi.service.dto.SalaryDTO;
import org.soptorshi.service.mapper.SalaryMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.SalaryCriteria;
import org.soptorshi.service.SalaryQueryService;

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

/**
 * Test class for the SalaryResource REST controller.
 *
 * @see SalaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SalaryResourceIntTest {

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);

    private static final Double DEFAULT_HOUSE_RENT = 1D;
    private static final Double UPDATED_HOUSE_RENT = 2D;

    private static final Double DEFAULT_MEDICAL_ALLOWANCE = 1D;
    private static final Double UPDATED_MEDICAL_ALLOWANCE = 2D;

    private static final Double DEFAULT_INCREMENT_RATE = 1D;
    private static final Double UPDATED_INCREMENT_RATE = 2D;

    private static final Double DEFAULT_OTHER_ALLOWANCE = 1D;
    private static final Double UPDATED_OTHER_ALLOWANCE = 2D;

    private static final Long DEFAULT_MODIFIED_BY = 1L;
    private static final Long UPDATED_MODIFIED_BY = 2L;

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private SalaryService salaryService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SalarySearchRepositoryMockConfiguration
     */
    @Autowired
    private SalarySearchRepository mockSalarySearchRepository;

    @Autowired
    private SalaryQueryService salaryQueryService;

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

    private MockMvc restSalaryMockMvc;

    private Salary salary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalaryResource salaryResource = new SalaryResource(salaryService, salaryQueryService);
        this.restSalaryMockMvc = MockMvcBuilders.standaloneSetup(salaryResource)
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
    public static Salary createEntity(EntityManager em) {
        Salary salary = new Salary()
            .basic(DEFAULT_BASIC)
            .houseRent(DEFAULT_HOUSE_RENT)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .incrementRate(DEFAULT_INCREMENT_RATE)
            .otherAllowance(DEFAULT_OTHER_ALLOWANCE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return salary;
    }

    @Before
    public void initTest() {
        salary = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalary() throws Exception {
        int databaseSizeBeforeCreate = salaryRepository.findAll().size();

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);
        restSalaryMockMvc.perform(post("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeCreate + 1);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testSalary.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testSalary.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testSalary.getIncrementRate()).isEqualTo(DEFAULT_INCREMENT_RATE);
        assertThat(testSalary.getOtherAllowance()).isEqualTo(DEFAULT_OTHER_ALLOWANCE);
        assertThat(testSalary.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSalary.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Salary in Elasticsearch
        verify(mockSalarySearchRepository, times(1)).save(testSalary);
    }

    @Test
    @Transactional
    public void createSalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salaryRepository.findAll().size();

        // Create the Salary with an existing ID
        salary.setId(1L);
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaryMockMvc.perform(post("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeCreate);

        // Validate the Salary in Elasticsearch
        verify(mockSalarySearchRepository, times(0)).save(salary);
    }

    @Test
    @Transactional
    public void checkBasicIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaryRepository.findAll().size();
        // set the field null
        salary.setBasic(null);

        // Create the Salary, which fails.
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        restSalaryMockMvc.perform(post("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouseRentIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaryRepository.findAll().size();
        // set the field null
        salary.setHouseRent(null);

        // Create the Salary, which fails.
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        restSalaryMockMvc.perform(post("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMedicalAllowanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaryRepository.findAll().size();
        // set the field null
        salary.setMedicalAllowance(null);

        // Create the Salary, which fails.
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        restSalaryMockMvc.perform(post("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalaries() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList
        restSalaryMockMvc.perform(get("/api/salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salary.getId().intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].incrementRate").value(hasItem(DEFAULT_INCREMENT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get the salary
        restSalaryMockMvc.perform(get("/api/salaries/{id}", salary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salary.getId().intValue()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.doubleValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.doubleValue()))
            .andExpect(jsonPath("$.incrementRate").value(DEFAULT_INCREMENT_RATE.doubleValue()))
            .andExpect(jsonPath("$.otherAllowance").value(DEFAULT_OTHER_ALLOWANCE.doubleValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllSalariesByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where basic equals to DEFAULT_BASIC
        defaultSalaryShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the salaryList where basic equals to UPDATED_BASIC
        defaultSalaryShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllSalariesByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultSalaryShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the salaryList where basic equals to UPDATED_BASIC
        defaultSalaryShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllSalariesByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where basic is not null
        defaultSalaryShouldBeFound("basic.specified=true");

        // Get all the salaryList where basic is null
        defaultSalaryShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultSalaryShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the salaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultSalaryShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllSalariesByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultSalaryShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the salaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultSalaryShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllSalariesByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where houseRent is not null
        defaultSalaryShouldBeFound("houseRent.specified=true");

        // Get all the salaryList where houseRent is null
        defaultSalaryShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultSalaryShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the salaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultSalaryShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllSalariesByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultSalaryShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the salaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultSalaryShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllSalariesByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where medicalAllowance is not null
        defaultSalaryShouldBeFound("medicalAllowance.specified=true");

        // Get all the salaryList where medicalAllowance is null
        defaultSalaryShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByIncrementRateIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where incrementRate equals to DEFAULT_INCREMENT_RATE
        defaultSalaryShouldBeFound("incrementRate.equals=" + DEFAULT_INCREMENT_RATE);

        // Get all the salaryList where incrementRate equals to UPDATED_INCREMENT_RATE
        defaultSalaryShouldNotBeFound("incrementRate.equals=" + UPDATED_INCREMENT_RATE);
    }

    @Test
    @Transactional
    public void getAllSalariesByIncrementRateIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where incrementRate in DEFAULT_INCREMENT_RATE or UPDATED_INCREMENT_RATE
        defaultSalaryShouldBeFound("incrementRate.in=" + DEFAULT_INCREMENT_RATE + "," + UPDATED_INCREMENT_RATE);

        // Get all the salaryList where incrementRate equals to UPDATED_INCREMENT_RATE
        defaultSalaryShouldNotBeFound("incrementRate.in=" + UPDATED_INCREMENT_RATE);
    }

    @Test
    @Transactional
    public void getAllSalariesByIncrementRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where incrementRate is not null
        defaultSalaryShouldBeFound("incrementRate.specified=true");

        // Get all the salaryList where incrementRate is null
        defaultSalaryShouldNotBeFound("incrementRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByOtherAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where otherAllowance equals to DEFAULT_OTHER_ALLOWANCE
        defaultSalaryShouldBeFound("otherAllowance.equals=" + DEFAULT_OTHER_ALLOWANCE);

        // Get all the salaryList where otherAllowance equals to UPDATED_OTHER_ALLOWANCE
        defaultSalaryShouldNotBeFound("otherAllowance.equals=" + UPDATED_OTHER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllSalariesByOtherAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where otherAllowance in DEFAULT_OTHER_ALLOWANCE or UPDATED_OTHER_ALLOWANCE
        defaultSalaryShouldBeFound("otherAllowance.in=" + DEFAULT_OTHER_ALLOWANCE + "," + UPDATED_OTHER_ALLOWANCE);

        // Get all the salaryList where otherAllowance equals to UPDATED_OTHER_ALLOWANCE
        defaultSalaryShouldNotBeFound("otherAllowance.in=" + UPDATED_OTHER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllSalariesByOtherAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where otherAllowance is not null
        defaultSalaryShouldBeFound("otherAllowance.specified=true");

        // Get all the salaryList where otherAllowance is null
        defaultSalaryShouldNotBeFound("otherAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultSalaryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the salaryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSalaryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultSalaryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the salaryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultSalaryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedBy is not null
        defaultSalaryShouldBeFound("modifiedBy.specified=true");

        // Get all the salaryList where modifiedBy is null
        defaultSalaryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedBy greater than or equals to DEFAULT_MODIFIED_BY
        defaultSalaryShouldBeFound("modifiedBy.greaterOrEqualThan=" + DEFAULT_MODIFIED_BY);

        // Get all the salaryList where modifiedBy greater than or equals to UPDATED_MODIFIED_BY
        defaultSalaryShouldNotBeFound("modifiedBy.greaterOrEqualThan=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedBy less than or equals to DEFAULT_MODIFIED_BY
        defaultSalaryShouldNotBeFound("modifiedBy.lessThan=" + DEFAULT_MODIFIED_BY);

        // Get all the salaryList where modifiedBy less than or equals to UPDATED_MODIFIED_BY
        defaultSalaryShouldBeFound("modifiedBy.lessThan=" + UPDATED_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSalariesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultSalaryShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSalaryShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultSalaryShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the salaryList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultSalaryShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedOn is not null
        defaultSalaryShouldBeFound("modifiedOn.specified=true");

        // Get all the salaryList where modifiedOn is null
        defaultSalaryShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultSalaryShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultSalaryShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllSalariesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        // Get all the salaryList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultSalaryShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the salaryList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultSalaryShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllSalariesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        salary.setEmployee(employee);
        salaryRepository.saveAndFlush(salary);
        Long employeeId = employee.getId();

        // Get all the salaryList where employee equals to employeeId
        defaultSalaryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the salaryList where employee equals to employeeId + 1
        defaultSalaryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSalaryShouldBeFound(String filter) throws Exception {
        restSalaryMockMvc.perform(get("/api/salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salary.getId().intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].incrementRate").value(hasItem(DEFAULT_INCREMENT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restSalaryMockMvc.perform(get("/api/salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSalaryShouldNotBeFound(String filter) throws Exception {
        restSalaryMockMvc.perform(get("/api/salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalaryMockMvc.perform(get("/api/salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSalary() throws Exception {
        // Get the salary
        restSalaryMockMvc.perform(get("/api/salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();

        // Update the salary
        Salary updatedSalary = salaryRepository.findById(salary.getId()).get();
        // Disconnect from session so that the updates on updatedSalary are not directly saved in db
        em.detach(updatedSalary);
        updatedSalary
            .basic(UPDATED_BASIC)
            .houseRent(UPDATED_HOUSE_RENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .incrementRate(UPDATED_INCREMENT_RATE)
            .otherAllowance(UPDATED_OTHER_ALLOWANCE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        SalaryDTO salaryDTO = salaryMapper.toDto(updatedSalary);

        restSalaryMockMvc.perform(put("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isOk());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);
        Salary testSalary = salaryList.get(salaryList.size() - 1);
        assertThat(testSalary.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testSalary.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testSalary.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testSalary.getIncrementRate()).isEqualTo(UPDATED_INCREMENT_RATE);
        assertThat(testSalary.getOtherAllowance()).isEqualTo(UPDATED_OTHER_ALLOWANCE);
        assertThat(testSalary.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSalary.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Salary in Elasticsearch
        verify(mockSalarySearchRepository, times(1)).save(testSalary);
    }

    @Test
    @Transactional
    public void updateNonExistingSalary() throws Exception {
        int databaseSizeBeforeUpdate = salaryRepository.findAll().size();

        // Create the Salary
        SalaryDTO salaryDTO = salaryMapper.toDto(salary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaryMockMvc.perform(put("/api/salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salary in the database
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Salary in Elasticsearch
        verify(mockSalarySearchRepository, times(0)).save(salary);
    }

    @Test
    @Transactional
    public void deleteSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);

        int databaseSizeBeforeDelete = salaryRepository.findAll().size();

        // Delete the salary
        restSalaryMockMvc.perform(delete("/api/salaries/{id}", salary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Salary> salaryList = salaryRepository.findAll();
        assertThat(salaryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Salary in Elasticsearch
        verify(mockSalarySearchRepository, times(1)).deleteById(salary.getId());
    }

    @Test
    @Transactional
    public void searchSalary() throws Exception {
        // Initialize the database
        salaryRepository.saveAndFlush(salary);
        when(mockSalarySearchRepository.search(queryStringQuery("id:" + salary.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(salary), PageRequest.of(0, 1), 1));
        // Search the salary
        restSalaryMockMvc.perform(get("/api/_search/salaries?query=id:" + salary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salary.getId().intValue())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].incrementRate").value(hasItem(DEFAULT_INCREMENT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salary.class);
        Salary salary1 = new Salary();
        salary1.setId(1L);
        Salary salary2 = new Salary();
        salary2.setId(salary1.getId());
        assertThat(salary1).isEqualTo(salary2);
        salary2.setId(2L);
        assertThat(salary1).isNotEqualTo(salary2);
        salary1.setId(null);
        assertThat(salary1).isNotEqualTo(salary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaryDTO.class);
        SalaryDTO salaryDTO1 = new SalaryDTO();
        salaryDTO1.setId(1L);
        SalaryDTO salaryDTO2 = new SalaryDTO();
        assertThat(salaryDTO1).isNotEqualTo(salaryDTO2);
        salaryDTO2.setId(salaryDTO1.getId());
        assertThat(salaryDTO1).isEqualTo(salaryDTO2);
        salaryDTO2.setId(2L);
        assertThat(salaryDTO1).isNotEqualTo(salaryDTO2);
        salaryDTO1.setId(null);
        assertThat(salaryDTO1).isNotEqualTo(salaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salaryMapper.fromId(null)).isNull();
    }
}
