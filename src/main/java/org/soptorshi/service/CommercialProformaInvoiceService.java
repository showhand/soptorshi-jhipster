package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProformaInvoice;
import org.soptorshi.repository.CommercialProformaInvoiceRepository;
import org.soptorshi.repository.search.CommercialProformaInvoiceSearchRepository;
import org.soptorshi.service.dto.CommercialProformaInvoiceDTO;
import org.soptorshi.service.mapper.CommercialProformaInvoiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialProformaInvoice.
 */
@Service
@Transactional
public class CommercialProformaInvoiceService {

    private final Logger log = LoggerFactory.getLogger(CommercialProformaInvoiceService.class);

    private final CommercialProformaInvoiceRepository commercialProformaInvoiceRepository;

    private final CommercialProformaInvoiceMapper commercialProformaInvoiceMapper;

    private final CommercialProformaInvoiceSearchRepository commercialProformaInvoiceSearchRepository;

    public CommercialProformaInvoiceService(CommercialProformaInvoiceRepository commercialProformaInvoiceRepository, CommercialProformaInvoiceMapper commercialProformaInvoiceMapper, CommercialProformaInvoiceSearchRepository commercialProformaInvoiceSearchRepository) {
        this.commercialProformaInvoiceRepository = commercialProformaInvoiceRepository;
        this.commercialProformaInvoiceMapper = commercialProformaInvoiceMapper;
        this.commercialProformaInvoiceSearchRepository = commercialProformaInvoiceSearchRepository;
    }

    /**
     * Save a commercialProformaInvoice.
     *
     * @param commercialProformaInvoiceDTO the entity to save
     * @return the persisted entity
     */
    public CommercialProformaInvoiceDTO save(CommercialProformaInvoiceDTO commercialProformaInvoiceDTO) {
        log.debug("Request to save CommercialProformaInvoice : {}", commercialProformaInvoiceDTO);
        CommercialProformaInvoice commercialProformaInvoice = commercialProformaInvoiceMapper.toEntity(commercialProformaInvoiceDTO);
        commercialProformaInvoice = commercialProformaInvoiceRepository.save(commercialProformaInvoice);
        CommercialProformaInvoiceDTO result = commercialProformaInvoiceMapper.toDto(commercialProformaInvoice);
        commercialProformaInvoiceSearchRepository.save(commercialProformaInvoice);
        return result;
    }

    /**
     * Get all the commercialProformaInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProformaInvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialProformaInvoices");
        return commercialProformaInvoiceRepository.findAll(pageable)
            .map(commercialProformaInvoiceMapper::toDto);
    }


    /**
     * Get one commercialProformaInvoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialProformaInvoiceDTO> findOne(Long id) {
        log.debug("Request to get CommercialProformaInvoice : {}", id);
        return commercialProformaInvoiceRepository.findById(id)
            .map(commercialProformaInvoiceMapper::toDto);
    }

    /**
     * Delete the commercialProformaInvoice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialProformaInvoice : {}", id);
        commercialProformaInvoiceRepository.deleteById(id);
        commercialProformaInvoiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialProformaInvoice corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProformaInvoiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialProformaInvoices for query {}", query);
        return commercialProformaInvoiceSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialProformaInvoiceMapper::toDto);
    }
}
