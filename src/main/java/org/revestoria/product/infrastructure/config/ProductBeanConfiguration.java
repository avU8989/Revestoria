package org.revestoria.product.infrastructure.config;

import org.revestoria.product.application.command.createproduct.CreateDraftProductHandler;
import org.revestoria.product.application.ports.ProductRepository;
import org.revestoria.product.domain.ProductFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//reminder --> Beans are objects known by Spring
//spring sees i have beans for those type of classes and will create them --> DI
@Configuration
public class ProductBeanConfiguration {
    @Bean
    public ProductFactory productFactory(){
        return new ProductFactory();
    }

    @Bean
    public CreateDraftProductHandler createDraftProductHandler(ProductRepository productRepository, ProductFactory productFactory){
        return new CreateDraftProductHandler(productRepository, productFactory);
    }
}
