package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.LeaveType;
import org.soptorshi.repository.LeaveTypeRepository;
import org.soptorshi.repository.search.LeaveTypeSearchRepository;
import org.soptorshi.service.LeaveTypeService;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.mapper.LeaveTypeMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.LeaveTypeCriteria;
import org.soptorshi.service.LeaveTypeQueryService;

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
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.soptorshi.domain.enumeration.PaidOrUnPaid;
/**
 * Test class for the LeaveTypeResource REST controller.
 *
 * @see LeaveTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class LeaveTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PaidOrUnPaid DEFAULT_PAID_LEAVE = PaidOrUnPaid.PAID;
    private static final PaidOrUnPaid UPDATED_PAID_LEAVE = PaidOrUnPaid.UNPAID;

    private static final Integer DEFAULT_MAXIMUM_NUMBER_OF_DAYS = 1;
    private static final Integer UPDATED_MAXIMUM_NUMBER_OF_DAYS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveTypeMapper leaveTypeMapper;

    @Autowired
    private LeaveTypeService leaveTypeService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.LeaveTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private LeaveTypeSearchRepository mockLeaveTypeSearchRepository;

    @Autowired
    private LeaveTypeQueryService leaveTypeQueryService;

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

    private MockMvc restLeaveTypeMockMvc;

    private LeaveType leaveType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveTypeResource leaveTypeResource = new LeaveTypeResource(leaveTypeService, leaveTypeQueryService);
        this.restLeaveTypeMockMvc = MockMvcBuilders.standaloneSetup(leaveTypeResource)
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
    public static LeaveType createEntity(EntityManager em) {
        LeaveType leaveType = new LeaveType()
            .name(DEFAULT_NAME)
            .paidLeave(DEFAULT_PAID_LEAVE)
            .maximumNumberOfDays(DEFAULT_MAXIMUM_NUMBER_OF_DAYS)
            .description(DEFAULT_DESCRIPTION);
        return leaveType;
    }

    @Before
    public void initTest() {
        leaveType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveType() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);
        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeaveType.getPaidLeave()).isEqualTo(DEFAULT_PAID_LEAVE);
        assertThat(testLeaveType.getMaximumNumberOfDays()).isEqualTo(DEFAULT_MAXIMUM_NUMBER_OF_DAYS);
        assertThat(testLeaveType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the LeaveType in Elasticsearch
        verify(mockLeaveTypeSearchRepository, times(1)).save(testLeaveType);
    }

    @Test
    @Transactional
    public void createLeaveTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveTypeRepository.findAll().size();

        // Create the LeaveType with an existing ID
        leaveType.setId(1L);
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the LeaveType in Elasticsearch
        verify(mockLeaveTypeSearchRepository, times(0)).save(leaveType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveTypeRepository.findAll().size();
        // set the field null
        leaveType.setName(null);

        // Create the LeaveType, which fails.
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        restLeaveTypeMockMvc.perform(post("/api/leave-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveTypes() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].paidLeave").value(hasItem(DEFAULT_PAID_LEAVE.toString())))
            .andExpect(jsonPath("$.[*].maximumNumberOfDays").value(hasItem(DEFAULT_MAXIMUM_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get the leaveType
        restLeaveTypeMockMvc.perform(get("/api/leave-types/{id}", leaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(leaveType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.paidLeave").value(DEFAULT_PAID_LEAVE.toString()))
            .andExpect(jsonPath("$.maximumNumberOfDays").value(DEFAULT_MAXIMUM_NUMBER_OF_DAYS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name equals to DEFAULT_NAME
        defaultLeaveTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveTypeList where name equals to UPDATED_NAME
        defaultLeaveTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveTypeList where name equals to UPDATED_NAME
        defaultLeaveTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where name is not null
        defaultLeaveTypeShouldBeFound("name.specified=true");

        // Get all the leaveTypeList where name is null
        defaultLeaveTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByPaidLeaveIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where paidLeave equals to DEFAULT_PAID_LEAVE
        defaultLeaveTypeShouldBeFound("paidLeave.equals=" + DEFAULT_PAID_LEAVE);

        // Get all the leaveTypeList where paidLeave equals to UPDATED_PAID_LEAVE
        defaultLeaveTypeShouldNotBeFound("paidLeave.equals=" + UPDATED_PAID_LEAVE);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByPaidLeaveIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where paidLeave in DEFAULT_PAID_LEAVE or UPDATED_PAID_LEAVE
        defaultLeaveTypeShouldBeFound("paidLeave.in=" + DEFAULT_PAID_LEAVE + "," + UPDATED_PAID_LEAVE);

        // Get all the leaveTypeList where paidLeave equals to UPDATED_PAID_LEAVE
        defaultLeaveTypeShouldNotBeFound("paidLeave.in=" + UPDATED_PAID_LEAVE);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByPaidLeaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where paidLeave is not null
        defaultLeaveTypeShouldBeFound("paidLeave.specified=true");

        // Get all the leaveTypeList where paidLeave is null
        defaultLeaveTypeShouldNotBeFound("paidLeave.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByMaximumNumberOfDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where maximumNumberOfDays equals to DEFAULT_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldBeFound("maximumNumberOfDays.equals=" + DEFAULT_MAXIMUM_NUMBER_OF_DAYS);

        // Get all the leaveTypeList where maximumNumberOfDays equals to UPDATED_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("maximumNumberOfDays.equals=" + UPDATED_MAXIMUM_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByMaximumNumberOfDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where maximumNumberOfDays in DEFAULT_MAXIMUM_NUMBER_OF_DAYS or UPDATED_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldBeFound("maximumNumberOfDays.in=" + DEFAULT_MAXIMUM_NUMBER_OF_DAYS + "," + UPDATED_MAXIMUM_NUMBER_OF_DAYS);

        // Get all the leaveTypeList where maximumNumberOfDays equals to UPDATED_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("maximumNumberOfDays.in=" + UPDATED_MAXIMUM_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByMaximumNumberOfDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where maximumNumberOfDays is not null
        defaultLeaveTypeShouldBeFound("maximumNumberOfDays.specified=true");

        // Get all the leaveTypeList where maximumNumberOfDays is null
        defaultLeaveTypeShouldNotBeFound("maximumNumberOfDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByMaximumNumberOfDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where maximumNumberOfDays greater than or equals to DEFAULT_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldBeFound("maximumNumberOfDays.greaterOrEqualThan=" + DEFAULT_MAXIMUM_NUMBER_OF_DAYS);

        // Get all the leaveTypeList where maximumNumberOfDays greater than or equals to UPDATED_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("maximumNumberOfDays.greaterOrEqualThan=" + UPDATED_MAXIMUM_NUMBER_OF_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByMaximumNumberOfDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where maximumNumberOfDays less than or equals to DEFAULT_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldNotBeFound("maximumNumberOfDays.lessThan=" + DEFAULT_MAXIMUM_NUMBER_OF_DAYS);

        // Get all the leaveTypeList where maximumNumberOfDays less than or equals to UPDATED_MAXIMUM_NUMBER_OF_DAYS
        defaultLeaveTypeShouldBeFound("maximumNumberOfDays.lessThan=" + UPDATED_MAXIMUM_NUMBER_OF_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where description equals to DEFAULT_DESCRIPTION
        defaultLeaveTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leaveTypeList where description equals to UPDATED_DESCRIPTION
        defaultLeaveTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeaveTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leaveTypeList where description equals to UPDATED_DESCRIPTION
        defaultLeaveTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeaveTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        // Get all the leaveTypeList where description is not null
        defaultLeaveTypeShouldBeFound("description.specified=true");

        // Get all the leaveTypeList where description is null
        defaultLeaveTypeShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLeaveTypeShouldBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].paidLeave").value(hasItem(DEFAULT_PAID_LEAVE.toString())))
            .andExpect(jsonPath("$.[*].maximumNumberOfDays").value(hasItem(DEFAULT_MAXIMUM_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restLeaveTypeMockMvc.perform(get("/api/leave-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLeaveTypeShouldNotBeFound(String filter) throws Exception {
        restLeaveTypeMockMvc.perform(get("/api/leave-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveTypeMockMvc.perform(get("/api/leave-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLeaveType() throws Exception {
        // Get the leaveType
        restLeaveTypeMockMvc.perform(get("/api/leave-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Update the leaveType
        LeaveType updatedLeaveType = leaveTypeRepository.findById(leaveType.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveType are not directly saved in db
        em.detach(updatedLeaveType);
        updatedLeaveType
            .name(UPDATED_NAME)
            .paidLeave(UPDATED_PAID_LEAVE)
            .maximumNumberOfDays(UPDATED_MAXIMUM_NUMBER_OF_DAYS)
            .description(UPDATED_DESCRIPTION);
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(updatedLeaveType);

        restLeaveTypeMockMvc.perform(put("/api/leave-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);
        LeaveType testLeaveType = leaveTypeList.get(leaveTypeList.size() - 1);
        assertThat(testLeaveType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeaveType.getPaidLeave()).isEqualTo(UPDATED_PAID_LEAVE);
        assertThat(testLeaveType.getMaximumNumberOfDays()).isEqualTo(UPDATED_MAXIMUM_NUMBER_OF_DAYS);
        assertThat(testLeaveType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the LeaveType in Elasticsearch
        verify(mockLeaveTypeSearchRepository, times(1)).save(testLeaveType);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveType() throws Exception {
        int databaseSizeBeforeUpdate = leaveTypeRepository.findAll().size();

        // Create the LeaveType
        LeaveTypeDTO leaveTypeDTO = leaveTypeMapper.toDto(leaveType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveTypeMockMvc.perform(put("/api/leave-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(leaveTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveType in the database
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LeaveType in Elasticsearch
        verify(mockLeaveTypeSearchRepository, times(0)).save(leaveType);
    }

    @Test
    @Transactional
    public void deleteLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);

        int databaseSizeBeforeDelete = leaveTypeRepository.findAll().size();

        // Delete the leaveType
        restLeaveTypeMockMvc.perform(delete("/api/leave-types/{id}", leaveType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LeaveType> leaveTypeList = leaveTypeRepository.findAll();
        assertThat(leaveTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LeaveType in Elasticsearch
        verify(mockLeaveTypeSearchRepository, times(1)).deleteById(leaveType.getId());
    }

    @Test
    @Transactional
    public void searchLeaveType() throws Exception {
        // Initialize the database
        leaveTypeRepository.saveAndFlush(leaveType);
        when(mockLeaveTypeSearchRepository.search(queryStringQuery("id:" + leaveType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(leaveType), PageRequest.of(0, 1), 1));
        // Search the leaveType
        restLeaveTypeMockMvc.perform(get("/api/_search/leave-types?query=id:" + leaveType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].paidLeave").value(hasItem(DEFAULT_PAID_LEAVE.toString())))
            .andExpect(jsonPath("$.[*].maximumNumberOfDays").value(hasItem(DEFAULT_MAXIMUM_NUMBER_OF_DAYS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveType.class);
        LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(1L);
        LeaveType leaveType2 = new LeaveType();
        leaveType2.setId(leaveType1.getId());
        assertThat(leaveType1).isEqualTo(leaveType2);
        leaveType2.setId(2L);
        assertThat(leaveType1).isNotEqualTo(leaveType2);
        leaveType1.setId(null);
        assertThat(leaveType1).isNotEqualTo(leaveType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveTypeDTO.class);
        LeaveTypeDTO leaveTypeDTO1 = new LeaveTypeDTO();
        leaveTypeDTO1.setId(1L);
        LeaveTypeDTO leaveTypeDTO2 = new LeaveTypeDTO();
        assertThat(leaveTypeDTO1).isNotEqualTo(leaveTypeDTO2);
        leaveTypeDTO2.setId(leaveTypeDTO1.getId());
        assertThat(leaveTypeDTO1).isEqualTo(leaveTypeDTO2);
        leaveTypeDTO2.setId(2L);
        assertThat(leaveTypeDTO1).isNotEqualTo(leaveTypeDTO2);
        leaveTypeDTO1.setId(null);
        assertThat(leaveTypeDTO1).isNotEqualTo(leaveTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(leaveTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(leaveTypeMapper.fromId(null)).isNull();
    }
}
