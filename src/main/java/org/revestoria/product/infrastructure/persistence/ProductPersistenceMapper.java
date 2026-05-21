package org.revestoria.product.infrastructure.persistence;

import org.revestoria.core.shared.domain.Money;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductCondition;
import org.revestoria.product.domain.ProductStatus;

public class ProductPersistenceMapper {

    public ProductJpaEntity toJpa(Product product){
        ProductJpaEntity entity = new ProductJpaEntity(
                product.getId(),
                product.getTitle(),
                product.getPrice().getAmount(),
                product.getDescription(),
                product.getPrice().getCurrency(),
                product.getProductStatus().name(),
                product.getProductCondition().name(),
                product.getBrand(),
                product.getSize(),
                product.getColor(),
                product.getMaterial(),
                product.getSellerId(),
                product.getCategoryId(),
                product.getReservedUntil(),
                product.getReservedByUserId(),
                product.getPublishedAt()
        );

        return entity;
    }

    public Product toDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                new Money(entity.getPriceAmount(), entity.getPriceCurrency()),
                ProductStatus.valueOf(entity.getProductStatus()),
                ProductCondition.valueOf(entity.getProductCondition()),
                entity.getBrand(),
                entity.getSize(),
                entity.getColor(),
                entity.getMaterial(),
                entity.getCategoryId(),
                entity.getSellerId(),
                entity.getReservedUntil(),
                entity.getReservedByUserId(),
                entity.getPublishedAt()
        );
    }
}
