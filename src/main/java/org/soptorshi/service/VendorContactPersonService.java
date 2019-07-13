package org.soptorshi.service;

import org.soptorshi.domain.VendorContactPerson;
import org.soptorshi.repository.VendorContactPersonRepository;
import org.soptorshi.repository.search.VendorContactPersonSearchRepository;
import org.soptorshi.service.dto.VendorContactPersonDTO;
import org.soptorshi.service.mapper.VendorContactPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VendorContactPerson.
 */
@Service
@Transactional
public class VendorContactPersonService {

    private final Logger log = LoggerFactory.getLogger(VendorContactPersonService.class);

    private final VendorContactPersonRepository vendorContactPersonRepository;

    private final VendorContactPersonMapper vendorContactPersonMapper;

    private final VendorContactPersonSearchRepository vendorContactPersonSearchRepository;

    public VendorContactPersonService(VendorContactPersonRepository vendorContactPersonRepository, VendorContactPersonMapper vendorContactPersonMapper, VendorContactPersonSearchRepository vendorContactPersonSearchRepository) {
        this.vendorContactPersonRepository = vendorContactPersonRepository;
        this.vendorContactPersonMapper = vendorContactPersonMapper;
        this.vendorContactPersonSearchRepository = vendorContactPersonSearchRepository;
    }

    /**
     * Save a vendorContactPerson.
     *
     * @param vendorContactPersonDTO the entity to save
     * @return the persisted entity
     */
    public VendorContactPersonDTO save(VendorContactPersonDTO vendorContactPersonDTO) {
        log.debug("Request to save VendorContactPerson : {}", vendorContactPersonDTO);
        VendorContactPerson vendorContactPerson = vendorContactPersonMapper.toEntity(vendorContactPersonDTO);
        vendorContactPerson = vendorContactPersonRepository.save(vendorContactPerson);
        VendorContactPersonDTO result = vendorContactPersonMapper.toDto(vendorContactPerson);
        vendorContactPersonSearchRepository.save(vendorContactPerson);
        return result;
    }

    /**
     * Get all the vendorContactPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendorContactPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VendorContactPeople");
        return vendorContactPersonRepository.findAll(pageable)
            .map(vendorContactPersonMapper::toDto);
    }


    /**
     * Get one vendorContactPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VendorContactPersonDTO> findOne(Long id) {
        log.debug("Request to get VendorContactPerson : {}", id);
        return vendorContactPersonRepository.findById(id)
            .map(vendorContactPersonMapper::toDto);
    }

    /**
     * Delete the vendorContactPerson by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VendorContactPerson : {}", id);
        vendorContactPersonRepository.deleteById(id);
        vendorContactPersonSearchRepository.deleteById(id);
    }

    /**
     * Search for the vendorContactPerson corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendorContactPersonDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VendorContactPeople for query {}", query);
        return vendorContactPersonSearchRepository.search(queryStringQuery(query), pageable)
            .map(vendorContactPersonMapper::toDto);
    }
}
