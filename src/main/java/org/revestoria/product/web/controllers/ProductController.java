package org.revestoria.product.web.controllers;

import jakarta.validation.Valid;
import org.revestoria.core.shared.application.CommandBus;
import org.revestoria.product.application.command.createproduct.CreateDraftProductCommand;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.domain.ProductCondition;
import org.revestoria.product.web.requests.CreateDraftProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final CommandBus commandBus;

    public ProductController(CommandBus commandBus){
        this.commandBus = commandBus;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createDraftProduct(@Valid @RequestBody CreateDraftProductRequest request){
        CreateDraftProductCommand command = new CreateDraftProductCommand(
                request.title(),
                request.description(),
                request.priceAmount(),
                request.priceCurrency(),
                ProductCondition.valueOf(request.productCondition()),
                request.brand(),
                request.size(),
                request.color(),
                request.material(),
                request.categoryId(),
                request.sellerId()
        );

        ProductResponse response = commandBus.send(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
