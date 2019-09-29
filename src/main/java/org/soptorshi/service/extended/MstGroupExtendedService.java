package org.soptorshi.service.extended;

import org.soptorshi.repository.MstGroupRepository;
import org.soptorshi.repository.search.MstGroupSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.MstGroupService;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.mapper.MstGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MstGroupExtendedService extends MstGroupService {
    public MstGroupExtendedService(MstGroupRepository mstGroupRepository, MstGroupMapper mstGroupMapper, MstGroupSearchRepository mstGroupSearchRepository) {
        super(mstGroupRepository, mstGroupMapper, mstGroupSearchRepository);
    }

    @Override
    public MstGroupDTO save(MstGroupDTO mstGroupDTO) {
        mstGroupDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        mstGroupDTO.setModifiedOn(LocalDate.now());
        return super.save(mstGroupDTO);
    }
}
