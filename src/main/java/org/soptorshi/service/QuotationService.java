package org.soptorshi.service;

import org.soptorshi.domain.Quotation;
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.search.QuotationSearchRepository;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.mapper.QuotationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Quotation.
 */
@Service
@Transactional
public class QuotationService {

    private final Logger log = LoggerFactory.getLogger(QuotationService.class);

    private final QuotationRepository quotationRepository;

    private final QuotationMapper quotationMapper;

    private final QuotationSearchRepository quotationSearchRepository;

    public QuotationService(QuotationRepository quotationRepository, QuotationMapper quotationMapper, QuotationSearchRepository quotationSearchRepository) {
        this.quotationRepository = quotationRepository;
        this.quotationMapper = quotationMapper;
        this.quotationSearchRepository = quotationSearchRepository;
    }

    /**
     * Save a quotation.
     *
     * @param quotationDTO the entity to save
     * @return the persisted entity
     */
    public QuotationDTO save(QuotationDTO quotationDTO) {
        log.debug("Request to save Quotation : {}", quotationDTO);
        Quotation quotation = quotationMapper.toEntity(quotationDTO);
        quotation = quotationRepository.save(quotation);
        QuotationDTO result = quotationMapper.toDto(quotation);
        quotationSearchRepository.save(quotation);
        return result;
    }

    /**
     * Get all the quotations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuotationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quotations");
        return quotationRepository.findAll(pageable)
            .map(quotationMapper::toDto);
    }


    /**
     * Get one quotation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<QuotationDTO> findOne(Long id) {
        log.debug("Request to get Quotation : {}", id);
        return quotationRepository.findById(id)
            .map(quotationMapper::toDto);
    }

    /**
     * Delete the quotation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
        quotationSearchRepository.deleteById(id);
    }

    /**
     * Search for the quotation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuotationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Quotations for query {}", query);
        return quotationSearchRepository.search(queryStringQuery(query), pageable)
            .map(quotationMapper::toDto);
    }
}
