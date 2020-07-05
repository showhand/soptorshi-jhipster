package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.SupplyZoneManagerExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneManagerService;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing SupplyZoneManager.
 */
@Service
@Transactional
public class SupplyZoneManagerExtendedService extends SupplyZoneManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerExtendedService.class);

    private final SupplyZoneManagerExtendedRepository supplyZoneManagerExtendedRepository;

    private final SupplyZoneManagerMapper supplyZoneManagerMapper;

    private final SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository;

    public SupplyZoneManagerExtendedService(SupplyZoneManagerExtendedRepository supplyZoneManagerExtendedRepository, SupplyZoneManagerMapper supplyZoneManagerMapper, SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository) {
        super(supplyZoneManagerExtendedRepository, supplyZoneManagerMapper, supplyZoneManagerSearchRepository);
        this.supplyZoneManagerExtendedRepository = supplyZoneManagerExtendedRepository;
        this.supplyZoneManagerMapper = supplyZoneManagerMapper;
        this.supplyZoneManagerSearchRepository = supplyZoneManagerSearchRepository;
    }

    public SupplyZoneManagerDTO save(SupplyZoneManagerDTO supplyZoneManagerDTO) {
        log.debug("Request to save SupplyZoneManager : {}", supplyZoneManagerDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyZoneManagerDTO.getId() == null) {
            supplyZoneManagerDTO.setCreatedBy(currentUser);
            supplyZoneManagerDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyZoneManagerDTO.setUpdatedBy(currentUser);
            supplyZoneManagerDTO.setUpdatedOn(currentDateTime);
        }

        SupplyZoneManager supplyZoneManager = supplyZoneManagerMapper.toEntity(supplyZoneManagerDTO);
        supplyZoneManager = supplyZoneManagerExtendedRepository.save(supplyZoneManager);
        SupplyZoneManagerDTO result = supplyZoneManagerMapper.toDto(supplyZoneManager);
        supplyZoneManagerSearchRepository.save(supplyZoneManager);
        return result;
    }

    public List<SupplyZoneManager> getZoneManagers(Employee employee) {
        return supplyZoneManagerExtendedRepository.getAllByEmployee(employee);
    }

    public List<SupplyZoneManager> getZoneManagers(Employee employee, SupplyZoneManagerStatus supplyZoneManagerStatus) {
        return supplyZoneManagerExtendedRepository.getAllByEmployeeAndStatus(employee, supplyZoneManagerStatus);
    }
}
