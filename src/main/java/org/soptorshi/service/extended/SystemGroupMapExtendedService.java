package org.soptorshi.service.extended;

import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.search.SystemGroupMapSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SystemGroupMapService;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.mapper.SystemGroupMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SystemGroupMapExtendedService extends SystemGroupMapService {
    public SystemGroupMapExtendedService(SystemGroupMapRepository systemGroupMapRepository, SystemGroupMapMapper systemGroupMapMapper, SystemGroupMapSearchRepository systemGroupMapSearchRepository) {
        super(systemGroupMapRepository, systemGroupMapMapper, systemGroupMapSearchRepository);
    }

    @Override
    public SystemGroupMapDTO save(SystemGroupMapDTO systemGroupMapDTO) {
        systemGroupMapDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        systemGroupMapDTO.setModifiedOn(LocalDate.now());
        return super.save(systemGroupMapDTO);
    }
}
