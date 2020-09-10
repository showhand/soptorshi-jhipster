package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.repository.extended.LeaveTypeExtendedRepository;
import org.soptorshi.repository.search.LeaveTypeSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.LeaveTypeService;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.mapper.LeaveTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing LeaveType.
 */
@Service
@Transactional
public class LeaveTypeExtendedService extends LeaveTypeService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeExtendedService.class);

    private final LeaveTypeExtendedRepository leaveTypeExtendedRepository;

    private final LeaveTypeMapper leaveTypeMapper;

    public LeaveTypeExtendedService(LeaveTypeExtendedRepository leaveTypeExtendedRepository, LeaveTypeMapper leaveTypeMapper, LeaveTypeSearchRepository leaveTypeSearchRepository) {
        super(leaveTypeExtendedRepository, leaveTypeMapper, leaveTypeSearchRepository);
        this.leaveTypeExtendedRepository = leaveTypeExtendedRepository;
        this.leaveTypeMapper = leaveTypeMapper;
    }

    /**
     * Save a leaveType.
     *
     * @param leaveTypeDTO the entity to save
     * @return the persisted entity
     */
    public LeaveTypeDTO save(LeaveTypeDTO leaveTypeDTO) {
        log.debug("Request to save LeaveType : {}", leaveTypeDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ?
            SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (leaveTypeDTO.getId() == null) {
            leaveTypeDTO.setCreatedBy(currentUser);
            leaveTypeDTO.setCreatedOn(currentDateTime);
        } else {
            leaveTypeDTO.setUpdatedBy(currentUser);
            leaveTypeDTO.setUpdatedOn(currentDateTime);
        }
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeDTO);
        leaveType = leaveTypeExtendedRepository.save(leaveType);
        LeaveTypeDTO result = leaveTypeMapper.toDto(leaveType);
        //leaveTypeSearchRepository.save(leaveType);
        return result;
    }

    public List<LeaveType> getAll() {
        return leaveTypeExtendedRepository.findAll();
    }
}
