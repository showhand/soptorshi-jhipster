package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.repository.CommercialPackagingRepository;
import org.soptorshi.repository.search.CommercialPackagingSearchRepository;
import org.soptorshi.service.dto.CommercialPackagingDTO;
import org.soptorshi.service.mapper.CommercialPackagingMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPackaging.
 */
@Service
@Transactional
public class CommercialPackagingService {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingService.class);

    private final CommercialPackagingRepository commercialPackagingRepository;

    private final CommercialPackagingMapper commercialPackagingMapper;

    private final CommercialPackagingSearchRepository commercialPackagingSearchRepository;

    public CommercialPackagingService(CommercialPackagingRepository commercialPackagingRepository, CommercialPackagingMapper commercialPackagingMapper, CommercialPackagingSearchRepository commercialPackagingSearchRepository) {
        this.commercialPackagingRepository = commercialPackagingRepository;
        this.commercialPackagingMapper = commercialPackagingMapper;
        this.commercialPackagingSearchRepository = commercialPackagingSearchRepository;
    }

    /**
     * Save a commercialPackaging.
     *
     * @param commercialPackagingDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPackagingDTO save(CommercialPackagingDTO commercialPackagingDTO) {
        log.debug("Request to save CommercialPackaging : {}", commercialPackagingDTO);
        CommercialPackaging commercialPackaging = commercialPackagingMapper.toEntity(commercialPackagingDTO);
        commercialPackaging = commercialPackagingRepository.save(commercialPackaging);
        CommercialPackagingDTO result = commercialPackagingMapper.toDto(commercialPackaging);
        commercialPackagingSearchRepository.save(commercialPackaging);
        return result;
    }

    /**
     * Get all the commercialPackagings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPackagings");
        return commercialPackagingRepository.findAll(pageable)
            .map(commercialPackagingMapper::toDto);
    }


    /**
     * Get one commercialPackaging by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPackagingDTO> findOne(Long id) {
        log.debug("Request to get CommercialPackaging : {}", id);
        return commercialPackagingRepository.findById(id)
            .map(commercialPackagingMapper::toDto);
    }

    /**
     * Delete the commercialPackaging by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPackaging : {}", id);
        commercialPackagingRepository.deleteById(id);
        commercialPackagingSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPackaging corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPackagings for query {}", query);
        return commercialPackagingSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPackagingMapper::toDto);
    }
}
