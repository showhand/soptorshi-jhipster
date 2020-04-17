package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.repository.SupplyZoneManagerRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyZoneManager.
 */
@Service
@Transactional
public class SupplyZoneManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerService.class);

    private final SupplyZoneManagerRepository supplyZoneManagerRepository;

    private final SupplyZoneManagerMapper supplyZoneManagerMapper;

    private final SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository;

    public SupplyZoneManagerService(SupplyZoneManagerRepository supplyZoneManagerRepository, SupplyZoneManagerMapper supplyZoneManagerMapper, SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository) {
        this.supplyZoneManagerRepository = supplyZoneManagerRepository;
        this.supplyZoneManagerMapper = supplyZoneManagerMapper;
        this.supplyZoneManagerSearchRepository = supplyZoneManagerSearchRepository;
    }

    /**
     * Save a supplyZoneManager.
     *
     * @param supplyZoneManagerDTO the entity to save
     * @return the persisted entity
     */
    public SupplyZoneManagerDTO save(SupplyZoneManagerDTO supplyZoneManagerDTO) {
        log.debug("Request to save SupplyZoneManager : {}", supplyZoneManagerDTO);
        SupplyZoneManager supplyZoneManager = supplyZoneManagerMapper.toEntity(supplyZoneManagerDTO);
        supplyZoneManager = supplyZoneManagerRepository.save(supplyZoneManager);
        SupplyZoneManagerDTO result = supplyZoneManagerMapper.toDto(supplyZoneManager);
        supplyZoneManagerSearchRepository.save(supplyZoneManager);
        return result;
    }

    /**
     * Get all the supplyZoneManagers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SupplyZoneManagerDTO> findAll() {
        log.debug("Request to get all SupplyZoneManagers");
        return supplyZoneManagerRepository.findAll().stream()
            .map(supplyZoneManagerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplyZoneManager by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyZoneManagerDTO> findOne(Long id) {
        log.debug("Request to get SupplyZoneManager : {}", id);
        return supplyZoneManagerRepository.findById(id)
            .map(supplyZoneManagerMapper::toDto);
    }

    /**
     * Delete the supplyZoneManager by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyZoneManager : {}", id);
        supplyZoneManagerRepository.deleteById(id);
        supplyZoneManagerSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyZoneManager corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SupplyZoneManagerDTO> search(String query) {
        log.debug("Request to search SupplyZoneManagers for query {}", query);
        return StreamSupport
            .stream(supplyZoneManagerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(supplyZoneManagerMapper::toDto)
            .collect(Collectors.toList());
    }
}
