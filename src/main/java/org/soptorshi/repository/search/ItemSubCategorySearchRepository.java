package org.soptorshi.repository.search;

import org.soptorshi.domain.ItemSubCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ItemSubCategory entity.
 */
public interface ItemSubCategorySearchRepository extends ElasticsearchRepository<ItemSubCategory, Long> {
}
