package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.DepartmentHead;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.Department;
import org.soptorshi.domain.Employee;
import org.soptorshi.repository.DepartmentHeadRepository;
import org.soptorshi.repository.search.DepartmentHeadSearchRepository;
import org.soptorshi.service.DepartmentHeadService;
import org.soptorshi.service.dto.DepartmentHeadDTO;
import org.soptorshi.service.mapper.DepartmentHeadMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.DepartmentHeadCriteria;
import org.soptorshi.service.DepartmentHeadQueryService;

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

/**
 * Test class for the DepartmentHeadResource REST controller.
 *
 * @see DepartmentHeadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class DepartmentHeadResourceIntTest {

    @Autowired
    private DepartmentHeadRepository departmentHeadRepository;

    @Autowired
    private DepartmentHeadMapper departmentHeadMapper;

    @Autowired
    private DepartmentHeadService departmentHeadService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.DepartmentHeadSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepartmentHeadSearchRepository mockDepartmentHeadSearchRepository;

    @Autowired
    private DepartmentHeadQueryService departmentHeadQueryService;

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

    private MockMvc restDepartmentHeadMockMvc;

    private DepartmentHead departmentHead;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentHeadResource departmentHeadResource = new DepartmentHeadResource(departmentHeadService, departmentHeadQueryService);
        this.restDepartmentHeadMockMvc = MockMvcBuilders.standaloneSetup(departmentHeadResource)
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
    public static DepartmentHead createEntity(EntityManager em) {
        DepartmentHead departmentHead = new DepartmentHead();
        return departmentHead;
    }

    @Before
    public void initTest() {
        departmentHead = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartmentHead() throws Exception {
        int databaseSizeBeforeCreate = departmentHeadRepository.findAll().size();

        // Create the DepartmentHead
        DepartmentHeadDTO departmentHeadDTO = departmentHeadMapper.toDto(departmentHead);
        restDepartmentHeadMockMvc.perform(post("/api/department-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentHeadDTO)))
            .andExpect(status().isCreated());

        // Validate the DepartmentHead in the database
        List<DepartmentHead> departmentHeadList = departmentHeadRepository.findAll();
        assertThat(departmentHeadList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentHead testDepartmentHead = departmentHeadList.get(departmentHeadList.size() - 1);

        // Validate the DepartmentHead in Elasticsearch
        verify(mockDepartmentHeadSearchRepository, times(1)).save(testDepartmentHead);
    }

    @Test
    @Transactional
    public void createDepartmentHeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentHeadRepository.findAll().size();

        // Create the DepartmentHead with an existing ID
        departmentHead.setId(1L);
        DepartmentHeadDTO departmentHeadDTO = departmentHeadMapper.toDto(departmentHead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentHeadMockMvc.perform(post("/api/department-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentHeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentHead in the database
        List<DepartmentHead> departmentHeadList = departmentHeadRepository.findAll();
        assertThat(departmentHeadList).hasSize(databaseSizeBeforeCreate);

        // Validate the DepartmentHead in Elasticsearch
        verify(mockDepartmentHeadSearchRepository, times(0)).save(departmentHead);
    }

    @Test
    @Transactional
    public void getAllDepartmentHeads() throws Exception {
        // Initialize the database
        departmentHeadRepository.saveAndFlush(departmentHead);

        // Get all the departmentHeadList
        restDepartmentHeadMockMvc.perform(get("/api/department-heads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentHead.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDepartmentHead() throws Exception {
        // Initialize the database
        departmentHeadRepository.saveAndFlush(departmentHead);

        // Get the departmentHead
        restDepartmentHeadMockMvc.perform(get("/api/department-heads/{id}", departmentHead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departmentHead.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllDepartmentHeadsByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        Office office = OfficeResourceIntTest.createEntity(em);
        em.persist(office);
        em.flush();
        departmentHead.setOffice(office);
        departmentHeadRepository.saveAndFlush(departmentHead);
        Long officeId = office.getId();

        // Get all the departmentHeadList where office equals to officeId
        defaultDepartmentHeadShouldBeFound("officeId.equals=" + officeId);

        // Get all the departmentHeadList where office equals to officeId + 1
        defaultDepartmentHeadShouldNotBeFound("officeId.equals=" + (officeId + 1));
    }


    @Test
    @Transactional
    public void getAllDepartmentHeadsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Department department = DepartmentResourceIntTest.createEntity(em);
        em.persist(department);
        em.flush();
        departmentHead.setDepartment(department);
        departmentHeadRepository.saveAndFlush(departmentHead);
        Long departmentId = department.getId();

        // Get all the departmentHeadList where department equals to departmentId
        defaultDepartmentHeadShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the departmentHeadList where department equals to departmentId + 1
        defaultDepartmentHeadShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllDepartmentHeadsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        departmentHead.setEmployee(employee);
        departmentHeadRepository.saveAndFlush(departmentHead);
        Long employeeId = employee.getId();

        // Get all the departmentHeadList where employee equals to employeeId
        defaultDepartmentHeadShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the departmentHeadList where employee equals to employeeId + 1
        defaultDepartmentHeadShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDepartmentHeadShouldBeFound(String filter) throws Exception {
        restDepartmentHeadMockMvc.perform(get("/api/department-heads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentHead.getId().intValue())));

        // Check, that the count call also returns 1
        restDepartmentHeadMockMvc.perform(get("/api/department-heads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDepartmentHeadShouldNotBeFound(String filter) throws Exception {
        restDepartmentHeadMockMvc.perform(get("/api/department-heads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentHeadMockMvc.perform(get("/api/department-heads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepartmentHead() throws Exception {
        // Get the departmentHead
        restDepartmentHeadMockMvc.perform(get("/api/department-heads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartmentHead() throws Exception {
        // Initialize the database
        departmentHeadRepository.saveAndFlush(departmentHead);

        int databaseSizeBeforeUpdate = departmentHeadRepository.findAll().size();

        // Update the departmentHead
        DepartmentHead updatedDepartmentHead = departmentHeadRepository.findById(departmentHead.getId()).get();
        // Disconnect from session so that the updates on updatedDepartmentHead are not directly saved in db
        em.detach(updatedDepartmentHead);
        DepartmentHeadDTO departmentHeadDTO = departmentHeadMapper.toDto(updatedDepartmentHead);

        restDepartmentHeadMockMvc.perform(put("/api/department-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentHeadDTO)))
            .andExpect(status().isOk());

        // Validate the DepartmentHead in the database
        List<DepartmentHead> departmentHeadList = departmentHeadRepository.findAll();
        assertThat(departmentHeadList).hasSize(databaseSizeBeforeUpdate);
        DepartmentHead testDepartmentHead = departmentHeadList.get(departmentHeadList.size() - 1);

        // Validate the DepartmentHead in Elasticsearch
        verify(mockDepartmentHeadSearchRepository, times(1)).save(testDepartmentHead);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartmentHead() throws Exception {
        int databaseSizeBeforeUpdate = departmentHeadRepository.findAll().size();

        // Create the DepartmentHead
        DepartmentHeadDTO departmentHeadDTO = departmentHeadMapper.toDto(departmentHead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentHeadMockMvc.perform(put("/api/department-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentHeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentHead in the database
        List<DepartmentHead> departmentHeadList = departmentHeadRepository.findAll();
        assertThat(departmentHeadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DepartmentHead in Elasticsearch
        verify(mockDepartmentHeadSearchRepository, times(0)).save(departmentHead);
    }

    @Test
    @Transactional
    public void deleteDepartmentHead() throws Exception {
        // Initialize the database
        departmentHeadRepository.saveAndFlush(departmentHead);

        int databaseSizeBeforeDelete = departmentHeadRepository.findAll().size();

        // Delete the departmentHead
        restDepartmentHeadMockMvc.perform(delete("/api/department-heads/{id}", departmentHead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepartmentHead> departmentHeadList = departmentHeadRepository.findAll();
        assertThat(departmentHeadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DepartmentHead in Elasticsearch
        verify(mockDepartmentHeadSearchRepository, times(1)).deleteById(departmentHead.getId());
    }

    @Test
    @Transactional
    public void searchDepartmentHead() throws Exception {
        // Initialize the database
        departmentHeadRepository.saveAndFlush(departmentHead);
        when(mockDepartmentHeadSearchRepository.search(queryStringQuery("id:" + departmentHead.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(departmentHead), PageRequest.of(0, 1), 1));
        // Search the departmentHead
        restDepartmentHeadMockMvc.perform(get("/api/_search/department-heads?query=id:" + departmentHead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentHead.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentHead.class);
        DepartmentHead departmentHead1 = new DepartmentHead();
        departmentHead1.setId(1L);
        DepartmentHead departmentHead2 = new DepartmentHead();
        departmentHead2.setId(departmentHead1.getId());
        assertThat(departmentHead1).isEqualTo(departmentHead2);
        departmentHead2.setId(2L);
        assertThat(departmentHead1).isNotEqualTo(departmentHead2);
        departmentHead1.setId(null);
        assertThat(departmentHead1).isNotEqualTo(departmentHead2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentHeadDTO.class);
        DepartmentHeadDTO departmentHeadDTO1 = new DepartmentHeadDTO();
        departmentHeadDTO1.setId(1L);
        DepartmentHeadDTO departmentHeadDTO2 = new DepartmentHeadDTO();
        assertThat(departmentHeadDTO1).isNotEqualTo(departmentHeadDTO2);
        departmentHeadDTO2.setId(departmentHeadDTO1.getId());
        assertThat(departmentHeadDTO1).isEqualTo(departmentHeadDTO2);
        departmentHeadDTO2.setId(2L);
        assertThat(departmentHeadDTO1).isNotEqualTo(departmentHeadDTO2);
        departmentHeadDTO1.setId(null);
        assertThat(departmentHeadDTO1).isNotEqualTo(departmentHeadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departmentHeadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departmentHeadMapper.fromId(null)).isNull();
    }
}
