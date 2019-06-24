package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Bill;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.BillRepository;
import org.soptorshi.repository.search.BillSearchRepository;
import org.soptorshi.service.BillService;
import org.soptorshi.service.dto.BillDTO;
import org.soptorshi.service.mapper.BillMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.BillCriteria;
import org.soptorshi.service.BillQueryService;

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

import org.soptorshi.domain.enumeration.BillCategory;
/**
 * Test class for the BillResource REST controller.
 *
 * @see BillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class BillResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BillCategory DEFAULT_BILL_CATEGORY = BillCategory.PAYABLE;
    private static final BillCategory UPDATED_BILL_CATEGORY = BillCategory.RECEIVABLE;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillService billService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.BillSearchRepositoryMockConfiguration
     */
    @Autowired
    private BillSearchRepository mockBillSearchRepository;

    @Autowired
    private BillQueryService billQueryService;

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

    private MockMvc restBillMockMvc;

    private Bill bill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillResource billResource = new BillResource(billService, billQueryService);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource)
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
    public static Bill createEntity(EntityManager em) {
        Bill bill = new Bill()
            .amount(DEFAULT_AMOUNT)
            .billCategory(DEFAULT_BILL_CATEGORY)
            .reason(DEFAULT_REASON)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return bill;
    }

    @Before
    public void initTest() {
        bill = createEntity(em);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBill.getBillCategory()).isEqualTo(DEFAULT_BILL_CATEGORY);
        assertThat(testBill.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testBill.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testBill.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    @Transactional
    public void createBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill with an existing ID
        bill.setId(1L);
        BillDTO billDTO = billMapper.toDto(bill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setAmount(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billCategory").value(hasItem(DEFAULT_BILL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.billCategory").value(DEFAULT_BILL_CATEGORY.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllBillsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amount equals to DEFAULT_AMOUNT
        defaultBillShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the billList where amount equals to UPDATED_AMOUNT
        defaultBillShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultBillShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the billList where amount equals to UPDATED_AMOUNT
        defaultBillShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBillsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where amount is not null
        defaultBillShouldBeFound("amount.specified=true");

        // Get all the billList where amount is null
        defaultBillShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByBillCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billCategory equals to DEFAULT_BILL_CATEGORY
        defaultBillShouldBeFound("billCategory.equals=" + DEFAULT_BILL_CATEGORY);

        // Get all the billList where billCategory equals to UPDATED_BILL_CATEGORY
        defaultBillShouldNotBeFound("billCategory.equals=" + UPDATED_BILL_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllBillsByBillCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billCategory in DEFAULT_BILL_CATEGORY or UPDATED_BILL_CATEGORY
        defaultBillShouldBeFound("billCategory.in=" + DEFAULT_BILL_CATEGORY + "," + UPDATED_BILL_CATEGORY);

        // Get all the billList where billCategory equals to UPDATED_BILL_CATEGORY
        defaultBillShouldNotBeFound("billCategory.in=" + UPDATED_BILL_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllBillsByBillCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where billCategory is not null
        defaultBillShouldBeFound("billCategory.specified=true");

        // Get all the billList where billCategory is null
        defaultBillShouldNotBeFound("billCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultBillShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the billList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultBillShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultBillShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the billList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultBillShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedBy is not null
        defaultBillShouldBeFound("modifiedBy.specified=true");

        // Get all the billList where modifiedBy is null
        defaultBillShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultBillShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the billList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultBillShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultBillShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the billList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultBillShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedDate is not null
        defaultBillShouldBeFound("modifiedDate.specified=true");

        // Get all the billList where modifiedDate is null
        defaultBillShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedDate greater than or equals to DEFAULT_MODIFIED_DATE
        defaultBillShouldBeFound("modifiedDate.greaterOrEqualThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the billList where modifiedDate greater than or equals to UPDATED_MODIFIED_DATE
        defaultBillShouldNotBeFound("modifiedDate.greaterOrEqualThan=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllBillsByModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList where modifiedDate less than or equals to DEFAULT_MODIFIED_DATE
        defaultBillShouldNotBeFound("modifiedDate.lessThan=" + DEFAULT_MODIFIED_DATE);

        // Get all the billList where modifiedDate less than or equals to UPDATED_MODIFIED_DATE
        defaultBillShouldBeFound("modifiedDate.lessThan=" + UPDATED_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllBillsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        bill.setEmployee(employee);
        billRepository.saveAndFlush(bill);
        Long employeeId = employee.getId();

        // Get all the billList where employee equals to employeeId
        defaultBillShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the billList where employee equals to employeeId + 1
        defaultBillShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBillShouldBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billCategory").value(hasItem(DEFAULT_BILL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBillShouldNotBeFound(String filter) throws Exception {
        restBillMockMvc.perform(get("/api/bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillMockMvc.perform(get("/api/bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).get();
        // Disconnect from session so that the updates on updatedBill are not directly saved in db
        em.detach(updatedBill);
        updatedBill
            .amount(UPDATED_AMOUNT)
            .billCategory(UPDATED_BILL_CATEGORY)
            .reason(UPDATED_REASON)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        BillDTO billDTO = billMapper.toDto(updatedBill);

        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBill.getBillCategory()).isEqualTo(UPDATED_BILL_CATEGORY);
        assertThat(testBill.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testBill.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testBill.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    @Transactional
    public void updateNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).deleteById(bill.getId());
    }

    @Test
    @Transactional
    public void searchBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        when(mockBillSearchRepository.search(queryStringQuery("id:" + bill.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bill), PageRequest.of(0, 1), 1));
        // Search the bill
        restBillMockMvc.perform(get("/api/_search/bills?query=id:" + bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].billCategory").value(hasItem(DEFAULT_BILL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bill.class);
        Bill bill1 = new Bill();
        bill1.setId(1L);
        Bill bill2 = new Bill();
        bill2.setId(bill1.getId());
        assertThat(bill1).isEqualTo(bill2);
        bill2.setId(2L);
        assertThat(bill1).isNotEqualTo(bill2);
        bill1.setId(null);
        assertThat(bill1).isNotEqualTo(bill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillDTO.class);
        BillDTO billDTO1 = new BillDTO();
        billDTO1.setId(1L);
        BillDTO billDTO2 = new BillDTO();
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO2.setId(billDTO1.getId());
        assertThat(billDTO1).isEqualTo(billDTO2);
        billDTO2.setId(2L);
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO1.setId(null);
        assertThat(billDTO1).isNotEqualTo(billDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billMapper.fromId(null)).isNull();
    }
}
