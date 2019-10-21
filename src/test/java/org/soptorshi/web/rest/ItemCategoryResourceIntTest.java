package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ItemCategory;
import org.soptorshi.repository.ItemCategoryRepository;
import org.soptorshi.repository.search.ItemCategorySearchRepository;
import org.soptorshi.service.dto.ItemCategoryDTO;
import org.soptorshi.service.impl.ItemCategoryServiceImpl;
import org.soptorshi.service.mapper.ItemCategoryMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.ItemCategoryQueryService;

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
 * Test class for the ItemCategoryResource REST controller.
 *
 * @see ItemCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ItemCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Autowired
    private ItemCategoryServiceImpl itemCategoryService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ItemCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemCategorySearchRepository mockItemCategorySearchRepository;

    @Autowired
    private ItemCategoryQueryService itemCategoryQueryService;

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

    private MockMvc restItemCategoryMockMvc;

    private ItemCategory itemCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemCategoryResource itemCategoryResource = new ItemCategoryResource(itemCategoryService, itemCategoryQueryService);
        this.restItemCategoryMockMvc = MockMvcBuilders.standaloneSetup(itemCategoryResource)
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
    public static ItemCategory createEntity(EntityManager em) {
        ItemCategory itemCategory = new ItemCategory()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return itemCategory;
    }

    @Before
    public void initTest() {
        itemCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemCategory() throws Exception {
        int databaseSizeBeforeCreate = itemCategoryRepository.findAll().size();

        // Create the ItemCategory
        ItemCategoryDTO itemCategoryDTO = itemCategoryMapper.toDto(itemCategory);
        restItemCategoryMockMvc.perform(post("/api/item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCategory testItemCategory = itemCategoryList.get(itemCategoryList.size() - 1);
        assertThat(testItemCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemCategory.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testItemCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ItemCategory in Elasticsearch
        verify(mockItemCategorySearchRepository, times(1)).save(testItemCategory);
    }

    @Test
    @Transactional
    public void createItemCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemCategoryRepository.findAll().size();

        // Create the ItemCategory with an existing ID
        itemCategory.setId(1L);
        ItemCategoryDTO itemCategoryDTO = itemCategoryMapper.toDto(itemCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCategoryMockMvc.perform(post("/api/item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemCategory in Elasticsearch
        verify(mockItemCategorySearchRepository, times(0)).save(itemCategory);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCategoryRepository.findAll().size();
        // set the field null
        itemCategory.setName(null);

        // Create the ItemCategory, which fails.
        ItemCategoryDTO itemCategoryDTO = itemCategoryMapper.toDto(itemCategory);

        restItemCategoryMockMvc.perform(post("/api/item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemCategories() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getItemCategory() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get the itemCategory
        restItemCategoryMockMvc.perform(get("/api/item-categories/{id}", itemCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name equals to DEFAULT_NAME
        defaultItemCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemCategoryList where name equals to UPDATED_NAME
        defaultItemCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemCategoryList where name equals to UPDATED_NAME
        defaultItemCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where name is not null
        defaultItemCategoryShouldBeFound("name.specified=true");

        // Get all the itemCategoryList where name is null
        defaultItemCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where shortName equals to DEFAULT_SHORT_NAME
        defaultItemCategoryShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the itemCategoryList where shortName equals to UPDATED_SHORT_NAME
        defaultItemCategoryShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultItemCategoryShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the itemCategoryList where shortName equals to UPDATED_SHORT_NAME
        defaultItemCategoryShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where shortName is not null
        defaultItemCategoryShouldBeFound("shortName.specified=true");

        // Get all the itemCategoryList where shortName is null
        defaultItemCategoryShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultItemCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the itemCategoryList where description equals to UPDATED_DESCRIPTION
        defaultItemCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultItemCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the itemCategoryList where description equals to UPDATED_DESCRIPTION
        defaultItemCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        // Get all the itemCategoryList where description is not null
        defaultItemCategoryShouldBeFound("description.specified=true");

        // Get all the itemCategoryList where description is null
        defaultItemCategoryShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemCategoryShouldBeFound(String filter) throws Exception {
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restItemCategoryMockMvc.perform(get("/api/item-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemCategoryShouldNotBeFound(String filter) throws Exception {
        restItemCategoryMockMvc.perform(get("/api/item-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemCategoryMockMvc.perform(get("/api/item-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemCategory() throws Exception {
        // Get the itemCategory
        restItemCategoryMockMvc.perform(get("/api/item-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemCategory() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        int databaseSizeBeforeUpdate = itemCategoryRepository.findAll().size();

        // Update the itemCategory
        ItemCategory updatedItemCategory = itemCategoryRepository.findById(itemCategory.getId()).get();
        // Disconnect from session so that the updates on updatedItemCategory are not directly saved in db
        em.detach(updatedItemCategory);
        updatedItemCategory
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION);
        ItemCategoryDTO itemCategoryDTO = itemCategoryMapper.toDto(updatedItemCategory);

        restItemCategoryMockMvc.perform(put("/api/item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeUpdate);
        ItemCategory testItemCategory = itemCategoryList.get(itemCategoryList.size() - 1);
        assertThat(testItemCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemCategory.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testItemCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ItemCategory in Elasticsearch
        verify(mockItemCategorySearchRepository, times(1)).save(testItemCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingItemCategory() throws Exception {
        int databaseSizeBeforeUpdate = itemCategoryRepository.findAll().size();

        // Create the ItemCategory
        ItemCategoryDTO itemCategoryDTO = itemCategoryMapper.toDto(itemCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCategoryMockMvc.perform(put("/api/item-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCategory in the database
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemCategory in Elasticsearch
        verify(mockItemCategorySearchRepository, times(0)).save(itemCategory);
    }

    @Test
    @Transactional
    public void deleteItemCategory() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);

        int databaseSizeBeforeDelete = itemCategoryRepository.findAll().size();

        // Delete the itemCategory
        restItemCategoryMockMvc.perform(delete("/api/item-categories/{id}", itemCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemCategory> itemCategoryList = itemCategoryRepository.findAll();
        assertThat(itemCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemCategory in Elasticsearch
        verify(mockItemCategorySearchRepository, times(1)).deleteById(itemCategory.getId());
    }

    @Test
    @Transactional
    public void searchItemCategory() throws Exception {
        // Initialize the database
        itemCategoryRepository.saveAndFlush(itemCategory);
        when(mockItemCategorySearchRepository.search(queryStringQuery("id:" + itemCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemCategory), PageRequest.of(0, 1), 1));
        // Search the itemCategory
        restItemCategoryMockMvc.perform(get("/api/_search/item-categories?query=id:" + itemCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCategory.class);
        ItemCategory itemCategory1 = new ItemCategory();
        itemCategory1.setId(1L);
        ItemCategory itemCategory2 = new ItemCategory();
        itemCategory2.setId(itemCategory1.getId());
        assertThat(itemCategory1).isEqualTo(itemCategory2);
        itemCategory2.setId(2L);
        assertThat(itemCategory1).isNotEqualTo(itemCategory2);
        itemCategory1.setId(null);
        assertThat(itemCategory1).isNotEqualTo(itemCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCategoryDTO.class);
        ItemCategoryDTO itemCategoryDTO1 = new ItemCategoryDTO();
        itemCategoryDTO1.setId(1L);
        ItemCategoryDTO itemCategoryDTO2 = new ItemCategoryDTO();
        assertThat(itemCategoryDTO1).isNotEqualTo(itemCategoryDTO2);
        itemCategoryDTO2.setId(itemCategoryDTO1.getId());
        assertThat(itemCategoryDTO1).isEqualTo(itemCategoryDTO2);
        itemCategoryDTO2.setId(2L);
        assertThat(itemCategoryDTO1).isNotEqualTo(itemCategoryDTO2);
        itemCategoryDTO1.setId(null);
        assertThat(itemCategoryDTO1).isNotEqualTo(itemCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemCategoryMapper.fromId(null)).isNull();
    }
}
