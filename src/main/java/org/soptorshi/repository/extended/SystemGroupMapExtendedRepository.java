package org.soptorshi.repository.extended;

import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.SystemGroupMapRepository;

public interface SystemGroupMapExtendedRepository extends SystemGroupMapRepository {
    SystemGroupMap getByGroupType(GroupType groupType);
}
