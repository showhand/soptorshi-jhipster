package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ProductPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductPrice and its DTO ProductPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductPriceMapper extends EntityMapper<ProductPriceDTO, ProductPrice> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ProductPriceDTO toDto(ProductPrice productPrice);

    @Mapping(source = "productId", target = "product")
    ProductPrice toEntity(ProductPriceDTO productPriceDTO);

    default ProductPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductPrice productPrice = new ProductPrice();
        productPrice.setId(id);
        return productPrice;
    }
}
