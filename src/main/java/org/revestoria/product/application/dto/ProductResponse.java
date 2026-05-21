package org.revestoria.product.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.revestoria.product.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String title,
        String description,

        @JsonProperty("price_amount")
        BigDecimal priceAmount,

        @JsonProperty("price_currency")
        String priceCurrency,

        String status,
        String condition,
        String brand,
        String size,
        String color,
        String material,

        @JsonProperty("category_id")
        UUID categoryId,
        UUID sellerId
) {
    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice().getAmount(),
                product.getPrice().getCurrency(),
                product.getProductStatus().name(),
                product.getProductCondition().name(),
                product.getBrand(),
                product.getSize(),
                product.getColor(),
                product.getMaterial(),
                product.getCategoryId(),
                product.getSellerId()
        );
    }
}
