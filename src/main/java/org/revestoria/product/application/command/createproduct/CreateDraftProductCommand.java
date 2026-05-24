package org.revestoria.product.application.command.createproduct;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.revestoria.core.shared.application.Command;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.domain.ProductCondition;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateDraftProductCommand(
        @NotBlank(message = "Product title is required")
        String title,
        String description,

        @NotNull(message = "Product price is required")
        BigDecimal priceAmount,

        @NotBlank(message ="Currency is required")
        String priceCurrency,

        @NotNull(message = "Product condition is required")
        ProductCondition productCondition,

        String brand,
        String size,
        String color,
        String material,
        UUID categoryId,

        @NotNull(message = "Seller id is required")
        UUID sellerId
) implements Command<ProductResponse>{
}
