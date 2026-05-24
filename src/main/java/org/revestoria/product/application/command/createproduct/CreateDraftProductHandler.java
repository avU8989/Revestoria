package org.revestoria.product.application.command.createproduct;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.revestoria.core.shared.application.CommandHandler;
import org.revestoria.core.shared.domain.Money;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.application.ports.ProductRepository;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductFactory;

public class CreateDraftProductHandler implements CommandHandler<CreateDraftProductCommand, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductFactory productFactory;

    public CreateDraftProductHandler(ProductRepository productRepository, ProductFactory productFactory){
        this.productFactory = productFactory;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse handle(CreateDraftProductCommand command) {
        Product product = productFactory.createDraft(
                command.title(),
                command.description(),
                new Money(command.priceAmount(), command.priceCurrency()),
                command.productCondition(),
                command.brand(),
                command.size(),
                command.color(),
                command.material(),
                command.categoryId(),
                command.sellerId()
        );

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    @Override
    public Class<CreateDraftProductCommand> commandType() {
        return CreateDraftProductCommand.class;
    }

}
