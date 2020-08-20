package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.HolidayType;
import org.soptorshi.repository.extended.HolidayTypeExtendedRepository;
import org.soptorshi.repository.search.HolidayTypeSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.HolidayTypeService;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.mapper.HolidayTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing HolidayType.
 */
@Service
@Transactional
public class HolidayTypeExtendedService extends HolidayTypeService {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeExtendedService.class);

    private final HolidayTypeExtendedRepository holidayTypeExtendedRepository;

    private final HolidayTypeMapper holidayTypeMapper;

    private final HolidayTypeSearchRepository holidayTypeSearchRepository;

    public HolidayTypeExtendedService(HolidayTypeExtendedRepository holidayTypeExtendedRepository, HolidayTypeMapper holidayTypeMapper, HolidayTypeSearchRepository holidayTypeSearchRepository) {
        super(holidayTypeExtendedRepository, holidayTypeMapper, holidayTypeSearchRepository);
        this.holidayTypeExtendedRepository = holidayTypeExtendedRepository;
        this.holidayTypeMapper = holidayTypeMapper;
        this.holidayTypeSearchRepository = holidayTypeSearchRepository;
    }

    /**
     * Save a holidayType.
     *
     * @param holidayTypeDTO the entity to save
     * @return the persisted entity
     */
    public HolidayTypeDTO save(HolidayTypeDTO holidayTypeDTO) {
        log.debug("Request to save HolidayType : {}", holidayTypeDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (holidayTypeDTO.getId() == null) {
            holidayTypeDTO.setCreatedBy(currentUser);
            holidayTypeDTO.setCreatedOn(currentDateTime);
        } else {
            holidayTypeDTO.setUpdatedBy(currentUser);
            holidayTypeDTO.setUpdatedOn(currentDateTime);
        }

        HolidayType holidayType = holidayTypeMapper.toEntity(holidayTypeDTO);
        holidayType = holidayTypeExtendedRepository.save(holidayType);
        HolidayTypeDTO result = holidayTypeMapper.toDto(holidayType);
        holidayTypeSearchRepository.save(holidayType);
        return result;
    }

    private boolean exists(HolidayTypeDTO holidayTypeDTO) {
        return holidayTypeExtendedRepository.existsByName(holidayTypeDTO.getName());
    }

    public List<HolidayType> getAll() {
        return holidayTypeExtendedRepository.findAll();
    }
}
