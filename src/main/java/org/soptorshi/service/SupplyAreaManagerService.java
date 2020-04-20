package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.repository.SupplyAreaManagerRepository;
import org.soptorshi.repository.search.SupplyAreaManagerSearchRepository;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaManagerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyAreaManager.
 */
@Service
@Transactional
public class SupplyAreaManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerService.class);

    private final SupplyAreaManagerRepository supplyAreaManagerRepository;

    private final SupplyAreaManagerMapper supplyAreaManagerMapper;

    private final SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository;

    public SupplyAreaManagerService(SupplyAreaManagerRepository supplyAreaManagerRepository, SupplyAreaManagerMapper supplyAreaManagerMapper, SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository) {
        this.supplyAreaManagerRepository = supplyAreaManagerRepository;
        this.supplyAreaManagerMapper = supplyAreaManagerMapper;
        this.supplyAreaManagerSearchRepository = supplyAreaManagerSearchRepository;
    }

    /**
     * Save a supplyAreaManager.
     *
     * @param supplyAreaManagerDTO the entity to save
     * @return the persisted entity
     */
    public SupplyAreaManagerDTO save(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        log.debug("Request to save SupplyAreaManager : {}", supplyAreaManagerDTO);
        SupplyAreaManager supplyAreaManager = supplyAreaManagerMapper.toEntity(supplyAreaManagerDTO);
        supplyAreaManager = supplyAreaManagerRepository.save(supplyAreaManager);
        SupplyAreaManagerDTO result = supplyAreaManagerMapper.toDto(supplyAreaManager);
        supplyAreaManagerSearchRepository.save(supplyAreaManager);
        return result;
    }

    /**
     * Get all the supplyAreaManagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaManagerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyAreaManagers");
        return supplyAreaManagerRepository.findAll(pageable)
            .map(supplyAreaManagerMapper::toDto);
    }


    /**
     * Get one supplyAreaManager by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyAreaManagerDTO> findOne(Long id) {
        log.debug("Request to get SupplyAreaManager : {}", id);
        return supplyAreaManagerRepository.findById(id)
            .map(supplyAreaManagerMapper::toDto);
    }

    /**
     * Delete the supplyAreaManager by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyAreaManager : {}", id);
        supplyAreaManagerRepository.deleteById(id);
        supplyAreaManagerSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyAreaManager corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaManagerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyAreaManagers for query {}", query);
        return supplyAreaManagerSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyAreaManagerMapper::toDto);
    }
}
