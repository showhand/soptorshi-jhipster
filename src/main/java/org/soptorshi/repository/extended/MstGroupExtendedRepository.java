package org.soptorshi.repository.extended;

import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.MstGroupRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MstGroupExtendedRepository extends MstGroupRepository {
    @Query(
        value="select mst_group.* from mst_group, ( " +
            "with recursive groupCTE(id, name, main_group, reserved_flag, modified_by, modified_on) as ( " +
            "   select id, name, main_group, reserved_flag, modified_by, modified_on  from mst_group " +
            "   where main_group=?1 " +
            " " +
            "   union all " +
            " " +
            "   select g.id, g.name, g.main_group, g.reserved_flag, g.modified_by, g.modified_on from mst_group g " +
            "   inner join groupCTE c on g.main_group = c.id " +
            ") " +
            "select id from groupCTE) cte where cte.id=mst_group.id order by mst_group.main_group",
        nativeQuery = true
    )
    List<MstGroup> findByMainGroup(Long mainGroupId);
}
