package org.revestoria.product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
    List<ProductJpaEntity> findByProductStatus(String productStatus);
}
