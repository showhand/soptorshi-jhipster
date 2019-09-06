package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.MstGroupRepository;
import org.soptorshi.repository.search.MstGroupSearchRepository;
import org.soptorshi.service.MstGroupService;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.mapper.MstGroupMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.MstGroupCriteria;
import org.soptorshi.service.MstGroupQueryService;

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

import org.soptorshi.domain.enumeration.ReservedFlag;
/**
 * Test class for the MstGroupResource REST controller.
 *
 * @see MstGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class MstGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MAIN_GROUP = 1L;
    private static final Long UPDATED_MAIN_GROUP = 2L;

    private static final ReservedFlag DEFAULT_RESERVED_FLAG = ReservedFlag.RESERVED;
    private static final ReservedFlag UPDATED_RESERVED_FLAG = ReservedFlag.NOT_RESERVED;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MstGroupRepository mstGroupRepository;

    @Autowired
    private MstGroupMapper mstGroupMapper;

    @Autowired
    private MstGroupService mstGroupService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.MstGroupSearchRepositoryMockConfiguration
     */
    @Autowired
    private MstGroupSearchRepository mockMstGroupSearchRepository;

    @Autowired
    private MstGroupQueryService mstGroupQueryService;

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

    private MockMvc restMstGroupMockMvc;

    private MstGroup mstGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MstGroupResource mstGroupResource = new MstGroupResource(mstGroupService, mstGroupQueryService);
        this.restMstGroupMockMvc = MockMvcBuilders.standaloneSetup(mstGroupResource)
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
    public static MstGroup createEntity(EntityManager em) {
        MstGroup mstGroup = new MstGroup()
            .name(DEFAULT_NAME)
            .mainGroup(DEFAULT_MAIN_GROUP)
            .reservedFlag(DEFAULT_RESERVED_FLAG)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return mstGroup;
    }

    @Before
    public void initTest() {
        mstGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createMstGroup() throws Exception {
        int databaseSizeBeforeCreate = mstGroupRepository.findAll().size();

        // Create the MstGroup
        MstGroupDTO mstGroupDTO = mstGroupMapper.toDto(mstGroup);
        restMstGroupMockMvc.perform(post("/api/mst-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MstGroup in the database
        List<MstGroup> mstGroupList = mstGroupRepository.findAll();
        assertThat(mstGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MstGroup testMstGroup = mstGroupList.get(mstGroupList.size() - 1);
        assertThat(testMstGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMstGroup.getMainGroup()).isEqualTo(DEFAULT_MAIN_GROUP);
        assertThat(testMstGroup.getReservedFlag()).isEqualTo(DEFAULT_RESERVED_FLAG);
        assertThat(testMstGroup.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMstGroup.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the MstGroup in Elasticsearch
        verify(mockMstGroupSearchRepository, times(1)).save(testMstGroup);
    }

    @Test
    @Transactional
    public void createMstGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mstGroupRepository.findAll().size();

        // Create the MstGroup with an existing ID
        mstGroup.setId(1L);
        MstGroupDTO mstGroupDTO = mstGroupMapper.toDto(mstGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMstGroupMockMvc.perform(post("/api/mst-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MstGroup in the database
        List<MstGroup> mstGroupList = mstGroupRepository.findAll();
        assertThat(mstGroupList).hasSize(databaseSizeBeforeCreate);

        // Validate the MstGroup in Elasticsearch
        verify(mockMstGroupSearchRepository, times(0)).save(mstGroup);
    }

    @Test
    @Transactional
    public void getAllMstGroups() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList
        restMstGroupMockMvc.perform(get("/api/mst-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mainGroup").value(hasItem(DEFAULT_MAIN_GROUP.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getMstGroup() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get the mstGroup
        restMstGroupMockMvc.perform(get("/api/mst-groups/{id}", mstGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mstGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mainGroup").value(DEFAULT_MAIN_GROUP.intValue()))
            .andExpect(jsonPath("$.reservedFlag").value(DEFAULT_RESERVED_FLAG.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllMstGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where name equals to DEFAULT_NAME
        defaultMstGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mstGroupList where name equals to UPDATED_NAME
        defaultMstGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMstGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mstGroupList where name equals to UPDATED_NAME
        defaultMstGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where name is not null
        defaultMstGroupShouldBeFound("name.specified=true");

        // Get all the mstGroupList where name is null
        defaultMstGroupShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstGroupsByMainGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where mainGroup equals to DEFAULT_MAIN_GROUP
        defaultMstGroupShouldBeFound("mainGroup.equals=" + DEFAULT_MAIN_GROUP);

        // Get all the mstGroupList where mainGroup equals to UPDATED_MAIN_GROUP
        defaultMstGroupShouldNotBeFound("mainGroup.equals=" + UPDATED_MAIN_GROUP);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByMainGroupIsInShouldWork() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where mainGroup in DEFAULT_MAIN_GROUP or UPDATED_MAIN_GROUP
        defaultMstGroupShouldBeFound("mainGroup.in=" + DEFAULT_MAIN_GROUP + "," + UPDATED_MAIN_GROUP);

        // Get all the mstGroupList where mainGroup equals to UPDATED_MAIN_GROUP
        defaultMstGroupShouldNotBeFound("mainGroup.in=" + UPDATED_MAIN_GROUP);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByMainGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where mainGroup is not null
        defaultMstGroupShouldBeFound("mainGroup.specified=true");

        // Get all the mstGroupList where mainGroup is null
        defaultMstGroupShouldNotBeFound("mainGroup.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstGroupsByMainGroupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where mainGroup greater than or equals to DEFAULT_MAIN_GROUP
        defaultMstGroupShouldBeFound("mainGroup.greaterOrEqualThan=" + DEFAULT_MAIN_GROUP);

        // Get all the mstGroupList where mainGroup greater than or equals to UPDATED_MAIN_GROUP
        defaultMstGroupShouldNotBeFound("mainGroup.greaterOrEqualThan=" + UPDATED_MAIN_GROUP);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByMainGroupIsLessThanSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where mainGroup less than or equals to DEFAULT_MAIN_GROUP
        defaultMstGroupShouldNotBeFound("mainGroup.lessThan=" + DEFAULT_MAIN_GROUP);

        // Get all the mstGroupList where mainGroup less than or equals to UPDATED_MAIN_GROUP
        defaultMstGroupShouldBeFound("mainGroup.lessThan=" + UPDATED_MAIN_GROUP);
    }


    @Test
    @Transactional
    public void getAllMstGroupsByReservedFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where reservedFlag equals to DEFAULT_RESERVED_FLAG
        defaultMstGroupShouldBeFound("reservedFlag.equals=" + DEFAULT_RESERVED_FLAG);

        // Get all the mstGroupList where reservedFlag equals to UPDATED_RESERVED_FLAG
        defaultMstGroupShouldNotBeFound("reservedFlag.equals=" + UPDATED_RESERVED_FLAG);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByReservedFlagIsInShouldWork() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where reservedFlag in DEFAULT_RESERVED_FLAG or UPDATED_RESERVED_FLAG
        defaultMstGroupShouldBeFound("reservedFlag.in=" + DEFAULT_RESERVED_FLAG + "," + UPDATED_RESERVED_FLAG);

        // Get all the mstGroupList where reservedFlag equals to UPDATED_RESERVED_FLAG
        defaultMstGroupShouldNotBeFound("reservedFlag.in=" + UPDATED_RESERVED_FLAG);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByReservedFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where reservedFlag is not null
        defaultMstGroupShouldBeFound("reservedFlag.specified=true");

        // Get all the mstGroupList where reservedFlag is null
        defaultMstGroupShouldNotBeFound("reservedFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultMstGroupShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the mstGroupList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMstGroupShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultMstGroupShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the mstGroupList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultMstGroupShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedBy is not null
        defaultMstGroupShouldBeFound("modifiedBy.specified=true");

        // Get all the mstGroupList where modifiedBy is null
        defaultMstGroupShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultMstGroupShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the mstGroupList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMstGroupShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultMstGroupShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the mstGroupList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultMstGroupShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedOn is not null
        defaultMstGroupShouldBeFound("modifiedOn.specified=true");

        // Get all the mstGroupList where modifiedOn is null
        defaultMstGroupShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultMstGroupShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the mstGroupList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultMstGroupShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllMstGroupsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        // Get all the mstGroupList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultMstGroupShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the mstGroupList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultMstGroupShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMstGroupShouldBeFound(String filter) throws Exception {
        restMstGroupMockMvc.perform(get("/api/mst-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mainGroup").value(hasItem(DEFAULT_MAIN_GROUP.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restMstGroupMockMvc.perform(get("/api/mst-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMstGroupShouldNotBeFound(String filter) throws Exception {
        restMstGroupMockMvc.perform(get("/api/mst-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMstGroupMockMvc.perform(get("/api/mst-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMstGroup() throws Exception {
        // Get the mstGroup
        restMstGroupMockMvc.perform(get("/api/mst-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMstGroup() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        int databaseSizeBeforeUpdate = mstGroupRepository.findAll().size();

        // Update the mstGroup
        MstGroup updatedMstGroup = mstGroupRepository.findById(mstGroup.getId()).get();
        // Disconnect from session so that the updates on updatedMstGroup are not directly saved in db
        em.detach(updatedMstGroup);
        updatedMstGroup
            .name(UPDATED_NAME)
            .mainGroup(UPDATED_MAIN_GROUP)
            .reservedFlag(UPDATED_RESERVED_FLAG)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        MstGroupDTO mstGroupDTO = mstGroupMapper.toDto(updatedMstGroup);

        restMstGroupMockMvc.perform(put("/api/mst-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstGroupDTO)))
            .andExpect(status().isOk());

        // Validate the MstGroup in the database
        List<MstGroup> mstGroupList = mstGroupRepository.findAll();
        assertThat(mstGroupList).hasSize(databaseSizeBeforeUpdate);
        MstGroup testMstGroup = mstGroupList.get(mstGroupList.size() - 1);
        assertThat(testMstGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMstGroup.getMainGroup()).isEqualTo(UPDATED_MAIN_GROUP);
        assertThat(testMstGroup.getReservedFlag()).isEqualTo(UPDATED_RESERVED_FLAG);
        assertThat(testMstGroup.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMstGroup.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the MstGroup in Elasticsearch
        verify(mockMstGroupSearchRepository, times(1)).save(testMstGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingMstGroup() throws Exception {
        int databaseSizeBeforeUpdate = mstGroupRepository.findAll().size();

        // Create the MstGroup
        MstGroupDTO mstGroupDTO = mstGroupMapper.toDto(mstGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMstGroupMockMvc.perform(put("/api/mst-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mstGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MstGroup in the database
        List<MstGroup> mstGroupList = mstGroupRepository.findAll();
        assertThat(mstGroupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MstGroup in Elasticsearch
        verify(mockMstGroupSearchRepository, times(0)).save(mstGroup);
    }

    @Test
    @Transactional
    public void deleteMstGroup() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);

        int databaseSizeBeforeDelete = mstGroupRepository.findAll().size();

        // Delete the mstGroup
        restMstGroupMockMvc.perform(delete("/api/mst-groups/{id}", mstGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MstGroup> mstGroupList = mstGroupRepository.findAll();
        assertThat(mstGroupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MstGroup in Elasticsearch
        verify(mockMstGroupSearchRepository, times(1)).deleteById(mstGroup.getId());
    }

    @Test
    @Transactional
    public void searchMstGroup() throws Exception {
        // Initialize the database
        mstGroupRepository.saveAndFlush(mstGroup);
        when(mockMstGroupSearchRepository.search(queryStringQuery("id:" + mstGroup.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(mstGroup), PageRequest.of(0, 1), 1));
        // Search the mstGroup
        restMstGroupMockMvc.perform(get("/api/_search/mst-groups?query=id:" + mstGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mstGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mainGroup").value(hasItem(DEFAULT_MAIN_GROUP.intValue())))
            .andExpect(jsonPath("$.[*].reservedFlag").value(hasItem(DEFAULT_RESERVED_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MstGroup.class);
        MstGroup mstGroup1 = new MstGroup();
        mstGroup1.setId(1L);
        MstGroup mstGroup2 = new MstGroup();
        mstGroup2.setId(mstGroup1.getId());
        assertThat(mstGroup1).isEqualTo(mstGroup2);
        mstGroup2.setId(2L);
        assertThat(mstGroup1).isNotEqualTo(mstGroup2);
        mstGroup1.setId(null);
        assertThat(mstGroup1).isNotEqualTo(mstGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MstGroupDTO.class);
        MstGroupDTO mstGroupDTO1 = new MstGroupDTO();
        mstGroupDTO1.setId(1L);
        MstGroupDTO mstGroupDTO2 = new MstGroupDTO();
        assertThat(mstGroupDTO1).isNotEqualTo(mstGroupDTO2);
        mstGroupDTO2.setId(mstGroupDTO1.getId());
        assertThat(mstGroupDTO1).isEqualTo(mstGroupDTO2);
        mstGroupDTO2.setId(2L);
        assertThat(mstGroupDTO1).isNotEqualTo(mstGroupDTO2);
        mstGroupDTO1.setId(null);
        assertThat(mstGroupDTO1).isNotEqualTo(mstGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mstGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mstGroupMapper.fromId(null)).isNull();
    }
}
