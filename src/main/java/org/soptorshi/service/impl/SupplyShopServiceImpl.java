package org.soptorshi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyShop;
import org.soptorshi.repository.SupplyShopRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.dto.SupplyShopDTO;
import org.soptorshi.service.mapper.SupplyShopMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyShop.
 */
@Service
@Transactional
public class SupplyShopServiceImpl implements SupplyShopService {

    private final Logger log = LoggerFactory.getLogger(SupplyShopServiceImpl.class);

    private final SupplyShopRepository supplyShopRepository;

    private final SupplyShopMapper supplyShopMapper;

    private final SupplyShopSearchRepository supplyShopSearchRepository;

    public SupplyShopServiceImpl(SupplyShopRepository supplyShopRepository, SupplyShopMapper supplyShopMapper, SupplyShopSearchRepository supplyShopSearchRepository) {
        this.supplyShopRepository = supplyShopRepository;
        this.supplyShopMapper = supplyShopMapper;
        this.supplyShopSearchRepository = supplyShopSearchRepository;
    }

    /**
     * Save a supplyShop.
     *
     * @param supplyShopDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupplyShopDTO save(SupplyShopDTO supplyShopDTO) {
        log.debug("Request to save SupplyShop : {}", supplyShopDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        Instant currentDateTime = Instant.now();
        if(supplyShopDTO.getId() == null) {
            supplyShopDTO.setCreatedBy(currentUser);
            supplyShopDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyShopDTO.setUpdatedBy(currentUser);
            supplyShopDTO.setUpdatedOn(currentDateTime);
        }
        SupplyShop supplyShop = supplyShopMapper.toEntity(supplyShopDTO);
        supplyShop = supplyShopRepository.save(supplyShop);
        SupplyShopDTO result = supplyShopMapper.toDto(supplyShop);
        supplyShopSearchRepository.save(supplyShop);
        return result;
    }

    /**
     * Get all the supplyShops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyShopDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyShops");
        return supplyShopRepository.findAll(pageable)
            .map(supplyShopMapper::toDto);
    }


    /**
     * Get one supplyShop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyShopDTO> findOne(Long id) {
        log.debug("Request to get SupplyShop : {}", id);
        return supplyShopRepository.findById(id)
            .map(supplyShopMapper::toDto);
    }

    /**
     * Delete the supplyShop by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplyShop : {}", id);
        supplyShopRepository.deleteById(id);
        supplyShopSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyShop corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyShopDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyShops for query {}", query);
        return supplyShopSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyShopMapper::toDto);
    }
}
