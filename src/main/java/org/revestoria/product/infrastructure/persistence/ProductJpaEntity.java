package org.revestoria.product.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceAmount;

    @Column(nullable = false, length = 3)
    private String priceCurrency;

    @Column(nullable = false)
    private String productStatus;

    @Column(nullable = false)
    private String productCondition;

    private String brand;
    private String size;
    private String color;
    private String material;

    private UUID categoryId;

    private UUID sellerId;
    private Instant reservedUntil;
    private UUID reservedByUserId;
    private Instant publishedAt;

    protected ProductJpaEntity(){}

    public ProductJpaEntity(UUID id,
                            String title,
                            BigDecimal priceAmount,
                            String description,
                            String priceCurrency,
                            String productStatus,
                            String productCondition,
                            String brand,
                            String size,
                            String color,
                            String material,
                            UUID sellerId,
                            UUID categoryId,
                            Instant reservedUntil,
                            UUID reservedByUserId,
                            Instant publishedAt) {
        this.id = id;
        this.title = title;
        this.priceAmount = priceAmount;
        this.description = description;
        this.priceCurrency = priceCurrency;
        this.productStatus = productStatus;
        this.productCondition = productCondition;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.material = material;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.reservedUntil = reservedUntil;
        this.reservedByUserId = reservedByUserId;
        this.publishedAt = publishedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public UUID getSellerId() {return sellerId; }

    public UUID getCategoryId() {
        return categoryId;
    }

    public Instant getReservedUntil() {
        return reservedUntil;
    }

    public UUID getReservedByUserId() {
        return reservedByUserId;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
}
