package org.soptorshi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyMoneyCollection;
import org.soptorshi.repository.SupplyMoneyCollectionRepository;
import org.soptorshi.repository.search.SupplyMoneyCollectionSearchRepository;
import org.soptorshi.service.SupplyMoneyCollectionService;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;
import org.soptorshi.service.mapper.SupplyMoneyCollectionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyMoneyCollection.
 */
@Service
@Transactional
public class SupplyMoneyCollectionServiceImpl implements SupplyMoneyCollectionService {

    private final Logger log = LoggerFactory.getLogger(SupplyMoneyCollectionServiceImpl.class);

    private final SupplyMoneyCollectionRepository supplyMoneyCollectionRepository;

    private final SupplyMoneyCollectionMapper supplyMoneyCollectionMapper;

    private final SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository;

    public SupplyMoneyCollectionServiceImpl(SupplyMoneyCollectionRepository supplyMoneyCollectionRepository, SupplyMoneyCollectionMapper supplyMoneyCollectionMapper, SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository) {
        this.supplyMoneyCollectionRepository = supplyMoneyCollectionRepository;
        this.supplyMoneyCollectionMapper = supplyMoneyCollectionMapper;
        this.supplyMoneyCollectionSearchRepository = supplyMoneyCollectionSearchRepository;
    }

    /**
     * Save a supplyMoneyCollection.
     *
     * @param supplyMoneyCollectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupplyMoneyCollectionDTO save(SupplyMoneyCollectionDTO supplyMoneyCollectionDTO) {
        log.debug("Request to save SupplyMoneyCollection : {}", supplyMoneyCollectionDTO);
        SupplyMoneyCollection supplyMoneyCollection = supplyMoneyCollectionMapper.toEntity(supplyMoneyCollectionDTO);
        supplyMoneyCollection = supplyMoneyCollectionRepository.save(supplyMoneyCollection);
        SupplyMoneyCollectionDTO result = supplyMoneyCollectionMapper.toDto(supplyMoneyCollection);
        supplyMoneyCollectionSearchRepository.save(supplyMoneyCollection);
        return result;
    }

    /**
     * Get all the supplyMoneyCollections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyMoneyCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyMoneyCollections");
        return supplyMoneyCollectionRepository.findAll(pageable)
            .map(supplyMoneyCollectionMapper::toDto);
    }


    /**
     * Get one supplyMoneyCollection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyMoneyCollectionDTO> findOne(Long id) {
        log.debug("Request to get SupplyMoneyCollection : {}", id);
        return supplyMoneyCollectionRepository.findById(id)
            .map(supplyMoneyCollectionMapper::toDto);
    }

    /**
     * Delete the supplyMoneyCollection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplyMoneyCollection : {}", id);
        supplyMoneyCollectionRepository.deleteById(id);
        supplyMoneyCollectionSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyMoneyCollection corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyMoneyCollectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyMoneyCollections for query {}", query);
        return supplyMoneyCollectionSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyMoneyCollectionMapper::toDto);
    }
}
