package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Manufacturer;
import org.soptorshi.repository.ManufacturerRepository;
import org.soptorshi.repository.search.ManufacturerSearchRepository;
import org.soptorshi.service.dto.ManufacturerDTO;
import org.soptorshi.service.mapper.ManufacturerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Manufacturer.
 */
@Service
@Transactional
public class ManufacturerService {

    private final Logger log = LoggerFactory.getLogger(ManufacturerService.class);

    private final ManufacturerRepository manufacturerRepository;

    private final ManufacturerMapper manufacturerMapper;

    private final ManufacturerSearchRepository manufacturerSearchRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository, ManufacturerMapper manufacturerMapper, ManufacturerSearchRepository manufacturerSearchRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerMapper = manufacturerMapper;
        this.manufacturerSearchRepository = manufacturerSearchRepository;
    }

    /**
     * Save a manufacturer.
     *
     * @param manufacturerDTO the entity to save
     * @return the persisted entity
     */

    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO) {
        log.debug("Request to save Manufacturer : {}", manufacturerDTO);
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDTO);
        manufacturer = manufacturerRepository.save(manufacturer);
        ManufacturerDTO result = manufacturerMapper.toDto(manufacturer);
        manufacturerSearchRepository.save(manufacturer);
        return result;
    }

    /**
     * Get all the manufacturers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<ManufacturerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Manufacturers");
        return manufacturerRepository.findAll(pageable)
            .map(manufacturerMapper::toDto);
    }


    /**
     * Get one manufacturer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */

    @Transactional(readOnly = true)
    public Optional<ManufacturerDTO> findOne(Long id) {
        log.debug("Request to get Manufacturer : {}", id);
        return manufacturerRepository.findById(id)
            .map(manufacturerMapper::toDto);
    }

    /**
     * Delete the manufacturer by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete Manufacturer : {}", id);
        manufacturerRepository.deleteById(id);
        manufacturerSearchRepository.deleteById(id);
    }

    /**
     * Search for the manufacturer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<ManufacturerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Manufacturers for query {}", query);
        return manufacturerSearchRepository.search(queryStringQuery(query), pageable)
            .map(manufacturerMapper::toDto);
    }
}
