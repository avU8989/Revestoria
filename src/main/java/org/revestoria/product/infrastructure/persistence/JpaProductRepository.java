package org.revestoria.product.infrastructure.persistence;

import org.revestoria.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
    List<ProductJpaEntity> findByProductStatus(String productStatus);
}
