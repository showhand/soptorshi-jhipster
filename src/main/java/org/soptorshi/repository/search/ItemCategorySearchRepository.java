package org.soptorshi.repository.search;

import org.soptorshi.domain.ItemCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ItemCategory entity.
 */
public interface ItemCategorySearchRepository extends ElasticsearchRepository<ItemCategory, Long> {
}
