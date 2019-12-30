package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialProductInfoService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialProductInfo.
 */
@Service
@Transactional
public class CommercialProductInfoExtendedService extends CommercialProductInfoService {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoExtendedService.class);

    private final CommercialProductInfoRepository commercialProductInfoRepository;

    private final CommercialProductInfoMapper commercialProductInfoMapper;

    private final CommercialProductInfoSearchRepository commercialProductInfoSearchRepository;

    private final CommercialBudgetExtendedService commercialBudgetExtendedService;

    public CommercialProductInfoExtendedService(CommercialProductInfoRepository commercialProductInfoRepository, CommercialProductInfoMapper commercialProductInfoMapper, CommercialProductInfoSearchRepository commercialProductInfoSearchRepository, CommercialBudgetExtendedService commercialBudgetExtendedService) {
        super(commercialProductInfoRepository, commercialProductInfoMapper, commercialProductInfoSearchRepository);
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
}
