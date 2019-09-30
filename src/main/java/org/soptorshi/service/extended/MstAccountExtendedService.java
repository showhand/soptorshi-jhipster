package org.soptorshi.service.extended;

import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MstAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MstAccountExtendedService extends MstAccountService {
    public MstAccountExtendedService(MstAccountRepository mstAccountRepository, MstAccountMapper mstAccountMapper, MstAccountSearchRepository mstAccountSearchRepository) {
        super(mstAccountRepository, mstAccountMapper, mstAccountSearchRepository);
    }

    @Override
    public MstAccountDTO save(MstAccountDTO mstAccountDTO) {
        mstAccountDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        mstAccountDTO.setModifiedOn(LocalDate.now());
        return super.save(mstAccountDTO);
    }
}
