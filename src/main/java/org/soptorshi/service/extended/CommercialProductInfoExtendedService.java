package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialProductInfo.
 */
@Service
@Transactional
public class CommercialProductInfoExtendedService {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoExtendedService.class);

    private final CommercialProductInfoRepository commercialProductInfoRepository;

    private final CommercialProductInfoMapper commercialProductInfoMapper;

    private final CommercialProductInfoSearchRepository commercialProductInfoSearchRepository;

    private final CommercialBudgetExtendedService commercialBudgetExtendedService;

    public CommercialProductInfoExtendedService(CommercialProductInfoRepository commercialProductInfoRepository, CommercialProductInfoMapper commercialProductInfoMapper, CommercialProductInfoSearchRepository commercialProductInfoSearchRepository, CommercialBudgetExtendedService commercialBudgetExtendedService) {
        this.commercialProductInfoRepository = commercialProductInfoRepository;
        this.commercialProductInfoMapper = commercialProductInfoMapper;
        this.commercialProductInfoSearchRepository = commercialProductInfoSearchRepository;
        this.commercialBudgetExtendedService = commercialBudgetExtendedService;
    }

    /**
     * Save a commercialProductInfo.
     *
     * @param commercialProductInfoDTO the entity to save
     * @return the persisted entity
     */

    @Transactional
    public CommercialProductInfoDTO save(CommercialProductInfoDTO commercialProductInfoDTO) {
        log.debug("Request to save CommercialProductInfo : {}", commercialProductInfoDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        Instant currentDateTime = Instant.now();

        if (commercialProductInfoDTO.getId() == null) {
            commercialProductInfoDTO.setCreatedBy(currentUser);
            commercialProductInfoDTO.setCreatedOn(currentDateTime);
        } else {
            commercialProductInfoDTO.setUpdatedBy(currentUser);
            commercialProductInfoDTO.setUpdatedOn(currentDateTime);
        }
        CommercialProductInfo commercialProductInfo = commercialProductInfoMapper.toEntity(commercialProductInfoDTO);
        commercialProductInfo = commercialProductInfoRepository.save(commercialProductInfo);
        CommercialProductInfoDTO result = commercialProductInfoMapper.toDto(commercialProductInfo);

        commercialProductInfoSearchRepository.save(commercialProductInfo);
        Optional<CommercialBudgetDTO> commercialBudgetDTO = commercialBudgetExtendedService.findOne(commercialProductInfo.getCommercialBudget().getId());
        if (commercialBudgetDTO.isPresent()) {
            commercialBudgetDTO.get().setTotalQuantity(commercialBudgetDTO.get().getTotalQuantity() == null ? commercialProductInfo.getOfferedQuantity() : commercialBudgetDTO.get().getTotalQuantity().add(commercialProductInfo.getOfferedQuantity()));
            commercialBudgetDTO.get().setTotalOfferedPrice(commercialBudgetDTO.get().getTotalOfferedPrice() == null ? commercialProductInfo.getOfferedTotalPrice() :
                commercialBudgetDTO.get().getTotalOfferedPrice().add(commercialProductInfo.getOfferedTotalPrice()));
            commercialBudgetDTO.get().setTotalBuyingPrice(commercialBudgetDTO.get().getTotalBuyingPrice() == null ? commercialProductInfo.getBuyingTotalPrice() : commercialBudgetDTO.get().getTotalBuyingPrice().add(commercialProductInfo.getBuyingTotalPrice()));
            commercialBudgetDTO.get().setProfitAmount(commercialBudgetDTO.get().getTotalOfferedPrice().subtract(commercialBudgetDTO.get().getTotalBuyingPrice()));
            commercialBudgetDTO.get().setProfitPercentage(commercialBudgetDTO.get().getTotalBuyingPrice().divide(commercialBudgetDTO.get().getTotalOfferedPrice().subtract(commercialBudgetDTO.get().getTotalBuyingPrice()).multiply(BigDecimal.valueOf(100)), 4, RoundingMode.HALF_UP));
            commercialBudgetExtendedService.save(commercialBudgetDTO.get());
        }

        return result;
    }

    /**
     * Get all the commercialProductInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProductInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialProductInfos");
        return commercialProductInfoRepository.findAll(pageable)
            .map(commercialProductInfoMapper::toDto);
    }


    /**
     * Get one commercialProductInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialProductInfoDTO> findOne(Long id) {
        log.debug("Request to get CommercialProductInfo : {}", id);
        return commercialProductInfoRepository.findById(id)
            .map(commercialProductInfoMapper::toDto);
    }

    /**
     * Delete the commercialProductInfo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialProductInfo : {}", id);
        commercialProductInfoRepository.deleteById(id);
        commercialProductInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialProductInfo corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProductInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialProductInfos for query {}", query);
        return commercialProductInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialProductInfoMapper::toDto);
    }
}
