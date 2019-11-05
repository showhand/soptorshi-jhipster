package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.domain.enumeration.CommercialStatus;
import org.soptorshi.repository.CommercialPackagingRepository;
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.extended.CommercialPoStatusExtendedRepository;
import org.soptorshi.repository.search.CommercialPackagingSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPackagingService;
import org.soptorshi.service.CommercialPoStatusService;
import org.soptorshi.service.dto.CommercialPackagingDTO;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.mapper.CommercialPackagingMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPackaging.
 */
@Service
@Transactional
public class CommercialPackagingExtendedService extends CommercialPackagingService {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingExtendedService.class);

    private final CommercialPackagingRepository commercialPackagingRepository;

    private final CommercialPackagingMapper commercialPackagingMapper;

    private final CommercialPackagingSearchRepository commercialPackagingSearchRepository;

    private final CommercialPoStatusExtendedRepository commercialPoStatusRepository;

    private final CommercialPoStatusService commercialPoStatusService;

    private final CommercialPurchaseOrderRepository commercialPurchaseOrderRepository;

    public CommercialPackagingExtendedService(CommercialPackagingRepository commercialPackagingRepository, CommercialPackagingMapper commercialPackagingMapper, CommercialPackagingSearchRepository commercialPackagingSearchRepository, CommercialPoStatusExtendedRepository commercialPoStatusRepository, CommercialPoStatusService commercialPoStatusService, CommercialPurchaseOrderRepository commercialPurchaseOrderRepository) {
        super(commercialPackagingRepository, commercialPackagingMapper, commercialPackagingSearchRepository);
        this.commercialPackagingRepository = commercialPackagingRepository;
        this.commercialPackagingMapper = commercialPackagingMapper;
        this.commercialPackagingSearchRepository = commercialPackagingSearchRepository;
        this.commercialPoStatusRepository = commercialPoStatusRepository;
        this.commercialPoStatusService = commercialPoStatusService;
        this.commercialPurchaseOrderRepository = commercialPurchaseOrderRepository;
    }

    /**
     * Save a commercialPackaging.
     *
     * @param commercialPackagingDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPackagingDTO save(CommercialPackagingDTO commercialPackagingDTO) {
        log.debug("Request to save CommercialPackaging : {}", commercialPackagingDTO);
        CommercialPoStatus commercialPoStatus = commercialPoStatusRepository.findTopByCommercialPurchaseOrderOrderByCreateOnDesc(
            commercialPurchaseOrderRepository.getOne(commercialPackagingDTO.getCommercialPurchaseOrderId()));

        if(commercialPoStatus.getStatus().equals(CommercialStatus.PAYMENT_COMPLETED_AND_WAITING_FOR_ARTWORK_OF_PACKAGING)) {
            String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
            LocalDate currentDate = LocalDate.now();
            if (commercialPackagingDTO.getId() == null) {
                commercialPackagingDTO.setCreatedBy(currentUser);
                commercialPackagingDTO.setCreateOn(currentDate);
                updateCommercialStatus(commercialPackagingDTO, currentUser, currentDate);
            } else {
                commercialPackagingDTO.setUpdatedBy(currentUser);
                commercialPackagingDTO.setUpdatedOn(currentDate);
            }
            CommercialPackaging commercialPackaging = commercialPackagingMapper.toEntity(commercialPackagingDTO);
            commercialPackaging = commercialPackagingRepository.save(commercialPackaging);
            CommercialPackagingDTO result = commercialPackagingMapper.toDto(commercialPackaging);
            commercialPackagingSearchRepository.save(commercialPackaging);
            return result;
        }
        else {
            return null;
        }
    }

    private void updateCommercialStatus(CommercialPackagingDTO commercialPackagingDTO, String currentUser, LocalDate currentDate) {
        CommercialPoStatusDTO commercialPoStatusDTO = new CommercialPoStatusDTO();
        commercialPoStatusDTO.setStatus(CommercialStatus.ARTWORK_OF_PACKAGING_APPROVED_AND_ISSUE_WORK_ORDER_FOR_PACKAGING_ACCESSORIES);
        commercialPoStatusDTO.setCommercialPurchaseOrderId(commercialPackagingDTO.getCommercialPurchaseOrderId());
        commercialPoStatusDTO.setCreatedBy(currentUser);
        commercialPoStatusDTO.setCreateOn(currentDate);
        commercialPoStatusService.save(commercialPoStatusDTO);
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
        /*commercialPackagingRepository.deleteById(id);
        commercialPackagingSearchRepository.deleteById(id);*/
    }

    /**
     * Search for the commercialPackaging corresponding to the query.
     *
     * @param query    the query of the search
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
