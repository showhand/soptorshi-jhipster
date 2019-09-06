package org.soptorshi.service;

import org.soptorshi.domain.ConversionFactor;
import org.soptorshi.repository.ConversionFactorRepository;
import org.soptorshi.repository.search.ConversionFactorSearchRepository;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.mapper.ConversionFactorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ConversionFactor.
 */
@Service
@Transactional
public class ConversionFactorService {

    private final Logger log = LoggerFactory.getLogger(ConversionFactorService.class);

    private final ConversionFactorRepository conversionFactorRepository;

    private final ConversionFactorMapper conversionFactorMapper;

    private final ConversionFactorSearchRepository conversionFactorSearchRepository;

    public ConversionFactorService(ConversionFactorRepository conversionFactorRepository, ConversionFactorMapper conversionFactorMapper, ConversionFactorSearchRepository conversionFactorSearchRepository) {
        this.conversionFactorRepository = conversionFactorRepository;
        this.conversionFactorMapper = conversionFactorMapper;
        this.conversionFactorSearchRepository = conversionFactorSearchRepository;
    }

    /**
     * Save a conversionFactor.
     *
     * @param conversionFactorDTO the entity to save
     * @return the persisted entity
     */
    public ConversionFactorDTO save(ConversionFactorDTO conversionFactorDTO) {
        log.debug("Request to save ConversionFactor : {}", conversionFactorDTO);
        ConversionFactor conversionFactor = conversionFactorMapper.toEntity(conversionFactorDTO);
        conversionFactor = conversionFactorRepository.save(conversionFactor);
        ConversionFactorDTO result = conversionFactorMapper.toDto(conversionFactor);
        conversionFactorSearchRepository.save(conversionFactor);
        return result;
    }

    /**
     * Get all the conversionFactors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConversionFactorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConversionFactors");
        return conversionFactorRepository.findAll(pageable)
            .map(conversionFactorMapper::toDto);
    }


    /**
     * Get one conversionFactor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ConversionFactorDTO> findOne(Long id) {
        log.debug("Request to get ConversionFactor : {}", id);
        return conversionFactorRepository.findById(id)
            .map(conversionFactorMapper::toDto);
    }

    /**
     * Delete the conversionFactor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConversionFactor : {}", id);
        conversionFactorRepository.deleteById(id);
        conversionFactorSearchRepository.deleteById(id);
    }

    /**
     * Search for the conversionFactor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConversionFactorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConversionFactors for query {}", query);
        return conversionFactorSearchRepository.search(queryStringQuery(query), pageable)
            .map(conversionFactorMapper::toDto);
    }
}
