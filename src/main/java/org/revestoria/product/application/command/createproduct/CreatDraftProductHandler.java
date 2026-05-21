package org.revestoria.product.application.command.createproduct;

import jakarta.transaction.Transactional;
import org.revestoria.core.shared.application.CommandHandler;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.application.ports.ProductRepository;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductCondition;
import org.revestoria.product.domain.ProductFactory;

public class CreatDraftProductHandler implements CommandHandler<CreateDraftProductCommand, ProductResponse> {

    private final ProductRepository productRepository;
    private final ProductFactory productFactory;

    public CreatDraftProductHandler(ProductRepository productRepository, ProductFactory productFactory){
        this.productFactory = productFactory;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse handle(CreateDraftProductCommand command) {
        Product product = productFactory.createDraft(
                command.title(),
                command.description(),
                command.price(),
                ProductCondition.valueOf(command.productCondition()),
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
