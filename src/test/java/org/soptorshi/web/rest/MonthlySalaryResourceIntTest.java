package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.SalaryMessages;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.repository.search.MonthlySalarySearchRepository;
import org.soptorshi.service.MonthlySalaryService;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.mapper.MonthlySalaryMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.MonthlySalaryCriteria;
import org.soptorshi.service.MonthlySalaryQueryService;

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

import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;
/**
 * Test class for the MonthlySalaryResource REST controller.
 *
 * @see MonthlySalaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class MonthlySalaryResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final MonthType DEFAULT_MONTH = MonthType.JANUARY;
    private static final MonthType UPDATED_MONTH = MonthType.FEBRUARY;

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_GROSS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HOUSE_RENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOUSE_RENT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MEDICAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDICAL_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OVER_TIME_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OVER_TIME_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FOOD_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FOOD_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ARREAR_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ARREAR_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DRIVER_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DRIVER_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FUEL_LUB_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FUEL_LUB_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MOBILE_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_MOBILE_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRAVEL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRAVEL_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_OTHER_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER_ALLOWANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_FESTIVAL_ALLOWANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FESTIVAL_ALLOWANCE = new BigDecimal(2);

    private static final Integer DEFAULT_ABSENT = 1;
    private static final Integer UPDATED_ABSENT = 2;

    private static final BigDecimal DEFAULT_FINE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FINE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADVANCE_HO = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADVANCE_HO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADVANCE_FACTORY = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADVANCE_FACTORY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROVIDENT_FUND = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROVIDENT_FUND = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LOAN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOAN_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BILL_PAYABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BILL_PAYABLE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BILL_RECEIVABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BILL_RECEIVABLE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYABLE = new BigDecimal(2);

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_ON_HOLD = false;
    private static final Boolean UPDATED_ON_HOLD = true;

    private static final MonthlySalaryStatus DEFAULT_STATUS = MonthlySalaryStatus.APPROVED_BY_MANAGER;
    private static final MonthlySalaryStatus UPDATED_STATUS = MonthlySalaryStatus.APPROVED_BY_ACCOUNTS;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VOUCHER_GENERATED = false;
    private static final Boolean UPDATED_VOUCHER_GENERATED = true;

    @Autowired
    private MonthlySalaryRepository monthlySalaryRepository;

    @Autowired
    private MonthlySalaryMapper monthlySalaryMapper;

    @Autowired
    private MonthlySalaryService monthlySalaryService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.MonthlySalarySearchRepositoryMockConfiguration
     */
    @Autowired
    private MonthlySalarySearchRepository mockMonthlySalarySearchRepository;

    @Autowired
    private MonthlySalaryQueryService monthlySalaryQueryService;

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

    private MockMvc restMonthlySalaryMockMvc;

    private MonthlySalary monthlySalary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonthlySalaryResource monthlySalaryResource = new MonthlySalaryResource(monthlySalaryService, monthlySalaryQueryService);
        this.restMonthlySalaryMockMvc = MockMvcBuilders.standaloneSetup(monthlySalaryResource)
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
    public static MonthlySalary createEntity(EntityManager em) {
        MonthlySalary monthlySalary = new MonthlySalary()
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .basic(DEFAULT_BASIC)
            .gross(DEFAULT_GROSS)
            .houseRent(DEFAULT_HOUSE_RENT)
            .medicalAllowance(DEFAULT_MEDICAL_ALLOWANCE)
            .overTimeAllowance(DEFAULT_OVER_TIME_ALLOWANCE)
            .foodAllowance(DEFAULT_FOOD_ALLOWANCE)
            .arrearAllowance(DEFAULT_ARREAR_ALLOWANCE)
            .driverAllowance(DEFAULT_DRIVER_ALLOWANCE)
            .fuelLubAllowance(DEFAULT_FUEL_LUB_ALLOWANCE)
            .mobileAllowance(DEFAULT_MOBILE_ALLOWANCE)
            .travelAllowance(DEFAULT_TRAVEL_ALLOWANCE)
            .otherAllowance(DEFAULT_OTHER_ALLOWANCE)
            .festivalAllowance(DEFAULT_FESTIVAL_ALLOWANCE)
            .absent(DEFAULT_ABSENT)
            .fine(DEFAULT_FINE)
            .advanceHO(DEFAULT_ADVANCE_HO)
            .advanceFactory(DEFAULT_ADVANCE_FACTORY)
            .providentFund(DEFAULT_PROVIDENT_FUND)
            .tax(DEFAULT_TAX)
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .billPayable(DEFAULT_BILL_PAYABLE)
            .billReceivable(DEFAULT_BILL_RECEIVABLE)
            .payable(DEFAULT_PAYABLE)
            .approved(DEFAULT_APPROVED)
            .onHold(DEFAULT_ON_HOLD)
            .status(DEFAULT_STATUS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .voucherGenerated(DEFAULT_VOUCHER_GENERATED);
        return monthlySalary;
    }

    @Before
    public void initTest() {
        monthlySalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonthlySalary() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryRepository.findAll().size();

        // Create the MonthlySalary
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);
        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isCreated());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlySalary testMonthlySalary = monthlySalaryList.get(monthlySalaryList.size() - 1);
        assertThat(testMonthlySalary.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMonthlySalary.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testMonthlySalary.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testMonthlySalary.getGross()).isEqualTo(DEFAULT_GROSS);
        assertThat(testMonthlySalary.getHouseRent()).isEqualTo(DEFAULT_HOUSE_RENT);
        assertThat(testMonthlySalary.getMedicalAllowance()).isEqualTo(DEFAULT_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalary.getOverTimeAllowance()).isEqualTo(DEFAULT_OVER_TIME_ALLOWANCE);
        assertThat(testMonthlySalary.getFoodAllowance()).isEqualTo(DEFAULT_FOOD_ALLOWANCE);
        assertThat(testMonthlySalary.getArrearAllowance()).isEqualTo(DEFAULT_ARREAR_ALLOWANCE);
        assertThat(testMonthlySalary.getDriverAllowance()).isEqualTo(DEFAULT_DRIVER_ALLOWANCE);
        assertThat(testMonthlySalary.getFuelLubAllowance()).isEqualTo(DEFAULT_FUEL_LUB_ALLOWANCE);
        assertThat(testMonthlySalary.getMobileAllowance()).isEqualTo(DEFAULT_MOBILE_ALLOWANCE);
        assertThat(testMonthlySalary.getTravelAllowance()).isEqualTo(DEFAULT_TRAVEL_ALLOWANCE);
        assertThat(testMonthlySalary.getOtherAllowance()).isEqualTo(DEFAULT_OTHER_ALLOWANCE);
        assertThat(testMonthlySalary.getFestivalAllowance()).isEqualTo(DEFAULT_FESTIVAL_ALLOWANCE);
        assertThat(testMonthlySalary.getAbsent()).isEqualTo(DEFAULT_ABSENT);
        assertThat(testMonthlySalary.getFine()).isEqualTo(DEFAULT_FINE);
        assertThat(testMonthlySalary.getAdvanceHO()).isEqualTo(DEFAULT_ADVANCE_HO);
        assertThat(testMonthlySalary.getAdvanceFactory()).isEqualTo(DEFAULT_ADVANCE_FACTORY);
        assertThat(testMonthlySalary.getProvidentFund()).isEqualTo(DEFAULT_PROVIDENT_FUND);
        assertThat(testMonthlySalary.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testMonthlySalary.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testMonthlySalary.getBillPayable()).isEqualTo(DEFAULT_BILL_PAYABLE);
        assertThat(testMonthlySalary.getBillReceivable()).isEqualTo(DEFAULT_BILL_RECEIVABLE);
        assertThat(testMonthlySalary.getPayable()).isEqualTo(DEFAULT_PAYABLE);
        assertThat(testMonthlySalary.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMonthlySalary.isOnHold()).isEqualTo(DEFAULT_ON_HOLD);
        assertThat(testMonthlySalary.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMonthlySalary.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMonthlySalary.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testMonthlySalary.isVoucherGenerated()).isEqualTo(DEFAULT_VOUCHER_GENERATED);

        // Validate the MonthlySalary in Elasticsearch
        verify(mockMonthlySalarySearchRepository, times(1)).save(testMonthlySalary);
    }

    @Test
    @Transactional
    public void createMonthlySalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monthlySalaryRepository.findAll().size();

        // Create the MonthlySalary with an existing ID
        monthlySalary.setId(1L);
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeCreate);

        // Validate the MonthlySalary in Elasticsearch
        verify(mockMonthlySalarySearchRepository, times(0)).save(monthlySalary);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlySalaryRepository.findAll().size();
        // set the field null
        monthlySalary.setYear(null);

        // Create the MonthlySalary, which fails.
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlySalaryRepository.findAll().size();
        // set the field null
        monthlySalary.setMonth(null);

        // Create the MonthlySalary, which fails.
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBasicIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlySalaryRepository.findAll().size();
        // set the field null
        monthlySalary.setBasic(null);

        // Create the MonthlySalary, which fails.
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlySalaryRepository.findAll().size();
        // set the field null
        monthlySalary.setGross(null);

        // Create the MonthlySalary, which fails.
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        restMonthlySalaryMockMvc.perform(post("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonthlySalaries() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].overTimeAllowance").value(hasItem(DEFAULT_OVER_TIME_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].arrearAllowance").value(hasItem(DEFAULT_ARREAR_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].driverAllowance").value(hasItem(DEFAULT_DRIVER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].fuelLubAllowance").value(hasItem(DEFAULT_FUEL_LUB_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].mobileAllowance").value(hasItem(DEFAULT_MOBILE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].travelAllowance").value(hasItem(DEFAULT_TRAVEL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowance").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].absent").value(hasItem(DEFAULT_ABSENT)))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advanceHO").value(hasItem(DEFAULT_ADVANCE_HO.intValue())))
            .andExpect(jsonPath("$.[*].advanceFactory").value(hasItem(DEFAULT_ADVANCE_FACTORY.intValue())))
            .andExpect(jsonPath("$.[*].providentFund").value(hasItem(DEFAULT_PROVIDENT_FUND.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billPayable").value(hasItem(DEFAULT_BILL_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].billReceivable").value(hasItem(DEFAULT_BILL_RECEIVABLE.intValue())))
            .andExpect(jsonPath("$.[*].payable").value(hasItem(DEFAULT_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].onHold").value(hasItem(DEFAULT_ON_HOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].voucherGenerated").value(hasItem(DEFAULT_VOUCHER_GENERATED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get the monthlySalary
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/{id}", monthlySalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monthlySalary.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.gross").value(DEFAULT_GROSS.intValue()))
            .andExpect(jsonPath("$.houseRent").value(DEFAULT_HOUSE_RENT.intValue()))
            .andExpect(jsonPath("$.medicalAllowance").value(DEFAULT_MEDICAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.overTimeAllowance").value(DEFAULT_OVER_TIME_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.foodAllowance").value(DEFAULT_FOOD_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.arrearAllowance").value(DEFAULT_ARREAR_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.driverAllowance").value(DEFAULT_DRIVER_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.fuelLubAllowance").value(DEFAULT_FUEL_LUB_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.mobileAllowance").value(DEFAULT_MOBILE_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.travelAllowance").value(DEFAULT_TRAVEL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.otherAllowance").value(DEFAULT_OTHER_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.festivalAllowance").value(DEFAULT_FESTIVAL_ALLOWANCE.intValue()))
            .andExpect(jsonPath("$.absent").value(DEFAULT_ABSENT))
            .andExpect(jsonPath("$.fine").value(DEFAULT_FINE.intValue()))
            .andExpect(jsonPath("$.advanceHO").value(DEFAULT_ADVANCE_HO.intValue()))
            .andExpect(jsonPath("$.advanceFactory").value(DEFAULT_ADVANCE_FACTORY.intValue()))
            .andExpect(jsonPath("$.providentFund").value(DEFAULT_PROVIDENT_FUND.intValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.intValue()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.billPayable").value(DEFAULT_BILL_PAYABLE.intValue()))
            .andExpect(jsonPath("$.billReceivable").value(DEFAULT_BILL_RECEIVABLE.intValue()))
            .andExpect(jsonPath("$.payable").value(DEFAULT_PAYABLE.intValue()))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.onHold").value(DEFAULT_ON_HOLD.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.voucherGenerated").value(DEFAULT_VOUCHER_GENERATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year equals to DEFAULT_YEAR
        defaultMonthlySalaryShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year equals to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultMonthlySalaryShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the monthlySalaryList where year equals to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year is not null
        defaultMonthlySalaryShouldBeFound("year.specified=true");

        // Get all the monthlySalaryList where year is null
        defaultMonthlySalaryShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year greater than or equals to DEFAULT_YEAR
        defaultMonthlySalaryShouldBeFound("year.greaterOrEqualThan=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year greater than or equals to UPDATED_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.greaterOrEqualThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where year less than or equals to DEFAULT_YEAR
        defaultMonthlySalaryShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the monthlySalaryList where year less than or equals to UPDATED_YEAR
        defaultMonthlySalaryShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month equals to DEFAULT_MONTH
        defaultMonthlySalaryShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the monthlySalaryList where month equals to UPDATED_MONTH
        defaultMonthlySalaryShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultMonthlySalaryShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the monthlySalaryList where month equals to UPDATED_MONTH
        defaultMonthlySalaryShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where month is not null
        defaultMonthlySalaryShouldBeFound("month.specified=true");

        // Get all the monthlySalaryList where month is null
        defaultMonthlySalaryShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBasicIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where basic equals to DEFAULT_BASIC
        defaultMonthlySalaryShouldBeFound("basic.equals=" + DEFAULT_BASIC);

        // Get all the monthlySalaryList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryShouldNotBeFound("basic.equals=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBasicIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where basic in DEFAULT_BASIC or UPDATED_BASIC
        defaultMonthlySalaryShouldBeFound("basic.in=" + DEFAULT_BASIC + "," + UPDATED_BASIC);

        // Get all the monthlySalaryList where basic equals to UPDATED_BASIC
        defaultMonthlySalaryShouldNotBeFound("basic.in=" + UPDATED_BASIC);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBasicIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where basic is not null
        defaultMonthlySalaryShouldBeFound("basic.specified=true");

        // Get all the monthlySalaryList where basic is null
        defaultMonthlySalaryShouldNotBeFound("basic.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByGrossIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where gross equals to DEFAULT_GROSS
        defaultMonthlySalaryShouldBeFound("gross.equals=" + DEFAULT_GROSS);

        // Get all the monthlySalaryList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryShouldNotBeFound("gross.equals=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByGrossIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where gross in DEFAULT_GROSS or UPDATED_GROSS
        defaultMonthlySalaryShouldBeFound("gross.in=" + DEFAULT_GROSS + "," + UPDATED_GROSS);

        // Get all the monthlySalaryList where gross equals to UPDATED_GROSS
        defaultMonthlySalaryShouldNotBeFound("gross.in=" + UPDATED_GROSS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByGrossIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where gross is not null
        defaultMonthlySalaryShouldBeFound("gross.specified=true");

        // Get all the monthlySalaryList where gross is null
        defaultMonthlySalaryShouldNotBeFound("gross.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByHouseRentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where houseRent equals to DEFAULT_HOUSE_RENT
        defaultMonthlySalaryShouldBeFound("houseRent.equals=" + DEFAULT_HOUSE_RENT);

        // Get all the monthlySalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryShouldNotBeFound("houseRent.equals=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByHouseRentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where houseRent in DEFAULT_HOUSE_RENT or UPDATED_HOUSE_RENT
        defaultMonthlySalaryShouldBeFound("houseRent.in=" + DEFAULT_HOUSE_RENT + "," + UPDATED_HOUSE_RENT);

        // Get all the monthlySalaryList where houseRent equals to UPDATED_HOUSE_RENT
        defaultMonthlySalaryShouldNotBeFound("houseRent.in=" + UPDATED_HOUSE_RENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByHouseRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where houseRent is not null
        defaultMonthlySalaryShouldBeFound("houseRent.specified=true");

        // Get all the monthlySalaryList where houseRent is null
        defaultMonthlySalaryShouldNotBeFound("houseRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMedicalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where medicalAllowance equals to DEFAULT_MEDICAL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("medicalAllowance.equals=" + DEFAULT_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("medicalAllowance.equals=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMedicalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where medicalAllowance in DEFAULT_MEDICAL_ALLOWANCE or UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("medicalAllowance.in=" + DEFAULT_MEDICAL_ALLOWANCE + "," + UPDATED_MEDICAL_ALLOWANCE);

        // Get all the monthlySalaryList where medicalAllowance equals to UPDATED_MEDICAL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("medicalAllowance.in=" + UPDATED_MEDICAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMedicalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where medicalAllowance is not null
        defaultMonthlySalaryShouldBeFound("medicalAllowance.specified=true");

        // Get all the monthlySalaryList where medicalAllowance is null
        defaultMonthlySalaryShouldNotBeFound("medicalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOverTimeAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where overTimeAllowance equals to DEFAULT_OVER_TIME_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("overTimeAllowance.equals=" + DEFAULT_OVER_TIME_ALLOWANCE);

        // Get all the monthlySalaryList where overTimeAllowance equals to UPDATED_OVER_TIME_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("overTimeAllowance.equals=" + UPDATED_OVER_TIME_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOverTimeAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where overTimeAllowance in DEFAULT_OVER_TIME_ALLOWANCE or UPDATED_OVER_TIME_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("overTimeAllowance.in=" + DEFAULT_OVER_TIME_ALLOWANCE + "," + UPDATED_OVER_TIME_ALLOWANCE);

        // Get all the monthlySalaryList where overTimeAllowance equals to UPDATED_OVER_TIME_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("overTimeAllowance.in=" + UPDATED_OVER_TIME_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOverTimeAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where overTimeAllowance is not null
        defaultMonthlySalaryShouldBeFound("overTimeAllowance.specified=true");

        // Get all the monthlySalaryList where overTimeAllowance is null
        defaultMonthlySalaryShouldNotBeFound("overTimeAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFoodAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where foodAllowance equals to DEFAULT_FOOD_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("foodAllowance.equals=" + DEFAULT_FOOD_ALLOWANCE);

        // Get all the monthlySalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("foodAllowance.equals=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFoodAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where foodAllowance in DEFAULT_FOOD_ALLOWANCE or UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("foodAllowance.in=" + DEFAULT_FOOD_ALLOWANCE + "," + UPDATED_FOOD_ALLOWANCE);

        // Get all the monthlySalaryList where foodAllowance equals to UPDATED_FOOD_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("foodAllowance.in=" + UPDATED_FOOD_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFoodAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where foodAllowance is not null
        defaultMonthlySalaryShouldBeFound("foodAllowance.specified=true");

        // Get all the monthlySalaryList where foodAllowance is null
        defaultMonthlySalaryShouldNotBeFound("foodAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByArrearAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where arrearAllowance equals to DEFAULT_ARREAR_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("arrearAllowance.equals=" + DEFAULT_ARREAR_ALLOWANCE);

        // Get all the monthlySalaryList where arrearAllowance equals to UPDATED_ARREAR_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("arrearAllowance.equals=" + UPDATED_ARREAR_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByArrearAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where arrearAllowance in DEFAULT_ARREAR_ALLOWANCE or UPDATED_ARREAR_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("arrearAllowance.in=" + DEFAULT_ARREAR_ALLOWANCE + "," + UPDATED_ARREAR_ALLOWANCE);

        // Get all the monthlySalaryList where arrearAllowance equals to UPDATED_ARREAR_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("arrearAllowance.in=" + UPDATED_ARREAR_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByArrearAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where arrearAllowance is not null
        defaultMonthlySalaryShouldBeFound("arrearAllowance.specified=true");

        // Get all the monthlySalaryList where arrearAllowance is null
        defaultMonthlySalaryShouldNotBeFound("arrearAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByDriverAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where driverAllowance equals to DEFAULT_DRIVER_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("driverAllowance.equals=" + DEFAULT_DRIVER_ALLOWANCE);

        // Get all the monthlySalaryList where driverAllowance equals to UPDATED_DRIVER_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("driverAllowance.equals=" + UPDATED_DRIVER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByDriverAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where driverAllowance in DEFAULT_DRIVER_ALLOWANCE or UPDATED_DRIVER_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("driverAllowance.in=" + DEFAULT_DRIVER_ALLOWANCE + "," + UPDATED_DRIVER_ALLOWANCE);

        // Get all the monthlySalaryList where driverAllowance equals to UPDATED_DRIVER_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("driverAllowance.in=" + UPDATED_DRIVER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByDriverAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where driverAllowance is not null
        defaultMonthlySalaryShouldBeFound("driverAllowance.specified=true");

        // Get all the monthlySalaryList where driverAllowance is null
        defaultMonthlySalaryShouldNotBeFound("driverAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFuelLubAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fuelLubAllowance equals to DEFAULT_FUEL_LUB_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("fuelLubAllowance.equals=" + DEFAULT_FUEL_LUB_ALLOWANCE);

        // Get all the monthlySalaryList where fuelLubAllowance equals to UPDATED_FUEL_LUB_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("fuelLubAllowance.equals=" + UPDATED_FUEL_LUB_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFuelLubAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fuelLubAllowance in DEFAULT_FUEL_LUB_ALLOWANCE or UPDATED_FUEL_LUB_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("fuelLubAllowance.in=" + DEFAULT_FUEL_LUB_ALLOWANCE + "," + UPDATED_FUEL_LUB_ALLOWANCE);

        // Get all the monthlySalaryList where fuelLubAllowance equals to UPDATED_FUEL_LUB_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("fuelLubAllowance.in=" + UPDATED_FUEL_LUB_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFuelLubAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fuelLubAllowance is not null
        defaultMonthlySalaryShouldBeFound("fuelLubAllowance.specified=true");

        // Get all the monthlySalaryList where fuelLubAllowance is null
        defaultMonthlySalaryShouldNotBeFound("fuelLubAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMobileAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where mobileAllowance equals to DEFAULT_MOBILE_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("mobileAllowance.equals=" + DEFAULT_MOBILE_ALLOWANCE);

        // Get all the monthlySalaryList where mobileAllowance equals to UPDATED_MOBILE_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("mobileAllowance.equals=" + UPDATED_MOBILE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMobileAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where mobileAllowance in DEFAULT_MOBILE_ALLOWANCE or UPDATED_MOBILE_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("mobileAllowance.in=" + DEFAULT_MOBILE_ALLOWANCE + "," + UPDATED_MOBILE_ALLOWANCE);

        // Get all the monthlySalaryList where mobileAllowance equals to UPDATED_MOBILE_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("mobileAllowance.in=" + UPDATED_MOBILE_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByMobileAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where mobileAllowance is not null
        defaultMonthlySalaryShouldBeFound("mobileAllowance.specified=true");

        // Get all the monthlySalaryList where mobileAllowance is null
        defaultMonthlySalaryShouldNotBeFound("mobileAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTravelAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where travelAllowance equals to DEFAULT_TRAVEL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("travelAllowance.equals=" + DEFAULT_TRAVEL_ALLOWANCE);

        // Get all the monthlySalaryList where travelAllowance equals to UPDATED_TRAVEL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("travelAllowance.equals=" + UPDATED_TRAVEL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTravelAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where travelAllowance in DEFAULT_TRAVEL_ALLOWANCE or UPDATED_TRAVEL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("travelAllowance.in=" + DEFAULT_TRAVEL_ALLOWANCE + "," + UPDATED_TRAVEL_ALLOWANCE);

        // Get all the monthlySalaryList where travelAllowance equals to UPDATED_TRAVEL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("travelAllowance.in=" + UPDATED_TRAVEL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTravelAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where travelAllowance is not null
        defaultMonthlySalaryShouldBeFound("travelAllowance.specified=true");

        // Get all the monthlySalaryList where travelAllowance is null
        defaultMonthlySalaryShouldNotBeFound("travelAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOtherAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where otherAllowance equals to DEFAULT_OTHER_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("otherAllowance.equals=" + DEFAULT_OTHER_ALLOWANCE);

        // Get all the monthlySalaryList where otherAllowance equals to UPDATED_OTHER_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("otherAllowance.equals=" + UPDATED_OTHER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOtherAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where otherAllowance in DEFAULT_OTHER_ALLOWANCE or UPDATED_OTHER_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("otherAllowance.in=" + DEFAULT_OTHER_ALLOWANCE + "," + UPDATED_OTHER_ALLOWANCE);

        // Get all the monthlySalaryList where otherAllowance equals to UPDATED_OTHER_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("otherAllowance.in=" + UPDATED_OTHER_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOtherAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where otherAllowance is not null
        defaultMonthlySalaryShouldBeFound("otherAllowance.specified=true");

        // Get all the monthlySalaryList where otherAllowance is null
        defaultMonthlySalaryShouldNotBeFound("otherAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFestivalAllowanceIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where festivalAllowance equals to DEFAULT_FESTIVAL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("festivalAllowance.equals=" + DEFAULT_FESTIVAL_ALLOWANCE);

        // Get all the monthlySalaryList where festivalAllowance equals to UPDATED_FESTIVAL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("festivalAllowance.equals=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFestivalAllowanceIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where festivalAllowance in DEFAULT_FESTIVAL_ALLOWANCE or UPDATED_FESTIVAL_ALLOWANCE
        defaultMonthlySalaryShouldBeFound("festivalAllowance.in=" + DEFAULT_FESTIVAL_ALLOWANCE + "," + UPDATED_FESTIVAL_ALLOWANCE);

        // Get all the monthlySalaryList where festivalAllowance equals to UPDATED_FESTIVAL_ALLOWANCE
        defaultMonthlySalaryShouldNotBeFound("festivalAllowance.in=" + UPDATED_FESTIVAL_ALLOWANCE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFestivalAllowanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where festivalAllowance is not null
        defaultMonthlySalaryShouldBeFound("festivalAllowance.specified=true");

        // Get all the monthlySalaryList where festivalAllowance is null
        defaultMonthlySalaryShouldNotBeFound("festivalAllowance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAbsentIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where absent equals to DEFAULT_ABSENT
        defaultMonthlySalaryShouldBeFound("absent.equals=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryList where absent equals to UPDATED_ABSENT
        defaultMonthlySalaryShouldNotBeFound("absent.equals=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAbsentIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where absent in DEFAULT_ABSENT or UPDATED_ABSENT
        defaultMonthlySalaryShouldBeFound("absent.in=" + DEFAULT_ABSENT + "," + UPDATED_ABSENT);

        // Get all the monthlySalaryList where absent equals to UPDATED_ABSENT
        defaultMonthlySalaryShouldNotBeFound("absent.in=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAbsentIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where absent is not null
        defaultMonthlySalaryShouldBeFound("absent.specified=true");

        // Get all the monthlySalaryList where absent is null
        defaultMonthlySalaryShouldNotBeFound("absent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAbsentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where absent greater than or equals to DEFAULT_ABSENT
        defaultMonthlySalaryShouldBeFound("absent.greaterOrEqualThan=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryList where absent greater than or equals to UPDATED_ABSENT
        defaultMonthlySalaryShouldNotBeFound("absent.greaterOrEqualThan=" + UPDATED_ABSENT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAbsentIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where absent less than or equals to DEFAULT_ABSENT
        defaultMonthlySalaryShouldNotBeFound("absent.lessThan=" + DEFAULT_ABSENT);

        // Get all the monthlySalaryList where absent less than or equals to UPDATED_ABSENT
        defaultMonthlySalaryShouldBeFound("absent.lessThan=" + UPDATED_ABSENT);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByFineIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fine equals to DEFAULT_FINE
        defaultMonthlySalaryShouldBeFound("fine.equals=" + DEFAULT_FINE);

        // Get all the monthlySalaryList where fine equals to UPDATED_FINE
        defaultMonthlySalaryShouldNotBeFound("fine.equals=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFineIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fine in DEFAULT_FINE or UPDATED_FINE
        defaultMonthlySalaryShouldBeFound("fine.in=" + DEFAULT_FINE + "," + UPDATED_FINE);

        // Get all the monthlySalaryList where fine equals to UPDATED_FINE
        defaultMonthlySalaryShouldNotBeFound("fine.in=" + UPDATED_FINE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByFineIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where fine is not null
        defaultMonthlySalaryShouldBeFound("fine.specified=true");

        // Get all the monthlySalaryList where fine is null
        defaultMonthlySalaryShouldNotBeFound("fine.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceHOIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceHO equals to DEFAULT_ADVANCE_HO
        defaultMonthlySalaryShouldBeFound("advanceHO.equals=" + DEFAULT_ADVANCE_HO);

        // Get all the monthlySalaryList where advanceHO equals to UPDATED_ADVANCE_HO
        defaultMonthlySalaryShouldNotBeFound("advanceHO.equals=" + UPDATED_ADVANCE_HO);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceHOIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceHO in DEFAULT_ADVANCE_HO or UPDATED_ADVANCE_HO
        defaultMonthlySalaryShouldBeFound("advanceHO.in=" + DEFAULT_ADVANCE_HO + "," + UPDATED_ADVANCE_HO);

        // Get all the monthlySalaryList where advanceHO equals to UPDATED_ADVANCE_HO
        defaultMonthlySalaryShouldNotBeFound("advanceHO.in=" + UPDATED_ADVANCE_HO);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceHOIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceHO is not null
        defaultMonthlySalaryShouldBeFound("advanceHO.specified=true");

        // Get all the monthlySalaryList where advanceHO is null
        defaultMonthlySalaryShouldNotBeFound("advanceHO.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceFactoryIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceFactory equals to DEFAULT_ADVANCE_FACTORY
        defaultMonthlySalaryShouldBeFound("advanceFactory.equals=" + DEFAULT_ADVANCE_FACTORY);

        // Get all the monthlySalaryList where advanceFactory equals to UPDATED_ADVANCE_FACTORY
        defaultMonthlySalaryShouldNotBeFound("advanceFactory.equals=" + UPDATED_ADVANCE_FACTORY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceFactoryIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceFactory in DEFAULT_ADVANCE_FACTORY or UPDATED_ADVANCE_FACTORY
        defaultMonthlySalaryShouldBeFound("advanceFactory.in=" + DEFAULT_ADVANCE_FACTORY + "," + UPDATED_ADVANCE_FACTORY);

        // Get all the monthlySalaryList where advanceFactory equals to UPDATED_ADVANCE_FACTORY
        defaultMonthlySalaryShouldNotBeFound("advanceFactory.in=" + UPDATED_ADVANCE_FACTORY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByAdvanceFactoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where advanceFactory is not null
        defaultMonthlySalaryShouldBeFound("advanceFactory.specified=true");

        // Get all the monthlySalaryList where advanceFactory is null
        defaultMonthlySalaryShouldNotBeFound("advanceFactory.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByProvidentFundIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where providentFund equals to DEFAULT_PROVIDENT_FUND
        defaultMonthlySalaryShouldBeFound("providentFund.equals=" + DEFAULT_PROVIDENT_FUND);

        // Get all the monthlySalaryList where providentFund equals to UPDATED_PROVIDENT_FUND
        defaultMonthlySalaryShouldNotBeFound("providentFund.equals=" + UPDATED_PROVIDENT_FUND);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByProvidentFundIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where providentFund in DEFAULT_PROVIDENT_FUND or UPDATED_PROVIDENT_FUND
        defaultMonthlySalaryShouldBeFound("providentFund.in=" + DEFAULT_PROVIDENT_FUND + "," + UPDATED_PROVIDENT_FUND);

        // Get all the monthlySalaryList where providentFund equals to UPDATED_PROVIDENT_FUND
        defaultMonthlySalaryShouldNotBeFound("providentFund.in=" + UPDATED_PROVIDENT_FUND);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByProvidentFundIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where providentFund is not null
        defaultMonthlySalaryShouldBeFound("providentFund.specified=true");

        // Get all the monthlySalaryList where providentFund is null
        defaultMonthlySalaryShouldNotBeFound("providentFund.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where tax equals to DEFAULT_TAX
        defaultMonthlySalaryShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the monthlySalaryList where tax equals to UPDATED_TAX
        defaultMonthlySalaryShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultMonthlySalaryShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the monthlySalaryList where tax equals to UPDATED_TAX
        defaultMonthlySalaryShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where tax is not null
        defaultMonthlySalaryShouldBeFound("tax.specified=true");

        // Get all the monthlySalaryList where tax is null
        defaultMonthlySalaryShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultMonthlySalaryShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the monthlySalaryList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultMonthlySalaryShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultMonthlySalaryShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the monthlySalaryList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultMonthlySalaryShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where loanAmount is not null
        defaultMonthlySalaryShouldBeFound("loanAmount.specified=true");

        // Get all the monthlySalaryList where loanAmount is null
        defaultMonthlySalaryShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billPayable equals to DEFAULT_BILL_PAYABLE
        defaultMonthlySalaryShouldBeFound("billPayable.equals=" + DEFAULT_BILL_PAYABLE);

        // Get all the monthlySalaryList where billPayable equals to UPDATED_BILL_PAYABLE
        defaultMonthlySalaryShouldNotBeFound("billPayable.equals=" + UPDATED_BILL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillPayableIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billPayable in DEFAULT_BILL_PAYABLE or UPDATED_BILL_PAYABLE
        defaultMonthlySalaryShouldBeFound("billPayable.in=" + DEFAULT_BILL_PAYABLE + "," + UPDATED_BILL_PAYABLE);

        // Get all the monthlySalaryList where billPayable equals to UPDATED_BILL_PAYABLE
        defaultMonthlySalaryShouldNotBeFound("billPayable.in=" + UPDATED_BILL_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billPayable is not null
        defaultMonthlySalaryShouldBeFound("billPayable.specified=true");

        // Get all the monthlySalaryList where billPayable is null
        defaultMonthlySalaryShouldNotBeFound("billPayable.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillReceivableIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billReceivable equals to DEFAULT_BILL_RECEIVABLE
        defaultMonthlySalaryShouldBeFound("billReceivable.equals=" + DEFAULT_BILL_RECEIVABLE);

        // Get all the monthlySalaryList where billReceivable equals to UPDATED_BILL_RECEIVABLE
        defaultMonthlySalaryShouldNotBeFound("billReceivable.equals=" + UPDATED_BILL_RECEIVABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillReceivableIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billReceivable in DEFAULT_BILL_RECEIVABLE or UPDATED_BILL_RECEIVABLE
        defaultMonthlySalaryShouldBeFound("billReceivable.in=" + DEFAULT_BILL_RECEIVABLE + "," + UPDATED_BILL_RECEIVABLE);

        // Get all the monthlySalaryList where billReceivable equals to UPDATED_BILL_RECEIVABLE
        defaultMonthlySalaryShouldNotBeFound("billReceivable.in=" + UPDATED_BILL_RECEIVABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByBillReceivableIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where billReceivable is not null
        defaultMonthlySalaryShouldBeFound("billReceivable.specified=true");

        // Get all the monthlySalaryList where billReceivable is null
        defaultMonthlySalaryShouldNotBeFound("billReceivable.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByPayableIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where payable equals to DEFAULT_PAYABLE
        defaultMonthlySalaryShouldBeFound("payable.equals=" + DEFAULT_PAYABLE);

        // Get all the monthlySalaryList where payable equals to UPDATED_PAYABLE
        defaultMonthlySalaryShouldNotBeFound("payable.equals=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByPayableIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where payable in DEFAULT_PAYABLE or UPDATED_PAYABLE
        defaultMonthlySalaryShouldBeFound("payable.in=" + DEFAULT_PAYABLE + "," + UPDATED_PAYABLE);

        // Get all the monthlySalaryList where payable equals to UPDATED_PAYABLE
        defaultMonthlySalaryShouldNotBeFound("payable.in=" + UPDATED_PAYABLE);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByPayableIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where payable is not null
        defaultMonthlySalaryShouldBeFound("payable.specified=true");

        // Get all the monthlySalaryList where payable is null
        defaultMonthlySalaryShouldNotBeFound("payable.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where approved equals to DEFAULT_APPROVED
        defaultMonthlySalaryShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the monthlySalaryList where approved equals to UPDATED_APPROVED
        defaultMonthlySalaryShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMonthlySalaryShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the monthlySalaryList where approved equals to UPDATED_APPROVED
        defaultMonthlySalaryShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where approved is not null
        defaultMonthlySalaryShouldBeFound("approved.specified=true");

        // Get all the monthlySalaryList where approved is null
        defaultMonthlySalaryShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOnHoldIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where onHold equals to DEFAULT_ON_HOLD
        defaultMonthlySalaryShouldBeFound("onHold.equals=" + DEFAULT_ON_HOLD);

        // Get all the monthlySalaryList where onHold equals to UPDATED_ON_HOLD
        defaultMonthlySalaryShouldNotBeFound("onHold.equals=" + UPDATED_ON_HOLD);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOnHoldIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where onHold in DEFAULT_ON_HOLD or UPDATED_ON_HOLD
        defaultMonthlySalaryShouldBeFound("onHold.in=" + DEFAULT_ON_HOLD + "," + UPDATED_ON_HOLD);

        // Get all the monthlySalaryList where onHold equals to UPDATED_ON_HOLD
        defaultMonthlySalaryShouldNotBeFound("onHold.in=" + UPDATED_ON_HOLD);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByOnHoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where onHold is not null
        defaultMonthlySalaryShouldBeFound("onHold.specified=true");

        // Get all the monthlySalaryList where onHold is null
        defaultMonthlySalaryShouldNotBeFound("onHold.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status equals to DEFAULT_STATUS
        defaultMonthlySalaryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the monthlySalaryList where status equals to UPDATED_STATUS
        defaultMonthlySalaryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMonthlySalaryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the monthlySalaryList where status equals to UPDATED_STATUS
        defaultMonthlySalaryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where status is not null
        defaultMonthlySalaryShouldBeFound("status.specified=true");

        // Get all the monthlySalaryList where status is null
        defaultMonthlySalaryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultMonthlySalaryShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the monthlySalaryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMonthlySalaryShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultMonthlySalaryShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the monthlySalaryList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMonthlySalaryShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedBy is not null
        defaultMonthlySalaryShouldBeFound("modifiedBy.specified=true");

        // Get all the monthlySalaryList where modifiedBy is null
        defaultMonthlySalaryShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultMonthlySalaryShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlySalaryList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMonthlySalaryShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultMonthlySalaryShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the monthlySalaryList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMonthlySalaryShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedOn is not null
        defaultMonthlySalaryShouldBeFound("modifiedOn.specified=true");

        // Get all the monthlySalaryList where modifiedOn is null
        defaultMonthlySalaryShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultMonthlySalaryShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlySalaryList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultMonthlySalaryShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultMonthlySalaryShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the monthlySalaryList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultMonthlySalaryShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByVoucherGeneratedIsEqualToSomething() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where voucherGenerated equals to DEFAULT_VOUCHER_GENERATED
        defaultMonthlySalaryShouldBeFound("voucherGenerated.equals=" + DEFAULT_VOUCHER_GENERATED);

        // Get all the monthlySalaryList where voucherGenerated equals to UPDATED_VOUCHER_GENERATED
        defaultMonthlySalaryShouldNotBeFound("voucherGenerated.equals=" + UPDATED_VOUCHER_GENERATED);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByVoucherGeneratedIsInShouldWork() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where voucherGenerated in DEFAULT_VOUCHER_GENERATED or UPDATED_VOUCHER_GENERATED
        defaultMonthlySalaryShouldBeFound("voucherGenerated.in=" + DEFAULT_VOUCHER_GENERATED + "," + UPDATED_VOUCHER_GENERATED);

        // Get all the monthlySalaryList where voucherGenerated equals to UPDATED_VOUCHER_GENERATED
        defaultMonthlySalaryShouldNotBeFound("voucherGenerated.in=" + UPDATED_VOUCHER_GENERATED);
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByVoucherGeneratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        // Get all the monthlySalaryList where voucherGenerated is not null
        defaultMonthlySalaryShouldBeFound("voucherGenerated.specified=true");

        // Get all the monthlySalaryList where voucherGenerated is null
        defaultMonthlySalaryShouldNotBeFound("voucherGenerated.specified=false");
    }

    @Test
    @Transactional
    public void getAllMonthlySalariesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        SalaryMessages comments = SalaryMessagesResourceIntTest.createEntity(em);
        em.persist(comments);
        em.flush();
        monthlySalary.addComments(comments);
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        Long commentsId = comments.getId();

        // Get all the monthlySalaryList where comments equals to commentsId
        defaultMonthlySalaryShouldBeFound("commentsId.equals=" + commentsId);

        // Get all the monthlySalaryList where comments equals to commentsId + 1
        defaultMonthlySalaryShouldNotBeFound("commentsId.equals=" + (commentsId + 1));
    }


    @Test
    @Transactional
    public void getAllMonthlySalariesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        monthlySalary.setEmployee(employee);
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        Long employeeId = employee.getId();

        // Get all the monthlySalaryList where employee equals to employeeId
        defaultMonthlySalaryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the monthlySalaryList where employee equals to employeeId + 1
        defaultMonthlySalaryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMonthlySalaryShouldBeFound(String filter) throws Exception {
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].overTimeAllowance").value(hasItem(DEFAULT_OVER_TIME_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].arrearAllowance").value(hasItem(DEFAULT_ARREAR_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].driverAllowance").value(hasItem(DEFAULT_DRIVER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].fuelLubAllowance").value(hasItem(DEFAULT_FUEL_LUB_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].mobileAllowance").value(hasItem(DEFAULT_MOBILE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].travelAllowance").value(hasItem(DEFAULT_TRAVEL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowance").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].absent").value(hasItem(DEFAULT_ABSENT)))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advanceHO").value(hasItem(DEFAULT_ADVANCE_HO.intValue())))
            .andExpect(jsonPath("$.[*].advanceFactory").value(hasItem(DEFAULT_ADVANCE_FACTORY.intValue())))
            .andExpect(jsonPath("$.[*].providentFund").value(hasItem(DEFAULT_PROVIDENT_FUND.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billPayable").value(hasItem(DEFAULT_BILL_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].billReceivable").value(hasItem(DEFAULT_BILL_RECEIVABLE.intValue())))
            .andExpect(jsonPath("$.[*].payable").value(hasItem(DEFAULT_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].onHold").value(hasItem(DEFAULT_ON_HOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].voucherGenerated").value(hasItem(DEFAULT_VOUCHER_GENERATED.booleanValue())));

        // Check, that the count call also returns 1
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMonthlySalaryShouldNotBeFound(String filter) throws Exception {
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMonthlySalary() throws Exception {
        // Get the monthlySalary
        restMonthlySalaryMockMvc.perform(get("/api/monthly-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        int databaseSizeBeforeUpdate = monthlySalaryRepository.findAll().size();

        // Update the monthlySalary
        MonthlySalary updatedMonthlySalary = monthlySalaryRepository.findById(monthlySalary.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlySalary are not directly saved in db
        em.detach(updatedMonthlySalary);
        updatedMonthlySalary
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .basic(UPDATED_BASIC)
            .gross(UPDATED_GROSS)
            .houseRent(UPDATED_HOUSE_RENT)
            .medicalAllowance(UPDATED_MEDICAL_ALLOWANCE)
            .overTimeAllowance(UPDATED_OVER_TIME_ALLOWANCE)
            .foodAllowance(UPDATED_FOOD_ALLOWANCE)
            .arrearAllowance(UPDATED_ARREAR_ALLOWANCE)
            .driverAllowance(UPDATED_DRIVER_ALLOWANCE)
            .fuelLubAllowance(UPDATED_FUEL_LUB_ALLOWANCE)
            .mobileAllowance(UPDATED_MOBILE_ALLOWANCE)
            .travelAllowance(UPDATED_TRAVEL_ALLOWANCE)
            .otherAllowance(UPDATED_OTHER_ALLOWANCE)
            .festivalAllowance(UPDATED_FESTIVAL_ALLOWANCE)
            .absent(UPDATED_ABSENT)
            .fine(UPDATED_FINE)
            .advanceHO(UPDATED_ADVANCE_HO)
            .advanceFactory(UPDATED_ADVANCE_FACTORY)
            .providentFund(UPDATED_PROVIDENT_FUND)
            .tax(UPDATED_TAX)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .billPayable(UPDATED_BILL_PAYABLE)
            .billReceivable(UPDATED_BILL_RECEIVABLE)
            .payable(UPDATED_PAYABLE)
            .approved(UPDATED_APPROVED)
            .onHold(UPDATED_ON_HOLD)
            .status(UPDATED_STATUS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .voucherGenerated(UPDATED_VOUCHER_GENERATED);
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(updatedMonthlySalary);

        restMonthlySalaryMockMvc.perform(put("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isOk());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeUpdate);
        MonthlySalary testMonthlySalary = monthlySalaryList.get(monthlySalaryList.size() - 1);
        assertThat(testMonthlySalary.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMonthlySalary.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testMonthlySalary.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testMonthlySalary.getGross()).isEqualTo(UPDATED_GROSS);
        assertThat(testMonthlySalary.getHouseRent()).isEqualTo(UPDATED_HOUSE_RENT);
        assertThat(testMonthlySalary.getMedicalAllowance()).isEqualTo(UPDATED_MEDICAL_ALLOWANCE);
        assertThat(testMonthlySalary.getOverTimeAllowance()).isEqualTo(UPDATED_OVER_TIME_ALLOWANCE);
        assertThat(testMonthlySalary.getFoodAllowance()).isEqualTo(UPDATED_FOOD_ALLOWANCE);
        assertThat(testMonthlySalary.getArrearAllowance()).isEqualTo(UPDATED_ARREAR_ALLOWANCE);
        assertThat(testMonthlySalary.getDriverAllowance()).isEqualTo(UPDATED_DRIVER_ALLOWANCE);
        assertThat(testMonthlySalary.getFuelLubAllowance()).isEqualTo(UPDATED_FUEL_LUB_ALLOWANCE);
        assertThat(testMonthlySalary.getMobileAllowance()).isEqualTo(UPDATED_MOBILE_ALLOWANCE);
        assertThat(testMonthlySalary.getTravelAllowance()).isEqualTo(UPDATED_TRAVEL_ALLOWANCE);
        assertThat(testMonthlySalary.getOtherAllowance()).isEqualTo(UPDATED_OTHER_ALLOWANCE);
        assertThat(testMonthlySalary.getFestivalAllowance()).isEqualTo(UPDATED_FESTIVAL_ALLOWANCE);
        assertThat(testMonthlySalary.getAbsent()).isEqualTo(UPDATED_ABSENT);
        assertThat(testMonthlySalary.getFine()).isEqualTo(UPDATED_FINE);
        assertThat(testMonthlySalary.getAdvanceHO()).isEqualTo(UPDATED_ADVANCE_HO);
        assertThat(testMonthlySalary.getAdvanceFactory()).isEqualTo(UPDATED_ADVANCE_FACTORY);
        assertThat(testMonthlySalary.getProvidentFund()).isEqualTo(UPDATED_PROVIDENT_FUND);
        assertThat(testMonthlySalary.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testMonthlySalary.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testMonthlySalary.getBillPayable()).isEqualTo(UPDATED_BILL_PAYABLE);
        assertThat(testMonthlySalary.getBillReceivable()).isEqualTo(UPDATED_BILL_RECEIVABLE);
        assertThat(testMonthlySalary.getPayable()).isEqualTo(UPDATED_PAYABLE);
        assertThat(testMonthlySalary.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMonthlySalary.isOnHold()).isEqualTo(UPDATED_ON_HOLD);
        assertThat(testMonthlySalary.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMonthlySalary.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMonthlySalary.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testMonthlySalary.isVoucherGenerated()).isEqualTo(UPDATED_VOUCHER_GENERATED);

        // Validate the MonthlySalary in Elasticsearch
        verify(mockMonthlySalarySearchRepository, times(1)).save(testMonthlySalary);
    }

    @Test
    @Transactional
    public void updateNonExistingMonthlySalary() throws Exception {
        int databaseSizeBeforeUpdate = monthlySalaryRepository.findAll().size();

        // Create the MonthlySalary
        MonthlySalaryDTO monthlySalaryDTO = monthlySalaryMapper.toDto(monthlySalary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlySalaryMockMvc.perform(put("/api/monthly-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monthlySalaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MonthlySalary in the database
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MonthlySalary in Elasticsearch
        verify(mockMonthlySalarySearchRepository, times(0)).save(monthlySalary);
    }

    @Test
    @Transactional
    public void deleteMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);

        int databaseSizeBeforeDelete = monthlySalaryRepository.findAll().size();

        // Delete the monthlySalary
        restMonthlySalaryMockMvc.perform(delete("/api/monthly-salaries/{id}", monthlySalary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MonthlySalary> monthlySalaryList = monthlySalaryRepository.findAll();
        assertThat(monthlySalaryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MonthlySalary in Elasticsearch
        verify(mockMonthlySalarySearchRepository, times(1)).deleteById(monthlySalary.getId());
    }

    @Test
    @Transactional
    public void searchMonthlySalary() throws Exception {
        // Initialize the database
        monthlySalaryRepository.saveAndFlush(monthlySalary);
        when(mockMonthlySalarySearchRepository.search(queryStringQuery("id:" + monthlySalary.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(monthlySalary), PageRequest.of(0, 1), 1));
        // Search the monthlySalary
        restMonthlySalaryMockMvc.perform(get("/api/_search/monthly-salaries?query=id:" + monthlySalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlySalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
            .andExpect(jsonPath("$.[*].gross").value(hasItem(DEFAULT_GROSS.intValue())))
            .andExpect(jsonPath("$.[*].houseRent").value(hasItem(DEFAULT_HOUSE_RENT.intValue())))
            .andExpect(jsonPath("$.[*].medicalAllowance").value(hasItem(DEFAULT_MEDICAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].overTimeAllowance").value(hasItem(DEFAULT_OVER_TIME_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].foodAllowance").value(hasItem(DEFAULT_FOOD_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].arrearAllowance").value(hasItem(DEFAULT_ARREAR_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].driverAllowance").value(hasItem(DEFAULT_DRIVER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].fuelLubAllowance").value(hasItem(DEFAULT_FUEL_LUB_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].mobileAllowance").value(hasItem(DEFAULT_MOBILE_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].travelAllowance").value(hasItem(DEFAULT_TRAVEL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].otherAllowance").value(hasItem(DEFAULT_OTHER_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].festivalAllowance").value(hasItem(DEFAULT_FESTIVAL_ALLOWANCE.intValue())))
            .andExpect(jsonPath("$.[*].absent").value(hasItem(DEFAULT_ABSENT)))
            .andExpect(jsonPath("$.[*].fine").value(hasItem(DEFAULT_FINE.intValue())))
            .andExpect(jsonPath("$.[*].advanceHO").value(hasItem(DEFAULT_ADVANCE_HO.intValue())))
            .andExpect(jsonPath("$.[*].advanceFactory").value(hasItem(DEFAULT_ADVANCE_FACTORY.intValue())))
            .andExpect(jsonPath("$.[*].providentFund").value(hasItem(DEFAULT_PROVIDENT_FUND.intValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billPayable").value(hasItem(DEFAULT_BILL_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].billReceivable").value(hasItem(DEFAULT_BILL_RECEIVABLE.intValue())))
            .andExpect(jsonPath("$.[*].payable").value(hasItem(DEFAULT_PAYABLE.intValue())))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].onHold").value(hasItem(DEFAULT_ON_HOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].voucherGenerated").value(hasItem(DEFAULT_VOUCHER_GENERATED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalary.class);
        MonthlySalary monthlySalary1 = new MonthlySalary();
        monthlySalary1.setId(1L);
        MonthlySalary monthlySalary2 = new MonthlySalary();
        monthlySalary2.setId(monthlySalary1.getId());
        assertThat(monthlySalary1).isEqualTo(monthlySalary2);
        monthlySalary2.setId(2L);
        assertThat(monthlySalary1).isNotEqualTo(monthlySalary2);
        monthlySalary1.setId(null);
        assertThat(monthlySalary1).isNotEqualTo(monthlySalary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlySalaryDTO.class);
        MonthlySalaryDTO monthlySalaryDTO1 = new MonthlySalaryDTO();
        monthlySalaryDTO1.setId(1L);
        MonthlySalaryDTO monthlySalaryDTO2 = new MonthlySalaryDTO();
        assertThat(monthlySalaryDTO1).isNotEqualTo(monthlySalaryDTO2);
        monthlySalaryDTO2.setId(monthlySalaryDTO1.getId());
        assertThat(monthlySalaryDTO1).isEqualTo(monthlySalaryDTO2);
        monthlySalaryDTO2.setId(2L);
        assertThat(monthlySalaryDTO1).isNotEqualTo(monthlySalaryDTO2);
        monthlySalaryDTO1.setId(null);
        assertThat(monthlySalaryDTO1).isNotEqualTo(monthlySalaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(monthlySalaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(monthlySalaryMapper.fromId(null)).isNull();
    }
}
