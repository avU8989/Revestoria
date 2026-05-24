package org.revestoria.product.web.requests;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateDraftProductRequest(
        String title,
        String description,
        BigDecimal priceAmount,
        String priceCurrency,
        String productCondition,
        String brand,
        String size,
        String color,
        String material,
        UUID categoryId,
        UUID sellerId
) {}