package org.soptorshi.repository;

import org.soptorshi.domain.ItemSubCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemSubCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Long>, JpaSpecificationExecutor<ItemSubCategory> {

}
