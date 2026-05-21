package org.revestoria.product.domain;

import org.revestoria.core.shared.domain.Money;

import java.util.UUID;

public class ProductFactory {
    public Product createDraft(
            String title,
            String description,
            Money price,
            ProductCondition productCondition,
            String brand,
            String size,
            String color,
            String material,
            UUID categoryId,
            UUID sellerId
    ){
        return new Product(
                UUID.randomUUID(),
                title,
                description,
                price,
                ProductStatus.DRAFT,
                productCondition,
                brand,
                size,
                color,
                material,
                categoryId,
                sellerId,
                null,
                null,
                null
        );
    }
}
