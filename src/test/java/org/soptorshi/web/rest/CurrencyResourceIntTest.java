package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.Currency;
import org.soptorshi.repository.CurrencyRepository;
import org.soptorshi.repository.search.CurrencySearchRepository;
import org.soptorshi.service.CurrencyService;
import org.soptorshi.service.dto.CurrencyDTO;
import org.soptorshi.service.mapper.CurrencyMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.CurrencyCriteria;
import org.soptorshi.service.CurrencyQueryService;

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

import org.soptorshi.domain.enumeration.CurrencyFlag;
/**
 * Test class for the CurrencyResource REST controller.
 *
 * @see CurrencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class CurrencyResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTATION = "AAAAAAAAAA";
    private static final String UPDATED_NOTATION = "BBBBBBBBBB";

    private static final CurrencyFlag DEFAULT_FLAG = CurrencyFlag.BASE;
    private static final CurrencyFlag UPDATED_FLAG = CurrencyFlag.OPTIONAL;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CurrencyService currencyService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.CurrencySearchRepositoryMockConfiguration
     */
    @Autowired
    private CurrencySearchRepository mockCurrencySearchRepository;

    @Autowired
    private CurrencyQueryService currencyQueryService;

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

    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyResource currencyResource = new CurrencyResource(currencyService, currencyQueryService);
        this.restCurrencyMockMvc = MockMvcBuilders.standaloneSetup(currencyResource)
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
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .description(DEFAULT_DESCRIPTION)
            .notation(DEFAULT_NOTATION)
            .flag(DEFAULT_FLAG)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return currency;
    }

    @Before
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurrency.getNotation()).isEqualTo(DEFAULT_NOTATION);
        assertThat(testCurrency.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCurrency.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCurrency.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the Currency in Elasticsearch
        verify(mockCurrencySearchRepository, times(1)).save(testCurrency);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Currency in Elasticsearch
        verify(mockCurrencySearchRepository, times(0)).save(currency);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.notation").value(DEFAULT_NOTATION.toString()))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description equals to DEFAULT_DESCRIPTION
        defaultCurrencyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the currencyList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCurrencyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the currencyList where description equals to UPDATED_DESCRIPTION
        defaultCurrencyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where description is not null
        defaultCurrencyShouldBeFound("description.specified=true");

        // Get all the currencyList where description is null
        defaultCurrencyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNotationIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where notation equals to DEFAULT_NOTATION
        defaultCurrencyShouldBeFound("notation.equals=" + DEFAULT_NOTATION);

        // Get all the currencyList where notation equals to UPDATED_NOTATION
        defaultCurrencyShouldNotBeFound("notation.equals=" + UPDATED_NOTATION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNotationIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where notation in DEFAULT_NOTATION or UPDATED_NOTATION
        defaultCurrencyShouldBeFound("notation.in=" + DEFAULT_NOTATION + "," + UPDATED_NOTATION);

        // Get all the currencyList where notation equals to UPDATED_NOTATION
        defaultCurrencyShouldNotBeFound("notation.in=" + UPDATED_NOTATION);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByNotationIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where notation is not null
        defaultCurrencyShouldBeFound("notation.specified=true");

        // Get all the currencyList where notation is null
        defaultCurrencyShouldNotBeFound("notation.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where flag equals to DEFAULT_FLAG
        defaultCurrencyShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the currencyList where flag equals to UPDATED_FLAG
        defaultCurrencyShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultCurrencyShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the currencyList where flag equals to UPDATED_FLAG
        defaultCurrencyShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where flag is not null
        defaultCurrencyShouldBeFound("flag.specified=true");

        // Get all the currencyList where flag is null
        defaultCurrencyShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultCurrencyShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the currencyList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCurrencyShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultCurrencyShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the currencyList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultCurrencyShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedBy is not null
        defaultCurrencyShouldBeFound("modifiedBy.specified=true");

        // Get all the currencyList where modifiedBy is null
        defaultCurrencyShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultCurrencyShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the currencyList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultCurrencyShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultCurrencyShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the currencyList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultCurrencyShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedOn is not null
        defaultCurrencyShouldBeFound("modifiedOn.specified=true");

        // Get all the currencyList where modifiedOn is null
        defaultCurrencyShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultCurrencyShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the currencyList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultCurrencyShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultCurrencyShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the currencyList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultCurrencyShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .description(UPDATED_DESCRIPTION)
            .notation(UPDATED_NOTATION)
            .flag(UPDATED_FLAG)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        CurrencyDTO currencyDTO = currencyMapper.toDto(updatedCurrency);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurrency.getNotation()).isEqualTo(UPDATED_NOTATION);
        assertThat(testCurrency.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCurrency.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCurrency.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the Currency in Elasticsearch
        verify(mockCurrencySearchRepository, times(1)).save(testCurrency);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Currency in Elasticsearch
        verify(mockCurrencySearchRepository, times(0)).save(currency);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Currency in Elasticsearch
        verify(mockCurrencySearchRepository, times(1)).deleteById(currency.getId());
    }

    @Test
    @Transactional
    public void searchCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);
        when(mockCurrencySearchRepository.search(queryStringQuery("id:" + currency.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(currency), PageRequest.of(0, 1), 1));
        // Search the currency
        restCurrencyMockMvc.perform(get("/api/_search/currencies?query=id:" + currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currency.class);
        Currency currency1 = new Currency();
        currency1.setId(1L);
        Currency currency2 = new Currency();
        currency2.setId(currency1.getId());
        assertThat(currency1).isEqualTo(currency2);
        currency2.setId(2L);
        assertThat(currency1).isNotEqualTo(currency2);
        currency1.setId(null);
        assertThat(currency1).isNotEqualTo(currency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyDTO.class);
        CurrencyDTO currencyDTO1 = new CurrencyDTO();
        currencyDTO1.setId(1L);
        CurrencyDTO currencyDTO2 = new CurrencyDTO();
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO2.setId(currencyDTO1.getId());
        assertThat(currencyDTO1).isEqualTo(currencyDTO2);
        currencyDTO2.setId(2L);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO1.setId(null);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyMapper.fromId(null)).isNull();
    }
}
