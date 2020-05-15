package org.soptorshi.repository.extended;

import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.MstAccountRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstAccountExtendedRepository extends MstAccountRepository {
    List<MstAccount> getAllByGroup(MstGroup group);
}
