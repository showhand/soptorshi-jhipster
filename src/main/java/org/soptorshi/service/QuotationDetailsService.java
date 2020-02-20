package org.soptorshi.service;

import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.repository.QuotationDetailsRepository;
import org.soptorshi.repository.search.QuotationDetailsSearchRepository;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.mapper.QuotationDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuotationDetails.
 */
@Service
@Transactional
public class QuotationDetailsService {

    private final Logger log = LoggerFactory.getLogger(QuotationDetailsService.class);

    private final QuotationDetailsRepository quotationDetailsRepository;

    private final QuotationDetailsMapper quotationDetailsMapper;

    private final QuotationDetailsSearchRepository quotationDetailsSearchRepository;

    public QuotationDetailsService(QuotationDetailsRepository quotationDetailsRepository, QuotationDetailsMapper quotationDetailsMapper, QuotationDetailsSearchRepository quotationDetailsSearchRepository) {
        this.quotationDetailsRepository = quotationDetailsRepository;
        this.quotationDetailsMapper = quotationDetailsMapper;
        this.quotationDetailsSearchRepository = quotationDetailsSearchRepository;
    }

    /**
     * Save a quotationDetails.
     *
     * @param quotationDetailsDTO the entity to save
     * @return the persisted entity
     */
    public QuotationDetailsDTO save(QuotationDetailsDTO quotationDetailsDTO) {
        log.debug("Request to save QuotationDetails : {}", quotationDetailsDTO);
        QuotationDetails quotationDetails = quotationDetailsMapper.toEntity(quotationDetailsDTO);
        quotationDetails = quotationDetailsRepository.save(quotationDetails);
        QuotationDetailsDTO result = quotationDetailsMapper.toDto(quotationDetails);
        quotationDetailsSearchRepository.save(quotationDetails);
        return result;
    }

    /**
     * Get all the quotationDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuotationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuotationDetails");
        return quotationDetailsRepository.findAll(pageable)
            .map(quotationDetailsMapper::toDto);
    }


    /**
     * Get one quotationDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<QuotationDetailsDTO> findOne(Long id) {
        log.debug("Request to get QuotationDetails : {}", id);
        return quotationDetailsRepository.findById(id)
            .map(quotationDetailsMapper::toDto);
    }

    /**
     * Delete the quotationDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete QuotationDetails : {}", id);
        quotationDetailsRepository.deleteById(id);
        quotationDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the quotationDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuotationDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuotationDetails for query {}", query);
        return quotationDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(quotationDetailsMapper::toDto);
    }
}
