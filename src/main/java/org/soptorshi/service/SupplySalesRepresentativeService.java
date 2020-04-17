package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplySalesRepresentative;
import org.soptorshi.repository.SupplySalesRepresentativeRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.mapper.SupplySalesRepresentativeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplySalesRepresentative.
 */
@Service
@Transactional
public class SupplySalesRepresentativeService {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeService.class);

    private final SupplySalesRepresentativeRepository supplySalesRepresentativeRepository;

    private final SupplySalesRepresentativeMapper supplySalesRepresentativeMapper;

    private final SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository;

    public SupplySalesRepresentativeService(SupplySalesRepresentativeRepository supplySalesRepresentativeRepository, SupplySalesRepresentativeMapper supplySalesRepresentativeMapper, SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository) {
        this.supplySalesRepresentativeRepository = supplySalesRepresentativeRepository;
        this.supplySalesRepresentativeMapper = supplySalesRepresentativeMapper;
        this.supplySalesRepresentativeSearchRepository = supplySalesRepresentativeSearchRepository;
    }

    /**
     * Save a supplySalesRepresentative.
     *
     * @param supplySalesRepresentativeDTO the entity to save
     * @return the persisted entity
     */
    public SupplySalesRepresentativeDTO save(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        log.debug("Request to save SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);
        SupplySalesRepresentative supplySalesRepresentative = supplySalesRepresentativeMapper.toEntity(supplySalesRepresentativeDTO);
        supplySalesRepresentative = supplySalesRepresentativeRepository.save(supplySalesRepresentative);
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);
        supplySalesRepresentativeSearchRepository.save(supplySalesRepresentative);
        return result;
    }

    /**
     * Get all the supplySalesRepresentatives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplySalesRepresentativeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplySalesRepresentatives");
        return supplySalesRepresentativeRepository.findAll(pageable)
            .map(supplySalesRepresentativeMapper::toDto);
    }


    /**
     * Get one supplySalesRepresentative by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplySalesRepresentativeDTO> findOne(Long id) {
        log.debug("Request to get SupplySalesRepresentative : {}", id);
        return supplySalesRepresentativeRepository.findById(id)
            .map(supplySalesRepresentativeMapper::toDto);
    }

    /**
     * Delete the supplySalesRepresentative by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplySalesRepresentative : {}", id);
        supplySalesRepresentativeRepository.deleteById(id);
        supplySalesRepresentativeSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplySalesRepresentative corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplySalesRepresentativeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplySalesRepresentatives for query {}", query);
        return supplySalesRepresentativeSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplySalesRepresentativeMapper::toDto);
    }
}
