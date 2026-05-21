package org.revestoria.product.infrastructure.persistence;

import org.revestoria.product.application.ports.ProductRepository;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter implements ProductRepository{
    private final JpaProductRepository jpaProductRepository;
    private final ProductPersistenceMapper mapper;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository, ProductPersistenceMapper mapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = mapper.toJpa(product);
        ProductJpaEntity savedEntity = jpaProductRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaProductRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaProductRepository.existsById(id);
    }

    @Override
    public List<Product> findByStatus(ProductStatus productStatus) {

        return jpaProductRepository.findByProductStatus(productStatus.name())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
