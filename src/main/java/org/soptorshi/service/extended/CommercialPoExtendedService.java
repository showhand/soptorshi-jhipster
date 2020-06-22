package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.repository.CommercialPoRepository;
import org.soptorshi.repository.search.CommercialPoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPoService;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing CommercialPo.
 */
@Service
@Transactional
public class CommercialPoExtendedService extends CommercialPoService {

    private final Logger log = LoggerFactory.getLogger(CommercialPoExtendedService.class);

    private final CommercialPoRepository commercialPoRepository;

    private final CommercialPoMapper commercialPoMapper;

    private final CommercialPoSearchRepository commercialPoSearchRepository;

    public CommercialPoExtendedService(CommercialPoRepository commercialPoRepository, CommercialPoMapper commercialPoMapper, CommercialPoSearchRepository commercialPoSearchRepository) {
        super(commercialPoRepository, commercialPoMapper, commercialPoSearchRepository);
        this.commercialPoRepository = commercialPoRepository;
        this.commercialPoMapper = commercialPoMapper;
        this.commercialPoSearchRepository = commercialPoSearchRepository;
    }

    /**
     * Save a commercialPo.
     *
     * @param commercialPoDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPoDTO save(CommercialPoDTO commercialPoDTO) {
        log.debug("Request to save CommercialPo : {}", commercialPoDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(commercialPoDTO.getId() == null) {
            commercialPoDTO.setCreatedBy(currentUser);
            commercialPoDTO.setCreatedOn(currentDateTime);
        }
        else {
            commercialPoDTO.setUpdatedBy(currentUser);
            commercialPoDTO.setUpdatedOn(currentDateTime);
        }
        CommercialPo commercialPo = commercialPoMapper.toEntity(commercialPoDTO);
        commercialPo = commercialPoRepository.save(commercialPo);
        CommercialPoDTO result = commercialPoMapper.toDto(commercialPo);
        commercialPoSearchRepository.save(commercialPo);
        return result;
    }
}
