package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ConversionFactor;
import org.soptorshi.domain.Currency;
import org.soptorshi.repository.ConversionFactorRepository;
import org.soptorshi.repository.search.ConversionFactorSearchRepository;
import org.soptorshi.service.ConversionFactorService;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.mapper.ConversionFactorMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ConversionFactorCriteria;
import org.soptorshi.service.ConversionFactorQueryService;

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
 * Test class for the ConversionFactorResource REST controller.
 *
 * @see ConversionFactorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ConversionFactorResourceIntTest {

    private static final BigDecimal DEFAULT_COV_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_COV_FACTOR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RCOV_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RCOV_FACTOR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_BCOV_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_BCOV_FACTOR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RBCOV_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RBCOV_FACTOR = new BigDecimal(2);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ConversionFactorRepository conversionFactorRepository;

    @Autowired
    private ConversionFactorMapper conversionFactorMapper;

    @Autowired
    private ConversionFactorService conversionFactorService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ConversionFactorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConversionFactorSearchRepository mockConversionFactorSearchRepository;

    @Autowired
    private ConversionFactorQueryService conversionFactorQueryService;

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

    private MockMvc restConversionFactorMockMvc;

    private ConversionFactor conversionFactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversionFactorResource conversionFactorResource = new ConversionFactorResource(conversionFactorService, conversionFactorQueryService);
        this.restConversionFactorMockMvc = MockMvcBuilders.standaloneSetup(conversionFactorResource)
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
    public static ConversionFactor createEntity(EntityManager em) {
        ConversionFactor conversionFactor = new ConversionFactor()
            .covFactor(DEFAULT_COV_FACTOR)
            .rcovFactor(DEFAULT_RCOV_FACTOR)
            .bcovFactor(DEFAULT_BCOV_FACTOR)
            .rbcovFactor(DEFAULT_RBCOV_FACTOR)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return conversionFactor;
    }

    @Before
    public void initTest() {
        conversionFactor = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversionFactor() throws Exception {
        int databaseSizeBeforeCreate = conversionFactorRepository.findAll().size();

        // Create the ConversionFactor
        ConversionFactorDTO conversionFactorDTO = conversionFactorMapper.toDto(conversionFactor);
        restConversionFactorMockMvc.perform(post("/api/conversion-factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversionFactorDTO)))
            .andExpect(status().isCreated());

        // Validate the ConversionFactor in the database
        List<ConversionFactor> conversionFactorList = conversionFactorRepository.findAll();
        assertThat(conversionFactorList).hasSize(databaseSizeBeforeCreate + 1);
        ConversionFactor testConversionFactor = conversionFactorList.get(conversionFactorList.size() - 1);
        assertThat(testConversionFactor.getCovFactor()).isEqualTo(DEFAULT_COV_FACTOR);
        assertThat(testConversionFactor.getRcovFactor()).isEqualTo(DEFAULT_RCOV_FACTOR);
        assertThat(testConversionFactor.getBcovFactor()).isEqualTo(DEFAULT_BCOV_FACTOR);
        assertThat(testConversionFactor.getRbcovFactor()).isEqualTo(DEFAULT_RBCOV_FACTOR);
        assertThat(testConversionFactor.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testConversionFactor.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ConversionFactor in Elasticsearch
        verify(mockConversionFactorSearchRepository, times(1)).save(testConversionFactor);
    }

    @Test
    @Transactional
    public void createConversionFactorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversionFactorRepository.findAll().size();

        // Create the ConversionFactor with an existing ID
        conversionFactor.setId(1L);
        ConversionFactorDTO conversionFactorDTO = conversionFactorMapper.toDto(conversionFactor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversionFactorMockMvc.perform(post("/api/conversion-factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversionFactorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConversionFactor in the database
        List<ConversionFactor> conversionFactorList = conversionFactorRepository.findAll();
        assertThat(conversionFactorList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConversionFactor in Elasticsearch
        verify(mockConversionFactorSearchRepository, times(0)).save(conversionFactor);
    }

    @Test
    @Transactional
    public void getAllConversionFactors() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList
        restConversionFactorMockMvc.perform(get("/api/conversion-factors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversionFactor.getId().intValue())))
            .andExpect(jsonPath("$.[*].covFactor").value(hasItem(DEFAULT_COV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rcovFactor").value(hasItem(DEFAULT_RCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].bcovFactor").value(hasItem(DEFAULT_BCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rbcovFactor").value(hasItem(DEFAULT_RBCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getConversionFactor() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get the conversionFactor
        restConversionFactorMockMvc.perform(get("/api/conversion-factors/{id}", conversionFactor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversionFactor.getId().intValue()))
            .andExpect(jsonPath("$.covFactor").value(DEFAULT_COV_FACTOR.intValue()))
            .andExpect(jsonPath("$.rcovFactor").value(DEFAULT_RCOV_FACTOR.intValue()))
            .andExpect(jsonPath("$.bcovFactor").value(DEFAULT_BCOV_FACTOR.intValue()))
            .andExpect(jsonPath("$.rbcovFactor").value(DEFAULT_RBCOV_FACTOR.intValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByCovFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where covFactor equals to DEFAULT_COV_FACTOR
        defaultConversionFactorShouldBeFound("covFactor.equals=" + DEFAULT_COV_FACTOR);

        // Get all the conversionFactorList where covFactor equals to UPDATED_COV_FACTOR
        defaultConversionFactorShouldNotBeFound("covFactor.equals=" + UPDATED_COV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByCovFactorIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where covFactor in DEFAULT_COV_FACTOR or UPDATED_COV_FACTOR
        defaultConversionFactorShouldBeFound("covFactor.in=" + DEFAULT_COV_FACTOR + "," + UPDATED_COV_FACTOR);

        // Get all the conversionFactorList where covFactor equals to UPDATED_COV_FACTOR
        defaultConversionFactorShouldNotBeFound("covFactor.in=" + UPDATED_COV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByCovFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where covFactor is not null
        defaultConversionFactorShouldBeFound("covFactor.specified=true");

        // Get all the conversionFactorList where covFactor is null
        defaultConversionFactorShouldNotBeFound("covFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRcovFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rcovFactor equals to DEFAULT_RCOV_FACTOR
        defaultConversionFactorShouldBeFound("rcovFactor.equals=" + DEFAULT_RCOV_FACTOR);

        // Get all the conversionFactorList where rcovFactor equals to UPDATED_RCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("rcovFactor.equals=" + UPDATED_RCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRcovFactorIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rcovFactor in DEFAULT_RCOV_FACTOR or UPDATED_RCOV_FACTOR
        defaultConversionFactorShouldBeFound("rcovFactor.in=" + DEFAULT_RCOV_FACTOR + "," + UPDATED_RCOV_FACTOR);

        // Get all the conversionFactorList where rcovFactor equals to UPDATED_RCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("rcovFactor.in=" + UPDATED_RCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRcovFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rcovFactor is not null
        defaultConversionFactorShouldBeFound("rcovFactor.specified=true");

        // Get all the conversionFactorList where rcovFactor is null
        defaultConversionFactorShouldNotBeFound("rcovFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByBcovFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where bcovFactor equals to DEFAULT_BCOV_FACTOR
        defaultConversionFactorShouldBeFound("bcovFactor.equals=" + DEFAULT_BCOV_FACTOR);

        // Get all the conversionFactorList where bcovFactor equals to UPDATED_BCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("bcovFactor.equals=" + UPDATED_BCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByBcovFactorIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where bcovFactor in DEFAULT_BCOV_FACTOR or UPDATED_BCOV_FACTOR
        defaultConversionFactorShouldBeFound("bcovFactor.in=" + DEFAULT_BCOV_FACTOR + "," + UPDATED_BCOV_FACTOR);

        // Get all the conversionFactorList where bcovFactor equals to UPDATED_BCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("bcovFactor.in=" + UPDATED_BCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByBcovFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where bcovFactor is not null
        defaultConversionFactorShouldBeFound("bcovFactor.specified=true");

        // Get all the conversionFactorList where bcovFactor is null
        defaultConversionFactorShouldNotBeFound("bcovFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRbcovFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rbcovFactor equals to DEFAULT_RBCOV_FACTOR
        defaultConversionFactorShouldBeFound("rbcovFactor.equals=" + DEFAULT_RBCOV_FACTOR);

        // Get all the conversionFactorList where rbcovFactor equals to UPDATED_RBCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("rbcovFactor.equals=" + UPDATED_RBCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRbcovFactorIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rbcovFactor in DEFAULT_RBCOV_FACTOR or UPDATED_RBCOV_FACTOR
        defaultConversionFactorShouldBeFound("rbcovFactor.in=" + DEFAULT_RBCOV_FACTOR + "," + UPDATED_RBCOV_FACTOR);

        // Get all the conversionFactorList where rbcovFactor equals to UPDATED_RBCOV_FACTOR
        defaultConversionFactorShouldNotBeFound("rbcovFactor.in=" + UPDATED_RBCOV_FACTOR);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByRbcovFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where rbcovFactor is not null
        defaultConversionFactorShouldBeFound("rbcovFactor.specified=true");

        // Get all the conversionFactorList where rbcovFactor is null
        defaultConversionFactorShouldNotBeFound("rbcovFactor.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultConversionFactorShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the conversionFactorList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultConversionFactorShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultConversionFactorShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the conversionFactorList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultConversionFactorShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedBy is not null
        defaultConversionFactorShouldBeFound("modifiedBy.specified=true");

        // Get all the conversionFactorList where modifiedBy is null
        defaultConversionFactorShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultConversionFactorShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the conversionFactorList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultConversionFactorShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultConversionFactorShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the conversionFactorList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultConversionFactorShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedOn is not null
        defaultConversionFactorShouldBeFound("modifiedOn.specified=true");

        // Get all the conversionFactorList where modifiedOn is null
        defaultConversionFactorShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultConversionFactorShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the conversionFactorList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultConversionFactorShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllConversionFactorsByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        // Get all the conversionFactorList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultConversionFactorShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the conversionFactorList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultConversionFactorShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }


    @Test
    @Transactional
    public void getAllConversionFactorsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        Currency currency = CurrencyResourceIntTest.createEntity(em);
        em.persist(currency);
        em.flush();
        conversionFactor.setCurrency(currency);
        conversionFactorRepository.saveAndFlush(conversionFactor);
        Long currencyId = currency.getId();

        // Get all the conversionFactorList where currency equals to currencyId
        defaultConversionFactorShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the conversionFactorList where currency equals to currencyId + 1
        defaultConversionFactorShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConversionFactorShouldBeFound(String filter) throws Exception {
        restConversionFactorMockMvc.perform(get("/api/conversion-factors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversionFactor.getId().intValue())))
            .andExpect(jsonPath("$.[*].covFactor").value(hasItem(DEFAULT_COV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rcovFactor").value(hasItem(DEFAULT_RCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].bcovFactor").value(hasItem(DEFAULT_BCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rbcovFactor").value(hasItem(DEFAULT_RBCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restConversionFactorMockMvc.perform(get("/api/conversion-factors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConversionFactorShouldNotBeFound(String filter) throws Exception {
        restConversionFactorMockMvc.perform(get("/api/conversion-factors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConversionFactorMockMvc.perform(get("/api/conversion-factors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConversionFactor() throws Exception {
        // Get the conversionFactor
        restConversionFactorMockMvc.perform(get("/api/conversion-factors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversionFactor() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        int databaseSizeBeforeUpdate = conversionFactorRepository.findAll().size();

        // Update the conversionFactor
        ConversionFactor updatedConversionFactor = conversionFactorRepository.findById(conversionFactor.getId()).get();
        // Disconnect from session so that the updates on updatedConversionFactor are not directly saved in db
        em.detach(updatedConversionFactor);
        updatedConversionFactor
            .covFactor(UPDATED_COV_FACTOR)
            .rcovFactor(UPDATED_RCOV_FACTOR)
            .bcovFactor(UPDATED_BCOV_FACTOR)
            .rbcovFactor(UPDATED_RBCOV_FACTOR)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ConversionFactorDTO conversionFactorDTO = conversionFactorMapper.toDto(updatedConversionFactor);

        restConversionFactorMockMvc.perform(put("/api/conversion-factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversionFactorDTO)))
            .andExpect(status().isOk());

        // Validate the ConversionFactor in the database
        List<ConversionFactor> conversionFactorList = conversionFactorRepository.findAll();
        assertThat(conversionFactorList).hasSize(databaseSizeBeforeUpdate);
        ConversionFactor testConversionFactor = conversionFactorList.get(conversionFactorList.size() - 1);
        assertThat(testConversionFactor.getCovFactor()).isEqualTo(UPDATED_COV_FACTOR);
        assertThat(testConversionFactor.getRcovFactor()).isEqualTo(UPDATED_RCOV_FACTOR);
        assertThat(testConversionFactor.getBcovFactor()).isEqualTo(UPDATED_BCOV_FACTOR);
        assertThat(testConversionFactor.getRbcovFactor()).isEqualTo(UPDATED_RBCOV_FACTOR);
        assertThat(testConversionFactor.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConversionFactor.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ConversionFactor in Elasticsearch
        verify(mockConversionFactorSearchRepository, times(1)).save(testConversionFactor);
    }

    @Test
    @Transactional
    public void updateNonExistingConversionFactor() throws Exception {
        int databaseSizeBeforeUpdate = conversionFactorRepository.findAll().size();

        // Create the ConversionFactor
        ConversionFactorDTO conversionFactorDTO = conversionFactorMapper.toDto(conversionFactor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversionFactorMockMvc.perform(put("/api/conversion-factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversionFactorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConversionFactor in the database
        List<ConversionFactor> conversionFactorList = conversionFactorRepository.findAll();
        assertThat(conversionFactorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConversionFactor in Elasticsearch
        verify(mockConversionFactorSearchRepository, times(0)).save(conversionFactor);
    }

    @Test
    @Transactional
    public void deleteConversionFactor() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);

        int databaseSizeBeforeDelete = conversionFactorRepository.findAll().size();

        // Delete the conversionFactor
        restConversionFactorMockMvc.perform(delete("/api/conversion-factors/{id}", conversionFactor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConversionFactor> conversionFactorList = conversionFactorRepository.findAll();
        assertThat(conversionFactorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConversionFactor in Elasticsearch
        verify(mockConversionFactorSearchRepository, times(1)).deleteById(conversionFactor.getId());
    }

    @Test
    @Transactional
    public void searchConversionFactor() throws Exception {
        // Initialize the database
        conversionFactorRepository.saveAndFlush(conversionFactor);
        when(mockConversionFactorSearchRepository.search(queryStringQuery("id:" + conversionFactor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(conversionFactor), PageRequest.of(0, 1), 1));
        // Search the conversionFactor
        restConversionFactorMockMvc.perform(get("/api/_search/conversion-factors?query=id:" + conversionFactor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversionFactor.getId().intValue())))
            .andExpect(jsonPath("$.[*].covFactor").value(hasItem(DEFAULT_COV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rcovFactor").value(hasItem(DEFAULT_RCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].bcovFactor").value(hasItem(DEFAULT_BCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].rbcovFactor").value(hasItem(DEFAULT_RBCOV_FACTOR.intValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversionFactor.class);
        ConversionFactor conversionFactor1 = new ConversionFactor();
        conversionFactor1.setId(1L);
        ConversionFactor conversionFactor2 = new ConversionFactor();
        conversionFactor2.setId(conversionFactor1.getId());
        assertThat(conversionFactor1).isEqualTo(conversionFactor2);
        conversionFactor2.setId(2L);
        assertThat(conversionFactor1).isNotEqualTo(conversionFactor2);
        conversionFactor1.setId(null);
        assertThat(conversionFactor1).isNotEqualTo(conversionFactor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversionFactorDTO.class);
        ConversionFactorDTO conversionFactorDTO1 = new ConversionFactorDTO();
        conversionFactorDTO1.setId(1L);
        ConversionFactorDTO conversionFactorDTO2 = new ConversionFactorDTO();
        assertThat(conversionFactorDTO1).isNotEqualTo(conversionFactorDTO2);
        conversionFactorDTO2.setId(conversionFactorDTO1.getId());
        assertThat(conversionFactorDTO1).isEqualTo(conversionFactorDTO2);
        conversionFactorDTO2.setId(2L);
        assertThat(conversionFactorDTO1).isNotEqualTo(conversionFactorDTO2);
        conversionFactorDTO1.setId(null);
        assertThat(conversionFactorDTO1).isNotEqualTo(conversionFactorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conversionFactorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conversionFactorMapper.fromId(null)).isNull();
    }
}
