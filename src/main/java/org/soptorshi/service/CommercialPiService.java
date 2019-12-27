package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.CommercialPoStatus;
import org.soptorshi.repository.CommercialPiRepository;
import org.soptorshi.repository.search.CommercialPiSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPiMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPi.
 */
@Service
@Transactional
public class CommercialPiService {

    private final Logger log = LoggerFactory.getLogger(CommercialPiService.class);

    private final CommercialPiRepository commercialPiRepository;

    private final CommercialPiMapper commercialPiMapper;

    private final CommercialPiSearchRepository commercialPiSearchRepository;

    private final CommercialPoService commercialPoService;

    public CommercialPiService(CommercialPiRepository commercialPiRepository, CommercialPiMapper commercialPiMapper, CommercialPiSearchRepository commercialPiSearchRepository,
                               CommercialPoService commercialPoService) {
        this.commercialPiRepository = commercialPiRepository;
        this.commercialPiMapper = commercialPiMapper;
        this.commercialPiSearchRepository = commercialPiSearchRepository;
        this.commercialPoService = commercialPoService;
    }

    /**
     * Save a commercialPi.
     *
     * @param commercialPiDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPiDTO save(CommercialPiDTO commercialPiDTO) {
        log.debug("Request to save CommercialPi : {}", commercialPiDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        Instant currentDateTime = Instant.now();

        if(commercialPiDTO.getId() == null) {
            commercialPiDTO.setCreatedBy(currentUser);
            commercialPiDTO.setCreatedOn(currentDateTime);
        }
        else {
            if(commercialPiDTO.getPiStatus().equals(CommercialPiStatus.PI_APPROVED_BY_THE_CUSTOMER)) {
                CommercialPoDTO commercialPoDTO = new CommercialPoDTO();
                commercialPoDTO.setCommercialPiId(commercialPiDTO.getId());
                commercialPoDTO.setCommercialPiProformaNo(commercialPiDTO.getProformaNo());
                commercialPoDTO.setPoStatus(CommercialPoStatus.PO_CREATED);
                commercialPoDTO.setPurchaseOrderNo(commercialPiDTO.getPurchaseOrderNo());
                commercialPoService.save(commercialPoDTO);
            }
            commercialPiDTO.setUpdatedBy(currentUser);
            commercialPiDTO.setUpdatedOn(currentDateTime);
        }
        CommercialPi commercialPi = commercialPiMapper.toEntity(commercialPiDTO);
        commercialPi = commercialPiRepository.save(commercialPi);
        CommercialPiDTO result = commercialPiMapper.toDto(commercialPi);
        commercialPiSearchRepository.save(commercialPi);
        return result;
    }

    /**
     * Get all the commercialPis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPiDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPis");
        return commercialPiRepository.findAll(pageable)
            .map(commercialPiMapper::toDto);
    }


    /**
     * Get one commercialPi by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPiDTO> findOne(Long id) {
        log.debug("Request to get CommercialPi : {}", id);
        return commercialPiRepository.findById(id)
            .map(commercialPiMapper::toDto);
    }

    /**
     * Delete the commercialPi by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPi : {}", id);
        commercialPiRepository.deleteById(id);
        commercialPiSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPi corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPiDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPis for query {}", query);
        return commercialPiSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPiMapper::toDto);
    }
}
