package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialInvoice;
import org.soptorshi.repository.CommercialInvoiceRepository;
import org.soptorshi.repository.search.CommercialInvoiceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.CommercialInvoiceDTO;
import org.soptorshi.service.mapper.CommercialInvoiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialInvoice.
 */
@Service
@Transactional
public class CommercialInvoiceService {

    private final Logger log = LoggerFactory.getLogger(CommercialInvoiceService.class);

    private final CommercialInvoiceRepository commercialInvoiceRepository;

    private final CommercialInvoiceMapper commercialInvoiceMapper;

    private final CommercialInvoiceSearchRepository commercialInvoiceSearchRepository;

    public CommercialInvoiceService(CommercialInvoiceRepository commercialInvoiceRepository, CommercialInvoiceMapper commercialInvoiceMapper, CommercialInvoiceSearchRepository commercialInvoiceSearchRepository) {
        this.commercialInvoiceRepository = commercialInvoiceRepository;
        this.commercialInvoiceMapper = commercialInvoiceMapper;
        this.commercialInvoiceSearchRepository = commercialInvoiceSearchRepository;
    }

    /**
     * Save a commercialInvoice.
     *
     * @param commercialInvoiceDTO the entity to save
     * @return the persisted entity
     */
    public CommercialInvoiceDTO save(CommercialInvoiceDTO commercialInvoiceDTO) {
        log.debug("Request to save CommercialInvoice : {}", commercialInvoiceDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        LocalDate currentDate = LocalDate.now();
        if (commercialInvoiceDTO.getId() == null) {
            commercialInvoiceDTO.setCreatedBy(currentUser);
            commercialInvoiceDTO.setCreateOn(currentDate);
        } else {
            commercialInvoiceDTO.setUpdatedBy(currentUser);
            commercialInvoiceDTO.setUpdatedOn(currentDate);
        }
        CommercialInvoice commercialInvoice = commercialInvoiceMapper.toEntity(commercialInvoiceDTO);
        commercialInvoice = commercialInvoiceRepository.save(commercialInvoice);
        CommercialInvoiceDTO result = commercialInvoiceMapper.toDto(commercialInvoice);
        commercialInvoiceSearchRepository.save(commercialInvoice);
        return result;
    }

    /**
     * Get all the commercialInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialInvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialInvoices");
        return commercialInvoiceRepository.findAll(pageable)
            .map(commercialInvoiceMapper::toDto);
    }


    /**
     * Get one commercialInvoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialInvoiceDTO> findOne(Long id) {
        log.debug("Request to get CommercialInvoice : {}", id);
        return commercialInvoiceRepository.findById(id)
            .map(commercialInvoiceMapper::toDto);
    }

    /**
     * Delete the commercialInvoice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialInvoice : {}", id);
        /*commercialInvoiceRepository.deleteById(id);
        commercialInvoiceSearchRepository.deleteById(id);*/
    }

    /**
     * Search for the commercialInvoice corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialInvoiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialInvoices for query {}", query);
        return commercialInvoiceSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialInvoiceMapper::toDto);
    }
}
