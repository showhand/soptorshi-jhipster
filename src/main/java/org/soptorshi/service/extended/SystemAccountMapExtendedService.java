package org.soptorshi.service.extended;


import net.sf.cglib.core.Local;
import org.soptorshi.repository.SystemAccountMapRepository;
import org.soptorshi.repository.search.SystemAccountMapSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SystemAccountMapService;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.mapper.SystemAccountMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SystemAccountMapExtendedService extends SystemAccountMapService {
    public SystemAccountMapExtendedService(SystemAccountMapRepository systemAccountMapRepository, SystemAccountMapMapper systemAccountMapMapper, SystemAccountMapSearchRepository systemAccountMapSearchRepository) {
        super(systemAccountMapRepository, systemAccountMapMapper, systemAccountMapSearchRepository);
    }

    @Override
    public SystemAccountMapDTO save(SystemAccountMapDTO systemAccountMapDTO) {
        systemAccountMapDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        systemAccountMapDTO.setModifiedOn(LocalDate.now());
        return super.save(systemAccountMapDTO);
    }
}
