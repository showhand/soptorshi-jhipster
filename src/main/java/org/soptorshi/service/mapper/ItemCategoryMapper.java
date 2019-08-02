package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ItemCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ItemCategory and its DTO ItemCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemCategoryMapper extends EntityMapper<ItemCategoryDTO, ItemCategory> {



    default ItemCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(id);
        return itemCategory;
    }
}
