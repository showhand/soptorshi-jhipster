package org.soptorshi.service;

import org.soptorshi.domain.Vendor;
import org.soptorshi.repository.VendorRepository;
import org.soptorshi.repository.search.VendorSearchRepository;
import org.soptorshi.service.dto.VendorDTO;
import org.soptorshi.service.mapper.VendorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Vendor.
 */
@Service
@Transactional
public class VendorService {

    private final Logger log = LoggerFactory.getLogger(VendorService.class);

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    private final VendorSearchRepository vendorSearchRepository;

    public VendorService(VendorRepository vendorRepository, VendorMapper vendorMapper, VendorSearchRepository vendorSearchRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
        this.vendorSearchRepository = vendorSearchRepository;
    }

    /**
     * Save a vendor.
     *
     * @param vendorDTO the entity to save
     * @return the persisted entity
     */
    public VendorDTO save(VendorDTO vendorDTO) {
        log.debug("Request to save Vendor : {}", vendorDTO);
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        VendorDTO result = vendorMapper.toDto(vendor);
        vendorSearchRepository.save(vendor);
        return result;
    }

    /**
     * Get all the vendors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendors");
        return vendorRepository.findAll(pageable)
            .map(vendorMapper::toDto);
    }


    /**
     * Get one vendor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VendorDTO> findOne(Long id) {
        log.debug("Request to get Vendor : {}", id);
        return vendorRepository.findById(id)
            .map(vendorMapper::toDto);
    }

    /**
     * Delete the vendor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vendor : {}", id);
        vendorRepository.deleteById(id);
        vendorSearchRepository.deleteById(id);
    }

    /**
     * Search for the vendor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VendorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vendors for query {}", query);
        return vendorSearchRepository.search(queryStringQuery(query), pageable)
            .map(vendorMapper::toDto);
    }
}
