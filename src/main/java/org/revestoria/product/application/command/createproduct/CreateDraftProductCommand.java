package org.revestoria.product.application.command.createproduct;

import org.revestoria.core.shared.application.Command;
import org.revestoria.core.shared.domain.Money;
import org.revestoria.product.application.dto.ProductResponse;

import java.util.UUID;

public record CreateDraftProductCommand(
        String title,
        String description,
        Money price,
        String productCondition,
        String brand,
        String size,
        String color,
        String material,
        UUID categoryId,
        UUID sellerId
) implements Command<ProductResponse>{
}
