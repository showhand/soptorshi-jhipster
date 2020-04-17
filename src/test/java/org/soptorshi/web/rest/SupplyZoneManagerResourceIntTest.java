package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.SupplyZoneManagerRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.service.SupplyZoneManagerService;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
 * Test class for the SupplyZoneManagerResource REST controller.
 *
 * @see SupplyZoneManagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class SupplyZoneManagerResourceIntTest {

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SupplyZoneManagerStatus DEFAULT_STATUS = SupplyZoneManagerStatus.ACTIVE;
    private static final SupplyZoneManagerStatus UPDATED_STATUS = SupplyZoneManagerStatus.INACTIVE;

    @Autowired
    private SupplyZoneManagerRepository supplyZoneManagerRepository;

    @Autowired
    private SupplyZoneManagerMapper supplyZoneManagerMapper;

    @Autowired
    private SupplyZoneManagerService supplyZoneManagerService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.SupplyZoneManagerSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupplyZoneManagerSearchRepository mockSupplyZoneManagerSearchRepository;

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

    private MockMvc restSupplyZoneManagerMockMvc;

    private SupplyZoneManager supplyZoneManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplyZoneManagerResource supplyZoneManagerResource = new SupplyZoneManagerResource(supplyZoneManagerService);
        this.restSupplyZoneManagerMockMvc = MockMvcBuilders.standaloneSetup(supplyZoneManagerResource)
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
    public static SupplyZoneManager createEntity(EntityManager em) {
        SupplyZoneManager supplyZoneManager = new SupplyZoneManager()
            .endDate(DEFAULT_END_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .status(DEFAULT_STATUS);
        // Add required entity
        SupplyZone supplyZone = SupplyZoneResourceIntTest.createEntity(em);
        em.persist(supplyZone);
        em.flush();
        supplyZoneManager.setSupplyZone(supplyZone);
        // Add required entity
        Employee employee = EmployeeResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        supplyZoneManager.setEmployee(employee);
        return supplyZoneManager;
    }

    @Before
    public void initTest() {
        supplyZoneManager = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplyZoneManager() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);
        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeCreate + 1);
        SupplyZoneManager testSupplyZoneManager = supplyZoneManagerList.get(supplyZoneManagerList.size() - 1);
        assertThat(testSupplyZoneManager.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSupplyZoneManager.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSupplyZoneManager.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSupplyZoneManager.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testSupplyZoneManager.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testSupplyZoneManager.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).save(testSupplyZoneManager);
    }

    @Test
    @Transactional
    public void createSupplyZoneManagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager with an existing ID
        supplyZoneManager.setId(1L);
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeCreate);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(0)).save(supplyZoneManager);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplyZoneManagerRepository.findAll().size();
        // set the field null
        supplyZoneManager.setStatus(null);

        // Create the SupplyZoneManager, which fails.
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        restSupplyZoneManagerMockMvc.perform(post("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplyZoneManagers() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get all the supplyZoneManagerList
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        // Get the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/{id}", supplyZoneManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplyZoneManager.getId().intValue()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupplyZoneManager() throws Exception {
        // Get the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/supply-zone-managers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        int databaseSizeBeforeUpdate = supplyZoneManagerRepository.findAll().size();

        // Update the supplyZoneManager
        SupplyZoneManager updatedSupplyZoneManager = supplyZoneManagerRepository.findById(supplyZoneManager.getId()).get();
        // Disconnect from session so that the updates on updatedSupplyZoneManager are not directly saved in db
        em.detach(updatedSupplyZoneManager);
        updatedSupplyZoneManager
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .status(UPDATED_STATUS);
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(updatedSupplyZoneManager);

        restSupplyZoneManagerMockMvc.perform(put("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isOk());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeUpdate);
        SupplyZoneManager testSupplyZoneManager = supplyZoneManagerList.get(supplyZoneManagerList.size() - 1);
        assertThat(testSupplyZoneManager.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSupplyZoneManager.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSupplyZoneManager.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSupplyZoneManager.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testSupplyZoneManager.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testSupplyZoneManager.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).save(testSupplyZoneManager);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplyZoneManager() throws Exception {
        int databaseSizeBeforeUpdate = supplyZoneManagerRepository.findAll().size();

        // Create the SupplyZoneManager
        SupplyZoneManagerDTO supplyZoneManagerDTO = supplyZoneManagerMapper.toDto(supplyZoneManager);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyZoneManagerMockMvc.perform(put("/api/supply-zone-managers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplyZoneManagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyZoneManager in the database
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(0)).save(supplyZoneManager);
    }

    @Test
    @Transactional
    public void deleteSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);

        int databaseSizeBeforeDelete = supplyZoneManagerRepository.findAll().size();

        // Delete the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(delete("/api/supply-zone-managers/{id}", supplyZoneManager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SupplyZoneManager> supplyZoneManagerList = supplyZoneManagerRepository.findAll();
        assertThat(supplyZoneManagerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SupplyZoneManager in Elasticsearch
        verify(mockSupplyZoneManagerSearchRepository, times(1)).deleteById(supplyZoneManager.getId());
    }

    @Test
    @Transactional
    public void searchSupplyZoneManager() throws Exception {
        // Initialize the database
        supplyZoneManagerRepository.saveAndFlush(supplyZoneManager);
        when(mockSupplyZoneManagerSearchRepository.search(queryStringQuery("id:" + supplyZoneManager.getId())))
            .thenReturn(Collections.singletonList(supplyZoneManager));
        // Search the supplyZoneManager
        restSupplyZoneManagerMockMvc.perform(get("/api/_search/supply-zone-managers?query=id:" + supplyZoneManager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyZoneManager.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneManager.class);
        SupplyZoneManager supplyZoneManager1 = new SupplyZoneManager();
        supplyZoneManager1.setId(1L);
        SupplyZoneManager supplyZoneManager2 = new SupplyZoneManager();
        supplyZoneManager2.setId(supplyZoneManager1.getId());
        assertThat(supplyZoneManager1).isEqualTo(supplyZoneManager2);
        supplyZoneManager2.setId(2L);
        assertThat(supplyZoneManager1).isNotEqualTo(supplyZoneManager2);
        supplyZoneManager1.setId(null);
        assertThat(supplyZoneManager1).isNotEqualTo(supplyZoneManager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyZoneManagerDTO.class);
        SupplyZoneManagerDTO supplyZoneManagerDTO1 = new SupplyZoneManagerDTO();
        supplyZoneManagerDTO1.setId(1L);
        SupplyZoneManagerDTO supplyZoneManagerDTO2 = new SupplyZoneManagerDTO();
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO2.setId(supplyZoneManagerDTO1.getId());
        assertThat(supplyZoneManagerDTO1).isEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO2.setId(2L);
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
        supplyZoneManagerDTO1.setId(null);
        assertThat(supplyZoneManagerDTO1).isNotEqualTo(supplyZoneManagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplyZoneManagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplyZoneManagerMapper.fromId(null)).isNull();
    }
}
