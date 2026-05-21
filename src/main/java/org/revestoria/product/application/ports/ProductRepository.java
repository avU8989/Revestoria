package org.revestoria.product.application.ports;

import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    boolean existsById(UUID id);
    List<Product> findByStatus(ProductStatus productStatus);
}
