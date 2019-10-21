package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ItemSubCategory;
import org.soptorshi.domain.ItemCategory;
import org.soptorshi.repository.ItemSubCategoryRepository;
import org.soptorshi.repository.search.ItemSubCategorySearchRepository;
import org.soptorshi.service.dto.ItemSubCategoryDTO;
import org.soptorshi.service.impl.ItemSubCategoryServiceImpl;
import org.soptorshi.service.mapper.ItemSubCategoryMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.ItemSubCategoryQueryService;

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
 * Test class for the ItemSubCategoryResource REST controller.
 *
 * @see ItemSubCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ItemSubCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ItemSubCategoryRepository itemSubCategoryRepository;

    @Autowired
    private ItemSubCategoryMapper itemSubCategoryMapper;

    @Autowired
    private ItemSubCategoryServiceImpl itemSubCategoryService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ItemSubCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemSubCategorySearchRepository mockItemSubCategorySearchRepository;

    @Autowired
    private ItemSubCategoryQueryService itemSubCategoryQueryService;

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

    private MockMvc restItemSubCategoryMockMvc;

    private ItemSubCategory itemSubCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemSubCategoryResource itemSubCategoryResource = new ItemSubCategoryResource(itemSubCategoryService, itemSubCategoryQueryService);
        this.restItemSubCategoryMockMvc = MockMvcBuilders.standaloneSetup(itemSubCategoryResource)
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
    public static ItemSubCategory createEntity(EntityManager em) {
        ItemSubCategory itemSubCategory = new ItemSubCategory()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return itemSubCategory;
    }

    @Before
    public void initTest() {
        itemSubCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemSubCategory() throws Exception {
        int databaseSizeBeforeCreate = itemSubCategoryRepository.findAll().size();

        // Create the ItemSubCategory
        ItemSubCategoryDTO itemSubCategoryDTO = itemSubCategoryMapper.toDto(itemSubCategory);
        restItemSubCategoryMockMvc.perform(post("/api/item-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemSubCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemSubCategory in the database
        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ItemSubCategory testItemSubCategory = itemSubCategoryList.get(itemSubCategoryList.size() - 1);
        assertThat(testItemSubCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemSubCategory.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testItemSubCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ItemSubCategory in Elasticsearch
        verify(mockItemSubCategorySearchRepository, times(1)).save(testItemSubCategory);
    }

    @Test
    @Transactional
    public void createItemSubCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemSubCategoryRepository.findAll().size();

        // Create the ItemSubCategory with an existing ID
        itemSubCategory.setId(1L);
        ItemSubCategoryDTO itemSubCategoryDTO = itemSubCategoryMapper.toDto(itemSubCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemSubCategoryMockMvc.perform(post("/api/item-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemSubCategory in the database
        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemSubCategory in Elasticsearch
        verify(mockItemSubCategorySearchRepository, times(0)).save(itemSubCategory);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemSubCategoryRepository.findAll().size();
        // set the field null
        itemSubCategory.setName(null);

        // Create the ItemSubCategory, which fails.
        ItemSubCategoryDTO itemSubCategoryDTO = itemSubCategoryMapper.toDto(itemSubCategory);

        restItemSubCategoryMockMvc.perform(post("/api/item-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemSubCategories() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getItemSubCategory() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get the itemSubCategory
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories/{id}", itemSubCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemSubCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where name equals to DEFAULT_NAME
        defaultItemSubCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemSubCategoryList where name equals to UPDATED_NAME
        defaultItemSubCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemSubCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemSubCategoryList where name equals to UPDATED_NAME
        defaultItemSubCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where name is not null
        defaultItemSubCategoryShouldBeFound("name.specified=true");

        // Get all the itemSubCategoryList where name is null
        defaultItemSubCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where shortName equals to DEFAULT_SHORT_NAME
        defaultItemSubCategoryShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the itemSubCategoryList where shortName equals to UPDATED_SHORT_NAME
        defaultItemSubCategoryShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultItemSubCategoryShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the itemSubCategoryList where shortName equals to UPDATED_SHORT_NAME
        defaultItemSubCategoryShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where shortName is not null
        defaultItemSubCategoryShouldBeFound("shortName.specified=true");

        // Get all the itemSubCategoryList where shortName is null
        defaultItemSubCategoryShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultItemSubCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the itemSubCategoryList where description equals to UPDATED_DESCRIPTION
        defaultItemSubCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultItemSubCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the itemSubCategoryList where description equals to UPDATED_DESCRIPTION
        defaultItemSubCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        // Get all the itemSubCategoryList where description is not null
        defaultItemSubCategoryShouldBeFound("description.specified=true");

        // Get all the itemSubCategoryList where description is null
        defaultItemSubCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemSubCategoriesByItemCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemCategory itemCategories = ItemCategoryResourceIntTest.createEntity(em);
        em.persist(itemCategories);
        em.flush();
        itemSubCategory.setItemCategories(itemCategories);
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);
        Long itemCategoriesId = itemCategories.getId();

        // Get all the itemSubCategoryList where itemCategories equals to itemCategoriesId
        defaultItemSubCategoryShouldBeFound("itemCategoriesId.equals=" + itemCategoriesId);

        // Get all the itemSubCategoryList where itemCategories equals to itemCategoriesId + 1
        defaultItemSubCategoryShouldNotBeFound("itemCategoriesId.equals=" + (itemCategoriesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultItemSubCategoryShouldBeFound(String filter) throws Exception {
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultItemSubCategoryShouldNotBeFound(String filter) throws Exception {
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemSubCategory() throws Exception {
        // Get the itemSubCategory
        restItemSubCategoryMockMvc.perform(get("/api/item-sub-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemSubCategory() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        int databaseSizeBeforeUpdate = itemSubCategoryRepository.findAll().size();

        // Update the itemSubCategory
        ItemSubCategory updatedItemSubCategory = itemSubCategoryRepository.findById(itemSubCategory.getId()).get();
        // Disconnect from session so that the updates on updatedItemSubCategory are not directly saved in db
        em.detach(updatedItemSubCategory);
        updatedItemSubCategory
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION);
        ItemSubCategoryDTO itemSubCategoryDTO = itemSubCategoryMapper.toDto(updatedItemSubCategory);

        restItemSubCategoryMockMvc.perform(put("/api/item-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemSubCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ItemSubCategory in the database
        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeUpdate);
        ItemSubCategory testItemSubCategory = itemSubCategoryList.get(itemSubCategoryList.size() - 1);
        assertThat(testItemSubCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemSubCategory.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testItemSubCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ItemSubCategory in Elasticsearch
        verify(mockItemSubCategorySearchRepository, times(1)).save(testItemSubCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingItemSubCategory() throws Exception {
        int databaseSizeBeforeUpdate = itemSubCategoryRepository.findAll().size();

        // Create the ItemSubCategory
        ItemSubCategoryDTO itemSubCategoryDTO = itemSubCategoryMapper.toDto(itemSubCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemSubCategoryMockMvc.perform(put("/api/item-sub-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemSubCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemSubCategory in the database
        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemSubCategory in Elasticsearch
        verify(mockItemSubCategorySearchRepository, times(0)).save(itemSubCategory);
    }

    @Test
    @Transactional
    public void deleteItemSubCategory() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);

        int databaseSizeBeforeDelete = itemSubCategoryRepository.findAll().size();

        // Delete the itemSubCategory
        restItemSubCategoryMockMvc.perform(delete("/api/item-sub-categories/{id}", itemSubCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemSubCategory> itemSubCategoryList = itemSubCategoryRepository.findAll();
        assertThat(itemSubCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemSubCategory in Elasticsearch
        verify(mockItemSubCategorySearchRepository, times(1)).deleteById(itemSubCategory.getId());
    }

    @Test
    @Transactional
    public void searchItemSubCategory() throws Exception {
        // Initialize the database
        itemSubCategoryRepository.saveAndFlush(itemSubCategory);
        when(mockItemSubCategorySearchRepository.search(queryStringQuery("id:" + itemSubCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(itemSubCategory), PageRequest.of(0, 1), 1));
        // Search the itemSubCategory
        restItemSubCategoryMockMvc.perform(get("/api/_search/item-sub-categories?query=id:" + itemSubCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemSubCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemSubCategory.class);
        ItemSubCategory itemSubCategory1 = new ItemSubCategory();
        itemSubCategory1.setId(1L);
        ItemSubCategory itemSubCategory2 = new ItemSubCategory();
        itemSubCategory2.setId(itemSubCategory1.getId());
        assertThat(itemSubCategory1).isEqualTo(itemSubCategory2);
        itemSubCategory2.setId(2L);
        assertThat(itemSubCategory1).isNotEqualTo(itemSubCategory2);
        itemSubCategory1.setId(null);
        assertThat(itemSubCategory1).isNotEqualTo(itemSubCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemSubCategoryDTO.class);
        ItemSubCategoryDTO itemSubCategoryDTO1 = new ItemSubCategoryDTO();
        itemSubCategoryDTO1.setId(1L);
        ItemSubCategoryDTO itemSubCategoryDTO2 = new ItemSubCategoryDTO();
        assertThat(itemSubCategoryDTO1).isNotEqualTo(itemSubCategoryDTO2);
        itemSubCategoryDTO2.setId(itemSubCategoryDTO1.getId());
        assertThat(itemSubCategoryDTO1).isEqualTo(itemSubCategoryDTO2);
        itemSubCategoryDTO2.setId(2L);
        assertThat(itemSubCategoryDTO1).isNotEqualTo(itemSubCategoryDTO2);
        itemSubCategoryDTO1.setId(null);
        assertThat(itemSubCategoryDTO1).isNotEqualTo(itemSubCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemSubCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemSubCategoryMapper.fromId(null)).isNull();
    }
}
